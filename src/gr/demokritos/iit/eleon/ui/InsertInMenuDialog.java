package gr.demokritos.iit.eleon.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author gmouchakis
 *
 */
public class InsertInMenuDialog extends Dialog {
	String result;
    private Text text;
    Shell shell;
            
    public InsertInMenuDialog (Shell parent, int style) {
            super (parent, style);
    }
    public InsertInMenuDialog (Shell parent) {
            this (parent, 0); //default style bits go here (not the Shell's style bits)
    }
    
    public String open (String label) {
            Shell parent = getParent();
            shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
            shell.setSize(444, 115);
            shell.setText(getText());
            shell.setActive();
            
            Label lblInsert = new Label(shell, SWT.NONE);
            lblInsert.setBounds(10, 10, 419, 15);
            lblInsert.setText(label);
            
            text = new Text(shell, SWT.BORDER);
            text.setBounds(10, 31, 419, 21);
            
            Button btnInsert = new Button(shell, SWT.PUSH);
            btnInsert.addSelectionListener(new SelectionAdapter() {
            	@Override
            	public void widgetSelected(SelectionEvent e) {
            		result = text.getText();
            		shell.dispose();
            	}
            });
            btnInsert.setBounds(135, 58, 81, 24);
            btnInsert.setText("&Insert");
            
            Button btnCancel = new Button(shell, SWT.PUSH);
            btnCancel.addSelectionListener(new SelectionAdapter() {
            	@Override
            	public void widgetSelected(SelectionEvent e) {
            		result = null;
            		shell.dispose();
            	}
            });
            btnCancel.setBounds(222, 58, 81, 24);
            btnCancel.setText("&Cancel");
            
            shell.open();
            Display display = parent.getDisplay();
            while (!shell.isDisposed()) {
                    if (!display.readAndDispatch()) display.sleep();
            }
            return result;
    }
}