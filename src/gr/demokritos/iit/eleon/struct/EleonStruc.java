/*
 * EleonStruc.java
 *
 * Created on 18 Μάρτιος 2009, 1:20 μμ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.authoring.DataBasePanel;
import gr.demokritos.iit.eleon.authoring.FieldData;
import gr.demokritos.iit.eleon.authoring.IconData;
import gr.demokritos.iit.eleon.authoring.NodeVector;
import gr.demokritos.iit.eleon.authoring.PropertiesHashtableRecord;
import gr.demokritos.iit.eleon.authoring.TemplateVector;
import gr.demokritos.iit.eleon.profiles.Robot;
import gr.demokritos.iit.eleon.profiles.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author dimitris
 */
public class EleonStruc {
    QueryLexiconHashtable lexHash;
    QueryHashtable ontoHash;
    QueryOptionsHashtable optionsHash;
    QueryProfileHashtable profileHash;
    GeneralizedProfiles gp;
    /**
     * Creates a new instance of EleonStruc
     */
    public EleonStruc() {
        lexHash=new QueryLexiconHashtable();
        ontoHash= new QueryHashtable();
        optionsHash=new QueryOptionsHashtable();
        profileHash=new QueryProfileHashtable();
        gp=new GeneralizedProfiles();
        createDefaultUser("NewUserType");
        createDefaultRobot("NewProfile");
        
    }
    public void clearStructs() {
        // First, clear the two main hashtables and the corresponding panels
        
        
        ontoHash.mainDBHashtable.clear();
        ontoHash.propertiesHashtable.clear();
        ontoHash.equivalentClassesHashtable.clear();
        ontoHash.superClassesHashtable.clear();
        ontoHash.valueRestrictionsHashtable.clear();
        ontoHash.annotationPropertiesHashtable.clear();
        gp.robotCharValuesHashtable.clear();
        gp.robotCharVector.clear();
        lexHash.mainLexiconHashtable.clear();
        profileHash.mainUsersHashtable.clear();
        profileHash.mainUserModelHashtable.clear();
        //QueryProfileHashtable.robotsHashtable.clear();
        // QueryProfileHashtable.mainRobotsModelHashtable.clear();
        
        
        
    }
    
    public void createDefaultHashtableField(String name) {
        Vector usersVector =profileHash.getUsersVectorFromMainUsersHashtable();
        ontoHash.createDefaultHashtableField(name, usersVector);
    }

    public Vector getCharacteristics() {
        Vector result=new Vector();
        for(int i=0;i<gp.robotCharVector.size();i++){
             Vector nextChar=(Vector)gp.robotCharVector.elementAt(i);
             result.add(nextChar.elementAt(0));
         }
         return result;
    }
    
    public Object getUser(String username){
        return profileHash.mainUsersHashtable.get(username);
    }
    
    public Vector getPropertyRobotsImportanceAndRepetitions(String property,String robotname){
        Vector allRobotTypesVector = profileHash.getRobotsVectorFromUsersHashtable();
        return ontoHash.getPropertyRobotsImportanceAndRepetitions(property,robotname, allRobotTypesVector);
    }
    
    public void addRobotInPropertiesHashtable(String name) {
        Vector allRobotTypesVector = profileHash.getRobotsVectorFromUsersHashtable();
        ontoHash.addRobotInPropertiesHashtable(name, allRobotTypesVector);
    }
    
    public Vector getRobotsCharValues(String field,String node, String username) {
        Object[] robots=profileHash.getRobotNamesToArray();
        return gp.getRobotsCharValues(field, node, username, robots);
    }
    
    public void addChangesInRobotCharValuesHashtable(Vector selectedItems){
        Object[] robots=profileHash.getRobotNamesToArray();
        gp.addChangesInRobotCharValuesHashtable(selectedItems, robots);
    }
    
    public void removeRobotFromRobotCharVector(String name) {
        
        Object[] r=profileHash.getRobotNamesToArray();
        gp.removeRobotFromRobotCharVector(name, r);
        
    }
    
    public void createDefaultUser(String name) {
        profileHash.mainUsersHashtable.put(name, new User("4", "10", "4", "male"));
        addUserInUserModelHashtable(name);
        ontoHash.addUserInPropertiesHashtable(name);
        //System.out.println("(createDefaultUser)---- " + name);
    }
    
    public void createDefaultRobot(String name) {
        profileHash.mainUsersHashtable.put(name, new Robot("50", "50", "50", "50", "50"));
        
        addRobotInUserModelHashtable(name);
        gp.addRobotInRobotCharValuesHashtable(name);
        addRobotInPropertiesHashtable(name);
        //System.out.println("(createDefaultUser)---- " + name);
    }

    public void setRobotCharVector(Vector charVector) {
        this.gp.robotCharVector=charVector;
    }
    
    public void updateIndependentLexiconHashtable(String usertype, String oldusertype, String action){
        Hashtable lexiconNounHashtable = (Hashtable)lexHash.mainLexiconHashtable.get("Nouns");
        Hashtable lexiconVerbHashtable = (Hashtable)lexHash.mainLexiconHashtable.get("Verbs");
        profileHash.updateIndependentLexiconHashtable( usertype,  oldusertype,  action,  lexiconNounHashtable,  lexiconVerbHashtable);
        
    }
    
    public void removeUser(String name) {
        profileHash.removeUser(name);
        ontoHash.deleteUserFromPropertiesHashtable(name);
    }
    
    public void removeRobot(String name) {
        profileHash.removeRobot(name);
        ontoHash.deleteRobotFromPropertiesHashtable(name);
    }
    public void addUserInUserModelHashtable(String newname) {
        Hashtable allEntityTypes = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Hashtable allEntities = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
        profileHash.addUserInUserModelHashtable(newname, allEntityTypes, allEntities);
        
    }
    
    public void addRobotInUserModelHashtable(String newname) {
        Hashtable allEntityTypes = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Hashtable allEntities = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
        profileHash.addRobotInUserModelHashtable(newname, allEntityTypes, allEntities);
        
    }
    
    public void addFieldInUserModelHashtable(String fieldname, String entitytypename) {
        Vector allChildrenEntities = ontoHash.getFullPathChildrenVectorFromMainDBHashtable(entitytypename, "Entity+Generic");
        profileHash.addFieldInUserModelHashtable( fieldname,  entitytypename,  allChildrenEntities);
    }
    
    public void createDefaultLexiconNoun(String name) {
        Vector usersVector = profileHash.getUsersVectorFromMainUsersHashtable();
        lexHash.createDefaultLexiconNoun(name, usersVector);
    }
    
    public void createDefaultLexiconVerb(String name) {
        Vector usersVector = profileHash.getUsersVectorFromMainUsersHashtable();
        lexHash.createDefaultLexiconVerb(name, usersVector);
    }
    
    public void renameLexiconEntry(String oldname, String newname) {
        Hashtable entityTypesHashtable = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        lexHash.renameLexiconEntry(oldname, newname, ontoHash.mainDBHashtable, entityTypesHashtable);
    }
    
    public void removeLexiconEntry(String name){
        Hashtable entityTypesHashtable = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        lexHash.removeLexiconEntry(name, ontoHash.mainDBHashtable, entityTypesHashtable);
    }
    
    public NodeVector getEntityTypeOrEntity(String name){
        return (NodeVector)ontoHash.mainDBHashtable.get(name);
    }
    
    public void putEntityTypeOrEntityToDB(String name, NodeVector entityTypeOrEntity){
        ontoHash.mainDBHashtable.put(name, entityTypeOrEntity);
    }
    
    public PropertiesHashtableRecord getProperty(String propName){
        return (PropertiesHashtableRecord) ontoHash.propertiesHashtable.get(propName);
    }
    
    public boolean existsProperty(String propName){
        return  ontoHash.propertiesHashtable.containsKey(propName);
    }
    
    public PropertiesHashtableRecord removeProperty(String propName){
        return (PropertiesHashtableRecord) ontoHash.propertiesHashtable.remove(propName);
    }
    
    public PropertiesHashtableRecord addProperty(String propName, PropertiesHashtableRecord prop){
        return (PropertiesHashtableRecord) ontoHash.propertiesHashtable.put(propName, prop);
    }
    
    
    public String nameWithoutOccur(String name){
        return ontoHash.nameWithoutOccur(name);
    }
    public NodeVector removeEntityTypeOrEntityFromDB(String name){
        return (NodeVector)ontoHash.mainDBHashtable.remove(name);
    }
    public boolean mainDBcontainsEntityOrEntityType(String name){
        return ontoHash.mainDBHashtable.containsKey(name);
    }
    
    
    public void initializeStructures() {
      
        this.ontoHash=new QueryHashtable();
        
        
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
                    ontoHash.createBasicEntityType(node, "Data Base");
                } else {
                    String parent = tn.getParent().toString();
                    ontoHash.createBasicEntityType(parent, node);
                    //QueryHashtable.createDefaultStory(node);  /// spiliot
                }
            }
            
            if ( (ima == DataBasePanel.ICON_GEI) ||
                    (ima == DataBasePanel.ICON_GENERIC)) {
                if (tn.getParent() == null) {
                    // do nothing
                    //System.out.println("Leaf's parent is tree's root!!!");
                } else {
                    String parent = tn.getParent().toString();
                    ontoHash.createEntity(parent, node);
                    //QueryHashtable.createDefaultStory(node);  /// spiliot
                }
            }
        }
        
        /*
         * Second: creating DefaultUpperVector
         */
        ontoHash.createDefaultUpperVector();
        
        /*
         * Third: Lexicon initialisation.
         */
        this.lexHash=new QueryLexiconHashtable();
        
        /*
         * Fourth: Users initialisation.
         */
        this.profileHash= new QueryProfileHashtable();
        //QueryProfileHashtable.createmainRobotsModelHashtable();
//QueryProfileHashtable.createRobotsHashtable();
        
        /*
         * Fifth: Options initialisation.
         */
        this.optionsHash=new QueryOptionsHashtable();
        this.createDefaultUser("NewUserType");
        
        this.createDefaultRobot("NewProfile");
        
        
    }
    
    public boolean existsValueRestriction(String name){
        return ontoHash.valueRestrictionsHashtable.containsKey(name);
    }
    public Vector getValueRestriction(String name){
        return (Vector)ontoHash.valueRestrictionsHashtable.get(name);
    }
    public Vector removeValueRestriction(String name){
        return (Vector)ontoHash.valueRestrictionsHashtable.remove(name);
    }
    public Vector addValueRestriction(String name, Vector restriction){
        return (Vector)ontoHash.valueRestrictionsHashtable.put(name, restriction);
    }
    
    public Hashtable getEntityTypesAndEntitiesHashtableFromMainDBHashtable(String entityTypeOrEntity) {
        return ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable(entityTypeOrEntity);
    }
    
    public Hashtable getChildrenEntities(DefaultMutableTreeNode node){
        return ontoHash.getChildrenEntities(node);
    }
    
    public Vector getChildrenVectorFromMainDBHashtable(String name, String entityOrEntityType){
        return ontoHash.getChildrenVectorFromMainDBHashtable(name, entityOrEntityType);
    }
    
    public void addEntityTypeInUserModelHashtable(String entityTypeName){
        profileHash.addEntityTypeInUserModelHashtable(entityTypeName);
    }
    
    public void addEntityInUserModelHashtable(String entity){
        profileHash.addEntityInUserModelHashtable(entity);
    }
    
    public boolean existsUser(String username){
        return profileHash.mainUsersHashtable.containsKey(username);
    }
    
    
    public void updateHashtable(String typeName,String microplanNumber,String propertyName,String attr, String lang,String value){
        ontoHash.updateHashtable(typeName, microplanNumber, propertyName, attr,  lang, value);
    }
    
    public Vector showValues(String name, String number, String field, String language) {
        return ontoHash.showValues( name,  number,  field,  language);
    }
    
    public void setBaseURI(String URI) {
        optionsHash.setBaseURI(URI);
        
    }
    
    public String getBaseURI() {
        return optionsHash.getBaseURI();
        
    }
    
    public Vector getUsersVectorFromMainUsersHashtable() {
        return profileHash.getUsersVectorFromMainUsersHashtable();
    }
    
    public void updateAppropriatenessValuesInMicroplanningOfFields(String usertype, String oldusertype, String action) {
        profileHash.updateAppropriatenessValuesInMicroplanningOfFields( usertype,  oldusertype,  action);
    }
    
    
    public void addUserInPropertiesHashtable(String name){
        ontoHash.addUserInPropertiesHashtable(name);
    }
    
    public void putUserOrRobotInMainUsersHashtable(String name, Object userOrRobot){
        profileHash.mainUsersHashtable.put(name, userOrRobot);
    }
    
    public boolean mainUserModelHashtableContainsProperty(String propName){
        return profileHash.mainUserModelHashtable.containsKey(propName);
    }
    public Hashtable putPropertyInMainUserModelHashtable(String propName, Hashtable property){
        return (Hashtable)profileHash.mainUserModelHashtable.put(propName, property);
    }
    public Hashtable getPropertyFromMainUserModelHashtable(String propName){
        return (Hashtable)profileHash.mainUserModelHashtable.get(propName);
    }
    public Vector getFullPathChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity) {
        return ontoHash.getFullPathChildrenVectorFromMainDBHashtable( nodename,  entityTypeOrEntity);
    }
    public boolean existsAnnotation(String annotation){
        return ontoHash.annotationPropertiesHashtable.containsKey(annotation);
    }
    public Vector addAnnotation(String name, Vector annotation){
        return (Vector)ontoHash.annotationPropertiesHashtable.put(name, annotation);
    }
    public Vector getAnnotation(String name){
        return (Vector)ontoHash.annotationPropertiesHashtable.get(name);
    }
    
    public Hashtable getNounsHashtable(){
        return (Hashtable)lexHash.mainLexiconHashtable.get("Nouns");
    }
    
    public Hashtable getVerbsHashtable(){
        return (Hashtable)lexHash.mainLexiconHashtable.get("Verbs");
    }
    public void createEntity(String parentNodeName, String createdNodeName) {
        ontoHash.createEntity( parentNodeName,  createdNodeName);
    }
    
    public void updateCreatedEntity(String parentNodeName, String createdNodeName) {
        ontoHash.updateCreatedEntity( parentNodeName,  createdNodeName);
    }
    
    public Enumeration getPropertyNames(){
        return ontoHash.propertiesHashtable.keys();
    }
    public Enumeration getProperties(){
        return ontoHash.propertiesHashtable.elements();
    }
    
    public Vector getUserModelling(String property, String entityOrType){
        return profileHash.getUserModelling(property, entityOrType);
    }
    
    public Hashtable showValues(String name, String language) {
        return lexHash.showValues(name, language);
    }
    public void removeEntityTypeOrEntityInUserModelHashtable(String nodename) {
        profileHash.removeEntityTypeOrEntityInUserModelHashtable(nodename) ;
    }
    
    public void updateExistingFieldsAfterRemovingANode(Vector deletedEntityTypes, Vector deletedEntities) {
        ontoHash.updateExistingFieldsAfterRemovingANode( deletedEntityTypes,  deletedEntities);
    }
    
    public void createSubType(String parentNodeName, String createdNodeName) {
        ontoHash.createSubType( parentNodeName,  createdNodeName) ;
    }
    
    public void renameEntityTypeOrEntityInUserModelHashtable(String oldname, String newname) {
        profileHash.renameEntityTypeOrEntityInUserModelHashtable( oldname,  newname);
    }
    
    public void updateLexiconEntryNoun(String name, String language, String attribute, String attributeValue) {
        lexHash.updateLexiconEntryNoun(name, language, attribute, attributeValue);
    }
    
    public void createRobotCharVectorAndHash() {
        gp.createRobotCharVectorAndHash();
    }
    public void createDefaultUpperVector() {
        ontoHash.createDefaultUpperVector();
    }
    public void createBasicEntityType(String parentNodeName ,String createdNodeName) {
        ontoHash.createBasicEntityType(parentNodeName, createdNodeName);
    }

    public void updateTemplateVector(String oldValue, String newValue) {
        Vector curVec = (Vector)getProperty(oldValue);
        if(curVec==null)
            curVec=(Vector)getProperty(newValue);
            TemplateVector temVec = (TemplateVector)curVec.elementAt(11);
            for(int h=0;h<temVec.size();h++){
                Hashtable values=(Hashtable)temVec.elementAt(h);
                Enumeration keys=values.keys();
                while(keys.hasMoreElements()){
                    String nextKey=keys.nextElement().toString();
                    Object nextOb=values.remove(nextKey);
                    String[] s=nextKey.split(":");
                    String reconstruction="";
                    for(int j=0;j<s.length;j++){
                        if(s[j].equals(oldValue))
                            s[j]=newValue;
                        if(j==0)
                            reconstruction=s[0];
                        else
                            reconstruction=reconstruction+":"+s[j];
                    }
                    values.put(reconstruction, nextOb);
                }
            }
           
    }
    
    public void writeStructureObjectToFile(ObjectOutputStream p) throws IOException {
        p.writeObject( (Hashtable) optionsHash.mainOptionsHashtable);
        p.writeObject( (Hashtable) ontoHash.mainDBHashtable);
        
        p.writeObject( (Hashtable) lexHash.mainLexiconHashtable);
        p.writeObject( (Hashtable) profileHash.mainUsersHashtable);
        p.writeObject( (Hashtable) profileHash.mainUserModelHashtable);
        
        p.writeObject( (Hashtable) ontoHash.propertiesHashtable);
        p.writeObject( (Hashtable) ontoHash.valueRestrictionsHashtable);
        p.writeObject( (Hashtable) ontoHash.equivalentClassesHashtable);
        p.writeObject( (Hashtable) ontoHash.superClassesHashtable);
        p.writeObject( (Hashtable) ontoHash.annotationPropertiesHashtable);
        
        p.writeObject(gp.robotCharVector);
        p.writeObject(gp.robotCharValuesHashtable);
    }
    
    public void readStrucutreObjectsFromFile(ObjectInputStream p) throws IOException, ClassNotFoundException {
        
        Object o1 = p.readObject();
        optionsHash.mainOptionsHashtable = (Hashtable) o1;
        Object o2 = p.readObject();
        ontoHash.mainDBHashtable = (Hashtable) o2;
        
        Object o3 = p.readObject();
        lexHash.mainLexiconHashtable = (Hashtable) o3;
        Object o4 = p.readObject();
        profileHash.mainUsersHashtable = (Hashtable) o4;
        Object o5 = p.readObject();
        profileHash.mainUserModelHashtable = (Hashtable) o5;
        
        
        
        Object o7 = p.readObject();
        ontoHash.propertiesHashtable= (Hashtable) o7;
        Object o8 = p.readObject();
        ontoHash.valueRestrictionsHashtable= (Hashtable) o8;
        
        
        
        Object o9 = p.readObject();
        ontoHash.equivalentClassesHashtable= (Hashtable) o9;
        
        Object o10 = p.readObject();
        ontoHash.superClassesHashtable= (Hashtable) o10;
        
        Object o11 = p.readObject();
        ontoHash.annotationPropertiesHashtable= (Hashtable) o11;
        
        Object o12 = p.readObject();
        gp.robotCharVector= (Vector) o12;
        
        Object o13 = p.readObject();
        gp.robotCharValuesHashtable= (Hashtable) o13;
        
    }
    
    public Vector getRobotsVectorFromUsersHashtable() {
        return profileHash.getRobotsVectorFromUsersHashtable();
    }
    public void addValuesFromHasValueRestrictions(String entityName, String Parent){
        ontoHash.addValuesFromHasValueRestrictions(entityName, Parent);
    }
    public Vector getFullPathParentsVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity) {
        return  ontoHash.getFullPathParentsVectorFromMainDBHashtable(nodename, entityTypeOrEntity);
    }
    
    public void updateChildrenEntitiesFieldColumn(String oldValue, String newValue, String fillerType) {
        ontoHash.updateChildrenEntitiesFieldColumn(oldValue, newValue, fillerType);
    }
    
    public void updateChildrenEntitiesFieldColumn(String oldValue, String newValue, String fillerType, DefaultMutableTreeNode node) {
        ontoHash.updateChildrenEntitiesFieldColumn(oldValue, newValue, fillerType, node);
    }
    public void renameHashtableField(String name, String oldfieldname, String newfieldname) {
        ontoHash.renameHashtableField(name, oldfieldname, newfieldname);
    }
    
    
    public void renameFieldInUserModelHashtable(String oldname, String newname) {
        profileHash.renameFieldInUserModelHashtable( oldname,  newname);
    }
    public void updateChildrenEntitiesFillerColumn(DefaultMutableTreeNode node, String oldValue, String newValue, String fieldName) {
        ontoHash.updateChildrenEntitiesFillerColumn( node,  oldValue,  newValue,  fieldName);
    }
    
    public void removeHashtableField(String name, String fieldname) {
        ontoHash.removeHashtableField(name, fieldname);
    }
    public String checkNameValidity(String nodeName) {
        return ontoHash.checkNameValidity(nodeName);
    }
  //  public Vector getExistingFieldnamesForEntityTypeAndChildren(DefaultMutableTreeNode entityType) {
  //      return ontoHash.getExistingFieldnamesForEntityTypeAndChildren(entityType);
  //  }
    public Hashtable getChildrenBasics(DefaultMutableTreeNode node) {
        return ontoHash.getChildrenBasics(node);
    }
    public Vector getAllOccurrences(String name){
        return ontoHash.getAllOccurrences(name);
    }
    public Vector getParents(String nodename){
        return ontoHash.getParents(nodename);
    }
    public void addRestriction(String type, String property, String entitytype, String value){
        ontoHash.addRestriction( type,  property,  entitytype,  value);
    }
    public Enumeration getUserNames(){
        return profileHash.getUserNames();
    }
    
    public Hashtable returnAllFieldsAndContainingEntityTypes() {
        return ontoHash.returnAllFieldsAndContainingEntityTypes();
    }
    public Vector getEquivalentClasses(String name){
        return (Vector) ontoHash.equivalentClassesHashtable.get(name);
    }
    
    public boolean existsEquivalentClasses(String name){
        return  ontoHash.equivalentClassesHashtable.containsKey(name);
    }
    
    public Vector removeEquivalentClasses(String name){
        return (Vector) ontoHash.equivalentClassesHashtable.remove(name);
    }
    
    public Vector addEquivalentClasses(String propName, Vector eqClass){
        return (Vector) ontoHash.equivalentClassesHashtable.put(propName, eqClass);
    }
    
    public Vector getSuperClasses(String name){
        return (Vector) ontoHash.superClassesHashtable.get(name);
    }
    
    public boolean existsSuperClasses(String name){
        return  ontoHash.superClassesHashtable.containsKey(name);
    }
    
    public Vector removeSuperClasses(String name){
        return (Vector) ontoHash.superClassesHashtable.remove(name);
    }
    
    public Vector addSuperClasses(String propName, Vector eqClass){
        return (Vector) ontoHash.superClassesHashtable.put(propName, eqClass);
    }
    
    public Set getRestrictionsKeySet() {
        return ontoHash.valueRestrictionsHashtable.keySet();
    }
    public void renameFieldInRestrictionsHashtable(String oldname, String newname){
        ontoHash.renameFieldInRestrictionsHashtable( oldname,  newname);
    }
    
    public void updateChildrenBasicTableVectors(int rowChanged) {
        ontoHash.updateChildrenBasicTableVectors(rowChanged);
    }
    
    public void insertRowInDataBaseTable(int rowPosition) {
        ontoHash.insertRowInDataBaseTable(rowPosition);
    }
    
    public void deletePropertyFromPropertiesHashtable(String propName){
        ontoHash.deletePropertyFromPropertiesHashtable(propName);
    }
    
    public boolean removeRowFromDataBaseTable(String fieldName, String fillerType, int rowPosition) {
        return ontoHash.removeRowFromDataBaseTable( fieldName,  fillerType, rowPosition);
    }
    
    public boolean removeRowFromDataBaseTable(String fieldName, String fillerType, int rowPosition, String node) {
        return ontoHash.removeRowFromDataBaseTable( fieldName,  fillerType, rowPosition,  node);
    }
    public Vector getUserModelValuesVector(String fieldname, String nodename, String username) {
        return profileHash.getUserModelValuesVector(fieldname, nodename, username);
    }
    public Vector getPropertyImportanceAndRepetitions(String property,String username){
        return ontoHash.getPropertyImportanceAndRepetitions( property, username);
    }
    public void updateImportanceOrRepetitionsForProperty(String property, String username, int valueID, String value) {
        ontoHash.updateImportanceOrRepetitionsForProperty(property, username, valueID, value);
    }
    public void updateUserOrRobotModelParameters(String fieldname, String nodename, String username, int valueID, String value) {
        profileHash.updateUserOrRobotModelParameters( fieldname,  nodename,  username,  valueID,  value);
    }
    public Enumeration restrictionKeys(){
        return ontoHash.valueRestrictionsHashtable.keys();
    }
    public Enumeration equivalentClassesHashtableKeys(){
        return ontoHash.equivalentClassesHashtable.keys();
    }
    
    public Enumeration superClassesHashtableKeys(){
        return ontoHash.superClassesHashtable.keys();
    }
    
               public Enumeration robotCharValuesHashtableKeys(){
        return gp.robotCharValuesHashtable.keys();
    } 
     public Enumeration robotCharValuesHashtableElements(){
        return gp.robotCharValuesHashtable.elements();
    }
    public Enumeration annotationPropertiesHashtableKeys(){
        return ontoHash.annotationPropertiesHashtable.keys();
    }
    public Enumeration annotationPropertiesHashtableElements(){
        return ontoHash.annotationPropertiesHashtable.elements();
    }
    public Enumeration restrictions(){
        return ontoHash.valueRestrictionsHashtable.elements();
    }
    
    public Set getPropertiesKeySet() {
        return ontoHash.propertiesHashtable.keySet();
    }
    public int getNoOfProperties(){
        return ontoHash.propertiesHashtable.size();
    }
    public int checkName(String nodeName) {
        return ontoHash.checkName(nodeName);
    }
    public void updateChildrenNounVectors(DefaultMutableTreeNode treeNode,Vector beforeVector, Vector newNounVector) {
        ontoHash.updateChildrenNounVectors( treeNode, beforeVector,  newNounVector);
    }
    public void insertExistingRowInDataBaseTable(FieldData fd, int rowPosition) {
        ontoHash.insertExistingRowInDataBaseTable( fd,  rowPosition);
    }
    public int checkLexiconName(String nodeName) {
        return lexHash.checkLexiconName(nodeName);
    }
    public int checkUsersName(String nodeName) {
        return profileHash.checkUsersName(nodeName);
    }
    
    public int checkRobotsName(String nodeName) {
        return profileHash.checkRobotsName(nodeName);
    }
    public void renameUser(String oldname, String newname) {
        profileHash.renameUser( oldname,  newname);
    }
    public void renameUserInUserOrRobotModelHashtable(String oldname, String newname) {
        profileHash.renameUserInUserOrRobotModelHashtable( oldname,  newname);
    }
    public void renameUserInAnnotationsHashtable(String oldName, String newName) {
        ontoHash.renameUserInAnnotationsHashtable( oldName,  newName);
    }
    public boolean setFunctional(String name){
        return ontoHash.setFunctional(name);
    }
    public void setInverseProperty(String propName, String inversePropName){
        ontoHash.setInverseProperty(propName, inversePropName);
    }
    public void setPropertyTransitive(String propName) {
        ontoHash.setPropertyTransitive(propName);
    }
    public void setPropertySymmetric(String propName) {
        ontoHash.setPropertySymmetric(propName);
    }
    public void setInverseFunctional(String propName) {
        ontoHash.setInverseFunctional(propName);
    }
    public void renameUserInPropertiesHashtable(String oldName, String newName){
        ontoHash.renameUserInPropertiesHashtable( oldName,  newName);
    }
    public void renameRobot(String oldname, String newname) {
        profileHash.renameRobot( oldname,  newname);
    }
    public void renameRobotInRobotCharVector(String old, String newname) {
        gp.renameRobotInRobotCharVector( old,  newname);
    }
    public void renameRobotInPropertiesHashtable(String oldName, String newName){
        ontoHash.renameRobotInPropertiesHashtable( oldName,  newName);
    }
    
    public void renameNodesGenericEntity(String oldname, String newname) {
        ontoHash.renameNodesGenericEntity( oldname,  newname);
    }
    public void updateExistingFieldsAfterRenamingANode(DefaultMutableTreeNode renamedNode, String newName) {
        ontoHash.updateExistingFieldsAfterRenamingANode( renamedNode,  newName);
    }
    public void updateChildrenTableVectorsWithNewParentName(String newName) {
        ontoHash.updateChildrenTableVectorsWithNewParentName(newName);
    }
    
    public void updateChildrenTableVectorsWithNewParentName(String newName, DefaultMutableTreeNode parent) {
        ontoHash.updateChildrenTableVectorsWithNewParentName(newName, parent);
    }
    
    public void updateChildrenTableVectorsWithNewParentName(String newName,String parent) {
        ontoHash.updateChildrenTableVectorsWithNewParentName(newName, parent);
    }
    public Enumeration getUserElements(){
        return profileHash.getUserElements();
    }
    
    public String getParentValuesVectorForOWLExport(String currentField, String usertype, String nodeName, int i) {
        return profileHash.getParentValuesVectorForOWLExport( currentField,  usertype,  nodeName,  i);
    }
    public Enumeration mainUserModelHashtableKeys(){
        return profileHash.mainUserModelHashtable.keys();
    }
    public int mainUserModelHashtableSize(){
        return profileHash.mainUserModelHashtable.size();
    }
    public Enumeration mainUserModelHashtableElements(){
        return profileHash.mainUserModelHashtable.elements();
    }
             public Enumeration mainOptionsHashtableKeys(){
        return optionsHash.mainOptionsHashtable.keys();
    }
    public Enumeration mainOptionsHashtableElements(){
        return optionsHash.mainOptionsHashtable.elements();
    }
     public String getParentTypeOfVectorForOWLExport(String usertype, String nodeName, int i) 
	{
         return profileHash.getParentTypeOfVectorForOWLExport(usertype, nodeName, i);
     }
     public Enumeration getRobotNames(){
         return profileHash.getRobotNames();
     }
     public Enumeration getRobotElements(){
          return profileHash.getRobotElements();
     }
     public Vector getRobotModelling(String property, String entityOrType){
         return profileHash.getRobotModelling(property, entityOrType);
     }
     public String getRobotsParentValuesVectorForOWLExport(String currentField, String usertype, String nodeName, int i) 
	{
         return profileHash.getRobotsParentValuesVectorForOWLExport( currentField,  usertype,  nodeName,  i);
     }
     public int getRobotCharVectorSize(){
         return gp.robotCharVector.size();
     }
     public Vector getRobotCharVectorElementAt(int i){
     return (Vector)gp.robotCharVector.elementAt(i);
     }

     public Vector getCharacteristic(String name){
         for(int i=0;i<gp.robotCharVector.size();i++){
             Vector nextChar=(Vector)gp.robotCharVector.elementAt(i);
             if(nextChar.elementAt(0).equals(name))
                 return nextChar;
         }
         return null;
     }

      public Object[] getRobotNamesToArray(){
         return profileHash.getRobotNamesToArray();
      }
       public Vector getValuesFromGreek2(String name, String number, String field, String language) {
           return ontoHash.getValuesFromGreek2(name, number, field, language);
       }
        public Vector getValuesFromEnglish2(String name, String number, String field, String language) {
            return ontoHash.getValuesFromEnglish2(name, number, field, language);
        }

         public Vector getValuesFromItalian2(String name, String number, String field, String language) {
            return ontoHash.getValuesFromItalian2(name, number, field, language);
        }

         public String getSpecialValueVerbFromItalian2(String name, String number, String field, String language) {
            return ontoHash.getSpecialValueVerbFromItalian2(name, number, field, language);
        }
                 public String getSpecialValueVerbFromGreek2(String name, String number, String field, String language) {
            return ontoHash.getSpecialValueVerbFromGreek2(name, number, field, language);
        }
         public String getSpecialValueVerbFromEnglish2(String name, String number, String field, String language) {
            return ontoHash.getSpecialValueVerbFromEnglish2(name, number, field, language);
        }
          public Hashtable showSpecialValues2(String name, String number, String field, String language) {
               return ontoHash.showSpecialValues2(name, number, field, language);
          }
          public Vector getNounsVectorFromMainLexiconHashtable() 
	{
              return lexHash.getNounsVectorFromMainLexiconHashtable();
          }
           public Vector getVerbsVectorFromMainLexiconHashtable() 
	{
              return lexHash.getVerbsVectorFromMainLexiconHashtable();
          }
           public Vector getChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity, Hashtable currentHashtable) {
               return ontoHash.getChildrenVectorFromMainDBHashtable(nodename, entityTypeOrEntity, currentHashtable);
           }
           
           public Vector getFullPathChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity, Hashtable allEntityTypes, Hashtable currentHash) {
               return ontoHash.getFullPathChildrenVectorFromMainDBHashtable( nodename,  entityTypeOrEntity,  allEntityTypes,  currentHash);
           }
            public ArrayList arrayListReturnAllEntityTypesContainingThisNoun(String noun, Hashtable allEntTypesHash) {
                return ontoHash.arrayListReturnAllEntityTypesContainingThisNoun(noun, allEntTypesHash);
            }
            public String returnAllEntityTypesContainingThisNoun(String noun) {
                return ontoHash.returnAllEntityTypesContainingThisNoun(noun);
            }
            public Vector getPropertiesForType(String nodeName) {
                return ontoHash.getPropertiesForType(nodeName);
            }
            public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username, Hashtable microplanningHashtable) 
	{
                return profileHash.getAppropriatenessValuesVector(fieldname, microplanNumber, username, microplanningHashtable);
            }

             public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username) 
	{
                return profileHash.getAppropriatenessValuesVector(fieldname, microplanNumber, username);
            }
              public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String nodename, String username) 
	{
                return profileHash.getAppropriatenessValuesVector(fieldname, microplanNumber, nodename, username);
            }
              public Vector getPServerAddressFromMainOptionsHashtable() 
	{
                  return optionsHash.getPServerAddressFromMainOptionsHashtable();
              }
              public void addPServerAddressToMainOptionsHashtable(String pserverIP, String pserverPort) 
	{
                  optionsHash.addPServerAddressToMainOptionsHashtable(pserverIP, pserverPort);
              }
              public void updateLexiconEntryVerb(String name, String language, String attribute, String attributeValue) 
	{
                  lexHash.updateLexiconEntryVerb(name, language, attribute, attributeValue);
              }
              public void updateLexiconEntryVerb(String name, String language, String attribute, Vector attributeValue) 
	{
                   lexHash.updateLexiconEntryVerb(name, language, attribute, attributeValue);
              }
              public Set getMainLexiconHashtableEntrySet(){
                 return lexHash.mainLexiconHashtable.entrySet();
              }
               public Vector getRobotsModelValuesVector(String fieldname, String nodename, String username) 
	{
                   return profileHash.getRobotsModelValuesVector(fieldname, nodename, username);
               }
                public void updateRobotsPreferenceForProperty(String property, String robotname, int valueID, String value) {
                        ontoHash.updateRobotsPreferenceForProperty(property, robotname, valueID, value);
                }
                 public void updateHashtableStoryInfo(String nodeName, String language, String storyString) {
                        ontoHash.updateHashtableStoryInfo(nodeName, language, storyString);
                 }
                 public Vector getRobotCharVector(){
                     return gp.robotCharVector;
                 }
                 public String getValueFromRobotCharValuesHash(String node, String username, int i) {
                        return gp.getValueFromRobotCharValuesHash(node, username, i);
                 }
                 public void setValueAtRobotCharValuesHash(String node, String username, int i, String value) {
                    gp.setValueAtRobotCharValuesHash(node, username, i, value);
                 }
                 public void renameAttributeInRobotCharValuesHashtable(String oldname, String newName) {
                    gp.renameAttributeInRobotCharValuesHashtable(oldname, newName);
                 }
                 public void removeAttributeFromHashtable(String name) {
                    gp.removeAttributeFromHashtable(name);
                 }
                 public JTable createDefaultTable(String tableType)
  {
                     return lexHash.createDefaultTable(tableType);
                 }
                  public void updateRobotInfo(String name, int attributeNumber, String attributeValue) 
	{
                      profileHash.updateRobotInfo(name, attributeNumber, attributeValue);
                  }
                  public void updateUserInfo(String name, int attributeNumber, String attributeValue) 
	{
                      profileHash.updateUserInfo(name, attributeNumber, attributeValue);
                  }
                  
                  public void removeUserInUserOrRobotModelHashtable(String username) 
	{
                      profileHash.removeUserInUserOrRobotModelHashtable(username);
                  }
                  public Vector getAllSupertypes(String type) {
                    return ontoHash.getAllSupertypes(type);
                  }
                   public void removeStoryFromHashtable(String nodeName, String fieldName) {
                        ontoHash.removeStoryFromHashtable(nodeName, fieldName);
                   }
                   
                    public void createStory(String nodeName, String fieldName) {
                        ontoHash.createStory(nodeName, fieldName);
                    }
                    
                    public void setPropertyDomain(Vector selectedDomains, String property) {
                        ontoHash.setPropertyDomain(selectedDomains, property);
                    }
                     public void addSubpropertiesToProperty(Vector selectedSubProp, String propName) {
                        ontoHash.addSubpropertiesToProperty(selectedSubProp, propName);
                     }
                     public void addSuperpropertiesToProperty(Vector selectedSuperProp, String propName) {
                      ontoHash.addSuperpropertiesToProperty(selectedSuperProp, propName);  
                     }
                     
                     public void setPropertyFillers(Vector fillers, Vector setVector) {
                        ontoHash.setPropertyFillers(fillers, setVector);
                     }
                    
     public String checkNameValidityNumberOnly(String nodeName) {
     return ontoHash.checkNameValidityNumberOnly(nodeName);
     }
     
      public boolean removeRowFromStoriesTable(String fieldName, int rowPosition) {
      return ontoHash.removeRowFromStoriesTable(fieldName, rowPosition);
      }
       public void insertRowInStoriesTable(int rowPosition) {
   ontoHash.insertRowInStoriesTable(rowPosition);
       }
       public Vector getExistingFieldnamesForStoryNode(DefaultMutableTreeNode storyNode) {
       return ontoHash.getExistingFieldnamesForStoryNode(storyNode);
       }

    Vector getPropertyForEntity(String entityName, int langID, String propertyName) {
        Vector langV=new Vector();
        NodeVector nv=(NodeVector)this.getEntityTypeOrEntity(entityName);
        if(langID==0)
            langV=nv.getIndependentFieldsVector();
        if(langID==1)
            langV=nv.getEnglishFieldsVector();
        if(langID==2)
            langV=nv.getGreekFieldsVector();
        if(langID==3)
            langV=nv.getItalianFieldsVector();
        for(int i=0;i<langV.size();i++){
            Vector nextProperty=(Vector)langV.elementAt(i);
            if (nextProperty.elementAt(0).toString().equals(propertyName))
                    return nextProperty;
        }
        return null;
    }
}
