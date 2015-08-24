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
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.JTree;

/**
 *
 * <p>Title: CheckCellRenderer</p>
 * <p>Description: A custom CheckCellRenderer class</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */

public class CheckCellRenderer extends JCheckBox implements TableCellRenderer
{
  protected static Border m_noFocusBorder;

  public CheckCellRenderer() 
  {
    super();
    m_noFocusBorder = new EmptyBorder(1, 2, 1, 2);
    setOpaque(true);
    setBorder(m_noFocusBorder);
  }

  public Component getTableCellRendererComponent(JTable table,
   Object value, boolean isSelected, boolean hasFocus, int row, int column)
  {
    if (value instanceof Boolean) 
    {
      Boolean b = (Boolean)value;
      setSelected(b.booleanValue());
    }

    setBackground(isSelected && !hasFocus ?
      table.getSelectionBackground() : table.getBackground());
    setForeground(isSelected && !hasFocus ?
      table.getSelectionForeground() : table.getForeground());

    setHorizontalAlignment(SwingConstants.CENTER);

    setFont(table.getFont());
    setBorder(hasFocus ? UIManager.getBorder(
      "Table.focusCellHighlightBorder") : m_noFocusBorder);

    return this;
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
