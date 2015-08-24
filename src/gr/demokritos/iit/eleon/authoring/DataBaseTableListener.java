/***************

<p>Title: DataBaseTableListener</p>

<p>Description: The listener used by DataBaseTable
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Kostas Stamatakis (2002)
@author Dimitris Spiliotopoulos (2002)
@author Maria Prospathopoulou
@author Theofilos Nickolaou
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)

***************/


package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.EditFieldProperties;
import gr.demokritos.iit.eleon.ui.KDialog;
import gr.demokritos.iit.eleon.ui.MessageDialog;
import gr.demokritos.iit.eleon.ui.RestrictionsDialog;
import gr.demokritos.iit.eleon.ui.lang.en.EnglishMicroPanel;
import gr.demokritos.iit.eleon.ui.lang.gr.GreekMicroPanel;
import gr.demokritos.iit.eleon.ui.lang.it.ItalianMicroPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;


public class DataBaseTableListener extends MouseAdapter
{
	public static String selectedField;
	static String selectedLanguage;
	JPopupMenu popupUser;
	JPopupMenu popupUserSpecial;
	JPopupMenu popupSystem;
        JPopupMenu popupInheritedProperties;
	public static int rowNo = -1;
	static int colNo;
	protected int rowActive; // row-number where the user-popup-menu starts to appear
	
	static EnglishMicroPanel emp;
	static ItalianMicroPanel imp;
	static GreekMicroPanel gmp;

	// LangResources
	private String englishVersionOf_text = LangResources.getString(Mpiro.selectedLocale, "englishVersionOf_text");
	private String italianVersionOf_text = LangResources.getString(Mpiro.selectedLocale, "italianVersionOf_text");
	private String greekVersionOf_text = LangResources.getString(Mpiro.selectedLocale, "greekVersionOf_text");
	private String microplan_text = LangResources.getString(Mpiro.selectedLocale, "microplan_text");
	private String forField_text = LangResources.getString(Mpiro.selectedLocale, "forField_text");
	private String editMicroplanOneMenu_action = LangResources.getString(Mpiro.selectedLocale, "editMicroplanOneMenu_action");
	private String editMicroplanTwoMenu_action = LangResources.getString(Mpiro.selectedLocale, "editMicroplanTwoMenu_action");
	private String editMicroplanThreeMenu_action = LangResources.getString(Mpiro.selectedLocale, "editMicroplanThreeMenu_action");
	private String editMicroplanFourMenu_action = LangResources.getString(Mpiro.selectedLocale, "editMicroplanFourMenu_action");
	private String editMicroplanFiveMenu_action = LangResources.getString(Mpiro.selectedLocale, "editMicroplanFiveMenu_action");
	private String english_action = LangResources.getString(Mpiro.selectedLocale, "english_action");
	private String italian_action = LangResources.getString(Mpiro.selectedLocale, "italian_action");
	private String greek_action = LangResources.getString(Mpiro.selectedLocale, "greek_action");
	private String appropriateness_action = LangResources.getString(Mpiro.selectedLocale, "appropriateness_action");

	JMenu editMicroplanOneMenu = new JMenu(editMicroplanOneMenu_action);
	JMenu editMicroplanTwoMenu = new JMenu(editMicroplanTwoMenu_action);
	JMenu editMicroplanThreeMenu = new JMenu(editMicroplanThreeMenu_action);
	JMenu editMicroplanFourMenu = new JMenu(editMicroplanFourMenu_action);
	JMenu editMicroplanFiveMenu = new JMenu(editMicroplanFiveMenu_action);
        JMenu editMicroplanSixMenu = new JMenu("edit field properties");


	/**
	 * The constructor: Using an integer to note which row is currently active
	 * @param rowActive An int showing the active row in the DataBaseTable
	 */
	public DataBaseTableListener(int rowActive) 
	{
		this.rowActive = rowActive;
	}

	/**
	 * A mouseReleased to account for Unix mouseReleased==MousePressed situation
	 * @param re The MouseEvent
	 */
	public void mouseReleased (MouseEvent re)  
	{
		if (SwingUtilities.isRightMouseButton(re)) 
		{
				mousePressed(re);
		}
	}


	/**
	 * A mousePressed for getting the popup menu
	 * @param re The MouseEvent
	 */
	public void mousePressed (MouseEvent re)
	{
	  if (SwingUtilities.isRightMouseButton(re))
	  {
	    if(re.isPopupTrigger()) 
	    {
	      // Get the exact point of the click
	      int x = re.getX();
	      int y = re.getY();
	      Point p = new Point(x,y);
	      rowNo = DataBaseTable.dbTable.rowAtPoint(p);
	      colNo = DataBaseTable.dbTable.columnAtPoint(p);
	      DataBaseTable.dbTable.getSelectionModel().setLeadSelectionIndex(rowNo);
	      DataBaseTable.dbTable.clearSelection();
	
	      // Put the general preview at the preview-area
	      TreePreviews.generalDataBasePreview();
	
	      // Determine which field and language are selected
	      selectedField = DataBaseTable.dbTable.getValueAt(rowNo, 0).toString();
	
	      // only for right-click
	      setPopups();

        // determine the row for the popupUser
        if (rowNo >= rowActive)
        {
        	popupUser.show(DataBaseTable.dbTable, re.getX(), re.getY());
        }
        else if (DataBasePanel.label01.getText().equalsIgnoreCase("Basic-entity-types"))
        {
        }
        else if (rowNo == 0)//maria
        {
        	popupUserSpecial.show(DataBaseTable.dbTable, re.getX(), re.getY());
        }
        else if (rowNo<8)
        {
        	popupSystem.show(DataBaseTable.dbTable, re.getX(), re.getY());
        }
        else{
                  popupInheritedProperties.show(DataBaseTable.dbTable, re.getX(), re.getY());
        }
			}
			DataBasePanel.microPlanPanel.setVisible(false);//theofilos
		}
		else // left has been clicked
		{
			//if (re.getClickCount() < 2)
			//{
	    // Get the exact point of the click
	    int x = re.getX();
	    int y = re.getY();
	    Point p = new Point(x,y);
	    rowNo = DataBaseTable.dbTable.rowAtPoint(p);
	    colNo = DataBaseTable.dbTable.columnAtPoint(p);
	    DataBaseTable.dbTable.getSelectionModel().setLeadSelectionIndex(rowNo);
	
	    // Put the general preview at the preview-area
	    TreePreviews.generalDataBasePreview();
	
	    // Determine which field and language are selected
	    selectedField = DataBaseTable.dbTable.getValueAt(rowNo, 0).toString();

      /*if ( (colNo == 3) && (rowNo >= rowActive) )
      {
          UserModelDialog umDialog = new UserModelDialog("Select Images ",
                                      null,
                                      "New Images",
                                      null, //new Vector(),
                                      "",
                                      "ENTITY-TYPE");

      }*/
			//} // if (re.getClickCount)
			if ((rowNo >= rowActive) && ((DataBasePanel.im == DataBasePanel.ICON_BOOK) //theofilos
            	|| (DataBasePanel.im == DataBasePanel.ICON_BASIC)))//theofilos
			{
	    	DataBasePanel.microPlanPanel.setVisible(true);//theofilos
	    	DataBasePanel.microPlanNum.setAction(chosenMicroLangNumAndApprop);//theofilos
	    	DataBasePanel.microPlanFlags.setAction(chosenMicroLangNumAndApprop);//theofilos
	    	DataBasePanel.microPlanApprop.setAction(chosenMicroLangNumAndApprop);//theofilos
	    	DataBasePanel.microPlanApprop.setText(LangResources.getString(
	    			Mpiro.selectedLocale, "appropriateness_action")); //theofilos
	    	
	    	int numLang = DataBasePanel.microPlanFlags.getSelectedIndex();   //theofilos
				if (numLang == 0)    //theofilos
				{   //theofilos
					DataBasePanel.microLangText.setText(englishVersionOf_text); //theofilos 
				}   //theofilos
				if (numLang == 1)    //theofilos
				{   //theofilos
					DataBasePanel.microLangText.setText(italianVersionOf_text); //theofilos
				}   //theofilos 
				if (numLang == 2)    //theofilos
				{   //theofilos
					DataBasePanel.microLangText.setText(greekVersionOf_text); //theofilos
				}		   //theofilos
            	
				DataBasePanel.microLayoutText.setText(microplan_text); //theofilos
				DataBasePanel.microField.setText(forField_text + "\""  //theofilos
														+ selectedField + "\""); //theofilos
				String microNum = (String) DataBasePanel.microPlanNum.getSelectedItem();   //theofilos
				//0 english, 1 italian, 2 greek
				int nLang = DataBasePanel.microPlanFlags.getSelectedIndex();   //theofilos
				String lang = "";    //theofilos
				if (nLang == 0)    //theofilos
				{   //theofilos
					lang = english_action;   //theofilos
					DataBasePanel.microLangText.setText(englishVersionOf_text); //theofilos 
				}   //theofilos
				if (nLang == 1)    //theofilos
				{   //theofilos
					lang = italian_action;   //theofilos
					DataBasePanel.microLangText.setText(italianVersionOf_text); //theofilos
				}   //theofilos   
				if (nLang == 2)    //theofilos
				{   //theofilos
					lang = greek_action;   //theofilos
					DataBasePanel.microLangText.setText(greekVersionOf_text); //theofilos
				}		   //theofilos
				editMicroplan(microNum,lang);  //theofilos
			}   //theofilos
          
			else 
			{//theofilos
				DataBasePanel.microPlanPanel.setVisible(false);//theofilos
			}//theofilos
		}
	};

	Action chosenMicroLangNumAndApprop = new AbstractAction()   //theofilos
	{   //theofilos
		public void actionPerformed(ActionEvent e)   //theofilos
		{   //theofilos
			String microNum = (String) DataBasePanel.microPlanNum.getSelectedItem();   //theofilos
			//0 english, 1 italian, 2 greek
			int nLang = DataBasePanel.microPlanFlags.getSelectedIndex();   //theofilos
			String lang = "";    //theofilos
			if (nLang == 0)    //theofilos
			{   //theofilos
				lang = english_action;   //theofilos
				DataBasePanel.microLangText.setText(englishVersionOf_text); //theofilos 
			}   //theofilos
			if (nLang == 1)    //theofilos
			{   //theofilos
				lang = italian_action;   //theofilos
				DataBasePanel.microLangText.setText(italianVersionOf_text); //theofilos
			}   //theofilos   
			if (nLang == 2)    //theofilos
			{   //theofilos
				lang = greek_action;   //theofilos
				DataBasePanel.microLangText.setText(greekVersionOf_text); //theofilos
			}		   //theofilos
			if (e.getSource() == DataBasePanel.microPlanApprop)   //theofilos
			{   //theofilos
				lang = appropriateness_action;   //theofilos
			}   //theofilos
			editMicroplan(microNum,lang);   //theofilos
		}   //theofilos
	};   //theofilos

	/**
	* The function for editing the microplans      //theofilos
	* @param microplanNumber The number of the microplan (String)
	* @param language The language for the microplan (String)
	* @return the action
	*/
	void editMicroplan(String microplanNumber, String language)   //theofilos
	{   //theofilos
		final String microplanNumberString = microplanNumber;   //theofilos
		final String languageString = language;   //theofilos
		
		//  Action action = new AbstractAction(languageString)   //theofilos
		// {   //theofilos
		// public void actionPerformed(ActionEvent e)   //theofilos
		// {   //theofilos
    if (languageString.equalsIgnoreCase(english_action))
    {
			emp = new EnglishMicroPanel(selectedField, microplanNumberString);
			/*  emp.labelEN.setIcon(new ImageIcon(Mpiro.image_uk));  //theofilos
			emp.labelEN.setText(englishVersionOf_text +  //theofilos
			                microplan_text +  //theofilos
			                microplanNumberString +  //theofilos
			                forField_text +  //theofilos
			                "\"" +  //theofilos
			                selectedField +  //theofilos
			                "\""); */  //theofilos
			DataBasePanel.previewPanel.removeAll();
			DataBasePanel.previewPanel.add("Center", emp);
    }
		else if (languageString.equalsIgnoreCase(italian_action))
		{
	    imp = new ItalianMicroPanel(selectedField, microplanNumberString);
			/* imp.labelIT.setIcon(new ImageIcon(Mpiro.image_italy));  //theofilos
	    imp.labelIT.setText(italianVersionOf_text +  //theofilos
	                    microplan_text +  //theofilos
	                    microplanNumberString +  //theofilos
	                    forField_text +  //theofilos
	                    "\"" +  //theofilos
	                    selectedField +  //theofilos
	                    "\""); */  //theofilos
	    DataBasePanel.previewPanel.removeAll();
	    DataBasePanel.previewPanel.add("Center", imp);
		}
    else if (languageString.equalsIgnoreCase(greek_action))
    {
	    gmp = new GreekMicroPanel(selectedField, microplanNumberString);
			/*  gmp.labelGR.setIcon(new ImageIcon(Mpiro.image_greece));  //theofilos
	    gmp.labelGR.setText(greekVersionOf_text +  //theofilos
	                    microplan_text +  //theofilos
	                    microplanNumberString +  //theofilos
	                    forField_text +  //theofilos
	                    "\"" +  //theofilos
	                    selectedField +  //theofilos
	                    "\""); */  //theofilos
	    DataBasePanel.previewPanel.removeAll();
	    DataBasePanel.previewPanel.add("Center", gmp);
    }
    else if (languageString.equalsIgnoreCase(appropriateness_action))
    {
    	new UserModelMicroplanDialog(selectedField, DataBasePanel.last.toString(), microplanNumberString);
    }

    DataBasePanel.previewPanel.revalidate();
    DataBasePanel.previewPanel.repaint();
       // }   //theofilos
  		//  };   //theofilos
    return ;
	}

  /**
   * The method that sets up the popup menu
   */
  public void setPopups() 
  {
		// The actions that will be added to the popup(s)
		String editInterestImportanceRepetitions_action = LangResources.getString(Mpiro.selectedLocale, "editInterestImportanceRepetitions_action");
		String insertNewFieldBefore_action = LangResources.getString(Mpiro.selectedLocale, "insertNewFieldBefore_action");
		String insertNewFieldAfter_action = LangResources.getString(Mpiro.selectedLocale, "insertNewFieldAfter_action");
		String renameField_action = LangResources.getString(Mpiro.selectedLocale, "renameField_action");
		String removeField_action = LangResources.getString(Mpiro.selectedLocale, "removeField_action");
		String addNewFieldEndOfTable_action = LangResources.getString(Mpiro.selectedLocale, "addNewFieldEndOfTable_action");
                String editFieldProperties_action=LangResources.getString(Mpiro.selectedLocale,  "editFieldProperties_action");
                String editRestriction_action=LangResources.getString(Mpiro.selectedLocale,  "editRestrictions_action");;
                String addExistingField_action="add existing field";
                
                
		Action a1 = new AbstractAction(editInterestImportanceRepetitions_action)
		{
			public void actionPerformed(ActionEvent e)
			{
		     UserModelDialog umDialog = new UserModelDialog(selectedField,
		                                                    DataBasePanel.last.toString(),
		                                                    "ENTITY-TYPE");
			}
		};
                Action robots = new AbstractAction("Edit Profile Preference...")
		{
			public void actionPerformed(ActionEvent e)
			{
		     RobotsModelDialog rmDialog = new RobotsModelDialog(selectedField,
		                                                    DataBasePanel.last.toString(),
		                                                    "ENTITY-TYPE");
			}
		};
              Action robotsChar = new AbstractAction("Profile Attributes...") {
            public void actionPerformed(ActionEvent e) {
                 RobotsCharDialog umDialog = new RobotsCharDialog("Char",
                        DataBasePanel.last.toString()+":"+selectedField,
                        "CHAR");
            }
        };
                
                
                Action pror = new AbstractAction(editFieldProperties_action)
		{
			public void actionPerformed(ActionEvent e)
			{
                            Vector propVector=(Vector) Mpiro.win.struc.getProperty(selectedField);
                            if (propVector== null){
                                propVector= new PropertiesHashtableRecord(DataBaseTable.dbTable.getValueAt(rowNo, 1).toString());
                            Mpiro.win.struc.addProperty(selectedField, (PropertiesHashtableRecord) propVector);
                            }
		EditFieldProperties efp=   new EditFieldProperties(Mpiro.win.getFrames()[0], true, propVector, selectedField);
                efp.setSize(500,410);
                efp.setLocation(300,180);
                efp.setTitle(selectedField);
              //  efp.jCheckBox1.setSelected(true);
                efp.setVisible(true);
                 if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
			}

		};
                
                Action restrictions = new AbstractAction(editRestriction_action)
		{
			public void actionPerformed(ActionEvent e)
			{
                            String last=DataBasePanel.last.toString();
                            if (last.substring(0,last.length()-1).endsWith("_occur")) last=last.substring(0,last.length()-7);
                            Vector resVector=Mpiro.win.struc.getValueRestriction(last+":"+selectedField);
                            if (resVector== null)
                            Mpiro.win.struc.addValueRestriction(last+":"+selectedField, new ValueRestriction());
                            
                RestrictionsDialog efp1;
                efp1 = new RestrictionsDialog( Mpiro.win.getFrames()[0],true, selectedField );
                efp1.setSize(400,520);
                efp1.setLocation(300,150);
                efp1.setTitle(selectedField);
              //  efp.jCheckBox1.setSelected(true);
                efp1.setVisible(true);
                 if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
			}
		};

		Action b1 = new AbstractAction(insertNewFieldBefore_action + "\"" + selectedField + "\"")
		{
			public void actionPerformed(ActionEvent e)
			{
				//Vector allExistingFieldsVector = (Vector)Mpiro.win.struc.getExistingFieldnamesForEntityTypeAndChildren(DataBasePanel.topA);
				if (Mpiro.win.struc.existsProperty("New-user-field"))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.thereIsAnUnnamedUserField_ETC_dialog);
					return;
				} 
				else 
				{
					TreePreviews.dbtm.insert(rowNo);
					DataBaseTable.dbTable.tableChanged(new TableModelEvent(
					TreePreviews.dbtm, rowNo, rowNo, TableModelEvent.ALL_COLUMNS,
					TableModelEvent.INSERT));
					DataBaseTable.dbTable.revalidate();
					DataBaseTable.dbTable.repaint();
					Mpiro.win.struc.createDefaultHashtableField(DataBasePanel.last.toString());
					Mpiro.win.struc.addFieldInUserModelHashtable("New-user-field", DataBasePanel.last.toString());
                                        //QueryProfileHashtable.addFieldInRobotsModelHashtable("New-user-field", DataBasePanel.last.toString());
                                        
					Mpiro.needExportToExprimo = true;		//maria
                                        Vector allOccur=Mpiro.win.struc.getAllOccurrences(DataBasePanel.last.toString());
                                        for(int j=0;j<allOccur.size();j++){
                                            String next=allOccur.elementAt(j).toString();
                                            Mpiro.win.struc.addValueRestriction(next+":"+"New-user-field",new ValueRestriction());
                                            Enumeration children=Mpiro.win.struc.getChildrenBasics(DataBasePanel.getNode(next)).keys();
                                            while(children.hasMoreElements()){
                                                Mpiro.win.struc.addValueRestriction(children.nextElement().toString()+":"+"New-user-field",new ValueRestriction());
                                            }
                                        }
				}
                 if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
			}
		};

		Action b2 = new AbstractAction(insertNewFieldAfter_action + "\"" + selectedField + "\"")
		{
			public void actionPerformed(ActionEvent e)
			{
				
				if (Mpiro.win.struc.existsProperty("New-user-field"))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.thereIsAnUnnamedUserField_ETC_dialog);
					return;				
				} 
				else 
				{
					TreePreviews.dbtm.insert(rowNo+1);
					DataBaseTable.dbTable.tableChanged(new TableModelEvent(
					TreePreviews.dbtm, rowNo+1, rowNo+1, TableModelEvent.ALL_COLUMNS,
					TableModelEvent.INSERT));
					DataBaseTable.dbTable.revalidate();
					DataBaseTable.dbTable.repaint();
					Mpiro.win.struc.createDefaultHashtableField(DataBasePanel.last.toString());
					Mpiro.win.struc.addFieldInUserModelHashtable("New-user-field", DataBasePanel.last.toString());
                                       // QueryProfileHashtable.addFieldInRobotsModelHashtable("New-user-field", DataBasePanel.last.toString());
					Mpiro.needExportToExprimo = true;		//maria
                                         Vector allOccur=Mpiro.win.struc.getAllOccurrences(DataBasePanel.last.toString());
                                        for(int j=0;j<allOccur.size();j++){
                                            String next=allOccur.elementAt(j).toString();
                                            Mpiro.win.struc.addValueRestriction(next+":"+"New-user-field",new ValueRestriction());
                                            Enumeration children=Mpiro.win.struc.getChildrenBasics(DataBasePanel.getNode(next)).keys();
                                            while(children.hasMoreElements()){
                                                Mpiro.win.struc.addValueRestriction(children.nextElement().toString()+":"+"New-user-field",new ValueRestriction());
                                            }
                                        }
				}
			 if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
            }

		};


		Action b3 = new AbstractAction(renameField_action + "\"" + selectedField + "\"")
		{
	    public void actionPerformed(ActionEvent e)
	    {
	      int row = DataBaseTable.dbTable.getSelectedRow();
             
	      DataBaseTable.dbTable.editCellAt(row, 0, e);
	      Mpiro.needExportToExprimo = true;		//maria
	
	      DataBaseTable.dbTable.repaint();
           if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
	    }
		};

		Action b4 = new AbstractAction(removeField_action + "\"" + selectedField + "\"")
		{
			public void actionPerformed(ActionEvent e)
			{
				
				Object[] options = {  LangResources.getString(Mpiro.selectedLocale, "yes_text"),  //theofilos
			                     		LangResources.getString(Mpiro.selectedLocale, "no_text")  //theofilos
													 }; //theofilos
        int j = JOptionPane.showOptionDialog(
																	null,
																	LangResources.getString(Mpiro.selectedLocale, "confirm_field_deletion")+ " \"" + selectedField + "\"", //theofilos
																	LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),  //theofilos
																	JOptionPane.DEFAULT_OPTION,  //theofilos
																	JOptionPane.QUESTION_MESSAGE,
																	null,//new ImageIcon(Mpiro.image_mpirotext),
																	options, //theofilos
																	options[0]); //theofilos				
				
				if(j==0)
				{
            
            
                                        
                                         String lastSelected=DataBasePanel.last.toString();
                if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur"))
                {
                    lastSelected=lastSelected.substring(0, lastSelected.length()-7);
                }
                                         for(Object parents:Mpiro.win.struc.getParents(lastSelected)){
                                             Vector nextParent=(Vector)Mpiro.win.struc.getEntityTypeOrEntity(parents.toString());
                                             Vector db=(Vector)nextParent.elementAt(0);
                                             for(int k=0;k<db.size();k++){
                                                 Vector field=(Vector) db.elementAt(k);
                                                 if(field.elementAt(0).toString().equalsIgnoreCase(selectedField)){
                                                     MessageDialog error=new MessageDialog(Mpiro.win.getFrames()[0], "this field is inherited from "+parents.toString()+" and cannot be removed.");
                return;
                                                 }
                                             }
                                             
                                         }
               /*  Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    Enumeration allTypesNames=allEntityTypes.keys();
                    while(allTypesNames.hasMoreElements())
                    {//DefaultMutableTreeNode nextNode=null;
                        Object nextEl=allTypesNames.nextElement();
               
                        
                         
                        if ((nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected)))&&(!(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString()))))
                        {System.out.println("lllllllllllkl"+nextEl.toString());
                            TreePreviews.dbtm.delete(rowNo,nextEl.toString());
                            ////
                        }}*/
					if (TreePreviews.dbtm.delete(rowNo)) 
					{
						DataBaseTable.dbTable.tableChanged(new TableModelEvent(
					  		TreePreviews.dbtm, rowNo, rowNo, TableModelEvent.ALL_COLUMNS,
					  		TableModelEvent.INSERT));
						Mpiro.needExportToExprimo = true;		//maria
					
					}
					DataBaseTable.dbTable.revalidate();
					DataBaseTable.dbTable.repaint(); 
				}
				else
				{
					return;
				}
         if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
			}
		};
		
                
                Action ex = new AbstractAction(addExistingField_action){
                    public void actionPerformed(ActionEvent e)
                    {                      
			KDialog addEntityDialog = new KDialog("test1", "test2", "", new Vector(), false, "FIELD", true);
		
                        if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
                    }
                };
                
              
		Action b5 = new AbstractAction(addNewFieldEndOfTable_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				//Vector allExistingFieldsVector = (Vector)Mpiro.win.struc.getExistingFieldnamesForEntityTypeAndChildren(DataBasePanel.topA);
				//////System.out.println("--" + allExistingFieldsVector.toString());
				if (Mpiro.win.struc.existsProperty("New-user-field"))
				{
					new MessageDialog(DataBaseTable.dbTable, MessageDialog.thereIsAnUnnamedUserField_ETC_dialog);
					return;
				} 
				else 
				{
					int row = DataBaseTable.dbTable.getRowCount();
					TreePreviews.dbtm.insert(row+1);
					DataBaseTable.dbTable.tableChanged(new TableModelEvent(
					TreePreviews.dbtm, row+1, row+1, TableModelEvent.ALL_COLUMNS,
					TableModelEvent.INSERT));
					DataBaseTable.dbTable.revalidate();
					DataBaseTable.dbTable.repaint();
					Mpiro.win.struc.createDefaultHashtableField(DataBasePanel.last.toString());
					Mpiro.win.struc.addFieldInUserModelHashtable("New-user-field", DataBasePanel.last.toString());
                                       // QueryProfileHashtable.addFieldInRobotsModelHashtable("New-user-field", DataBasePanel.last.toString());
					Mpiro.needExportToExprimo = true;		//maria
                                         Vector allOccur=Mpiro.win.struc.getAllOccurrences(DataBasePanel.last.toString());
                                        for(int j=0;j<allOccur.size();j++){
                                            String next=allOccur.elementAt(j).toString();
                                            Mpiro.win.struc.addValueRestriction(next+":"+"New-user-field",new ValueRestriction());
                                            Enumeration children=Mpiro.win.struc.getChildrenBasics(DataBasePanel.getNode(next)).keys();
                                            while(children.hasMoreElements()){
                                                Mpiro.win.struc.addValueRestriction(children.nextElement().toString()+":"+"New-user-field",new ValueRestriction());
                                            }
                                        }
				}
                 if(Mpiro.win.ontoPipe.isRealTime())
        Mpiro.win.ontoPipe.rebind();
			}
		};

    editMicroplanOneMenu.removeAll();
    editMicroplanTwoMenu.removeAll();
    editMicroplanThreeMenu.removeAll();
    editMicroplanFourMenu.removeAll();
    editMicroplanFiveMenu.removeAll();
    editMicroplanSixMenu.removeAll();
    //editMicroplanOneMenu.add(editMicroplan("1", english_action));   //theofilos
    //editMicroplanOneMenu.add(editMicroplan("1", italian_action));   //theofilos
    //editMicroplanOneMenu.add(editMicroplan("1", greek_action));   //theofilos
    editMicroplanOneMenu.addSeparator();
    //editMicroplanOneMenu.add(editMicroplan("1", appropriateness_action));   //theofilos

    //editMicroplanTwoMenu.add(editMicroplan("2", english_action));   //theofilos
    //editMicroplanTwoMenu.add(editMicroplan("2", italian_action));   //theofilos
    //editMicroplanTwoMenu.add(editMicroplan("2", greek_action));   //theofilos
    editMicroplanTwoMenu.addSeparator();
    //editMicroplanTwoMenu.add(editMicroplan("2", appropriateness_action));   //theofilos

    //editMicroplanThreeMenu.add(editMicroplan("3", english_action));   //theofilos
    //editMicroplanThreeMenu.add(editMicroplan("3", italian_action));   //theofilos
    //editMicroplanThreeMenu.add(editMicroplan("3", greek_action));   //theofilos
    editMicroplanThreeMenu.addSeparator();
    //editMicroplanThreeMenu.add(editMicroplan("3", appropriateness_action));   //theofilos

    //editMicroplanFourMenu.add(editMicroplan("4", english_action));   //theofilos
    //editMicroplanFourMenu.add(editMicroplan("4", italian_action));   //theofilos
    //editMicroplanFourMenu.add(editMicroplan("4", greek_action));   //theofilos
    editMicroplanFourMenu.addSeparator();
    //editMicroplanFourMenu.add(editMicroplan("4", appropriateness_action));   //theofilos

    //editMicroplanFiveMenu.add(editMicroplan("5", english_action));   //theofilos
    //editMicroplanFiveMenu.add(editMicroplan("5", italian_action));   //theofilos
    //editMicroplanFiveMenu.add(editMicroplan("5", greek_action));   //theofilos
    editMicroplanFiveMenu.addSeparator();
    //editMicroplanFiveMenu.add(editMicroplan("5", appropriateness_action));   //theofilos
editMicroplanSixMenu.addSeparator();
    // for all new fields
    popupUser = new JPopupMenu();
		popupUser.add(a1);
                popupUser.add(robots);
                popupUser.add(robotsChar);
		popupUser.addSeparator();
		//popupUser.add(editMicroplanOneMenu);   //theofilos
		//popupUser.add(editMicroplanTwoMenu);   //theofilos
		//popupUser.add(editMicroplanThreeMenu);   //theofilos
		//popupUser.add(editMicroplanFourMenu);   //theofilos
		//popupUser.add(editMicroplanFiveMenu);   //theofilos
		//popupUser.addSeparator();   //theofilos
		popupUser.add(b1);
		popupUser.add(b2);
		//popupUser.add(b3);    //theofilos
		popupUser.add(b4);
                popupUser.add(pror);
                popupUser.add(restrictions);
                //popupUser.add(ex);
		DataBaseTable.dbTable.add(popupUser);

    // for the special field "Subtype-of"
    popupUserSpecial = new JPopupMenu();
		popupUserSpecial.add(a1);
                 popupUserSpecial.add(robots);
                 popupUserSpecial.add(robotsChar);
		popupUserSpecial.addSeparator();
		popupUserSpecial.add(b5);
              //  popupUserSpecial.add(ex);
		DataBaseTable.dbTable.add(popupUserSpecial);

		// for built-in fields (rowNo = 1-7)
		popupSystem = new JPopupMenu();
		popupSystem.add(b5);
              //  popupSystem.add(ex);
		DataBaseTable.dbTable.add(popupSystem);
                
                popupInheritedProperties= new JPopupMenu();
                popupInheritedProperties.add(b5);
               // popupInheritedProperties.add(ex);
                popupInheritedProperties.add(restrictions);
                popupInheritedProperties.add(pror);
                DataBaseTable.dbTable.add(popupInheritedProperties);
	} // setPopups()
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
