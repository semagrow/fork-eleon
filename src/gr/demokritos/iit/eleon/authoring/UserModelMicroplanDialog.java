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

import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KComboBox;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.IOException;
import javax.swing.tree.*;
import javax.swing.JTree;
import javax.swing.border.*;


public class UserModelMicroplanDialog extends JFrame implements ActionListener
{
	static JDialog dialog;
	String frameTitle;
	private KLabel textFieldLabel;
	
	private KButton okButton;
	
	NodeVector currentVector;
	
	static String field;
	static String node;
	static String number;
	
	static String currentUsertype;
	
	private String appropriateness_action = LangResources.getString(Mpiro.selectedLocale, "appropriateness_action");
	
	
	public UserModelMicroplanDialog(String fieldname, String nodename, String microplanNumber)
	{
		String userTypes_text = LangResources.getString(Mpiro.selectedLocale, "usertypesSmall_text");	//maria
		String english_text = LangResources.getString(Mpiro.selectedLocale, "engl_text");	//maria
		String italian_text = LangResources.getString(Mpiro.selectedLocale, "ital_text");	//maria
		String greek_text = LangResources.getString(Mpiro.selectedLocale, "gre_text");	//maria
		
		field = fieldname;
		node = nodename;
		number = microplanNumber;
		frameTitle = new String(appropriateness_action + " (" + field + ")");
		// Get the nodeVector of the selected node
		currentVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
		
		// The dialog and its components from top to bottom (1-6)
		dialog = new JDialog(this, frameTitle, true);
		super.setIconImage(Mpiro.obj.image_corner);
		dialog.setResizable(false);
		//dialog.setResizable(false);
		// 1
		textFieldLabel = new KLabel("  "+userTypes_text+"                            "+english_text+	//maria
																"        "+italian_text+"           "+greek_text);						//maria
		textFieldLabel.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,11));
		
		// 2
		JPanel userPanels = new JPanel(new GridBagLayout());
		//userPanels.setPreferredSize(new Dimension(300, 100));
		userPanels.setBorder(new LineBorder(new Color(250,250,250), 1));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(3,5,3,3);
		c.anchor = GridBagConstraints.NORTH;
		c.weightx = 1.0; c.weighty = 0.0;
		c.gridy = 0;

    Vector allUserTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
    Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
    while (allUserTypesVectorEnum.hasMoreElements())
    {
			String user = allUserTypesVectorEnum.nextElement().toString();
			//System.out.println("()---- " + user);
			UserModelPanel panel = new UserModelPanel(user);
			userPanels.add(panel, c);
			c.gridy++;
    }

		// 3
		okButton = new KButton("OK");
		okButton.setPreferredSize(new Dimension(100, 30));;
		JPanel okButtonPanel = new JPanel(new BorderLayout());
		okButtonPanel.setPreferredSize(new Dimension(100, 40));
		okButtonPanel.add(BorderLayout.SOUTH, okButton);
		
		// Place them in the contentPane of the dialog
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(BorderLayout.NORTH, textFieldLabel);
		contentPane.add(BorderLayout.CENTER, userPanels);
		contentPane.add(BorderLayout.SOUTH, okButtonPanel);
		
		// Add the actionListener
		okButton.addActionListener(this);
		
		// Make the dialog visible
		dialog.pack();
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = dialog.getSize();
		dialog.setLocation( (screenSize.width - size.width) / 2,
		                    (screenSize.height - size.height) / 2 );
		dialog.setVisible(true);
	} // constructor


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == okButton) 
		{
			dialog.dispose();
		}
	}


	class UserModelPanel extends JPanel implements ActionListener 
	{
		EnglishComboBox enCB;
		ItalianComboBox itCB;
		GreekComboBox grCB;
		
		String username;
		
		public UserModelPanel(String usertype) 
		{
			username = usertype;
			currentUsertype = new String(usertype);
			this.setLayout(new GridBagLayout());
			
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(1,0,1,0);
			c.anchor = GridBagConstraints.WEST;
			c.weightx = 1.0; c.weighty = 1.0;
			
			this.setSize(new Dimension(250, 10));
			JTextField user = new JTextField(username, 14);
			user.setSize(new Dimension(100, 10));
			user.setForeground(new Color(0,0,0));
			user.setEditable(false);
			user.setBorder(new EmptyBorder(new Insets(1,1,1,1)));
			
			enCB = new EnglishComboBox();
			itCB = new ItalianComboBox();
			grCB = new GreekComboBox();
			
			enCB.addActionListener(this);
			itCB.addActionListener(this);
			grCB.addActionListener(this);

			Vector valuesVector = Mpiro.win.struc.getAppropriatenessValuesVector(field, number, username);
			enCB.setSelectedItem((String)valuesVector.elementAt(0));
			itCB.setSelectedItem((String)valuesVector.elementAt(1));
			grCB.setSelectedItem((String)valuesVector.elementAt(2));
			
			this.add(user, c);
			c.gridx = 1;
			this.add(enCB, c);
			c.gridx = 2;
			this.add(new KLabel("       "), c);
			c.gridx = 3;
			this.add(itCB, c);
			c.gridx = 4;
			this.add(new KLabel("       "), c);
			c.gridx = 5;
			this.add(grCB, c);
		}


		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == enCB)
			{
				Mpiro.win.struc.updateHashtable(node, number, field, username, "English", enCB.getSelectedItem().toString());
				Mpiro.needExportToEmulator=true;			//maria
			}
			else if (e.getSource() == itCB)
			{
				Mpiro.win.struc.updateHashtable(node, number, field, username, "Italian", itCB.getSelectedItem().toString());
				Mpiro.needExportToEmulator=true;			//maria
			}
			else if (e.getSource() == grCB)
			{
				Mpiro.win.struc.updateHashtable(node, number, field, username, "Greek", grCB.getSelectedItem().toString());
				Mpiro.needExportToEmulator=true;			//maria
			}
		} // actionPerformed

	}//class UserModelPanel


	class EnglishComboBox extends KComboBox 
	{	
		public EnglishComboBox() 
		{
			for (int i=-5; i<=5; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
		}
	}

  class ItalianComboBox extends KComboBox 
  {
		public ItalianComboBox() 
		{
			for (int i=-5; i<=5; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
		}
  }

	class GreekComboBox extends KComboBox 
	{
		public GreekComboBox() 
		{
			for (int i=-5; i<=5; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
		}
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
