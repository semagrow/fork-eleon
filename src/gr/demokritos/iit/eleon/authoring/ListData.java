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

import java.io.Serializable;

/**
 *
 * @author dimitris
 */
public class ListData implements Serializable
{
	protected String m_name;
	protected boolean m_selected;

	public ListData(String name)
	{
		m_name = name;
		m_selected = false;
	}
   /*     public boolean equals(ListData ld){            
            if (this.m_name.equalsIgnoreCase(ld.m_name)&&this.m_selected==ld.m_selected)
                return true;
            else
                return false;
        }
*/
	public String getName() { return m_name; }
	public void setSelected(boolean selected) { m_selected = selected;}
	public void invertSelected() { m_selected = !m_selected; }
	public boolean isSelected() { return m_selected; }
	public String toString() { return m_name; }
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
