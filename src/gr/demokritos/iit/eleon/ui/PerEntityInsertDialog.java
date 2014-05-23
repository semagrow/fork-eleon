/***************

<p>Title: PerEntityInsertDialog</p>

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

import gr.demokritos.iit.eleon.functionality.PerEntityNode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class PerEntityInsertDialog  extends Dialog {

	Shell shell;
    private Text textSubject;
    private Text textObject;
    private PerEntityNode perEntityNode;
            
    public PerEntityInsertDialog (Shell parent, int style) {
            super (parent, style);
    }
    public PerEntityInsertDialog (Shell parent) {
            this (parent, 0); // your default style bits go here (not the Shell's style bits)
    }
    public PerEntityNode open () {
            Shell parent = getParent();
            shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
            shell.setSize(496, 135);
            shell.setText(getText());
            shell.setActive();
            
            Label lblSubjectPattern = new Label(shell, SWT.NONE);
            lblSubjectPattern.setBounds(10, 31, 90, 15);
            lblSubjectPattern.setText("Subject pattern");
            
            textSubject = new Text(shell, SWT.BORDER);
            textSubject.setBounds(106, 31, 375, 21);
            
            Button btnInsert = new Button(shell, SWT.PUSH);
            btnInsert.addSelectionListener(new SelectionAdapter() {
            	@Override
            	public void widgetSelected(SelectionEvent e) {
            		String subjectPattern = textSubject.getText();
            		String objectPattern = textObject.getText();
            		//it seems that when the text field is left empty its value is String "". we prefer it null.
            		if (subjectPattern.equals("")) {
            			subjectPattern = null;
            		}
            		if (objectPattern.equals("")) {
            			objectPattern = null;
            		}
            		if (subjectPattern == null && objectPattern == null) {
            			perEntityNode = null;
            		} else {
            			perEntityNode = new PerEntityNode(subjectPattern, objectPattern);
            		}
            		shell.dispose();
            	}
            });
            btnInsert.setBounds(155, 79, 81, 24);
            btnInsert.setText("&Insert");
            
            Button btnCancel = new Button(shell, SWT.PUSH);
            btnCancel.addSelectionListener(new SelectionAdapter() {
            	@Override
            	public void widgetSelected(SelectionEvent e) {
            		perEntityNode = null;
            		shell.dispose();
            	}
            });
            btnCancel.setBounds(242, 79, 81, 24);
            btnCancel.setText("&Cancel");
            
            Label lblInsertRegexPattern = new Label(shell, SWT.NONE);
            lblInsertRegexPattern.setBounds(10, 10, 471, 15);
            lblInsertRegexPattern.setText("Insert regex pattern for the subject and for the object. One of them can be blank.");
            
            Label lblObjectPattern = new Label(shell, SWT.NONE);
            lblObjectPattern.setText("Object pattern");
            lblObjectPattern.setBounds(10, 52, 90, 15);
            
            textObject = new Text(shell, SWT.BORDER);
            textObject.setBounds(106, 52, 375, 21);
            
            shell.open();
            Display display = parent.getDisplay();
            while (!shell.isDisposed()) {
                    if (!display.readAndDispatch()) display.sleep();
            }
            return perEntityNode;
    }
}
