/***************

<p>Title: ELEON Window</p>

<p>Description:
The main ELEON window.
</p>

<p>Copyright (c) 2001-2009 National Centre for Scientific Research "Demokritos"</p>

@author Dimitris Spiliotopoulos (MPIRO, 2001-2004)
@author Kostas Stamatakis
@author Theofilos Nikolaou
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


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.zip.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.net.URL;
import java.io.*;
import javax.swing.tree.*;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.DialogExportToOwl;
import gr.demokritos.iit.eleon.authoring.FlagPanel;
import gr.demokritos.iit.eleon.authoring.ExportDialog;
import gr.demokritos.iit.eleon.authoring.IconData;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.LexiconPanel;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.MpiroFileFilter;
import gr.demokritos.iit.eleon.authoring.OwlExport;
import gr.demokritos.iit.eleon.authoring.OwlFileFilter;
import gr.demokritos.iit.eleon.authoring.OwlImport;
import gr.demokritos.iit.eleon.authoring.Prepositions;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.authoring.QueryOptionsHashtable;
import gr.demokritos.iit.eleon.authoring.QueryUsersHashtable;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.MessageDialog;
import gr.demokritos.iit.eleon.ui.Reasoner;
import gr.demokritos.iit.eleon.ui.RobotCharacteristicsPanel;
import gr.demokritos.iit.eleon.ui.StoriesPanel;
import gr.demokritos.iit.eleon.ui.UsersPanel;
import gr.demokritos.iit.eleon.ui.lang.LangChooser;


public class ELEONWindow extends JFrame
implements ActionListener
{

    // A.I 28/10/02 don't create Structures here, do it via pageFactory later
    // public static Structures emulator = new Structures();

    public static String selectedFont = "Dialog";
    public static boolean debugMode = false;
    public static boolean alltabsMode = false;
    public static boolean resetInteractionHistory = true;
    public static boolean resetInteractionHistoryBeforeEachPreview = true;
    
    //static final long serialVersionUID= 7753555422102686221L;
    // public static MpiroRpcServer eeee;

    public static Color colorG = new Color(100, 100, 100);
    public static Font font = new Font(Mpiro.selectedFont, Font.BOLD, 10);
    public static Border border = new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED),
        new EtchedBorder(EtchedBorder.LOWERED));

    public static JTabbedPane tabbedPane;
    private static String userDir = System.getProperty("user.dir");
    public static String loadedDomain = new String("");
    public static ZipFile domainZip = null;
    public static ZipFile resourcesZip = null;
    private KButton newButton;
    private KButton saveButton;
    private KButton loadButton;
    private KButton exportButton;
    private KButton quitButton;
    private JPanel buttonColumn;

    private JPanel panelA;
    private DataBasePanel panelB;
    private JPanel panelC;
    private JPanel panelD;
    private JPanel panelF;
    static StoriesPanel storiespanel;
    private URL previewURL;
    private JPanel flags;
    private static FlagPanel flagPanel;
    
   // public static ComparisonTree comparTree = new ComparisonTree();
  //  public static ComparisonTree statisticalTree = new ComparisonTree();

    final JFileChooser d = new JFileChooser(System.getProperty("user.dir"));


    public static Locale enLocale = new Locale("en", "US");
    public static Locale grLocale = new Locale("el", "GR");
    public static Locale itLocale = new Locale("it", "IT");

    public static Locale selectedLocale;

    public static String activatedPreviewLanguage = "";
    public static ArrayList selectedLanguagesToExportArrayList = new ArrayList();
    public static boolean restart = false;

    public static boolean needExportToEmulator = false;
    public static boolean needExportToExprimo = true;
    public static File jardir = new File(System.getProperty("user.dir"));

    private JTextField textToFind;
    private KButton searchButton;
    private JPanel searchPanel;

    public ELEONWindowMenuBar menuBar;
    ELEONWindowListener listener;
    
    public ELEONWindow()
    {
        
     /*   try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception e){}
       */ 
        super.setTitle("ELEON authoring tool");
        /* Setting the Mpiro default close operation to do nothing unless explicitly
         told */
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        /* Put the M-PIRO logo on the frame's corner */
        ImageIcon im = new ImageIcon( Mpiro.obj.image_corner );
        Image image = im.getImage();
        setIconImage(image);
        menuBar = new ELEONWindowMenuBar( this );
        setJMenuBar( menuBar );

        /* Add a window listener to the frame */
        listener = new ELEONWindowListener();
        addWindowListener( listener );

        /* A JTabbedPane */
        tabbedPane = new JTabbedPane(SwingConstants.TOP);

        QueryHashtable.createRobotCharVectorAndHash();
        /* Three JPanels and an instance of
           MpiroPanel, LexiconPanel and MicroPanel  */
        panelA = new UsersPanel();
        panelB = new DataBasePanel();
        panelC = new LexiconPanel();
        panelD = new StoriesPanel();
        //panelF = new MacronodePanel(null);

        /* The panel containing all the buttons
           and the search panel (buttonColumn) */
        buttonColumn = new JPanel(new GridBagLayout());
        //buttonColumn.setPreferredSize(new Dimension(400,65));; //theofilos
        //buttonColumn.setBackground(colorG);; //theofilos
        //buttonColumn.setBorder(new LineBorder(new Color(255,255,255), 2)); //theofilos
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0, 12, 0, 12); //theofilos (25,2,8,2)
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.1;
        c.weighty = 0.0;

        /* The 5-button panel */
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        newButton = new KButton(new ImageIcon(Mpiro.obj.image_new));
        loadButton = new KButton(new ImageIcon(Mpiro.obj.image_open));
        //ImageIcon openIcon = new ImageIcon("open.gif");
        //saveButton.setIcon(openIcon);
        saveButton = new KButton(new ImageIcon(Mpiro.obj.image_save));
        exportButton = new KButton(new ImageIcon(Mpiro.obj.image_exportText));
        quitButton = new KButton(new ImageIcon(Mpiro.obj.image_exit));
        newButton.setPreferredSize(new Dimension(40, 20));
        loadButton.setPreferredSize(new Dimension(40, 20));
        saveButton.setPreferredSize(new Dimension(40, 20));
        exportButton.setPreferredSize(new Dimension(40, 20));
        quitButton.setPreferredSize(new Dimension(40, 20));

        buttonPanel.add(newButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(quitButton);
        buttonPanel.setOpaque(true);
        //buttonPanel.setBorder(new EtchedBorder()); // border);
        buttonPanel.setPreferredSize(new Dimension(150, 25)); //theofilos 24

        /*
         *   The multiLegend, a panel that changes its content
         *   when changing tab on the tabbedPane
         */
        //multiLegend = new JPanel();   //theofilos //for 5 lines

        /* The legend (a panel giving an ID for each node's icon) */
        //LegendPanel legendPanel = new LegendPanel(0);
        //multiLegend.add("Center", legendPanel);
        //multiLegend.setOpaque(false);
        //multiLegend.setPreferredSize(new Dimension(60,300));

        textToFind = new JTextField();
        textToFind.setFont(new Font(Mpiro.selectedFont, Font.BOLD, 12));
        textToFind.setPreferredSize(new Dimension(200, 24));
        textToFind.setEnabled(false);

        searchButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "search_text"));
        searchButton.setFont(new Font(Mpiro.selectedFont, Font.BOLD, 12));
        searchButton.setForeground(new Color(0, 0, 0));
        searchButton.setPreferredSize(new Dimension(75, 24));
        searchButton.setEnabled(false);

        searchPanel = new JPanel();
        searchPanel.add("West", textToFind);
        searchPanel.add("East", searchButton);
        searchPanel.setOpaque(false);

       /* The 3 flags */
        flagPanel = new FlagPanel();

        /* Adding the small panels into the buttonColumn  */
        //c.gridwidth = ; c.gridheight = 1; //theofilos c.gridwidth = 1; c.gridheight = 8;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        buttonColumn.add(buttonPanel, c);
        /*      c.fill = GridBagConstraints.VERTICAL;
           c.weightx = 1.0; c.weighty = 1.0;
           c.gridwidth = 1; c.gridheight = 16;
           c.gridy=8;
           buttonColumn.add(multiLegend, c);
           c.fill = GridBagConstraints.EAST;  */
        c.insets = new Insets(0, 10, 0, 10); //theofilos (25,2,8,2)
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.1;
        c.weighty = 0.0;
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        buttonColumn.add(searchPanel, c);
        c.weightx = 0.1;
        c.weighty = 0.0;
        //     c.gridwidth = 1; c.gridheight = 1;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 2;
        c.gridy = 0;
        //buttonColumn.add(flagPanel, c);

        /* Adding listeners to the buttons */
        newButton.addActionListener( this );
        saveButton.addActionListener( this );
        loadButton.addActionListener( this );
        exportButton.addActionListener( this );
        quitButton.addActionListener( this );
        //flagPanel.enButton.addActionListener( this );
        //flagPanel.itButton.addActionListener( this );
        //flagPanel.grButton.addActionListener( this );
        searchButton.addActionListener( this );
        textToFind.addActionListener( this );

        /* Creating and naming the tabs */

        createTab();
        createTab();
        createTab();
        createTab();
        createTab();
        createTab();
        tabbedPane.setBackground(colorG);
        tabbedPane.setOpaque(true);
        tabbedPane.addChangeListener(new TabChangeListener());

        tabbedPane.setTitleAt(tabbedPane.getTabCount() - 6, LangResources.getString(Mpiro.selectedLocale, "usertypes_text"));
        tabbedPane.setTitleAt(tabbedPane.getTabCount() - 5, LangResources.getString(Mpiro.selectedLocale, "database_text"));
        tabbedPane.setTitleAt(tabbedPane.getTabCount() - 4, LangResources.getString(Mpiro.selectedLocale, "lexicon_text"));
        tabbedPane.setTitleAt(tabbedPane.getTabCount() - 3, LangResources.getString(Mpiro.selectedLocale, "stories_text"));
        tabbedPane.setTitleAt(tabbedPane.getTabCount() - 2, LangResources.getString(Mpiro.selectedLocale, "documentplanning_text"));
        tabbedPane.setTitleAt(tabbedPane.getTabCount() - 1, LangResources.getString(Mpiro.selectedLocale, "macronodes_text"));

        if (!Mpiro.alltabsMode) {
            tabbedPane.removeTabAt(5);
            tabbedPane.removeTabAt(4);
        }
        // removing stories tab
        tabbedPane.removeTabAt(3);

        setLook(1);
        //d.updateUI();

        /* Adding the tabbedPane and the lowerPanel (butons & statusbar)
           in the ContentPane of the Frame  */
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add("North", buttonColumn); //theofilos  "West"
        contentPane.add("Center", tabbedPane); //theofilos  "Center"
        pack();
        
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Dimension size = getSize();
        //setSize(screenSize.width,screenSize.height - 30);
        //setLocation( (screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2 ) );
        setLocation(0, 0);
        
        setExtendedState( MAXIMIZED_BOTH );
        setVisible( true );

        /* Initialize the domain */
        initializeDomain();
    } //constructor

    public void setLook(int look) {
        try {
            if (look == 1) {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            if (look == 2) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            }
            if (look == 3) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            if (look == 4) {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            }
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (Exception exc) {
            System.err.println("Error loading L&F: " + exc);
        }
    }

    /* The actions for each button's press */
    public void actionPerformed( ActionEvent e )
    {
        if( (e.getSource() == newButton) || (e.getSource() == this.menuBar.newFileItem) ) {
            Object[] optionButtons = {
                LangResources.getString(Mpiro.selectedLocale, "ok_button"),
                LangResources.getString(Mpiro.selectedLocale, "cancel_button") };

            int k = JOptionPane.showOptionDialog(this,
                                                 LangResources.getString(Mpiro.selectedLocale, "youAreAboutToDeleteTheEntireDomain_dialog"), //spiliot
                                                 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"), //spiliot
                                                 JOptionPane.WARNING_MESSAGE,
                                                 JOptionPane.OK_CANCEL_OPTION,
                                                 new ImageIcon( Mpiro.obj.image_mpirotext ),
                                                 optionButtons,
                                                 optionButtons[1]);

            if (k == 0) {
                DataBasePanel.microPlanPanel.setVisible(false);
                // First, clear the two main hashtables and the corresponding panels
                QueryHashtable.clearDomain();
                // Then, create the default two NodeVectors

                //QueryHashtable.createMainDBHashtable();
                QueryLexiconHashtable.createMainLexiconHashtable();
                QueryHashtable.createBasicEntityType("type", "Data Base");
                QueryHashtable.createBasicEntityType("Data Base", "Basic-entity-types");

                QueryHashtable.createDefaultUpperVector();

                // Finally, clear all trees
                DataBasePanel.clearTree();
                LexiconPanel.clearTree();
                StoriesPanel.clearTree();
                UsersPanel.clearTree();
                UsersPanel.robotsChar=new RobotCharacteristicsPanel();
                UsersPanel.users.add(new DefaultMutableTreeNode(new IconData(UsersPanel.ICON_USER, "NewUserType")));
                QueryUsersHashtable.createDefaultUser("NewUserType");

                // put fileName on frame's titleBar
                setTitle("ELEON authoring tool");
                loadedDomain = "";
                this.menuBar.pserverAddressItem.setEnabled(false);
                this.menuBar.activatePreviewLanguageMenu.setEnabled(false);
                this.menuBar.xmlMenu.setEnabled(false);
                //resetInteractionHistoryItem.setEnabled(false);
                //resetInteractionHistoryEmulatorItem.setEnabled(false);
                panelB.fillComboBox();
            }
            else {}
        }

        if( e.getSource() == this.menuBar.runReasoner ) {
        	Reasoner reas = new Reasoner( getFrames()[0], true );
            reas.setSize(400,520);
                reas.setLocation(300,150);
                reas.setTitle("Racer Connection");
            reas.setVisible(true);
         }
        
        if( (e.getSource() == saveButton) || (e.getSource() == this.menuBar.saveFileItem) ) {
            d.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            d.setAcceptAllFileFilterUsed(false);
            MpiroFileFilter filter = new MpiroFileFilter();
            OwlFileFilter filter2 = new OwlFileFilter();
            d.setFileFilter( filter2 );             //kallonis
            d.addChoosableFileFilter(filter);
            int returnVal = d.showSaveDialog( Mpiro.win );
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = d.getSelectedFile().toString();

                //if save to mpiro format
                if(d.getFileFilter().getDescription().equals("Mpiro files (*.mpiro)")){
                    fileName = MpiroFileFilter.checkExtension(fileName, ".mpiro");
                    try {
                        FileOutputStream output = new FileOutputStream(fileName);
                        ObjectOutputStream p = new ObjectOutputStream(output);
                        p.writeObject( (Hashtable) QueryOptionsHashtable.mainOptionsHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.mainDBHashtable);
                        
                        p.writeObject( (Hashtable) QueryLexiconHashtable.mainLexiconHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUsersHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUserModelHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUserModelStoryHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.propertiesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.valueRestrictionsHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.equivalentClassesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.superClassesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.annotationPropertiesHashtable);
                         p.writeObject( (Hashtable) QueryUsersHashtable.robotsHashtable);
                          p.writeObject( (Hashtable) QueryUsersHashtable.mainRobotsModelHashtable);
                           p.writeObject(QueryHashtable.robotCharVector);
                        p.writeObject(QueryHashtable.robotCharValuesHashtable);
                        p.flush();
                        p.close();
                    }
                    catch (java.io.IOException IOE) {
                        System.out.println("  |||| Save-domain Exception |||| " + IOE);
                        IOE.printStackTrace();
                    }
                }else{
                    DialogExportToOwl dialogExportToOwl = new DialogExportToOwl( this, "", true );
                    dialogExportToOwl.rdfFile = d.getSelectedFile();
                    if (fileName.indexOf(".")==-1) fileName=fileName+".rdf";
                    dialogExportToOwl.jTextField2.setText(fileName);
                    
                    
                    dialogExportToOwl.jTextField3.setText(fileName.substring(0, fileName.lastIndexOf('.')) + "_mpiro.xml");
                    String ontName="";
                    if (d.getSelectedFile().getName().indexOf(".")!=-1) 
                   ontName = d.getSelectedFile().getName().substring(0, d.getSelectedFile().getName().lastIndexOf('.'));
                    else
                        ontName = d.getSelectedFile().getName()+".rdf";
                    dialogExportToOwl.jTextField1.setText(QueryOptionsHashtable.getBaseURI());
                    dialogExportToOwl.show();
                    if (dialogExportToOwl.modalResult) {
                        File rdfFile = dialogExportToOwl.rdfFile;
                        if (rdfFile.getName().indexOf(".")==-1) rdfFile= new File(rdfFile.getAbsolutePath()+".rdf");
                        fileName = OwlFileFilter.checkExtension(fileName, ".rdf");
                        try {
                            setCursor(Cursor.WAIT_CURSOR);
                            OwlExport.ExportToOwlFile(rdfFile, dialogExportToOwl.jComboBox1.getSelectedItem().toString(), dialogExportToOwl.jTextField1.getText(),rdfFile.getName(), false);
                            OwlExport.exportLexicon(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf(rdfFile.getName())), rdfFile.getName());
          
                        OwlExport.exportMicroplans(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf(rdfFile.getName())), rdfFile.getName());
                    
                        OwlExport.exportUserModelling(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf(rdfFile.getName())), rdfFile.getName());
OwlExport.exportRobotModelling(rdfFile.getAbsolutePath().substring(0, rdfFile.getAbsolutePath().lastIndexOf(rdfFile.getName())), rdfFile.getName());
                        System.out.println("file:::::"+rdfFile.toString());
                       System.out.println("format:::::"+dialogExportToOwl.jComboBox1.getSelectedItem().toString());
                       System.out.println("url"+dialogExportToOwl.jTextField1.getText()); 
                        
                        
                        }
                        catch (Exception IOE) {
                            System.out.println("   |||| Save-domain Exception |||| " + IOE);
                            IOE.printStackTrace();
                        }
                    }else{
                        d.removeChoosableFileFilter(filter);
                        d.removeChoosableFileFilter(filter2);
                        return;
                    }
                }
                // put fileName on frame's titleBar
                setTitle("ELEON authoring tool --- " + fileName);
                loadedDomain = d.getSelectedFile().getName();
                try { //spiliot
                    File domainZipFile = new File(userDir + System.getProperty("file.separator") +
                                                  loadedDomain + "_domain.zip"); //spiliot
                    if (loadedDomain.indexOf(".")!=-1 )
                   domainZipFile = new File(userDir + System.getProperty("file.separator") +
                                                  loadedDomain.substring(0, loadedDomain.lastIndexOf('.')) + "_domain.zip"); //spiliot
 
                         
                    
                    File resourcesZipFile = new File(userDir + System.getProperty("file.separator") + "mpiro_resources.zip"); //spiliot
                    if (domainZipFile.exists()) { //spiliot
                        domainZip = new ZipFile(domainZipFile); //spiliot
                    } //spiliot
                    if (resourcesZipFile.exists()) { //spiliot
                        resourcesZip = new ZipFile(resourcesZipFile); //spiliot
                    } //spiliot
                } //spiliot
                catch (Exception ze) { //spiliot
                    ze.printStackTrace(); //spiliot
                } //spiliot
                this.menuBar.pserverAddressItem.setEnabled( true );
                this.menuBar.activatePreviewLanguageMenu.setEnabled( true );
                this.menuBar.xmlMenu.setEnabled( true );
                //resetInteractionHistoryItem.setEnabled(false);
                //resetInteractionHistoryEmulatorItem.setEnabled(false);
                fireActionEnglishItem();
                panelB.fillComboBox();
                panelB.updateFlagCombo();
                setCursor(Cursor.DEFAULT_CURSOR);
            }
            d.removeChoosableFileFilter(filter);
            d.removeChoosableFileFilter(filter2);
        }

        if (e.getSource() == loadButton || e.getSource() == this.menuBar.openFileItem) {
            Object[] optionButtons = {
                LangResources.getString(Mpiro.selectedLocale, "ok_button"), //spiliot
                LangResources.getString(Mpiro.selectedLocale, "cancel_button")}; //spiliot

            int k = 0;
            //if domain is not empty
            if (QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").size() != 2)
                k = JOptionPane.showOptionDialog(this,
                                                 LangResources.getString(Mpiro.selectedLocale, "youAreAboutToDeleteTheEntireDomain_dialog"),
                                                 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                                 JOptionPane.WARNING_MESSAGE,
                                                 JOptionPane.OK_CANCEL_OPTION,
                                                 new ImageIcon( Mpiro.obj.image_mpirotext ),
                                                 optionButtons, //spiliot
                                                 optionButtons[1]); //spiliot

            if (k == 0) {
                d.setFileSelectionMode(JFileChooser.FILES_ONLY);
                d.setAcceptAllFileFilterUsed(false);
                MpiroFileFilter filter = new MpiroFileFilter();
                OwlFileFilter filter2 = new OwlFileFilter();//kallonis
                d.setFileFilter(filter2);             //kallonis
                d.addChoosableFileFilter(filter);
                int returnVal = d.showOpenDialog( this );
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    panelB.previewFunctionsPanel.setVisible(false);
                    panelB.updateFlagCombo();
                    DataBasePanel.microPlanPanel.setVisible(false);
                    setCursor(Cursor.WAIT_CURSOR); //theofilos WAIT_CURSOR
                    File fileName = d.getSelectedFile();
                    try {
                        //if save to mpiro format
                        if(d.getFileFilter().getDescription().equals("Mpiro files (*.mpiro)")){
                            // First, clear the two main hashtables and the corresponding panels
                            QueryHashtable.clearDomain();

                            FileInputStream input = new FileInputStream(fileName);
                            ObjectInputStream p = new ObjectInputStream(input);
                            
                            try {
                                Object o1 = p.readObject();
                                QueryOptionsHashtable.mainOptionsHashtable = (Hashtable) o1;
                                Object o2 = p.readObject();
                                QueryHashtable.mainDBHashtable = (Hashtable) o2;
   //                             Hashtable en=QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity");
     ///                           en.put("olympic-games","current");
        //                        en.put("virtual-reality","current");
                                Object o3 = p.readObject();
                                QueryLexiconHashtable.mainLexiconHashtable = (Hashtable) o3;
                                Object o4 = p.readObject();
                                QueryUsersHashtable.mainUsersHashtable = (Hashtable) o4;
                                Object o5 = p.readObject();
                                QueryUsersHashtable.mainUserModelHashtable = (Hashtable) o5;
                                Object o6 = p.readObject();
                                QueryUsersHashtable.mainUserModelStoryHashtable = (Hashtable) o6;
                                
                //                Object o7 = p.readObject();
                //                QueryHashtable.propertiesHashtable= (Hashtable) o7;
                            }
                            catch (java.lang.ClassNotFoundException ce) {
                                System.out.println("    |||| Open-domain Exception |||| " + ce);
                            }
                            
                            try{
                                
                            Object o7 = p.readObject();
                                QueryHashtable.propertiesHashtable= (Hashtable) o7;
                                Object o8 = p.readObject();
                                QueryHashtable.valueRestrictionsHashtable= (Hashtable) o8;
                                
                              
                            }
                            catch (java.io.IOException ce1) {
                                QueryHashtable.propertiesHashtable= new Hashtable();
                                QueryHashtable.valueRestrictionsHashtable=new Hashtable();
                               // QueryHashtable.equivalentClassesHashtable=new Hashtable();
                                System.out.println("|||| old version mpiro file |||| " + ce1);
                            }
                            try{
                            Object o9 = p.readObject();
                                QueryHashtable.equivalentClassesHashtable= (Hashtable) o9;
                            }
                            catch (java.io.IOException ce1) {
                             QueryHashtable.equivalentClassesHashtable=new Hashtable();
                                System.out.println("|||| (not so) old version mpiro file |||| " + ce1);
                            }
                            try{
                            Object o10 = p.readObject();
                                QueryHashtable.superClassesHashtable= (Hashtable) o10;
                            }
                            catch (java.io.IOException ce1) {
                             QueryHashtable.superClassesHashtable=new Hashtable();
                                System.out.println("|||| (not so) old version mpiro file |||| " + ce1);
                            }
                            try{
                            Object o11 = p.readObject();
                                QueryHashtable.annotationPropertiesHashtable= (Hashtable) o11;
                            }
                            catch (java.io.IOException ce1) {
                             QueryHashtable.annotationPropertiesHashtable=new Hashtable();
                                System.out.println("|||| (not so) old version mpiro file |||| " + ce1);
                            }
                            try{
                            Object o12 = p.readObject();
                                QueryUsersHashtable.robotsHashtable= (Hashtable) o12;
                            }
                            catch (java.io.IOException ce1) {
                             QueryUsersHashtable.robotsHashtable=new Hashtable();
                                System.out.println("|||| (not so) old version mpiro file |||| " + ce1);
                            }
                            try{
                            Object o13 = p.readObject();
                                QueryUsersHashtable.mainRobotsModelHashtable= (Hashtable) o13;
                               QueryHashtable.robotCharVector=(Vector)  p.readObject();
                        QueryHashtable.robotCharValuesHashtable=(Hashtable)p.readObject();
                            }
                            catch (java.io.IOException ce1) {
                             QueryUsersHashtable.mainRobotsModelHashtable=new Hashtable();
                             QueryUsersHashtable.fillMainRobotsModelHashtable();
                             
                             QueryUsersHashtable.createDefaultRobot("NewProfile");
                              QueryHashtable.robotCharVector=new Vector();
                        QueryHashtable.robotCharValuesHashtable=new Hashtable();
                                System.out.println("|||| (not so) old version mpiro file |||| " + ce1);
                            }
                            
                            // p.writeObject( (Hashtable) QueryUsersHashtable.mainRobotsModelHashtable);
                            p.close();
                            
                            
                           //for(Enumeration properties=QueryHashtable.propertiesHashtable.elements();properties.hasMoreElements();)
                          // {
                          //      Vector temp=(Vector)properties.nextElement();
                          //      temp.add("true");
                          //  }
                            UsersPanel.robotsChar=new RobotCharacteristicsPanel();
                            
                            
                        }
                        else { //save to owl
                            //System.out.println("0.06");
                            if (!OwlImport.ImportFromOwlFile(fileName, true)) {
                                setCursor(Cursor.DEFAULT_CURSOR);
                                //System.out.println("0.07");
                                return;
                            }
                            //initialize file path for export (kallonis)
                            DialogExportToOwl.jTextField2.setText(d.getSelectedFile().getAbsolutePath());
                            String ontName = fileName.getName().substring(0, fileName.getName().lastIndexOf('.'));
                            DialogExportToOwl.jTextField1.setText(QueryOptionsHashtable.getBaseURI());
                            //-------------
                        }
//QueryHashtable.mainDBHashtable.remove("exhibit1");
                        // these methods clear all trees and redraw them
                //       Object tttttt=QueryHashtable.mainDBHashtable.get("aaa");
                        DataBasePanel.reloadDBTree();
                        LexiconPanel.reloadLexiconTree();
                        StoriesPanel.reloadStoriesTree();
                        UsersPanel.reloadUsersTree();
                        this.menuBar.pserverAddressItem.setEnabled( true );
                        this.menuBar.activatePreviewLanguageMenu.setEnabled( true );
                        this.menuBar.xmlMenu.setEnabled( true );
                        //resetInteractionHistoryItem.setEnabled(true);
                        //resetInteractionHistoryEmulatorItem.setEnabled(true);

                        /*
                           QueryOptionsHashtable.addPServerAddressToMainOptionsHashtable("143.233.6.3", "1111");
                         */

                        // put fileName on frame's titleBar
                        setTitle("ELEON authoring tool --- " + fileName);
                        loadedDomain = d.getSelectedFile().getName();
                        
                         File domainZipFile = new File(userDir + System.getProperty("file.separator") +
                                                  loadedDomain + "_domain.zip"); //spiliot
                    if (loadedDomain.indexOf(".")!=-1 )
                        domainZipFile = new File(userDir + System.getProperty("file.separator") +
                            loadedDomain.substring(0, loadedDomain.lastIndexOf('.')) + "_domain.zip"); //spiliot
                        File resourcesZipFile = new File(userDir + System.getProperty("file.separator") + "mpiro_resources.zip"); //spiliot
                        if (domainZipFile.exists()) { //spiliot
                            domainZip = new ZipFile(domainZipFile); //spiliot
                        }
                        if (resourcesZipFile.exists()) { //spiliot
                            resourcesZip = new ZipFile(resourcesZipFile); //spiliot
                        } //spiliot
                    }
                    catch (Exception ioex) {
                        System.out.println("      |||| Exception ||||" + ioex);
                        ioex.printStackTrace(System.out);
                    }

                    // A.I. 25/02/03
                    ArrayList languages = new ArrayList(); //spiliot
                    languages.add("English"); //spiliot
                    languages.add("Italian"); //spiliot
                    languages.add("Greek"); //spiliot
                    if (loadedDomain.lastIndexOf('.')!=-1)
                    Prepositions.reset(loadedDomain.substring(0, loadedDomain.lastIndexOf('.')), languages); //spiliot
                    else
                        Prepositions.reset(loadedDomain, languages);

                    fireActionEnglishItem();
                    panelB.fillComboBox();
                    setCursor(Cursor.DEFAULT_CURSOR); // DEFAULT_CURSOR
                    System.out.println( getTitle() );
                    DataBasePanel.exportDir = getTitle().substring( 26 ); //maria
                    needExportToEmulator = true;
                    needExportToExprimo = true;
                }
                d.removeChoosableFileFilter(filter);
                d.removeChoosableFileFilter(filter2);
            }
        }

        if (e.getSource() == exportButton || e.getSource() == this.menuBar.exportFileItem) {
            if (!Mpiro.win.getTitle().equalsIgnoreCase("ELEON authoring tool")) {
                new ExportDialog( super.getTitle().substring(26) );
            }
            else {
                new MessageDialog(this, MessageDialog.cannotExportANewOrUnsavedDomain_dialog);
            }
        }

        if (e.getSource() == quitButton || e.getSource() == this.menuBar.exitFileItem) {
            Object[] options = {
                LangResources.getString(Mpiro.selectedLocale, "yes_text"),
                LangResources.getString(Mpiro.selectedLocale, "no_text"),
                LangResources.getString(Mpiro.selectedLocale, "cancel_text")
            };

            int j = JOptionPane.showOptionDialog(this,
                                                 LangResources.getString(Mpiro.selectedLocale, "wouldYouLikeToSaveTheChanges-ETC_dialog"),
                                                 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                                 JOptionPane.DEFAULT_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE,
                                                 new ImageIcon( Mpiro.obj.image_mpirotext ),
                                                 options,
                                                 options[0]);

            if (j == 0) {
                MpiroFileFilter filter = new MpiroFileFilter();
                d.setFileFilter(filter);
                int returnVal = d.showSaveDialog( this );
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = d.getSelectedFile().toString();
                    fileName = MpiroFileFilter.checkExtension(fileName, ".mpiro");
                    try {
                        FileOutputStream output = new FileOutputStream(fileName);
                        ObjectOutputStream p = new ObjectOutputStream(output);
                        p.writeObject( (Hashtable) QueryOptionsHashtable.mainOptionsHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.mainDBHashtable);
                        p.writeObject( (Hashtable) QueryLexiconHashtable.mainLexiconHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUsersHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUserModelHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUserModelStoryHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.propertiesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.valueRestrictionsHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.equivalentClassesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.superClassesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.annotationPropertiesHashtable);
                         p.writeObject( (Hashtable) QueryUsersHashtable.robotsHashtable);
                          p.writeObject( (Hashtable) QueryUsersHashtable.mainRobotsModelHashtable);
                           p.writeObject(QueryHashtable.robotCharVector);
                        p.writeObject(QueryHashtable.robotCharValuesHashtable);
                        p.flush();
                        p.close();
                    }
                    catch (java.io.IOException IOE) {
                        
                        System.out.println("       |||| Exception |||| ");
                        IOE.printStackTrace();
                      IOE.printStackTrace(System.out);
                    }
                    System.exit(0);
                }
                else { return; }
                System.exit(0);
            }
            if (j == 1) {
                System.exit(0);
            }
            else {}
        }

        /*
           if (e.getSource() == flagPanel.enButton)
           {
          System.out.println("ENGLISH");
           }
           if (e.getSource() == flagPanel.itButton)
           {
          System.out.println("ITALIAN");
           }
           if (e.getSource() == flagPanel.grButton)
           {
          System.out.println("GREEK");
           }
         */

        if (e.getSource() == this.menuBar.aboutHelpItem) {
            JOptionPane.showMessageDialog(this,
                                          "ELEON AUTHORING TOOL v1.1" + "\n\n" +
                                          "Copyright (c) 2001 - 2009" + "\n" +
                                          "Software and Knowledge Engineering Laboratory," + "\n" +
                                          "Institute of Informatics and Telecommunications," + "\n" +
                                          "National Centre for Scientific Research \"Demokritos\", Greece." + "\n\n" +
                                          "Contains enhancements by the" + "\n" +
                                          "Information Processing Laboratory," + "\n" +
                                          "Department of Informatics," + "\n" +
                                          "Athens University of Economics and Business, Greece.",
                                          "About ELEON Authoring Tool",
                                          JOptionPane.PLAIN_MESSAGE,
                                          new ImageIcon( Mpiro.obj.image_mpiro ) );
        }

        //theofilos  -->
        if (e.getSource() == searchButton) {
            int tabNo = tabbedPane.getSelectedIndex();
            //1 = DBHashtable , 2 = LexiconHashtable, 3 = UsersHashtable
            String findText = textToFind.getText();
            //TreePath treeP = DataBasePanel.databaseTree.getNextMatch(findText,0,Position.Bias.Forward);
            if (tabNo == 1) {
                DataBasePanel.searchTree(findText);
            }
        }

        if (e.getSource() == textToFind) { //theofilos 10
            int tabNo = tabbedPane.getSelectedIndex();
            //1 = DataBase Tab , 2 = LexiconTab, 0 = Users Tab
            String findText = textToFind.getText();
            //TreePath treeP = DataBasePanel.databaseTree.getNextMatch(findText,0,Position.Bias.Forward);
            if (tabNo == 1) {
                DataBasePanel.searchTree(findText);
            }
        } //<-- //theofilos
    } // actionPerformed

    /* Method that adds a panel to every tab of the tabbedPane */
    public void createTab() {
        JPanel panel = new JPanel();
        switch (tabbedPane.getTabCount() % 6) {
            case 0:
                panel = panelA;
                break;
            case 1:
                panel = panelB;
                break;
            case 2:
                panel = panelC;
                break;
            case 3:
                panel = panelD;
                break;
            case 4:
                panel = new JPanel();
                break;
            case 5:
                panel = new JPanel();
                //panel = panelF;
                break;
        }

        panel.setOpaque(true);
        panel.setBackground(new Color(175, 175, 175));

        tabbedPane.addTab("", panel);
        tabbedPane.setBackgroundAt(tabbedPane.getTabCount() - 1,
                                   new Color(150, 150, 150));
        tabbedPane.setForegroundAt(tabbedPane.getTabCount() - 1,
                                   new Color(50, 50, 50));
    }


    public static void fireActionEnglishItem() {
        //String olduserdir = System.getProperty("user.dir"); //maria
  /*      String olduserdir = jardir.getAbsolutePath(); //maria
        String exprimohome = olduserdir + System.getProperty("file.separator") + "exprimo";
        System.setProperty("user.dir", exprimohome);
        try {
            // setting the exprimo home directory
            uk.ac.ed.ltg.exprimo.workbench.Workbench0.setPropertyValue("exprimo.home", exprimohome);
            if (resourcesZip == null) { //spiliot mexri kai 1602
                System.out.println("Cannot start exprimo because resources file mpiro_resources.zip not found");
            }
            else {
                ArrayList zips = new ArrayList();
              
                    if (loadedDomain.indexOf(".")!=-1 )
                zips.add(olduserdir + System.getProperty("file.separator") + loadedDomain.substring(0, loadedDomain.lastIndexOf('.')) + "_domain.zip"); //kallonis
                    else
                zips.add(olduserdir + System.getProperty("file.separator") + loadedDomain + "_domain.zip"); //kallonis
                zips.add(olduserdir + System.getProperty("file.separator") + "mpiro_resources.zip");

                if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("")) {
                    // creating a new factory for English
                    Mpiro.pageFactory = uk.ac.ed.ltg.exprimo.workbench.Factory.newFactory(zips, "exprimo/Examples/mpiro_v2_1-en.xml");
                    Mpiro.pageFactory.reloadLanguage(zips, "exprimo/Examples/mpiro_v2_1-it.xml"); //spiliot
                    Mpiro.pageFactory.initializeEmulator();
                    ExportUtilsPEmulator.exportToEmulator();
                }
                else {
                    // reloading a new factory for English
                    Mpiro.pageFactory.reloadLanguage(zips, "exprimo/Examples/mpiro_v2_1-en.xml"); //spiliot
                }
                // A.I. 28/10/02 Don't set the IP Address , tell
                // Factory to use emulator
                Mpiro.pageFactory.setUseEmulator(true);
                Mpiro.pageFactory.setUseDummyPredicates(true);
            }
        }
        catch (Exception ex) {
            System.out.println("(Exception)---  " + ex);
            ex.printStackTrace();
        }
        System.setProperty("user.dir", olduserdir);
        resetInteractionHistoryItem.setEnabled(true);
*/
        // setEnabled the language // TEMP
        Mpiro.activatedPreviewLanguage = "ENGLISH";
        flagPanel.setMpiroFlagPanelLanguage("ENGLISH");
    }

    public static void fireActionItalianItem() {
        //String olduserdir = System.getProperty("user.dir");  //maria
   /*     String olduserdir = jardir.getAbsolutePath(); //maria
        String exprimohome = olduserdir + System.getProperty("file.separator") + "exprimo";
        System.setProperty("user.dir", exprimohome);
        try {
            // setting the exprimo home directory
            uk.ac.ed.ltg.exprimo.workbench.Workbench.setPropertyValue("exprimo.home", exprimohome);
            if (resourcesZip == null) { //spiliot mexri kai 1654
                System.out.println("Cannot start exprimo because resources file mpiro_resources.zip not found");
            }
            else {
                ArrayList zips = new ArrayList();
                zips.add(olduserdir + System.getProperty("file.separator") + loadedDomain.substring(0, loadedDomain.lastIndexOf('.')) + "_domain.zip"); //kallonis
                zips.add(olduserdir + System.getProperty("file.separator") + "mpiro_resources.zip");

                if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("")) {
                    // creating a new factory for Italian
                    Mpiro.pageFactory = uk.ac.ed.ltg.exprimo.workbench.Factory.newFactory(zips, "exprimo/Examples/mpiro_v2_1-it.xml"); //spiliot
                    Mpiro.pageFactory.initializeEmulator();
                    ExportUtilsPEmulator.exportToEmulator();
                }
                else {
                    // reloading a new factory for Italian
                    Mpiro.pageFactory.reloadLanguage(zips, "exprimo/Examples/mpiro_v2_1-it.xml"); //spiliot
                }
                Mpiro.pageFactory.setUseEmulator(true);
                Mpiro.pageFactory.setUseDummyPredicates(true);
            }
        }
        catch (Exception ex) {
            System.out.println("(Exception)---  " + ex);
            ex.printStackTrace();
        }
        System.setProperty("user.dir", olduserdir);
        resetInteractionHistoryItem.setEnabled(true);
*/
        // setEnabled the language // TEMP
        Mpiro.activatedPreviewLanguage = "ITALIAN";
        flagPanel.setMpiroFlagPanelLanguage("ITALIAN");
    }

    public static void fireActionGreekItem() {
        //String olduserdir = System.getProperty("user.dir");  //maria
    /*    String olduserdir = jardir.getAbsolutePath(); //maria
        String exprimohome = olduserdir + System.getProperty("file.separator") + "exprimo";
        System.setProperty("user.dir", exprimohome);
        try {
            // setting the exprimo home directory
            uk.ac.ed.ltg.exprimo.workbench.Workbench.setPropertyValue("exprimo.home", exprimohome);
            if (resourcesZip == null) { //spiliot mexri kai 1704
                System.out.println("Cannot start exprimo because resources file mpiro_resources.zip not found");
            }
            else {
                ArrayList zips = new ArrayList();
                zips.add(olduserdir + System.getProperty("file.separator") + loadedDomain.substring(0, loadedDomain.lastIndexOf('.')) + "_domain.zip"); //kallonis
                zips.add(olduserdir + System.getProperty("file.separator") + "mpiro_resources.zip");
                if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("")) {
                    // creating a new factory for Greek
                    Mpiro.pageFactory = uk.ac.ed.ltg.exprimo.workbench.Factory.newFactory(zips, "exprimo/Examples/mpiro_v2_1-gk.xml"); //spiliot
                    Mpiro.pageFactory.initializeEmulator();
                    ExportUtilsPEmulator.exportToEmulator();
                }
                else {
                    // reloading a new factory for Greek
                    Mpiro.pageFactory.reloadLanguage(zips, "exprimo/Examples/mpiro_v2_1-gk.xml"); //spiliot
                }
                Mpiro.pageFactory.setUseEmulator(true);
                Mpiro.pageFactory.setUseDummyPredicates(true);
            }
        }
        catch (Exception ex) {
            System.out.println("(Exception)---  " + ex);
            ex.printStackTrace();
        }
        System.setProperty("user.dir", olduserdir);
        //englishItem.setEnabled(true);
        //italianItem.setEnabled(true);
        //greekItem.setEnabled(false);
        resetInteractionHistoryItem.setEnabled(true);
*/
        // setEnabled the language // TEMP
        Mpiro.activatedPreviewLanguage = "GREEK";
        flagPanel.setMpiroFlagPanelLanguage("GREEK");
    }

    /* --------------------------------------------------------------
     * Domain Initialisation!
     * --------------------------------------------------------------
     */
    public void initializeDomain() {
        /** Setting and checking on the LOCALE */
        Locale.setDefault(enLocale);
        Locale defaultLocale = Locale.getDefault();
        String country = defaultLocale.getCountry();
        String language = defaultLocale.getLanguage();

        /*
         * First: DataBase initialisation.
         */
        QueryHashtable.createMainDBHashtable();
        QueryHashtable.createPropertiesHashtable();
        QueryHashtable.createValueRestrictionsHashtable();
        QueryHashtable.createEquivalentClassesHashtable();
        QueryHashtable.createSuperClassesHashtable();
        QueryHashtable.createAnnotationPropertiesHashtable();
                

        // Make the default NodeVectors for every tree node except Root.
        Enumeration enum1 = DataBasePanel.top.breadthFirstEnumeration();
        DefaultMutableTreeNode tmp;
        IconData id;
        while (enum1.hasMoreElements()) {
            tmp = (DefaultMutableTreeNode) enum1.nextElement();
            Object o = (Object) (tmp.getUserObject());
            id = (IconData) o;
            Icon ii = id.getIcon();
            ImageIcon ima = (ImageIcon) ii;
            String node = tmp.toString();
            TreeNode tn = (TreeNode) tmp;

            if ( (ima != DataBasePanel.ICON_TOP_B) &&
                (ima != DataBasePanel.ICON_BUILT) &&
                (ima != DataBasePanel.ICON_GEI) &&
                (ima != DataBasePanel.ICON_GENERIC)) {
                if (tn.getParent() == null) {
                    QueryHashtable.createBasicEntityType(node, "Data Base");
                }
                else {
                    String parent = tn.getParent().toString();
                    QueryHashtable.createBasicEntityType(parent, node);
                    //QueryHashtable.createDefaultStory(node);  /// spiliot
                }
            }

            if ( (ima == DataBasePanel.ICON_GEI) ||
                (ima == DataBasePanel.ICON_GENERIC)) {
                if (tn.getParent() == null) {
                    // do nothing
                    //System.out.println("Leaf's parent is tree's root!!!");
                }
                else {
                    String parent = tn.getParent().toString();
                    QueryHashtable.createEntity(parent, node);
                    //QueryHashtable.createDefaultStory(node);  /// spiliot
                }
            }
        }

        /*
         * Second: creating DefaultUpperVector
         */
        QueryHashtable.createDefaultUpperVector();

        /*
         * Third: Lexicon initialisation.
         */
        QueryLexiconHashtable.createMainLexiconHashtable();

        /*
         * Fourth: Users initialisation.
         */
        QueryUsersHashtable.createMainUserModelHashtable();
        QueryUsersHashtable.createMainUserModelStoryHashtable();
        QueryUsersHashtable.createMainUsersHashtable();
        QueryUsersHashtable.createmainRobotsModelHashtable();
        QueryUsersHashtable.createRobotsHashtable();

        /*
         * Fifth: Options initialisation.
         */
        QueryOptionsHashtable.createMainOptionsHashtable();

    } // initializeDomain()

    class TabChangeListener
        implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            //setLegend(((JTabbedPane) e.getSource()).getSelectedIndex());
            panelB.fillComboBox();
            if ( ( (JTabbedPane) e.getSource()).getSelectedIndex() == 0 ||
                ( (JTabbedPane) e.getSource()).getSelectedIndex() == 2) {
                searchButton.setEnabled(false);
                textToFind.setEnabled(false);
            }
            else {
                searchButton.setEnabled(true);
                textToFind.setEnabled(true);
            }

            if ( ( (JTabbedPane) e.getSource()).getSelectedIndex() == 3) {
                StoriesPanel.reloadStoriesTree();

                StoriesPanel.multiPanel.removeAll();
                StoriesPanel.multiPanel.revalidate();
                StoriesPanel.multiPanel.repaint();
                StoriesPanel.label01.setText("");

                /*
                  if (StoriesPanel.last != null)
                  {
                   System.out.println("()---- " + StoriesPanel.last.toString());
                   TreePath lastSelectedComponentPath = new TreePath(StoriesPanel.last.getPath());
                   //TreePath lastSelectedComponentPath = new TreePath(storiesTree.getSelectionPaths());
                   //TreePath dd = storiesTree.getSelectionPath();
                   System.out.println("()---- " + lastSelectedComponentPath.toString());
                   //storiesTree.scrollPathToVisible(dd);

                   //TreePreviews.setStoriesTable(StoriesPanel.last.toString());
                   //storiesTree.setSelectionPath(lastSelectedComponentPath);
                   StoriesPanel.storiesTree.expandPath(lastSelectedComponentPath);

                   StoriesPanel.storiesTree.scrollPathToVisible(lastSelectedComponentPath);
                   StoriesPanel.storiesTree.revalidate();
                   StoriesPanel.storiesTree.repaint();
                  }
                 */
            }
        }
    } //class TabChangeListener

    public static void main( String args[] )
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("DEBUG")) {
                Mpiro.debugMode = true;
            }
            else if (args[0].equalsIgnoreCase("ALLTABS")) {
                Mpiro.alltabsMode = true;
            }
            else {
                Mpiro.selectedFont = args[0];
            }
            //System.out.println(args[0]);
            //Font givenFont = new Font(args[0], Font.PLAIN, 10);
            //System.out.println("()+++  " + givenFont);
            //String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(Locale.ENGLISH);
            //for (int i=0; i<fonts.length; i++)
            //{
            //    System.out.println("()---  " + fonts[0]);
            //}
            //System.out.println("()+++  " + Mpiro.selectedFont);
        }
        Mpiro.obj = new Mpiro();
        new LangChooser();
        Mpiro.win = new ELEONWindow();
    }

}

