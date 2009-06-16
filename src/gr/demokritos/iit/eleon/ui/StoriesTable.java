//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.ColorRenderer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;


public class StoriesTable extends JScrollPane
{
  public static JTable storiesTable;
  public static StoriesTableModel m_data;
  static StoriesTableListener stl;
  protected JLabel m_title;

  public StoriesTable(StoriesTableModel data) 
  {
		m_data = data;
		
		storiesTable = new JTable();
		storiesTable.setAutoCreateColumnsFromModel(false);
		storiesTable.setModel(m_data);
		storiesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		ColorRenderer imageRenderer = new ColorRenderer(0);
		
		for (int k = 0; k < StoriesTableModel.m_columns.length; k++) 
		{
	    TableCellRenderer renderer;
	    DefaultTableCellRenderer textRenderer = new DefaultTableCellRenderer();
	    textRenderer.setHorizontalAlignment(StoriesTableModel.m_columns[k].m_alignment);
	
	    TableCellEditor editor;
	
	    if (k==StoriesTableModel.COL_LANGUAGE)
	    {
	      renderer = imageRenderer;
	    }
	    else
	    {
	      renderer = textRenderer;
	    }
	    editor = new DefaultCellEditor(new JTextField());
	
	    TableColumn column = new TableColumn(k,
	      StoriesTableModel.m_columns[k].m_width,
	        renderer, editor);
			storiesTable.addColumn(column);
    }

    JTableHeader header = storiesTable.getTableHeader();
    header.setUpdateTableInRealTime(false);

		// put the storiesTable in the scrollPane
		setViewportView(storiesTable);
		storiesTable.setPreferredScrollableViewportSize(new Dimension(380, 150));
		
		// add the mouseListener
		stl = new StoriesTableListener();
		storiesTable.addMouseListener(stl);
  } // constuctor

} // class

