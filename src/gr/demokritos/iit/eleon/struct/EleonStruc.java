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
    
    /**
     *This method deletes all the information stored in ELEON data structures
     */
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
    
    /**
     *  This method is invoked when a new field is added in entity type. The
     *name of the new field is by default new-user-field
     */
    public void createDefaultHashtableField(String entityType) {
        Vector usersVector =profileHash.getUsersVectorFromMainUsersHashtable();
        ontoHash.createDefaultHashtableField(entityType, usersVector);
    }
    
    /**
     *  This method returns a vector with the existing profile characteristics
     */
    public Vector getCharacteristics() {
        Vector result=new Vector();
        for(int i=0;i<gp.robotCharVector.size();i++){
            Vector nextChar=(Vector)gp.robotCharVector.elementAt(i);
            result.add(nextChar.elementAt(0));
        }
        return result;
    }
    
    /**
     *  This method returns the element of the mainUsersHashtable
     *for the given key (username). The object returned can be either
     *instance of Class User or instance of Class Robot
     */
    public Object getUser(String username){
        return profileHash.mainUsersHashtable.get(username);
    }
    
    /**
     *  This method returns a vector with the importance and repetition values
     *for the given property and the given robot
     */
    public Vector getPropertyRobotsImportanceAndRepetitions(String property, String robotname){
        Vector allRobotTypesVector = profileHash.getRobotsVectorFromUsersHashtable();
        return ontoHash.getPropertyRobotsImportanceAndRepetitions(property,robotname, allRobotTypesVector);
    }
    
    /**
     *This method adds the default preference value for each property for the given
     *robot
     */
    public void addRobotInPropertiesHashtable(String name) {
        Vector allRobotTypesVector = profileHash.getRobotsVectorFromUsersHashtable();
        ontoHash.addRobotInPropertiesHashtable(name, allRobotTypesVector);
    }
    
    /**
     *This method returns a vector with the characteristic values for the
     *given property(field), the given entity or entity-type(node) and
     *the given robot(robotname)
     */
    public Vector getRobotsCharValues(String field, String node, String robotname) {
        Object[] robots=profileHash.getRobotNamesToArray();
        return gp.getRobotsCharValues(field, node, robotname, robots);
    }
    
    /**
     *This method is used when the user defines for which profiles
     *each characteristic is going to be used. selectedItems contains
     *the profiles for which the selected characteristic is going to be used.
     */
    public void addChangesInRobotCharValuesHashtable(Vector selectedItems){
        Object[] robots=profileHash.getRobotNamesToArray();
        gp.addChangesInRobotCharValuesHashtable(selectedItems, robots);
    }
    
    
    /**
     *This method is used when a profile is deleted to remove this profile
     *from the characteristics vector
     */
    public void removeRobotFromRobotCharVector(String name) {
        
        Object[] r=profileHash.getRobotNamesToArray();
        gp.removeRobotFromRobotCharVector(name, r);
        
    }
    
    
    /**
     *This method creates a new user with the default attributes (max facts per sentence etc.)
     *and the default user modeling values
     */
    public void createDefaultUser(String name) {
        profileHash.mainUsersHashtable.put(name, new User("4", "10", "4", "male"));
        addUserInUserModelHashtable(name);
        ontoHash.addUserInPropertiesHashtable(name);
        //System.out.println("(createDefaultUser)---- " + name);
    }
    
    /**
     *This method creates a new profile with the default OCEAN attributes
     *and the default robot modeling values
     */
    public void createDefaultRobot(String name) {
        profileHash.mainUsersHashtable.put(name, new Robot("50", "50", "50", "50", "50"));
        
        addRobotInUserModelHashtable(name);
        gp.addRobotInRobotCharValuesHashtable(name);
        addRobotInPropertiesHashtable(name);
        //System.out.println("(createDefaultUser)---- " + name);
    }
    
    
    /**
     *This method sets the robotCharVector
     */
    public void setRobotCharVector(Vector charVector) {
        this.gp.robotCharVector=charVector;
    }
    
    public void updateIndependentLexiconHashtable(String usertype, String oldusertype, String action){
        Hashtable lexiconNounHashtable = (Hashtable)lexHash.mainLexiconHashtable.get("Nouns");
        Hashtable lexiconVerbHashtable = (Hashtable)lexHash.mainLexiconHashtable.get("Verbs");
        profileHash.updateIndependentLexiconHashtable( usertype,  oldusertype,  action,  lexiconNounHashtable,  lexiconVerbHashtable);
        
    }
    
    /**
     *This method removes the user with the given name
     *from mainUsersHashtable, userModelHashtable and from propertiesHashtable
     */
    public void removeUser(String name) {
        profileHash.removeUser(name);
        ontoHash.deleteUserFromPropertiesHashtable(name);
    }
    
    /**
     *This method removes the robot with the given name
     *from mainUsersHashtable, userModelHashtable and from propertiesHashtable
     */
    public void removeRobot(String name) {
        profileHash.removeRobot(name);
        ontoHash.deleteRobotFromPropertiesHashtable(name);
    }
    
    /**
     *This method is used when a new user is created to
     *add him to the userModelHashtable
     */
    public void addUserInUserModelHashtable(String newname) {
        Hashtable allEntityTypes = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Hashtable allEntities = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
        profileHash.addUserInUserModelHashtable(newname, allEntityTypes, allEntities);
        
    }
    
    /**
     *This method is used when a new robot is created to
     *add him to the userModelHashtable
     */
    public void addRobotInUserModelHashtable(String newname) {
        Hashtable allEntityTypes = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        Hashtable allEntities = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
        profileHash.addRobotInUserModelHashtable(newname, allEntityTypes, allEntities);
        
    }
    
    
    public void addFieldInUserModelHashtable(String fieldname, String entitytypename) {
        Vector allChildrenEntities = ontoHash.getFullPathChildrenVectorFromMainDBHashtable(entitytypename, "Entity+Generic");
        profileHash.addFieldInUserModelHashtable( fieldname,  entitytypename,  allChildrenEntities);
    }
    
    
    /**
     *This method creates a new lexicon noun with the given
     *name
     */
    public void createDefaultLexiconNoun(String name) {
        Vector usersVector = profileHash.getUsersVectorFromMainUsersHashtable();
        lexHash.createDefaultLexiconNoun(name, usersVector);
    }
    
    /**
     *This method creates a new lexicon verb with the given
     *name
     */
    public void createDefaultLexiconVerb(String name) {
        Vector usersVector = profileHash.getUsersVectorFromMainUsersHashtable();
        lexHash.createDefaultLexiconVerb(name, usersVector);
    }
    
    /**
     *This method renames the lexicon entry with name oldname
     *so that it has name newname
     */
    public void renameLexiconEntry(String oldname, String newname) {
        Hashtable entityTypesHashtable = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        lexHash.renameLexiconEntry(oldname, newname, ontoHash.mainDBHashtable, entityTypesHashtable);
    }
    
    /**
     *This method removes the lexicon entry with
     *the given name
     */
    public void removeLexiconEntry(String name){
        Hashtable entityTypesHashtable = ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        lexHash.removeLexiconEntry(name, ontoHash.mainDBHashtable, entityTypesHashtable);
    }
    
    /**
     *This method returns the element of the mainDBHashtable
     *which corresponds to the key name
     */
    public NodeVector getEntityTypeOrEntity(String name){
        return (NodeVector)ontoHash.mainDBHashtable.get(name);
    }
    
    /**
     *This method inserts a new element(entityTypeOrEntity) to the mainDBHashtable
     *for the given key (name)
     */
    public void putEntityTypeOrEntityToDB(String name, NodeVector entityTypeOrEntity){
        ontoHash.mainDBHashtable.put(name, entityTypeOrEntity);
    }
    
    /**
     *This method returns the element of the propertiesHashtable
     *which corresponds to the key propName
     */
    public PropertiesHashtableRecord getProperty(String propName){
        return (PropertiesHashtableRecord) ontoHash.propertiesHashtable.get(propName);
    }
    
    /**
     *This method returns true if the key propName
     *exists in propertiesHashtable. Otherwise it returns false
     */
    public boolean existsProperty(String propName){
        return  ontoHash.propertiesHashtable.containsKey(propName);
    }
    
    /**
     *This method removes the element of the propertiesHashtable
     *which corresponds to the key propName
     */
    public PropertiesHashtableRecord removeProperty(String propName){
        return (PropertiesHashtableRecord) ontoHash.propertiesHashtable.remove(propName);
    }
    
    /**
     *This method inserts a new element(prop) to the propertiesHashtable
     *for the given key (propName)
     */
    public PropertiesHashtableRecord addProperty(String propName, PropertiesHashtableRecord prop){
        return (PropertiesHashtableRecord) ontoHash.propertiesHashtable.put(propName, prop);
    }
    
    /**
     *If the string given contains the substring _occur
     *this method returns the string without the substring
     *_occur and everything after that. Otherwise it returns
     *the string given
     */
    public String nameWithoutOccur(String name){
        return ontoHash.nameWithoutOccur(name);
    }
    
    /**
     *This method removes the element of the mainDBHashtable
     *which corresponds to the key name
     */
    public NodeVector removeEntityTypeOrEntityFromDB(String name){
        return (NodeVector)ontoHash.mainDBHashtable.remove(name);
    }
    
    /**
     *This method returns true if the key name
     *exists in mainDBHashtable. Otherwise it returns false
     */
    public boolean mainDBcontainsEntityOrEntityType(String name){
        return ontoHash.mainDBHashtable.containsKey(name);
    }
    
    /**
     *This method initializes the data structures. It creates the basic-entity-types
     *and the Data-types in the hierarchy and a default user and a default profile
     */
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
    
    /**
     *This method returns true if the key name
     *exists in valueRestrictionsHashtable. Otherwise it returns false
     */
    public boolean existsValueRestriction(String name){
        return ontoHash.valueRestrictionsHashtable.containsKey(name);
    }
    
    /**
     *This method returns the element of the valueRestrictionsHashtable
     *which corresponds to the key name
     */
    public Vector getValueRestriction(String name){
        return (Vector)ontoHash.valueRestrictionsHashtable.get(name);
    }
    
    /**
     *This method removes the element of the valueRestrictionsHashtable
     *which corresponds to the key name
     */
    public Vector removeValueRestriction(String name){
        return (Vector)ontoHash.valueRestrictionsHashtable.remove(name);
    }
    
    /**
     *This method inserts a new element(restriction) to the valueRestrictionsHashtable
     *for the given key (name)
     */
    public Vector addValueRestriction(String name, Vector restriction){
        return (Vector)ontoHash.valueRestrictionsHashtable.put(name, restriction);
    }
    
    /**
     * If the string entityTypeOrEntity equals "Entity type", this method returns
     * a hashtable of all entity types and their parents. If the string
     * entityTypeOrEntity equals "Entity", this method returns
     * a hashtable of all entities and their parents.
     */
    public Hashtable getEntityTypesAndEntitiesHashtableFromMainDBHashtable(String entityTypeOrEntity) {
        return ontoHash.getEntityTypesAndEntitiesHashtableFromMainDBHashtable(entityTypeOrEntity);
    }
    
    /**
     *This method returns the entities which are children of the given node
     */
    public Hashtable getChildrenEntities(DefaultMutableTreeNode node){
        return ontoHash.getChildrenEntities(node);
    }
    
    /**
     *  Returns a vector of all FIRST children (sorted in alphabetical order) currently saved
     *  for a particular entity type node (nodename). If the string entityTypeOrEntity equals "Entity type",
     *  the children returned are entity types. If the string entityTypeOrEntity equals "Entity",
     *  the children returned are entities
     */
    public Vector getChildrenVectorFromMainDBHashtable(String name, String entityOrEntityType){
        return ontoHash.getChildrenVectorFromMainDBHashtable(name, entityOrEntityType);
    }
    
    /**
     *This method creates the default user modeling values
     *when a new entity type with name entityTypeName is created
     */
    public void addEntityTypeInUserModelHashtable(String entityTypeName){
        profileHash.addEntityTypeInUserModelHashtable(entityTypeName);
    }
    
    /**
     *This method creates the default user modeling values
     *when a new entity with name entity is created
     */
    public void addEntityInUserModelHashtable(String entity){
        profileHash.addEntityInUserModelHashtable(entity);
    }
    
    /**
     * This method returns true if there exists
     * a user with the given username.
     * Otherwise it returns false
     */
    public boolean existsUser(String username){
        return profileHash.mainUsersHashtable.containsKey(username);
    }
    
    /* This method updates the microplanning hashtable for an entity-type (typeName),
     *  field (propertyName), attribute (attr), language (lang),
     *  storing the attribute value (value)
     */
    public void updateHashtable(String typeName, String microplanNumber, String propertyName, String attr, String lang, String value){
        ontoHash.updateHashtable(typeName, microplanNumber, propertyName, attr,  lang, value);
    }
    
    /**
     *  The method that gets the values from the entity type (name) hashtable,
     *  for a specific field (field) and shows them.
     *  It is displayed on the micro-planning space for the specific language (language)
     */
    public Vector showValues(String name, String number, String field, String language) {
        return ontoHash.showValues( name,  number,  field,  language);
    }
    
    /**
     *  This method sets the base URI of the ontology
     */
    public void setBaseURI(String URI) {
        optionsHash.setBaseURI(URI);
    }
    
    /**
     *  This method gets the base URI of the ontology
     */
    public String getBaseURI() {
        return optionsHash.getBaseURI();
        
    }
    
    /**
     *  Returns a vector of all *users* (sorted in alphabetical order) currently saved
     */
    public Vector getUsersVectorFromMainUsersHashtable() {
        return profileHash.getUsersVectorFromMainUsersHashtable();
    }
    
    /**
     * this method updates the user modelling values of microplans when a user is renamed (action equals RENAME),
     *deleted (action equals REMOVE) or added (action equals ADD)
     */
    public void updateAppropriatenessValuesInMicroplanningOfFields(String usertype, String oldusertype, String action) {
        profileHash.updateAppropriatenessValuesInMicroplanningOfFields( usertype,  oldusertype,  action);
    }
    
    /**
     * this method adds the default user modeling values for the given user
     *for  property in the properties hashtable
     */
    public void addUserInPropertiesHashtable(String name){
        ontoHash.addUserInPropertiesHashtable(name);
    }
    
    /**
     * this method adds the given object (userOrRobot) as an element
     *in the mainUsersHashtable for the key name
     */
    public void putUserOrRobotInMainUsersHashtable(String name, Object userOrRobot){
        profileHash.mainUsersHashtable.put(name, userOrRobot);
    }
    
     /**
     *This method returns true if the key propName
     *exists in mainUserModelHashtable. Otherwise it returns false
     */
    public boolean mainUserModelHashtableContainsProperty(String propName){
        return profileHash.mainUserModelHashtable.containsKey(propName);
    }
    
    /**
     * this method adds the given object (property) as an element
     *in the mainUserModelHashtable for the key propName
     */
    public Hashtable putPropertyInMainUserModelHashtable(String propName, Hashtable property){
        return (Hashtable)profileHash.mainUserModelHashtable.put(propName, property);
    }
    public Hashtable getPropertyFromMainUserModelHashtable(String propName){
        return (Hashtable)profileHash.mainUserModelHashtable.get(propName);
    }
    
    /**
     *  Returns a vector of all children (sorted in alphabetical order) currently saved
     *  for a particular entity type node (nodename). If the string entityTypeOrEntity equals "Entity type",
     *  the children returned are entity types. If the string entityTypeOrEntity equals "Entity",
     *  the children returned are entities
     */
    public Vector getFullPathChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity) {
        return ontoHash.getFullPathChildrenVectorFromMainDBHashtable( nodename,  entityTypeOrEntity);
    }
    
    /**
     *This method returns true if the key annotation
     *exists in annotationPropertiesHashtable. Otherwise it returns false
     */
    public boolean existsAnnotation(String annotation){
        return ontoHash.annotationPropertiesHashtable.containsKey(annotation);
    }
    
    /**
     * this method adds the given object (annotation) as an element
     *in the annotationPropertiesHashtable for the key name
     */
    public Vector addAnnotation(String name, Vector annotation){
        return (Vector)ontoHash.annotationPropertiesHashtable.put(name, annotation);
    }
    public Vector getAnnotation(String name){
        return (Vector)ontoHash.annotationPropertiesHashtable.get(name);
    }
    
     /**
     * This method returns the hashtable 
      *with all the nouns af the lexicon
     */
    public Hashtable getNounsHashtable(){
        return (Hashtable)lexHash.mainLexiconHashtable.get("Nouns");
    }
    
     /**
     * This method returns the hashtable 
      *with all the verbs af the lexicon
     */
    public Hashtable getVerbsHashtable(){
        return (Hashtable)lexHash.mainLexiconHashtable.get("Verbs");
    }
    
     /**
     * This method creates an entity with name createdNodeName 
      *under the entity type with name parentNodeName
     */
    public void createEntity(String parentNodeName, String createdNodeName) {
        ontoHash.createEntity( parentNodeName,  createdNodeName);
    }
    
    public void updateCreatedEntity(String parentNodeName, String createdNodeName) {
        ontoHash.updateCreatedEntity( parentNodeName,  createdNodeName);
    }
    
     /**
     * This method returns an enumeration with all the property names
     */
    public Enumeration getPropertyNames(){
        return ontoHash.propertiesHashtable.keys();
    }
    
    /**
     * This method returns an enumeration with all the properties
     */
    public Enumeration getProperties(){
        return ontoHash.propertiesHashtable.elements();
    }
    
     /*returns a vector with 2 enumerations as elements. the first contains usernames
      *and the second the modelling values for the given property  and entity
      *or entity type*/
    public Vector getUserModelling(String property, String entityOrType){
        return profileHash.getUserModelling(property, entityOrType);
    }
    
    /**
	 *  The method that gets the values from the lexicon entry (name) hashtable,
	 *  for a specific language (language) and shows them.
	 *  The values are displayed on the lexicon right hand side space.
	 */
    public Hashtable showValues(String name, String language) {
        return lexHash.showValues(name, language);
    }
    
    public void removeEntityTypeOrEntityInUserModelHashtable(String nodename) {
        profileHash.removeEntityTypeOrEntityInUserModelHashtable(nodename) ;
    }
    
    /**
     *  Updates all fields of all remaining nodes that have relevant information of a deleted DataBase node
     */
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
    
     /**
     * This method initializes the robotCharValuesHashtable and robotCharVector
     */ 
    public void createRobotCharVectorAndHash() {
        gp.createRobotCharVectorAndHash();
    }
    
    /**
     * This method initializes the UpperVector with the upper model types of Data Base 
     *in mainDBHashtable
     */ 
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
    
    /**
     * This method writes the data structures in the
     *ObjectOutputStream
     */
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
    
    /**
     * This method reads the data structures from the
     *ObjectInputStream
     */
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
    
      /**
     * This method returns an enumeration with the existing user names
     */
    public Enumeration getUserNames(){
        return profileHash.getUserNames();
    }
    
     /**
     *  Returns a Hashtable with:
     *  keys:   all fields in mainDBHashtable
     *  values: their owner entity-types
     */
    public Hashtable returnAllFieldsAndContainingEntityTypes() {
        return ontoHash.returnAllFieldsAndContainingEntityTypes();
    }
    public Vector getEquivalentClasses(String name){
        return (Vector) ontoHash.equivalentClassesHashtable.get(name);
    }
    
    /**
     *This method returns true if the key name
     *exists in equivalentClassesHashtable. Otherwise it returns false
     */
    public boolean existsEquivalentClasses(String name){
        return  ontoHash.equivalentClassesHashtable.containsKey(name);
    }
    
    /**
     *This method removes the element of the equivalentClassesHashtable
     *which corresponds to the key name
     */
    public Vector removeEquivalentClasses(String name){
        return (Vector) ontoHash.equivalentClassesHashtable.remove(name);
    }
    
    /**
     * this method adds the given object (eqClass) as an element
     *in the equivalentClassesHashtable for the key name
     */
    public Vector addEquivalentClasses(String name, Vector eqClass){
        return (Vector) ontoHash.equivalentClassesHashtable.put(name, eqClass);
    }
    
    public Vector getSuperClasses(String name){
        return (Vector) ontoHash.superClassesHashtable.get(name);
    }
    
    /**
     *This method returns true if the key name
     *exists in superClassesHashtable. Otherwise it returns false
     */
    public boolean existsSuperClasses(String name){
        return  ontoHash.superClassesHashtable.containsKey(name);
    }
    
    /**
     *This method removes the element of the superClassesHashtable
     *which corresponds to the key name
     */
    public Vector removeSuperClasses(String name){
        return (Vector) ontoHash.superClassesHashtable.remove(name);
    }
    
    /**
     * this method adds the given object (sClass) as an element
     *in the superClassesHashtable for the key name
     */
    public Vector addSuperClasses(String name, Vector sClass){
        return (Vector) ontoHash.superClassesHashtable.put(name, sClass);
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
    
    /**
	 *  This method is invoked from the UserModelDialog when a UserModelDialog.UserModelPanel is created
	 *  for each user (username) in a field (fieldname) of a node (nodename)
	 */
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
    
    /**
     * This method sets the property with the 
     *given name functional
     */
    public boolean setFunctional(String name){
        return ontoHash.setFunctional(name);
    }
    
    /**
     * This method sets the inverse property of the property 
     *with name propName to be the property with name inversePropName
     */
    public void setInverseProperty(String propName, String inversePropName){
        ontoHash.setInverseProperty(propName, inversePropName);
    }
    
     /**
     * This method sets the property with the 
     *given name transitive
     */
    public void setPropertyTransitive(String propName) {
        ontoHash.setPropertyTransitive(propName);
    }
    
     /**
     * This method sets the property with the 
     *given name symmetric
     */
    public void setPropertySymmetric(String propName) {
        ontoHash.setPropertySymmetric(propName);
    }
    
     /**
     * This method sets the property with the 
     *given name inverse functional
     */
    public void setInverseFunctional(String propName) {
        ontoHash.setInverseFunctional(propName);
    }
    
    public void renameUserInPropertiesHashtable(String oldName, String newName){
        ontoHash.renameUserInPropertiesHashtable( oldName,  newName);
    }
    
    
     /**
     * This method renames a robot in mainUsersHashtable and 
      *in mainUserModelHashtable
     */        
    public void renameRobot(String oldname, String newname) {
        profileHash.renameRobot( oldname,  newname);
    }
    
     /**
     * This method renames a robot in robotCharVector
     */ 
    public void renameRobotInRobotCharVector(String old, String newname) {
        gp.renameRobotInRobotCharVector( old,  newname);
    }
   
     /**
     * This method renames a robot in propertiesHashtable
     */ 
    public void renameRobotInPropertiesHashtable(String oldName, String newName){
        ontoHash.renameRobotInPropertiesHashtable( oldName,  newName);
    }
    
    public void renameNodesGenericEntity(String oldname, String newname) {
        ontoHash.renameNodesGenericEntity( oldname,  newname);
    }
    
    /**
     *  Updates all fields of all nodes that have relevant information of a renamed DataBase node
     *  It is used in:  DataBasePanel (when renaming a node)
     *
     */
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
    
    /*returns an enumeration with max facts per sentence
      *facts per page, synthesizer voice and links per page for 
      *each user*/
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
    public String getParentTypeOfVectorForOWLExport(String usertype, String nodeName, int i) {
        return profileHash.getParentTypeOfVectorForOWLExport(usertype, nodeName, i);
    }
    public Enumeration getRobotNames(){
        return profileHash.getRobotNames();
    }
     /*returns an enumeration with with
      OCEAN values for each robot*/
    public Enumeration getRobotElements(){
        return profileHash.getRobotElements();
    }
    
    /*returns a vector with 2 enumerations as elements. the first contains profile names
      *and the second the modelling values for the given property  and entity
      *or entity type*/
    public Vector getRobotModelling(String property, String entityOrType){
        return profileHash.getRobotModelling(property, entityOrType);
    }
    public String getRobotsParentValuesVectorForOWLExport(String currentField, String usertype, String nodeName, int i) {
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
     /**
     *  The method that gets the values from "Greek" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
    public Vector getValuesFromGreek2(String name, String number, String field, String language) {
        return ontoHash.getValuesFromGreek2(name, number, field, language);
    }
     /**
     *  The method that gets the values from "English" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
    public Vector getValuesFromEnglish2(String name, String number, String field, String language) {
        return ontoHash.getValuesFromEnglish2(name, number, field, language);
    }
     /**
     *  The method that gets the values from "Italian" for a field (field) of
     *  an entity type (name) and adds them to the current language (language) field
     */
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
    
    /**
	 *  Returns a vector of all *nouns* (sorted in alphabetical order) currently saved
	 */    
    public Vector getNounsVectorFromMainLexiconHashtable() {
        return lexHash.getNounsVectorFromMainLexiconHashtable();
    }
    
    /**
	 *  Returns a vector of all *verbs* (sorted in alphabetical order) currently saved
	 */
    public Vector getVerbsVectorFromMainLexiconHashtable() {
        return lexHash.getVerbsVectorFromMainLexiconHashtable();
    }
    
    /**
     * Returns a vector of all FIRST children (sorted in
     *  alphabetical order) currently saved for a particular
     *  entity type node (nodename). If the string 
     * entityTypeOrEntity equals "Entity type" it returns 
     *children which are entity types, if entityTypeOrEntity equals
     *"Entity", it returns entities
     */
    public Vector getChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity, Hashtable currentHashtable) {
        return ontoHash.getChildrenVectorFromMainDBHashtable(nodename, entityTypeOrEntity, currentHashtable);
    }
    
     /**
     *  Returns a vector of all children for an entity-type. If the string 
     * entityTypeOrEntity equals "Entity type" it returns 
     *children which are entity types, if entityTypeOrEntity equals
     *"Entity", it returns entities
     */
    public Vector getFullPathChildrenVectorFromMainDBHashtable(String nodename, String entityTypeOrEntity, Hashtable allEntityTypes, Hashtable currentHash) {
        return ontoHash.getFullPathChildrenVectorFromMainDBHashtable( nodename,  entityTypeOrEntity,  allEntityTypes,  currentHash);
    }
    
    public ArrayList arrayListReturnAllEntityTypesContainingThisNoun(String noun, Hashtable allEntTypesHash) {
        return ontoHash.arrayListReturnAllEntityTypesContainingThisNoun(noun, allEntTypesHash);
    }
    public String returnAllEntityTypesContainingThisNoun(String noun) {
        return ontoHash.returnAllEntityTypesContainingThisNoun(noun);
    }
    
    /*returns all the properties of a type along with the filler types
     *for each property. The returned vector is the data for the
     *databasetable model*/
    public Vector getPropertiesForType(String nodeName) {
        return ontoHash.getPropertiesForType(nodeName);
    }
    public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username, Hashtable microplanningHashtable) {
        return profileHash.getAppropriatenessValuesVector(fieldname, microplanNumber, username, microplanningHashtable);
    }
    
    public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username) {
        return profileHash.getAppropriatenessValuesVector(fieldname, microplanNumber, username);
    }
    public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String nodename, String username) {
        return profileHash.getAppropriatenessValuesVector(fieldname, microplanNumber, nodename, username);
    }
    
    /* get the selected pserver address to mainOptionsHashtable for the specific doamin */
    public Vector getPServerAddressFromMainOptionsHashtable() {
        return optionsHash.getPServerAddressFromMainOptionsHashtable();
    }
    
    /* Add or replace the selected pserver address to mainOptionsHashtable  */
    public void addPServerAddressToMainOptionsHashtable(String pserverIP, String pserverPort) {
        optionsHash.addPServerAddressToMainOptionsHashtable(pserverIP, pserverPort);
    }
    
    /**
	 *  This method updates the hashtable for a lexicon entry (name), language (language), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
    public void updateLexiconEntryVerb(String name, String language, String attribute, String attributeValue) {
        lexHash.updateLexiconEntryVerb(name, language, attribute, attributeValue);
    }
    /**
	 *  This method updates the hashtable for a lexicon entry (name), language (language), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
    public void updateLexiconEntryVerb(String name, String language, String attribute, Vector attributeValue) {
        lexHash.updateLexiconEntryVerb(name, language, attribute, attributeValue);
    }
    
    /**
	 *  This method returns the data from the mainLexiconHashtable as a Set
	 */
    public Set getMainLexiconHashtableEntrySet(){
        return lexHash.mainLexiconHashtable.entrySet();
    }
    public Vector getRobotsModelValuesVector(String fieldname, String nodename, String username) {
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
    public JTable createDefaultTable(String tableType) {
        return lexHash.createDefaultTable(tableType);
    }
    public void updateRobotInfo(String name, int attributeNumber, String attributeValue) {
        profileHash.updateRobotInfo(name, attributeNumber, attributeValue);
    }
    public void updateUserInfo(String name, int attributeNumber, String attributeValue) {
        profileHash.updateUserInfo(name, attributeNumber, attributeValue);
    }
    
     /**
     * this method removes the user or robot from the 
      *mainUserModelHashtable
     */
    public void removeUserInUserOrRobotModelHashtable(String username) {
        profileHash.removeUserInUserOrRobotModelHashtable(username);
    }
    
     /**
     * this method returns a vector with all the entity-types 
      *which have as child the entity type with the given name(type)
     */
    public Vector getAllSupertypes(String type) {
        return ontoHash.getAllSupertypes(type);
    }
    
    public void removeStoryFromHashtable(String nodeName, String fieldName) {
        ontoHash.removeStoryFromHashtable(nodeName, fieldName);
    }
    
    public void createStory(String nodeName, String fieldName) {
        ontoHash.createStory(nodeName, fieldName);
    }
    
    /*sets the types containes in selectedDomains as the domain of the given property*/
    public void setPropertyDomain(Vector selectedDomains, String property) {
        ontoHash.setPropertyDomain(selectedDomains, property);
    }
    
    /*adds the property names contained in selectedSubProp as subproperties of 
     the property with name propName*/
    public void addSubpropertiesToProperty(Vector selectedSubProp, String propName) {
        ontoHash.addSubpropertiesToProperty(selectedSubProp, propName);
    }
    
    /*adds the property names contained in selectedSuperProp as superproperties of 
     the property with name propName*/
    public void addSuperpropertiesToProperty(Vector selectedSuperProp, String propName) {
        ontoHash.addSuperpropertiesToProperty(selectedSuperProp, propName);
    }
    
    /*It is used to modify the fillers of a property for the currently selected entity.
 *setVector contains the fillers before the operation*/
    public void setPropertyFillers(Vector fillers, Vector setVector) {
        ontoHash.setPropertyFillers(fillers, setVector);
    }
    
    /**
     * This method is used for accepting only numbers
     * Checks the input name for invalid characters.
     * It is used in: DDialog.DatePanel.getDate();
     */
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
    
     /**
     * this method returns a vector with the fillers of a property with name propertyName 
      *for a specific entity with name entityName. langID is 0 for language independent 
      *fields, 1 for english, 2 for greek and 3 for italian
     */
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
