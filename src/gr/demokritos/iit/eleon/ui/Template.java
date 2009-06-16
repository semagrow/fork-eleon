//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.ColumnLayout;
import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.FieldData;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.NodeVector;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.TemplateVector;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.net.URL;


public class Template extends JPanel implements ActionListener
{
	static String fieldOwner_text = LangResources.getString(Mpiro.selectedLocale, "fieldOwner_text");								//maria
	static String fieldFiller_text = LangResources.getString(Mpiro.selectedLocale, "fieldFiller_text");								//maria
	public static String auto_text = LangResources.getString(Mpiro.selectedLocale, "auto_text");											//maria
	public static String name_text = LangResources.getString(Mpiro.selectedLocale, "name_text");											//maria
	public static String pronoun_text = LangResources.getString(Mpiro.selectedLocale, "pronoun_text");										//maria
	public static String typeWithDefiniteArticle_text = LangResources.getString(Mpiro.selectedLocale, "typeWithDefiniteArticle_text");		//maria
	public static String typeWithIndefiniteArticle_text = LangResources.getString(Mpiro.selectedLocale, "typeWithIndefiniteArticle_text");	//maria
	public static String nominative_text = LangResources.getString(Mpiro.selectedLocale, "nominative_text");								//maria
	public static String genitive_text = LangResources.getString(Mpiro.selectedLocale, "genitive_text");									//maria
	public static String accusative_text = LangResources.getString(Mpiro.selectedLocale, "accusative_text");								//maria

  String[] semanticsChoice = {fieldOwner_text, fieldFiller_text};																//maria
  String[] typeChoice = {auto_text, name_text, pronoun_text, typeWithDefiniteArticle_text, typeWithIndefiniteArticle_text};		//maria
  String[] caseChoice = {nominative_text, genitive_text, accusative_text};														//maria
  String[] tenseChoice = {"past", "future", "present"};
  String[] voiceChoice = {"active","passive"};
  
  Color c = new Color(255,255,255);
  Font font = new Font(Mpiro.selectedFont,Font.BOLD,11);
  Font smallFont = new Font(Mpiro.selectedFont,Font.BOLD,10);

  //static JPanel bigPanel;
  final KButton insertBeforeButton;
  final KButton insertAfterButton;
  final KButton deleteButton;
  static int finalEIG;
  static int y=-1;
  //int finalAA;

  static JPanel aggregPanel;
  static KRadioButton aggTrue;
  static KRadioButton aggFalse;

  static String m_fieldName;
  static String m_microplanNumber;
  static TemplateVector tv;
  static Hashtable allFields;
  static Vector slotVector;
  //static Hashtable[] counts;
  static Vector counts= new Vector();

  static JPanel multiPanel;
  SlotPanel sp;
  static JPopupMenu popup;
 // JTextField stringField;
 
  //static boolean advancedOptions = false;		//maria 

  //FocusListener templateStringListener = new FocusListener();

  public Template(int eig, String fieldName, String microplanNumber) 
  {
		String insertSlot_button = LangResources.getString(Mpiro.selectedLocale, "insertSlot_button");
		String beforeSelected_button = LangResources.getString(Mpiro.selectedLocale, "beforeSelected_button");
		String afterSelected_button = LangResources.getString(Mpiro.selectedLocale, "afterSelected_button");
		String removeSelected_button = LangResources.getString(Mpiro.selectedLocale, "removeSelected_button");
		String slot_button = LangResources.getString(Mpiro.selectedLocale, "slot_button");
		
		m_fieldName = fieldName;
		m_microplanNumber = microplanNumber;
		
		finalEIG = eig; //eig takes the values: 1 for english, 2 for italian and 3 for greek
		
                
 /*                String lastSelected=DataBasePanel.last.toString();
                if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur"))
                {
                    lastSelected=lastSelected.substring(0, lastSelected.length()-7);
                }
                
                 Hashtable allEntityTypes = (Hashtable) QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    Enumeration allTypesNames=allEntityTypes.keys();
                    while(allTypesNames.hasMoreElements())
                    {//DefaultMutableTreeNode nextNode=null;
                        Object nextEl=allTypesNames.nextElement();
               
                        
                         
                        if ((nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected)))&&(!(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString()))))
                        {  y++; 
                            NodeVector nv1 = (NodeVector)QueryHashtable.mainDBHashtable.get(nextEl.toString());
		tv = (TemplateVector)nv1.elementAt(4);
          //      System.out.println(tv.elementAt(finalEIG-1).toString());
               Hashtable testin= (Hashtable)tv.elementAt(finalEIG-1);
               //System.out.println(testin.toString());
               //counts.add("ssssss");
		counts.add(testin); // because finalEIG is 1 for english
		/*          
                        // but englishValues position in tv is 0, etc.
                        if (counts[y].containsKey(m_microplanNumber + ":" + m_fieldName))
		{
			slotVec[y] = (Vector)counts[y].get(m_microplanNumber + ":" + m_fieldName);
			////System.out.println("================ " + slotVector);
		}
		else
		{
			////System.out.println(fieldName + ": This field has not yet any values");
			slotVec[y]   = new Vector();
			Hashtable p1 = new Hashtable();
			Hashtable p2 = new Hashtable();
			slotVec[y].addElement(p1);
			slotVec[y].addElement(p2);
		}
               
                        }}*/
                        
                
                
		// get the values-hashtable
		Vector property = (Vector) QueryHashtable.propertiesHashtable.get(m_fieldName);
		tv = (TemplateVector) property.elementAt(11);
		allFields = (Hashtable)tv.elementAt(finalEIG-1); // because finalEIG is 1 for english
		                                               // but englishValues position in tv is 0, etc.
		////System.out.println("............... " + allFields);
		////System.out.println("............... " + m_fieldName);
		if (allFields.containsKey(m_microplanNumber + ":" + m_fieldName))
		{
			slotVector = (Vector)allFields.get(m_microplanNumber + ":" + m_fieldName);
			////System.out.println("================ " + slotVector);
		}
		else
		{
			////System.out.println(fieldName + ": This field has not yet any values");
			slotVector = new Vector();
			Hashtable h1 = new Hashtable();
			Hashtable h2 = new Hashtable();
			slotVector.addElement(h1);
			slotVector.addElement(h2);
		}

    sp = new SlotPanel(slotVector);

    // aggregation panel
    aggregPanel = new JPanel(new BorderLayout());
    JPanel aggregInnerPanel = new JPanel(new GridBagLayout());
    KLabel aggregationLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "aggregationAllowed_text"));
    aggregationLabel.setPreferredSize(new Dimension(150, 20));
    aggTrue = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "true_text"), true);
    aggFalse = new KRadioButton(LangResources.getString(Mpiro.selectedLocale, "false_text"), false);
    ButtonGroup gag = new ButtonGroup();
    gag.add(aggTrue);
    gag.add(aggFalse);

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(0,1,0,1);
    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.NONE;
    c.weightx = 0.0; c.weighty = 0.0;
    c.gridx = 0; c.gridy = 0;
    aggregInnerPanel.add(aggregationLabel, c);
    c.gridx = 1;
    aggregInnerPanel.add(aggTrue, c);
    c.gridx = 2;
    aggregInnerPanel.add(aggFalse, c);
    aggregInnerPanel.setBorder(new LineBorder(Color.WHITE, 2));
    aggregPanel.add(BorderLayout.WEST, aggregInnerPanel);

    multiPanel = new JPanel(new BorderLayout());
    //if(finalEIG==1)
    //{
    //	if(advancedOptions)
    	//{
    		////System.out.println("eprepe na mpei!!!");
		multiPanel.add(BorderLayout.NORTH, aggregPanel);
    	//}
    //}
    //else if(finalEIG==2)
    //{
    	//if(advancedOptions)
    	//{
    	//	multiPanel.add(BorderLayout.NORTH, aggregPanel);
    	//}	//maria
    //}
    //else if(finalEIG==3)
    //{
    	//if(advancedOptions)		//maria
    	//{																		//maria
    	//	multiPanel.add(BorderLayout.NORTH, aggregPanel);
    	//}
    //}																		//maria
    multiPanel.add(BorderLayout.CENTER, sp);

		TemplateListener teli = new TemplateListener();
    //addMouseListener(teli);
    //addPopup(Template.this);

    /* The button panel (insert/delete) */
    JPanel insertDeletePanel = new JPanel(new GridBagLayout());
    insertDeletePanel.setBorder(new EmptyBorder(new Insets(5,0,0,0)));
    insertBeforeButton = new KButton(insertSlot_button, beforeSelected_button, true);
    insertAfterButton = new KButton(insertSlot_button, afterSelected_button, true);
    deleteButton = new KButton(removeSelected_button, slot_button, true);
    GridBagConstraints ca = new GridBagConstraints();
    ca.insets = new Insets(0,2,0,2);
    ca.anchor = GridBagConstraints.WEST;
    ca.fill = GridBagConstraints.HORIZONTAL;
    ca.weightx = 1.0; ca.weighty = 0.0;
    insertDeletePanel.add(insertBeforeButton, ca);
    ca.gridx = 1;
    insertDeletePanel.add(insertAfterButton, ca);
    ca.gridx = 2;
    insertDeletePanel.add(deleteButton, ca);

    /* Add the Action Listeners  */
    insertBeforeButton.addActionListener(Template.this);
    insertAfterButton.addActionListener(Template.this);
    deleteButton.addActionListener(Template.this);
    aggTrue.addActionListener(Template.this);
    aggFalse.addActionListener(Template.this);

    /* Adding everything in this */
    setLayout(new BorderLayout());
    add("North", multiPanel);
    //add("South", aggregPanel);
    add("South", insertDeletePanel);

		if (allFields.containsKey(m_microplanNumber + ":" + m_fieldName + ":Aggreg"))
		{
		  String aggregValue = allFields.get(m_microplanNumber + ":" + m_fieldName + ":Aggreg").toString();
		  ////System.out.println(aggregValue);
		  if (aggregValue.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "true_text")))
		  {
		  	aggTrue.setSelected(true);
		  }
		  else
		  {
		  	aggFalse.setSelected(true);
		  }
		}
		else //if there is no "Aggreg" value saved
		{
		  allFields.put(m_microplanNumber + ":" + m_fieldName + ":Aggreg", LangResources.getString(Mpiro.enLocale, "false_text"));
                  for(int x=0;x<=y;x++)
                  {
                     Hashtable h1=(Hashtable) counts.get(x);
                     h1.put(m_microplanNumber + ":" + m_fieldName + ":Aggreg", LangResources.getString(Mpiro.enLocale, "false_text"));
                     
                  }
                  aggFalse.setSelected(true);
		}

	} //constructor Template


	/* The actions for each popup-command */
	Action a1 = new AbstractAction("Insert slot before selected")
	{
		public void actionPerformed(ActionEvent e)
		{
			int lastChecked = sp.checkChecked(slotVector);
			if (lastChecked == -1)
			{}
			else
			{
				slotVector.insertElementAt(new Hashtable(), lastChecked);
				sp = new SlotPanel(slotVector);
				multiPanel.removeAll();
				multiPanel.add("North", aggregPanel);
				multiPanel.add("Center", sp);
				multiPanel.revalidate();
				multiPanel.repaint();
				Mpiro.needExportToExprimo = true;		//maria
			}
		}
  };
  
  Action a2 = new AbstractAction("Insert slot after selected")
  {
		public void actionPerformed(ActionEvent e)
		{
			int lastChecked = sp.checkChecked(slotVector);
			if (lastChecked == -1)
			{}
			else
			{
				slotVector.insertElementAt(new Hashtable(), lastChecked+1);
				sp = new SlotPanel(slotVector);
				multiPanel.removeAll();
				multiPanel.add("North", aggregPanel);
				multiPanel.add("Center", sp);
				multiPanel.revalidate();
				multiPanel.repaint();
				Mpiro.needExportToExprimo = true;		//maria
			}
		}
  };
  
  Action a3 = new AbstractAction("Remove selected slot")
  {
		public void actionPerformed(ActionEvent e)
		{
			int lastChecked = sp.checkChecked(slotVector);
			if (lastChecked == -1)
			{}
			else if(slotVector.size()==1)							//maria
			{  //gia na mh diagrafetai h monadikh 8esh				//maria
				System.out.println("size---"+slotVector.size());	//maria
			}														//maria
			else
			{
				slotVector.removeElementAt(lastChecked);
				sp = new SlotPanel(slotVector);
				multiPanel.removeAll();
				multiPanel.add("North", aggregPanel);
				multiPanel.add("Center", sp);
				multiPanel.revalidate();
				multiPanel.repaint();
				Mpiro.needExportToExprimo = true;		//maria
			}
		}
  };

	/* Same 3 actions also in buttons */
	public void actionPerformed(ActionEvent e) 
	{
	  if (e.getSource() == insertBeforeButton) 
	  {
			int lastChecked = sp.checkChecked(slotVector);
			if (lastChecked == -1)
			{}
			else
			{
				slotVector.insertElementAt(new Hashtable(), lastChecked);
				sp = new SlotPanel(slotVector);
				multiPanel.removeAll();
				multiPanel.add("North", aggregPanel);
				multiPanel.add("Center", sp);
				multiPanel.revalidate();
				multiPanel.repaint();
			}
		}

    if (e.getSource() == insertAfterButton) 
    {
			int lastChecked = sp.checkChecked(slotVector);
			if (lastChecked == -1)
			{}
			else
			{
				slotVector.insertElementAt(new Hashtable(), lastChecked+1);
				sp = new SlotPanel(slotVector);
				multiPanel.removeAll();
				multiPanel.add("North", aggregPanel);
				multiPanel.add("Center", sp);
				multiPanel.revalidate();
				multiPanel.repaint();
			}
		}

		if (e.getSource() == deleteButton) 
		{
			int lastChecked = sp.checkChecked(slotVector);
			if (lastChecked == -1)
			{}
			else if(slotVector.size()==1)							//maria
			{  //gia na h diagrafetai h monadikh 8esh				//maria
				System.out.println("size---"+slotVector.size());	//maria
			}														//maria
			else
			{
				slotVector.removeElementAt(lastChecked);
				sp = new SlotPanel(slotVector);
				multiPanel.removeAll();
                multiPanel.add("North", aggregPanel);
				multiPanel.add("Center", sp);
				multiPanel.revalidate();
				multiPanel.repaint();
			}
		}

		if (e.getSource() == aggTrue) 
		{
			allFields.put(m_microplanNumber + ":" + m_fieldName + ":Aggreg", LangResources.getString(Mpiro.enLocale, "true_text"));
			for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                     h1.put(m_microplanNumber + ":" + m_fieldName + ":Aggreg", LangResources.getString(Mpiro.enLocale, "true_text"));
                  
                        }
                        aggTrue.setSelected(true);
		}
		
		if (e.getSource() == aggFalse) 
                {
			allFields.put(m_microplanNumber + ":" + m_fieldName + ":Aggreg", LangResources.getString(Mpiro.enLocale, "false_text"));
			for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                     h1.put(m_microplanNumber + ":" + m_fieldName + ":Aggreg", LangResources.getString(Mpiro.enLocale, "false_text"));
                 
                        }
                        aggFalse.setSelected(true);
		}

	} // actionPerformed


	class SlotPanel extends JPanel 
	{
		Slot slot[] = null;

		public SlotPanel(Vector slotVector) 
		{
			setLayout(new ColumnLayout(0,0,0,0));
			
			slot = new Slot[200];
			ButtonGroup group = new ButtonGroup();				//maria
			for (int t = 0; t < slotVector.size(); t++)
			{
				slot[t] = new Slot(t);
				
				Hashtable slotValues = (Hashtable)slotVector.elementAt(t);
				if (!slotValues.containsKey("SELECTION"))
				{
					slotValues.put("SELECTION", "string");
				}
				String selection = slotValues.get("SELECTION").toString();
				if (selection.equalsIgnoreCase("string"))
				{
					if (slotValues.containsKey("string"))
					{
						String string = (String)slotValues.get("string");
						slot[t].stringField.setText(string);
					}
                                     
                                        
          else
          {
          	slotValues.put("string", "");
          }
                                        
                                        if (slotValues.containsKey("plural"))
					{
						String plural = (String)slotValues.get("plural");
						slot[t].pluralField.setText(plural);
					}
                                     
                                        
          else
          {
          	slotValues.put("plural", "");
          }
                                        if(slotValues.containsKey("verb")){
                                           if (slotValues.get("verb").toString().equalsIgnoreCase("true"))
					{
                                            slot[t].verb.setSelected(true);
                                            slot[t].tenseCombo.setSelectedItem(slotValues.get("tense"));
                                            slot[t].voiceCombo.setSelectedItem(slotValues.get("voice"));
                                            slot[t].tenseCombo.setEnabled(true);
                                            slot[t].voiceCombo.setEnabled(true);
                                            slot[t].pluralField.setEnabled(true);
                                            slot[t].pluralL.setEnabled(true);
                                        }}
                                           else{
                                            slotValues.put("verb","false");
                                            slotValues.put("tense","present");
                                            slotValues.put("voice","active");
                                            slot[t].verb.setSelected(false);
                                           }
                                        if(slotValues.containsKey("prep"))
                                           if (slotValues.get("prep").toString().equalsIgnoreCase("true"))
					
                                            slot[t].prep.setSelected(true);
                                          
                                           else
                                            slotValues.put("prep","false");
                                            
                                           
                                        
          slot[t].stringRadio.setSelected(true);					//maria
          slot[t].multiPanel.removeAll();
          GridBagConstraints gbc = new GridBagConstraints();
          gbc.insets = new Insets(0,1,0,1);
	        gbc.anchor = GridBagConstraints.WEST;
                //gbc.gridwidth = 4; gbc.gridheight = 3;
	        gbc.weightx = 0.0; gbc.weighty = 0.0;
	        gbc.gridx = 0; gbc.gridy = 0;
					//stringField.setText(values.get("string").toString());
					slot[t].multiPanel.add(slot[t].stringField,gbc);
                                        gbc.gridx=1;
                                        gbc.gridy=1;
                                        slot[t].multiPanel.add(slot[t].prep,gbc);
                                        gbc.gridy=0;
                                        slot[t].multiPanel.add(slot[t].verb,gbc);
                                        
                                        
                                     //   gbc.gridx=1;
                                        
                                    //    gbc.gridy=0;
                                        
                                     //   multiPanel.add(voiceL,c);
                                        gbc.gridx=2;
                                        gbc.gridy=0;
                                        slot[t].multiPanel.add(slot[t].voiceCombo,gbc);
                                      //  gbc.gridx=3;
                                      //  gbc.gridy=0;
                                      //  multiPanel.add(tenseL,c);
                                        gbc.gridy=0;
                                        gbc.gridx=3;
                                        slot[t].multiPanel.add(slot[t].tenseCombo,gbc);
                                        gbc.gridx=4;
                                         slot[t].multiPanel.add(slot[t].pluralL,gbc);
                                        gbc.gridx=5;
                                        slot[t].multiPanel.add(slot[t].pluralField,gbc);
          //slot[t].multiPanel.add(slot[t].stringField);
          //slot[t].multiPanel.add(slot[t].verb);
          //slot[t].multiPanel.add(slot[t].prep);
          //slot[t].multiPanel.add(slot[t].tenseCombo);
          //slot[t].multiPanel.add(slot[t].voiceCombo);
          if ((slotValues.containsKey("semantics")) ||
              (slotValues.containsKey("type")) ||
              (slotValues.containsKey("grCase")))
          {
		      	if (slotValues.containsKey("semantics"))
						{
          		String semantics = (String)slotValues.get("semantics");
              /*if(semantics.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "fieldOwner_text")))					//maria
              {																												//maria
              	//slot[t].semanticsCombo.setSelectedItem(semantics);														//maria
                  slot[t].semanticsCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "fieldOwner_text"));	//maria
              }																												//maria
              else if(semantics.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "fieldFiller_text")))				//maria
              {																												//maria
                  slot[t].semanticsCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "fieldFiller_text"));	//maria
              }	*/																											//maria																											//maria
						}
	          if (slotValues.containsKey("type"))
	          {
							String type = (String)slotValues.get("type");
							if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "auto_text")))									//maria
							{																												//maria
								//slot[t].typeCombo.setSelectedItem(type);																	//maria
							    slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "auto_text"));				//maria
							}																												//maria
							else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "name_text")))							//maria
							{																												//maria
								slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "name_text"));				//maria
							}																												//maria
							else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "pronoun_text")))							//maria
							{																												//maria
								slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "pronoun_text"));			//maria
							}																												//maria
							else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithDefiniteArticle_text")))			//maria
							{																												//maria
								slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithDefiniteArticle_text"));//maria
							}																												//maria
							else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithIndefiniteArticle_text")))		//maria
							{																												//maria
								slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithIndefiniteArticle_text"));//maria
							}																												//maria
						}
		       	if (slotValues.containsKey("grCase"))
		        {
							String grCase = (String)slotValues.get("grCase");
							if(grCase.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "nominative_text")))							//maria
							{																												//maria
								//slot[t].caseCombo.setSelectedItem(grCase);																//maria
								slot[t].caseCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "nominative_text"));		//maria
							}																												//maria
							else if(grCase.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "genitive_text")))						//maria
							{																												//maria
								slot[t].caseCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "genitive_text"));			//maria
							}																												//maria
							else if(grCase.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "accusative_text")))					//maria
							{																												//maria
								slot[t].caseCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "accusative_text"));		//maria
							}																												//maria
						}
					}
				}
				if (selection.equalsIgnoreCase("referring"))
				{
					boolean showNothing = false;							//maria
					if (slotValues.containsKey("semantics"))
					{
          	String semantics = (String)slotValues.get("semantics");
            if(semantics.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "fieldOwner_text")))						//maria
            {	
							slot[t].expressionForFieldOwnerRadio.setSelected(true);														//maria
							//slot[t].semanticsCombo.setSelectedItem(semantics);														//maria
							//slot[t].semanticsCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "fieldOwner_text"));	//maria
						}																												//maria
            else if(semantics.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "fieldFiller_text")))				//maria
            {
							if(slot[t].fillerTypeIsBuiltIn)										//maria
							{																//maria
								slot[t].fieldFillerRadio.setSelected(true);					//maria		
								showNothing = true;
							}																//maria
    					else															//maria
    					{																//maria
								slot[t].expressionForFieldFillerRadio.setSelected(true);	//maria								
							}																//maria
            	//slot[t].semanticsCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "fieldFiller_text"));//maria
          	}		
					}
          else
          {		
          	slot[t].expressionForFieldOwnerRadio.setSelected(true);		
          }			//maria	
          if (slotValues.containsKey("type"))
          {
						String type = (String)slotValues.get("type");
						if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "auto_text")))									//maria
						{																												//maria
							//slot[t].typeCombo.setSelectedItem(type);																	//maria
							slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "auto_text"));				//maria
						}																												//maria
						else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "name_text")))							//maria
						{																												//maria
							slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "name_text"));				//maria
						}																												//maria
						else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "pronoun_text")))							//maria
						{																												//maria
							slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "pronoun_text"));			//maria
						}																												//maria
						else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithDefiniteArticle_text")))			//maria
						{																												//maria
							slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithDefiniteArticle_text"));//maria
						}																												//maria
						else if(type.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "typeWithIndefiniteArticle_text")))		//maria
						{																												//maria
							slot[t].typeCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "typeWithIndefiniteArticle_text"));//maria
						}			
					}
          if (slotValues.containsKey("grCase"))
          {
						String grCase = (String)slotValues.get("grCase");
						if(grCase.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "nominative_text")))							//maria
						{																												//maria
							//slot[t].caseCombo.setSelectedItem(grCase);																//maria
							slot[t].caseCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "nominative_text"));		//maria
						}																												//maria
						else if(grCase.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "genitive_text")))						//maria
						{																												//maria
							slot[t].caseCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "genitive_text"));			//maria
						}																												//maria
						else if(grCase.equalsIgnoreCase(LangResources.getString(Mpiro.enLocale, "accusative_text")))					//maria
						{																												//maria
							slot[t].caseCombo.setSelectedItem(LangResources.getString(Mpiro.selectedLocale, "accusative_text"));		//maria
						}			
					}
					if (slotValues.containsKey("string"))
					{
						String string = (String)slotValues.get("string");
						slot[t].stringField.setText(string);
					}
                                        if (slotValues.containsKey("plural"))
					{
						String plural = (String)slotValues.get("plural");
						slot[t].pluralField.setText(plural);
					}
					//slot[t].expressionForFieldOwnerRadio.setSelected(true);		//maria
					slot[t].multiPanel.removeAll();
					if(!showNothing)												//maria
					{																//maria
						GridBagConstraints c = new GridBagConstraints();
						c.insets = new Insets(0,1,0,1);
						c.anchor = GridBagConstraints.WEST;
						c.weightx = 0.0; c.weighty = 0.0;
						c.gridx = 0; c.gridy = 0;
						//slot[t].multiPanel.add(slot[t].semanticsL, c);			//maria
						slot[t].multiPanel.add(slot[t].typeL, c);					//maria
						c.gridy = 1;
						//slot[t].multiPanel.add(slot[t].semanticsCombo, c);		//maria
						slot[t].multiPanel.add(slot[t].typeCombo, c);				//maria
						c.gridx = 1; c.gridy = 0;
						//slot[t].multiPanel.add(slot[t].typeL, c);					//maria
						slot[t].multiPanel.add(slot[t].caseL, c);					//maria
						c.gridy = 1;
						//slot[t].multiPanel.add(slot[t].typeCombo, c);				//maria
						slot[t].multiPanel.add(slot[t].caseCombo, c);				//maria
						//if (finalEIG == 1 || finalEIG == 2)
						//{
						//}
						//else if (finalEIG == 3)
						//{
						//c.gridx = 2; c.gridy = 0;							//maria
						//slot[t].multiPanel.add(slot[t].caseL, c);			//maria
						//c.gridy = 1;										//maria
						//slot[t].multiPanel.add(slot[t].caseCombo, c);		//maria
						//}
					}
				}
        //}           
				group.add(slot[t].selectSlot);			//maria
            		
				slot[t].multiPanel.revalidate();
				slot[t].multiPanel.repaint();
				add(slot[t]);
			}//for
			revalidate();
			repaint();
		} // constructor

		/* a method that checks which slot is checked(!) */
		public int checkChecked(Vector sv) 
		{
			int checkNo=-1;
			for (int tot=0; tot<sv.size(); tot++) 
			{
				Slot slo = this.slot[tot];
				if (slo.selectSlot.isSelected()) 
				{
					//System.out.println(tot);
					checkNo = tot;
				}
			}
			//System.out.println("checkN0 is: " + checkNo);
			return checkNo;
		}

	}//class SlotPanel

  class Slot extends JPanel implements FocusListener 
  {
	  JPanel linePanel;
	  JPanel multiPanel;
	  JPanel labelPanel;
	  KLabel slotL;
	  KLabel slotNumberL;
	  JRadioButton selectSlot; // CheckBox indicating if a slot is selected		//maria
	  KLabel semanticsL;
	  KLabel typeL;
	  KLabel caseL;
          KLabel pluralL;
         // KLabel voiceL;
         // KLabel tenseL;
	  JRadioButton stringRadio;						//maria
	  JRadioButton expressionForFieldOwnerRadio;	//maria
	  JRadioButton expressionForFieldFillerRadio;	//maria
	  JRadioButton fieldFillerRadio;				//maria
	  JRadioButton verb;
          JRadioButton prep;
          JTextField stringField;
          JTextField pluralField;
	  //KComboBox semanticsCombo;					//maria
	  KComboBox typeCombo;
	  KComboBox caseCombo;
          KComboBox voiceCombo;
          KComboBox tenseCombo;
  
		String fieldFillerType;						//maria
		boolean fillerTypeIsBuiltIn = false;			//maria
		
		Hashtable values;
		int slotPosition;

		public Slot(int aa)
		{
			String slot_text = LangResources.getString(Mpiro.selectedLocale, "slot_text");
			String string_text = LangResources.getString(Mpiro.selectedLocale, "string_text");
			//String referringExpression_text = LangResources.getString(Mpiro.selectedLocale, "referringExpression_text");	//maria
			String referringOwnerExpression_text = LangResources.getString(Mpiro.selectedLocale, "referringOwnerExpression_text");	//maria
			String referringFillerExpression_text = LangResources.getString(Mpiro.selectedLocale, "referringFillerExpression_text");//maria
			String fieldFiller_text = LangResources.getString(Mpiro.selectedLocale, "fieldFiller_text");					//maria
			String semantics_text = LangResources.getString(Mpiro.selectedLocale, "semantics_text");
			String type_text = LangResources.getString(Mpiro.selectedLocale, "type_text");
			String case_text = LangResources.getString(Mpiro.selectedLocale, "case_text");
			
			String nodeName = DataBasePanel.last.toString();									//maria
			NodeVector nodeVector = (NodeVector)QueryHashtable.mainDBHashtable.get(nodeName);	//maria
			Vector nodeTable = (Vector)nodeVector.elementAt(0);									//maria
			for(int j=0; j<nodeTable.size(); j++)												//maria
			{																					//maria
				FieldData ff = (FieldData)nodeTable.elementAt(j);								//maria
				if(ff.elementAt(0).toString().equalsIgnoreCase(m_fieldName))					//maria
				{																				//maria
					fieldFillerType = ff.elementAt(1).toString();								//maria
					//System.out.println("fieldFillerType-----"+fieldFillerType);				//maria
				}																				//maria
			}
			if ((fieldFillerType.equalsIgnoreCase("Number"))||		//maria
      		(fieldFillerType.equalsIgnoreCase("String"))||		//maria
      		(fieldFillerType.equalsIgnoreCase("Date"))||		//maria
      		(fieldFillerType.equalsIgnoreCase("Dimension")))	//maria
			{		
				fillerTypeIsBuiltIn = true;		
			}				//maria					

	    slotPosition = aa+1;
	    linePanel = new JPanel();  // Panel to be used as separation lines
	    multiPanel = new JPanel(new GridBagLayout());    // Panel of changing content,
	
	    // JPanel on the left of slot
	    labelPanel = new JPanel(new GridBagLayout());
	    selectSlot = new JRadioButton();					//maria
	    GridBagConstraints cc = new GridBagConstraints();
	    cc.insets = new Insets(-3,0,-3,0);
	    cc.anchor = GridBagConstraints.WEST;
	    cc.fill = GridBagConstraints.NONE;
	    cc.weightx = 0.0; cc.weighty = 0.0;
	    slotL = new KLabel(slot_text);
	    // Make a string from Integer
	    Integer f = new Integer(slotPosition);
	    String value = f.toString();
	    slotNumberL = new KLabel(value);
	    cc.gridwidth = 2;
	    labelPanel.add(slotL, cc);
      cc.gridwidth = 1;
      cc.gridy = 1;
      labelPanel.add(slotNumberL, cc);
      cc.gridx = 1;
      labelPanel.add(selectSlot, cc);

      semanticsL = new KLabel(semantics_text);
      typeL = new KLabel(type_text);
      caseL = new KLabel(case_text);
      pluralL= new KLabel("plural:");
     // voiceL= new KLabel("voice");
     // tenseL= new KLabel("tense");
      stringRadio = new JRadioButton(string_text);					//maria
      expressionForFieldOwnerRadio = new JRadioButton(referringOwnerExpression_text);	//maria
      expressionForFieldFillerRadio = new JRadioButton(referringFillerExpression_text);	//maria
      fieldFillerRadio = new JRadioButton(fieldFiller_text);			//maria
      ButtonGroup bg = new ButtonGroup();
      
      bg.add(stringRadio);											//maria
      bg.add(expressionForFieldOwnerRadio);							//maria
      if(fillerTypeIsBuiltIn)											//maria
      {																//maria
      	bg.add(fieldFillerRadio);									//maria
      }																//maria
      else															//maria
      {																//maria
      	bg.add(expressionForFieldFillerRadio);						//maria
      }																//maria
      stringField = new JTextField(14);
      pluralField = new JTextField(11);
      //semanticsCombo = new KComboBox(semanticsChoice);				//maria
      typeCombo = new KComboBox(typeChoice);
      caseCombo = new KComboBox(caseChoice);
      
      voiceCombo = new KComboBox(voiceChoice);
      tenseCombo = new KComboBox(tenseChoice);
      verb= new JRadioButton();
      prep= new JRadioButton();
      //verb.setSelected(false);
      prep.setText("Preposition");
      verb.setText("verb");
      voiceCombo.setEnabled(false);
      tenseCombo.setEnabled(false);
      pluralL.setEnabled(false);
                                pluralField.setEnabled(false);
      /* Formating works */
      /* Borders & Sizes */
      labelPanel.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
      labelPanel.setPreferredSize(new Dimension(30,40));
      linePanel.setBorder(new LineBorder(new Color(250,250,250), 1));
      //linePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
      linePanel.setPreferredSize(new Dimension(370,3));
      /* Fonts */
      slotNumberL.setFont(new Font(Mpiro.selectedFont, Font.BOLD, 14));
      stringRadio.setFont(smallFont);								//maria
      expressionForFieldOwnerRadio.setFont(smallFont);			//maria
      expressionForFieldFillerRadio.setFont(smallFont);			//maria
      fieldFillerRadio.setFont(smallFont);						//maria
      stringField.setFont(font);
      pluralField.setFont(font);

      /* The layout manager for each Slot */
      setLayout(new GridBagLayout());
      /* Put the elements of a slot together */
      GridBagConstraints c = new GridBagConstraints();
      c.insets = new Insets(0,5,0,5);
      c.anchor = GridBagConstraints.WEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.0; c.weighty = 0.0;
      c.gridwidth = 1; c.gridheight = 3;	//maria(to 3)
      c.gridx = 0; c.gridy = 0;
      add(labelPanel, c);
      c.gridheight = 1;
      c.gridx = 1;
      add(stringRadio, c);						//maria
      c.gridy = 1;
      add(expressionForFieldOwnerRadio, c);		//maria
      c.gridy = 2;								//maria
      if(fillerTypeIsBuiltIn)						//maria
      {											//maria
      	add(fieldFillerRadio, c);				//maria
      }											//maria
      else										//maria
      {											//maria
      	add(expressionForFieldFillerRadio, c);	//maria
      }											//maria
      c.gridheight = 2;
      c.gridx = 2;	c.gridy = 0;
      add(multiPanel, c);
      c.gridwidth = 3; c.gridheight = 1;
      c.gridx = 0;	c.gridy = 3;	//maria(to 3)
      add(linePanel, c);

      values = (Hashtable)slotVector.elementAt(slotPosition-1);

			/* Add the ActionListeners */
			stringRadio.addActionListener(new ActionListener() 
			{			//maria
				public void actionPerformed(ActionEvent e) 
				{
					multiPanel.removeAll();
                                        
                                        GridBagConstraints c = new GridBagConstraints();
	        c.insets = new Insets(0,1,0,1);
	        c.anchor = GridBagConstraints.WEST;
                //c.gridwidth = 4; c.gridheight = 3;
	        c.weightx = 0.0; c.weighty = 0.0;
	        c.gridx = 0; c.gridy = 0;
					//stringField.setText(values.get("string").toString());
					multiPanel.add(stringField,c);
                                        c.gridx=1;
                                        c.gridy=1;
                                        multiPanel.add(prep,c);
                                        c.gridy=0;
                                        multiPanel.add(verb,c);
                                        
                                        
                                     //   c.gridx=1;
                                        
                                    //    c.gridy=0;
                                        
                                     //   multiPanel.add(voiceL,c);
                                        c.gridx=2;
                                        c.gridy=0;
                                        multiPanel.add(voiceCombo,c);
                                      //  c.gridx=3;
                                      //  c.gridy=0;
                                      //  multiPanel.add(tenseL,c);
                                        c.gridy=0;
                                        c.gridx=3;
                                        multiPanel.add(tenseCombo,c);
                                        c.gridx=4;
                                         multiPanel.add(pluralL,c);
                                        c.gridx=5;
                                        multiPanel.add(pluralField,c);
					multiPanel.revalidate();
					multiPanel.repaint();
					values.put("SELECTION", "string");
                                       // values.put("verb",)
					slotVector.setElementAt(values, slotPosition-1);
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                                        for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                      
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }
				}
			});
			
			expressionForFieldOwnerRadio.addActionListener(new ActionListener() 
			{		//maria
				public void actionPerformed(ActionEvent e) 
				{
	        multiPanel.removeAll();
	        //semanticsCombo.setSelectedItem(values.get("semantics"));
	        //typeCombo.setSelectedItem(values.get("type").toString());
	        //caseCombo.setSelectedItem(values.get("grCase").toString());
	        GridBagConstraints c = new GridBagConstraints();
	        c.insets = new Insets(0,1,0,1);
	        c.anchor = GridBagConstraints.WEST;
	        c.weightx = 0.0; c.weighty = 0.0;
	        c.gridx = 0; c.gridy = 0;
	        //multiPanel.add(semanticsL, c);			//maria
	        multiPanel.add(typeL, c);					//maria
	        c.gridy = 1;
	        //multiPanel.add(semanticsCombo, c);		//maria
	        multiPanel.add(typeCombo, c);				//maria
	        c.gridx = 1; c.gridy = 0;
	        //multiPanel.add(typeL, c);				//maria
	        multiPanel.add(caseL, c);					//maria
	        c.gridy = 1;
	        //multiPanel.add(typeCombo, c);			//maria
	        multiPanel.add(caseCombo, c);				//maria
	        //c.gridx = 2; c.gridy = 0;				//maria
	        //multiPanel.add(caseL, c);				//maria
	        //c.gridy = 1;							//maria
	        //multiPanel.add(caseCombo, c);			//maria
	        //}
	        multiPanel.revalidate();
	        multiPanel.repaint();
          //System.out.println("values before------"+values);
          values.put("SELECTION", "referring");
          values.put("semantics", LangResources.getString(Mpiro.enLocale, "fieldOwner_text"));	//maria
          //System.out.println("values after------"+values);
          ////System.out.println("slotvector before------"+slotVector);
          slotVector.setElementAt(values, slotPosition-1);
          ////System.out.println("slotvector after------"+slotVector);
          ////System.out.println("allfields before------"+allFields);
          Mpiro.needExportToExprimo=true;
          allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
          for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                      
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }
          ////System.out.println("allfields after------"+allFields);
				}
			});
            
			expressionForFieldFillerRadio.addActionListener(new ActionListener() 
			{		//maria
				public void actionPerformed(ActionEvent e) 
				{						//maria
					multiPanel.removeAll();									//maria
					GridBagConstraints c = new GridBagConstraints();			//maria
					c.insets = new Insets(0,1,0,1);							//maria
					c.anchor = GridBagConstraints.WEST;						//maria
					c.weightx = 0.0; c.weighty = 0.0;							//maria
					c.gridx = 0; c.gridy = 0;									//maria
					multiPanel.add(typeL, c);									//maria	
					c.gridy = 1;												//maria
					multiPanel.add(typeCombo, c);								//maria
					c.gridx = 1; c.gridy = 0;									//maria
					multiPanel.add(caseL, c);									//maria
					c.gridy = 1;												//maria
					multiPanel.add(caseCombo, c);								//maria
					multiPanel.revalidate();									//maria
					multiPanel.repaint();										//maria
					//System.out.println("values before------"+values);			//maria
					values.put("SELECTION", "referring");						//maria
					values.put("semantics", LangResources.getString(Mpiro.enLocale, "fieldFiller_text"));	//maria
					//System.out.println("values after------"+values);			//maria
					slotVector.setElementAt(values, slotPosition-1);			//maria
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);	//maria
                                        for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                      
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }
				}																	//maria
			});																			//maria

			fieldFillerRadio.addActionListener(new ActionListener() 
			{											//maria
				public void actionPerformed(ActionEvent e) 
				{													//maria
					multiPanel.removeAll();																		//maria
					multiPanel.revalidate();																	//maria
					multiPanel.repaint();																		//maria
					System.out.println("values before------"+values);											//maria
					values.put("SELECTION", "referring");														//maria
					values.put("semantics", LangResources.getString(Mpiro.enLocale, "fieldFiller_text"));		//maria          
					values.put("type",LangResources.getString(Mpiro.enLocale, "name_text"));					//maria
					values.put("grCase", LangResources.getString(Mpiro.enLocale, "nominative_text"));			//maria
					System.out.println("values after------"+values);											//maria
					slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                     
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }//maria
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);							//maria
				}																								//maria
			});																									//maria
			//values.put("SELECTION", "string");
			//System.out.println("---------------- " + values);
                        verb.addActionListener(new ActionListener() 
                        {
                            public void actionPerformed(ActionEvent e) 
				{
                            if (verb.isSelected()){
                                values.put("verb","true");
                                values.put("prep","false");
                                values.put("voice",voiceCombo.getSelectedItem().toString());
                                values.put("tense",tenseCombo.getSelectedItem().toString());
                                voiceCombo.setEnabled(true);
                                tenseCombo.setEnabled(true);
                                pluralL.setEnabled(true);
                                pluralField.setEnabled(true);
                                prep.setSelected(false);
                                
                            }
                            else{
                                 values.put("verb","false");
                                 values.put("voice",voiceCombo.getSelectedItem().toString());
                                values.put("tense",tenseCombo.getSelectedItem().toString());
                                voiceCombo.setEnabled(false);
                                tenseCombo.setEnabled(false);
                                pluralL.setEnabled(false);
                                pluralField.setEnabled(false);
                            }
                            slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                      
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }//maria
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                        }});
                        
                        prep.addActionListener(new ActionListener() 
                        {
                            public void actionPerformed(ActionEvent e) 
				{
                         if (prep.isSelected()){
                             values.put("prep","true");
                             values.put("verb","false");
                             verb.setSelected(false);
                              voiceCombo.setEnabled(false);
                                tenseCombo.setEnabled(false);
                                pluralL.setEnabled(false);
                                pluralField.setEnabled(false);
                         }
                         else
                             values.put("prep","false");
			}});
			stringField.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					values.put("string", stringField.getText());
					//System.out.println(m_fieldName);
					//System.out.println("SLOT " + (slotPosition-1) + " " + values);
					slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                     
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  } Mpiro.needExportToExprimo=true;
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
				}
			});
                        
                        pluralField.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					values.put("plural", pluralField.getText());
					//System.out.println(m_fieldName);
					//System.out.println("SLOT " + (slotPosition-1) + " " + values);
					slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                     
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  } Mpiro.needExportToExprimo=true;
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
				}
			});

			stringField.addFocusListener(Slot.this);
                        pluralField.addFocusListener(Slot.this);
                       // verb.addFocusListener(Slot.this);
			
			/*semanticsCombo.addActionListener(new ActionListener() 
			*{
			  public void actionPerformed(ActionEvent e) 
			  {
			  	if(semanticsCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "fieldOwner_text")))			//maria
			  	{																																			//maria
			    	//values.put("semantics", semanticsCombo.getSelectedItem());																			//maria
			        values.put("semantics", LangResources.getString(Mpiro.enLocale, "fieldOwner_text"));													//maria
			    }																																			//maria
			    else if(semanticsCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "fieldFiller_text")))	//maria
			  	{																																			//maria
			        values.put("semantics", LangResources.getString(Mpiro.enLocale, "fieldFiller_text"));													//maria
			    }																																			//maria
			      //System.out.println("SLOT " + (slotPosition-1) + " " + values);																	
			      slotVector.setElementAt(values, slotPosition-1);																					
			      allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);																	
			  }
			});*/			//maria
            
			typeCombo.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					if(typeCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "auto_text")))						//maria
					{																																			//maria
						//values.put("type", typeCombo.getSelectedItem());																						//maria
						values.put("type",LangResources.getString(Mpiro.enLocale, "auto_text"));																//maria
					}																																			//maria
					else if(typeCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "name_text")))				//maria
					{																																			//maria
						values.put("type",LangResources.getString(Mpiro.enLocale, "name_text"));																//maria
					}																																			//maria
					else if(typeCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "pronoun_text")))				//maria
					{																																			//maria
						values.put("type",LangResources.getString(Mpiro.enLocale, "pronoun_text"));																//maria
					}																																			//maria
					else if(typeCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "typeWithDefiniteArticle_text")))//maria
					{																																			//maria
						values.put("type",LangResources.getString(Mpiro.enLocale, "typeWithDefiniteArticle_text"));												//maria
					}																																			//maria
					else if(typeCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "typeWithIndefiniteArticle_text")))//maria
					{																																			//maria
						values.put("type",LangResources.getString(Mpiro.enLocale, "typeWithIndefiniteArticle_text"));											//maria
					}																																			//maria
					//System.out.println("SLOT " + (slotPosition-1) + " " + values);
					slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                     Hashtable h1=(Hashtable) counts.get(x);
                    
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  } Mpiro.needExportToExprimo=true;
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
				}
			});
			
			caseCombo.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					if(caseCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "nominative_text")))				//maria
					{																																			//maria
						//values.put("grCase", caseCombo.getSelectedItem());																					//maria
						values.put("grCase", LangResources.getString(Mpiro.enLocale, "nominative_text"));														//maria
					}																																			//maria
					else if(caseCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "genitive_text")))			//maria
					{																																			//maria
						values.put("grCase", LangResources.getString(Mpiro.enLocale, "genitive_text"));															//maria
					}																																			//maria
					else if(caseCombo.getSelectedItem().toString().equalsIgnoreCase(LangResources.getString(Mpiro.selectedLocale, "accusative_text")))			//maria
					{																																			//maria
						values.put("grCase", LangResources.getString(Mpiro.enLocale, "accusative_text"));														//maria
					}																																			//maria
					//System.out.println("SLOT " + (slotPosition-1) + " " + values);
					slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                      
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }Mpiro.needExportToExprimo=true;
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
				}
			});
                        
                        tenseCombo.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					if(tenseCombo.getSelectedItem().toString().equalsIgnoreCase("past"))						//maria
					{																																			//maria
						//values.put("type", typeCombo.getSelectedItem());																						//maria
						values.put("tense","past");																//maria
					}																																			//maria
					if(tenseCombo.getSelectedItem().toString().equalsIgnoreCase("present"))						//maria
					{																																			//maria
						//values.put("type", typeCombo.getSelectedItem());																						//maria
						values.put("tense","present");																//maria
					}																																			//maria
					//System.out.println("SLOT " + (slotPosition-1) + " " + values);
                                        if(tenseCombo.getSelectedItem().toString().equalsIgnoreCase("future"))						//maria
					{																																			//maria
						//values.put("type", typeCombo.getSelectedItem());																						//maria
						values.put("tense","future");																//maria
					}
					slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                     Hashtable h1=(Hashtable) counts.get(x);
                     
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }Mpiro.needExportToExprimo=true;
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
				}
			});
                        
                        voiceCombo.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					if(voiceCombo.getSelectedItem().toString().equalsIgnoreCase("passive"))						//maria
					{																																			//maria
						//values.put("type", typeCombo.getSelectedItem());																						//maria
						values.put("voice","passive");																//maria
					}																																			//maria
					if(voiceCombo.getSelectedItem().toString().equalsIgnoreCase("active"))						//maria
					{																																			//maria
						//values.put("type", typeCombo.getSelectedItem());																						//maria
						values.put("voice","active");																//maria
					}																																			//maria
					//System.out.println("SLOT " + (slotPosition-1) + " " + values);
                                     
					slotVector.setElementAt(values, slotPosition-1);
                                        for(int x=0;x<=y;x++)
                  {
                     Hashtable h1=(Hashtable) counts.get(x);
                     
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }
                                        Mpiro.needExportToExprimo=true;
					allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
				}
			});

		} // constructor Slot


		public void focusGained(FocusEvent fe) 
		{}

		public void focusLost(FocusEvent fe) 
		{
			if (fe.getSource() == stringField) 
			{
				values.put("string", stringField.getText());
				slotVector.setElementAt(values, slotPosition-1);
                                for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                      
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }Mpiro.needExportToExprimo=true;
				allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
			}
                        
                        if (fe.getSource() == pluralField) 
			{
				values.put("plural", pluralField.getText());
				slotVector.setElementAt(values, slotPosition-1);
                                for(int x=0;x<=y;x++)
                  {
                      Hashtable h1=(Hashtable) counts.get(x);
                      
                     h1.put(m_microplanNumber + ":" + m_fieldName, slotVector);
                  }Mpiro.needExportToExprimo=true;
				allFields.put(m_microplanNumber + ":" + m_fieldName, slotVector);
			}
		}

	} // class Slot


	class TemplateListener extends MouseAdapter implements MouseListener
	{
		public TemplateListener(){}  // constructor
		
		public void mouseClicked(MouseEvent me)  
		{
			if (SwingUtilities.isRightMouseButton(me)) 
			{
				int x = me.getX();
				int y = me.getY();
				//System.out.println(x + " " + y);
				popup.show(Template.this, x, y);
			}
		}
	}

  public void addPopup(JPanel x) 
  {
		popup = new JPopupMenu();
		popup.add(a1);
		popup.add(a2);
		popup.add(a3);
		
		x.add(popup);
	}

} //class Template