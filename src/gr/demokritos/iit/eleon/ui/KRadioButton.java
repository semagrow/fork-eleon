//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.Mpiro;

import java.awt.*;
import javax.swing.*;


/**
 * <p>Title: KRadioButton</p>
 * <p>Description: Custom JRadioButton</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis
 * @version 1.0
 */
public class KRadioButton extends JRadioButton 
{
  static Font font= new Font(Mpiro.selectedFont,Font.BOLD,11);
  public KRadioButton(String text, boolean checked) 
  {
	  setText(text);
	  setFont(font);
	  if (checked == true) { setSelected(true); }
	  else {}
  }
}