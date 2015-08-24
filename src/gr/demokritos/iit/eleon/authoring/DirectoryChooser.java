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

import gr.demokritos.iit.eleon.ui.KLabel;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;


/**
 * <p>Title: DataBasePanel</p>
 * <p>Description: The dialog to choose the directory to export to ILEX or EXPRIMO</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
//Edited and enhanced by Maria Prospathopoulou
public class DirectoryChooser extends JFrame implements ActionListener
{
	//JLabel theImage;
	Icon jpgIcon;
	Icon gifIcon;
	JButton okButton;
	JButton cancelButton;
	JDialog dialog;
	JFileChooser fc = new JFileChooser();
	String mode;
	String domainName = "mpiro_domain"; // A.I.

	/**
	* Constructor. Pressing "OK" calls the methods to export depending on selected mode
	* @param currentDir The current directory path to show on open
	* @param ilexOrExprimo chosen "ILEX" or "EXPRIMO" mode
	*/
	public DirectoryChooser(String currentDir, String ilexOrExprimo)
	{
		// begin A.I.
		//System.out.println("currentDir-----"+currentDir);
		String domainString = new File(currentDir).getName();
		//System.out.println("domainString---"+ domainString);
		if (domainString.endsWith(".mpiro"))
		{
			domainName = domainString.substring(0, domainString.length() - 6);
		}
                //kallonis
                if (domainString.endsWith(".rdf"))
                {
                  domainName = domainString.substring(0, domainString.length() - 4);
                }

		//System.out.println("domainName---"+domainName);
		// end A.I.
		mode = ilexOrExprimo;
		// remove the approve/cancel buttons
		fc.setControlButtonsAreShown(false);

		if (mode.equalsIgnoreCase("EXPRIMO"))
		{
			fc.setCurrentDirectory(Mpiro.jardir);		//maria
		}
		else
		{
			fc.setCurrentDirectory(new File(currentDir));
		}
		//File t=new File(System.getProperty("user.dir"));
		//System.out.println("fc.setCurrentDirectory---"+fc.getCurrentDirectory());
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// make custom controls
		JPanel custom = new JPanel();

		//custom.setLayout(new BoxLayout(custom, BoxLayout.Y_AXIS));
		custom.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

    c.anchor = GridBagConstraints.WEST;
    c.fill = GridBagConstraints.NONE;
    c.insets = new Insets(10, 10, 0, 0);
    c.gridy = 0;
    KLabel description = new KLabel(LangResources.getString(Mpiro.selectedLocale, "selectDirectoryToExportTheCurrentDomain_text"));
    custom.add(description, c);
    c.insets = new Insets(0, 0, 0, 0);
    c.gridy = 1;
    custom.add(fc, c);

    okButton = new JButton(LangResources.getString(Mpiro.selectedLocale, "exportDomain_text"));
    okButton.setSelected(true);
    cancelButton = new JButton(LangResources.getString(Mpiro.selectedLocale, "cancelExport_text"));
    JPanel buttons = new JPanel(new BorderLayout());
    buttons.add(BorderLayout.WEST, okButton);
    buttons.add(BorderLayout.EAST, cancelButton);

    c.anchor = GridBagConstraints.EAST;
    c.insets = new Insets(0, 0, 10, 10);
    c.gridy = 2;
    custom.add(buttons, c);

    okButton.addActionListener(this);
    cancelButton.addActionListener(this);

    // show the filechooser
    dialog = new JDialog(this, LangResources.getString(Mpiro.selectedLocale, "selectExportDirectory_text"), true);
    super.setIconImage(Mpiro.obj.image_corner);
    dialog.getContentPane().add(custom);
    dialog.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension size = dialog.getSize();
    dialog.setLocation( (screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2 );
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    dialog.show();
	}



	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == cancelButton)
		{
			dialog.dispose();
		}
		if (e.getSource() == okButton)
		{
/* 		if (mode.equalsIgnoreCase("ILEX"))
      {
        dialog.dispose();

        String dirPath = fc.getCurrentDirectory().getAbsolutePath();
        ExportUtilsIlex.createExportDirectories(dirPath);
        ExportProgressMonitor progress = new ExportProgressMonitor(0, 11, 0);
        progress.show();
        progress.updateOKButton(false);
        progress.updateProgressBar(1, 1);		//maria

        //progress.updateProgressLabel("Exporting English.lexicon", 1);	//maria
        ExportUtilsIlex.createEnglishLexicon();
        progress.updateProgressBar(2,1);		//maria

        //progress.updateProgressLabel("Exporting Italian.lexicon", 1);	//maria
        ExportUtilsIlex.createItalianLexicon();
        progress.updateProgressBar(3, 1);		//maria

        //progress.updateProgressLabel("Exporting Greek.lexicon", 1);	//maria
        ExportUtilsIlex.createGreekLexicon();
        progress.updateProgressBar(4, 1);		//maria

        //progress.updateProgressLabel("Exporting types.gram", 1);		//maria
        ExportUtilsIlex.createTypesGram();
        progress.updateProgressBar(5, 1);		//maria

        //progress.updateProgressLabel("Exporting predicates.gram", 1);	//maria
        ExportUtilsIlex.createPredicatesGram();
        progress.updateProgressBar(6, 1);		//maria

        //progress.updateProgressLabel("Exporting instances.gram", 1);	//maria
        ExportUtilsIlex.createInstancesGramAndMsgcat();
        progress.updateProgressBar(7, 1);		//maria

        //progress.updateProgressLabel("Exporting stories.gram", 1);	//maria
        ExportUtilsIlex.createStoriesGram();
        progress.updateProgressBar(8, 1);		//maria

        //progress.updateProgressLabel("Exporting expressions.gram", 1);//maria
        ExportUtilsIlex.createExpressionsGram();
        progress.updateProgressBar(9, 1);		//maria

        //progress.updateProgressLabel("Exporting loadDomain.txt", 1);	//maria
        ExportUtilsIlex.createLoadDomainTxt();
        progress.updateProgressBar(10, 1);	//maria

        //progress.updateProgressLabel("Exporting msgcat.english, msgcat.italian, msgcat.greek", 1);	//maria
        ExportUtilsIlex.createDomainSpecificAlterations();
        progress.updateProgressBar(11, 1);	//maria

        //progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
        progress.updateOKButton(true);
    	}
      else*/
		/*	if (mode.equalsIgnoreCase("EXPRIMO"))
			{
				dialog.dispose();
				ArrayList languageList = (ArrayList)Mpiro.selectedLanguagesToExportArrayList.clone();

				String dirPath = fc.getCurrentDirectory().getAbsolutePath();
				ExportUtilsWebinfo.createExportDirectories(dirPath);
				ExportUtilsExprimo exprimoExport = new ExportUtilsExprimo(domainName, languageList);

				if(ExportDialog.doubleExport)									//maria
				{
					ExportUtilsPEmulator.progress.updateProgressBar(1, 1);		//maria
					int n = 2;
	  			Iterator langIter = languageList.iterator();
	  			while (langIter.hasNext())
	  			{
	    			String language = langIter.next().toString();
	    			//ExportUtilsPEmulator.progress.updateProgressLabel("Exporting " + language + "  lexicon", 1);//maria
	     			exprimoExport.createLexicon(language);
	     			ExportUtilsPEmulator.progress.updateProgressBar(n, 1);	//maria
	     			n++;
	  			}
					//ExportUtilsPEmulator.progress.updateProgressLabel("Exporting types.xml", 1);	//maria
					exprimoExport.createTypesGram(languageList);
					ExportUtilsPEmulator.progress.updateProgressBar(5, 1);		//maria

					//ExportUtilsPEmulator.progress.updateProgressLabel("Exporting predicates.xml", 1);//maria
					exprimoExport.createPredicatesGram(languageList);
					ExportUtilsPEmulator.progress.updateProgressBar(6, 1);		//maria

					//ExportUtilsPEmulator.progress.updateProgressLabel("Exporting instances.xml", 1);//maria
					exprimoExport.createInstancesGramAndMsgcat(languageList);
					ExportUtilsPEmulator.progress.updateProgressBar(7, 1);		//maria

					exprimoExport.saveToZip(languageList);

					//ExportUtilsPEmulator.progress.updateProgressLabel("Exporting imagesinfo.xml", 1);//maria
					ExportUtilsWebinfo.createImagesInfo();
					ExportUtilsPEmulator.progress.updateProgressBar(8, 1);		//maria

					//ExportUtilsPEmulator.progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1);	//maria
					ExportUtilsWebinfo.createLanguageInfo();
					ExportUtilsPEmulator.progress.updateProgressBar(9, 1);		//maria

					// Here we check whether to restart exprimo
					//ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...", 1);	//maria
					if (Mpiro.restart)
					{
						if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase(""))
						{
					  	//ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...not available", 1);	//maria
						}
						else
						{
				  		if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English"))
				  		{
				    		//ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
				    		Mpiro.fireActionEnglishItem();
				  		}
				  		else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian"))
				  		{
				    		//ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
				    		Mpiro.fireActionItalianItem();
				  		}
				  		else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek"))
				  		{
				    		//ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Greek", 1);		//maria
				    		Mpiro.fireActionGreekItem();
				  		}
						}
					}
        	else
        	{
          	//ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...not selected by user", 1);//maria
        	}
        	ExportUtilsPEmulator.progress.updateProgressBar(10, 1);				//maria

        	//ExportUtilsPEmulator.progress.updateProgressLabel("Export finished succesfully.", 1);				//maria
        	ExportUtilsPEmulator.progress.updateOKButton(true);

        	ExportDialog.doubleExport = false;									//maria
				}
				else
				{
					ExportProgressMonitor progress = new ExportProgressMonitor(0, 10, 0);
					progress.exprimoOrEmulatorsLabel.setText(LangResources.getString(Mpiro.selectedLocale,"exportToExprimo_text" ));	//maria
					progress.show();
					progress.updateOKButton(false);
					progress.updateProgressBar(1, 1);									//maria

					int n = 2;
					Iterator langIter = languageList.iterator();
					while (langIter.hasNext())
					{
						String language = langIter.next().toString();
						//progress.updateProgressLabel("Exporting " + language + "  lexicon", 1);	//maria
						exprimoExport.createLexicon(language);
						progress.updateProgressBar(n, 1);								//maria
						n++;
					}

					//progress.updateProgressLabel("Exporting types.xml", 1);				//maria
					exprimoExport.createTypesGram(languageList);
					progress.updateProgressBar(5, 1);									//maria

					//progress.updateProgressLabel("Exporting predicates.xml", 1);		//maria
					exprimoExport.createPredicatesGram(languageList);
					progress.updateProgressBar(6, 1);									//maria

					//progress.updateProgressLabel("Exporting instances.xml", 1);			//maria
					exprimoExport.createInstancesGramAndMsgcat(languageList);
					progress.updateProgressBar(7, 1);									//maria

					exprimoExport.saveToZip(languageList);

					//progress.updateProgressLabel("Exporting imagesinfo.xml", 1);		//maria
					ExportUtilsWebinfo.createImagesInfo();
					progress.updateProgressBar(8, 1);									//maria

					//progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1);	//maria
					ExportUtilsWebinfo.createLanguageInfo();
					progress.updateProgressBar(9, 1);									//maria

					// Here we check whether to restart exprimo
					//progress.updateProgressLabel("Restarting exprimo...", 1);			//maria
	  			if (Mpiro.restart)
					{
	        	if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase(""))
	        	{
	          	//progress.updateProgressLabel("Restarting exprimo...not available", 1);	//maria
	        	}
	        	else
	        	{
	        		if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English"))
	        		{
	          		//progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
	          		Mpiro.fireActionEnglishItem();
	        		}
	        		else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian"))
	        		{
	          		//progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
	          		Mpiro.fireActionItalianItem();
	        		}
	        		else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek"))
	        		{
	          		//progress.updateProgressLabel("Restarting exprimo...Greek", 1);		//maria
	          		Mpiro.fireActionGreekItem();
	        		}
	        	}
					}
        	else
        	{
          	//progress.updateProgressLabel("Restarting exprimo...not selected by user", 1);	//maria
        	}
        	progress.updateProgressBar(10, 1);									//maria

        	//progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
        	progress.updateOKButton(true);
				}
				Mpiro.needExportToExprimo = false;			//maria
			}*/
		}
	} //actionPerformed
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
