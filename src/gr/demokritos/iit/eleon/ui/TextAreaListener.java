//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.Mpiro;
import java.awt.event.*;

public class TextAreaListener extends FocusAdapter
{

	public TextAreaListener() 
	{}

	public void focusGained (FocusEvent fe)  
	{
		//System.out.println("FOCUS GAINED");
	}


	public void focusLost (FocusEvent fe)  
	{
		//int rowSel = StoriesTable.storiesTable.getSelectedRow();
		//String storyName = StoriesTable.m_data.getValueAt(rowSel, 0).toString();
		//String language = StoriesTable.m_data.getValueAt(rowSel, 1).toString();
		String language = StoriesTableListener.foundLanguage;
		String text = StoriesPanel.storyText.getText();
		
		Mpiro.win.struc.updateHashtableStoryInfo(StoriesPanel.last.toString(),
		                                       language,
		                                       text);
	}


	public void focusLost ()  
	{
		//int rowSel = StoriesTable.storiesTable.getSelectedRow();
		//String storyName = StoriesTable.m_data.getValueAt(rowSel, 0).toString();
		//String language = StoriesTable.m_data.getValueAt(rowSel, 1).toString();
		String language = StoriesTableListener.foundLanguage;
		String text = StoriesPanel.storyText.getText();
		
		Mpiro.win.struc.updateHashtableStoryInfo(StoriesPanel.last.toString(),
		                                       language,
		                                       text);
	}

} // TextAreaListener