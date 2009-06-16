//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

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