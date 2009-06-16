//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.ListData;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.NodeVector;
import gr.demokritos.iit.eleon.struct.QueryHashtable;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.IOException;
import javax.swing.tree.*;
import javax.swing.JTree;


/* This is a dialog-box to insert a new upper-type */
public class XDialog extends JFrame
{
	private JDialog dialog;
	private KLabel textFieldLabel;
	private OC oc;
	private JTextField textField;
	private KButton okButton;
	private KButton cancelButton;

	private NodeVector rootVector;
	private Vector upperVector;


	public XDialog (Dialog dialogParent, String frameTitle, String textFieldTitle) 
	{
		dialog = new JDialog(dialogParent, frameTitle, true);
		super.setIconImage(Mpiro.obj.image_corner);
		textFieldLabel = new KLabel(textFieldTitle);
		textField = new JTextField(16);
		oc = new OC();

		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(BorderLayout.NORTH, textFieldLabel);
		contentPane.add(BorderLayout.CENTER, textField);
		contentPane.add(BorderLayout.SOUTH, oc);

		// Add the actionListener
		DListener dl = new DListener();
		okButton.addActionListener(dl);
		cancelButton.addActionListener(dl);

		// Make the dialog visible
		dialog.pack();
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = this.getSize();
		dialog.setLocation( (screenSize.width - size.width) / 3,
              (screenSize.height - size.height) / 3 );
		dialog.setVisible(true);

		// get current-upper-vector
		//rootVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity("Data Base");
		//upperVector = (Vector)rootVector.elementAt(1);
	}

	class OC extends JPanel
	{
		public OC() 
		{
			setLayout(new GridLayout(1,2));
			setPreferredSize(new Dimension(210,30));
			okButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
			cancelButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
			add(okButton);
			add(cancelButton);
		}
	}


	class DListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
	    if (e.getSource() == okButton) 
	    {
	      String newUpperType = textField.getText();
	
	      // get current-upper-vector
	      rootVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity("Data Base");
	      upperVector = (Vector)rootVector.elementAt(1);
	
	      //String newUpperType = textField.getText();
	      // check if the newName already exists!
	      boolean exists = false;
	      for (int y=0; y<upperVector.size(); y++) 
	      {
	        ListData data = (ListData)upperVector.elementAt(y);
	        String oneType = data.getName();
	        if (newUpperType.equalsIgnoreCase(oneType)) 
	        {
	        	exists = true;
	        }
	      }
        // if a name is given (=text) and
        String checkValid = Mpiro.win.struc.checkNameValidity(newUpperType);

        if (newUpperType.indexOf(" ") >= 0)
        {
        	new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog); // Empty space is not allowed!
        }
        else if (newUpperType.equalsIgnoreCase(""))
        {
        	new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog); // Have to specify a name!
        }
        //check the validity of the new name
        else if (!checkValid.equalsIgnoreCase("VALID"))
        {
        	new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                    + "\n" + checkValid);
        }
			  else if (exists)
			  {
			  	new MessageDialog(XDialog.this, MessageDialog.thisUpperTypeNameAlreadyExists_dialog);
			  }
			  else
			  {
				  upperVector.addElement(new ListData(newUpperType));
				  if (KDialog.dialog.getTitle().indexOf("basic") > 0)                             
					{
						dialog.dispose();
						KDialog.dialog.dispose();
						KDialog kBasic = new KDialog("New basic type",
                                         "Name of the basic type",
                                         "Links to upper model types",
                                         upperVector,
                                         true,
                                         "BASIC", true);
				  }
				  else
					{
            dialog.dispose();
            KDialog.dialog.dispose();
            KDialog kUpper = new KDialog("Upper model types",
                                         null,
                                         "Upper types",
                                         upperVector,
                                         true,
                                         "UPPER", true);
				  }
				  rootVector.setElementAt(upperVector, 1);
					//dialog.dispose();
			  }
			}
	    if (e.getSource() == cancelButton) 
	    {
			  dialog.dispose();
	    }
		}
	}
}