/***************

<p>Title: Property Tree Facet</p>

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
@author Giannis Mouchakis (SemaGrow 2014)

***************/


package gr.demokritos.iit.eleon.facets.dataset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.facets.TreeFacet;
import gr.demokritos.iit.eleon.ui.InsertInMenuDialog;


public class PropertyTreeFacet extends DatasetFacet implements TreeFacet
{
	
	/*
	 * CONSTRUCTOR
	 */

	
	public PropertyTreeFacet( MainShell shell )
	{
		this.myShell = shell;
	}
	

	/*
	 * TreeFacet IMPLEMENTATION
	 */


	@Override
	public void initTree()
	{
		myTree = new Tree( this.myShell, SWT.BORDER );
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
				if (treeNodeData.getDc_title() != null) {
					setTitle( treeNodeData.getDc_title() );
				}
				else {
					setTitle( "" );
				}
				if (treeNodeData.getVoid_sparqlEnpoint() != null) {
					setInfo( treeNodeData.getVoid_sparqlEnpoint() );
				}
				else {
					setInfo( "" );
				}
				myShell.createTableContents(myTree);
			}
		});
		myTree.setBounds(318, 84, 369, 578);
		
		TreeItem root = new TreeItem(myTree, SWT.NONE);
		root.setText("root");
		
		//insert menu for this tree
		final Menu treeMenu = new Menu(myTree);
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
					DatasetNode data = new DatasetNode( mySelf, myShell.activeAnnotationSchema );
					data.setAuthor( myShell.currentAuthor );
					newItem.setData(data);
					newItem.setText(label);
				}
			}
		});
		insertNewDatasource.setText("Insert dataset label");
		new MenuItem(treeMenu, SWT.SEPARATOR);

	    final MenuItem remove = new MenuItem(treeMenu, SWT.NONE);
	    remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = myTree.getSelection();
				if (selected.length > 0) {
					TreeItem itemToDelete = selected[0];
					if (itemToDelete.getText().equals("root")) {
						
						MessageBox box = new MessageBox(myShell.getShell(), SWT.OK | SWT.ICON_INFORMATION);
		                box.setText("Info");
		                box.setMessage("Cannot remove root node.");
		                box.open();
		                return;
					}
					itemToDelete.dispose();//simple delete. to be changed later.
					Table table = PropertyTreeFacet.this.myShell.table;
					if (table != null && !table.isDisposed()) {
						table.dispose();
						table = null;
					}
					//String name =  itemToDelete.getText();
					//TreeItem parent = itemToDelete.getParentItem();
					//while(deleteNode(parent, name));//We suppose that the user can select only one item to remove.
					//treePerEntity.select(parent);
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

	
	/*
	@Override
	public void buildPropertyTree( Tree propertyTree )
	{
		ExtendedIterator<OntProperty> it = this.ont.listAllOntProperties();
		while( it.hasNext() ) {
			OntProperty p = it.next();
			ExtendedIterator<? extends OntProperty> it2 = p.listSuperProperties( true );
			while( it2.hasNext() ) {
				OntProperty superp = it2.next();
				// TODO
			}
		}
	}
	*/

	
	@Override
	public void syncFrom( OntModel ont )
	{
		Individual top = ont.getIndividual( DatasetFacet.entityTop );
		
		// Get all statements ?r void:subset ?o rdf:type void:Dataset
		OntProperty sub = ont.getOntProperty( DatasetFacet.propSubsumes );
		OntClass propFacet = ont.getOntClass( DatasetFacet.propPropertyDataset );
		ExtendedIterator<Individual> it = ont.listIndividuals( propFacet );
		List<Statement> todo = new ArrayList<Statement>();

		List<Individual> dangling = new ArrayList<Individual>();
		List<TreeItem> done = new ArrayList<TreeItem>();
		while( it.hasNext() ) {
			Individual r = it.next();
			StmtIterator stmts = ont.listStatements( null, sub, r );
			boolean at_least_one = false;
			while( stmts.hasNext() ) {
				Statement stmt = stmts.next();
				if( stmt.getSubject().equals(top) ) {
					OntProperty p = ont.getOntProperty( stmt.getObject().asResource().getURI() );
					PropertyTreeNode n = makeNode( p, null );
					TreeItem treeItem = new TreeItem( this.getRoot(), SWT.NONE );
					treeItem.setText( n.getDc_title() );
					treeItem.setData( n );
					done.add( treeItem );
				}
				else {
					todo.add( stmt );	
				}
				at_least_one = true;
			}
			if( ! at_least_one ) {
				dangling.add( r );
			}
		}
		
		boolean work_more = true;
		List<Statement> todo2;
		while( work_more ) {
			todo2 = new ArrayList<Statement>();

			for( Statement stmt : todo ) {
				Resource superSet = stmt.getSubject();
				Resource subSet = stmt.getObject().asResource();
				// look for superSet in done List
				TreeItem father = null;
				Iterator<TreeItem> it2 = done.iterator();
				while( (father == null) && it2.hasNext() ) {
					TreeItem treeItem = it2.next();
					if( ((PropertyTreeNode)treeItem.getData()).getProperty().asResource().equals(superSet) ) {
						// found my dad
						father = treeItem;
					}
				} // end looking for superSet in done List
				if( father != null ) {
					// add to tree
					PropertyTreeNode n = makeNode( subSet, null );
					TreeItem treeItem = new TreeItem( father, SWT.NONE );
					treeItem.setText( n.getDc_title() );
					treeItem.setData( n );
					done.add( treeItem );
				}
				else {
					// keep for next iteration
					todo2.add( stmt );
				}
			} // end for statements : todo
			if( todo2.size() < todo.size() ) {
				todo.clear();
				todo = todo2;
				todo2 = new ArrayList<Statement>( todo.size() );
			}
			else {
				work_more = false;
			}
		}
		
		if( work_more ) {
			// There are dangling subset statements
		}
		
		if( ! dangling.isEmpty() ) {
			// There are dangling datasets
		}
	}


	@Override
	public void syncTo( OntModel ont )
	{
		// TODO Auto-generated method stub
		
	}

	
	/*
	 * INTERNAL HELPERS 
	 */

	
	private PropertyTreeNode makeNode( Resource dataset, String defaultOwner )
	{
		Property prop = dataset.getModel().getProperty( "http://rdfs.org/ns/void#property" );
		Statement stmt = dataset.getProperty( prop );
		if( stmt == null ) { return null; }
		Resource myProperty = stmt.getObject().asResource();
		PropertyTreeNode retv =
				new PropertyTreeNode( this, myProperty, "activeAnnotationSchema" );

		int i = 0;
		while( DatasetNode.property_qnames[1][i] != null ) {
			String uri = DatasetNode.property_uris[0][i];
			Property p = myProperty.getModel().getProperty( uri );
			stmt = dataset.getProperty( p );
			if( stmt != null ) {
				RDFNode v = stmt.getObject();
				if( v.canAs(Resource.class) ) {
					retv.property_values[1][i] = v;
				}
				else if( v.canAs(Literal.class) ) {
					if( Integer.class.equals(v.asLiteral().getDatatype().getJavaClass()) ) {
						retv.property_values[1][i] = new Integer( v.asLiteral().getInt() );
					}
					else {
						retv.property_values[1][i] = v.asLiteral().getLexicalForm();
					}
				}
			}
			else {
				retv.property_values[1][i] = null;
			}
		}
		
		if( retv.property_values[1][0] == null ) {
			retv.property_values[1][0] = defaultOwner;
		}
		return retv;
	}


}
