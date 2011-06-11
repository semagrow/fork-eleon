//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.CheckBoxList;
import gr.demokritos.iit.eleon.authoring.DataBaseEntityTable;
import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.ImageFilter;
import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.ListData;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.NodeVector;
import gr.demokritos.iit.eleon.struct.QueryHashtable;

import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;

/**
 * <p>Title: DDialog</p>
 * <p>Description: A generic dialog used for (see Constructor)
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class DDialog
    extends JFrame
    implements ActionListener {

    static JDialog dialog;
    static KLabel listLabel;
    private KLabel textFieldLabel;
    private OkCancel okCancel;
    private JTextField textField;
    static CheckBoxList chboli;
    private boolean isSelected = false; // is something in the list selected?

    private KButton okButton;
    private KButton cancelButton;

    private DefaultMutableTreeNode lastNode;
    private DefaultMutableTreeNode createdChild;

    static String text;
    static Vector itemsSelected;
    static Object item;
    static JPanel checkPanel;
    static JPanel oldValuePanel;

    // For "Date"
    private JPanel betweenAndClearDatePanel;
    private JCheckBox clearDate;
    private JCheckBox between;
    private JPanel datePanel;

    //private JLabel dateFieldLabel;
    private DatePanel date1;
    private DatePanel date2;

    // For "Dimension"
    private JCheckBox clearDimension;
    private JPanel dimensionPanel;
    private DimensionPanel dimension;

    // For "Images"
    private AddRemovePanel addRemovePanel;
    private KButton addButton;
    private KButton removeButton;
    final JFileChooser imageAddChooser = new JFileChooser();
    private Vector setVector = new Vector();

    public Vector imageVector = new Vector();
    NodeVector currentVector;

    Vector commonNouns; // inherited nouns
    Vector allNounsClone; // rest of nouns ([all] - [inherited])
    Vector beforeOkNounVector; // initial nounVector of a node
    // (before changing it)

    public Vector positionsVector = null;
    public Vector selectionsVector = null;

    static String type;

    /**
     * @param frameTitle The title for the frame
     * @param textFieldTitle Controls the visibility of the text field: if set to null no textField is visible
     * @param listTitle The title for the list
     * @param listItems The vector of items for the list
     * @param stringItems The string of the items (SET only dialog - later renamed to "Many")
     * @param dialogType The type of dialog (SET  for the entity(SET) dialog,
     *                                       DATE  for the entity(DATE) dialog,
     *                                       DIMENSION  for the entity(DIMENSION) dialog,
     *                                       IMAGES  for the entity(IMAGES) dialog,)
     */
    public DDialog(String frameTitle, String textFieldTitle, String listTitle,
                   Vector listItems, String stringItems, String dialogType) {
//this.setAlwaysOnTop(true);
//this.setFocusTraversalKeysEnabled(true);
//this.setFocusTraversalPolicy(true);
//this.setFocusable(true);
//this.setFocusTraversalPolicyProvider(true);
        type = dialogType;
        //
        if (type == "IMAGES") {
            imageVector = setImageVector(stringItems);
        }

        // Get the nodeVector of the selected 
        if(!type.equals("ROBOT"))
        currentVector = (NodeVector) Mpiro.win.struc.getEntityTypeOrEntity(DataBasePanel.last.toString());

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
        oldValuePanel = new JPanel(new BorderLayout());
        // 5
        checkPanel = new JPanel(new BorderLayout());
        if (type == "IMAGES") {
            chboli = new CheckBoxList(imageVector);
        }
        else {
            chboli = new CheckBoxList(listItems);
        }
        //ListModel lm=chboli.list.getModel();
        
        chboli.setPreferredSize(new Dimension(170, 118));

        // 6 (For "Date")
        datePanel = new JPanel(new BorderLayout());
        datePanel.setPreferredSize(new Dimension(450, 100));
        between = new JCheckBox(LangResources.getString(Mpiro.selectedLocale, "clickToEnterTwoDatesFromTo_text"), false);
        clearDate = new JCheckBox(LangResources.getString(Mpiro.selectedLocale, "selectToClearEntry_text"), false);
        betweenAndClearDatePanel = new JPanel(new GridBagLayout());
        betweenAndClearDatePanel.add(between);
        betweenAndClearDatePanel.add(clearDate);

        date1 = new DatePanel();
        date2 = new DatePanel();

        datePanel.add("North", betweenAndClearDatePanel);
        datePanel.add("Center", date1);

        // 7 (For "Dimension")
        dimensionPanel = new JPanel(new BorderLayout());
        dimensionPanel.setPreferredSize(new Dimension(180, 70));
        clearDimension = new JCheckBox(LangResources.getString(Mpiro.selectedLocale, "selectToClearEntry_text"), false);

        dimension = new DimensionPanel();

        dimensionPanel.add("North", clearDimension);
        dimensionPanel.add("Center", dimension);

        // 7 (For "Dimension")
        addRemovePanel = new AddRemovePanel();

        if (listItems != null) {
            checkPanel.add("Center", chboli);
        }

        if (type == "SET") {
           // textFieldLabel.setVisible(false);
            //textField.setVisible(false);
           setVector = new Vector();
            //System.out.println("(DDialog.constructor)--stringItems---  " + stringItems);
            if (stringItems.startsWith("Select ")||stringItems.equals("")) {
                // do nothing!
            }
            else {
                StringTokenizer sToken = new StringTokenizer(stringItems);
                while (sToken.hasMoreTokens()) {
                    ListData token = new ListData(sToken.nextToken().toString());
                    setVector.addElement(token);
                }
            }
            chboli.clear();
            chboli.setChecked(setVector);

        }
        if (type.equals("ANNOTATION") || type.equals("ROBOT")) {
           // textFieldLabel.setVisible(false);
            //textField.setVisible(false);
           setVector = new Vector();
            //System.out.println("(DDialog.constructor)--stringItems---  " + stringItems);
            if (stringItems.equals("")) {
                // do nothing!
            }
            else {
                StringTokenizer sToken = new StringTokenizer(stringItems);
                while (sToken.hasMoreTokens()) {
                    ListData token = new ListData(sToken.nextToken().toString());
                    setVector.addElement(token);
                }
            }
            chboli.clear();
            chboli.setChecked(setVector);

        }
        if ((type == "SUBPROPERTIES")||(type == "SUPERPROPERTIES")|| (type == "DOMAIN")) {
            
           textFieldLabel.setVisible(false);
            textField.setVisible(false);
           setVector = new Vector();
            //System.out.println("(DDialog.constructor)--stringItems---  " + stringItems);
            
                StringTokenizer sToken = new StringTokenizer(stringItems);
                while (sToken.hasMoreTokens()) {
                    ListData token = new ListData(sToken.nextToken().toString());
                    setVector.addElement(token);
                }
            
           chboli.clear();
            chboli.setChecked(setVector);

        }
        else if (type == "DATE") {
            date1.setDate(stringItems);

            KLabel oldValueLabel = new KLabel(LangResources.getString(Mpiro.selectedLocale, "currentDate_text"));
            JTextArea oldValue = new JTextArea();
            oldValue.setLineWrap(true);
            oldValue.setWrapStyleWord(true);
            oldValue.setBorder(new EmptyBorder(0, 2, 0, 2));
            oldValue.setFont(new Font(Mpiro.selectedFont, Font.BOLD, 10));
            oldValue.setEditable(false);
            oldValue.setBackground(new Color(228, 228, 195));
            oldValue.setForeground(Color.gray);

            if (stringItems.equalsIgnoreCase("Select a \"Date\"")) {
                oldValue.setText("");
            }
            else {
                oldValue.setText(stringItems);
            }
            JScrollPane oldValueScroll = new JScrollPane(oldValue);
            oldValueScroll.setPreferredSize(new Dimension(440, 20));
            oldValuePanel.add("North", oldValueLabel);
            oldValuePanel.add("Center", oldValueScroll);
        }

        else if (type == "DIMENSION") {
            dimension.setDimension(stringItems);
        }

        else if (type == "IMAGES") {
            checkPanel.add("Center", chboli);
        }

        // 8
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
        contentPane.add(oldValuePanel, c);

        if (textFieldTitle != null) {
            c.gridy = 1;
            contentPane.add(textFieldLabel, c);
            c.gridy = 2;
            contentPane.add(textField, c);
        }

        c.gridy = 3;
        contentPane.add(listLabel, c);

        c.gridy = 4;

        if (type == "SET") {
            contentPane.add(checkPanel, c);
        }
        if (type == "ANNOTATION"|| type.equals("ROBOT")) {
            contentPane.add(checkPanel, c);
        }
         if ((type == "SUBPROPERTIES")||(type == "SUPERPROPERTIES") ||(type == "DOMAIN")) {
            contentPane.add(checkPanel, c);
        }

        else if (type == "DATE") {
            contentPane.add(datePanel, c);
        }

        else if (type == "DIMENSION") {
            contentPane.add(dimensionPanel, c);
        }

        else if (type == "IMAGES") {
            contentPane.add(checkPanel, c);
            c.gridy = 5;
            contentPane.add(addRemovePanel, c);
        }

        c.gridy = 5;

        c.gridy = 6;
        contentPane.add(okCancel, c);

        // Add the actionListener
        between.addActionListener(DDialog.this);
        DialogListener listener = new DialogListener();
        okButton.addActionListener(listener);
        cancelButton.addActionListener(listener);
        addButton.addActionListener(listener);
        removeButton.addActionListener(listener);

        // Make the dialog visible
        dialog.pack();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = this.getSize();
        dialog.setLocation( (screenSize.width - size.width) / 2,
                           (screenSize.height - size.height) / 2);
        dialog.setVisible(true);

    } // constructor

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == between) {
            datePanel.removeAll();
            if (between.isSelected()) {
                datePanel.add("North", betweenAndClearDatePanel);
                datePanel.add("Center", date1);
                datePanel.add("South", date2);
            }
            else {
                datePanel.add("North", betweenAndClearDatePanel);
                datePanel.add("Center", date1);
            }
            datePanel.revalidate();
            datePanel.repaint();

        }

    }

    /**
     * <p>Title: AddRemovePanel</p>
     * <p>Description: A panel containing the "Add" & "Remove" buttons
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class AddRemovePanel
        extends JPanel {

        public AddRemovePanel() {
            setLayout(new GridLayout(1, 2));
            setPreferredSize(new Dimension(300, 30));
            addButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "addImage_text"));
            removeButton = new KButton(LangResources.getString(Mpiro.selectedLocale, "removeImage_text"));
            add(addButton);
            add(removeButton);
        }
    }

    /**
     * <p>Title: OkCancel</p>
     * <p>Description: A panel containing the "OK" & "Cancel" buttons
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
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

    /**
     * <p>Title: DialogListener</p>
     * <p>Description: The listener for the dialog
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     *    */
    class DialogListener
        implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == okButton) {
                
                if(type == "DOMAIN"){
                     Vector selectedDomains = (Vector) chboli.getItemsVector();
                    
                    Mpiro.win.struc.setPropertyDomain(selectedDomains, textFieldLabel.getText());
                    
                  
                           dialog.dispose();
                            return;
                }
                
                if(type == "SUBPROPERTIES"){
                    Vector selectedSubProp = (Vector) chboli.getItemsVector();
                    
                    Mpiro.win.struc.addSubpropertiesToProperty(selectedSubProp, textFieldLabel.getText());
                    
                  
                           dialog.dispose();
                            return;
                }
                
                if(type == "SUPERPROPERTIES"){
                    Vector selectedSuperProp = (Vector) chboli.getItemsVector();
                    Mpiro.win.struc.addSuperpropertiesToProperty(selectedSuperProp, textFieldLabel.getText());
                            
                    dialog.dispose();
                    return;
                }
                
                
                /* Specifically for each type of DDialog */
                if (type == "SET") {
               //     Hashtable test=QueryHashtable.mainDBHashtable;
                    Vector selectedItems = (Vector) chboli.getItemsVector();
                    
                    Mpiro.win.struc.setPropertyFillers(selectedItems, setVector);
                                        
                    
                  //  Mpiro.needExportToExprimo = true; //maria

                } // if SET
                if (type == "ANNOTATION") {
               //     Hashtable test=QueryHashtable.mainDBHashtable;
                    Vector selectedItems = (Vector) chboli.getItemsVector();
                    String value="";
                    if(selectedItems.size()>0)
                        value=selectedItems.elementAt(0).toString();
                    for(int h=1;h<selectedItems.size();h++){
                        value+=" "+selectedItems.elementAt(h).toString();
                    }
               //     System.out.print(String.valueOf(AnnotationPropertiesPanel.jTable1.getEditingRow()));
                    AnnotationPropertiesPanel.jTable1.setValueAt(value, AnnotationPropertiesPanel.jTable1.getSelectedRow(),4);
                //AnnotationPropertiesPanel.jTable1   
                }

                
                 if (type == "ROBOT") {
               //     Hashtable test=QueryHashtable.mainDBHashtable;
                    Vector selectedItems = (Vector) chboli.getItemsVector();
                    String value="";
                    if(selectedItems.size()>0)
                        value=selectedItems.elementAt(0).toString();
                    for(int h=1;h<selectedItems.size();h++){
                        value+=" "+selectedItems.elementAt(h).toString();
                    }
                    
                    Mpiro.win.struc.addChangesInRobotCharValuesHashtable(selectedItems);
               //     System.out.print(String.valueOf(AnnotationPropertiesPanel.jTable1.getEditingRow()));
                    RobotCharacteristicsPanel.jTable1.setValueAt(value, RobotCharacteristicsPanel.jTable1.getSelectedRow(),1);
                //AnnotationPropertiesPanel.jTable1   
                }
                if (type == "DATE") {

                    if (clearDate.isSelected()) {
                        DataBaseEntityTable.m_data.setValueAt("Select a \"Date\"", DataBaseEntityTable.dbetl.rowNo, 1);
                        Mpiro.needExportToExprimo = true; //maria
                    }

                    else {
                        String firstDate = date1.getDate();
                        String secondDate;
                        String newValue = new String();
                        if (between.isSelected()) {
                            secondDate = date2.getDate();
                            newValue = "between (" + firstDate + ") (" + secondDate + ")";
                        }
                        else if (!between.isSelected()) {
                            newValue = "(" + firstDate + ")";
                        }
                        DataBaseEntityTable.m_data.setValueAt(newValue, DataBaseEntityTable.dbetl.rowNo, 1);
                    }
                    Mpiro.needExportToExprimo = true; //maria
                } // if DATE

                if (type == "DIMENSION") {
                    if (clearDimension.isSelected()) {
                        DataBaseEntityTable.m_data.setValueAt("Select a \"Dimension\"", DataBaseEntityTable.dbetl.rowNo, 1);
                        Mpiro.needExportToExprimo = true; //maria
                    }

                    else {
                        String newValue = dimension.getDimension();
                        DataBaseEntityTable.m_data.setValueAt(newValue, DataBaseEntityTable.dbetl.rowNo, 1);
                    }
                    Mpiro.needExportToExprimo = true; //maria
                } // if DIMENSION

                if (type == "IMAGES") {
                    // Update the entity table with the new value
                    Vector selectedItems = (Vector) chboli.getListVector();
                    StringBuffer newItems = new StringBuffer("");

                    Enumeration enumer = selectedItems.elements();
                    while (enumer.hasMoreElements()) {
                        String item = enumer.nextElement().toString();
                        newItems.append("<" + item + ">");
                    }
                    String newValue = newItems.toString();
                    if (newValue.length() == 0) {
                        newValue = "Select images .....";
                    }
                    DataBaseEntityTable.m_data.setValueAt(newValue, DataBaseEntityTable.dbetl.rowNo, 1);
                    Mpiro.needExportToExprimo = true; //maria
                } // if IMAGES

                // dispose the dialog
                dialog.dispose();
            }

            if (e.getSource() == cancelButton) {
                dialog.dispose();
            }

            if (e.getSource() == addButton) {
                ImageFilter imagefilter = new ImageFilter();
                imageAddChooser.setFileFilter(imagefilter);
                File userdir = new File(System.getProperty("user.dir"));
                File picturesdir = new File(userdir.toString() + "/pictures");
                if (!picturesdir.exists() || !picturesdir.isDirectory()) {
                    picturesdir.mkdir();
                    new MessageDialog(dialog, MessageDialog.aNewDirectoryPicturesHasBeenCreatedInYourUserDir_ETC_dialog);
                }
                imageAddChooser.setCurrentDirectory(picturesdir);

                int returnVal = imageAddChooser.showDialog(dialog, LangResources.getString(Mpiro.selectedLocale, "addImage_text"));
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = imageAddChooser.getSelectedFile().getName();
                    imageVector.addElement(new ListData(fileName));
                    chboli.clear();
                    chboli = new CheckBoxList(imageVector);
                    chboli.setPreferredSize(new Dimension(170, 118));
                }
                else {
                    // do nothing
                }
                checkPanel.removeAll();
                checkPanel.add("Center", chboli);
                checkPanel.revalidate();
                checkPanel.repaint();
                imageAddChooser.removeChoosableFileFilter(imagefilter);
            }

            if (e.getSource() == removeButton) {

                Vector itemsToRemoveVector = chboli.getPositionsVector();
                //System.out.println("(DDialog.removeButton)- selected items-------- " + itemsToRemoveVector);
                Enumeration enumer = itemsToRemoveVector.elements();
                int k = 0;
                Integer position;
                while (enumer.hasMoreElements()) {
                    position = (Integer) enumer.nextElement();
                    imageVector.removeElementAt(position.intValue() - k);
                    k++;
                }
                chboli = new CheckBoxList(imageVector);
                chboli.setPreferredSize(new Dimension(170, 118));
                checkPanel.removeAll();
                checkPanel.add("Center", chboli);
                checkPanel.revalidate();
                checkPanel.repaint();
            }
        } // actionPerformed
    } // class DialogListener

    /**
     * <p>Title: TimePeriodCombo</p>
     * <p>Description: A combobox for time periods (used in "DATE")
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class TimePeriodCombo
        extends KComboBox {

        public TimePeriodCombo() {
            addItem("year");
            addItem("decade");
            addItem("century");
        }
    }

    /**
     * <p>Title: YearModifierCombo</p>
     * <p>Description: A combobox for year modifier (used in "DATE")
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class YearModifierCombo
        extends KComboBox {

        public YearModifierCombo() {
            addItem("");
            setEnabled(false);
            setPreferredSize(new Dimension(110, 25));
        }
    }

    /**
     * <p>Title: DecadeModifierCombo</p>
     * <p>Description: A combobox for decade modifier (used in "DATE")
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class DecadeModifierCombo
        extends KComboBox {

        public DecadeModifierCombo() {
            addItem("");
            addItem("early");
            addItem("late");
            setPreferredSize(new Dimension(110, 25));
        }
    }

    /**
     * <p>Title: CenturyModifierCombo</p>
     * <p>Description: A combobox for century modifier (used in "DATE")
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class CenturyModifierCombo
        extends KComboBox {

        public CenturyModifierCombo() {
            addItem("");
            addItem("early");
            addItem("late");
            addItem("first-quarter");
            addItem("second-quarter");
            addItem("third-quarter");
            addItem("last-quarter");
            setPreferredSize(new Dimension(110, 25));
        }
    }

    /**
     * <p>Title: BCADCombo</p>
     * <p>Description: A combobox for "BC"-"AD" modifier (used in "DATE")
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class BCADCombo
        extends KComboBox {

        public BCADCombo() {
            addItem("BC");
            addItem("AD");
        }
    }

    /**
     * <p>Title: DimensionCombo</p>
     * <p>Description: A combobox for dimension (used in "DIMENSION")
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class DimensionCombo
        extends KComboBox {

        public DimensionCombo() {
            addItem("kgr");
            addItem("gr");
            addItem("m");
            addItem("cm");
            addItem("mm");
            addItem("sqrm");
        }
    }

    /**
     * <p>Title: DatePanel</p>
     * <p>Description: A panel to edit date
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class DatePanel
        extends JPanel
        implements ActionListener {

        JCheckBox circa;
        JTextField dateField;
        TimePeriodCombo timePeriod;
        YearModifierCombo yearModifier;
        DecadeModifierCombo decadeModifier;
        CenturyModifierCombo centuryModifier;
        BCADCombo BCAD;
        JPanel modifierPanel;

        public DatePanel() {
            setPreferredSize(new Dimension(350, 40));
            setLayout(new GridBagLayout());

            dateField = new JTextField();
            dateField.setPreferredSize(new Dimension(60, 25));

            circa = new JCheckBox(" circa", false);
            circa.setFont( (new Font(Mpiro.selectedFont, Font.ITALIC, 10)));

            timePeriod = new TimePeriodCombo();

            modifierPanel = new JPanel();
            modifierPanel.setPreferredSize(new Dimension(115, 35));
            yearModifier = new YearModifierCombo();
            decadeModifier = new DecadeModifierCombo();
            centuryModifier = new CenturyModifierCombo();

            BCAD = new BCADCombo();

            modifierPanel.add(yearModifier);

            add(circa);
            add(dateField);
            add(timePeriod);
            add(modifierPanel);
            add(BCAD);

            timePeriod.addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {

            modifierPanel.removeAll();
            if (timePeriod.getSelectedItem().toString().equalsIgnoreCase("year")) {
                modifierPanel.add(yearModifier);
            }
            else if (timePeriod.getSelectedItem().toString().equalsIgnoreCase("decade")) {
                modifierPanel.add(decadeModifier);
            }
            else if (timePeriod.getSelectedItem().toString().equalsIgnoreCase("century")) {
                modifierPanel.add(centuryModifier);
            }
            modifierPanel.revalidate();
            modifierPanel.repaint();

        }

        public String getDate() {

            StringBuffer date = new StringBuffer();

            String checkValid = Mpiro.win.struc.checkNameValidityNumberOnly(dateField.getText());
            //check for empty dateField
            if ( (dateField.getText().length() == 0) || (dateField.getText().length() > 12)) {
                new MessageDialog(dialog, MessageDialog.pleaseGiveADate_ETC_dialog);
                dateField.setText("0");
            }

            //check the validity of the new name
            else if (!checkValid.equalsIgnoreCase("VALID")) {
                new MessageDialog(dialog, MessageDialog.theDateContainsTheFollowingInvalidCharacters_dialog
                                  + "\n" + checkValid);
                dateField.setText("0");
            }

            if (circa.isSelected()) {
                date.append("c. ");
            }
            date.append(dateField.getText() + " ");
            date.append(timePeriod.getSelectedItem().toString() + " ");
            if (timePeriod.getSelectedItem().toString().equalsIgnoreCase("decade") &&
                (!decadeModifier.getSelectedItem().toString().equalsIgnoreCase(""))) {
                date.append(decadeModifier.getSelectedItem().toString() + " ");
            }
            else if (timePeriod.getSelectedItem().toString().equalsIgnoreCase("century") &&
                     (!centuryModifier.getSelectedItem().toString().equalsIgnoreCase(""))) {
                date.append(centuryModifier.getSelectedItem().toString() + " ");
            }
            date.append(BCAD.getSelectedItem().toString());
            return date.toString();
        }

        public void setDate(String oldValue) {

            String date = new String(oldValue);
            //System.out.println(date);
            String d1;
            String d2;
            if ( (date.length() != 0) && (!date.equalsIgnoreCase("Select a \"Date\""))) {

                if (date.substring(0, 7).equalsIgnoreCase("between")) {
                    between.setSelected(true);
                    datePanel.removeAll();
                    datePanel.add("North", betweenAndClearDatePanel);
                    datePanel.add("Center", date1);
                    datePanel.add("South", date2);

                    //System.out.println(date.substring(0, 7));
                    d1 = date.substring(date.indexOf("(") + 1, date.indexOf(")"));
                    d2 = date.substring(date.lastIndexOf("(") + 1, date.lastIndexOf(")"));
                    //System.out.println("date1======== " + d1);
                    //System.out.println("date2======== " + d2);

                    // put all tokens in vectors
                    StringTokenizer dst1 = new StringTokenizer(d1);
                    Vector dv1 = new Vector();
                    while (dst1.hasMoreTokens()) {
                        String token = dst1.nextToken();
                        dv1.addElement(token);
                    }
                    StringTokenizer dst2 = new StringTokenizer(d2);
                    Vector dv2 = new Vector();
                    while (dst2.hasMoreTokens()) {
                        String token = dst2.nextToken();
                        dv2.addElement(token);
                    }

                    displayDate("date1", dv1);
                    displayDate("date2", dv2);
                }
                else {
                    d1 = date.substring(date.indexOf("(") + 1, date.indexOf(")"));
                    //System.out.println("date1======== " + d1);

                    // put all tokens in vectors
                    StringTokenizer dst1 = new StringTokenizer(d1);
                    Vector dv1 = new Vector();
                    while (dst1.hasMoreTokens()) {
                        String token = dst1.nextToken();
                        dv1.addElement(token);
                    }
                    displayDate("date1", dv1);
                }
            }
        } // setDate

        private void displayDate(String datePanelNo, Vector items) {

            // check which datepanel we display
            DatePanel datepanel = null;
            if (datePanelNo.equalsIgnoreCase("date1")) {
                datepanel = date1;
            }
            else if (datePanelNo.equalsIgnoreCase("date2")) {
                datepanel = date2;
            }

            // the "circa"
            if (items.firstElement().toString().equalsIgnoreCase("c.")) {
                datepanel.circa.setSelected(true);
                items.removeElementAt(0);
            }

            // the "BC/AD"
            if (items.lastElement().toString().equalsIgnoreCase("BC")) {
                datepanel.BCAD.setSelectedItem("BC");
                items.removeElementAt(items.size() - 1);
            }
            else if (items.lastElement().toString().equalsIgnoreCase("AD")) {
                datepanel.BCAD.setSelectedItem("AD");
                items.removeElementAt(items.size() - 1);
            }

            // the "timePeriod" and its modifier
            datepanel.modifierPanel.removeAll();
            if (items.elementAt(1).toString().equalsIgnoreCase("year")) {
                datepanel.timePeriod.setSelectedItem("year");
                datepanel.modifierPanel.add(datepanel.yearModifier);
                items.removeElementAt(items.size() - 1);
            }
            else if (items.elementAt(1).toString().equalsIgnoreCase("decade")) {
                datepanel.timePeriod.setSelectedItem("decade");
                datepanel.modifierPanel.add(datepanel.decadeModifier);
                if (items.size() == 3) {
                    datepanel.decadeModifier.setSelectedItem(items.elementAt(2).toString());
                    items.removeElementAt(items.size() - 1);
                }
                items.removeElementAt(items.size() - 1);
            }
            else if (items.elementAt(1).toString().equalsIgnoreCase("century")) {
                datepanel.timePeriod.setSelectedItem("century");
                datepanel.modifierPanel.add(datepanel.centuryModifier);
                if (items.size() == 3) {
                    datepanel.centuryModifier.setSelectedItem(items.elementAt(2).toString());
                    items.removeElementAt(items.size() - 1);
                }
                items.removeElementAt(items.size() - 1);
            }
            datepanel.modifierPanel.revalidate();
            datepanel.modifierPanel.repaint();

            // finally the "date"
            datepanel.dateField.setText(items.elementAt(0).toString());
            items.removeElementAt(0);

        } // displayDate
    } // class DatePanel

    /**
     * <p>Title: DimensionPanel</p>
     * <p>Description: A panel to edit dimension
     * </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: NCSR "Demokritos"</p>
     * @author Dimitris Spiliotopoulos
     * @version 1.0
     */
    class DimensionPanel
        extends JPanel {

        JTextField dimensionField;
        DimensionCombo dimensionCombo;

        public DimensionPanel() {

            setPreferredSize(new Dimension(150, 40));
            setLayout(new GridBagLayout());

            dimensionField = new JTextField();
            dimensionField.setPreferredSize(new Dimension(60, 25));

            dimensionCombo = new DimensionCombo();

            add(dimensionField);
            add(dimensionCombo);
        }

        public String getDimension() {

            StringBuffer dimension = new StringBuffer();
            if (dimensionField.getText().equalsIgnoreCase("")) {
                dimension.append("0 ");
            }
            else {
                dimension.append(dimensionField.getText() + " ");
            }
            dimension.append(dimensionCombo.getSelectedItem().toString());
            return dimension.toString();
        }

        public void setDimension(String oldValue) {
            String dimensionOld = new String(oldValue);
            //System.out.println(dimensionOld);

            if ( (dimensionOld.length() != 0) && (!dimensionOld.equalsIgnoreCase("Select a \"Dimension\""))) {
                dimensionField.setText(dimensionOld.substring(0, dimensionOld.indexOf(" ")));
                dimensionCombo.setSelectedItem(dimensionOld.substring(dimensionOld.indexOf(" ") + 1, dimensionOld.length()));
            }
        } // setDimension
    } // class DimensionPanel
    
    
   
    


    /**
     * Creates a vector from a string of image items
     * @param imageStringItems The strings of the image items
     * @return This vector
     */
    public Vector setImageVector(String imageStringItems) {

        Vector imageVec = new Vector();
        if (imageStringItems.equalsIgnoreCase("Select images .....")) {
            // do nothing
        }
        else {
            StringTokenizer images = new StringTokenizer(imageStringItems, ">");
            //System.out.println("(DDialog.setImageVector)-tokenCount--- " + images.countTokens());
            while (images.hasMoreTokens()) {
                String token = images.nextToken();
                //System.out.println("(DDialog.setImageVector)-token--- " + token.substring(1));
                imageVec.addElement(new ListData(token.substring(1)));
            }
        }
        return imageVec;
    } // setImageVector

} // class DDialog
