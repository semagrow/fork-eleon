//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

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