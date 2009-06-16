//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.NodeVector;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.QueryUsersHashtable;
import gr.demokritos.iit.eleon.authoring.TreePreviews;
import gr.demokritos.iit.eleon.authoring.UserModelStoryDialog;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;


public class StoriesTableListener extends MouseAdapter
{
	JPopupMenu popupUser;
	static String foundLanguage;
	public static String selectedField;
	static String selectedLanguage;
	public static int rowNo;
	public static int colNo;
	public static String lastEditedField;
	int row;


	public StoriesTableListener() 
	{}


	public void mouseReleased (MouseEvent re)  
	{
		if (SwingUtilities.isRightMouseButton(re)) 
		{
			mousePressed(re);
		}
	}


	public void mousePressed (MouseEvent re)  
	{
		if (SwingUtilities.isRightMouseButton(re)) 
		{
			if(re.isPopupTrigger()) 
			{
				int x = re.getX();
				int y = re.getY();
				Point p = new Point(x,y);
				rowNo = StoriesTable.storiesTable.rowAtPoint(p);
				colNo = StoriesTable.storiesTable.columnAtPoint(p);
				StoriesTable.storiesTable.getSelectionModel().setLeadSelectionIndex(rowNo);
				StoriesTable.storiesTable.clearSelection();
				
				// Determine which field and language are selected
				selectedField = StoriesTable.storiesTable.getValueAt(rowNo, 0).toString();
				selectedLanguage = StoriesTable.storiesTable.getValueAt(rowNo, 1).toString();
				
				// Put the general preview at the preview-area
				TreePreviews.generalStoryPreview();
				
				// only for right-click
				//setPopupsDefault();

				// determine the row for the popupUser
				if (selectedField.equalsIgnoreCase("New-story"))
				{
					setPopupsDefault();
				}
				else
				{
					setPopups();
				}
				popupUser.show(StoriesTable.storiesTable, re.getX(), re.getY());
			}
		}
		// left has been clicked
		else 
		{
			int x = re.getX();
			int y = re.getY();
			Point p = new Point(x,y);
			int rowNo = StoriesTable.storiesTable.rowAtPoint(p);
			int colNo = StoriesTable.storiesTable.columnAtPoint(p);
			StoriesTable.storiesTable.getSelectionModel().setLeadSelectionIndex(rowNo);
			
			// Put the general preview at the preview-area
			TreePreviews.generalStoryPreview();
			
			// Determine which field and language are selected
			selectedField = StoriesTable.storiesTable.getValueAt(rowNo, 0).toString();
			selectedLanguage = StoriesTable.storiesTable.getValueAt(rowNo, 1).toString();
		}
	};


	public void setPopups() 
	{
		String editInterestImportanceRepetitions_action = LangResources.getString(Mpiro.selectedLocale, "editInterestImportanceRepetitions_action");
		String editEnglishStoryText_action = LangResources.getString(Mpiro.selectedLocale, "editEnglishStoryText_action");
		String editItalianStoryText_action = LangResources.getString(Mpiro.selectedLocale, "editItalianStoryText_action");
		String editGreekStoryText_action = LangResources.getString(Mpiro.selectedLocale, "editGreekStoryText_action");
		String addNewStoryEndOfTable_action = LangResources.getString(Mpiro.selectedLocale, "addNewStoryEndOfTable_action");
		String renameStory_action = LangResources.getString(Mpiro.selectedLocale, "renameStory_action");
		String removeStory_action = LangResources.getString(Mpiro.selectedLocale, "removeStory_action");
		
		// The actions that will be added to the popup
		Action a1 = new AbstractAction(editInterestImportanceRepetitions_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				UserModelStoryDialog umsDialog = new UserModelStoryDialog(selectedField,StoriesPanel.last.toString());
			}
		};

		Action a2 = new AbstractAction(editEnglishStoryText_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				String node = StoriesPanel.last.toString();
				NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(node);
				StoriesVector sv = (StoriesVector)nv.elementAt(3);
				
				foundLanguage = "English";
				lastEditedField = new String(selectedField);
				//String fieldName = StoriesTable.storiesTable.getValueAt
				//(StoriesTable.storiesTable.getSelectedRow(), 0).toString();
				StoriesPanel.previewPanel.removeAll();
				
				Hashtable englishValues = (Hashtable)sv.elementAt(0);
				if (englishValues.containsKey(selectedField))
				{
					String story = englishValues.get(selectedField).toString();
					StoriesPanel.storyText.setText(story);
				}
				else
				{
					StoriesPanel.storyText.setText("");
				}
				StoriesPanel.previewPanel.removeAll();
				StoriesPanel.previewPanel.add(StoriesPanel.storyScroll);
				StoriesPanel.previewPanel.revalidate();
				StoriesPanel.previewPanel.repaint();
			}
		};

		Action a3 = new AbstractAction(editItalianStoryText_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				String node = StoriesPanel.last.toString();
				NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(node);
				StoriesVector sv = (StoriesVector)nv.elementAt(3);
				
				foundLanguage = "Italian";
				lastEditedField = new String(selectedField);
				
				//String fieldName = StoriesTable.storiesTable.getValueAt
				//(StoriesTable.storiesTable.getSelectedRow(), 0).toString();
				StoriesPanel.previewPanel.removeAll();
				
				Hashtable italianValues = (Hashtable)sv.elementAt(1);
				if (italianValues.containsKey(selectedField))
				{
					String story = italianValues.get(selectedField).toString();
					StoriesPanel.storyText.setText(story);
				}
				else
				{
					StoriesPanel.storyText.setText("");
				}
				StoriesPanel.previewPanel.removeAll();
				StoriesPanel.previewPanel.add(StoriesPanel.storyScroll);
				StoriesPanel.previewPanel.revalidate();
				StoriesPanel.previewPanel.repaint();
			}
		};

		Action a4 = new AbstractAction(editGreekStoryText_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				String node = StoriesPanel.last.toString();
				NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(node);
				StoriesVector sv = (StoriesVector)nv.elementAt(3);
				
				foundLanguage = "Greek";
				lastEditedField = new String(selectedField);
				
				//String fieldName = StoriesTable.storiesTable.getValueAt
				//(StoriesTable.storiesTable.getSelectedRow(), 0).toString();
				StoriesPanel.previewPanel.removeAll();
				
				Hashtable greekValues = (Hashtable)sv.elementAt(2);
				if (greekValues.containsKey(selectedField))
				{
					String story = greekValues.get(selectedField).toString();
					StoriesPanel.storyText.setText(story);
				}
				else
				{
					StoriesPanel.storyText.setText("");
				}
				StoriesPanel.previewPanel.removeAll();
				StoriesPanel.previewPanel.add(StoriesPanel.storyScroll);
				StoriesPanel.previewPanel.revalidate();
				StoriesPanel.previewPanel.repaint();
			}
		};

		Action b1 = new AbstractAction(addNewStoryEndOfTable_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				Vector allExistingFieldsVector = (Vector)QueryHashtable.getExistingFieldnamesForStoryNode(StoriesPanel.last);
				if (allExistingFieldsVector.contains("New-story"))
				{
					new MessageDialog(StoriesTable.storiesTable, MessageDialog.thereIsAnUnnamedStoryField_ETC_dialog);
					return;
				}
				else
				{
					int row = StoriesTable.storiesTable.getRowCount();
					TreePreviews.stm.insert(row+1);
					StoriesTable.storiesTable.tableChanged(new TableModelEvent(
					TreePreviews.stm, row+1, row+1, TableModelEvent.ALL_COLUMNS,
					TableModelEvent.INSERT));
					StoriesTable.storiesTable.revalidate();
					StoriesTable.storiesTable.repaint();
				}
			}
		};

		Action b2 = new AbstractAction(renameStory_action + "\"" + selectedField + "\"")
		{
			String renameStory_text = LangResources.getString(Mpiro.selectedLocale, "renameStory_text");
			String newName_text = LangResources.getString(Mpiro.selectedLocale, "newName_text");
			public void actionPerformed(ActionEvent e)
			{
				//StoriesTable.storiesTable.editCellAt(rowNo, 0, e);
				new KDialog(renameStory_text, newName_text, "", new Vector(), false, "RENAME", true);
				StoriesTable.storiesTable.revalidate();
				StoriesTable.storiesTable.repaint();
			}
		};

		Action b3 = new AbstractAction(removeStory_action + "\"" + selectedField + "\"")
		{
			public void actionPerformed(ActionEvent e)
			{
				if (StoriesTable.storiesTable.getRowCount() > 1)
				{
					if (TreePreviews.stm.delete(rowNo)) 
					{
						StoriesTable.storiesTable.tableChanged(new TableModelEvent(
										StoriesTable.m_data, rowNo, rowNo, TableModelEvent.ALL_COLUMNS,
										TableModelEvent.INSERT));
					}
					QueryHashtable.removeStoryFromHashtable(StoriesPanel.last.toString(), selectedField);
					QueryUsersHashtable.removeStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), selectedField);
					StoriesTable.storiesTable.revalidate();
					StoriesTable.storiesTable.repaint();
				}
				else
				{
					QueryHashtable.removeStoryFromHashtable(StoriesPanel.last.toString(), selectedField);
					QueryUsersHashtable.removeStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), selectedField);
					QueryHashtable.createStory(StoriesPanel.last.toString(), "New-story");
					TreePreviews.setStoriesTable(StoriesPanel.last.toString());
				}
			}
		};

    popupUser = new JPopupMenu();
    popupUser.add(a1);
    popupUser.addSeparator();
    popupUser.add(a2);
    popupUser.add(a3);
    popupUser.add(a4);
    popupUser.addSeparator();
    popupUser.add(b1);
    popupUser.add(b2);
    popupUser.add(b3);
    StoriesTable.storiesTable.add(popupUser);
	}

	public void setPopupsDefault() 
	{
	  String editInterestImportanceRepetitions_action = LangResources.getString(Mpiro.selectedLocale, "editInterestImportanceRepetitions_action");
	  String editEnglishStoryText_action = LangResources.getString(Mpiro.selectedLocale, "editEnglishStoryText_action");
	  String editItalianStoryText_action = LangResources.getString(Mpiro.selectedLocale, "editItalianStoryText_action");
	  String editGreekStoryText_action = LangResources.getString(Mpiro.selectedLocale, "editGreekStoryText_action");
	  String addNewStoryEndOfTable_action = LangResources.getString(Mpiro.selectedLocale, "addNewStoryEndOfTable_action");
	  String renameStory_action = LangResources.getString(Mpiro.selectedLocale, "renameStory_action");
	  String removeStory_action = LangResources.getString(Mpiro.selectedLocale, "removeStory_action");

		// The actions that will be added to the popup
		Action a0 = new AbstractAction(editInterestImportanceRepetitions_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				UserModelStoryDialog umsDialog = new UserModelStoryDialog(selectedField,StoriesPanel.last.toString());
			}
		};

		Action a1 = new AbstractAction(addNewStoryEndOfTable_action)
		{
			public void actionPerformed(ActionEvent e)
			{
				Vector allExistingFieldsVector = (Vector)QueryHashtable.getExistingFieldnamesForStoryNode(StoriesPanel.last);
				if (allExistingFieldsVector.contains("New-story"))
				{
					new MessageDialog(StoriesTable.storiesTable, MessageDialog.thereIsAnUnnamedStoryField_ETC_dialog);
					return;
				}
				else
				{
					int row = StoriesTable.storiesTable.getRowCount();
					TreePreviews.stm.insert(row+1);
					StoriesTable.storiesTable.tableChanged(new TableModelEvent(
					              TreePreviews.stm, row+1, row+1, TableModelEvent.ALL_COLUMNS,
					              TableModelEvent.INSERT));
					StoriesTable.storiesTable.revalidate();
					StoriesTable.storiesTable.repaint();
				}
			}
		};

		Action a2 = new AbstractAction(renameStory_action + "\"" + selectedField + "\"")
		{
			public void actionPerformed(ActionEvent e)
			{
				//StoriesTable.storiesTable.editCellAt(rowNo, 0, e);
				String renameStory_text = LangResources.getString(Mpiro.selectedLocale, "renameStory_text");
				String newName_text = LangResources.getString(Mpiro.selectedLocale, "newName_text");
				new KDialog(renameStory_text, newName_text, "", new Vector(), false, "RENAME", true);
				StoriesTable.storiesTable.revalidate();
				StoriesTable.storiesTable.repaint();
			}
		};

		Action a3 = new AbstractAction(removeStory_action + "\"" + selectedField + "\"")
		{
			public void actionPerformed(ActionEvent e)
			{
				if (StoriesTable.storiesTable.getRowCount() > 1)
				{
					if (TreePreviews.stm.delete(rowNo))
					{
						StoriesTable.storiesTable.tableChanged(new TableModelEvent(
						StoriesTable.m_data, rowNo, rowNo, TableModelEvent.ALL_COLUMNS,
						TableModelEvent.INSERT));
					}
					QueryHashtable.removeStoryFromHashtable(StoriesPanel.last.toString(), selectedField);
					QueryUsersHashtable.removeStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), selectedField);
					StoriesTable.storiesTable.revalidate();
					StoriesTable.storiesTable.repaint();
				}
				else
				{
					QueryHashtable.removeStoryFromHashtable(StoriesPanel.last.toString(), selectedField);
					QueryUsersHashtable.removeStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), selectedField);
					QueryHashtable.createStory(StoriesPanel.last.toString(), "New-story");
					TreePreviews.setStoriesTable(StoriesPanel.last.toString());
				}
			}
		};

    a0.setEnabled(false);
    a1.setEnabled(false);
    //a3.setEnabled(false);
    popupUser = new JPopupMenu();
    popupUser.add(a0);
    popupUser.addSeparator();
    popupUser.add(a1);
    popupUser.add(a2);
    popupUser.add(a3);
    StoriesTable.storiesTable.add(popupUser);
	}

} // StoriesTableListener