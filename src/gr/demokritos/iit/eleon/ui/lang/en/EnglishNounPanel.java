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


package gr.demokritos.iit.eleon.ui.lang.en;

import gr.demokritos.iit.eleon.authoring.ColumnLayout;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.LexiconDefaultVector;
import gr.demokritos.iit.eleon.authoring.LexiconPanel;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;


/**
 * <p>Title: EnglishNounPanel</p>
 * <p>Description: The panel for the english nouns in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class EnglishNounPanel extends JPanel implements ActionListener, FocusListener 
{
	static Font panelfont= new Font(Mpiro.selectedFont,Font.BOLD,11);;
	JCheckBox advancedSpellingOptions;
	JPanel p044;
	JPanel centerpanel;
	
	static JTextField enbasetext;
	static JRadioButton encountnoun;
	static JRadioButton enmassnoun;
	static JTextField enpluraltext;
	static JCheckBox encb;
	
	/**
	 * Constructor.
	 */
	public EnglishNounPanel() 
	{
	  String englishSpecificInformation_text = LangResources.getString(Mpiro.selectedLocale, "englishSpecificInformation_text");
	  String baseForm_text = LangResources.getString(Mpiro.selectedLocale, "baseForm_text");
	  String countable_text = LangResources.getString(Mpiro.selectedLocale, "countable_text");
	  String yes_text = LangResources.getString(Mpiro.selectedLocale, "yes_text");
	  String no_text = LangResources.getString(Mpiro.selectedLocale, "no_text");
	  String advancedSpellingOptions_text = LangResources.getString(Mpiro.selectedLocale, "advancedSpellingOptions_text");
	  String spelling_text = LangResources.getString(Mpiro.selectedLocale, "spelling_text");
	  String plural_text = LangResources.getString(Mpiro.selectedLocale, "plural_text");
	  String checkTheBoxToModify_text = LangResources.getString(Mpiro.selectedLocale, "checkTheBoxToModify_text");
	  String theDefaultSuggestion_text = LangResources.getString(Mpiro.selectedLocale, "theDefaultSuggestion_text");


    // The panels in order of appearance
    // 1
    JLabel labelEN = new JLabel(" " + LexiconPanel.n.getParent().toString() + ": " + englishSpecificInformation_text);
    labelEN.setFont(new Font(Mpiro.selectedFont,Font.BOLD,16));
    labelEN.setForeground(Color.black);
    labelEN.setPreferredSize(new Dimension(380, 33));
    // 2
    KLabel baseform = new KLabel(baseForm_text);
    enbasetext = new JTextField("", 22);
    // 3
    KLabel count = new KLabel(countable_text);
    JPanel countpanel = new JPanel(new BorderLayout());
    encountnoun = new JRadioButton(yes_text, true);
    enmassnoun = new JRadioButton(no_text, false);
    ButtonGroup bg = new ButtonGroup();
    bg.add(encountnoun);
    bg.add(enmassnoun);
    countpanel.add("West", encountnoun);
    countpanel.add("Center", enmassnoun);
    // 4
    KLabel spelling = new KLabel(spelling_text);
    KLabel plural = new KLabel(plural_text);
    enpluraltext = new JTextField("", 18);
    encb = new JCheckBox();
    KLabel cbspace = new KLabel(" ");
    JPanel cbpanel = new JPanel(new BorderLayout());
    cbpanel.add("West", cbspace);
    cbpanel.add("Center", encb);
    JPanel pluralpanel = new JPanel(new BorderLayout());
    KLabel irregular1 = new KLabel("                           " + checkTheBoxToModify_text);
    KLabel irregular2 = new KLabel("                           " + theDefaultSuggestion_text);
    JPanel irregular = new JPanel(new GridLayout(2, 1));
    irregular1.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
    irregular2.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
    irregular.add(irregular1);
    irregular.add(irregular2);
    pluralpanel.add("North", irregular);
    pluralpanel.add("West", plural);
    pluralpanel.add("Center", enpluraltext);
    pluralpanel.add("East", cbpanel);

    //after 3 (advanced spelling options button)
    advancedSpellingOptions = new JCheckBox(advancedSpellingOptions_text, false);
    JPanel advancedspellingoptionspanel = new JPanel(new BorderLayout());
    advancedspellingoptionspanel.add(advancedSpellingOptions);

    JPanel p01 = new JPanel(new BorderLayout());
    JPanel p03 = new JPanel(new BorderLayout());
    JPanel p04 = new JPanel(new BorderLayout());
    JPanel p011 = new JPanel(new BorderLayout());
    JPanel p033 = new JPanel(new BorderLayout());
    p044 = new JPanel(new BorderLayout());
    p01.setPreferredSize(new Dimension(90,10));
    p03.setPreferredSize(new Dimension(90,10));
    p04.setPreferredSize(new Dimension(90,10));
    p01.add("West", baseform);
    p03.add("West", count);
    p04.add("West", spelling);
    p011.add("West", p01);
    p033.add("West", p03);
    p044.add("West", p04);
    p011.add("Center", enbasetext);
    p033.add("Center", countpanel);
    p044.add("Center", pluralpanel);

    // Put them all (1 - 4) together
    centerpanel = new JPanel(new ColumnLayout());

    centerpanel.add(p011);
    centerpanel.add(p033);
    centerpanel.add(advancedspellingoptionspanel);
    //centerpanel.add(p044);

    this.setLayout(new BorderLayout());
    this.add("North", labelEN);
    this.add("Center", centerpanel);

    // Formatting works
    encountnoun.setFont(panelfont);
    enmassnoun.setFont(panelfont);
    p044.setBorder(new LineBorder(new Color(250,250,250), 1));
    p044.setPreferredSize(new Dimension(395,50));


    advancedSpellingOptions.addMouseListener(new MouseAdapter() 
    {
	    public void mousePressed(MouseEvent e) 
	    {
	      if (advancedSpellingOptions.isSelected() == false) 
	      	{centerpanel.add(p044);} 
	      	
	      else if (advancedSpellingOptions.isSelected() == true) 
	      	{centerpanel.remove(p044);}
	      	
	      centerpanel.revalidate();
	      centerpanel.repaint();
	    }
    });


		/** Add the Action Listeners */
		enbasetext.addFocusListener(EnglishNounPanel.this);
		encountnoun.addActionListener(EnglishNounPanel.this);
		enmassnoun.addActionListener(EnglishNounPanel.this);
		enpluraltext.addFocusListener(EnglishNounPanel.this);
		encb.addActionListener(EnglishNounPanel.this);


		//System.out.println(QueryLexiconHashtable.currentValues);
		//if ((Mpiro.win.struc.showValues(DataBasePanel.last.toString(),
		//    TreePreviews.dbt.dbtl.selectedField.toString(),
		//    DataBaseTable.dbTable.getValueAt(currentRow,3).toString())).isEmpty() == false) {
		//System.out.println("Current Values: " + QueryHashtable.currentValues);
		showValues(Mpiro.win.struc.showValues(LexiconPanel.parent.toString(), "English"));
		//}
		
		//System.out.println(QueryLexiconHashtable.currentValues);
    centerpanel.revalidate();
    centerpanel.repaint();
	} // constructor



  // A general method added in the actionPerformed method
  public void printAndUpdate(String s, JCheckBox cb, JTextField tf, JRadioButton rb, LexiconDefaultVector ldv) 
  {
		//currentRow = DataBaseTable.dbTable.getSelectedRow();	
		String node = LexiconPanel.parent.toString();
		//System.out.println("my parent : " + node);
		
		String label = s;
		String item = "";
		
		// Distinguish the type of Object clicked.
		if (cb != null) 
		{
	    if (cb.isSelected()) 
	    	{item = "true";} 
	    
	    else if (! cb.isSelected()) 
	    	{item = "false";}
		}
		if (tf != null) 
		{
			item = tf.getText();
		}
		if (rb != null) 
		{
	    //System.out.println("Lexicon label: " + label);
	    if (label.equalsIgnoreCase("countable"))
	    {
	      if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "yes_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "yes_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "yes_text"))
	          )
	      	{item = LangResources.getString(Mpiro.enLocale, "yes_text");}
	      	
	      else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "no_text")) ||
	                rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "no_text")) ||
	                rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "no_text"))
	                )
	      	{item = LangResources.getString(Mpiro.enLocale, "no_text");}
	    }
	    //item = rb.getText();
	    //System.out.println("Lexicon item : " + item);
		}

		// Update the Hashtable
		Mpiro.win.struc.updateLexiconEntryNoun(node, "English", label, item);
		Mpiro.needExportToExprimo = true;		//maria
		
		// Print some info
		//System.out.println("/////// Info START");
		//System.out.println(node + " / " + "English" + " / " + label);
		//System.out.println(item);
		//System.out.println(QueryLexiconHashtable.currentValues);
		//System.out.println("/////// Info END");
	}

  public void focusGained(FocusEvent fe) 
  {
		//System.out.println("FOCUS GAINED");
  }

  public void focusLost(FocusEvent fe) 
  {
	  //System.out.println("FOCUS LOST");
	  if (fe.getSource() == enbasetext) 
	  	{printAndUpdate("enbasetext", null, enbasetext, null, null);}
	  
	  if (fe.getSource() == enpluraltext) 
	  	{printAndUpdate("enpluraltext", null, enpluraltext, null, null);}
  }

  public void actionPerformed(ActionEvent e) 
  {
	  if (e.getSource() == encountnoun) 
	  	{printAndUpdate("countable", null, null, encountnoun, null);}
	
	  if (e.getSource() == enmassnoun) 
	  	{printAndUpdate("countable", null, null, enmassnoun, null);}
	  
	  if (e.getSource() == encb) 
	  	{printAndUpdate("encb", encb, null, null, null);}
  } // actionPerformed

  public static void showValues(Hashtable currentValues) 
  {
		//System.out.println("//////////////////");
		//System.out.println(currentValues);
		//System.out.println("//////////////////");
		
		//if (currentHashtableValues.isEmpty() == false) {
		for (Enumeration k = currentValues.keys(), e = currentValues.elements(); k.hasMoreElements(); ) 
		{
			String keyString = k.nextElement().toString();
			String keyValue = e.nextElement().toString();

			if (keyString.compareTo("enbasetext") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      enbasetext.setText(keyValue);
			} 
           
			else if (keyString.compareTo("countable") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      if (keyValue.equalsIgnoreCase("Yes")) {encountnoun.setSelected(true);}
	      else if (keyValue.equalsIgnoreCase("No")) {enmassnoun.setSelected(true);}
			} 
			else if (keyString.compareTo("enpluraltext") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      enpluraltext.setText(keyValue);
			} 
			else if (keyString.compareTo("encb") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      if (keyValue.equalsIgnoreCase("true")) {encb.setSelected(true);}
	      else if (keyValue.equalsIgnoreCase("false")) {encb.setSelected(false);}
			} 
			else 
			{
			  //System.out.println();
			  //System.out.println("----------- Lexicon Alert ---------");
			  //System.out.println();
			};// if
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
