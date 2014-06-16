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

/**
 * @author gmouchakis
 *
 */
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
