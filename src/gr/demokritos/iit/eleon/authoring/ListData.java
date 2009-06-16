/*
 * ListData.java
 *
 * Created on 4 ����������� 2009, 2:25 ��
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

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