/***************

<p>Title: Copy Existing Node Dialog</p>

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

@author Giannis Mouchakis (SemaGrow 2014)

***************/

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.functionality.Functions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author gmouchakis
 *
 */
public class CopyExistingNodeDialog extends Dialog {

	protected Shell shell;
	
	/**
	 * 
	 */
	public CopyExistingNodeDialog(Shell parent, int style) {
		 super (parent, style);
	}
	
	/**
	 * @param parent
	 */
	public CopyExistingNodeDialog(Shell parent) {
		 this (parent, 0);//default style bits go here (not the Shell's style bits)
	}
	
	public void copy(Tree originalTree, final TreeItem selectedItem) {
		
		Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText("Select Nodes");
		shell.setSize(559, 742);
		shell.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Select node to copy");
		
		final Tree treeCopy = new Tree(shell, SWT.BORDER);
		treeCopy.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TreeItem trtmRoot = new TreeItem(treeCopy, SWT.NONE);
		trtmRoot.setText("root");
		
		Functions.copyTree(originalTree.getItems()[0], trtmRoot);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(6, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
				
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (treeCopy.getSelectionCount() == 0) {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("You have to select an item from the tree.");
	                box.open();
				} else if (treeCopy.getSelection()[0].getText().equals("root")) {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("Cannot select root node to copy.");
	                box.open();
				}
				//if the selected item is root then only a data source node can be copied as a child.
				else if (Functions.notDatasourceUnderRoot(selectedItem, treeCopy)) {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("You can only copy a non Data Source node under the root node.\n Please choose another node.");
	                box.open();
				} else {
					TreeItem item = new TreeItem(selectedItem, SWT.NONE);
					item.setText(treeCopy.getSelection()[0].getText());
					item.setData(treeCopy.getSelection()[0].getData());
					Functions.copyTree(treeCopy.getSelection()[0], item);
					shell.dispose();
				}
			}
		});
		btnOk.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		//btnOk.setBounds(0, 0, 81, 24);
		btnOk.setText("    OK    ");
		
		Button btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				//selectedItem = null;
			}
		});
		btnCancel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setActive();

		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	
}
