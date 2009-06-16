//  "M-PIRO AUTHORING TOOL v.4.1"
//  Copyright (c) 2001, 2002, 2003
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.tree.*;
import javax.swing.JTree;


/**
 * <p>Title: LangDependentPanel</p>
 * <p>Description: The panel for English microplan editing</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Maria Prospathopoulou
 * @version 1.0
 */
public class LangDependentPanel extends JPanel implements ActionListener
{
	static JLabel ENLabel;
	static JLabel ITLabel;
	static JLabel GRLabel;

	static JPanel englishPanel;
	static JPanel italianPanel;
	static JPanel greekPanel;
	static JPanel allPanel;

	static String fieldName;
	//static int n;

	//static Vector nodeTable;
	static DataBaseEntityTable ENdbet;
	static DataBaseEntityTable ITdbet;
	static DataBaseEntityTable GRdbet;
	static DataBaseEntityTableModel ENdbetm;
	static DataBaseEntityTableModel ITdbetm;
	static DataBaseEntityTableModel GRdbetm;


	/**
	 * Constructor
	 * @param fieldName The name of the language dependent field that is being editted
	 */
	public LangDependentPanel(String fName)
	{
		fieldName = fName;

		ENLabel = new JLabel(LangResources.getString(Mpiro.selectedLocale, "english_action"));
		ENLabel.setPreferredSize(new Dimension(360, 20));
		ENLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,14));
		ENLabel.setForeground(Color.black);

		ITLabel = new JLabel(LangResources.getString(Mpiro.selectedLocale, "italian_action"));
		ITLabel.setPreferredSize(new Dimension(360, 20));
		ITLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,14));
		ITLabel.setForeground(Color.black);

		GRLabel = new JLabel(LangResources.getString(Mpiro.selectedLocale, "greek_action"));
		GRLabel.setPreferredSize(new Dimension(360, 20));
		GRLabel.setFont(new Font(Mpiro.selectedFont,Font.BOLD,14));
		GRLabel.setForeground(Color.black);

		englishPanel = new JPanel(new BorderLayout());
		englishPanel.setPreferredSize(new Dimension(390, 55));
		englishPanel.add(BorderLayout.NORTH, ENLabel);

		italianPanel = new JPanel(new BorderLayout());
		italianPanel.setPreferredSize(new Dimension(390, 55));//prepei na einai analoga meta pedia
		italianPanel.add(BorderLayout.NORTH, ITLabel);

		greekPanel = new JPanel(new BorderLayout());
		greekPanel.setPreferredSize(new Dimension(390, 55));//prepei na einai analoga meta pedia
		greekPanel.add(BorderLayout.NORTH, GRLabel);

		allPanel = new JPanel(new BorderLayout());
		allPanel.setPreferredSize(new Dimension(390, 165));//prepei na einai analoga meta pedia


		NodeVector nodeVector = (NodeVector)QueryHashtable.mainDBHashtable.get(DataBasePanel.last.toString());
		//nodeTable = (Vector)nodeVector.elementAt(0);
		Vector englishFieldsVector = (Vector)nodeVector.getEnglishFieldsVector();
		Vector italianFieldsVector = (Vector)nodeVector.getItalianFieldsVector();
		Vector greekFieldsVector = (Vector)nodeVector.getGreekFieldsVector();

		//FieldData ff =(FieldData) engl.elementAt(n);
		Vector ENFieldVector = new Vector();
		Vector ITFieldVector = new Vector();
		Vector GRFieldVector = new Vector();

		if(fieldName.equalsIgnoreCase("title"))
		{
			ENFieldVector.clear();
			ITFieldVector.clear();
			GRFieldVector.clear();
			FieldData enf =(FieldData)englishFieldsVector.elementAt(0);
			ENFieldVector.addElement(enf);
			FieldData itf =(FieldData)italianFieldsVector.elementAt(0);
			ITFieldVector.addElement(itf);
			FieldData grf =(FieldData)greekFieldsVector.elementAt(0);
			GRFieldVector.addElement(grf);
		}
		else if(fieldName.equalsIgnoreCase("name"))
		{
			greekPanel.setPreferredSize(new Dimension(390, 90));
			allPanel.setPreferredSize(new Dimension(390, 200));

			ENFieldVector.clear();
			ITFieldVector.clear();
			GRFieldVector.clear();

			FieldData enf =(FieldData)englishFieldsVector.elementAt(1);
			ENFieldVector.addElement(enf);
			//System.out.println(ENFieldVector);
			FieldData itf =(FieldData)italianFieldsVector.elementAt(1);
			ITFieldVector.addElement(itf);
			//System.out.println(ITFieldVector);
			FieldData grf1 =(FieldData)greekFieldsVector.elementAt(1);
			FieldData grf2 =(FieldData)greekFieldsVector.elementAt(2);
			FieldData grf3 =(FieldData)greekFieldsVector.elementAt(3);
			GRFieldVector.addElement(grf1);
			GRFieldVector.addElement(grf2);
			GRFieldVector.addElement(grf3);
			//System.out.println(GRFieldVector);
		}
		else if(fieldName.equalsIgnoreCase("shortname"))
		{
			greekPanel.setPreferredSize(new Dimension(390, 90));
			allPanel.setPreferredSize(new Dimension(390, 200));

			ENFieldVector.clear();
			ITFieldVector.clear();
			GRFieldVector.clear();

			FieldData enf =(FieldData)englishFieldsVector.elementAt(2);
			ENFieldVector.addElement(enf);
			FieldData itf =(FieldData)italianFieldsVector.elementAt(3);
			ITFieldVector.addElement(itf);
			FieldData grf1 =(FieldData)greekFieldsVector.elementAt(5);
			FieldData grf2 =(FieldData)greekFieldsVector.elementAt(6);
			FieldData grf3 =(FieldData)greekFieldsVector.elementAt(7);
			GRFieldVector.addElement(grf1);
			GRFieldVector.addElement(grf2);
			GRFieldVector.addElement(grf3);
		}
		else if(fieldName.equalsIgnoreCase("notes"))
		{
			ENFieldVector.clear();
			ITFieldVector.clear();
			GRFieldVector.clear();
			FieldData enf =(FieldData)englishFieldsVector.elementAt(3);
			ENFieldVector.addElement(enf);
			FieldData itf =(FieldData)italianFieldsVector.elementAt(5);
			ITFieldVector.addElement(itf);
			FieldData grf =(FieldData)greekFieldsVector.elementAt(10);
			GRFieldVector.addElement(grf);
		}
		else if(fieldName.equalsIgnoreCase("gender"))
		{
			italianPanel.setPreferredSize(new Dimension(390, 70));
			greekPanel.setPreferredSize(new Dimension(390, 70));
			allPanel.setPreferredSize(new Dimension(390, 200));

			ENFieldVector.clear();
			ITFieldVector.clear();
			GRFieldVector.clear();

			FieldData enf =(FieldData)englishFieldsVector.elementAt(4);
			ENFieldVector.addElement(enf);
			FieldData itf1 =(FieldData)italianFieldsVector.elementAt(2);
			FieldData itf2 =(FieldData)italianFieldsVector.elementAt(4);
			ITFieldVector.addElement(itf1);
			ITFieldVector.addElement(itf2);
			FieldData grf1 =(FieldData)greekFieldsVector.elementAt(4);
			FieldData grf2 =(FieldData)greekFieldsVector.elementAt(8);
			GRFieldVector.addElement(grf1);
			GRFieldVector.addElement(grf2);
		}
		else if(fieldName.equalsIgnoreCase("number"))
		{
			ENFieldVector.clear();
			ITFieldVector.clear();
			GRFieldVector.clear();
			FieldData enf =(FieldData)englishFieldsVector.elementAt(5);
			ENFieldVector.addElement(enf);
			FieldData itf =(FieldData)italianFieldsVector.elementAt(6);
			ITFieldVector.addElement(itf);
			FieldData grf =(FieldData)greekFieldsVector.elementAt(10);
			GRFieldVector.addElement(grf);
		}
		else if(fieldName.equalsIgnoreCase("item-type-description"))
		{
			ENFieldVector.clear();
			ITFieldVector.clear();
			GRFieldVector.clear();
			FieldData enf =(FieldData)englishFieldsVector.elementAt(6);
			ENFieldVector.addElement(enf);
			FieldData itf =(FieldData)italianFieldsVector.elementAt(7);
			ITFieldVector.addElement(itf);
			FieldData grf =(FieldData)greekFieldsVector.elementAt(11);
			GRFieldVector.addElement(grf);
		}

		ENdbetm = new DataBaseEntityTableModel(ENFieldVector, false);
		//System.out.println(ENdbetm.getValueAt(0,0));
		ENdbet = new DataBaseEntityTable(ENdbetm);
		//System.out.println(ENdbet.getValueAt(0,0));
		ITdbetm = new DataBaseEntityTableModel(ITFieldVector, false);
		ITdbet = new DataBaseEntityTable(ITdbetm);
		GRdbetm = new DataBaseEntityTableModel(GRFieldVector, false);
		//System.out.println(GRdbetm.getValueAt(0,0));
		GRdbet = new DataBaseEntityTable(GRdbetm);
		//System.out.println(GRdbet.getValueAt(0,0));

		englishPanel.add(BorderLayout.CENTER, ENdbet);
		italianPanel.add(BorderLayout.CENTER, ITdbet);
		greekPanel.add(BorderLayout.CENTER, GRdbet);

		allPanel.add(BorderLayout.NORTH, englishPanel);
		allPanel.add(BorderLayout.CENTER, italianPanel);
		allPanel.add(BorderLayout.SOUTH, greekPanel);

		add(allPanel);
	}//constructor

	public void actionPerformed(ActionEvent e)
	{}

}//class
