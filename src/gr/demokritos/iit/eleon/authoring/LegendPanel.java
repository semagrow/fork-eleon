//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

/**
 * <p>Title: LegendPanel</p>
 * <p>Description: The panel showing the legends</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class LegendPanel extends JPanel implements ActionListener 
{
  public static Color colorG = new Color(100,100,100);
  public static Color colorW = new Color(255,255,255);
  public static Font smallfont = new Font(Mpiro.selectedFont,Font.PLAIN,10);
  public static Border border = new CompoundBorder(
  new EtchedBorder(EtchedBorder.RAISED),
  new EtchedBorder(EtchedBorder.LOWERED));
  JButton b;

  public LegendPanel(int i) 
  {
	  JPanel legendPanel = new JPanel();
		//JPanel legendPanel = new JPanel(new ColumnLayout(0,0,2,1));
		legendPanel.setBorder(border);
		legendPanel.setBackground(colorW);
		legendPanel.setPreferredSize(new Dimension(60,300));
		
		//JLabel legend = new JLabel("Legend");
		String legend_text = LangResources.getString(Mpiro.selectedLocale, "legend_legendtext");
		KLabel legend = new KLabel(legend_text);
		legendPanel.add(legend);
	
	  /** The elements of the second tab's legend */
	  if (i==0) 
	  {
			String user_text = LangResources.getString(Mpiro.selectedLocale, "user_legendtext");
			String type_text = LangResources.getString(Mpiro.selectedLocale, "(user)types_legendtext");
			G g1 = new G(user_text, type_text, new ImageIcon(Mpiro.obj.image_user) );
			legendPanel.add(g1);
	  }

		/** The elements of the second tab's legend */
		if (i==1) 
		{
			String basic_text = LangResources.getString(Mpiro.selectedLocale, "basic_legendtext");
			String entity_text = LangResources.getString(Mpiro.selectedLocale, "entity_legendtext");
			String types1_text = LangResources.getString(Mpiro.selectedLocale, "(entity)types_legendtext");
			String entities_text = LangResources.getString(Mpiro.selectedLocale, "entities_legendtext");
			String generic_text = LangResources.getString(Mpiro.selectedLocale, "generic_legendtext");
			String data_text = LangResources.getString(Mpiro.selectedLocale, "data_legendtext");
			String types2_text = LangResources.getString(Mpiro.selectedLocale, "(data)types_legendtext");
			String types_text = LangResources.getString(Mpiro.selectedLocale, "types_legendtext");
			G g1 = new G(basic_text, types_text, new ImageIcon(Mpiro.obj.image_basic));
			G g2 = new G(entity_text, types1_text, new ImageIcon(Mpiro.obj.image_closed));
			G g3 = new G(entities_text, null, new ImageIcon(Mpiro.obj.image_leaf));
			G g4 = new G(generic_text, entities_text, new ImageIcon(Mpiro.obj.image_generic));
			G g5 = new G(data_text, types2_text, new ImageIcon(Mpiro.obj.image_built));
			
			legendPanel.add(g1);
			legendPanel.add(g2);
			legendPanel.add(g3);
			legendPanel.add(g4);
			legendPanel.add(g5);
	  }

	  /** The elements of the third tab's legend */
	  if (i==2) 
	  {
			String nouns_text = LangResources.getString(Mpiro.selectedLocale, "nouns_legendtext");
			String verbs_text = LangResources.getString(Mpiro.selectedLocale, "verbs_legendtext");
			String language_text = LangResources.getString(Mpiro.selectedLocale, "language_legendtext");
			String specific_text = LangResources.getString(Mpiro.selectedLocale, "specific_legendtext");
			G g1 = new G(nouns_text, null, new ImageIcon(Mpiro.obj.image_n));
			G g2 = new G(verbs_text, null, new ImageIcon(Mpiro.obj.image_v));
			G g3 = new G(language_text, specific_text, new ImageIcon(Mpiro.obj.image_leaf));
			
			legendPanel.add(g1);
			legendPanel.add(g2);
			legendPanel.add(g3);
	  }


		/** The elements of the fourth tab's legend */
		if (i==3) 
		{
			String basic_text = LangResources.getString(Mpiro.selectedLocale, "basic_legendtext");
			String entity_text = LangResources.getString(Mpiro.selectedLocale, "entity_legendtext");
			String types1_text = LangResources.getString(Mpiro.selectedLocale, "(entity)types_legendtext");
			String entities_text = LangResources.getString(Mpiro.selectedLocale, "entities_legendtext");
			String generic_text = LangResources.getString(Mpiro.selectedLocale, "generic_legendtext");
			String data_text = LangResources.getString(Mpiro.selectedLocale, "data_legendtext");
			String types2_text = LangResources.getString(Mpiro.selectedLocale, "(data)types_legendtext");
			String types_text = LangResources.getString(Mpiro.selectedLocale, "types_legendtext");
			G g1 = new G(basic_text, types_text, new ImageIcon(Mpiro.obj.image_basic));
			G g2 = new G(entity_text, types1_text, new ImageIcon(Mpiro.obj.image_closed));
			G g3 = new G(entities_text, null, new ImageIcon(Mpiro.obj.image_leaf));
			G g4 = new G(generic_text, entities_text, new ImageIcon(Mpiro.obj.image_generic));
			G g5 = new G(data_text, types2_text, new ImageIcon(Mpiro.obj.image_built));
			
			legendPanel.add(g1);
			legendPanel.add(g2);
			legendPanel.add(g3);
			legendPanel.add(g4);
			legendPanel.add(g5);
		}
	  setLayout(new BorderLayout());
	  add("Center", legendPanel);
	}	  // constructor


	/** An instance of the following class is an element of the legend,
			composed by a top line, a tree icon and a short text
			(one or two labels) */

	public class G extends JPanel 
	{
		public G(String s1, String s2, ImageIcon ii) 
		{
			JPanel linePanel = new JPanel();
			linePanel.setBorder(new LineBorder((new Color(200,200,200)), 1));
			linePanel.setPreferredSize(new Dimension(40,1));
			
			b = new JButton("", ii);
			b.setBackground(colorW);
			b.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
			b.addActionListener(LegendPanel.this);
			
			JButton up = new JButton(s1);
			up.setBorder(new EmptyBorder(0,0,0,0));
			up.setForeground(colorG);
			up.setBackground(colorW);
			up.setFont(smallfont);
			JButton down = new JButton(s2);
			down.setBorder(new EmptyBorder(0,0,0,0));
			down.setForeground(colorG);
			down.setBackground(colorW);
			down.setFont(smallfont);
			
			setLayout(new ColumnLayout(0,0,1,1));
			add(linePanel);
			add(b);
			add(up);
			add(down);
			
			setOpaque(false);
		}
	}

  public void actionPerformed(ActionEvent e) 
  {
    if (e.getSource() == b) 
    {
		  //System.out.println("last");
    }
  }

}