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
 * <p>Title: IconData</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class IconData
{
	protected Icon   m_icon;
	protected Icon   m_openIcon;
	protected Object m_data;

	public IconData(Icon icon, Object data)
	{
		m_icon = icon;
		m_openIcon = null;
		m_data = data;
	}

	public IconData(Icon icon, Icon openIcon, Object data)
	{
		m_icon = icon;
		m_openIcon = openIcon;
		m_data = data;
	}

	public Icon getIcon()
	{
		return m_icon;
	}

	public Icon getOpenIcon()
	{
		return m_openIcon!=null ? m_openIcon : m_icon;
	}

	public Object getObject()
	{
		return m_data;
	}

	public String toString()
	{
		return m_data.toString();
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
