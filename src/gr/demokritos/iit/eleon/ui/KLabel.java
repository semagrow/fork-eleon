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
 * <p>Title: KLabel</p>
 * <p>Description: Custom JLabel</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis
 * @version 1.0
 */
public class KLabel extends JLabel 
{
  static Font font= new Font(Mpiro.selectedFont,Font.BOLD,11);
  public KLabel(String text)
  {
    setText(text);
    setFont(font);
    setForeground(new Color(102,102,153));
  }
}