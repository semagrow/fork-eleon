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
