//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

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

} // class OptionsEditDialog