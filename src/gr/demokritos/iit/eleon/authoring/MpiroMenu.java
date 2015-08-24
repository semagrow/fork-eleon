/***************

<p>Title: </p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

***************/


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


/*
This file is part of the ELEON Ontology Authoring and Enrichment Tool.

ELEON is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, see <http://www.gnu.org/licenses/>.
*/
