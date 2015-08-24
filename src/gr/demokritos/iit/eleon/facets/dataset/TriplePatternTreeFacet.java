/***************

<p>Title: </p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

***************/


/***************

<p>Title: Entity Inclusion Tree Facet</p>

<p>Description:
***************/


package gr.demokritos.iit.eleon.facets.dataset;

import java.util.ArrayList;
import java.util.List;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.facets.TreeFacet;
import gr.demokritos.iit.eleon.ui.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TreeItem;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.*;


public class TriplePatternTreeFacet extends DatasetFacet implements TreeFacet
{
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger( TriplePatternTreeFacet.class );
	static final String myRDFName = "http://rdf.iit.demokritos.gr/2013/sevod#patternFacet";
	
	private final Resource resMyself;	

	
	/*
	 * CONSTRUCTOR
	 */

	
	public TriplePatternTreeFacet( MainShell shell )
	{
		super( shell );
		this.resMyself = this.myShell.data.createResource( TriplePatternTreeFacet.myRDFName );
	}
	

	/*
	 * Facet IMPLEMENTATION
	 */

	@Override
	public boolean isAutoFilled() { return false; }


	@Override
	public boolean isEditable() { return true; }

	@Override
	public void update() throws IllegalArgumentException
	{ throw new IllegalArgumentException( "Cannot auto-fill" ); }


	@Override
	public void init( boolean generateTree )
	throws IllegalArgumentException
	{
		// This facet is manual; let my super explode if generateTree is true 
		super.init( generateTree );

		myTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//check to avoid strange bug when the listener is activated but no selection exists
				//causing java.lang.ArrayIndexOutOfBoundsException: 0
				if (myTree.getSelection().length==0) {
					setTitle( "" );
					setInfo( "" );
					if ( myShell.table!=null && !myShell.table.isDisposed() ) {
						myShell.table.dispose();
						myShell.table = null;
					}
					return;
				}
				if (myTree.getSelection()[0].getText().equals("root")) {
					setTitle( "" );
					setInfo( "" );
					if ( myShell.table!=null && !myShell.table.isDisposed() ) {
						myShell.table.dispose();
						myShell.table = null;
					}	
					return;
				}
				DatasetNode treeNodeData = (DatasetNode) myTree.getSelection()[0].getData();
				if (treeNodeData.getLabel() != null) {
					setTitle( treeNodeData.getLabel() );
				}
				else {
					setTitle( "" );
				}
				Resource se = (Resource) treeNodeData.getValue("void:sparqlEndpoint");
				if( se != null) { setInfo( se.toString() ); }
				else { setInfo( "" ); }
				myShell.createTableContents( myTree );
			}
		});
		//myTree.setBounds(318, 84, 369, 578);
		
		myTree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				TreeItem selected = myTree.getSelection()[0];
				if (selected.getExpanded()) {
					selected.setExpanded(false);
				} else {
					selected.setExpanded(true);
				}
			}
		});
		
		myTree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_LEFT) {
					if (myTree.getSelection().length==0) return;
					myTree.getSelection()[0].setExpanded(false);
				} else if (e.keyCode == SWT.ARROW_RIGHT) {
					if (myTree.getSelection().length==0) return;
					myTree.getSelection()[0].setExpanded(true);
				} else if ((e.stateMask & SWT.CTRL) != 0) {//Ctrl was pressed
					if (myTree.getSelection().length==0) return;
					if (e.keyCode == SWT.ARROW_DOWN) {
						TreeItem parent = myTree.getSelection()[0].getParentItem();
						if (parent == null) return;//root has no siblings
						TreeItem[] children = parent.getItems();
						int index = -1;
						for (int i=0; i<children.length; i++) {
							if (myTree.getSelection()[0] == children[i]) {
								index = i;
								break;
							}
						}
						assert index != -1;
						if (index == children.length - 1) {
							myTree.setSelection(children[0]);
							myTree.notifyListeners(SWT.Selection, new Event());
						} else {
							myTree.setSelection(children[index + 1]);
							myTree.notifyListeners(SWT.Selection, new Event());
						}
					} else if (e.keyCode == SWT.ARROW_UP) {
						TreeItem parent = myTree.getSelection()[0].getParentItem();
						if (parent == null) return;//root has no siblings
						TreeItem[] children = parent.getItems();
						int index = -1;
						for (int i=0; i<children.length; i++) {
							if (myTree.getSelection()[0] == children[i]) {
								index = i;
								break;
							}
						}
						assert index != -1;
						if (index == 0) {
							myTree.setSelection(children[children.length - 1]);
							myTree.notifyListeners(SWT.Selection, new Event());
						} else {
							myTree.setSelection(children[index - 1]);
							myTree.notifyListeners(SWT.Selection, new Event());
						}
					}
				}
			}
		});
		
		//insert menu for this tree
		final Menu treeMenu = new Menu( myTree );
		myTree.setMenu(treeMenu);
		
		final MenuItem insertNewDatasource = new MenuItem(treeMenu, SWT.NONE);
		insertNewDatasource.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = myTree.getSelection();
				if (selected.length > 0/* && selected[0].getText().equals("root")*/) {
					InsertInMenuDialog insert = new InsertInMenuDialog( myShell.getShell() );
					String label = insert.open("Insert dataset label");
					if (label == null) return;
					TreeItem newItem = new TreeItem(selected[0], SWT.NONE);
					DatasetNode data = new DatasetNode( null, mySelf );
					data.setOwner( myShell.annotators.getActiveResource() );
					data.setLabel(label);
					newItem.setData(data);
					newItem.setText(label);
					selected[0].setExpanded(true);
				}
			}
		});
		insertNewDatasource.setText("Insert dataset label");
		
		final MenuItem insertNewChild = new MenuItem(treeMenu, SWT.NONE);
	    insertNewChild.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = myTree.getSelection();
				if (selected.length > 0) {
					if (selected[0].getText().equals("root")){
						MessageBox box = new MessageBox(myShell, SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("You cannot add a subset directly under root.");
		                box.open();
		                return;
					}
					// selection.setExpanded(true);
					PerEntityInsertDialog dialog = new PerEntityInsertDialog( myShell );
					String[] result = dialog.open();//result[0] containts the subject pattern and result[1] the object pattern
					//EntityInclusionTreeNode nodeData = dialog.open();
					if (result[0] == null && result[1] == null ) {
						return;
					} else {
						TreeItem selection = selected[0];
						TreeItem item = new TreeItem(selection, SWT.NONE);
						TriplePatternTreeNode nodeData =
								new TriplePatternTreeNode( null, mySelf, null, result[0], null, null, result[1] );
						nodeData.setOwner( myShell.annotators.getActiveResource() );
						item.setData(nodeData);
						String subjectPattern = result[0];
						String objectPattern = result[1];
						String itemText = "";
						/*if (subjectPattern != null) {
							itemText += "(sbj)=" + subjectPattern + " ";
						}
						if (objectPattern != null) {
							itemText += "(obj)=" + objectPattern;
						}*/
						if (subjectPattern == null) {
							itemText += "?s ";
						} else {
							itemText += subjectPattern + " ";
						}
						itemText += "?p ";
						if (objectPattern == null) {
							itemText += "?o";
						} else {
							itemText += objectPattern;
						}
						//TODO:check if node already exists.
						nodeData.setLabel(itemText);
						item.setText(itemText);
						selected[0].setExpanded(true);
					}
					//System.out.println("Insert Into - " + selected[0].getText());
				} else {
					MessageBox box = new MessageBox( myShell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
	                return;
				}
			}

		});
	    insertNewChild.setText("Insert new subset");
	    
	    final MenuItem insertExistingChild = new MenuItem(treeMenu, SWT.NONE);
	    insertExistingChild.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = myTree.getSelection();
				if (selected.length > 0) {
					CopyExistingNodeDialog dialogCopy = new CopyExistingNodeDialog( myShell );
					dialogCopy.open(myTree, selected[0]);
					selected[0].setExpanded(true);
				} else {
					MessageBox box = new MessageBox(myShell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
				}
			}

		});
	    insertExistingChild.setText("Add existing dataset or subset as child");
	    
	    new MenuItem(treeMenu, SWT.SEPARATOR);
	    
	    final MenuItem remove = new MenuItem(treeMenu, SWT.NONE);
	    remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = myTree.getSelection();
				if (selected.length > 0) {
					TreeItem itemToDelete = selected[0];
					if (itemToDelete.getText().equals("root")) {
						MessageBox box = new MessageBox( myShell.getShell(), SWT.OK | SWT.ICON_INFORMATION );
		                box.setText("Info");
		                box.setMessage("Cannot remove root node.");
		                box.open();
		                return;
					}
					Table table = TriplePatternTreeFacet.this.myShell.table;
					if (table != null && !table.isDisposed()) {
						table.dispose();
						table = null;
					}
					itemToDelete.dispose();//simple delete. to be changed later.
				} else {
					MessageBox box = new MessageBox( myShell, SWT.OK | SWT.ICON_INFORMATION );
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
				}
			}

		});
	    remove.setText("Remove");
	}


	@Override
	public void syncFrom( OntModel ont )
	{
		// Get all statements ?r void:subset ?o svd:facet svd:propertyFacet
		OntProperty svd_facet = ont.getOntProperty( DatasetFacet.propFacet );
		Resource svd_me = ont.createResource( TriplePatternTreeFacet.myRDFName );

		List<Resource> datasets = new ArrayList<Resource>();
		StmtIterator stmtss = ont.listStatements( null, svd_facet, svd_me );
		while( stmtss.hasNext() ) {
			datasets.add( stmtss.next().getSubject() );
		}
		syncFrom( ont, datasets );
	}


	/*
	 * INTERNAL HELPERS
	 */

	
	protected TriplePatternTreeNode makeNode( Resource dataset, Resource defaultOwner )
	{
		Property p; Statement stmt;
		String label = "";

		String subC = "";
		p = dataset.getModel().getProperty( "http://rdf.iit.demokritos.gr/2013/sevod#subjectVocabulary" );
		stmt = dataset.getProperty( p );
		if( stmt != null ) {
			subC = stmt.getObject().asResource().getURI();
			subC = this.myShell.data.shortForm( subC );
			label = "(" + subC + ")";
		}
		
		String subR = "";
		p = dataset.getModel().getProperty( "http://rdf.iit.demokritos.gr/2013/sevod#subjectRegexPattern" );
		stmt = dataset.getProperty( p );
		if( stmt != null ) {
			subR = stmt.getObject().asLiteral().getString();
			label += " " + subR; 
		}
		else {
			label += " ?r";
		}
		
		String pred = "";
		p = dataset.getModel().getProperty( "http://rdfs.org/ns/void#property" );
		stmt = dataset.getProperty( p );
		if( stmt != null ) {
			pred = stmt.getObject().asResource().getURI();
			pred = this.myShell.data.shortForm( pred );
			label += " " + pred; 
		}
		else {
			label += " ?p ";
		}
		
		String objC = "";
		p = dataset.getModel().getProperty( "http://rdf.iit.demokritos.gr/2013/sevod#objectVocabulary" );
		stmt = dataset.getProperty( p );
		if( stmt != null ) {
			objC = stmt.getObject().asResource().getURI();
			objC = this.myShell.data.shortForm( objC );
			label = "(" + objC + ") ";
		}
		
		String objR = "";
		p = dataset.getModel().getProperty( "http://rdf.iit.demokritos.gr/2013/sevod#objectRegexPattern" );
		stmt = dataset.getProperty( p );
		if( stmt != null ) {
			objR = stmt.getObject().asLiteral().getString();
			label += objR;
		}
		else {
			label += "?v";
		}

		TriplePatternTreeNode retv =
				new TriplePatternTreeNode( dataset, this, subC, subR, pred, objC, objR );

		copyValues( dataset, retv );
		
		if( retv.getOwner() == null ) {
			retv.setOwner( defaultOwner );
		}
		if( retv.getLabel() == null ) {
			retv.setLabel( label );
		}
		return retv;
	}


	protected void write_facet( OntModel model, DatasetNode node )
	{
		model.add( node.getResource(), this.facet, this.resMyself );
	}

}


/*
This file is part of the ELEON Ontology Authoring and Enrichment Tool.

ELEON is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, see <http://www.gnu.org/licenses/>.
*/
