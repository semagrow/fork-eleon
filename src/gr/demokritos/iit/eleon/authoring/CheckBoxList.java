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
import java.io.*;

/**
* A custom CheckBoxList
*
* @author Stamatakis Kostas, Spiliotopoulos Dimitris (kstam, dspiliot @iit.demokritos.gr).
* @version 1.0
**/
public class CheckBoxList extends JScrollPane
{

	public JList list;
	public CheckListCellRenderer renderer;
	public CheckListListener lst;
	public Vector m_vector;

	public CheckBoxList(Vector vector)
	{
		m_vector = vector;

		list = new JList(vector);
 		renderer = new CheckListCellRenderer();
		list.setCellRenderer(renderer);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lst = new CheckListListener(this);
		list.setBorder(new EmptyBorder(new Insets(0,5,0,5)));
		list.addMouseListener(lst);
		list.addKeyListener(lst);

		setViewportView(list);
	}

	public Vector getListVector() 
	{
		return m_vector;
	}

	/**
	* Returns a vector of selected items' positions
	* @return The positions vector
	*/
	public Vector getPositionsVector() 
	{

		Vector positionsVector = new Vector();
		int total = list.getModel().getSize();
		// loop (until "total") to find which items are selected
		for (int x=0; x<total; x++) 
		{
			ListData ld = (ListData)list.getModel().getElementAt(x);
			if (ld.isSelected()) 
			{
				Integer integer = new Integer(x);
				positionsVector.addElement(integer);
			}
		}
		return positionsVector;

	}

  /**
   * Returns a vector of selected items' names
   * @return A vector of selected items' names
   */
	public Vector getItemsVector() 
	{

		Vector itemsVector = new Vector();
		int total = list.getModel().getSize();
		// loop (until "total") to find which items are selected
		for (int x=0; x<total; x++) 
		{
			ListData ld = (ListData)list.getModel().getElementAt(x);
			if (ld.isSelected()) 
			{
				String item = ld.toString();
				itemsVector.addElement(item);
			}
		}
		return itemsVector;
	}


	/**
	 * A method that takes the elements of a vector,
	 * compares them with the items of the list and
	 * if they are equal they become checked.
	 * @param v The Vector
	 */
	public void setChecked(Vector v) 
	{

		int listLength = list.getModel().getSize();

		String b[] = null;
		b = new String[200];
		for (int f=0; f<v.size(); f++) 
		{
			b[f] = v.elementAt(f).toString();
		}

		for (int k=0; k<listLength; k++) 
		{
			ListData l = (ListData)list.getModel().getElementAt(k);
			//Component c = renderer.getListCellRendererComponent(list, l, k, true, false);
			//JCheckBox c = (JCheckBox)list.getModel().getElementAt(k);
			for (int y=0; y<v.size(); y++) 
			{
				if (b[y].compareToIgnoreCase(l.toString())==0) 
				{
					l.setSelected(true);
					//System.out.println(b[y]);
					//System.out.println(renderer.getListCellRendererComponent(list, l, k, true, false));
					//Component c = renderer.getListCellRendererComponent(list, l, k, true, false);
					//c.setEnabled(false); ////////////////////
					//c.setText("     ");
					//c.setBackground(new Color(50,50,50));
				} 
				else 
				{
					//c.setEnabled(true);
				}
			}
		}

		list.repaint();
	}
        
        
       

	/**
	 *  A method that clears all selections
	 */
	public void clear() 
	{

		int listLength = list.getModel().getSize();
//ListModel lm=list.getModel();
		for (int k=0; k<listLength; k++) 
		{
                  // ListModel lm= list.getModel();
			ListData l = (ListData) list.getModel().getElementAt(k);
			l.setSelected(false);
		}
		list.repaint();
	}
     

}

/**
 * The cellRenderer for the checkList
 */
class CheckListCellRenderer extends JCheckBox implements ListCellRenderer
{
	
	protected static Border m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);

	public CheckListCellRenderer()
	{
		super();
		setOpaque(true);
		setBorder(m_noFocusBorder);
	}

	public Component getListCellRendererComponent(JList list,
		Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		setText(value.toString());

		setBackground(isSelected ? list.getSelectionBackground() :
			list.getBackground());
		setForeground(isSelected ? list.getSelectionForeground() :
			list.getForeground());

		ListData data = (ListData)value;
		setSelected(data.isSelected());

		setFont(list.getFont());
		setBorder((cellHasFocus) ?
			UIManager.getBorder("List.focusCellHighlightBorder")
			 : m_noFocusBorder);

		return this;
	}
}

/**
 * The listener for the checkList
 */
class CheckListListener implements MouseListener, KeyListener
{
	protected CheckBoxList m_parent;
	protected JList list;

	public CheckListListener(CheckBoxList parent)
	{
		m_parent = parent;
		list = parent.list;
	}

	public void mouseClicked(MouseEvent e)
	{

		if (SwingUtilities.isRightMouseButton(e)) 
		{
			int index = list.locationToIndex(e.getPoint());
			if (index>=0) 
			{
				ListData data = (ListData)list.getModel().getElementAt(index);
				doCheck();
			}
		} 
		else 
		{ // if left click has been clicked
			int index = list.locationToIndex(e.getPoint());
			if (index>=0) 
			{
				ListData data = (ListData)list.getModel().getElementAt(index);
				doCheck();
			}
		}

	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyChar() == ' ')
			doCheck();
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	protected void doCheck()
	{
		int index = list.getSelectedIndex();
		if (index < 0)
			return;
		ListData data = (ListData)list.getModel().getElementAt(index);
		data.invertSelected();
		list.repaint();
	}
}

/**
 * The way the data are represented
 */
