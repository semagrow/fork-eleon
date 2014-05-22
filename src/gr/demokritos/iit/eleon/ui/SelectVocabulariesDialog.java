/**
 * 
 */
package gr.demokritos.iit.eleon.ui;

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

/**
 * @author gmouchakis
 *
 */
public class SelectVocabulariesDialog extends Dialog {

	protected Shell shell;
	protected List list;
	java.util.List<String> selectedItems = new ArrayList<String>();
	
	/**
	 * 
	 */
	public SelectVocabulariesDialog(Shell parent, int style) {
		 super (parent, style);
	}
	
	/**
	 * @param parent
	 */
	public SelectVocabulariesDialog(Shell parent) {
		 this (parent, 0);//default style bits go here (not the Shell's style bits)
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

		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return selectedItems;
	}


}
