//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.ui.StoriesPanel;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;

/**
 * <p>Title: LangCombo</p>
 * <p>Description: The class that handles the language flags in the microplan cells</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
//Edited and enhanced by Maria Prospathopoulou 
public class LangCombo extends JComboBox 
{
	static Font font= new Font(Mpiro.selectedFont,Font.PLAIN,11);
	static Color c = new Color(235, 235, 235);	    // white
	
	public static ImageIcon ICON_EIG = new ImageIcon( Mpiro.obj.image_comboEIG );
	public static ImageIcon ICON_EI = new ImageIcon( Mpiro.obj.image_comboEI );
	public static ImageIcon ICON_EG = new ImageIcon( Mpiro.obj.image_comboEG );
	public static ImageIcon ICON_IG = new ImageIcon( Mpiro.obj.image_comboIG );
	public static ImageIcon ICON_E = new ImageIcon( Mpiro.obj.image_comboE );
	public static ImageIcon ICON_I = new ImageIcon( Mpiro.obj.image_comboI );
	public static ImageIcon ICON_G = new ImageIcon( Mpiro.obj.image_comboG );
	public static ImageIcon ICON_X = new ImageIcon( Mpiro.obj.image_comboX );
	
	//static EnglishMicroPanel emp;
	//static ItalianMicroPanel imp;
	//static GreekMicroPanel gmp;
	
	//static Object foundStoryText;
	//static String foundLanguage;
	//static String m_id;
	//static String fieldName;

	/*
	public LangCombo() 
	{
		setBackground(c);
		setFont(font);
		
		addItem("");
		addItem("English");
		addItem("Italian");
		addItem("Greek");
		
		addActionListener(lst);
	} // constructor

	// EXPERIMENTAL
	public LangCombo(String id) 
	{
		m_id = id;
		
		addItem("English");
		addItem("Italian");
		addItem("Greek");
		
		addActionListener(lst);
	} // constructor

	public static String returnField() 
	{
	System.out.println("===+++=== " + m_id);
	return m_id;
	}
	//

	ActionListener lst = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if ((int)Mpiro.tabbedPane.getSelectedIndex() == 1) // database-tab
			{
				int ii = DataBaseTable.dbTable.getSelectedRow();
				//System.out.println("()---- " + getSelectedItem());
				fieldName = DataBaseTable.dbTable.getValueAt(DataBaseTable.dbTable.getSelectedRow(), 0).toString();
				
				if (fieldName != "")
				{
					//System.out.println("(LangCombo)�� " + fieldName);
					if (getSelectedItem() == "English")
					{
					    setMicroPanel(1);
					}
					else if (getSelectedItem() == "Italian")
					{
					    setMicroPanel(2);
					}
					else if (getSelectedItem() == "Greek")
					{
					    setMicroPanel(3);
					}
					else
					{
					    //System.out.println("(LangCombo)No language -------------");
					}
					DataBaseTable.m_data.setValueAt(updateMicroplanningIndex(fieldName), DataBaseTable.dbTable.getSelectedRow(), 3);
					//DataBaseTable.m_data.setValueAt("", DataBaseTable.dbTable.getSelectedRow(), 3);
					
					DataBaseTable.dbTable.revalidate();
					DataBaseTable.dbTable.repaint();
				}
        else
        {
          //System.out.println("(LangCombo)No field -------------");
          //LangCombo.this.setSelectedIndex(0);
          //LangCombo.this.setSelectedItem("");
        }
			}

			else if ((int)Mpiro.tabbedPane.getSelectedIndex() == 4) // stories-tab
			{
				String node = StoriesPanel.last.toString();
				NodeVector nv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(node);
				StoriesVector sv = (StoriesVector)nv.elementAt(3);
				
				if (getSelectedItem() == "English")
				{
					foundLanguage = "English";
					
					String fieldName = StoriesTable.storiesTable.getValueAt
							 							(StoriesTable.storiesTable.getSelectedRow(), 0).toString();
					StoriesPanel.previewPanel.removeAll();
					
					Hashtable englishValues = (Hashtable)sv.elementAt(0);
					if (englishValues.containsKey(fieldName))
					{
				 	  String story = englishValues.get(fieldName).toString();
					  StoriesPanel.storyText.setText(story);
					}
					else
					{
						//StoriesPanel.storyText.setText("Default story text in English");
						StoriesPanel.storyText.setText("");
					}
          //StoriesTable.m_data.setValueAt(updateStoriesIndex(fieldName), StoriesTable.storiesTable.getSelectedRow(), 1);
				}
				else if (getSelectedItem() == "Italian")
				{
					foundLanguage = "Italian";
					
					String fieldName = StoriesTable.storiesTable.getValueAt
										 (StoriesTable.storiesTable.getSelectedRow(), 0).toString();
					StoriesPanel.previewPanel.removeAll();
					
					Hashtable italianValues = (Hashtable)sv.elementAt(1);
					if (italianValues.containsKey(fieldName))
					{
					  String story = italianValues.get(fieldName).toString();
					  StoriesPanel.storyText.setText(story);
					}
					else
					{
					  //StoriesPanel.storyText.setText("Default story text in Italian");
					  StoriesPanel.storyText.setText("");
					}
          //StoriesTable.m_data.setValueAt(updateStoriesIndex(fieldName), StoriesTable.storiesTable.getSelectedRow(), 1);
				 }
				 else if (getSelectedItem() == "Greek")
				 {
					 foundLanguage = "Greek";

					 String fieldName = StoriesTable.storiesTable.getValueAt
											 (StoriesTable.storiesTable.getSelectedRow(), 0).toString();
					 StoriesPanel.previewPanel.removeAll();

					 Hashtable greekValues = (Hashtable)sv.elementAt(2);
					 if (greekValues.containsKey(fieldName))
					 {
					 	  String story = greekValues.get(fieldName).toString();
						  StoriesPanel.storyText.setText(story);
					 }
					 else
					 {
						  StoriesPanel.storyText.setText("");
					 }
           //StoriesTable.m_data.setValueAt(updateStoriesIndex(fieldName), StoriesTable.storiesTable.getSelectedRow(), 1);

				 }
	       else
	       {
           StoriesPanel.previewPanel.removeAll();
           StoriesPanel.previewPanel.add(StoriesPanel.htmlScroll);
           StoriesPanel.previewPanel.revalidate();
           StoriesPanel.previewPanel.repaint();
           return;
	       }
				 StoriesPanel.previewPanel.removeAll();
				 StoriesPanel.previewPanel.add(StoriesPanel.storyScroll);
				 StoriesPanel.previewPanel.revalidate();
				 StoriesPanel.previewPanel.repaint();
			 }

			 else // other tab
			 {
				 //System.out.println("(LangCombo)No action defined!");
			 }

      }
   }; // ActionListener
	*/
	/*
	// Making new micro-panel instances (1 for English, 2 for Italian, 3 for Greek)
	public static void setMicroPanel(int i)
	{
		if (i==1)
		{
			emp = new EnglishMicroPanel(fieldName);
			emp.labelEN.setText("   English micro-planning expression for field: " +
			       							"\"" +
			                   fieldName +
			       							"\"");
			DataBasePanel.previewPanel.removeAll();
			DataBasePanel.previewPanel.add("Center", emp);
			DataBasePanel.previewPanel.revalidate();
			DataBasePanel.previewPanel.repaint();
		}
		if (i==2)
    {
       imp = new ItalianMicroPanel(fieldName);
       imp.labelIT.setText("   Italian micro-planning expression for field: " +
                           "\"" +
                           fieldName +
                           "\"");
       DataBasePanel.previewPanel.removeAll();
       DataBasePanel.previewPanel.add("Center", imp);
       DataBasePanel.previewPanel.revalidate();
       DataBasePanel.previewPanel.repaint();
    }
		if (i==3)
		{
		 gmp = new GreekMicroPanel(fieldName);
		 gmp.labelGR.setText("   Greek micro-planning expression for field: " +
		                     "\"" +
		                     fieldName +
		                     "\"");
		 DataBasePanel.previewPanel.removeAll();
		 DataBasePanel.previewPanel.add("Center", gmp);
		 DataBasePanel.previewPanel.revalidate();
		 DataBasePanel.previewPanel.repaint();
		}
	}
	*/

	public static String updateMicroplanningIndex(String fieldName)												//maria
	{																											//maria
		int numberOfEnglishMicroplans = 0;																		//maria
		int numberOfItalianMicroplans = 0;																		//maria
		int numberOfGreekMicroplans = 0;																		//maria
		String result = new String("");																			//maria
		String entityType = DataBasePanel.last.toString();														//maria
		Vector entityTypeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entityType);						//maria
		Hashtable mpHashtable = (Hashtable)entityTypeVector.get(5);												//maria
		String selectionEnglish = new String("");																//maria
		String selectionItalian = new String("");																//maria
		String selectionGreek = new String("");																	//maria
		for (int i=1; i<=5; i++)																				//maria
		{																										//maria
	  	selectionEnglish = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:English").toString();		//maria
	  	selectionItalian = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:Italian").toString();		//maria
	  	selectionGreek   = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:Greek").toString();		//maria
	  	if(!selectionEnglish.equalsIgnoreCase("NoMicroPlanning"))											//maria
	  	{																									//maria
	  		numberOfEnglishMicroplans++;																	//maria
	  	}																									//maria
	  	if(!selectionItalian.equalsIgnoreCase("NoMicroPlanning"))											//maria
	  	{																									//maria
	  		numberOfItalianMicroplans++;																	//maria
	  	}																									//maria
	  	if(!selectionGreek.equalsIgnoreCase("NoMicroPlanning"))												//maria
	  	{																									//maria
	  		numberOfGreekMicroplans++;																		//maria
	  	}																									//maria
	  }																										//maria
    result = numberOfEnglishMicroplans+"EN  "+numberOfItalianMicroplans+"IT  "+numberOfGreekMicroplans+"EL";//maria
    return result;																							//maria
	}																											//maria

        
        public static String updateMicroplanningIndex2(String fieldName)												//maria
	{																											//maria
		int numberOfEnglishMicroplans = 0;																		//maria
		int numberOfItalianMicroplans = 0;																		//maria
		int numberOfGreekMicroplans = 0;																		//maria
		String result = new String("");																			//maria
		//String entityType = DataBasePanel.last.toString();														//maria
		Vector property = (Vector) Mpiro.win.struc.getProperty(fieldName);						//maria
		Hashtable mpHashtable = (Hashtable) property.get(10);												//maria
		String selectionEnglish = new String("");																//maria
		String selectionItalian = new String("");																//maria
		String selectionGreek = new String("");																	//maria
		for (int i=1; i<=5; i++)																				//maria
		{																										//maria
	  	selectionEnglish = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:English").toString();		//maria
	  	if(!mpHashtable.containsKey(i + ":" + fieldName + ":" + "SELECTION:Italian")){
            mpHashtable.put(i + ":" + fieldName + ":" + "SELECTION:Italian", "NoMicroPlanning");
        }
        selectionItalian = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:Italian").toString();		//maria
	  	selectionGreek   = mpHashtable.get(i + ":" + fieldName + ":" + "SELECTION:Greek").toString();		//maria
	  	if(!selectionEnglish.equalsIgnoreCase("NoMicroPlanning"))											//maria
	  	{																									//maria
	  		numberOfEnglishMicroplans++;																	//maria
	  	}																									//maria
	  	if(!selectionItalian.equalsIgnoreCase("NoMicroPlanning"))											//maria
	  	{																									//maria
	  		numberOfItalianMicroplans++;																	//maria
	  	}																									//maria
	  	if(!selectionGreek.equalsIgnoreCase("NoMicroPlanning"))												//maria
	  	{																									//maria
	  		numberOfGreekMicroplans++;																		//maria
	  	}																									//maria
	  }																										//maria
    result = numberOfEnglishMicroplans+"EN  "+numberOfItalianMicroplans+"IT  "+numberOfGreekMicroplans+"EL";//maria
    return result;																							//maria
	}	
        
	public static String updateMicroplanningIndex(String microplanNumber, String fieldName)
	{
		String entityType = DataBasePanel.last.toString();
		Vector entityTypeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entityType);
		//System.out.println("entitytypevector-----"+entityTypeVector);
		Hashtable mpHashtable = (Hashtable)entityTypeVector.get(5);
		String selectionEnglish = mpHashtable.get(microplanNumber + ":" + fieldName + ":" + "SELECTION:English").toString();
		System.out.println("selectionEnglish-----"+selectionEnglish);
		String selectionItalian = mpHashtable.get(microplanNumber + ":" + fieldName + ":" + "SELECTION:Italian").toString();
		String selectionGreek   = mpHashtable.get(microplanNumber + ":" + fieldName + ":" + "SELECTION:Greek").toString();

    if ( (!selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) &&
         (!selectionItalian.equalsIgnoreCase("NoMicroPlanning")) &&
         (!selectionGreek.equalsIgnoreCase("NoMicroPlanning"))
       )
    {
    	return "ICON_EIG";
    }
    else if ( (!selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) &&
              (!selectionItalian.equalsIgnoreCase("NoMicroPlanning")) &&
              (selectionGreek.equalsIgnoreCase("NoMicroPlanning"))
            )
    {
    	return "ICON_EI";
    }
    else if ( (!selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) &&
              (selectionItalian.equalsIgnoreCase("NoMicroPlanning")) &&
              (!selectionGreek.equalsIgnoreCase("NoMicroPlanning"))
            )
    {
    	return "ICON_EG";
    }
    else if ( (selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) &&
              (!selectionItalian.equalsIgnoreCase("NoMicroPlanning")) &&
              (!selectionGreek.equalsIgnoreCase("NoMicroPlanning"))
            )
    {
    	return "ICON_IG";
    }
    else if ( (!selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) &&
              (selectionItalian.equalsIgnoreCase("NoMicroPlanning")) &&
              (selectionGreek.equalsIgnoreCase("NoMicroPlanning"))
            )
    {
    	return "ICON_E";
    }
    else if ( (selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) &&
              (!selectionItalian.equalsIgnoreCase("NoMicroPlanning")) &&
              (selectionGreek.equalsIgnoreCase("NoMicroPlanning"))
            )
    {
    	return "ICON_I";
    }
    else if ( (selectionEnglish.equalsIgnoreCase("NoMicroPlanning")) &&
              (selectionItalian.equalsIgnoreCase("NoMicroPlanning")) &&
              (!selectionGreek.equalsIgnoreCase("NoMicroPlanning"))
            )
    {
    	return "ICON_G";
    }
	  else
	  {
	  	return "ICON_X";
	  }
	}


	public static String updateStoriesIndex(String fieldName)
	{
		String entityType = StoriesPanel.last.toString();
		Vector entityTypeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entityType);
		Vector storiesVector = (Vector)entityTypeVector.get(3);
		Hashtable englishStoryHashtable = (Hashtable)storiesVector.get(0);
		Hashtable italianStoryHashtable = (Hashtable)storiesVector.get(1);
		Hashtable greekStoryHashtable = (Hashtable)storiesVector.get(2);

    if ( (!englishStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
         (!italianStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
         (!greekStoryHashtable.get(fieldName).toString().equalsIgnoreCase(""))
       )
    {
    	return "ICON_EIG";
    }
    else if ( (!englishStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (!italianStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (greekStoryHashtable.get(fieldName).toString().equalsIgnoreCase(""))
            )
    {
    	return "ICON_EI";
    }
    else if ( (!englishStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (italianStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (!greekStoryHashtable.get(fieldName).toString().equalsIgnoreCase(""))
            )
    {
    	return "ICON_EG";
    }
    else if ( (englishStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (!italianStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (!greekStoryHashtable.get(fieldName).toString().equalsIgnoreCase(""))
            )
    {
    	return "ICON_IG";
    }
    else if ( (!englishStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (italianStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (greekStoryHashtable.get(fieldName).toString().equalsIgnoreCase(""))
            )
    {
    	return "ICON_E";
    }
    else if ( (englishStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (!italianStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (greekStoryHashtable.get(fieldName).toString().equalsIgnoreCase(""))
            )
    {
    	return "ICON_I";
    }
    else if ( (englishStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (italianStoryHashtable.get(fieldName).toString().equalsIgnoreCase("")) &&
              (!greekStoryHashtable.get(fieldName).toString().equalsIgnoreCase(""))
            )
    {
    	return "ICON_G";
    }

    else
    {
    	return "ICON_X";
    }
	}

}//LangCombo