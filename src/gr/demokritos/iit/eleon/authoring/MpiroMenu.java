//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.lang.Object;
import java.lang.*;
import java.io.*;


class MpiroMenu extends JMenuBar implements ActionListener
{
	static ImageIcon ICON_NEW = new ImageIcon(Mpiro.obj.image_new);
	static ImageIcon ICON_OPEN = new ImageIcon(Mpiro.obj.image_open);
	static ImageIcon ICON_SAVE = new ImageIcon(Mpiro.obj.image_save);
	static ImageIcon ICON_EXPORTTEXT = new ImageIcon(Mpiro.obj.image_exportText);
	//static ImageIcon ICON_EXPORTXML = new ImageIcon(Mpiro.obj.image_exportXML);
	static ImageIcon ICON_EXIT = new ImageIcon(Mpiro.obj.image_exit);
	
	static JMenu fileMenu;
	static JMenu lookfeelMenu;
	static JMenu optionsMenu;
	static JMenu helpMenu;
	static JMenuItem newFileItem;
	static JMenuItem openFileItem;
	static JMenuItem saveFileItem;
	static JMenuItem exportFileItem;
	static JMenuItem exitFileItem;

  final JFileChooser mm = new JFileChooser();


  public MpiroMenu() 
  {
		fileMenu = new JMenu(" File");
		lookfeelMenu = new JMenu(" Look & Feel");
		optionsMenu = new JMenu(" Options");
		helpMenu = new JMenu(" Help");
		add(fileMenu);
		add(lookfeelMenu);
		add(optionsMenu);
		add(helpMenu);
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription("The file items");
		
		lookfeelMenu.setMnemonic(KeyEvent.VK_L);
		lookfeelMenu.getAccessibleContext().setAccessibleDescription("The look and feel items");
		
		optionsMenu.setMnemonic(KeyEvent.VK_P);
		optionsMenu.getAccessibleContext().setAccessibleDescription("The options items");
		
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription("The help items");
		
		
		// the fileMenu
		newFileItem = new JMenuItem(" New", ICON_NEW);
		newFileItem.setMnemonic(KeyEvent.VK_N);
		fileMenu.add(newFileItem);

    openFileItem = new JMenuItem(" Open", ICON_OPEN);
    openFileItem.setMnemonic(KeyEvent.VK_O);
    //openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
    openFileItem.getAccessibleContext().setAccessibleDescription("Opens a new file");
    fileMenu.add(openFileItem);

    saveFileItem = new JMenuItem(" Save as...", ICON_SAVE);
    saveFileItem.setMnemonic(KeyEvent.VK_S);
    fileMenu.add(saveFileItem);

    fileMenu.addSeparator();

    exportFileItem = new JMenuItem(" Export...", ICON_EXPORTTEXT);
    exportFileItem.setMnemonic(KeyEvent.VK_E);
    fileMenu.add(exportFileItem);

    fileMenu.addSeparator();

    exitFileItem = new JMenuItem(" Exit", ICON_EXIT);
    exitFileItem.setMnemonic(KeyEvent.VK_X);
    fileMenu.add(exitFileItem);

		saveFileItem.addActionListener(MpiroMenu.this);
  }


	/* The actions the menu items */
	public void actionPerformed(ActionEvent e)
	{
	  Object source = e.getSource();
	  if (source == saveFileItem)
	  {
			mm.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			MpiroFileFilter filter = new MpiroFileFilter();
			mm.setFileFilter(filter);
			int returnVal = mm.showSaveDialog(MpiroMenu.this);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				String fileName = mm.getSelectedFile().toString();
				fileName = MpiroFileFilter.checkExtension(fileName, ".mpiro");
				try
				{
			    FileOutputStream output = new FileOutputStream(fileName);
			    ObjectOutputStream p = new ObjectOutputStream(output);
			    Mpiro.win.struc.writeStructureObjectToFile(p);
			    p.flush();
			    p.close();
				}
				catch (java.io.IOException IOE)
				{
		    System.out.println("|||| Exception ||||");
		    IOE.printStackTrace();
				}
				// put fileName on frame's titleBar
				Mpiro.loadedDomain = mm.getSelectedFile().getName();
				
				mm.removeChoosableFileFilter(filter);
    	}
		}
	}
}