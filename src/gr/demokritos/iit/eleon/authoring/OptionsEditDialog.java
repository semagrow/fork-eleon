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


package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.struct.QueryOptionsHashtable;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KLabel;
import gr.demokritos.iit.eleon.ui.MessageDialog;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.IOException;
import javax.swing.tree.*;
import javax.swing.JTree;
import javax.swing.border.*;


public class OptionsEditDialog extends JFrame implements ActionListener
{
	static JDialog dialog;
	static KLabel selectionLabel;
	static JPanel selectionPanel;
	private OkCancel okCancel;
	
	private JTextField pserverIP;
	private JTextField pserverPort;
	private KButton okButton;
	private KButton cancelButton;
	static String domainName;


	public OptionsEditDialog(String dialogType)
	{
	  if (dialogType.equalsIgnoreCase("PSERVER-ADDRESS"))
	  {
	    dialog = new JDialog(this, LangResources.getString(Mpiro.selectedLocale, "editPersonalisationServerAddress_text"), true);
	    super.setIconImage(Mpiro.obj.image_corner);
	
	    Vector oldPServerAddressVector = Mpiro.win.struc.getPServerAddressFromMainOptionsHashtable();
	
	    selectionLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "pleaseGiveANewIPAddressAndPort_text"));
	    selectionLabel.setPreferredSize(new Dimension(250, 20));
	
	    selectionPanel = new JPanel(new GridBagLayout());
	    selectionPanel.setPreferredSize(new Dimension(160,40));

      JLabel labelone = new JLabel(" http:// ");
      JLabel labeltwo = new JLabel(" : ");
      pserverIP = new JTextField(oldPServerAddressVector.elementAt(0).toString());
      pserverIP.setPreferredSize(new Dimension(100, 20));
      pserverPort = new JTextField(oldPServerAddressVector.elementAt(1).toString());

      okCancel = new OkCancel();

      selectionPanel.add(labelone);
      selectionPanel.add(pserverIP);
      selectionPanel.add(labeltwo);
      selectionPanel.add(pserverPort);

      // Place them in the contentPane of the dialog
      Container contentPane = dialog.getContentPane();
      contentPane.setLayout(new BorderLayout());
      contentPane.add("North", selectionLabel);
      contentPane.add("Center", selectionPanel);
      contentPane.add("South", okCancel);

      // Add the actionListener
      okButton.addActionListener(OptionsEditDialog.this);
      cancelButton.addActionListener(OptionsEditDialog.this);
		}
    // The dialog and its components

    // Make the dialog visible
    dialog.pack();
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension size = dialog.getSize();
    dialog.setLocation( (screenSize.width - size.width) / 2,
                        (screenSize.height - size.height) / 2 );
    dialog.setVisible(true);
	} // constructor



  class OkCancel extends JPanel
  {
	  public OkCancel() 
	  {
	    setLayout(new GridLayout(1,2));
	    setPreferredSize(new Dimension(160,30));
	    okButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
	    cancelButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
	    add(okButton);
	    add(cancelButton);
	  }
  }

	public void actionPerformed(ActionEvent e)
	{
	  if (e.getSource() == okButton)
	  {
	    Mpiro.win.struc.addPServerAddressToMainOptionsHashtable(pserverIP.getText(), pserverPort.getText());
	    dialog.dispose();
	    new MessageDialog(dialog, MessageDialog.theNewPersonalisationServerAddressWillBeRecorded_ETC_dialog);
	  }
	
	  if (e.getSource() == cancelButton)
	  {
	  	dialog.dispose();
	  }
	} // actionPerformed

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
