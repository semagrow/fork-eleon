//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KDialog;
import gr.demokritos.iit.eleon.ui.KLabel;
import gr.demokritos.iit.eleon.ui.MessageDialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.JTree;
import java.util.*;

import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;
import java.io.IOException;


/**
 * <p>Title: DataBaseNounPanel</p>
 * <p>Description: This class creates a panel showing the nouns chosen to describe the selected entity-type on the DataBaseTree</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
//Edited and enhanced by Theofilos Nickolaou 
public class DataBaseNounPanel extends JPanel 
{
	static KLabel nounLabel;
	public static NounSelected nounSelected;
	Vector nounVector;
	static Vector selectedNounVector;
	
	/**
	 * A small panel showing new and inherited (with "*") nouns
	 * that can be used to describe the entity type
	 */
  public DataBaseNounPanel() 
  {

	  setLayout(new GridBagLayout());
	  setBorder(new EmptyBorder(new Insets(2,5,2,5)));
	  GridBagConstraints c = new GridBagConstraints();
	  // The label
	  nounLabel = new KLabel("Nouns that can be used to describe this entity type");
	  // The textArea
	  nounSelected = new NounSelected(new Vector());
	  //JScrollPane nounScroll = new JScrollPane(nounSelected);

	  // The button "edit"
	  KButton edit = new KButton(LangResources.getString(Mpiro.selectedLocale, "editNouns_button"));
	  edit.setPreferredSize(new Dimension(75, 20));
	  if (DataBasePanel.im == DataBasePanel.ICON_TOP_A) 
	  {
			edit.setEnabled(false);
	  }

		edit.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e) 
			{
				getNounsFromLexTree();
		    //System.out.println(nounVector);
		    if (nounVector.isEmpty()) 
		    {
		      new MessageDialog(DataBaseTable.dbTable, MessageDialog.thereAreNoNounsInTheLexicon_dialog);
		    }
		    else
		    {
		      // The selected-noun-vector: the nouns found at the NounSelected area.
		      selectedNounVector = new Vector(TreePreviews.dbnp.nounSelected.getNouns());
		      // Put the noun-vector in the dialog's constructor (so as to fill the CheckBoxList)...
		      KDialog nounDialog = new KDialog("Nouns info", null, "Lexicon nouns",	nounVector, false, "NOUN", true);
		      // ... and use the selectedNounVector to recheck the nouns that are already
		      // in the nounSelected area.
		      nounDialog.chboli.setChecked(selectedNounVector);
		    }
			}
		});

	  // Putting them together in the nounPanel
	  c.insets = new Insets(0,0,1,10);
	  c.anchor = GridBagConstraints.WEST;
	  c.fill = GridBagConstraints.HORIZONTAL;
	  c.weightx = 1.0; c.weighty = 0.0;
	  c.gridwidth = 2; c.gridheight = 1;
	  add(nounLabel, c);
	  c.anchor = GridBagConstraints.NORTH;
	  c.gridwidth = 1; c.gridheight = 2;
	  c.gridy = 1;
	  add(nounSelected, c);
	  c.anchor = GridBagConstraints.WEST; 
	  c.fill = GridBagConstraints.NONE;
	  c.gridx = 1; c.gridy = 2;
	  add(edit, c);

  } // constructor

	public void getNounsFromLexTree() 
	{
		
		// The noun-vector: all the nouns from the lexTree.
		nounVector = new Vector();
		Enumeration enumer = LexiconPanel.top.breadthFirstEnumeration();
		DefaultMutableTreeNode tmp;
		IconData id;
		while (enumer.hasMoreElements())
		{
			tmp = (DefaultMutableTreeNode) enumer.nextElement();
			Object o = (Object)(tmp.getUserObject());
			id = (IconData)o;
			Icon ii = id.getIcon();
			ImageIcon im = (ImageIcon)ii;
			if (im == LexiconPanel.ICON_N)
			{
				String nounCode = tmp.toString();
				nounVector.addElement(new ListData(nounCode));
			}
		}	
	} // method getNounsFromLexTree()

} // class