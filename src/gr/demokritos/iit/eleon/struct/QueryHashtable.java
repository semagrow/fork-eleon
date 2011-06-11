/***************

<p>Title: Generalized Profiles</p>

<p>Description:
This class holds the ontology, without any linguistic or adaptation annotations. 
</p>

<p>Copyright (c) 2001-2009 National Centre for Scientific Research "Demokritos"</p>

@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009)

***************/


package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.authoring.*;
import gr.demokritos.iit.eleon.ui.MessageDialog;
import gr.demokritos.iit.eleon.ui.StoriesPanel;
import gr.demokritos.iit.eleon.ui.StoriesTable;
import gr.demokritos.iit.eleon.ui.StoriesTableListener;
import gr.demokritos.iit.eleon.ui.StoriesVector;
import gr.demokritos.iit.eleon.ui.UsersPanel;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.tree.*;

public class QueryHashtable extends Object implements Serializable {
    Vector currentValues;
    Hashtable currentSpecialValues;
    Hashtable currentFieldValues;
    Hashtable currentStoryValues;
    Object currentStoryText;
    Vector currentStoriesVector;
    
    String hashtableEntry;
    String field;
    String currentValue;
    String currentSpecialValue;
    
   // Font panelFont = new Font(Mpiro.selectedFont,Font.BOLD,11);
   // Dimension leftMargin = new Dimension(150,10);
    public Hashtable mainDBHashtable;
    public Hashtable propertiesHashtable;
    public Hashtable equivalentClassesHashtable;
    public Hashtable superClassesHashtable;
    public Hashtable valueRestrictionsHashtable;
    Hashtable currentHashtable;
    Hashtable defaultHashtable;
    Vector currentVector;
    public Hashtable annotationPropertiesHashtable;
    
    // used only in
    Vector allEntityTypesBeforeRemove = null;
    Vector allEntitiesAndGenericBeforeRemove = null;

    //public Vector robotCharVector;

    //public Hashtable robotCharValuesHashtable;
    
    /** Create the main hashtable. As keys the entity types */
    public QueryHashtable() {
        mainDBHashtable = new Hashtable();
        propertiesHashtable = new Hashtable();
        equivalentClassesHashtable = new Hashtable();
        superClassesHashtable = new Hashtable();
        valueRestrictionsHashtable = new Hashtable();
        annotationPropertiesHashtable = new Hashtable();
       // robotCharValuesHashtable=new Hashtable();
       // robotCharVector=new Vector();
    }

     public QueryHashtable(Hashtable mainDBHashtable, Hashtable propertiesHashtable, Hashtable equivalentClassesHashtable, Hashtable superClassesHashtable, Hashtable valueRestrictionsHashtable, Hashtable annotationPropertiesHashtable) {
        this.mainDBHashtable = mainDBHashtable;
        this.propertiesHashtable = propertiesHashtable;
        this.equivalentClassesHashtable = equivalentClassesHashtable;
        this.superClassesHashtable = superClassesHashtable;
        this.valueRestrictionsHashtable = valueRestrictionsHashtable;
        this.annotationPropertiesHashtable = annotationPropertiesHashtable;
     //   this.robotCharValuesHashtable=robotCharValuesHashtable;
    //    this.robotCharVector=robotCharVector;
    }
    
    
    /**
     * This method is used when renaming a node.
     * Checks the input name for invalid characters.
     */
    public String checkNameValidity(String nodeName) {
        Vector invalidEntriesVector = new Vector();
        StringBuffer invalidEntriesString = new StringBuffer();
        for (int k=0; k < nodeName.length(); k++) {
            Character n = new Character(nodeName.charAt(k));
            if (  !n.isLetterOrDigit(nodeName.charAt(k)) && !n.toString().equalsIgnoreCase("-") && !invalidEntriesVector.contains(n)  ) {
                invalidEntriesVector.addElement(n);
            }
        }
        if (invalidEntriesVector.isEmpty()) {
            return "VALID";
        } else {
            //System.out.println(invalidEntriesVector.toString());
            Enumeration enumer = invalidEntriesVector.elements();
            while (enumer.hasMoreElements()) {
                String k = enumer.nextElement().toString();
                invalidEntriesString.append(" \"" + k + "\"");
            }
            return invalidEntriesString.toString();
        }
    }
    
    /**
     * This method is used for accepting only numbers
     * Checks the input name for invalid characters.
     * It is used in: DDialog.DatePanel.getDate();
     */
    public String checkNameValidityNumberOnly(String nodeName) {
        Vector invalidEntriesVector = new Vector();
        StringBuffer invalidEntriesString = new StringBuffer();
        for (int k=0; k < nodeName.length(); k++) {
            Character n = new Character(nodeName.charAt(k));
            if ( !n.isDigit(nodeName.charAt(k)) && !invalidEntriesVector.contains(n)  ) {
                invalidEntriesVector.addElement(n);
                //System.out.println(n);
            }
        }
        if (invalidEntriesVector.isEmpty()) {
            return "VALID";
        } else {
            //System.out.println(invalidEntriesVector.toString());
            Enumeration enumer = invalidEntriesVector.elements();
            while (enumer.hasMoreElements()) {
                String k = enumer.nextElement().toString();
                invalidEntriesString.append(" \"" + k + "\"");
            }
            return invalidEntriesString.toString();
        }
    }
    
    
    /**
     * This method checks the mainDBHashtable for name replication.
     * It is used in methods:
     * createBasicEntityType(), createEntity(), createSubType()
     * and in KDialog (option: "rename").
     */
    public int checkName(String nodeName) {
        int check = 0;
        
        if (nodeName.indexOf(" ") >= 0) {
            check = 3;				 // Empty space is not allowed!
        } else if (nodeName.equalsIgnoreCase("")) {
            check = 2;				 // Have to specify a node-name!
        } else if (nodeName.startsWith("UM_"))//kallonis
        {
            check = 4;				 // Starts with UM_
        } else if (nodeName.charAt(0)>='0' && nodeName.charAt(0)<='9') {
            check = 5;				 //start with number
        } else {
            Enumeration enu = mainDBHashtable.keys();
            while (enu.hasMoreElements()) {
                String keyName = enu.nextElement().toString();
                if (keyName.equals(nodeName) ) {
                    check = 1;  // This node-name already exists!
                }
            }
            if (nodeName.equalsIgnoreCase("Data-types") ||
                    nodeName.equalsIgnoreCase("Date") ||
                    nodeName.equalsIgnoreCase("Dimension") ||
                    nodeName.equalsIgnoreCase("Number") ||
                    nodeName.equalsIgnoreCase("String") ) {
                check = 1;
            }
        }
        return check;
    }
    
    
    /**
     * This method is called when a basic-entity-type is created
     */
    public void createBasicEntityType(String parentNodeName ,String createdNodeName) {
        NodeVector nv = new NodeVector(parentNodeName);
        mainDBHashtable.put(createdNodeName, nv);
    }
    
    /**
     * This method is called when a sub-type is created
     */
    public void createSubType(String parentNodeName, String createdNodeName) {
        //System.out.println("parent:"+parentNodeName+"created>:"+createdNodeName);
        int start = new DefaultVector("x").size();
        int end = DataBaseTable.m_data.getRowCount();
        int addedRows = end - start;
        //System.out.println(String.valueOf(addedRows));
        // create a new default nodeVector,
        // add to its first vector (DataBaseTableVector) a number of elements
        // equal to the number of addedRows to his parent's dataBaseTable
        // and add the augmented nodeVector in the mainDBHashtable
        NodeVector nv = new NodeVector(nameWithoutOccur(parentNodeName));
        Vector tableVector =(Vector) nv.elementAt(0);
        //   tableVector=(Vector) tableVector.elementAt(0);
        //System.out.println("tableVector.toString(): "+tableVector.toString());
        for (int toAdd=0; toAdd<addedRows; toAdd++) {
            tableVector.addElement(new FieldData(toAdd));
        }
        nv.setElementAt(tableVector, 0);
        mainDBHashtable.put(createdNodeName, nv);
        //System.out.print(mainDBHashtable.toString());
        //System.out.println("nnnnnnnnnnnnnnnnnnnnnnnn"+nv.toString());
        // update children of parent (parentNodeName)
        for (int m=start; m<end; m++) {
            updateChildrenBasicTableVectors(m);
        }
        
        // update all children (new sub-type included) with parent's nounVector
        NodeVector pnv = (NodeVector)mainDBHashtable.get(parentNodeName);
        Vector nounVector = (Vector)pnv.elementAt(2);
        updateChildrenNounVectors(new Vector(), nounVector);
        
        //the new children inherits the restrictions of the parent
        for(int i=8;i<tableVector.size();i++){
            Vector temp=(Vector) tableVector.elementAt(i);
            String propertyName=temp.elementAt(0).toString();
            if(propertyName.equals("Subtype-of") || propertyName.equals("title") || propertyName.equals("name")|| propertyName.equals("shortname")|| propertyName.equals("notes")|| propertyName.equals("images")|| propertyName.equals("gender")|| propertyName.equals("number"))
                            continue;
            valueRestrictionsHashtable.put(createdNodeName+":"+propertyName, valueRestrictionsHashtable.get(parentNodeName+":"+propertyName));
        }
    }
    
    
    /**
     * Following two methods are called when an entity is created
     */
    public void createEntity(String parentNodeName, String createdNodeName) {
        NodeVector nv = new NodeVector(parentNodeName, createdNodeName);
        mainDBHashtable.put(createdNodeName, nv);
    }
    
    
    /**
     * Following method updates an already created Entity
     */
    public void updateCreatedEntity(String parentNodeName, String createdNodeName) {//System.out.println("parent***:"+parentNodeName+"created:"+createdNodeName);
        int start = 1; //add all properties of the parent except type
        int end = DataBaseTable.m_data.getRowCount();
        int addedRows = end - start;
        Vector ptv=getPropertiesForType(parentNodeName);
       // NodeVector entityTypeParentNode = (NodeVector) mainDBHashtable.get(parentNodeName);
       // Vector parentDatabaseTableVector =(Vector) entityTypeParentNode.elementAt(0);
        //  parentDatabaseTableVector =(Vector) parentDatabaseTableVector.elementAt(0);
        end=ptv.size();
        // create a new default nodeVector (for Entity),
        // add (according to fillerType of each row) to its first vector
        // (DataBaseEntityTableVector), especially to first vector's sub-vectors
        // (indep, en, it, gr), a number of elements equal to
        // the number of addedRows to his parent's dataBaseTable,
        // and add the augmented nodeVector in the mainDBHashtable
        NodeVector nv = new NodeVector(parentNodeName, createdNodeName);
        Vector tableVector =(Vector) nv.elementAt(0);
        //  tableVector =(Vector) tableVector.elementAt(0);
        Vector independentFieldsVector = (Vector)tableVector.elementAt(0);
        Vector englishFieldsVector = (Vector)tableVector.elementAt(1);
        Vector italianFieldsVector = (Vector)tableVector.elementAt(2);
        Vector greekFieldsVector = (Vector)tableVector.elementAt(3);
        
        //NodeVector pnv = (NodeVector)mainDBHashtable.get(parentNodeName);  // NodeVector
      //  Vector ptv = (Vector)pnv.elementAt(0);  // DataBaseTable
        
        for (int m=start; m<end; m++) {
            // Get current node's DataBaseTable rows at start to end
            // and check their fillerTypes. Then update children according to fillerType.
            FieldData pfd = (FieldData)ptv.elementAt(m);
            String fieldName = pfd.elementAt(0).toString();
            String fillerType = pfd.elementAt(1).toString();
            String setValued = pfd.elementAt(2).toString();
//System.out.println("plpllpl"+fieldName);
            //FieldData f1 = new FieldData(fieldName, "");
            //FieldData f2 = new FieldData(fieldName, fillerType);
            
            if (fillerType.equalsIgnoreCase("String")) {
                englishFieldsVector.addElement(new FieldData(fieldName, ""));
                italianFieldsVector.addElement(new FieldData(fieldName, ""));
                greekFieldsVector.addElement(new FieldData(fieldName, ""));
            } else if (fillerType.equalsIgnoreCase("Number")) {
                independentFieldsVector.addElement(new FieldData(fieldName, ""));
            } else if (setValued.equalsIgnoreCase("true")) {
                independentFieldsVector.addElement(new FieldData(fieldName, "Select multiple ....."));
            } else {
                independentFieldsVector.addElement(new FieldData(fieldName, "Select a " + "\"" + fillerType + "\""));
            }
        }
        // update tableVector
        tableVector.setElementAt(independentFieldsVector, 0);
        tableVector.setElementAt(englishFieldsVector, 1);
        tableVector.setElementAt(italianFieldsVector, 2);
        tableVector.setElementAt(greekFieldsVector, 3);
        // update nodeVector
        nv.setElementAt(tableVector, 0);
        
        mainDBHashtable.put(createdNodeName, nv);
    }
    
    
    /**
     * This method is called when an entity type or sub-type is created
     * Initialises the currentStoriesVector and adds the default story text for the first (default) story
     */
    public void createStory(String nodeName, String fieldName) {
        NodeVector nv = (NodeVector)mainDBHashtable.get(nodeName);
        StoriesVector sv = (StoriesVector)nv.elementAt(3);
        Hashtable englishValues = (Hashtable)sv.elementAt(0);
        Hashtable italianValues = (Hashtable)sv.elementAt(1);
        Hashtable greekValues = (Hashtable)sv.elementAt(2);
        
        //englishValues.put(fieldName, "Default story text in English");
        //italianValues.put(fieldName, "Default story text in Italian");
        //greekValues.put(fieldName, "Default story text in Greek");
        englishValues.put(fieldName, "");
        italianValues.put(fieldName, "");
        greekValues.put(fieldName, "");
    }
    
    /**
     * Following method creates DefaultUpperVector
     */
    public void createDefaultUpperVector() {
        NodeVector nv = (NodeVector)mainDBHashtable.get("Data Base");
        Vector u = (Vector)nv.elementAt(1);
        
        String[] upperTypes = {"3D-physical-object", "named-time-period",
        "spatial-location", "human",
        "substance-thing", "other-abstraction"};
        Vector upperVector = new Vector();
        for (int i=0; i < upperTypes.length; i++) {
            upperVector.addElement(new ListData(upperTypes[i]));
        }
        u = new Vector(upperVector);
        nv.setElementAt(u, 1);
    }
    
    
    /**
     *  This method is invoked when an entity-type (name) field (fieldname) is first created
     *  Used in: DataBaseTableListener
     */
    public void createDefaultHashtableField(String name, Vector usersVector) {
        String fieldname = "New-user-field";
        
        NodeVector nv = (NodeVector)mainDBHashtable.get(name);
        Hashtable microplanningHashtable = nv.getMicroPlanningValues();
        
        String[] allAttributesEnglish = {"SELECTION", "Verb", "Voice", "Tense", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub", "Casesub", "Referobj", "Caseobj", "Aggreg"};
        String[] allAttributesItalianGreek = {"SELECTION", "Verb", "Voice", "Tense", "Aspect", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub", "Casesub", "Referobj", "Caseobj", "Aggreg"};
        String[] allValuesEnglish = {"NoMicroPlanning", "Choose a verb identifier", "Active", "Past", "", "", "", "", "Indicative", "revFalse", "Auto", "Nominative", "Auto", "Accusative", "aggTrue"};
        String[] allValuesItalianGreek = {"NoMicroPlanning", "Choose a verb identifier", "Active", "Past", "Simple", "", "", "", "", "Indicative", "revFalse", "Auto", "Nominative", "Auto", "Accusative", "aggTrue"};
         PropertiesHashtableRecord prop=new PropertiesHashtableRecord();
        propertiesHashtable.put(fieldname, prop);
        for (int k=1; k<6; k++) {
            String microplanNumber = new Integer(k).toString();
            for (int i=0; i<allAttributesEnglish.length; i++) {
                updateHashtable(name, microplanNumber, fieldname, allAttributesEnglish[i], "English", allValuesEnglish[i]);
            }
            
            for (int i=0; i<allAttributesItalianGreek.length; i++) {
                updateHashtable(name, microplanNumber, fieldname, allAttributesItalianGreek[i], "Italian", allValuesItalianGreek[i]);
                updateHashtable(name, microplanNumber, fieldname, allAttributesItalianGreek[i], "Greek", allValuesItalianGreek[i]);
            }
            
            // adding the default value "0" as appropriateness for every user
           
            Enumeration usersVectorEnum = usersVector.elements();
            while (usersVectorEnum.hasMoreElements()) {
                String user = usersVectorEnum.nextElement().toString();
                updateHashtable(name, microplanNumber, fieldname, user, "English", "0");
                updateHashtable(name, microplanNumber, fieldname, user, "Italian", "0");
                updateHashtable(name, microplanNumber, fieldname, user, "Greek", "0");
            }
        }
       
    }
    
    
    
        /* ----------- UPDATE --------------------------------------------------
         * Following methods serve to update the appropriate children-entries
         * of mainDBHashtable with changes made in one of its ancestor-entries.
         * ---------------------------------------------------------------------
         */
    
        /* First, two methods to get current node's children:
         * getChildrenBasics() collects only children of basic-type and entity-type,
         * getChildrenEntities() collects only children which are entities.
         * And a third method to check where a table contains (if it does) a field:
         * tableVectorContainFieldAt()
         */
    public Hashtable getChildrenBasics(DefaultMutableTreeNode node) {
        Hashtable hash = new Hashtable();
        
        Enumeration enumer = node.preorderEnumeration();
        while (enumer.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumer.nextElement();
            if (child.isNodeSibling(node)) {
            } else {
                // A. get child's name
                String key = nameWithoutOccur(child.toString());
                // B. get child's icon
                Object obj = (Object)(child.getUserObject());
                IconData id = (IconData)obj;
                Icon ii = id.getIcon();
                ImageIcon im = (ImageIcon)ii;
                
                if (! mainDBHashtable.containsKey(key)) {
                    System.err.println("ERROR: the specified key: " + "\"" + key + "\"" + " does not exist!");
                } else {
                    if (im == DataBasePanel.ICON_BOOK || im == DataBasePanel.ICON_BASIC) {
                        Vector childVector = (Vector)mainDBHashtable.get(key);
                        hash.put(key, childVector);
                    }
                }
            }
        } // while
        return hash;
    }
    
    
    public Hashtable getChildrenEntities(DefaultMutableTreeNode node) {
        Hashtable hash = new Hashtable();
        
        Enumeration enumer = node.preorderEnumeration();
        while (enumer.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumer.nextElement();
            if (child.isNodeSibling(node)) {
            } else {
                // A. get child's name
                String key = nameWithoutOccur(child.toString());
                // B. get child's icon
                Object obj = (Object)(child.getUserObject());
                IconData id = (IconData)obj;
                Icon ii = id.getIcon();
                ImageIcon im = (ImageIcon)ii;
                
                if (! mainDBHashtable.containsKey(key)) {
                    System.err.println("ERROR: the specified key: " + "\"" + key + "\"" + " does not exist!");
                } else {
                    if (im == DataBasePanel.ICON_GEI || im == DataBasePanel.ICON_GENERIC) {
                        Vector childVector = (Vector)mainDBHashtable.get(key);
                        hash.put(key, childVector);
                    }
                }
            }
        } // while
        //System.out.println("(getChildrenEntities)----node- " + node.toString());
        //System.out.println("(getChildrenEntities)----children- " + hash.keySet().toString());
        return hash;
    }
    
    
    public int tableVectorContainFieldAt(Vector entityTableVector, String f, int langID) {
        int x = -1;
        Vector langVector = (Vector)entityTableVector.elementAt(langID);
        for (int y = 0; y < langVector.size(); y++) {
            FieldData fd = (FieldData)langVector.elementAt(y);
            String field = fd.elementAt(0).toString();
            if (field.equalsIgnoreCase(f)) {
                x = y;  // langVector DO contain f
            } else {
                // langVector DO NOT contain f
            }
        }
        return x;
    }
    
    
        /* ----------- UPDATE 1 ------------------------------------------------
         * This method is called when changing a row of the DataBaseTable
         * of a node. It updates with the new value these children ONLY
         * which are BasicTypes or EntityTypes.
         * ---------------------------------------------------------------------
         */
    public void updateChildrenBasicTableVectors(int rowChanged) {
        // First, get the row that has been changed as vector (FieldData).
        FieldData fd = (FieldData)DataBaseTable.m_data.m_vector.elementAt(rowChanged);
        String a = new String(fd.elementAt(0).toString());
        String b = new String(fd.elementAt(1).toString());
        String c = new String(fd.elementAt(2).toString());
        //System.out.println("a: "+a);
        //System.out.println("b: "+b);
        //System.out.println("c: "+c);
        boolean cc;
        if (c.equalsIgnoreCase("true")) {
            cc = true;
        } else {
            cc = false;
        }
        String d = new String("");
        FieldData newfd = new FieldData(a, b, cc , d);
        
        //System.out.println(newfd);  ////////++++++++++++++
        
        // Updating BasicTypes & EntityTypes.
        Enumeration enumeration1 = getChildrenBasics(DataBasePanel.last).elements();
        while (enumeration1.hasMoreElements()) {
            NodeVector childVector = (NodeVector)enumeration1.nextElement();
            Vector childTable = (Vector)childVector.elementAt(0);
            childTable.setElementAt(newfd, rowChanged);
            childVector.setElementAt(childTable, 0);
            //System.out.println(childTable);  ////////++++++++++++++
        }
    }
    
    
        /* ----------- UPDATE 2 ------------------------------------------------
         * This method is called when changing the field-name in a DataBaseTable's row
         * and updates only children-Entities according to filler-type of the field.
         * Filler-type may be String or not.
         * If not a String language independent fields are updated,
         * else english/italian/greek fields.
         * ---------------------------------------------------------------------
         */
    public void updateChildrenEntitiesFieldColumn(String oldValue, String newValue, String fillerType) {
        // A first check!
        if (newValue.equalsIgnoreCase(oldValue)) {
        } else {
            // A short parenthese to update current-node's templateVector
            NodeVector curVec = (NodeVector)mainDBHashtable.get(DataBasePanel.last.toString());
            TemplateVector temVec = (TemplateVector)curVec.elementAt(4);
            Hashtable enValues = (Hashtable)temVec.elementAt(0);
            Hashtable itValues = (Hashtable)temVec.elementAt(1);
            Hashtable grValues = (Hashtable)temVec.elementAt(2);
            if (enValues.containsKey(oldValue)) {
                Vector v = (Vector)enValues.get(oldValue);
                enValues.put(newValue, v);
                enValues.remove(oldValue);
            }
            if (itValues.containsKey(oldValue)) {
                Vector v = (Vector)itValues.get(oldValue);
                itValues.put(newValue, v);
                itValues.remove(oldValue);
            }
            if (grValues.containsKey(oldValue)) {
                Vector v = (Vector)grValues.get(oldValue);
                grValues.put(newValue, v);
                grValues.remove(oldValue);
            }
            temVec.setElementAt(enValues, 0);
            temVec.setElementAt(itValues, 1);
            temVec.setElementAt(grValues, 2);	// end updating current templateVector
            
            // The 2 in method getChildren() forces it to collect only Entities.
            
            
       Hashtable entitiesHash=getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
        Enumeration entities=entitiesHash.keys();

        while(entities.hasMoreElements()){
            String nextEntity=entities.nextElement().toString();
            NodeVector ent=(NodeVector)mainDBHashtable.get(nextEntity);
                ent.renameFieldData(oldValue, newValue,1);
                ent.renameFieldData(oldValue, newValue, 2);
                ent.renameFieldData(oldValue, newValue, 3);
                ent.renameFieldData(oldValue, newValue, 4);
            }
            
            
            
            
            Enumeration enumeration2 = getChildrenEntities(DataBasePanel.last).keys();
            while (enumeration2.hasMoreElements()) {
                String entityName = enumeration2.nextElement().toString();
                System.out.println("sssssss"+entityName);
                NodeVector childVector = (NodeVector)mainDBHashtable.get(entityName);
                Vector childTable = (Vector)childVector.elementAt(0);  // A vector of 4 vectors
                Vector independentFieldsVector = (Vector)childTable.elementAt(0);
                Vector englishFieldsVector = (Vector)childTable.elementAt(1);
                Vector italianFieldsVector = (Vector)childTable.elementAt(2);
                Vector greekFieldsVector = (Vector)childTable.elementAt(3);
                
                // check if tables already contain fieldName
                int indCheck = tableVectorContainFieldAt(childTable, oldValue, 0);
                int engCheck = tableVectorContainFieldAt(childTable, oldValue, 1);
                int itaCheck = tableVectorContainFieldAt(childTable, oldValue, 2);
                int greCheck = tableVectorContainFieldAt(childTable, oldValue, 3);
                
                // FIRST: Filler-type is a String
                if (fillerType.equalsIgnoreCase("String")) {
                    // 1. updating english fields
                    if (engCheck >= 0) {
                        FieldData currentRowEN = (FieldData)englishFieldsVector.elementAt(engCheck);
                        String currentFillerEN = currentRowEN.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdEN = new FieldData(newValue, currentFillerEN);
                        englishFieldsVector.setElementAt(fdEN, engCheck);
                    }
                    
                    // 2. updating italian fields
                    if (itaCheck >= 0) {
                        FieldData currentRowIT = (FieldData)italianFieldsVector.elementAt(itaCheck);
                        String currentFillerIT = currentRowIT.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdIT = new FieldData(newValue, currentFillerIT);
                        italianFieldsVector.setElementAt(fdIT, itaCheck);
                    }
                    
                    // 3. updating greek fields
                    if (greCheck >= 0) {
                        FieldData currentRowGR = (FieldData)greekFieldsVector.elementAt(greCheck);
                        String currentFillerGR = currentRowGR.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdGR = new FieldData(newValue, currentFillerGR);
                        greekFieldsVector.setElementAt(fdGR, greCheck);
                    }
                } // end filler-type is a String
                
                // SECOND: Filler-type not a String
                else {
                    // 4. Updating the language-independent fields
                    if (indCheck >= 0) {
                        FieldData currentRowIN = (FieldData)independentFieldsVector.elementAt(indCheck);
                        String currentFillerIN = currentRowIN.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdIN = new FieldData(newValue, currentFillerIN);
                        independentFieldsVector.setElementAt(fdIN, indCheck);
                    }
                }
                // Update the whole NodeVector
                childTable.setElementAt(independentFieldsVector, 0);
                childTable.setElementAt(englishFieldsVector, 1);
                childTable.setElementAt(italianFieldsVector, 2);
                childTable.setElementAt(greekFieldsVector, 3);
                childVector.setElementAt(childTable, 0);
            } // while
        }
    } // method updateChildrenEntitiesFieldColumn()
    
    
    
    /*
        public void updateChildrenEntitiesFieldColumn(String oldValue, String newValue, String fillerType, DefaultMutableTreeNode parentNode)
        {
                // A first check!
                if (newValue.equalsIgnoreCase(oldValue))
                {}
                else
                {
                        // A short parenthese to update current-node's templateVector
                        NodeVector curVec = (NodeVector)mainDBHashtable.get(parentNode.toString());
                        TemplateVector temVec = (TemplateVector)curVec.elementAt(4);
                        Hashtable enValues = (Hashtable)temVec.elementAt(0);
                        Hashtable itValues = (Hashtable)temVec.elementAt(1);
                        Hashtable grValues = (Hashtable)temVec.elementAt(2);
                        if (enValues.containsKey(oldValue))
                        {
                                Vector v = (Vector)enValues.get(oldValue);
                                enValues.put(newValue, v);
                                enValues.remove(oldValue);
                        }
                        if (itValues.containsKey(oldValue))
                        {
                                Vector v = (Vector)itValues.get(oldValue);
                                itValues.put(newValue, v);
                                itValues.remove(oldValue);
                        }
                        if (grValues.containsKey(oldValue))
                        {
                                Vector v = (Vector)grValues.get(oldValue);
                                grValues.put(newValue, v);
                                grValues.remove(oldValue);
                        }
                        temVec.setElementAt(enValues, 0);
                        temVec.setElementAt(itValues, 1);
                        temVec.setElementAt(grValues, 2);	// end updating current templateVector
     
                        // The 2 in method getChildren() forces it to collect only Entities.
                        Enumeration enumeration2 = getChildrenEntities(DataBasePanel.parentNode).keys();
                        while (enumeration2.hasMoreElements())
                        {
                                String entityName = enumeration2.nextElement().toString();
                                NodeVector childVector = (NodeVector)mainDBHashtable.get(entityName);
                                Vector childTable = (Vector)childVector.elementAt(0);  // A vector of 4 vectors
                                Vector independentFieldsVector = (Vector)childTable.elementAt(0);
                                Vector englishFieldsVector = (Vector)childTable.elementAt(1);
                                Vector italianFieldsVector = (Vector)childTable.elementAt(2);
                                Vector greekFieldsVector = (Vector)childTable.elementAt(3);
     
                                // check if tables already contain fieldName
                                int indCheck = tableVectorContainFieldAt(childTable, oldValue, 0);
                                int engCheck = tableVectorContainFieldAt(childTable, oldValue, 1);
                                int itaCheck = tableVectorContainFieldAt(childTable, oldValue, 2);
                                int greCheck = tableVectorContainFieldAt(childTable, oldValue, 3);
     
                                // FIRST: Filler-type is a String
                                if (fillerType.equalsIgnoreCase("String"))
                                {
                                        // 1. updating english fields
                                        if (engCheck >= 0)
                                        {
                                                FieldData currentRowEN = (FieldData)englishFieldsVector.elementAt(engCheck);
                                                String currentFillerEN = currentRowEN.elementAt(1).toString();
                                                // make a new row (FieldData) from new FieldName and previous FillerText
                                                FieldData fdEN = new FieldData(newValue, currentFillerEN);
                                                englishFieldsVector.setElementAt(fdEN, engCheck);
                                        }
     
                                        // 2. updating italian fields
                                        if (itaCheck >= 0)
                                        {
                                                FieldData currentRowIT = (FieldData)italianFieldsVector.elementAt(itaCheck);
                                                String currentFillerIT = currentRowIT.elementAt(1).toString();
                                                // make a new row (FieldData) from new FieldName and previous FillerText
                                                FieldData fdIT = new FieldData(newValue, currentFillerIT);
                                                italianFieldsVector.setElementAt(fdIT, itaCheck);
                                        }
     
                                        // 3. updating greek fields
                                        if (greCheck >= 0)
                                        {
                                                FieldData currentRowGR = (FieldData)greekFieldsVector.elementAt(greCheck);
                                                String currentFillerGR = currentRowGR.elementAt(1).toString();
                                                // make a new row (FieldData) from new FieldName and previous FillerText
                                                FieldData fdGR = new FieldData(newValue, currentFillerGR);
                                                greekFieldsVector.setElementAt(fdGR, greCheck);
                                        }
                                } // end filler-type is a String
     
                          // SECOND: Filler-type not a String
                          else
                          {
                                   // 4. Updating the language-independent fields
                                        if (indCheck >= 0)
                                        {
                                                FieldData currentRowIN = (FieldData)independentFieldsVector.elementAt(indCheck);
                                                String currentFillerIN = currentRowIN.elementAt(1).toString();
                                                // make a new row (FieldData) from new FieldName and previous FillerText
                                                FieldData fdIN = new FieldData(newValue, currentFillerIN);
                                                independentFieldsVector.setElementAt(fdIN, indCheck);
                                        }
                          }
                          // Update the whole NodeVector
                  childTable.setElementAt(independentFieldsVector, 0);
                  childTable.setElementAt(englishFieldsVector, 1);
                  childTable.setElementAt(italianFieldsVector, 2);
                  childTable.setElementAt(greekFieldsVector, 3);
                          childVector.setElementAt(childTable, 0);
                        } // while
                }
        } // method updateChildrenEntitiesFieldColumn()
     
     
     */
    public void updateChildrenEntitiesFieldColumn(String oldValue, String newValue, String fillerType, DefaultMutableTreeNode node) {
        // A first check!
        if (newValue.equalsIgnoreCase(oldValue)) {
        } else {
            // A short parenthese to update current-node's templateVector
            NodeVector curVec = (NodeVector)mainDBHashtable.get(node.toString());
            TemplateVector temVec = (TemplateVector)curVec.elementAt(4);
            Hashtable enValues = (Hashtable)temVec.elementAt(0);
            Hashtable itValues = (Hashtable)temVec.elementAt(1);
            Hashtable grValues = (Hashtable)temVec.elementAt(2);
            if (enValues.containsKey(oldValue)) {
                Vector v = (Vector)enValues.get(oldValue);
                enValues.put(newValue, v);
                enValues.remove(oldValue);
            }
            if (itValues.containsKey(oldValue)) {
                Vector v = (Vector)itValues.get(oldValue);
                itValues.put(newValue, v);
                itValues.remove(oldValue);
            }
            if (grValues.containsKey(oldValue)) {
                Vector v = (Vector)grValues.get(oldValue);
                grValues.put(newValue, v);
                grValues.remove(oldValue);
            }
            temVec.setElementAt(enValues, 0);
            temVec.setElementAt(itValues, 1);
            temVec.setElementAt(grValues, 2);	// end updating current templateVector
            
            // The 2 in method getChildren() forces it to collect only Entities.
            Enumeration enumeration2 = getChildrenEntities(node).keys();
            while (enumeration2.hasMoreElements()) {
                String entityName = enumeration2.nextElement().toString();
                NodeVector childVector = (NodeVector)mainDBHashtable.get(entityName);
                Vector childTable = (Vector)childVector.elementAt(0);  // A vector of 4 vectors
                Vector independentFieldsVector = (Vector)childTable.elementAt(0);
                Vector englishFieldsVector = (Vector)childTable.elementAt(1);
                Vector italianFieldsVector = (Vector)childTable.elementAt(2);
                Vector greekFieldsVector = (Vector)childTable.elementAt(3);
                
                // check if tables already contain fieldName
                int indCheck = tableVectorContainFieldAt(childTable, oldValue, 0);
                int engCheck = tableVectorContainFieldAt(childTable, oldValue, 1);
                int itaCheck = tableVectorContainFieldAt(childTable, oldValue, 2);
                int greCheck = tableVectorContainFieldAt(childTable, oldValue, 3);
                
                // FIRST: Filler-type is a String
                if (fillerType.equalsIgnoreCase("String")) {
                    // 1. updating english fields
                    if (engCheck >= 0) {
                        FieldData currentRowEN = (FieldData)englishFieldsVector.elementAt(engCheck);
                        String currentFillerEN = currentRowEN.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdEN = new FieldData(newValue, currentFillerEN);
                        englishFieldsVector.setElementAt(fdEN, engCheck);
                    }
                    
                    // 2. updating italian fields
                    if (itaCheck >= 0) {
                        FieldData currentRowIT = (FieldData)italianFieldsVector.elementAt(itaCheck);
                        String currentFillerIT = currentRowIT.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdIT = new FieldData(newValue, currentFillerIT);
                        italianFieldsVector.setElementAt(fdIT, itaCheck);
                    }
                    
                    // 3. updating greek fields
                    if (greCheck >= 0) {
                        FieldData currentRowGR = (FieldData)greekFieldsVector.elementAt(greCheck);
                        String currentFillerGR = currentRowGR.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdGR = new FieldData(newValue, currentFillerGR);
                        greekFieldsVector.setElementAt(fdGR, greCheck);
                    }
                } // end filler-type is a String
                
                // SECOND: Filler-type not a String
                else {
                    // 4. Updating the language-independent fields
                    if (indCheck >= 0) {
                        FieldData currentRowIN = (FieldData)independentFieldsVector.elementAt(indCheck);
                        String currentFillerIN = currentRowIN.elementAt(1).toString();
                        // make a new row (FieldData) from new FieldName and previous FillerText
                        FieldData fdIN = new FieldData(newValue, currentFillerIN);
                        independentFieldsVector.setElementAt(fdIN, indCheck);
                    }
                }
                // Update the whole NodeVector
                childTable.setElementAt(independentFieldsVector, 0);
                childTable.setElementAt(englishFieldsVector, 1);
                childTable.setElementAt(italianFieldsVector, 2);
                childTable.setElementAt(greekFieldsVector, 3);
                childVector.setElementAt(childTable, 0);
            } // while
        }
    }
    
    
        /* ----------- UPDATE 3 ------------------------------------------------
         * This method is called when changing the filler-type
         * in a DataBaseTable's row and updates only children-Entities.
         * Filler-type may be String or not.
         * If not a String language independent fields are updated,
         * else english/italian/greek fields.
         * ---------------------------------------------------------------------
         */
    public void updateChildrenEntitiesFillerColumn(DefaultMutableTreeNode node, String oldValue, String newValue, String fieldName) {
        if (newValue.equalsIgnoreCase("string") && oldValue.equalsIgnoreCase("string")) {
        } else {
            // The 2 in method getChildren() forces it to collect only Entities.
            //Vector childrenEntititesAndGeneric = getChildrenVectorFromMainDBHashtable(node.toString(), "Entity+Generic");
            Vector childrenEntititesAndGeneric = getFullPathChildrenVectorFromMainDBHashtable(node.toString(), "Entity+Generic");
            //System.out.println("(Query583)---- " + "childrenEntititesAndGeneric: " + childrenEntititesAndGeneric);
            Enumeration enumeration2 = childrenEntititesAndGeneric.elements();
            //Enumeration enumeration2 = getChildrenEntities(node).keys();
            while (enumeration2.hasMoreElements()) {
                String nextEntity = enumeration2.nextElement().toString();
                //System.out.println("(Query583)---- " + "node: " + node.toString() + "   ||   entity=== " + nextEntity);
                
                NodeVector childVector = (NodeVector)mainDBHashtable.get(nextEntity);
                Vector childTable = (Vector)childVector.elementAt(0);  // A vector of 4 vectors
                Vector independentFieldsVector = (Vector)childTable.elementAt(0);
                Vector englishFieldsVector = (Vector)childTable.elementAt(1);
                Vector italianFieldsVector = (Vector)childTable.elementAt(2);
                Vector greekFieldsVector = (Vector)childTable.elementAt(3);
                
                // check if tables already contain fieldName
                int indCheck = tableVectorContainFieldAt(childTable, fieldName, 0);
                int engCheck = tableVectorContainFieldAt(childTable, fieldName, 1);
                int itaCheck = tableVectorContainFieldAt(childTable, fieldName, 2);
                int greCheck = tableVectorContainFieldAt(childTable, fieldName, 3);
                
                
                if (newValue.equalsIgnoreCase("string") && !oldValue.equalsIgnoreCase("string")) {
                    System.out.println("GGGGG"+String.valueOf(indCheck)+fieldName);
                    if (indCheck<0)
                        return;
                    // Updating the language-dependent fields
                    FieldData fd1 = new FieldData(fieldName, "");
                    if (engCheck < 0) {
                        englishFieldsVector.addElement(fd1);
                    }
                    FieldData fd2 = new FieldData(fieldName, "");
                    if (itaCheck < 0) {
                        italianFieldsVector.addElement(fd2);
                    }
                    FieldData fd3 = new FieldData(fieldName, "");
                    if (greCheck < 0) {
                        greekFieldsVector.addElement(fd3);
                    }
                    
                    // Updating the language-independent fields
                    independentFieldsVector.removeElementAt(indCheck);
                } else if (!newValue.equalsIgnoreCase("string") && oldValue.equalsIgnoreCase("string")) {
                    System.out.println("GGGGG"+String.valueOf(engCheck)+String.valueOf(itaCheck)+String.valueOf(greCheck)+fieldName);
                    if ((engCheck<0)&&(itaCheck<0)&&(greCheck<0))
                        return;
                    FieldData fdALL;
                    if (newValue.equalsIgnoreCase("number"))
                        //(newValue.equalsIgnoreCase("date")) ||
                        //(newValue.equalsIgnoreCase("dimension")))
                    {
                        fdALL = new FieldData(fieldName, "");
                    } else {
                        fdALL = new FieldData(fieldName, "Select a " + "\"" + newValue + "\"");
                    }
                    
                    // Updating the language-independent fields
                    if (indCheck < 0) {
                        independentFieldsVector.addElement(fdALL);
                    }
                    System.out.println("LLLLLLLLL"+String.valueOf(engCheck));
                    // Updating the english fields
                    englishFieldsVector.removeElementAt(engCheck);
                    
                    // Updating the italian fields
                    italianFieldsVector.removeElementAt(itaCheck);
                    
                    // Updating the greek fields
                    greekFieldsVector.removeElementAt(greCheck);
                } else if (!newValue.equalsIgnoreCase("string") && !oldValue.equalsIgnoreCase("string")) {
                    //System.out.println("3. " + newValue + " WAS " + oldValue);
                    FieldData f;
                    if (newValue.equalsIgnoreCase("number")) //||
                        //(newValue.equalsIgnoreCase("date")) ||
                        //(newValue.equalsIgnoreCase("dimension")))
                    {
                        f = new FieldData(fieldName, "");
                    } else {
                        f = new FieldData(fieldName, "Select a " + "\"" + newValue + "\"");
                    }
                    
                    // Updating the independent fields
                    if (newValue.equalsIgnoreCase("Select multiple .....")) {
                        f = new FieldData(fieldName, newValue);
                    }
                    independentFieldsVector.setElementAt(f, indCheck);
                }
                // Update the whole NodeVector
                childTable.setElementAt(independentFieldsVector, 0);
                childTable.setElementAt(englishFieldsVector, 1);
                childTable.setElementAt(italianFieldsVector, 2);
                childTable.setElementAt(greekFieldsVector, 3);
                childVector.setElementAt(childTable, 0);
            } // while
        }
    }// method updateChildrenEntitiesFillerColumn()
    
    
        /* ----------- UPDATE 4a ------------------------------------------------
         * This method is called when inserting a row in a DataBaseTable.
         * ---------------------------------------------------------------------
         */
    public void insertRowInDataBaseTable(String name,String prop,String domain, String range,int rowPosition) {
        Enumeration en=DataBasePanel.top.preorderEnumeration();
        DefaultMutableTreeNode dmtn=new DefaultMutableTreeNode();
        while(en.hasMoreElements()){
            dmtn=(DefaultMutableTreeNode) en.nextElement();
            if (dmtn.toString().equalsIgnoreCase(name)) break;
        }
        //DataBaseTable.m_data.m_vector.insertElementAt(new FieldData(rowPosition+1), rowPosition);
        
        // Insert 1 row in descendants table vectors:
        // 1. Get BasicType and EntityType nodes
        Hashtable basicNodeVectors = getChildrenBasics(dmtn);
        Enumeration enum1 = basicNodeVectors.keys();
        while (enum1.hasMoreElements()) {
            String key = (String)enum1.nextElement();
            String key1=key;
            
            
            if(key.substring(0,key.length()-1).endsWith("_occur")) {
                key=key.substring(0, key.length()-7);
            }
            Hashtable allEntityTypes6 = (Hashtable) getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            Enumeration allTypesNames6=allEntityTypes6.keys();
            while(allTypesNames6.hasMoreElements()) {
                DefaultMutableTreeNode nextNode6=null;
                Object nextEl6=allTypesNames6.nextElement();
                
                
                
                if (((nextEl6.toString().startsWith(key+"_occur")||nextEl6.toString().equalsIgnoreCase(key)))&&(!(nextEl6.toString().equalsIgnoreCase(key1)))) {
                    System.out.println("sddssdsdfsdfsdfadasad"+nextEl6.toString());
                    Enumeration topchildren6=DataBasePanel.top.preorderEnumeration();
                    
                    while(topchildren6.hasMoreElements()) {
                        nextNode6=(DefaultMutableTreeNode) topchildren6.nextElement();
                        //System.out.println("2121212221212"+nextNode.toString());
                        if (nextNode6.toString().equalsIgnoreCase(nextEl6.toString()))
                            break;
                    }
                    
                    //System.out.println("sddssdsdfsdfsdfadasad"+nextNode.toString());
                    NodeVector nd6=(NodeVector) mainDBHashtable.get(nextEl6);
                    //    NodeVector nd= (NodeVector) nextEl;
                    Vector oneChild6 = (Vector)nd6.elementAt(0);
                    if (!oneChild6.contains(new FieldData(rowPosition+1)))
                        oneChild6.insertElementAt(new FieldData(prop,range,true,""), rowPosition);
                    
                    NodeVector oneChildNode6 = (NodeVector)mainDBHashtable.get(nextEl6.toString());
                    //System.out.println(oneChildNode.toString()+"sdfsdfsdfs");
                    oneChildNode6.setElementAt(oneChild6, 0);
                    
                    
                    Hashtable basicNodeVectors16 = getChildrenBasics(nextNode6);
                    Enumeration enum36 = basicNodeVectors16.keys();
                    while (enum36.hasMoreElements()) {
                        String key6 = (String)enum36.nextElement();
                        //System.out.println("asasasasasas"+key1);
                        NodeVector oneChildVector16 = (NodeVector)basicNodeVectors16.get(key6);
                        Vector oneChildTableVector16 = (Vector)oneChildVector16.elementAt(0);
                        if (!oneChildTableVector16.contains(new FieldData(rowPosition+1)))
                            oneChildTableVector16.insertElementAt(new FieldData(prop,range,true,""), rowPosition);
                        
                        NodeVector oneChildNodeVector16 = (NodeVector)mainDBHashtable.get(key6);
                        oneChildNodeVector16.setElementAt(oneChildTableVector16, 0);
                    }
                    
                    // 2. Get Entities (while default filler-type is String, a new row is added
                    //    at each child-entity language-independent fields-table.
                    Hashtable entityNodeVectors16 = getChildrenEntities(nextNode6);
                    Enumeration enum46 = entityNodeVectors16.keys();
                    while (enum46.hasMoreElements())
                        
                    {
                  /*  String occur="";
                    if(nextNode.toString().substring(0,nextNode.toString().length()-1).endsWith("_occur"))
                        occur=nextNode.toString().substring(nextNode.toString().length()-7,nextNode.toString().length());
                        String key2 = (String)enum4.nextElement()+occur;*/
                        
                        String key26 = (String)enum46.nextElement();
                        System.out.println("nextNode::::::: "+nextNode6.toString()+"  key2:::   "+key26);
                        NodeVector oneChildVector16 = (NodeVector)entityNodeVectors16.get(key26);
                        Vector oneChildTableVector16 = (Vector)oneChildVector16.elementAt(0);  // a vector of
                        // 4 vectors
                        Vector independentFieldsVector16 = (Vector)oneChildTableVector16.elementAt(0);
                        Vector englishFieldsVector16 = (Vector)oneChildTableVector16.elementAt(1);
                        Vector italianFieldsVector16 = (Vector)oneChildTableVector16.elementAt(2);
                        Vector greekFieldsVector16 = (Vector)oneChildTableVector16.elementAt(3);
                        
                        // default: a new row added at every lang-dependent fields-table.if (!oneChild6.contains(new FieldData(rowPosition+1)))
                        if (range.equalsIgnoreCase("String")){
                            if (!greekFieldsVector16.contains(new FieldData(prop, ""))){
                                greekFieldsVector16.insertElementAt(new FieldData(prop, ""), greekFieldsVector16.size());
                                italianFieldsVector16.insertElementAt(new FieldData(prop, ""), italianFieldsVector16.size());
                                englishFieldsVector16.insertElementAt(new FieldData(prop, ""), englishFieldsVector16.size());
                            }} else independentFieldsVector16.insertElementAt(new FieldData(prop, ""), independentFieldsVector16.size());
                        Vector updatedTableVector16 = new Vector();
                        updatedTableVector16.addElement(independentFieldsVector16);
                        updatedTableVector16.addElement(englishFieldsVector16);
                        updatedTableVector16.addElement(italianFieldsVector16);
                        updatedTableVector16.addElement(greekFieldsVector16);
                        
                        NodeVector oneChildNodeVector16 = (NodeVector)mainDBHashtable.get(key26);
                        oneChildNodeVector16.setElementAt(updatedTableVector16, 0);
                    }
                    
                }
            }
            
            
            NodeVector oneChildVector = (NodeVector)basicNodeVectors.get(key1);
            Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);
            if (rowPosition < 0)
                rowPosition = 0;
            if (rowPosition > oneChildTableVector.size())
                rowPosition = oneChildTableVector.size();
            if (!oneChildTableVector.contains(new FieldData(rowPosition+1)))
                oneChildTableVector.insertElementAt(new FieldData(prop,range,true,""), rowPosition);
            
            NodeVector oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
            oneChildNodeVector.setElementAt(oneChildTableVector, 0);
        }
        
        // 2. Get Entities (while default filler-type is String, a new row is added
        //    at each child-entity language-independent fields-table.
        Hashtable entityNodeVectors = getChildrenEntities(dmtn);
        Enumeration enum2 = entityNodeVectors.keys();
        while (enum2.hasMoreElements()) {
            String key = (String)enum2.nextElement();
            System.out.println("last::::::: "+DataBasePanel.last.toString()+"  key:::   "+key);
            NodeVector oneChildVector = (NodeVector)entityNodeVectors.get(key);
            Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);  // a vector of
            // 4 vectors
            Vector independentFieldsVector = (Vector)oneChildTableVector.elementAt(0);
            Vector englishFieldsVector = (Vector)oneChildTableVector.elementAt(1);
            Vector italianFieldsVector = (Vector)oneChildTableVector.elementAt(2);
            Vector greekFieldsVector = (Vector)oneChildTableVector.elementAt(3);
            if (range.equalsIgnoreCase("String")){
                if (!greekFieldsVector.contains(new FieldData(prop, ""))){
                    // default: a new row added at every lang-dependent fields-table.
                    greekFieldsVector.insertElementAt(new FieldData(prop, ""), greekFieldsVector.size());
                    italianFieldsVector.insertElementAt(new FieldData(prop, ""), italianFieldsVector.size());
                    englishFieldsVector.insertElementAt(new FieldData(prop, ""), englishFieldsVector.size());
                }} else independentFieldsVector.insertElementAt(new FieldData(prop, ""), independentFieldsVector.size());
            Vector updatedTableVector = new Vector();
            updatedTableVector.addElement(independentFieldsVector);
            updatedTableVector.addElement(englishFieldsVector);
            updatedTableVector.addElement(italianFieldsVector);
            updatedTableVector.addElement(greekFieldsVector);
            
            NodeVector oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
            oneChildNodeVector.setElementAt(updatedTableVector, 0);
        }
        String lastSelected=dmtn.toString();
        if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur")) {
            lastSelected=lastSelected.substring(0, lastSelected.length()-7);
        }
        Hashtable allEntityTypes = (Hashtable) getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Enumeration allTypesNames=allEntityTypes.keys();
        while(allTypesNames.hasMoreElements()) {
            DefaultMutableTreeNode nextNode=null;
            Object nextEl=allTypesNames.nextElement();
            
            
            
            if ((nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected)))&&(!(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString())))) {
                //System.out.println("sddssdsdfsdfsdfadasad"+nextEl.toString());
                Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
                
                while(topchildren.hasMoreElements()) {
                    nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
                    //System.out.println("2121212221212"+nextNode.toString());
                    if (nextNode.toString().equalsIgnoreCase(nextEl.toString()))
                        break;
                }
                
                //System.out.println("sddssdsdfsdfsdfadasad"+nextNode.toString());
                NodeVector nd=(NodeVector) mainDBHashtable.get(nextEl);
                //    NodeVector nd= (NodeVector) nextEl;
                Vector oneChild = (Vector)nd.elementAt(0);
                if (!oneChild.contains(new FieldData(rowPosition+1)))
                    oneChild.insertElementAt(new FieldData(prop,range,true,""), rowPosition);
                
                NodeVector oneChildNode = (NodeVector)mainDBHashtable.get(nextEl.toString());
                //System.out.println(oneChildNode.toString()+"sdfsdfsdfs");
                oneChildNode.setElementAt(oneChild, 0);
                
                
                Hashtable basicNodeVectors1 = getChildrenBasics(nextNode);
                Enumeration enum3 = basicNodeVectors1.keys();
                while (enum3.hasMoreElements()) {
                    String key1 = (String)enum3.nextElement();
                    //System.out.println("asasasasasas"+key1);
                    NodeVector oneChildVector1 = (NodeVector)basicNodeVectors1.get(key1);
                    Vector oneChildTableVector1 = (Vector)oneChildVector1.elementAt(0);
                    if (!oneChildTableVector1.contains(new FieldData(rowPosition+1)))
                        oneChildTableVector1.insertElementAt(new FieldData(prop,range,true,""), rowPosition);
                    
                    NodeVector oneChildNodeVector1 = (NodeVector)mainDBHashtable.get(key1);
                    oneChildNodeVector1.setElementAt(oneChildTableVector1, 0);
                }
                
                // 2. Get Entities (while default filler-type is String, a new row is added
                //    at each child-entity language-independent fields-table.
                Hashtable entityNodeVectors1 = getChildrenEntities(nextNode);
                Enumeration enum4 = entityNodeVectors1.keys();
                while (enum4.hasMoreElements())
                    
                {
                  /*  String occur="";
                    if(nextNode.toString().substring(0,nextNode.toString().length()-1).endsWith("_occur"))
                        occur=nextNode.toString().substring(nextNode.toString().length()-7,nextNode.toString().length());
                        String key2 = (String)enum4.nextElement()+occur;*/
                    
                    String key2 = (String)enum4.nextElement();
                    System.out.println("nextNode::::::: "+nextNode.toString()+"  key2:::   "+key2);
                    NodeVector oneChildVector1 = (NodeVector)entityNodeVectors1.get(key2);
                    Vector oneChildTableVector1 = (Vector)oneChildVector1.elementAt(0);  // a vector of
                    // 4 vectors
                    Vector independentFieldsVector1 = (Vector)oneChildTableVector1.elementAt(0);
                    Vector englishFieldsVector1 = (Vector)oneChildTableVector1.elementAt(1);
                    Vector italianFieldsVector1 = (Vector)oneChildTableVector1.elementAt(2);
                    Vector greekFieldsVector1 = (Vector)oneChildTableVector1.elementAt(3);
                    if (range.equalsIgnoreCase("String")){
                        // default: a new row added at every lang-dependent fields-table.
                        if (!greekFieldsVector1.contains(new FieldData(prop, ""))){
                            greekFieldsVector1.insertElementAt(new FieldData(prop, ""), greekFieldsVector1.size());
                            italianFieldsVector1.insertElementAt(new FieldData(prop, ""), italianFieldsVector1.size());
                            englishFieldsVector1.insertElementAt(new FieldData(prop, ""), englishFieldsVector1.size());
                        }} else independentFieldsVector1.insertElementAt(new FieldData(prop, ""), independentFieldsVector1.size());
                    Vector updatedTableVector1 = new Vector();
                    updatedTableVector1.addElement(independentFieldsVector1);
                    updatedTableVector1.addElement(englishFieldsVector1);
                    updatedTableVector1.addElement(italianFieldsVector1);
                    updatedTableVector1.addElement(greekFieldsVector1);
                    
                    NodeVector oneChildNodeVector1 = (NodeVector)mainDBHashtable.get(key2);
                    oneChildNodeVector1.setElementAt(updatedTableVector1, 0);
                }
                
            }
        }
        
    } // method insertRowInDataBaseTable()
    
    
    public void insertRowInDataBaseTable(int rowPosition) {
        // insert element (1 row)in current table vector
        if (rowPosition < 0)
            rowPosition = 0;
        if (rowPosition > DataBaseTable.m_data.m_vector.size())
            rowPosition = DataBaseTable.m_data.m_vector.size();
        DataBaseTable.m_data.m_vector.insertElementAt(new FieldData(rowPosition+1), rowPosition);
        
        // Insert 1 row in descendants table vectors:
        // 1. Get BasicType and EntityType nodes
        Hashtable basicNodeVectors = getChildrenBasics(DataBasePanel.last);
        Enumeration enum1 = basicNodeVectors.keys();
        while (enum1.hasMoreElements()) {
            String key = (String)enum1.nextElement();
            String key1=key;
            
            
            if(key.substring(0,key.length()-1).endsWith("_occur")) {
                key=key.substring(0, key.length()-7);
            }
            Hashtable allEntityTypes6 = (Hashtable) getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            Enumeration allTypesNames6=allEntityTypes6.keys();
            while(allTypesNames6.hasMoreElements()) {
                DefaultMutableTreeNode nextNode6=null;
                Object nextEl6=allTypesNames6.nextElement();
                
                
                
                if (((nextEl6.toString().startsWith(key+"_occur")||nextEl6.toString().equalsIgnoreCase(key)))&&(!(nextEl6.toString().equalsIgnoreCase(key1)))) {
                    System.out.println("sddssdsdfsdfsdfadasad"+nextEl6.toString());
                    Enumeration topchildren6=DataBasePanel.top.preorderEnumeration();
                    
                    while(topchildren6.hasMoreElements()) {
                        nextNode6=(DefaultMutableTreeNode) topchildren6.nextElement();
                        //System.out.println("2121212221212"+nextNode.toString());
                        if (nextNode6.toString().equalsIgnoreCase(nextEl6.toString()))
                            break;
                    }
                    
                    //System.out.println("sddssdsdfsdfsdfadasad"+nextNode.toString());
                    NodeVector nd6=(NodeVector) mainDBHashtable.get(nextEl6);
                    //    NodeVector nd= (NodeVector) nextEl;
                    Vector oneChild6 = (Vector)nd6.elementAt(0);
                    if (!oneChild6.contains(new FieldData(rowPosition+1)))
                        oneChild6.insertElementAt(new FieldData(rowPosition+1), rowPosition);
                    
                    NodeVector oneChildNode6 = (NodeVector)mainDBHashtable.get(nextEl6.toString());
                    //System.out.println(oneChildNode.toString()+"sdfsdfsdfs");
                    oneChildNode6.setElementAt(oneChild6, 0);
                    
                    
                    Hashtable basicNodeVectors16 = getChildrenBasics(nextNode6);
                    Enumeration enum36 = basicNodeVectors16.keys();
                    while (enum36.hasMoreElements()) {
                        String key6 = (String)enum36.nextElement();
                        //System.out.println("asasasasasas"+key1);
                        NodeVector oneChildVector16 = (NodeVector)basicNodeVectors16.get(key6);
                        Vector oneChildTableVector16 = (Vector)oneChildVector16.elementAt(0);
                        if (!oneChildTableVector16.contains(new FieldData(rowPosition+1)))
                            oneChildTableVector16.insertElementAt(new FieldData(rowPosition+1), rowPosition);
                        
                        NodeVector oneChildNodeVector16 = (NodeVector)mainDBHashtable.get(key6);
                        oneChildNodeVector16.setElementAt(oneChildTableVector16, 0);
                    }
                    
                    // 2. Get Entities (while default filler-type is String, a new row is added
                    //    at each child-entity language-independent fields-table.
                    Hashtable entityNodeVectors16 = getChildrenEntities(nextNode6);
                    Enumeration enum46 = entityNodeVectors16.keys();
                    while (enum46.hasMoreElements())
                        
                    {
                  /*  String occur="";
                    if(nextNode.toString().substring(0,nextNode.toString().length()-1).endsWith("_occur"))
                        occur=nextNode.toString().substring(nextNode.toString().length()-7,nextNode.toString().length());
                        String key2 = (String)enum4.nextElement()+occur;*/
                        
                        String key26 = (String)enum46.nextElement();
                        System.out.println("nextNode::::::: "+nextNode6.toString()+"  key2:::   "+key26);
                        NodeVector oneChildVector16 = (NodeVector)entityNodeVectors16.get(key26);
                        Vector oneChildTableVector16 = (Vector)oneChildVector16.elementAt(0);  // a vector of
                        // 4 vectors
                        Vector independentFieldsVector16 = (Vector)oneChildTableVector16.elementAt(0);
                        Vector englishFieldsVector16 = (Vector)oneChildTableVector16.elementAt(1);
                        Vector italianFieldsVector16 = (Vector)oneChildTableVector16.elementAt(2);
                        Vector greekFieldsVector16 = (Vector)oneChildTableVector16.elementAt(3);
                        
                        // default: a new row added at every lang-dependent fields-table.if (!oneChild6.contains(new FieldData(rowPosition+1)))
                        if (!greekFieldsVector16.contains(new FieldData("New-user-field", ""))){
                            greekFieldsVector16.insertElementAt(new FieldData("New-user-field", ""), greekFieldsVector16.size());
                            italianFieldsVector16.insertElementAt(new FieldData("New-user-field", ""), italianFieldsVector16.size());
                            englishFieldsVector16.insertElementAt(new FieldData("New-user-field", ""), englishFieldsVector16.size());
                        }
                        Vector updatedTableVector16 = new Vector();
                        updatedTableVector16.addElement(independentFieldsVector16);
                        updatedTableVector16.addElement(englishFieldsVector16);
                        updatedTableVector16.addElement(italianFieldsVector16);
                        updatedTableVector16.addElement(greekFieldsVector16);
                        
                        NodeVector oneChildNodeVector16 = (NodeVector)mainDBHashtable.get(key26);
                        oneChildNodeVector16.setElementAt(updatedTableVector16, 0);
                    }
                    
                }
            }
            
            
            NodeVector oneChildVector = (NodeVector)basicNodeVectors.get(key1);
            Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);
            if (rowPosition < 0)
                rowPosition = 0;
            if (rowPosition > oneChildTableVector.size())
                rowPosition = oneChildTableVector.size();
            if (!oneChildTableVector.contains(new FieldData("New-user-field","String",false,"")))
                oneChildTableVector.insertElementAt(new FieldData("New-user-field","String",false,""), rowPosition);
            
            NodeVector oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
            Vector firstrow=(Vector) oneChildNodeVector.elementAt(0);
            firstrow=(Vector) firstrow.elementAt(0);
            Vector clone=(Vector) oneChildTableVector.clone();
            clone.setElementAt(firstrow,0);
            oneChildNodeVector.setElementAt(clone, 0);
        }
        
        // 2. Get Entities (while default filler-type is String, a new row is added
        //    at each child-entity language-independent fields-table.
        Hashtable entityNodeVectors = getChildrenEntities(DataBasePanel.last);
        Enumeration enum2 = entityNodeVectors.keys();
        while (enum2.hasMoreElements()) {
            String key = (String)enum2.nextElement();
            System.out.println("last::::::: "+DataBasePanel.last.toString()+"  key:::   "+key);
            NodeVector oneChildVector = (NodeVector)entityNodeVectors.get(key);
            Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);  // a vector of
            // 4 vectors
            Vector independentFieldsVector = (Vector)oneChildTableVector.elementAt(0);
            Vector englishFieldsVector = (Vector)oneChildTableVector.elementAt(1);
            Vector italianFieldsVector = (Vector)oneChildTableVector.elementAt(2);
            Vector greekFieldsVector = (Vector)oneChildTableVector.elementAt(3);
            if (!greekFieldsVector.contains(new FieldData("New-user-field", ""))){
                // default: a new row added at every lang-dependent fields-table.
                greekFieldsVector.insertElementAt(new FieldData("New-user-field", ""), greekFieldsVector.size());
                italianFieldsVector.insertElementAt(new FieldData("New-user-field", ""), italianFieldsVector.size());
                englishFieldsVector.insertElementAt(new FieldData("New-user-field", ""), englishFieldsVector.size());
            }
            Vector updatedTableVector = new Vector();
            updatedTableVector.addElement(independentFieldsVector);
            updatedTableVector.addElement(englishFieldsVector);
            updatedTableVector.addElement(italianFieldsVector);
            updatedTableVector.addElement(greekFieldsVector);
            
            NodeVector oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
            oneChildNodeVector.setElementAt(updatedTableVector, 0);
        }
        String lastSelected=DataBasePanel.last.toString();
        if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur")) {
            lastSelected=lastSelected.substring(0, lastSelected.length()-7);
        }
        Hashtable allEntityTypes = (Hashtable) getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Enumeration allTypesNames=allEntityTypes.keys();
        while(allTypesNames.hasMoreElements()) {
            DefaultMutableTreeNode nextNode=null;
            Object nextEl=allTypesNames.nextElement();
            
            
            
            if ((nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected)))&&(!(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString())))) {
                //System.out.println("sddssdsdfsdfsdfadasad"+nextEl.toString());
                Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
                
                while(topchildren.hasMoreElements()) {
                    nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
                    //System.out.println("2121212221212"+nextNode.toString());
                    if (nextNode.toString().equalsIgnoreCase(nextEl.toString()))
                        break;
                }
                
                //System.out.println("sddssdsdfsdfsdfadasad"+nextNode.toString());
                NodeVector nd=(NodeVector) mainDBHashtable.get(nextEl);
                //    NodeVector nd= (NodeVector) nextEl;
                Vector oneChild = (Vector)nd.elementAt(0);
                if (!oneChild.contains(new FieldData(rowPosition+1)))
                    oneChild.insertElementAt(new FieldData(rowPosition+1), rowPosition);
                
                NodeVector oneChildNode = (NodeVector)mainDBHashtable.get(nextEl.toString());
                //System.out.println(oneChildNode.toString()+"sdfsdfsdfs");
                oneChildNode.setElementAt(oneChild, 0);
                
                
                Hashtable basicNodeVectors1 = getChildrenBasics(nextNode);
                Enumeration enum3 = basicNodeVectors1.keys();
                while (enum3.hasMoreElements()) {
                    String key1 = (String)enum3.nextElement();
                    //System.out.println("asasasasasas"+key1);
                    NodeVector oneChildVector1 = (NodeVector)basicNodeVectors1.get(key1);
                    Vector oneChildTableVector1 = (Vector)oneChildVector1.elementAt(0);
                    if (!oneChildTableVector1.contains(new FieldData(rowPosition+1)))
                        oneChildTableVector1.insertElementAt(new FieldData(rowPosition+1), rowPosition);
                    
                    NodeVector oneChildNodeVector1 = (NodeVector)mainDBHashtable.get(key1);
                    oneChildNodeVector1.setElementAt(oneChildTableVector1, 0);
                }
                
                // 2. Get Entities (while default filler-type is String, a new row is added
                //    at each child-entity language-independent fields-table.
                Hashtable entityNodeVectors1 = getChildrenEntities(nextNode);
                Enumeration enum4 = entityNodeVectors1.keys();
                while (enum4.hasMoreElements())
                    
                {
                  /*  String occur="";
                    if(nextNode.toString().substring(0,nextNode.toString().length()-1).endsWith("_occur"))
                        occur=nextNode.toString().substring(nextNode.toString().length()-7,nextNode.toString().length());
                        String key2 = (String)enum4.nextElement()+occur;*/
                    
                    String key2 = (String)enum4.nextElement();
                    System.out.println("nextNode::::::: "+nextNode.toString()+"  key2:::   "+key2);
                    NodeVector oneChildVector1 = (NodeVector)entityNodeVectors1.get(key2);
                    Vector oneChildTableVector1 = (Vector)oneChildVector1.elementAt(0);  // a vector of
                    // 4 vectors
                    Vector independentFieldsVector1 = (Vector)oneChildTableVector1.elementAt(0);
                    Vector englishFieldsVector1 = (Vector)oneChildTableVector1.elementAt(1);
                    Vector italianFieldsVector1 = (Vector)oneChildTableVector1.elementAt(2);
                    Vector greekFieldsVector1 = (Vector)oneChildTableVector1.elementAt(3);
                    
                    // default: a new row added at every lang-dependent fields-table.
                    if (!greekFieldsVector1.contains(new FieldData("New-user-field", ""))){
                        greekFieldsVector1.insertElementAt(new FieldData("New-user-field", ""), greekFieldsVector1.size());
                        italianFieldsVector1.insertElementAt(new FieldData("New-user-field", ""), italianFieldsVector1.size());
                        englishFieldsVector1.insertElementAt(new FieldData("New-user-field", ""), englishFieldsVector1.size());
                    }
                    Vector updatedTableVector1 = new Vector();
                    updatedTableVector1.addElement(independentFieldsVector1);
                    updatedTableVector1.addElement(englishFieldsVector1);
                    updatedTableVector1.addElement(italianFieldsVector1);
                    updatedTableVector1.addElement(greekFieldsVector1);
                    
                    NodeVector oneChildNodeVector1 = (NodeVector)mainDBHashtable.get(key2);
                    oneChildNodeVector1.setElementAt(updatedTableVector1, 0);
                }
                
            }
        }
        
    } // method insertRowInDataBaseTable()
    
    
    
    
    public void insertExistingRowInDataBaseTable(FieldData fd, int rowPosition) {
        // insert element (1 row)in current table vector
        if (rowPosition < 0)
            rowPosition = 0;
        if (rowPosition > DataBaseTable.m_data.m_vector.size())
            rowPosition = DataBaseTable.m_data.m_vector.size();
        
        
        // Insert 1 row in descendants table vectors:
        // 1. Get BasicType and EntityType nodes
        Hashtable basicNodeVectors = getChildrenBasics(DataBasePanel.last);
        Enumeration enum1 = basicNodeVectors.keys();
        while (enum1.hasMoreElements()) {
            String key = (String)enum1.nextElement();
            String key1=key;
            
            
            if(key.substring(0,key.length()-1).endsWith("_occur")) {
                key=key.substring(0, key.length()-7);
            }
            Hashtable allEntityTypes6 = (Hashtable) getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
            Enumeration allTypesNames6=allEntityTypes6.keys();
            while(allTypesNames6.hasMoreElements()) {
                DefaultMutableTreeNode nextNode6=null;
                Object nextEl6=allTypesNames6.nextElement();
                
                
                
                if (((nextEl6.toString().startsWith(key+"_occur")||nextEl6.toString().equalsIgnoreCase(key)))&&(!(nextEl6.toString().equalsIgnoreCase(key1)))) {
                    System.out.println("sddssdsdfsdfsdfadasad"+nextEl6.toString());
                    Enumeration topchildren6=DataBasePanel.top.preorderEnumeration();
                    
                    while(topchildren6.hasMoreElements()) {
                        nextNode6=(DefaultMutableTreeNode) topchildren6.nextElement();
                        //System.out.println("2121212221212"+nextNode.toString());
                        if (nextNode6.toString().equalsIgnoreCase(nextEl6.toString()))
                            break;
                    }
                    
                    //System.out.println("sddssdsdfsdfsdfadasad"+nextNode.toString());
                    NodeVector nd6=(NodeVector) mainDBHashtable.get(nextEl6);
                    //    NodeVector nd= (NodeVector) nextEl;
                    Vector oneChild6 = (Vector)nd6.elementAt(0);
                    boolean contains=false;
                    for(int i=0;i<oneChild6.size();i++){
                        Vector next=(Vector) oneChild6.elementAt(i);
                        if (next.elementAt(0).toString().equalsIgnoreCase(fd.m_field)){
                            oneChild6.remove(i);
                            oneChild6.insertElementAt(next,rowPosition);
                            contains=true;
                            break;
                        }
                    }
                    if (contains) continue;
                    oneChild6.insertElementAt(fd, rowPosition);
                    valueRestrictionsHashtable.put(nextEl6.toString()+":"+fd.m_field,new ValueRestriction());
                    
                    NodeVector oneChildNode6 = (NodeVector)mainDBHashtable.get(nextEl6.toString());
                    //System.out.println(oneChildNode.toString()+"sdfsdfsdfs");
                    oneChildNode6.setElementAt(oneChild6, 0);
                    
                    
                    Hashtable basicNodeVectors16 = getChildrenBasics(nextNode6);
                    Enumeration enum36 = basicNodeVectors16.keys();
                    while (enum36.hasMoreElements()) {
                        String key6 = (String)enum36.nextElement();
                        //System.out.println("asasasasasas"+key1);
                        NodeVector oneChildVector16 = (NodeVector)basicNodeVectors16.get(key6);
                        Vector oneChildTableVector16 = (Vector)oneChildVector16.elementAt(0);
                        contains=false;
                        for(int i=0;i<oneChildTableVector16.size();i++){
                            Vector next=(Vector) oneChildTableVector16.elementAt(i);
                            if (next.elementAt(0).toString().equalsIgnoreCase(fd.m_field)){
                                oneChildTableVector16.remove(i);
                                oneChildTableVector16.insertElementAt(next,rowPosition);
                                contains=true;
                                break;
                            }
                        }
                        if (!contains)
                            oneChildTableVector16.insertElementAt(fd, rowPosition);
                        valueRestrictionsHashtable.put(key6.toString()+":"+fd.m_field,new ValueRestriction());
                        NodeVector oneChildNodeVector16 = (NodeVector)mainDBHashtable.get(key6);
                        oneChildNodeVector16.setElementAt(oneChildTableVector16, 0);
                    }
                    
                    // 2. Get Entities (while default filler-type is String, a new row is added
                    //    at each child-entity language-independent fields-table.
                    Hashtable entityNodeVectors16 = getChildrenEntities(nextNode6);
                    Enumeration enum46 = entityNodeVectors16.keys();
                    while (enum46.hasMoreElements())
                        
                    {
                  /*  String occur="";
                    if(nextNode.toString().substring(0,nextNode.toString().length()-1).endsWith("_occur"))
                        occur=nextNode.toString().substring(nextNode.toString().length()-7,nextNode.toString().length());
                        String key2 = (String)enum4.nextElement()+occur;*/
                        
                        String key26 = (String)enum46.nextElement();
                        System.out.println("nextNode::::::: "+nextNode6.toString()+"  key2:::   "+key26);
                        NodeVector oneChildVector16 = (NodeVector)entityNodeVectors16.get(key26);
                        Vector oneChildTableVector16 = (Vector)oneChildVector16.elementAt(0);  // a vector of
                        // 4 vectors
                        Vector independentFieldsVector16 = (Vector)oneChildTableVector16.elementAt(0);
                        Vector englishFieldsVector16 = (Vector)oneChildTableVector16.elementAt(1);
                        Vector italianFieldsVector16 = (Vector)oneChildTableVector16.elementAt(2);
                        Vector greekFieldsVector16 = (Vector)oneChildTableVector16.elementAt(3);
                        if(fd.m_filler.equalsIgnoreCase("String")){
                            // default: a new row added at every lang-dependent fields-table.if (!oneChild6.contains(new FieldData(rowPosition+1)))
                            // if (!greekFieldsVector16.contains(fd)){
                            greekFieldsVector16.insertElementAt(new FieldData("String",""), greekFieldsVector16.size());
                            italianFieldsVector16.insertElementAt(new FieldData("String",""), italianFieldsVector16.size());
                            englishFieldsVector16.insertElementAt(new FieldData("String",""), englishFieldsVector16.size());
                            //        }
                        }else{independentFieldsVector16.insertElementAt(new FieldData(fd.m_field,"Select ..."),independentFieldsVector16.size());}
                        Vector updatedTableVector16 = new Vector();
                        updatedTableVector16.addElement(independentFieldsVector16);
                        updatedTableVector16.addElement(englishFieldsVector16);
                        updatedTableVector16.addElement(italianFieldsVector16);
                        updatedTableVector16.addElement(greekFieldsVector16);
                        
                        NodeVector oneChildNodeVector16 = (NodeVector)mainDBHashtable.get(key26);
                        oneChildNodeVector16.setElementAt(updatedTableVector16, 0);
                    }
                    
                }
            }
            
            
            NodeVector oneChildVector = (NodeVector)basicNodeVectors.get(key1);
            Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);
            if (rowPosition < 0)
                rowPosition = 0;
            if (rowPosition > oneChildTableVector.size())
                rowPosition = oneChildTableVector.size();
            //   boolean t=(!oneChildTableVector.contains((Object) fd));
            boolean contains=false;
            for(int i=0;i<oneChildTableVector.size();i++){
                Vector next=(Vector) oneChildTableVector.elementAt(i);
                if (next.elementAt(0).toString().equalsIgnoreCase(fd.m_field)){
                    oneChildTableVector.remove(i);
                    oneChildTableVector.insertElementAt(next,rowPosition);
                    contains=true;
                    break;
                }
            }
            if (!contains){//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
                oneChildTableVector.insertElementAt(fd, rowPosition);
                valueRestrictionsHashtable.put(key1+":"+fd.m_field,new ValueRestriction());
                NodeVector oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
                Vector firstrow=(Vector) oneChildNodeVector.elementAt(0);
                firstrow=(Vector) firstrow.elementAt(0);
                Vector clone=(Vector) oneChildTableVector.clone();
                clone.setElementAt(firstrow,0);
                oneChildNodeVector.setElementAt(clone, 0);
                
            } else{
                Vector temp=(Vector) propertiesHashtable.get(fd.m_field);
                temp=(Vector) temp.elementAt(0);
                temp.remove(key1);
            }
        }
        
        boolean contains=false;
        for(int i=0;i<DataBaseTable.m_data.m_vector.size();i++){
            Vector next=(Vector) DataBaseTable.m_data.m_vector.elementAt(i);
            if (next.elementAt(0).toString().equalsIgnoreCase(fd.m_field)){
                DataBaseTable.m_data.m_vector.remove(i);
                DataBaseTable.m_data.m_vector.insertElementAt(next,rowPosition);
                contains=true;
                break;
            }
        }
        if(!contains){
            Vector temp=(Vector) propertiesHashtable.get(fd.m_field);
            temp=(Vector) temp.elementAt(0);
            temp.add(DataBasePanel.last.toString());
            DataBaseTable.m_data.m_vector.insertElementAt(fd, rowPosition);
            valueRestrictionsHashtable.put(DataBasePanel.last.toString()+":"+fd.m_field,new ValueRestriction());
            // 2. Get Entities (while default filler-type is String, a new row is added
            //    at each child-entity language-independent fields-table.
            Hashtable entityNodeVectors = getChildrenEntities(DataBasePanel.last);
            Enumeration enum2 = entityNodeVectors.keys();
            while (enum2.hasMoreElements()) {
                String key = (String)enum2.nextElement();
                System.out.println("last::::::: "+DataBasePanel.last.toString()+"  key:::   "+key);
                NodeVector oneChildVector = (NodeVector)entityNodeVectors.get(key);
                Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);  // a vector of
                // 4 vectors
                Vector independentFieldsVector = (Vector)oneChildTableVector.elementAt(0);
                Vector englishFieldsVector = (Vector)oneChildTableVector.elementAt(1);
                Vector italianFieldsVector = (Vector)oneChildTableVector.elementAt(2);
                Vector greekFieldsVector = (Vector)oneChildTableVector.elementAt(3);
                contains=false;
                for(int r=0;r<greekFieldsVector.size();r++){
                    FieldData field=(FieldData) greekFieldsVector.elementAt(r);
                    if(field.m_field.equalsIgnoreCase(fd.m_field)){
                        contains=true;
                        break;
                    }
                }
                if(fd.m_filler.equalsIgnoreCase("String")){
                    if (!contains){
//if (!greekFieldsVector.contains(fd)){
                        // default: a new row added at every lang-dependent fields-table.
                        greekFieldsVector.insertElementAt(new FieldData(fd.m_field,""), greekFieldsVector.size());
                        italianFieldsVector.insertElementAt(new FieldData(fd.m_field,""), italianFieldsVector.size());
                        englishFieldsVector.insertElementAt(new FieldData(fd.m_field,""), englishFieldsVector.size());
                    }}//}
                else {
                    contains=false;
                    for(int r=0;r<independentFieldsVector.size();r++){
                        FieldData field=(FieldData) independentFieldsVector.elementAt(r);
                        if(field.m_field.equalsIgnoreCase(fd.m_field)){
                            contains=true;
                            break;
                        }
                    }
                    if (!contains){
                        independentFieldsVector.insertElementAt(new FieldData(fd.m_field,"Select ..."), independentFieldsVector.size());
                    }}
                Vector updatedTableVector = new Vector();
                updatedTableVector.addElement(independentFieldsVector);
                updatedTableVector.addElement(englishFieldsVector);
                updatedTableVector.addElement(italianFieldsVector);
                updatedTableVector.addElement(greekFieldsVector);
                
                NodeVector oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
                oneChildNodeVector.setElementAt(updatedTableVector, 0);
            }}
        String lastSelected=DataBasePanel.last.toString();
        if(lastSelected.substring(0,lastSelected.length()-1).endsWith("_occur")) {
            lastSelected=lastSelected.substring(0, lastSelected.length()-7);
        }
        Hashtable allEntityTypes = (Hashtable) getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Enumeration allTypesNames=allEntityTypes.keys();
        while(allTypesNames.hasMoreElements()) {
            DefaultMutableTreeNode nextNode=null;
            Object nextEl=allTypesNames.nextElement();
            
            
            
            if ((nextEl.toString().startsWith(lastSelected+"_occur")||(nextEl.toString().equalsIgnoreCase(lastSelected)))&&(!(nextEl.toString().equalsIgnoreCase(DataBasePanel.last.toString())))) {
                //System.out.println("sddssdsdfsdfsdfadasad"+nextEl.toString());
                Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
                
                while(topchildren.hasMoreElements()) {
                    nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
                    //System.out.println("2121212221212"+nextNode.toString());
                    if (nextNode.toString().equalsIgnoreCase(nextEl.toString()))
                        break;
                }
                
                //System.out.println("sddssdsdfsdfsdfadasad"+nextNode.toString());
                NodeVector nd=(NodeVector) mainDBHashtable.get(nextEl);
                //    NodeVector nd= (NodeVector) nextEl;
                Vector oneChild = (Vector)nd.elementAt(0);
                contains=false;
                for(int i=0;i<oneChild.size();i++){
                    Vector next=(Vector) oneChild.elementAt(i);
                    if (next.elementAt(0).toString().equalsIgnoreCase(fd.m_field)){
                        oneChild.remove(i);
                        oneChild.insertElementAt(next,rowPosition);
                        contains=true;
                        break;
                    }
                }
                if (!contains){
                    oneChild.insertElementAt(fd, rowPosition);
                    valueRestrictionsHashtable.put(nextEl.toString()+":"+fd.m_field,new ValueRestriction());
                    NodeVector oneChildNode = (NodeVector)mainDBHashtable.get(nextEl.toString());
                    //System.out.println(oneChildNode.toString()+"sdfsdfsdfs");
                    oneChildNode.setElementAt(oneChild, 0);
                }
                
                Hashtable basicNodeVectors1 = getChildrenBasics(nextNode);
                Enumeration enum3 = basicNodeVectors1.keys();
                while (enum3.hasMoreElements()) {
                    String key1 = (String)enum3.nextElement();
                    //System.out.println("asasasasasas"+key1);
                    NodeVector oneChildVector1 = (NodeVector)basicNodeVectors1.get(key1);
                    Vector oneChildTableVector1 = (Vector)oneChildVector1.elementAt(0);
                    contains=false;
                    for(int i=0;i<oneChildTableVector1.size();i++){
                        Vector next=(Vector) oneChildTableVector1.elementAt(i);
                        if (next.elementAt(0).toString().equalsIgnoreCase(fd.m_field)){
                            oneChildTableVector1.remove(i);
                            oneChildTableVector1.insertElementAt(next,rowPosition);
                            contains=true;
                            break;
                        }
                    }
                    if (!contains){
                        oneChildTableVector1.insertElementAt(fd, rowPosition);
                        valueRestrictionsHashtable.put(key1+":"+fd.m_field,new ValueRestriction());
                        NodeVector oneChildNodeVector1 = (NodeVector)mainDBHashtable.get(key1);
                        oneChildNodeVector1.setElementAt(oneChildTableVector1, 0);
                    }}
                
                // 2. Get Entities (while default filler-type is String, a new row is added
                //    at each child-entity language-independent fields-table.
                Hashtable entityNodeVectors1 = getChildrenEntities(nextNode);
                Enumeration enum4 = entityNodeVectors1.keys();
                while (enum4.hasMoreElements())
                    
                {
                  /*  String occur="";
                    if(nextNode.toString().substring(0,nextNode.toString().length()-1).endsWith("_occur"))
                        occur=nextNode.toString().substring(nextNode.toString().length()-7,nextNode.toString().length());
                        String key2 = (String)enum4.nextElement()+occur;*/
                    
                    String key2 = (String)enum4.nextElement();
                    System.out.println("nextNode::::::: "+nextNode.toString()+"  key2:::   "+key2);
                    NodeVector oneChildVector1 = (NodeVector)entityNodeVectors1.get(key2);
                    Vector oneChildTableVector1 = (Vector)oneChildVector1.elementAt(0);  // a vector of
                    // 4 vectors
                    Vector independentFieldsVector1 = (Vector)oneChildTableVector1.elementAt(0);
                    Vector englishFieldsVector1 = (Vector)oneChildTableVector1.elementAt(1);
                    Vector italianFieldsVector1 = (Vector)oneChildTableVector1.elementAt(2);
                    Vector greekFieldsVector1 = (Vector)oneChildTableVector1.elementAt(3);
                    
                    // default: a new row added at every lang-dependent fields-table.
                    if(fd.m_filler.equalsIgnoreCase("String")){
                        if (!greekFieldsVector1.contains((Object) fd)){
                            greekFieldsVector1.insertElementAt(fd, greekFieldsVector1.size());
                            italianFieldsVector1.insertElementAt(fd, italianFieldsVector1.size());
                            englishFieldsVector1.insertElementAt(fd, englishFieldsVector1.size());
                        }}else {
                        independentFieldsVector1.insertElementAt(fd, independentFieldsVector1.size());
                        }
                    Vector updatedTableVector1 = new Vector();
                    updatedTableVector1.addElement(independentFieldsVector1);
                    updatedTableVector1.addElement(englishFieldsVector1);
                    updatedTableVector1.addElement(italianFieldsVector1);
                    updatedTableVector1.addElement(greekFieldsVector1);
                    
                    NodeVector oneChildNodeVector1 = (NodeVector)mainDBHashtable.get(key2);
                    oneChildNodeVector1.setElementAt(updatedTableVector1, 0);
                }
                
            }
        }
        
    }
    
    
    
    
        /* ----------- UPDATE 4b ------------------------------------------------
         * This method is called when inserting a row in a StoriesTable.
         * ---------------------------------------------------------------------
         */
    public void insertRowInStoriesTable(int rowPosition) {
        // insert element (1 row)in current table vector
        if (rowPosition < 0)
            rowPosition = 0;
        if (rowPosition > StoriesTable.m_data.m_vector.size())
            rowPosition = StoriesTable.m_data.m_vector.size();
        StoriesTable.m_data.m_vector.insertElementAt(new FieldData("New-story", "ICON_X"), rowPosition);
        createStory(StoriesPanel.last.toString(), "New-story");
    } // method insertRowInDataBaseTable()
    
    
        /* ----------- UPDATE 5a ------------------------------------------------
         * This method is called when removing a row from a DataBaseTable.
         * ---------------------------------------------------------------------
         */
    public boolean removeRowFromDataBaseTable(String fieldName, String fillerType, int rowPosition) {
        if (rowPosition < 0 || rowPosition >= DataBaseTable.m_data.m_vector.size()) {
            return false;
        }
        
        // A short parenthese to update current-node's templateVector
        NodeVector curVec = (NodeVector)mainDBHashtable.get(DataBasePanel.last.toString());
        TemplateVector temVec = (TemplateVector)curVec.elementAt(4);
        Hashtable enValues = (Hashtable)temVec.elementAt(0);
        Hashtable itValues = (Hashtable)temVec.elementAt(1);
        Hashtable grValues = (Hashtable)temVec.elementAt(2);
        if (enValues.containsKey(fieldName)) {
            enValues.remove(fieldName);
        }
        if (itValues.containsKey(fieldName)) {
            itValues.remove(fieldName);
        }
        if (grValues.containsKey(fieldName)) {
            grValues.remove(fieldName);
        }
        temVec.setElementAt(enValues, 0);
        temVec.setElementAt(itValues, 1);
        temVec.setElementAt(grValues, 2);	// end updating current templateVector
        
        // remove element (1 row) from current table's vector
        DataBaseTable.m_data.m_vector.remove(rowPosition);
        
        // Delete appropriate row from descendants table vectors:
        // 1. Get BasicType and EntityType nodes
        Hashtable basicNodeVectors = getChildrenBasics(DataBasePanel.last);
        Enumeration enum1 = basicNodeVectors.keys();
        while (enum1.hasMoreElements()) {
            NodeVector oneChildVector=new NodeVector();
            NodeVector  oneChildNodeVector=new NodeVector();
            String key = (String)enum1.nextElement();
            System.out.println("BBBBB"+key);
            if (key.substring(0, key.length()-1).endsWith("_occur"))
                key=key.substring(0, key.length()-7);
            oneChildVector = (NodeVector)mainDBHashtable.get(key);
            Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);
            for(int y=oneChildTableVector.size();y>8;y--){
                Vector property=(Vector) oneChildTableVector.elementAt(y-1);
                if (property.elementAt(0).toString().equalsIgnoreCase(fieldName)||property==null){
                    oneChildTableVector.removeElementAt(y-1);
                    oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
                    oneChildNodeVector.setElementAt(oneChildTableVector, 0);
                    System.out.println("BBBBB2"+key);}}
            // System.out.println(basicNodeVectors.get(key+String.valueOf().toString()));
            for(int u=2;mainDBHashtable.get(key+"_occur"+String.valueOf(u))!=null;u++){
                oneChildVector   = (NodeVector)mainDBHashtable.get(key+"_occur"+String.valueOf(u));
                oneChildTableVector = (Vector)oneChildVector.elementAt(0);
                System.out.println("BBBBB1"+key+String.valueOf(u));
                for(int y=oneChildTableVector.size();y>8;y--){
                    Vector property=(Vector) oneChildTableVector.elementAt(y-1);
                    if (property.elementAt(0).toString().equalsIgnoreCase(fieldName)||property==null){
                        
                        oneChildTableVector.removeElementAt(y-1);
                        oneChildNodeVector = (NodeVector)mainDBHashtable.get(key+"_occur"+String.valueOf(u));
                        oneChildNodeVector.setElementAt(oneChildTableVector, 0);}}}
        }
        
        // 2. Get Entities
         Hashtable entitiesHash=getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
        Enumeration entities=entitiesHash.keys();
        
        while(entities.hasMoreElements()){
            String nextEntity=entities.nextElement().toString();
  
            NodeVector ent=(NodeVector)mainDBHashtable.get(nextEntity);
 
                ent.removeFieldData(fieldName,1);
                ent.removeFieldData(fieldName,2);
                ent.removeFieldData(fieldName,3);
                ent.removeFieldData(fieldName,4);
            }
      
        
        
//        Hashtable entityNodeVectors = getChildrenEntities(DataBasePanel.last);
//        Enumeration enum2 = entityNodeVectors.keys();
//        while (enum2.hasMoreElements()) {
//            String key = (String)enum2.nextElement();
//            NodeVector childVector = (NodeVector)mainDBHashtable.get(key);
//            Vector childTable = (Vector)childVector.elementAt(0);  // a vector of
//            // 4 vectors
//            Vector independentFieldsVector = (Vector)childTable.elementAt(0);
//            Vector englishFieldsVector = (Vector)childTable.elementAt(1);
//            Vector italianFieldsVector = (Vector)childTable.elementAt(2);
//            Vector greekFieldsVector = (Vector)childTable.elementAt(3);
//            
//            // check if tables already contain fieldName
//            int indCheck = tableVectorContainFieldAt(childTable, fieldName, 0);
//            int engCheck = tableVectorContainFieldAt(childTable, fieldName, 1);
//            int itaCheck = tableVectorContainFieldAt(childTable, fieldName, 2);
//            int greCheck = tableVectorContainFieldAt(childTable, fieldName, 3);
//            
//            if (fillerType.equalsIgnoreCase("string")) {
//                // 1. removing from english fields
//                if (engCheck >= 0) {
//                    englishFieldsVector.removeElementAt(engCheck);
//                }
//                // 2. removing from italian fields
//                if (itaCheck >= 0) {
//                    italianFieldsVector.removeElementAt(itaCheck);
//                }
//                // 3. removing from greek fields
//                if (greCheck >= 0) {
//                    greekFieldsVector.removeElementAt(greCheck);
//                }
//            } else {
//                // 4. removing from independent fields
//                if (indCheck >= 0) {
//                    independentFieldsVector.removeElementAt(indCheck);
//                }
//            }
//            // Update the whole NodeVector
//            childTable.setElementAt(independentFieldsVector, 0);
//            childTable.setElementAt(englishFieldsVector, 1);
//            childTable.setElementAt(italianFieldsVector, 2);
//            childTable.setElementAt(greekFieldsVector, 3);
//            childVector.setElementAt(childTable, 0);
//        } // while
        return true;
    }
    
    
    
    public boolean removeRowFromDataBaseTable(String fieldName, String fillerType, int rowPosition, String node) {
        if (rowPosition < 0 || rowPosition >= DataBaseTable.m_data.m_vector.size()) {
            return false;
        }
        Enumeration topchildren=DataBasePanel.top.preorderEnumeration();
        DefaultMutableTreeNode nextNode=null;
        while(topchildren.hasMoreElements()) {
            nextNode=(DefaultMutableTreeNode) topchildren.nextElement();
            //System.out.println("2121212221212"+nextNode.toString());
            if (nextNode.toString().equalsIgnoreCase(node.toString()))
                break;
        }
        // A short parenthese to update current-node's templateVector
        NodeVector curVec = (NodeVector)mainDBHashtable.get(node);
        TemplateVector temVec = (TemplateVector)curVec.elementAt(4);
        Hashtable enValues = (Hashtable)temVec.elementAt(0);
        Hashtable itValues = (Hashtable)temVec.elementAt(1);
        Hashtable grValues = (Hashtable)temVec.elementAt(2);
        if (enValues.containsKey(fieldName)) {
            enValues.remove(fieldName);
        }
        if (itValues.containsKey(fieldName)) {
            itValues.remove(fieldName);
        }
        if (grValues.containsKey(fieldName)) {
            grValues.remove(fieldName);
        }
        temVec.setElementAt(enValues, 0);
        temVec.setElementAt(itValues, 1);
        temVec.setElementAt(grValues, 2);	// end updating current templateVector
        
        // remove element (1 row) from current table's vector
        //DataBaseTable.m_data.m_vector.remove(rowPosition);
        
        NodeVector entityTypeParentNode = (NodeVector) mainDBHashtable.get(node);
        Vector parentDatabaseTableVector =(Vector) entityTypeParentNode.elementAt(0);
        //   parentDatabaseTableVector=(Vector) parentDatabaseTableVector.elementAt(0);
        int l=0;
        for (l=0;l<parentDatabaseTableVector.size();l++){
            FieldData property = (FieldData) parentDatabaseTableVector.elementAt(l);
            if (property.m_field.equalsIgnoreCase(fieldName)) break;
        }
        parentDatabaseTableVector.remove(l);
        // Delete appropriate row from descendants table vectors:
        // 1. Get BasicType and EntityType nodes
        Hashtable basicNodeVectors = getChildrenBasics(nextNode);
        Enumeration enum1 = basicNodeVectors.keys();
        while (enum1.hasMoreElements()) {
            String key = (String)enum1.nextElement();
            System.out.println("BBBBB"+key);
            if (key.substring(0, key.length()-1).endsWith("_occur"))
                key=key.substring(0, key.length()-7);
            NodeVector oneChildVector = (NodeVector)basicNodeVectors.get(key);
            Vector oneChildTableVector = (Vector)oneChildVector.elementAt(0);
            oneChildTableVector.removeElementAt(rowPosition);
            NodeVector oneChildNodeVector = (NodeVector)mainDBHashtable.get(key);
            oneChildNodeVector.setElementAt(oneChildTableVector, 0);
            for(int u=1;basicNodeVectors.get(key+String.valueOf(u))!=null;u++){
                System.out.println("BBBBB1"+key+String.valueOf(u));
                oneChildVector = (NodeVector)basicNodeVectors.get(key+String.valueOf(u));
                oneChildTableVector = (Vector)oneChildVector.elementAt(0);
                oneChildTableVector.removeElementAt(rowPosition);
                oneChildNodeVector = (NodeVector)mainDBHashtable.get(key+String.valueOf(u));
                oneChildNodeVector.setElementAt(oneChildTableVector, 0);
                
            }
        }
        
        // 2. Get Entities
        Hashtable entityNodeVectors = getChildrenEntities(nextNode);
        Enumeration enum2 = entityNodeVectors.keys();
        while (enum2.hasMoreElements()) {
            String key = (String)enum2.nextElement();
            NodeVector childVector = (NodeVector)mainDBHashtable.get(key);
            Vector childTable = (Vector)childVector.elementAt(0);  // a vector of
            // 4 vectors
            Vector independentFieldsVector = (Vector)childTable.elementAt(0);
            Vector englishFieldsVector = (Vector)childTable.elementAt(1);
            Vector italianFieldsVector = (Vector)childTable.elementAt(2);
            Vector greekFieldsVector = (Vector)childTable.elementAt(3);
            
            // check if tables already contain fieldName
            int indCheck = tableVectorContainFieldAt(childTable, fieldName, 0);
            int engCheck = tableVectorContainFieldAt(childTable, fieldName, 1);
            int itaCheck = tableVectorContainFieldAt(childTable, fieldName, 2);
            int greCheck = tableVectorContainFieldAt(childTable, fieldName, 3);
            
            if (fillerType.equalsIgnoreCase("string")) {
                // 1. removing from english fields
                if (engCheck >= 0) {
                    englishFieldsVector.removeElementAt(engCheck);
                }
                // 2. removing from italian fields
                if (itaCheck >= 0) {
                    italianFieldsVector.removeElementAt(itaCheck);
                }
                // 3. removing from greek fields
                if (greCheck >= 0) {
                    greekFieldsVector.removeElementAt(greCheck);
                }
            } else {
                // 4. removing from independent fields
                if (indCheck >= 0) {
                    independentFieldsVector.removeElementAt(indCheck);
                }
            }
            // Update the whole NodeVector
            childTable.setElementAt(independentFieldsVector, 0);
            childTable.setElementAt(englishFieldsVector, 1);
            childTable.setElementAt(italianFieldsVector, 2);
            childTable.setElementAt(greekFieldsVector, 3);
            childVector.setElementAt(childTable, 0);
        } // while
        return true;
    }
    
    
        /* ----------- UPDATE 5b ------------------------------------------------
         * This method is called when removing a row from a StoriesTable.
         * ---------------------------------------------------------------------
         */
    public boolean removeRowFromStoriesTable(String fieldName, int rowPosition) {
        if (rowPosition < 0 || rowPosition >= StoriesTable.m_data.m_vector.size()) {
            return false;
        }
        
        // A short parenthese to update current-node's templateVector
        NodeVector curVec = (NodeVector)mainDBHashtable.get(StoriesPanel.last.toString());
        StoriesVector stVec = (StoriesVector)curVec.elementAt(3);
        Hashtable enValues = (Hashtable)stVec.elementAt(0);
        Hashtable itValues = (Hashtable)stVec.elementAt(1);
        Hashtable grValues = (Hashtable)stVec.elementAt(2);
        if (enValues.containsKey(fieldName)) {
            enValues.remove(fieldName);
        }
        if (itValues.containsKey(fieldName)) {
            itValues.remove(fieldName);
        }
        if (grValues.containsKey(fieldName)) {
            grValues.remove(fieldName);
        }
        stVec.setElementAt(enValues, 0);
        stVec.setElementAt(itValues, 1);
        stVec.setElementAt(grValues, 2);	// end updating current templateVector
        
        // remove element (1 row) from current table's vector
        StoriesTable.m_data.m_vector.remove(rowPosition);
        
        return true;
    }
    
    
        /* ----------- UPDATE 6 ------------------------------------------------
         * This method is called when renaming a node. It updates
         * its children's tables with the new value:
         * for Basic-types & Entity-types, first row (Subtype-of, ...),
         * for Entities, first row (entity, ...) and second row (type, ...).
         * ---------------------------------------------------------------------
         */
    public void updateChildrenTableVectorsWithNewParentName(String newName,String parent) {
        int nodeCount = 0;  // count nodes other than leafs
        int leafCount = 0;  // count leaf-nodes
        
        // get only direct-children (avoiding children's children)
        Enumeration enumeration = DataBasePanel.last.children();
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumeration.nextElement();
            
            Object obj = (Object)(child.getUserObject());
            IconData id = (IconData)obj;
            Icon ii = id.getIcon();
            ImageIcon im = (ImageIcon)ii;
            
            NodeVector childVector = (NodeVector)mainDBHashtable.get(child.toString());
            
            Vector childTable = (Vector)childVector.elementAt(0);
            
            if ((!(im == DataBasePanel.ICON_GEI)) && (!(im == DataBasePanel.ICON_GENERIC))) {
                nodeCount++;
                
                FieldData fd = (FieldData)childTable.elementAt(0);
                
                String a = new String(fd.elementAt(0).toString());
                String b = new String(newName);
                String c = new String(fd.elementAt(2).toString());
                boolean cc;
                if (c.equalsIgnoreCase("true")) {
                    cc = true;
                } else {
                    cc = false;
                }
                String d = new String("");
                
                FieldData newfd = new FieldData(a, b, cc, d);
                childTable.setElementAt(newfd, 0);
                childVector.setElementAt(childTable, 0);
            } else {
                leafCount++;
                // a childTable here is a Vector of 4 Vectors.
                // IndependentFieldsVector's first and second rows have to be modified.
                Vector independentFieldsVector = (Vector)childTable.elementAt(0);
                FieldData updatedTypeName = new FieldData("type", newName);
                independentFieldsVector.setElementAt(updatedTypeName, 1);
                childTable.setElementAt(independentFieldsVector, 0);
                childVector.setElementAt(childTable, 0);
            }
        }
    }
    
    
    
    public void updateChildrenTableVectorsWithNewParentName(String newName) {
        int nodeCount = 0;  // count nodes other than leafs
        int leafCount = 0;  // count leaf-nodes
        
        // get only direct-children (avoiding children's children)
        Enumeration enumeration = DataBasePanel.last.children();
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumeration.nextElement();
            
            Object obj = (Object)(child.getUserObject());
            IconData id = (IconData)obj;
            Icon ii = id.getIcon();
            ImageIcon im = (ImageIcon)ii;
            
            NodeVector childVector = (NodeVector)mainDBHashtable.get(child.toString());
            
            Vector childTable = (Vector)childVector.elementAt(0);
            
            if ((!(im == DataBasePanel.ICON_GEI)) && (!(im == DataBasePanel.ICON_GENERIC))) {
                nodeCount++;
                
                FieldData fd = (FieldData)childTable.elementAt(0);
                
                String a = new String(fd.elementAt(0).toString());
                String b = new String(newName);
                String c = new String(fd.elementAt(2).toString());
                boolean cc;
                if (c.equalsIgnoreCase("true")) {
                    cc = true;
                } else {
                    cc = false;
                }
                String d = new String("");
                
                FieldData newfd = new FieldData(a, b, cc, d);
                childTable.setElementAt(newfd, 0);
                childVector.setElementAt(childTable, 0);
            } else {
                leafCount++;
                // a childTable here is a Vector of 4 Vectors.
                // IndependentFieldsVector's first and second rows have to be modified.
                Vector independentFieldsVector = (Vector)childTable.elementAt(0);
                FieldData updatedTypeName = new FieldData("type", newName);
                independentFieldsVector.setElementAt(updatedTypeName, 1);
                childTable.setElementAt(independentFieldsVector, 0);
                childVector.setElementAt(childTable, 0);
            }
        }
    }
    
    
    public void updateChildrenTableVectorsWithNewParentName(String newName,DefaultMutableTreeNode parent) {
        int nodeCount = 0;  // count nodes other than leafs
        int leafCount = 0;  // count leaf-nodes
//System.out.println("ppppppp"+parent.toString());
        // get only direct-children (avoiding children's children)
        Enumeration enumeration = parent.children();
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)enumeration.nextElement();
            
            Object obj = (Object)(child.getUserObject());
            IconData id = (IconData)obj;
            Icon ii = id.getIcon();
            ImageIcon im = (ImageIcon)ii;
            
            NodeVector childVector = (NodeVector)mainDBHashtable.get(child.toString());
            
            Vector childTable = (Vector)childVector.elementAt(0);
            
            if ((!(im == DataBasePanel.ICON_GEI)) && (!(im == DataBasePanel.ICON_GENERIC))) {
                nodeCount++;
//System.out.println("latttt");
                FieldData fd = (FieldData)childTable.elementAt(0);
                
                String a = new String(fd.elementAt(0).toString());
                String b = new String(newName);
                String c = new String(fd.elementAt(2).toString());
                boolean cc;
                if (c.equalsIgnoreCase("true")) {
                    cc = true;
                } else {
                    cc = false;
                }
                String d = new String("");
                
                FieldData newfd = new FieldData(a, b, cc, d);
                childTable.setElementAt(newfd, 0);
                childVector.setElementAt(childTable, 0);
            } else {//System.out.println("L@@O@O@OO@OO");
                leafCount++;
                // a childTable here is a Vector of 4 Vectors.
                // IndependentFieldsVector's first and second rows have to be modified.
                Vector independentFieldsVector = (Vector)childTable.elementAt(0);
                FieldData updatedTypeName = new FieldData("type", newName);
                independentFieldsVector.setElementAt(updatedTypeName, 1);
                childTable.setElementAt(independentFieldsVector, 0);
                childVector.setElementAt(childTable, 0);
            }
        }
    }
    
        /* ----------- UPDATE 7 ------------------------------------------------
         * This method is called when changing a node's nounVector. It updates
         * its children's nounVectors with the new values
         * ---------------------------------------------------------------------
         */
    public void updateChildrenNounVectors(DefaultMutableTreeNode treeNode,Vector beforeVector, Vector newNounVector) {
        Vector clone = new Vector(newNounVector); // for not changing newNounVector
        
        Hashtable h = getChildrenBasics(treeNode);
        Enumeration e = h.elements();
        Enumeration ke = h.keys();
        while (e.hasMoreElements()) {
            Object kel=ke.nextElement();
            //  System.out.println("^^^^^"+kel.toString());
            
            NodeVector n = (NodeVector) e.nextElement();;
            Vector oldNounVector = (Vector)n.elementAt(2);
            for (int k = 0; k < oldNounVector.size(); k++) {
                String nextNoun = oldNounVector.elementAt(k).toString();
                if (!clone.contains(nextNoun) && !beforeVector.contains(nextNoun)) {
                    clone.addElement(nextNoun);
                }
            }
            n.setElementAt(clone, 2);
            
            String last=kel.toString();
            
            if(last.substring(0,last.length()-1).endsWith("_occur")) {
                last=last.substring(0, last.length()-7);
            }
            
            Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
            while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
                // Object nextEl=allTypesNames.nextElement();
                DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                
                
                if ((nextEl.toString().startsWith(last+"_occur")||(nextEl.toString().equalsIgnoreCase(last))) && !nextEl.toString().equalsIgnoreCase(kel.toString())) {
                    System.out.println("%%%%%%%__"+nextEl.toString());
                    
                    NodeVector nv1=(NodeVector) mainDBHashtable.get(nextEl.toString());
                    //NodeVector nv2=(NodeVector) mainDBHashtable.get(nextEl.getParent().toString());
                    // Vector noun=(Vector) nv2.elementAt(2);
                /*         Vector noun=KDialog.getAllInheritedNouns(nextEl.toString());
                         Vector temp=new Vector();
                         Vector nounNew=(Vector) nv1.elementAt(2);
                         for(int z=0;z<noun.size();z++)
                         {
                             if (!temp.contains(noun.elementAt(z)))
                                 temp.add(noun.elementAt(z));
                         }
                         for(int z=0;z<beforeVector.size();z++)
                         {
                             if (!temp.contains(beforeVector.elementAt(z)))
                                 temp.add(beforeVector.elementAt(z));
                         }
                 
                         nv1.setElementAt(temp, 2); */
                    nv1.setElementAt(clone, 2);
                    
                    updateChildrenNounVectors(nextEl,beforeVector,newNounVector);
                }
            }
            clone = new Vector(newNounVector);
        }}
    
    public void updateChildrenNounVectors(Vector beforeVector, Vector newNounVector) {
        Vector clone = new Vector(newNounVector); // for not changing newNounVector
        
        Hashtable h = getChildrenBasics(DataBasePanel.last);
        Enumeration e = h.elements();
        while (e.hasMoreElements()) {
            NodeVector n = (NodeVector)e.nextElement();
            Vector oldNounVector = (Vector)n.elementAt(2);
            for (int k = 0; k < oldNounVector.size(); k++) {
                String nextNoun = oldNounVector.elementAt(k).toString();
                if (!clone.contains(nextNoun) && !beforeVector.contains(nextNoun)) {
                    clone.addElement(nextNoun);
                }
            }
            n.setElementAt(clone, 2);
            clone = new Vector(newNounVector);
        }
    }
    
    
        /* ----------- UPDATE 8 ------------------------------------------------
         * This method is called when changing a node's stories.
         * ---------------------------------------------------------------------
         */
    public void updateHashtableStoryInfo(String nodeName, String language, String storyString) {
        NodeVector currentVector = (NodeVector)mainDBHashtable.get(nodeName);
        StoriesVector currentStoriesVector = (StoriesVector)currentVector.elementAt(3);
        
        if (language.equalsIgnoreCase("English")) {
            Hashtable englishValues = (Hashtable)currentStoriesVector.elementAt(0);
            //int i = StoriesTable.storiesTable.getSelectedRow();
            //String field = StoriesTable.m_data.getValueAt(i, 0).toString();
            String field = StoriesTableListener.lastEditedField;
            englishValues.put(field, storyString);
        } else if (language.equalsIgnoreCase("Italian")) {
            Hashtable italianValues = (Hashtable)currentStoriesVector.elementAt(1);
            //int i = StoriesTable.storiesTable.getSelectedRow();
            //String field = StoriesTable.m_data.getValueAt(i, 0).toString();
            String field = StoriesTableListener.lastEditedField;
            italianValues.put(field, storyString);
        } else if (language.equalsIgnoreCase("Greek")) {
            Hashtable greekValues = (Hashtable)currentStoriesVector.elementAt(2);
            //int i = StoriesTable.storiesTable.getSelectedRow();
            //String field = StoriesTable.m_data.getValueAt(i, 0).toString();
            String field = StoriesTableListener.lastEditedField;
            greekValues.put(field, storyString);
        }
    }
    
    
        /* ----------- UPDATE 9 ------------------------------------------------
         *  This method is invoked when an entity-type (name) story is removed)
         * ---------------------------------------------------------------------
         */
    public void removeStoryFromHashtable(String nodeName, String fieldName) {
        NodeVector nv = (NodeVector)mainDBHashtable.get(nodeName);
        StoriesVector sv = (StoriesVector)nv.elementAt(3);
        Hashtable englishValues = (Hashtable)sv.elementAt(0);
        Hashtable italianValues = (Hashtable)sv.elementAt(1);
        Hashtable greekValues = (Hashtable)sv.elementAt(2);
        
        if (englishValues.containsKey(fieldName)) {
            englishValues.remove(fieldName);
            italianValues.remove(fieldName);
            greekValues.remove(fieldName);
        } else {
            //System.out.println("Trying to remove field from storiesVector:" + " this field does not exist!");
        }
    } // removeStoryFromHashtable
    
    
        /* ----------- UPDATE 10 ------------------------------------------------
         *  This method updates the hashtable for an entity-type (name),
         *  field (field), attribute (attribute), language (language),
         *  storing the attribute value (attributeValue). {(microplanning info)}
         * ----------------------------------------------------------------------
         */
//    public void updateHashtable(String name, String microplanNumber, String field, String attribute, String language, String attributeValue) {
//       // Hashtable temp=propertiesHashtable;
//        //System.out.println(propertiesHashtable.get(field));
//                String nodename=((Vector)((Vector)propertiesHashtable.get(field)).elementAt(0)).elementAt(0).toString();
//	  Vector nodenameVector = (Vector)mainDBHashtable.get(nodename);
//	  Hashtable microplanningHashtable = (Hashtable)nodenameVector.get(5);
//          
//     /*   if(name.substring(0,name.length()-1).endsWith("_occur")) {
//            name=name.substring(0, name.length()-7);
//        }
//        Hashtable allEntityTypes = (Hashtable) getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
//        Enumeration allTypesNames=allEntityTypes.keys();
//        while(allTypesNames.hasMoreElements()) {//DefaultMutableTreeNode nextNode=null;
//            Object nextEl=allTypesNames.nextElement();
//            
//            
//            
//            if (nextEl.toString().startsWith(name+"_occur")||nextEl.toString().equalsIgnoreCase(name)) {
//                String tempHashtableName = nextEl.toString();
//                currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
//                currentHashtable = (Hashtable)currentVector.get(5);
//                */
//                microplanningHashtable.put(microplanNumber + ":" + field + ":" + attribute + ":" + language, attributeValue);
//                
//          //  }
//       // }
//        
//        
//        //System.out.println("*****name: "+name+" micropnanumber: "+microplanNumber+" field: "+field+" attribute: "+attribute+" lang: "+language+" attributeValue: "+attributeValue);
//        
//    }
    
    public void updateHashtable(String name, String microplanNumber, String field, String attribute, String language, String attributeValue) {
        
        
        Vector property=(Vector) propertiesHashtable.get(field);
        currentHashtable = (Hashtable) property.get(10);
        
        currentHashtable.put(microplanNumber + ":" + field + ":" + attribute + ":" + language, attributeValue);
        Mpiro.needExportToExprimo=true;
        
        
        
        
        //System.out.println("*****name: "+name+" micropnanumber: "+microplanNumber+" field: "+field+" attribute: "+attribute+" lang: "+language+" attributeValue: "+attributeValue);
        
    }
    
    
    
    /**
     *  UPDATE 11
     *  This method updates the hashtable for an entity-type (name), field (field),
     *  language (language), storing the stringfield (stringfield), semantics (semantics), type (type).
     *                                  {(microplanning info)}
     */
        /*public void updateHashtableTemplateInfo(String name, String fieldname, String language,
                                                   String stringfield, String semantics, String type) {
         
        String tempHashtableName = name;
        currentHashtable = (Hashtable)mainDBHashtable.get(tempHashtableName);
        int slotsno = 1;
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) { // for
         
                  String keyString = k.nextElement().toString();
                  String valueString = e.nextElement().toString();
                  String checkIfStory = keyString.substring(0, 5);
                  if (checkIfStory.compareTo("SLOTS") == 0) { // if 1
                      Integer currentslotsno = new Integer(keyString.substring(keyString.indexOf(5, keyString.indexOf(":", 0))));
                      if (currentslotsno.intValue() > slotsno) { // if 2
                          slotsno = currentslotsno.intValue();
                      } // if 2
                  } // if 1
        } // for
        int newslotsno = slotsno+1;
         
    }*/
    
    
    /**
     *  UPDATE 12
     *  This method is invoked when an entity-type (name) is removed
     */
    public void renameNodesGenericEntity(String oldname, String newname) {
        NodeVector newGeneric = (NodeVector)mainDBHashtable.get(oldname);
        mainDBHashtable.put(newname, newGeneric);
        mainDBHashtable.remove(oldname);//////////////////added
    }
    
    
    /**
     *  This method is invoked when an entity-type (name) field (oldfieldname) is renamed (newfieldname)
     */
    public void renameHashtableField(String name, String oldfieldname, String newfieldname) {
        if(!propertiesHashtable.containsKey(oldfieldname))
             currentVector = (Vector)propertiesHashtable.get(newfieldname);
        else
            currentVector = (Vector)propertiesHashtable.get(oldfieldname);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        int oldKeylength = oldfieldname.length();
        int newKeyLength = newfieldname.length();
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            
            String keyStringField = keyString.substring(2, keyString.indexOf(":", 2));
            
            if (oldfieldname.compareTo(keyStringField) == 0 ) { // if 1
                String beforeString = keyString.substring(0, 2);
                String appendableString = keyString.substring(keyString.indexOf(":", 2), keyString.length() );
                
                currentHashtable.put(beforeString+newfieldname+appendableString, valueString);
                currentHashtable.remove(keyString);
            } else {
                //System.out.println("Did not find a matching field to replace");
            } // if 2
        }
    }
    
    
    /**
     *  This method is invoked when an entity-type (name) is removed
     */
    public void removeHashtable(String name) {
        mainDBHashtable.remove(name);
    }
    
    
    /**
     *  This method is invoked when an entity-type (name) field (fieldname) is removed)
     */
    public void removeHashtableField(String name, String fieldname) { // removeHashtableField
        currentVector = (Vector)mainDBHashtable.get(name);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        int keylength = fieldname.length();
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) { // for
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            
            String keyStringField = keyString.substring(2, keyString.indexOf(":", 2));
            
            if (fieldname.compareTo(keyStringField) == 0 ) { // if
                ////System.out.println("found");
                currentHashtable.remove(keyString);
            } else {
                ////System.out.println("Did not find a matching field to remove");
            } // if
        } // for
    } // removeHashtableField
    
    
    /**
     *  The method that gets the values from "English" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
    public Vector getValuesFromEnglish(String name, String number, String field, String language) {
        currentValues.clear();
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
            
                        /*
                        String keyStringNumber = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (field.compareTo(keyStringField) == 0) { // if 2
                if (keyStringLanguage.compareTo("English") == 0) { // if 3
                    if (keyStringAttribute.compareTo("Voice") == 0 |
                            keyStringAttribute.compareTo("Tense") == 0 |
                            keyStringAttribute.compareTo("Mood") == 0 |
                            keyStringAttribute.compareTo("Reversible") == 0 |
                            keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
                        currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + "English");
                        //currentValue = (String)currentHashtable.get(keyStringFieldAndAttribute + "English");
                        currentValues.addElement(currentValue);
                        updateHashtable(DataBasePanel.last.toString(),
                                number,
                                field,
                                keyStringAttribute,
                                language,
                                currentValue);
                    } // if 4
                } else {
                } // if 3
            } else {
            } // if 2
        } // for
        return currentValues;
    } //getValuesFromEnglish
    
    
    /**
     *  The method that gets the values from "Italian" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
    public Vector getValuesFromItalian(String name, String number, String field, String language) {
        currentValues.clear();
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (field.compareTo(keyStringField) == 0) { // if 2
                if (keyStringLanguage.compareTo("Italian") == 0) { // if 3
                    if (keyStringAttribute.compareTo("Voice") == 0 |
                            keyStringAttribute.compareTo("Tense") == 0 |
                            keyStringAttribute.compareTo("Aspect") == 0 |
                            keyStringAttribute.compareTo("Mood") == 0 |
                            keyStringAttribute.compareTo("Reversible") == 0 |
                            keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
                        currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + "Italian");
                        //currentValue = (String)currentHashtable.get(keyStringFieldAndAttribute + "Italian");
                        if (!(keyStringAttribute.compareTo("Aspect") == 0 && language.compareTo("English") == 0)) {
                            currentValues.addElement(currentValue);
                            updateHashtable(DataBasePanel.last.toString(),
                                    number,
                                    field,
                                    keyStringAttribute,
                                    language,
                                    currentValue);
                        }
                    } // if 4
                } else {
                } // if 3
            } else {
            } // if 2
        } // for
        return currentValues;
    } //getValuesFromEnglish
    
    
    /**
     *  The method that gets the values from "Greek" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
    public Vector getValuesFromGreek(String name, String number, String field, String language) {
        currentValues.clear();
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (field.compareTo(keyStringField) == 0) { // if 2
                if (keyStringLanguage.compareTo("Greek") == 0) { // if 3
                    if (keyStringAttribute.compareTo("Voice") == 0 |
                            keyStringAttribute.compareTo("Tense") == 0 |
                            keyStringAttribute.compareTo("Aspect") == 0 |
                            keyStringAttribute.compareTo("Mood") == 0 |
                            keyStringAttribute.compareTo("Reversible") == 0 |
                            keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
                        currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + "Greek");
                        //currentValue = (String)currentHashtable.get(keyStringFieldAndAttribute + "Greek");
                        if (!(keyStringAttribute.compareTo("Aspect") == 0 && language.compareTo("English") == 0)) {
                            currentValues.addElement(currentValue);
                            updateHashtable(DataBasePanel.last.toString(),
                                    number,
                                    field,
                                    keyStringAttribute,
                                    language,
                                    currentValue);
                        }
                    } // if 4
                } else {
                } // if 3
            } else {
            } // if 2
        } // for
        return currentValues;
    } //getValuesFromGreek
    
    
    public Vector getValuesFromEnglish2(String name, String number, String field, String language) {
        currentValues.clear();
        //String tempHashtableName = name;
        currentVector = (Vector)propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
            
                        /*
                        String keyStringNumber = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (field.compareTo(keyStringField) == 0) { // if 2
                if (keyStringLanguage.compareTo("English") == 0) { // if 3
                    if (keyStringAttribute.compareTo("Voice") == 0 |
                            keyStringAttribute.compareTo("Tense") == 0 |
                            keyStringAttribute.compareTo("Mood") == 0 |
                            keyStringAttribute.compareTo("Reversible") == 0 |
                            keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
                        currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + "English");
                        //currentValue = (String)currentHashtable.get(keyStringFieldAndAttribute + "English");
                        currentValues.addElement(currentValue);
                        updateHashtable(DataBasePanel.last.toString(),
                                number,
                                field,
                                keyStringAttribute,
                                language,
                                currentValue);
                    } // if 4
                } else {
                } // if 3
            } else {
            } // if 2
        } // for
        return currentValues;
    } //getValuesFromEnglish
    
    
    /**
     *  The method that gets the values from "Italian" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
    public Vector getValuesFromItalian2(String name, String number, String field, String language) {
        currentValues.clear();
        //String tempHashtableName = name;
        currentVector = (Vector)propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (field.compareTo(keyStringField) == 0) { // if 2
                if (keyStringLanguage.compareTo("Italian") == 0) { // if 3
                    if (keyStringAttribute.compareTo("Voice") == 0 |
                            keyStringAttribute.compareTo("Tense") == 0 |
                            keyStringAttribute.compareTo("Aspect") == 0 |
                            keyStringAttribute.compareTo("Mood") == 0 |
                            keyStringAttribute.compareTo("Reversible") == 0 |
                            keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
                        currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + "Italian");
                        //currentValue = (String)currentHashtable.get(keyStringFieldAndAttribute + "Italian");
                        if (!(keyStringAttribute.compareTo("Aspect") == 0 && language.compareTo("English") == 0)) {
                            currentValues.addElement(currentValue);
                            updateHashtable(DataBasePanel.last.toString(),
                                    number,
                                    field,
                                    keyStringAttribute,
                                    language,
                                    currentValue);
                        }
                    } // if 4
                } else {
                } // if 3
            } else {
            } // if 2
        } // for
        return currentValues;
    } //getValuesFromEnglish
    
    
    /**
     *  The method that gets the values from "Greek" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
    public Vector getValuesFromGreek2(String name, String number, String field, String language) {
        currentValues.clear();
        //String tempHashtableName = name;
        currentVector = (Vector)propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (field.compareTo(keyStringField) == 0) { // if 2
                if (keyStringLanguage.compareTo("Greek") == 0) { // if 3
                    if (keyStringAttribute.compareTo("Voice") == 0 |
                            keyStringAttribute.compareTo("Tense") == 0 |
                            keyStringAttribute.compareTo("Aspect") == 0 |
                            keyStringAttribute.compareTo("Mood") == 0 |
                            keyStringAttribute.compareTo("Reversible") == 0 |
                            keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
                        currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + "Greek");
                        //currentValue = (String)currentHashtable.get(keyStringFieldAndAttribute + "Greek");
                        if (!(keyStringAttribute.compareTo("Aspect") == 0 && language.compareTo("English") == 0)) {
                            currentValues.addElement(currentValue);
                            updateHashtable(DataBasePanel.last.toString(),
                                    number,
                                    field,
                                    keyStringAttribute,
                                    language,
                                    currentValue);
                        }
                    } // if 4
                } else {
                } // if 3
            } else {
            } // if 2
        } // for
        return currentValues;
    } //getValuesFromGreek
    
    
    public String getSpecialValueVerbFromEnglish(String name, String number, String field, String language) {
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        String verb = currentHashtable.get(number + ":" + field + ":Verb:English").toString();
        updateHashtable(name, number, field, "Verb", language, verb);
        return verb;
    } //getSpecialValueVerbFromEnglish
    
    
    public String getSpecialValueVerbFromItalian(String name, String number, String field, String language) {
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        String verb = currentHashtable.get(number + ":" + field + ":Verb:Italian").toString();
        updateHashtable(name, number, field, "Verb", language, verb);
        return verb;
    } //getSpecialValueVerbFromItalian
    
    
    public String getSpecialValueVerbFromGreek(String name, String number, String field, String language) {
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        String verb = currentHashtable.get(number + ":" + field + ":Verb:Greek").toString();
        updateHashtable(name, number, field, "Verb", language, verb);
        return verb;
    } //getSpecialValueVerbFromGreek
    
    
    public String getSpecialValueVerbFromEnglish2(String name, String number, String field, String language) {
        //String tempHashtableName = name;
        currentVector = (Vector)propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        String verb = currentHashtable.get(number + ":" + field + ":Verb:English").toString();
        updateHashtable(name, number, field, "Verb", language, verb);
        return verb;
    } //getSpecialValueVerbFromEnglish
    
    
    public String getSpecialValueVerbFromItalian2(String name, String number, String field, String language) {
        //String tempHashtableName = name;
        currentVector = (Vector)propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        String verb = currentHashtable.get(number + ":" + field + ":Verb:Italian").toString();
        updateHashtable(name, number, field, "Verb", language, verb);
        return verb;
    } //getSpecialValueVerbFromItalian
    
    
    public String getSpecialValueVerbFromGreek2(String name, String number, String field, String language) {
        //String tempHashtableName = name;
        currentVector = (Vector)propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        String verb = currentHashtable.get(number + ":" + field + ":Verb:Greek").toString();
        updateHashtable(name, number, field, "Verb", language, verb);
        return verb;
    } //getSpecialValueVerbFromGreek
    
    
    
    /**
     *  The method that gets the values from the entity type (name) hashtable,
     *  for a specific field (field) and shows them.
     *  It is displayed on the micro-planning space for the specific language (language)
     */
//    public Vector showValues(String name, String number, String field, String language) {
//        String tempHashtableName = name;
//        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
//        currentHashtable = (Hashtable)currentVector.get(5);
//        currentValues = new Vector();
//        
//        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
//            String keyString = k.nextElement().toString();
//            String valueString = e.nextElement().toString();
//            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
//            String keyStringNumber = keyStringTokens.nextToken();
//            String keyStringField = keyStringTokens.nextToken();
//            String keyStringAttribute = keyStringTokens.nextToken();
//            String keyStringLanguage = keyStringTokens.nextToken();
//                        /*
//                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
//                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
//                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
//                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
//                         */
//            if (number.compareTo(keyStringNumber) == 0) //if 1
//            {
//                if (field.compareTo(keyStringField) == 0) { // if 2
//                    if (keyStringLanguage.compareTo(language) == 0) { // if 3
//                        if (keyStringAttribute.compareTo("Voice") == 0 |
//                                keyStringAttribute.compareTo("Tense") == 0 |
//                                keyStringAttribute.compareTo("Aspect") == 0 |
//                                keyStringAttribute.compareTo("Mood") == 0 |
//                                keyStringAttribute.compareTo("Reversible") == 0 |
//                                keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
//                            currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + language);
//                            currentValues.addElement(currentValue);
//                        } // if 4
//                    } else {
//                    } // if 3
//                } else {
//                } // if 2
//            }// if 1
//        } // for
//        return currentValues;
//    } //showValues
//    
    
    
    public Vector showValues(String name, String number, String field, String language) {
        //String tempHashtableName = name;
        currentVector = (Vector) propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        currentValues = new Vector();
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            if(keyStringTokens.countTokens()!=4)continue;
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (number.compareTo(keyStringNumber) == 0) //if 1
            {
                if (field.compareTo(keyStringField) == 0) { // if 2
                    if (keyStringLanguage.compareTo(language) == 0) { // if 3
                        if (keyStringAttribute.compareTo("Voice") == 0 |
                                keyStringAttribute.compareTo("Tense") == 0 |
                                keyStringAttribute.compareTo("Aspect") == 0 |
                                keyStringAttribute.compareTo("Mood") == 0 |
                                keyStringAttribute.compareTo("Reversible") == 0 |
                                keyStringAttribute.compareTo("Aggreg") == 0 ) { // if 4
                            currentValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + language);
                            currentValues.addElement(currentValue);
                        } // if 4
                    } else {
                    } // if 3
                } else {
                } // if 2
            }// if 1
        } // for
        return currentValues;
    } //showValues
    
    
    public Hashtable showSpecialValues2(String name, String number, String field, String language) {
        // String tempHashtableName = name;
        currentVector = (Vector) propertiesHashtable.get(field);
        currentHashtable = (Hashtable)currentVector.get(10);
        
        currentSpecialValues = new Hashtable();
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            if(keyStringTokens.countTokens()!=4)continue;
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (number.compareTo(keyStringNumber) == 0) {
                if (field.compareTo(keyStringField) == 0) {
                    if (keyStringLanguage.compareTo(language) == 0) {
                        if (keyStringAttribute.compareTo("SELECTION") == 0 |
                                keyStringAttribute.compareTo("Verb") == 0 |
                                keyStringAttribute.compareTo("Prep") == 0 |
                                keyStringAttribute.compareTo("Preadj") == 0 |
                                keyStringAttribute.compareTo("Postadj") == 0 |
                                keyStringAttribute.compareTo("Casesub") == 0 |
                                keyStringAttribute.compareTo("Caseobj") == 0 |
                                keyStringAttribute.compareTo("Adverb") == 0 |
                                keyStringAttribute.compareTo("Refersub") == 0 |
                                keyStringAttribute.compareTo("Referobj") == 0 ) {
                            currentSpecialValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + language);
                            currentSpecialValues.put(keyStringAttribute, currentSpecialValue);
                        }
                    }
                }
            }
        }
        return currentSpecialValues;
    }
    
    public Hashtable showSpecialValues(String name, String number, String field, String language) {
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        currentSpecialValues = new Hashtable();
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            String keyString = k.nextElement().toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (number.compareTo(keyStringNumber) == 0) {
                if (field.compareTo(keyStringField) == 0) {
                    if (keyStringLanguage.compareTo(language) == 0) {
                        if (keyStringAttribute.compareTo("SELECTION") == 0 |
                                keyStringAttribute.compareTo("Verb") == 0 |
                                keyStringAttribute.compareTo("Prep") == 0 |
                                keyStringAttribute.compareTo("Preadj") == 0 |
                                keyStringAttribute.compareTo("Postadj") == 0 |
                                keyStringAttribute.compareTo("Casesub") == 0 |
                                keyStringAttribute.compareTo("Caseobj") == 0 |
                                keyStringAttribute.compareTo("Adverb") == 0 |
                                keyStringAttribute.compareTo("Refersub") == 0 |
                                keyStringAttribute.compareTo("Referobj") == 0 ) {
                            currentSpecialValue = (String)currentHashtable.get(keyStringNumber + ":" + keyStringField + ":" + keyStringAttribute + ":" + language);
                            currentSpecialValues.put(keyStringAttribute, currentSpecialValue);
                        }
                    }
                }
            }
        }
        return currentSpecialValues;
    }
    
    
    
    /**
     *  This method is invoked when an entity-type (name) story (oldstoryname) is renamed (newstoryname)
     */
    public void renameHashtableStory(String name, String oldstoryname, String newstoryname) {
        currentVector = (Vector)mainDBHashtable.get(name);
        Vector storiesVector = (Vector)currentVector.get(3);
        Hashtable enValues = (Hashtable)storiesVector.elementAt(0);
        Hashtable itValues = (Hashtable)storiesVector.elementAt(1);
        Hashtable grValues = (Hashtable)storiesVector.elementAt(2);
        
        enValues.put(newstoryname, enValues.get(oldstoryname));
        enValues.remove(oldstoryname);
        itValues.put(newstoryname, itValues.get(oldstoryname));
        itValues.remove(oldstoryname);
        grValues.put(newstoryname, grValues.get(oldstoryname));
        grValues.remove(oldstoryname);
    }
    
    
    /**
     * ?
     */
    public void clearHashtableMicroplanningEntries(String name, String number, String field, String language) {
        currentValues.clear();
        String tempHashtableName = name;
        currentVector = (Vector)mainDBHashtable.get(tempHashtableName);
        currentHashtable = (Hashtable)currentVector.get(5);
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements(); ) {
            Object entry = k.nextElement();
            String keyString = entry.toString();
            String valueString = e.nextElement().toString();
            StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
            String keyStringNumber = keyStringTokens.nextToken();
            String keyStringField = keyStringTokens.nextToken();
            String keyStringAttribute = keyStringTokens.nextToken();
            String keyStringLanguage = keyStringTokens.nextToken();
                        /*
                        String keyStringField = keyString.substring(0, keyString.indexOf(":", 0));
                        String keyStringAttribute = keyString.substring(keyString.indexOf(":", keyString.indexOf(":"))+1, keyString.indexOf(":", keyString.indexOf(":")+1));
                        String keyStringFieldAndAttribute = keyString.substring(0, keyString.indexOf(":", keyString.indexOf(":")+1)+1);
                        String keyStringLanguage = keyString.substring(keyString.indexOf(":", keyString.indexOf(":")+1)+1, keyString.length());
                         */
            if (field.compareTo(keyStringField) == 0) { // if 2
                if (keyStringLanguage.compareTo(language) == 0) { // if 3
                    currentHashtable.remove(entry);
                } else {
                } // if 3
            } else {
            } // if 2
        } // for
    } //clearHashtableMicroplanningEntries
    
    
    /**
     *  Returns a vector of all FIRST children (sorted in alphabetical order) currently saved
     *  for a particular entity type node (nodename)
     *  It is used in: DataBasePanel.reloadDBPanel();
     *                 ExportUtilsIlex.createTypesGram();
     */
    public Vector getChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity) {
        Hashtable currentHashtable = new Hashtable();
        if (entityTypeOrEntity.equalsIgnoreCase("Entity type")) {
            currentHashtable = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        } else if (entityTypeOrEntity.equalsIgnoreCase("Entity")) {
            currentHashtable = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
        } else if (entityTypeOrEntity.equalsIgnoreCase("Generic")) {
            currentHashtable = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Generic");
        } else if (entityTypeOrEntity.equalsIgnoreCase("Entity+Generic")) {
            currentHashtable = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
        }
        
        Vector tempVector = new Vector();
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements();) {
            String name = e.nextElement().toString();
            String child = k.nextElement().toString();
            
            if (name.equalsIgnoreCase(nodename)) {
                tempVector.addElement(child);
            }
        }
        Vector childrenVector = new Vector();
        
        if (tempVector.isEmpty() == true) {
        } else {
            childrenVector = QuickSort.quickSort(0, tempVector.size()-1, tempVector);
        }
        return childrenVector;
    }
    
    
    /**
     * Returns a vector of all FIRST children (sorted in
     *  alphabetical order) currently saved * for a particular
     *  entity type node (nodename) * It is used in:
     *  ExportUtilsExprimo.createTypesGram; the hashtable can be
     *  passed through as it be during the export
     *  process (A.I. 12/02/02)
     */
    public Vector getChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity, Hashtable currentHashtable) {
        Vector tempVector = new Vector();
        
        for (Enumeration k = currentHashtable.keys(), e = currentHashtable.elements(); k.hasMoreElements();) {
            String name = e.nextElement().toString();
            String child = k.nextElement().toString();
            
            if (name.equalsIgnoreCase(nodename)) {
                boolean ttt=tempVector.add(child);
                System.out.print("eee");
            }
        }
        
        Vector childrenVector = new Vector();
        
        if (tempVector.isEmpty() == true) {
        } else {
            childrenVector = QuickSort.quickSort(0, tempVector.size()-1, tempVector);
        }
        return childrenVector;
    }
    
    
    
    /**
     *  Returns a hashtable of all entity types and their parent currently saved
     *  It is used when loading a new domain
     */
    public Hashtable getEntityTypesAndEntitiesHashtableFromMainDBHashtable(String entityTypeOrEntity) {
        //Hashtable tempMainDBHashtable = new Hashtable();
        //tempMainDBHashtable = (Hashtable)mainDBHashtable.clone();
        
        Hashtable entityTypesHashtable = new Hashtable();
        Hashtable entitiesHashtable = new Hashtable();
        Hashtable genericHashtable = new Hashtable();
        Hashtable entityAndGenericHashtable = new Hashtable();
        Hashtable returnHashtable = new Hashtable();
        
        Enumeration enumer = mainDBHashtable.keys();
        while (enumer.hasMoreElements()) {
            String name = enumer.nextElement().toString();
            
            NodeVector nodeVector = (NodeVector)mainDBHashtable.get(name);
            Vector tableVector = (Vector)nodeVector.elementAt(0);
            Class test = tableVector.elementAt(0).getClass();
            // two class-types: FieldData (for EntityTypes) and DefaultVector (for Entities)
            if (test.toString().equalsIgnoreCase("class gr.demokritos.iit.eleon.authoring.FieldData")) {
                FieldData fd = (FieldData)tableVector.elementAt(0);
                String[] subtypeof = fd.elementAt(1).toString().split(" ");
                for (int i=0;i<subtypeof.length;i++)
               // if(getParents(name).size()==0) continue;
                    entityTypesHashtable.put(name, subtypeof[i]);  // collect the pairs [nodeName,subtypeof]
                
            } else if (test.toString().equalsIgnoreCase("class gr.demokritos.iit.eleon.authoring.DefaultVector")) {
                Vector independentFields = (Vector)tableVector.elementAt(0);
                FieldData fd = (FieldData)independentFields.elementAt(1);
                String parent[] = ((String)fd.elementAt(1)).split(" ");
                for(int i=0;i<parent.length;i++){
                    if (name.startsWith("Generic-")) {
                        genericHashtable.put(name, parent[i]);  // collect the pairs [nodeName,subtypeof]
                    } else {
                        entitiesHashtable.put(name, parent[i]);  // collect the pairs [nodeName,subtypeof]
                    }
                    entityAndGenericHashtable.put(name, parent[i]);  // collect the pairs [nodeName,subtypeof]
                }
            } else {
                ////System.out.println(test + " : this class cannot be processed!");
            }
        }
        if (entityTypeOrEntity.equalsIgnoreCase("Entity type")) {
            returnHashtable = entityTypesHashtable;
        } else if (entityTypeOrEntity.equalsIgnoreCase("Entity")) {
            returnHashtable = entitiesHashtable;
        } else if (entityTypeOrEntity.equalsIgnoreCase("Generic")) {
            returnHashtable = genericHashtable;
        } else if (entityTypeOrEntity.equalsIgnoreCase("Entity+Generic")) {
            returnHashtable = entityAndGenericHashtable;
        }
        return returnHashtable;
    }
    
    
    /**
     *  Returns a vector of all field names of all children entity-types of an entity-type node
     *  It is used in:  DataBaseTableModel (to check a new field name against all existing field names for an entity-type node and all its entity-type children)
     *
     */
//    public Vector getExistingFieldnamesForEntityTypeAndChildren(DefaultMutableTreeNode entityType) {
//        //Getting all entity types in a Vector
//        Hashtable allEntityTypes = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
//        Vector allEntityTypesVector = new Vector();
//
//        for (Enumeration k = allEntityTypes.keys() ; k.hasMoreElements();) {
//            String name = k.nextElement().toString();
//            allEntityTypesVector.addElement(name);
//        }
//        ////System.out.println("allEntityTypesVector" + allEntityTypesVector);
//        //Using "all entity types in a Vector" to construct a vector of all children (entity types) for our node
//        Vector childrenEntityTypesVector = new Vector();
//
//        Enumeration allChildrenEnum = entityType.preorderEnumeration();
//        while (allChildrenEnum.hasMoreElements()) {
//            String nodeName = allChildrenEnum.nextElement().toString();
//            if (allEntityTypesVector.contains(nodeName)) {
//                childrenEntityTypesVector.addElement(nodeName);
//            }
//        }
//        ////System.out.println("childrenEntityTypesVector" + childrenEntityTypesVector);
//
//        Vector allFieldNamesVector = new Vector();
//
//        //Enumeration allChildrenFieldNamesEnum = childrenEntityTypesVector.elements();
//        Enumeration allChildrenFieldNamesEnum = allEntityTypesVector.elements();
//        while (allChildrenFieldNamesEnum.hasMoreElements()) {
//            String nodeName = allChildrenFieldNamesEnum.nextElement().toString();
//            NodeVector nodeVector = (NodeVector)mainDBHashtable.get(nodeName);
//            Vector dbTableVector = (Vector)nodeVector.get(0);
//
//            // //System.out.println("dbTableVector= "+ dbTableVector);
//            Enumeration dbTableVectorEnum = dbTableVector.elements();
//            while (dbTableVectorEnum.hasMoreElements()) {
//                Vector rowVector = (Vector)dbTableVectorEnum.nextElement();
//                String field = rowVector.get(0).toString();
//                if (!allFieldNamesVector.contains(field)) {
//                    allFieldNamesVector.add(field);
//                }
//            }
//        }
//        ////System.out.println("allFieldNamesVector= "+ allFieldNamesVector);
//        return allFieldNamesVector;
//    }
    
    
    /**
     *  Returns a vector of all children for an entity-type
     *  It is used in:  updateExistingFieldsAfterRemovingANode();
     *                  ExportUtilsIlex.createTypesGram();
     *
     */
    public Vector getFullPathChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity) {//System.out.println("50");
        Vector fullPathChildrenVector = new Vector();
        
        Vector allChildrenEntitiesVector = new Vector();
        Hashtable allEntityTypes = getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
//System.out.println("51");
        for (Enumeration k = allEntityTypes.keys(), e = allEntityTypes.elements(); k.hasMoreElements(); ) {//System.out.println("511");
            try{
                String node = k.nextElement().toString();
                String parent = e.nextElement().toString();//System.out.println("512");
                if(node.contains("_occur")) continue;
                ////System.out.println(node + " + " + parent);
                if ( !(parent.equalsIgnoreCase("type")) && !(parent.equalsIgnoreCase(nodename)) ) {
//System.out.println("513");
                    //System.out.println("fFFFFf"+parent+"hhhh"+allEntityTypes);
                    if (allEntityTypes.containsKey(parent)){
                        String tempparent = allEntityTypes.get(parent).toString();
                        //System.out.println("5131");
                        parent = tempparent;
                       // System.out.println("eeeeeeeeef"+parent);
                    }
                    
                }
                if (parent.equalsIgnoreCase(nodename)) {//System.out.println("514");
                    allChildrenEntitiesVector.addElement(node);
                    ////System.out.println(allChildrenEntitiesVector);
                } else {//System.out.println("515");
                    ////System.out.println("(getFullPathChildrenVectorFromMainDBHashtable)---- ALERT)");
                }
            } catch(NullPointerException w){w.printStackTrace();}
        }
        allChildrenEntitiesVector.addElement(nodename);
        ////System.out.println("(getFullPathChildrenVectorFromMainDBHashtable)---fullChildrenEntities- )" + allChildrenEntitiesVector);
//System.out.println("53");
        if (entityTypeOrEntity.equalsIgnoreCase("Entity type")) {
            allChildrenEntitiesVector.remove(nodename);
            fullPathChildrenVector = allChildrenEntitiesVector;
        } else if (entityTypeOrEntity.equalsIgnoreCase("Entity")) {
            Enumeration enumer = allChildrenEntitiesVector.elements();
            while (enumer.hasMoreElements()) {
                String element = enumer.nextElement().toString();
                Vector children = getChildrenVectorFromMainDBHashtable(element, "Entity");
                if (!children.isEmpty()) {
                    Enumeration enum2 = children.elements();
                    while (enum2.hasMoreElements()) {
                        String leaf = enum2.nextElement().toString();
                        fullPathChildrenVector.addElement(leaf);
                    }
                }
            }
        }
        
        else if (entityTypeOrEntity.equalsIgnoreCase("Generic")) {
            Enumeration enumer = allChildrenEntitiesVector.elements();
            while (enumer.hasMoreElements()) {
                String element = enumer.nextElement().toString();
                Vector children = getChildrenVectorFromMainDBHashtable(element, "Generic");
                if (!children.isEmpty()) {
                    Enumeration enum2 = children.elements();
                    while (enum2.hasMoreElements()) {
                        String leaf = enum2.nextElement().toString();
                        fullPathChildrenVector.addElement(leaf);
                    }
                }
            }
        }
        
        else if (entityTypeOrEntity.equalsIgnoreCase("Entity+Generic")) {
            Enumeration enumer = allChildrenEntitiesVector.elements();
            while (enumer.hasMoreElements()) {
                String element = enumer.nextElement().toString();
                Vector children = getChildrenVectorFromMainDBHashtable(element, "Entity+Generic");
                if (!children.isEmpty()) {
                    Enumeration enum2 = children.elements();
                    while (enum2.hasMoreElements()) {
                        String leaf = enum2.nextElement().toString();
                        fullPathChildrenVector.addElement(leaf);
                    }
                }
            }
        }
        ////System.out.println("(getFullPathChildrenVectorFromMainDBHashtable)---fullPathChildrenVector- )" + fullPathChildrenVector);
        return fullPathChildrenVector;
    }
    
    
    /**
     *  Returns a vector of all children for an entity-type It is
     *  used in: ExportUtilsExprimo.createTypesGram(); the
     *  hashtables can be passed through as they will be static
     *  during the export process (A.I. 12/02/02)
     */
    public Vector getFullPathChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity, Hashtable allEntityTypes, Hashtable currentHash) {
        Vector fullPathChildrenVector = new Vector();
        Vector allChildrenEntitiesVector = new Vector();
        
        for (Enumeration k = allEntityTypes.keys(), e = allEntityTypes.elements(); k.hasMoreElements(); ) {
            String node = k.nextElement().toString();
            String parent = e.nextElement().toString();
            
            while ( !(parent.equalsIgnoreCase("type")) && !(parent.equalsIgnoreCase(nodename)) ) {
                String tempparent = allEntityTypes.get(parent).toString();
                parent = tempparent;
            }
            
            if (parent.equalsIgnoreCase(nodename)) {
                allChildrenEntitiesVector.addElement(node);
                ////System.out.println(allChildrenEntitiesVector);
            } else {
                ////System.out.println("(getFullPathChildrenVectorFromMainDBHashtable)---- ALERT)");
            }
        }
        allChildrenEntitiesVector.addElement(nodename);
        ////System.out.println("(getFullPathChildrenVectorFromMainDBHashtable)---fullChildrenEntities- )" + allChildrenEntitiesVector);
        
        if (entityTypeOrEntity.equalsIgnoreCase("Entity type")) {
            allChildrenEntitiesVector.remove(nodename);
            fullPathChildrenVector = allChildrenEntitiesVector;
        }
        
        else if (entityTypeOrEntity.equalsIgnoreCase("Entity")) {
            Enumeration enumer = allChildrenEntitiesVector.elements();
            while (enumer.hasMoreElements()) {
                String element = enumer.nextElement().toString();
                Vector children = getChildrenVectorFromMainDBHashtable(element, "Entity", currentHash);
                if (!children.isEmpty()) {
                    Enumeration enum2 = children.elements();
                    while (enum2.hasMoreElements()) {
                        String leaf = enum2.nextElement().toString();
                        fullPathChildrenVector.addElement(leaf);
                    }
                }
            }
        }
        
        else if (entityTypeOrEntity.equalsIgnoreCase("Generic")) {
            Enumeration enumer = allChildrenEntitiesVector.elements();
            while (enumer.hasMoreElements()) {
                String element = enumer.nextElement().toString();
                Vector children = getChildrenVectorFromMainDBHashtable(element, "Generic", currentHash);
                if (!children.isEmpty()) {
                    Enumeration enum2 = children.elements();
                    while (enum2.hasMoreElements()) {
                        String leaf = enum2.nextElement().toString();
                        fullPathChildrenVector.addElement(leaf);
                    }
                }
            }
        }
        
        else if (entityTypeOrEntity.equalsIgnoreCase("Entity+Generic")) {
            Enumeration enumer = allChildrenEntitiesVector.elements();
            while (enumer.hasMoreElements()) {
                String element = enumer.nextElement().toString();
                Vector children = getChildrenVectorFromMainDBHashtable(element, "Entity+Generic", currentHash);
                if (!children.isEmpty()) {
                    Enumeration enum2 = children.elements();
                    while (enum2.hasMoreElements()) {
                        String leaf = enum2.nextElement().toString();
                        fullPathChildrenVector.addElement(leaf);
                    }
                }
            }
        }
        ////System.out.println("(getFullPathChildrenVectorFromMainDBHashtable)---fullPathChildrenVector- )" + fullPathChildrenVector);
        return fullPathChildrenVector;
    }
    
    
    /**
     *  Returns a vector of all parents for an entity or generic-entity or entity-type
     *  entityTypeOrEntity (the type of "nodename")
     *  It is used in:  ExportUtilsWebinfo.createImagesInfo();
     *
     */
    public Vector getFullPathParentsVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity) {
        Vector fullPathParentsVector = new Vector();
        
        Hashtable allEntitiesHashtable = getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
        Hashtable allEntityTypesHashtable = getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        allEntityTypesHashtable.remove("Data Base");
        allEntityTypesHashtable.remove("Basic-entity-types");
        
        String possibleChild;
        if (!entityTypeOrEntity.equalsIgnoreCase("Entity type")) {
            String parentOfEntity = allEntitiesHashtable.get(nodename).toString();
            fullPathParentsVector.addElement(parentOfEntity);
            possibleChild = parentOfEntity;
        } else {
            possibleChild = nodename;
        }
        
        while (allEntityTypesHashtable.containsKey(possibleChild)) {
            String parent = allEntityTypesHashtable.get(possibleChild).toString();
            if (
                    !parent.equalsIgnoreCase("Basic-entity-types") &&
                    !parent.equalsIgnoreCase("type")
                    ) {
                fullPathParentsVector.addElement(parent);
            }
            possibleChild = parent;
        }
        return fullPathParentsVector;
    }
    
    /**
     *  Returns a vector of all parents for an entity or generic-entity or entity-type
     *  entityTypeOrEntity (the type of "nodename")
     *  It is used in:  arrayListReturnAllEntityTypesContainingThisNoun;
     *
     */
    public ArrayList getFullPathParentsVectorFromMainDBHashtable(String nodename, Hashtable allEntityTypesHashtable) {
        ArrayList fullPathParentsList = new ArrayList();
        
        allEntityTypesHashtable.remove("Data Base");
        allEntityTypesHashtable.remove("Basic-entity-types");
        
        String possibleChild = nodename;
        
        while (allEntityTypesHashtable.containsKey(possibleChild)) {
            String parent = allEntityTypesHashtable.get(possibleChild).toString();
            if (
                    !parent.equalsIgnoreCase("Basic-entity-types") &&
                    !parent.equalsIgnoreCase("type")
                    ) {
                fullPathParentsList.add(parent);
            }
            possibleChild = parent;
        }
        return fullPathParentsList;
    }
    
    
    
    /**
     *  Returns a vector of all field names a story table of a story node "storyNode"
     *  It is used in:  StoriesTableListener (when adding a new story)
     */
    public Vector getExistingFieldnamesForStoryNode(DefaultMutableTreeNode storyNode) {
        Vector allStoryFieldsVector = new Vector();
        
        String nodeName = storyNode.toString();
        NodeVector nodeVector = (NodeVector)mainDBHashtable.get(nodeName);
        Vector storiesVector = (Vector)nodeVector.get(3);
        Hashtable englishStoriesHashtable = (Hashtable)storiesVector.get(0);
        
        ////System.out.println("(QH.getExistingFieldnamesForStoryNode)---EnglishFields= "+ englishStoriesHashtable.keySet());
        
        Enumeration englishStoriesHashtableEnum = englishStoriesHashtable.keys();
        while (englishStoriesHashtableEnum.hasMoreElements()) {
            String storyField = englishStoriesHashtableEnum.nextElement().toString();
            allStoryFieldsVector.add(storyField);
        }
        ////System.out.println("(QH.getExistingFieldnamesForStoryNode)---allStoryFieldsVector= "+ allStoryFieldsVector);
        return allStoryFieldsVector;
    }
    
    
    
    /**
     *  Updates all fields of all remaining nodes that have relevant information of a deleted DataBase node
     *  It is used in:  DataBasePanel (when removing a node (and consequently all its children)
     *
     */
    public void updateExistingFieldsAfterRemovingANode(Vector deletedEntityTypes, Vector deletedEntities) {
        if (deletedEntityTypes.isEmpty()) // ONLY an entity was deleted
        {
            //Getting all *entities* in a Vector
            Hashtable allEntities = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
            Hashtable allGeneric = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Generic");
            Vector allEntitiesAndGenericVector = new Vector();
            
            for (Enumeration k = allEntities.keys() ; k.hasMoreElements();) {
                String name = k.nextElement().toString();
                allEntitiesAndGenericVector.addElement(name);
            }
            for (Enumeration k = allGeneric.keys() ; k.hasMoreElements();) {
                String name = k.nextElement().toString();
                allEntitiesAndGenericVector.addElement(name);
            }
            ////System.out.println("allEntitiesAndGenericVector" + allEntitiesAndGenericVector);
            
            // Change all instances of all deleted entities
            // for all remaining entities in the domain
            Enumeration allEntitiesChildrenFieldNamesEnum = allEntitiesAndGenericVector.elements();
            while (allEntitiesChildrenFieldNamesEnum.hasMoreElements()) {
                String nodeName = allEntitiesChildrenFieldNamesEnum.nextElement().toString();
                NodeVector nodeVector = (NodeVector)mainDBHashtable.get(nodeName);
                Vector dbTablesVector = (Vector)nodeVector.get(0);
                Vector independentVector = (Vector)dbTablesVector.get(0);
                
                ////System.out.println("independentVector= "+ independentVector);
                
                for (int i=0; i < independentVector.size(); i++) {
                    FieldData rowVector = (FieldData)independentVector.elementAt(i);
                    String field = rowVector.get(0).toString();
                    String filler = rowVector.get(1).toString();
                    ////System.out.println("row= "+ field + " :: " + filler);
                    
                    String deletedEntity = deletedEntities.firstElement().toString();
                    
                    if (filler.equalsIgnoreCase(deletedEntity)) {
                        String newFiller = "";
                        ////System.out.println("newFiller= "+ newFiller);
                        FieldData fd = new FieldData(field, newFiller);
                        independentVector.setElementAt(fd, i);
                    }
                    
                    else if ((filler.indexOf(" ") > 0) && (!filler.startsWith("Select "))) {
                        StringTokenizer str = new StringTokenizer(filler);
                        StringBuffer newFillerBuffer = new StringBuffer("");
                        while (str.hasMoreTokens()) {
                            String token = str.nextToken();
                            if (!token.equalsIgnoreCase(deletedEntity)) {
                                newFillerBuffer.append(token + " ");
                            }
                        }
                        String newFiller = newFillerBuffer.toString();
                        String trimmedNewFiller = newFiller.trim();
                        
                        ////System.out.println("trimmedNewFiller= "+ trimmedNewFiller);
                        FieldData fd = new FieldData(field, trimmedNewFiller);
                        independentVector.setElementAt(fd, i);
                    }
                }
            }
            return;
        } // ONLY an entity was deleted
        
        // create a vector containing all the strings names of the deleted entity-types
        Vector deletedEntityTypesStringVector = new Vector();
        Enumeration deletedEntityTypesEnum = deletedEntityTypes.elements();
        while (deletedEntityTypesEnum.hasMoreElements()) {
            deletedEntityTypesStringVector.add(deletedEntityTypesEnum.nextElement().toString());
        }
        
        // create a vector containing all the strings names of the deleted entities
        Vector deletedEntitiesStringVector = new Vector();
        Enumeration deletedEntitiesEnum = deletedEntities.elements();
        while (deletedEntitiesEnum.hasMoreElements()) {
            deletedEntitiesStringVector.add(deletedEntitiesEnum.nextElement().toString());
        }
        
        Vector allFieldsContainingDeletedFillers = new Vector();
        
        // Search all entity-types' fields and find all that have used the
        // deleted entity-types as fillers.
        // Those fillers should be turned to "String"
        Vector allEntityTypesVector = getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
        Enumeration allEntityTypesVectorEnum = allEntityTypesVector.elements();
        while (allEntityTypesVectorEnum.hasMoreElements()) {
            String entityType = allEntityTypesVectorEnum.nextElement().toString();
            NodeVector entityTypeVector = (NodeVector)mainDBHashtable.get(entityType);
            Vector databaseTableVector = (Vector)entityTypeVector.get(0);
            
            for (int rowNo=0; rowNo< databaseTableVector.size(); rowNo++) {
                FieldData row = (FieldData)databaseTableVector.get(rowNo);
                String field = row.get(0).toString();
                String filler = row.get(1).toString();
                //Boolean setValued = (Boolean)rowVector.get(2);
                String mplanning = row.get(3).toString();
                
                if (deletedEntityTypesStringVector.contains(filler)) {
                    if (!allFieldsContainingDeletedFillers.contains(field)) {
                        allFieldsContainingDeletedFillers.addElement(field);
                    }
                    FieldData fd = new FieldData(field, "String", false, mplanning);
                    databaseTableVector.setElementAt(fd, rowNo);
                }
            }
        }
        
        // Search all entities' and generic-entities' fields and find all that have used the
        // deleted entities as fillers.
        // Those fillers should be turned to "String"
        Vector allEntitiesAndGenericVector = getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity+Generic");
        Enumeration allEntitiesAndGenericVectorEnum = allEntitiesAndGenericVector.elements();
        while (allEntitiesAndGenericVectorEnum.hasMoreElements()) {
            String entity = allEntitiesAndGenericVectorEnum.nextElement().toString();
            NodeVector entityVector = (NodeVector)mainDBHashtable.get(entity);
            Vector dbTablesVector = (Vector)entityVector.get(0);
            Vector independentTableVector = (Vector)dbTablesVector.get(0);
            Vector englishTableVector = (Vector)dbTablesVector.get(1);
            Vector italianTableVector = (Vector)dbTablesVector.get(2);
            Vector greekTableVector = (Vector)dbTablesVector.get(3);
            
            for (int i=0; i < independentTableVector.size(); i++) {
                FieldData rowVector = (FieldData)independentTableVector.elementAt(i);
                String field = rowVector.get(0).toString();
                String filler = rowVector.get(1).toString();
                ////System.out.println("row= "+ field + " :: " + filler);
                
                if (allFieldsContainingDeletedFillers.contains(field)) {
                    FieldData fd1 = new FieldData(field, "");
                    englishTableVector.addElement(fd1);
                    FieldData fd2 = new FieldData(field, "");
                    italianTableVector.addElement(fd2);
                    FieldData fd3 = new FieldData(field, "");
                    greekTableVector.addElement(fd3);
                    independentTableVector.removeElementAt(i);
                    i--;
                }
                
                else if (deletedEntitiesStringVector.contains(filler)) {
                    FieldData fd = new FieldData(field,"");
                    independentTableVector.setElementAt(fd, i);
                }
                
                else if ((filler.indexOf(" ") > 0) && (!filler.startsWith("Select "))) {
                    StringTokenizer str = new StringTokenizer(filler);
                    StringBuffer newFillerBuffer = new StringBuffer("");
                    while (str.hasMoreTokens()) {
                        String token = str.nextToken();
                        if (!deletedEntitiesStringVector.contains(token)) {
                            newFillerBuffer.append(token + " ");
                        }
                    }
                    String newFiller = newFillerBuffer.toString();
                    String trimmedNewFiller = newFiller.trim();
                    
                    FieldData fd = new FieldData(field, trimmedNewFiller);
                    independentTableVector.setElementAt(fd, i);
                }
            }
        }
    } // updateExistingFieldsAfterRemovingANode
    
    
    
    /**
     *  Updates all fields of all nodes that have relevant information of a renamed DataBase node
     *  It is used in:  DataBasePanel (when renaming a node)
     *
     */
    public void updateExistingFieldsAfterRenamingANode(DefaultMutableTreeNode renamedNode, String newName) {
        // Break up the deletedNodes vector into three Vectors containing
        // the deleted Entity-types, Entities, and Generic-entities respectively
        String renamedNodeName = renamedNode.toString();
        
        Object obj = (Object)(renamedNode.getUserObject());
        IconData id = (IconData)obj;
        Icon ii = id.getIcon();
        ImageIcon im = (ImageIcon)ii;
        
        //Getting all *entities* in a Vector
        Hashtable allEntities = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
        Hashtable allGeneric = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Generic");
        Vector allEntitiesAndGenericVector = new Vector();
        
        for (Enumeration k = allEntities.keys() ; k.hasMoreElements();) {
            String name = k.nextElement().toString();
            allEntitiesAndGenericVector.addElement(name);
        }
        for (Enumeration k = allGeneric.keys() ; k.hasMoreElements();) {
            String name = k.nextElement().toString();
            allEntitiesAndGenericVector.addElement(name);
        }
        
        //Getting all *entity types* in a Vector
        Hashtable allEntityTypes = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Vector allEntityTypesVector = new Vector();
        
        for (Enumeration k = allEntityTypes.keys() ; k.hasMoreElements();) {
            String name = k.nextElement().toString();
            allEntityTypesVector.addElement(name);
        }
        
        if (im == DataBasePanel.ICON_GEI || im == DataBasePanel.ICON_GENERIC)//  an entity or generic was renamed
            
        {//System.out.println("|||||"+im.toString());
            // Change all instances of the renamed entity
            // for all remaining entities in the domain
            Enumeration allEntitiesChildrenFieldNamesEnum = allEntitiesAndGenericVector.elements();
            while (allEntitiesChildrenFieldNamesEnum.hasMoreElements()) {
                String nodeName = allEntitiesChildrenFieldNamesEnum.nextElement().toString();
                NodeVector nodeVector = (NodeVector)mainDBHashtable.get(nodeName);
                Vector dbTablesVector = (Vector)nodeVector.get(0);
                Vector independentVector = (Vector)dbTablesVector.get(0);
                
                for (int i=0; i < independentVector.size(); i++) {
                    FieldData rowVector = (FieldData)independentVector.elementAt(i);
                    String field = rowVector.get(0).toString();
                    String filler = rowVector.get(1).toString();
                    
                    if (filler.equals(renamedNodeName)) {
                        String newFiller = newName;
                        FieldData fd = new FieldData(field, newFiller);
                        independentVector.setElementAt(fd, i);
                    }
                    
                    else if ((filler.indexOf(" ") > 0) && (!filler.startsWith("Select "))) {
                        StringTokenizer str = new StringTokenizer(filler);
                        StringBuffer newFillerBuffer = new StringBuffer("");
                        while (str.hasMoreTokens()) {
                            String token = str.nextToken();
                            if (token.equals(renamedNodeName)) {
                                newFillerBuffer.append(newName + " ");
                            } else {
                                newFillerBuffer.append(token + " ");
                            }
                        }
                        String newFiller = newFillerBuffer.toString();
                        String trimmedNewFiller = newFiller.trim();
                        
                        ////System.out.println("trimmedNewFiller= "+ trimmedNewFiller);
                        FieldData fd = new FieldData(field, trimmedNewFiller);
                        independentVector.setElementAt(fd, i);
                    }
                } // for
            } // while
        } // an entity or generic was renamed
        
        
        else if (im == DataBasePanel.ICON_BOOK || im == DataBasePanel.ICON_BASIC) // an entity type was renamed
        {
            // Change all instances of the renamed entity type to "newName"
            // for all remaining entity types in the domain
            Enumeration allEntityTypesChildrenFieldNamesEnum = allEntityTypesVector.elements();
            while (allEntityTypesChildrenFieldNamesEnum.hasMoreElements()) {
                String nodeName = allEntityTypesChildrenFieldNamesEnum.nextElement().toString();
                NodeVector nodeVector = (NodeVector)mainDBHashtable.get(nodeName);
                Vector dbTableVector = (Vector)nodeVector.get(0);
                
                for (int i=0; i < dbTableVector.size(); i++) {
                    FieldData rowVector = (FieldData)dbTableVector.elementAt(i);
                    String field = rowVector.get(0).toString();
                    String filler = rowVector.get(1).toString();
                    System.out.println("EEEeeeeee"+rowVector.get(2).toString());
                    //   Boolean setValued = (Boolean)rowVector.get(2);
                    String mplanning = rowVector.get(3).toString();
                    
                    if (renamedNodeName.equalsIgnoreCase(filler)) {
                        FieldData fd = new FieldData(field, newName, true, mplanning);
                        dbTableVector.setElementAt(fd, i);
                    }
                }
            } // while
            
            // Change all instances of the renamed entity type to "newName" for all remaining entities
            // in the domain (handling the <<Select a "renamedNodeName">> cases)
            Enumeration allEntitiesChildrenFieldNamesEnum = allEntitiesAndGenericVector.elements();
            while (allEntitiesChildrenFieldNamesEnum.hasMoreElements()) {
                String nodeName = allEntitiesChildrenFieldNamesEnum.nextElement().toString();
                NodeVector nodeVector = (NodeVector)mainDBHashtable.get(nodeName);
                Vector dbTablesVector = (Vector)nodeVector.get(0);
                Vector independentVector = (Vector)dbTablesVector.get(0);
                ////System.out.println("independentVector= "+ independentVector);
                
                for (int i=0; i < independentVector.size(); i++) {
                    FieldData rowVector = (FieldData)independentVector.elementAt(i);
                    String field = rowVector.get(0).toString();
                    String filler = rowVector.get(1).toString();
                    ////System.out.println("row= "+ field + " :: " + filler);
                    
                    if (filler.startsWith("Select a ")) {
                        StringTokenizer str = new StringTokenizer(filler, "\"");
                        StringBuffer newFillerBuffer = new StringBuffer("");
                        while (str.hasMoreTokens()) {
                            String token = str.nextToken();
                            if (token.equalsIgnoreCase(renamedNodeName)) {
                                newFillerBuffer.append(newName + "\"");
                            } else {
                                newFillerBuffer.append(token + "\"");
                            }
                        }
                        String newFiller = newFillerBuffer.toString();
                        FieldData fd = new FieldData(field, newFiller);
                        independentVector.setElementAt(fd, i);
                    } // if
                } // for
            } // while
        } else {
            System.out.println("(updateExistingFieldsAfterRenamingANode)---- ERROR!!!!");
        }
    } // updateExistingFieldsAfterRenamingANode
    
    
    /**
     *  Returns a string of all entity-types that have the describing *noun* but not inherited
     *  It is used in: ExportUtilsIlex
     *
     */
    public String returnAllEntityTypesContainingThisNoun(String noun) {
        Vector returnVector = new Vector();
        StringBuffer stringBuffer = new StringBuffer();
        Hashtable allEntityTypesHashtable = (Hashtable)getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        for (Enumeration k = allEntityTypesHashtable.keys(), e = allEntityTypesHashtable.elements(); k.hasMoreElements(); ) {
            String entitytype = k.nextElement().toString();
            String parent = e.nextElement().toString();
            NodeVector env = (NodeVector)mainDBHashtable.get(entitytype);
            NodeVector pnv = (NodeVector)mainDBHashtable.get(parent);
            Vector entitytypeNounsVector = (Vector)env.elementAt(2);
            Vector parentNounsVector = new Vector();
            if ( !entitytype.equalsIgnoreCase("Basic-entity-types") && !entitytype.equalsIgnoreCase("Data Base") ) {
                parentNounsVector = (Vector)pnv.elementAt(2);
            }
            if ( (entitytypeNounsVector.contains(noun)) && !(parentNounsVector.contains(noun)) ) {
                stringBuffer.append(entitytype + " ");
            }
        }
        String returnString = stringBuffer.toString().trim();
        return returnString;
    }
    
    
    //A.I. 06/12/02 trying to optimize, new method signature
    public String returnAllEntityTypesContainingThisNoun(String noun, Hashtable allEntTypesHash) {
        Vector returnVector = new Vector();
        StringBuffer stringBuffer = new StringBuffer();
        Hashtable allEntityTypesHashtable = allEntTypesHash;
        for (Enumeration k = allEntityTypesHashtable.keys(), e = allEntityTypesHashtable.elements(); k.hasMoreElements(); ) {
            String entitytype = k.nextElement().toString();
            String parent = e.nextElement().toString();
            NodeVector env = (NodeVector)mainDBHashtable.get(entitytype);
            NodeVector pnv = (NodeVector)mainDBHashtable.get(parent);
            Vector entitytypeNounsVector = (Vector)env.elementAt(2);
            Vector parentNounsVector = new Vector();
            if ( !entitytype.equalsIgnoreCase("Basic-entity-types") && !entitytype.equalsIgnoreCase("Data Base") ) {
                parentNounsVector = (Vector)pnv.elementAt(2);
            }
            if ( (entitytypeNounsVector.contains(noun)) && !(parentNounsVector.contains(noun)) ) {
                stringBuffer.append(entitytype + " ");
            }
        }
        String returnString = stringBuffer.toString().trim();
        return returnString;
    }
    
    
    public ArrayList arrayListReturnAllEntityTypesContainingThisNoun(String noun, Hashtable allEntTypesHash) {
        ArrayList returnList = new ArrayList();
        Hashtable allEntityTypesHashtable = allEntTypesHash;
        Enumeration k = allEntityTypesHashtable.keys();
        Enumeration e = allEntityTypesHashtable.elements();
        while(k.hasMoreElements()) {
            String parent="";
            String entitytype = k.nextElement().toString();
            try{
                parent = e.nextElement().toString();
            }catch (NullPointerException npe) {continue;}
            if( !entitytype.substring(0,entitytype.length()-1).endsWith("_occur")){
                NodeVector env = (NodeVector)mainDBHashtable.get(entitytype);
                NodeVector pnv = (NodeVector)mainDBHashtable.get(parent);
                Vector entitytypeNounsVector = (Vector)env.elementAt(2);
                Vector parentNounsVector = new Vector();
                
                if ( !entitytype.equalsIgnoreCase("Basic-entity-types") && !entitytype.equalsIgnoreCase("Data Base") ) {
                    parentNounsVector = (Vector)pnv.elementAt(2);
                }
                
                if (entitytypeNounsVector.contains(noun) && !(parentNounsVector.contains(noun))) {
                    {
                        returnList.add(entitytype);
                        ArrayList parents = getFullPathParentsVectorFromMainDBHashtable(entitytype, allEntTypesHash);
                        returnList.addAll(parents);
                    }
                }
            }}
        
        return returnList;
    }
    
    
    /**
     *  Returns a Hashtable with:
     *  keys:   all fields in mainDBHashtable
     *  values: their owner entity-types
     *  It is used in: ExportUtilsPServer (setMicroplanningAppropriateness)
     *
     */
    public Hashtable returnAllFieldsAndContainingEntityTypes() {
        Hashtable returnHashtable = new Hashtable();
        Enumeration propNames=propertiesHashtable.keys();
        Enumeration properties=propertiesHashtable.elements();
        while(propNames.hasMoreElements()){
            String name=propNames.nextElement().toString();
            String domain=((PropertiesHashtableRecord)properties.nextElement()).getDomain().elementAt(0).toString();
             returnHashtable.put(name, domain);
        }
       return returnHashtable;
        
//        Hashtable allEntityTypesHashtable = getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
//        allEntityTypesHashtable.remove("Data Base");
//        allEntityTypesHashtable.remove("Basic-entity-types");
//        
//        for (Enumeration k = allEntityTypesHashtable.keys(), e = allEntityTypesHashtable.elements(); k.hasMoreElements(); ) {
//            String entitytype = k.nextElement().toString();
//            String parent = e.nextElement().toString();
//            NodeVector env = (NodeVector)mainDBHashtable.get(entitytype);
//            if (entitytype.substring(0,entitytype.length()-1).endsWith("_occur"))
//                entitytype=entitytype.substring(0,entitytype.length()-7);
//            NodeVector pnv = (NodeVector)mainDBHashtable.get(allEntityTypesHashtable.get(entitytype));
//            Vector parentDBTable1 = (Vector)pnv.elementAt(0);
//            Vector parentDBTable= (Vector) parentDBTable1.clone();
//            
//            for(int i=2;i<10;i++){
//                
//                if (allEntityTypesHashtable.get(entitytype+"_occur"+String.valueOf(i))==null) break;
//                pnv = (NodeVector)mainDBHashtable.get(allEntityTypesHashtable.get(entitytype+"_occur"+String.valueOf(i)));
//                Vector fields=(Vector) pnv.elementAt(0);
//                for (int o=8;o<fields.size();o++){
//                    parentDBTable.add(fields.elementAt(o));
//                }
//                
//            }
//            //NodeVector pnv = (NodeVector)mainDBHashtable.get(parent);
//            Vector entitytypeDBTable = (Vector)env.elementAt(0);
//            //Vector parentDBTable = (Vector)pnv.elementAt(0);
//            
//            // create a vector containing all fields for the parent entity-type
//            Vector allFieldsInParent = new Vector();
//            Enumeration parentDBTableEnum = parentDBTable.elements();
//            while (parentDBTableEnum.hasMoreElements()) {
//                Vector rowVector = (Vector)parentDBTableEnum.nextElement();
//                String field = rowVector.get(0).toString();
//                allFieldsInParent.addElement(field);
//            }
//            
//            // export all fields that are not contained in the parent entity-type
//            Enumeration entitytypeDBTableEnum = entitytypeDBTable.elements();
//            while (entitytypeDBTableEnum.hasMoreElements()) {
//                Vector rowVector = (Vector)entitytypeDBTableEnum.nextElement();
//                String field = rowVector.get(0).toString();
//                
//                if (!allFieldsInParent.contains(field)) {
//                    returnHashtable.put(field, entitytype);
//                }
//            }
//        }
//        return returnHashtable;
    } // returnAllFieldsAndContainingEntityTypes()
    
    
    public Vector getParents(String nodename){
        Vector parents=new Vector();
        if(nodename.contains("_occur"))
            nodename=nodename.substring(0,nodename.indexOf("_occur"));
        Enumeration allTypes=DataBasePanel.top.preorderEnumeration();
        while(allTypes.hasMoreElements()){
            DefaultMutableTreeNode dmtn=(DefaultMutableTreeNode) allTypes.nextElement();
            if (dmtn.toString().startsWith(nodename+"_occur")||dmtn.toString().equals(nodename)){
                try{
                    if(!dmtn.getParent().toString().contains("_occur"))
                    parents.add(dmtn.getParent().toString());
                }catch(NullPointerException n){};
            }
            
        }
        return parents;
    }
    
    //public String[] getParents2(String nodename){
        // Object ob =mainDBHashtable.get(nodename);
        
        //Object h= ((Vector)(((NodeVector)mainDBHashtable.get(nodename)).databaseTableVector.elementAt(0))).elementAt(1).toString().split(" ");
    //    return ((Vector)(((NodeVector)mainDBHashtable.get(nodename)).databaseTableVector.elementAt(0))).elementAt(1).toString().split(" ");
   // }
    
    public String nameWithoutOccur(String name){
        return  name.contains("_occur") ? name.substring(0,name.indexOf("_occur")) : name ;
    }
    
    public Vector getAllOccurrences(String name){
        name=nameWithoutOccur(name);
        Vector all=new Vector();
        all.add(name);
        for(int i=2;;i++){
            if((mainDBHashtable.get(name+"_occur"+String.valueOf(1)))==null)
                break;
            all.add(name+"_occur"+String.valueOf(1));
        }
        return all;
    }
    
    public void renameFieldInRestrictionsHashtable(String oldname, String newname){
        Enumeration allTypes=getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type").keys();
        while(allTypes.hasMoreElements()){
            String next=allTypes.nextElement().toString();
            // if(valueRestrictionsHashtable.contains(next+":"+oldname)){
            Object value=valueRestrictionsHashtable.get(next+":"+oldname);
            if(value!=null){
                valueRestrictionsHashtable.put(next+":"+newname,value);
                valueRestrictionsHashtable.remove(next+":"+oldname);
                //  }
            }
        }
    }
    
    public void removeFieldFromDomain(String field,String entityType){
        Vector property=(Vector) propertiesHashtable.get(field);
        Vector Domain=(Vector) property.elementAt(0);
        Domain.remove((Object) entityType);
        if(Domain.size()==0)
            deletePropertyFromPropertiesHashtable(field);
    }
    
    public void deletePropertyFromPropertiesHashtable(String propName){
        Vector propVector=(Vector)propertiesHashtable.get((propName));
        Vector subproperties=(Vector) propVector.elementAt(2);
        for(int j=0;j<subproperties.size();j++){
            Vector nextSubProperty=(Vector)propertiesHashtable.get((subproperties.elementAt(j).toString()));
            Vector SuperPropertiesOfnextSubProperty=(Vector) nextSubProperty.elementAt(3);
            SuperPropertiesOfnextSubProperty.remove(propName);
        }
        Vector superproperties=(Vector) propVector.elementAt(3);
        for(int j=0;j<superproperties.size();j++){
            Vector nextSuperProperty=(Vector)propertiesHashtable.get((superproperties.elementAt(j).toString()));
            Vector SubPropertiesOfnextSuperProperty=(Vector) nextSuperProperty.elementAt(2);
            SubPropertiesOfnextSuperProperty.remove(propName);
        }
        String inverse=propVector.elementAt(5).toString();
        if(!inverse.equalsIgnoreCase("")) {
            Vector inverseProperty=(Vector)propertiesHashtable.get(inverse);
            inverseProperty.setElementAt("",5);
        }
        propertiesHashtable.remove(propName);
    }
    
    public void addValuesFromHasValueRestrictions(String entityName, String Parent){
        Parent=nameWithoutOccur(Parent);
        NodeVector entity=(NodeVector) mainDBHashtable.get(entityName);
        Vector db=(Vector) entity.elementAt(0);
        Vector subdb=(Vector) db.elementAt(0);
        for(int h=3;h<subdb.size();h++){
            Vector property=(Vector) subdb.elementAt(h);
            if(!valueRestrictionsHashtable.containsKey(Parent+":"+property.elementAt(0).toString()))
                continue;
            Vector hasValue=(Vector) valueRestrictionsHashtable.get(Parent+":"+property.elementAt(0).toString());
            hasValue=(Vector) hasValue.elementAt(2);
           if(hasValue!=null)
                property.setElementAt(hasValue.toString().replace("]","").replace("[","").replace(",",""),1);
        }
        for(int g=1;g<4;g++){
            subdb=(Vector) db.elementAt(g);
            for(int h=5;h<subdb.size();h++){
                Vector property=(Vector) subdb.elementAt(h);
                try{
                    Vector hasValue=(Vector) valueRestrictionsHashtable.get(Parent+":"+property.elementAt(0).toString());
                    hasValue=(Vector) hasValue.elementAt(2);
                    if(hasValue!=null)
                        property.setElementAt(hasValue.toString().replace("]","").replace("[","").replace(",",""),1);
                }catch(NullPointerException m){continue;}
                
                
            }
        }
    }
    
    public Vector getPropertyImportanceAndRepetitions(String property,String username){
        Vector prop=(Vector) propertiesHashtable.get(property);
        Hashtable users=(Hashtable) prop.elementAt(12);
        Vector user=(Vector) users.get((username));
        return user;
    }
    
    public Vector getPropertyRobotsImportanceAndRepetitions(String property,String robotname, Vector allRobotTypesVector){
        Vector prop=(Vector) propertiesHashtable.get(property);
        Hashtable robots;
        try {
            robots = (Hashtable) prop.elementAt(15);
        } catch(java.lang.ArrayIndexOutOfBoundsException aooi) {
             Hashtable robotsHashtable=new Hashtable();
                
		Enumeration allRobotTypesVectorEnum = allRobotTypesVector.elements();
		while (allRobotTypesVectorEnum.hasMoreElements())
		{
		  String robot = allRobotTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                  robotsHashtable.put(robot,v);	  
		}
                prop.add(15, robotsHashtable);// add(robotsHashtable);
                robots=(Hashtable) prop.elementAt(15);
        }
        Vector robot=(Vector) robots.get((robotname));
        return robot;
    }
    
    public void updateImportanceOrRepetitionsForProperty(String property, String username, int valueID, String value) {
        Vector prop = (Vector)propertiesHashtable.get(property);
        Hashtable users = (Hashtable)prop.elementAt(12);
        Vector usernameVector = (Vector)users.get(username);
        usernameVector.setElementAt(value, valueID);
    }
    
    public void updateRobotsPreferenceForProperty(String property, String robotname, int valueID, String value) {
        Vector prop = (Vector)propertiesHashtable.get(property);
        Hashtable robots = (Hashtable)prop.elementAt(15);
        Vector robotnameVector = (Vector)robots.get(robotname);
        robotnameVector.setElementAt(value, valueID);
    }
    
    public void renameUserInPropertiesHashtable(String oldName, String newName){
        Enumeration properties= propertiesHashtable.elements();
        while(properties.hasMoreElements()){
            Vector nextProp=(Vector) properties.nextElement();
            Hashtable users=(Hashtable) nextProp.elementAt(12);
            Object user=users.get(oldName);
            users.put(newName,user);
            users.remove(oldName);
        }
    }
    
    public void renameRobotInPropertiesHashtable(String oldName, String newName){
        Enumeration properties= propertiesHashtable.elements();
        while(properties.hasMoreElements()){
            Vector nextProp=(Vector) properties.nextElement();
            Hashtable users=(Hashtable) nextProp.elementAt(15);
            Object user=users.get(oldName);
            users.put(newName,user);
            users.remove(oldName);
        }
    }
    
    public void deleteUserFromPropertiesHashtable(String oldName){
        Enumeration properties= propertiesHashtable.elements();
        while(properties.hasMoreElements()){
            Vector nextProp=(Vector) properties.nextElement();
            Hashtable users=(Hashtable) nextProp.elementAt(12);
            users.remove(oldName);
        }
    }
    
    public void deleteRobotFromPropertiesHashtable(String oldName){
        Enumeration properties= propertiesHashtable.elements();
        while(properties.hasMoreElements()){
            Vector nextProp=(Vector) properties.nextElement();
            Hashtable users=(Hashtable) nextProp.elementAt(15);
            users.remove(oldName);
        }
    }
    
    public void addUserInPropertiesHashtable(String name){
        Enumeration properties= propertiesHashtable.elements();
        while(properties.hasMoreElements()){
            Vector nextProp=(Vector) properties.nextElement();
            Hashtable users=(Hashtable) nextProp.elementAt(12);
            Vector v=new Vector();
            v.add("3");
            v.add("3");
            v.add("1");
            users.put(name,v);
        }
    }
    
    public int getMinOfMaxCardinalities(String node,String property){
        if(!valueRestrictionsHashtable.contains(node+":"+property))
            valueRestrictionsHashtable.put(node+":"+property, new ValueRestriction());
        Vector prop=(Vector) valueRestrictionsHashtable.get(node+":"+property);
        prop=(Vector) prop.elementAt(3);
        int minOfMax=Integer.MAX_VALUE;
        for(int y=0;y<prop.size();y++){
            minOfMax=minOfMax<Integer.parseInt(prop.elementAt(y).toString())?minOfMax:Integer.parseInt(prop.elementAt(y).toString());
        }
        return minOfMax;
    }
    
    public int getCardinality(String node,String property){
        Vector prop=(Vector) valueRestrictionsHashtable.get(node+":"+property);
        prop=(Vector) prop.elementAt(5);
        if(prop.size()>0)
            return Integer.parseInt(prop.elementAt(0).toString());
        else
            return -1;
    }
    
    public void removeFillerFromInverseProperty(String Entity, String Value, String Field){
        Vector property=(Vector) propertiesHashtable.get(Field);
        if (!property.elementAt(5).toString().equalsIgnoreCase("")){
            String inverseProp=property.elementAt(5).toString();
            Vector entityValue=(Vector) mainDBHashtable.get(Value);
            entityValue=(Vector) entityValue.elementAt(0);
            Vector fieldValue=(Vector) entityValue.elementAt(0);
            for(int i=3;i<fieldValue.size();i++){
                Vector nextField=(Vector) fieldValue.elementAt(i);
                if(nextField.elementAt(0).toString().equalsIgnoreCase(inverseProp)){
                    String fillers=nextField.elementAt(1).toString();
                    fillers=fillers.replace(Entity+" ","").replace(" "+Entity,"").replace(Entity,"");
                    if (fillers.equalsIgnoreCase("")) fillers="Select ...";
                    nextField.setElementAt(fillers,1);
                    break;
                }
            }
        }
    }
    
        public void addFillerToInverseProperty(String Entity, String Value, String Field){
        Vector property=(Vector) propertiesHashtable.get(Field);
        if (!property.elementAt(5).toString().equalsIgnoreCase("")){
            String inverseProp=property.elementAt(5).toString();
            Vector entityValue=(Vector) mainDBHashtable.get(Value);
            entityValue=(Vector) entityValue.elementAt(0);
            Vector fieldValue=(Vector) entityValue.elementAt(0);
            for(int i=3;i<fieldValue.size();i++){
                Vector nextField=(Vector) fieldValue.elementAt(i);
                if(nextField.elementAt(0).toString().equalsIgnoreCase(inverseProp)){
                    String fillers=nextField.elementAt(1).toString();
                    if(!(fillers.contains(Entity+" ")||fillers.contains(" "+Entity)||fillers.equalsIgnoreCase(Entity))){
                        if(fillers.startsWith("Select")||fillers.equalsIgnoreCase("")){
                            fillers=Entity;
                        }
                        else{
                            fillers=fillers+" "+Entity;
                        }
                  //  fillers=fillers.replace(Entity+" ","").replace(" "+Entity,"").replace(Entity,"");
                   // if (fillers.equalsIgnoreCase("")) fillers="Select ...";
                    nextField.setElementAt(fillers,1);
                    break;
                    }
                }
            }
        }
    }
        
        public void addRestriction(String type, String property, String entitytype, String value){
           
            if(!valueRestrictionsHashtable.contains(nameWithoutOccur(entitytype)+":"+property));
                    valueRestrictionsHashtable.put(nameWithoutOccur(entitytype)+":"+property, new ValueRestriction());
           Vector record=(Vector)valueRestrictionsHashtable.get(nameWithoutOccur(entitytype)+":"+property);
            
           Vector subVector=null;
           if(type.equalsIgnoreCase("maxCardinality")){
               subVector=(Vector) record.elementAt(3);
           }
           if(type.equalsIgnoreCase("minCardinality")){
               subVector=(Vector) record.elementAt(4);
           }
           if(type.equalsIgnoreCase("cardinality")){
               subVector=(Vector) record.elementAt(5);
           }
           if(type.equalsIgnoreCase("allValuesFrom")){
               subVector=(Vector) record.elementAt(0);
           }
           if(type.equalsIgnoreCase("someValuesFrom")){
               subVector=(Vector) record.elementAt(1);
           }
           
           if(type.equalsIgnoreCase("hasValue")){
               subVector=(Vector) record.elementAt(2);
           }
           subVector.add(value);
        }

    public void addRobotInPropertiesHashtable(String name, Vector allRobotTypesVector) {
        for(Enumeration props=propertiesHashtable.elements();props.hasMoreElements();){
            Vector prop=(Vector)props.nextElement();
             Hashtable robots;
        try {
            robots = (Hashtable) prop.elementAt(15);
        } catch(java.lang.ArrayIndexOutOfBoundsException aooi) {
             Hashtable robotsHashtable=new Hashtable();
                
		Enumeration allRobotTypesVectorEnum = allRobotTypesVector.elements();
		while (allRobotTypesVectorEnum.hasMoreElements())
		{
		  String robot = allRobotTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                  robotsHashtable.put(robot,v);	  
		}
                prop.add(15, robotsHashtable);// add(robotsHashtable);
                robots=(Hashtable) prop.elementAt(15);
                continue;
        }
             Vector v=new Vector();
                  v.add("3");
             robots.put(name, v);
            
        }
    }

    public void renameUserInAnnotationsHashtable(String oldName, String newName) {
        Enumeration an=annotationPropertiesHashtable.elements();
        while(an.hasMoreElements()){
            Vector vec1=(Vector)an.nextElement();
            if(vec1.size()==0) continue;
            for(int i=0;i<vec1.size();i++){
            Vector vec=(Vector)vec1.elementAt(i);
            String users=vec.elementAt(4).toString();
            if(users.endsWith(oldName)){
                users=users.substring(0,users.length()-oldName.length());
                vec.setElementAt(users+newName,4);}
            else
            {
                users.replace(" "+oldName," "+newName);
                vec.setElementAt(users,4);
            }
            }
        }
    }


    
    /*It is used when subproperties or superproperties of a property are modified to check
     if the domain of the subproperty is less general than the domain of the superproperty*/
     private boolean checkDomains(String nextDomain,String nextSuperdomain){
    
    if (nextSuperdomain.equalsIgnoreCase(nextDomain))
        return true;
    else {
        Vector children=getChildrenVectorFromMainDBHashtable(nextDomain,"entity type");
        for (int d=0;d<children.size();d++){
            if (checkDomains(children.elementAt(d).toString(),nextSuperdomain))
                return true;
            
        }
    }
    return false;
}

    
     
    public void addSubpropertiesToProperty(Vector selectedSubProp, String propName) {
        Vector propVector=(Vector) propertiesHashtable.get(propName);
                    Vector sub= (Vector) propVector.elementAt(2);
                    sub=(Vector) sub.clone();
                           Vector finalSub=new Vector();
       // System.out.println("dddddddddds"+selectedSubProp.toString()+sub.toString());
        if(!selectedSubProp.equals(sub)){
            for(int n=0;n<selectedSubProp.size();n++){
                if (!sub.contains(selectedSubProp.elementAt(n))){
                    boolean domainOK=false;
                    boolean rangeOK=false;
                    Vector subprop= (Vector) propertiesHashtable.get(selectedSubProp.elementAt(n).toString());
                    Vector subDomain=(Vector) subprop.elementAt(0);
                    Vector domainsuper=(Vector) propVector.elementAt(0);
                    for(int j=0;j<domainsuper.size();j++){
                        String nextSuperdomain=domainsuper.elementAt(0).toString();
                        for(int m=0;m<subDomain.size();m++){
                            String nextDomain=subDomain.elementAt(m).toString();
                            
                            if(checkDomains(nextSuperdomain,nextDomain)){
                                domainOK=true;
                                //finalSuper.add(selectedSubProp.elementAt(n));
                                break;
                                
                            }
                            if(m==subDomain.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(null, //theofilos
                                        "inconsistent domains",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
                        /*           */
                    }
                    
                    
                    
                    //  Vector subprop= (Vector) propertiesHashtable.get(selectedSubProp.elementAt(n).toString());
                    Vector subRange=(Vector) subprop.elementAt(1);
                    Vector rangesuper=(Vector) propVector.elementAt(1);
                    for(int j=0;j<rangesuper.size();j++){
                        String nextSuperrange=rangesuper.elementAt(0).toString();
                        for(int m=0;m<subRange.size();m++){
                            String nextRange=subRange.elementAt(m).toString();
                            
                            if(checkDomains(nextSuperrange,nextRange)){
                                rangeOK=true;
                                
                                break;
                                
                            }
                            if(m==subRange.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(null, //theofilos
                                        "inconsistent range",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
                        /*           */
                    }
                    
                    if(domainOK && rangeOK){ finalSub.add(selectedSubProp.elementAt(n));
                    
                    Vector subPropVector=(Vector) propertiesHashtable.get(selectedSubProp.elementAt(n).toString());
                    subPropVector= (Vector) subPropVector.elementAt(3);
                    subPropVector.add(propName);
                    }
                    
                }
                else
                    finalSub.add(selectedSubProp.elementAt(n));
                
            }
            propVector.setElementAt(finalSub,2);
            
            for(int y=0;y<sub.size();y++){
                if(!finalSub.contains(sub.elementAt(y))){
                    Vector deletedSubroperty=(Vector)propertiesHashtable.get(sub.elementAt(y).toString());
                    Vector superpropertiesOfdeletedSubroperty=(Vector)deletedSubroperty.elementAt(3);
                    superpropertiesOfdeletedSubroperty.remove(propName);
                }
            }
            
            
}
    }

    public void addSuperpropertiesToProperty(Vector selectedSuperProp, String propName) {
       Vector finalSuper=new Vector();
                            Vector propVector=(Vector) propertiesHashtable.get(propName);
                            
                            Vector superp=(Vector) propVector.elementAt(3);
                            superp=(Vector) superp.clone();
        //System.out.println("dddddddddds"+selectedSuperProp.toString());
        if(superp.elements()!=selectedSuperProp.elements()){
            
            for(int n=0;n<selectedSuperProp.size();n++){
                if (!superp.contains(selectedSuperProp.elementAt(n))){
                    boolean domainOK=false;
                    boolean rangeOK=false;
                    Vector subprop= (Vector) propertiesHashtable.get(selectedSuperProp.elementAt(n).toString());
                    Vector subDomain=(Vector) subprop.elementAt(0);
                    Vector domainsuper=(Vector) propVector.elementAt(0);
                    for(int j=0;j<domainsuper.size();j++){
                        String nextSuperdomain=domainsuper.elementAt(0).toString();
                        for(int m=0;m<subDomain.size();m++){
                            String nextDomain=subDomain.elementAt(m).toString();
                            
                            if(checkDomains(nextDomain,nextSuperdomain)){
                                domainOK=true;
                                //finalSuper.add(selectedSuperProp.elementAt(n));
                                break;
                                
                            }
                            if(m==subDomain.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(null, //theofilos
                                        "inconsistent domains",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
                        /*           */
                    }
                    
                    
                    
                    //  Vector subprop= (Vector) propertiesHashtable.get(selectedSuperProp.elementAt(n).toString());
                    Vector subRange=(Vector) subprop.elementAt(1);
                    Vector rangesuper=(Vector) propVector.elementAt(1);
                    for(int j=0;j<rangesuper.size();j++){
                        String nextSuperrange=rangesuper.elementAt(0).toString();
                        for(int m=0;m<subRange.size();m++){
                            String nextRange=subRange.elementAt(m).toString();
                            
                            if(checkDomains(nextRange,nextSuperrange)){
                                rangeOK=true;
                                
                                break;
                                
                            }
                            if(m==subRange.size()-1){
                                Object[] optionButtons = {
                                    "ok",
                                };
                                
                                JOptionPane.showOptionDialog(null, //theofilos
                                        "inconsistent range",
                                        LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                        JOptionPane.WARNING_MESSAGE,
                                        JOptionPane.OK_OPTION,
                                        null,
                                        optionButtons,
                                        optionButtons[0]);
                                
                            }
                            //  Vector children=getChildrenVectorFromMainDBHashtable(nextSuperdomain,"entity type");
                            
                        }
                        /*           */
                    }
                    
                    if(domainOK && rangeOK){ finalSuper.add(selectedSuperProp.elementAt(n));
                    //addSubpropertyValueToProperty(selectedSuperProp.elementAt(n).toString(),propName);
                    Vector superPropVector=(Vector) propertiesHashtable.get(selectedSuperProp.elementAt(n).toString());
                    superPropVector= (Vector) superPropVector.elementAt(2);
                    superPropVector.add(propName);
                    
                    }
                }
                else
                    finalSuper.add(selectedSuperProp.elementAt(n));
                
            }
            propVector.setElementAt(finalSuper,3);
            
        }
                            for(int y=0;y<superp.size();y++){
                if(!finalSuper.contains(superp.elementAt(y))){
                    Vector deletedSuperroperty=(Vector)propertiesHashtable.get(superp.elementAt(y).toString());
                    Vector subpropertiesOfdeletedSuperroperty=(Vector)deletedSuperroperty.elementAt(2);
                    subpropertiesOfdeletedSuperroperty.remove(propName);
                }
            }
    }
    
    /* It is used when the fillers of a property for a given entity are modified, to
     *check if some cardinality restriction is violated*/
        private Vector checkCardinalities(Vector values, String entityName, String fieldName)
    {
        
        int total=values.size();
                    // The total number of items in the list
           //         int total = chboli.list.getModel().getSize();
                    Vector restrictions=(Vector) valueRestrictionsHashtable.get(DataBasePanel.getNode(entityName).getParent().toString()+":"+fieldName);
                    //String[] card=textFieldLabel.getText().split(" ");
                   Vector maxCardinalities=(Vector) restrictions.elementAt(3);
                   Vector Cardinality=(Vector) restrictions.elementAt(5);
                   
                   int minmax=Integer.MAX_VALUE;
                   for(int k=0;k<maxCardinalities.size();k++){
                       minmax= minmax<=Integer.parseInt(maxCardinalities.elementAt(k).toString())? minmax:Integer.parseInt(maxCardinalities.elementAt(k).toString());
                   }
                   
                    Vector v=(Vector) restrictions.elementAt(2);
                    
               //     try{
                     for (int x = 0; x < v.size(); x++) {
                        
                        if(!values.contains(v.elementAt(x))){
                            System.out.println("HASVALUE WARNING");
                            Object[] optionButtons = {
				"OK", 
				};

		 JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
                                                                                                 "value "+v.elementAt(x).toString()+" added (from hasValue restriction)", 
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null, 
												 optionButtons, 
												 optionButtons[0]);
                            values.add(v.elementAt(x));
                           // dialog.dispose();
                           // return;
                        }
                    }
                    if(total+v.size()>minmax)
                    {
                        System.out.println("MAX CARDINALITY ERROR");
                        Object[] optionButtons = {
				"OK", 
				};

			JOptionPane.showOptionDialog( Mpiro.win.getFrames()[0],
					"Error: Selected Values are more than "+String.valueOf(minmax)+" (Max Cardinality Restriction)", 
					LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
					JOptionPane.WARNING_MESSAGE,
					JOptionPane.OK_OPTION,
					null, 
					optionButtons, 
					optionButtons[0]);
			//dialog.dispose();
			return null;
                    }//}catch(NumberFormatException m){};
                    if(!Cardinality.isEmpty()){
                    if(total+v.size()>Integer.parseInt(Cardinality.elementAt(0).toString()))
                    {
                        Object[] optionButtons = {
				"OK", 
				};

			JOptionPane.showOptionDialog( Mpiro.win.getFrames()[0],
												"Error: Number of selected values are more than "+Cardinality.elementAt(0).toString()+" (Cardinality Restriction)",
												LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												JOptionPane.WARNING_MESSAGE,
												JOptionPane.OK_OPTION,
												null, 
												optionButtons, 
												optionButtons[0] );
                        System.out.println("CARDINALITY ERROR");
                     //   dialog.dispose();
                        return null;
                    }}//catch(NumberFormatException m){};
                    
                    Vector property=(Vector)propertiesHashtable.get(fieldName);
                    if(property.elementAt(6).toString().equalsIgnoreCase("true")){
                        if(total+v.size()>1)
                    {
                        Object[] optionButtons = {
				"OK", 
				};

			JOptionPane.showOptionDialog( Mpiro.win.getFrames()[0],
                                                                                                 "Error: Number of selected values are more than 1 (Functional Property)", 
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null, 
												 optionButtons, 
												 optionButtons[0]);
                        System.out.println("CARDINALITY ERROR");
                        //dialog.dispose();
                        return null;
                    }
                    }
                    return values;
    }

/*It is used to modify the fillers of a property for a given instance.
 *setVector contains the fillers before the operation*/
    public void setPropertyFillers(Vector fillers, Vector setVector) {
//        String propName=DataBaseEntityTable.m_data.getValueAt(DataBaseEntityTable.dbetl.rowNo,0).toString();
//                    fillers=checkCardinalities(fillers, DataBasePanel.last.toString(), propName);
//                    try{
//                    fillers.size();
//                    }catch(java.lang.NullPointerException npe){
//                       // dialog.dispose();
//                        return;
//                    }
//
//                    int total=fillers.size();
                    // The total number of items in the list
           //         int total = chboli.list.getModel().getSize();
  /*                  Vector restrictions=(Vector) valueRestrictionsHashtable.get(nameWithoutOccur(DataBasePanel.last.getParent().toString())+":"+DataBaseEntityTable.m_data.getValueAt(DataBaseEntityTable.dbetl.rowNo,0));
                    //String[] card=textFieldLabel.getText().split(" ");
                   Vector maxCardinalities=(Vector) restrictions.elementAt(3);
                   Vector Cardinality=(Vector) restrictions.elementAt(5);
                   
                   int minmax=Integer.MAX_VALUE;
                   for(int k=0;k<maxCardinalities.size();k++){
                       minmax= minmax<=Integer.parseInt(maxCardinalities.elementAt(k).toString())? minmax:Integer.parseInt(maxCardinalities.elementAt(k).toString());
                   }
                   
                    Vector v=(Vector) restrictions.elementAt(2);
                    
               //     try{
                     for (int x = 0; x < v.size(); x++) {
                        
                        if(!selectedItems.contains(v.elementAt(x))){
                            System.out.println("HASVALUE WARNING");
                            Object[] optionButtons = {
				"OK", 
				};

			JOptionPane.showOptionDialog(DDialog.this, //theofilos
                                                                                                 "value "+v.elementAt(x).toString()+" added (from hasValue restriction)", 
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null, 
												 optionButtons, 
												 optionButtons[0]);
                            selectedItems.add(v.elementAt(x));
                           // dialog.dispose();
                           // return;
                        }
                    }
                    if(total+v.size()>minmax)
                    {
                        System.out.println("MAX CARDINALITY ERROR");
                        Object[] optionButtons = {
				"OK", 
				};

			JOptionPane.showOptionDialog(DDialog.this, //theofilos
                                                                                                 "Error: Selected Values are more than "+String.valueOf(minmax)+" (Max Cardinality Restriction)", 
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null, 
												 optionButtons, 
												 optionButtons[0]);
                        dialog.dispose();
                        return;
                    }//}catch(NumberFormatException m){};
                    if(!Cardinality.isEmpty()){
                    if(total+v.size()>Integer.parseInt(Cardinality.elementAt(0).toString()))
                    {
                        Object[] optionButtons = {
				"OK", 
				};

			JOptionPane.showOptionDialog(DDialog.this, //theofilos
                                                                                                 "Error: Number of selected values are more than "+Cardinality.elementAt(0).toString()+" (Cardinality Restriction)", 
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null, 
												 optionButtons, 
												 optionButtons[0]);
                        System.out.println("CARDINALITY ERROR");
                        dialog.dispose();
                        return;
                    }}//catch(NumberFormatException m){};
                    
                    Vector property=(Vector)propertiesHashtable.get(DataBaseEntityTable.m_data.getValueAt(DataBaseEntityTable.dbetl.rowNo,0));
                    if(property.elementAt(6).toString().equalsIgnoreCase("true")){
                        if(total+v.size()>1)
                    {
                        Object[] optionButtons = {
				"OK", 
				};

			JOptionPane.showOptionDialog(DDialog.this, //theofilos
                                                                                                 "Error: Number of selected values are more than 1 (Functional Property)", 
												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
												 JOptionPane.WARNING_MESSAGE,
												 JOptionPane.OK_OPTION,
												 null, 
												 optionButtons, 
												 optionButtons[0]);
                        System.out.println("CARDINALITY ERROR");
                        dialog.dispose();
                        return;
                    }
                    }
                    
                  //  Enumeration en=v.elements();
                  //  Enumeration selected = selectedItems.elements();
                   
                    
                    // Loop (until "total") to find which URLs are selected
    /*                for (int x = 0; x < total; x++) {
                        ListData ld = (ListData) chboli.list.getModel().getElementAt(x);
                        if (ld.isSelected()) {
                            isSelected = true;
                        }
                    }
*/
                    // Update the entity table with the new value
                    
//                     for(int h=0;h<fillers.size();h++){
//                        boolean setVectorContains=false;
//                        for(int k=0;k<setVector.size();k++){
//                            if(setVector.elementAt(k).toString().equalsIgnoreCase(fillers.elementAt(h).toString()))
//                            {setVectorContains=true;
//                            break;
//                            }
//                        }
//                         if(!setVectorContains){
//                             Vector propVector=(Vector)propertiesHashtable.get(propName);
//                             if(propVector.elementAt(7).toString().equals("true")){
//                                 Vector domain=(Vector)propVector.elementAt(0);
//                                 for(int j=0;j<domain.size();j++){
//                                     Enumeration entities=getChildrenEntities(DataBasePanel.getNode(domain.elementAt(j).toString())).elements();
//                                     while(entities.hasMoreElements()){
//                                         Vector nextEntity=(Vector) entities.nextElement();
//                                         Vector temp=(Vector) nextEntity.elementAt(0);
//                                         temp=(Vector) temp.elementAt(0);
//                                         for(int k=3;k<temp.size();k++){
//                                             Vector field=(Vector)temp.elementAt(k);
//                                             if(field.elementAt(0).toString().equalsIgnoreCase(propName)){
//                                                 if(field.elementAt(1).toString().contains(" "+fillers.elementAt(h).toString())||field.elementAt(1).toString().contains(fillers.elementAt(h).toString()+" ")||field.elementAt(1).toString().equalsIgnoreCase(fillers.elementAt(h).toString()))
//                                                 {
//                                                     Object[] optionButtons = {
//				"OK",
//				};
//
//			JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
//                                                                                                 "Error:Inverse functional property "+propName+" can't have the same filler ("+fillers.elementAt(h).toString()+") for more than one entity.",
//												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
//												 JOptionPane.WARNING_MESSAGE,
//												 JOptionPane.OK_OPTION,
//												 null,
//												 optionButtons,
//												 optionButtons[0]);
//                       // System.out.println("CARDINALITY ERROR");
//                                                    // dialog.dispose();
//                                                     return;
//                                                 }
//                                             }
//                                         }
//                                     }
//                                 }
//                             }
//
//                             addFillerToInverseProperty(DataBasePanel.last.toString(),fillers.elementAt(h).toString(),DataBaseEntityTable.m_data.getValueAt(DataBaseEntityTable.dbetl.rowNo, 0).toString());
//                         }
//                    }
                    
                    
                    StringBuffer newItems = new StringBuffer("");

                    Enumeration enumer = fillers.elements();
                    while (enumer.hasMoreElements()) {
                        String item = enumer.nextElement().toString();
                        newItems.append(item + " ");
                    }
                    String newValue = newItems.toString();
                    String trimmedNewValue = newValue.trim();
                    if (trimmedNewValue.length() == 0) {
                        trimmedNewValue = "Select ...";
                    }
                    Vector p=Mpiro.win.struc.getPropertyForEntity(DataBasePanel.last.toString(),FlagPanel.langID, DataBaseEntityTable.m_data.getValueAt(DataBaseEntityTable.dbetl.rowNo,0).toString() );
//                    if(setVector.size()>0){
//                    if(!setVector.elementAt(0).toString().startsWith("Select ")&&!setVector.elementAt(0).toString().equalsIgnoreCase("")){
//                    for(int k=0;k<setVector.size();k++){
//                        if(!fillers.contains(setVector.elementAt(k).toString())){
//
//
//                            if (!p.contains(setVector.elementAt(k).toString())){
//
//                             Object[] optionButtons = {
//				"OK",
//				};
//
//			JOptionPane.showOptionDialog(Mpiro.win.getFrames()[0],
//                                                 "Filler "+setVector.elementAt(k).toString()+" doesn't exist in explicit model",
//												 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
//												 JOptionPane.WARNING_MESSAGE,
//												 JOptionPane.OK_OPTION,
//												 null,
//												 optionButtons,
//												 optionButtons[0]);
//                                                     return;
//
//                    }}
//
//                    }}}
                    
                    p.setElementAt(trimmedNewValue, 1);
                   
                    
                    
                    DataBaseEntityTable.m_data.setValueAt(trimmedNewValue, DataBaseEntityTable.dbetl.rowNo, 1);
    }

    /*This method sets the inverse property of the given property. It does not use any reasoning and makes no checks to
     *ensure that the ontology remains consistent. This functionality has been transfered to
     *MicroReasoner*/
       public void setInverseProperty(String propName, String inversePropName){
         ((Vector)propertiesHashtable.get(propName)).setElementAt(inversePropName, 5);
        }

    public void setPropertyTransitive(String propName) {
       Vector propVector=(Vector)propertiesHashtable.get(propName);
        propVector.setElementAt("true",8);}

    /*This method sets the given property symmetric. It does not use any reasoning and makes no checks to
     *ensure that the ontology remains consistent. This functionality has been transfered to 
     *MicroReasoner*/
    public void setPropertySymmetric(String propName) { 
        
        Vector propVector=(Vector) propertiesHashtable.get(propName);
        propVector.setElementAt("true",9);
        
    }
    
    
     
    

    
  
            public boolean setFunctional(String name){
         boolean canSet=true;

            Enumeration keys=valueRestrictionsHashtable.keys();
            Enumeration elements=valueRestrictionsHashtable.elements();
            while(keys.hasMoreElements()){
                String nextKey=keys.nextElement().toString();
                Vector nextElement=(Vector)elements.nextElement();
                if(nextKey.endsWith(":"+name)){
                    Vector minCard=(Vector)nextElement.elementAt(4);
                    for (int y=0;y<minCard.size();y++){
                    if (!minCard.elementAt(y).toString().equalsIgnoreCase("0")&&!minCard.elementAt(y).toString().equalsIgnoreCase("1")){
                        MessageDialog error=new MessageDialog(null, "Error. "+name+" has minCardinality "+minCard.elementAt(y).toString()+" for type " +nextKey.split(":")[0]);
                            canSet=false;
                            break;
                    }
                }

                    Vector card=(Vector)nextElement.elementAt(5);
                    for (int y=0;y<card.size();y++){
                    if (!card.elementAt(y).toString().equalsIgnoreCase("0")&&!card.elementAt(y).toString().equalsIgnoreCase("1")){
                        //ERROR
                        MessageDialog error=new MessageDialog(null, "Error. "+name+" has Cardinality "+card.elementAt(y).toString()+" for type " +nextKey.split(":")[0]);
                            canSet=false;
                            break;
                    }
                }
            }}



            Vector dom=(Vector)((Vector)propertiesHashtable.get(name)).elementAt(0);
            for (int i=0;i<dom.size();i++)
            {
                if(!canSetFunctional(dom.elementAt(i).toString(), name))
                {
                    canSet=false;
                    break;
                }
            }

            if(((Vector)propertiesHashtable.get(name)).elementAt(8).toString().equalsIgnoreCase("true")){
                canSet=false;
                 Object[] optionButtons = {
                "ok",
            };

            JOptionPane.showOptionDialog(null, //theofilos
                    "Property "+name+" is transitive. It cannot be set functional(OWL FULL element)",
                    LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                    JOptionPane.WARNING_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    optionButtons,
                    optionButtons[0]);
            }

    /*                    Enumeration restrictionKeys=valueRestrictionsHashtable.keys();
            Enumeration restrictions=valueRestrictionsHashtable.elements();
            while(restrictionKeys.hasMoreElements()){
                Vector nextRes=(Vector) restrictions.nextElement();
                if(restrictionKeys.nextElement().toString().split(":")[1].equalsIgnoreCase(propName)){
                    if(!((Vector)nextRes.elementAt(3)).isEmpty()||!((Vector)nextRes.elementAt(4)).isEmpty()||!((Vector)nextRes.elementAt(5)).isEmpty()){
                         Object[] optionButtons = {
                "ok",
            };

            JOptionPane.showOptionDialog(EditFieldProperties.this, //theofilos
                    "οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½ οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½ οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½ οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½οΏ½",
                    LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                    JOptionPane.WARNING_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    optionButtons,
                    optionButtons[0]);
            canSet=false;
            break;
                    }
                }
            }*/
          //  if(canSet)
        //propVector.setElementAt("true",6);
            return(canSet);
    }
        
    private boolean canSetInverseFunctional(String propName){
            Vector propVector=(Vector)propertiesHashtable.get(propName);
         Vector Domain=(Vector)propVector.elementAt(0);
            Vector fillers=new Vector();
            for(int k=0;k<Domain.size();k++){
                Enumeration entities=getChildrenEntities(DataBasePanel.getNode(Domain.elementAt(k).toString())).elements();
                while(entities.hasMoreElements()){
                  //  Vector nextEntity=(Vector)mainDBHashtable.get(entities.nextElement().toString());
                    Vector db=(Vector) entities.nextElement();
                    db=(Vector) db.elementAt(0);
                    Vector ind=(Vector) db.elementAt(0);
                    for(int h=3;h<ind.size();h++){
                        Vector field=(Vector) ind.elementAt(h);
                        if(field.elementAt(1).toString().startsWith("Select"))
                            break;
                        String[] values=field.elementAt(1).toString().split(" ");
                        if(field.elementAt(0).toString().equalsIgnoreCase(propName)){
                            for(int l=0;l<values.length;l++){
                                if(fillers.contains(values[l])){
                                    Object[] optionButtons = {
                "ok",
            };

            JOptionPane.showOptionDialog(null, //theofilos
                    "This property cannot be set inverse functional, because "+values[l]+" is filler of this property for more than one entities",
                    LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                    JOptionPane.WARNING_MESSAGE,
                    JOptionPane.OK_OPTION,
                    null,
                    optionButtons,
                    optionButtons[0]);
                                    return false;
                                }
                                else
                                    fillers.add(values[l]);
                            }
                        }
                    }
                }
            }
            return true;
    }
    
     private boolean canSetFunctional(String node, String name){
        Hashtable childrenEntities= getChildrenEntities(DataBasePanel.getNode(node));
        Enumeration keys=childrenEntities.keys();
        Enumeration values=childrenEntities.elements();
        while(keys.hasMoreElements()){
            String nextKey=keys.nextElement().toString();
            Vector nextEntity=(Vector) values.nextElement();
            nextEntity=(Vector) nextEntity.elementAt(0);
            nextEntity=(Vector) nextEntity.elementAt(0);
            for(int y=3;y<nextEntity.size();y++){
                Vector nextField=(Vector) nextEntity.elementAt(y);
                if (nextField.elementAt(0).toString().equals(name)){
                    if(!nextField.elementAt(1).toString().startsWith("Select ")&&nextField.elementAt(1).toString().split(" ").length>1){
                        MessageDialog error=new MessageDialog(null, "Error. "+nextKey+" has more than 1 value for "+name);
                        System.out.println("ERROR: "+nextKey);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    


     public void setInverseFunctional(String propName) {
        if(canSetInverseFunctional(propName)){
            Vector propVector=(Vector)propertiesHashtable.get(propName);
             propVector.setElementAt("true",7);
     //  if(setFunctional(propVector.elementAt(5).toString())){
       //    propVector.setElementAt("true",7);
            if(!propVector.elementAt(5).toString().equals("")){
       Vector inverse=(Vector) propertiesHashtable.get(propVector.elementAt(5).toString());
       inverse.setElementAt("true",6);}
        }
    }

    /*returns all the properties of a type along with the filler types
     *for each property. The returned vector is the data for the
     *databasetable model*/
    public Vector getPropertiesForType(String nodeName) {
        Vector result=new Vector();
        result.add(new FieldData("type", VectorAsStringForDBTable((Vector)getParents(nodeName)), true, ""));
       // Enumeration propNames=;
        Vector sortedPropNames=new Vector(propertiesHashtable.keySet());
        if(sortedPropNames.size()==0) return result;
        QuickSort.quickSort(0, sortedPropNames.size()-1, sortedPropNames);
       // Enumeration propVectors=propertiesHashtable.elements();
      //  while(propNames.hasMoreElements()){
            for(int i=0;i<sortedPropNames.size();i++){
            String propName=(String)  sortedPropNames.elementAt(i);
            PropertiesHashtableRecord propVector=(PropertiesHashtableRecord) propertiesHashtable.get(propName);
            if(propVector.hasTypeInItsDomain(nodeName)){
                result.add(new FieldData(propName, VectorAsStringForDBTable((Vector)propVector.elementAt(1)) , true, ""));
            }
            
            
        }
        return result;
    }
    
    public String VectorAsStringForDBTable(Vector v){
        return v.toString().substring(1,v.toString().length()-1);
    }

    /*returns all the supertypes of the given type.
     *Both direct and indirect parents*/
    public Vector getAllSupertypes(String type) {
        Vector allSuperTypes=new Vector();
        Vector parents=getParents(type);
        for(int i=0;i<parents.size();i++){
            allSuperTypes.add(parents.elementAt(i));
            Vector parentAllSuperTypes=getAllSupertypes(parents.elementAt(i).toString());
            for(int j=0;j<parentAllSuperTypes.size();j++){
                allSuperTypes.add(parentAllSuperTypes.elementAt(j));
            }
        }
        return allSuperTypes;
    }

    public void setPropertyDomain(Vector selectedDomains, String property) {
        PropertiesHashtableRecord prop=(PropertiesHashtableRecord) propertiesHashtable.get(property);
        prop.setElementAt(selectedDomains,0);
        

        
        Hashtable entitiesHash=getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
        Enumeration entities=entitiesHash.keys();
        Enumeration  parents=entitiesHash.elements();
        while(entities.hasMoreElements()){
            boolean entityHasProperty=false;
            String nextEntity=entities.nextElement().toString();
            String nextParent=parents.nextElement().toString();
            for(int i=0;i<selectedDomains.size();i++){
                if(getAllSupertypes(nextParent).contains(selectedDomains.elementAt(i).toString()) || nextParent.equals(selectedDomains.elementAt(i).toString())){
                    entityHasProperty=true;
                    break;}
            }
            NodeVector ent=(NodeVector)mainDBHashtable.get(nextEntity);
            if(!entityHasProperty)
            {
                ent.removeFieldData(property,1);
                ent.removeFieldData(property,2);
                ent.removeFieldData(property,3);
                ent.removeFieldData(property,4);
            }
            else{
                if(!ent.independentFieldsVectorContainsField(property)&& !ent.greekFieldsVectorContainsField(property))
                {
                    FieldData fd=new FieldData(property, "");
                    if(prop.getRange().contains("String"))
                    {
                        ent.addFieldData(fd,2);
                        ent.addFieldData(fd,3);
                        ent.addFieldData(fd,4);
                        
                    }
                    else
                        ent.addFieldData(fd,1);
                }
            }
            
            
        }
       /////TO ADD:::1.remove or add prop to entities 2.check restrictions/subproperties etc
        DataBaseTable.dbTable.revalidate();
					DataBaseTable.dbTable.repaint(); 
    }
    
    /*adds the given filler to the the property of the given entity*/
    public void addFillerToProperty(String property, String entity, String filler, int vector){
        NodeVector nv=(NodeVector)mainDBHashtable.get(entity);
        Vector fieldVector=new Vector();
        if(vector==1)
            fieldVector=nv.getIndependentFieldsVector();
        if(vector==2)
            fieldVector=nv.getEnglishFieldsVector();
        if(vector==1)
            fieldVector=nv.getGreekFieldsVector();
        if(vector==1)
            fieldVector=nv.getItalianFieldsVector();
        for(int i=0;i<fieldVector.size();i++){
            Vector propertyOfEntity=(Vector) fieldVector.elementAt(i);
            if(propertyOfEntity.elementAt(0).toString().equals(property)){
                if(propertyOfEntity.elementAt(1).toString().equals("")||propertyOfEntity.elementAt(1).toString().startsWith("Select")){
                    propertyOfEntity.setElementAt(filler, 1);
                    return;}
                else
                {
                    String[] existingFillers=propertyOfEntity.elementAt(1).toString().split(" ");
                    for(int j=0; j<existingFillers.length;j++){
                        if(existingFillers[j].equals(filler))
                            return;
                        
                    }
                    propertyOfEntity.setElementAt(filler+" "+propertyOfEntity.elementAt(1).toString(), 1);
                }
            }
        }
            
    }
    
   
    
}//QueryHashTable
