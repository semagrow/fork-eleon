/***************

<p>Title: ExportDialog</p>

<p>Description: The dialog that asks for the export destination
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2011 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Dimitris Spiliotopoulos (2002)
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)

***************/



package gr.demokritos.iit.eleon.authoring;

import gr.aueb.cs.nlg.Languages.Languages;
import gr.aueb.cs.nlg.NLGEngine.NLGEngine;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KLabel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class ExportDialog extends JFrame implements ActionListener
{
	static JDialog dialog;
	static KLabel selectionLabel;
	static JPanel selectionPanels;
	//static JPanel selectionPanelIlex;
	//static JPanel selectionPanelExprimoGroup;
	static JPanel selectionPanelExprimo, selectionPanelExprimoEnglishItalianGreek, selectionPanelExprimoRestart;
	static JPanel selectionPanelOthers;
	private OkCancel okCancel;
	
	//private JRadioButton exprimo;
	//private JRadioButton ilex;
	//private JRadioButton resrartEngineForGreek;
	//private JRadioButton resrartEngineForEnglish;
	private JTextField resrartEngineForEnglishIP;
	private JCheckBox exportOntology, exportLexicon, exportMicros, exportUsers, resrartEngineForGreek;
	private KButton okButton;
	private KButton cancelButton;
	static String domainName;

	static boolean doubleExport = false;		//maria
	
	/**
	 * Contructor.
	 * @param domainNameFromMpiro The domain name
	 */
	public ExportDialog(String domainNameFromMpiro)
	{
		domainName = domainNameFromMpiro;
		
		// The dialog and its components
		dialog = new JDialog(Mpiro.win.getFrames()[0], LangResources.getString(Mpiro.selectedLocale, "mpiroExportSelection_text"), true);
		super.setIconImage(Mpiro.obj.image_corner);

    selectionLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "exportOf_text"));
    selectionLabel.setPreferredSize(new Dimension(200, 20));

    selectionPanels = new JPanel(new BorderLayout());

    //selectionPanelExprimoGroup = new JPanel(new BorderLayout());
    //selectionPanelExprimo = new JPanel(new BorderLayout());
    selectionPanelExprimoEnglishItalianGreek = new JPanel(new BorderLayout());
    selectionPanelExprimoRestart = new JPanel(new BorderLayout());
    //selectionPanelIlex = new JPanel(new BorderLayout());
    selectionPanelOthers = new JPanel(new BorderLayout());
    selectionPanelOthers = new JPanel(new BorderLayout());

    //selectionPanelExprimoEnglishItalianGreek.setBorder(new EmptyBorder(new Insets(0,10,0,10)));
    //selectionPanelExprimoRestart.setBorder(new EmptyBorder(new Insets(0,10,0,10)));

    //exprimo = new JRadioButton(" EXPRIMO", true);
    //ilex = new JRadioButton(" ILEX", false);
    resrartEngineForGreek = new JCheckBox("Restart Engine", true);
    //resrartEngineForEnglish = new JCheckBox("Restart Engine for English", true);
    resrartEngineForEnglishIP = new JTextField(" ");

    /*ButtonGroup selectionButtons = new ButtonGroup();
    selectionButtons.add(exprimo);
    selectionButtons.add(ilex);
    selectionButtons.add(resrartEngineForGreek);
    selectionButtons.add(resrartEngineForEnglish);
		*/
    exportOntology = new JCheckBox("Export Ontological Model", true);
    exportLexicon = new JCheckBox("Export Lexicon", true);
    exportMicros = new JCheckBox("Export Microplans", true);
    exportUsers = new JCheckBox("Export User Modelling", true);

    okCancel = new OkCancel();

    //selectionPanelIlex.add(BorderLayout.NORTH, ilex);

    //selectionPanelExprimo.add(BorderLayout.NORTH, exprimo);
    selectionPanelExprimoEnglishItalianGreek.add(BorderLayout.NORTH, exportOntology);
    selectionPanelExprimoEnglishItalianGreek.add(BorderLayout.CENTER, exportLexicon);
    selectionPanelExprimoEnglishItalianGreek.add(BorderLayout.SOUTH, exportMicros);
    selectionPanelExprimoRestart.add(BorderLayout.NORTH, exportUsers);

    //selectionPanelExprimoGroup.add(BorderLayout.NORTH, selectionPanelExprimo);
    //selectionPanelExprimoGroup.add(BorderLayout.CENTER, selectionPanelExprimoEnglishItalianGreek);
    //selectionPanelExprimoGroup.add(BorderLayout.SOUTH, selectionPanelExprimoRestart);
    selectionPanelOthers.add(BorderLayout.CENTER, resrartEngineForGreek);
   // selectionPanelOthers.add(BorderLayout.SOUTH, resrartEngineForEnglish);

    selectionPanels.add(BorderLayout.NORTH, selectionPanelExprimoEnglishItalianGreek);
    selectionPanels.add(BorderLayout.CENTER, selectionPanelOthers);
    selectionPanels.add(BorderLayout.SOUTH, selectionPanelExprimoRestart);

    // Place them in the contentPane of the dialog
    Container contentPane = dialog.getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add("North", selectionLabel);
    contentPane.add("Center", selectionPanels);
    contentPane.add("South", okCancel);

    // Add the actionListener
    //ilex.addActionListener(ExportDialog.this);
    //exprimo.addActionListener(ExportDialog.this);
    exportOntology.addActionListener(ExportDialog.this);
    exportLexicon.addActionListener(ExportDialog.this);
    exportMicros.addActionListener(ExportDialog.this);
    exportUsers.addActionListener(ExportDialog.this);
    resrartEngineForGreek.addActionListener(ExportDialog.this);
   /// resrartEngineForEnglish.addActionListener(ExportDialog.this);
    okButton.addActionListener(ExportDialog.this);
    cancelButton.addActionListener(ExportDialog.this);

    // Make the dialog visible
    dialog.pack();
    dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension size = dialog.getSize();
    dialog.setLocation( (screenSize.width - size.width) / 2,
                        (screenSize.height - size.height) / 2 );
    dialog.setVisible(true);
	} // constructor


	class OkCancel extends JPanel
	{
	  public OkCancel() 
	  {
	    setLayout(new GridLayout(1,2));
	    setPreferredSize(new Dimension(200,30));
	    okButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
	    cancelButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
	    add(okButton);
	    add(cancelButton);
	  }
	}


	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == okButton)
		{
                 /*   Enumeration rrr=Mpiro.win.struc.getProperties();
                    while(rrr.hasMoreElements()){
                        Vector temp=(Vector) rrr.nextElement();
                        Hashtable temp1=(Hashtable) temp.elementAt(12);
                        boolean con=temp1.contains("NewUserType");
                        if(temp1.containsKey("NewUserType"))
                        temp1.remove("NewUserType");
                      //  temp.remove();
                    }*/
                    
                    
                    ExportProgressMonitor progress = new ExportProgressMonitor(0, 10, 0); //maria
									progress.exprimoOrEmulatorsLabel.setText("export to NLG"); //maria
									progress.show(); //maria
                                                                        progress.setAlwaysOnTop(true);
									progress.updateOKButton(false); //maria
									progress.updateProgressBar(1, 1);  
                    File tempOwlFile= new File("OwlTemp.owl");
                 try{
                    if (exportOntology.isSelected()){
                        OwlExport.ExportToOwlFile(tempOwlFile,"RDF/XML-ABBREV", Mpiro.win.struc.getBaseURI(),tempOwlFile.getName(),false);
                    }
                progress.updateProgressBar(3, 1);
                 if (exportLexicon.isSelected()){
                     OwlExport.exportLexicon(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                 }
                progress.updateProgressBar(5, 1);
                    if (exportMicros.isSelected()){
                        OwlExport.exportMicroplans(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                    }
                progress.updateProgressBar(7, 1);
                    if (exportUsers.isSelected()){
                        OwlExport.exportUserModelling(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                        OwlExport.exportRobotModelling(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                    }
                     }
                     catch(java.lang.Exception ex){
                     ex.printStackTrace(System.out);}
                 String owlPath = tempOwlFile.getAbsolutePath();
                                        progress.updateProgressBar(8, 1);
                              
        String NLResourcePath = tempOwlFile.getAbsolutePath().substring(0,tempOwlFile.getAbsolutePath().length()-11);
                 if (resrartEngineForGreek.isSelected()){
           
            
            
                     DataBasePanel.myEngine=  new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, !DataBasePanel.pServerIsStarted, false, null, null, null, null, DataBasePanel.navIP, DataBasePanel.navPort, DataBasePanel.dbUser, DataBasePanel.dbPass,"127.0.0.1",1111);

                             //new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, !DataBasePanel.pServerIsStarted, false, 0, null, null, null, null, DataBasePanel.navIP, DataBasePanel.navPort, DataBasePanel.dbUser, DataBasePanel.dbPass,"127.0.0.1",1111);
                     //DataBasePanel.myEngine=  new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, !DataBasePanel.pServerIsStarted, false, null, null, null, null, DataBasePanel.navIP, DataBasePanel.navPort, DataBasePanel.dbUser, DataBasePanel.dbPass,"127.0.0.1",1111);
                    // DataBasePanel.myEngineGreek =  new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, false, false, null, null, null, null, "", 0, "", "");
                            DataBasePanel.myEngine.initStatisticalTree();
                            //myEngineGreek.initStatisticalTree();
        DataBasePanel.myEngine.initPServer();
                 }
       // progress.updateProgressBar(8, 1);
              //    if (resrartEngineForEnglish.isSelected()){
                //     DataBasePanel.myEngineEnglish= new NLGEngine(owlPath, NLResourcePath,  Languages.ENGLISH, !DataBasePanel.pServerIsStarted, false, null, null, null, null, DataBasePanel.navIP, DataBasePanel.navPort, DataBasePanel.dbUser, DataBasePanel.dbPass,"127.0.0.1", 1111);
                  //   DataBasePanel.myEngineEnglish =  new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, false, false, null, null, null, null, "", 0, "", "");
                   //         DataBasePanel.myEngineEnglish.initStatisticalTree();
                   //          DataBasePanel.myEngineEnglish.initPServer();
                // }
        progress.updateProgressBar(10, 1);
        Mpiro.needExportToExprimo=false;
        progress.dispose();
        dialog.dispose();
                 
                 
                 
                 
                 
                 
                 
                 
                 
			/* if (ilex.isSelected()) {
			    //System.out.println("()---- " + "ILEX");
			    dialog.dispose();
			    new DirectoryChooser(domainName, "ILEX");
			}
			
			else if (exprimo.isSelected()) {
			    //System.out.println("()---- " + "EXPRIMO");*/
			    //Mpiro.selectedLanguagesToExportVector.clear();
/*		  if (resrartEngineForGreek.isSelected()) 
		  {
	     	//System.out.println("()---- " + "Personalisation emulator");
	     	if((exportOntology.isSelected())||(exportLexicon.isSelected())||(exportMicros.isSelected()))		//maria
					{doubleExport = true;}																									//maria
        dialog.dispose();
        // the following 2 lines initialise an emulator
        // in case the exprimo machine isn't called yet
        Mpiro.pageFactory.setUseEmulator(true);
        Mpiro.pageFactory.initializeEmulator();
        try
        {
        	ExportUtilsPEmulator.exportToEmulator();
        }
        catch (UMException ume)
        {
        	System.out.println("(ExportDialog:ExportUtilsPEmulator.exportToEmulator)---- " + ume);
        	ume.printStackTrace();
        }
				Mpiro.needExportToEmulator = false;				//maria
			}
			if ((exportOntology.isSelected())||(exportLexicon.isSelected())||(exportMicros.isSelected()))
			{
				Mpiro.selectedLanguagesToExportArrayList.clear();
				if (exportOntology.isSelected())
					{Mpiro.selectedLanguagesToExportArrayList.add("English");}
				
				if (exportLexicon.isSelected())
					{Mpiro.selectedLanguagesToExportArrayList.add("Italian");}
				
				if (exportMicros.isSelected())
					{Mpiro.selectedLanguagesToExportArrayList.add("Greek");}
				
				if (exportUsers.isSelected())
					{Mpiro.restart = true;}
				else
					{Mpiro.restart = false;}
				dialog.dispose();
				new DirectoryChooser(domainName, "EXPRIMO");
				//Mpiro.needExportToExprimo = false;			//maria
			}

			/*	else if (resrartEngineForGreek.isSelected()) {
			    //System.out.println("()---- " + "Personalisation emulator");
			    dialog.dispose();
			    // the following 2 lines initialise an emulator
			    // in case the exprimo machine isn't called yet
			    Mpiro.pageFactory.setUseEmulator(true);
			    Mpiro.pageFactory.initializeEmulator();
			    try
			    {
			      ExportUtilsPEmulator.exportToEmulator();
			    }
			    catch (UMException ume)
			    {
			        System.out.println("(ExportDialog:ExportUtilsPEmulator.exportToEmulator)---- " + ume);
			        ume.printStackTrace();
			    	}
					}
			*/
			/*   else*/ 
		/*	if (resrartEngineForEnglish.isSelected()) 
			{
	      //System.out.println("()---- " + "Personalisation server");
	      dialog.dispose();
	      new IPChooser(domainName);
			}
			if(!((exportOntology.isSelected())||(exportLexicon.isSelected())||(exportMicros.isSelected())||	//maria
				(exportUsers.isSelected())||(resrartEngineForGreek.isSelected())||(resrartEngineForEnglish.isSelected())))				//maria
			{																							//maria
				JOptionPane.showMessageDialog(dialog, 													//maria
							LangResources.getString(Mpiro.selectedLocale, "nothingToExport_dialog"), 		//maria
							LangResources.getString(Mpiro.selectedLocale,"export_text"),				//maria
							JOptionPane.INFORMATION_MESSAGE);											//maria
			}		*/																					//maria
		}

    else if (e.getSource() == cancelButton)
    {
      dialog.dispose();
    }

						/*maria else if (e.getSource() == exportOntology)
              {
                if (!exportLexicon.isSelected() &&
                    !exportMicros.isSelected())
                {
                  exportOntology.setSelected(true);
                }
              }
              else if (e.getSource() == exportLexicon)
              {
                if (!exportOntology.isSelected() &&
                    !exportMicros.isSelected())
                {
                  exportLexicon.setSelected(true);
                }
              }
              else if (e.getSource() == exportMicros)
              {
                if (!exportOntology.isSelected() &&
                    !exportLexicon.isSelected())
                {
                  exportMicros.setSelected(true);
                }
              }
              else if (e.getSource() == ilex)
              {
                exportOntology.setEnabled(false);
                exportLexicon.setEnabled(false);
                exportMicros.setEnabled(false);
                exportUsers.setEnabled(false);

              }
              else if (e.getSource() == exprimo)
              {
                exportOntology.setEnabled(true);
                exportLexicon.setEnabled(true);
                exportMicros.setEnabled(true);
                exportUsers.setEnabled(true);
              }
              else if (e.getSource() == resrartEngineForGreek)
              {
                exportOntology.setEnabled(false);
                exportLexicon.setEnabled(false);
                exportMicros.setEnabled(false);
                exportUsers.setEnabled(false);
              }
              else if (e.getSource() == resrartEngineForEnglish)
              {
                exportOntology.setEnabled(false);
                exportLexicon.setEnabled(false);
                exportMicros.setEnabled(false);
                exportUsers.setEnabled(false);
              }maria*/

	} // actionPerformed
        
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
