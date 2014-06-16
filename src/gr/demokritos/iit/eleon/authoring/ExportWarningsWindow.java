//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;

/**
 * <p>Title: ExportWarningsWindow</p>
 * <p>Description: A window used during export to exprimo for showing warnings</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class ExportWarningsWindow extends JFrame 
{
	private static JTextArea tPane;
	static JScrollPane scroll;
	private JTextField key, result;
	private JButton clear, find, findAll;
	private int loc;
	
	private DefaultHighlighter high;
	private DefaultHighlighter.DefaultHighlightPainter highlight_painter;
	
	private static int occ;
	private static int total;
	
	private String previous;
	
	private Font times;
	
	private static int ratio;
	private static boolean knowRatio;
	
	public ExportWarningsWindow() 
	{
		super("Warnings during Export to XML");
		super.setIconImage(Mpiro.obj.image_corner);
		
		loc = -1;
		occ = 0;
		previous = "";
		total = 0;
		
		ratio = 0;
		knowRatio = false;

		times  = new Font("TimesRoman", Font.PLAIN, 14);
	
		high =new DefaultHighlighter();
		highlight_painter =new DefaultHighlighter.DefaultHighlightPainter(new Color(198,198,250));
	
		clear = new JButton("Clear");
		clear.setFocusPainted(false);
		clear.setForeground(Color.black);
		clear.setBackground(Color.white);
	
		key = new JTextField(15);
		key.setFont(times);
		key.setEditable(true);
		key.setBackground(Color.white);
		key.setForeground(Color.black);

		find = new JButton("Find");
		find.setFocusPainted(false);
		find.setForeground(Color.black);
		find.setBackground(Color.white);
		
		findAll = new JButton("FindAll");
		findAll.setFocusPainted(false);
		findAll.setForeground(Color.black);
		findAll.setBackground(Color.white);
		
		result = new JTextField(20);
		result.setFont(times);
		result.setEditable(false);
		result.setBackground(new Color(225, 225, 225));
		result.setForeground(Color.black);
		result.setBorder(null);
		
		FlowLayout searchFl = new FlowLayout(FlowLayout.LEFT);
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(searchFl);
		searchPanel.setBackground(new Color(225, 225, 225));
		searchFl.setVgap(2);
		searchFl.setHgap(10);
		
		searchPanel.add(clear);
		searchPanel.add(key);
		searchPanel.add(find);
		searchPanel.add(findAll);
		searchPanel.add(result);

		tPane = new JTextArea();//previously: 23,62
		tPane.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		tPane.setEditable(false); // allow editing
		//tPane.setLineWrap(true);
		//tPane.setWrapStyleWord(true);
		Insets insets = new Insets(5, 5, 5, 5);
		tPane.setMargin(insets);
		tPane.setBackground(Color.white);
		tPane.setForeground(Color.black);
		tPane.setHighlighter(high);
		scroll = new JScrollPane(tPane);
	
		Container c = getContentPane();
		c.add(searchPanel, BorderLayout.NORTH);
		c.add(scroll, BorderLayout.CENTER);
	
		Dimension dim = getToolkit().getScreenSize(); // get pixel size of this screen
		setSize((int)(dim.width/1.6), (int)(dim.height/1.6));
		setResizable(true);
		setLocation(15, 15); // place window at...(=centre of the screen)
	
		show(); // display window

		find.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!knowRatio) {getRatio();}
				shiftHighLight();
			}
		});

		findAll.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  allHighLights();
			}
		});
	
		key.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!knowRatio) {getRatio();}
				shiftHighLight();
			}
		});

		clear.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				loc = -1;
				occ = 0;
				
				try
				{
					key.setText("");
					result.setText("");
					high.removeAllHighlights();
				}
				catch(Exception highlightException){;}	
			}
		});

	}

  private void getRatio() 
  {
		try 
		{
	    int maximum_scroll = scroll.getVerticalScrollBar().getMaximum();
	    int no_lines = tPane.getLineOfOffset(tPane.getText().length());
	    ratio = maximum_scroll / no_lines;
	    knowRatio = true;
		} 
		catch(javax.swing.text.BadLocationException ble){;}
	}

  private void shiftHighLight() 
  {
		high.removeAllHighlights();
		int line = -1;
		if(key.getText().length()>0
		 	&& !key.getText().toLowerCase().equals(previous)) 
		{
			total = countOccurrences(key.getText());
		}

		if(key.getText().length()==0) {result.setText("no keyword entered");}

		else if(total==0) 
		{
			result.setText("no matches found");
		}

		else 
		{
	    if(!key.getText().toLowerCase().equals(previous))
	    {
				occ = 0;
				loc = -1;
	    }

	    if((loc = tPane.getText().toLowerCase().indexOf(key.getText().toLowerCase(), loc+1)) >= 0) 
	    {
    		try
    		{
			    high.addHighlight(loc,loc+key.getText().length(), highlight_painter);
			    line = tPane.getLineOfOffset(loc);
			    scroll.getVerticalScrollBar().setValue(line*ratio - (int)((((JPanel)getContentPane()).getHeight()-30)*.33));
    		}
    		catch(Exception highlightException){;}
	    }
	    occ++;
	    if(line>=0) 
	    {
				if(occ > total) {total = countOccurrences(key.getText());}
				result.setText("match " + occ + "/" + total + " at line " + line);
				previous = key.getText().toLowerCase();
	    }
	    else 
	    {
				result.setText("no more matches");
				occ = 0;
	    }
		}
	}

  private void allHighLights() 
  {
		high.removeAllHighlights();
		loc = -1;
		int occurrences = 0;
		if(key.getText().length()>0) 
		{
	    while((loc = tPane.getText().toLowerCase().indexOf(key.getText().toLowerCase(), loc+1)) >= 0) 
	    {
	  		try
	  		{
			    occurrences++;
			    high.addHighlight(loc,loc+key.getText().length(), highlight_painter);
    		}
    		catch(Exception highlightException){;}
	    }
	    result.setText(occurrences + " match" + (occurrences == 1 ? "" : "es")+ " found");
	    occ = 0;
	    loc = -1;
		}
		else result.setText("no keyword entered");
	}

	private int countOccurrences(String text)
	{
		int number = 0;
		int index = -1;
		while((index = tPane.getText().toLowerCase().indexOf(text.toLowerCase(), index+1)) >= 0) 
		{
			number++;
		}
		return number;
	}

	static void addText(String s) 
	{
		tPane.append(s + "\n");
		int in = scroll.getVerticalScrollBar().getMaximum();
		scroll.getVerticalScrollBar().setValue(in);
	}

}