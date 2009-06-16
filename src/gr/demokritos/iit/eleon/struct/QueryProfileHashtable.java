//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.authoring.*;
import gr.demokritos.iit.eleon.profiles.*;
import java.util.*;
import java.io.*;

public class QueryProfileHashtable extends Object implements Serializable 
{
	//Hashtable currentValues;
	//Hashtable currentTables;
	
	String hashtableEntry;
	String field;
	String currentValue;
	String currentSpecialValue;
	
	public Hashtable mainUsersHashtable;
	public Hashtable mainUserModelHashtable;
        public Hashtable mainRobotsModelHashtable;
	//public Hashtable mainUserModelStoryHashtable;
         //public Vector robotCharVector;

   // public Hashtable robotCharValuesHashtable;
	//Vector currentVector;
     public QueryProfileHashtable(){
         mainUserModelHashtable = new Hashtable();
         //mainUserModelStoryHashtable = new Hashtable();
         mainUsersHashtable = new Hashtable();
         
     }

     public QueryProfileHashtable(Hashtable mainUserModelH, Hashtable mainUserH){
         mainUserModelHashtable =  mainUserModelH;
         //mainUserModelStoryHashtable = new Hashtable();
         mainUsersHashtable = mainUserH;

     }


    
	/* Create the main users hashtable. (for initialisation purposes only) */

	public void createMainUsersHashtable() 
	{
		mainUsersHashtable = new Hashtable();
		
	}
        
//        public void createRobotsHashtable() 
//	{
//		robotsHashtable = new Hashtable();
//		createDefaultRobot("NewProfile");
//	}

	/**
	 * This method checks the mainUsersHashtable for name replication.
	 */
	
	public int checkUsersName(String nodeName) 
	{
		int check = 0;
		
		if (nodeName.indexOf(" ") >= 0) 
		{
			check = 3; // Empty space is not allowed!
		}
		else if (nodeName.equalsIgnoreCase(""))
		{
			check = 2; // Have to specify a node-name!
		}
		else
		{
			Enumeration enu1 = getUserNames();
			while (enu1.hasMoreElements()) 
			{
				String keyName = enu1.nextElement().toString();
				if (keyName.equalsIgnoreCase(nodeName))
				{
					check = 1;  // This node-name already exists!
				}
			}
		}
		return check;
	}
        
        public int checkRobotsName(String nodeName) 
	{
		int check = 0;
		
		if (nodeName.indexOf(" ") >= 0) 
		{
			check = 3; // Empty space is not allowed!
		}
		else if (nodeName.equalsIgnoreCase(""))
		{
			check = 2; // Have to specify a node-name!
		}
		else
		{
			Enumeration enu1 = getRobotNames();
			while (enu1.hasMoreElements()) 
			{
				String keyName = enu1.nextElement().toString();
				if (keyName.equalsIgnoreCase(nodeName))
				{
					check = 1;  // This node-name already exists!
				}
			}
		}
		return check;
	}


	


	public void updateIndependentLexiconHashtable(String usertype, String oldusertype, String action, Hashtable lexiconNounHashtable, Hashtable lexiconVerbHashtable) 
	{
		if (action.equalsIgnoreCase("ADD"))
		{
			
			Enumeration lexiconNounHashtableEnum = lexiconNounHashtable.keys();
			while (lexiconNounHashtableEnum.hasMoreElements())
			{
				String noun = lexiconNounHashtableEnum.nextElement().toString();
				Hashtable currentNounHashtable = (Hashtable)lexiconNounHashtable.get(noun);
				Hashtable currentNounIndependentHashtable = (Hashtable)currentNounHashtable.get("Independent");
				currentNounIndependentHashtable.put(usertype, "0");
			}
			
			Enumeration lexiconVerbHashtableEnum = lexiconVerbHashtable.keys();
			while (lexiconVerbHashtableEnum.hasMoreElements())
			{
				String verb = lexiconVerbHashtableEnum.nextElement().toString();
				Hashtable currentVerbHashtable = (Hashtable)lexiconVerbHashtable.get(verb);
				Hashtable currentVerbIndependentHashtable = (Hashtable)currentVerbHashtable.get("Independent");
				currentVerbIndependentHashtable.put(usertype, "0");
			}
		}
		else if (action.equalsIgnoreCase("RENAME"))
		{
			
			Enumeration lexiconNounHashtableEnum = lexiconNounHashtable.keys();
			while (lexiconNounHashtableEnum.hasMoreElements())
			{
				String noun = lexiconNounHashtableEnum.nextElement().toString();
				Hashtable currentNounHashtable = (Hashtable)lexiconNounHashtable.get(noun);
				Hashtable currentNounIndependentHashtable = (Hashtable)currentNounHashtable.get("Independent");
				String oldvalue = currentNounIndependentHashtable.get(oldusertype).toString();
				currentNounIndependentHashtable.put(usertype, oldvalue);
				currentNounIndependentHashtable.remove(oldusertype);
			}
			
			Enumeration lexiconVerbHashtableEnum = lexiconVerbHashtable.keys();
			while (lexiconVerbHashtableEnum.hasMoreElements())
			{
				String verb = lexiconVerbHashtableEnum.nextElement().toString();
				Hashtable currentVerbHashtable = (Hashtable)lexiconVerbHashtable.get(verb);
				Hashtable currentVerbIndependentHashtable = (Hashtable)currentVerbHashtable.get("Independent");
				String oldvalue = currentVerbIndependentHashtable.get(oldusertype).toString();
				currentVerbIndependentHashtable.put(usertype, oldvalue);
				currentVerbIndependentHashtable.remove(oldusertype);
			}
		}
		else if (action.equalsIgnoreCase("REMOVE"))
		{
			
			Enumeration lexiconNounHashtableEnum = lexiconNounHashtable.keys();
			while (lexiconNounHashtableEnum.hasMoreElements())
			{
				String noun = lexiconNounHashtableEnum.nextElement().toString();
				Hashtable currentNounHashtable = (Hashtable)lexiconNounHashtable.get(noun);
				Hashtable currentNounIndependentHashtable = (Hashtable)currentNounHashtable.get("Independent");
				currentNounIndependentHashtable.put(usertype, "0");
				currentNounIndependentHashtable.remove(usertype);
			}
			
			Enumeration lexiconVerbHashtableEnum = lexiconVerbHashtable.keys();
			while (lexiconVerbHashtableEnum.hasMoreElements())
			{
				String verb = lexiconVerbHashtableEnum.nextElement().toString();
				Hashtable currentVerbHashtable = (Hashtable)lexiconVerbHashtable.get(verb);
				Hashtable currentVerbIndependentHashtable = (Hashtable)currentVerbHashtable.get("Independent");
				currentVerbIndependentHashtable.put(usertype, "0");
				currentVerbIndependentHashtable.remove(usertype);
			}
		}
	}


	public void updateAppropriatenessValuesInMicroplanningOfFields(String usertype, String oldusertype, String action) 
	{
	  if (action.equalsIgnoreCase("ADD"))
	  {
	    Hashtable allFieldsAndContainingEntityTypesHashtable =  Mpiro.win.struc.returnAllFieldsAndContainingEntityTypes();
	    for (Enumeration k = allFieldsAndContainingEntityTypesHashtable.keys(),
	                     e = allFieldsAndContainingEntityTypesHashtable.elements();
	                     k.hasMoreElements(); )
			{
				String field = k.nextElement().toString();
				String entityTypeName = e.nextElement().toString();
				
				for (int i=1; i<6; i++)
				{
					String microplanNumber = new Integer(i).toString();
					
					Vector nodeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
					Hashtable currentHashtable = (Hashtable)nodeVector.get(5);
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "English", "0");
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "Italian", "0");
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "Greek", "0");
					
					//Mpiro.win.struc.updateHashtable(entityTypeName, microplanNumber, fieldname, user, "English", "0");
					//Mpiro.win.struc.updateHashtable(entityTypeName, microplanNumber, fieldname, user, "Italian", "0");
					//Mpiro.win.struc.updateHashtable(entityTypeName, microplanNumber, fieldname, user, "Greek", "0");
				}
			}
		}
    else if (action.equalsIgnoreCase("RENAME"))
    {
	    Hashtable allFieldsAndContainingEntityTypesHashtable =  Mpiro.win.struc.returnAllFieldsAndContainingEntityTypes();
	    for (Enumeration k = allFieldsAndContainingEntityTypesHashtable.keys(),
	                     e = allFieldsAndContainingEntityTypesHashtable.elements();
	                     k.hasMoreElements(); )
	    {
	      String field = k.nextElement().toString();
	      String entityTypeName = e.nextElement().toString();
	
	      for (int i=1; i<6; i++)
	      {
					String microplanNumber = new Integer(i).toString();
					
					Vector nodeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
					Hashtable currentHashtable = (Hashtable)nodeVector.get(5);
                                        String oldvalueEnglish = "NoMicroPlanning";
					String oldvalueItalian = "NoMicroPlanning";
					String oldvalueGreek ="NoMicroPlanning";
                                        try{
					oldvalueEnglish = currentHashtable.get(microplanNumber + ":" + field + ":" + oldusertype + ":" + "English").toString();
					oldvalueItalian = currentHashtable.get(microplanNumber + ":" + field + ":" + oldusertype + ":" + "Italian").toString();
					oldvalueGreek = currentHashtable.get(microplanNumber + ":" + field + ":" + oldusertype + ":" + "Greek").toString();
                                        }
                                        catch(NullPointerException r){
         
                                        }
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "English", oldvalueEnglish);
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "Italian", oldvalueItalian);
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "Greek", oldvalueGreek);
					currentHashtable.remove(microplanNumber + ":" + field + ":" + oldusertype + ":" + "English");
					currentHashtable.remove(microplanNumber + ":" + field + ":" + oldusertype + ":" + "Italian");
					currentHashtable.remove(microplanNumber + ":" + field + ":" + oldusertype + ":" + "Greek");
				}
			}
		}
    else if (action.equalsIgnoreCase("REMOVE"))
    {
	    Hashtable allFieldsAndContainingEntityTypesHashtable =  Mpiro.win.struc.returnAllFieldsAndContainingEntityTypes();
	    for (Enumeration k = allFieldsAndContainingEntityTypesHashtable.keys(),
	                     e = allFieldsAndContainingEntityTypesHashtable.elements();
	                     k.hasMoreElements(); )
			{
				String field = k.nextElement().toString();
				String entityTypeName = e.nextElement().toString();
				
				for (int i=1; i<6; i++)
				{
					String microplanNumber = new Integer(i).toString();
					
					Vector nodeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entityTypeName);
					Hashtable currentHashtable = (Hashtable)nodeVector.get(5);
					currentHashtable.remove(microplanNumber + ":" + field + ":" + usertype + ":" + "English");
					currentHashtable.remove(microplanNumber + ":" + field + ":" + usertype + ":" + "Italian");
					currentHashtable.remove(microplanNumber + ":" + field + ":" + usertype + ":" + "Greek");
				}
			}
		}
	}


	/**
	 *  This method updates the hashtable for a user entry (name), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
	public void updateUserInfo(String name, int attributeNumber, String attributeValue) 
	{
		User currentUser = (User)mainUsersHashtable.get(name);
		currentUser.updateElementAt(attributeValue, attributeNumber);
	}

        public void updateRobotInfo(String name, int attributeNumber, String attributeValue) 
	{
		Robot robot = (Robot)mainUsersHashtable.get(name);
		robot.updateElementAt(attributeValue, attributeNumber);
	}

	/**
	 *  This method is invoked when a user-type entry (oldname) is renamed (newname)
	 */
	public void renameUser(String oldname, String newname) 
	{
		//System.out.println("oldname =" + oldname);
		//System.out.println("newname =" + newname);
		
		if (mainUsersHashtable.containsKey(oldname))
		{
			Object oldnameVector = mainUsersHashtable.get(oldname);
			mainUsersHashtable.put(newname, oldnameVector);
			mainUsersHashtable.remove(oldname);
			renameUserInUserOrRobotModelHashtable(oldname, newname);
		}
		//System.out.println(mainLexiconHashtable.entrySet());
	}

        
        public void renameRobot(String oldname, String newname) 
	{
		//System.out.println("oldname =" + oldname);
		//System.out.println("newname =" + newname);
		
		if (mainUsersHashtable.containsKey(oldname))
		{
			Object oldnameVector = mainUsersHashtable.get(oldname);
			mainUsersHashtable.put(newname, oldnameVector);
			mainUsersHashtable.remove(oldname);
			renameUserInUserOrRobotModelHashtable(oldname, newname);
		}
		//System.out.println(mainLexiconHashtable.entrySet());
	}

	/**
	 *  This method is invoked when an entity-type (name) is removed
	 */
	public void removeUser(String name) 
	{
		mainUsersHashtable.remove(name);
		removeUserInUserOrRobotModelHashtable(name);
                
	}
        
        public void removeRobot(String name) 
	{
		mainUsersHashtable.remove(name);
		removeUserInUserOrRobotModelHashtable(name);
                
	}


	/**
	 *  Returns a vector of all *users* (sorted in alphabetical order) currently saved
	 *  It is used when loading a new domain
	 */
	public Vector getUsersVectorFromMainUsersHashtable() 
	{
		Vector usersVector = new Vector();
		Enumeration enumer = getUserNames();
                
                
		while (enumer.hasMoreElements())
		{
			usersVector.addElement(enumer.nextElement().toString());
		}
		usersVector = QuickSort.quickSort(0, usersVector.size()-1, usersVector);
		return usersVector;
	}
        
        public Vector getRobotsVectorFromUsersHashtable() 
	{
		Vector usersVector = new Vector();
		Enumeration enumer = getRobotNames();
                
		while (enumer.hasMoreElements())
		{
			usersVector.addElement(enumer.nextElement().toString());
		}
		usersVector = QuickSort.quickSort(0, usersVector.size()-1, usersVector);
		return usersVector;
	}


	//////////////////////////////////////////////////////////////////////
	//  UserModel Hashtable methods for DataBase
	//////////////////////////////////////////////////////////////////////
	
	/** Create the main user-model hashtable. (for initialisation purposes only) */
	public void createMainUserModelHashtable() 
	{
		mainUserModelHashtable = new Hashtable();
	} // create�ainUserModelHashtable

//        public void createMainRobotsModelHashtable() 
//	{
//		mainRobotsModelHashtable = new Hashtable();
//	}
	/**
	 *  This method is invoked when a new user entry is created (newname)
	 */
	public void addUserInUserModelHashtable(String newname, Hashtable allEntityTypes, Hashtable allEntities) 
	{
		
		Enumeration mainUserModelHashtableEnumKeys = mainUserModelHashtable.keys();
		Enumeration mainUserModelHashtableEnumElements = mainUserModelHashtable.elements();
		while (mainUserModelHashtableEnumKeys.hasMoreElements())
		{
		  String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
		  Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtableEnumElements.nextElement();
		  Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
		  Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
		  while (fieldnameHashtableEnumKeys.hasMoreElements())
		  {
	      String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
	      Hashtable entitynameHashtable = (Hashtable)fieldnameHashtableEnumElements.nextElement();
	      if (allEntityTypes.containsKey(entityname))
	      {
	        entitynameHashtable.put(newname, new Vector());
	        Vector vec = (Vector)entitynameHashtable.get(newname);
	        vec.addElement("");
	        vec.addElement("");
	        vec.addElement("");
				}
        else if (allEntities.containsKey(entityname))
        {
	        entitynameHashtable.put(newname, new Vector());
	        Vector vec = (Vector)entitynameHashtable.get(newname);
	        vec.addElement("");
	        vec.addElement("");
	        vec.addElement("");
				}
				else 
				{
					System.out.println("(QueryUsersHashtable.addUserInUserModelHashtable)---- ALERT!!!!!");
				}
			}
		}
		//}
	}
        public void addRobotInUserModelHashtable(String newname, Hashtable allEntityTypes, Hashtable allEntities) 
	{
		
		Enumeration mainRobotsModelHashtableEnumKeys = mainUserModelHashtable.keys();
		Enumeration mainRobotsModelHashtableEnumElements = mainUserModelHashtable.elements();
		while (mainRobotsModelHashtableEnumKeys.hasMoreElements())
		{
		  String fieldname = mainRobotsModelHashtableEnumKeys.nextElement().toString();
		  Hashtable fieldnameHashtable = (Hashtable)mainRobotsModelHashtableEnumElements.nextElement();
		  Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
		  Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
		  while (fieldnameHashtableEnumKeys.hasMoreElements())
		  {
	      String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
	      Hashtable entitynameHashtable = (Hashtable)fieldnameHashtableEnumElements.nextElement();
	      if (allEntityTypes.containsKey(entityname))
	      {
	        entitynameHashtable.put(newname, new Vector());
	        Vector vec = (Vector)entitynameHashtable.get(newname);
	        vec.addElement("");
	       // vec.addElement("0");
	      //  vec.addElement("0");
				}
        else if (allEntities.containsKey(entityname))
        {
	        entitynameHashtable.put(newname, new Vector());
	        Vector vec = (Vector)entitynameHashtable.get(newname);
	        vec.addElement("");
	      ///  vec.addElement("");
	      //  vec.addElement("");
				}
				else 
				{
					System.out.println("(QueryUsersHashtable.addUserInUserModelHashtable)---- ALERT!!!!!");
				}
			}
		}
		//}
	}


	/**
	 *  This method is invoked when a user entry (oldname) is renamed (newname)
	 */
	public void renameUserInUserOrRobotModelHashtable(String oldname, String newname) 
	{
		if (!oldname.equalsIgnoreCase(newname))
		{
	    Enumeration mainUserModelHashtableEnumKeys = mainUserModelHashtable.keys();
	    Enumeration mainUserModelHashtableEnumElements = mainUserModelHashtable.elements();
	    while (mainUserModelHashtableEnumKeys.hasMoreElements())
	    {
	      String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
	      Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtableEnumElements.nextElement();
	      Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
	      Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
	      while (fieldnameHashtableEnumKeys.hasMoreElements())
	      {
					String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
					Hashtable entitynameHashtable = (Hashtable)fieldnameHashtableEnumElements.nextElement();
					if (entitynameHashtable.containsKey(oldname))
					{
						Vector oldnameVector = (Vector)entitynameHashtable.get(oldname);
						entitynameHashtable.put(newname, oldnameVector);
						entitynameHashtable.remove(oldname);
					}
				}
			}
		}
	}
        

	/**
	 *  This method is invoked when a new user entry is removed (username)
	 */
	public void removeUserInUserOrRobotModelHashtable(String username) 
	{
	  Enumeration mainUserModelHashtableEnumKeys = mainUserModelHashtable.keys();
	  Enumeration mainUserModelHashtableEnumElements = mainUserModelHashtable.elements();
	  while (mainUserModelHashtableEnumKeys.hasMoreElements())
	  {
			String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
			Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtableEnumElements.nextElement();
			Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
			Enumeration fieldnameHashtableEnumElements = fieldnameHashtable.elements();
			while (fieldnameHashtableEnumKeys.hasMoreElements())
			{
				String entityname = fieldnameHashtableEnumKeys.nextElement().toString();
				Hashtable entitynameHashtable = (Hashtable)fieldnameHashtableEnumElements.nextElement();
				if (entitynameHashtable.containsKey(username))
				{
					entitynameHashtable.remove(username);
				}
			}
		}
	}


	/**
	 *  This method is invoked when a new field entry is created (fieldname)
	 */
  public void addFieldInUserModelHashtable(String fieldname, String entitytypename, Vector allChildrenEntities) 
  {
		//Hashtable allEntityTypes = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		//Hashtable allEntities = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		//System.out.println("40");
		// get all children entities of the entity type that consequently contain the new field
		//Vector allChildrenEntities = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable(entitytypename, "Entity+Generic");
		//System.out.println("41");
              //  Hashtable fieldnameHashtable=new Hashtable();
               // try{
                 Hashtable   fieldnameHashtable=(Hashtable) mainUserModelHashtable.get(fieldname);
               // }
           //     catch(NullPointerException v){
                 if(fieldnameHashtable==null){
                    mainUserModelHashtable.put(fieldname, new Hashtable());
		 fieldnameHashtable = (Hashtable) mainUserModelHashtable.get(fieldname);
                }
		
		//System.out.println("42");
		// add the entity-type into the field
		fieldnameHashtable.put(entitytypename, new Hashtable());
		// add users into entity-type
		Vector allUsersVector = (Vector)getUsersVectorFromMainUsersHashtable();
		Enumeration allUsersVectorEnum = allUsersVector.elements();//System.out.println("43");
		while (allUsersVectorEnum.hasMoreElements())
		{
	    String user = allUsersVectorEnum.nextElement().toString();
	    Hashtable entitytypeHashtable = (Hashtable)fieldnameHashtable.get(entitytypename);
	    entitytypeHashtable.put(user, new Vector());
	    Vector vec = (Vector)entitytypeHashtable.get(user);
	    vec.addElement("3");
	    vec.addElement("3");
	    vec.addElement("1");
		}
              
                //add robots
               allUsersVector = (Vector)getRobotsVectorFromUsersHashtable();
	allUsersVectorEnum = allUsersVector.elements();//System.out.println("43");
		while (allUsersVectorEnum.hasMoreElements())
		{
	    String user = allUsersVectorEnum.nextElement().toString();
	    Hashtable entitytypeHashtable = (Hashtable)fieldnameHashtable.get(entitytypename);
	    entitytypeHashtable.put(user, new Vector());
	    Vector vec = (Vector)entitytypeHashtable.get(user);
	    vec.addElement("3");
	   // vec.addElement("3");
	   // vec.addElement("1");
		}
//System.out.println("44");
		// add all children entities into the field
		Enumeration allChildrenEntitiesEnum = allChildrenEntities.elements();
		while (allChildrenEntitiesEnum.hasMoreElements())
		{
			String entity = allChildrenEntitiesEnum.nextElement().toString();
                        if (entity.substring(0,entity.length()-1).endsWith("_occur")) 
                            entity=entity.substring(0,entity.length()-7);
			fieldnameHashtable.put(entity, new Hashtable());
			//System.out.println("45");
			// add users into entity
			Enumeration allUsersVectorEnum2 = allUsersVector.elements();
			while (allUsersVectorEnum2.hasMoreElements())
			{
				String user = allUsersVectorEnum2.nextElement().toString();
				Hashtable entityHashtable = (Hashtable)fieldnameHashtable.get(entity);
				entityHashtable.put(user, new Vector());
				Vector vec = (Vector)entityHashtable.get(user);
				vec.addElement("");
				vec.addElement("");
				vec.addElement("");
			}
                       allUsersVectorEnum2 = allUsersVector.elements();
			while (allUsersVectorEnum2.hasMoreElements())
			{
				String user = allUsersVectorEnum2.nextElement().toString();
				Hashtable entityHashtable = (Hashtable)fieldnameHashtable.get(entity);
				entityHashtable.put(user, new Vector());
				Vector vec = (Vector)entityHashtable.get(user);
				vec.addElement("");
			//	vec.addElement("");
				//vec.addElement("");
			}
		}
	}

  

	/**
	 *  This method is invoked when a field name (oldname) is renamed (newname)
	 */
	public void renameFieldInUserModelHashtable(String oldname, String newname) 
	{
		if (!oldname.equalsIgnoreCase(newname))
		{
			if (mainUserModelHashtable.containsKey(oldname))
			{
				Hashtable oldfieldHashtable = (Hashtable)mainUserModelHashtable.get(oldname);
				mainUserModelHashtable.put(newname, oldfieldHashtable);
				mainUserModelHashtable.remove(oldname);
			}
		}
	}


	/**
	 *  This method is invoked when a field name (fieldname) is removed
	 */
	public void removeFieldInUserModelHashtable(String fieldname) 
	{
		mainUserModelHashtable.remove(fieldname);
	}
//        public void removeFieldInRobotsModelHashtable(String fieldname) 
//	{
//		mainRobotsModelHashtable.remove(fieldname);
//	}


	/**
	 *  This method is invoked when a new entity-type is created (newname)
	 */
	public void addEntityTypeInUserModelHashtable(String entitytypename) 
	{
		Hashtable fieldnameHashtable = new Hashtable();
		if (!mainUserModelHashtable.containsKey("Subtype-of"))
		{
			mainUserModelHashtable.put("Subtype-of", new Hashtable());
		}
		fieldnameHashtable = (Hashtable)mainUserModelHashtable.get("Subtype-of");
		
		// add the entity-type into the field
		fieldnameHashtable.put(entitytypename, new Hashtable());
		// add users into entity-type
		Vector allUsersVector = (Vector)getUsersVectorFromMainUsersHashtable();
		Enumeration allUsersVectorEnum = allUsersVector.elements();
		while (allUsersVectorEnum.hasMoreElements())
		{
			String user = allUsersVectorEnum.nextElement().toString();
			Hashtable entitytypeHashtable = (Hashtable)fieldnameHashtable.get(entitytypename);
			entitytypeHashtable.put(user, new Vector());
			Vector vec = (Vector)entitytypeHashtable.get(user);
			vec.addElement("0");
			vec.addElement("0");
			vec.addElement("0");
		}
                
                 allUsersVector = (Vector)getRobotsVectorFromUsersHashtable();
		 allUsersVectorEnum = allUsersVector.elements();
		while (allUsersVectorEnum.hasMoreElements())
		{
			String user = allUsersVectorEnum.nextElement().toString();
			Hashtable entitytypeHashtable = (Hashtable)fieldnameHashtable.get(entitytypename);
			entitytypeHashtable.put(user, new Vector());
			Vector vec = (Vector)entitytypeHashtable.get(user);
			vec.addElement("0");
			//vec.addElement("0");
			//vec.addElement("0");
		}
	}


	/**
	 *  This method is invoked when a new entity is created (newname)
	 */
	public void addEntityInUserModelHashtable(String newname) 
	{
		if (!mainUserModelHashtable.containsKey("type"))
		{
			mainUserModelHashtable.put("type", new Hashtable());
		}

		// the "name" refers to both name and shortname fields of entities ONLY
		if (!mainUserModelHashtable.containsKey("name"))
		{
			mainUserModelHashtable.put("name", new Hashtable());
		}
		
		Hashtable allEntities = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		
		// find all the fields inherited from parent entity-type
		Vector allEntityFieldsVector = new Vector();
		String parentEntityType = allEntities.get(newname).toString();
		NodeVector nv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(parentEntityType);
		Vector databaseTable =(Vector) nv.elementAt(0);
              //  databaseTable =(Vector) databaseTable.elementAt(0);
		for (int i=8; i<databaseTable.size(); i++)
		{
			Vector rowVector = (Vector)databaseTable.elementAt(i);
			String fieldname = rowVector.firstElement().toString();
			allEntityFieldsVector.addElement(fieldname);
		}
		// add the "type" field
		allEntityFieldsVector.addElement("type");
		allEntityFieldsVector.addElement("name");
		
		// for each field
		Enumeration allEntityFieldsVectorEnum = allEntityFieldsVector.elements();
		while (allEntityFieldsVectorEnum.hasMoreElements())
		{
			String fieldname = allEntityFieldsVectorEnum.nextElement().toString();
			Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtable.get(fieldname);
			// add the new entity into the field
                       // System.out.println("userrrmodel"+fieldname+newname+fieldnameHashtable.toString());
                        try{
			fieldnameHashtable.put(newname, new Hashtable());
                        }
                        catch(NullPointerException exc){};
			Hashtable entityHashtable = (Hashtable)fieldnameHashtable.get(newname);
			
			// add the user model info into the entity
			Vector allUsersVector = (Vector)getUsersVectorFromMainUsersHashtable();
			Enumeration allUsersVectorEnum = allUsersVector.elements();
			while (allUsersVectorEnum.hasMoreElements())
			{
				String user = allUsersVectorEnum.nextElement().toString();
				entityHashtable.put(user, new Vector());
				Vector vec = (Vector)entityHashtable.get(user);
				vec.addElement("");
				vec.addElement("");
				vec.addElement("");
			}
                        
                        allUsersVector = (Vector)getRobotsVectorFromUsersHashtable();
			allUsersVectorEnum = allUsersVector.elements();
			while (allUsersVectorEnum.hasMoreElements())
			{
				String user = allUsersVectorEnum.nextElement().toString();
				entityHashtable.put(user, new Vector());
				Vector vec = (Vector)entityHashtable.get(user);
				vec.addElement("");
				//vec.addElement("");
				//vec.addElement("");
			}
		}
	}
        
        

	/**
	 *  This method is invoked when an entity-type or entity (oldname) is renamed (newname)
	 */
	public void renameEntityTypeOrEntityInUserModelHashtable(String oldname, String newname) 
	{
		if (!oldname.equals(newname))
		{
			Enumeration mainUserModelHashtableEnumKeys = mainUserModelHashtable.keys();
			Enumeration mainUserModelHashtableEnumElements = mainUserModelHashtable.elements();
			while (mainUserModelHashtableEnumKeys.hasMoreElements())
			{
				String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
				Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtableEnumElements.nextElement();
				if (fieldnameHashtable.containsKey(oldname))
				{
					Hashtable oldnameHashtable = (Hashtable)fieldnameHashtable.get(oldname);
					fieldnameHashtable.put(newname, oldnameHashtable);
					fieldnameHashtable.remove(oldname);
				}
			}
		}
	}
        

	/**
	 *  This method is invoked when a new entity-type or entity is removed (nodename)
	 */
  public void removeEntityTypeOrEntityInUserModelHashtable(String nodename) 
  {
    Enumeration mainUserModelHashtableEnumKeys = mainUserModelHashtable.keys();
    Enumeration mainUserModelHashtableEnumElements = mainUserModelHashtable.elements();
    while (mainUserModelHashtableEnumKeys.hasMoreElements())
    {
      String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
      Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtableEnumElements.nextElement();
      if (fieldnameHashtable.containsKey(nodename))
      {
      	fieldnameHashtable.remove(nodename);
      }
    }
  }
  
//   public void removeEntityTypeOrEntityInRobotsModelHashtable(String nodename) 
//  {
//    Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
//    Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
//    while (mainRobotsModelHashtableEnumKeys.hasMoreElements())
//    {
//      String fieldname = mainRobotsModelHashtableEnumKeys.nextElement().toString();
//      Hashtable fieldnameHashtable = (Hashtable)mainRobotsModelHashtableEnumElements.nextElement();
//      if (fieldnameHashtable.containsKey(nodename))
//      {
//      	fieldnameHashtable.remove(nodename);
//      }
//    }
//  }


	/**
	 *  This method is invoked when the user-modelling info (value), of a parameter (valueID)
	 *  for a user-type (username), for a field (fieldname), of a database node (nodename)
	 *  is changed
	 */
	public void updateUserOrRobotModelParameters(String fieldname, String nodename, String username, int valueID, String value) 
	{
	  Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtable.get(fieldname);
	  Hashtable nodenameHashtable = (Hashtable)fieldnameHashtable.get(nodename);
	  Vector usernameVector = (Vector)nodenameHashtable.get(username);
	  usernameVector.setElementAt(value, valueID);
	}


	/**
	 *  This method is invoked from the UserModelDialog when a UserModelDialog.UserModelPanel is created
	 *  for each user (username) in a field (fieldname) of a node (nodename)
	 */
	public Vector getUserModelValuesVector(String fieldname, String nodename, String username) 
	{
            Vector usernameVector=new Vector();
	  Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtable.get(fieldname);
         
          if(!fieldnameHashtable.containsKey(nodename))
	  fieldnameHashtable.put(nodename, new Hashtable());
          Hashtable nodenameHashtable = (Hashtable)fieldnameHashtable.get(nodename);
          if(!nodenameHashtable.containsKey(username)){
              //Vector tempVector=getParentValuesVector(fieldname, username, nodename);
            Vector  tempVector= new Vector();
             tempVector.add("");
             tempVector.add("");
                            tempVector.add("");
                            nodenameHashtable.put(username, tempVector);
                          return tempVector;
                            
          }
         
	  usernameVector = (Vector)nodenameHashtable.get(username);
       
          return usernameVector;
	}
        
        
        public Vector getRobotsModelValuesVector(String fieldname, String nodename, String username) 
	{
            Vector usernameVector=new Vector();
            if(!mainUserModelHashtable.containsKey(fieldname))
                mainUserModelHashtable.put(fieldname, new Hashtable());
	  Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtable.get(fieldname);
         
          if(!fieldnameHashtable.containsKey(nodename))
	  fieldnameHashtable.put(nodename, new Hashtable());
          Hashtable nodenameHashtable = (Hashtable)fieldnameHashtable.get(nodename);
          if(!nodenameHashtable.containsKey(username)){
              //Vector tempVector=getParentValuesVector(fieldname, username, nodename);
            Vector  tempVector= new Vector();
             tempVector.add("");
            // tempVector.add("");
               //             tempVector.add("");
                            nodenameHashtable.put(username, tempVector);
                          return tempVector;
                            
          }
         
	  usernameVector = (Vector)nodenameHashtable.get(username);
       
          return usernameVector;
	}

        
        public Vector getParentValuesVector(String currentField, String usertype, String nodeName) 
	{
		String containingEntityType = Mpiro.win.struc.getParents(nodeName).elementAt(0).toString();
                if(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
		Vector parentValuesVector = getUserModelValuesVector(currentField, containingEntityType, usertype);
		return parentValuesVector;
                }
                else{
                   return (Vector) (((Hashtable)((Vector)Mpiro.win.struc.getProperty(currentField)).elementAt(12)).get(usertype));
                }
	}
        
        public Vector getRobotsParentValuesVector(String currentField, String usertype, String nodeName) 
	{
		String containingEntityType = Mpiro.win.struc.getParents(nodeName).elementAt(0).toString();
                if(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
		Vector parentValuesVector = getRobotsModelValuesVector(currentField, containingEntityType, usertype);
		return parentValuesVector;
                }
                else{
                  return (Vector) (((Hashtable)((Vector)Mpiro.win.struc.getProperty(currentField)).elementAt(15)).get(usertype));
                }
	}

        
        
        public String getParentTypeOfVectorForOWLExport(String usertype, String nodeName, int i) 
	{
		String containingEntityType = Mpiro.win.struc.getParents(nodeName).elementAt(0).toString();
                
                while(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
                // Hashtable temp=   (Hashtable)mainUserModelHashtable.get("Subtype-of");
                String toReturn=((Vector)((Hashtable)((Hashtable)mainUserModelHashtable.get("Subtype-of")).get(containingEntityType)).get(usertype)).elementAt(i).toString();
                if (!toReturn.equals("")) return toReturn;
                containingEntityType = Mpiro.win.struc.getParents(containingEntityType).elementAt(0).toString();
                }
                
                return "0";
                
	}
        
        
       
         public String getParentValuesVectorForOWLExport(String currentField, String usertype, String nodeName, int i) 
	{
		String containingEntityType = Mpiro.win.struc.getParents(nodeName).elementAt(0).toString();
                
                while(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
                String toReturn=getUserModelValuesVector(currentField, containingEntityType, usertype).elementAt(i).toString();
                if (!toReturn.equals("")) return toReturn;
                System.out.println(containingEntityType);
                containingEntityType = Mpiro.win.struc.getParents(containingEntityType).elementAt(0).toString();
                }
                
                return ((Vector) (((Hashtable)((Vector)Mpiro.win.struc.getProperty(currentField)).elementAt(12)).get(usertype))).elementAt(i).toString();
                
	}
         
          public String getRobotsParentValuesVectorForOWLExport(String currentField, String usertype, String nodeName, int i) 
	{
		String containingEntityType = Mpiro.win.struc.getParents(nodeName).elementAt(0).toString();
                
                while(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
                String toReturn=getRobotsModelValuesVector(currentField, containingEntityType, usertype).elementAt(i).toString();
                if (!toReturn.equals("")) return toReturn;
                System.out.println(containingEntityType);
                containingEntityType = Mpiro.win.struc.getParents(containingEntityType).elementAt(0).toString();
                }
                
                if(!currentField.equals("ClassOrInd"))
                    return ((Vector) (((Hashtable)((Vector)Mpiro.win.struc.getProperty(currentField)).elementAt(15)).get(usertype))).elementAt(i).toString();
                    else
                        return ("0");
	}
	/**
	 *  This method is invoked from the UserModelMicroplanDialog when a UserModelMicroplanDialog.UserModelPanel is created
	 *  for each user (username) in a field (fieldname) of a microplan (microplanNumber) of a node (nodename)
	 */
           public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username) 
	{
              String nodename=((Vector)((Vector)Mpiro.win.struc.getProperty(fieldname)).elementAt(0)).elementAt(0).toString();
	  Vector nodenameVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(nodename);
	  Hashtable microplanningHashtable = (Hashtable)nodenameVector.get(5);
	
	  Vector appropriatenessVector = new Vector();
          String valueEnglish = "0";
					String valueItalian = "0";
					String valueGreek ="0";
                                        try{
					 valueEnglish = microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "English").toString();
	   valueItalian = microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "Italian").toString();
	   valueGreek = microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "Greek").toString();
                                        }
                                        catch(NullPointerException r){
          microplanningHashtable.put(microplanNumber + ":" + fieldname + ":" + username + ":" + "English","0");
          microplanningHashtable.put(microplanNumber + ":" + fieldname + ":" + username + ":" + "Italian","0");
          microplanningHashtable.put(microplanNumber + ":" + fieldname + ":" + username + ":" + "Greek","0");
                                        }
	 
	  appropriatenessVector.add(valueEnglish);
	  appropriatenessVector.add(valueItalian);
	  appropriatenessVector.add(valueGreek);
	
	  return appropriatenessVector;
	}
          
	public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String nodename, String username) 
	{
	  Vector nodenameVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(nodename);
	  Hashtable microplanningHashtable = (Hashtable)nodenameVector.get(5);
	
	  Vector appropriatenessVector = new Vector();
          String valueEnglish = "0";
					String valueItalian = "0";
					String valueGreek ="0";
                                        try{
					 valueEnglish = microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "English").toString();
	   valueItalian = microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "Italian").toString();
	   valueGreek = microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "Greek").toString();
                                        }
                                        catch(NullPointerException r){
          microplanningHashtable.put(microplanNumber + ":" + fieldname + ":" + username + ":" + "English","0");
          microplanningHashtable.put(microplanNumber + ":" + fieldname + ":" + username + ":" + "Italian","0");
          microplanningHashtable.put(microplanNumber + ":" + fieldname + ":" + username + ":" + "Greek","0");
                                        }
	 
	  appropriatenessVector.add(valueEnglish);
	  appropriatenessVector.add(valueItalian);
	  appropriatenessVector.add(valueGreek);
	
	  return appropriatenessVector;
	}

	/**
	 *  This method is invoked from ExportUtilsPServer and
	 *  ExportUtilsPEmulator when the appropriateness vales of the
	 *  microplanning expressions are being exported, the hashtable has
	 *  already been created (A.I. 12/02/02)
	 */
	public Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username, Hashtable microplanningHashtable) 
	{
		Vector appropriatenessVector = new Vector();
		if (microplanningHashtable != null && fieldname != null && username != null) 
		{
	    String valueEnglish = (String)microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "English");
	    String valueItalian = (String)microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "Italian");
	    String valueGreek = (String)microplanningHashtable.get(microplanNumber + ":" + fieldname + ":" + username + ":" + "Greek");
	    if (valueEnglish == null) { valueEnglish = "0"; }
	    if (valueItalian == null) { valueItalian = "0"; }
	    if (valueGreek == null) { valueGreek = "0"; }
	    appropriatenessVector.add(valueEnglish);
	    appropriatenessVector.add(valueItalian);
	    appropriatenessVector.add(valueGreek);
		}
		else 
		{
			System.err.println("fieldname " +fieldname+ " username " + username);
		}
		return appropriatenessVector;
	}





	//////////////////////////////////////////////////////////////////////
	//  UserModel Hashtable methods for Stories
	//////////////////////////////////////////////////////////////////////

	/** Create the main user-model hashtable. (for initialisation purposes only) */
	//public void createMainUserModelStoryHashtable() 
	//{
//		mainUserModelStoryHashtable = new Hashtable();
//	} 


	/**
	 *  This method is invoked when a new user entry is created (newname)
	 */
//	public void addUserInUserModelStoryHashtable(String newname) 
	//{
	  //if (mainUserModelHashtable.size() != 0)
	  //{
	  //Enumeration mainUserModelStoryHashtableEnumKeys = mainUserModelStoryHashtable.keys();
	  //Enumeration mainUserModelStoryHashtableEnumElements = mainUserModelStoryHashtable.elements();
//	  while (mainUserModelStoryHashtableEnumKeys.hasMoreElements())
//	  {
//	    // "owner" is entity-type or entity
//	    String owner = mainUserModelStoryHashtableEnumKeys.nextElement().toString();
//	    Hashtable ownerHashtable = (Hashtable)mainUserModelStoryHashtableEnumElements.nextElement();
//	    Enumeration ownerHashtableEnumKeys = ownerHashtable.keys();
//	    Enumeration ownerHashtableEnumElements = ownerHashtable.elements();
//	    while (ownerHashtableEnumKeys.hasMoreElements())
//	    {
//	      String storyname = ownerHashtableEnumKeys.nextElement().toString();
//	      Hashtable storynameHashtable = (Hashtable)ownerHashtableEnumElements.nextElement();
//	      storynameHashtable.put(newname, new Vector());
//	      Vector vec = (Vector)storynameHashtable.get(newname);
//	      vec.addElement("0");
//	      vec.addElement("0");
//	      vec.addElement("0");
//			}
//		}
//    //}
//	}


	/**
	 *  This method is invoked when a user entry (oldname) is renamed (newname)
	 */
//	public void renameUserInUserModelStoryHashtable(String oldname, String newname) 
//	{
//	  if (!oldname.equalsIgnoreCase(newname))
//	  {
//      Enumeration mainUserModelStoryHashtableEnumKeys = mainUserModelStoryHashtable.keys();
//      Enumeration mainUserModelStoryHashtableEnumElements = mainUserModelStoryHashtable.elements();
//      while (mainUserModelStoryHashtableEnumKeys.hasMoreElements())
//      {
//        // "owner" is entity-type or entity
//        String owner = mainUserModelStoryHashtableEnumKeys.nextElement().toString();
//        Hashtable ownerHashtable = (Hashtable)mainUserModelStoryHashtableEnumElements.nextElement();
//        Enumeration ownerHashtableEnumKeys = ownerHashtable.keys();
//        Enumeration ownerHashtableEnumElements = ownerHashtable.elements();
//        while (ownerHashtableEnumKeys.hasMoreElements())
//        {
//          String storyname = ownerHashtableEnumKeys.nextElement().toString();
//          Hashtable storynameHashtable = (Hashtable)ownerHashtableEnumElements.nextElement();
//          if (storynameHashtable.containsKey(oldname))
//          {
//            Vector oldnameVector = (Vector)storynameHashtable.get(oldname);
//            storynameHashtable.put(newname, oldnameVector);
//            storynameHashtable.remove(oldname);
//					}
//    		}
//			}
//		}
//	}


	/**
	 *  This method is invoked when a new user entry is removed (username)
	 */
//	public void removeUserInUserModelStoryHashtable(String username) 
//	{
//	  Enumeration mainUserModelStoryHashtableEnumKeys = mainUserModelStoryHashtable.keys();
//	  Enumeration mainUserModelStoryHashtableEnumElements = mainUserModelStoryHashtable.elements();
//	  while (mainUserModelStoryHashtableEnumKeys.hasMoreElements())
//	  {
//	    // "owner" is entity-type or entity
//	    String owner = mainUserModelStoryHashtableEnumKeys.nextElement().toString();
//	    Hashtable ownerHashtable = (Hashtable)mainUserModelStoryHashtableEnumElements.nextElement();
//	    Enumeration ownerHashtableEnumKeys = ownerHashtable.keys();
//	    Enumeration ownerHashtableEnumElements = ownerHashtable.elements();
//	    while (ownerHashtableEnumKeys.hasMoreElements())
//	    {
//	      String storyname = ownerHashtableEnumKeys.nextElement().toString();
//	      Hashtable storynameHashtable = (Hashtable)ownerHashtableEnumElements.nextElement();
//	      if (storynameHashtable.containsKey(username))
//	      {
//          storynameHashtable.remove(username);
//      	}
//      }
//  	}
//	}


	/**
	 *  This method is invoked when a new story entry is created (storyname)
	 */
//	public void addStoryInUserModelStoryHashtable(String storyname, String nodename) 
//	{
//	  Hashtable nodenameUMStoryHashtable = (Hashtable)mainUserModelStoryHashtable.get(nodename);
//	  nodenameUMStoryHashtable.put(storyname, new Hashtable());
//	  Hashtable storynameHashtable = (Hashtable)nodenameUMStoryHashtable.get(storyname);
//
//    // add users into story hashtable
//    Vector allUsersVector = (Vector)getUsersVectorFromMainUsersHashtable();
//    Enumeration allUsersVectorEnum = allUsersVector.elements();
//    while (allUsersVectorEnum.hasMoreElements())
//    {
//	    String user = allUsersVectorEnum.nextElement().toString();
//	    storynameHashtable.put(user, new Vector());
//	    Vector vec = (Vector)storynameHashtable.get(user);
//	    vec.addElement("0");
//	    vec.addElement("0");
//	    vec.addElement("0");
//		}
//	}


	/**
	 *  This method is invoked when a story name (oldname) is renamed (newname) for a particular storynode (storynode)
	 */
//	public void renameStoryInUserModelStoryHashtable(String storynode, String oldname, String newname) 
//	{
//	  if (!oldname.equalsIgnoreCase(newname))
//	  {
//	    Hashtable storynodeHashtable = (Hashtable)mainUserModelStoryHashtable.get(storynode);
//	    if (storynodeHashtable.containsKey(oldname))
//	    {
//	      Hashtable oldstoryHashtable = (Hashtable)storynodeHashtable.get(oldname);
//	      storynodeHashtable.put(newname, oldstoryHashtable);
//	      storynodeHashtable.remove(oldname);
//      }
//  	}
//	}


	/**
	 *  This method is invoked when a story name (storyname) is removed for a particular storynode (storynode)
	 */
//	public void removeStoryInUserModelStoryHashtable(String storynode, String storyname) 
//	{
//		Hashtable storynodeHashtable = (Hashtable)mainUserModelStoryHashtable.get(storynode);
//		if (storynodeHashtable.containsKey(storyname))
//		{
//			storynodeHashtable.remove(storyname);
//		}
//	}


	/**
	 *  This method is invoked when a new entity-type or entity is created (entitytypeOrEntityname)
	 */
//	public void addEntityTypeOrEntityInUserModelStoryHashtable(String entitytypeOrEntityname) 
//	{
//		mainUserModelStoryHashtable.put(entitytypeOrEntityname, new Hashtable());
//	}



	/**
	 *  This method is invoked when an entity-type or entity (oldname) is renamed (newname)
	 */
//	public void renameEntityTypeOrEntityInUserModelStoryHashtable(String oldname, String newname) 
//	{
//		if (!oldname.equalsIgnoreCase(newname))
//		{
//			if (mainUserModelStoryHashtable.containsKey(oldname))
//			{
//				Hashtable oldnameHashtable = (Hashtable)mainUserModelStoryHashtable.get(oldname);
//				mainUserModelStoryHashtable.put(newname, oldnameHashtable);
//				mainUserModelStoryHashtable.remove(oldname);
//			}
//		}
//	}


	/**
	 *  This method is invoked when a new entity-type or entity is removed (nodename)
	 */
//  public void removeEntityTypeOrEntityInUserModelStoryHashtable(String nodename) 
//  {
//		if (mainUserModelStoryHashtable.containsKey(nodename))
//		{
//		  mainUserModelStoryHashtable.remove(nodename);
//		}
//  }


	/**
	 *  This method is invoked when the user-modelling info (value), of a parameter (valueID)
	 *  for a user-type (username), for a story (storyname), of a story node (nodename)
	 *  is changed
	 */
//	public void updateUserModelStoryParameters(String storyname, String nodename, String username, int valueID, String value) 
//	{
//	  Hashtable nodenameHashtable = (Hashtable)mainUserModelStoryHashtable.get(nodename);
//	  Hashtable storynameHashtable = (Hashtable)nodenameHashtable.get(storyname);
//	  Vector usernameVector = (Vector)storynameHashtable.get(username);
//	  usernameVector.setElementAt(value, valueID);
//	}


	/**
	 *  This method is invoked from the UserModelStoryDialog when a UserModelStoryDialog.UserModelStoryPanel is created
	 *  for each user (username) in a story (storyname) of a node (nodename)
	 */
//	public Vector getUserModelStoryValuesVector(String storyname, String nodename, String username) 
//	{
//	  Hashtable nodenameHashtable = (Hashtable)mainUserModelStoryHashtable.get(nodename);
//	  Hashtable storynameHashtable = (Hashtable)nodenameHashtable.get(storyname);
//	  Vector usernameVector = (Vector)storynameHashtable.get(username);
//	  return usernameVector;
//	}


        
  
    void updateRobotsCharModelParameters(String field, String node, String username, int i, String string) {
       // throw new UnsupportedOperationException("Not yet implemented");
    }

    
     public Enumeration getUserNames(){
        Vector users=new Vector();
        Enumeration keys=mainUsersHashtable.keys();
        Enumeration elements=mainUsersHashtable.elements();
        while(keys.hasMoreElements()){
            Object key=keys.nextElement();
            Object element=elements.nextElement();
            if(element instanceof User)
                users.add(key);
            
        }
        return users.elements();
    }

     
     public Enumeration getRobotNames(){
        Vector robots=new Vector();
        Enumeration keys=mainUsersHashtable.keys();
        Enumeration elements=mainUsersHashtable.elements();
        while(keys.hasMoreElements()){
            Object key=keys.nextElement();
            Object element=elements.nextElement();
            if(element instanceof gr.demokritos.iit.eleon.profiles.Robot)
                robots.add(key);
            
        }
        return robots.elements();
    }
     
      public Object[] getRobotNamesToArray(){
        Vector robots=new Vector();
        Enumeration keys=mainUsersHashtable.keys();
        Enumeration elements=mainUsersHashtable.elements();
        while(keys.hasMoreElements()){
            Object key=keys.nextElement();
            Object element=elements.nextElement();
            if(element instanceof gr.demokritos.iit.eleon.profiles.Robot)
                robots.add(key);
            
        }
        return robots.toArray();
    }
     
     /*returns an enumeration with max facts per sentence
      *facts per page, synthesizer voice and links per page for 
      *each user*/
     public Enumeration getUserElements(){
        Vector users=new Vector();
        Enumeration elements=mainUsersHashtable.elements();
        while(elements.hasMoreElements()){
            Object element=elements.nextElement();
            if(element instanceof User)
                users.add(element);
            
        }
        return users.elements();
    }
     
     /*returns an enumeration with with
      OCEAN values for each robot*/
     public Enumeration getRobotElements(){
        Vector robots=new Vector();
        Enumeration elements=mainUsersHashtable.elements();
        while(elements.hasMoreElements()){
            Object element=elements.nextElement();
            if(element instanceof gr.demokritos.iit.eleon.profiles.Robot)
                robots.add(element);
            
        }
        return robots.elements();
    }
     
     /*returns a vector with 2 enumerations as elements. the first contains usernames
      *and the second the modelling values for the given property  and entity
      *or entity type*/
      public Vector getUserModelling(String property, String entityOrType){
        Vector userModelling=new Vector();
        Vector usernames=new Vector();
        Vector modellingValues=new Vector();
        Hashtable usersAndProfiles=((Hashtable)((Hashtable)mainUserModelHashtable.get(property)).get(entityOrType));
        Enumeration keys=usersAndProfiles.keys();
        Enumeration elements=usersAndProfiles.elements();
        while(keys.hasMoreElements()){
            String nextKey=keys.nextElement().toString();
            Object nextEl=elements.nextElement();
            if(existsUser(nextKey))
            {
                usernames.add(nextKey);
                modellingValues.add(nextEl);
                
            }
        }
        userModelling.add(usernames.elements());
        userModelling.add(modellingValues.elements());
        return userModelling;
    }

    private boolean existsUser(String user) {
        Enumeration users=getUserNames();
        while(users.hasMoreElements()){
            if(users.nextElement().equals(user))
                return true;
            
        }
        return false;
    }
    
    private boolean existsRobot(String robot) {
        Enumeration robots=getRobotNames();
        while(robots.hasMoreElements()){
            if(robots.nextElement().equals(robot))
                return true;
            
        }
        return false;
    }
    
     /*returns a vector with 2 enumerations as elements. the first contains profile names
      *and the second the modelling values for the given property  and entity
      *or entity type*/
      public Vector getRobotModelling(String property, String entityOrType){
        Vector robotModelling=new Vector();
        Vector robotnames=new Vector();
        Vector modellingValues=new Vector();
        Hashtable robotsAndProfiles=((Hashtable)((Hashtable)mainUserModelHashtable.get(property)).get(entityOrType));
        Enumeration keys=robotsAndProfiles.keys();
        Enumeration elements=robotsAndProfiles.elements();
        while(keys.hasMoreElements()){
            String nextKey=keys.nextElement().toString();
            Object nextEl=elements.nextElement();
            if(existsRobot(nextKey))
            {
                robotnames.add(nextKey);
                modellingValues.add(nextEl);
                
            }
        }
        robotModelling.add(robotnames.elements());
        robotModelling.add(modellingValues.elements());
        return robotModelling;
    }
    
}//class QueryUserHashtable