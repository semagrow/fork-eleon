//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * <p>Title: ColorRenderer</p>
 * <p>Description: A custom ColorRenderer</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
//Edited and enhanced by Theofilos Nickolaou
public class ColorRenderer extends JLabel implements TableCellRenderer 
{
	final static int INHERITED = 0;
	final static int ADDED = 1;
	final static int GENERAL = 2;
	final int inherited;

	public ColorRenderer(int inherited) 
	{
		this.inherited = inherited;
		setOpaque (true);
	}

	public Component getTableCellRendererComponent(JTable table,
	 	Object value, boolean isSelected, boolean hasFocus, int row, int column) 
	{
		int limit = row;
		int statusValue = GENERAL;

		if (limit < inherited)
		{
			statusValue = INHERITED;
		} 
		else if (limit >= inherited)
		{
			statusValue = ADDED;
		}

		if (isSelected) 
		{
			setBackground (new Color(200,200,255));
			switch (statusValue) 
			{
        case INHERITED:
	   	  setBackground (new Color(200,200,255));
           setForeground (Color.gray);
           break;
        case ADDED:
           setForeground (Color.black);
           break;
        default:
           setForeground (Color.cyan);
           break;
			}
		} 
		else 
		{
			setForeground (Color.black);
			switch (statusValue) 
			{
        case INHERITED:
			  setForeground (Color.gray);
           setBackground (new Color(228,228,195));
           break;
        case ADDED:
           setBackground (Color.white);
           break;
        default:
           setBackground (Color.cyan);
           break;
			}
		}

		if ((value != null)) //&& (!value.toString().startsWith("ICON_"))) //theofilos
		{
			if(value.toString().endsWith("EL"))
			{
				setHorizontalAlignment(SwingConstants.CENTER); //theofilos
			}
			setText (value.toString());
			setIcon(null);
		}
		/*else if ((value != null) && (value.toString().startsWith("ICON_")) && (limit >= inherited))
		{
			if (value.toString().endsWith("EIG"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_EIG);
			}
			else if (value.toString().endsWith("EI"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_EI);
			}
			else if (value.toString().endsWith("EG"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_EG);
			}
			else if (value.toString().endsWith("IG"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_IG);
			}
			else if (value.toString().endsWith("E"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_E);
			}
			else if (value.toString().endsWith("I"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_I);
			}
			else if (value.toString().endsWith("G"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_G);
			}
			else if (value.toString().endsWith("X"))
			{
				setHorizontalAlignment(SwingConstants.CENTER);
				setText("");
				setIcon(LangCombo.ICON_X);
			}
		}*/ //theofilos
		
		return this;
   }
}