/***************

<p>Title: MenuBar</p>

<p>Description:
Entry point for the ELEON application.
</p>

<p>Copyright (c) 2001-2009 National Centre for Scientific Research "Demokritos"</p>

@author Dimitris Spiliotopoulos (MPIRO, 2001-2004)
@author Maria Prospathopoulou
@author Spyros Kallonis
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009)
@author Stasinos Konstantopoulos (INDIGO, 2009)

***************/


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


package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.OptionsEditDialog;
import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.struct.QueryOptionsHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;
import gr.demokritos.iit.eleon.authoring.TreePreviews;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


public class ELEONWindowMenuBar extends JMenuBar
implements ActionListener
{
    private JMenu fileMenu;
    
    JMenuItem newFileItem;
    JMenuItem openFileItem;
    JMenuItem saveFileItem;
    JMenuItem exportFileItem;
    JMenuItem exitFileItem;

    JMenuItem pserverAddressItem;
    JMenu activatePreviewLanguageMenu;
    private JMenuItem englishItem;
    private JMenuItem italianItem;
    private JMenuItem greekItem;
    private static JMenuItem resetInteractionHistoryItem;

    //private JMenuItem resetInteractionHistoryEmulatorItem;
    public static JCheckBoxMenuItem resetInteractionHistoryBeforeEachPreviewItem;
    public static JCheckBoxMenuItem showAllPagesInPreviewsItem;
    private JMenu automaticExportMenu;

    public static JRadioButtonMenuItem exportToDefaultItem;
    public static JRadioButtonMenuItem exportToChoiceItem;
    public static JRadioButtonMenuItem notAutomaticExportItem;

    private JMenu helpMenu;
    JMenuItem aboutHelpItem;

    private JMenu optionsMenu;
    private JMenu lafMenu;

    private JMenu debugMenu;
    
    private JMenuItem queryMainDBHashtableItem;
    private JMenuItem queryMainLexiconHashtableItem;
    private JMenuItem queryMainUsersHashtableItem;
    private JMenuItem queryMainUserModelHashtableItem;
    private JMenuItem queryMainUserModelStoryHashtableItem;
    private JMenuItem queryOptionsHashtableItem;
    private JMenuItem updateOptionsHashtableItem;
    private JMenuItem testHashtableItem;

    JMenu xmlMenu;
    private JMenuItem createXmlOutputItem;

    private JMenu owlMenu;
    private JMenuItem exportToOWL;
    private JMenuItem importFromOWL;
    JMenuItem runReasoner;
    JMenuItem startPServer;
    
    JRadioButtonMenuItem javaLafItem;
    JRadioButtonMenuItem motifLafItem;
    JRadioButtonMenuItem windowsLafItem;
    //JRadioButtonMenuItem metalLafItem;
    ButtonGroup lafMenuGroup;
    
    public ELEONWindowMenuBar( ActionListener l )
    {
        fileMenu = new JMenu(LangResources.getString(Mpiro.selectedLocale, "file_menu"));
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.getAccessibleContext().setAccessibleDescription("The file items");
        add( fileMenu );

        optionsMenu = new JMenu(LangResources.getString(Mpiro.selectedLocale, "options_menu"));
        optionsMenu.setMnemonic(KeyEvent.VK_O);
        optionsMenu.getAccessibleContext().setAccessibleDescription("The options items");
        add( optionsMenu );

        lafMenu = new JMenu(LangResources.getString(Mpiro.selectedLocale, "lookandfeel_menu"));
        lafMenu.setMnemonic(KeyEvent.VK_L);
        lafMenu.getAccessibleContext().setAccessibleDescription("The look and feel items");
        //add( lafMenu );

        helpMenu = new JMenu(LangResources.getString(Mpiro.selectedLocale, "help_menu"));
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.getAccessibleContext().setAccessibleDescription("The help items");
        add( helpMenu );
        
        debugMenu = new JMenu( "DEBUG" );
        xmlMenu = new JMenu( "XML" );
        if( Mpiro.debugMode ) {
            add( debugMenu );
            add( xmlMenu );
        }

        Icon icon;
        String str;
        
        icon = new ImageIcon( Mpiro.obj.image_new );
        str = LangResources.getString( Mpiro.selectedLocale, "new_menu" );
        newFileItem = new JMenuItem( str, icon );
        newFileItem.setMnemonic( KeyEvent.VK_N );
        newFileItem.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK) );
        fileMenu.add( newFileItem );
        
        icon = new ImageIcon( Mpiro.obj.image_open );
        str = LangResources.getString( Mpiro.selectedLocale, "open_menu" );
        openFileItem = new JMenuItem( str, icon );
        openFileItem.setMnemonic( KeyEvent.VK_O );
        openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        //openFileItem.getAccessibleContext().setAccessibleDescription("Opens a new file");
        fileMenu.add(openFileItem);

        icon = new ImageIcon( Mpiro.obj.image_save );
        str = LangResources.getString( Mpiro.selectedLocale, "saveas_menu");
        saveFileItem = new JMenuItem( str, icon );
        saveFileItem.setMnemonic(KeyEvent.VK_S);
        saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveFileItem);
        runReasoner=new JMenuItem("Run Reasoner");
        startPServer= new JMenuItem("Start PServer");
        optionsMenu.add(runReasoner);
        optionsMenu.add(startPServer);

        icon = new ImageIcon( Mpiro.obj.image_owl );
        owlMenu = new JMenu("OWL");
        owlMenu.setMnemonic(KeyEvent.VK_W);
        owlMenu.setIcon( icon );
//      owlMenu.setDisabledIcon( icon );
        owlMenu.setVisible( false ); // not used
        fileMenu.add(owlMenu);

        icon = new ImageIcon( Mpiro.obj.image_owlexport );
        exportToOWL = new JMenuItem( LangResources.getString(Mpiro.selectedLocale, "owlexport_menu"), icon );
        exportToOWL.setMnemonic(KeyEvent.VK_W);
        exportToOWL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        owlMenu.add(exportToOWL);

        importFromOWL = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "owlimport_menu"), icon );
        icon = new ImageIcon( Mpiro.obj.image_owlimport );
        importFromOWL.setMnemonic(KeyEvent.VK_L);
        importFromOWL.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        owlMenu.add(importFromOWL);

        fileMenu.addSeparator();

        icon = new ImageIcon( Mpiro.obj.image_exportText );
        exportFileItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "export_menu"), icon );
        exportFileItem.setMnemonic(KeyEvent.VK_E);
        exportFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        fileMenu.add(exportFileItem);

        fileMenu.addSeparator();

        icon = new ImageIcon( Mpiro.obj.image_exit );
        exitFileItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "exit_menu"), icon );
        exitFileItem.setMnemonic( KeyEvent.VK_X );
        exitFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        fileMenu.add( exitFileItem );

        newFileItem.addActionListener( l );
        openFileItem.addActionListener( l );
        saveFileItem.addActionListener( l );
        runReasoner.addActionListener( l );
        startPServer.addActionListener( l );
        exportToOWL.addActionListener( l );
        importFromOWL.addActionListener( l );
        exportFileItem.addActionListener( l );
        exitFileItem.addActionListener( l );

        // the optionsMenu
        pserverAddressItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "editPServerAddress_menu"));
        //optionsMenu.add(pserverAddressItem);
        if( ELEONWindow.loadedDomain.equalsIgnoreCase("") ) {
            pserverAddressItem.setEnabled( false );
        }
        else {
            pserverAddressItem.setEnabled( true );
        }
        pserverAddressItem.addActionListener( l );

        //optionsMenu.addSeparator();

        activatePreviewLanguageMenu = new JMenu(LangResources.getString(Mpiro.selectedLocale, "activatePreviewLanguage_menu"));
        //optionsMenu.add(activatePreviewLanguageMenu);
        if(  ELEONWindow.loadedDomain.equalsIgnoreCase("") ) {
            activatePreviewLanguageMenu.setEnabled(false);
            xmlMenu.setEnabled(false);
        }
        else {
            activatePreviewLanguageMenu.setEnabled(true);
            xmlMenu.setEnabled(true);
        }

        englishItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "english_menu"));
        activatePreviewLanguageMenu.add(englishItem);
        englishItem.addActionListener( l );

        italianItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "italian_menu"));
        activatePreviewLanguageMenu.add(italianItem);
        italianItem.addActionListener( l );

        greekItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "greek_menu"));
        activatePreviewLanguageMenu.add( greekItem );
        greekItem.addActionListener( l );
        /*
         resetInteractionHistoryItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "resetInteractionHistory_menu"));
         optionsMenu.add(resetInteractionHistoryItem);
         if (loadedDomain.equalsIgnoreCase(""))
         {
          resetInteractionHistoryItem.setEnabled(false);
         }
         else
         {
          resetInteractionHistoryItem.setEnabled(true);
         }
         resetInteractionHistoryItem.addActionListener(Mpiro.win);


         resetInteractionHistoryEmulatorItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "resetInteractionHistoryEmulator_menu"));
         optionsMenu.add(resetInteractionHistoryEmulatorItem);
         resetInteractionHistoryEmulatorItem.setEnabled(false);
         resetInteractionHistoryEmulatorItem.addActionListener(Mpiro.win);
         */

        resetInteractionHistoryItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "resetInteractionHistory_menu"));
        optionsMenu.add(resetInteractionHistoryItem);
        //resetInteractionHistoryItem.setEnabled(false);
        resetInteractionHistoryItem.addActionListener( l );

        optionsMenu.addSeparator();

        resetInteractionHistoryBeforeEachPreviewItem = new JCheckBoxMenuItem(LangResources.getString(Mpiro.selectedLocale,
            "resetInteractionHistoryBeforeEachPreview_menu"), true);
        showAllPagesInPreviewsItem = new JCheckBoxMenuItem(LangResources.getString(Mpiro.selectedLocale, "showAllPagesInPreviews_menu"), true);
        resetInteractionHistoryBeforeEachPreviewItem.addActionListener( l );
        optionsMenu.add(resetInteractionHistoryBeforeEachPreviewItem);
        //optionsMenu.add(showAllPagesInPreviewsItem);

        optionsMenu.addSeparator();

        automaticExportMenu = new JMenu(LangResources.getString(Mpiro.selectedLocale, "automaticExport_menu"), true); //maria
        optionsMenu.add(automaticExportMenu);
        automaticExportMenu.setEnabled(true);
        exportToDefaultItem = new JRadioButtonMenuItem(LangResources.getString(Mpiro.selectedLocale, "exportToDefault_menu"), true); //maria
        automaticExportMenu.add(exportToDefaultItem);
        exportToChoiceItem = new JRadioButtonMenuItem(LangResources.getString(Mpiro.selectedLocale, "exportToChoice_menu"), false); //maria
        automaticExportMenu.add(exportToChoiceItem);
        notAutomaticExportItem = new JRadioButtonMenuItem(LangResources.getString(Mpiro.selectedLocale, "notAutomaticExport_menu"), false); //maria
        automaticExportMenu.add(notAutomaticExportItem);

        ButtonGroup automaticExportButtons = new ButtonGroup(); //maria
        automaticExportButtons.add(exportToDefaultItem); //maria
        automaticExportButtons.add(exportToChoiceItem); //maria
        automaticExportButtons.add(notAutomaticExportItem); //maria

        // the lafMenu
        lafMenuGroup = new ButtonGroup();

        javaLafItem = new JRadioButtonMenuItem(" Java Look & Feel");
        lafMenuGroup.add(javaLafItem);
        lafMenu.add(javaLafItem);
        motifLafItem = new JRadioButtonMenuItem(" Motif Look & Feel");
        lafMenuGroup.add(motifLafItem);
        lafMenu.add(motifLafItem);
        windowsLafItem = new JRadioButtonMenuItem(" Windows Look & Feel");
        lafMenuGroup.add(windowsLafItem);
        lafMenu.add(windowsLafItem);
        //metalLafItem = new JRadioButtonMenuItem(" Metal Look & Feel");
        //lafMenuGroup.add(metalLafItem);
        //lafMenu.add(metalLafItem);

        javaLafItem.addActionListener( l );
        motifLafItem.addActionListener( l );
        windowsLafItem.addActionListener( l );
        //metalLafItem.addActionListener( l );

        javaLafItem.setSelected( true );

        // the helpMenu
        aboutHelpItem = new JMenuItem(LangResources.getString(Mpiro.selectedLocale, "about_menu"));
        aboutHelpItem.setMnemonic(KeyEvent.VK_A);
        aboutHelpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        helpMenu.add( aboutHelpItem );

        aboutHelpItem.addActionListener( l );

        // the debugMenu
        queryMainDBHashtableItem = new JMenuItem("Query main DB Hashtable");
        queryMainLexiconHashtableItem = new JMenuItem("Query main Lexicon Hashtable");
        queryMainUsersHashtableItem = new JMenuItem("Query main Users Hashtable");
        queryMainUserModelHashtableItem = new JMenuItem("Query main User Model Hashtable");
        queryMainUserModelStoryHashtableItem = new JMenuItem("Query main User Model Story Hashtable");
        queryOptionsHashtableItem = new JMenuItem("Query options Hashtable");
        updateOptionsHashtableItem = new JMenuItem("Update options Hashtable");
        testHashtableItem = new JMenuItem("test Hashtable");
        debugMenu.add(queryMainDBHashtableItem);
        queryMainDBHashtableItem.addActionListener( l );
        debugMenu.add(queryMainLexiconHashtableItem);
        queryMainLexiconHashtableItem.addActionListener( l );
        debugMenu.add(queryMainUsersHashtableItem);
        queryMainUsersHashtableItem.addActionListener( l );
        debugMenu.add(queryMainUserModelHashtableItem);
        queryMainUserModelHashtableItem.addActionListener( l );
        debugMenu.add(queryMainUserModelStoryHashtableItem);
        queryMainUserModelStoryHashtableItem.addActionListener( l );
        debugMenu.add(queryOptionsHashtableItem);
        queryOptionsHashtableItem.addActionListener( l );
        debugMenu.add(updateOptionsHashtableItem);
        updateOptionsHashtableItem.addActionListener( l );
        debugMenu.add(testHashtableItem);
        testHashtableItem.addActionListener( l );

        // the xmlMenu
        createXmlOutputItem = new JMenuItem("Create XML output");
        xmlMenu.add(createXmlOutputItem);
        createXmlOutputItem.addActionListener( l );
    }

    public void actionPerformed( ActionEvent ae )
    {
        if (ae.getSource() == javaLafItem) {
            Mpiro.win.setLook(1);
            Mpiro.win.d.updateUI();
        }

        if (ae.getSource() == motifLafItem) {
        	Mpiro.win.setLook(2);
        	Mpiro.win.d.updateUI();
        }

        if (ae.getSource() == windowsLafItem) {
        	Mpiro.win.setLook(3);
        	Mpiro.win.d.updateUI();
        }

        if (ae.getSource() == pserverAddressItem) {
            new OptionsEditDialog("PSERVER-ADDRESS");
        }

        if (ae.getSource() == englishItem) {
        	gr.demokritos.iit.eleon.ui.ELEONWindow.fireActionEnglishItem();
        }

        if (ae.getSource() == italianItem) {
        	gr.demokritos.iit.eleon.ui.ELEONWindow.fireActionItalianItem();
        }

        if (ae.getSource() == greekItem) {
        	gr.demokritos.iit.eleon.ui.ELEONWindow.fireActionGreekItem();
        }
        
         if (ae.getSource() == resetInteractionHistoryItem)
         {
        	 gr.demokritos.iit.eleon.ui.ELEONWindow.resetInteractionHistory = true;
         }
         

        if (ae.getSource() == resetInteractionHistoryBeforeEachPreviewItem) {
            
            if(resetInteractionHistoryBeforeEachPreviewItem.isSelected())
            	gr.demokritos.iit.eleon.ui.ELEONWindow.resetInteractionHistoryBeforeEachPreview=true;
            else
            	gr.demokritos.iit.eleon.ui.ELEONWindow.resetInteractionHistoryBeforeEachPreview=false;
            
        //    try {
        ///        ExportUtilsPEmulator.resetInteractionHistoryEmulator();
        //    }
       //     catch (UMException ume) {
       //         System.out.println("(Mpiro.actionPerformed) resetInteractionHistory FAILED" + ume);
       //         ume.printStackTrace();
        //    }
        }

        if (ae.getSource() == queryMainDBHashtableItem) {
            System.out.println("(QueryOptionsHashtable.mainDBHashtable)-------------- " + "\n");
            System.out.println("---------------- ENTITY-TYPES -------------- " + "\n");
            Vector allentitytypesVector = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
            Enumeration allentitytypesVectorEnum = allentitytypesVector.elements();
            while (allentitytypesVectorEnum.hasMoreElements()) {
                String key = allentitytypesVectorEnum.nextElement().toString();
                String element = Mpiro.win.struc.getEntityTypeOrEntity(key).toString();
                System.out.println(key + " = " + element + "\n");
            }
            System.out.println("---------------- ENTITIES -------------- " + "\n");
            Vector allentitiesVector = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity+Generic");
            Enumeration allentitiesVectorEnum = allentitiesVector.elements();
            while (allentitiesVectorEnum.hasMoreElements()) {
                String key = allentitiesVectorEnum.nextElement().toString();
                String element = Mpiro.win.struc.getEntityTypeOrEntity(key).toString();
                System.out.println(key + " = " + element + "\n");
            }
            System.out.println("=================================================\n\n");
        }

        if (ae.getSource() == queryMainLexiconHashtableItem) {
            System.out.println("(QueryOptionsHashtable.mainLexiconHashtable)---  " + "\n" +
                               Mpiro.win.struc.getMainLexiconHashtableEntrySet().toString());
        }

        if (ae.getSource() == queryMainUsersHashtableItem) {
       //     System.out.println("(QueryOptionsHashtable.mainUsersHashtable)---  " + "\n" +
         //                      QueryProfileHashtable.mainUsersHashtable.entrySet().toString());
        }

        if (ae.getSource() == queryMainUserModelHashtableItem) {
            System.out.println("(QueryOptionsHashtable.mainUserModelHashtable)-------------- " + "\n");
            System.out.println("---------------- FIELDS -------------- " + "\n");

            Enumeration allfieldsEnum = Mpiro.win.struc.mainUserModelHashtableKeys();
            while (allfieldsEnum.hasMoreElements()) {
                String field = allfieldsEnum.nextElement().toString();
                System.out.println("++++++++++" + field + "+++++++++"); /////////////
                Hashtable entitytypesAndEntitiesHashtable = (Hashtable) Mpiro.win.struc.getPropertyFromMainUserModelHashtable(field);
                Enumeration entitytypesAndEntitiesHashtableEnum = entitytypesAndEntitiesHashtable.keys();
                while (entitytypesAndEntitiesHashtableEnum.hasMoreElements()) {
                    String entitytypeOrEntity = entitytypesAndEntitiesHashtableEnum.nextElement().toString();
                    System.out.println(entitytypeOrEntity); /////////////
                    Hashtable usersHashtable = (Hashtable) entitytypesAndEntitiesHashtable.get(entitytypeOrEntity);
                    Enumeration usersHashtableEnum = usersHashtable.keys();
                    while (usersHashtableEnum.hasMoreElements()) {
                        String usertype = usersHashtableEnum.nextElement().toString();
                        Vector usermodelValuesVector = (Vector) usersHashtable.get(usertype);
                        System.out.println(usertype + " = " + usermodelValuesVector.toString());
                    }
                    System.out.println();
                }
                System.out.println("\n");
            }
            System.out.println("=================================================\n\n");
        }

     //   if (ae.getSource() == queryMainUserModelStoryHashtableItem) {
       //     System.out.println("(QueryOptionsHashtable.mainUserModelStoryHashtable)---  " + "\n" +
         //                      QueryProfileHashtable.mainUserModelStoryHashtable.entrySet().toString());
       // }

        if (ae.getSource() == queryOptionsHashtableItem) {
      //      System.out.println("(QueryOptionsHashtable.mainOptionsHashtable)---  " + "\n" +
        //                       QueryOptionsHashtable.mainOptionsHashtable.entrySet().toString());
        }

        if (ae.getSource() == updateOptionsHashtableItem) {
    //        QueryOptionsHashtable.createMainOptionsHashtable();
        }

        if (ae.getSource() == testHashtableItem) {
            //check earlier for name existing in the domain

            String name = "exhibit45";

            Hashtable allEntityTypes = new Hashtable();
            Hashtable allEntitiesAndGeneric = new Hashtable();
            Hashtable allGeneric = new Hashtable();
            allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            allEntitiesAndGeneric = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");

            Vector path;
            boolean isEntityType = false;

            if (allEntityTypes.containsKey(name)) {
                path = Mpiro.win.struc.getFullPathParentsVectorFromMainDBHashtable(name, "Entity type");
                isEntityType = true;
            }
            else {
                path = Mpiro.win.struc.getFullPathParentsVectorFromMainDBHashtable(name, "Entity");
            }
            //that should never be - check earlier for name existing in the domain

            DefaultMutableTreeNode currentNode = DataBasePanel.top;
            Enumeration enum1 = DataBasePanel.top.preorderEnumeration();
            while (enum1.hasMoreElements()) {
                currentNode = (DefaultMutableTreeNode) enum1.nextElement();
                String namenode = currentNode.toString();
              //System.out.println("ttttest namenode: " + currentNode);
                if (namenode.equalsIgnoreCase(name)) {
                    break;
                }
            }
            Object[] o = (Object[]) currentNode.getPath();
            TreePath treeP = new TreePath(o);
            DataBasePanel.databaseTree.expandPath(treeP);
            DataBasePanel.databaseTree.setSelectionPath(treeP);

            //System.out.println("+ " + treeP.toString());

            DataBasePanel.databaseTree.scrollPathToVisible(treeP);
            DataBasePanel.databaseTree.revalidate();
            DataBasePanel.databaseTree.repaint();

            TreePreviews.generalDataBasePreview();
            TreePreviews.setDataBaseTableAfterSearch(name, currentNode, isEntityType);
        }

     //   if (ae.getSource() == createXmlOutputItem) {
    //        new CreateSolemlDialog();
      //  }
    }
    
}

