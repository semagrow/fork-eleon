//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.Mpiro;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * <p>Title: KButton</p>
 * <p>Description: A custom JButton</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis
 * @version 1.0
 */
public class KButton extends JButton
{
	static Font font= new Font(Mpiro.selectedFont,Font.BOLD,12);
	static Color color1 = new Color(80,80,80);	    // dark gray
	static Color color2 = new Color(150,150,150);	 // light gray
	static Border border = new BevelBorder(BevelBorder.RAISED);
	
	public KButton(String text) 
	{
		setText(text);
		setForeground(color1);
		setFont(font);
		setBorder(border);
		//Insets in = this.getInsets();
		//System.out.println(in);
		
		//JPanel panel = new JPanel();
		//panel.setOpaque(false);
		//panel.setBorder(new EmptyBorder(new Insets(0,3,0,3)));
		//JLabel label = new JLabel(text);
		//panel.add(label);
		//label.setForeground(color);
		//label.setFont(font);
		//add(panel);
	}  // Constructor 1


	public KButton(String text1, String text2, boolean enabled) 
	{
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		panel.setBorder(new EmptyBorder(new Insets(3,3,3,3)));
		
		JLabel label1 = new JLabel(text1);
		JLabel label2 = new JLabel(text2);
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(0,0,0,0);
    //c.fill = GridBagConstraints.BOTH;
    c.anchor = GridBagConstraints.CENTER;
    c.weightx = 0.0; c.weighty = 0.0;
    c.gridx = 0; c.gridy = 0;
    c.gridwidth = 1;
		panel.add(label1, c);
		c.gridy = 1;
		panel.add(label2, c);
		
		add(panel);
		
		/** Formatting works */
		label1.setFont(font);
		label2.setFont(font);
		if (enabled == false) 
		{
	    label1.setForeground(color2);
	    label2.setForeground(color2);
	    setEnabled(false);
		} 
		else 
		{
	    label1.setForeground(color1);
	    label2.setForeground(color1);
	    setEnabled(true);
		}
		setBorder(border);
	}  // Constructor 2
   
    
  public KButton(ImageIcon ico) 
  {
		setIcon(ico);
	}//Constructor 3

}