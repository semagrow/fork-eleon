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

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;


/**
 * <p>Title: IconCellEditor</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class IconCellEditor extends JLabel implements TreeCellEditor, ActionListener
{
	protected JTree      m_tree = null;
	protected JTextField m_editor = null;
	protected IconData   m_item = null;
	protected int        m_lastRow = -1;
	protected long       m_lastClick = 0;
	protected Vector     m_listeners = null;

	public IconCellEditor(JTree tree)
	{
		super();
		m_tree = tree;
		m_listeners = new Vector();
	}

	public Component getTreeCellEditorComponent(JTree tree,
				Object value, boolean isSelected, boolean expanded,
				boolean leaf, int row)
	{
		if (value instanceof DefaultMutableTreeNode)
		{
			DefaultMutableTreeNode node =(DefaultMutableTreeNode)value;
			Object obj = node.getUserObject();
			if (obj instanceof IconData)
			{
				IconData idata = (IconData)obj;
				m_item = idata;
				// Reserve some more space...
				setText(idata.toString()+"     ");
				setIcon(idata.m_icon);
				setFont(tree.getFont());
				return this;
			}
		}
		return null;	// We don't support other objects...
	}

	public Object getCellEditorValue()
	{
		if (m_item != null && m_editor != null)
			m_item.m_data = m_editor.getText();
		return m_item;
	}

	public boolean isCellEditable(EventObject evt)
	{
		if (evt instanceof MouseEvent)
		{
			MouseEvent mEvt = (MouseEvent)evt;
			if (mEvt.getClickCount() == 1)
			{
				int row = m_tree.getRowForLocation(mEvt.getX(),
					mEvt.getY());
				if (row != m_lastRow)
				{
					m_lastRow = row;
					m_lastClick = System.currentTimeMillis();
					return false;
				}
				else if (System.currentTimeMillis()-m_lastClick > 1000)
				{
					m_lastRow = -1;
					m_lastClick = 0;
					prepareEditor();
					mEvt.consume();
					return true;
				}
				else
					return false;
			}
		}
		return false;
	}

	protected void prepareEditor()
	{
		if (m_item == null)
			return;
		String str = m_item.toString();

		m_editor = new JTextField(str);
		m_editor.addActionListener(this);
		m_editor.selectAll();
		m_editor.setFont(m_tree.getFont());
		m_editor.setCaretPosition(2);

		add(m_editor);
		revalidate();

		TreePath path = m_tree.getPathForRow(m_lastRow);
		m_tree.startEditingAtPath(path);
	}

	protected void removeEditor()
	{
		if (m_editor != null)
		{
			remove(m_editor);
			m_editor.setVisible(false);
			m_editor = null;
			m_item = null;
		}
	}

	public void doLayout()
	{
		super.doLayout();
		if (m_editor != null)
		{
			int offset = getIconTextGap();
			if (getIcon() != null) offset += getIcon().getIconWidth();
			Dimension cSize = getSize();
			//Dimension  eSize = m_editor.getPreferredSize();		             //+
			////int n = m_treepath.getPathCount(); 						          //+
			//Rectangle r = new Rectangle();							                //+
			//r = m_tree.getBounds(r);										             //+
			//eSize.width = r.width -(offset);// *n);						          //+
			//m_editor.setSize(eSize);							                      //+
			//m_editor.setLocation(offset, 0);				                      //+
			//m_editor.setBounds(offset, 0, eSize.width, cSize.height);		    //+
			m_editor.setBounds(offset, 0, cSize.width - offset,cSize.height);
			//setSize(new Dimension(eSize.width + offset, cSize.height));	    //+
		}
	}

	public boolean shouldSelectCell(EventObject evt)
	{
		return true;
	}

	public boolean stopCellEditing()
	{
		if (m_item != null)
			m_item.m_data = m_editor.getText();

		ChangeEvent e = new ChangeEvent(this);
		for (int k=0; k<m_listeners.size(); k++)
		{
			CellEditorListener l = (CellEditorListener)m_listeners.
				elementAt(k);
			l.editingStopped(e);
		}
		removeEditor();
		return true;
	}

	public void cancelCellEditing()
	{
		ChangeEvent e = new ChangeEvent(this);
		for (int k=0; k<m_listeners.size(); k++)
		{
			CellEditorListener l = (CellEditorListener)m_listeners.
				elementAt(k);
			l.editingCanceled(e);
		}
		removeEditor();
	}

	public void addCellEditorListener(CellEditorListener l)
	{
		m_listeners.addElement(l);
	}

	public void removeCellEditorListener(CellEditorListener l)
	{
		m_listeners.removeElement(l);
	}

	public void actionPerformed(ActionEvent e)
	{
		stopCellEditing();
		m_tree.stopEditing();
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
