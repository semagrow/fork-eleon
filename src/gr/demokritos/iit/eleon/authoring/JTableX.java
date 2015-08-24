/***************

<p>Title: </p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

***************/


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
