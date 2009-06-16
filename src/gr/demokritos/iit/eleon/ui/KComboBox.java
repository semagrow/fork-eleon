//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.*;

import java.awt.*;
import javax.swing.*;


/**
* <p>Title: KComboBox</p>
* <p>Description: Custom JComboBox</p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: NCSR "Demokritos"</p>
* @author Dimitris Spiliotopoulos
* @version 1.0
*/
public class KComboBox extends JComboBox 
{
	static Font font= new Font(Mpiro.selectedFont,Font.PLAIN,11);
	static Color c = new Color(235, 235, 235);	    // white
	
     
        
	public KComboBox() 
	{
    setBackground(c);
    setFont(font);
	}
	
	public KComboBox(String[] string) 
	{
    int l = string.length;
    for (int e = 0; e < l; e++) 
    {
			addItem(string[e]);
    }

    setBackground(c);
    setFont(font);
	}
          
       
}