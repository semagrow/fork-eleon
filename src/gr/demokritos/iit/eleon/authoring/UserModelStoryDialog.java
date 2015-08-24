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


public class UserModelStoryDialog extends JFrame implements ActionListener
{
	static JDialog dialog;
	String frameTitle;
	private KLabel textFieldLabel;
	
	private KButton okButton;
	
	Hashtable valuesHashtable;
	
	static String story;
	static String node;
	static String type;


	/*
	 *
	 */

	public UserModelStoryDialog(String storyname, String nodename)
	{
		story = storyname;
		node = nodename;
		frameTitle = new String("Editing (" + storyname + ")");
		// Get the nodeVector of the selected node
		valuesHashtable = new Hashtable();
		
		// The dialog and its components from top to bottom (1-6)
		dialog = new JDialog(this, frameTitle, true);
		super.setIconImage(Mpiro.obj.image_corner);
		dialog.setResizable(false);
		//dialog.setResizable(false);
		// 1
		textFieldLabel = new KLabel("  User-types                                  interest    imp�rtance  repetitions");
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

		//valuesHashtable = Mpiro.win.struc.showValues(LexiconPanel.n.toString(), "Independent");
		//System.out.println(valuesHashtable);
		
		Vector allUserTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
		while (allUserTypesVectorEnum.hasMoreElements())
		{
			String user = allUserTypesVectorEnum.nextElement().toString();
			//System.out.println("()---- " + user);
			UserModelStoryPanel panel = new UserModelStoryPanel(user);
			userPanels.add(panel, c);
			c.gridy++;
		}

		// 3
		okButton = new KButton("OK");
		okButton.setPreferredSize(new Dimension(100, 30));;
		JPanel okButtonPanel = new JPanel(new BorderLayout());
		okButtonPanel.setPreferredSize(new Dimension(100, 40));
		okButtonPanel.add("South", okButton);
		
		// Place them in the contentPane of the dialog
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add("North", textFieldLabel);
		contentPane.add("Center", userPanels);
		contentPane.add("South", okButtonPanel);
		
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


	class UserModelStoryPanel extends JPanel implements ActionListener 
	{
		InterestComboBox intCB;
		ImportanceComboBox impCB;
		RepetitionsComboBox repCB;
		String username;
		
		public UserModelStoryPanel(String usertype) 
		{
			username = usertype;
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
			
			intCB = new InterestComboBox();
			impCB = new ImportanceComboBox();
			repCB = new RepetitionsComboBox();
			
			intCB.addActionListener(this);
			impCB.addActionListener(this);
			repCB.addActionListener(this);

//			Vector valuesVector = QueryProfileHashtable.getUserModelStoryValuesVector(story, node, username);
//			intCB.setSelectedItem((String)valuesVector.elementAt(0));
//			impCB.setSelectedItem((String)valuesVector.elementAt(1));
//			repCB.setSelectedItem((String)valuesVector.elementAt(2));
			
			this.add(user, c);
			c.gridx = 1;
			this.add(intCB, c);
			c.gridx = 2;
			this.add(new KLabel("       "), c);
			c.gridx = 3;
			this.add(impCB, c);
			c.gridx = 4;
			this.add(new KLabel("       "), c);
			c.gridx = 5;
			this.add(repCB, c);
		}

		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == intCB)
			{
			//	QueryProfileHashtable.updateUserModelStoryParameters(story, node, username, 0, intCB.getSelectedItem().toString());
			}
			else if (e.getSource() == impCB)
			{
				//QueryProfileHashtable.updateUserModelStoryParameters(story, node, username, 1, impCB.getSelectedItem().toString());
			}
			else if (e.getSource() == repCB)
			{
				//QueryProfileHashtable.updateUserModelStoryParameters(story, node, username, 2, repCB.getSelectedItem().toString());
			}
		} // actionPerformed

	}//class UserModelStoryPanel


	class InterestComboBox extends KComboBox 
	{
		public InterestComboBox() 
		{
			for (int i=0; i<=3; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
			this.setSize(new Dimension(10, 10));
		}
	}


	class ImportanceComboBox extends KComboBox 
	{
		public ImportanceComboBox() 
		{
			for (int i=0; i<=3; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
			this.setSize(new Dimension(10, 10));
		}
	}


	class RepetitionsComboBox extends KComboBox 
	{
		public RepetitionsComboBox() 
		{
			for (int i=0; i<=3; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
			this.setSize(new Dimension(10, 10));
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
