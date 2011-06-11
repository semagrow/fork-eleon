//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;

import gr.demokritos.iit.eleon.authoring.*;
import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;

/**
 * <p>Title: KDialog</p>
 * <p>Description: A generic dialog used for (see Constructor)
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class KDialog
        extends JFrame {
    public static JDialog dialog;
    static KLabel listLabel;
    private KLabel textFieldLabel;
    private CustomizePanel customizePanel;
    private OkCancel okCancel;
    private JTextField textField;
    public static CheckBoxList chboli;
    private boolean isSelected = false; // is something in the list selected?
    
    private KButton customizeButton;
    private KButton addButton;
    private KButton removeButton;
    private KButton okButton;
    private KButton cancelButton;
    
    private CheckboxGroup cbg;
    private ButtonGroup cbg1;
    
    private DefaultMutableTreeNode lastNode;
    private DefaultMutableTreeNode createdChild;
    
    static String text;
    static Vector itemsSelected;
    static Object item;
    static JPanel checkPanel;
    static JPanel checkPanel1;
    static JPanel inheritedPanel;
    static JScrollPane scrollPane;
    
    Vector m_listItems;
    static NodeVector currentVector;
    
    Vector commonNouns; // inherited nouns
    Vector allNounsClone; // rest of nouns ([all] - [inherited])
    Vector beforeOkNounVector; // initial nounVector of a node
    // (before changing it)
    
    public Vector positionsVector = null;
    public Vector selectionsVector = null;
    
    static String type;
    
    /** a) String textFieldTitleThe controls the visibility
     *     of the textField: if set to null no textField is visible.
     *	 b) Boolean customizeVisible controls the visibility of
     *	    the add/remove buttons: if set to true the customizePanel
     *     appears in the dialog.
     *  c) String dialogType controls which actionListener is added
     *     to the dialog's buttons.
     *     UPPER  for the upperTypes dialog,
     *     BASIC  for the addNewBasicType dialog,
     *     NOUN   for the nounList dialog,
     *     ENTITY-TYPE for the addSubtype dialog
     *     ENTITY for the addEntity dialog
     *     LEXICON-NOUN for the addNoun dialog
     *     LEXICON-VERB for the addVerb dialog
     *     USER for the addUser dialog
     *     RENAME for renaming node dialog.
     * @param frameTitle the title for the frame
     * @param textFieldTitle the text field title
     * @param listTitle the list title
     * @param listItems the items list
     * @param customizeVisible "true" or "false"
     * @param dialogType the type of dialog (see description)
     */
    public KDialog(String frameTitle, String textFieldTitle, String listTitle,
            Vector listItems, boolean customizeVisible, String dialogType, boolean visible) {
        type = dialogType;
        m_listItems = new Vector(listItems);
        
        
        // this.setVisible(visible);
        
        // Get the nodeVector of the selected node
        if (Mpiro.win.tabbedPane.getSelectedIndex() == 0) { //Lexicon tab
        } else if (Mpiro.win.tabbedPane.getSelectedIndex() == 2) { // Users tab
        } else if (Mpiro.win.tabbedPane.getSelectedIndex() == 1) {
            currentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
        }
        // The dialog and its components from top to bottom (1-6)
        dialog = new JDialog(Mpiro.win.getFrames()[0], frameTitle, true);
        super.setIconImage(Mpiro.obj.image_corner);
        // 1
        textFieldLabel = new KLabel(textFieldTitle);
        // 2
        textField = new JTextField(16);
        // 3
        listLabel = new KLabel(listTitle);
        // 4
        inheritedPanel = new JPanel(new BorderLayout());
        // 5
        checkPanel = new JPanel(new BorderLayout());
        chboli = new CheckBoxList(listItems);
        chboli.setPreferredSize(new Dimension(170, 118));
        if (!listItems.isEmpty()) {
            checkPanel.add("Center", chboli);
        }
        if (type == "USER") {
            textField.setText("NewUserType");
            textField.selectAll();
        }
        
        if (type == "ROBOT") {
            textField.setText("NewProfile");
            textField.selectAll();
        }
        
        if (type == "SUPER") {
            
            textField.setVisible(false);
            JList  list= new JList(textFieldTitle.split(" "));
            checkPanel.add(list);
            textFieldLabel.setText(DataBasePanel.last.toString());
        }
        
        if (type == "TEST") {
            //  JScrollPane scrollPane = new JScrollPane();
            //    checkPanel = new JPanel(new BorderLayout());
            checkPanel1 = new JPanel(new BorderLayout());
            // JScrollPane scrollPane = new JScrollPane(colorList);
            // p.add(scrollPane);
            textField.setVisible(false);
            
            Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            allEntityTypes.remove("Data Base");
            allEntityTypes.remove("Basic-entity-types");
            if (DataBasePanel.last.toString().substring(0,DataBasePanel.last.toString().length()-1).endsWith("_occur"))
                allEntityTypes.remove(DataBasePanel.last.toString().substring(0,DataBasePanel.last.toString().length()-7));
            else
                allEntityTypes.remove(DataBasePanel.last.toString());
            allEntityTypes.remove(DataBasePanel.last.getParent().toString());
            //  Enumeration allTypesNames=allEntityTypes.keys();
            Vector r=new Vector();
            if(allEntityTypes.size()>0)
                r=QuickSort.quickSort(0,allEntityTypes.size()-1,new Vector(allEntityTypes.keySet()));
            //   do{
            //   System.out.println("*******"+allTypesNames.nextElement().toString());
            //   }while(allTypesNames.hasMoreElements());
            
            checkPanel1.setLayout(new GridLayout(allEntityTypes.size() , 1));
            //  JScrollPane scrollPane = new JScrollPane());
            
            //   String j=allTypesNames.toString();
            // Vector ver= (Vector)allTypesNames;
            
            cbg1 = new ButtonGroup();
            JRadioButton radioButton;
            //  while (allTypesNames.hasMoreElements()){
            for(int h=0;h<r.size();h++){
//scrollPane.add(new Checkbox(allTypesNames.nextElement().toString(), cbg, true));
                String nextElem=r.elementAt(h).toString();
                if (!(nextElem.substring(0, nextElem.length()-1).endsWith("_occur"))){
                    radioButton=new JRadioButton(nextElem);
                    radioButton.setActionCommand(nextElem);
                    cbg1.add(radioButton);
                    checkPanel1.add(radioButton);
                }
                //checkPanel.add(new Checkbox("two", cbg, false));
                //checkPanel.add(new Checkbox("three", cbg, false));
            }
            // checkPanel1.add(okButton);
            //  checkPanel1.add(cancelButton);
            // colorList.addListSelectionListener(this);
            scrollPane= new JScrollPane(checkPanel1);
            scrollPane.setPreferredSize(new Dimension(300,300));
            // checkPanel1.setPreferredSize(new Dimension(300,300));
            checkPanel.add(scrollPane);
            
            // checkPanel.add(scrollPane);
        }
        if (type.equals("ANNOTATION")) {
            //  JScrollPane scrollPane = new JScrollPane();
            //    checkPanel = new JPanel(new BorderLayout());
            checkPanel1 = new JPanel(new BorderLayout());
            // JScrollPane scrollPane = new JScrollPane(colorList);
            // p.add(scrollPane);
            textField.setVisible(false);
            
            Hashtable allEntities = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
            // allEntityTypes.remove("Data Base");
            //  allEntityTypes.remove("Basic-entity-types");
            //  if (DataBasePanel.last.toString().substring(0,DataBasePanel.last.toString().length()-1).endsWith("_occur"))
            //      allEntityTypes.remove(DataBasePanel.last.toString().substring(0,DataBasePanel.last.toString().length()-7));
            //  else
            //  allEntityTypes.remove(DataBasePanel.last.toString());
            //   allEntityTypes.remove(DataBasePanel.last.getParent().toString());
            //  Enumeration allTypesNames=allEntityTypes.keys();
            Vector r=new Vector();
            if(allEntities.size()>0)
                r=QuickSort.quickSort(0,allEntities.size()-1,new Vector(allEntities.keySet()));
            //   do{
            //   System.out.println("*******"+allTypesNames.nextElement().toString());
            //   }while(allTypesNames.hasMoreElements());
            
            checkPanel1.setLayout(new GridLayout(allEntities.size() , 1));
            //  JScrollPane scrollPane = new JScrollPane());
            
            //   String j=allTypesNames.toString();
            // Vector ver= (Vector)allTypesNames;
            
            cbg1 = new ButtonGroup();
            JRadioButton radioButton;
            //  while (allTypesNames.hasMoreElements()){
            for(int h=0;h<r.size();h++){
//scrollPane.add(new Checkbox(allTypesNames.nextElement().toString(), cbg, true));
                String nextElem=r.elementAt(h).toString();
                if (!(nextElem.substring(0, nextElem.length()-1).endsWith("_occur"))){
                    radioButton=new JRadioButton(nextElem);
                    radioButton.setActionCommand(nextElem);
                    cbg1.add(radioButton);
                    checkPanel1.add(radioButton);
                }
                //checkPanel.add(new Checkbox("two", cbg, false));
                //checkPanel.add(new Checkbox("three", cbg, false));
            }
            // checkPanel1.add(okButton);
            //  checkPanel1.add(cancelButton);
            // colorList.addListSelectionListener(this);
            scrollPane= new JScrollPane(checkPanel1);
            scrollPane.setPreferredSize(new Dimension(300,300));
            // checkPanel1.setPreferredSize(new Dimension(300,300));
            checkPanel.add(scrollPane);
            
            // checkPanel.add(scrollPane);
        }
        
        if (type == "FIELD") {
            //  JScrollPane scrollPane = new JScrollPane();
            //    checkPanel = new JPanel(new BorderLayout());
            checkPanel1 = new JPanel(new BorderLayout());
            // JScrollPane scrollPane = new JScrollPane(colorList);
            // p.add(scrollPane);
            textField.setVisible(false);
            
            //Enumeration properties=Mpiro.win.struc.getPropertyNames();
            Vector orderedTypes=QuickSort.quickSort(0,Mpiro.win.struc.getNoOfProperties()-1,new Vector(Mpiro.win.struc.getPropertiesKeySet()));
            checkPanel1.setLayout(new GridLayout(Mpiro.win.struc.getNoOfProperties() , 1));
            //  JScrollPane scrollPane = new JScrollPane());
            
            
            cbg1 = new ButtonGroup();
            JRadioButton radioButton;
            for(int g=0;g<orderedTypes.size();g++){
                // while (properties.hasMoreElements()){
//scrollPane.add(new Checkbox(allTypesNames.nextElement().toString(), cbg, true));
                String nextElem=orderedTypes.elementAt(g).toString();
                Vector vec=(Vector)   Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
                vec=(Vector) vec.elementAt(0);
                boolean exists=false;
                for(int i=0;i<vec.size();i++){
                    Vector temp=(Vector) vec.elementAt(i);
                    if (temp.elementAt(0).toString().equalsIgnoreCase(nextElem)) {
                        exists=true;
                        break;
                    }
                }
                if(!exists){
                    //   if (!(nextElem.substring(0, nextElem.length()-1).endsWith("_occur"))){
                    radioButton=new JRadioButton(nextElem);
                    radioButton.setActionCommand(nextElem);
                    cbg1.add(radioButton);
                    checkPanel1.add(radioButton);}
                // }
                //checkPanel.add(new Checkbox("two", cbg, false));
                //checkPanel.add(new Checkbox("three", cbg, false));
            }
            // checkPanel1.add(okButton);
            //  checkPanel1.add(cancelButton);
            // colorList.addListSelectionListener(this);
            scrollPane= new JScrollPane(checkPanel1);
            scrollPane.setPreferredSize(new Dimension(300,300));
            // checkPanel1.setPreferredSize(new Dimension(300,300));
            checkPanel.add(scrollPane);
            
            // checkPanel.add(scrollPane);
        }
        
        
        else if (type == "BASIC") {
            chboli.clear();
        } else if (type == "UPPER") {
            Vector upper = (Vector) currentVector.elementAt(1);
            chboli.clear();
            chboli.setChecked(upper);
            
        }
        
        else if (type == "NOUN") {
            // get parent-node's nounVector
            
            
            
            
            
            String last=DataBasePanel.last.toString();
            // DefaultMutableTreeNode parent = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
            if(last.substring(0,last.length()-1).endsWith("_occur")) {
                
                last=last.substring(0, last.length()-7);
                currentVector=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(last);
            }
            Vector pNounVector=new Vector();
            //  Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            //        for (int k=0; k<v.size(); k++)
            //	{
            //		String noun = v.elementAt(k).toString();
            // Enumeration allTypesNames=allEntityTypes.keys();
            Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
            while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
                // Object nextEl=allTypesNames.nextElement();
                DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                
                
                if (nextEl.toString().startsWith(last+"_occur")||nextEl.toString().equalsIgnoreCase(last)) {
                    String parent=nextEl.getParent().toString();
                    // System.out.println(noun+"   "+nextEl.getParent().toString());
                    NodeVector pn = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(parent.toString());
                    Vector temp1 = (Vector)pn.elementAt(2);
                    for(int y=0;y<temp1.size();y++){
                        if (!pNounVector.contains(temp1.elementAt(y)))
                            pNounVector.add(temp1.elementAt(y));
                    }
                    pn.nounVector=(Vector)pn.elementAt(2);
                }}//}
            
            
            
            
            //     DefaultMutableTreeNode parent = (DefaultMutableTreeNode) DataBasePanel.last.getParent();
            //     NodeVector pn = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parent.toString());
            //     Vector pNounVector = (Vector) pn.elementAt(2);
            // get current-node's nounVector
            Vector nouns = (Vector) currentVector.elementAt(2);
            beforeOkNounVector = new Vector(nouns); // a vector that will be used later
            // when pressing the ok-button determine which nouns are common in both vectors
            // and put them in a new Vector (commonNouns)
            commonNouns = new Vector();
            Enumeration enu = nouns.elements();
            while (enu.hasMoreElements()) {
                String oneNoun = enu.nextElement().toString();
                if (pNounVector.contains(oneNoun)) {
                    commonNouns.addElement(oneNoun.toString());
                }
            }
            if (!commonNouns.isEmpty()) {
                listLabel.setText(LangResources.getString(Mpiro.selectedLocale, "otherLexiconNouns_text"));
                KLabel inheritedLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "inheritedNouns_text"));
                JTextArea inheritedNouns = new JTextArea();
                inheritedNouns.setLineWrap(true);
                inheritedNouns.setWrapStyleWord(true);
                inheritedNouns.setBorder(new EmptyBorder(0, 2, 0, 2));
                inheritedNouns.setFont(new Font(Mpiro.selectedFont, Font.BOLD, 11));
                inheritedNouns.setEditable(false);
                inheritedNouns.setBackground(new Color(228, 228, 195));
                inheritedNouns.setForeground(Color.gray);
                for (int a = 0; a < commonNouns.size(); a++) {
                    inheritedNouns.append(commonNouns.elementAt(a) + " ");
                }
                JScrollPane inheritedScroll = new JScrollPane(inheritedNouns);
                inheritedScroll.setPreferredSize(new Dimension(170, 35));
                inheritedPanel.add(BorderLayout.NORTH, inheritedLabel);
                inheritedPanel.add(BorderLayout.CENTER, inheritedScroll);
                
                // All lexicon-nouns are in listItems. Make a clone of it.
                allNounsClone = new Vector(listItems);
                Vector p=getAllInheritedNouns(DataBasePanel.last.toString());
                for (int i = 0; i < commonNouns.size(); i++) {
                    String o = p.elementAt(i).toString();
                    System.out.println(o);
                    for (int ii = 0; ii < allNounsClone.size(); ii++) {
                        if (o.equalsIgnoreCase(allNounsClone.elementAt(ii).toString())) {
                            allNounsClone.removeElementAt(ii);
                        }
                    }
                }
                checkPanel.removeAll();
                chboli = new CheckBoxList(allNounsClone);
                chboli.setChecked(nouns);
                checkPanel.add("Center", chboli);
                checkPanel.revalidate();
                checkPanel.repaint();
            } else {
                chboli.clear();
                chboli.setChecked(nouns);
            }
            currentVector.nounVector=(Vector) currentVector.elementAt(2);
        } else if (type == "ENTITY-TYPE") {
            textField.setText("NewEntityType");
            textField.selectAll();
        }
        //   else if (type == "ADD VALUE") {
        //         textField.setText("");
        ///     textField.selectAll();
        // }
        
        else if (type == "ENTITY") {
            
            textField.setText("NewEntity");
            textField.selectAll();
        }
        
        else if (type == "LEXICON-NOUN") {
            textField.setText("NewNoun");
            textField.selectAll();
        }
        
        else if (type == "LEXICON-VERB") {
            textField.setText("NewVerb");
            textField.selectAll();
        }
        
        else if (type == "RENAME") {
            if (Mpiro.win.tabbedPane.getSelectedIndex() == 0) { //Users tab
                textField.setText(UsersPanel.last.toString());
                textField.selectAll();
            } else if (Mpiro.win.tabbedPane.getSelectedIndex() == 1) { //DataBase tab
                String ren=DataBasePanel.last.toString();
                if (DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-1).endsWith("_occur")) {
                    ren=DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-7);
                }
                
                if(ren.contains("http://")){
                    
                     Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(null, //theofilos
                                        "Selected item cannot be renamed, because it is imported from another ontology",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                       dialog.dispose();         
                            
                    
//                    Model model = ModelFactory.createDefaultModel();
//                    Resource n = model.createResource(ren);
//                    ren=n.getLocalName();
                }
                
                textField.setText(ren);
                textField.selectAll();
            } else if (Mpiro.win.tabbedPane.getSelectedIndex() == 2) { //Lexicon tab
                textField.setText(LexiconPanel.n.toString());
                textField.selectAll();
            } else if (Mpiro.win.tabbedPane.getSelectedIndex() == 3) { //Stories tab
                textField.setText(StoriesTableListener.selectedField);
                textField.selectAll();
            }
        }
        // 6
        customizePanel = new CustomizePanel();
        // 7
        okCancel = new OkCancel();
        
        // Place them in the contentPane of the dialog
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 0, 1, 0);
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        c.gridy = 0;
        contentPane.add(inheritedPanel, c);
        
        if (textFieldTitle != null) {
            c.gridy = 1;
            contentPane.add(textFieldLabel, c);
            c.gridy = 2;
            contentPane.add(textField, c);
        }
        
        c.gridy = 3;
        contentPane.add(listLabel, c);
        
        c.gridy = 4;
        contentPane.add(checkPanel, c);
        
        c.gridy = 5;
        if (customizeVisible == true) {
            contentPane.add(customizePanel, c);
        }
        
        c.gridy = 6;
        contentPane.add(okCancel, c);
        
        // Add the actionListener
        DialogListener listener = new DialogListener();
        customizeButton.addActionListener(listener);
        addButton.addActionListener(listener);
        removeButton.addActionListener(listener);
        okButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
        
        //  ***************************
        textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "pressedEnter"); //theofilos
        textField.getActionMap().put("pressedEnter", pressed_ENTER); //theofilos
        textField.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "pressed_ESC"); //theofilos
        textField.getActionMap().put("pressed_ESC", pressed_ESC); //theofilos
        //   **************************
        
        
        // Make the dialog visible
        dialog.pack();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = this.getSize();
        dialog.setLocation( (screenSize.width - size.width) / 2,
                (screenSize.height - size.height) / 2);
        dialog.setVisible(visible);
    } // constructor
    
    class CustomizePanel
            extends JPanel {
        public CustomizePanel() {
            setLayout(new GridLayout(2, 1));
            setPreferredSize(new Dimension(200, 50));
            customizeButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "customizeAboveList_text"));
            addButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "addAnItemInTheList_text"));
            removeButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "removeSelectedItem_text"));
            //removeButton.setEnabled(false);
            add(customizeButton);
        }
    }
    
    class OkCancel
            extends JPanel {
        public OkCancel() {
            setLayout(new GridLayout(1, 2));
            setPreferredSize(new Dimension(160, 30));
            okButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "ok_button"));
            cancelButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "cancel_button"));
            add(okButton);
            add(cancelButton);
        }
    }
    
    class DialogListener
            implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == customizeButton) {
                customizePanel.removeAll();
                customizePanel.add(addButton);
                customizePanel.add(removeButton);
                customizePanel.revalidate();
                customizePanel.repaint();
            }
            
            if (e.getSource() == addButton) {
                XDialog xd = new XDialog(dialog,
                        LangResources.getString(Mpiro.selectedLocale, "addUpperModelType_text"),
                        LangResources.getString(Mpiro.selectedLocale, "nameOfTheUpperModelType_text"));
            }
            
            if (e.getSource() == removeButton) {
                Vector p = chboli.getPositionsVector();
                
                NodeVector rootVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("Data Base");
                Vector upperTypes = (Vector) rootVector.elementAt(1);
                Vector clone = new Vector(upperTypes);
                int count = 0;
                
                for (int t = 0; t < p.size(); t++) {
                    Integer a = (Integer) p.elementAt(t);
                    int r = a.intValue();
                    clone.setElementAt(new ListData("***"), r);
                }
                
                Enumeration ee = clone.elements();
                while (ee.hasMoreElements()) {
                    ListData ll = (ListData) ee.nextElement();
                    if (ll.toString().equalsIgnoreCase("***")) {
                        int pos = clone.indexOf(ll);
                        String removedUpperType = upperTypes.get(pos - count).toString();
                        upperTypes.removeElementAt(pos - count);
                        //Updating the UpperVectors of all Basic-entity-types
                        for (Enumeration topAChildren = DataBasePanel.topA.children(); topAChildren.hasMoreElements(); ) {
                            String child = topAChildren.nextElement().toString();
                            NodeVector childVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(child);
                            Vector childUpperTypes = (Vector) childVector.elementAt(1);
                            if (childUpperTypes.contains(removedUpperType)) {
                                childUpperTypes.removeElement(removedUpperType);
                            }
                        }
                        count++;
                    }
                }
                chboli = new CheckBoxList(upperTypes);
                dialog.getContentPane().repaint();
            }
            
            if (e.getSource() == okButton) {
                // For all three types of KDialog
                // The total number of items in the list
                int total = chboli.list.getModel().getSize();
                // Loop (until "total") to find which URLs are selected
                for (int x = 0; x < total; x++) {
                    ListData ld = (ListData) chboli.list.getModel().getElementAt(x);
                    if (ld.isSelected()) {
                        isSelected = true;
                    }
                }
                
                /* Specifically for each type of KDialog */
                if (type == "BASIC") {
                    text = textField.getText();
                    int ss = text.length();
                    
                    /* Or if its a basic-type add dialog:
                     * Are the necessary fields filled? */
                    if (isSelected == false || ss == 0) {
                        if (isSelected == false && ss == 0) {
                            new MessageDialog(dialog, MessageDialog.pleaseSpecifyANameAndAtLeastOneUpperModelType_dialog);
                        } else if (isSelected == false) {
                            new MessageDialog(dialog, MessageDialog.pleaseSpecifyAtLeastOneUpperModelType_dialog);
                        } else if (ss == 0) {
                            new MessageDialog(dialog, MessageDialog.pleaseSpecifyAName_dialog);
                        }
                    } else // if a name is given (=text) and
                        // at least one upper-type has been selected
                    {
                        text = textField.getText();
                        
                        String checkValid = Mpiro.win.struc.checkNameValidity(text);
                        int check = Mpiro.win.struc.checkName(text);
                        
                        if ( (check == 1) && (text.equalsIgnoreCase("NewEntityType"))) {
                            new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntityType_dialog);
                        } else if (check == 1) {
                            new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                        } else if (check == 2) {
                            new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                        } else if (check == 3) {
                            new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                        } else if (check == 4) {//kallonis
                            new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                        } else if (check == 5) {//kallonis
                            new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                        }
                        //check the validity of the new name
                        else if (!checkValid.equalsIgnoreCase("VALID")) {
                            new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                    + "\n" + checkValid);
                        } else if (check == 0) {
                            dialog.dispose();
                            DataBasePanel.addBasicEntityType(text);
                            Mpiro.needExportToEmulator = true; //maria
                            Mpiro.needExportToExprimo = true; //maria
                            TreePreviews.setDataBaseTable(DataBasePanel.last.toString());
                        }
                    }
                } // if BASIC
                
                if (type == "UPPER") {
                    // Update the selected node's entry of mainDBHashtable with the chosen upperVector
                    dialog.dispose();
                    currentVector.setElementAt(chboli.getItemsVector(), 1);
                    Mpiro.needExportToExprimo = true; //maria
                } // if UPPER
                
                if (type == "NOUN") {
                    // 1. Update the appropriate nodeVector of mainDBHashtable with the chosen nounVector.
                    // 2. Update current node's children' nodeVectors with the chosen nounVector.
                    // 3. Show the selected nouns in the DataBaseNounPanel.
                    if (commonNouns.isEmpty()) {
                        currentVector.setElementAt(chboli.getItemsVector(), 2);
                        currentVector.nounVector=(Vector) currentVector.elementAt(2);
                        
                        
                        String last= DataBasePanel.last.toString();
                        if(last.substring(0,last.length()-1).endsWith("_occur")) {
                            last=last.substring(0, last.length()-7);
                        }
                        
                        Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
                        while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
                            // Object nextEl=allTypesNames.nextElement();
                            DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                            
                            
                            if ((nextEl.toString().startsWith(last+"_occur")||(nextEl.toString().equalsIgnoreCase(last))) && !(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString()))) {
                                System.out.println("dddddd"+nextEl.toString());
                                Vector noun=getAllInheritedNouns(DataBasePanel.last.toString());
                                /// Vector noun=(Vector) nv1.elementAt(2);
                                Vector nounNew=chboli.getItemsVector();
                                NodeVector nv1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nextEl.toString());
                                //Vector noun=(Vector) nv2.elementAt(2);
                                for(int z=0;z<noun.size();z++) {
                                    if (!nounNew.contains(noun.elementAt(z)))
                                        nounNew.add(noun.elementAt(z));
                                }
                                nv1.setElementAt(nounNew, 2);
                                nv1.nounVector=(Vector) nv1.elementAt(2);
                                Mpiro.win.struc.updateChildrenNounVectors(nextEl,beforeOkNounVector, chboli.getItemsVector());
                            }}
                        //   System.out.println("rrrrrrr"+nounNew.toArray().toString());
                        
                        Mpiro.win.struc.updateChildrenNounVectors(DataBasePanel.last,beforeOkNounVector, chboli.getItemsVector());
                        TreePreviews.dbnp.nounSelected.updateNouns(chboli.getItemsVector());
                        Mpiro.needExportToExprimo = true; //maria
                        dialog.dispose();
                    } else {
                        Vector inheritedVector = new Vector(commonNouns);
                        Vector notInheritedVector = (Vector) chboli.getItemsVector();
                        for (int v = 0; v < notInheritedVector.size(); v++) {
                            inheritedVector.addElement(notInheritedVector.elementAt(v));
                        }
                        currentVector.setElementAt(inheritedVector, 2);
                        currentVector.nounVector=(Vector) currentVector.elementAt(2);
                        String last= DataBasePanel.last.toString();
                        if(last.substring(0,last.length()-1).endsWith("_occur")) {
                            last=last.substring(0, last.length()-7);
                        }
                        
                        Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
                        while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
                            // Object nextEl=allTypesNames.nextElement();
                            DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                            
                            
                            if ((nextEl.toString().startsWith(last+"_occur")||(nextEl.toString().equalsIgnoreCase(last))) && !(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString()))) {
                                Vector noun=getAllInheritedNouns(DataBasePanel.last.toString());
                                /// Vector noun=(Vector) nv1.elementAt(2);
                                Vector nounNew=chboli.getItemsVector();
                                NodeVector nv1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nextEl.toString());
                                //Vector noun=(Vector) nv2.elementAt(2);
                                for(int z=0;z<noun.size();z++) {
                                    if (!nounNew.contains(noun.elementAt(z)))
                                        nounNew.add(noun.elementAt(z));
                                }
                                nv1.setElementAt(nounNew, 2);
                                nv1.nounVector=(Vector) nv1.elementAt(2);
                                Mpiro.win.struc.updateChildrenNounVectors(nextEl,beforeOkNounVector, chboli.getItemsVector());
                            }}
                        Mpiro.win.struc.updateChildrenNounVectors(DataBasePanel.last,beforeOkNounVector, inheritedVector);
                        TreePreviews.dbnp.nounSelected.updateNouns(inheritedVector);
                        Mpiro.needExportToExprimo = true; //maria
                        dialog.dispose();
                    }
                } // if NOUN
                
                if (type == "ENTITY-TYPE") {
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkName(text);
                    
                    if ( (check == 1) && (text.equalsIgnoreCase("NewEntityType"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntityType_dialog);
                    } else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    } else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    } else if (check == 4) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                    } else if (check == 5) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    } else if (check == 0) {
                        dialog.dispose();
                        String lastWithoutOccur= DataBasePanel.last.toString();
                        if (lastWithoutOccur.substring(0,lastWithoutOccur.length()-1).endsWith("_occur"))
                            lastWithoutOccur=lastWithoutOccur.substring(0,lastWithoutOccur.length()-7);
                        //    Enumeration parents=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type").elements();
                        Enumeration alltypes= Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type").keys();
                        
                        while (alltypes.hasMoreElements()){
                            
                            String type=alltypes.nextElement().toString();
                            //  String parent=parents.nextElement().toString();
                            if (type.equalsIgnoreCase(lastWithoutOccur)){
                                DataBasePanel.addEntityType(text,type);
                            }
                            if (type.startsWith(lastWithoutOccur+"_occur")){
                                DataBasePanel.addEntityType(text+type.substring(type.length()-7,type.length()),type);
                            }
                            //        DataBasePanel.addEntityType()
                            
                        }
                        //Mpiro.win.struc.addValueRestriction(text,new ValueRestriction());
                        //DataBasePanel.addEntityType(text);
                        Mpiro.needExportToEmulator = true; //maria
                        Mpiro.needExportToExprimo = true; //maria
                        TreePreviews.setDataBaseTable(DataBasePanel.last.toString());
                    }
                } //if ENTITY-TYPE
                
                ///    if (type == "ADD VALUE"){
                //         DatatypeValue.addValue(textField.getText());
                //       dialog.dispose();
                //}
                
                if (type == "FIELD") {
                    Vector propVector=(Vector) Mpiro.win.struc.getProperty(cbg1.getSelection().getActionCommand());
                    Vector Domain=(Vector) propVector.elementAt(0);
                    if (Domain.contains(Mpiro.win.struc.nameWithoutOccur(DataBasePanel.last.toString()))) {
                        System.out.println("Prop exists");
                        return;
                    }
                    //NodeVector nv=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity("person");
                    FieldData fd=new FieldData(cbg1.getSelection().getActionCommand(),propVector.elementAt(1).toString().replace("]","").replace("[",""),true,"");
                    Mpiro.win.struc.insertExistingRowInDataBaseTable(fd,8);
                    Mpiro.win.struc.addFieldInUserModelHashtable(fd.m_field, DataBasePanel.last.toString());
                  //  QueryProfileHashtable.addFieldInRobotsModelHashtable(fd.m_field, DataBasePanel.last.toString());
                    DataBaseTable.dbTable.revalidate();
                    DataBaseTable.dbTable.repaint();
                    dialog.dispose();
                    
                }
                
                if (type == "TEST") {
                    // String childName =
                    System.out.println("sssssss"+cbg1.getSelection().getActionCommand());
                    createSubClass(cbg1.getSelection().getActionCommand() , DataBasePanel.last.toString());
         /*           Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                    Enumeration allTypesNames=allEntityTypes.keys();
                    int occur=2;
          
                                if (childName.substring(0,childName.length()-1).endsWith("occur"))
              childName=childName.substring(0,childName.length()-7) ;
                    for (;allTypesNames.hasMoreElements();)
                    {
                        String typeName=allTypesNames.nextElement().toString();
          
                        if(typeName.startsWith(childName+"_occur"))
                        {
                            if ( Integer.parseInt(typeName.substring(childName.length()+6, typeName.length()))>=occur)
                            {System.out.println(String.valueOf(occur));
                                occur=Integer.parseInt(typeName.substring(childName.length()+6, typeName.length()))+1;
                            }
                        }
          
                    }
          
          
          
                    text=childName+"_occur"+String.valueOf(occur);
                    System.out.println(text);
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkName(text);
          
                    if ( (check == 1) && (text.equalsIgnoreCase("NewEntityType"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntityType_dialog);
                    }
                    else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    }
                    else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    }
                  //  else if (check == 3) {
                  //      new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                 //   }
                    else if (check == 4) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                    }
                    else if (check == 5) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                    }
                    //check the validity of the new name
            //        else if (!checkValid.equalsIgnoreCase("VALID")) {
            //            new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
            //                              + "\n" + checkValid);
            //        }
                    else if (check == 0) {
                        dialog.dispose();
                        DataBasePanel.addEntityType(text);
                        Mpiro.needExportToEmulator = true; //maria
                        Mpiro.needExportToExprimo = true; //maria
                        TreePreviews.setDataBaseTable(DataBasePanel.last.toString());
                    }
                        //NodeVector nv = new NodeVector(DataBasePanel.last.toString(), "createdNodeName");
                //Mpiro.win.struc.putEntityTypeOrEntityToDB("createdNodeName", nv);
          
                    //Mpiro.win.struc.createSubType(childName, text);
           //         NodeVector nv = new NodeVector(childName);
        //	Vector tableVector = nv.getDatabaseTableVector();
          //      tableVector.addElement(new FieldData(0));
            //    		nv.setElementAt(tableVector, 0);
                    NodeVector entityTypeNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(text);
            NodeVector entityTypeParentNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(childName);
          
            Vector childDatabaseTableVector = entityTypeNode.getDatabaseTableVector();
            Vector parentDatabaseTableVector = entityTypeParentNode.getDatabaseTableVector();
          
            int parentPropertiesNum; //get the number of the inherited properties
          
            //parentPropertiesNum = parentDatabaseTableVector.size();
            for (int g = 0; g < parentDatabaseTableVector.size(); g++) {
                FieldData property = ((FieldData) parentDatabaseTableVector.get(g));
                System.out.println("!!@!@!!!!@111!1"+property.toString()+property.m_field);
          
                for (int h = 0; h < childDatabaseTableVector.size(); h++)
                {
                    FieldData property1 = ((FieldData) childDatabaseTableVector.get(h));
                    //System.out.println("^$#^$%^"+property.m_field+property1.m_field);
                    if (!(property.m_field.equalsIgnoreCase(property1.m_field) ))
                    {//System.out.println("diaforo");
                        if(h<childDatabaseTableVector.size()-1)
                        continue;
                       childDatabaseTableVector.add(property);System.out.println("________"+property.m_field);
                    }
                    else
                     //  System.out.println("iso");
                       break;
          
          
                }
            }
          
          
          
                 Hashtable allEntities = (Hashtable)  Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity");
               //  Enumeration allEntitiesNames=allEntities.keys();
                 Enumeration keyss=allEntities.keys();
                     Iterator valuess=allEntities.values().iterator();
                     //DataBasePanel.last= (DefaultMutableTreeNode) QueryHashtable.
          
                    System.out.println("0::::::"+ DataBasePanel.selpath.getPathComponent(0).toString() );
                    System.out.println("1::::::"+ DataBasePanel.selpath.getPathComponent(1).toString() );
          
                     while(keyss.hasMoreElements())
          
                 {
          
                    // System.out.println("****"+"keys:::"+keyss.nextElement().toString());
                     //System.out.println("****"+"values:::"+valuess.next().toString());
                    if(valuess.next().toString()==childName)
                     {System.out.println("@@@@"+childName);
                         DataBasePanel.addEntity(keyss.nextElement().toString()+"_occur"+String.valueOf(occur),text);
                     }
                     else
                         keyss.nextElement().toString();
                 }
          
                                   Vector children= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(cbg.getSelectedCheckbox().getLabel(),"Entity type");
                    System.out.println("cccccccccccccccccc"+children.toString());
          */
                } //if TEST
                
                if (type == "ANNOTATION") {
                    AnnotationPropertiesPanel.jTable1.setValueAt(cbg1.getSelection().getActionCommand(), AnnotationPropertiesPanel.jTable1.getSelectedRow(),1);
                    dialog.dispose();
                }
                if (type == "SUPER") {
                    dialog.dispose();
                }
                
                if (type == "ENTITY") {
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkName(text);
                    
                    if ( (check == 1) && (text.equalsIgnoreCase("NewEntity"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntity_dialog);
                    } else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    } else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    } else if (check == 4) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                    } else if (check == 5) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    } else if (check == 0) {
                        dialog.dispose();
                        // DataBasePanel.addEntity(text);
                        
                        
                        String nameWithoutOccur=DataBasePanel.last.toString();
                        if (DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-1).endsWith("_occur") ){
                            nameWithoutOccur=DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-7);
                        } //System.out.println("2");
                        DataBasePanel.addEntity(text, nameWithoutOccur);
                        boolean hasMore=true;
                        for(int r=2;hasMore;r++) { //System.out.println("3");
                            Object elem1=null;
                            hasMore=false;
                            DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                            Enumeration lastTypeChildren=first.preorderEnumeration();
                            do{
                                //System.out.println("4");
                                elem1=lastTypeChildren.nextElement();
                                if (elem1.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(r))) {
                                    hasMore=true; //System.out.println("5");
                                    DataBasePanel.addEntity(text+"_occur"+String.valueOf(r),nameWithoutOccur+"_occur"+String.valueOf(r));
                                    break;
                                }
                                
                            }while(lastTypeChildren.hasMoreElements());
                            // if (e)
                        }
                        Mpiro.needExportToEmulator = true; //maria
                        Mpiro.needExportToExprimo = true; //maria
                    }
                } //if ENTITY
                
                if (type == "LEXICON-NOUN") {
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkLexiconName(text);
                    
                    if ( (check == 1) && (text.equalsIgnoreCase("NewNoun"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedNounNode_dialog);
                    } else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    } else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    } else if (check == 0) {
                        dialog.dispose();
                        LexiconPanel.addNoun(text);
                        Mpiro.needExportToEmulator = true; //maria
                        Mpiro.needExportToExprimo = true; //maria
                    }
                } //if LEXICON-NOUN
                
                if (type == "LEXICON-VERB") {
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkLexiconName(text);
                    
                    if ( (check == 1) && (text.equalsIgnoreCase("NewVerb"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedVerbNode_dialog);
                    } else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    } else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    } else if (check == 0) {
                        dialog.dispose();
                        LexiconPanel.addVerb(text);
                        Mpiro.needExportToExprimo = true; //maria
                    }
                } //if LEXICON-VERB
                
                if (type == "USER") {
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkUsersName(text);
                    
                    if ( (check == 1) && (text.equalsIgnoreCase("NewUserType"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedUserNode_dialog);
                    } else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    } else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    } else if (check == 0) {
                        dialog.dispose();
                        UsersPanel.addUser(text);
                        Mpiro.needExportToExprimo = true; //maria
                    }
                } //if USER
                if (type == "ROBOT") {
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkRobotsName(text);
                    
                    if ( (check == 1) && (text.equalsIgnoreCase("NewProfile"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedUserNode_dialog);
                    } else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    } else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    } else if (check == 0) {
                        dialog.dispose();
                        UsersPanel.addRobot(text);
                        Mpiro.needExportToExprimo = true; //maria
                    }
                }
                
                if (type == "RENAME") {
                    if (Mpiro.win.tabbedPane.getSelectedIndex() == 0) { //Users tab
                        // Check if the new name already exists
                        text = textField.getText();
                        
                        String checkValid = Mpiro.win.struc.checkNameValidity(text);
                        String type="user";
                        if(UsersPanel.last.getUserObject() instanceof IconData){
                            IconData id = (IconData)UsersPanel.last.getUserObject();
                            Icon ii = id.getIcon();
                            ImageIcon im = (ImageIcon)ii;
                            //TreePreviews.setUsersPanel(last.toString());
                            if (im == UsersPanel.ICON_ROBOT)
                                type="robot";
                        }
                        
                        int check = Mpiro.win.struc.checkUsersName(text);
                        if(type.equals("robot"))
                            check = Mpiro.win.struc.checkRobotsName(text);
                        
                        
                        if (check == 1) {
                            if (!text.equalsIgnoreCase(UsersPanel.last.toString())) {
                                new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                            } else { // if new name equals old name: do nothing!
                                dialog.dispose();
                            }
                        } // end if check==1
                        else if (check == 2) {
                            new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                        } else if (check == 3) {
                            new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                        }
                        //check the validity of the new name
                        else if (!checkValid.equalsIgnoreCase("VALID")) {
                            new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                    + "\n" + checkValid);
                        }
                        //if all checks pass
                        else if (check == 0 && type.equals("user")) {
                            // Rename the user entry
                            Mpiro.win.struc.renameUser(UsersPanel.last.toString(), text);
                            Mpiro.win.struc.updateIndependentLexiconHashtable(text, UsersPanel.last.toString(), "RENAME");
                            Mpiro.win.struc.updateAppropriatenessValuesInMicroplanningOfFields(text, UsersPanel.last.toString(), "RENAME");
                            Mpiro.win.struc.renameUserInUserOrRobotModelHashtable(UsersPanel.last.toString(), text);
                            Mpiro.win.struc.renameUserInAnnotationsHashtable(UsersPanel.last.toString(), text);
                           // QueryProfileHashtable.renameUserInUserModelStoryHashtable(UsersPanel.last.toString(), text);
                            Mpiro.win.struc.renameUserInPropertiesHashtable(UsersPanel.last.toString(), text);
                            //    Mpiro.win.struc.renameUserInPropertiesHashtable(UsersPanel.last.toString(), text);
                            Mpiro.needExportToEmulator = true; //maria
                            lastNode = UsersPanel.last;
                            Object obj = (Object) (lastNode.getUserObject());
                            lastNode.setUserObject(new IconData(UsersPanel.ICON_USER, text));
                            UsersPanel.userstree.revalidate();
                            UsersPanel.userstree.repaint();
                            // Finally close the dialog-box
                            dialog.dispose();
                            // ... and update DB view
                            UsersPanel.clearDBPanels();
                        } // end if check==0
                        else if (check == 0 && type.equals("robot")) {
                            // Rename the user entry
                            Mpiro.win.struc.renameRobot(UsersPanel.last.toString(), text);
                            Mpiro.win.struc.renameRobotInRobotCharVector(UsersPanel.last.toString(), text);
                            Mpiro.win.struc.renameUserInUserOrRobotModelHashtable(UsersPanel.last.toString(), text);
                            //QueryProfileHashtable.updateIndependentLexiconHashtable(text, UsersPanel.last.toString(), "RENAME");
                            //Mpiro.win.struc.updateAppropriatenessValuesInMicroplanningOfFields(text, UsersPanel.last.toString(), "RENAME");
                            //Mpiro.win.struc.renameUserInUserOrRobotModelHashtable(UsersPanel.last.toString(), text);
                            //QueryProfileHashtable.renameUserInUserModelStoryHashtable(UsersPanel.last.toString(), text);
                            //Mpiro.win.struc.renameUserInPropertiesHashtable(UsersPanel.last.toString(), text);
                            Mpiro.win.struc.renameRobotInPropertiesHashtable(UsersPanel.last.toString(), text);
                            Mpiro.needExportToEmulator = true; //maria
                            lastNode = UsersPanel.last;
                            Object obj = (Object) (lastNode.getUserObject());
                            lastNode.setUserObject(new IconData(UsersPanel.ICON_ROBOT, text));
                            UsersPanel.userstree.revalidate();
                            UsersPanel.userstree.repaint();
                            // Finally close the dialog-box
                            dialog.dispose();
                            // ... and update DB view
                            UsersPanel.clearDBPanels();
                        } // end if check==0
                    }
                    
                    else if (Mpiro.win.tabbedPane.getSelectedIndex() == 1) { //database tab
                        text = textField.getText();
                        
                        String checkValid = Mpiro.win.struc.checkNameValidity(text);
                        int check = Mpiro.win.struc.checkName(text);
                        
                        // Check if the new name already exists
                        if (check == 1) {
                            if (!text.equalsIgnoreCase(DataBasePanel.last.toString())) {
                                new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                            } else { // if new name equals old name: do nothing!
                                dialog.dispose();
                            }
                        } // end if check==1
                        else if (check == 2) {
                            new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                        } else if (check == 3) {
                            new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                        } else if (check == 4) {//kallonis
                            new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                        } else if (check == 5) {//kallonis
                            new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                        }
                        //check the validity of the new name
                        else if (!checkValid.equalsIgnoreCase("VALID")) {
                            new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                    + "\n" + checkValid);
                        }
                        //if all checks pass
                        else if (check == 0) {
                            //System.out.println("%%%%%%%%%%%");
                            // Rename all oldname instances in mainUserModelHashtable
                            //System.out.println("1");
                            String nameWithoutOccur=DataBasePanel.last.toString();
                            if (DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-1).endsWith("_occur") ){
                                nameWithoutOccur=DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-7);
                            } //System.out.println("2");
                            String p=new String("");
//                            if(nameWithoutOccur.contains("http://")){
//                                
//                                Model model = ModelFactory.createDefaultModel();
//                                Resource n = model.createResource(nameWithoutOccur);
//                                p=n.getNameSpace();
//                           }
                            renameEntity(p+text,nameWithoutOccur);
                            boolean hasMore=true;
                            for(int r=2;hasMore;r++) { //System.out.println("3");
                                Object elem1=null;
                                hasMore=false;
                                DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                                Enumeration lastTypeChildren=first.preorderEnumeration();
                                do{
                                    //System.out.println("4");
                                    elem1=lastTypeChildren.nextElement();
                                    if (elem1.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(r))) {
                                        hasMore=true; //System.out.println("5");
                                        renameEntity(text+"_occur"+String.valueOf(r),nameWithoutOccur+"_occur"+String.valueOf(r));
                                        break;
                                    }
                                    
                                }while(lastTypeChildren.hasMoreElements());
                                // if (e)
                            }
                            
//      while (lastTypeChildren.hasMoreElements())
                            // {
                            //     elem1=lastTypeChildren.nextElement();
                            ////System.out.println("QQQQQQQQ"+elem1);
                            //     if (elem1.toString().equalsIgnoreCase(nameWithoutOccur+"occur_2")) {
                            // node= (DefaultMutableTreeNode) elem1;
                            //         break;
                            //     }
                            // //System.out.println(parent);
                            // }
                            
                            
                        } // end if check==0
                        
                    } else if (Mpiro.win.tabbedPane.getSelectedIndex() == 2) { //Lexicon tab
                        // Check if the new name already exists
                        text = textField.getText();
                        
                        String checkValid = Mpiro.win.struc.checkNameValidity(text);
                        int check = Mpiro.win.struc.checkLexiconName(text);
                        
                        if (check == 1) {
                            if (!text.equalsIgnoreCase(LexiconPanel.n.toString())) {
                                new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                            } else { // if new name equals old name: do nothing!
                                dialog.dispose();
                            }
                        } // end if check==1
                        else if (check == 2) {
                            new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                        } else if (check == 3) {
                            new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                        }
                        //check the validity of the new name
                        else if (!checkValid.equalsIgnoreCase("VALID")) {
                            new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                    + "\n" + checkValid);
                        }
                        //if all checks pass
                        else if (check == 0) {
                            // Rename the Lexicon entry
                            Mpiro.win.struc.renameLexiconEntry(LexiconPanel.n.toString(), text);
                            Mpiro.needExportToEmulator = true; //maria
                            Mpiro.needExportToExprimo = true; //maria
                            lastNode = LexiconPanel.n;
                            Object obj = (Object) (lastNode.getUserObject());
                            if (obj instanceof IconData) {
                                IconData id = (IconData) obj;
                                Icon i = id.getIcon();
                                ImageIcon ii = (ImageIcon) i;
                                if (ii == LexiconPanel.ICON_N) {
                                    lastNode.setUserObject(new IconData(LexiconPanel.ICON_N, text));
                                } else if (ii == LexiconPanel.ICON_V) {
                                    lastNode.setUserObject(new IconData(LexiconPanel.ICON_V, text));
                                }
                            }
                            LexiconPanel.lexicontree.revalidate();
                            LexiconPanel.lexicontree.repaint();
                            // Finally close the dialog-box
                            dialog.dispose();
                            // ... and update DB view
                            LexiconPanel.clearDBPanels();
                        } // end if check==0
                    }
                    
//                    else if (Mpiro.win.tabbedPane.getSelectedIndex() == 3) {
//                        text = textField.getText();
//                        String oldStory = StoriesTableListener.selectedField;
//                        
//                        String checkName = Mpiro.win.struc.checkNameValidity(text);
//                        Vector storyNamesVector = new Vector();
//                        Enumeration enumer = StoriesTable.m_data.m_vector.elements();
//                        while (enumer.hasMoreElements()) {
//                            Vector rowVector = (Vector) enumer.nextElement();
//                            if (rowVector.elementAt(0) != null) {
//                                String storyName = rowVector.get(0).toString();
//                                storyNamesVector.addElement(storyName);
//                            }
//                        }
//                        
//                        if ( (storyNamesVector.contains(text)) && (! (text.equalsIgnoreCase(oldStory)))) {
//                            new MessageDialog(StoriesTable.storiesTable, MessageDialog.thisStoryNameAlreadyExists_dialog);
//                        } else if (text.indexOf(" ") > 0 || text.startsWith(" ")) {
//                            new MessageDialog(StoriesTable.storiesTable, MessageDialog.noSpacesAreAllowedForAStoryName_dialog);
//                        } else if (text.equalsIgnoreCase("")) {
//                            new MessageDialog(StoriesTable.storiesTable, MessageDialog.pleaseGiveANameForTheStory_dialog);
//                        } else if (!checkName.equalsIgnoreCase("VALID")) {
//                            new MessageDialog(StoriesTable.storiesTable, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog + "\n" + checkName);
//                        } else {
//                            StoriesTable.storiesTable.setValueAt(text, StoriesTableListener.rowNo, 0);
//                            ////System.out.println("(oldStory)---- " + oldStory);
//                            ////System.out.println("(newStory)---- " + text);
//                            /* Update mainDBHashtable */
//                            QueryHashtable.renameHashtableStory(StoriesPanel.last.toString(), oldStory, text);
//                            
//                            // Update mainUserModelStoryHashtable
//                            if ( (oldStory.equalsIgnoreCase("New-story")) && (!text.equalsIgnoreCase("New-story"))) {
//                               // QueryProfileHashtable.addStoryInUserModelStoryHashtable(text, StoriesPanel.last.toString());
//                            } else if ( (!oldStory.equalsIgnoreCase("New-story")) && (!text.equalsIgnoreCase("New-story"))) {
//                               // QueryProfileHashtable.renameStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), oldStory, text);
//                            } else if (text.equalsIgnoreCase("New-story")) {
//                             //   QueryProfileHashtable.removeStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), oldStory);
//                            } else {
//                                System.out.println("(ALERT)---- " + oldStory + "  ==  " + text);
//                            }
//                        }
//                        dialog.dispose();
//                    }
                    
                } // if RENAME
                
                
            }
            
            if (e.getSource() == cancelButton) {
                dialog.dispose();
            }
        } // actionPerformed
    } // class DialogListener
    
    //  ****************************************
    Action pressed_ENTER = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            // For all three types of KDialog
            // The total number of items in the list
            int total = chboli.list.getModel().getSize();
            // Loop (until "total") to find which URLs are selected
            for (int x = 0; x < total; x++) {
                ListData ld = (ListData) chboli.list.getModel().getElementAt(x);
                if (ld.isSelected()) {
                    isSelected = true;
                }
            }
            
            /* Specifically for each type of KDialog */
            if (type == "BASIC") {
                text = textField.getText();
                int ss = text.length();
                
                /* Or if its a basic-type add dialog:
                 * Are the necessary fields filled? */
                if (isSelected == false || ss == 0) {
                    if (isSelected == false && ss == 0) {
                        new MessageDialog(dialog, MessageDialog.pleaseSpecifyANameAndAtLeastOneUpperModelType_dialog);
                    } else if (isSelected == false) {
                        new MessageDialog(dialog, MessageDialog.pleaseSpecifyAtLeastOneUpperModelType_dialog);
                    } else if (ss == 0) {
                        new MessageDialog(dialog, MessageDialog.pleaseSpecifyAName_dialog);
                    }
                } else // if a name is given (=text) and
                    // at least one upper-type has been selected
                {
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkName(text);
                    
                    if ( (check == 1) && (text.equalsIgnoreCase("NewEntityType"))) {
                        new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntityType_dialog);
                    } else if (check == 1) {
                        new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                    } else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    } else if (check == 4) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                    } else if (check == 5) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    } else if (check == 0) {
                        dialog.dispose();
                        DataBasePanel.addBasicEntityType(text);
                        Mpiro.needExportToEmulator = true; //maria
                        Mpiro.needExportToExprimo = true; //maria
                        TreePreviews.setDataBaseTable(DataBasePanel.last.toString());
                    }
                }
            } // if BASIC
            
            if (type == "TEST") {
                // String childName =
                System.out.println("sssssss"+cbg1.getSelection().getActionCommand());
                createSubClass(cbg1.getSelection().getActionCommand() , DataBasePanel.last.toString());}
            if (type == "ANNOTATION") {
                AnnotationPropertiesPanel.jTable1.setValueAt(cbg1.getSelection().getActionCommand(), AnnotationPropertiesPanel.jTable1.getSelectedRow(),1);
                dialog.dispose();
            }
            
            if (type == "UPPER") {
                // Update the selected node's entry of mainDBHashtable with the chosen upperVector
                dialog.dispose();
                currentVector.setElementAt(chboli.getItemsVector(), 1);
                Mpiro.needExportToExprimo = true; //maria
            } // if UPPER
            
            if (type == "NOUN") {
                // 1. Update the appropriate nodeVector of mainDBHashtable with the chosen nounVector.
                // 2. Update current node's children' nodeVectors with the chosen nounVector.
                // 3. Show the selected nouns in the DataBaseNounPanel.
                if (commonNouns.isEmpty()) {
                    currentVector.setElementAt(chboli.getItemsVector(), 2);
                    currentVector.nounVector=(Vector) currentVector.elementAt(2);
                    String last= DataBasePanel.last.toString();
                    if(last.substring(0,last.length()-1).endsWith("_occur")) {
                        last=last.substring(0, last.length()-7);
                    }
                    
                    Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
                    while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
                        // Object nextEl=allTypesNames.nextElement();
                        DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                        
                        
                        if ((nextEl.toString().startsWith(last+"_occur")||(nextEl.toString().equalsIgnoreCase(last))) && !(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString()))) {
                            System.out.println("%%%%%%%"+nextEl.toString());
                            
                            Vector noun=getAllInheritedNouns(DataBasePanel.last.toString());
                            /// Vector noun=(Vector) nv1.elementAt(2);
                            Vector nounNew=chboli.getItemsVector();
                            NodeVector nv1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nextEl.toString());
                            //Vector noun=(Vector) nv2.elementAt(2);
                            for(int z=0;z<noun.size();z++) {
                                if (!nounNew.contains(noun.elementAt(z)))
                                    nounNew.add(noun.elementAt(z));
                            }
                            nv1.setElementAt(nounNew, 2);
                            nv1.nounVector=(Vector) nv1.elementAt(2);
                            Mpiro.win.struc.updateChildrenNounVectors(nextEl,beforeOkNounVector, chboli.getItemsVector());
                        }}
                    Mpiro.win.struc.updateChildrenNounVectors(DataBasePanel.last,beforeOkNounVector, chboli.getItemsVector());
                    TreePreviews.dbnp.nounSelected.updateNouns(chboli.getItemsVector());
                    Mpiro.needExportToExprimo = true; //maria
                    dialog.dispose();
                } else {
                    Vector inheritedVector = new Vector(commonNouns);
                    Vector notInheritedVector = (Vector) chboli.getItemsVector();
                    for (int v = 0; v < notInheritedVector.size(); v++) {
                        inheritedVector.addElement(notInheritedVector.elementAt(v));
                    }
                    currentVector.setElementAt(inheritedVector, 2);
                    currentVector.nounVector=(Vector) currentVector.elementAt(2);
                    String last= DataBasePanel.last.toString();
                    if(last.substring(0,last.length()-1).endsWith("_occur")) {
                        last=last.substring(0, last.length()-7);
                    }
                    
                    Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
                    while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
                        // Object nextEl=allTypesNames.nextElement();
                        DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                        
                        
                        if ((nextEl.toString().startsWith(last+"_occur")||(nextEl.toString().equalsIgnoreCase(last))) && !(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString()))) {
                            Vector noun=getAllInheritedNouns(DataBasePanel.last.toString());
                            /// Vector noun=(Vector) nv1.elementAt(2);
                            Vector nounNew=chboli.getItemsVector();
                            NodeVector nv1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nextEl.toString());
                            //Vector noun=(Vector) nv2.elementAt(2);
                            for(int z=0;z<noun.size();z++) {
                                if (!nounNew.contains(noun.elementAt(z)))
                                    nounNew.add(noun.elementAt(z));
                            }
                            nv1.setElementAt(nounNew, 2);
                            nv1.nounVector=(Vector) nv1.elementAt(2);
                            Mpiro.win.struc.updateChildrenNounVectors(nextEl,beforeOkNounVector, chboli.getItemsVector());
                        }}
                    Mpiro.win.struc.updateChildrenNounVectors(DataBasePanel.last,beforeOkNounVector, inheritedVector);
                    TreePreviews.dbnp.nounSelected.updateNouns(inheritedVector);
                    Mpiro.needExportToExprimo = true; //maria
                    dialog.dispose();
                }
            } // if NOUN
            
            if (type == "ENTITY-TYPE") {
                text = textField.getText();
                
                String checkValid = Mpiro.win.struc.checkNameValidity(text);
                int check = Mpiro.win.struc.checkName(text);
                
                if ( (check == 1) && (text.equalsIgnoreCase("NewEntityType"))) {
                    new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntityType_dialog);
                } else if (check == 1) {
                    new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                } else if (check == 2) {
                    new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                } else if (check == 3) {
                    new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                } else if (check == 4) {//kallonis
                    new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                } else if (check == 5) {//kallonis
                    new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                }
                //check the validity of the new name
                else if (!checkValid.equalsIgnoreCase("VALID")) {
                    new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                            + "\n" + checkValid);
                } else if (check == 0) {
                    dialog.dispose();
                    DataBasePanel.addEntityType(text);
                    Mpiro.needExportToEmulator = true; //maria
                    Mpiro.needExportToExprimo = true; //maria
                    TreePreviews.setDataBaseTable(DataBasePanel.last.toString());
                }
            } //if ENTITY-TYPE
            
            //      if(type == "ADD VALUE"){
            //        DatatypeValue.addValue(textField.getText());
            //      dialog.dispose();
            // }
            
            if (type == "ENTITY") {
                text = textField.getText();
                
                String checkValid = Mpiro.win.struc.checkNameValidity(text);
                int check = Mpiro.win.struc.checkName(text);
                
                if ( (check == 1) && (text.equalsIgnoreCase("NewEntity"))) {
                    new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntity_dialog);
                } else if (check == 1) {
                    new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                } else if (check == 2) {
                    new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                } else if (check == 3) {
                    new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                } else if (check == 4) {//kallonis
                    new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                } else if (check == 5) {//kallonis
                    new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                }
                //check the validity of the new name
                else if (!checkValid.equalsIgnoreCase("VALID")) {
                    new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                            + "\n" + checkValid);
                } else if (check == 0) {
                    dialog.dispose();
                    // DataBasePanel.addEntity(text);
                    
                    
                    String nameWithoutOccur=DataBasePanel.last.toString();
                    if (DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-1).endsWith("_occur") ){
                        nameWithoutOccur=DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-7);
                    } //System.out.println("2");
                    DataBasePanel.addEntity(text, nameWithoutOccur);
                    boolean hasMore=true;
                    for(int r=2;hasMore;r++) { //System.out.println("3");
                        Object elem1=null;
                        hasMore=false;
                        DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                        Enumeration lastTypeChildren=first.preorderEnumeration();
                        do{
                            //System.out.println("4");
                            elem1=lastTypeChildren.nextElement();
                            if (elem1.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(r))) {
                                hasMore=true; //System.out.println("5");
                                DataBasePanel.addEntity(text+"_occur"+String.valueOf(r),nameWithoutOccur+"_occur"+String.valueOf(r));
                                break;
                            }
                            
                        }while(lastTypeChildren.hasMoreElements());
                        // if (e)
                    }
                    Mpiro.needExportToEmulator = true; //maria
                    Mpiro.needExportToExprimo = true; //maria
                }
            } //if ENTITY
            
            if (type == "LEXICON-NOUN") {
                text = textField.getText();
                
                String checkValid = Mpiro.win.struc.checkNameValidity(text);
                int check = Mpiro.win.struc.checkLexiconName(text);
                
                if ( (check == 1) && (text.equalsIgnoreCase("NewNoun"))) {
                    new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedNounNode_dialog);
                } else if (check == 1) {
                    new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                } else if (check == 2) {
                    new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                } else if (check == 3) {
                    new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                }
                //check the validity of the new name
                else if (!checkValid.equalsIgnoreCase("VALID")) {
                    new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                            + "\n" + checkValid);
                } else if (check == 0) {
                    dialog.dispose();
                    LexiconPanel.addNoun(text);
                    Mpiro.needExportToEmulator = true; //maria
                    Mpiro.needExportToExprimo = true; //maria
                }
            } //if LEXICON-NOUN
            
            if (type == "LEXICON-VERB") {
                text = textField.getText();
                
                String checkValid = Mpiro.win.struc.checkNameValidity(text);
                int check = Mpiro.win.struc.checkLexiconName(text);
                
                if ( (check == 1) && (text.equalsIgnoreCase("NewVerb"))) {
                    new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedVerbNode_dialog);
                } else if (check == 1) {
                    new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                } else if (check == 2) {
                    new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                } else if (check == 3) {
                    new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                }
                //check the validity of the new name
                else if (!checkValid.equalsIgnoreCase("VALID")) {
                    new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                            + "\n" + checkValid);
                } else if (check == 0) {
                    dialog.dispose();
                    LexiconPanel.addVerb(text);
                    Mpiro.needExportToExprimo = true; //maria
                }
            } //if LEXICON-VERB
            
            if (type == "USER") {
                text = textField.getText();
                
                String checkValid = Mpiro.win.struc.checkNameValidity(text);
                int check = Mpiro.win.struc.checkUsersName(text);
                
                if ( (check == 1) && (text.equalsIgnoreCase("NewUserType"))) {
                    new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedUserNode_dialog);
                } else if (check == 1) {
                    new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                } else if (check == 2) {
                    new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                } else if (check == 3) {
                    new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                }
                //check the validity of the new name
                else if (!checkValid.equalsIgnoreCase("VALID")) {
                    new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                            + "\n" + checkValid);
                } else if (check == 0) {
                    dialog.dispose();
                    UsersPanel.addUser(text);
                    Mpiro.needExportToEmulator = true; //maria
                }
            } //if USER
            
            
            if (type == "ROBOT") {
                text = textField.getText();
                
                String checkValid = Mpiro.win.struc.checkNameValidity(text);
                int check = Mpiro.win.struc.checkRobotsName(text);
                
                if ( (check == 1) && (text.equalsIgnoreCase("NewProfile"))) {
                    new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedUserNode_dialog);
                } else if (check == 1) {
                    new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                } else if (check == 2) {
                    new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                } else if (check == 3) {
                    new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                }
                //check the validity of the new name
                else if (!checkValid.equalsIgnoreCase("VALID")) {
                    new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                            + "\n" + checkValid);
                } else if (check == 0) {
                    dialog.dispose();
                    UsersPanel.addRobot(text);
                    Mpiro.needExportToExprimo = true; //maria
                }
            }
            if (type == "RENAME") {
                if (Mpiro.win.tabbedPane.getSelectedIndex() == 0) { //Users tab
                    // Check if the new name already exists
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    String type="user";
                    if(UsersPanel.last.getUserObject() instanceof IconData){
                        IconData id = (IconData)UsersPanel.last.getUserObject();
                        Icon ii = id.getIcon();
                        ImageIcon im = (ImageIcon)ii;
                        //TreePreviews.setUsersPanel(last.toString());
                        if (im == UsersPanel.ICON_ROBOT)
                            type="robot";
                    }
                    
                    int check = Mpiro.win.struc.checkUsersName(text);
                    if(type.equals("robot"))
                        check = Mpiro.win.struc.checkRobotsName(text);
                    
                    
                    if (check == 1) {
                        if (!text.equalsIgnoreCase(UsersPanel.last.toString())) {
                            new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                        } else { // if new name equals old name: do nothing!
                            dialog.dispose();
                        }
                    } // end if check==1
                    else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    }
                    //if all checks pass
                    else if (check == 0 && type.equals("user")) {
                        // Rename the user entry
                        Mpiro.win.struc.renameUser(UsersPanel.last.toString(), text);
                        Mpiro.win.struc.updateIndependentLexiconHashtable(text, UsersPanel.last.toString(), "RENAME");
                        Mpiro.win.struc.updateAppropriatenessValuesInMicroplanningOfFields(text, UsersPanel.last.toString(), "RENAME");
                        Mpiro.win.struc.renameUserInUserOrRobotModelHashtable(UsersPanel.last.toString(), text);
                        Mpiro.win.struc.renameUserInUserOrRobotModelHashtable(UsersPanel.last.toString(), text);
                       // QueryProfileHashtable.renameUserInUserModelStoryHashtable(UsersPanel.last.toString(), text);
                        Mpiro.win.struc.renameUserInPropertiesHashtable(UsersPanel.last.toString(), text);
                        //    Mpiro.win.struc.renameUserInPropertiesHashtable(UsersPanel.last.toString(), text);
                        Mpiro.needExportToEmulator = true; //maria
                        lastNode = UsersPanel.last;
                        Object obj = (Object) (lastNode.getUserObject());
                        lastNode.setUserObject(new IconData(UsersPanel.ICON_USER, text));
                        UsersPanel.userstree.revalidate();
                        UsersPanel.userstree.repaint();
                        // Finally close the dialog-box
                        dialog.dispose();
                        // ... and update DB view
                        UsersPanel.clearDBPanels();
                    } // end if check==0
                    else if (check == 0 && type.equals("robot")) {
                        // Rename the user entry
                        Mpiro.win.struc.renameRobot(UsersPanel.last.toString(), text);
                        Mpiro.win.struc.renameRobotInRobotCharVector(UsersPanel.last.toString(), text);
                        //QueryProfileHashtable.updateIndependentLexiconHashtable(text, UsersPanel.last.toString(), "RENAME");
                        //Mpiro.win.struc.updateAppropriatenessValuesInMicroplanningOfFields(text, UsersPanel.last.toString(), "RENAME");
                        Mpiro.win.struc.renameUserInUserOrRobotModelHashtable(UsersPanel.last.toString(), text);
                        //QueryProfileHashtable.renameUserInUserModelStoryHashtable(UsersPanel.last.toString(), text);
                        //Mpiro.win.struc.renameUserInPropertiesHashtable(UsersPanel.last.toString(), text);
                        Mpiro.win.struc.renameRobotInPropertiesHashtable(UsersPanel.last.toString(), text);
                        Mpiro.needExportToEmulator = true; //maria
                        lastNode = UsersPanel.last;
                        Object obj = (Object) (lastNode.getUserObject());
                        lastNode.setUserObject(new IconData(UsersPanel.ICON_ROBOT, text));
                        UsersPanel.userstree.revalidate();
                        UsersPanel.userstree.repaint();
                        // Finally close the dialog-box
                        dialog.dispose();
                        // ... and update DB view
                        UsersPanel.clearDBPanels();
                    } // end if check==0
                }
                
                else if (Mpiro.win.tabbedPane.getSelectedIndex() == 1) { //database tab
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkName(text);
                    
                    // Check if the new name already exists
                    if (check == 1) {
                        if (!text.equalsIgnoreCase(DataBasePanel.last.toString())) {
                            new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                        } else { // if new name equals old name: do nothing!
                            dialog.dispose();
                        }
                    } // end if check==1
                    else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    } else if (check == 4) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
                    } else if (check == 5) {//kallonis
                        new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    }
                    //if all checks pass
                    else if (check == 0) {
                        //System.out.println("%%%%%%%%%%%");
                        // Rename all oldname instances in mainUserModelHashtable
                        //System.out.println("1");
                        String nameWithoutOccur=DataBasePanel.last.toString();
                        if (DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-1).endsWith("_occur") ){
                            nameWithoutOccur=DataBasePanel.last.toString().substring(0, DataBasePanel.last.toString().length()-7);
                        } //System.out.println("2");
                        renameEntity(text,nameWithoutOccur);
                        boolean hasMore=true;
                        for(int r=2;hasMore;r++) { //System.out.println("3");
                            Object elem1=null;
                            hasMore=false;
                            DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
                            Enumeration lastTypeChildren=first.preorderEnumeration();
                            do{
                                //System.out.println("4");
                                elem1=lastTypeChildren.nextElement();
                                if (elem1.toString().equalsIgnoreCase(nameWithoutOccur+"_occur"+String.valueOf(r))) {
                                    hasMore=true; //System.out.println("5");
                                    renameEntity(text+"_occur"+String.valueOf(r),nameWithoutOccur+"_occur"+String.valueOf(r));
                                    break;
                                }
                                
                            }while(lastTypeChildren.hasMoreElements());
                            // if (e)
                        }
                        
//      while (lastTypeChildren.hasMoreElements())
                        // {
                        //     elem1=lastTypeChildren.nextElement();
                        ////System.out.println("QQQQQQQQ"+elem1);
                        //     if (elem1.toString().equalsIgnoreCase(nameWithoutOccur+"occur_2")) {
                        // node= (DefaultMutableTreeNode) elem1;
                        //         break;
                        //     }
                        // //System.out.println(parent);
                        // }
                        
                        
                    } // end if check==0
                    
                } else if (Mpiro.win.tabbedPane.getSelectedIndex() == 2) { //Lexicon tab
                    // Check if the new name already exists
                    text = textField.getText();
                    
                    String checkValid = Mpiro.win.struc.checkNameValidity(text);
                    int check = Mpiro.win.struc.checkLexiconName(text);
                    
                    if (check == 1) {
                        if (!text.equalsIgnoreCase(LexiconPanel.n.toString())) {
                            new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
                        } else { // if new name equals old name: do nothing!
                            dialog.dispose();
                        }
                    } // end if check==1
                    else if (check == 2) {
                        new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
                    } else if (check == 3) {
                        new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
                    }
                    //check the validity of the new name
                    else if (!checkValid.equalsIgnoreCase("VALID")) {
                        new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
                                + "\n" + checkValid);
                    }
                    //if all checks pass
                    else if (check == 0) {
                        // Rename the Lexicon entry
                        Mpiro.win.struc.renameLexiconEntry(LexiconPanel.n.toString(), text);
                        Mpiro.needExportToEmulator = true; //maria
                        Mpiro.needExportToExprimo = true; //maria
                        lastNode = LexiconPanel.n;
                        Object obj = (Object) (lastNode.getUserObject());
                        if (obj instanceof IconData) {
                            IconData id = (IconData) obj;
                            Icon i = id.getIcon();
                            ImageIcon ii = (ImageIcon) i;
                            if (ii == LexiconPanel.ICON_N) {
                                lastNode.setUserObject(new IconData(LexiconPanel.ICON_N, text));
                            } else if (ii == LexiconPanel.ICON_V) {
                                lastNode.setUserObject(new IconData(LexiconPanel.ICON_V, text));
                            }
                        }
                        LexiconPanel.lexicontree.revalidate();
                        LexiconPanel.lexicontree.repaint();
                        // Finally close the dialog-box
                        dialog.dispose();
                        // ... and update DB view
                        LexiconPanel.clearDBPanels();
                    } // end if check==0
                }
                
//                else if (Mpiro.win.tabbedPane.getSelectedIndex() == 3) {
//                    text = textField.getText();
//                    String oldStory = StoriesTableListener.selectedField;
//                    
//                    String checkName = Mpiro.win.struc.checkNameValidity(text);
//                    Vector storyNamesVector = new Vector();
//                    Enumeration enumer = StoriesTable.m_data.m_vector.elements();
//                    while (enumer.hasMoreElements()) {
//                        Vector rowVector = (Vector) enumer.nextElement();
//                        if (rowVector.elementAt(0) != null) {
//                            String storyName = rowVector.get(0).toString();
//                            storyNamesVector.addElement(storyName);
//                        }
//                    }
//                    
//                    if ( (storyNamesVector.contains(text)) && (! (text.equalsIgnoreCase(oldStory)))) {
//                        new MessageDialog(StoriesTable.storiesTable, MessageDialog.thisStoryNameAlreadyExists_dialog);
//                    } else if (text.indexOf(" ") > 0 || text.startsWith(" ")) {
//                        new MessageDialog(StoriesTable.storiesTable, MessageDialog.noSpacesAreAllowedForAStoryName_dialog);
//                    } else if (text.equalsIgnoreCase("")) {
//                        new MessageDialog(StoriesTable.storiesTable, MessageDialog.pleaseGiveANameForTheStory_dialog);
//                    } else if (!checkName.equalsIgnoreCase("VALID")) {
//                        new MessageDialog(StoriesTable.storiesTable, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog + "\n" + checkName);
//                    } else {
//                        StoriesTable.storiesTable.setValueAt(text, StoriesTableListener.rowNo, 0);
//                        ////System.out.println("(oldStory)---- " + oldStory);
//                        ////System.out.println("(newStory)---- " + text);
//                        /* Update mainDBHashtable */
//                        QueryHashtable.renameHashtableStory(StoriesPanel.last.toString(), oldStory, text);
//                        
//                        // Update mainUserModelStoryHashtable
//                        if ( (oldStory.equalsIgnoreCase("New-story")) && (!text.equalsIgnoreCase("New-story"))) {
//                          //  QueryProfileHashtable.addStoryInUserModelStoryHashtable(text, StoriesPanel.last.toString());
//                        } else if ( (!oldStory.equalsIgnoreCase("New-story")) && (!text.equalsIgnoreCase("New-story"))) {
//                           // QueryProfileHashtable.renameStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), oldStory, text);
//                        } else if (text.equalsIgnoreCase("New-story")) {
//                            QueryProfileHashtable.removeStoryInUserModelStoryHashtable(StoriesPanel.last.toString(), oldStory);
//                        } else {
//                            System.out.println("(ALERT)---- " + oldStory + "  ==  " + text);
//                        }
//                    }
//                    dialog.dispose();
//                }
                
            } // if RENAME
        }
        
    };
    
    Action pressed_ESC = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            dialog.dispose();
        }
    };
    public void renameEntity(String text) {
        // Rename all oldname instances in mainUserModelHashtable
        Mpiro.win.struc.renameEntityTypeOrEntityInUserModelHashtable(DataBasePanel.last.toString(), text);
       // QueryProfileHashtable.renameEntityTypeOrEntityInRobotsModelHashtable(DataBasePanel.last.toString(), text);
        // Rename all oldname instances in mainUserModelStoryHashtable
        //QueryProfileHashtable.renameEntityTypeOrEntityInUserModelStoryHashtable(DataBasePanel.last.toString(), text);
        // Rename all instances of the node name in the domain
        Mpiro.win.struc.updateExistingFieldsAfterRenamingANode(DataBasePanel.last, text);
        // Remove the old entry
        Mpiro.win.struc.removeEntityTypeOrEntityFromDB(DataBasePanel.last.toString());
        // Add an entry in mainDBHashtable with new node-name as key
        Mpiro.win.struc.putEntityTypeOrEntityToDB(text, currentVector);
        // Update new node's children tables (Subtype-of: new nodeName)
        Mpiro.win.struc.updateChildrenTableVectorsWithNewParentName(text);
        Mpiro.needExportToEmulator = true; //maria
        Mpiro.needExportToExprimo = true; //maria
        // Refresh the selected node giving it its new name
        lastNode = (DefaultMutableTreeNode) DataBasePanel.last;
        Object obj = (Object) (lastNode.getUserObject());
        if (obj instanceof IconData) {
            IconData id = (IconData) obj;
            Icon i = id.getIcon();
            ImageIcon ii = (ImageIcon) i;
            if (ii == DataBasePanel.ICON_BASIC) {
                lastNode.setUserObject(new IconData(DataBasePanel.ICON_BASIC, text));
                Enumeration enumer = lastNode.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                TreePreviews.setDataBaseTable(text);
            } else if (ii == DataBasePanel.ICON_BOOK) {
                lastNode.setUserObject(new IconData(DataBasePanel.ICON_BOOK, text));
                Enumeration enumer = lastNode.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                TreePreviews.setDataBaseTable(text);
            } else if (ii == DataBasePanel.ICON_GEI || ii == DataBasePanel.ICON_GENERIC) {
                lastNode.setUserObject(new IconData(DataBasePanel.ICON_GEI, text));
                // ... and update DataBaseEntityTables' first rows with new name
                NodeVector newCurrentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(text);
                Vector newTableVector = (Vector) newCurrentVector.elementAt(0);
                Vector inFV = (Vector) newTableVector.elementAt(0);
                FieldData inFD = new FieldData("entity-id", text);
                inFV.setElementAt(inFD, 0);
                newTableVector.setElementAt(inFV, 0);
                newCurrentVector.setElementAt(newTableVector, 0);
                TreePreviews.setDataBaseTable(text);
            }
        }
        DataBasePanel.databaseTree.revalidate();
        DataBasePanel.databaseTree.repaint();
        // Finally close the dialog-box
        dialog.dispose();
        
    }
    
    public static void renameEntity(String text,String nodeName) {
        DefaultMutableTreeNode node = null;
        Object elem1=null;
        //  System.out.println("QQQQQQQQ"+parent);
        //node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
        //Object obj = node.getUserObject();
        //if (obj == null)return false;
        //String parent = node.toString();
        //System.out.println("parent:::::"+parent);
        //addObject(new IconData(ICON_GEI, nodeName));
        DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
        
        Enumeration lastTypeChildren=first.preorderEnumeration();
        //Enumeration lastTypeChildren=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity Type").keys();
        while (lastTypeChildren.hasMoreElements()) {
            elem1=lastTypeChildren.nextElement();
            //System.out.println("QQQQQQQQ"+elem1);
            //System.out.println("QQQQQQQQ"+elem1.toString());
            if (elem1.toString().equals(nodeName)) {
                node= (DefaultMutableTreeNode) elem1;
                break;
            }
            // //System.out.println(parent);
        }
        //System.out.println(nodeName);
        // Rename all oldname instances in mainUserModelHashtable
        Mpiro.win.struc.renameEntityTypeOrEntityInUserModelHashtable(nodeName, text);
       // QueryProfileHashtable.renameEntityTypeOrEntityInRobotsModelHashtable(nodeName, text);
        // Rename all oldname instances in mainUserModelStoryHashtable
      //  QueryProfileHashtable.renameEntityTypeOrEntityInUserModelStoryHashtable(nodeName, text);
        // Rename all instances of the node name in the domain
        Mpiro.win.struc.updateExistingFieldsAfterRenamingANode(node, text);
        // Remove the old entry
        Mpiro.win.struc.removeEntityTypeOrEntityFromDB(nodeName);
        // Add an entry in mainDBHashtable with new node-name as key
        // currentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
        Mpiro.win.struc.putEntityTypeOrEntityToDB(text, currentVector);
        // Update new node's children tables (Subtype-of: new nodeName)
        Mpiro.win.struc.updateChildrenTableVectorsWithNewParentName(text,node);
        Mpiro.needExportToEmulator = true; //maria
        Mpiro.needExportToExprimo = true; //maria
        // Refresh the selected node giving it its new name
        // ..lastNode = (DefaultMutableTreeNode) ob;
        Object obj = (Object) (node.getUserObject());
        if (obj instanceof IconData) {
            IconData id = (IconData) obj;
            Icon i = id.getIcon();
            ImageIcon ii = (ImageIcon) i;
            if (ii == DataBasePanel.ICON_BASIC) {
                node.setUserObject(new IconData(DataBasePanel.ICON_BASIC, text));
                Enumeration enumer = node.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                TreePreviews.setDataBaseTable(text);
            } else if (ii == DataBasePanel.ICON_BOOK) {
                node.setUserObject(new IconData(DataBasePanel.ICON_BOOK, text));
                Enumeration enumer = node.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                TreePreviews.setDataBaseTable(text);
            } else if (ii == DataBasePanel.ICON_GEI || ii == DataBasePanel.ICON_GENERIC) {
                node.setUserObject(new IconData(DataBasePanel.ICON_GEI, text));
                // ... and update DataBaseEntityTables' first rows with new name
                NodeVector newCurrentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(text);
                Vector newTableVector = (Vector) newCurrentVector.elementAt(0);
                Vector inFV = (Vector) newTableVector.elementAt(0);
                FieldData inFD = new FieldData("entity-id", text);
                inFV.setElementAt(inFD, 0);
                newTableVector.setElementAt(inFV, 0);
                newCurrentVector.setElementAt(newTableVector, 0);
                TreePreviews.setDataBaseTable(text);
            }
        }
        DataBasePanel.databaseTree.revalidate();
        DataBasePanel.databaseTree.repaint();
        DataBasePanel.databaseTree.updateUI();
        // Finally close the dialog-box
        dialog.dispose();
        
    }
    
    public static Vector getAllInheritedNouns(String node) {
        Vector nv1=new Vector();
        String nodeWithoutOccur=node;
        if(node.substring(0,node.length()-1).endsWith("_occur")) {
            nodeWithoutOccur=node.substring(0, node.length()-7);
        }
        
        Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
        while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
            // Object nextEl=allTypesNames.nextElement();
            DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
            
            
            if (nextEl.toString().startsWith(nodeWithoutOccur+"_occur")||nextEl.toString().equalsIgnoreCase(nodeWithoutOccur)) {
                System.out.println("%%%%%%%"+nextEl.toString());
                
                //     NodeVector nv1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nextEl.toString());
                // Vector noun=(Vector) nv1.elementAt(2);
                NodeVector nv2=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nextEl.getParent().toString());
                Vector noun=(Vector) nv2.elementAt(2);
                //Vector nounNew=chboli.getItemsVector();
                for(int z=0;z<noun.size();z++) {
                    if (!nv1.contains(noun.elementAt(z)))
                        nv1.add(noun.elementAt(z));
                }
                nv2.nounVector=(Vector) nv2.elementAt(2);
            }}
        return(nv1);
    }
    
    
    public static void renameEntity(String text,String nodeName,String parent) {
        Vector currentVector1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nodeName);
        DefaultMutableTreeNode node = null;
        Object elem1=null;
        //  //System.out.println("QQQQQQQQ"+parent);
        //node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
        //Object obj = node.getUserObject();
        //if (obj == null)return false;
        //String parent = node.toString();
        ////System.out.println("parent:::::"+parent);
        //addObject(new IconData(ICON_GEI, nodeName));
        DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
        
        Enumeration lastTypeChildren=first.preorderEnumeration();
        //Enumeration lastTypeChildren=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity Type").keys();
        while (lastTypeChildren.hasMoreElements()) {
            elem1=lastTypeChildren.nextElement();
            ////System.out.println("QQQQQQQQ"+elem1);
            //System.out.println("QQQQQQQQ"+elem1.toString());
            if (elem1.toString().equalsIgnoreCase(nodeName)) {
                node= (DefaultMutableTreeNode) elem1;
                break;
            }
            // //System.out.println(parent);
            
        }
        //System.out.println(nodeName);
        // Rename all oldname instances in mainUserModelHashtable
        //  if(!nodeName.contains("_occur")){
        Mpiro.win.struc.renameEntityTypeOrEntityInUserModelHashtable(nodeName, text);
       // QueryProfileHashtable.renameEntityTypeOrEntityInRobotsModelHashtable(nodeName, text);
        // Rename all oldname instances in mainUserModelStoryHashtable
      //  QueryProfileHashtable.renameEntityTypeOrEntityInUserModelStoryHashtable(nodeName, text);
        // Rename all instances of the node name in the domain
        Mpiro.win.struc.updateExistingFieldsAfterRenamingANode(node, text);
        // Remove the old entry
        Mpiro.win.struc.removeEntityTypeOrEntityFromDB(nodeName);
        // Add an entry in mainDBHashtable with new node-name as key
        // currentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());
        
        Mpiro.win.struc.putEntityTypeOrEntityToDB(text, (NodeVector)currentVector1);
        // Update new node's children tables (Subtype-of: new nodeName)
        Mpiro.win.struc.updateChildrenTableVectorsWithNewParentName(text,node);
        // }
        //     Enumeration temp=QueryHashtable.mainDBHashtable.keys();
        //  System.out.println("...");
        Mpiro.needExportToEmulator = true; //maria
        Mpiro.needExportToExprimo = true; //maria
        // Refresh the selected node giving it its new name
        // ..lastNode = (DefaultMutableTreeNode) ob;
        Object obj = (Object) (node.getUserObject());
        if (obj instanceof IconData) {
            IconData id = (IconData) obj;
            Icon i = id.getIcon();
            ImageIcon ii = (ImageIcon) i;
            if (ii == DataBasePanel.ICON_BASIC) {
                node.setUserObject(new IconData(DataBasePanel.ICON_BASIC, text));
                Enumeration enumer = node.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                
                TreePreviews.setDataBaseTable(text,parent);
            } else if (ii == DataBasePanel.ICON_BOOK) {
                node.setUserObject(new IconData(DataBasePanel.ICON_BOOK, text));
                Enumeration enumer = node.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                
                TreePreviews.setDataBaseTable(text,parent);
            } else if (ii == DataBasePanel.ICON_GEI || ii == DataBasePanel.ICON_GENERIC) {
                node.setUserObject(new IconData(DataBasePanel.ICON_GEI, text));
                // ... and update DataBaseEntityTables' first rows with new name
                NodeVector newCurrentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(text);
                Vector newTableVector = (Vector) newCurrentVector.elementAt(0);
                Vector inFV = (Vector) newTableVector.elementAt(0);
                FieldData inFD = new FieldData("entity-id", text);
                inFV.setElementAt(inFD, 0);
                newTableVector.setElementAt(inFV, 0);
                newCurrentVector.setElementAt(newTableVector, 0);
                //TreePreviews.setDataBaseTable(text,parent);
            }
        }
        DataBasePanel.databaseTree.revalidate();
        DataBasePanel.databaseTree.repaint();
        DataBasePanel.databaseTree.updateUI();
        // Finally close the dialog-box
        if (dialog!=null)
            dialog.dispose();
        
    }
    
    
    
    public static void renameOccur(String text,String nodeName,String parent) {
//Vector currentVector1=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(nodeName);
        //DefaultMutableTreeNode node = null;
        //Object elem1=null;
        //  //System.out.println("QQQQQQQQ"+parent);
        //node = (DefaultMutableTreeNode) (selpath.getLastPathComponent());
        //Object obj = node.getUserObject();
        //if (obj == null)return false;
        //String parent = node.toString();
        ////System.out.println("parent:::::"+parent);
        //addObject(new IconData(ICON_GEI, nodeName));
        //DefaultMutableTreeNode first = (DefaultMutableTreeNode) DataBasePanel.top.getChildAt(0);
        
        // Enumeration lastTypeChildren=first.preorderEnumeration();
        //Enumeration lastTypeChildren=Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity Type").keys();
        //   while (lastTypeChildren.hasMoreElements())
        //  {
        //    elem1=lastTypeChildren.nextElement();
        ////System.out.println("QQQQQQQQ"+elem1);
        //System.out.println("QQQQQQQQ"+elem1.toString());
        //    if (elem1.toString().equalsIgnoreCase(nodeName)) {
        //     node= (DefaultMutableTreeNode) elem1;
        //     break;
        //  }
        // //System.out.println(parent);
        
        //}
        DefaultMutableTreeNode node=DataBasePanel.getNode(nodeName);
        Object obj = (Object) (node.getUserObject());
        if (obj instanceof IconData) {
            IconData id = (IconData) obj;
            Icon i = id.getIcon();
            ImageIcon ii = (ImageIcon) i;
            if (ii == DataBasePanel.ICON_BASIC) {
                node.setUserObject(new IconData(DataBasePanel.ICON_BASIC, text));
                Enumeration enumer = node.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                
                // TreePreviews.setDataBaseTable(text,parent);
            } else if (ii == DataBasePanel.ICON_BOOK) {
                node.setUserObject(new IconData(DataBasePanel.ICON_BOOK, text));
                Enumeration enumer = node.children();
                while (enumer.hasMoreElements()) {
                    DefaultMutableTreeNode kkk = (DefaultMutableTreeNode) enumer.nextElement();
                    if (kkk.toString().startsWith("Generic-")) {
                        String oldname = kkk.toString();
                        kkk.setUserObject(new IconData(DataBasePanel.ICON_GENERIC, "Generic-" + text));
                        Mpiro.win.struc.renameNodesGenericEntity(oldname, "Generic-" + text);
                    }
                }
                
                //TreePreviews.setDataBaseTable(text,parent);
            } else if (ii == DataBasePanel.ICON_GEI || ii == DataBasePanel.ICON_GENERIC) {
                node.setUserObject(new IconData(DataBasePanel.ICON_GEI, text));
                // ... and update DataBaseEntityTables' first rows with new name
                //   NodeVector newCurrentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(text);
                //  Vector newTableVector = (Vector) newCurrentVector.elementAt(0);
                //  Vector inFV = (Vector) newTableVector.elementAt(0);
                // FieldData inFD = new FieldData("entity-id", text);
                // inFV.setElementAt(inFD, 0);
                // newTableVector.setElementAt(inFV, 0);
                // newCurrentVector.setElementAt(newTableVector, 0);
                //TreePreviews.setDataBaseTable(text,parent);
            }
        }
        DataBasePanel.databaseTree.revalidate();
        DataBasePanel.databaseTree.repaint();
        DataBasePanel.databaseTree.updateUI();
        // Finally close the dialog-box
        if (dialog!=null)
            dialog.dispose();
        
    }
    
    
    
    public void createSubClass(String childName1,String parent) {
        
        String childName=childName1;
        //  Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Enumeration allTypesNames=DataBasePanel.top.preorderEnumeration();
        int occur=2;
        
        if (childName.substring(0,childName.length()-1).endsWith("occur"))
            childName=childName.substring(0,childName.length()-7) ;
        for (;allTypesNames.hasMoreElements();) {
            String typeName=allTypesNames.nextElement().toString();
            
            if(typeName.startsWith(childName+"_occur")) {
                if ( Integer.parseInt(typeName.substring(childName.length()+6, typeName.length()))>=occur) {//System.out.println(String.valueOf(occur));
                    occur=Integer.parseInt(typeName.substring(childName.length()+6, typeName.length()))+1;
                }
            }
            
        }
        
        
        NodeVector nextEntVector2 = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parent);
        text=childName+"_occur"+String.valueOf(occur);
        
        
        
        
        // Vector pNounVector1=(Vector) nextEntVector2.elementAt(2);
        //  Vector pNounVector=  (Vector) pNounVector1.clone();
        //System.out.println(text);
        String checkValid = Mpiro.win.struc.checkNameValidity(text);
        int check = Mpiro.win.struc.checkName(text);
        
        if ( (check == 1) && (text.equalsIgnoreCase("NewEntityType"))) {
            new MessageDialog(dialog, MessageDialog.attentionYouHaveAnUnnamedEntityType_dialog);
        } else if (check == 1) {
            new MessageDialog(dialog, MessageDialog.thisNameAlreadyExists_dialog);
        } else if (check == 2) {
            new MessageDialog(dialog, MessageDialog.pleaseGiveANameForTheNode_dialog);
        }
        //  else if (check == 3) {
        //      new MessageDialog(dialog, MessageDialog.noSpacesAreAllowedForAName_dialog);
        //   }
        else if (check == 4) {//kallonis
            new MessageDialog(dialog, MessageDialog.nameStartWithUM_dialog);
        } else if (check == 5) {//kallonis
            new MessageDialog(dialog, MessageDialog.nameStartWithNumber_dialog);
        }
        //check the validity of the new name
        //        else if (!checkValid.equalsIgnoreCase("VALID")) {
        //            new MessageDialog(dialog, MessageDialog.theNameContainsTheFollowingInvalidCharacters_dialog
        //                              + "\n" + checkValid);
        //        }
        else if (check == 0) {
            dialog.dispose();
            
            System.out.println("vvvvvvvvvvvvvvv"+childName);
            
            
            
            //System.out.println("PPPpPPpPpppPPPPP"+parent);
            DataBasePanel.addEntityTypeOccur(text,parent);
            createSubtypes(childName, String.valueOf(occur));
            createEntities(childName, String.valueOf(occur));
            Mpiro.needExportToEmulator = true; //maria
            Mpiro.needExportToExprimo = true; //maria
            TreePreviews.setDataBaseTable(DataBasePanel.last.toString());
            //pn.nounVector=(Vector)pn.elementAt(2);
        }
        
        
        
        
        NodeVector entityTypeNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(parent);
        NodeVector entityTypeParentNode = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(childName);
        
        Vector childDatabaseTableVector =(Vector) entityTypeNode.elementAt(0);
        Vector parentDatabaseTableVector =(Vector) entityTypeParentNode.elementAt(0);
        
        Vector nounVector=(Vector) entityTypeParentNode.elementAt(2);
        Vector nounVectorOccur=(Vector) entityTypeNode.elementAt(2);
        
        for(int l=0;l<nounVectorOccur.size();l++){
            if(!nounVector.contains(nounVectorOccur.elementAt(l))) {
                nounVector.add(nounVectorOccur.elementAt(l));
                addNounToChildrenTypes(childName, nounVectorOccur.elementAt(l));
            }
        }
        
        
        
        
        
        
        
        
        
        for (int g = 0; g < childDatabaseTableVector.size(); g++) {////ston occur1 prosthetei tis idiotites tou occur2
            FieldData property1 = ((FieldData) childDatabaseTableVector.get(g));
            System.out.println("1"+property1.m_field);
            //System.out.println("!!@!@!!!!@111!111"+property1.toString()+property1.m_field);
            if (!(parentDatabaseTableVector.contains(property1))){
                parentDatabaseTableVector.add(g,property1);
                Mpiro.win.struc.addValueRestriction(childName+":"+property1.m_field,Mpiro.win.struc.getValueRestriction(parent+":"+property1.m_field));
                addFieldToChildrenTypes(childName, property1, g);
                addFieldToChildrenEntities(childName, property1);
            }
            
        }
        
        
    }
    
    private void createSubtypes(String name, String occur){
        Enumeration children= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(name,"Entity type").elements();
        while(children.hasMoreElements()){
            String next=children.nextElement().toString();
            DataBasePanel.addEntityTypeOccur(next+"_occur"+occur, name+"_occur"+occur);
            createEntities(next, occur);
            createSubtypes(next, occur);
        }
        // DataBasePanel.addEntityType(text,parent);
        
    }
    
    private void createEntities(String name, String occur){
        Enumeration children= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(name,"Entity").elements();
        while(children.hasMoreElements()){
            String next=children.nextElement().toString();
            DataBasePanel.addEntityOccur(next+"_occur"+occur, name+"_occur"+occur);
        }
        // DataBasePanel.addEntityType(text,parent);
        
    }
    
    private void addNounToChildrenTypes(String name, Object noun){
        Enumeration children= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(name,"Entity type").elements();
        while(children.hasMoreElements()){
            String next=children.nextElement().toString();
            NodeVector nextChild=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(next);
            Vector nounVector=(Vector) nextChild.elementAt(2);
            if(!nounVector.contains(noun)){
                nounVector.add(noun);
                addNounToChildrenTypes(next, noun);
            }
        }
    }
    
    private void addFieldToChildrenTypes(String name, FieldData field, int g){
        Enumeration children= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(name,"Entity type").elements();
        while(children.hasMoreElements()){
            String next=children.nextElement().toString();
            NodeVector nextChild=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(next);
            Vector dataVector=(Vector) nextChild.elementAt(0);
            if(!dataVector.contains(field)){
                dataVector.add(g,field);
                Mpiro.win.struc.addValueRestriction(next+":"+field.m_field,Mpiro.win.struc.getValueRestriction(name+":"+field.m_field));
                addFieldToChildrenTypes(next, field, g);
                addFieldToChildrenEntities(next, field);
                
            }
        }
    }
    
    private void addFieldToChildrenEntities(String name, FieldData field){
        Enumeration children= Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(name,"Entity").elements();
        while(children.hasMoreElements()){
            String next=children.nextElement().toString();
            NodeVector nextChild=(NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(next);
            Vector dataVector=(Vector) nextChild.elementAt(0);
            if(field.m_filler.equalsIgnoreCase("String")){
                for(int i=1;i<4;i++){
                    Vector langVec=(Vector)dataVector.elementAt(i);
                    langVec.add(new FieldData(field.m_field,""));
                }
            } else{
                Vector indep=(Vector)dataVector.elementAt(0);
                indep.add(new FieldData(field.m_field,""));
            }
            //     if(!dataVector.contains(field)){
            //    dataVector.add(g,field);
            //    addFieldToChildrenTypes(next, field, g);
            //   Mpiro.win.struc.addValueRestriction(next+":"+field.m_field,Mpiro.win.struc.getValueRestriction(name+":"+field.m_field));
            // }
        }
    }
    //  *****************************************8
} // class KDialog