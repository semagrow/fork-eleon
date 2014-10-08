/***************

<p>Title: Select Vocabularies Dialog</p>

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

import gr.demokritos.iit.eleon.facets.dataset.DatasetNode;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class SelectVocabulariesDialog extends Dialog
{

	protected Shell shell;
	protected List list;
	final public DatasetNode node;
	java.util.List<String> selectedItems = new ArrayList<String>();
	
	/**
	 * 
	 */
	public SelectVocabulariesDialog( Shell parent, DatasetNode node, int style )
	{
		 super( parent, style );
		 this.node = node;
	}
	
	/**
	 * @param parent
	 */
	public SelectVocabulariesDialog( Shell parent, DatasetNode node )
	{
		// default style bits go here (not the Shell's style bits)
		this( parent, node, 0 );
	}
	
	public java.util.List<String> open(Menu vocabulariesMenu) {
			
		Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText("Select Nodes");
		shell.setSize(559, 592);
		shell.setActive();

		Label lblSelectOneFrom = new Label(shell, SWT.NONE);
		lblSelectOneFrom.setBounds(10, 10, 424, 15);
		lblSelectOneFrom.setText("Select one or more from the Data Schemas bellow.");

		list = new List(shell, SWT.BORDER | SWT.MULTI);
		list.setBounds(10, 31, 533, 496);
				
		for (MenuItem menuItem : vocabulariesMenu.getItems()) {
			if (menuItem.getSelection()) {//get all checked items
				list.add(menuItem.getText());
			}
		}


		Button btnOk = new Button(shell, SWT.NONE);
		btnOk.setBounds(192, 533, 81, 24);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionCount() == 0) {
					MessageBox box = new MessageBox(shell, SWT.OK | SWT.ICON_INFORMATION);
	                box.setText("Info");
	                box.setMessage("You have to select an item from the list.");
	                box.open();
	                selectedItems.clear();
				} else {
					for (String selectedItem : list.getSelection()) {
						selectedItems.add(selectedItem);
					}
					shell.dispose();
				}
			}
		});
		btnOk.setText("OK");

		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				selectedItems.clear();
			}
		});
		btnCancel.setBounds(279, 533, 81, 24);
		btnCancel.setText("Cancel");

		shell.setDefaultButton(btnOk);
		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return selectedItems;
	}


}
