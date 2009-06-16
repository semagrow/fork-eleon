//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui.lang.gr;

import gr.demokritos.iit.eleon.authoring.CheckCellRenderer;
import gr.demokritos.iit.eleon.authoring.ColumnLayout;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.LexiconDefaultVector;
import gr.demokritos.iit.eleon.authoring.LexiconPanel;
import gr.demokritos.iit.eleon.authoring.LexiconTable;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.ui.ELEONWindow;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KLabel;
import gr.demokritos.iit.eleon.ui.MessageDialog;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.*;


/**
 * <p>Title: GreekVerbPanel</p>
 * <p>Description: The panel for the greek verbs in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
 //Edited and enhanced by Maria Prospathopoulou
public class GreekVerbPanel extends JPanel implements ActionListener, FocusListener 
{
	private boolean DEBUG = true;
	JCheckBox advancedSpellingOptions;
	JPanel advancedspellingoptionspanel;
	KButton updateFields = new KButton(LangResources.getString(Mpiro.selectedLocale, "updateFields_button"));
	JPanel vPanel;
	JPanel spellingPanel;
	
	public static JTextField vbasetext;
	public static JTextField vbasetext2;
	
	static JRadioButton vtrans1;
	static JRadioButton vtrans2;
	public static JTextField infText;
	static JCheckBox cb1;
	static JTextField infText2;
	static JCheckBox cb2;
	public static JTextField apText;
	static JCheckBox cb3;
	
	static LexiconDefaultVector vTableVector;
	static LexiconDefaultVector pTableVector;
	static LexiconTable vTable;
	static LexiconTable pTable;
	
	static DefaultTableModel m_data1;
	static DefaultTableModel m_data2;
	static Vector greek1;
	static Vector greek2;


	public GreekVerbPanel() 
	{
		String greekSpecificInformation_text = LangResources.getString(Mpiro.selectedLocale, "greekSpecificInformation_text");
		String baseForm_text = LangResources.getString(Mpiro.selectedLocale, "baseForm_text");
		String secondPersonOfBaseForm_text = LangResources.getString(Mpiro.selectedLocale, "2ndPersonOfBaseForm_text");
		String transitive_text = LangResources.getString(Mpiro.selectedLocale, "transitive_text");
		String yes_text = LangResources.getString(Mpiro.selectedLocale, "yes_text");
		String no_text = LangResources.getString(Mpiro.selectedLocale, "no_text");
		String advancedSpellingOptions_text = LangResources.getString(Mpiro.selectedLocale, "advancedSpellingOptions_text");
		String spellingOfVerbForms_text = LangResources.getString(Mpiro.selectedLocale, "spellingOfVerbForms_text");
		String checkToChange1_text = LangResources.getString(Mpiro.selectedLocale, "checkToChange1_text");		//maria
		String checkToChange2_text = LangResources.getString(Mpiro.selectedLocale, "checkToChange2_text");		//maria
		String passiveParticiple_text = LangResources.getString(Mpiro.selectedLocale, "passiveParticiple_text");
		String activeInfinitive_text = LangResources.getString(Mpiro.selectedLocale, "activeInfinitive_text");
		String passiveInfinitive_text = LangResources.getString(Mpiro.selectedLocale, "passiveInfinitive_text");
		String activeParticiple_text = LangResources.getString(Mpiro.selectedLocale, "activeParticiple_text");
		String checkTheBoxToModify_text = LangResources.getString(Mpiro.selectedLocale, "checkTheBoxToModify_text");
		String theDefaultSuggestion_text = LangResources.getString(Mpiro.selectedLocale, "theDefaultSuggestion_text");
		
		String tenseAspect_tabletext = LangResources.getString(Mpiro.selectedLocale, "tenseAspect_tabletext");
		String voice_tabletext = LangResources.getString(Mpiro.selectedLocale, "voice_tabletext");
		String number_tabletext = LangResources.getString(Mpiro.selectedLocale, "number_tabletext");
		String person_tabletext = LangResources.getString(Mpiro.selectedLocale, "person_tabletext");
		String verbForm_tabletext = LangResources.getString(Mpiro.selectedLocale, "verbForm_tabletext");
		String change_tabletext = LangResources.getString(Mpiro.selectedLocale, "change_tabletext");		//maria
		String gender_tabletext = LangResources.getString(Mpiro.selectedLocale, "gender_tabletext");
		String participleForm_tabletext = LangResources.getString(Mpiro.selectedLocale, "participleForm_tabletext");

		greek1 = new Vector();
		greek1.addElement(tenseAspect_tabletext);
		greek1.addElement(voice_tabletext);
		greek1.addElement(number_tabletext);
		greek1.addElement(person_tabletext);
		greek1.addElement(verbForm_tabletext);
		greek1.addElement(change_tabletext);		//maria
		
		greek2 = new Vector();
		greek2.addElement(gender_tabletext);
		/////old///////greek2.addElement("Number");
		/////old///////greek2.addElement("Person");
		greek2.addElement(participleForm_tabletext);
		greek2.addElement(change_tabletext);		//maria

		// vectors + models
		LexiconDefaultVector ldf1 = new LexiconDefaultVector("verb-greek-1");
		LexiconDefaultVector ldf2 = new LexiconDefaultVector("verb-greek-2");
		m_data1 = new DefaultTableModel(ldf1, greek1);
		m_data2 = new DefaultTableModel(ldf2, greek2);

		//Create the vTable
		vTable = new LexiconTable(3);
		vTable.setModel(m_data1);
		JScrollPane vScroll = new JScrollPane();
		vScroll.setViewportView(vTable);
		vScroll.setPreferredSize(new Dimension(180,577));
		
		JTextField noneditabletextfield = new JTextField();
		noneditabletextfield.setEditable(false);
		TableCellEditor nonEditableEditor = new DefaultCellEditor(noneditabletextfield);
		
		TableCellEditor checkBoxEditor = new DefaultCellEditor(new JCheckBox());
		TableCellRenderer checkBoxRenderer = new CheckCellRenderer();
		TableCellEditor textFieldEditor = new DefaultCellEditor(new JTextField());
		TableCellRenderer textFieldRenderer = new DefaultTableCellRenderer();
		
		TableColumn column1 = new TableColumn(0, 90, textFieldRenderer, nonEditableEditor);
		TableColumn column2 = new TableColumn(1, 55, textFieldRenderer, nonEditableEditor);
		TableColumn column3 = new TableColumn(2, 60, textFieldRenderer, nonEditableEditor);
		TableColumn column4 = new TableColumn(3, 40, textFieldRenderer, nonEditableEditor);
		TableColumn column5 = new TableColumn(4, 110, textFieldRenderer, textFieldEditor);	//maria
		TableColumn column6 = new TableColumn(5, 60, checkBoxRenderer, checkBoxEditor);		//maria

		//vTable.addColumn(column1);
		//vTable.addColumn(column2);
		//vTable.addColumn(column3);
		//vTable.addColumn(column4);
		vTable.addColumn(column5);
		vTable.addColumn(column6);
		
		//Create the pTable
		pTable = new LexiconTable(4);
		pTable.setModel(m_data2);
		JScrollPane pScroll = new JScrollPane(pTable);
		pScroll.setPreferredSize(new Dimension(180,49));

		TableColumn column7 = new TableColumn(0, 70, textFieldRenderer, nonEditableEditor);
		TableColumn column8 = new TableColumn(1, 110, textFieldRenderer, textFieldEditor);		//maria
		TableColumn column9 = new TableColumn(2, 60, checkBoxRenderer, checkBoxEditor);			//maria
		
		//pTable.addColumn(column7);
		pTable.addColumn(column8);
		pTable.addColumn(column9);

		//Create the panels that hold the JScrollPane pairs for each table
		JPanel verbPanel = new JPanel(new BorderLayout());
		//vPanel.setBorder(new LineBorder(new Color(250, 250, 250), 1));
		verbPanel.add(BorderLayout.WEST, new JScrollPane(QueryLexiconHashtable.createDefaultTable("verb-greek-1")));
		verbPanel.add(BorderLayout.EAST, vScroll);
		
		JPanel participlePanel = new JPanel(new BorderLayout());
		participlePanel.add(BorderLayout.WEST, new JScrollPane(QueryLexiconHashtable.createDefaultTable("verb-greek-2")));
		participlePanel.add(BorderLayout.EAST, pScroll);
		
		// The Greek verb view
		// The panels in order of appearance :
		// 1
		JLabel labelGV = new JLabel(" " + LexiconPanel.n.getParent().toString() + ": " + greekSpecificInformation_text);
		labelGV.setFont(new Font(Mpiro.selectedFont,Font.BOLD,16));
		labelGV.setForeground(Color.black);
		labelGV.setPreferredSize(new Dimension(380, 33));
		// 2
		KLabel vBaseform = new KLabel(baseForm_text);
		vbasetext = new JTextField(16);
		// 3
		KLabel vBaseform2 = new KLabel(secondPersonOfBaseForm_text);
		vbasetext2 = new JTextField(16);
		// 4
		KLabel vTransLabel = new KLabel(transitive_text);
		vtrans1 = new JRadioButton(yes_text, true);
		vtrans2 = new JRadioButton(no_text, false);
		ButtonGroup vbg = new ButtonGroup();
		vbg.add(vtrans1);
		vbg.add(vtrans2);
		JPanel vTransPanel = new JPanel(new BorderLayout());
		vTransPanel.add("West", vtrans1);
		vTransPanel.add("Center", vtrans2);
		vtrans1.setFont(new Font(Mpiro.selectedFont,Font.BOLD,11));
		vtrans2.setFont(new Font(Mpiro.selectedFont,Font.BOLD,11));
		
		//after 4 (advanced spelling options button)
		advancedSpellingOptions = new JCheckBox(advancedSpellingOptions_text, false);
		advancedspellingoptionspanel = new JPanel(new BorderLayout());
		advancedspellingoptionspanel.setPreferredSize(new Dimension(380, 20));
		advancedspellingoptionspanel.add(BorderLayout.WEST, advancedSpellingOptions);

		// Putting all the panels 1 - 4 together
		vPanel = new JPanel(new ColumnLayout());
		
		JPanel pv1 = new JPanel(new BorderLayout());
		JPanel pv2 = new JPanel(new BorderLayout());
		JPanel pv3 = new JPanel(new BorderLayout());
		
		JPanel pv11 = new JPanel(new BorderLayout());
		JPanel pv22 = new JPanel(new BorderLayout());
		JPanel pv33 = new JPanel(new BorderLayout());
		
		pv1.setPreferredSize(new Dimension(170,10));
		pv2.setPreferredSize(new Dimension(170,10));
		pv3.setPreferredSize(new Dimension(170,10));
		
		pv1.add("West", vBaseform);
		pv2.add("West", vBaseform2);
		pv3.add("West", vTransLabel);
		
		pv11.add("West", pv1);
		pv22.add("West", pv2);
		pv33.add("West", pv3);
		
		pv11.add("Center", vbasetext);
		pv22.add("Center", vbasetext2);
		pv33.add("Center", vTransPanel);

		vPanel.add(labelGV);
		vPanel.add(pv11);
		vPanel.add(pv22);
		vPanel.add(pv33);
		vPanel.add(advancedspellingoptionspanel);
		
		//vPanel.setPreferredSize(new Dimension(370, 200));
		
		// The table labels
		
		KLabel changeLabel1;							//maria
		KLabel changeLabel2;							//maria
		
		if(Mpiro.selectedLocale == Mpiro.enLocale)		//maria
		{										
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
		KLabel pTableLabel = new KLabel(passiveParticiple_text);
		
		JPanel vTableLabelPanel = new JPanel(new BorderLayout());
		JPanel pTableLabelPanel = new JPanel(new BorderLayout());
        
		vTableLabelPanel.add("East", changePanel);		//maria
		
		vTableLabelPanel.add("West", vTableLabel);
		//vTableLabelAndSaveTablesPanel.add("Center", saveTables1);
		pTableLabelPanel.add("West", pTableLabel);
		//pTableLabelAndSaveTablesPanel.add("Center", saveTables2);
		

		// The "infinitive" panel
		JPanel infPanel = new JPanel(new BorderLayout());
		JPanel infPanel2 = new JPanel(new BorderLayout());
		KLabel infLabel = new KLabel(activeInfinitive_text);
		KLabel infLabel2 = new KLabel(passiveInfinitive_text);
		
		infText = new JTextField("", 12);
		infText2 = new JTextField("", 12);
		KLabel empty1 = new KLabel(" ");	 /////////
		cb1 = new JCheckBox();
		cb2 = new JCheckBox();
		JPanel all3 = new JPanel(new BorderLayout());
		all3.add("West", infText);
		//all3.add("Center", empty1);
		all3.add("East", cb1);				 /////////
		JPanel a114 = new JPanel(new BorderLayout());
		a114.add("West", infText2);
		//a114.add("Center", empty1);
		a114.add("East", cb2);				 /////////
		
		JPanel infLeft = new JPanel(new BorderLayout());
		infLeft.add("West", infLabel);
		JPanel infLeft2 = new JPanel(new BorderLayout());
		infLeft2.add("West", infLabel2);
    KLabel grirregular1 = new KLabel		   // The instructions label-set to modify the default suggestion
           ("                                       " +
           	"                                      " );						//maria
    KLabel grirregular2 = new KLabel
           ("                                       " +
            "                                      " );					//maria
		JPanel grirregular = new JPanel(new GridLayout(2, 1));
		//grirregular1.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));		//maria
		//grirregular2.setFont(new Font(Mpiro.selectedFont,Font.PLAIN,10));		//maria
		grirregular.add(grirregular1);
		grirregular.add(grirregular2);			  // end of label-set
		infLeft.setPreferredSize(new Dimension(230, 10));
		infLeft2.setPreferredSize(new Dimension(230, 10));
		infPanel.add("West", infLeft);
		infPanel.add("Center", all3);
		infPanel.add("North", grirregular);
		infPanel2.add("West", infLeft2);
		infPanel2.add("Center", a114);


		// The "active participle" panel
		JPanel apPanel = new JPanel(new BorderLayout());
		KLabel apLabel = new KLabel(activeParticiple_text);
		apText = new JTextField("", 12);
		KLabel empty2 = new KLabel(" ");	 /////////
		cb3 = new JCheckBox();
		JPanel all31 = new JPanel(new BorderLayout());
		all31.add("West", apText);
		//all31.add("Center", empty2);
		all31.add("East", cb3);				 /////////
		JPanel apLeft = new JPanel(new BorderLayout());
		apLeft.add("West", apLabel);
		apLeft.setPreferredSize(new Dimension(230, 10));
		apPanel.add("West", apLeft);
		apPanel.add("Center", all31);

		JPanel spacePanel = new JPanel(new BorderLayout());
		spacePanel.setPreferredSize(new Dimension(10, 5));
		// Add everything to the big panel
		spellingPanel = new JPanel(new ColumnLayout(0,0,0,0));
		//spellingPanel.add(vTableLabel);
		spellingPanel.add(vTableLabelPanel);
		//spellingPanel.add(vScroll);
		spellingPanel.add(verbPanel);
		spellingPanel.add(infPanel);
		spellingPanel.add(infPanel2);
		spellingPanel.add(apPanel);
		//spellingPanel.add(pTableLabel);
		spellingPanel.add(spacePanel);
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
					advancedspellingoptionspanel.add(BorderLayout.EAST, updateFields);
					autocompleteSpellingFields();
				} 
				else if (advancedSpellingOptions.isSelected() == true) 
				{
					vPanel.remove(spellingPanel);
					advancedspellingoptionspanel.remove(updateFields);
				}
				vPanel.revalidate();
				vPanel.repaint();
			}
		});

		/** Add the Action Listeners */
		updateFields.addActionListener(GreekVerbPanel.this);
		
		vbasetext.addFocusListener(GreekVerbPanel.this);
		vbasetext2.addFocusListener(GreekVerbPanel.this);
		infText.addFocusListener(GreekVerbPanel.this);
		infText2.addFocusListener(GreekVerbPanel.this);
		apText.addFocusListener(GreekVerbPanel.this);
		
		vtrans1.addActionListener(GreekVerbPanel.this);
		vtrans2.addActionListener(GreekVerbPanel.this);
		cb1.addActionListener(GreekVerbPanel.this);
		cb2.addActionListener(GreekVerbPanel.this);
		cb3.addActionListener(GreekVerbPanel.this);

		//ltfl_pTable = new LexiconTablesFocusListener();
		//ltfl_vTable = new LexiconTablesFocusListener();
		//pTable.addFocusListener(ltfl_pTable);
		//vTable.addFocusListener(ltfl_vTable);
		
		//ltml_pTable = new LexiconTablesMouseListener();
		//ltml_vTable = new LexiconTablesMouseListener();
		//pTable.addMouseListener(ltml_pTable);
		//vTable.addMouseListener(ltml_vTable);

		//System.out.println(QueryLexiconHashtable.currentValues);
		
		//System.out.println("Current Values: " + QueryHashtable.currentValues);
		showValues(QueryLexiconHashtable.showValues(LexiconPanel.parent.toString(), "Greek"));
		
		//System.out.println(QueryLexiconHashtable.currentValues);
		vPanel.revalidate();
		vPanel.repaint();
	}	  // constructor


	// A general method added in the actionPerformed method
	public static void printAndUpdate(String s, JCheckBox cb, JTextField tf, JRadioButton rb, Vector ldv) 
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
			// Update the Hashtable
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Greek", label, item);
		}
		if (tf != null) 
		{
			item = tf.getText();
			// Update the Hashtable
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Greek", label, item);
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
			
			// Update the Hashtable
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Greek", label, item);
			Mpiro.needExportToExprimo = true;
		}
		if (ldv != null) 
		{
			//System.out.println("Lexicon label: " + label);
			itemVector = ldv;
			//System.out.println("Lexicon item : " + itemVector);
			// Update the Hashtable
			QueryLexiconHashtable.updateLexiconEntryVerb(node, "Greek", label, itemVector);
			Mpiro.needExportToExprimo = true;
		}

		// Print some info
		//System.out.println("/////// Info START");
		//System.out.println(node + " / " + "Greek" + " / " + label);
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
		
		if (fe.getSource() == GreekVerbPanel.vbasetext2) 
		{
			printAndUpdate("vbasetext2", null, vbasetext2, null, null);
		}
		
		if (fe.getSource() == GreekVerbPanel.infText) 
		{
			printAndUpdate("infText", null, infText, null, null);
		}
		
		if (fe.getSource() == GreekVerbPanel.infText2) 
		{
			printAndUpdate("infText2", null, infText2, null, null);
		}
		
		if (fe.getSource() == GreekVerbPanel.apText) 
		{
			printAndUpdate("apText", null, apText, null, null);
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
		
		if (e.getSource() == updateFields) 
		{		
			autocompleteSpellingFields();		
		}
	} // actionPerformed


	public static void showValues(Hashtable currentValues) 
	{
		Vector dataV = (Vector)currentValues.get("vTable");
		//System.out.println("dataV:  " + dataV);
		Vector dataP = (Vector)currentValues.get("pTable");
		//System.out.println("dataP:  " + dataP);
		
		m_data1.setDataVector(dataV, greek1);
		m_data2.setDataVector(dataP, greek2);
		//..............
		
		for (Enumeration k = currentValues.keys(), e = currentValues.elements(); k.hasMoreElements(); )
		{
			String keyString = k.nextElement().toString();
			String keyValue = e.nextElement().toString();
			
			if (keyString.compareTo("vbasetext") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				vbasetext.setText(keyValue);
			}
			
			else if (keyString.compareTo("vbasetext2") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				vbasetext2.setText(keyValue);
			} 
           
			else if (keyString.compareTo("transitive") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				if ( keyValue.equalsIgnoreCase("Yes")) {vtrans1.setSelected(true);}
				else if ( keyValue.equalsIgnoreCase("No")) {vtrans2.setSelected(true);}
			}
			
			else if (keyString.compareTo("infText") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				infText.setText(keyValue);
			} 
			
			else if (keyString.compareTo("cb1") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				if (keyValue.equalsIgnoreCase("true")) {cb1.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb1.setSelected(false);}
			} 
           
			else if (keyString.compareTo("infText2") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				infText2.setText(keyValue);
			} 
			
			else if (keyString.compareTo("cb2") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				if (keyValue.equalsIgnoreCase("true")) {cb2.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb2.setSelected(false);}
			} 
			
			else if (keyString.compareTo("apText") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				apText.setText(keyValue);
			} 
           
			else if (keyString.compareTo("cb3") == 0) 
			{
				//System.out.println("found value for:   \"" + keyString + "\"   which is: \"" + keyValue + "\"");
				if (keyValue.equalsIgnoreCase("true")) {cb3.setSelected(true);}
				else if (keyValue.equalsIgnoreCase("false")) {cb3.setSelected(false);}
			} 
			
			else 
			{
				//System.out.println();
				//System.out.println("----------- Lexicon Alert ---------");
				//System.out.println();
			}; // if
		}//for
	}


	public static void autocompleteSpellingFields() 
	{
		if ( (vbasetext.getText().length() != 0) && (vbasetext2.getText().length() != 0) )
		{
			String baseformString = vbasetext.getText();
			try
			{
				String spellingString = GreekMorphology.getSpellingStringVerb(vbasetext.getText());
				
				Vector table1 = m_data1.getDataVector();
				Enumeration table1Enum = table1.elements();
				while (table1Enum.hasMoreElements())
				{
					Vector row = (Vector)table1Enum.nextElement();
					Vector extraFeatures = new Vector();
					if (row.elementAt(5).toString().equalsIgnoreCase("false"))
					{
						if (row.elementAt(0).toString().equalsIgnoreCase("Present progressive"))
						{
							extraFeatures.addElement("present-verb");
						}
						else if (row.elementAt(0).toString().equalsIgnoreCase("Past progressive"))
						{
							extraFeatures.addElement("past-verb");
							extraFeatures.addElement("progressive-verb");
						}
						else if (row.elementAt(0).toString().equalsIgnoreCase("Past simple"))
						{
							extraFeatures.addElement("past-verb");
							extraFeatures.addElement("simple-verb");
						}
						if (row.elementAt(1).toString().equalsIgnoreCase("Active"))
						{
							extraFeatures.addElement("active-verb");
						}
						else if (row.elementAt(1).toString().equalsIgnoreCase("Passive"))
						{
							extraFeatures.addElement("passive-verb");
						}
						if (row.elementAt(2).toString().equalsIgnoreCase("Singular"))
						{
							extraFeatures.addElement("singular-verb");
						}
						else if (row.elementAt(2).toString().equalsIgnoreCase("Plural"))
						{
							extraFeatures.addElement("plural-verb");
						}
						if (row.elementAt(3).toString().equalsIgnoreCase("1st"))
						{
							extraFeatures.addElement("firstperson");
						}
						else if (row.elementAt(3).toString().equalsIgnoreCase("2nd"))
						{
							extraFeatures.addElement("secondperson");
						}
						else if (row.elementAt(3).toString().equalsIgnoreCase("3rd"))
						{
							extraFeatures.addElement("thirdperson");
						}
            Vector featuresVector = GreekMorphology.getFeaturesVectorVerb(LexiconPanel.parent.toString(), "Greek", extraFeatures);
            row.setElementAt(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)), 4);
					}
				}//while
				Vector table2 = m_data2.getDataVector();
				Enumeration table2Enum = table2.elements();
				while (table2Enum.hasMoreElements())
				{
					Vector row = (Vector)table2Enum.nextElement();
					Vector extraFeatures = new Vector();
					if (row.elementAt(2).toString().equalsIgnoreCase("false"))
					{
						extraFeatures.addElement("participle-verb");
						extraFeatures.addElement("passive-verb");
						
						if (row.elementAt(0).toString().equalsIgnoreCase("Masculine"))
						{
							extraFeatures.addElement("masculine-verb");
						}
						else if (row.elementAt(0).toString().equalsIgnoreCase("Feminine"))
						{
							extraFeatures.addElement("feminine-verb");
						}
						else if (row.elementAt(0).toString().equalsIgnoreCase("Neuter"))
						{
							extraFeatures.addElement("neuter-verb");
						}
						Vector featuresVector = GreekMorphology.getFeaturesVectorVerb(LexiconPanel.parent.toString(), "Greek", extraFeatures);
						row.setElementAt(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)), 1);
					}
				}

				if (!cb1.isSelected())
				{
					Vector extraFeatures = new Vector();
					extraFeatures.addElement("infinitive");
					extraFeatures.addElement("active-verb");
					Vector featuresVector = GreekMorphology.getFeaturesVectorVerb(LexiconPanel.parent.toString(), "Greek", extraFeatures);
					infText.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}

				if (!cb2.isSelected())
				{
					Vector extraFeatures = new Vector();
					extraFeatures.addElement("infinitive");
					extraFeatures.addElement("passive-verb");
					Vector featuresVector = GreekMorphology.getFeaturesVectorVerb(LexiconPanel.parent.toString(), "Greek", extraFeatures);
					infText2.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}

				if (!cb3.isSelected())
				{
					Vector extraFeatures = new Vector();
					extraFeatures.addElement("participle-verb");
					extraFeatures.addElement("active-verb");
					Vector featuresVector = GreekMorphology.getFeaturesVectorVerb(LexiconPanel.parent.toString(), "Greek", extraFeatures);
					apText.setText(GreekAccentUtils.addAccentToWord(GreekMorphology.getInflectedForm(baseformString, featuresVector, spellingString)));
				}
			}
			catch (Exception ex)
			{
				new MessageDialog(Mpiro.win.tabbedPane, MessageDialog.attentionAnErrorHasOccuredWhileAutocompleting_ETC_dialog
												+ "\"" + LexiconPanel.parent.toString() + "\"");
				//ex.printStackTrace();
			}
		}//if

    else
    {
			Vector table1 = m_data1.getDataVector();
			Enumeration table1Enum2 = table1.elements();
			while (table1Enum2.hasMoreElements())
			{
				Vector row = (Vector)table1Enum2.nextElement();
				if (row.elementAt(5).toString().equalsIgnoreCase("false"))
				{
					row.setElementAt("", 4);
				}
			}

			Vector table2 = m_data2.getDataVector();
			Enumeration table2Enum2 = table2.elements();
			while (table2Enum2.hasMoreElements())
			{
				Vector row = (Vector)table2Enum2.nextElement();
				if (row.elementAt(2).toString().equalsIgnoreCase("false"))
				{
					row.setElementAt("", 1);
				}
			}
			if (!cb1.isSelected())
			{
				infText.setText("");
			}
			if (!cb2.isSelected())
			{
				infText2.setText("");
			}
			if (!cb3.isSelected())
			{
				apText.setText("");
			}
		}
    vTable.repaint();
    pTable.repaint();
	}// autocompleteSpellingFields

	/*public static void updateTables() 
	  {
	      printAndUpdate("vTable", null, null, null, m_data1.getDataVector());
	      printAndUpdate("pTable", null, null, null, m_data2.getDataVector());
	  }*/

}// GreekVerbPanel
