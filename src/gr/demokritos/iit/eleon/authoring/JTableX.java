//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import javax.swing.*;
import javax.swing.table.*;
import java.util.Vector;

/**
 * <p>Title: JTableX</p>
 * <p>Description: A custom table that is used for upper model entries</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class JTableX extends JTable
{
	protected RowEditorModel rm;
	
	public JTableX()
	{
		super();
		rm = null;
	}
	
	public JTableX(TableModel tm)
	{
		super(tm);
		rm = null;
	}
	
	public JTableX(TableModel tm, TableColumnModel cm)
	{
		super(tm,cm);
		rm = null;
	}
	
	public JTableX(TableModel tm, TableColumnModel cm, ListSelectionModel sm)
	{
		super(tm,cm,sm);
		rm = null;
	}
	
	public JTableX(int rows, int cols)
	{
		super(rows,cols);
		rm = null;
	}
	
	public JTableX(final Vector rowData, final Vector columnNames)
	{
		super(rowData, columnNames);
		rm = null;
	}
	
	public JTableX(final Object[][] rowData, final Object[] colNames)
	{
		super(rowData, colNames);
		rm = null;
	}
	
	// new constructor
	public JTableX(TableModel tm, RowEditorModel rm)
	{
		super(tm,null,null);
		this.rm = rm;
	}
	
	public void setRowEditorModel(RowEditorModel rm)
	{
		this.rm = rm;
	}
	
	public RowEditorModel getRowEditorModel()
	{
		return rm;
	}
	
	public TableCellEditor getCellEditor(int row, int col)
	{
		TableCellEditor tmpEditor = null;
		if (rm!=null)
		   tmpEditor = rm.getEditor(row);
		if (tmpEditor!=null)
		   return tmpEditor;
		return super.getCellEditor(row,col);
	}
}