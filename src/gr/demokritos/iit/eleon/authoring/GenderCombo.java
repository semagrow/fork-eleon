//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.KComboBox;


/**
 * <p>Title: GenderCombo</p>
 * <p>Description: Custom JComboBox for showing the gender</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class GenderCombo extends KComboBox 
{
  public GenderCombo() 
  {
	  addItem("masculine");
	  addItem("feminine");
	  addItem("neuter");
  }
}