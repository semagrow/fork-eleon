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
import gr.demokritos.iit.eleon.ui.KComboBox;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.JTree;

/**
 * <p>Title: FillerCombo</p>
 * <p>Description: Custom JComboBox for showing the fillers</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
class FillerCombo extends KComboBox 
{
	public FillerCombo() 
  {
		Vector toBeSortedVector = new Vector();
		Enumeration enumer = DataBasePanel.top.breadthFirstEnumeration();
		DefaultMutableTreeNode tmp;
		IconData id;
		while (enumer.hasMoreElements())
		{
			tmp = (DefaultMutableTreeNode) enumer.nextElement();
			Object o = (Object)(tmp.getUserObject());
			id = (IconData)o;
			Icon ii = id.getIcon();
			ImageIcon im = (ImageIcon)ii;
			if ((im != DataBasePanel.ICON_TOP) &&
			   
			   (im != DataBasePanel.ICON_TOP_B) &&
			   (im != DataBasePanel.ICON_GEI) &&
			   (im != DataBasePanel.ICON_GENERIC))
			{
				String choiceItem = tmp.toString();
                                if(choiceItem.contains("_occur")) continue;
				toBeSortedVector.addElement(choiceItem);
			}
		}
		
		toBeSortedVector = (Vector)QuickSort.quickSort(0, toBeSortedVector.size()-1, toBeSortedVector);
		for (int i = 0; i < toBeSortedVector.size(); i++)
		{
			addItem(toBeSortedVector.elementAt(i));
		}
  } // constructor 1


	public FillerCombo(DefaultMutableTreeNode node) 
	{
		Vector toBeSortedVector = new Vector();
		toBeSortedVector.addElement("");
		
		Hashtable h = Mpiro.win.struc.getChildrenEntities(node);
		           //System.out.println("(FillerCombo)---check use (node)- " + node);
		String item;
		
		if (h.isEmpty()) 
		{
			addItem("Select a " + "\"" + node + "\"");
		} 
		else 
		{
			Enumeration enumer = h.keys();
			while (enumer.hasMoreElements())
			{
				item = enumer.nextElement().toString();
				toBeSortedVector.addElement(item);
			}
		}
		toBeSortedVector = (Vector)QuickSort.quickSort(0, toBeSortedVector.size()-1, toBeSortedVector);
		for (int i = 0; i < toBeSortedVector.size(); i++)
		{
			addItem(toBeSortedVector.elementAt(i));
		}
	} // constructor 2

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
