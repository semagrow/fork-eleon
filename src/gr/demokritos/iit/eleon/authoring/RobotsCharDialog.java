//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KComboBox;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.IOException;
import javax.swing.border.*;


public class RobotsCharDialog extends JFrame implements ActionListener
{	
	static JDialog dialog;
	String frameTitle;
	private KLabel textFieldLabel;
	
	private KButton okButton;
	private Vector<Vector> combos;
	//NodeVector currentVector;
	
	static String field;
	static String node;
	static String type;
	
	static String currentUsertype;

	/*
	 *
	 */
	public RobotsCharDialog(String fieldname, String nodename, String dialogType)
	{
		//String userTypes_text = LangResources.getString(Mpiro.selectedLocale, "usertypesSmall_text");	//maria
		//String importance_text = LangResources.getString(Mpiro.selectedLocale, "importance_text");		//maria
		//String repetitions_text = LangResources.getString(Mpiro.selectedLocale, "repetitions_text");	//maria
		String editing_text = LangResources.getString(Mpiro.selectedLocale, "editing_text");			//maria
		combos=new Vector();
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
		//currentVector = (NodeVector)QueryHashtable.mainDBHashtable.get(DataBasePanel.last.toString());
		
		// The dialog and its components from top to bottom (1-6)
		dialog = new JDialog(this, frameTitle, true);
		super.setIconImage(Mpiro.obj.image_corner);
		dialog.setResizable(false);
                
		//dialog.setResizable(false);
                
		// 1
                Object[] r=QueryUsersHashtable.robotsHashtable.keySet().toArray();
                String robots="";
                for(int i=0;i<r.length;i++){
                    if(r[i].toString().length()>11){
                        robots=robots+" "+r[i].toString().substring(0,9)+"...";
                    }else{
                        String temp=" "+r[i].toString();
                   // robots=robots+"  "+r[i].toString();
                        
                        int whitespaces=new Double(((12-r[i].toString().length())*1.6)/2).intValue();
                    for(int k=0;k<whitespaces;k++)
                        temp=" "+temp+" ";
                    robots=robots+temp;
                    }
                    
                }
		textFieldLabel = new KLabel("  "+"Profiles"+"                                           "+robots);		//maria
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

		Vector<Vector> allUserTypesVector = QueryHashtable.robotCharVector;
		Enumeration<Vector> allUserTypesVectorEnum = allUserTypesVector.elements();
                int robottypes=0;
		while (allUserTypesVectorEnum.hasMoreElements())
		{
		  String user = allUserTypesVectorEnum.nextElement().elementAt(0).toString();
		  //System.out.println("()---- " + user);
		  UserModelPanel panel = new UserModelPanel(user);
                  robottypes++;
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
    int yNo=QueryHashtable.robotCharVector.size();
    int xNo=r.length;
    
    dialog.setSize(215+xNo*55,67+yNo*40);
    Dimension size = dialog.getSize();
    dialog.setLocation( (screenSize.width - size.width) / 2,
                        (screenSize.height - size.height) / 2 );
    dialog.setVisible(true);
	} // constructor


  public void actionPerformed(ActionEvent e) 
  {
    if (e.getSource() == okButton) 
    {
            try {
                OwlExport.exportUserCharacteristics("http://localhost/OwlTemp.owl#");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
		Hashtable allFieldsAndContainingEntityTypesHashtable = QueryHashtable.returnAllFieldsAndContainingEntityTypes();
		
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
		String containingEntityType = QueryHashtable.getParents(QueryHashtable.nameWithoutOccur(DataBasePanel.last.toString())).elementAt(0).toString();
                if(!containingEntityType.equalsIgnoreCase("Basic-entity-types")){
		Vector parentValuesVector = QueryUsersHashtable.getRobotsModelValuesVector(currentField, containingEntityType, usertype);
		return parentValuesVector;
                }
                else{
                   return (Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(15)).get(usertype));
                }
	}


	class UserModelPanel extends JPanel implements ActionListener 
	{
		//InterestComboBox intCB;
            
	//	PreferenceComboBox impCB;
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
                       // c.ipadx=15;
              // c.gridwidth=5;
			
			this.setSize(new Dimension(350, 10));
			JTextField user = new JTextField(username, 14);
			user.setSize(new Dimension(100, 10));
			user.setForeground(new Color(0,0,0));
			user.setEditable(false);
			user.setBorder(new EmptyBorder(new Insets(1,1,1,1)));
			
                        Set robots=QueryUsersHashtable.robotsHashtable.keySet();
                        
                      //  combos[rcombosobottype]=new PreferenceComboBox[robots.size()];
			//intCB = new InterestComboBox();
			//impCB = new PreferenceComboBox();
			//repCB = new RepetitionsComboBox();
                        Vector<PreferenceComboBox> v1=new Vector();
                        for(int i=0;i<robots.size();i++){
                           v1.add(new PreferenceComboBox());
                            v1.elementAt(i).addActionListener(this);
                        }
			combos.add(v1);
			//intCB.addActionListener(this);
			//impCB.addActionListener(this);
			//repCB.addActionListener(this);
Vector valuesVector=new Vector();
                        if(type.equalsIgnoreCase("Property")){
                            
             valuesVector=QueryHashtable.getPropertyRobotsImportanceAndRepetitions(field,username);
                        }else
                            valuesVector=QueryHashtable.getRobotsCharValues(field, node, username);
			//valuesVector = QueryUsersHashtable.getRobotsModelValuesVector(field, node, username);
			/*!!!*/  //intCB.setSelectedItem((String)valuesVector.elementAt(0));
			//impCB.setSelectedItem((String)valuesVector.elementAt(0));
        			//repCB.setSelectedItem((String)valuesVector.elementAt(2));
                                this.add(user, c);
                                c.gridx = 1;
                        for(int i=0;i<v1.size();i++){
                                    
    v1.elementAt(i).setSelectedItem((String)valuesVector.elementAt(i));
    if(valuesVector.elementAt(i).equals("")){
        v1.elementAt(i).setSelectedItem("3");
        v1.elementAt(i).setForeground(Color.red);
                        }
    else
        v1.elementAt(i).setForeground(Color.black);
        
                        
    
    

    
    
                                c.gridx++;
			
			
			//this.add(intCB, c);
			//c.gridx = 2;
			//this.add(new KLabel("       "), c);
			//c.gridx = 3;
                                this.add(v1.elementAt(i), c);
                                if(((String)valuesVector.elementAt(i)).equals("-1"))
			v1.elementAt(i).setEnabled(false);
                        }
			//c.gridx = 2;
		//	this.add(new KLabel("       "), c);
			//c.gridx = 3;
			//this.add(repCB, c);
		}

		public void actionPerformed(ActionEvent e) 
		{
                    for(int j=0;j<combos.size();j++){
                        Vector<PreferenceComboBox> v1=combos.elementAt(j);
                    for(int i=0;i<v1.size();i++){
                        if (e.getSource() == v1.elementAt(i))
			{
                           System.out.println(v1.elementAt(i).getForeground().toString());
                            if(((v1.elementAt(i).getForeground().equals(Color.red))&&(!QueryHashtable.getValueFromRobotCharValuesHash(node, username,i).equals(v1.elementAt(i).getSelectedItem().toString())))||(v1.elementAt(i).getForeground().equals(Color.black))){
                            QueryHashtable.setValueAtRobotCharValuesHash(node, username,i,v1.elementAt(i).getSelectedItem().toString());
                            v1.elementAt(i).setForeground(Color.black);
                             boolean universal=false;
        for(int k=0;k<QueryHashtable.robotCharVector.size();k++){
            if(((Vector)QueryHashtable.robotCharVector.elementAt(k)).elementAt(0).toString().equalsIgnoreCase(username)){
                universal=(Boolean)((Vector)QueryHashtable.robotCharVector.elementAt(k)).elementAt(2);
                break;
            }
        }
        if(universal){
            for(int h=0;h<v1.size();h++){
                v1.elementAt(h).setSelectedItem( v1.elementAt(i).getSelectedItem());
                 v1.elementAt(h).setForeground(Color.black);
            }
        }
                            }}
                    
                        
                        
                    }}
                 //   }
               //     if(type.equalsIgnoreCase("Property")){
                 //       if (e.getSource() == impCB)
		///	{
				//QueryHashtable.updateImportanceOrRepetitionsForProperty(field, username, 1, impCB.getSelectedItem().toString());
		//		QueryHashtable.updateRobotsCharPreferenceForProperty(field, username, 0, impCB.getSelectedItem().toString());
				//Mpiro.needExportToEmulator=true;			//maria
		//	}
			//else if (e.getSource() == repCB)
		//	{
		//		QueryHashtable.updateImportanceOrRepetitionsForProperty(field, username, 2, repCB.getSelectedItem().toString());
		//		Mpiro.needExportToEmulator=true;			//maria
		//	}
                  //  }
		
                    //else{
                    /* if (e.getSource() == intCB)
			{
			QueryUsersHashtable.updateUserModelParameters(field, node, username, 0, intCB.getSelectedItem().toString());
			}
			else*/ //if (e.getSource() == impCB)
		//	{
			//	QueryUsersHashtable.updateUserModelParameters(field, node, username, 1, impCB.getSelectedItem().toString());
		///		QueryUsersHashtable.updateRobotsCharModelParameters(field, node, username, 0, impCB.getSelectedItem().toString());
				//Mpiro.needExportToEmulator=true;			//maria
		//	}
			//else if (e.getSource() == repCB)
			//{
			//	QueryUsersHashtable.updateUserModelParameters(field, node, username, 2, repCB.getSelectedItem().toString());
			//	Mpiro.needExportToEmulator=true;			//maria
			//}
                   // }
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
				String parentValue = RobotsCharDialog.getParentValuesVector(RobotsCharDialog.field, RobotsCharDialog.currentUsertype).get(0).toString();
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
				//setForeground(Color.red);
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

} // class UserModelDialog