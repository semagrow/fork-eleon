//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui.lang.it;

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

/**
 * <p>Title: ItalianNounPanel</p>
 * <p>Description: The panel for the english nouns in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class ItalianNounPanel extends JPanel implements ActionListener, FocusListener 
{
	static Font panelfont= new Font(Mpiro.selectedFont,Font.BOLD,11);;
	JCheckBox advancedSpellingOptions;
	JPanel itp044;
	JPanel itcenterpanel;
	
	static JTextField itbasetext;
	static JRadioButton itmasculine;
	static JRadioButton itfeminine;
	static JRadioButton itcountnoun;
	static JRadioButton itmassnoun;
	static JTextField itpluraltext;
	static JCheckBox itcb;

	/**
	 * Constructor.
	 */
	public ItalianNounPanel() 
	{
		String italianSpecificInformation_text = LangResources.getString(Mpiro.selectedLocale, "italianSpecificInformation_text");
		String baseForm_text = LangResources.getString(Mpiro.selectedLocale, "baseForm_text");
		String grammatical_text = LangResources.getString(Mpiro.selectedLocale, "grammatical_text");
		String gender_text = LangResources.getString(Mpiro.selectedLocale, "gender_text");
		String masculine_text = LangResources.getString(Mpiro.selectedLocale, "masculine_text");
		String feminine_text = LangResources.getString(Mpiro.selectedLocale, "feminine_text");
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
	  JLabel labelIN = new JLabel(" " + LexiconPanel.n.getParent().toString() + ": " + italianSpecificInformation_text);
	  labelIN.setFont(new Font(Mpiro.selectedFont,Font.BOLD,16));
	  labelIN.setForeground(Color.black);
	  labelIN.setPreferredSize(new Dimension(380, 33));
	  // 2
	  KLabel itbaseform = new KLabel(baseForm_text);
	  itbasetext = new JTextField("", 22);
	  // 3
	  JPanel itgender = new JPanel(new GridLayout(2, 1));
	  KLabel itgender1 = new KLabel(grammatical_text);
	  KLabel itgender2 = new KLabel(gender_text);
	  itgender.add(itgender1);
	  itgender.add(itgender2);
	  itmasculine = new JRadioButton(masculine_text, true);
	  itfeminine = new JRadioButton(feminine_text, false);
	  ButtonGroup itbg = new ButtonGroup();
	  itbg.add(itmasculine);
	  itbg.add(itfeminine);
	  JPanel itgenderpanel = new JPanel(new BorderLayout());
	  itgenderpanel.add("West", itmasculine);
	  itgenderpanel.add("Center", itfeminine);
		// 4
		KLabel itcount = new KLabel(countable_text);
		JPanel itcountpanel = new JPanel(new BorderLayout());
		itcountnoun = new JRadioButton(yes_text, true);
		itmassnoun = new JRadioButton(no_text, false);
		ButtonGroup itbg1 = new ButtonGroup();
		itbg1.add(itcountnoun);
		itbg1.add(itmassnoun);
		itcountpanel.add("West", itcountnoun);
		itcountpanel.add("Center", itmassnoun);
		
		//after 4 (advanced spelling options button)
		advancedSpellingOptions = new JCheckBox(advancedSpellingOptions_text, false);
		JPanel advancedspellingoptionspanel = new JPanel(new BorderLayout());
		advancedspellingoptionspanel.add(advancedSpellingOptions);

		// 5
		KLabel itspelling = new KLabel(spelling_text);
		KLabel itplural = new KLabel(plural_text);
		itpluraltext = new JTextField("", 18);
		itcb = new JCheckBox();
		KLabel itcbspace = new KLabel(" ");
		JPanel itcbpanel = new JPanel(new BorderLayout());
		itcbpanel.add("West", itcbspace);
		itcbpanel.add("Center", itcb);
		JPanel itpluralpanel = new JPanel(new BorderLayout());
		KLabel itirregular1 = new KLabel("               " + checkTheBoxToModify_text);
		KLabel itirregular2 = new KLabel("               " + theDefaultSuggestion_text);
		JPanel itirregular = new JPanel(new GridLayout(2, 1));
		itirregular1.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
		itirregular2.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));
		itirregular.add(itirregular1);
		itirregular.add(itirregular2);
		itpluralpanel.add("North", itirregular);
		itpluralpanel.add("West", itplural);
		itpluralpanel.add("Center", itpluraltext);
		itpluralpanel.add("East", itcbpanel);

	  JPanel itp01 = new JPanel(new BorderLayout());
	  JPanel itp02 = new JPanel(new BorderLayout());
	  JPanel itp03 = new JPanel(new BorderLayout());
	  JPanel itp04 = new JPanel(new BorderLayout());
	  JPanel itp011 = new JPanel(new BorderLayout());
	  JPanel itp022 = new JPanel(new BorderLayout());
	  JPanel itp033 = new JPanel(new BorderLayout());
	  itp044 = new JPanel(new BorderLayout());
	  itp01.setPreferredSize(new Dimension(90,10));
	  itp02.setPreferredSize(new Dimension(90,30));
	  itp03.setPreferredSize(new Dimension(90,10));
	  itp04.setPreferredSize(new Dimension(90,10));
	  itp01.add("West", itbaseform);
	  itp02.add("West", itgender);
	  itp03.add("West", itcount);
	  itp04.add("West", itspelling);
	  itp011.add("West", itp01);
	  itp022.add("West", itp02);
	  itp033.add("West", itp03);
	  itp044.add("West", itp04);
	  itp011.add("Center", itbasetext);
	  itp022.add("Center", itgenderpanel);
	  itp033.add("Center", itcountpanel);
	  itp044.add("Center", itpluralpanel);

	  // Put them all (1 - 5) together
	  itcenterpanel = new JPanel(new ColumnLayout());

	  itcenterpanel.add(itp011);
	  itcenterpanel.add(itp022);
	  itcenterpanel.add(itp033);
                itcenterpanel.add(advancedspellingoptionspanel);
	  //itcenterpanel.add(itp044);

	  this.setLayout(new BorderLayout());
	  this.add("North", labelIN);
	  this.add("Center", itcenterpanel);

	  // Formatting works
	  itmasculine.setFont(panelfont);
	  itfeminine.setFont(panelfont);
	  itcountnoun.setFont(panelfont);
	  itmassnoun.setFont(panelfont);
	  itp044.setBorder(new LineBorder(new Color(250,250,250), 1));
	  itp044.setPreferredSize(new Dimension(395,50));

		advancedSpellingOptions.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e) 
			{
				if (advancedSpellingOptions.isSelected() == false) 
				{
					itcenterpanel.add(itp044);
				} 
				else if (advancedSpellingOptions.isSelected() == true) 
				{
					itcenterpanel.remove(itp044);
				}
				itcenterpanel.revalidate();
				itcenterpanel.repaint();
			}
		});


		/** Add the Action Listeners */
		itbasetext.addFocusListener(ItalianNounPanel.this);
		itmasculine.addActionListener(ItalianNounPanel.this);
		itfeminine.addActionListener(ItalianNounPanel.this);
		itcountnoun.addActionListener(ItalianNounPanel.this);
		itmassnoun.addActionListener(ItalianNounPanel.this);
		itpluraltext.addFocusListener(ItalianNounPanel.this);
		itcb.addActionListener(ItalianNounPanel.this);


		//System.out.println(QueryLexiconHashtable.currentValues);
		
		//System.out.println("Current Values: " + QueryHashtable.currentValues);
		showValues(QueryLexiconHashtable.showValues(LexiconPanel.parent.toString(), "Italian"));
		//}

		//System.out.println(QueryLexiconHashtable.currentValues);
		itcenterpanel.revalidate();
		itcenterpanel.repaint();
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
	    if (label.equalsIgnoreCase("itgender"))
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
			//System.out.println("Lexicon label: " + label);
			//item = rb.getText();
			//System.out.println("Lexicon item : " + item);
		}
		// Update the Hashtable
		QueryLexiconHashtable.updateLexiconEntryNoun(node, "Italian", label, item);
		Mpiro.needExportToExprimo = true;		//maria
		
		// Print some info
		//System.out.println("/////// Info START");
		//System.out.println(node + " / " + "Italian" + " / " + label);
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
		
		if (fe.getSource() == itbasetext) 
		{
			printAndUpdate("itbasetext", null, itbasetext, null, null);
		}
		if (fe.getSource() == itpluraltext) 
		{
			printAndUpdate("itpluraltext", null, itpluraltext, null, null);
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == itmasculine) 
		{
			printAndUpdate("itgender", null, null, itmasculine, null);
		}
		if (e.getSource() == itfeminine) 
		{
			printAndUpdate("itgender", null, null, itfeminine, null);
		}
		if (e.getSource() == itcountnoun) 
		{
			printAndUpdate("countable", null, null, itcountnoun, null);
		}
		if (e.getSource() == itmassnoun) 
		{
			printAndUpdate("countable", null, null, itmassnoun, null);
		}
		if (e.getSource() == itcb) 
		{
			printAndUpdate("itcb", itcb, null, null, null);
		}
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
			
			if (keyString.compareTo("itbasetext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				itbasetext.setText(keyValue);
			} 
			else if (keyString.compareTo("itgender") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("Masculine")) 
				{
					itmasculine.setSelected(true);
				}
				else if (keyValue.equalsIgnoreCase("Feminine")) 
				{
					itfeminine.setSelected(true);
				}
			} 
			else if (keyString.compareTo("countable") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("Yes")) 
				{
					itcountnoun.setSelected(true);
				}
				else if (keyValue.equalsIgnoreCase("No")) 
				{
					itmassnoun.setSelected(true);
				}
			} 
			else if (keyString.compareTo("itpluraltext") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				itpluraltext.setText(keyValue);
			} 
			else if (keyString.compareTo("itcb") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if (keyValue.equalsIgnoreCase("true")) 
				{
					itcb.setSelected(true);
				}
				else if (keyValue.equalsIgnoreCase("false")) 
				{
					itcb.setSelected(false);
				}
			} 
			else 
			{
				//System.out.println();
				//System.out.println("----------- Lexicon Alert ---------");
				//System.out.println();
			};// if
		}//for
	}
} //ItalianNounPanel