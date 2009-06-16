//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.util.*;
import java.io.*;
import javax.swing.*;


/**
 * <p>Title: FieldData</p>
 * <p>Description: Custom vector for storing the fields</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class FieldData extends Vector implements Serializable
{
  public String  m_field;
  public String  m_filler;
  public Boolean m_approved;
  public String  m_mplanning;
	//public ImageIcon m_mplanning;
  public LangCombo m_mplanning2;

  /*
  public FieldData()
  { // 1st constructor (for use in DataBaseTables)

	 //int ii = DataBaseTable.m_data.m_vector.size();

    m_field = new String("User-field-");// + (ii + 1));
    m_filler = new String("string");
    m_approved = new Boolean(false);
    m_mplanning = new String("");
	 addElement(m_field);
	 addElement(m_filler);
	 addElement(m_approved);
	 addElement(m_mplanning);

  }
  */

  public FieldData(int i)
  { // 1st constructor (for use in DataBaseTables)
		m_field = new String("New-user-field");
		m_filler = new String("String");
		m_approved = new Boolean(true);
		m_mplanning = new String("0EN 0IT 0EL");
		addElement(m_field);
		addElement(m_filler);
		addElement(m_approved);
		addElement(m_mplanning);
  }
  
 

  /*
  public FieldData(int i)
  { // 1st constructor (for use in DataBaseTables)

    m_field = new String("User-field-" + i);
    m_filler = new String("string");
    m_approved = new Boolean(false);
    m_mplanning = new String("");
	 addElement(m_field);
	 addElement(m_filler);
	 addElement(m_approved);
	 addElement(m_mplanning);

  } */

  /** EXPERIMENTAL
  public FieldData(String a)
  { // 1st constructor (for use in DataBaseTables)

	 int ii = DataBaseTable.m_data.m_vector.size();

    m_field = new String("User-field-" + (ii + 1));
    m_filler = new String("string");
    m_approved = new Boolean(false);

	 a = m_field;
    m_mplanning2 = new LangCombo(a);
	 addElement(m_field);
	 addElement(m_filler);
	 addElement(m_approved);
	 addElement(m_mplanning);

  }
  */

  public FieldData(String field, String filler, boolean approved, String mplanning)
  { // 2nd constructor (for use in DataBaseTables)
		m_field = field;
		m_filler = filler;
		m_approved = new Boolean(approved);
		m_mplanning = mplanning;
		addElement(m_field);
		addElement(m_filler);
		addElement(m_approved);
		addElement(m_mplanning);
  }


  public FieldData(String field, String filler)
  { // 3rd constructor (for use in DataBaseTableEntities)
		m_field = field;
		m_filler = filler;
		addElement(m_field);
		addElement(m_filler);
  }

}//FieldData