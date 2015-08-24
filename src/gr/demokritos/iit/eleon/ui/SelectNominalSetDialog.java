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


package gr.demokritos.iit.eleon.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.hp.hpl.jena.rdf.model.Resource;

public class SelectNominalSetDialog extends Dialog {

	protected Shell shell;
	private List<Resource> selectedNominals = new ArrayList<Resource>();;
	
	/**
	 * 
	 */
	public SelectNominalSetDialog(Shell parent, int style) {
		 super (parent, style);
	}
	
	/**
	 * @param parent
	 */
	public SelectNominalSetDialog(Shell parent) {
		 this (parent, 0);//default style bits go here (not the Shell's style bits)
	}
	
	public List<Resource> open(final List<Resource> availableNominals, final List<Resource> currentNominals) {
		
		Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText("Select classes");
		shell.setSize(559, 742);
		shell.setLayout(new GridLayout(1, false));
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblNewLabel.setText("Select one or more from the classes bellow.");
		
		final Table table = new Table(shell, SWT.CHECK | SWT.BORDER);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		for (Resource nominal : availableNominals) {
			TableItem item = new TableItem(table, SWT.NONE);
		    item.setText(nominal.toString());
		    if (currentNominals.contains(nominal)) {
		    	item.setChecked(true);
		    } else {
		    	item.setChecked(false);
		    }
		}
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new GridLayout(6, true));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		selectedNominals.addAll(currentNominals);
				
		Button btnOk = new Button(composite, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedNominals.clear();
				int i = 0;
				for (TableItem tableItem : table.getItems()) {
					if (tableItem.getChecked()) {
						selectedNominals.add(availableNominals.get(i));
					}
					i++;
				}
				shell.dispose();
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
			}
		});
		btnCancel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnCancel.setText("Cancel");
		
		shell.setDefaultButton(btnOk);
		shell.setActive();

		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return selectedNominals;
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
