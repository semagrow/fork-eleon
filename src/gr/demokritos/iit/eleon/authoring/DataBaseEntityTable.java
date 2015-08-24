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

import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.ui.KLabel;
import gr.demokritos.iit.eleon.ui.NumberCombo;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import javax.swing.table.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * <p>Title: DataBaseEntityTable</p>
 * <p>Description: A table used to represent the information about entities & generic entities</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class DataBaseEntityTable extends JScrollPane
{
	static JTableX dbeTable; // an eXtended JTable
	protected KLabel m_title;
	public static DataBaseEntityTableModel m_data;
	//private TableCellEditor editor;
	private TableCellEditor editor;
	private TableCellRenderer renderer;
	public static DataBaseEntityTableListener dbetl = null;
	static String parentSet;


  /**
   * The constructor foe the DataBaseEntityTable
   * @param data The TableModel
   */
  public DataBaseEntityTable(DataBaseEntityTableModel data) 
  {

		m_data = data;
		
		dbeTable = new JTableX();
		//dbeTable.setAutoCreateColumnsFromModel(true);
		dbeTable.setModel(m_data);
                
		dbeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//dbeTable.set
		// create a RowEditorModel... this is used to hold the extra
		// information that is needed to deal with row specific editors
		RowEditorModel rm = new RowEditorModel();
		
		// tell the JTableX which RowEditorModel we are using
		dbeTable.setRowEditorModel(rm);
		
		// set the CellRenderer (diff. for lang.-indep. and lang.-dep. fields)
		// An instance of a default renderer
		DefaultTableCellRenderer dr = new DefaultTableCellRenderer();
		// An instance of the renderer who colors the rows
		ColorRenderer cr = new ColorRenderer(1); // 1 rows non-editable //was 2
		// check if first row's first cell is "entity-id"
		FieldData firstRow = (FieldData)m_data.m_vector.elementAt(0);
		String firstCell = firstRow.elementAt(0).toString();
		if (firstCell.equalsIgnoreCase("type")) //"entity-id")) 
		{
			renderer = cr;  // only for language independent fields
		}
		else
		{
			renderer = dr;  // for language-dependent fields' tables
		}
		dbeTable.getColumn(dbeTable.getColumnName(0)).setCellRenderer(renderer);
		dbeTable.getColumn(dbeTable.getColumnName(1)).setCellRenderer(renderer);
		
		// get parent's node-name and its DataBaseTable.
		// They will be used right below, in order to retrieve a field's filler.
		//DefaultMutableTreeNode parent = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
		//NodeVector nv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(Mpiro.win.struc.nameWithoutOccur(parent.toString()));
		//Vector tableVector = (Vector)nv.elementAt(0);
		
		int a = DataBaseEntityTableModel.COL_FILLER;

	  for (int r=0; r<m_data.getRowCount(); r++) 
		{
		
			// get every row's field-name
			String fieldName = (String)m_data.getValueAt(r, 0);
			String fieldFiller = (String)m_data.getValueAt(r, 1);
                        System.out.println("name:  "+fieldName+" filler:  "+fieldFiller);
			
			// two fieldNames need their special fillerCombo as filler
			if ((fieldName.equalsIgnoreCase("gender")) ||
				(fieldName.equalsIgnoreCase("grammatical gender of name")) ||
				(fieldName.equalsIgnoreCase("grammatical gender of shortname")))
			{
				// create a new editor with a new fillerCombo for each row
				editor = new DefaultCellEditor(new GenderCombo());
				// tell the RowEditorModel to use editor for row r
				rm.addEditorForRow(r, editor);
			}
                        else if (fieldName.equalsIgnoreCase("number"))
			{
				// create a new editor with a new fillerCombo for each row
				editor = new DefaultCellEditor(new NumberCombo());
				// tell the RowEditorModel to use editor for row r
				rm.addEditorForRow(r, editor);
			}
			// fillerType is unknown for the moment
			//String fillerType = "";
			// get parent's DataBaseTable's rows
		//	Enumeration en = tableVector.elements();
		//	while (en.hasMoreElements()) 
		//	{
		//		FieldData fd = (FieldData)en.nextElement();
		//		// get each row's field-name
		//		String parentField = fd.elementAt(0).toString();
		//		String parentFiller = fd.elementAt(1).toString();
		//		parentSet = fd.elementAt(2).toString();
				//System.out.println(parentSet); //////++++++++++++++++
			//	if (parentField.equalsIgnoreCase(fieldName))
			//	{
					//if (fd.elementAt(1).toString().equalsIgnoreCase("string") )
					//{
					//	fillerType = "";
						//System.out.println(r + " +++ " + fd.elementAt(1).toString()); //////++++++++++++++++
					//}
				
			//		if (fieldName.equalsIgnoreCase("images") ||
			//		fd.elementAt(1).toString().equalsIgnoreCase("date") ||
			//		fd.elementAt(1).toString().equalsIgnoreCase("number") ||
			//		fd.elementAt(1).toString().equalsIgnoreCase("dimension"))
			//		{
						//fillerType = "";
			//			JTextField temptext = new JTextField();//////////
			//			TableCellEditor tempeditor = new DefaultCellEditor(temptext);
			//			rm.addEditorForRow(r, tempeditor);//////////////
						//System.out.println(r + " +++ " + fd.elementAt(1).toString()); //////++++++++++++++++
			//		}
			//		else
			//		{
			   /*   fillerType = fd.elementAt(1).toString();System.out.println("dfffffffffff"+fd.elementAt(1).toString());
			      //System.out.println(r + " +++ " + fd.elementAt(1).toString()); //////++++++++++++++++
			      //System.out.println("(DataBaseEntityTable)-- " + parentSet);
			      if (parentSet.equalsIgnoreCase("false"))
			      {
							Enumeration enum = DataBasePanel.top.breadthFirstEnumeration();
			      	DefaultMutableTreeNode parentFillerType = null;
							while (enum.hasMoreElements())
							{
								DefaultMutableTreeNode tmp = (DefaultMutableTreeNode)enum.nextElement();
								if (parentFiller.equalsIgnoreCase(tmp.toString()))
								{
									parentFillerType = tmp;
									// create a new editor with a new fillerCombo for each row
									editor = new DefaultCellEditor(new FillerCombo(parentFillerType));
									// tell the RowEditorModel to use editor for row r
									rm.addEditorForRow(r, editor);
									//System.out.println(node);
									break;
								}
							}
			      }
						else if (parentSet.equalsIgnoreCase("true"))
						{*/
                                           // dbeTable.setDefaultEditor(String.class, new MyEditor());
                        else{
							//JTextField temptext = new JTextField();//////////
							TableCellEditor tempeditor = new MyEditor();
							rm.addEditorForRow(r, tempeditor);//////////////
                        }
							//System.out.println(r + " +++ " + fd.elementAt(1).toString()); //////++++++++++++++++
						//}

		//			}
		//		}
		//	}
		} // for

	  // put the dbeTable in the scrollPane
	  setViewportView(dbeTable);
	  dbeTable.setPreferredScrollableViewportSize(new Dimension(380, 150));

     // add the mouseListener
	  dbetl = new DataBaseEntityTableListener();
	  dbeTable.addMouseListener(dbetl);

  } // constuctor
} // class
class  MyEditor extends AbstractCellEditor implements TableCellEditor {
      /**
   Defines a cell editor that saves the content of the table cell on mouse exit
   */
    public MyEditor() {
        this.text = new JTextField();
        this.text.addFocusListener(new FocusListener() {
         public void focusGained(FocusEvent e) {
            }
            
            public void focusLost(FocusEvent e) {
                stopCellEditing();
        }});
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value, 
                                                                           boolean isselected, int row, int col) {
        this.text.setText((String)value);
        return this.text;
    }
    
        
    public Object getCellEditorValue() {
        return this.text.getText();
    }
    
    private JTextField text;
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
