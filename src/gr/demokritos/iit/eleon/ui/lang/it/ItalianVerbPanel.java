//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui.lang.it;

import gr.demokritos.iit.eleon.authoring.CheckCellRenderer;
import gr.demokritos.iit.eleon.authoring.ColumnLayout;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.LexiconDefaultVector;
import gr.demokritos.iit.eleon.authoring.LexiconPanel;
import gr.demokritos.iit.eleon.authoring.LexiconTable;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.ui.KLabel;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import javax.swing.tree.*;
import javax.swing.JTree;

/**
 * <p>Title: ItalianVerbPanel</p>
 * <p>Description: The panel for the italian verbs in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class ItalianVerbPanel extends JPanel implements ActionListener, FocusListener 
{
	private boolean DEBUG = true;
	JCheckBox advancedSpellingOptions;
	JPanel vPanel;
	JPanel spellingPanel;
	
	static JTextField vbasetext;
	static JRadioButton vtrans1;
	static JRadioButton vtrans2;
	static LexiconDefaultVector vTableVector;
	static LexiconDefaultVector pTableVector;
	static LexiconTable vTable;
	static LexiconTable pTable;
	
	static DefaultTableModel m_data1;
	static DefaultTableModel m_data2;
	static Vector italian1;
	static Vector italian2;


	public ItalianVerbPanel() 
	{
	  String italianSpecificInformation_text = LangResources.getString(Mpiro.selectedLocale, "italianSpecificInformation_text");
	  String baseForm_text = LangResources.getString(Mpiro.selectedLocale, "baseForm_text");
	  String transitive_text = LangResources.getString(Mpiro.selectedLocale, "transitive_text");
	  String yes_text = LangResources.getString(Mpiro.selectedLocale, "yes_text");
	  String no_text = LangResources.getString(Mpiro.selectedLocale, "no_text");
	  String advancedSpellingOptions_text = LangResources.getString(Mpiro.selectedLocale, "advancedSpellingOptions_text");
	  String spellingOfVerbForms_text = LangResources.getString(Mpiro.selectedLocale, "spellingOfVerbForms_text");
	  String checkToChange1_text = LangResources.getString(Mpiro.selectedLocale, "checkToChange1_text");		//maria
	  String checkToChange2_text = LangResources.getString(Mpiro.selectedLocale, "checkToChange2_text");		//maria
	  String pastParticiple_text = LangResources.getString(Mpiro.selectedLocale, "pastParticiple_text");
	
	  String tense_tabletext = LangResources.getString(Mpiro.selectedLocale, "tense_tabletext");
	  String voice_tabletext = LangResources.getString(Mpiro.selectedLocale, "voice_tabletext");
	  String number_tabletext = LangResources.getString(Mpiro.selectedLocale, "number_tabletext");
	  String person_tabletext = LangResources.getString(Mpiro.selectedLocale, "person_tabletext");
	  String verbForm_tabletext = LangResources.getString(Mpiro.selectedLocale, "verbForm_tabletext");
	  String change_tabletext = LangResources.getString(Mpiro.selectedLocale, "change_tabletext");		//maria
	  String gender_tabletext = LangResources.getString(Mpiro.selectedLocale, "gender_tabletext");
	  String participleForm_tabletext = LangResources.getString(Mpiro.selectedLocale, "participleForm_tabletext");
	
	  italian1 = new Vector();
	  italian1.addElement(tense_tabletext);
	  italian1.addElement(number_tabletext);
	  italian1.addElement(person_tabletext);
	  italian1.addElement(verbForm_tabletext);
	  italian1.addElement(change_tabletext);		//maria

	  italian2 = new Vector();
	  italian2.addElement(gender_tabletext);
	  italian2.addElement(number_tabletext);
	  italian2.addElement(participleForm_tabletext);
	  italian2.addElement(change_tabletext);		//maria
	
	  // vectors + models
	  LexiconDefaultVector ldf1 = new LexiconDefaultVector("verb-italian-1");
	  LexiconDefaultVector ldf2 = new LexiconDefaultVector("verb-italian-2");
	  m_data1 = new DefaultTableModel(ldf1, italian1);
	  m_data2 = new DefaultTableModel(ldf2, italian2);
	
	
	  //Create the vTable
	  vTable = new LexiconTable(1);
	  vTable.setModel(m_data1);
	  JScrollPane vScroll = new JScrollPane();
	  vScroll.setViewportView(vTable);
	  vScroll.setPreferredSize(new Dimension(180,288));

    //////////////////////////
    JTextField noneditabletextfield = new JTextField();
    noneditabletextfield.setEditable(false);
    TableCellEditor nonEditableEditor = new DefaultCellEditor(noneditabletextfield);

    TableCellEditor checkBoxEditor = new DefaultCellEditor(new JCheckBox());
    TableCellRenderer checkBoxRenderer = new CheckCellRenderer();
    TableCellEditor textFieldEditor = new DefaultCellEditor(new JTextField());
    TableCellRenderer textFieldRenderer = new DefaultTableCellRenderer();

    TableColumn column1 = new TableColumn(0, 90, textFieldRenderer, nonEditableEditor);
    TableColumn column2 = new TableColumn(1, 50, textFieldRenderer, nonEditableEditor);
    TableColumn column3 = new TableColumn(2, 40, textFieldRenderer, nonEditableEditor);
    TableColumn column4 = new TableColumn(3, 90, textFieldRenderer, textFieldEditor);	//maria
    TableColumn column5 = new TableColumn(4, 60, checkBoxRenderer, checkBoxEditor);		//maria

    //vTable.addColumn(column1);
    //vTable.addColumn(column2);
    //vTable.addColumn(column3);
    vTable.addColumn(column4);
    vTable.addColumn(column5);


    //Create the pTable
    LexiconTable pTable = new LexiconTable(2);
    pTable.setModel(m_data2);
    JScrollPane pScroll = new JScrollPane(pTable);
    pScroll.setPreferredSize(new Dimension(180,65));

	  TableColumn column6 = new TableColumn(0, 60, textFieldRenderer, nonEditableEditor);
	  TableColumn column7 = new TableColumn(1, 60, textFieldRenderer, nonEditableEditor);
	  TableColumn column8 = new TableColumn(2, 110, textFieldRenderer, textFieldEditor);	//maria
	  TableColumn column9 = new TableColumn(3, 60, checkBoxRenderer, checkBoxEditor);		//maria
	
	  //pTable.addColumn(column6);
	  //pTable.addColumn(column7);
	  pTable.addColumn(column8);
	  pTable.addColumn(column9);
	
	
	  //Create the panels that hold the JScrollPane pairs for each table
	  JPanel verbPanel = new JPanel(new BorderLayout());
	  //vPanel.setBorder(new LineBorder(new Color(250, 250, 250), 1));
	  verbPanel.add(BorderLayout.WEST, new JScrollPane(QueryLexiconHashtable.createDefaultTable("verb-italian-1")));
	  verbPanel.add(BorderLayout.EAST, vScroll);

    JPanel participlePanel = new JPanel(new BorderLayout());
    participlePanel.add(BorderLayout.WEST, new JScrollPane(QueryLexiconHashtable.createDefaultTable("verb-italian-2")));
    participlePanel.add(BorderLayout.EAST, pScroll);


	  // The Italian verb view
	  // The panels in order of appearance :
	  // 1
	  JLabel labelIV = new JLabel(" " + LexiconPanel.n.getParent().toString() + ": " + italianSpecificInformation_text);
	  labelIV.setFont(new Font(Mpiro.selectedFont,Font.BOLD,16));
	  labelIV.setForeground(Color.black);
	  labelIV.setPreferredSize(new Dimension(380, 33));
	  // 2
	  KLabel vBaseform = new KLabel(baseForm_text);
	  vbasetext = new JTextField("", 24);
	  // 3
    KLabel vTransLabel = new KLabel(transitive_text);
    vtrans1 = new JRadioButton(yes_text, true);
    vtrans2 = new JRadioButton(no_text, false);
    ButtonGroup vbg = new ButtonGroup();
    vbg.add(vtrans1);
    vbg.add(vtrans2);
    JPanel vTransPanel = new JPanel(new BorderLayout());
    // Formatting works
    vTransPanel.add("West", vtrans1);
    vTransPanel.add("Center", vtrans2);
    vtrans1.setFont(new Font(Mpiro.selectedFont,Font.BOLD,11));
    vtrans2.setFont(new Font(Mpiro.selectedFont,Font.BOLD,11));

    //after 3 (advanced spelling options button)
    advancedSpellingOptions = new JCheckBox(advancedSpellingOptions_text, false);
    JPanel advancedspellingoptionspanel = new JPanel(new BorderLayout());
    //advancedspellingoptionspanel.setPreferredSize(new Dimension(250, 20));
    advancedspellingoptionspanel.add(advancedSpellingOptions);

    // Putting all the panels 1 - 3 together
    vPanel = new JPanel(new ColumnLayout());

    JPanel pv1 = new JPanel(new BorderLayout());
    JPanel pv2 = new JPanel(new BorderLayout());

    JPanel pv11 = new JPanel(new BorderLayout());
    JPanel pv22 = new JPanel(new BorderLayout());

    pv1.setPreferredSize(new Dimension(90,10));
    pv2.setPreferredSize(new Dimension(90,10));

    pv1.add("West", vBaseform);
    pv2.add("West", vTransLabel);

    pv11.add("West", pv1);
    pv22.add("West", pv2);

    pv11.add("Center", vbasetext);
    pv22.add("Center", vTransPanel);

    vPanel.add(labelIV);
    vPanel.add(pv11);
    vPanel.add(pv22);
    vPanel.add(advancedspellingoptionspanel);

    //vPanel.setPreferredSize(new Dimension(370,200));
    KLabel changeLabel1;							//maria
    KLabel changeLabel2;							//maria
        
    if(Mpiro.selectedLocale == Mpiro.enLocale)		//maria
    {												//maria
	  	changeLabel1 = new KLabel("              									"+					//maria
	  														"              									"+					//maria
							  								"              									"+					//maria
							  								"              									"+					//maria
	  														"									"+checkToChange1_text);		//maria
	  	changeLabel2 = new KLabel("              									"+					//maria
							  								"              									"+					//maria
							  								"              									"+					//maria
							  								"              									"+					//maria
	  														"									"+checkToChange2_text);		//maria
		}																									//maria
    else																								//maria
    {																									//maria
    	changeLabel1 = new KLabel("              									"+					//maria
						    								"              									"+					//maria
						    								"              									"+
						    								"									"+checkToChange1_text);		//maria
    	changeLabel2 = new KLabel("              									"+					//maria
						    								"              									"+					//maria
						    								"              									"+
						    								"									"+checkToChange2_text);		//maria
		}																									//maria
        
    changeLabel1.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));	//maria
    changeLabel2.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));	//maria
    JPanel changePanel = new JPanel(new GridLayout(2, 1));				//maria
    changePanel.add(changeLabel1);										//maria
    changePanel.add(changeLabel2);										//maria

    KLabel vTableLabel = new KLabel(spellingOfVerbForms_text);
    KLabel pTableLabel = new KLabel(pastParticiple_text);

    JPanel vTableLabelPanel = new JPanel(new BorderLayout());
    JPanel pTableLabelPanel = new JPanel(new BorderLayout());
    
    vTableLabelPanel.add("East", changePanel);		//maria

    vTableLabelPanel.add("West", vTableLabel);
    pTableLabelPanel.add("West", pTableLabel);

    spellingPanel = new JPanel(new ColumnLayout(0,0,0,0));
    spellingPanel.add(vPanel);
    //spellingPanel.add(vTableLabel);
    spellingPanel.add(vTableLabelPanel);
    //spellingPanel.add(vScroll);
    spellingPanel.add(verbPanel);
    //spellingPanel.add(pTableLabel);
    spellingPanel.add(pTableLabelPanel);
    //spellingPanel.add(pScroll);
    spellingPanel.add(participlePanel);
    this.setLayout(new ColumnLayout(0,0,0,0));
    this.add(vPanel);

		advancedSpellingOptions.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e) 
			{
				if (advancedSpellingOptions.isSelected() == false) 
				{
					vPanel.add(spellingPanel);
				} 
				else if (advancedSpellingOptions.isSelected() == true) 
				{
					vPanel.remove(spellingPanel);
				}
				vPanel.revalidate();
				vPanel.repaint();
			}
		});


		/** Add the Action Listeners */
		vbasetext.addFocusListener(ItalianVerbPanel.this);
		vtrans1.addActionListener(ItalianVerbPanel.this);
		vtrans2.addActionListener(ItalianVerbPanel.this);

		showValues(QueryLexiconHashtable.showValues(LexiconPanel.parent.toString(), "Italian"));
		
		vPanel.revalidate();
		vPanel.repaint();
		//panelAdvanced.repaint();
	}	  // constructor


	// A general method added in the actionPerformed method
	public void printAndUpdate(String s, JCheckBox cb, JTextField tf, JRadioButton rb, Vector ldv) 
	{
		//currentRow = DataBaseTable.dbTable.getSelectedRow();
		String node = LexiconPanel.parent.toString();
		//System.out.println("my parent : " + node);
		
		String label = s;
		String item = "";
		Vector itemVector = null;
		
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
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Italian", label, item);
			Mpiro.needExportToExprimo = true;
		}
		if (tf != null) 
		{
			item = tf.getText();
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Italian", label, item);
			Mpiro.needExportToExprimo = true;
		}
		if (rb != null) 
		{
			if (label.equalsIgnoreCase("transitive"))
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
			//item = rb.getText();
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Italian", label, item);
		 	Mpiro.needExportToExprimo = true;
		}
		if (ldv != null) 
		{
			//System.out.println("Lexicon label: " + label);
			itemVector = ldv;
			//System.out.println("Lexicon item : " + itemVector);
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Italian", label, itemVector);
			Mpiro.needExportToExprimo = true;
		}

		// Update the Hashtable
		//QueryLexiconHashtable.updateLexiconEntry(node, "Italian", label, item);
		
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
		
		if (fe.getSource() == vbasetext) 
		{
			printAndUpdate("vbasetext", null, vbasetext, null, null);
		}
  }

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == vtrans1) 
		{
			printAndUpdate("transitive", null, null, vtrans1, null);
		}

    if (e.getSource() == vtrans2) 
    {
			printAndUpdate("transitive", null, null, vtrans2, null);
    }
		/*
		if (e.getSource() == saveTables1 || e.getSource() == saveTables2) 
		{
			printAndUpdate("vTable", null, null, null, m_data1.getDataVector());
			printAndUpdate("pTable", null, null, null, m_data2.getDataVector());
		}
		*/
	} // actionPerformed


	public static void showValues(Hashtable currentValues) 
	{
		//System.out.println("//////////////////");
		//System.out.println(currentValues);
		//System.out.println("//////////////////");

		//.............
		Vector dataV = (Vector)currentValues.get("vTable");
		//System.out.println("dataV:  " + dataV);
		Vector dataP = (Vector)currentValues.get("pTable");
		//System.out.println("dataP:  " + dataP);
		
		m_data1.setDataVector(dataV, italian1);
		m_data2.setDataVector(dataP, italian2);
		//..............
		
		for (Enumeration k = currentValues.keys(), e = currentValues.elements(); k.hasMoreElements(); ) 
		{
			String keyString = k.nextElement().toString();
			String keyValue = e.nextElement().toString();
			
			if (keyString.compareTo("vbasetext") == 0) 
			{ // if
	      //System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
	      //System.out.println("focus to:   " + keyString);
	      vbasetext.setText(keyValue);
			} 
			else if (keyString.compareTo("transitive") == 0) 
			{
				//System.out.println("!!!!!!!!!!!!!!!!   " + keyValue);
				//System.out.println("focus to:   " + keyString);
				if ( keyValue.equalsIgnoreCase("Yes")) 
				{
					vtrans1.setSelected(true);
				}
				else if ( keyValue.equalsIgnoreCase("No")) 
				{
					vtrans2.setSelected(true);
				}
			} 
			else 
			{
				//System.out.println();
				//System.out.println("----------- Lexicon Alert ---------");
				//System.out.println();
			}; // if
		}
	}
}//ItalianVerbPanel