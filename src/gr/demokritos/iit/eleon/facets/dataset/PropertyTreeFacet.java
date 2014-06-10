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
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.*;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.facets.TreeFacet;
import gr.demokritos.iit.eleon.ui.InsertInMenuDialog;


public class PropertyTreeFacet extends DatasetFacet implements TreeFacet
{
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger( PropertyTreeFacet.class );
	static final String myRDFName = "http://rdf.iit.demokritos.gr/2013/sevod#propertyFacet";

	private final Resource resMyself;

	/*
	 * CONSTRUCTOR
	 */

	
	public PropertyTreeFacet( MainShell shell )
	{
		super( shell );
		this.resMyself = this.myShell.data.createResource( PropertyTreeFacet.myRDFName );
	}
	

	/*
	 * Facet IMPLEMENTATION
	 */

	@Override
	public boolean isAutoFilled() { return true; }


	@Override
	public boolean isEditable() { return false; }


	@Override
	public void init( boolean generateTree )
	{
		// my super does not know how to init( true ); i'll do that later
		super.init( false );
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
					data.setOwner( myShell.annotators.getActiveResource() );
					data.setLabel(label);
					newItem.setData(data);
					newItem.setText(label);
					selected[0].setExpanded(true);
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
		
	    if( generateTree ) {
	    	update();
	    }
	}


	@Override
	public void syncFrom( OntModel ont )
	{
		// Get all statements ?r void:subset ?o svd:facet svd:propertyFacet
		OntProperty svd_facet = ont.getOntProperty( DatasetFacet.propFacet );
		Resource svd_me = ont.createResource( PropertyTreeFacet.myRDFName );

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

	protected PropertyTreeNode makeNode( Resource dataset, Resource defaultOwner )
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
				new PropertyTreeNode( dataset, this, myProperty );

		copyValues( dataset, retv );
		
		if( retv.getOwner() == null ) {
			retv.setOwner( defaultOwner );
		}
		if( retv.getLabel() == null ) {
			if( myProperty != null ) {
				// label defaults to property name
				String qname = this.myShell.data.shortForm( myProperty.getURI() );
				retv.setLabel( qname );
			}
			else {
				throw new IllegalArgumentException( "Cannot make a property node without label and without a property" );
			}
		}
		return retv;
	}


	protected void write_facet( OntModel model, DatasetNode node )
	{
		model.add( node.getResource(), this.facet, this.resMyself );
	}

	@Override
	public void update()
	throws IllegalArgumentException
	{
		//FIXME
	}
	
	public void update( TreeItem localRoot, OntModel dataSchema )
	throws IllegalArgumentException
	{
		List<OntProperty> propertiesList = dataSchema.listAllOntProperties().toList();
		ArrayList<OntProperty> notInsertedPropertiesList = new ArrayList<OntProperty>();
		for (OntProperty ontProperty : propertiesList) {
			OntProperty superProperty = ontProperty.getSuperProperty();
			if (superProperty == null) {
				TreeItem treeItem = new TreeItem(localRoot, SWT.NONE);
				String label = "?s " + ontProperty.toString() + " ?o";
				treeItem.setText(label);
				PropertyTreeNode property =
						new PropertyTreeNode( null, this, ontProperty );
				property.setOwner( this.myShell.annotators.getActiveResource() );
				property.setLabel(label);
				treeItem.setData(property);
			} else {	
					boolean inserted = insertChildInTree(localRoot, ontProperty, superProperty );
					if ( ! inserted ) {
						notInsertedPropertiesList.add(ontProperty);
					}
			}
		}
		while ( ! notInsertedPropertiesList.isEmpty()) {
			java.util.Collections.rotate( notInsertedPropertiesList, 1 );
			OntProperty ontProperty = notInsertedPropertiesList.get( 0 );
			OntProperty superProperty = ontProperty.getSuperProperty();
			// All properties with null super-properties have been consumed by the for loop above 
			assert superProperty != null;
			boolean inserted = insertChildInTree(localRoot, ontProperty, superProperty );
			if( inserted ) {
				notInsertedPropertiesList.remove( 0 );
			}
		}
	}
	
	private boolean insertChildInTree( TreeItem treeItem, OntProperty ontProperty, OntProperty superProperty )
	{
		boolean inserted = false;
		for(TreeItem child : treeItem.getItems()) {
			if (((PropertyTreeNode) child.getData()).getProperty().equals(superProperty)) {
				TreeItem newtreeItem = new TreeItem(child, SWT.NONE);
				String label = ontProperty.toString();
				newtreeItem.setText(label);
				PropertyTreeNode property = new PropertyTreeNode( null, this, ontProperty );
				property.setOwner( this.myShell.annotators.getActiveResource() );
				property.setLabel(label);
				newtreeItem.setData(property);
				inserted = true;
			}
			if ((!inserted) && child.getItemCount()>0) {//if not leaf check the children
				inserted = insertChildInTree( child, ontProperty, superProperty );
			}
		}
		return inserted;
	}
	

	

}
