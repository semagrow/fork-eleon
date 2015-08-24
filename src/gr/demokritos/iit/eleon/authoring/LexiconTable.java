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
import java.io.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import javax.swing.tree.*;
import javax.swing.JTree;


public class LexiconTable extends JTable
{
	static JTable lexiconTable;
	static DefaultTableModel m_data;
	protected	Vector initVector;
	protected	LexiconDefaultVector ldf;
	
	public LexiconTable(int tableID) 
	{
	  setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	  JTableHeader header = getTableHeader();
	  header.setUpdateTableInRealTime(false);
	  setAutoCreateColumnsFromModel(false);
	} // constuctor
} //class


class LexiconFieldData extends Vector
{
  public String  m_tense;
  public String  m_gender;
  public String  m_number;
  public String  m_person;
  public String  m_voice;
  public String  m_verbForm;
  public String  m_participleForm;
  public Boolean m_irregular;


  public LexiconFieldData(String tense, String number, String person, String verbForm, boolean irregular)
  { // 1st constructor (for verb-italian-1)  ////////OLD// & verb-greek-2)
		m_tense = tense;
		m_number = number;
		m_person = person;
		m_verbForm = verbForm;
		m_irregular = new Boolean(irregular);
		addElement(m_tense);
		addElement(m_number);
		addElement(m_person);
		addElement(m_verbForm);
		addElement(m_irregular);
  }


  public LexiconFieldData(String gender, String number, String participleForm, boolean irregular)
  { // 2nd constructor (for verb-italian-2)
		m_gender = gender;
		m_number = number;
		m_participleForm = participleForm;
		m_irregular = new Boolean(irregular);
		addElement(m_gender);
		addElement(m_number);
		addElement(m_participleForm);
		addElement(m_irregular);
  }


  public LexiconFieldData(String tense, String voice, String number, String person, String verbForm, boolean irregular)
  { // 3rd constructor (for verb-greek-1)
		m_tense = tense;
		m_voice = voice;
		m_number = number;
		m_person = person;
		m_verbForm = verbForm;
		m_irregular = new Boolean(irregular);
		addElement(m_tense);
		addElement(m_voice);
		addElement(m_number);
		addElement(m_person);
		addElement(m_verbForm);
		addElement(m_irregular);
  }


  public LexiconFieldData(String tense, String verbForm, boolean irregular)
  { // 4th constructor (for verb-greek-2)
		m_tense = tense;
		m_verbForm = verbForm;
		m_irregular = new Boolean(irregular);
		addElement(m_tense);
		addElement(m_verbForm);
		addElement(m_irregular);
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
