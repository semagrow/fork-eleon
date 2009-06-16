/*
 * StoriesTableData.java
 *
 * Created on 8 Απρίλιος 2009, 5:55 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gr.demokritos.iit.eleon.ui;

/**
 *
 * @author dimitris
 */
class StoriesColumnData
{
  public String  m_title;
  int m_width;
  int m_alignment;

  public StoriesColumnData(String title, int width, int alignment) 
  {
	  m_title = title;
	  m_width = width;
	  m_alignment = alignment;
  }
}
