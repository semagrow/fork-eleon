//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui.lang.en;

import gr.demokritos.iit.eleon.authoring.ColumnLayout;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.LexiconDefaultVector;
import gr.demokritos.iit.eleon.authoring.LexiconPanel;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

/**
 * <p>Title: EnglishVerbPanel</p>
 * <p>Description: The panel for the english verbs in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class EnglishVerbPanel extends JPanel implements ActionListener, FocusListener 
{
	static Font panelfont= new Font(Mpiro.selectedFont,Font.BOLD,11);;
	JCheckBox advancedSpellingOptions;
	JPanel pv33;
	JPanel vcenterpanel;
	
	static JTextField vbasetext;
	static JRadioButton vtrans1;
	static JRadioButton vtrans2;
	static JCheckBox vcb1;
	static JCheckBox vcb2;
	static JCheckBox vcb3;
	static JCheckBox vcb4;
	static JTextField thirdpstext;
	static JTextField sipatext;
	static JTextField prpatext;
	static JTextField papatext;


	public EnglishVerbPanel() 
	{
		String englishSpecificInformation_text = LangResources.getString(Mpiro.selectedLocale, "englishSpecificInformation_text");
		String baseForm_text = LangResources.getString(Mpiro.selectedLocale, "baseForm_text");
		String transitive_text = LangResources.getString(Mpiro.selectedLocale, "transitive_text");
		String yes_text = LangResources.getString(Mpiro.selectedLocale, "yes_text");
		String no_text = LangResources.getString(Mpiro.selectedLocale, "no_text");
		String advancedSpellingOptions_text = LangResources.getString(Mpiro.selectedLocale, "advancedSpellingOptions_text");
		String spelling_text = LangResources.getString(Mpiro.selectedLocale, "spelling_text");
		String thirdPersonSingular_text = LangResources.getString(Mpiro.selectedLocale, "3rdPersonSingular_text");
		String simplePast_text = LangResources.getString(Mpiro.selectedLocale, "simplePast_text");
		String presentParticiple_text = LangResources.getString(Mpiro.selectedLocale, "presentParticiple_text");
		String pastParticiple2_text = LangResources.getString(Mpiro.selectedLocale, "pastParticiple2_text");
		String checkTheBoxToModify_text = LangResources.getString(Mpiro.selectedLocale, "checkTheBoxToModify_text");
		String theDefaultSuggestion_text = LangResources.getString(Mpiro.selectedLocale, "theDefaultSuggestion_text");


	  // The panels in order of appearance :
	  // 1
	  JLabel labelEV = new JLabel(" " + LexiconPanel.n.getParent().toString() + ": " + englishSpecificInformation_text);
	  labelEV.setFont(new Font(Mpiro.selectedFont,Font.BOLD,16));
	  labelEV.setForeground(Color.black);
	  labelEV.setPreferredSize(new Dimension(380, 33));
	  // 2
	  KLabel vbaseform = new KLabel(baseForm_text);
	  vbasetext = new JTextField("", 24);
	  // 3
	  KLabel vtranslabel = new KLabel(transitive_text);
	  vtrans1 = new JRadioButton(yes_text, true);
	  vtrans2 = new JRadioButton(no_text, false);
	  ButtonGroup bg = new ButtonGroup();
	  bg.add(vtrans1);
	  bg.add(vtrans2);
	  JPanel vtranspanel = new JPanel(new BorderLayout());
	  vtranspanel.add("West", vtrans1);
	  vtranspanel.add("Center", vtrans2);

    //after 3 (advanced spelling options button)
    advancedSpellingOptions = new JCheckBox(advancedSpellingOptions_text, false);
    JPanel advancedspellingoptionspanel = new JPanel(new BorderLayout());
    //advancedspellingoptionspanel.setPreferredSize(new Dimension(250, 20));
    advancedspellingoptionspanel.add(advancedSpellingOptions);

	  // 4
	  KLabel vspelling = new KLabel(spelling_text);
	  vcb1 = new JCheckBox();
	  KLabel vcb1space = new KLabel(" ");
	  JPanel vcb1panel = new JPanel(new BorderLayout());
	  vcb1panel.add("West", vcb1space);
	  vcb1panel.add("Center", vcb1);
	  vcb2 = new JCheckBox();
	  KLabel vcb2space = new KLabel(" ");
	  JPanel vcb2panel = new JPanel(new BorderLayout());
	  vcb2panel.add("West", vcb2space);
	  vcb2panel.add("Center", vcb2);
	  vcb3 = new JCheckBox();
	  KLabel vcb3space = new KLabel(" ");
	  JPanel vcb3panel = new JPanel(new BorderLayout());
	  vcb3panel.add("West", vcb3space);
	  vcb3panel.add("Center", vcb3);
	  vcb4 = new JCheckBox();
	  KLabel vcb4space = new KLabel(" ");
	  JPanel vcb4panel = new JPanel(new BorderLayout());
	  vcb4panel.add("West", vcb4space);
	  vcb4panel.add("Center", vcb4);
	  JPanel vcheckboxpanel = new JPanel(new GridLayout(4,1));
	  vcheckboxpanel.add(vcb1panel);
	  vcheckboxpanel.add(vcb2panel);
	  vcheckboxpanel.add(vcb3panel);
	  vcheckboxpanel.add(vcb4panel);
	  KLabel thirdps = new KLabel(thirdPersonSingular_text);
	  thirdpstext = new JTextField("", 12);
	  KLabel sipa = new KLabel(simplePast_text);
	  sipatext = new JTextField("", 12);
	  KLabel prpa = new KLabel(presentParticiple_text);
	  prpatext = new JTextField("", 12);
	  KLabel papa = new KLabel(pastParticiple2_text);
	  papatext = new JTextField("", 12);
	  JPanel vlabels = new JPanel(new GridLayout(4,1));
	  JPanel vtextfields = new JPanel(new GridLayout(4,1));
	  vlabels.add(thirdps);
	  vlabels.add(sipa);
	  vlabels.add(prpa);
	  vlabels.add(papa);
	  vtextfields.add(thirdpstext);
	  vtextfields.add(sipatext);
	  vtextfields.add(prpatext);
	  vtextfields.add(papatext);
	  KLabel virregular1 = new KLabel("                                          " + checkTheBoxToModify_text);
	  KLabel virregular2 = new KLabel("                                          " + theDefaultSuggestion_text);
	  JPanel virregular = new JPanel(new GridLayout(2, 1));
	  virregular1.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
	  virregular2.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
	  virregular.add(virregular1);
	  virregular.add(virregular2);
	  JPanel vright = new JPanel(new BorderLayout());
	  vright.add("North", virregular);
	  vright.add("West", vlabels);
	  vright.add("Center", vtextfields);
	  vright.add("East", vcheckboxpanel);

	  // Putting all the panels 1 - 4 together
	  vcenterpanel = new JPanel(new ColumnLayout());

	  JPanel pv1 = new JPanel(new BorderLayout());
	  JPanel pv2 = new JPanel(new BorderLayout());
	  JPanel pv3 = new JPanel(new BorderLayout());

	  JPanel pv11 = new JPanel(new BorderLayout());
	  JPanel pv22 = new JPanel(new BorderLayout());
	  pv33 = new JPanel(new BorderLayout());

	  pv1.setPreferredSize(new Dimension(90,10));
	  pv2.setPreferredSize(new Dimension(90,10));
	  pv3.setPreferredSize(new Dimension(90,10));

		pv1.add("West", vbaseform);
		pv2.add("West", vtranslabel);
		pv3.add("West", vspelling);
		
		pv11.add("West", pv1);
		pv22.add("West", pv2);
		pv33.add("West", pv3);
		
		pv11.add("Center", vbasetext);
		pv22.add("Center", vtranspanel);
		pv33.add("Center", vright);
		
		vcenterpanel.add(pv11);
		vcenterpanel.add(pv22);
		vcenterpanel.add(advancedspellingoptionspanel);
		//vcenterpanel.add(pv33);

		this.setLayout(new BorderLayout());
		this.add("North", labelEV);
		this.add("Center", vcenterpanel);
		
		// Formatting works
		vtrans1.setFont(panelfont);
		vtrans2.setFont(panelfont);
		pv33.setBorder(new LineBorder(new Color(250,250,250), 1));
		pv33.setPreferredSize(new Dimension(385,110));

    advancedSpellingOptions.addMouseListener(new MouseAdapter() 
    {
	    public void mousePressed(MouseEvent e) 
	    {
	      if (advancedSpellingOptions.isSelected() == false) 
	      {
	        //vPanel.setLayout(new ColumnLayout());
	        vcenterpanel.add(pv33);
	      } 
	      
	      else if (advancedSpellingOptions.isSelected() == true) 
	      	{vcenterpanel.remove(pv33);}
	      	
	      vcenterpanel.revalidate();
	      vcenterpanel.repaint();
	    }
    });



		/** Add the Action Listeners */
		vbasetext.addFocusListener(EnglishVerbPanel.this);
		vtrans1.addActionListener(EnglishVerbPanel.this);
		vtrans2.addActionListener(EnglishVerbPanel.this);
		
		vcb1.addActionListener(EnglishVerbPanel.this);
		vcb2.addActionListener(EnglishVerbPanel.this);
		vcb3.addActionListener(EnglishVerbPanel.this);
		vcb4.addActionListener(EnglishVerbPanel.this);
		
		thirdpstext.addFocusListener(EnglishVerbPanel.this);
		sipatext.addFocusListener(EnglishVerbPanel.this);
		prpatext.addFocusListener(EnglishVerbPanel.this);
		papatext.addFocusListener(EnglishVerbPanel.this);

		//System.out.println(QueryLexiconHashtable.currentValues);
		//if ((QueryLexiconHashtable.showValues(DataBasePanel.last.toString(),
		//    TreePreviews.dbt.dbtl.selectedField.toString(),
		//    DataBaseTable.dbTable.getValueAt(currentRow,3).toString())).isEmpty() == false) {
		
		//System.out.println("Current Values: " + QueryHashtable.currentValues);
		showValues(QueryLexiconHashtable.showValues(LexiconPanel.parent.toString(), "English"));
		//}
		/*
		if ((QueryHashtable.showSpecialValues(DataBasePanel.last.toString(),
		TreePreviews.dbt.dbtl.selectedField.toString(),
		DataBaseTable.dbTable.getValueAt(currentRow,3).toString())).isEmpty() == false) {
		
		//System.out.println("Current Special Values: " + QueryHashtable.currentSpecialValues);
		showSpecialValues(QueryHashtable.showSpecialValues(DataBasePanel.last.toString(),
		       TreePreviews.dbt.dbtl.selectedField.toString(),
		       DataBaseTable.dbTable.getValueAt(currentRow,3).toString()));
		}
		*/
		//System.out.println(QueryLexiconHashtable.currentValues);
		vcenterpanel.revalidate();
		vcenterpanel.repaint();
		//panelAdvanced.repaint();
	}	  // constructor


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
			{item = tf.getText();}
		if (rb != null) 
		{
	    if (label.equalsIgnoreCase("transitive"))
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
		}

		// Update the Hashtable
		QueryLexiconHashtable.updateLexiconEntryVerb(node, "English", label, item);
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
	  if (fe.getSource() == vbasetext) 
	  	{printAndUpdate("vbasetext", null, vbasetext, null, null);}
	
	  if (fe.getSource() == thirdpstext) 
	  	{printAndUpdate("thirdpstext", null, thirdpstext, null, null);}
	
	  if (fe.getSource() == sipatext) 
	  	{printAndUpdate("sipatext", null, sipatext, null, null);}
	
	  if (fe.getSource() == prpatext) 
	  	{printAndUpdate("prpatext", null, prpatext, null, null);}
	
	  if (fe.getSource() == papatext) 
	  	{printAndUpdate("papatext", null, papatext, null, null);}
  }


  public void actionPerformed(ActionEvent e) 
  {
  	if (e.getSource() == vtrans1) 
    	{printAndUpdate("transitive", null, null, vtrans1, null);}

    if (e.getSource() == vtrans2) 
    	{printAndUpdate("transitive", null, null, vtrans2, null);}

    if (e.getSource() == vcb1) 
    	{printAndUpdate("vcb1", vcb1, null, null, null);}

    if (e.getSource() == vcb2) 
    	{printAndUpdate("vcb2", vcb2, null, null, null);}

    if (e.getSource() == vcb3) 
    	{printAndUpdate("vcb3", vcb3, null, null, null);}

    if (e.getSource() == vcb4) 
    	{printAndUpdate("vcb4", vcb4, null, null, null);}
		/*
		    if (e.getSource() == advancedOptions) {
		
		        centerPanel.removeAll();
		        if (advancedOptions.isSelected()) {
		             centerPanel.add(panelMain, "North");
		             centerPanel.add(panelAdvanced, "Center");
		             centerPanel.revalidate();
		             centerPanel.repaint();
		             //advancedOptions.setSelected(false);
		        } else {
		             centerPanel.add(panelMain);
		             centerPanel.revalidate();
		             centerPanel.repaint();
		        }
		    }
		*/
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
			
			if (keyString.compareTo("vbasetext") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      vbasetext.setText(keyValue);
			} 
           
			else if (keyString.compareTo("transitive") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      if ( keyValue.equalsIgnoreCase("Yes")) {vtrans1.setSelected(true);}
	      else if ( keyValue.equalsIgnoreCase("No")) {vtrans2.setSelected(true);}
			} 
           
			else if (keyString.compareTo("vcb1") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      if (keyValue.equalsIgnoreCase("true")) {vcb1.setSelected(true);}
	      else if (keyValue.equalsIgnoreCase("false")) {vcb1.setSelected(false);}
			}
			
			else if (keyString.compareTo("vcb2") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      if (keyValue.equalsIgnoreCase("true")) {vcb2.setSelected(true);}
	      else if (keyValue.equalsIgnoreCase("false")) {vcb2.setSelected(false);}
			} 
           
			else if (keyString.compareTo("vcb3") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      if (keyValue.equalsIgnoreCase("true")) {vcb3.setSelected(true);}
	      else if (keyValue.equalsIgnoreCase("false")) {vcb3.setSelected(false);}
			} 
			
			else if (keyString.compareTo("vcb4") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      if (keyValue.equalsIgnoreCase("true")) {vcb4.setSelected(true);}
	      else if (keyValue.equalsIgnoreCase("false")) {vcb4.setSelected(false);}
			} 
           
			else if (keyString.compareTo("thirdpstext") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      thirdpstext.setText(keyValue);
			} 
			
			else if (keyString.compareTo("sipatext") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      sipatext.setText(keyValue);
			} 
			
			else if (keyString.compareTo("prpatext") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      prpatext.setText(keyValue);
			} 
           
			else if (keyString.compareTo("papatext") == 0) 
			{
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      papatext.setText(keyValue);
			} 
			
			else 
			{
			  //System.out.println();
			  //System.out.println("----------- Lexicon Alert ---------");
			  //System.out.println();
			};// if
		}
  }
} //class
