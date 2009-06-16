//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import javax.swing.table.*;
import java.util.*;

public class RowEditorModel
{
	private Hashtable data;
	public RowEditorModel()
	{
		data = new Hashtable();
	}
	public void addEditorForRow(int row, TableCellEditor e )
	{
		data.put(new Integer(row), e);
	}
	public void removeEditorForRow(int row)
	{
		data.remove(new Integer(row));
	}
	public TableCellEditor getEditor(int row)
	{
		return (TableCellEditor)data.get(new Integer(row));
	}
}