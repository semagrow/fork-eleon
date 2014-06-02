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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import com.hp.hpl.jena.rdf.model.*;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.facets.TreeFacet;
import gr.demokritos.iit.eleon.ui.InsertInMenuDialog;


public class PropertyTreeFacet extends DatasetFacet implements TreeFacet
{
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger( PropertyTreeFacet.class );
	
	/*
	 * CONSTRUCTOR
	 */

	
	public PropertyTreeFacet( MainShell shell )
	{
		super( shell );
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
				if (treeNodeData.getLabel() != null) {
					setTitle( treeNodeData.getLabel() );
				}
				else {
					setTitle( "" );
				}
				String se = (String)treeNodeData.getValue("void:sparqlEnpoint");
				if( se != null) { setInfo( se ); }
				else { setInfo( "" ); }
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
					DatasetNode data = new DatasetNode( null, mySelf );
					data.setOwner( myShell.currentAnnotator );
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


	/*
	 * INTERNAL HELPERS 
	 */

	protected PropertyTreeNode makeNode( Resource dataset, String defaultOwner )
	{
		Property prop = dataset.getModel().getProperty( "http://rdfs.org/ns/void#property" );
		Statement stmt = dataset.getProperty( prop );
		Resource myProperty;
		if( stmt == null ) {
			// This node is part of a per-property tree, but not a property partition
			myProperty = null;
		}
		else {
			myProperty = stmt.getObject().asResource();
		}
		PropertyTreeNode retv =
				new PropertyTreeNode( dataset, this, myProperty, "activeAnnotationSchema" );

		copyValues( dataset, retv );
		
		if( retv.getOwner() == null ) {
			retv.setOwner( defaultOwner );
		}
		if( retv.getLabel() == null ) {
			if( myProperty != null ) {
				// label defaults to property name
				String qname = this.myShell.ont.shortForm( myProperty.getURI() );
				retv.setLabel( qname );
			}
			else {
				throw new IllegalArgumentException( "Cannot make a property node without label and without a property" );
			}
		}
		return retv;
	}


}
