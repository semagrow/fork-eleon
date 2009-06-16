//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;
import gr.demokritos.iit.eleon.ui.KComboBox;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;


/**
 * <p>Title: IndependentLexiconPanel</p>
 * <p>Description: The panel for the language independent option for the nouns/verbs in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class IndependentLexiconPanel extends JPanel 
{
	//static Font panelfont= new Font(Mpiro.selectedFont,Font.BOLD,11);;
	JCheckBox advancedSpellingOptions;
	JPanel centerpanel;
	Hashtable valuesHashtable = new Hashtable();
	
	/**
	* The constructor
	*/
	public IndependentLexiconPanel() 
	{
		String languageIndependentInformation_text = LangResources.getString(Mpiro.selectedLocale, "languageIndependentInformation_text");
		String appropriateness_text = LangResources.getString(Mpiro.selectedLocale, "appropriateness_text");
		
		// the  label panel
		JLabel labelIN = new JLabel(" " + LexiconPanel.n + ": " + languageIndependentInformation_text);
		labelIN.setFont(new Font(Mpiro.selectedFont,Font.BOLD,16));
		labelIN.setForeground(Color.black);
		labelIN.setPreferredSize(new Dimension(380, 33));
		
		// the appropriateness panel
		KLabel appropriateness = new KLabel(appropriateness_text);

    JPanel appropriatenessPanel = new JPanel(new BorderLayout());
    appropriatenessPanel.add("West", appropriateness);

    JPanel userPanels = new JPanel(new GridBagLayout());
    userPanels.setBorder(new LineBorder(new Color(250,250,250), 1));
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.insets = new Insets(1,5,1,0);
    c.anchor = GridBagConstraints.WEST;
    c.weightx = 1.0; c.weighty = 1.0;
    c.gridy = 0;

    valuesHashtable = Mpiro.win.struc.showValues(LexiconPanel.n.toString(), "Independent");
    //System.out.println(valuesHashtable);

    Vector allUserTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
    Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
    while (allUserTypesVectorEnum.hasMoreElements())
    {
	    String user = allUserTypesVectorEnum.nextElement().toString();
	    //System.out.println("()---- " + user);
	    UserTypePanel panel = new UserTypePanel(user);
	    panel.setSize(new Dimension(220, 30));
	    userPanels.add(panel, c);
	    c.gridy++;
    }

		centerpanel = new JPanel(new ColumnLayout());
		centerpanel.add(appropriatenessPanel);
		centerpanel.add(userPanels);
		
		this.setLayout(new BorderLayout());
		this.add("North", labelIN);
		this.add("Center", centerpanel);
		
		//valuesHashtable = Mpiro.win.struc.showValues(LexiconPanel.n.toString(), "Independent");
		//System.out.println(QueryLexiconHashtable.currentValues);
		//System.out.println(valuesHashtable);
		centerpanel.revalidate();
		centerpanel.repaint();
	}	  // constructor


  class UserTypePanel extends JPanel implements ActionListener 
  {
		AppropriatenessComboBox acb;
		String username;
		public UserTypePanel(String usertype) 
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
			
			acb = new AppropriatenessComboBox();
			acb.addActionListener(this);
			String value = valuesHashtable.get(username).toString();
			acb.setSelectedItem(value);
			
			this.add(user, c);
			c.gridx = 1;
			this.add(acb, c);
		}

    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource() == acb)
      {
	      //System.out.println("()---- " + username + "===" + acb.getSelectedItem().toString());
	      if (LexiconPanel.parent.toString().equalsIgnoreCase("Nouns"))
	      {
	        Mpiro.win.struc.updateLexiconEntryNoun(LexiconPanel.n.toString(), "Independent", username, acb.getSelectedItem().toString());
	      	Mpiro.needExportToEmulator=true;		//maria
	      }
	      else if (LexiconPanel.parent.toString().equalsIgnoreCase("Verbs"))
	      {
	      	Mpiro.win.struc.updateLexiconEntryVerb(LexiconPanel.n.toString(), "Independent", username, acb.getSelectedItem().toString());
	      }
      }
		} // actionPerformed
  }


  class AppropriatenessComboBox extends KComboBox 
  {
	  public AppropriatenessComboBox() 
	  {
			for (int i=-5; i<=5; i++)
			{
		    Integer number = new Integer(i);
		    this.addItem(number.toString());
			}
			this.setSize(new Dimension(10, 10));
	  }
	}

} //class