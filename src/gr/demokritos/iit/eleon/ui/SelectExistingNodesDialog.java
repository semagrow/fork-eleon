/**
 * 
 */
package gr.demokritos.iit.eleon.ui;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author gmouchakis
 *
 */
public class SelectExistingNodesDialog extends Dialog {

	protected Shell shell;
	private java.util.List<String> listNames;
	private String selectedItem = null;
	protected List list;
	
	/**
	 * 
	 */
	public SelectExistingNodesDialog(Shell parent, int style) {
		 super (parent, style);
	}
	
	/**
	 * @param parent
	 */
	public SelectExistingNodesDialog(Shell parent) {
		 this (parent, 0);//default style bits go here (not the Shell's style bits)
	}
	
public String open(TreeItem rootItem) {
			
		Shell parent = getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText("Select Nodes");
		shell.setSize(559, 592);
		shell.setActive();

		listNames = new ArrayList<String>();
		
		selectedItem = null;

		Label lblSelectOneFrom = new Label(shell, SWT.NONE);
		lblSelectOneFrom.setBounds(10, 10, 424, 15);
		lblSelectOneFrom.setText("Select one from the existing node bellow.");

		list = new List(shell, SWT.BORDER);
		list.setBounds(10, 31, 533, 496);
		getAllNamesList(rootItem);
		for (String name : listNames) {
			list.add(name);
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
	                selectedItem = null;
				} else {
					selectedItem = list.getSelection()[0];//single selection list.
					System.out.println(selectedItem);
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
				selectedItem = null;
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
		return selectedItem;
	}

	
	protected void getAllNamesList(TreeItem treeItem) {
		for(TreeItem child : treeItem.getItems()) {
			String name = child.getText();
			if ( ! listNames.contains(name)) {
				listNames.add(name);
			}
			if (child.getItemCount()>0) {//if not leaf check the children
				getAllNamesList(child);
			}
		}
	}

}
