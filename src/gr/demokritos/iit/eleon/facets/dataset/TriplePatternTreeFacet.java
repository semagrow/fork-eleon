/***************

<p>Title: Entity Inclusion Tree Facet</p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.
<br> Copyright (c) 2001-2014 National Centre for Scientific Research "Demokritos"
</p>

<p>
ELEON is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
</p>

<p>
ELEON is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
</p>

<p>
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
</p>

@author Stasinos Konstantopoulos (INDIGO, 2009; RoboSKEL 2011; SemaGrow 2012-2014)

***************/


package gr.demokritos.iit.eleon.facets.dataset;

import java.util.ArrayList;
import java.util.List;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.facets.TreeFacet;
import gr.demokritos.iit.eleon.ui.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
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
		this.resMyself = this.myShell.ont.createResource( TriplePatternTreeFacet.myRDFName );
	}
	

	/*
	 * TreeFacet IMPLEMENTATION
	 */


	@Override
	public void initTree()
	{
		super.initTree();
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
				String se = (String)treeNodeData.getValue("void:sparqlEnpoint");
				if( se != null) { setInfo( se ); }
				else { setInfo( "" ); }
				myShell.createTableContents( myTree );
			}
		});
		//myTree.setBounds(318, 84, 369, 578);
		
		/*TreeItem root = new TreeItem( myTree, SWT.NONE );
		root.setText("root");*/
		
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
					newItem.setData(data);
					newItem.setText(label);
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
					if (result[0] == null && result[0] == null ) {
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
						nodeData.setOwner( myShell.annotators.getActiveResource() );
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
						item.setText(itemText);
					}
					//System.out.println("Insert Into - " + selected[0].getText());
				} else {
					MessageBox box = new MessageBox( myShell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Nothing selected.");
	                box.open();
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
					dialogCopy.copy(myTree, selected[0]);
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
		p = dataset.getModel().getProperty( "http://rdf.iit.demokritos.gr/2013/sevod#subjectClass" );
		stmt = dataset.getProperty( p );
		if( stmt != null ) {
			subC = stmt.getObject().asResource().getURI();
			subC = this.myShell.ont.shortForm( subC );
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
			pred = this.myShell.ont.shortForm( pred );
			label += " " + pred; 
		}
		else {
			label += " ?p ";
		}
		
		String objC = "";
		p = dataset.getModel().getProperty( "http://rdf.iit.demokritos.gr/2013/sevod#objectClass" );
		stmt = dataset.getProperty( p );
		if( stmt != null ) {
			objC = stmt.getObject().asResource().getURI();
			objC = this.myShell.ont.shortForm( objC );
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
