//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.JTree;

/**
 * <p>Title: DataBaseTable</p>
 * <p>Description: The table for each basic-entity-type and entity-type</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class DataBaseTable extends JScrollPane
{
  public static JTable dbTable = null;
  public static DataBaseTableListener dbtl = null;
  protected JLabel m_title;
  public static DataBaseTableModel m_data;
  static int rowActive;

  /**
   *
   * @param data The databaseTableModel used
   * @param parentTableSize An int showing the size of the table of the parent node (used for colouring the inherited fields)
   */
  public DataBaseTable(DataBaseTableModel data, int parentTableSize) 
  {
		m_data = data;
		
		// from row at rowActive and on, the table is editable
		rowActive = parentTableSize;//maria -1
		
		dbTable = new JTable();
		dbTable.setAutoCreateColumnsFromModel(false);
		dbTable.setModel(m_data);
		dbTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		///////dbTable.setRowSelectionAllowed(false);	 //////////////
		///////dbTable.setColumnSelectionAllowed(false); //////////////
		
		// An instance of the renderer who colors the rows
		ColorRenderer cr = new ColorRenderer(rowActive);
		
		ColorRenderer imageRenderer = new ColorRenderer(rowActive);
		/*
		{
		  public void setValue(Object value)
		 {
				ImageIcon im = (ImageIcon)LangCombo.ICON_EIG;
				super.setHorizontalAlignment(SwingConstants.CENTER);
				super.setIcon(im);
		  }
		};
		*/

    // A different renderer for each column
    for (int k = 0; k < DataBaseTableModel.m_columns.length; k++) 
    {
      TableCellRenderer renderer;
      CheckCellRenderer checkboxrenderer = new CheckCellRenderer();
      if (k==DataBaseTableModel.COL_APPROVED)
      {
        renderer = new CheckCellRenderer();
      }
			else if (k==DataBaseTableModel.COL_MPLANNING)
			{
			  //renderer = new DefaultTableCellRenderer();
			  renderer = imageRenderer;
			}
		  else
		  {
		    renderer = cr;
		  }

      TableCellEditor editor;
      if (k==DataBaseTableModel.COL_FILLER)
        editor = new DefaultCellEditor(new FillerCombo());
      //else if (k==DataBaseTableModel.COL_MPLANNING)
			//editor = new DefaultCellEditor(new JTextField());
   //   else if (k==DataBaseTableModel.COL_APPROVED)
     //   editor = new DefaultCellEditor(new JCheckBox());
      else
        editor = new DefaultCellEditor(new JTextField());
if (k!=DataBaseTableModel.COL_APPROVED&&k!=DataBaseTableModel.COL_MPLANNING){
      TableColumn column = new TableColumn(k,
        DataBaseTableModel.m_columns[k].m_width,
				renderer, editor);
			dbTable.addColumn(column);
    }}

		// put the dbTable in the scrollPane
		setViewportView(dbTable);
		dbTable.setPreferredScrollableViewportSize(new Dimension(380, 150));
		
		// add the mouseListener
		dbtl = new DataBaseTableListener(rowActive);
		dbTable.addMouseListener(dbtl);
             //   TableColumn tc=dbTable.getColumn(2);
               // dbTable.removeColumn(tc);
  } // constuctor
} // class