//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui.lang.it;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.DataBaseTable;
import gr.demokritos.iit.eleon.authoring.DataBaseTableListener;
import gr.demokritos.iit.eleon.authoring.LangCombo;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.LexiconPanel;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.TreePreviews;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KComboBox;
import gr.demokritos.iit.eleon.ui.KLabel;
import gr.demokritos.iit.eleon.ui.KRadioButton;
import gr.demokritos.iit.eleon.ui.Template;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

/**
 * <p>Title: ItalianMicroPanel</p>
 * <p>Description: The panel for Italian microplan editing</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos, Kostas Stamatakis
 * @version 1.0
 */
//Edited and enhanced by Maria Prospathopoulou and Theofilos Nickolaou 
public class ItalianMicroPanel extends JPanel implements ActionListener, FocusListener 
{
	static Font panelFont= new Font(Mpiro.selectedFont,Font.BOLD,11);
	static Dimension leftMargin = new Dimension(150,10);
	static JCheckBox clause;
	static JCheckBox advancedOptions;
	static JCheckBox template;
	static JCheckBox noMicroPlanning;
	
	static KLabel labelIT;
	static KButton getGreek;
	static KButton getEnglish;
	//static String label;
	
	static KComboBox verbID;
	static KComboBox prepositionID;
	static KComboBox expSubjectID;
	static KComboBox expObjectID;
	static KComboBox adverbID;
	static JTextField preField;
	static JTextField postField;
	static KComboBox caseExpSubjectID;
	static KComboBox caseExpObjectID;
	
	static KRadioButton active;
	static KRadioButton passive;

	static KRadioButton pastTense;
	static KRadioButton presentTense;
	static KRadioButton futureTense;
	
	static KRadioButton simple;
	static KRadioButton progressive;
	
	static KRadioButton revTrue;
	static KRadioButton revFalse;
	
	static KRadioButton aggTrue;
	static KRadioButton aggFalse;
	
	static KRadioButton indicative;
	static KRadioButton imperative;
	static KRadioButton subjunctive;
	static KRadioButton nonfinite;
	
	static JPanel centerPanel;
	static JPanel panelMain;
	static JPanel panelAdvanced;
	
	static int currentRow;

  JPanel linePanel[] = null;

  static Template template2;

  private static String microplanNumber;


	/**
	 * Constructor
	 * @param fieldName The name of the field that the microplan refers to
	 * @param number The number of the microplan that is being edited
	 */
	public ItalianMicroPanel(String fieldName, String number) 
	{
	  microplanNumber = number;
	  template2 = new Template(2, fieldName, microplanNumber);
	  currentRow = DataBaseTableListener.rowNo;
	
		/** Make 15 thin empty panels to use them as lines */
		linePanel = new JPanel[15] ;
		for (int i = 0; i < 15; i++) 
		{
			linePanel[i] = new JPanel();
			linePanel[i].setBorder(new LineBorder(new Color(250,250,250), 1));
			//linePanel[i].setBorder(new BevelBorder(BevelBorder.LOWERED));
			linePanel[i].setPreferredSize(new Dimension(370,3));
		}

    /** The panels etc in order of appearance */
    // 1
    JPanel labelPanel = new JPanel(new GridBagLayout());
    //labelPanel.setPreferredSize(new Dimension(390, 20));  //theofilos
    GridBagConstraints labelCon = new GridBagConstraints();
    //labelCon.insets = new Insets(-3,10,0,0);  //theofilos
    //labelCon.anchor = GridBagConstraints.WEST;  //theofilos
    //labelCon.weightx = 1.0; labelCon.weighty = 0.0;  //theofilos
    //labelCon.fill = GridBagConstraints.HORIZONTAL;  //theofilos

    labelIT = new KLabel("");
    //labelIT.setPreferredSize(new Dimension(380, 20));  //theofilos
    //labelIT.setFont(new Font(Mpiro.selectedFont,Font.BOLD,13));  //theofilos
    //labelIT.setForeground(Color.black);  //theofilos
    //labelPanel.add(labelIT, labelCon);  //theofilos
    // 2
    JPanel clausePanel = new JPanel(new GridBagLayout());
    GridBagConstraints con = new GridBagConstraints();
    con.insets = new Insets(-3,10,0,0);
    con.anchor = GridBagConstraints.WEST;
    con.weightx = 1.0; con.weighty = 0.0;
    con.fill = GridBagConstraints.HORIZONTAL;
    //clausePanel.setPreferredSize(new Dimension(380,30));
    //clausePanel.setBorder(new EmptyBorder(new Insets(2,10,2,10)));
    clause = new JCheckBox(LangResources.getString(Mpiro.selectedLocale, "clausePlan_text"), false);
    template = new JCheckBox(LangResources.getString(Mpiro.selectedLocale, "template_text"), false);
    noMicroPlanning = new JCheckBox(LangResources.getString(Mpiro.selectedLocale, "doNotUseForThisLanguage_text"), true);
    advancedOptions = new JCheckBox(LangResources.getString(Mpiro.selectedLocale, "showAdvancedOptions_text"), false);
    ButtonGroup gct = new ButtonGroup();
    gct.add(clause);
    gct.add(template);
    gct.add(noMicroPlanning);
    clausePanel.setPreferredSize(new Dimension(390, 65));
    clausePanel.add(clause, con);
    clausePanel.add(advancedOptions, con);
    con.gridy = 1;
    con.insets = new Insets(-4,10,0,0);
    clausePanel.add(template, con);
    con.gridy = 2;
    con.insets = new Insets(-5,10,0,0);
    clausePanel.add(noMicroPlanning, con);
    // 3
    KLabel verb = new KLabel(LangResources.getString(Mpiro.selectedLocale, "verb_text"));
    verbID = new KComboBox();
    String defaultVerbID = new String(LangResources.getString(Mpiro.selectedLocale, "chooseAVerbIdentifier_text"));	//maria
    verbID.addItem(defaultVerbID);		//maria
    int verbNum = LexiconPanel.vnode.getChildCount();
		for (int i = 0; i < verbNum; i++) 
		{
			String iVerb = null;
			iVerb = LexiconPanel.vnode.getChildAt(i).toString();
			verbID.addItem(iVerb);
		}
    // 4
    KLabel voice = new KLabel(LangResources.getString(Mpiro.selectedLocale, "voice_text"));
    active = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "active_text"), true);
    passive = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "passive_text"), false);
    ButtonGroup gv = new ButtonGroup();
    gv.add(active);
    gv.add(passive);
    // 5
    KLabel mood = new KLabel(LangResources.getString(Mpiro.selectedLocale, "mood_text"));
    indicative = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "indicative_text"), true);
    imperative = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "imperative_text"), false);
    subjunctive = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "subjunctive_text"), false);
    nonfinite = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "nonfinite_text"), false);
    ButtonGroup gm = new ButtonGroup();
    gm.add(indicative);
    gm.add(imperative);
    gm.add(subjunctive);
    gm.add(nonfinite);
    // 6
    KLabel tense = new KLabel(LangResources.getString(Mpiro.selectedLocale, "tense_text"));
    pastTense = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "past_text"), true);
    presentTense = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "present_text"), false);
    futureTense = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "future_text"), false);
    ButtonGroup gt = new ButtonGroup();
    gt.add(pastTense);
    gt.add(presentTense);
    gt.add(futureTense);
    // 7
    KLabel aspect = new KLabel(LangResources.getString(Mpiro.selectedLocale, "aspect_text"));
    simple = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "simple_text"), true);
    progressive = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "progressive_text"), false);
    ButtonGroup gas = new ButtonGroup();
    gas.add(simple);
    gas.add(progressive);
    // 8
    KLabel reversible1 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "reversible_text"));
    KLabel reversible2 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "subjectObject_text"));
    JPanel reversible = new JPanel(new GridLayout(2,1));
    reversible.add(reversible1);
    reversible.add(reversible2);
    revTrue = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "true_text"), false);
    revFalse = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "false_text"), true);
    ButtonGroup gr = new ButtonGroup();
    gr.add(revTrue);
    gr.add(revFalse);
    // 9
    KLabel preposition1 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "preposition_text"));
    KLabel preposition2 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "beforeObject_text"));
    JPanel preposition = new JPanel(new GridLayout(2,1));
    preposition.add(preposition1);
    preposition.add(preposition2);
    prepositionID = new KComboBox();
    prepositionID.addItem(" ");
    prepositionID.addItem("a");
    prepositionID.addItem("con");
    prepositionID.addItem("da");
    prepositionID.addItem("di");
    prepositionID.addItem("durante");
    prepositionID.addItem("fra");
    prepositionID.addItem("in");
    prepositionID.addItem("per");
    prepositionID.addItem("secondo");
    prepositionID.addItem("su");
    prepositionID.addItem("vicino");
    prepositionID.setEditable(true);
    // 10
    KLabel expSubject1 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "referringExpression_text"));
    KLabel expSubject2 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "forSubject_text"));
    JPanel expSubject = new JPanel(new GridLayout(2,1));
    expSubject.add(expSubject1);
    expSubject.add(expSubject2);
    expSubjectID = new KComboBox();
    expSubjectID.addItem(Template.auto_text);						//maria
    expSubjectID.addItem(Template.name_text);						//maria
    expSubjectID.addItem(Template.pronoun_text);					//maria
    expSubjectID.addItem(Template.typeWithDefiniteArticle_text);	//maria
    expSubjectID.addItem(Template.typeWithIndefiniteArticle_text);	//maria
    // 10.5
    KLabel caseExpSubject1 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "caseOfReferring_text"));
    KLabel caseExpSubject2 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "expressionForSubject_text"));
    JPanel caseExpSubject = new JPanel(new GridLayout(2,1));
    caseExpSubject.add(caseExpSubject1);
    caseExpSubject.add(caseExpSubject2);
    caseExpSubjectID = new KComboBox();
    caseExpSubjectID.addItem(Template.nominative_text);	//maria
    caseExpSubjectID.addItem(Template.genitive_text);	//maria
    caseExpSubjectID.addItem(Template.accusative_text);	//maria
    // 11
    KLabel expObject1 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "referringExpression_text"));
    KLabel expObject2 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "forObject_text"));
    JPanel expObject = new JPanel(new GridLayout(2,1));
    expObject.add(expObject1);
    expObject.add(expObject2);
    expObjectID = new KComboBox();
    expObjectID.addItem(Template.auto_text);						//maria
    expObjectID.addItem(Template.name_text);						//maria
    expObjectID.addItem(Template.pronoun_text);						//maria
    expObjectID.addItem(Template.typeWithDefiniteArticle_text);		//maria
    expObjectID.addItem(Template.typeWithIndefiniteArticle_text);	//maria
    // 11.5
    KLabel caseExpObject1 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "caseOfReferring_text"));
    KLabel caseExpObject2 = new KLabel(LangResources.getString(Mpiro.selectedLocale, "expressionForObject_text"));
    JPanel caseExpObject = new JPanel(new GridLayout(2,1));
    caseExpObject.add(caseExpObject1);
    caseExpObject.add(caseExpObject2);
    caseExpObjectID = new KComboBox();
    caseExpObjectID.addItem(Template.accusative_text);	//maria
    caseExpObjectID.addItem(Template.genitive_text);	//maria
    caseExpObjectID.addItem(Template.nominative_text);	//maria
    // 12
    KLabel preAdjunct = new KLabel(LangResources.getString(Mpiro.selectedLocale, "preAdjunct_text"));
    preField = new JTextField(12);
    // 13
    KLabel postAdjunct = new KLabel(LangResources.getString(Mpiro.selectedLocale, "postAdjunct_text"));
    postField = new JTextField(12);
    // 14
    KLabel adverb = new KLabel(LangResources.getString(Mpiro.selectedLocale, "adverb_text"));
    adverbID = new KComboBox();
    adverbID.addItem(" ");
    adverbID.addItem("frequentemente");
    adverbID.addItem("inizialmente");
    adverbID.addItem("velocemente");
    adverbID.setEditable(true);
    adverbID.setPreferredSize(new Dimension(160,22));
    // 15
    KLabel aggregation = new KLabel(LangResources.getString(Mpiro.selectedLocale, "aggregationAllowed_text"));
    aggTrue = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "true_text"), true);
    aggFalse = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "false_text"), false);
    ButtonGroup gag = new ButtonGroup();
    gag.add(aggTrue);
    gag.add(aggFalse);
    // 16
    getEnglish = new KButton(LangResources.getString(Mpiro.selectedLocale, "getValuesFromEnglish_text"));
    getGreek = new KButton(LangResources.getString(Mpiro.selectedLocale, "getValuesFromGreek_text"));
    JPanel getPanel = new JPanel(new GridLayout(1,2));
    getPanel.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
    getPanel.add(getEnglish);
    getPanel.add(getGreek);

		/** Put them all (1-16 and the lines) together */
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBorder(new EmptyBorder(new Insets(2,7,0,0)));
		panelMain = new JPanel(new GridBagLayout());
		panelAdvanced = new JPanel(new GridBagLayout());
		panelAdvanced.setBorder(new TitledBorder(new LineBorder(Color.white, 2),
		                       LangResources.getString(Mpiro.selectedLocale, "advancedOptions_text"),
		                       0, 0, new Font(Mpiro.selectedFont, 1, 12),
		                       new Color(0,0,0)));

    centerPanel.add("West", panelMain);
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(1,0,1,0);
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.weightx = 1.0; c.weighty = 1.0;
    EmptyBorder border = new EmptyBorder(new Insets(0,0,0,3));

    c.gridwidth = 4;
    c.gridx = 0; c.gridy = 2;
    panelMain.add(linePanel[1], c);
    c.gridy = 4;
    panelMain.add(linePanel[2], c);
    c.gridy = 6;
    panelMain.add(linePanel[3], c);
    c.gridy = 11;
    panelMain.add(linePanel[4], c);
    c.gridy = 14;
    panelMain.add(linePanel[5], c);
    c.gridy = 17;
    panelMain.add(linePanel[6], c);
    c.gridy = 20;
    panelMain.add(linePanel[7], c);
    c.gridy = 22;

    c.fill = GridBagConstraints.NONE;

    verb.setBorder(border);
    c.gridx = 0; c.gridy = 1;
    c.gridwidth = 1;
    panelMain.add(verb, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelMain.add(verbID, c);

    voice.setBorder(border);
    c.gridwidth = 1;
    c.gridx = 0; c.gridy = 3;
    panelMain.add(voice, c);
    c.gridx = 1; c.gridy = 3;
    panelMain.add(active, c);
    c.gridx = 2; c.gridy = 3;
    panelMain.add(passive, c);

    tense.setBorder(border);
    c.gridx = 0; c.gridy = 5;
    panelMain.add(tense, c);
    c.gridx = 1;
    panelMain.add(pastTense, c);
    c.gridx = 2;
    panelMain.add(presentTense, c);
    c.gridx = 3;
    panelMain.add(futureTense, c);

    aspect.setBorder(border);
    c.gridwidth = 1;
    c.gridx = 0; c.gridy = 7;
    panelMain.add(aspect, c);
    c.gridx = 1; c.gridy = 7;
    panelMain.add(simple, c);
    c.gridx = 2; c.gridy = 7;
    panelMain.add(progressive, c);

    preposition.setBorder(border);
    c.gridheight = 2;
    c.gridx = 0; c.gridy = 12;
    panelMain.add(preposition, c);
    c.gridx = 1;
    panelMain.add(prepositionID, c);

    preAdjunct.setBorder(border);
    c.gridx = 0; c.gridy = 15;
    c.gridwidth = 1;
    panelMain.add(preAdjunct, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelMain.add(preField, c);

    postAdjunct.setBorder(border);
    c.gridx = 0; c.gridy = 18;
    c.gridwidth = 1;
    panelMain.add(postAdjunct, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelMain.add(postField, c);

    adverb.setBorder(border);
    c.gridx = 0; c.gridy = 21;
    c.gridwidth = 1;
    panelMain.add(adverb, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelMain.add(adverbID, c);


    // Advanced Options

    c.fill = GridBagConstraints.HORIZONTAL;

    c.gridwidth = 4;
    c.gridx = 0; c.gridy = 5;
    panelAdvanced.add(linePanel[8], c);
    c.gridy = 8;
    panelAdvanced.add(linePanel[9], c);
    c.gridy = 11;
    panelAdvanced.add(linePanel[10], c);
    c.gridy = 15;
    panelAdvanced.add(linePanel[11], c);
    c.gridy = 18;
    panelAdvanced.add(linePanel[12], c);
    c.gridy = 21;
    panelAdvanced.add(linePanel[13], c);

    c.fill = GridBagConstraints.NONE;

    mood.setBorder(border);
    c.gridx = 0; c.gridy = 3;
    c.gridheight = 2;
    panelAdvanced.add(mood, c);
    c.gridx = 1;
    c.gridheight = 1;
    panelAdvanced.add(indicative, c);
    c.gridx = 2;
    panelAdvanced.add(imperative, c);
    c.gridx = 1; c.gridy = 4;
    panelAdvanced.add(subjunctive, c);
    c.gridx = 2;
    panelAdvanced.add(nonfinite, c);

    reversible.setBorder(border);
    c.gridx = 0; c.gridy = 7;
    panelAdvanced.add(reversible, c);
    c.gridx = 1;
    panelAdvanced.add(revTrue, c);
    c.gridx = 2;
    panelAdvanced.add(revFalse, c);

    expSubject.setBorder(border);
    c.gridx = 0; c.gridy = 10;
    c.gridwidth = 1; c.gridheight = 1;
    panelAdvanced.add(expSubject, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelAdvanced.add(expSubjectID, c);

    caseExpSubject.setBorder(border);
    c.gridx = 0; c.gridy = 14;
    c.gridwidth = 1;
    panelAdvanced.add(caseExpSubject, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelAdvanced.add(caseExpSubjectID, c);

    expObject.setBorder(border);
    c.gridx = 0; c.gridy = 17;
    c.gridwidth = 1;
    panelAdvanced.add(expObject, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelAdvanced.add(expObjectID, c);

    caseExpObject.setBorder(border);
    c.gridx = 0; c.gridy = 20;
    c.gridwidth = 1;
    panelAdvanced.add(caseExpObject, c);
    c.gridx = 1;
    c.gridwidth = 2;
    panelAdvanced.add(caseExpObjectID, c);

    aggregation.setBorder(border);
    c.gridx = 0; c.gridy = 23;
    c.gridwidth = 1;
    panelAdvanced.add(aggregation, c);
    c.gridx = 1;
    panelAdvanced.add(aggTrue, c);
    c.gridx = 2;
    panelAdvanced.add(aggFalse, c);


    /** Adding them all in the ContentPane of the Panel  */
    JPanel up = new JPanel(new BorderLayout());
    //up.add("North", labelPanel);  //theofilos
    up.add("Center", clausePanel);
    JScrollPane centerScroll = new JScrollPane(centerPanel);
    centerScroll.setPreferredSize(new Dimension(390, 408));

    setLayout(new BorderLayout());
    add("North", up);
    add("Center", centerScroll);
    add("South", getPanel);

    /** Add the Action Listeners */

    verbID.addActionListener(ItalianMicroPanel.this);

    active.addActionListener(ItalianMicroPanel.this);
    passive.addActionListener(ItalianMicroPanel.this);

    pastTense.addActionListener(ItalianMicroPanel.this);
    presentTense.addActionListener(ItalianMicroPanel.this);
    futureTense.addActionListener(ItalianMicroPanel.this);

    simple.addActionListener(ItalianMicroPanel.this);
    progressive.addActionListener(ItalianMicroPanel.this);

    prepositionID.addFocusListener(ItalianMicroPanel.this);
    prepositionID.addActionListener(ItalianMicroPanel.this);
    preField.addFocusListener(ItalianMicroPanel.this);
    postField.addFocusListener(ItalianMicroPanel.this);
    adverbID.addFocusListener(ItalianMicroPanel.this);
    adverbID.addActionListener(ItalianMicroPanel.this);

    indicative.addActionListener(ItalianMicroPanel.this);
    imperative.addActionListener(ItalianMicroPanel.this);
    subjunctive.addActionListener(ItalianMicroPanel.this);
    nonfinite.addActionListener(ItalianMicroPanel.this);

    expSubjectID.addActionListener(ItalianMicroPanel.this);
    expObjectID.addActionListener(ItalianMicroPanel.this);

    caseExpSubjectID.addActionListener(ItalianMicroPanel.this);
    caseExpObjectID.addActionListener(ItalianMicroPanel.this);

    revTrue.addActionListener(ItalianMicroPanel.this);
    revFalse.addActionListener(ItalianMicroPanel.this);

    aggTrue.addActionListener(ItalianMicroPanel.this);
    aggFalse.addActionListener(ItalianMicroPanel.this);

    clause.addActionListener(ItalianMicroPanel.this);
    advancedOptions.addActionListener(ItalianMicroPanel.this);
    template.addActionListener(ItalianMicroPanel.this);
    noMicroPlanning.addActionListener(ItalianMicroPanel.this);
    getEnglish.addActionListener(ItalianMicroPanel.this);
    getGreek.addActionListener(ItalianMicroPanel.this);


    if ((QueryHashtable.showValues2(DataBasePanel.last.toString(),
                                    microplanNumber,
                                    TreePreviews.dbt.dbtl.selectedField.toString(),
                                    "Italian")).isEmpty() == false)
    {

    	showValues(QueryHashtable.showValues2(DataBasePanel.last.toString(),
                                           microplanNumber,
                                           TreePreviews.dbt.dbtl.selectedField.toString(),
                                           "Italian"));
    }
    if ((QueryHashtable.showSpecialValues2(DataBasePanel.last.toString(),
                                          microplanNumber,
                                          TreePreviews.dbt.dbtl.selectedField.toString(),
                                          "Italian")).isEmpty() == false)
    {

			showSpecialValues(QueryHashtable.showSpecialValues2(DataBasePanel.last.toString(),
                                                          microplanNumber,
                                                          TreePreviews.dbt.dbtl.selectedField.toString(),
                                                          "Italian"));
    }
    panelMain.revalidate();
    panelMain.repaint();
  	panelAdvanced.repaint();
	}	  // constructor


	/**
	 * A general method added in the actionPerformed method. Updates the non-null parameters.
	 * @param s String
	 * @param cb KComboBox
	 * @param tf JTextField
	 * @param rb KRadioButton
	 */
	public void printAndUpdate(String s, KComboBox cb, JTextField tf, KRadioButton rb) 
	{
		String node = DataBasePanel.last.toString();
		String field = DataBaseTable.dbTable.getValueAt(currentRow,0).toString();
		String filler = DataBaseTable.dbTable.getValueAt(currentRow,1).toString();
		String setValued = DataBaseTable.dbTable.getValueAt(currentRow,2).toString();
		String label = s;
		String item = "";

		// Distinguish the type of Object clicked.
		if (cb != null) 
		{
			if((cb==expSubjectID)||(cb==expObjectID))																										//maria
			{																																				//maria
	   		if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "auto_text")))							//maria
	   		{																																			//maria
	   			item = LangResources.getString(Mpiro.enLocale, "auto_text");																			//maria
	   		}																																			//maria
	   		else if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "name_text")))						//maria
	   		{																																			//maria
	   			item = LangResources.getString(Mpiro.enLocale, "name_text");																			//maria
	   		}																																			//maria
	   		else if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "pronoun_text")))					//maria
	   		{																																			//maria
	   			item = LangResources.getString(Mpiro.enLocale, "pronoun_text");																			//maria
	   		}																																			//maria
	   		else if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "typeWithDefiniteArticle_text")))	//maria
	   		{																																			//maria
	   			item = LangResources.getString(Mpiro.enLocale, "typeWithDefiniteArticle_text");															//maria
	   		}																																			//maria
	   		else if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "typeWithIndefiniteArticle_text")))	//maria
	   		{																																			//maria
	   			item = LangResources.getString(Mpiro.enLocale, "typeWithIndefiniteArticle_text");														//maria
	   		}																																			//maria
			}																																				//maria
     	else if((cb==caseExpSubjectID)||(cb==caseExpObjectID))																							//maria
     	{																																				//maria
     		if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "nominative_text")))						//maria
     		{																																			//maria
     			item = LangResources.getString(Mpiro.enLocale, "nominative_text");																		//maria
     		}																																			//maria
     		else if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "genitive_text")))					//maria
     		{																																			//maria
     			item = LangResources.getString(Mpiro.enLocale, "genitive_text");																		//maria
     		}																																			//maria
     		else if(cb.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "accusative_text")))					//maria
     		{																																			//maria
     			item = LangResources.getString(Mpiro.enLocale, "accusative_text");																		//maria
     		}																																			//maria
     	}																																				//maria
     	else																																			//maria
      {    
      	item = cb.getSelectedItem().toString();	
      }
		}
		if (tf != null) 
		{
			item = tf.getText();
		}
		if (rb != null) 
		{
	    if (label.equalsIgnoreCase("Voice"))
	    {
	      if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "active_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "active_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "active_text"))
	          )
	      {
	        item = LangResources.getString(Mpiro.enLocale, "active_text");
	      }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "passive_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "passive_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "passive_text"))
                  )
        {
          item = LangResources.getString(Mpiro.enLocale, "passive_text");
        }
			}

      else if (label.equalsIgnoreCase("Tense"))
      {
        if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "past_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "past_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "past_text"))
            )
        {
          item = LangResources.getString(Mpiro.enLocale, "past_text");
        }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "present_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "present_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "present_text"))
                  )
        {
          item = LangResources.getString(Mpiro.enLocale, "present_text");
        }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "future_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "future_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "future_text"))
                  )
        {
          item = LangResources.getString(Mpiro.enLocale, "future_text");
        }
      }

      else if (label.equalsIgnoreCase("Aspect"))
      {
	      if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "simple_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "simple_text")) ||
	          rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "simple_text"))
	          )
	      {
	        item = LangResources.getString(Mpiro.enLocale, "simple_text");
	      }
	      else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "progressive_text")) ||
	                rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "progressive_text")) ||
	                rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "progressive_text"))
	                )
	      {
	        item = LangResources.getString(Mpiro.enLocale, "progressive_text");
	      }
      }

	    else if (label.equalsIgnoreCase("Mood"))
	    {
        if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "indicative_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "indicative_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "indicative_text"))
            )
        {
          item = LangResources.getString(Mpiro.enLocale, "indicative_text");
        }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "imperative_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "imperative_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "imperative_text"))
                  )
        {
          item = LangResources.getString(Mpiro.enLocale, "imperative_text");
        }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "subjunctive_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "subjunctive_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "subjunctive_text"))
                  )
        {
          item = LangResources.getString(Mpiro.enLocale, "subjunctive_text");
        }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "nonfinite_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "nonfinite_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "nonfinite_text"))
                  )
        {
          item = LangResources.getString(Mpiro.enLocale, "nonfinite_text");
        }
	    }

      else if (label.equalsIgnoreCase("Reversible"))
      {
        if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "true_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "true_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "true_text"))
            )
        {
          item = "rev" + LangResources.getString(Mpiro.enLocale, "true_text");
        }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "false_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "false_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "false_text"))
                  )
        {
          item = "rev" + LangResources.getString(Mpiro.enLocale, "false_text");
        }
      }

      else if (label.equalsIgnoreCase("Aggreg"))
      {
        if (rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "true_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "true_text")) ||
            rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "true_text"))
            )
        {
          item = "agg" + LangResources.getString(Mpiro.enLocale, "true_text");
        }
        else if ( rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "false_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.itLocale, "false_text")) ||
                  rb.getText().equalsIgnoreCase(LangResources.getString(Mpiro.grLocale, "false_text"))
                  )
        {
          item = "agg" + LangResources.getString(Mpiro.enLocale, "false_text");
        }
      }

      //if (label.startsWith("Agg")) {
      //        item = "agg" + rb.getText();
      //} else if (label.startsWith("Rev")) {
      //        item = "rev" + rb.getText();
      //} else {
      //        item = rb.getText();
      //}
		}
		// Update the Hashtable
		//System.out.println("()---- " + node + " " + field + " " + label + " " + "Italian" + " " + item);
		QueryHashtable.updateHashtable2(node, microplanNumber, field, label, "Italian", item);
		Mpiro.needExportToExprimo = true;		//maria         
	}

  public void focusGained(FocusEvent fe) 
  {}

  public void focusLost(FocusEvent fe) 
  {
		if (fe.getSource() == prepositionID) 
		{
			printAndUpdate("Prep", prepositionID, null, null);
		}
		if (fe.getSource() == preField) 
		{
			printAndUpdate("Preadj", null, preField, null);
		}
		if (fe.getSource() == postField) 
		{
			printAndUpdate("Postadj", null, postField, null);
		}
		if (fe.getSource() == adverbID) 
		{
			printAndUpdate("Adverb", adverbID, null, null);
		}
  }


  public void actionPerformed(ActionEvent e) 
  {
		if (e.getSource() == verbID) 
		{
			printAndUpdate("Verb", verbID, null, null);
		}
		
		if (e.getSource() == active) 
		{
			printAndUpdate("Voice", null, null, active);
		}
		
		if (e.getSource() == passive) 
		{
			printAndUpdate("Voice", null, null, passive);
		}

		if (e.getSource() == pastTense) 
		{
			printAndUpdate("Tense", null, null, pastTense);
		}
		
		if (e.getSource() == presentTense) 
		{
			printAndUpdate("Tense", null, null, presentTense);
		}
		
		if (e.getSource() == futureTense) 
		{
			printAndUpdate("Tense", null, null, futureTense);
		}
		
		if (e.getSource() == simple) 
		{
			printAndUpdate("Aspect", null, null, simple);
		}
		
		if (e.getSource() == progressive) 
		{
			printAndUpdate("Aspect", null, null, progressive);
		}
		
		if (e.getSource() == prepositionID) 
		{
			printAndUpdate("Prep", prepositionID, null, null);
		}

		if (e.getSource() == adverbID) 
		{
			printAndUpdate("Adverb", adverbID, null, null);
		}
		
		if (e.getSource() == expSubjectID) 
		{
			printAndUpdate("Refersub", expSubjectID, null, null);
		}
		
		if (e.getSource() == expObjectID) 
		{
			printAndUpdate("Referobj", expObjectID, null, null);
		}
		
		if (e.getSource() == caseExpSubjectID) 
		{
			printAndUpdate("Casesub", caseExpSubjectID, null, null);
		}
		
		if (e.getSource() == caseExpObjectID) 
		{
			printAndUpdate("Caseobj", caseExpObjectID, null, null);
		}
		
		if (e.getSource() == indicative) 
		{
			printAndUpdate("Mood", null, null, indicative);
		}

		if (e.getSource() == imperative) 
		{
			printAndUpdate("Mood", null, null, imperative);
		}
		
		if (e.getSource() == subjunctive) 
		{
			printAndUpdate("Mood", null, null, subjunctive);
		}
		
		if (e.getSource() == nonfinite) 
		{
			printAndUpdate("Mood", null, null, nonfinite);
		}
		
		if (e.getSource() == revTrue) 
		{
			printAndUpdate("Reversible", null, null, revTrue);
		}
		
		if (e.getSource() == revFalse) 
		{
			printAndUpdate("Reversible", null, null, revFalse);
		}
		
		if (e.getSource() == aggTrue) 
		{
			printAndUpdate("Aggreg", null, null, aggTrue);
		}

		if (e.getSource() == aggFalse) 
		{
			printAndUpdate("Aggreg", null, null, aggFalse);
		}

    if (e.getSource() == clause) 
    {
	    advancedOptions.setEnabled(true);
	    getEnglish.setEnabled(true);
	    getGreek.setEnabled(true);
	    centerPanel.removeAll();
	    if (advancedOptions.isSelected()) 
	    {
				centerPanel.add(panelMain, "North");
				centerPanel.add(panelAdvanced, "Center");
				centerPanel.revalidate();
				centerPanel.repaint();
			} 
			else 
			{
				centerPanel.add(panelMain);
				centerPanel.revalidate();
				centerPanel.repaint();
				//advancedOptions.setEnabled(true);
			}
			// storing the selection
			String node = DataBasePanel.last.toString();
			//String field = DataBaseTable.dbTable.getValueAt(currentRow,0).toString();
			String field = DataBaseTableListener.selectedField;
			QueryHashtable.updateHashtable2(node, microplanNumber, field, "SELECTION", "Italian", "Clause");
			Mpiro.needExportToEmulator=true;		//maria
			// the following 3 lines update the microplanning index of the dbtable
			//if (microplanNumber.equalsIgnoreCase("1"))															//maria
			//{																									//maria
			//    DataBaseTable.m_data.setValueAt(LangCombo.updateMicroplanningIndex(microplanNumber, field), DataBaseTableListener.rowNo, 3);//maria
			//}																									//maria
			DataBaseTable.m_data.setValueAt(LangCombo.updateMicroplanningIndex2(field), DataBaseTableListener.rowNo, 3);	//maria
			DataBaseTable.dbTable.revalidate();
			DataBaseTable.dbTable.repaint();
		}

		if (e.getSource() == template) 
		{
			centerPanel.removeAll();
			centerPanel.add(template2);
			centerPanel.revalidate();
			centerPanel.repaint();
			advancedOptions.setEnabled(false);
			getEnglish.setEnabled(false);
			getGreek.setEnabled(false);
			// storing the selection
			String node = DataBasePanel.last.toString();
			//String field = DataBaseTable.dbTable.getValueAt(currentRow,0).toString();
			String field = DataBaseTableListener.selectedField;
			QueryHashtable.updateHashtable2(node, microplanNumber, field, "SELECTION", "Italian", "Template");
			Mpiro.needExportToEmulator=true;		//maria
			// the following 3 lines update the microplanning index of the dbtable
			//if (microplanNumber.equalsIgnoreCase("1"))															//maria
			//{																									//maria
			//    DataBaseTable.m_data.setValueAt(LangCombo.updateMicroplanningIndex(microplanNumber, field), DataBaseTableListener.rowNo, 3);//maria
			//}																									//maria
			DataBaseTable.m_data.setValueAt(LangCombo.updateMicroplanningIndex2(field), DataBaseTableListener.rowNo, 3);	//maria
			DataBaseTable.dbTable.revalidate();
			DataBaseTable.dbTable.repaint();
		}

		if (e.getSource() == noMicroPlanning) 
		{
			centerPanel.removeAll();
			//centerPanel.add(template2);
			centerPanel.revalidate();
			centerPanel.repaint();
			advancedOptions.setEnabled(false);
			getEnglish.setEnabled(false);
			getGreek.setEnabled(false);
			// storing the selection
			String node = DataBasePanel.last.toString();
			//String field = DataBaseTable.dbTable.getValueAt(currentRow,0).toString();
			String field = DataBaseTableListener.selectedField;
			QueryHashtable.updateHashtable2(node, microplanNumber, field, "SELECTION", "Italian", "NoMicroPlanning");
			Mpiro.needExportToEmulator=true;		//maria
			// the following 3 lines update the microplanning index of the dbtable
			//if (microplanNumber.equalsIgnoreCase("1"))															//maria
			//{																									//maria
			//    DataBaseTable.m_data.setValueAt(LangCombo.updateMicroplanningIndex(microplanNumber, field), DataBaseTableListener.rowNo, 3);//maria
			//}																									//maria
			DataBaseTable.m_data.setValueAt(LangCombo.updateMicroplanningIndex2(field), DataBaseTableListener.rowNo, 3);	//maria
			DataBaseTable.dbTable.revalidate();
			DataBaseTable.dbTable.repaint();
		}

		if (e.getSource() == advancedOptions) 
		{
			centerPanel.removeAll();
			if (advancedOptions.isSelected()) 
			{
				centerPanel.add(panelMain, "North");
				centerPanel.add(panelAdvanced, "Center");
				centerPanel.revalidate();
				centerPanel.repaint();
				//advancedOptions.setSelected(false);
			} 
			else 
			{
				centerPanel.add(panelMain);
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		}

		if (e.getSource() == getEnglish) 
		{
			//QueryHashtable.clearHashtableMicroplanningEntries(DataBasePanel.last.toString(),
			//                                                  DataBaseTable.dbTable.getValueAt(DataBaseTable.dbTable.getSelectedRow(),0).toString(),
			//                                                  "Italian");
			showValues(QueryHashtable.getValuesFromEnglish2(DataBasePanel.last.toString(),
			                         microplanNumber,
			                         DataBaseTable.dbTable.getValueAt(DataBaseTableListener.rowNo,0).toString(),
			                         "Italian"));
			showSpecialValuesVerbOnly(QueryHashtable.getSpecialValueVerbFromEnglish2(DataBasePanel.last.toString(),
			                          microplanNumber,
			                          DataBaseTable.dbTable.getValueAt(DataBaseTableListener.rowNo,0).toString(),
			                          "Italian"));
		}

    if (e.getSource() == getGreek) 
    {
			//QueryHashtable.clearHashtableMicroplanningEntries(DataBasePanel.last.toString(),
			//                                                  DataBaseTable.dbTable.getValueAt(DataBaseTable.dbTable.getSelectedRow(),0).toString(),
			//                                                  "Italian");
			showValues(QueryHashtable.getValuesFromGreek2(DataBasePanel.last.toString(),
			                          microplanNumber,
			                          DataBaseTable.dbTable.getValueAt(DataBaseTableListener.rowNo,0).toString(),
			                          "Italian"));
			showSpecialValuesVerbOnly(QueryHashtable.getSpecialValueVerbFromGreek2(DataBasePanel.last.toString(),
			                          microplanNumber,
			                          DataBaseTable.dbTable.getValueAt(DataBaseTableListener.rowNo,0).toString(),
			                          "Italian"));
    }

	} // actionPerformed


	public static void showValues(Vector currentHashtableValues) 
	{
	  if (!currentHashtableValues.isEmpty()) 
	  {
	    for (Enumeration e = currentHashtableValues.elements(); e.hasMoreElements(); ) 
	    {
				String value = e.nextElement().toString();
				
				if (value.compareTo("Active") == 0) 
				{
				  active.setSelected(true);
				} 
           
				else if (value.compareTo("Passive") == 0) 
				{
				  passive.setSelected(true);
				} 
				
				else if (value.compareTo("Past") == 0) 
				{
				  pastTense.setSelected(true);
				} 
				
				else if (value.compareTo("Present") == 0) 
				{
				  presentTense.setSelected(true);
				} 
				
				else if (value.compareTo("Future") == 0) 
				{
				  futureTense.setSelected(true);
				} 
				
				else if (value.compareTo("Indicative") == 0) 
				{
				  indicative.setSelected(true);
				} 
           
				else if (value.compareTo("Imperative") == 0) 
				{
				  imperative.setSelected(true);
				} 
				
				else if (value.compareTo("Subjunctive") == 0) 
				{
				  subjunctive.setSelected(true);
				} 
				
				else if (value.compareTo("Nonfinite") == 0) 
				{
				  nonfinite.setSelected(true);
				} 
				
				else if (value.compareTo("revTrue") == 0) 
				{
				  revTrue.setSelected(true);
				} 
				
				else if (value.compareTo("revFalse") == 0) 
				{
				  revFalse.setSelected(true);
				} 
           
				else if (value.compareTo("aggTrue") == 0) 
				{
				  aggTrue.setSelected(true);
				} 
				
				else if (value.compareTo("aggFalse") == 0) 
				{
				  aggFalse.setSelected(true);
				} 
				
				else if (value.compareTo("Simple") == 0) 
				{
					simple.setSelected(true);
				} 
				
				else if (value.compareTo("Progressive") == 0) 
				{
				  progressive.setSelected(true);
				} 
           
				else 
				{
					//System.out.println("----------- Alert ---------");
				};
			}
		}
	}


	public static void showSpecialValues(Hashtable currentHashtableValues) 
	{
		for (Enumeration k = currentHashtableValues.keys(), e = currentHashtableValues.elements(); k.hasMoreElements(); ) 
		{
	    String keyValue = k.nextElement().toString();
	    String elementValue = e.nextElement().toString();
	
	    if (keyValue.compareTo("SELECTION") == 0) 
	    {
				if (elementValue.equalsIgnoreCase("Clause"))
				{
					clause.setSelected(true);
				  centerPanel.removeAll();
				  centerPanel.add(panelMain);
				  centerPanel.revalidate();
				  centerPanel.repaint();
				  advancedOptions.setEnabled(true);
				  getEnglish.setEnabled(true);
				  getGreek.setEnabled(true);
				}
				else if (elementValue.equalsIgnoreCase("Template"))
				{
				  template.setSelected(true);
				  centerPanel.removeAll();
				  centerPanel.add(template2);
				  centerPanel.revalidate();
				  centerPanel.repaint();
				  advancedOptions.setEnabled(false);
				  getEnglish.setEnabled(false);
				  getGreek.setEnabled(false);
				}
				else if (elementValue.equalsIgnoreCase("NoMicroPlanning"))
				{
				  noMicroPlanning.setSelected(true);
				  centerPanel.removeAll();
				  //centerPanel.add(template2);
				  centerPanel.revalidate();
				  centerPanel.repaint();
				  advancedOptions.setEnabled(false);
				  getEnglish.setEnabled(false);
				  getGreek.setEnabled(false);
				}
			}

			if (keyValue.compareTo("Verb") == 0) 
			{
				verbID.setSelectedItem((Object)elementValue);
			}
			if (keyValue.compareTo("Prep") == 0) 
			{
				prepositionID.setSelectedItem((Object)elementValue);
			}
			if (keyValue.compareTo("Preadj") == 0) 
			{
				preField.setText(elementValue);
			}
			if (keyValue.compareTo("Postadj") == 0) 
			{
				postField.setText(elementValue);
			}
			if (keyValue.compareTo("Adverb") == 0) 
			{
				adverbID.setSelectedItem((Object)elementValue);
			}
			if (keyValue.compareTo("Refersub") == 0) 
			{
				if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "auto_text")))								//maria
				{																													//maria
					//expSubjectID.setSelectedItem((Object)elementValue);															//maria
					expSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "auto_text"));						//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "name_text")))						//maria
				{																													//maria
					expSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "name_text"));						//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "pronoun_text")))						//maria
				{																													//maria
					expSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "pronoun_text"));					//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithDefiniteArticle_text")))		//maria
				{																													//maria
					expSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithDefiniteArticle_text"));	//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithIndefiniteArticle_text")))	//maria
				{																													//maria
					expSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithIndefiniteArticle_text"));	//maria
				}																													//maria
			}
      if (keyValue.compareTo("Referobj") == 0) 
      {
				if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "auto_text")))								//maria
				{																													//maria
					//expSubjectID.setSelectedItem((Object)elementValue);															//maria
					expObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "auto_text"));						//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "name_text")))						//maria
				{																													//maria
					expObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "name_text"));						//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "pronoun_text")))						//maria
				{																													//maria
					expObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "pronoun_text"));						//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithDefiniteArticle_text")))		//maria
				{																													//maria
					expObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithDefiniteArticle_text"));		//maria
				}																													//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithIndefiniteArticle_text")))	//maria
				{																													//maria
					expObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithIndefiniteArticle_text"));	//maria
				}																													//maria
			}
      if (keyValue.compareTo("Casesub") == 0) 
      {
				if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "nominative_text")))			//maria
				{																										//maria
					//caseExpSubjectID.setSelectedItem((Object)elementValue);											//maria
					caseExpSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "nominative_text"));	//maria
				}																										//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "genitive_text")))		//maria
				{																										//maria
					caseExpSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "genitive_text"));	//maria
				}																										//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "accusative_text")))		//maria
				{																										//maria
					caseExpSubjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "accusative_text"));	//maria
				}																										//maria
			}
			if (keyValue.compareTo("Caseobj") == 0) 
			{
				if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "nominative_text")))			//maria
				{																										//maria
					//caseExpObjectID.setSelectedItem((Object)elementValue);											//maria
					caseExpObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "nominative_text"));	//maria
				}																										//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "genitive_text")))		//maria
				{																										//maria
					caseExpObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "genitive_text"));	//maria
				}																										//maria
				else if(elementValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "accusative_text")))		//maria
				{																										//maria
					caseExpObjectID.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "accusative_text"));	//maria
				}																										//maria
			}
		} //for loop
	} // show special values

	public static void showSpecialValuesVerbOnly(String importedVerb) 
  {
		verbID.setSelectedItem((Object)importedVerb);
  } // showSpecialValuesVerbOnly

}