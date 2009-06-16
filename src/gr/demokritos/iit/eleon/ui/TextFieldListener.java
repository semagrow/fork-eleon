//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.ui.lang.gr.GreekVerbPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.table.*;
import java.util.*;


public class TextFieldListener extends FocusAdapter
{
	static JTextField selectedField = null;

	public TextFieldListener() 
	{}

	public void focusGained (FocusEvent fe)  
	{
		//System.out.println("FOCUS GAINED");
	}

	public void focusLost (FocusEvent fe)  
	{
		//System.out.println("FOCUS LOST");
		
		if (fe.getSource() == GreekVerbPanel.vbasetext) 
		{
		  GreekVerbPanel.printAndUpdate("vbasetext", null, GreekVerbPanel.vbasetext, null, null);
		}
		
		if (fe.getSource() == GreekVerbPanel.vbasetext2) 
		{
		  GreekVerbPanel.printAndUpdate("vbasetext2", null, GreekVerbPanel.vbasetext2, null, null);
		}
		
		if (fe.getSource() == GreekVerbPanel.infText) 
		{
		  GreekVerbPanel.printAndUpdate("infText", null, GreekVerbPanel.infText, null, null);
		}
		
		if (fe.getSource() == GreekVerbPanel.apText) 
		{
		  GreekVerbPanel.printAndUpdate("apText", null, GreekVerbPanel.apText, null, null);
		}
	}
} // TextAreaListener