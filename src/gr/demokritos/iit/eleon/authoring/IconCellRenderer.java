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
 * <p>Title: IconCellRenderer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class IconCellRenderer extends JLabel implements TreeCellRenderer
{
	protected Color m_textSelectionColor;
	protected Color m_textNonSelectionColor;
	protected Color m_bkSelectionColor;
	protected Color m_bkNonSelectionColor;
	protected Color m_borderSelectionColor;

	protected boolean m_selected;

	public IconCellRenderer()
	{
		super();
		m_textSelectionColor = UIManager.getColor("Tree.selectionForeground");
		m_textNonSelectionColor = UIManager.getColor("Tree.textForeground");
		m_bkSelectionColor = UIManager.getColor("Tree.selectionBackground");
		m_bkNonSelectionColor = UIManager.getColor("Tree.textBackground");
		m_borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
		setOpaque(false);
	}

	public Component getTreeCellRendererComponent(JTree tree,
					Object value, boolean sel, boolean expanded, boolean leaf,
					int row, boolean hasFocus)
	{
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
              //  
		Object obj = node.getUserObject();
		setText(obj.toString());
		if (obj instanceof IconData)
		{
			IconData idata = (IconData)obj;
			if (expanded)
				setIcon(idata.getOpenIcon());
			else
				setIcon(idata.getIcon());
		}
		else setIcon(null);

		setFont(tree.getFont());
		setForeground(sel ? m_textSelectionColor :
			m_textNonSelectionColor);
		setBackground(sel ? m_bkSelectionColor :
			m_bkNonSelectionColor);
		m_selected = sel;
		return this;
	}


	public void paint(Graphics g)
	{
		Color bColor = getBackground();
		Icon icon = getIcon();
		g.setColor(bColor);
		int offset = 0;
		if(icon != null && getText() != null)
			offset = (icon.getIconWidth() + getIconTextGap());
		g.fillRect(offset, 0, getWidth() - 1 - offset,
			getHeight() - 1);
		if (m_selected)
		{
			g.setColor(m_borderSelectionColor);
			g.drawRect(offset, 0, getWidth()-1-offset, getHeight()-1);
		}
		super.paint(g);
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
