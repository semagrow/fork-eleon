//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.aueb.cs.nlg.Languages.Languages;
import gr.aueb.cs.nlg.NLGEngine.NLGEngine;
import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryOptionsHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;
import gr.demokritos.iit.eleon.ui.AnnotationPropertiesPanel;
import gr.demokritos.iit.eleon.ui.Equivalent;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KDialog;
import gr.demokritos.iit.PServer.UMVisit;
import java.net.URL;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.text.Position; //theofilos

/**
 * <p>Title: DataBasePanel</p>
 * <p>Description: The whole panel for DATABASE tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
//Edited and enhanced by Maria Prospathopoulou and Theofilos Nickolaou
public class DataBasePanel
        extends JPanel {
    public static ImageIcon ICON_TOP = new ImageIcon(Mpiro.obj.image_top);
    public static ImageIcon ICON_TOP_A = new ImageIcon(Mpiro.obj.image_topa);
    public static ImageIcon ICON_TOP_B = new ImageIcon(Mpiro.obj.image_topb);
    public static ImageIcon ICON_BASIC = new ImageIcon(Mpiro.obj.image_basic);
    public static ImageIcon ICON_BUILT = new ImageIcon(Mpiro.obj.image_built);
    public static ImageIcon ICON_BOOK = new ImageIcon(Mpiro.obj.image_closed);
    public static ImageIcon ICON_GEI = new ImageIcon(Mpiro.obj.image_leaf);
    public static ImageIcon ICON_GENERIC = new ImageIcon(Mpiro.obj.image_generic);
    public static ImageIcon ICON_MPIRO = new ImageIcon(Mpiro.obj.image_mpiro);
    public static ImageIcon ICON_HADRA = new ImageIcon(Mpiro.obj.image_hadra);
    
    
    
    public static JScrollPane htmlView;
    public static JPanel previewPanel;
    static JPanel depthPanel;
    static JCheckBox useComparisons;
    static ViewPanel htmlPane;
    static JScrollPane treeView;
    private static boolean DEBUG = false;
    private URL helpURL;
    protected TreePath clickedPath;
    public static JLabel label01;
    public static JTree databaseTree;
    public static JPanel diafora;
    public static JPanel multiPanel;
    public static JPanel multiTable;
    public static JPanel multiNoun;
    public static JPanel multiFlagPanel;
    
    static JPopupMenu popuptopA;
    static JPopupMenu popupbasic;
    static JPopupMenu popupsub;
    static JPopupMenu popupentity;
    static JPopupMenu popupgeneric;
    
    public static ImageIcon im;
    
    public static DefaultMutableTreeNode top;
    public static DefaultMutableTreeNode topA;
    static DefaultMutableTreeNode topB;
    static DefaultTreeModel treeModel;
    public static TreePath selpath;
    static String name = "";
    static String userID="";
    public static DefaultMutableTreeNode last;
    
    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    // LangResources
    private String renameNode_text = LangResources.getString(Mpiro.selectedLocale, "renameNode_text");
    private String newName_text = LangResources.getString(Mpiro.selectedLocale, "newName_text");
    private String addNewBasicType_action = LangResources.getString(Mpiro.selectedLocale, "addNewBasicType_action");
    private String addSubtype_action = LangResources.getString(Mpiro.selectedLocale, "addSubtype_action");
    private String test = LangResources.getString(Mpiro.selectedLocale, "Addexistingtype_action");
    private String addEntity_action = LangResources.getString(Mpiro.selectedLocale, "addEntity_action");
    private String addEntitiesFromDB_action = LangResources.getString(Mpiro.selectedLocale, "addEntitiesFromDB_action");
    private String addGenericEntity_action = LangResources.getString(Mpiro.selectedLocale, "addGenericEntity_action");
    private String upperModelTypes_action = LangResources.getString(Mpiro.selectedLocale, "upperModelTypes_action");
    private String rename_action = LangResources.getString(Mpiro.selectedLocale, "rename_action");
    private String delete_action = LangResources.getString(Mpiro.selectedLocale, "delete_action");
    private String previewEnglish_action = LangResources.getString(Mpiro.selectedLocale, "previewEnglish_action");
    private String previewItalian_action = LangResources.getString(Mpiro.selectedLocale, "previewItalian_action");
    private String previewGreek_action = LangResources.getString(Mpiro.selectedLocale, "previewGreek_action");
    private String addBasicType_text = LangResources.getString(Mpiro.selectedLocale, "addBasicType_text");
    private String nameOfTheBasicType_text = LangResources.getString(Mpiro.selectedLocale, "nameOfTheBasicType_text");
    private String linksToUpperModelTypes_text = LangResources.getString(Mpiro.selectedLocale, "linksToUpperModelTypes_text");
    private String upperModelTypes_text = LangResources.getString(Mpiro.selectedLocale, "upperModelTypes_text");
    private String textPreviewArea_text = LangResources.getString(Mpiro.selectedLocale, "textPreviewArea_text");
    private String errorInGeneratingPreview_ETC_text = LangResources.getString(Mpiro.selectedLocale, "errorWhileGeneratingPreview-ETC_text");
    
    public static String exportDir = new String(); //maria
    
    public static JPanel previewFunctionsPanel; //theofilos
    private JComboBox usersCombo;
    private JComboBox robotsCombo;//theofilos
    private KButton previewButton; //theofilos
    private JPanel previewAllPanel; //theofilos
    private JComboBox flags; //theofilos
    private JComboBox depth;
    private JLabel depthLabel;
    //public static JPasswordField pass;
    
    public static JPanel microPlanPanel; //theofilos  13-01-2004
    public static JComboBox microPlanFlags; //theofilos  13-01-2004
    public static JComboBox microPlanNum; //theofilos
    public static KButton microPlanApprop; //theofilos
    public static JLabel microLangText; //theofilos
    public static JLabel microLayoutText; //theofilos
    public static JLabel microField; //theofilos
    public JPanel microAndPreviewPanel; //theofilos
    
    public static boolean pServerIsStarted=false;
    public static String dbUser;
    public static String dbPass;
    public static String navIP="127.0.0.1";
    public static int navPort=53000;
    
    private static Vector searchedElements; //theofilos
    
    private static ImageIcon ii_UK = new ImageIcon(Mpiro.obj.image_uk);
    private static ImageIcon ii_IT = new ImageIcon(Mpiro.obj.image_italy);
    private static ImageIcon ii_GR = new ImageIcon(Mpiro.obj.image_greece);
    
    public JCheckBox hideNodes;
    
    Hashtable foundTexts = new Hashtable();
    String previousFindText = "";
    
    public static NLGEngine myEngine;
    
    /**
     * The constructor
     */
    public DataBasePanel() {
        //Random generator=new Random();
       // userID=String.valueOf(generator.nextInt(100000000));
        searchedElements = new Vector();
        searchedElements.add("Data-types"); //theofilos
        searchedElements.add("String"); //theofilos
        searchedElements.add("Number"); //theofilos
        searchedElements.add("Date"); //theofilos
        searchedElements.add("Dimension"); //theofilos
        
        setLayout(new BorderLayout());
        
        /* The Tree and the ScrollPane. Then, put the first into the second */
        top = new DefaultMutableTreeNode(new IconData(ICON_TOP, "Data Base"));
        treeModel = new DefaultTreeModel(top);
        createNodes(top, 1);
        databaseTree = new JTree(treeModel);
        DataBaseTreeListener databasetreeListener = new DataBaseTreeListener();
        addPopups();
        databaseTree.addMouseListener(databasetreeListener);
        
        databaseTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        databaseTree.setShowsRootHandles(true);
        databaseTree.setEditable(false);
        databaseTree.putClientProperty("JTree.lineStyle", "Angled");
        
        IconCellRenderer cellrenderer = new IconCellRenderer();
        databaseTree.setCellRenderer(cellrenderer);
        IconCellEditor celleditor = new IconCellEditor(databaseTree);
        databaseTree.setCellEditor(celleditor);
        databaseTree.setInvokesStopCellEditing(true);
        
        treeView = new JScrollPane(databaseTree);
        treeView.setPreferredSize(new Dimension(300, 500));
        treeView.setMinimumSize(new Dimension(290, 500));
        
        /* The ex-HTML viewing pane */
        htmlPane = new ViewPanel(ICON_MPIRO, textPreviewArea_text, 14);
        
        //htmlPane = new JEditorPane();
        //htmlPane.setEditable(false);
        //htmlPane.setContentType("text/html");
        //initHelp();
        
        htmlView = new JScrollPane(htmlPane);
        htmlView.setPreferredSize(new Dimension(400, 120));
        htmlView.setMinimumSize(new Dimension(400, 120));
        
        previewPanel = new JPanel(new BorderLayout());
        previewPanel.add("Center", htmlView);
        
        /* The label */
        label01 = new JLabel(LangResources.getString(Mpiro.selectedLocale, "clickanEntityOrAnEntityTypeNode_text"));
        label01.setFont(new Font(Mpiro.selectedFont, Font.BOLD, 12));
        label01.setForeground(Color.black);
        label01.setPreferredSize(new Dimension(380, 20));
        
        /* The labelPanel */
        JPanel labelPanel = new JPanel(new GridBagLayout());
        //labelPanel.setBackground(new Color(200,100,100));
        labelPanel.setBorder(new EmptyBorder(new Insets(0, 3, 0, 13)));
        multiFlagPanel = new JPanel(new BorderLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = 2;
        labelPanel.add(label01, c);
        // **************************
        //hideNodes = new JCheckBox("Hide");
        //hideNodes.setText("Hide");
        //c.anchor = GridBagConstraints.EAST;
        //labelPanel.add(hideNodes, c);
        //hideNodes.setAction(h1);
        // **************************
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        labelPanel.add(multiFlagPanel, c);
        
                /* A panel changing its content:
                   1. dbTable + nounPanel, or
                 2. dbTableEntity				 */
        multiPanel = new JPanel(new GridBagLayout());
        multiTable = new JPanel(new BorderLayout());
        multiNoun = new JPanel(new BorderLayout());
        
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        multiPanel.add(multiTable, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.0;
        c.gridy = 1;
        multiPanel.add(multiNoun, c);
        
        /* The panel of the up-right side of the SplitPanes */
        diafora = new JPanel();
        diafora.setLayout(new BorderLayout());
        diafora.add("North", labelPanel);
        diafora.add("Center", multiPanel);
        
        // The scrollPane containing the diafora panel
        JScrollPane multiScroll = new JScrollPane(diafora);
        
        // Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JSplitPane spl = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        spl.setTopComponent(multiScroll);
        microAndPreviewPanel = new JPanel(new BorderLayout()); //theofilos
        microAndPreviewPanel.setPreferredSize(new Dimension(280, 550)); //theofilos
        spl.setBottomComponent(microAndPreviewPanel); //theofilos
        microAndPreviewPanel.add(previewPanel, BorderLayout.CENTER); //theofilos
        //splitPane.setBottomComponent(spl);
        
        spl.setDividerLocation(280);
        //splitPane.setDividerLocation(290); //XXX: ignored in some releases
        //of Swing. bug 4101306
        //workaround for bug 4101306:
        spl.setPreferredSize(new Dimension(280, 550)); //(280, 580));
        splitPane.setPreferredSize(new Dimension(725, 580));
        spl.setMinimumSize(new Dimension(280, 550)); //(280, 580));
        splitPane.setMinimumSize(new Dimension(725, 580));
        /************************************/
                /* The previewFunctions Panel which contains the
                 *	users combo box the flags and the preview button*/
        //theofilos
        previewFunctionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 35, 3));
        previewAllPanel = new JPanel(new BorderLayout());
        previewFunctionsPanel.setBorder(new LineBorder(new Color(100, 100, 100), 1));
        //previewAllPanel.setBorder(new LineBorder(new Color(0,0,0), 2));
        //previewAllPanel.setLayout(new GridLayout(2,1));
        previewAllPanel.setPreferredSize(new Dimension(280, 580)); //theofilos  c.gridx
        previewAllPanel.setMinimumSize(new Dimension(280, 580)); //theofilos
        previewFunctionsPanel.setMinimumSize(new Dimension(280, 30)); //theofilos
        previewFunctionsPanel.setPreferredSize(new Dimension(280, 30)); //theofilos
        
        usersCombo = new JComboBox(); //theofilos
        usersCombo.addItem("NewUserType");
        usersCombo.setPreferredSize(new Dimension(130, 25)); //theofilos
        usersCombo.setAction(chosenUser);
        previewButton = new KButton(""); //theofilos
        previewButton.setFont(new Font(Mpiro.selectedFont, Font.BOLD, 12)); //theofilos
        previewButton.setForeground(new Color(0, 0, 0)); //theofilos
        //String selectedUser = usersCombo.getSelectedItem().toString(); //theofilos
        //previewButton.setAction(createPreview(selectedUser));//theofilos
        previewButton.setPreferredSize(new Dimension(115, 24));
        previewButton.setText(LangResources.getString(Mpiro.selectedLocale, "preview_text")); //theofilos
        depth=new JComboBox();
        depth.setPreferredSize(new Dimension(40, 25));
        depth.addItem("1");
        depth.addItem("2");
        depth.addItem("3");
        depth.addItem("4");
      //  depth.setAlignmentY(Component.TOP_ALIGNMENT);
        depthLabel=new JLabel();
        depthLabel.setText("Depth:");
      // depth.setLocation(0,0);
      // depth.setAlignmentY(0);
        depthPanel=new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        //depth.setAlignmentY(Component.TOP_ALIGNMENT);
       // depthPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        depthPanel.setPreferredSize(new Dimension(90, 25));
       
      
        depthPanel.add(depthLabel);
         depthPanel.add(depth);
      //   depth.setLocation(0,0);
         //depth.setAlignmentY(0);
        // depth.setAlignmentY(Component.TOP_ALIGNMENT);
        
        flags = new JComboBox();
        flags.setPreferredSize(new Dimension(70, 25));
        flags.addItem(ii_UK);
        flags.addItem(ii_IT);
        flags.addItem(ii_GR);
        flags.setAction(chosenLanguage);
        useComparisons=new JCheckBox();
        useComparisons.setText("Comparisons");
        //pass= new JPasswordField();
        //pass.setPreferredSize(new Dimension(115, 24));
      //  depthLabel.setAlignmentX(112);
       previewFunctionsPanel.add(depthLabel);
        previewFunctionsPanel.add(depth);
      // previewFunctionsPanel.add(depthPanel);
        previewFunctionsPanel.add(useComparisons);
        //previewFunctionsPanel.add(pass);
        previewFunctionsPanel.add(flags);
        previewFunctionsPanel.add(usersCombo);
        //previewFunctionsPanel.add(flagPanel,c);
        previewFunctionsPanel.add(previewButton);
        //depthLabel.setAlignmentX(112);
        //depthLabel.setLocation(depthLabel.getX()+420, depthLabel.getY());
       // depthLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        
        //  ********************************************   13-1-2004 //theofilos
        microPlanFlags = new JComboBox(); //theofilos
        microPlanFlags.setPreferredSize(new Dimension(47, 25)); //theofilos
        microPlanFlags.addItem(new ImageIcon(Mpiro.obj.image_uk)); //theofilos
        microPlanFlags.addItem(new ImageIcon(Mpiro.obj.image_italy)); //theofilos
        microPlanFlags.addItem(new ImageIcon(Mpiro.obj.image_greece)); //theofilos
        //microPlanFlags.setAction(chosenLanguage); //theofilos
        microLangText = new JLabel(""); //theofilos
        microLayoutText = new JLabel(""); //theofilos
        microField = new JLabel(""); //theofilos
        microPlanNum = new JComboBox(); //theofilos
        microPlanNum.setPreferredSize(new Dimension(35, 25)); //theofilos
        microPlanNum.setEditable(false); //theofilos
        microPlanNum.addItem("1"); //theofilos
        microPlanNum.addItem("2"); //theofilos
        microPlanNum.addItem("3"); //theofilos
        microPlanNum.addItem("4"); //theofilos
        microPlanNum.addItem("5"); //theofilos
        microPlanApprop = new KButton(""); //theofilos
        microPlanApprop.setPreferredSize(new Dimension(100, 24)); //theofilos
        microPlanPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 3, 3)); //theofilos
        microPlanPanel.add(microPlanFlags); //theofilos
        microPlanPanel.add(microLangText); //theofilos
        microPlanPanel.add(microLayoutText); //theofilos
        microPlanPanel.add(microPlanNum); //theofilos
        microPlanPanel.add(microField); //theofilos
        JLabel Sp = new JLabel(""); //theofilos
        Sp.setPreferredSize(new Dimension(20, 10)); //theofilos
        microPlanPanel.add(Sp); //theofilos
        microPlanPanel.add(microPlanApprop); //theofilos
        microAndPreviewPanel.add(microPlanPanel, BorderLayout.PAGE_START); //theofilos
        microPlanPanel.setVisible(false); //theofilos
        //microPlanNum.setAction(editMicroplan("1","english_action"));
        //**************************************
        previewAllPanel.add(spl, BorderLayout.CENTER);
        previewAllPanel.add(previewFunctionsPanel, BorderLayout.PAGE_END);
        previewFunctionsPanel.setVisible(false); //theofilos
        /***************************************/
        splitPane.setBottomComponent(previewAllPanel); //(spl);
        
        //Add the split pane to the DataBasePanel.
        add("Center", splitPane);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    } // constructor
    
    /**
     * This MouseListener for the first tab's tree
     */
    class DataBaseTreeListener
            extends MouseAdapter
            implements MouseListener {
        public DataBaseTreeListener() {} // constructor
        
        public void mouseClicked(MouseEvent me) {
            if (SwingUtilities.isRightMouseButton(me)) {
                int x = me.getX();
                int y = me.getY();
                
                TreePath path = databaseTree.getPathForLocation(x, y);
                if (path != null) {
                    databaseTree.setSelectionPath(path);
                    selpath = path;
                    last = (DefaultMutableTreeNode) (path.getLastPathComponent());
                    Object obj = (Object) (last.getUserObject());
                    name = obj.toString();
                    IconData id = (IconData) obj;
                    Icon ii = id.getIcon();
                    im = (ImageIcon) ii;
                    initHelp();
                    TreePreviews.generalDataBasePreview();
                    TreePreviews.setDataBaseTable(name);
                    if (im == ICON_TOP) {
                        previewFunctionsPanel.setVisible(false); //theofilos
                        microPlanPanel.setVisible(false); //theofilos
                    }
                    if (im == ICON_TOP_A) {
                        previewFunctionsPanel.setVisible(false); //theofilos
                        microPlanPanel.setVisible(false); //theofilos
                        popuptopA.show(databaseTree, x, y);
                    }
                    if (im == ICON_BASIC) {
                        previewFunctionsPanel.setVisible(false); //theofilos
                        microPlanPanel.setVisible(false); //theofilos
                        a4.setEnabled(true);
                        Enumeration ee = last.children();
                        while (ee.hasMoreElements()) {
                            String child = ee.nextElement().toString();
                            if (child.startsWith("Generic-")) {
                                a4.setEnabled(false);
                            }
                        }
                        popupbasic.show(databaseTree, x, y);
                    }
                    if (im == ICON_BOOK) {
                        previewFunctionsPanel.setVisible(false); //theofilos
                        microPlanPanel.setVisible(false); //theofilos
                        a4.setEnabled(true);
                        Enumeration ee = last.children();
                        while (ee.hasMoreElements()) {
                            String child = ee.nextElement().toString();
                            if (child.startsWith("Generic-")) {
                                a4.setEnabled(false);
                            }
                        }
                        popupsub.show(databaseTree, x, y);
                    }
                    if (im == ICON_GEI) {
                        previewFunctionsPanel.setVisible(true); //theofilos
                        microPlanPanel.setVisible(false); //theofilos
                        // TEMP
                                                /*   if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("ENGLISH"))
                                                 {
                                                  //previewEnglishEntityMenu.setEnabled(true);
                                                  //previewItalianEntityMenu.setEnabled(false);
                                                  //previewGreekEntityMenu.setEnabled(false);
                                                 }
                                                 else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("ITALIAN"))
                                                 {
                                                  //previewEnglishEntityMenu.setEnabled(false);
                                                  //previewItalianEntityMenu.setEnabled(true);
                                                  //previewGreekEntityMenu.setEnabled(false);
                                                 }
                                                 else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("GREEK"))
                                                 {
                                                  previewEnglishEntityMenu.setEnabled(false);
                                                  previewItalianEntityMenu.setEnabled(false);
                                                  previewGreekEntityMenu.setEnabled(true);
                                                 }
                                                 else
                                                 {
                                                  previewEnglishEntityMenu.setEnabled(false);
                                                  previewItalianEntityMenu.setEnabled(false);
                                                  previewGreekEntityMenu.setEnabled(false);
                                                 }
                                                 
                                                 previewEnglishEntityMenu.removeAll();
                                                 previewItalianEntityMenu.removeAll();
                                                 previewGreekEntityMenu.removeAll();
                                                 Vector userTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
                                                 Enumeration userTypesVectorEnum = userTypesVector.elements();
                                                 while (userTypesVectorEnum.hasMoreElements())
                                                 {
                                                  String usertype = userTypesVectorEnum.nextElement().toString();
                                                  previewEnglishEntityMenu.add(createPreview(usertype));
                                                  previewItalianEntityMenu.add(createPreview(usertype));
                                                  previewGreekEntityMenu.add(createPreview(usertype));
                                                 }  */
                        popupentity.show(databaseTree, x, y);
                    }
                    if (im == ICON_GENERIC) {
                        previewFunctionsPanel.setVisible(true); //theofilos
                        microPlanPanel.setVisible(false); //theofilos
                        // TEMP
                                                /*    if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("ENGLISH"))
                                                  {
                                                   previewEnglishGenericMenu.setEnabled(true);
                                                   previewItalianGenericMenu.setEnabled(false);
                                                   previewGreekGenericMenu.setEnabled(false);
                                                  }
                                                  else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("ITALIAN"))
                                                  {
                                                   previewEnglishGenericMenu.setEnabled(false);
                                                   previewItalianGenericMenu.setEnabled(true);
                                                   previewGreekGenericMenu.setEnabled(false);
                                                  }
                                                  else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("GREEK"))
                                                  {
                                                   previewEnglishGenericMenu.setEnabled(false);
                                                   previewItalianGenericMenu.setEnabled(false);
                                                   previewGreekGenericMenu.setEnabled(true);
                                                  }
                                                  else
                                                  {
                                                   previewEnglishGenericMenu.setEnabled(false);
                                                   previewItalianGenericMenu.setEnabled(false);
                                                   previewGreekGenericMenu.setEnabled(false);
                                                  }
                                                 
                                                  previewEnglishGenericMenu.removeAll();
                                                  previewItalianGenericMenu.removeAll();
                                                  previewGreekGenericMenu.removeAll();
                                                  Vector userTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
                                                  Enumeration userTypesVectorEnum = userTypesVector.elements();
                                                  while (userTypesVectorEnum.hasMoreElements())
                                                  {
                                                   String usertype = userTypesVectorEnum.nextElement().toString();
                                                   previewEnglishGenericMenu.add(createPreview(usertype));
                                                   previewItalianGenericMenu.add(createPreview(usertype));
                                                   previewGreekGenericMenu.add(createPreview(usertype));
                                                  } */
                        
                        popupgeneric.show(databaseTree, x, y);
                    }
                }
            } else { //Left has been clicked
                //microPlanPanel.setVisible(false);//theofilos
                
                int x = me.getX();
                int y = me.getY();
                TreePath path = databaseTree.getPathForLocation(x, y);
                if (path != null) {
                    microPlanPanel.setVisible(false); //theofilos
                    databaseTree.setSelectionPath(path);
                    selpath = path;
                    last = (DefaultMutableTreeNode) (path.getLastPathComponent());
                    Object obj = (Object) (last.getUserObject());
                    name = obj.toString();
                    IconData id = (IconData) obj;
                    Icon ii = id.getIcon();
                    im = (ImageIcon) ii;
                    if ( (im == ICON_GENERIC) || (im == ICON_GEI)) { //theofilos
                        previewFunctionsPanel.setVisible(true);
                    } //theofilos
                    else {
                        previewFunctionsPanel.setVisible(false);
                    } //theofilos
                    initHelp();
                    TreePreviews.generalDataBasePreview();
                    TreePreviews.setDataBaseTable(name);
                }
            }
        }
    } // class DataBaseTreeListener
    
    /**
     * The method that initializes the previewPanel
     * setting the file help.html
     */
    private void initHelp() {
        String s = null;
        try {
            s = "file:"
                    + System.getProperty("user.dir")
                    + System.getProperty("file.separator")
                    + "HelpFile.html";
            if (DEBUG) {
                //System.out.println("Help URL is " + s);
            }
            helpURL = new URL(s);
            //displayURL(helpURL);
        } catch (Exception e) {
            System.err.println("Couldn't create help URL: " + s);
        }
    }
    
        /*
          public static void displayURL(URL url) {
         try {
          htmlPane.setPage(url);
         } catch (IOException e) {
          System.err.println("Attempted to read a bad URL: " + url);
         }
          }
         */
        /*
          Action h1 = new AbstractAction()//DataBaseTable.rowActive)
          {
         public void actionPerformed(ActionEvent e)
         {
          if (hideNodes.isSelected())
          {		int nodeNum = DataBaseTable.rowActive;
          for (int i=nodeNum; i>0; i--)
          {
           //System.out.println(nodeNum);
           if (TreePreviews.dbtm.delete(0))
           {
          DataBaseTable.rowActive = 1;
          DataBaseTable.dbTable.tableChanged(new TableModelEvent(
           TreePreviews.dbtm, 1, 1, TableModelEvent.ALL_COLUMNS,
           TableModelEvent.INSERT));
          Mpiro.needExportToExprimo = true;		//maria
          //nodeNum--;
           }
         
          }
          multiTable.revalidate();
          multiTable.repaint();
         
          DataBaseTable.dbTable.revalidate();
          DataBaseTable.dbTable.repaint();
         }
         else
         {
          TreePath path = databaseTree.getSelectionPath();
          last = (DefaultMutableTreeNode)(path.getLastPathComponent());
          Object obj = (Object)(last.getUserObject());
          name = obj.toString();
          //System.out.println("name="+name);
          TreePreviews.setDataBaseTable(name);
          TreePreviews.generalDataBasePreview();
         
          //microPlanPanel.setVisible(false);//theofilos
           ///int x = me.getX();
           //int y = me.getY();
           TreePath path = databaseTree.getSelectionPath();
           if (path != null)
           {
           //System.out.println("NOT NULL="+path.toString());
           //databaseTree.setSelectionPath(path);
           selpath = path;
         last = (DefaultMutableTreeNode)(path.getLastPathComponent());
         Object obj = (Object)(last.getUserObject());
         name = obj.toString();
         IconData id = (IconData)obj;
         Icon ii = id.getIcon();
         im = (ImageIcon)ii;
         if ((im == ICON_GENERIC) || (im == ICON_GEI)) //theofilos
          { previewFunctionsPanel.setVisible(true); } //theofilos
         else  {previewFunctionsPanel.setVisible(false);} //theofilos
         initHelp();
         TreePreviews.generalDataBasePreview();
         TreePreviews.setDataBaseTable(name);
          }
          }
         }
          };
         */
    
    /**
     *  The Actions, i.e. the commands added to the popup menus
     */
    Action a1 = new AbstractAction(addNewBasicType_action) {
        public void actionPerformed(ActionEvent e) {
            NodeVector rootVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("Data Base");
            Vector upperVector = (Vector) rootVector.elementAt(1);
            KDialog newBasicTypeDialog = new KDialog(addBasicType_text,
                    nameOfTheBasicType_text,
                    linksToUpperModelTypes_text,
                    upperVector,
                    true,
                    "BASIC", true);
            
        }
    };
    
    Action a2 = new AbstractAction(addSubtype_action) {
        public void actionPerformed(ActionEvent e) {
            KDialog addEntityDialog = new KDialog(addSubtype_action, newName_text, "", new Vector(), false, "ENTITY-TYPE", true);
        }
    };
    
    Action a3 = new AbstractAction(addEntity_action) {
        public void actionPerformed(ActionEvent e) {
            KDialog addEntityDialog = new KDialog(addEntity_action, newName_text, "", new Vector(), false, "ENTITY", true);
        }
    };
    
    Action test1 = new AbstractAction(test) {
        public void actionPerformed(ActionEvent e) {
            KDialog addEntityDialog = new KDialog(test, newName_text, "", new Vector(), false, "TEST", true);
        }
    };
    
    Action a4 = new AbstractAction(addGenericEntity_action) {
        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode v = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
            String k = v.toString();
            addObject(new IconData(ICON_GENERIC, "Generic-" + k));
            Mpiro.win.struc.createEntity(k, "Generic-" + k);
            Mpiro.win.struc.updateCreatedEntity(k, "Generic-" + k);
            Mpiro.win.struc.addEntityInUserModelHashtable("Generic-" + k);
            //QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable("Generic-" + k);
            Mpiro.needExportToEmulator = true; //maria
            Mpiro.needExportToExprimo = true; //maria
            databaseTree.revalidate();
            databaseTree.repaint();
        }
    };
    
    Action a5 = new AbstractAction(upperModelTypes_action) {
        public void actionPerformed(ActionEvent e) {
            NodeVector rootVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("Data Base");
            Vector upperVector = (Vector) rootVector.elementAt(1);
            KDialog upperTypesDialog = new KDialog(upperModelTypes_action,
                    null,
                    upperModelTypes_text,
                    upperVector,
                    true,
                    "UPPER", true);
            databaseTree.repaint();
        }
    };
    
    Action a6 = new AbstractAction(rename_action) {
        public void actionPerformed(ActionEvent e) {
            renameCurrentNode();
        }
    };
    
    Action a7 = new AbstractAction(delete_action) {
        public void actionPerformed(ActionEvent e) {
            Object[] optionButtons = {
                LangResources.getString(Mpiro.selectedLocale, "ok_button"), //theofilos
                LangResources.getString(Mpiro.selectedLocale, "cancel_button")}; //theofilos
            
            int j = JOptionPane.showOptionDialog(DataBasePanel.this, //theofilos
                    LangResources.getString(Mpiro.selectedLocale, "confirm_deletion_dialog")
                    + "\n" + "\"" + last.toString() + "\" ?", //theofilos
                    LangResources.getString(Mpiro.selectedLocale, "warning_dialog"), //theofilos
                    JOptionPane.WARNING_MESSAGE, //theofilos
                    JOptionPane.OK_CANCEL_OPTION, //theofilos
                    null, //theofilos
                    optionButtons, //theofilos
                    optionButtons[1]); //theofilos
            
            
            if (j == 0) {
                removeNode();
                Mpiro.needExportToEmulator = true; //maria
                Mpiro.needExportToExprimo = true; //maria
                databaseTree.repaint();
            } else {
                databaseTree.repaint();
            }
        }
    };
    
    //kallonis
    Action a8 = new AbstractAction(addEntitiesFromDB_action){
        public void actionPerformed(ActionEvent e){
            DialogImportFromDatabase dialog = new DialogImportFromDatabase(Mpiro.win.getFrames()[0],"",true);
            if(dialog.initialized)
                dialog.show();
        }
    };
    
    //theofilos 10 lines
    Action chosenUser = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            //JComboBox cb = (JComboBox)e.getSource();
            String selectedUser = (String) usersCombo.getSelectedItem();
            previewButton.setAction(createPreview(selectedUser));
            previewButton.setText(LangResources.getString(Mpiro.selectedLocale, "preview_text")); //theofilos
            //updateLabel(petName);
        }
    };
    
    
    //theofilos Action Listener for flags ComboBox 15 lines
    Action chosenLanguage = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            //0 english, 1 italian, 2 greek
            int lang = flags.getSelectedIndex();
            if (lang == 0 && Mpiro.activatedPreviewLanguage != "ENGLISH") {
                gr.demokritos.iit.eleon.ui.ELEONWindow.fireActionEnglishItem();
            }
            if (lang == 1 && Mpiro.activatedPreviewLanguage != "ITALIAN") {
            	gr.demokritos.iit.eleon.ui.ELEONWindow.fireActionItalianItem();
            }
            if (lang == 2 && Mpiro.activatedPreviewLanguage != "GREEK") {
            	gr.demokritos.iit.eleon.ui.ELEONWindow.fireActionGreekItem();
            } else {}
        }
    };
    
    
   /*     Action a9 = new AbstractAction("view supertypes") {
                public void actionPerformed(ActionEvent e) {
                    String last=DataBasePanel.last.toString();
                    if (last.substring(0, last.length()-1).endsWith("_occur"))
                        last=last.substring(0, last.length()-7);
                        String parents= "";
                    Enumeration all=DataBasePanel.top.preorderEnumeration();
                    while(all.hasMoreElements()){
                        DefaultMutableTreeNode next=(DefaultMutableTreeNode) all.nextElement();
                        if ((next.toString().equalsIgnoreCase(last))||(next.toString().startsWith(last+"_occur"))){
                            parents=parents+next.getParent().toString()+" ";
                        }
                    }
    
                        KDialog supertypes = new KDialog("view supertypes", parents, "", new Vector(), false, "SUPER", true);
                }
        };
    */
    Action equiv = new AbstractAction("Equivalent Types") {
        public void actionPerformed(ActionEvent e)  {
            
            Equivalent eq = new Equivalent(Mpiro.win.getFrames()[0], true, true);
            eq.setVisible(true);
        }
    };
    
    Action annotation = new AbstractAction("Annotation Properties") {
        public void actionPerformed(ActionEvent e)  {
            
            AnnotationPropertiesPanel ap = new AnnotationPropertiesPanel(Mpiro.win.getFrames()[0], true);
            ap.setVisible(true);
        }
    };
    Action robotsClass = new AbstractAction("Edit profiles preference...") {
            public void actionPerformed(ActionEvent e) {
                RobotsModelDialog umDialog = new RobotsModelDialog("ClassOrInd",
                        last.toString(),
                        "CLASS");
            }
        };
        Action robotsInd = new AbstractAction("Edit profiles preference...") {
            public void actionPerformed(ActionEvent e) {
                RobotsModelDialog umDialog = new RobotsModelDialog("ClassOrInd",
                        last.toString(),
                        "Ind");
            }
        };
         Action robotsChar = new AbstractAction("Profile Attributes...") {
            public void actionPerformed(ActionEvent e) {
                RobotsCharDialog umDialog = new RobotsCharDialog("Char",
                        last.toString(),
                        "CHAR");
            }
        };
    
    Action superTypes = new AbstractAction("SuperTypes") {
        public void actionPerformed(ActionEvent e)  {
            
            Equivalent eq = new Equivalent(Mpiro.win.getFrames()[0], true, false);
            eq.setVisible(true);
            
        }
    };
    
        /*Action createPreview(String usertype) {
                final String userTypeString = usertype;
                Action action = new AbstractAction(userTypeString) {
                        public void actionPerformed(ActionEvent e) {
                                // find the entity node-name
                                String entity = databaseTree.getLastSelectedPathComponent().toString();
         
                                if (entity.substring(0, entity.length()-1).endsWith("_occur"))
                                    entity=entity.substring(0, entity.length()-7);
//Mpiro.win.struc.getEntityTypeOrEntity(entity)
/*                                String parent=last.getParent().toString();
                                if (parent.substring(0, parent.length()-1).endsWith("_occur"))
                                    parent=parent.substring(0, parent.length()-7);
NodeVector f11=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parent);
         
f11.nounVector=(Vector) f11.elementAt(2);*/
    // setting the exprimo home directory
                        /*	String olduserdir = System.getProperty("user.dir");
                                System.setProperty("user.dir", olduserdir + System.getProperty("file.separator") + "exprimo");
                         
                                StringBuffer previewText = new StringBuffer();
                                /*
                                  try
                                  {
                                  Mpiro.pageFactory.setCurrentUser("auth-" + userTypeString);
                                  uk.ac.ed.ltg.exprimo.workbench.ExpUser u = Mpiro.pageFactory.getCurrentUser();
                                  u.setNewFocus(entity);
                         
                                  uk.ac.ed.ltg.exprimo.page.Page p;
                         */
/*
                                try {
                                        if (!Mpiro.notAutomaticExportItem.isSelected()) { //maria
                                                if (Mpiro.needExportToEmulator && Mpiro.needExportToExprimo) { //maria
                                                        ExportDialog.doubleExport = true;
                                                } //maria
                                                if (Mpiro.needExportToEmulator) { //maria
                                                        Mpiro.pageFactory.setUseEmulator(true); //maria
                                                        Mpiro.pageFactory.initializeEmulator(); //maria
                                                        try { //maria
                                                                ExportUtilsPEmulator.exportToEmulator(); //maria
                                                        } //maria
                                                        catch (UMException ume) { //maria
                                                                System.out.println("(DataBasePanel:ExportUtilsEmulator.exportToEmulator)..." + ume); //maria
                                                                ume.printStackTrace(); //maria
                                                        } //maria
                                                        Mpiro.needExportToEmulator = false; //maria
                                                } //maria
                                                if (Mpiro.needExportToExprimo) { //maria
                                                        if (Mpiro.exportToChoiceItem.isSelected()) { //maria
                                                                //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.clear(); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("English"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Italian"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Greek"); //maria
                                                                Mpiro.restart = true;
                                                                //System.out.println("exportDir----"+exportDir);							//maria
                                                                new DirectoryChooser(exportDir, "EXPRIMO"); //maria
                                                                Mpiro.needExportToExprimo = false; //maria
                                                        } //maria
                                                        else { //Mpiro.exportToDefault.isSelected()										//maria
                                                                Mpiro.selectedLanguagesToExportArrayList.clear(); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("English"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Italian"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Greek"); //maria
                                                                Mpiro.restart = true; //maria
                                                                ArrayList languageList = (ArrayList) Mpiro.selectedLanguagesToExportArrayList.clone(); //maria
                                                                //File defaultExportDir = new File(System.getProperty("user.dir"));				//maria
                                                                //String dirPath = defaultExportDir.getAbsolutePath();
                                                                ExportUtilsWebinfo.createExportDirectories(Mpiro.jardir.getAbsolutePath()); //maria
                                                                String loadedDomain = new String(""); //maria
                                                                if (Mpiro.loadedDomain.endsWith(".mpiro")) { //maria
                                                                        loadedDomain = Mpiro.loadedDomain.substring(0, Mpiro.loadedDomain.length() - 6); //maria
                                                                } //maria
                                                                ExportUtilsExprimo exprimoExport = new ExportUtilsExprimo(loadedDomain, languageList); //maria
                                                                if (ExportDialog.doubleExport) { //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(1, 1); //maria
                                                                        int n = 2; //maria
                                                                        Iterator langIter = languageList.iterator(); //maria
                                                                        while (langIter.hasNext()) { //maria
                                                                                String language = langIter.next().toString(); //maria																		//maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting " + language + "  lexicon", 1); //maria
                                                                                exprimoExport.createLexicon(language); //maria																	//maria
                                                                                ExportUtilsPEmulator.progress.updateProgressBar(n, 1); //maria
                                                                                n++; //maria
                                                                        } //maria
 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting types.xml", 1); //maria
                                                                        exprimoExport.createTypesGram(languageList); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(5, 1); //maria
 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting predicates.xml", 1); //maria
                                                                        exprimoExport.createPredicatesGram(languageList); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(6, 1); //maria
 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting instances.xml", 1); //maria
                                                                        exprimoExport.createInstancesGramAndMsgcat(languageList); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(7, 1); //maria
 
                                                                        exprimoExport.saveToZip(languageList); //maria
 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting imagesinfo.xml", 1); //maria
                                                                        ExportUtilsWebinfo.createImagesInfo(); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(8, 1); //maria
 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1); //maria
                                                                        ExportUtilsWebinfo.createLanguageInfo(); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(9, 1); //maria
 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...", 1);	//maria
                                                                        if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English")) { //maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
                                                                                Mpiro.fireActionEnglishItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian")) { //maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
                                                                                Mpiro.fireActionItalianItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek")) { //maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Greek", 1);		//maria
                                                                                Mpiro.fireActionGreekItem(); //maria
                                                                        } //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(10, 1); //maria
 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
                                                                        ExportUtilsPEmulator.progress.updateOKButton(true); //maria
 
                                                                        ExportDialog.doubleExport = false; //maria
                                                                } //maria
                                                                else { //maria
                                                                        ExportProgressMonitor progress = new ExportProgressMonitor(0, 10, 0); //maria
                                                                        progress.exprimoOrEmulatorsLabel.setText(LangResources.getString(Mpiro.selectedLocale, "exportToExprimo_text")); //maria
                                                                        progress.show(); //maria
                                                                        progress.updateOKButton(false); //maria
                                                                        progress.updateProgressBar(1, 1); //maria
 
                                                                        int n = 2; //maria
                                                                        Iterator langIter = languageList.iterator(); //maria
                                                                        while (langIter.hasNext()) { //maria
                                                                                String language = langIter.next().toString(); //maria
                                                                                //progress.updateProgressLabel("Exporting " + language + "  lexicon", 1);	//maria
                                                                                exprimoExport.createLexicon(language); //maria
                                                                                progress.updateProgressBar(n, 1); //maria
                                                                                n++; //maria
                                                                        } //maria
 
                                                                        //progress.updateProgressLabel("Exporting types.xml", 1);			//maria
                                                                        exprimoExport.createTypesGram(languageList); //maria
                                                                        progress.updateProgressBar(5, 1); //maria
 
                                                                        //progress.updateProgressLabel("Exporting predicates.xml", 1);	//maria
                                                                        exprimoExport.createPredicatesGram(languageList); //maria
                                                                        progress.updateProgressBar(6, 1); //maria
 
                                                                        //progress.updateProgressLabel("Exporting instances.xml", 1);		//maria
                                                                        exprimoExport.createInstancesGramAndMsgcat(languageList); //maria
                                                                        progress.updateProgressBar(7, 1); //maria
 
                                                                        exprimoExport.saveToZip(languageList); //maria
 
                                                                        //progress.updateProgressLabel("Exporting imagesinfo.xml", 1);	//maria
                                                                        ExportUtilsWebinfo.createImagesInfo(); //maria
                                                                        progress.updateProgressBar(8, 1); //maria
 
                                                                        //progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1); //maria
                                                                        ExportUtilsWebinfo.createLanguageInfo(); //maria
                                                                        progress.updateProgressBar(9, 1); //maria
 
                                                                        //progress.updateProgressLabel("Restarting exprimo...", 1);		//maria
 
                                                                        if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English")) { //maria
                                                                                //progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
                                                                                Mpiro.fireActionEnglishItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian")) { //maria
                                                                                //progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
                                                                                Mpiro.fireActionItalianItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek")) { //maria
                                                                                //progress.updateProgressLabel("Restarting exprimo...Greek", 1);	//maria
                                                                                Mpiro.fireActionGreekItem(); //maria
                                                                        } //maria
 
                                                                        progress.updateProgressBar(10, 1); //maria
 
                                                                        //progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
                                                                        progress.updateOKButton(true); //maria
                                                                } //maria
                                                                Mpiro.needExportToExprimo = false; //maria
                                                        } //maria
                                                } //maria
                                        } //maria
                                        Mpiro.pageFactory.setCurrentUser("auth-" + userTypeString);
                                        uk.ac.ed.ltg.exprimo.workbench.ExpUser u = Mpiro.pageFactory.getCurrentUser();
                                        u.setNewFocus(entity);
                                   //     Object f=Mpiro.win.struc.getEntityTypeOrEntity("IBM2000");
                        //                System.out.println("IBM2000"+f.toString());
                             //           NodeVector f1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("dfgdfdd");
                            //            System.out.println("dfgdfdd"+f1.toString());
                                      //  f1.nounVector=(Vector) f1.elementAt(2);
                             //           System.out.println(f1.nounVector.toString());
                                 //      Object f2=Mpiro.win.struc.getEntityTypeOrEntity("Computer_occur2");
                               //      System.out.println("Computer"+f2.toString());
                     ////                  Object f3=Mpiro.win.struc.getEntityTypeOrEntity("IBM2000_occur2");
                      //                System.out.println("IBM2000_occur2"+f3.toString());
                                        uk.ac.ed.ltg.exprimo.page.Page p = Mpiro.pageFactory.makePage();
                                        String currentText = new String();
                                    //    try{
 
                                        currentText = p.getRawText();
 
                                   //     }
                                  //      catch (NullPointerException x){}
                                        if (currentText == null) {
                                                previewText.append(Mpiro.pageFactory.noMoreInfo(Mpiro.pageFactory.getContextLanguage()));
                                        }
                                        else {
                                                previewText.append(currentText);
                                        }
                                        p.assimilateFacts();
                                        p.destroy();
 
                                        // create the previews
                    int textNum = 0;
                                        if (Mpiro.showAllPagesInPreviewsItem.isSelected()) {
                                                while (! (currentText == null ) && textNum < 2) {
                            textNum++;
                                                        p = Mpiro.pageFactory.makePage();
                                                        currentText = p.getRawText();
                                                        previewText.append("<br><br>" + "==============" + "<br><br>");
                                                        if (currentText == null) {
                                                                previewText.append(Mpiro.pageFactory.noMoreInfo(Mpiro.pageFactory.getContextLanguage()));
                                                        }
                                                        else {
                                                                previewText.append(currentText);
                                                        }
                                                        p.assimilateFacts();
                                                        p.destroy();
                                                }
                                        }
 
                                        if (Mpiro.resetInteractionHistoryBeforeEachPreviewItem.isSelected()) {
                                                ExportUtilsPEmulator.resetInteractionHistoryEmulatorHideProgress();
                                        }
 
                                        // reset the user directory to normal
                                        System.setProperty("user.dir", olduserdir);
 
                                        TreePreviews.entityPreview(entity, previewText.toString());
                                        //p.saveXML(solemldir.getName() + System.getProperty("file.separator") + entity + ".xml");
                                }
                                catch (Exception ex) {
                                        System.out.println("(DataBasePanel.createPreview) Exception ---  " + ex);
                                        ex.printStackTrace();
                                        // reset the user directory to normal
                                        System.setProperty("user.dir", olduserdir);
                                        TreePreviews.entityPreview(entity, errorInGeneratingPreview_ETC_text);
                                }
                        }
                };
                return action;
        }
 */
    
    
    
    
    Action createPreview(String usertype) {
        
        final String userTypeString = usertype;
        Action action = new AbstractAction(userTypeString) {
            public void actionPerformed(ActionEvent e) {
                
                // find the entity node-name
                String entity = Mpiro.win.struc.nameWithoutOccur(databaseTree.getLastSelectedPathComponent().toString());
                
                //    if (entity.substring(0, entity.length()-1).endsWith("_occur"))
                //      entity=entity.substring(0, entity.length()-7);
//Mpiro.win.struc.getEntityTypeOrEntity(entity)
/*                                String parent=last.getParent().toString();
                                if (parent.substring(0, parent.length()-1).endsWith("_occur"))
                                    parent=parent.substring(0, parent.length()-7);
NodeVector f11=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parent);
 
f11.nounVector=(Vector) f11.elementAt(2);*/
                // setting the exprimo home directory
                //		String olduserdir = System.getProperty("user.dir");
                //		System.setProperty("user.dir", olduserdir + System.getProperty("file.separator") + "exprimo");
                
                StringBuffer previewText = new StringBuffer();
                                /*
                                  try
                                  {
                                  Mpiro.pageFactory.setCurrentUser("auth-" + userTypeString);
                                  uk.ac.ed.ltg.exprimo.workbench.ExpUser u = Mpiro.pageFactory.getCurrentUser();
                                  u.setNewFocus(entity);
                                 
                                  uk.ac.ed.ltg.exprimo.page.Page p;
                                 */
                
                try {
                    File tempOwlFile= new File("OwlTemp.owl");
                    if (!Mpiro.win.menuBar.notAutomaticExportItem.isSelected()) { //maria
                        //if (Mpiro.needExportToEmulator && Mpiro.needExportToExprimo) { //maria
                        //	ExportDialog.doubleExport = true;
                        //} //maria
                        //if (Mpiro.needExportToEmulator) { //maria
                        //		Mpiro.pageFactory.setUseEmulator(true); //maria
                        //		Mpiro.pageFactory.initializeEmulator(); //maria
                        //		try { //maria
                        //			ExportUtilsPEmulator.exportToEmulator(); //maria
                        //		} //maria
                        //		catch (UMException ume) { //maria
                        //			System.out.println("(DataBasePanel:ExportUtilsEmulator.exportToEmulator)..." + ume); //maria
                        //			ume.printStackTrace(); //maria
                        //		} //maria
                        //		Mpiro.needExportToEmulator = false; //maria
                        //	} //maria
                        if (Mpiro.needExportToExprimo) {
                            ExportProgressMonitor progress = new ExportProgressMonitor(0, 10, 0); //maria
                            progress.exprimoOrEmulatorsLabel.setText("export to NLG"); //maria
                            progress.show(); //maria
                            progress.updateOKButton(false); //maria
                            progress.updateProgressBar(1, 1);
//maria
                                                /*	if (Mpiro.exportToChoiceItem.isSelected()) { //maria
                                                                //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.clear(); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("English"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Italian"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Greek"); //maria
                                                                Mpiro.restart = true;
                                                                //System.out.println("exportDir----"+exportDir);							//maria
                                                                new DirectoryChooser(exportDir, "EXPRIMO"); //maria
                                                                Mpiro.needExportToExprimo = false; //maria
                                                        } //maria
                                                        else { //Mpiro.exportToDefault.isSelected()										//maria
                                                                Mpiro.selectedLanguagesToExportArrayList.clear(); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("English"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Italian"); //maria
                                                                Mpiro.selectedLanguagesToExportArrayList.add("Greek"); //maria
                                                                Mpiro.restart = true; //maria
                                                                ArrayList languageList = (ArrayList) Mpiro.selectedLanguagesToExportArrayList.clone(); //maria
                                                                //File defaultExportDir = new File(System.getProperty("user.dir"));				//maria
                                                                //String dirPath = defaultExportDir.getAbsolutePath();
                                                                ExportUtilsWebinfo.createExportDirectories(Mpiro.jardir.getAbsolutePath()); //maria
                                                                String loadedDomain = new String(""); //maria
                                                                if (Mpiro.loadedDomain.endsWith(".mpiro")) { //maria
                                                                        loadedDomain = Mpiro.loadedDomain.substring(0, Mpiro.loadedDomain.length() - 6); //maria
                                                                } //maria
                                                                ExportUtilsExprimo exprimoExport = new ExportUtilsExprimo(loadedDomain, languageList); //maria
                                                                if (ExportDialog.doubleExport) { //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(1, 1); //maria
                                                                        int n = 2; //maria
                                                                        Iterator langIter = languageList.iterator(); //maria
                                                                        while (langIter.hasNext()) { //maria
                                                                                String language = langIter.next().toString(); //maria																		//maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting " + language + "  lexicon", 1); //maria
                                                                                exprimoExport.createLexicon(language); //maria																	//maria
                                                                                ExportUtilsPEmulator.progress.updateProgressBar(n, 1); //maria
                                                                                n++; //maria
                                                                        } //maria
                                                 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting types.xml", 1); //maria
                                                                        exprimoExport.createTypesGram(languageList); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(5, 1); //maria
                                                 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting predicates.xml", 1); //maria
                                                                        exprimoExport.createPredicatesGram(languageList); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(6, 1); //maria
                                                 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting instances.xml", 1); //maria
                                                                        exprimoExport.createInstancesGramAndMsgcat(languageList); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(7, 1); //maria
                                                 
                                                                        exprimoExport.saveToZip(languageList); //maria
                                                 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting imagesinfo.xml", 1); //maria
                                                                        ExportUtilsWebinfo.createImagesInfo(); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(8, 1); //maria
                                                 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1); //maria
                                                                        ExportUtilsWebinfo.createLanguageInfo(); //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(9, 1); //maria
                                                 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...", 1);	//maria
                                                                        if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English")) { //maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
                                                                                Mpiro.fireActionEnglishItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian")) { //maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
                                                                                Mpiro.fireActionItalianItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek")) { //maria
                                                                                //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Greek", 1);		//maria
                                                                                Mpiro.fireActionGreekItem(); //maria
                                                                        } //maria
                                                                        ExportUtilsPEmulator.progress.updateProgressBar(10, 1); //maria
                                                 
                                                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
                                                                        ExportUtilsPEmulator.progress.updateOKButton(true); //maria
                                                 
                                                                        ExportDialog.doubleExport = false; //maria
                                                                } //maria
                                                                else { //maria
                                                                        ExportProgressMonitor progress = new ExportProgressMonitor(0, 10, 0); //maria
                                                                        progress.exprimoOrEmulatorsLabel.setText(LangResources.getString(Mpiro.selectedLocale, "exportToExprimo_text")); //maria
                                                                        progress.show(); //maria
                                                                        progress.updateOKButton(false); //maria
                                                                        progress.updateProgressBar(1, 1); //maria
                                                 
                                                                        int n = 2; //maria
                                                                        Iterator langIter = languageList.iterator(); //maria
                                                                        while (langIter.hasNext()) { //maria
                                                                                String language = langIter.next().toString(); //maria
                                                                                //progress.updateProgressLabel("Exporting " + language + "  lexicon", 1);	//maria
                                                                                exprimoExport.createLexicon(language); //maria
                                                                                progress.updateProgressBar(n, 1); //maria
                                                                                n++; //maria
                                                                        } //maria
                                                 
                                                                        //progress.updateProgressLabel("Exporting types.xml", 1);			//maria
                                                                        exprimoExport.createTypesGram(languageList); //maria
                                                                        progress.updateProgressBar(5, 1); //maria
                                                 
                                                                        //progress.updateProgressLabel("Exporting predicates.xml", 1);	//maria
                                                                        exprimoExport.createPredicatesGram(languageList); //maria
                                                                        progress.updateProgressBar(6, 1); //maria
                                                 
                                                                        //progress.updateProgressLabel("Exporting instances.xml", 1);		//maria
                                                                        exprimoExport.createInstancesGramAndMsgcat(languageList); //maria
                                                                        progress.updateProgressBar(7, 1); //maria
                                                 
                                                                        exprimoExport.saveToZip(languageList); //maria
                                                 
                                                                        //progress.updateProgressLabel("Exporting imagesinfo.xml", 1);	//maria
                                                                        ExportUtilsWebinfo.createImagesInfo(); //maria
                                                                        progress.updateProgressBar(8, 1); //maria
                                                 
                                                                        //progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1); //maria
                                                                        ExportUtilsWebinfo.createLanguageInfo(); //maria
                                                                        progress.updateProgressBar(9, 1); //maria
                                                 
                                                                        //progress.updateProgressLabel("Restarting exprimo...", 1);		//maria
                                                 
                                                                        if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English")) { //maria
                                                                                //progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
                                                                                Mpiro.fireActionEnglishItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian")) { //maria
                                                                                //progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
                                                                                Mpiro.fireActionItalianItem(); //maria
                                                                        } //maria
                                                                        else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek")) { //maria
                                                                                //progress.updateProgressLabel("Restarting exprimo...Greek", 1);	//maria
                                                                                Mpiro.fireActionGreekItem(); //maria
                                                                        } //maria
                                                 
                                                                        progress.updateProgressBar(10, 1); //maria
                                                 
                                                                        //progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
                                                                        progress.updateOKButton(true); //maria
                                                                } //maria
                                                                Mpiro.needExportToExprimo = false; //maria
                                                        } //maria*/
                            
                            //     String hhhh=tempOwlFile.getAbsolutePath();
                            // String ggg=tempOwlFile.getAbsolutePath().substring(0,tempOwlFile.getAbsolutePath().indexOf(":\\"))+":\\\\OwlTemp.owl";
                            // // if(tempOwlFile.getAbsolutePath().contains(" ")){
                            //tempOwlFile=new File(tempOwlFile.getAbsolutePath().substring(0,tempOwlFile.getAbsolutePath().indexOf(":\\"))+":\\\\OwlTemp.owl");
                            //}
                            try{
                                //progress.updateProgressBar(2, 1);
                                OwlExport.ExportToOwlFile(tempOwlFile,"RDF/XML-ABBREV", Mpiro.win.struc.getBaseURI(),tempOwlFile.getName(),false);
                                progress.updateProgressBar(3, 1);
                                OwlExport.exportLexicon(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                                progress.updateProgressBar(4, 1);
                                OwlExport.exportMicroplans(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                                progress.updateProgressBar(5, 1);
                                OwlExport.exportRobotModelling(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                                progress.updateProgressBar(6, 1);
                                OwlExport.exportUserModelling(tempOwlFile.getAbsolutePath().substring(0, tempOwlFile.getAbsolutePath().lastIndexOf(tempOwlFile.getName())), Mpiro.win.struc.getBaseURI());
                                progress.updateProgressBar(8, 1);
                            } catch(java.lang.Exception ex){
                                ex.printStackTrace(System.out);
                            }
                            Mpiro.needExportToExprimo = false;
                            String owlPath = tempOwlFile.getAbsolutePath();
                            /*
                             *NLGEngine(String owlFilePath, String NLResourcesPath,  String lang, boolean useEmul, boolean load_DBases,
    MicroplansAndOrderingQueryManager MAOQMan, NLGLexiconQueryManager NLGLQMan, UserModellingQueryManager UMQMan, OntModel m,
    String navigation_IP,int navigation_port,
    String DBusername,String  DBpassword)*/
                            
                            String NLResourcePath = tempOwlFile.getAbsolutePath().substring(0,tempOwlFile.getAbsolutePath().length()-11);
                            myEngine =  new NLGEngine(owlPath, NLResourcePath,  Languages.ENGLISH, !pServerIsStarted, false, null, null, null, null, navIP, navPort, dbUser, dbPass, "127.0.0.1",1111);
                            myEngine.initStatisticalTree();
                            myEngine.initPServer();
                            progress.updateProgressBar(9, 1);
                          //  myEngineGreek =  new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, !pServerIsStarted, false, null, null, null, null, navIP, navPort, dbUser, dbPass, "127.0.0.1", 1111);
                         //   myEngineGreek.initStatisticalTree();
                         //   myEngineGreek.initPServer();
                            progress.updateProgressBar(10, 1);
                            progress.dispose();
                            /*String owlFilePath, String NLResourcesPath,  String lang, boolean useEmul, boolean load_DBases,
    MicroplansAndOrderingQueryManager MAOQMan, NLGLexiconQueryManager NLGLQMan, UserModellingQueryManager UMQMan, OntModel m,
    String navigation_IP,int navigation_port,
    String DBusername,String  DBpassword)*/
                        } //maria
                    } //maria
                    //	Mpiro.pageFactory.setCurrentUser("auth-" + userTypeString);
                    //	uk.ac.ed.ltg.exprimo.workbench.ExpUser u = Mpiro.pageFactory.getCurrentUser();
                    //	u.setNewFocus(entity);
                    //     Object f=Mpiro.win.struc.getEntityTypeOrEntity("IBM2000");
                    //                System.out.println("IBM2000"+f.toString());
                    //           NodeVector f1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("dfgdfdd");
                    //            System.out.println("dfgdfdd"+f1.toString());
                    //  f1.nounVector=(Vector) f1.elementAt(2);
                    //           System.out.println(f1.nounVector.toString());
                    //      Object f2=Mpiro.win.struc.getEntityTypeOrEntity("Computer_occur2");
                    //      System.out.println("Computer"+f2.toString());
                    ////                  Object f3=Mpiro.win.struc.getEntityTypeOrEntity("IBM2000_occur2");
                    //                System.out.println("IBM2000_occur2"+f3.toString());
                    //	uk.ac.ed.ltg.exprimo.page.Page p = Mpiro.pageFactory.makePage();
                    String currentText = new String();
                    
                    String UT = "http://www.aueb.gr/users/ion/owlnl/UserModelling#"+userTypeString;
                    String base=Mpiro.win.struc.getBaseURI();
                    if(!base.endsWith("#"))
                        base=base+"#";
                    String objectURI = base+entity;
  /*       String owlPath = "C:\\Documents and Settings\\dbilid\\desktop\\Final\\MPIRO-authoring-v4.4_with_OWL\\NLFiles-MPIRO\\mpiro.owl";
        String NLResourcePath = "C:\\Documents and Settings\\dbilid\\desktop\\Final\\MPIRO-authoring-v4.4_with_OWL\\NLFiles-MPIRO\\";
        String UT = "http://www.owlnl.com/NLG/UserModelling#Adult";
        String objectURI = "http://www.aueb.gr/users/ion/mpiro.owl#exhibit1";*/
                   // int type, String objectURI, String userType, String userID, int depth, int MFPS, boolean withComp
                    String result[] = new String[2];
                    if(Mpiro.resetInteractionHistory||Mpiro.resetInteractionHistoryBeforeEachPreview)
                        
                    { 
                       myEngine.initStatisticalTree();
                           myEngine.initPServer();
                        UMVisit umv=myEngine.getUMVisit();
                      //  myEngineEnglish.initStatisticalTree();
                      //      myEngineEnglish.initPServer();
                     //   UMVisit umvEng=myEngineEnglish.getUMVisit();
        try {
           ///  myEngineEnglish.initStatisticalTree();
                          //  myEngineEnglish.initPServer();
                           // progress.updateProgressBar(9, 1);
                         //   myEngineGreek =  new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, false, false, null, null, null, null, "", 0, "root", new String(pass.getPassword()));
                        //    myEngineGreek.initStatisticalTree();
                        ///    myEngineGreek.initPServer();
            Mpiro.resetInteractionHistory=false;
            Random generator=new Random();
        userID=String.valueOf(generator.nextInt(100000000));
            umv.newUser(userID , UT);
           // umvEng.newUser(userID , UT);
            
        ///    myEngineEnglish.initStatisticalTree();
             //               myEngineEnglish.initPServer();
                           // progress.updateProgressBar(9, 1);
                         //   myEngineGreek =  new NLGEngine(owlPath, NLResourcePath,  Languages.GREEK, false, false, null, null, null, null, "", 0, "root", new String(pass.getPassword()));
                     //       myEngineGreek.initStatisticalTree();
                     //       myEngineGreek.initPServer();
        } catch (gr.demokritos.iit.PServer.UMException ex) {
            ex.printStackTrace();
        }}
                   // String base= "";
                    System.out.println(UT);
                    if( Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek"))
                        myEngine.setLanguage("el");
                        else
                            myEngine.setLanguage("en");
                    
                        result = myEngine.GenerateDescription(0, objectURI, UT, userID, Integer.parseInt(depth.getSelectedItem().toString()), -1, useComparisons.isSelected(), "robot1");
                  //  else
                   //     result = myEngineEnglish.GenerateDescription(0, objectURI, UT, userID, Integer.parseInt(depth.getSelectedItem().toString()), -1, useComparisons.isSelected(), "robot1");
                    
               //     result[1]=result[1];
               //     result[1]=result[1];
                    System.out.println("result0"+result[0]);
                    System.out.println("result1"+result[1]);
                    System.out.println("Text Generated For User ----> "+userID);
                    previewText.append(result[1]);
                   
                    //    try{
                    
                    //	currentText = p.getRawText();
                    
                    //     }
                    //      catch (NullPointerException x){}
                    //	if (currentText == null) {
                    //		previewText.append(Mpiro.pageFactory.noMoreInfo(Mpiro.pageFactory.getContextLanguage()));
                    //	}
                    //	else {
                    //		previewText.append(currentText);
                    //	}
                    //	p.assimilateFacts();
                    //	p.destroy();
                    
                    // create the previews
                    //      int textNum = 0;
                    //			if (Mpiro.showAllPagesInPreviewsItem.isSelected()) {
                    //				while (! (currentText == null ) && textNum < 2) {
                    //          textNum++;
                    //					p = Mpiro.pageFactory.makePage();
                    //					currentText = p.getRawText();
                    //					previewText.append("<br><br>" + "==============" + "<br><br>");
                    //					if (currentText == null) {
                    //						previewText.append(Mpiro.pageFactory.noMoreInfo(Mpiro.pageFactory.getContextLanguage()));
                    //					}
                    //					else {
                    //						previewText.append(currentText);
                    //					}
                    //					p.assimilateFacts();
                    //					p.destroy();
                    //				}
                    //			}
                    
                    //			if (Mpiro.resetInteractionHistoryBeforeEachPreviewItem.isSelected()) {
                    //			ExportUtilsPEmulator.resetInteractionHistoryEmulatorHideProgress();
                    //		}
                    
                    // reset the user directory to normal
                    //		System.setProperty("user.dir", olduserdir);
                    
                    TreePreviews.entityPreview(entity, previewText.toString());
                    //p.saveXML(solemldir.getName() + System.getProperty("file.separator") + entity + ".xml");
                } catch (Exception ex) {
                    System.out.println("(DataBasePanel.createPreview) Exception ---  " + ex);
                    ex.printStackTrace();
                    // reset the user directory to normal
                    //	System.setProperty("user.dir", olduserdir);
                    TreePreviews.entityPreview(entity, errorInGeneratingPreview_ETC_text);
                }
            }
        };
        return action;
    }
    
    
    
    
    
    
/*	Action createPreview(String usertype,final StringBuffer previewText,final int num) {
                final String userTypeString = usertype;
      //  StringBuffer aaa= new StringBuffer();
       // if (previewText==null) previewText=aaa;
 
        //StringBuffer
         //String entity = databaseTree.getLastSelectedPathComponent().toString();
       // //System.out.println("&&&&&&"+usertype+entity+previewText);
                Action action = new AbstractAction(userTypeString) {
            public void actionPerformed(ActionEvent e) {
                        trrr (userTypeString, previewText, num);
            }};
       //createPreview(usertype,previewText,"1");
        // if (num=="")createPreview(usertype,previewText,"1");
                return action;
        }
 
public void trrr(String userTypeString,StringBuffer previewText,int num){
   if (num==1) previewText.delete(0,previewText.length());
                // find the entity node-name
               // StringBuffer previewText=new StringBuffer();
               String entity = databaseTree.getLastSelectedPathComponent().toString();
                if (entity.substring(0,entity.length()-1).endsWith("occur"))
              entity=entity.substring(0,entity.length()-7) ;
        if (num!=1)  entity=entity+"_occur"+String.valueOf(num);
  //     String test1=  databaseTree.getNextMatch(entity+"2",0,null).getLastPathComponent().toString();
  //     String test2=  databaseTree.getNextMatch(entity+"3",0,null).getLastPathComponent().toString();
  //     String test3=  databaseTree.getNextMatch(entity+"4",0,null).getLastPathComponent().toString();
 
 //      //System.out.println(test1+test2+test3);
////System.out.println("**************test1"+previewText+"dlete:"+previewText.delete(0,previewText.length()));
                // setting the exprimo home directory
                String olduserdir = System.getProperty("user.dir");
                System.setProperty("user.dir", olduserdir + System.getProperty("file.separator") + "exprimo");
 
 
 
      //
     //             try
    //              {
    //              Mpiro.pageFactory.setCurrentUser("auth-" + userTypeString);
    //              uk.ac.ed.ltg.exprimo.workbench.ExpUser u = Mpiro.pageFactory.getCurrentUser();
   //               u.setNewFocus(entity);
 
    //              uk.ac.ed.ltg.exprimo.page.Page p;
    //
 
                try {
                    if (!Mpiro.notAutomaticExportItem.isSelected()) { //maria
                        if (Mpiro.needExportToEmulator && Mpiro.needExportToExprimo) { //maria
                            ExportDialog.doubleExport = true;
                        } //maria
                        if (Mpiro.needExportToEmulator) { //maria
                            Mpiro.pageFactory.setUseEmulator(true); //maria
                            Mpiro.pageFactory.initializeEmulator(); //maria
                            try { //maria
                                ExportUtilsPEmulator.exportToEmulator(); //maria
                            } //maria
                            catch (UMException ume) { //maria
                                //System.out.println("(DataBasePanel:ExportUtilsEmulator.exportToEmulator)..." + ume); //maria
                                ume.printStackTrace(); //maria
                            } //maria
                            Mpiro.needExportToEmulator = false; //maria
                        } //maria
                        if (Mpiro.needExportToExprimo) { //maria
                            if (Mpiro.exportToChoiceItem.isSelected()) { //maria
                                //maria
                                Mpiro.selectedLanguagesToExportArrayList.clear(); //maria
                                Mpiro.selectedLanguagesToExportArrayList.add("English"); //maria
                                Mpiro.selectedLanguagesToExportArrayList.add("Italian"); //maria
                                Mpiro.selectedLanguagesToExportArrayList.add("Greek"); //maria
                                Mpiro.restart = true;
                                ////System.out.println("exportDir----"+exportDir);							//maria
                                new DirectoryChooser(exportDir, "EXPRIMO"); //maria
                                Mpiro.needExportToExprimo = false; //maria
                            } //maria
                            else { //Mpiro.exportToDefault.isSelected()										//maria
                                Mpiro.selectedLanguagesToExportArrayList.clear(); //maria
                                Mpiro.selectedLanguagesToExportArrayList.add("English"); //maria
                                Mpiro.selectedLanguagesToExportArrayList.add("Italian"); //maria
                                Mpiro.selectedLanguagesToExportArrayList.add("Greek"); //maria
                                Mpiro.restart = true; //maria
                                ArrayList languageList = (ArrayList) Mpiro.selectedLanguagesToExportArrayList.clone(); //maria
                                //File defaultExportDir = new File(System.getProperty("user.dir"));				//maria
                                //String dirPath = defaultExportDir.getAbsolutePath();
                                ExportUtilsWebinfo.createExportDirectories(Mpiro.jardir.getAbsolutePath()); //maria
                                String loadedDomain = new String(""); //maria
                                if (Mpiro.loadedDomain.endsWith(".mpiro")) { //maria
                                    loadedDomain = Mpiro.loadedDomain.substring(0, Mpiro.loadedDomain.length() - 6); //maria
                                } //maria
                                ExportUtilsExprimo exprimoExport = new ExportUtilsExprimo(loadedDomain, languageList); //maria
                                if (ExportDialog.doubleExport) { //maria
                                    ExportUtilsPEmulator.progress.updateProgressBar(1, 1); //maria
                                    int n = 2; //maria
                                    Iterator langIter = languageList.iterator(); //maria
                                    while (langIter.hasNext()) { //maria
                                        String language = langIter.next().toString(); //maria																		//maria
                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting " + language + "  lexicon", 1); //maria
                                        exprimoExport.createLexicon(language); //maria																	//maria
                                        ExportUtilsPEmulator.progress.updateProgressBar(n, 1); //maria
                                        n++; //maria
                                    } //maria
 
                                    //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting types.xml", 1); //maria
                                    exprimoExport.createTypesGram(languageList); //maria
                                    ExportUtilsPEmulator.progress.updateProgressBar(5, 1); //maria
 
                                    //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting predicates.xml", 1); //maria
                                    exprimoExport.createPredicatesGram(languageList); //maria
                                    ExportUtilsPEmulator.progress.updateProgressBar(6, 1); //maria
 
                                    //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting instances.xml", 1); //maria
                                    exprimoExport.createInstancesGramAndMsgcat(languageList); //maria
                                    ExportUtilsPEmulator.progress.updateProgressBar(7, 1); //maria
 
                                    exprimoExport.saveToZip(languageList); //maria
 
                                    //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting imagesinfo.xml", 1); //maria
                                    ExportUtilsWebinfo.createImagesInfo(); //maria
                                    ExportUtilsPEmulator.progress.updateProgressBar(8, 1); //maria
 
                                    //ExportUtilsPEmulator.progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1); //maria
                                    ExportUtilsWebinfo.createLanguageInfo(); //maria
                                    ExportUtilsPEmulator.progress.updateProgressBar(9, 1); //maria
 
                                    //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...", 1);	//maria
                                    if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English")) { //maria
                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
                                        Mpiro.fireActionEnglishItem(); //maria
                                    } //maria
                                    else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian")) { //maria
                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
                                        Mpiro.fireActionItalianItem(); //maria
                                    } //maria
                                    else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek")) { //maria
                                        //ExportUtilsPEmulator.progress.updateProgressLabel("Restarting exprimo...Greek", 1);		//maria
                                        Mpiro.fireActionGreekItem(); //maria
                                    } //maria
                                    ExportUtilsPEmulator.progress.updateProgressBar(10, 1); //maria
 
                                    //ExportUtilsPEmulator.progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
                                    ExportUtilsPEmulator.progress.updateOKButton(true); //maria
 
                                    ExportDialog.doubleExport = false; //maria
                                } //maria
                                else { //maria
                                    ExportProgressMonitor progress = new ExportProgressMonitor(0, 10, 0); //maria
                                    progress.exprimoOrEmulatorsLabel.setText(LangResources.getString(Mpiro.selectedLocale, "exportToExprimo_text")); //maria
                                    progress.show(); //maria
                                    progress.updateOKButton(false); //maria
                                    progress.updateProgressBar(1, 1); //maria
 
                                    int n = 2; //maria
                                    Iterator langIter = languageList.iterator(); //maria
                                    while (langIter.hasNext()) { //maria
                                        String language = langIter.next().toString(); //maria
                                        //progress.updateProgressLabel("Exporting " + language + "  lexicon", 1);	//maria
                                        exprimoExport.createLexicon(language); //maria
                                        progress.updateProgressBar(n, 1); //maria
                                        n++; //maria
                                    } //maria
 
                                    //progress.updateProgressLabel("Exporting types.xml", 1);			//maria
                                    exprimoExport.createTypesGram(languageList); //maria
                                    progress.updateProgressBar(5, 1); //maria
 
                                    //progress.updateProgressLabel("Exporting predicates.xml", 1);	//maria
                                    exprimoExport.createPredicatesGram(languageList); //maria
                                    progress.updateProgressBar(6, 1); //maria
 
                                    //progress.updateProgressLabel("Exporting instances.xml", 1);		//maria
                                    exprimoExport.createInstancesGramAndMsgcat(languageList); //maria
                                    progress.updateProgressBar(7, 1); //maria
 
                                    exprimoExport.saveToZip(languageList); //maria
 
                                    //progress.updateProgressLabel("Exporting imagesinfo.xml", 1);	//maria
                                    ExportUtilsWebinfo.createImagesInfo(); //maria
                                    progress.updateProgressBar(8, 1); //maria
 
                                    //progress.updateProgressLabel("Exporting englishinfo.xml, italianinfo.xml, greekinfo.xml", 1); //maria
                                    ExportUtilsWebinfo.createLanguageInfo(); //maria
                                    progress.updateProgressBar(9, 1); //maria
 
                                    //progress.updateProgressLabel("Restarting exprimo...", 1);		//maria
 
                                    if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("English")) { //maria
                                        //progress.updateProgressLabel("Restarting exprimo...English", 1);	//maria
                                        Mpiro.fireActionEnglishItem(); //maria
                                    } //maria
                                    else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Italian")) { //maria
                                        //progress.updateProgressLabel("Restarting exprimo...Italian", 1);	//maria
                                        Mpiro.fireActionItalianItem(); //maria
                                    } //maria
                                    else if (Mpiro.activatedPreviewLanguage.equalsIgnoreCase("Greek")) { //maria
                                        //progress.updateProgressLabel("Restarting exprimo...Greek", 1);	//maria
                                        Mpiro.fireActionGreekItem(); //maria
                                    } //maria
 
                                    progress.updateProgressBar(10, 1); //maria
 
                                    //progress.updateProgressLabel("Export finished succesfully.", 1);	//maria
                                    progress.updateOKButton(true); //maria
                                } //maria
                                Mpiro.needExportToExprimo = false; //maria
                            } //maria
                        } //maria
                    } //maria
                    Mpiro.pageFactory.setCurrentUser("auth-" + userTypeString);
                    uk.ac.ed.ltg.exprimo.workbench.ExpUser u = Mpiro.pageFactory.getCurrentUser();
                    u.setNewFocus(entity);
 
                    uk.ac.ed.ltg.exprimo.page.Page p = Mpiro.pageFactory.makePage();
                    String currentText = new String();
                    currentText = p.getRawText();
                    if (currentText == null) {
                        previewText.append(Mpiro.pageFactory.noMoreInfo(Mpiro.pageFactory.getContextLanguage()));
                    }
                    else {
                        previewText.append(currentText);
                    }
                    p.assimilateFacts();
                    p.destroy();
 
                    // create the previews
                    int textNum = 0;
                    if (Mpiro.showAllPagesInPreviewsItem.isSelected()) {
                        while (! (currentText == null ) && textNum < 2) {
                            textNum++;
                            p = Mpiro.pageFactory.makePage();
                            currentText = p.getRawText();
                            previewText.append("<br><br>" + "==============" + "<br><br>");
                            if (currentText == null) {
                                previewText.append(Mpiro.pageFactory.noMoreInfo(Mpiro.pageFactory.getContextLanguage()));
                            }
                            else {
                                previewText.append(currentText);
                            }
                            p.assimilateFacts();
                            p.destroy();
                        }
                    }
 
                    if (Mpiro.resetInteractionHistoryBeforeEachPreviewItem.isSelected()) {
                        ExportUtilsPEmulator.resetInteractionHistoryEmulatorHideProgress();
                    }
 
                    // reset the user directory to normal
                    System.setProperty("user.dir", olduserdir);
 
                    TreePreviews.entityPreview(entity, previewText.toString());
                    //p.saveXML(solemldir.getName() + System.getProperty("file.separator") + entity + ".xml");
                }
                catch (Exception ex) {
                    //System.out.println("(DataBasePanel.createPreview) Exception ---  " + ex);
                    ex.printStackTrace();
                    // reset the user directory to normal
                    System.setProperty("user.dir", olduserdir);
                    TreePreviews.entityPreview(entity, errorInGeneratingPreview_ETC_text);
                }
         //   int g;
         //  if (num!="")   g = (int) (num) +1;
      if (databaseTree.getNextMatch(entity+"_occur"+String.valueOf(num+1),0,null).getLastPathComponent().toString()!=null)
          trrr(userTypeString, previewText,num+1);
 
}
        /**
 * The method that adds the popups
 */
    public void addPopups() {
        popuptopA = new JPopupMenu();
        popuptopA.add(a1);
        
        popupbasic = new JPopupMenu();
        popupbasic.add(a2);
        popupbasic.add(a3);
        popupbasic.add(test1);
        // popupbasic.add(a9);
        popupbasic.add(a4);
        popupbasic.add(equiv);
        popupbasic.add(annotation);
        popupbasic.add(robotsClass);
        popupbasic.add(robotsChar);
        popupbasic.add(superTypes);
        popupbasic.add(a8);//kallonis
        popupbasic.addSeparator();
        popupbasic.add(a6);
        popupbasic.add(a7);
        popupbasic.addSeparator();
        popupbasic.add(a5);
        
        
        
        popupsub = new JPopupMenu();
        popupsub.add(a2);
        popupsub.add(a3);
        popupsub.add(test1);
        //   popupsub.add(a9);
        popupsub.add(a4);
        popupsub.add(equiv);
        popupsub.add(annotation);
        popupsub.add(robotsClass);
    popupsub.add(robotsChar);
        popupsub.add(superTypes);
        popupsub.add(a8);//kallonis
        popupsub.addSeparator();
        popupsub.add(a6);
        popupsub.add(a7);
        
                                /*
                 popupentity = new JPopupMenu();
                 popupentity.add(a6);
                 popupentity.add(a7);
                 popupentity.addSeparator();
                 popupentity.add(a8);
                 popupentity.add(a9);
                 popupentity.add(a10);
                                 
                 popupgeneric = new JPopupMenu();
                 popupgeneric.add(a7);
                 popupgeneric.addSeparator();
                 popupgeneric.add(a8);
                 popupgeneric.add(a9);
                 popupgeneric.add(a10);
                                 */
        
        popupentity = new JPopupMenu();
        popupentity.add(a6);
        popupentity.add(a7);
        popupentity.add(annotation);
        popupentity.add(robotsInd);
        popupentity.add(robotsChar);
        //popupentity.addSeparator();  //theofilos
        //popupentity.add(previewEnglishEntityMenu);  //theofilos
        //popupentity.add(previewItalianEntityMenu);  //theofilos
        //popupentity.add(previewGreekEntityMenu);  //theofilos
        
        popupgeneric = new JPopupMenu();
        popupgeneric.add(a7);
        //popupgeneric.addSeparator();  //theofilos
        //popupgeneric.add(previewEnglishGenericMenu);  //theofilos
        //popupgeneric.add(previewItalianGenericMenu);  //theofilos
        //popupgeneric.add(previewGreekGenericMenu);  //theofilos
        
        databaseTree.add(popuptopA);
        databaseTree.add(popupbasic);
        databaseTree.add(popupsub);
        databaseTree.add(popupentity);
        databaseTree.add(popupgeneric);
        
    }
    
        /* The following methods are functions defined
         * for the databaseTree. In order of appearance:
         * 1. clearTree()
         * 2. removeCurrentNode()
         * 3. renameCurrentNode()
         */
    public static void clearTree() {
        DefaultMutableTreeNode firstChild = (DefaultMutableTreeNode) top.getFirstChild();
        firstChild.removeAllChildren();
        treeModel.reload();
        databaseTree.repaint();
    }
    
    public void removeNode() {
        
        
        
        
        boolean exists=false;
        String nameWithoutOccur=Mpiro.win.struc.nameWithoutOccur(DataBasePanel.last.toString());
        //        if (DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-1).endsWith("_occur") ){
        //          nameWithoutOccur=DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-7);
        //    }
        Object elem1=new Object();
        //hasMore=false;
        DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
        Enumeration lastTypeChildren=first.preorderEnumeration();
        do{
            //System.out.println("4");
            elem1=lastTypeChildren.nextElement();
            if (elem1.toString().equalsIgnoreCase(nameWithoutOccur+"_occur2")) {
                //System.out.println("5");
                exists=true;
                break;
            }
            
        }while(lastTypeChildren.hasMoreElements());
        if (!exists) {//System.out.println("+++++"+last.getUserObject().toString());
            removeCurrentNode();
        } else {
            if(im == ICON_GEI){
                boolean hasMore=true;
                for(int r=2;hasMore;r++) { //System.out.println("3");
                    Object elem2=null;
                    hasMore=false;
                    DefaultMutableTreeNode first1 = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                    Enumeration lastTypeChildren1=first1.preorderEnumeration();
                    do{
                        //System.out.println("4");
                        elem2=lastTypeChildren1.nextElement();
                        if (elem2.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(r))) {
                            hasMore=true; //System.out.println("5");
                            removeNode(elem2.toString());
                            break;
                        }
                        
                    }while(lastTypeChildren1.hasMoreElements());
                    // if (e)
                }
                removeNode(nameWithoutOccur);
                
            } else 
                if(getNode(Mpiro.win.struc.nameWithoutOccur(DataBasePanel.last.getParent().toString())+"_occur2")!=null){
                    boolean hasMore=true;
                    for(int r=2;hasMore;r++) { //System.out.println("3");
                        Object elem2=null;
                        hasMore=false;
                        DefaultMutableTreeNode first1 = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                        Enumeration lastTypeChildren1=first1.preorderEnumeration();
                        do{
                            //System.out.println("4");
                            elem2=lastTypeChildren1.nextElement();
                            if (elem2.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(r))) {
                                hasMore=true; //System.out.println("5");
                                removeNode(elem2.toString());
                                break;
                            }
                            
                        }while(lastTypeChildren1.hasMoreElements());
                        // if (e)
                    }
                    removeNode(nameWithoutOccur);
                } else{
                    Object[] optionButtons = {
                        "all",
                        "only_this",
                        "cancel"};
                    
                    int j = JOptionPane.showOptionDialog(DataBasePanel.this, //theofilos
                            "delete all occurrences of"
                            + "\n" + "\"" + nameWithoutOccur + "\" ?", //theofilos
                            LangResources.getString(Mpiro.selectedLocale, "warning_dialog"), //theofilos
                            JOptionPane.WARNING_MESSAGE, //theofilos
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            null, //theofilos
                            optionButtons, //theofilos
                            optionButtons[1]); //theofilos
                    
                    
                    if (j == 1) {
                        
                        
                        
                        
                        deleteOccur(nameWithoutOccur, last.getParent().toString());
                        
                        
                        // Enumeration temp=QueryHashtable.mainDBHashtable.keys();
                        //            System.out.println("...");
                        
                    }
                    
                    if (j==0){
                        boolean hasMore=true;
                        for(int r=2;hasMore;r++) { //System.out.println("3");
                            Object elem2=null;
                            hasMore=false;
                            DefaultMutableTreeNode first1 = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                            Enumeration lastTypeChildren1=first1.preorderEnumeration();
                            do{
                                //System.out.println("4");
                                elem2=lastTypeChildren1.nextElement();
                                if (elem2.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(r))) {
                                    hasMore=true; //System.out.println("5");
                                    removeNode(elem2.toString());
                                    break;
                                }
                                
                            }while(lastTypeChildren1.hasMoreElements());
                            // if (e)
                        }
                        removeNode(nameWithoutOccur);
                    }
                }}
        }
        
        public void removeNode(String nodeName){
            Vector removedNodes = new Vector();
            
            // Continuing with remaining node and its children
            TreePath currentSelection = databaseTree.getNextMatch(nodeName,0, Position.Bias.Forward);
            if (currentSelection != null) {
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
                
                // Get selected node's children names and remove also
                // their entries from mainDBHashtable
                Enumeration enumer = currentNode.preorderEnumeration();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) enumer.nextElement();
                    if (child.isNodeSibling(DataBasePanel.last)) {
                        //System.err.println("DUPLICATION EXCEPTION: " +
                        //"\"" + child + "\"" + " is sibling!");
                    } else {
                        String c = child.toString();
                        if (!Mpiro.win.struc.mainDBcontainsEntityOrEntityType(c)) {
                            System.err.println("(DataBasePanel)ERROR: the specified key: " + "\"" + c + "\"" +
                                    " does not exist!");
                        } else {
                            ////System.out.println(c);
                            Mpiro.win.struc.removeEntityTypeOrEntityFromDB(c);
                            removedNodes.addElement(child);
                        }
                    }
                }
                removedNodes.addElement(currentNode);
                // Break up the deletedNodes vector into three Vectors containing
                // the deleted Entity-types, Entities, and Generic-entities respectively
                Vector deletedEntityTypes = new Vector();
                Vector deletedEntities = new Vector();
                
                Enumeration removedNodesVectorEnum = removedNodes.elements();
                while (removedNodesVectorEnum.hasMoreElements()) {
                    DefaultMutableTreeNode removedNode = (DefaultMutableTreeNode) removedNodesVectorEnum.nextElement();
                    Object obj = (Object) (removedNode.getUserObject());
                    IconData id = (IconData) obj;
                    Icon ii = id.getIcon();
                    ImageIcon im = (ImageIcon) ii;
                    
                    if (im == DataBasePanel.ICON_BOOK || im == DataBasePanel.ICON_BASIC) {
                        deletedEntityTypes.addElement(removedNode);
                    } else if (im == DataBasePanel.ICON_GEI || im == DataBasePanel.ICON_GENERIC) {
                        deletedEntities.addElement(removedNode);
                    } else {
                        //System.out.println("(DataBasePanel.removeCurrentNode)---- ERROR!!!!");
                    }
                }
                ////System.out.println("(DataBasePanel.removeCurrentNode)-deletedNodes--- " + removedNodes);
                ////System.out.println("(DataBasePanel.removeCurrentNode)-deletedEntityTypes--- " + deletedEntityTypes);
                ////System.out.println("(DataBasePanel.removeCurrentNode)-deletedEntities--- " + deletedEntities);
                
                
                
                // Check if it's the root
                MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
                if (parent != null) {
                    currentNode.removeAllChildren();
                    treeModel.removeNodeFromParent(currentNode);
                    // Remove appropriate entry from mainDBHashtable
                    Mpiro.win.struc.removeEntityTypeOrEntityFromDB(currentNode.toString());
                    
                    // Set the Database Tree to "Data Base"
                    TreePreviews.setDataBaseTable("Data Base");
                    DataBasePanel.databaseTree.setSelectionPath(new TreePath(top));
                    last = (DefaultMutableTreeNode) (DataBasePanel.databaseTree.getLastSelectedPathComponent());
                    DataBasePanel.databaseTree.revalidate();
                    DataBasePanel.databaseTree.repaint();
                    // Remove all references of deleted nodes from remaining nodes' fields
                    if (!removedNodes.isEmpty()) {
                        Mpiro.win.struc.updateExistingFieldsAfterRemovingANode(deletedEntityTypes, deletedEntities);
                        // remove all instances of the deleted nodes from mainUserModelHashtable
                        Enumeration removedNodesEnum = removedNodes.elements();
                        while (removedNodesEnum.hasMoreElements()) {
                            String nodename = removedNodesEnum.nextElement().toString();
                            Mpiro.win.struc.removeEntityTypeOrEntityInUserModelHashtable(nodename);
                           // QueryProfileHashtable.removeEntityTypeOrEntityInRobotsModelHashtable(nodename);
                           // QueryProfileHashtable.removeEntityTypeOrEntityInUserModelStoryHashtable(nodename);
                        }
                    }
                    return;
                }
                
            }
        }
        
        public static void removeCurrentNode() {
            // Setting up two hashtables to be used in "QueryHashtable.updateExistingFieldsAfterRemovingANode"
            //QueryHashtable.allEntityTypesBeforeRemove = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
            //QueryHashtable.allEntitiesAndGenericBeforeRemove = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity+Generic");
            
            // Before removing a node we construct a vector containing all nodes that are deleted
            // This is used for updating the information about those nodes in the field of other nodes
            Vector removedNodes = new Vector();
            
            // Continuing with remaining node and its children
            TreePath currentSelection = databaseTree.getSelectionPath();
            if (currentSelection != null) {
                DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
                
                // Get selected node's children names and remove also
                // their entries from mainDBHashtable
                Enumeration enumer = currentNode.preorderEnumeration();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) enumer.nextElement();
                    if (child.isNodeSibling(DataBasePanel.last)) {
                        //System.err.println("DUPLICATION EXCEPTION: " +
                        //"\"" + child + "\"" + " is sibling!");
                    } else {
                        String c = child.toString();
                        if (!Mpiro.win.struc.mainDBcontainsEntityOrEntityType(c)) {
                            System.err.println("(DataBasePanel)ERROR: the specified key: " + "\"" + c + "\"" +
                                    " does not exist!");
                        } else {
                            ////System.out.println(c);
                            Mpiro.win.struc.removeEntityTypeOrEntityFromDB(c);
                            removedNodes.addElement(child);
                        }
                    }
                }
                removedNodes.addElement(currentNode);
                // Break up the deletedNodes vector into three Vectors containing
                // the deleted Entity-types, Entities, and Generic-entities respectively
                Vector deletedEntityTypes = new Vector();
                Vector deletedEntities = new Vector();
                
                Enumeration removedNodesVectorEnum = removedNodes.elements();
                while (removedNodesVectorEnum.hasMoreElements()) {
                    DefaultMutableTreeNode removedNode = (DefaultMutableTreeNode) removedNodesVectorEnum.nextElement();
                    Object obj = (Object) (removedNode.getUserObject());
                    IconData id = (IconData) obj;
                    Icon ii = id.getIcon();
                    ImageIcon im = (ImageIcon) ii;
                    
                    if (im == DataBasePanel.ICON_BOOK || im == DataBasePanel.ICON_BASIC) {
                        deletedEntityTypes.addElement(removedNode);
                    } else if (im == DataBasePanel.ICON_GEI || im == DataBasePanel.ICON_GENERIC) {
                        deletedEntities.addElement(removedNode);
                    } else {
                        //System.out.println("(DataBasePanel.removeCurrentNode)---- ERROR!!!!");
                    }
                }
                ////System.out.println("(DataBasePanel.removeCurrentNode)-deletedNodes--- " + removedNodes);
                ////System.out.println("(DataBasePanel.removeCurrentNode)-deletedEntityTypes--- " + deletedEntityTypes);
                ////System.out.println("(DataBasePanel.removeCurrentNode)-deletedEntities--- " + deletedEntities);
                
                
                
                // Check if it's the root
                MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
                if (parent != null) {
                    currentNode.removeAllChildren();
                    treeModel.removeNodeFromParent(currentNode);
                    // Remove appropriate entry from mainDBHashtable
                    Mpiro.win.struc.removeEntityTypeOrEntityFromDB(currentNode.toString());
                    
                    // Set the Database Tree to "Data Base"
                    TreePreviews.setDataBaseTable("Data Base");
                    DataBasePanel.databaseTree.setSelectionPath(new TreePath(top));
                    last = (DefaultMutableTreeNode) (DataBasePanel.databaseTree.getLastSelectedPathComponent());
                    DataBasePanel.databaseTree.revalidate();
                    DataBasePanel.databaseTree.repaint();
                    // Remove all references of deleted nodes from remaining nodes' fields
                    if (!removedNodes.isEmpty()) {
                        Mpiro.win.struc.updateExistingFieldsAfterRemovingANode(deletedEntityTypes, deletedEntities);
                        // remove all instances of the deleted nodes from mainUserModelHashtable
                        Enumeration removedNodesEnum = removedNodes.elements();
                        while (removedNodesEnum.hasMoreElements()) {
                            String nodename = removedNodesEnum.nextElement().toString();
                            Mpiro.win.struc.removeEntityTypeOrEntityInUserModelHashtable(nodename);
                           // QueryProfileHashtable.removeEntityTypeOrEntityInRobotsModelHashtable(nodename);
                           // QueryProfileHashtable.removeEntityTypeOrEntityInUserModelStoryHashtable(nodename);
                        }
                    }
                    return;
                }
            }
            // Either there was no selection, or the root was selected.
            //toolkit.beep();
        }
        
        public void renameCurrentNode() {
            KDialog renameDialog = new KDialog(renameNode_text, newName_text, "", new Vector(), false, "RENAME", true);
        }
        
        /* Add child to the currently selected node. */
        public static DefaultMutableTreeNode addObject(Object child) {
            DefaultMutableTreeNode parentNode=null;
            TreePath parentPath = databaseTree.getSelectionPath();
            
            if (parentPath == null) {
                parentNode = top;
            } else {
                parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
            }
            
            return addObject(parentNode, child, true);
        }
        
        public static DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                Object child) {
            return addObject(parent, child, false);
        }
        
        public static DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                Object child,
                boolean shouldBeVisible) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
            
            if (parent == null) {
                parent = top;
            }
            
            treeModel.insertNodeInto(childNode, parent, parent.getChildCount());
            
            // Make sure the user can see the new node.
            if (shouldBeVisible) {
                databaseTree.scrollPathToVisible(new TreePath(childNode.getPath()));
            }
            return childNode;
        }
        
        /**
         *  The method to create the nodes
         *  @param roota The root node
         *  @param ko The int with possible values: "1" for Basic-entity-types ONLY, "2" for Basic-entity-types AND Data-types
         */
        public static void createNodes(DefaultMutableTreeNode roota, int ko) {
            // A) Basic & Built-in types
            String[] basictype = {
                "Exhibit", "Historical-period", "Place", "Technique", "Style", "Material", "Person"};
            String[] builtintype = {
                "String", "Number", "Date", "Dimension"};
            // In the same order:
            // A) Basic & Built-ins
            DefaultMutableTreeNode[] basicnode = null;
            DefaultMutableTreeNode[] builtinnode = null;
            topA = new DefaultMutableTreeNode(new IconData(ICON_TOP_A, "Basic-entity-types"));
            topB = new DefaultMutableTreeNode(new IconData(ICON_TOP_B, "Data-types"));
            
            if (ko == 1) {
                roota.add(topA);
                roota.add(topB);
            } else if (ko == 2) {
                roota.add(topA);
            }
            
            builtinnode = new DefaultMutableTreeNode[builtintype.length];
            for (int a = 0; a < builtintype.length; a++) {
                builtinnode[a] = new DefaultMutableTreeNode(new IconData(ICON_BUILT, builtintype[a]));
                topB.add(builtinnode[a]);
            }
            if(Mpiro.win!=null)
            Mpiro.win.ontoPipe.rebind();
        } // createNodes method
        
        public static boolean addBasicEntityType(String nodeName) {
            DefaultMutableTreeNode node = null;
            node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
            Object obj = node.getUserObject();
            if (obj == null)return false;
            String parent = node.toString();
            addObject(new IconData(ICON_BASIC, nodeName));
            // Now, create a new element in the mainDBHashtable with the given parameters
            Mpiro.win.struc.createSubType(parent, nodeName);
            Mpiro.win.struc.addEntityTypeInUserModelHashtable(nodeName); //==
           // QueryProfileHashtable.addEntityTypeInRobotsModelHashtable(nodeName);
            //QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable(nodeName); //==
            // Update the above created entry of mainDBHashtable with the chosen upperVector
            NodeVector createdVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nodeName);
            createdVector.setElementAt(KDialog.chboli.getItemsVector(), 1);
            // Mpiro.win.struc.addValueRestriction(nodeName,new ValueRestriction());
            databaseTree.revalidate();
            databaseTree.repaint();
            //Vector v=new Vector();
            //if(QueryHashtable.superClassesHashtable.containsKey(nodeName))
            //    v=(Vector)Mpiro.win.struc.getSuperClasses(nodeName);
            //v.add(("(Class:"+parent+")"));
            //QueryHashtable.superClassesHashtable.put(nodeName,v);
            Mpiro.win.ontoPipe.rebind();
            return true;
        }
        
      
        
        public static boolean addEntityType(String nodeName) {
            DefaultMutableTreeNode node = null;
            node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
            Object obj = node.getUserObject();
            if (obj == null)return false;
            String parent = node.toString();
            addObject(new IconData(ICON_BOOK, nodeName));
            Mpiro.win.struc.createSubType(parent, nodeName);
            Mpiro.win.struc.addEntityTypeInUserModelHashtable(nodeName); //==
          //  QueryProfileHashtable.addEntityTypeInRobotsModelHashtable(nodeName);
           // QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable(nodeName); //==
            databaseTree.revalidate();
            databaseTree.repaint();
            //Vector v=new Vector();
            //v.add(("(Class:"+parent+")"));
            //QueryHashtable.superClassesHashtable.put(nodeName,v);
            Mpiro.win.ontoPipe.rebind();
            return true;
        }
        
        public static boolean addEntity(String nodeName) {
            DefaultMutableTreeNode node = null;
            node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
            Object obj = node.getUserObject();
            if (obj == null)return false;
            String parent = node.toString();
            //System.out.println("parent:::::"+parent);
            addObject(new IconData(ICON_GEI, nodeName));
            Mpiro.win.struc.createEntity(parent, nodeName);
            Mpiro.win.struc.updateCreatedEntity(parent, nodeName);
            Mpiro.win.struc.addEntityInUserModelHashtable(nodeName); //==
          //  QueryProfileHashtable.addEntityInRobotsModelHashtable(nodeName);
            //QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable(nodeName); //==
            Mpiro.win.struc.addValuesFromHasValueRestrictions(nodeName, parent);
            databaseTree.revalidate();
            databaseTree.repaint();
            return true;
        }
        
        
        public static boolean addEntityType(String nodeName,String parent) {
            DefaultMutableTreeNode node = null;
            Object elem1=null;
            DefaultMutableTreeNode first = (DefaultMutableTreeNode) top.getChildAt(0);
            
            Enumeration lastTypeChildren=first.preorderEnumeration();
            //Enumeration lastTypeChildren=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity Type").keys();
            while (lastTypeChildren.hasMoreElements()) {
                elem1=lastTypeChildren.nextElement();
                ////System.out.println("QQQQQQQQ"+elem1);
                if (elem1.toString().equalsIgnoreCase(parent)) {
                    node= (DefaultMutableTreeNode) elem1;
                    break;
                }
                //System.out.println("OOOOOOOOOOOOOOOOOOOOOOOO"+parent);
            }
            addObject(node,new IconData(ICON_BOOK, nodeName));
            Mpiro.win.struc.createSubType(parent, nodeName);
            Mpiro.win.struc.addEntityTypeInUserModelHashtable(nodeName); //==
           // QueryProfileHashtable.addEntityTypeInRobotsModelHashtable(nodeName);
           // QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable(nodeName); //==
            databaseTree.revalidate();
            databaseTree.repaint();
            // Vector v=new Vector();
            // if(QueryHashtable.superClassesHashtable.containsKey(nodeName))
            //      v=(Vector)Mpiro.win.struc.getSuperClasses(nodeName);
            //  v.add(("(Class:"+parent+")"));
            //QueryHashtable.superClassesHashtable.put(nodeName,v);
            Mpiro.win.ontoPipe.rebind();
            return true;
        }
        
        public static boolean addEntityTypeOccur(String nodeName,String parent) {
            DefaultMutableTreeNode node = null;
            Object elem1=null;
            DefaultMutableTreeNode first = (DefaultMutableTreeNode) top.getChildAt(0);
            
            Enumeration lastTypeChildren=first.preorderEnumeration();
            //Enumeration lastTypeChildren=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity Type").keys();
            while (lastTypeChildren.hasMoreElements()) {
                elem1=lastTypeChildren.nextElement();
                ////System.out.println("QQQQQQQQ"+elem1);
                if (elem1.toString().equalsIgnoreCase(parent)) {
                    node= (DefaultMutableTreeNode) elem1;
                    break;
                }
                //System.out.println("OOOOOOOOOOOOOOOOOOOOOOOO"+parent);
            }
            addObject(node,new IconData(ICON_BOOK, nodeName));
            //Mpiro.win.struc.createSubType(parent, nodeName);
            //Mpiro.win.struc.addEntityTypeInUserModelHashtable(nodeName); //==
            //QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable(nodeName); //==
            databaseTree.revalidate();
            databaseTree.repaint();
            //  if(QueryHashtable.superClassesHashtable.containsKey(Mpiro.win.struc.nameWithoutOccur(nodeName)))
            //  ((Vector)Mpiro.win.struc.getSuperClasses(Mpiro.win.struc.nameWithoutOccur(nodeName))).add("(Class:"+parent+")");
            //  else{
            //       Vector v=new Vector();
            //      v.add("(Class:"+parent+")");
            //       QueryHashtable.superClassesHashtable.put(Mpiro.win.struc.nameWithoutOccur(nodeName), v);
            //   }
            Mpiro.win.ontoPipe.rebind();
            return true;
        }
        
        
        public static boolean addEntity(String nodeName,String parent) {
            DefaultMutableTreeNode node = null;
            Object elem1=null;
            //  //System.out.println("QQQQQQQQ"+parent);
            //node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
            //Object obj = node.getUserObject();
            //if (obj == null)return false;
            //String parent = node.toString();
            ////System.out.println("parent:::::"+parent);
            //addObject(new IconData(ICON_GEI, nodeName));
            DefaultMutableTreeNode first = (DefaultMutableTreeNode) top.getChildAt(0);
            
            Enumeration lastTypeChildren=first.preorderEnumeration();
            //Enumeration lastTypeChildren=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity Type").keys();
            while (lastTypeChildren.hasMoreElements()) {
                elem1=lastTypeChildren.nextElement();
                ////System.out.println("QQQQQQQQ"+elem1);
                if (elem1.toString().equalsIgnoreCase(parent)) {
                    node= (DefaultMutableTreeNode) elem1;
                    break;
                }
                // //System.out.println(parent);
            }
            addObject(node,new IconData(ICON_GEI, nodeName));
            Mpiro.win.struc.createEntity(parent, nodeName);
            Mpiro.win.struc.updateCreatedEntity(parent, nodeName);
            
            Mpiro.win.struc.addEntityInUserModelHashtable(nodeName); //==
           // QueryProfileHashtable.addEntityInRobotsModelHashtable(nodeName); 
           // QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable(nodeName); //==
            Mpiro.win.struc.addValuesFromHasValueRestrictions(nodeName, parent);
            databaseTree.revalidate();
            databaseTree.repaint();
            Mpiro.win.ontoPipe.rebind();
            return true;
        }
        
        
        public static boolean addEntityOccur(String nodeName,String parent) {
            DefaultMutableTreeNode node = null;
            Object elem1=null;
            //  //System.out.println("QQQQQQQQ"+parent);
            //node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
            //Object obj = node.getUserObject();
            //if (obj == null)return false;
            //String parent = node.toString();
            ////System.out.println("parent:::::"+parent);
            //addObject(new IconData(ICON_GEI, nodeName));
            DefaultMutableTreeNode first = (DefaultMutableTreeNode) top.getChildAt(0);
            
            Enumeration lastTypeChildren=first.preorderEnumeration();
            //Enumeration lastTypeChildren=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity Type").keys();
            while (lastTypeChildren.hasMoreElements()) {
                elem1=lastTypeChildren.nextElement();
                ////System.out.println("QQQQQQQQ"+elem1);
                if (elem1.toString().equalsIgnoreCase(parent)) {
                    node= (DefaultMutableTreeNode) elem1;
                    break;
                }
                // //System.out.println(parent);
            }
            addObject(node,new IconData(ICON_GEI, nodeName));
            //Mpiro.win.struc.createEntity(parent, nodeName);
            //Mpiro.win.struc.updateCreatedEntity(parent, nodeName);
            
            //Mpiro.win.struc.addEntityInUserModelHashtable(nodeName); //==
            //QueryProfileHashtable.addEntityTypeOrEntityInUserModelStoryHashtable(nodeName); //==
            //Mpiro.win.struc.addValuesFromHasValueRestrictions(nodeName, parent);
            databaseTree.revalidate();
            databaseTree.repaint();
            Mpiro.win.ontoPipe.rebind();
            return true;
        }
        
        
        /**
         *  Reloads the Database Tree.
         *  It is used when loading a new domain
         */
        public static void reloadDBTree() {
            // Clear the tree
            clearTree();
            
            // Set the *first* node
            DefaultMutableTreeNode first = (DefaultMutableTreeNode) top.getChildAt(0);
            
            Vector tempVector = new Vector();
            tempVector.addElement("Basic-entity-types");
            Vector childrenVector = new Vector();

            Hashtable allEntityTypes = new Hashtable();
            Hashtable allEntities = new Hashtable();
            //Hashtable allGeneric = new Hashtable();
           // Hashtable temp= Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            allEntityTypes = (Hashtable) Mpiro.win.ontoPipe.getExtension().getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            allEntities = (Hashtable) Mpiro.win.ontoPipe.getExtension().getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
            //allGeneric = (Hashtable) Mpiro.win.ontoPipe.getExtension().getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Generic");
            
            
            //         allEntities.remove("olympic-games");
            //                       allEntities.remove("virtual-reality");
            // Remove the 2 entries that are not needed
            allEntityTypes.remove("Data Base");
            allEntityTypes.remove("Basic-entity-types");
            
            //String nodename = new String("Basic-entity-types");
            //int childrenNumber = 0;
            
            // Adding the basic entity types
            childrenVector = Mpiro.win.ontoPipe.getExtension().getChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
            addChildren(first, childrenVector);
            //remove (Basic) Entity Types from the allEntityTypes HashTable
            for (Enumeration k = childrenVector.elements(); k.hasMoreElements(); ) {
                String entitytype = k.nextElement().toString();
                allEntityTypes.remove(entitytype);
            }
            
            // Adding the entity types
            boolean continueLoop=true;
            while (allEntityTypes.size() != 0 &&  continueLoop) {
                continueLoop=false;
                Enumeration enu1 = first.preorderEnumeration();
                while (enu1.hasMoreElements()) {
                    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) enu1.nextElement();
                    if (!currentNode.isNodeSibling(first)) {
                        continueLoop=true;
                        String node = currentNode.toString();
                        if (allEntityTypes.containsValue(node)) {
                            childrenVector = Mpiro.win.ontoPipe.getExtension().getChildrenVectorFromMainDBHashtable(node, "Entity type");
                            Enumeration enu2 = childrenVector.elements();
                            while (enu2.hasMoreElements()) {
                                String child = enu2.nextElement().toString();
                                currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_BOOK, child)));
                                allEntityTypes.remove(child);
                            }
                            databaseTree.revalidate();
                            databaseTree.repaint();
                        }
                    }
                }
            }
            
            // Adding the entities
            childrenVector.clear();
          //  childrenNumber = 0;
             continueLoop=true;
            while (allEntities.size() != 0 && continueLoop) {
                 continueLoop=false;
                
                Enumeration enu1 = first.preorderEnumeration();
                while (enu1.hasMoreElements()) {
                    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) enu1.nextElement();
                    if (!currentNode.isNodeSibling(first)) {
                        continueLoop=true;
                        String node = currentNode.toString();
                        if (allEntities.containsValue(node)) {
                            childrenVector = Mpiro.win.ontoPipe.getExtension().getChildrenVectorFromMainDBHashtable(node, "Entity");
                            Enumeration enu2 = childrenVector.elements();
                            while (enu2.hasMoreElements()) {
                                String child = enu2.nextElement().toString();
                                currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_GEI, child)));
                                allEntities.remove(child);
                            }
                            databaseTree.revalidate();
                            databaseTree.repaint();
                        }
                    }
                }
            }
            
            // Adding the generic entities
            childrenVector.clear();
           // childrenNumber = 0;
            
//            while (allGeneric.size() != 0) {
//                Enumeration enu1 = first.preorderEnumeration();
//                while (enu1.hasMoreElements()) {
//                    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) enu1.nextElement();
//                    if (!currentNode.isNodeSibling(first)) {
//                        String node = currentNode.toString();
//                        if (allGeneric.containsValue(node)) {
//                            childrenVector = Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(node, "Generic");
//                            Enumeration enu2 = childrenVector.elements();
//                            while (enu2.hasMoreElements()) {
//                                String child = enu2.nextElement().toString();
//                                currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_GENERIC, child)));
//                                allGeneric.remove(child);
//                            }
//                            databaseTree.revalidate();
//                            databaseTree.repaint();
//                        }
//                    }
//                }
//            }
            databaseTree.revalidate();
            databaseTree.repaint();
        } //reloadDBTree()
        
        public static void addChildren(DefaultMutableTreeNode currentNode, Vector childrenVector) {
            Enumeration enumer = childrenVector.elements();
            while (enumer.hasMoreElements()) {
                String child = enumer.nextElement().toString();
                currentNode.add(new DefaultMutableTreeNode(new IconData(DataBasePanel.ICON_BASIC, child)));
            }
        }
        
        public static void searchTree(String text) { //theofilos
            DefaultMutableTreeNode Dmt;
            String nodeName = "";
            
            if (text.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        LangResources.getString(Mpiro.selectedLocale, "no_input_msg"),
                        LangResources.getString(Mpiro.selectedLocale, "search_text"),
                        JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(Mpiro.obj.image_mpirotext));
                searchedElements.clear(); //theofilos
                searchedElements.add("Data-types"); //theofilos
                searchedElements.add("String"); //theofilos
                searchedElements.add("Number"); //theofilos
                searchedElements.add("Date"); //theofilos
                searchedElements.add("Dimension"); //theofilos
            }
            
            else {
                //TreePath currentSelection = databaseTree.getSelectionPath();
                //TreePath currentSelection = databaseTree.getPathForRow(databaseTree.getRowForPath(
                //databaseTree.getSelectionPath())+1);
                //DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)currentSelection.getLastPathComponent();
                
                DefaultMutableTreeNode currentNode = DataBasePanel.top;
                Enumeration enumer = currentNode.preorderEnumeration();
                //DefaultMutableTreeNode tempp = new DefaultMutableTreeNode(databaseTree.getLastSelectedPathComponent());
                //Enumeration enum = tempp.preorderEnumeration();
                ////System.out.println("sss="+enum.toString());
                String namenode = "";
                while (enumer.hasMoreElements()) {
                    currentNode = (DefaultMutableTreeNode) enumer.nextElement();
                    namenode = currentNode.toString();
                    //if (namenode.equalsIgnoreCase(text))
                    if ( (namenode.toLowerCase()).startsWith(text.toLowerCase())) {
                        if (searchedElements.contains(namenode)) {
                            
                        } else {
                            searchedElements.addElement(namenode);
                            break;
                        }
                    }
                    namenode = "";
                }
                if (namenode != "") {
                    Hashtable allEntityTypes = new Hashtable();
                    Hashtable allEntitiesAndGeneric = new Hashtable();
                    Hashtable allGeneric = new Hashtable();
                    allEntityTypes = (Hashtable) Mpiro.win.ontoPipe.getExtension().getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    allEntitiesAndGeneric = (Hashtable) Mpiro.win.ontoPipe.getExtension().getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
                    
                    Vector path;
                    boolean isEntityType = false;
                    
                    if (allEntityTypes.containsKey(namenode)) {
                        path = Mpiro.win.ontoPipe.getExtension().getFullPathParentsVectorFromMainDBHashtable(namenode, "Entity type");
                        isEntityType = true;
                    } else {
                        path = Mpiro.win.ontoPipe.getExtension().getFullPathParentsVectorFromMainDBHashtable(namenode, "Entity");
                    }
                    Object[] o = (Object[]) currentNode.getPath();
                    TreePath treeP = new TreePath(o);
                    DataBasePanel.databaseTree.expandPath(treeP);
                    DataBasePanel.databaseTree.setSelectionPath(treeP);
                    
                    DataBasePanel.databaseTree.scrollPathToVisible(treeP);
                    DataBasePanel.databaseTree.revalidate();
                    DataBasePanel.databaseTree.repaint();
                    
                    TreePreviews.generalDataBasePreview();
                    TreePreviews.setDataBaseTableAfterSearch(namenode, currentNode, isEntityType);
                    
                    //***********************
                    Dmt = (DefaultMutableTreeNode) (treeP.getLastPathComponent()); //theofilos
                    Object obj = (Object) (Dmt.getUserObject()); //theofilos
                    //nodeName = obj.toString();  //theofilos
                    IconData id = (IconData) obj; //theofilos
                    Icon ii = id.getIcon(); //theofilos
                    im = (ImageIcon) ii; //theofilos
                    if ( (im == ICON_GEI) || (im == ICON_GENERIC)) { //theofilos
                        previewFunctionsPanel.setVisible(true);
                    } //theofilos
                    else { //theofilos
                        previewFunctionsPanel.setVisible(false);
                    } //theofilos
                } else {
                    JOptionPane.showMessageDialog(null,
                            LangResources.getString(Mpiro.selectedLocale, "not_found_in_search_msg"),
                            LangResources.getString(Mpiro.selectedLocale, "search_text"),
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon(Mpiro.obj.image_mpirotext));
                    searchedElements.clear(); //theofilos
                    searchedElements.add("Data-types"); //theofilos
                    searchedElements.add("String"); //theofilos
                    searchedElements.add("Number"); //theofilos
                    searchedElements.add("Date"); //theofilos
                    searchedElements.add("Dimension"); //theofilos
                }
            }
        }
        
        public void fillComboBox() { //theofilos
            usersCombo.removeAllItems();
            Vector userTypesVector = Mpiro.win.ontoPipe.getExtension().getUsersVectorFromMainUsersHashtable();
            ////System.out.println("vector=" + userTypesVector.toString() + "#");
            Enumeration userTypesVectorEnum = userTypesVector.elements();
            while (userTypesVectorEnum.hasMoreElements()) {
                String usertype = userTypesVectorEnum.nextElement().toString();
                usersCombo.addItem(usertype);
            }
        }
        
        public void updateFlagCombo() {
            flags.setSelectedItem(ii_UK);
        }
        
        private void jbInit() throws Exception {
        }
        
        
        public static void deletePropFromChildren(String ParentName, FieldData property11) {
            Vector vec= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(ParentName,"entity+generic");
            for(int x=0;x<4;x++){
                for (int u=0;u<vec.size();u++) {
                    Vector vec1=(Vector) Mpiro.win.struc.getEntityTypeOrEntity(vec.elementAt(u).toString());
                    
                    Vector vec2=(Vector) vec1.elementAt(0);
                    vec2=(Vector) vec2.elementAt(x);
                    // System.out.println("MMMMMMMM"+vec2.toString());
                    for (int y=3;y<vec2.size();y++) {
                        Vector  vec4=(Vector) vec2.elementAt(y);
                        //System.out.println("GGGGGGGF"+vec4.elementAt(0).toString());
                        if(vec4.elementAt(0).toString().equalsIgnoreCase(property11.elementAt(0).toString()))
                            vec2.remove(y);
                    }}
            }
            
            Vector vecTypes=(Vector) Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(ParentName,"entity type");
            for(int s=0;s<vecTypes.size();s++) {
                Vector lo=(Vector) Mpiro.win.struc.getEntityTypeOrEntity(vecTypes.elementAt(0).toString());
                //System.out.println(vecTypes.firstElement().toString()+"JJJJJJJJJJJJJJJjjjjjjjjj");
                Vector LK=(Vector) lo.elementAt(s);
                for(int k=3;k<LK.size();k++){
                    if(((Vector)LK.elementAt(k)).elementAt(0).toString().equalsIgnoreCase(property11.elementAt(0).toString())){
                        LK.removeElementAt(k);
                        break;
                    }
                }
                // LK.remove(property11);
                deletePropFromChildren(vecTypes.firstElement().toString(), property11);
                
            }
        }
        public static void removeNounFromChildren(String nameWithoutOccur,Object noun){
            Vector children=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(nameWithoutOccur,"entity type");
            for(int g=0;g<children.size();g++) {
                NodeVector nv=    (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(children.elementAt(g).toString());
                Vector n1=nv.nounVector;
                n1.remove(noun);
                nv.set(2,n1);
                removeNounFromChildren(children.elementAt(g).toString(),noun);
            }}
        public static DefaultMutableTreeNode getNode(String Name){
            Enumeration allNodes=top.preorderEnumeration();
            while(allNodes.hasMoreElements()){
                DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode) allNodes.nextElement();
                if(dmtn.toString().equalsIgnoreCase(Name))
                    return dmtn;
            }
            return null;
        }
        private static void moveSubtypes(String name){
            Enumeration subtypes=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(name, "entity type").elements();
            while(subtypes.hasMoreElements()){
                String next=subtypes.nextElement().toString();
                Vector db=(Vector)Mpiro.win.struc.getEntityTypeOrEntity(next);
                Vector database=(Vector)db.elementAt(0);
                Vector subType=(Vector)database.elementAt(0);
                subType.setElementAt(DataBasePanel.getNode(next+"_occur2").getParent().toString(),1);
                Mpiro.win.struc.putEntityTypeOrEntityToDB(next+"_occur2",(NodeVector)db);
                moveSubtypes(next);
                moveEntities(next);
            }
        }
        
        private static void moveEntities(String name){
            Enumeration subtypes=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(name, "entity").elements();
            while(subtypes.hasMoreElements()){
                String next=subtypes.nextElement().toString();
                Vector db=(Vector)Mpiro.win.struc.getEntityTypeOrEntity(next);
                Vector database=(Vector)db.elementAt(0);
                Vector indep=(Vector)database.elementAt(0);
                Vector subType=(Vector)indep.elementAt(1);
                //DefaultMutableTreeNode test=DataBasePanel.getNode(next+"_occur2");
                subType.setElementAt(DataBasePanel.getNode(next+"_occur2").getParent().toString(),1);
                Mpiro.win.struc.putEntityTypeOrEntityToDB(next+"_occur2",(NodeVector)db);
                //   moveSubtypes(next);
                // moveEntities(next);
            }
        }
        
        public static void deleteOccur(String nameWithoutOccur, String parent){
            
            String nodenameToDelete=nameWithoutOccur;
            for(Enumeration children=getNode(parent).children();children.hasMoreElements();){
                String nextChild=children.nextElement().toString();
                if(nextChild.startsWith(nameWithoutOccur+"_occur")||nextChild.equalsIgnoreCase(nameWithoutOccur)){
                    nodenameToDelete=nextChild;
                    break;
                }
            }
            
            NodeVector entityTypeWithoutOccur = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nameWithoutOccur);
            NodeVector entityTypeParentOfLast = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parent);
            
            Vector databaseVectorOfTypeWithoutOccur =(Vector) entityTypeWithoutOccur.elementAt(0);
            Vector databaseVectorOfParentOfLast =(Vector) entityTypeParentOfLast.elementAt(0);
            
            
            for (int g = 8; g < databaseVectorOfParentOfLast.size(); g++) {
                FieldData property1 = ((FieldData) databaseVectorOfParentOfLast.get(g));
                //System.out.println("!!@!@!!!!@111!111"+property1.toString()+property1.m_field);
                
                boolean check=true;
                
                for(Enumeration lastTypeChildren5=((DefaultMutableTreeNode)top.getChildAt(0)).preorderEnumeration();lastTypeChildren5.hasMoreElements();){
                    //  while(lastTypeChildren5.hasMoreElements())
                    //{
                    DefaultMutableTreeNode next=(DefaultMutableTreeNode)lastTypeChildren5.nextElement();
                    // String name2=lastTypeChildren5.nextElement().toString();
                    //System.out.println("lllllllllll"+name2);
                    //if(( (nameWithoutOccur.equalsIgnoreCase(name2.replaceAll("_occur", "").substring(0, name2.replaceAll("_occur", "").length()-1))) ||  nameWithoutOccur.equalsIgnoreCase(name2))&& (!(name2.equalsIgnoreCase(last.toString()))))
                    if((next.toString().startsWith(nameWithoutOccur+"_occur")||next.toString().equalsIgnoreCase(nameWithoutOccur))&&!next.toString().equalsIgnoreCase(DataBasePanel.last.toString())) {
                        Vector db=(Vector)((Vector)Mpiro.win.struc.getEntityTypeOrEntity(next.getParent().toString())).elementAt(0);
                        if(db.contains(property1)){
                            check=false;
                            break;
                        }
                    }
                    
                }
                if(check){
                    //  databaseVectorOfTypeWithoutOccur.remove(property1);
                    for(int k=3;k<databaseVectorOfTypeWithoutOccur.size();k++){
                        if(((Vector)databaseVectorOfTypeWithoutOccur.elementAt(k)).elementAt(0).toString().equalsIgnoreCase(property1.elementAt(0).toString())){
                            databaseVectorOfTypeWithoutOccur.removeElementAt(k);
                            break;
                        }
                    }
                    deletePropFromChildren(nameWithoutOccur, property1);
                }
                
            }
            
            
            
            
            if(!nodenameToDelete.contains("_occur")){
                Vector db=(Vector)Mpiro.win.struc.getEntityTypeOrEntity(nameWithoutOccur);
                Vector database=(Vector)db.elementAt(0);
                Vector subType=(Vector)database.elementAt(0);
                subType.setElementAt(DataBasePanel.getNode(nameWithoutOccur+"_occur2").getParent().toString(),1);
                Mpiro.win.struc.putEntityTypeOrEntityToDB(nameWithoutOccur+"_occur2", (NodeVector)db);
                moveSubtypes(nameWithoutOccur);
                moveEntities(nameWithoutOccur);
            }
            Vector nounVector1 = (Vector) entityTypeParentOfLast.nounVector;
            
            Vector nounVector3 = (Vector) entityTypeWithoutOccur.nounVector;
            
            for(int h=0;h<nounVector1.size();h++){
                boolean deleteNoun=true;
                
                Enumeration lastTypeChildren5=((DefaultMutableTreeNode)top.getChildAt(0)).preorderEnumeration();
                while(lastTypeChildren5.hasMoreElements()) {
                    Object noun=new Object();
                    DefaultMutableTreeNode nodeNext1= (DefaultMutableTreeNode) lastTypeChildren5.nextElement();
                    String name2=nodeNext1.toString();
                    if( (nameWithoutOccur.equalsIgnoreCase(name2.replaceAll("_occur", "").substring(0, name2.replaceAll("_occur", "").length()-1))) ||  nameWithoutOccur.equalsIgnoreCase(name2)) {
                        NodeVector par = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nodeNext1.getParent().toString());
                        Vector nounVector2 = (Vector) par.elementAt(2);
                        noun=nounVector1.elementAt(h);
                        if(nounVector2.contains(noun))
                            deleteNoun=false;
                        
                    }
                    if (deleteNoun){
                        nounVector3.remove(noun);
                        removeNounFromChildren(nameWithoutOccur,noun);
                        
                        
                        
                    }
                }
                
            }
            
            entityTypeParentOfLast.nounVector=(Vector) entityTypeParentOfLast.elementAt(2);
            entityTypeWithoutOccur.nounVector=(Vector) entityTypeWithoutOccur.elementAt(2);
            // }
            // }
            
            int d=2;
            //System.out.println("dddd"+String.valueOf(d));
            if (nodenameToDelete.toString().substring(0,nodenameToDelete.toString().length()-1).endsWith("_occur") )  {
                char c = nodenameToDelete.toString().charAt(nodenameToDelete.toString().length()-1);
                d=c-'0'+1;
            }
            removeCurrentNode();
            boolean hasMore2=true;
            //  Enumeration temp=QueryHashtable.mainDBHashtable.keys();
            //  System.out.println("...");
            for(;hasMore2;d++) { //System.out.println("3");
                Object elem3=null;
                hasMore2=false;
                //DefaultMutableTreeNode first3 = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                Enumeration lastTypeChildren3=((DefaultMutableTreeNode)top.getChildAt(0)).preorderEnumeration();
                do{
                    //im=ICON_BOOK;
                    
                    //System.out.println("4");
                    elem3=lastTypeChildren3.nextElement();
                    
                    if (elem3.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(d))) {
                        
                        DefaultMutableTreeNode tree1= (DefaultMutableTreeNode) elem3;
                        String par1=tree1.getParent().toString();
                        Enumeration hash1= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(tree1.toString(), "entity").elements();
                        Enumeration subtypes=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(tree1.toString(), "entity type").elements();
                        //   Enumeration childrenEnt=hash1.
                        Object nextChild=null;
                        hasMore2=true; //System.out.println("5");
                        //System.out.println("texttttttt"+nameWithoutOccur+"_occur"+String.valueOf(d));
                        if (d!=2){
                            KDialog.renameOccur(nameWithoutOccur+"_occur"+String.valueOf(d-1),nameWithoutOccur+"_occur"+String.valueOf(d),par1);
                            while(hash1.hasMoreElements()) {
                                nextChild=hash1.nextElement();
                                //System.out.println("dfasddfsafaf"+nextChild.toString());
                                KDialog.renameOccur(nextChild.toString().substring(0, nextChild.toString().length()-7)+"_occur"+String.valueOf(d-1),nextChild.toString().substring(0, nextChild.toString().length()-7)+"_occur"+String.valueOf(d),nameWithoutOccur+"_occur"+String.valueOf(d));
                                
                            }
                            while(subtypes.hasMoreElements()) {
                                
                                nextChild=subtypes.nextElement();
                                for(Enumeration hash2=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(nextChild.toString(), "entity").elements();hash2.hasMoreElements();){
                                    Object nextEnt=hash2.nextElement();
                                    KDialog.renameOccur(nextEnt.toString().substring(0, nextEnt.toString().length()-7)+"_occur"+String.valueOf(d-1),nextEnt.toString().substring(0, nextEnt.toString().length()-7)+"_occur"+String.valueOf(d),nameWithoutOccur+"_occur"+String.valueOf(d));
                                }
                                KDialog.renameOccur(nextChild.toString().substring(0, nextChild.toString().length()-7)+"_occur"+String.valueOf(d-1),nextChild.toString().substring(0, nextChild.toString().length()-7)+"_occur"+String.valueOf(d),nameWithoutOccur+"_occur"+String.valueOf(d));
                            }
                            
                            
                        } else {
                            KDialog.renameEntity(nameWithoutOccur,nameWithoutOccur+"_occur"+String.valueOf(d),par1);
                            while(hash1.hasMoreElements()) {
                                nextChild=hash1.nextElement();
                                KDialog.renameEntity(nextChild.toString().substring(0, nextChild.toString().length()-7),nextChild.toString(),nameWithoutOccur+"_occur"+String.valueOf(d));
                                
                            }
                            while(subtypes.hasMoreElements()) {
                                nextChild=subtypes.nextElement();
                                for(Enumeration hash2=Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(nextChild.toString(), "entity").elements();hash2.hasMoreElements();){
                                    Object nextEnt=hash2.nextElement();
                                    KDialog.renameEntity(nextEnt.toString().substring(0, nextEnt.toString().length()-7),nextEnt.toString(),nameWithoutOccur+"_occur"+String.valueOf(d));
                                }
                                KDialog.renameEntity(nextChild.toString().substring(0, nextChild.toString().length()-7),nextChild.toString(),nameWithoutOccur+"_occur"+String.valueOf(d));
                            }
                            
                            
                        }
                        break;
                    }
                    
                }while(lastTypeChildren3.hasMoreElements());
                
                // if (e)
            }
        }
        
    } //class
