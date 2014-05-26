/***************

<p>Title: Property Hierarchy Facet</p>

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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.facets.TreeFacet;
import gr.demokritos.iit.eleon.ui.InsertInMenuDialog;

public class PropertyTreeFacet implements TreeFacet
{
	private Tree treePerProperty;
	private MainShell myShell;
	private String title;
	private String info;

	
	/*
	 * CONSTRUCTOR
	 */

	
	public PropertyTreeFacet( MainShell shell )
	{
		this.myShell = shell;
	}
	

	/*
	 * Facet IMPLEMENTATION
	 */

	public String getTitle() { return this.title; }
	public String getInfo() { return this.info; }


	/*
	 * TreeFacet IMPLEMENTATION
	 */


	public Tree getTree() { return this.treePerProperty; }

	public void createPerPropertyTree()
	{
		treePerProperty = new Tree( this.myShell, SWT.BORDER );
		treePerProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//check to avoid strange bug when the listener is activated but no selection exists
				//causing java.lang.ArrayIndexOutOfBoundsException: 0
				if (treePerProperty.getSelection().length==0) {
					title = "";
					info = "";
					if ( myShell.table!=null && !myShell.table.isDisposed() ) {
						myShell.table.dispose();
						myShell.table = null;
					}
					return;
				}
				if (treePerProperty.getSelection()[0].getText().equals("root")) {
					title = "";
					info = "";
					if ( myShell.table!=null && !myShell.table.isDisposed() ) {
						myShell.table.dispose();
						myShell.table = null;
					}
					return;
				}
				DatasetNode treeNodeData = (DatasetNode) treePerProperty.getSelection()[0].getData();
				if (treeNodeData.getDc_title() != null) {
					title = treeNodeData.getDc_title();
				}
				else { title = ""; }
				if (treeNodeData.getVoid_sparqlEnpoint() != null) {
					info = treeNodeData.getVoid_sparqlEnpoint();
				}
				else { info = ""; }
				myShell.createTableContents(treePerProperty);
			}
		});
		treePerProperty.setBounds(318, 84, 369, 578);
		
		TreeItem root = new TreeItem(treePerProperty, SWT.NONE);
		root.setText("root");
		
		//insert menu for this tree
		final Menu treeMenu = new Menu(treePerProperty);
		treePerProperty.setMenu(treeMenu);
				
		final MenuItem insertNewDatasource = new MenuItem(treeMenu, SWT.NONE);
		insertNewDatasource.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] selected = treePerProperty.getSelection();
				if (selected.length > 0/* && selected[0].getText().equals("root")*/) {
					InsertInMenuDialog insert = new InsertInMenuDialog( myShell.getShell() );
					String label = insert.open("Insert dataset label");
					if (label == null) return;
					TreeItem newItem = new TreeItem(selected[0], SWT.NONE);
					DatasetNode data = new DatasetNode();
					data.setDc_creator( myShell.currentAuthor );
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
				TreeItem[] selected = treePerProperty.getSelection();
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
	

}
