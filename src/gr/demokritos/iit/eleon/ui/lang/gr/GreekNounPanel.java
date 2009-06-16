//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui.lang.gr;

import gr.demokritos.iit.eleon.authoring.ColumnLayout;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.LexiconDefaultVector;
import gr.demokritos.iit.eleon.authoring.LexiconPanel;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

/**
 * <p>Title: GreekNounPanel</p>
 * <p>Description: The panel for the greek nouns in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class GreekNounPanel extends JPanel implements ActionListener, FocusListener 
{
	static Font panelfont = new Font(Mpiro.selectedFont,Font.BOLD,11);
	
	JCheckBox advancedSpellingOptions;
	JPanel advancedspellingoptionspanel;
	KButton updateFields = new KButton(LangResources.getString(Mpiro.selectedLocale, "updateFields_button"));
	JPanel p66;
	JPanel grcenterpanel;
	
	static JTextField grbasetext;
	static JTextField grpluraltext;
	static JRadioButton grmasculine;
	static JRadioButton grfeminine;
	static JRadioButton grneuter;
	static JRadioButton grcountnoun;
	static JRadioButton grmassnoun;
	static JRadioButton grinf1;
	static JRadioButton grinf2;

	static JTextField grsntext;
	static JTextField grsgtext;
	static JTextField grsatext;
	static JTextField grpntext;
	static JTextField grpgtext;
	static JTextField grpatext;
	static JCheckBox cb1;
	static JCheckBox cb2;
	static JCheckBox cb3;
	static JCheckBox cb4;
	static JCheckBox cb5;
	static JCheckBox cb6;

	/**
	 * Constructor.
	 */
	public GreekNounPanel() 
	{
		String greekSpecificInformation_text = LangResources.getString(Mpiro.selectedLocale, "greekSpecificInformation_text");
		String nominativeSingularForm_text = LangResources.getString(Mpiro.selectedLocale, "nominativeSingularForm_text");
		String nominativePluralForm_text = LangResources.getString(Mpiro.selectedLocale, "nominativePluralForm_text");
		String grammatical_text = LangResources.getString(Mpiro.selectedLocale, "grammatical_text");
		String gender_text = LangResources.getString(Mpiro.selectedLocale, "gender_text");
		String masculine_text = LangResources.getString(Mpiro.selectedLocale, "masculine_text");
		String feminine_text = LangResources.getString(Mpiro.selectedLocale, "feminine_text");
		String neuter_text = LangResources.getString(Mpiro.selectedLocale, "neuter_text");
		String countable_text = LangResources.getString(Mpiro.selectedLocale, "countable_text");
		String yes_text = LangResources.getString(Mpiro.selectedLocale, "yes_text");
		String no_text = LangResources.getString(Mpiro.selectedLocale, "no_text");
		String inflection_text = LangResources.getString(Mpiro.selectedLocale, "inflection_text");
		String inflected_text = LangResources.getString(Mpiro.selectedLocale, "inflected_text");
		String notInflected_text = LangResources.getString(Mpiro.selectedLocale, "notInflected_text");
		String advancedSpellingOptions_text = LangResources.getString(Mpiro.selectedLocale, "advancedSpellingOptions_text");
		String spelling_text = LangResources.getString(Mpiro.selectedLocale, "spelling_text");
		String singularNominative_text = LangResources.getString(Mpiro.selectedLocale, "singularNominative_text");
		String singularGenitive_text = LangResources.getString(Mpiro.selectedLocale, "singularGenitive_text");
		String singularAccusative_text = LangResources.getString(Mpiro.selectedLocale, "singularAccusative_text");
		String pluralNominative_text = LangResources.getString(Mpiro.selectedLocale, "pluralNominative_text");
		String pluralGenitive_text = LangResources.getString(Mpiro.selectedLocale, "pluralGenitive_text");
		String pluralAccusative_text = LangResources.getString(Mpiro.selectedLocale, "pluralAccusative_text");
		String checkTheBoxToModify_text = LangResources.getString(Mpiro.selectedLocale, "checkTheBoxToModify_text");
		String theDefaultSuggestion_text = LangResources.getString(Mpiro.selectedLocale, "theDefaultSuggestion_text");
		

		// The panels in order of appearance :
		// 1
		JLabel labelGN = new JLabel(" " + LexiconPanel.n.getParent().toString() + ": " + greekSpecificInformation_text);
		labelGN.setFont(new Font(Mpiro.selectedFont,Font.BOLD,16));
		labelGN.setForeground(Color.black);
		labelGN.setPreferredSize(new Dimension(380, 33));
		// 2
		KLabel grbaseform = new KLabel(nominativeSingularForm_text);
		KLabel grbasepluralform = new KLabel(nominativePluralForm_text);
		grbasetext = new JTextField(12);
		grpluraltext = new JTextField(12);
		//grbasetext.setText(GreekConverter.Win2UniString(" ������"));
		grbasetext.setText("");
		grpluraltext.setText("");
		// 3
		JPanel grgender = new JPanel(new GridLayout(2, 1));
		KLabel grgender1 = new KLabel(grammatical_text);
		KLabel grgender2 = new KLabel(gender_text);
		grgender.add(grgender1);
		grgender.add(grgender2);
		grmasculine = new JRadioButton(masculine_text, false);
		grfeminine = new JRadioButton(feminine_text, false);
		grneuter = new JRadioButton(neuter_text, true);
		ButtonGroup grbg1 = new ButtonGroup();
		grbg1.add(grmasculine);
		grbg1.add(grfeminine);
		grbg1.add(grneuter);
		JPanel grgenderpanel = new JPanel(new BorderLayout());
		grgenderpanel.add("West", grmasculine);
		grgenderpanel.add("Center", grfeminine);
		grgenderpanel.add("East", grneuter);
		// 4
		KLabel grcount = new KLabel(countable_text);
		grcountnoun = new JRadioButton(yes_text, true);
		grmassnoun = new JRadioButton(no_text, false);
		ButtonGroup grbg2 = new ButtonGroup();
		grbg2.add(grcountnoun);
		grbg2.add(grmassnoun);
		JPanel grcountpanel = new JPanel(new BorderLayout());
		grcountpanel.add("West", grcountnoun);
		grcountpanel.add("Center", grmassnoun);
		// 5
		KLabel grinflection = new KLabel(inflection_text);
		grinf1 = new JRadioButton(inflected_text, true);
		grinf2 = new JRadioButton(notInflected_text, false);
		ButtonGroup grbg4 = new ButtonGroup();
		grbg4.add(grinf1); grbg4.add(grinf2);
		JPanel grinflectionpanel = new JPanel(new BorderLayout());
		grinflectionpanel.add("West", grinf1);
		grinflectionpanel.add("Center", grinf2);
		
		//after 5 (advanced spelling options button)
		advancedSpellingOptions = new JCheckBox(advancedSpellingOptions_text, false);
		advancedspellingoptionspanel = new JPanel(new BorderLayout());
		advancedspellingoptionspanel.setPreferredSize(new Dimension(380, 20));
		advancedspellingoptionspanel.add(BorderLayout.WEST, advancedSpellingOptions);
		
		// 6
		KLabel grspelling = new KLabel(spelling_text);
		cb1 = new JCheckBox();
		KLabel cb1space = new KLabel(" ");
		JPanel cb1panel = new JPanel(new BorderLayout());
		cb1panel.add("West", cb1space);
		cb1panel.add("Center", cb1);
		cb2 = new JCheckBox();
		KLabel cb2space = new KLabel(" ");
		JPanel cb2panel = new JPanel(new BorderLayout());
		cb2panel.add("West", cb2space);
		cb2panel.add("Center", cb2);
		cb3 = new JCheckBox();
		KLabel cb3space = new KLabel(" ");
		JPanel cb3panel = new JPanel(new BorderLayout());
		cb3panel.add("West", cb3space);
		cb3panel.add("Center", cb3);
		cb4 = new JCheckBox();
		KLabel cb4space = new KLabel(" ");
		JPanel cb4panel = new JPanel(new BorderLayout());
		cb4panel.add("West", cb4space);
		cb4panel.add("Center", cb4);
		cb5 = new JCheckBox();
		KLabel cb5space = new KLabel(" ");
		JPanel cb5panel = new JPanel(new BorderLayout());
		cb5panel.add("West", cb5space);
		cb5panel.add("Center", cb5);
		cb6 = new JCheckBox();
		KLabel cb6space = new KLabel(" ");
		JPanel cb6panel = new JPanel(new BorderLayout());
		cb6panel.add("West", cb6space);
		cb6panel.add("Center", cb6);
		JPanel grcheckboxpanel = new JPanel(new GridLayout(6,1));
		grcheckboxpanel.add(cb1panel);
		grcheckboxpanel.add(cb2panel);
		grcheckboxpanel.add(cb3panel);
		grcheckboxpanel.add(cb4panel);
		grcheckboxpanel.add(cb5panel);
		grcheckboxpanel.add(cb6panel);

		KLabel grsn = new KLabel(singularNominative_text);
		grsntext = new JTextField("", 12);
		KLabel grsg = new KLabel(singularGenitive_text);
		grsgtext = new JTextField("", 12);
		KLabel grsa = new KLabel(singularAccusative_text);
		grsatext = new JTextField("", 12);
		KLabel grpn = new KLabel(pluralNominative_text);
		grpntext = new JTextField("", 12);
		KLabel grpg = new KLabel(pluralGenitive_text);
		grpgtext = new JTextField("", 12);
		KLabel grpa = new KLabel(pluralAccusative_text);
		grpatext = new JTextField("", 12);
		JPanel grvoices = new JPanel(new BorderLayout());
		JPanel voice = new JPanel(new GridLayout(6,1));
		JPanel voicetext = new JPanel(new GridLayout(6,1));
		voice.add(grsn);
		voice.add(grsg);
		voice.add(grsa);
		voice.add(grpn);
		voice.add(grpg);
		voice.add(grpa);
		voicetext.add(grsntext);
		voicetext.add(grsgtext);
		voicetext.add(grsatext);
		voicetext.add(grpntext);
		voicetext.add(grpgtext);
		voicetext.add(grpatext);
		KLabel grirregular1 = new KLabel("                                          " + checkTheBoxToModify_text);
		KLabel grirregular2 = new KLabel("                                          " + theDefaultSuggestion_text);
		JPanel grirregular = new JPanel(new GridLayout(2, 1));
		grirregular1.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
		grirregular2.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
		grirregular.add(grirregular1);
		grirregular.add(grirregular2);
		grvoices.add("North", grirregular);
		grvoices.add("West", voice);
		grvoices.add("Center", voicetext);
		grvoices.add("East", grcheckboxpanel);

		// Putting all the panels 1 - 7 together
		grcenterpanel = new JPanel(new ColumnLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel(new BorderLayout());
		JPanel p3 = new JPanel(new BorderLayout());
		JPanel p4 = new JPanel(new BorderLayout());
		JPanel p5 = new JPanel(new BorderLayout());
		JPanel p6 = new JPanel(new BorderLayout());
		
		JPanel p11 = new JPanel(new BorderLayout());
		JPanel p22 = new JPanel(new BorderLayout());
		JPanel p33 = new JPanel(new BorderLayout());
		JPanel p44 = new JPanel(new BorderLayout());
		JPanel p55 = new JPanel(new BorderLayout());
		p66 = new JPanel(new BorderLayout());
		
		p1.setPreferredSize(new Dimension(160,10));
		p2.setPreferredSize(new Dimension(160,10));
		p3.setPreferredSize(new Dimension(85,30));
		p4.setPreferredSize(new Dimension(85,10));
		p5.setPreferredSize(new Dimension(85,10));
		p6.setPreferredSize(new Dimension(85,10));

		p1.add("West", grbaseform);
		p2.add("West", grbasepluralform);
		p3.add("West", grgender);
		p4.add("West", grcount);
		p5.add("West", grinflection);
		p6.add("West", grspelling);
		
		p11.add("West", p1);
		p22.add("West", p2);
		p33.add("West", p3);
		p44.add("West", p4);
		p55.add("West", p5);
		p66.add("West", p6);
		
		p11.add("Center", grbasetext);
		p22.add("Center", grpluraltext);
		p33.add("Center", grgenderpanel);
		p44.add("Center", grcountpanel);
		p55.add("Center", grinflectionpanel);
		p66.add("Center", grvoices);
		
		grcenterpanel.add(p11);
		grcenterpanel.add(p22);
		grcenterpanel.add(p33);
		grcenterpanel.add(p44);
		grcenterpanel.add(p55);
		grcenterpanel.add(advancedspellingoptionspanel);
		//grcenterpanel.add(grinflectionpanel);
		//grcenterpanel.add(p66);
		
		this.setLayout(new BorderLayout());
		this.add("North", labelGN);
		this.add("Center", grcenterpanel);
		
		// Formatting works
		grmasculine.setFont(panelfont);
		grfeminine.setFont(panelfont);
		grneuter.setFont(panelfont);
		grcountnoun.setFont(panelfont);
		grmassnoun.setFont(panelfont);
		grinf1.setFont(panelfont);
		grinf2.setFont(panelfont);
		p66.setBorder(new LineBorder(new Color(250,250,250), 1));
		p66.setPreferredSize(new Dimension(395,160));

		advancedSpellingOptions.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e) 
			{
				if (advancedSpellingOptions.isSelected() == false) 
				{
					grcenterpanel.add(p66);
					advancedspellingoptionspanel.add(BorderLayout.EAST, updateFields);
					autocompleteSpellingFields();
				} 
				else if (advancedSpellingOptions.isSelected() == true) 
				{
					grcenterpanel.remove(p66);
					advancedspellingoptionspanel.remove(updateFields);
				}
				grcenterpanel.revalidate();
				grcenterpanel.repaint();
			}
		});


		/** Add the Action Listeners */
		updateFields.addActionListener(GreekNounPanel.this);
		
		grbasetext.addFocusListener(GreekNounPanel.this);
		grpluraltext.addFocusListener(GreekNounPanel.this);
		grmasculine.addActionListener(GreekNounPanel.this);
		grfeminine.addActionListener(GreekNounPanel.this);
		grneuter.addActionListener(GreekNounPanel.this);
		grcountnoun.addActionListener(GreekNounPanel.this);
		grmassnoun.addActionListener(GreekNounPanel.this);
		grinf1.addActionListener(GreekNounPanel.this);
		grinf2.addActionListener(GreekNounPanel.this);
		grsntext.addFocusListener(GreekNounPanel.this);
		grsgtext.addFocusListener(GreekNounPanel.this);
		grsatext.addFocusListener(GreekNounPanel.this);
		grpntext.addFocusListener(GreekNounPanel.this);
		grpgtext.addFocusListener(GreekNounPanel.this);
		grpatext.addFocusListener(GreekNounPanel.this);
		cb1.addActionListener(GreekNounPanel.this);
		cb2.addActionListener(GreekNounPanel.this);
		cb3.addActionListener(GreekNounPanel.this);
		cb4.addActionListener(GreekNounPanel.this);
		cb5.addActionListener(GreekNounPanel.this);
		cb6.addActionListener(GreekNounPanel.this);

		//System.out.println(QueryLexiconHashtable.currentValues);
		//System.out.println("Current Values: " + QueryHashtable.currentValues);
		showValues(Mpiro.win.struc.showValues(LexiconPanel.parent.toString(), "Greek"));
		//}
		
		//System.out.println(QueryLexiconHashtable.currentValues);
		grcenterpanel.revalidate();
		grcenterpanel.repaint();

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
			{
				item = "true";
			} 
			else if (! cb.isSelected()) 
			{
				item = "false";
			}
		}
		if (tf != null) 
		{
			item = tf.getText();
		}
		if (rb != null) 
		{
			if (label.equalsIgnoreCase("grgender"))
			{
				if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "masculine_text")) ||
						rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "masculine_text")) ||
						rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "masculine_text"))
						)
				{
					item = LangResources.getString(Mpiro.enLocale, "masculine_text");
				}
				else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "feminine_text")) ||
									rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "feminine_text")) ||
									rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "feminine_text"))
								)
				{
					item = LangResources.getString(Mpiro.enLocale, "feminine_text");
				}
				else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "neuter_text")) ||
									rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "neuter_text")) ||
									rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "neuter_text"))
								)
				{
					item = LangResources.getString(Mpiro.enLocale, "neuter_text");
				}
			}

			else if (label.equalsIgnoreCase("countable"))
			{
				if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "yes_text")) ||
				    rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "yes_text")) ||
				    rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "yes_text"))
				    )
				{
				  item = LangResources.getString(Mpiro.enLocale, "yes_text");
				}
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "no_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "no_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "no_text"))
                  )
        {
          item = LangResources.getString(Mpiro.enLocale, "no_text");
        }
			}

			else if (label.equalsIgnoreCase("grinflection"))
			{
	      if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "inflected_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "inflected_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "inflected_text"))
						)
	      {
	        item = LangResources.getString(Mpiro.enLocale, "inflected_text");
	      }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "notInflected_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "notInflected_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "notInflected_text"))
								)
        {
					item = LangResources.getString(Mpiro.enLocale, "notInflected_text");
				}
			}

			//System.out.println("Lexicon label: " + label);
			//item = rb.getText();
			//System.out.println("Lexicon item : " + item);
		}//if

		// Update the Hashtable
		Mpiro.win.struc.updateLexiconEntryNoun(node, "Greek", label, item);
		Mpiro.needExportToExprimo = true;		//maria

		/* Print some info
		//System.out.println("/////// Info START");
		//System.out.println(node + " / " + "Greek" + " / " + label);
		//System.out.println(item);
		//System.out.println(QueryLexiconHashtable.currentValues);
		//System.out.println("/////// Info END");
		*/
	}


	public void focusGained(FocusEvent fe) 
	{
		//System.out.println("FOCUS GAINED");
	}

	public void focusLost(FocusEvent fe) 
	{
		//System.out.println("FOCUS LOST");	
		if (fe.getSource() == grbasetext) 
		{
			printAndUpdate("grbasetext", null, grbasetext, null, null);
		}
		if (fe.getSource() == grpluraltext) 
		{
			printAndUpdate("grpluraltext", null, grpluraltext, null, null);
		}
		if (fe.getSource() == grsntext) 
		{
			printAndUpdate("grsntext", null, grsntext, null, null);
		}
		if (fe.getSource() == grsgtext) 
		{
			printAndUpdate("grsgtext", null, grsgtext, null, null);
		}
		if (fe.getSource() == grsatext) 
		{
			printAndUpdate("grsatext", null, grsatext, null, null);
		}
		if (fe.getSource() == grpntext) 
		{
			printAndUpdate("grpntext", null, grpntext, null, null);
		}
		if (fe.getSource() == grpgtext) 
		{
			printAndUpdate("grpgtext", null, grpgtext, null, null);
		}
		if (fe.getSource() == grpatext) 
		{
			printAndUpdate("grpatext", null, grpatext, null, null);
		}
	}


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == grmasculine) 
		{
			printAndUpdate("grgender", null, null, grmasculine, null);
		}
		if (e.getSource() == grfeminine) 
		{
			printAndUpdate("grgender", null, null, grfeminine, null);
		}
		if (e.getSource() == grneuter) 
		{
			printAndUpdate("grgender", null, null, grneuter, null);
		}
		if (e.getSource() == grcountnoun) 
		{
			printAndUpdate("countable", null, null, grcountnoun, null);
		}
		if (e.getSource() == grmassnoun) 
		{
			printAndUpdate("countable", null, null, grmassnoun, null);
		}
		if (e.getSource() == grinf1) 
		{
			printAndUpdate("grinflection", null, null, grinf1, null);
		}
		if (e.getSource() == grinf2) 
		{
			printAndUpdate("grinflection", null, null, grinf2, null);
		}
		if (e.getSource() == cb1) 
		{
			printAndUpdate("cb1", cb1, null, null, null);
		}
		if (e.getSource() == cb2) 
		{
			printAndUpdate("cb2", cb2, null, null, null);
		}
		if (e.getSource() == cb3) 
		{
			printAndUpdate("cb3", cb3, null, null, null);
		}
		if (e.getSource() == cb4) 
		{
			printAndUpdate("cb4", cb4, null, null, null);
		}
		if (e.getSource() == cb5) 
		{
			printAndUpdate("cb5", cb5, null, null, null);
		}
		if (e.getSource() == cb6) 
		{
			printAndUpdate("cb6", cb6, null, null, null);
		}
		if (e.getSource() == updateFields) 
		{
			autocompleteSpellingFields();
		}
	} // actionPerformed


	public static void showValues(Hashtable currentValues) 
	{
		//System.out.println("//////////////////");
		//System.out.println(currentValues);
		//System.out.println("//////////////////");
		
		for (Enumeration k = currentValues.keys(), e = currentValues.elements(); k.hasMoreElements(); ) 
		{
			String keyString = k.nextElement().toString();
			String keyValue = e.nextElement().toString();
			
			if (keyString.compareTo("grbasetext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grbasetext.setText(keyValue);
			} 
			else if (keyString.compareTo("grpluraltext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grpluraltext.setText(keyValue);
			} 
			else if (keyString.compareTo("grgender") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("Masculine")) {grmasculine.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("Feminine")) {grfeminine.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("Neuter")) {grneuter.setSelected(true);}
			} 
			else if (keyString.compareTo("grinflection") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("Inflected")) {grinf1.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("Not inflected")) {grinf2.setSelected(true);}
			} 
			else if (keyString.compareTo("countable") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("Yes")) {grcountnoun.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("No")) {grmassnoun.setSelected(true);}
			} 
			else if (keyString.compareTo("grsntext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grsntext.setText(keyValue);
			} 
			else if (keyString.compareTo("grsgtext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grsgtext.setText(keyValue);
			} 
			else if (keyString.compareTo("grsatext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grsatext.setText(keyValue);
			} 
			else if (keyString.compareTo("grpntext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grpntext.setText(keyValue);
			} 
			else if (keyString.compareTo("grpgtext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grpgtext.setText(keyValue);
			} 
			else if (keyString.compareTo("grpatext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				grpatext.setText(keyValue);
			} 
			else if (keyString.compareTo("cb1") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("true")) {cb1.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb1.setSelected(false);}
			} 
			else if (keyString.compareTo("cb2") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("true")) {cb2.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb2.setSelected(false);}
			} 
			else if (keyString.compareTo("cb3") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("true")) {cb3.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb3.setSelected(false);}
			} 
			else if (keyString.compareTo("cb4") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("true")) {cb4.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb4.setSelected(false);}
			} 
			else if (keyString.compareTo("cb5") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("true")) {cb5.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb5.setSelected(false);}
			} 
			else if (keyString.compareTo("cb6") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("true")) {cb6.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb6.setSelected(false);}
			} 
			else 
			{
				//System.out.println();
				//System.out.println("----------- Lexicon Alert ---------");
				//System.out.println();
			};// if
		} // for
	}// showValues


	public static void autocompleteSpellingFields() 
	{
		if ( (grbasetext.getText().length() != 0) && (grpluraltext.getText().length() != 0) )
		{
			String baseformString = grbasetext.getText();
			String spellingString = GreekMorphology.getSpellingStringNoun(LexiconPanel.parent.toString(), grbasetext.getText());
			if (spellingString.length() != 0)
			{
				if (!cb1.isSelected())
				{
					Vector featuresVector = GreekMorphology.getFeaturesVectorNoun(LexiconPanel.parent.toString(), "Greek", "singular-noun nominative-noun");
					grsntext.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}
				if (!cb2.isSelected())
				{
					Vector featuresVector = GreekMorphology.getFeaturesVectorNoun(LexiconPanel.parent.toString(), "Greek", "singular-noun genitive-noun");
					grsgtext.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}
				if (!cb3.isSelected())
				{
					Vector featuresVector = GreekMorphology.getFeaturesVectorNoun(LexiconPanel.parent.toString(), "Greek", "singular-noun accusative-noun");
					grsatext.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}
				if (!cb4.isSelected())
				{
					Vector featuresVector = GreekMorphology.getFeaturesVectorNoun(LexiconPanel.parent.toString(), "Greek", "plural-noun nominative-noun");
					grpntext.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}
				if (!cb5.isSelected())
				{
					Vector featuresVector = GreekMorphology.getFeaturesVectorNoun(LexiconPanel.parent.toString(), "Greek", "plural-noun genitive-noun");
					grpgtext.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}
				if (!cb6.isSelected())
				{
					Vector featuresVector = GreekMorphology.getFeaturesVectorNoun(LexiconPanel.parent.toString(), "Greek", "plural-noun accusative-noun");
					grpatext.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}
			}
			else
			{
				if (!cb1.isSelected())
				{
					grsntext.setText("");
				}
				if (!cb2.isSelected())
				{
					grsgtext.setText("");
				}
				if (!cb3.isSelected())
				{
					grsatext.setText("");
				}
				if (!cb4.isSelected())
				{
					grpntext.setText("");
				}
				if (!cb5.isSelected())
				{
					grpgtext.setText("");
				}
				if (!cb6.isSelected())
				{
					grpatext.setText("");
				}
			}
		}

		else
		{
			if (!cb1.isSelected())
			{
				grsntext.setText("");
			}
			if (!cb2.isSelected())
			{
				grsgtext.setText("");
			}
			if (!cb3.isSelected())
			{
				grsatext.setText("");
			}
			if (!cb4.isSelected())
			{
				grpntext.setText("");
			}
			if (!cb5.isSelected())
			{
				grpgtext.setText("");
			}
			if (!cb6.isSelected())
			{
				grpatext.setText("");
			}
		}
	}// autocompleteSpellingFields

} //GreekNounPanel