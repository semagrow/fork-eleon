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


public class RobotsModelDialog extends JFrame implements ActionListener
{	
	static JDialog dialog;
	String frameTitle;
	private KLabel textFieldLabel;
	
	private KButton okButton;
	
	NodeVector currentVector;
	
	static String field;
	static String node;
	static String type;
	
	static String currentUsertype;

	/*
	 *
	 */
	public RobotsModelDialog(String fieldname, String nodename, String dialogType)
	{
		//String userTypes_text = LangResources.getString(Mpiro.selectedLocale, "usertypesSmall_text");	//maria
		//String importance_text = LangResources.getString(Mpiro.selectedLocale, "importance_text");		//maria
		//String repetitions_text = LangResources.getString(Mpiro.selectedLocale, "repetitions_text");	//maria
		String editing_text = LangResources.getString(Mpiro.selectedLocale, "editing_text");			//maria
		
		if (fieldname.equalsIgnoreCase("name (nominative)"))
		{
			field = "name";
		}
		else
		{              
			field = fieldname;
		}
		node = nodename;
		type = dialogType;
		frameTitle = new String(editing_text+ "(" + fieldname + ")");		//maria
		// Get the nodeVector of the selected node
		currentVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
		
		// The dialog and its components from top to bottom (1-6)
		dialog = new JDialog(this, frameTitle, true);
		super.setIconImage(Mpiro.obj.image_corner);
		dialog.setResizable(false);
		//dialog.setResizable(false);
		// 1
		textFieldLabel = new KLabel("  "+"Profiles"+"                                "+"Preference"+"   "+"");		//maria
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

		Vector allUserTypesVector = Mpiro.win.struc.getRobotsVectorFromUsersHashtable();
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


	public static Vector getParentValuesVector(String currentField, String usertype) 
	{
      /*      if(type.equalsIgnoreCase("Property")){
                Vector defaultVector = new Vector();
			defaultVector.addElement("0");
			defaultVector.addElement("1");
			defaultVector.addElement("2");
			return defaultVector;
            }
            else{
                
            }*/
		Hashtable allFieldsAndContainingEntityTypesHashtable = Mpiro.win.struc.returnAllFieldsAndContainingEntityTypes();
		
		// this is used for the "type" and "called" (name/shortname) fields
		// that are not present in entity-types. They are entity only
		if (!allFieldsAndContainingEntityTypesHashtable.containsKey(currentField))
		{
			Vector defaultVector = new Vector();
			defaultVector.addElement("3");
			defaultVector.addElement("3");
			defaultVector.addElement("1");
			return defaultVector;
		}
		String containingEntityType = Mpiro.win.struc.getParents(Mpiro.win.struc.nameWithoutOccur(DataBasePanel.last.toString())).elementAt(0).toString();
                if(!containingEntityType.equalsIgnoreCase("Basic-entity-types")){
		Vector parentValuesVector = Mpiro.win.struc.getRobotsModelValuesVector(currentField, containingEntityType, usertype);
		return parentValuesVector;
                }
                else{
                   return (Vector) (((Hashtable)((Vector)Mpiro.win.struc.getProperty(currentField)).elementAt(15)).get(usertype));
                }
	}


	class UserModelPanel extends JPanel implements ActionListener 
	{
		//InterestComboBox intCB;
		PreferenceComboBox impCB;
		//RepetitionsComboBox repCB;
		
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
			
			//intCB = new InterestComboBox();
			impCB = new PreferenceComboBox();
			//repCB = new RepetitionsComboBox();
			
			//intCB.addActionListener(this);
			impCB.addActionListener(this);
			//repCB.addActionListener(this);
Vector valuesVector=new Vector();
                        if(type.equalsIgnoreCase("Property")){
                            
             valuesVector=Mpiro.win.struc.getPropertyRobotsImportanceAndRepetitions(field,username);
                        }else //getPropertyRobotsImportanceAndRepetitions
			valuesVector = Mpiro.win.struc.getRobotsModelValuesVector(field, node, username);
			/*!!!*/  //intCB.setSelectedItem((String)valuesVector.elementAt(0));
			impCB.setSelectedItem((String)valuesVector.elementAt(0));
			//repCB.setSelectedItem((String)valuesVector.elementAt(2));
			
			this.add(user, c);
			c.gridx = 1;
			//this.add(intCB, c);
			//c.gridx = 2;
			//this.add(new KLabel("       "), c);
			//c.gridx = 3;
			this.add(impCB, c);
			//c.gridx = 2;
		//	this.add(new KLabel("       "), c);
			//c.gridx = 3;
			//this.add(repCB, c);
		}

		public void actionPerformed(ActionEvent e) 
		{
                    if(type.equalsIgnoreCase("Property")){
                        if (e.getSource() == impCB)
			{
				//Mpiro.win.struc.updateImportanceOrRepetitionsForProperty(field, username, 1, impCB.getSelectedItem().toString());
				Mpiro.win.struc.updateRobotsPreferenceForProperty(field, username, 0, impCB.getSelectedItem().toString());
				Mpiro.needExportToEmulator=true;			//maria
			}
			//else if (e.getSource() == repCB)
		//	{
		//		Mpiro.win.struc.updateImportanceOrRepetitionsForProperty(field, username, 2, repCB.getSelectedItem().toString());
		//		Mpiro.needExportToEmulator=true;			//maria
		//	}
                    }
		
                    else{
                    /* if (e.getSource() == intCB)
			{
			QueryProfileHashtable.updateUserModelParameters(field, node, username, 0, intCB.getSelectedItem().toString());
			}
			else*/ if (e.getSource() == impCB)
			{
			//	QueryProfileHashtable.updateUserModelParameters(field, node, username, 1, impCB.getSelectedItem().toString());
				Mpiro.win.struc.updateUserOrRobotModelParameters(field, node, username, 0, impCB.getSelectedItem().toString());
				//Mpiro.needExportToEmulator=true;			//maria
			}
			//else if (e.getSource() == repCB)
			//{
			//	QueryProfileHashtable.updateUserModelParameters(field, node, username, 2, repCB.getSelectedItem().toString());
			//	Mpiro.needExportToEmulator=true;			//maria
			//}
                    }
		} // actionPerformed
	}


	/* class InterestComboBox extends KComboBox 
		{
			public InterestComboBox() 
			{
				if (type.equalsIgnoreCase("ENTITY"))
				{
					this.addItem(new String(""));
				}
				for (int i=0; i<=3; i++)
				{
					Integer number = new Integer(i);
					this.addItem(number.toString());
				}
				
				if (type.equalsIgnoreCase("ENTITY"))
				{
					String parentValue = UserModelDialog.getParentValuesVector(UserModelDialog.field, UserModelDialog.currentUsertype).get(0).toString();
					ComboBoxRenderer cbr = new ComboBoxRenderer(parentValue);
					cbr.setPreferredSize(new Dimension(8, 17));
					this.setRenderer(cbr);
				}
			}
		}
	*/
	
	class PreferenceComboBox extends KComboBox 
	{
		public PreferenceComboBox() 
		{
			if (!type.equalsIgnoreCase("Property")&&!type.equalsIgnoreCase("CLASS"))
			{
				this.addItem("");
			}
			for (int i=0; i<=3; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
			
			if (!type.equalsIgnoreCase("Property"))
			{
				String parentValue = RobotsModelDialog.getParentValuesVector(RobotsModelDialog.field, RobotsModelDialog.currentUsertype).get(0).toString();
			//String parentValue="9";	
                            ComboBoxRenderer cbr = new ComboBoxRenderer(parentValue);
				cbr.setPreferredSize(new Dimension(8, 17));
				this.setRenderer(cbr);
			}
		}
	}

	/*class RepetitionsComboBox extends KComboBox 
	{
		public RepetitionsComboBox() 
		{
			if (!type.equalsIgnoreCase("Property"))
			{
				this.addItem("");
			}
			for (int i=0; i<=3; i++)
			{
				Integer number = new Integer(i);
				this.addItem(number.toString());
			}
			
			if (!type.equalsIgnoreCase("Property"))
			{
				String parentValue = RobotsModelDialog.getParentValuesVector(RobotsModelDialog.field, RobotsModelDialog.currentUsertype).get(2).toString();
				ComboBoxRenderer cbr = new ComboBoxRenderer(parentValue);
				cbr.setPreferredSize(new Dimension(8, 17));
				this.setRenderer(cbr);
			}
		}
	}*/


	class ComboBoxRenderer extends JLabel implements ListCellRenderer 
	{
		String currentParentValue = new String();
		
		public ComboBoxRenderer(String parentVal) 
		{
			setOpaque(true);
			currentParentValue = parentVal;
		}

		public Component getListCellRendererComponent(JList list,Object value,
																							   int index,boolean isSelected,
																							   boolean cellHasFocus)
		{
			setText(value.toString());
			String valueString = value.toString();
			
			if (valueString.equalsIgnoreCase(currentParentValue))
			{
				setForeground(Color.red);
			}
			else
			{
				setForeground(Color.black);
			}
			
			if (isSelected) 
			{
				setBackground(list.getSelectionBackground());
			} 
			else 
			{
				setBackground(list.getBackground());
			}
			
			//setBackground(isSelected ? Color.red : Color.white);
			//setForeground(isSelected ? Color.white : Color.black);
			return this;
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
