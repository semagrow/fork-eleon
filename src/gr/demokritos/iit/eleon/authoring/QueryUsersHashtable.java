//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
//import java.lang.reflect.Array;
import java.lang.*;
import java.io.*;
import java.lang.Object.*;


public class QueryUsersHashtable extends Object implements Serializable 
{
	static Hashtable currentValues;
	static Hashtable currentTables;
	
	static String hashtableEntry;
	static String field;
	static String currentValue;
	static String currentSpecialValue;
	
	static Font panelFont= new Font(Mpiro.selectedFont,Font.BOLD,11);
	static Dimension leftMargin = new Dimension(150,10);
	public static Hashtable mainUsersHashtable;
        public static Hashtable robotsHashtable;
	public static Hashtable mainUserModelHashtable;
        public static Hashtable mainRobotsModelHashtable;
	public static Hashtable mainUserModelStoryHashtable;
	static Vector currentVector;


	/** Create the main users hashtable. (for initialisation purposes only) */
	public static void createMainUsersHashtable() 
	{
		mainUsersHashtable = new Hashtable();
		createDefaultUser("NewUserType");
	} // create�ainUsersHashtable
        
        public static void createRobotsHashtable() 
	{
		robotsHashtable = new Hashtable();
		createDefaultRobot("NewProfile");
	}

	/**
	 * This method checks the mainUsersHashtable for name replication.
	 */
	public static int checkUsersName(String nodeName) 
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
			Enumeration enu1 = mainUsersHashtable.keys();
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
        
        public static int checkRobotsName(String nodeName) 
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
			Enumeration enu1 = robotsHashtable.keys();
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


	public static void createDefaultUser(String name) 
	{
		mainUsersHashtable.put(name, new Vector());
		currentVector = (Vector)mainUsersHashtable.get(name);
		currentVector.addElement("4");
		currentVector.addElement("10");
		currentVector.addElement("4");
		currentVector.addElement("male");
		QueryUsersHashtable.addUserInUserModelHashtable(name);
                QueryHashtable.addUserInPropertiesHashtable(name);
		//System.out.println("(QueryUsersHashtable.createDefaultUser)---- " + name);
	}
        
        public static void createDefaultRobot(String name) 
	{
		robotsHashtable.put(name, new Vector());
		currentVector = (Vector)robotsHashtable.get(name);
		currentVector.addElement("50");
		currentVector.addElement("50");
		currentVector.addElement("50");
		currentVector.addElement("50");
                currentVector.addElement("50");
		QueryUsersHashtable.addRobotInRobotsModelHashtable(name);
                QueryHashtable.addRobotInRobotCharValuesHashtable(name);
                QueryHashtable.addRobotInPropertiesHashtable(name);
		//System.out.println("(QueryUsersHashtable.createDefaultUser)---- " + name);
	}


	public static void updateIndependentLexiconHashtable(String usertype, String oldusertype, String action) 
	{
		if (action.equalsIgnoreCase("ADD"))
		{
			Hashtable lexiconNounHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Nouns");
			Enumeration lexiconNounHashtableEnum = lexiconNounHashtable.keys();
			while (lexiconNounHashtableEnum.hasMoreElements())
			{
				String noun = lexiconNounHashtableEnum.nextElement().toString();
				Hashtable currentNounHashtable = (Hashtable)lexiconNounHashtable.get(noun);
				Hashtable currentNounIndependentHashtable = (Hashtable)currentNounHashtable.get("Independent");
				currentNounIndependentHashtable.put(usertype, "0");
			}
			Hashtable lexiconVerbHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Verbs");
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
			Hashtable lexiconNounHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Nouns");
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
			Hashtable lexiconVerbHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Verbs");
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
			Hashtable lexiconNounHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Nouns");
			Enumeration lexiconNounHashtableEnum = lexiconNounHashtable.keys();
			while (lexiconNounHashtableEnum.hasMoreElements())
			{
				String noun = lexiconNounHashtableEnum.nextElement().toString();
				Hashtable currentNounHashtable = (Hashtable)lexiconNounHashtable.get(noun);
				Hashtable currentNounIndependentHashtable = (Hashtable)currentNounHashtable.get("Independent");
				currentNounIndependentHashtable.put(usertype, "0");
				currentNounIndependentHashtable.remove(usertype);
			}
			Hashtable lexiconVerbHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Verbs");
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


	public static void updateAppropriatenessValuesInMicroplanningOfFields(String usertype, String oldusertype, String action) 
	{
	  if (action.equalsIgnoreCase("ADD"))
	  {
	    Hashtable allFieldsAndContainingEntityTypesHashtable =  QueryHashtable.returnAllFieldsAndContainingEntityTypes();
	    for (Enumeration k = allFieldsAndContainingEntityTypesHashtable.keys(),
	                     e = allFieldsAndContainingEntityTypesHashtable.elements();
	                     k.hasMoreElements(); )
			{
				String field = k.nextElement().toString();
				String entityTypeName = e.nextElement().toString();
				
				for (int i=1; i<6; i++)
				{
					String microplanNumber = new Integer(i).toString();
					
					Vector nodeVector = (Vector)QueryHashtable.mainDBHashtable.get(entityTypeName);
					Hashtable currentHashtable = (Hashtable)nodeVector.get(5);
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "English", "0");
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "Italian", "0");
					currentHashtable.put(microplanNumber + ":" + field + ":" + usertype + ":" + "Greek", "0");
					
					//QueryHashtable.updateHashtable(entityTypeName, microplanNumber, fieldname, user, "English", "0");
					//QueryHashtable.updateHashtable(entityTypeName, microplanNumber, fieldname, user, "Italian", "0");
					//QueryHashtable.updateHashtable(entityTypeName, microplanNumber, fieldname, user, "Greek", "0");
				}
			}
		}
    else if (action.equalsIgnoreCase("RENAME"))
    {
	    Hashtable allFieldsAndContainingEntityTypesHashtable =  QueryHashtable.returnAllFieldsAndContainingEntityTypes();
	    for (Enumeration k = allFieldsAndContainingEntityTypesHashtable.keys(),
	                     e = allFieldsAndContainingEntityTypesHashtable.elements();
	                     k.hasMoreElements(); )
	    {
	      String field = k.nextElement().toString();
	      String entityTypeName = e.nextElement().toString();
	
	      for (int i=1; i<6; i++)
	      {
					String microplanNumber = new Integer(i).toString();
					
					Vector nodeVector = (Vector)QueryHashtable.mainDBHashtable.get(entityTypeName);
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
	    Hashtable allFieldsAndContainingEntityTypesHashtable =  QueryHashtable.returnAllFieldsAndContainingEntityTypes();
	    for (Enumeration k = allFieldsAndContainingEntityTypesHashtable.keys(),
	                     e = allFieldsAndContainingEntityTypesHashtable.elements();
	                     k.hasMoreElements(); )
			{
				String field = k.nextElement().toString();
				String entityTypeName = e.nextElement().toString();
				
				for (int i=1; i<6; i++)
				{
					String microplanNumber = new Integer(i).toString();
					
					Vector nodeVector = (Vector)QueryHashtable.mainDBHashtable.get(entityTypeName);
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
	public static void updateUserInfo(String name, int attributeNumber, String attributeValue) 
	{
		currentVector = (Vector)mainUsersHashtable.get(name);
		currentVector.setElementAt(attributeValue, attributeNumber);
	}

        public static void updateRobotInfo(String name, int attributeNumber, String attributeValue) 
	{
		currentVector = (Vector)robotsHashtable.get(name);
		currentVector.setElementAt(attributeValue, attributeNumber);
	}

	/**
	 *  This method is invoked when a user-type entry (oldname) is renamed (newname)
	 */
	public static void renameUser(String oldname, String newname) 
	{
		//System.out.println("oldname =" + oldname);
		//System.out.println("newname =" + newname);
		
		if (mainUsersHashtable.containsKey(oldname))
		{
			Vector oldnameVector = (Vector)mainUsersHashtable.get(oldname);
			mainUsersHashtable.put(newname, oldnameVector);
			mainUsersHashtable.remove(oldname);
			QueryUsersHashtable.renameUserInUserModelHashtable(oldname, newname);
		}
		//System.out.println(mainLexiconHashtable.entrySet());
	}

        
        public static void renameRobot(String oldname, String newname) 
	{
		//System.out.println("oldname =" + oldname);
		//System.out.println("newname =" + newname);
		
		if (robotsHashtable.containsKey(oldname))
		{
			Vector oldnameVector = (Vector)robotsHashtable.get(oldname);
			robotsHashtable.put(newname, oldnameVector);
			robotsHashtable.remove(oldname);
			QueryUsersHashtable.renameRobotInRobotsModelHashtable(oldname, newname);
		}
		//System.out.println(mainLexiconHashtable.entrySet());
	}

	/**
	 *  This method is invoked when an entity-type (name) is removed
	 */
	public static void removeUser(String name) 
	{
		mainUsersHashtable.remove(name);
		QueryUsersHashtable.removeUserInUserModelHashtable(name);
                QueryHashtable.deleteUserFromPropertiesHashtable(name);
	}
        
        public static void removeRobot(String name) 
	{
		robotsHashtable.remove(name);
		QueryUsersHashtable.removeRobotInRobotsModelHashtable(name);
                QueryHashtable.deleteRobotFromPropertiesHashtable(name);
	}


	/**
	 *  Returns a vector of all *users* (sorted in alphabetical order) currently saved
	 *  It is used when loading a new domain
	 */
	public static Vector getUsersVectorFromMainUsersHashtable() 
	{
		Vector usersVector = new Vector();
		Enumeration enumer = mainUsersHashtable.keys();
                
		while (enumer.hasMoreElements())
		{
			usersVector.addElement(enumer.nextElement().toString());
		}
		usersVector = QuickSort.quickSort(0, usersVector.size()-1, usersVector);
		return usersVector;
	}
        
        public static Vector getRobotsVectorFromRobotsHashtable() 
	{
		Vector usersVector = new Vector();
		Enumeration enumer = robotsHashtable.keys();
                
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
	public static void createMainUserModelHashtable() 
	{
		mainUserModelHashtable = new Hashtable();
	} // create�ainUserModelHashtable

        public static void createMainRobotsModelHashtable() 
	{
		mainRobotsModelHashtable = new Hashtable();
	}
	/**
	 *  This method is invoked when a new user entry is created (newname)
	 */
	public static void addUserInUserModelHashtable(String newname) 
	{
		Hashtable allEntityTypes = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		
		//if (mainUserModelHashtable.size() != 0)
		//{
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
        public static void addRobotInRobotsModelHashtable(String newname) 
	{
		Hashtable allEntityTypes = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		
		//if (mainUserModelHashtable.size() != 0)
		//{
		Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
		Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
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
	public static void renameUserInUserModelHashtable(String oldname, String newname) 
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
        
        public static void renameRobotInRobotsModelHashtable(String oldname, String newname) 
	{
		if (!oldname.equalsIgnoreCase(newname))
		{
	    Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
	    Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
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
	public static void removeUserInUserModelHashtable(String username) 
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
        
        	public static void removeRobotInRobotsModelHashtable(String username) 
	{
	  Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
	  Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
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
  public static void addFieldInUserModelHashtable(String fieldname, String entitytypename) 
  {
		//Hashtable allEntityTypes = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		//Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		//System.out.println("40");
		// get all children entities of the entity type that consequently contain the new field
		Vector allChildrenEntities = QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable(entitytypename, "Entity+Generic");
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
		Vector allUsersVector = (Vector)QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
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
		}
	}

  
   public static void addFieldInRobotsModelHashtable(String fieldname, String entitytypename) 
  {
		//Hashtable allEntityTypes = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		//Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		//System.out.println("40");
		// get all children entities of the entity type that consequently contain the new field
		Vector allChildrenEntities = QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable(entitytypename, "Entity+Generic");
		//System.out.println("41");
              //  Hashtable fieldnameHashtable=new Hashtable();
               // try{
                 Hashtable   fieldnameHashtable=(Hashtable) mainRobotsModelHashtable.get(fieldname);
               // }
           //     catch(NullPointerException v){
                 if(fieldnameHashtable==null){
                    mainRobotsModelHashtable.put(fieldname, new Hashtable());
		 fieldnameHashtable = (Hashtable) mainRobotsModelHashtable.get(fieldname);
                }
		
		//System.out.println("42");
		// add the entity-type into the field
		fieldnameHashtable.put(entitytypename, new Hashtable());
		// add users into entity-type
		Vector allUsersVector = (Vector)QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
		Enumeration allUsersVectorEnum = allUsersVector.elements();//System.out.println("43");
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
                      //  if (entity.substring(0,entity.length()-1).endsWith("_occur")) 
                      //      entity=entity.substring(0,entity.length()-7);
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
			//	vec.addElement("");
				//vec.addElement("");
			}
		}
	}

	/**
	 *  This method is invoked when a field name (oldname) is renamed (newname)
	 */
	public static void renameFieldInUserModelHashtable(String oldname, String newname) 
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
        public static void renameFieldInRobotsModelHashtable(String oldname, String newname) 
	{
		if (!oldname.equalsIgnoreCase(newname))
		{
			if (mainRobotsModelHashtable.containsKey(oldname))
			{
				Hashtable oldfieldHashtable = (Hashtable)mainRobotsModelHashtable.get(oldname);
				mainRobotsModelHashtable.put(newname, oldfieldHashtable);
				mainRobotsModelHashtable.remove(oldname);
			}
		}
	}


	/**
	 *  This method is invoked when a field name (fieldname) is removed
	 */
	public static void removeFieldInUserModelHashtable(String fieldname) 
	{
		mainUserModelHashtable.remove(fieldname);
	}
        public static void removeFieldInRobotsModelHashtable(String fieldname) 
	{
		mainRobotsModelHashtable.remove(fieldname);
	}


	/**
	 *  This method is invoked when a new entity-type is created (newname)
	 */
	public static void addEntityTypeInUserModelHashtable(String entitytypename) 
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
		Vector allUsersVector = (Vector)QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
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
	}
        public static void addEntityTypeInRobotsModelHashtable(String entitytypename) 
	{
		Hashtable fieldnameHashtable = new Hashtable();
		if (!mainRobotsModelHashtable.containsKey("Subtype-of"))
		{
			mainRobotsModelHashtable.put("Subtype-of", new Hashtable());
		}
		fieldnameHashtable = (Hashtable)mainRobotsModelHashtable.get("Subtype-of");
		
		// add the entity-type into the field
		fieldnameHashtable.put(entitytypename, new Hashtable());
		// add users into entity-type
		Vector allUsersVector = (Vector)QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
		Enumeration allUsersVectorEnum = allUsersVector.elements();
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
	public static void addEntityInUserModelHashtable(String newname) 
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
		
		Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		
		// find all the fields inherited from parent entity-type
		Vector allEntityFieldsVector = new Vector();
		String parentEntityType = allEntities.get(newname).toString();
		NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(parentEntityType);
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
			Vector allUsersVector = (Vector)QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
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
		}
	}
        
        
        public static void addEntityInRobotsModelHashtable(String newname) 
	{
		if (!mainRobotsModelHashtable.containsKey("type"))
		{
			mainRobotsModelHashtable.put("type", new Hashtable());
		}

		// the "name" refers to both name and shortname fields of entities ONLY
		if (!mainRobotsModelHashtable.containsKey("name"))
		{
			mainRobotsModelHashtable.put("name", new Hashtable());
		}
		
		Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		
		// find all the fields inherited from parent entity-type
		Vector allEntityFieldsVector = new Vector();
		String parentEntityType = allEntities.get(newname).toString();
		NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(parentEntityType);
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
			Hashtable fieldnameHashtable = (Hashtable)mainRobotsModelHashtable.get(fieldname);
			// add the new entity into the field
                        System.out.println("userrrmodel"+fieldname+newname+fieldnameHashtable.toString());
                        try{
			fieldnameHashtable.put(newname, new Hashtable());
                        }
                        catch(NullPointerException exc){};
			Hashtable entityHashtable = (Hashtable)fieldnameHashtable.get(newname);
			
			// add the user model info into the entity
			Vector allUsersVector = (Vector)QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
			Enumeration allUsersVectorEnum = allUsersVector.elements();
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
	public static void renameEntityTypeOrEntityInUserModelHashtable(String oldname, String newname) 
	{
		if (!oldname.equalsIgnoreCase(newname))
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
        
        public static void renameEntityTypeOrEntityInRobotsModelHashtable(String oldname, String newname) 
	{
		if (!oldname.equalsIgnoreCase(newname))
		{
			Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
			Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
			while (mainRobotsModelHashtableEnumKeys.hasMoreElements())
			{
				String fieldname = mainRobotsModelHashtableEnumKeys.nextElement().toString();
				Hashtable fieldnameHashtable = (Hashtable)mainRobotsModelHashtableEnumElements.nextElement();
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
  public static void removeEntityTypeOrEntityInUserModelHashtable(String nodename) 
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
  
   public static void removeEntityTypeOrEntityInRobotsModelHashtable(String nodename) 
  {
    Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
    Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
    while (mainRobotsModelHashtableEnumKeys.hasMoreElements())
    {
      String fieldname = mainRobotsModelHashtableEnumKeys.nextElement().toString();
      Hashtable fieldnameHashtable = (Hashtable)mainRobotsModelHashtableEnumElements.nextElement();
      if (fieldnameHashtable.containsKey(nodename))
      {
      	fieldnameHashtable.remove(nodename);
      }
    }
  }


	/**
	 *  This method is invoked when the user-modelling info (value), of a parameter (valueID)
	 *  for a user-type (username), for a field (fieldname), of a database node (nodename)
	 *  is changed
	 */
	public static void updateUserModelParameters(String fieldname, String nodename, String username, int valueID, String value) 
	{
	  Hashtable fieldnameHashtable = (Hashtable)mainUserModelHashtable.get(fieldname);
	  Hashtable nodenameHashtable = (Hashtable)fieldnameHashtable.get(nodename);
	  Vector usernameVector = (Vector)nodenameHashtable.get(username);
	  usernameVector.setElementAt(value, valueID);
	}
        public static void updateRobotsModelParameters(String fieldname, String nodename, String username, int valueID, String value) 
	{
	  Hashtable fieldnameHashtable = (Hashtable)mainRobotsModelHashtable.get(fieldname);
	  Hashtable nodenameHashtable = (Hashtable)fieldnameHashtable.get(nodename);
	  Vector usernameVector = (Vector)nodenameHashtable.get(username);
	  usernameVector.setElementAt(value, valueID);
	}


	/**
	 *  This method is invoked from the UserModelDialog when a UserModelDialog.UserModelPanel is created
	 *  for each user (username) in a field (fieldname) of a node (nodename)
	 */
	public static Vector getUserModelValuesVector(String fieldname, String nodename, String username) 
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
        
        
        public static Vector getRobotsModelValuesVector(String fieldname, String nodename, String username) 
	{
            Vector usernameVector=new Vector();
            if(!mainRobotsModelHashtable.containsKey(fieldname))
                mainRobotsModelHashtable.put(fieldname, new Hashtable());
	  Hashtable fieldnameHashtable = (Hashtable)mainRobotsModelHashtable.get(fieldname);
         
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

        
        public static Vector getParentValuesVector(String currentField, String usertype, String nodeName) 
	{
      /*      if(type.equalsIgnoreCase("Property")){
                Vector defaultVector = new Vector();
			defaultVector.addElement("0");
			defaultVector.addElement("1");
			defaultVector.addElement("2");
			return defaultVector;
            }
            else{
                
            }*/
		//Hashtable allFieldsAndContainingEntityTypesHashtable = QueryHashtable.returnAllFieldsAndContainingEntityTypes();
		
		// this is used for the "type" and "called" (name/shortname) fields
		// that are not present in entity-types. They are entity only
		//if (!allFieldsAndContainingEntityTypesHashtable.containsKey(currentField))
		//{
		//	Vector defaultVector = new Vector();
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("1");
		//	return defaultVector;
		//}
		String containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString();
                if(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
		Vector parentValuesVector = QueryUsersHashtable.getUserModelValuesVector(currentField, containingEntityType, usertype);
		return parentValuesVector;
                }
                else{
                   return (Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(12)).get(usertype));
                }
	}
        
        public static Vector getRobotsParentValuesVector(String currentField, String usertype, String nodeName) 
	{
      /*      if(type.equalsIgnoreCase("Property")){
                Vector defaultVector = new Vector();
			defaultVector.addElement("0");
			defaultVector.addElement("1");
			defaultVector.addElement("2");
			return defaultVector;
            }
            else{
                
            }*/
		//Hashtable allFieldsAndContainingEntityTypesHashtable = QueryHashtable.returnAllFieldsAndContainingEntityTypes();
		
		// this is used for the "type" and "called" (name/shortname) fields
		// that are not present in entity-types. They are entity only
		//if (!allFieldsAndContainingEntityTypesHashtable.containsKey(currentField))
		//{
		//	Vector defaultVector = new Vector();
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("1");
		//	return defaultVector;
		//}
		String containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString();
                if(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
		Vector parentValuesVector = QueryUsersHashtable.getRobotsModelValuesVector(currentField, containingEntityType, usertype);
		return parentValuesVector;
                }
                else{
                  return (Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(15)).get(usertype));
                }
	}

        
        
        public static String getParentTypeOfVectorForOWLExport(String usertype, String nodeName, int i) 
	{
		String containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString();
                
           //     if(type.equals("entity")){
             //       String toReturn=((Hashtable)QueryUsersHashtable.mainUserModelHashtable.get("type")).get(nodeName).toString();
               // if (!toReturn.equals("")) return toReturn;
                 //   containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString()
               // }
                
                while(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
                // Hashtable temp=   (Hashtable)QueryUsersHashtable.mainUserModelHashtable.get("Subtype-of");
                String toReturn=((Vector)((Hashtable)((Hashtable)QueryUsersHashtable.mainUserModelHashtable.get("Subtype-of")).get(containingEntityType)).get(usertype)).elementAt(i).toString();
                if (!toReturn.equals("")) return toReturn;
                containingEntityType = QueryHashtable.getParents(containingEntityType).elementAt(0).toString();
                }
                
                return "0";
                
            //    while(!containingEntityType.equalsIgnoreCase("Basic-entity-types")){
		// Vector defaultVector = new Vector();
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	return defaultVector;
              //  }
              //  else{
               //    return (Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(12)).get(usertype));
               // }
	}
        
        public static String getRobotParentTypeOfVectorForOWLExport(String usertype, String nodeName, int i) 
	{
		String containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString();
                
           //     if(type.equals("entity")){
             //       String toReturn=((Hashtable)QueryUsersHashtable.mainUserModelHashtable.get("type")).get(nodeName).toString();
               // if (!toReturn.equals("")) return toReturn;
                 //   containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString()
               // }
                
                while(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
                // Hashtable temp=   (Hashtable)QueryUsersHashtable.mainUserModelHashtable.get("Subtype-of");
                String toReturn=((Vector)((Hashtable)((Hashtable)QueryUsersHashtable.mainRobotsModelHashtable.get("Subtype-of")).get(containingEntityType)).get(usertype)).elementAt(i).toString();
                if (!toReturn.equals("")) return toReturn;
                containingEntityType = QueryHashtable.getParents(containingEntityType).elementAt(0).toString();
                }
                
                return "0";
                
            //    while(!containingEntityType.equalsIgnoreCase("Basic-entity-types")){
		// Vector defaultVector = new Vector();
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	return defaultVector;
              //  }
              //  else{
               //    return (Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(12)).get(usertype));
               // }
	}
        
        
       
         public static String getParentValuesVectorForOWLExport(String currentField, String usertype, String nodeName, int i) 
	{
      /*      if(type.equalsIgnoreCase("Property")){
                Vector defaultVector = new Vector();
			defaultVector.addElement("0");
			defaultVector.addElement("1");
			defaultVector.addElement("2");
			return defaultVector;
            }
            else{
                
            }*/
		//Hashtable allFieldsAndContainingEntityTypesHashtable = QueryHashtable.returnAllFieldsAndContainingEntityTypes();
		
		// this is used for the "type" and "called" (name/shortname) fields
		// that are not present in entity-types. They are entity only
		//if (!allFieldsAndContainingEntityTypesHashtable.containsKey(currentField))
		//{
		//	Vector defaultVector = new Vector();
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("1");
		//	return defaultVector;
		//}
		String containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString();
                
                while(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
                String toReturn=QueryUsersHashtable.getUserModelValuesVector(currentField, containingEntityType, usertype).elementAt(i).toString();
                if (!toReturn.equals("")) return toReturn;
                System.out.println(containingEntityType);
                containingEntityType = QueryHashtable.getParents(containingEntityType).elementAt(0).toString();
                }
                
                return ((Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(12)).get(usertype))).elementAt(i).toString();
                
            //    while(!containingEntityType.equalsIgnoreCase("Basic-entity-types")){
		// Vector defaultVector = new Vector();
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	return defaultVector;
              //  }
              //  else{
               //    return (Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(12)).get(usertype));
               // }
	}
         
          public static String getRobotsParentValuesVectorForOWLExport(String currentField, String usertype, String nodeName, int i) 
	{
      /*      if(type.equalsIgnoreCase("Property")){
                Vector defaultVector = new Vector();
			defaultVector.addElement("0");
			defaultVector.addElement("1");
			defaultVector.addElement("2");
			return defaultVector;
            }
            else{
                
            }*/
		//Hashtable allFieldsAndContainingEntityTypesHashtable = QueryHashtable.returnAllFieldsAndContainingEntityTypes();
		
		// this is used for the "type" and "called" (name/shortname) fields
		// that are not present in entity-types. They are entity only
		//if (!allFieldsAndContainingEntityTypesHashtable.containsKey(currentField))
		//{
		//	Vector defaultVector = new Vector();
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("3");
		//	defaultVector.addElement("1");
		//	return defaultVector;
		//}
            //  System.out.println("::::::::::: "+nodeName);
		String containingEntityType = QueryHashtable.getParents(nodeName).elementAt(0).toString();
                
                while(!(containingEntityType.equalsIgnoreCase("Basic-entity-types")||containingEntityType.equalsIgnoreCase("Data Base"))){
                String toReturn=QueryUsersHashtable.getRobotsModelValuesVector(currentField, containingEntityType, usertype).elementAt(i).toString();
                if (!toReturn.equals("")) return toReturn;
                System.out.println(containingEntityType);
                containingEntityType = QueryHashtable.getParents(containingEntityType).elementAt(0).toString();
                }
               // System.out.println("MMMMMMMM"+currentField+"   "+usertype);
             //   Hashtable temp=(Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(15);
             //   System.out.println("MMMMMMMM"+currentField+"   "+usertype+"   "+temp.toString());
                if(!currentField.equals("ClassOrInd"))
                return ((Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(15)).get(usertype))).elementAt(i).toString();
                else
                    return ("0");
            //    while(!containingEntityType.equalsIgnoreCase("Basic-entity-types")){
		// Vector defaultVector = new Vector();
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	defaultVector.addElement("-1");
		//	return defaultVector;
              //  }
              //  else{
               //    return (Vector) (((Hashtable)((Vector)QueryHashtable.propertiesHashtable.get(currentField)).elementAt(12)).get(usertype));
               // }
	}
	/**
	 *  This method is invoked from the UserModelMicroplanDialog when a UserModelMicroplanDialog.UserModelPanel is created
	 *  for each user (username) in a field (fieldname) of a microplan (microplanNumber) of a node (nodename)
	 */
           public static Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username) 
	{
              String nodename=((Vector)((Vector)QueryHashtable.propertiesHashtable.get(fieldname)).elementAt(0)).elementAt(0).toString();
	  Vector nodenameVector = (Vector)QueryHashtable.mainDBHashtable.get(nodename);
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
          
	public static Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String nodename, String username) 
	{
	  Vector nodenameVector = (Vector)QueryHashtable.mainDBHashtable.get(nodename);
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
	public static Vector getAppropriatenessValuesVector(String fieldname, String microplanNumber, String username, Hashtable microplanningHashtable) 
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


	/*
	 *  This method is invoked from the UserModelDialog when a UserModelDialog.UserModelPanel is created
	 *  for each user (username) in a field (fieldname) of a node (nodename)
	 */
	/* public static void initialiseTwoBuiltInFieldsInUserModelHashtable() 
	 		{
        String entityTypefield = new String("Subtype-of");
        String entityfield = new String("type");

        Hashtable allEntityTypesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
        allEntityTypesHashtable.remove("Data Base");
        allEntityTypesHashtable.remove("Basic-entity-types");
        Hashtable allEntitiesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");

        Enumeration allEntityTypesHashtableEnum = allEntityTypesHashtable.keys();
        while (allEntityTypesHashtableEnum.hasMoreElements())
        {
            String entitytype = allEntityTypesHashtableEnum.nextElement().toString();
            QueryUsersHashtable.addFieldInUserModelHashtable(entityTypefield, entitytype);
        }

        Enumeration allEntitiesHashtableEnum = allEntitiesHashtable.keys();
        while (allEntitiesHashtableEnum.hasMoreElements())
        {
            String entity = allEntitiesHashtableEnum.nextElement().toString();
            QueryUsersHashtable.addFieldInUserModelHashtable(entityfield, entity);
        }

    		}
		*/


	//////////////////////////////////////////////////////////////////////
	//  UserModel Hashtable methods for Stories
	//////////////////////////////////////////////////////////////////////

	/** Create the main user-model hashtable. (for initialisation purposes only) */
	public static void createMainUserModelStoryHashtable() 
	{
		mainUserModelStoryHashtable = new Hashtable();
	} // create�ainUserModelHashtable


	/**
	 *  This method is invoked when a new user entry is created (newname)
	 */
	public static void addUserInUserModelStoryHashtable(String newname) 
	{
	  //if (mainUserModelHashtable.size() != 0)
	  //{
	  Enumeration mainUserModelStoryHashtableEnumKeys = mainUserModelStoryHashtable.keys();
	  Enumeration mainUserModelStoryHashtableEnumElements = mainUserModelStoryHashtable.elements();
	  while (mainUserModelStoryHashtableEnumKeys.hasMoreElements())
	  {
	    // "owner" is entity-type or entity
	    String owner = mainUserModelStoryHashtableEnumKeys.nextElement().toString();
	    Hashtable ownerHashtable = (Hashtable)mainUserModelStoryHashtableEnumElements.nextElement();
	    Enumeration ownerHashtableEnumKeys = ownerHashtable.keys();
	    Enumeration ownerHashtableEnumElements = ownerHashtable.elements();
	    while (ownerHashtableEnumKeys.hasMoreElements())
	    {
	      String storyname = ownerHashtableEnumKeys.nextElement().toString();
	      Hashtable storynameHashtable = (Hashtable)ownerHashtableEnumElements.nextElement();
	      storynameHashtable.put(newname, new Vector());
	      Vector vec = (Vector)storynameHashtable.get(newname);
	      vec.addElement("0");
	      vec.addElement("0");
	      vec.addElement("0");
			}
		}
    //}
	}


	/**
	 *  This method is invoked when a user entry (oldname) is renamed (newname)
	 */
	public static void renameUserInUserModelStoryHashtable(String oldname, String newname) 
	{
	  if (!oldname.equalsIgnoreCase(newname))
	  {
      Enumeration mainUserModelStoryHashtableEnumKeys = mainUserModelStoryHashtable.keys();
      Enumeration mainUserModelStoryHashtableEnumElements = mainUserModelStoryHashtable.elements();
      while (mainUserModelStoryHashtableEnumKeys.hasMoreElements())
      {
        // "owner" is entity-type or entity
        String owner = mainUserModelStoryHashtableEnumKeys.nextElement().toString();
        Hashtable ownerHashtable = (Hashtable)mainUserModelStoryHashtableEnumElements.nextElement();
        Enumeration ownerHashtableEnumKeys = ownerHashtable.keys();
        Enumeration ownerHashtableEnumElements = ownerHashtable.elements();
        while (ownerHashtableEnumKeys.hasMoreElements())
        {
          String storyname = ownerHashtableEnumKeys.nextElement().toString();
          Hashtable storynameHashtable = (Hashtable)ownerHashtableEnumElements.nextElement();
          if (storynameHashtable.containsKey(oldname))
          {
            Vector oldnameVector = (Vector)storynameHashtable.get(oldname);
            storynameHashtable.put(newname, oldnameVector);
            storynameHashtable.remove(oldname);
					}
    		}
			}
		}
	}


	/**
	 *  This method is invoked when a new user entry is removed (username)
	 */
	public static void removeUserInUserModelStoryHashtable(String username) 
	{
	  Enumeration mainUserModelStoryHashtableEnumKeys = mainUserModelStoryHashtable.keys();
	  Enumeration mainUserModelStoryHashtableEnumElements = mainUserModelStoryHashtable.elements();
	  while (mainUserModelStoryHashtableEnumKeys.hasMoreElements())
	  {
	    // "owner" is entity-type or entity
	    String owner = mainUserModelStoryHashtableEnumKeys.nextElement().toString();
	    Hashtable ownerHashtable = (Hashtable)mainUserModelStoryHashtableEnumElements.nextElement();
	    Enumeration ownerHashtableEnumKeys = ownerHashtable.keys();
	    Enumeration ownerHashtableEnumElements = ownerHashtable.elements();
	    while (ownerHashtableEnumKeys.hasMoreElements())
	    {
	      String storyname = ownerHashtableEnumKeys.nextElement().toString();
	      Hashtable storynameHashtable = (Hashtable)ownerHashtableEnumElements.nextElement();
	      if (storynameHashtable.containsKey(username))
	      {
          storynameHashtable.remove(username);
      	}
      }
  	}
	}


	/**
	 *  This method is invoked when a new story entry is created (storyname)
	 */
	public static void addStoryInUserModelStoryHashtable(String storyname, String nodename) 
	{
	  Hashtable nodenameUMStoryHashtable = (Hashtable)mainUserModelStoryHashtable.get(nodename);
	  nodenameUMStoryHashtable.put(storyname, new Hashtable());
	  Hashtable storynameHashtable = (Hashtable)nodenameUMStoryHashtable.get(storyname);

    // add users into story hashtable
    Vector allUsersVector = (Vector)QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
    Enumeration allUsersVectorEnum = allUsersVector.elements();
    while (allUsersVectorEnum.hasMoreElements())
    {
	    String user = allUsersVectorEnum.nextElement().toString();
	    storynameHashtable.put(user, new Vector());
	    Vector vec = (Vector)storynameHashtable.get(user);
	    vec.addElement("0");
	    vec.addElement("0");
	    vec.addElement("0");
		}
	}


	/**
	 *  This method is invoked when a story name (oldname) is renamed (newname) for a particular storynode (storynode)
	 */
	public static void renameStoryInUserModelStoryHashtable(String storynode, String oldname, String newname) 
	{
	  if (!oldname.equalsIgnoreCase(newname))
	  {
	    Hashtable storynodeHashtable = (Hashtable)mainUserModelStoryHashtable.get(storynode);
	    if (storynodeHashtable.containsKey(oldname))
	    {
	      Hashtable oldstoryHashtable = (Hashtable)storynodeHashtable.get(oldname);
	      storynodeHashtable.put(newname, oldstoryHashtable);
	      storynodeHashtable.remove(oldname);
      }
  	}
	}


	/**
	 *  This method is invoked when a story name (storyname) is removed for a particular storynode (storynode)
	 */
	public static void removeStoryInUserModelStoryHashtable(String storynode, String storyname) 
	{
		Hashtable storynodeHashtable = (Hashtable)mainUserModelStoryHashtable.get(storynode);
		if (storynodeHashtable.containsKey(storyname))
		{
			storynodeHashtable.remove(storyname);
		}
	}


	/**
	 *  This method is invoked when a new entity-type or entity is created (entitytypeOrEntityname)
	 */
	public static void addEntityTypeOrEntityInUserModelStoryHashtable(String entitytypeOrEntityname) 
	{
		mainUserModelStoryHashtable.put(entitytypeOrEntityname, new Hashtable());
	}



	/**
	 *  This method is invoked when an entity-type or entity (oldname) is renamed (newname)
	 */
	public static void renameEntityTypeOrEntityInUserModelStoryHashtable(String oldname, String newname) 
	{
		if (!oldname.equalsIgnoreCase(newname))
		{
			if (mainUserModelStoryHashtable.containsKey(oldname))
			{
				Hashtable oldnameHashtable = (Hashtable)mainUserModelStoryHashtable.get(oldname);
				mainUserModelStoryHashtable.put(newname, oldnameHashtable);
				mainUserModelStoryHashtable.remove(oldname);
			}
		}
	}


	/**
	 *  This method is invoked when a new entity-type or entity is removed (nodename)
	 */
  public static void removeEntityTypeOrEntityInUserModelStoryHashtable(String nodename) 
  {
		if (mainUserModelStoryHashtable.containsKey(nodename))
		{
		  mainUserModelStoryHashtable.remove(nodename);
		}
  }


	/**
	 *  This method is invoked when the user-modelling info (value), of a parameter (valueID)
	 *  for a user-type (username), for a story (storyname), of a story node (nodename)
	 *  is changed
	 */
	public static void updateUserModelStoryParameters(String storyname, String nodename, String username, int valueID, String value) 
	{
	  Hashtable nodenameHashtable = (Hashtable)mainUserModelStoryHashtable.get(nodename);
	  Hashtable storynameHashtable = (Hashtable)nodenameHashtable.get(storyname);
	  Vector usernameVector = (Vector)storynameHashtable.get(username);
	  usernameVector.setElementAt(value, valueID);
	}


	/**
	 *  This method is invoked from the UserModelStoryDialog when a UserModelStoryDialog.UserModelStoryPanel is created
	 *  for each user (username) in a story (storyname) of a node (nodename)
	 */
	public static Vector getUserModelStoryValuesVector(String storyname, String nodename, String username) 
	{
	  Hashtable nodenameHashtable = (Hashtable)mainUserModelStoryHashtable.get(nodename);
	  Hashtable storynameHashtable = (Hashtable)nodenameHashtable.get(storyname);
	  Vector usernameVector = (Vector)storynameHashtable.get(username);
	  return usernameVector;
	}

    public static void createmainRobotsModelHashtable() {
       mainRobotsModelHashtable = new Hashtable();
    }

    public static void fillMainRobotsModelHashtable() {
        Enumeration keys=mainUserModelHashtable.keys();
        while(keys.hasMoreElements()){
            String nextKey=keys.nextElement().toString();
            mainRobotsModelHashtable.put(nextKey, new Hashtable());
            Hashtable robotsHash=(Hashtable) mainRobotsModelHashtable.get(nextKey);
            //Hashtable nextProperty=null;
            Hashtable nextProperty=(Hashtable) mainUserModelHashtable.get(nextKey);
            Enumeration entitiesAndTypes=nextProperty.keys();
            while(entitiesAndTypes.hasMoreElements()){
                robotsHash.put(entitiesAndTypes.nextElement().toString(), new Hashtable());
            }
            mainUserModelHashtable.put("ClassOrInd", new Hashtable());
            Hashtable classOrInd=(Hashtable) mainUserModelHashtable.get("ClassOrInd");
            Enumeration types=QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type").keys();
            while(types.hasMoreElements()){
                String next=types.nextElement().toString();
                if(!next.equalsIgnoreCase("data base")&&!next.equalsIgnoreCase("Basic-entity-types"))
                classOrInd.put(next, new Hashtable());
            }
            Enumeration entities=QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity").keys();
            while(entities.hasMoreElements()){
                classOrInd.put(entities.nextElement().toString(), new Hashtable());
            }
        }}
        
    /*    for(Enumeration properties=QueryHashtable.propertiesHashtable.keys();properties.hasMoreElements();){
            
            String nextProp=properties.nextElement().toString();
            mainRobotsModelHashtable.put(nextProp, new Hashtable());
            Hashtable prop=(Hashtable)mainRobotsModelHashtable.get(nextProp);
        Enumeration allNodes = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type").keys();
		//Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic").keys();
		
		//if (mainUserModelHashtable.size() != 0)
		//{
		//Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
		//Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
		while (allNodes.hasMoreElements())
		{
            String next=allNodes.nextElement().toString();
                    if(next.equals("Data Base")||next.equals("Basic-entity-types"))
                continue;
            prop.put(next, new Hashtable());
                }
	
			
         
        allNodes = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic").keys();
		//Hashtable allEntities = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic").keys();
		
		//if (mainUserModelHashtable.size() != 0)
		//{
		//Enumeration mainRobotsModelHashtableEnumKeys = mainRobotsModelHashtable.keys();
		//Enumeration mainRobotsModelHashtableEnumElements = mainRobotsModelHashtable.elements();
		while (allNodes.hasMoreElements())
		{
            prop.put(allNodes.nextElement().toString(), new Hashtable());
                }
        }
    }*/

    static void updateRobotsCharModelParameters(String field, String node, String username, int i, String string) {
       // throw new UnsupportedOperationException("Not yet implemented");
    }

}//class QueryUserHashtable