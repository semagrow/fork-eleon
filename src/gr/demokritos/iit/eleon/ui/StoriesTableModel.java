//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.FieldData;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;

import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.JTree;


public class StoriesTableModel extends AbstractTableModel
{
  public static final StoriesColumnData m_columns[] = 
  {
    new StoriesColumnData( LangResources.getString(Mpiro.selectedLocale, "story_tabletext"), 200, JLabel.LEFT ),
    new StoriesColumnData( LangResources.getString(Mpiro.selectedLocale, "language_tabletext"), 200, JLabel.LEFT ),
    //new StoriesColumnData( "Story", 200, JLabel.LEFT ),
    //new StoriesColumnData( "Language", 200, JLabel.LEFT ),
  };

  public static final int COL_STORY = 0;
  public static final int COL_LANGUAGE = 1;

  public Vector m_vector;
  protected Vector m_initVector;


  public StoriesTableModel(Vector initVector) 
  {
		m_vector = new Vector();
		m_initVector = initVector;
		setDefaultData();
  }

  public void setDefaultData() 
  {
    m_vector.removeAllElements();
    m_vector = m_initVector;
  }

  public int getRowCount() 
  {
    return m_vector==null ? 0 : m_vector.size();
  }

  public int getColumnCount() 
  {
    return m_columns.length;
  }

  public String getColumnName(int column) 
  {
    return m_columns[column].m_title;
  }

/*
  public boolean isCellEditable(int nRow, int nCol) 
  {
    if (nCol > 1)
    {
       return false;
    } else {
       return true;
    }
  }
*/
  public boolean isCellEditable(int nRow, int nCol) 
  {
/*
    if (getValueAt(nRow, 0).toString().equalsIgnoreCase("New-story"))
    {
        if (nCol > 0) 
        {
              return false;
        }
        else
        {
              return true;
        }
    }
    else if (nCol == 1)
    {
        return false;
    }
    else
    {
        return true;
    }
  }
*/
		return false;
  }


  public Object getValueAt(int nRow, int nCol) 
  {
	  if (nRow < 0 || nRow>=getRowCount())
    {
    	return " ";
    }
	  FieldData row = (FieldData)m_vector.elementAt(nRow);
	  switch (nCol) 
	  {
	    case COL_STORY: return row.m_field;
	    case COL_LANGUAGE: return row.m_filler;
	  }
	  return " ";
  }

  public void setValueAt(Object value, int nRow, int nCol) 
  {
    if (nRow < 0 || nRow>=getRowCount())
    {
      return;
    }
    FieldData row = (FieldData)m_vector.elementAt(nRow);
    String svalue = value.toString();
		String oldStory = getValueAt(nRow, 0).toString();

    switch (nCol) 
    {
      case COL_STORY:

      /* old way
           String checkName = QueryHashtable.checkNameValidity(svalue);
           Vector storyNamesVector = new Vector();
	   Enumeration enum = StoriesTable.m_data.m_vector.elements();
	   while (enum.hasMoreElements()) {
	          Vector rowVector = (Vector)enum.nextElement();
	          if (rowVector.elementAt(0) != null) {
	              String storyName = rowVector.get(0).toString();
	              storyNamesVector.addElement(storyName);
	          }
	   }

	   if ((storyNamesVector.contains(svalue)) && (!(svalue.equalsIgnoreCase(oldStory))))
           {
                 new MessageDialog(StoriesTable.storiesTable, MessageDialog.thisStoryNameAlreadyExists_dialog);
           }
          else if (svalue.indexOf(" ") > 0 || svalue.startsWith(" "))
	   {
          	 new MessageDialog(StoriesTable.storiesTable, MessageDialog.noSpacesAreAllowedForAStoryName_dialog);
	   }
	   else if (svalue.equalsIgnoreCase(""))
	   {
                 new MessageDialog(StoriesTable.storiesTable, MessageDialog.pleaseGiveANameForTheStory_dialog);
	   }
           else if (!checkName.equalsIgnoreCase("VALID"))
           {
                 new MessageDialog(StoriesTable.storiesTable, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog + "\n" + checkName);
	   }
           else
           {
                    row.m_field = svalue;
                    //System.out.println("(oldStory)---- " + oldStory);
                    //System.out.println("(newStory)---- " + svalue);
                    // Update mainDBHashtable
                    QueryHashtable.renameHashtableStory(StoriesPanel.last.toString(), oldStory, svalue);

                    // Update mainUserModelStoryHashtable
                    if ( (oldStory.equalsIgnoreCase("New-story")) && (!svalue.equalsIgnoreCase("New-story")) )
                    {
                        QueryUsersHashtable.addStoryInUserModelStoryHashtable(svalue, StoriesPanel.last.toString());
                    }
                    else if ( (!oldStory.equalsIgnoreCase("New-story")) && (!svalue.equalsIgnoreCase("New-story")))
                    {
                        QueryUsersHashtable.renameStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), oldStory, svalue);
                    }
                    else if (svalue.equalsIgnoreCase("New-story"))
                    {
                        QueryUsersHashtable.removeStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), oldStory);
                    }
                    else
                    {
                        //System.out.println("(ALERT)---- " + oldStory + "  ==  " + svalue);
                    }
           }
           */
        row.m_field = svalue;
        break;

      case COL_LANGUAGE:
        row.m_filler = svalue;
        break;
    }

		/* Update current vector */
		FieldData story = (FieldData)m_vector.elementAt(nRow);
		story.setElementAt(value, nCol);
  }

  public void insert(int row) 
  {
		QueryHashtable.insertRowInStoriesTable(row);
  }

  public boolean delete(int row) 
  {
		String story = (String)getValueAt(row, 0);
		return (boolean)QueryHashtable.removeRowFromStoriesTable(story, row);
  }

}