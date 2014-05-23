/***************

<p>Title: Insert in Menu Dialog</p>

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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

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