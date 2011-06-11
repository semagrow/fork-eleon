/***************

<p>Title: Flag Panel</p>

<p>Description:
A panel of 3 language flags.
Language-independent state when none of them is selected.
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2011 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Dimitris Spiliotopoulos (2002)
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)

***************/



package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class FlagPanel extends JPanel
{
	// used for FlagPanel(); i.e. for Mpirp frame
	public static ImageIcon ICON_X = new ImageIcon(Mpiro.obj.image_comboX);
	public static ImageIcon ICON_E = new ImageIcon(Mpiro.obj.image_comboE);
	public static ImageIcon ICON_I = new ImageIcon(Mpiro.obj.image_comboI);
	public static ImageIcon ICON_G = new ImageIcon(Mpiro.obj.image_comboG);

	// used for FlagPanel(int)
	public static ImageIcon ICON_EN = new ImageIcon(Mpiro.obj.image_uk);
	public static ImageIcon ICON_IT = new ImageIcon(Mpiro.obj.image_italy);
	public static ImageIcon ICON_GR = new ImageIcon(Mpiro.obj.image_greece);
	public static ImageIcon ICON_INDEP = new ImageIcon(Mpiro.obj.image_independent);

	JButton languageButton;

	JButton enButton;
	JButton itButton;
	JButton grButton;
	JButton independentButton;

	public static int langID;

	public FlagPanel()
	{
	  languageButton = new JButton();
	  languageButton.setIcon(ICON_X);
	  setLayout(new GridLayout(1,1));
	  add(languageButton);
	  setOpaque(true);
	  setPreferredSize(new Dimension(70, 17));
  }  // constructor


  public FlagPanel(int componentID)
  {
		langID = 0;

		enButton = new JButton(ICON_EN);
		itButton = new JButton(ICON_IT);
		grButton = new JButton(ICON_GR);
		independentButton = new JButton(ICON_INDEP);

		setLayout(new GridLayout(1,3));
		add(enButton);
		add(itButton);
		add(grButton);
		setOpaque(true);
		setPreferredSize(new Dimension(60, 15));

		FlagPanelListener fpl = new FlagPanelListener(componentID);
		enButton.addActionListener(fpl);
		itButton.addActionListener(fpl);
		grButton.addActionListener(fpl);
		independentButton.addActionListener(fpl);
  }  // constructor


  class FlagPanelListener implements ActionListener
  {
	  int ii; // tab

	  public FlagPanelListener(int i)
	  {
		  ii = i;
	  }

	  public void actionPerformed(ActionEvent e)
	  {
		if (e.getSource() == enButton)
		{
				langID = 1;

				removeAll();
				add(independentButton);
				add(itButton);
				add(grButton);
				revalidate();
				repaint();

				if (ii == 1)
					refresh(LangResources.getString(Mpiro.selectedLocale, "englishFieldsOf_text"), langID);
			 }
		if (e.getSource() == itButton)
		{
				langID = 2;

				removeAll();
				add(enButton);
				add(independentButton);
				add(grButton);
				revalidate();
				repaint();

				if (ii == 1)
					refresh(LangResources.getString(Mpiro.selectedLocale, "italianFieldsOf_text"), langID);
			}
		if (e.getSource() == grButton)
		{
				langID = 3;

				removeAll();
				add(enButton);
				add(itButton);
				add(independentButton);
				revalidate();
				repaint();

				if (ii == 1)
					refresh(LangResources.getString(Mpiro.selectedLocale, "greekFieldsOf_text"), langID);
			}
		if (e.getSource() == independentButton)
		{
				langID = 0;

				removeAll();
				add(enButton);
				add(itButton);
				add(grButton);
				revalidate();
				repaint();

				if (ii == 1)
					refresh(LangResources.getString(Mpiro.selectedLocale, "languageIndependentFieldsOf_text"), langID);
			}
		} // actionPerformed
	} // class FlagPanelListener


  public void refresh(String lang, int iii)
  {
		DataBaseEntityTableModel dbetm = null;
		DataBaseEntityTable dbet = null;

		DataBasePanel.label01.setText(lang + "\"" + DataBasePanel.last + "\"");
		DataBasePanel.label01.revalidate();

		String parent = DataBasePanel.last.getParent().toString();
		String node = Mpiro.win.struc.nameWithoutOccur(DataBasePanel.last.toString());
		NodeVector nodeVector = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(node);

		// *********************************
		/* System.out.println("parent=" + parent);
		System.out.println("node=" + node);
		System.out.println("nodevector=" + nodeVector.toString());
		Vector indep = (Vector)nodeVector.getIndependentFieldsVector();
		Vector eng = (Vector)nodeVector.getEnglishFieldsVector();
		Vector ita = (Vector)nodeVector.getItalianFieldsVector();
		Vector gre = (Vector)nodeVector.getGreekFieldsVector();

		Vector TEMP = new Vector();
		TEMP.addElement(indep.elementAt(0));
		TEMP.addElement(indep.elementAt(1));
		TEMP.addElement(indep.elementAt(2));
		TEMP.addElement(eng.elementAt(0));
		TEMP.addElement(eng.elementAt(1));
		TEMP.addElement(eng.elementAt(2));
		TEMP.addElement(eng.elementAt(3));
		TEMP.addElement(eng.elementAt(4));
		TEMP.addElement(eng.elementAt(5));
		//	dbetm = new DataBaseEntityTableModel(TEMP);
		//	dbet = new DataBaseEntityTable(dbetm);
		//System.out.println("nodevector=" + nodeVector.toString()); */
		// *******************************

		if (iii == 0)
		{  // case 0 (independent)
			Vector indep = (Vector)nodeVector.getIndependentFieldsVector();
			dbetm = new DataBaseEntityTableModel(indep, true);
			dbet = new DataBaseEntityTable(dbetm);
		}
		if (iii == 1)
		{  // case 1 (english)
			Vector eng = (Vector)nodeVector.getEnglishFieldsVector();
			dbetm = new DataBaseEntityTableModel(eng, false);
			dbet = new DataBaseEntityTable(dbetm);
		}
		if (iii == 2)
		{  // case 2 (italian)
			Vector ita = (Vector)nodeVector.getItalianFieldsVector();
			dbetm = new DataBaseEntityTableModel(ita, false);
			dbet = new DataBaseEntityTable(dbetm);
		}
		if (iii == 3)
		{  // case 3 (greek)
			Vector gre = (Vector)nodeVector.getGreekFieldsVector();
			dbetm = new DataBaseEntityTableModel(gre, false);
			dbet = new DataBaseEntityTable(dbetm);
		}
		DataBasePanel.multiTable.removeAll();
		DataBasePanel.multiTable.add(dbet);
		DataBasePanel.multiTable.revalidate();
		DataBasePanel.multiTable.repaint();
  }

  public void setMpiroFlagPanelLanguage(String lang)
  {
	if (lang.equalsIgnoreCase("ENGLISH")) {languageButton.setIcon(ICON_E);}
	else if (lang.equalsIgnoreCase("ITALIAN")) {languageButton.setIcon(ICON_I);}
	else if (lang.equalsIgnoreCase("GREEK")) {languageButton.setIcon(ICON_G);}
	else {languageButton.setIcon(ICON_X);}
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

