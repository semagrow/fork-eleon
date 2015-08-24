/***************

<p>Title: </p>

<p>Description:

</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2015 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

***************/


package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.authoring.*;
import gr.demokritos.iit.eleon.ui.KButton;
import gr.demokritos.iit.eleon.ui.KLabel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
//import java.lang.reflect.Array;
import java.lang.*;
import java.io.*;
import java.lang.Object.*;


public class QueryLexiconHashtable extends Object implements Serializable 
{
	 Hashtable currentValues;
	Hashtable currentTables;
	
	String hashtableEntry;
	String field;
	String currentValue;
	String currentSpecialValue;
	
	//Font panelFont= new Font(Mpiro.selectedFont,Font.BOLD,11);
	//Dimension leftMargin = new Dimension(150,10);
	public Hashtable mainLexiconHashtable;
	Hashtable currentNounHashtable;
	Hashtable currentVerbHashtable;
	Hashtable currentHashtable;
	Hashtable currentHashtableLanguage;
	//public Hashtable defaultHashtable;
	
	//	 Hashtable tempHashtableName;
	//KLabel labelEN;
	//KButton getItalian;
	//KButton getGreek;
	//KButton moreButton;
	//JPanel centerPanel;
	//JPanel panelMain;
	//JPanel panelAdvanced;
	//JPanel linePanel[] = null;
	
	/** Create the main lexicon hashtable. (for initialisation purposes only) */
	public QueryLexiconHashtable() 
	{
		mainLexiconHashtable = new Hashtable();
		mainLexiconHashtable.put("Nouns", new Hashtable());
		mainLexiconHashtable.put("Verbs", new Hashtable());
	} //

    public QueryLexiconHashtable(Hashtable lexHash)
	{
		mainLexiconHashtable = lexHash;

	}

	/**
	 * This method checks the mainLexiconHashtable for name replication.
	 * It will be used in methods:
	 * createNoun(), createVerb(),
	 * and in KDialog (option: "rename").
	 */
	public int checkLexiconName(String nodeName) 
	{
		int check = 0;
		
		if (nodeName.indexOf(" ") >= 0) 
		{
			check = 3;				 // Empty space is not allowed!
		}
		else if (nodeName.equalsIgnoreCase(""))
		{
			check = 2;				 // Have to specify a node-name!
		}
		else
		{
			//if (VerbOrNoun.compareToIgnoreCase("Nouns") == 0) {
			currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
			Enumeration enu1 = currentNounHashtable.keys();
			while (enu1.hasMoreElements()) 
			{
				String keyName = enu1.nextElement().toString();
				if (keyName.equalsIgnoreCase(nodeName))
				{
					check = 1;  // This node-name already exists!
				}
			}
			
			//} else if (VerbOrNoun.compareToIgnoreCase("Verbs") == 0) {
			currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
			Enumeration enu2 = currentVerbHashtable.keys();
			while (enu2.hasMoreElements()) 
			{
				String keyName = enu2.nextElement().toString();
				if (keyName.equalsIgnoreCase(nodeName))
				{
					check = 1;  // This node-name already exists!
				}
			}
			//}
		}
		return check;
	}


	public void createDefaultLexiconNoun(String name, Vector usersVector) 
	{
		currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
		currentNounHashtable.put(name, new Hashtable());
		currentHashtable = (Hashtable)currentNounHashtable.get(name);
		currentHashtable.put("Independent",  new Hashtable());
		currentHashtable.put("English",  new Hashtable());
		currentHashtable.put("Italian", new Hashtable());
		currentHashtable.put("Greek", new Hashtable());
		
		Hashtable currentNounIndependentHashtable = (Hashtable)currentHashtable.get("Independent");
		Hashtable currentNounEnglishHashtable = (Hashtable)currentHashtable.get("English");
		Hashtable currentNounItalianHashtable = (Hashtable)currentHashtable.get("Italian");
		Hashtable currentNounGreekHashtable = (Hashtable)currentHashtable.get("Greek");
		
		
		currentNounEnglishHashtable.put("enbasetext", "");
		currentNounEnglishHashtable.put("countable", "Yes");
		currentNounEnglishHashtable.put("enpluraltext", "");
		currentNounEnglishHashtable.put("encb", "false");
		
		currentNounItalianHashtable.put("itbasetext", "");
		currentNounItalianHashtable.put("itgender", "Masculine");
		currentNounItalianHashtable.put("countable", "Yes");
		currentNounItalianHashtable.put("itpluraltext", "");
		currentNounItalianHashtable.put("itcb", "false");
		
		currentNounGreekHashtable.put("grbasetext", "");
		currentNounGreekHashtable.put("grpluraltext", "");
		currentNounGreekHashtable.put("grgender", "Neuter");
		currentNounGreekHashtable.put("countable", "Yes");
		currentNounGreekHashtable.put("grinflection", "Inflected");
		currentNounGreekHashtable.put("cb1", "false");
		currentNounGreekHashtable.put("cb2", "false");
		currentNounGreekHashtable.put("cb3", "false");
		currentNounGreekHashtable.put("cb4", "false");
		currentNounGreekHashtable.put("cb5", "false");
		currentNounGreekHashtable.put("cb6", "false");
		currentNounGreekHashtable.put("grsntext", "");
		currentNounGreekHashtable.put("grsgtext", "");
		currentNounGreekHashtable.put("grsatext", "");
		currentNounGreekHashtable.put("grpntext", "");
		currentNounGreekHashtable.put("grpgtext", "");
		currentNounGreekHashtable.put("grpatext", "");
		
		
		Enumeration usersVectorEnum = usersVector.elements();
		while (usersVectorEnum.hasMoreElements())
		{
			String user = usersVectorEnum.nextElement().toString();
			currentNounIndependentHashtable.put(user, "0");
		}
		//currentHashtable = (Hashtable)mainLexiconHashtable.get(defaultHashtable);
		// System.out.println((Hashtable)mainLexiconHashtable.get("defaultHashtable"));
	}


	public void createDefaultLexiconVerb(String name, Vector usersVector) 
	{
		currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
		currentVerbHashtable.put(name, new Hashtable());
		currentHashtable = (Hashtable)currentVerbHashtable.get(name);
		currentHashtable.put("Independent",  new Hashtable());
		currentHashtable.put("English",  new Hashtable());
		currentHashtable.put("Italian", new Hashtable());
		currentHashtable.put("Greek", new Hashtable());
		
		Hashtable currentVerbIndependentHashtable = (Hashtable)currentHashtable.get("Independent");
		Hashtable currentVerbEnglishHashtable = (Hashtable)currentHashtable.get("English");
		Hashtable currentVerbItalianHashtable = (Hashtable)currentHashtable.get("Italian");
		Hashtable currentVerbGreekHashtable = (Hashtable)currentHashtable.get("Greek");
		
		currentVerbEnglishHashtable.put("vbasetext", "");
		currentVerbEnglishHashtable.put("transitive", "Yes");
		currentVerbEnglishHashtable.put("vcb1", "false");
		currentVerbEnglishHashtable.put("vcb2", "false");
		currentVerbEnglishHashtable.put("vcb3", "false");
		currentVerbEnglishHashtable.put("vcb4", "false");
		currentVerbEnglishHashtable.put("thirdpstext", "");
		currentVerbEnglishHashtable.put("sipatext", "");
		currentVerbEnglishHashtable.put("prpatext", "");
		currentVerbEnglishHashtable.put("papatext", "");

    currentVerbItalianHashtable.put("vbasetext", "");
    currentVerbItalianHashtable.put("transitive", "Yes");
    currentVerbItalianHashtable.put("vTable", new LexiconDefaultVector("verb-italian-1"));
    currentVerbItalianHashtable.put("pTable", new LexiconDefaultVector("verb-italian-2"));

    currentVerbGreekHashtable.put("vbasetext", "");
    currentVerbGreekHashtable.put("vbasetext2", "");
    currentVerbGreekHashtable.put("transitive", "Yes");
    currentVerbGreekHashtable.put("vTable", new LexiconDefaultVector("verb-greek-1"));
    currentVerbGreekHashtable.put("pTable", new LexiconDefaultVector("verb-greek-2"));
    currentVerbGreekHashtable.put("infText", "");
    currentVerbGreekHashtable.put("cb1", "false");
    currentVerbGreekHashtable.put("infText2", "");
    currentVerbGreekHashtable.put("cb2", "false");
    currentVerbGreekHashtable.put("apText", "");
    currentVerbGreekHashtable.put("cb3", "false");

		
		Enumeration usersVectorEnum = usersVector.elements();
		while (usersVectorEnum.hasMoreElements())
		{
			String user = usersVectorEnum.nextElement().toString();
		  currentVerbIndependentHashtable.put(user, "0");
		}
	}


	/**
	 *  This method creates the default information for a lexicon entry (name).
	 *  It is called when the lexicon entry is created.
	 */
	/* public void createLexiconEntry(String name) 
		{
		  String keyStringName = name;
		  String verbOrNoun = keyStringName.substring(keyStringName.length()-4, keyStringName.length());
		  if (verbOrNoun.equalsIgnoreCase("noun")) 
		  {
		      createDefaultLexiconNoun(name);
		  } else if (verbOrNoun.equalsIgnoreCase("verb")) 
		  {
		      createDefaultLexiconVerb(name);
		  }
		  //System.out.println(keyStringName);///////////////////////////////////////////
		  //System.out.println(verbOrNoun);////////////////////////////////////////////////////
		}
	*/


	/**
	 *  This method updates the hashtable for a lexicon entry (name), language (language), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
	public void updateLexiconEntryNoun(String name, String language, String attribute, String attributeValue) 
	{
		currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
		currentHashtable = (Hashtable)currentNounHashtable.get(name);
		currentHashtableLanguage = (Hashtable)currentHashtable.get(language);
		currentHashtableLanguage.put(attribute, attributeValue);
	}


	/**
	 *  This method updates the hashtable for a lexicon entry (name), language (language), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
	public void updateLexiconEntryNoun(String name, String language, String attribute, Vector attributeValue) 
	{
		currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
		currentHashtable = (Hashtable)currentVerbHashtable.get(name);
		currentHashtableLanguage = (Hashtable)currentHashtable.get(language);
		currentHashtableLanguage.put(attribute, attributeValue);
	}


	/**
	 *  This method updates the hashtable for a lexicon entry (name), language (language), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
	public void updateLexiconEntryVerb(String name, String language, String attribute, String attributeValue) 
	{
	  currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
	  currentHashtable = (Hashtable)currentNounHashtable.get(name);
	  currentHashtableLanguage = (Hashtable)currentHashtable.get(language);
	  currentHashtableLanguage.put(attribute, attributeValue);
	}


	/**
	 *  This method updates the hashtable for a lexicon entry (name), language (language), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
	public void updateLexiconEntryVerb(String name, String language, String attribute, Vector attributeValue) 
	{
	  currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
	  currentHashtable = (Hashtable)currentVerbHashtable.get(name);
	  currentHashtableLanguage = (Hashtable)currentHashtable.get(language);
	  currentHashtableLanguage.put(attribute, attributeValue);
	}


	/**
	 *  This method updates the hashtable for a lexicon entry (name), language (language), attribute (attribute),
	 *  storing the attribute value (attributeValue). {(info)}
	 */
	/* public void updateLexiconEntryUserInfo(String name, String language, String attribute, String attributeValue) 
	   {
		    String keyStringName = name;
		    String verbOrNoun = keyStringName.substring(keyStringName.length()-4, keyStringName.length());
		    if (verbOrNoun.equalsIgnoreCase("noun")) 
		    {
		        createDefaultLexiconNoun(name);
		    } 
		    else if (verbOrNoun.equalsIgnoreCase("verb")) 
		    {
		        createDefaultLexiconVerb(name);
		    }
		    currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
		    currentHashtable = (Hashtable)currentNounHashtable.get(name);
		    currentHashtableLanguage = (Hashtable)currentHashtable.get(language);
		    currentHashtableLanguage.put(attribute, attributeValue);
	    }
	*/


	/**
	 *  This method is invoked when a lexicon entry (oldname) is renamed (newname)
	 */
	public void renameLexiconEntry(String oldname, String newname, Hashtable mainDB, Hashtable entityTypesHashtable)
	{
	  currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
	  currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");

	  if (currentNounHashtable.containsKey(oldname)) 
	  {
	    Hashtable oldnameHashtable = (Hashtable)currentNounHashtable.get(oldname);
	    currentNounHashtable.put(newname, oldnameHashtable);
	    currentNounHashtable.remove(oldname);
	    renameNounFromNounVectors(oldname, newname, mainDB, entityTypesHashtable);
		} 
    else if (currentVerbHashtable.containsKey(oldname)) 
    {
	    Hashtable oldnameHashtable = (Hashtable)currentVerbHashtable.get(oldname);
	    currentVerbHashtable.put(newname, oldnameHashtable);
	    currentVerbHashtable.remove(oldname);
	    renameVerbFromMicroplanning(oldname, newname, mainDB, entityTypesHashtable);
		}
		//System.out.println(mainLexiconHashtable.entrySet());
	}


	/**
	 *  This method is invoked when an entity-type (name) is removed
	 */
	public void removeLexiconEntry(String name, Hashtable mainDB, Hashtable entityTypesHashtable) 
	{
	  currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
	  currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
	  if (currentNounHashtable.containsKey(name)) 
	  {
      currentNounHashtable.remove(name);
      removeNounFromNounVectors(name, mainDB, entityTypesHashtable);
		} 
		else if (currentVerbHashtable.containsKey(name)) 
		{
      currentVerbHashtable.remove(name);
      removeVerbFromMicroplanning(name, mainDB, entityTypesHashtable);
		}
	}


	/**
	 *  The method that gets the values from the lexicon entry (name) hashtable,
	 *  for a specific language (language) and shows them.
	 *  The values are displayed on the lexicon right hand side space.
	 */
	public Hashtable showValues(String name, String language) 
	{
		//System.out.println("current lexicon entry = " + name);
		//System.out.println("current language      = " + language);
		
		currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
		currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
		
		if (currentNounHashtable.containsKey(name)) 
		{
			currentHashtable = (Hashtable)currentNounHashtable.get(name);
			currentHashtableLanguage = (Hashtable)currentHashtable.get(language);
		} 
		else if (currentVerbHashtable.containsKey(name)) 
		{
			currentHashtable = (Hashtable)currentVerbHashtable.get(name);
			currentHashtableLanguage = (Hashtable)currentHashtable.get(language);
		}
		currentValues = new Hashtable();
		currentValues = currentHashtableLanguage;
		
		//System.out.println("Final values found from hashtable:  " + currentValues);
		return currentValues;
	} //showValues



	/**
	 *  Returns a vector of all *nouns* (sorted in alphabetical order) currently saved
	 *  It is used when loading a new domain
	 */
	public Vector getNounsVectorFromMainLexiconHashtable() 
	{
		currentNounHashtable = (Hashtable)mainLexiconHashtable.get("Nouns");
		//System.out.println(currentNounHashtable.toString());
		Vector tempVector = new Vector();
		Enumeration enumer = currentNounHashtable.keys();
		while (enumer.hasMoreElements()) 
		{
			tempVector.addElement(enumer.nextElement().toString());
		}
		Vector nounsVector = new Vector();
		if (tempVector.isEmpty() == true) 
		{
			//nounsVector = null;
		} 
		else 
		{
			nounsVector = QuickSort.quickSort(0, tempVector.size()-1, tempVector);
		}
		//System.out.println("nounsVector: " + nounsVector);
		return nounsVector;
	}


	/**
	 *  Returns a vector of all *verbs* (sorted in alphabetical order) currently saved
	 *  It is used when loading a new domain
	 */
	public Vector getVerbsVectorFromMainLexiconHashtable() 
	{
		currentVerbHashtable = (Hashtable)mainLexiconHashtable.get("Verbs");
		//System.out.println(currentVerbHashtable.toString());
		Vector tempVector = new Vector();
		Enumeration enumer = currentVerbHashtable.keys();
		while (enumer.hasMoreElements()) 
		{
			tempVector.addElement(enumer.nextElement().toString());
		}
		Vector verbsVector = new Vector();
		if (tempVector.isEmpty() == true) 
		{
			//verbsVector = null;
		} 
		else 
		{
			verbsVector = QuickSort.quickSort(0, tempVector.size()-1, tempVector);
		}
		//System.out.println("verbsVector: " + verbsVector.toString());
		return verbsVector;
	}


	/**
	 *  Called when a verb is renamed in the Lexicon
	 *  If the verb (oldVerbName) was selected in any MicroPlanning tabs, it is renamed (newVerbName) also
	 */
	public void renameVerbFromMicroplanning(String oldVerbName, String newVerbName, Hashtable mainDB, Hashtable entityTypesHashtable) 
	{
		currentHashtable = mainDB;
		
		
		entityTypesHashtable.remove("Data Base");
		entityTypesHashtable.remove("Basic-entity-types");
		
		//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) entityTypesHashtable= " + entityTypesHashtable);
		
		NodeVector nodeVector;
		Hashtable microPlanning;
		
		for (Enumeration enumer = entityTypesHashtable.keys(); enumer.hasMoreElements(); ) 
		{
			String node = enumer.nextElement().toString();
			//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current node= " + node);
			nodeVector = (NodeVector)currentHashtable.get(node);
			microPlanning = (Hashtable)nodeVector.get(5);
			//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			for (Enumeration k = microPlanning.keys(), e = microPlanning.elements(); e.hasMoreElements(); ) 
			{
				String key = k.nextElement().toString();
				String value = e.nextElement().toString();
				//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key&value= " + key + " = " + value);
				if (value.equalsIgnoreCase(oldVerbName)) 
				{
					//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key= " + key);
					microPlanning.put(key, newVerbName);
				}
				else
				{
					//System.out.println("(QueryLexiconHashtable.renameVerbFromMicroplanning) NO VALUE FOUND");
				}
				//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			}
		}
	}//renameVerbFromMicroplanning



	/**
	 *  Called when a verb is removed in the Lexicon
	 *  If the verb (verbName) was selected in any MicroPlanning tabs, it is removed
	 */
	public void removeVerbFromMicroplanning(String verbName, Hashtable mainDB, Hashtable entityTypesHashtable) 
	{
		currentHashtable = mainDB;
		
		
		entityTypesHashtable.remove("Data Base");
		entityTypesHashtable.remove("Basic-entity-types");
		
		// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) entityTypesHashtable= " + entityTypesHashtable);
		
		NodeVector nodeVector;
		Hashtable microPlanning;
		
		for (Enumeration enumer = entityTypesHashtable.keys(); enumer.hasMoreElements(); ) 
		{
			String node = enumer.nextElement().toString();
			// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current node= " + node);
			nodeVector = (NodeVector)currentHashtable.get(node);
			microPlanning = (Hashtable)nodeVector.get(5);
			// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			for (Enumeration k = microPlanning.keys(), e = microPlanning.elements(); e.hasMoreElements(); ) 
			{
				String key = k.nextElement().toString();
				String value = e.nextElement().toString();
				// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key&value= " + key + " = " + value);
				if (value.equalsIgnoreCase(verbName)) 
				{
					// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key= " + key);
					microPlanning.remove(key);
				}
				else
				{
					// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) NO VALUE FOUND");
				}
				// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			}
		}
	}//removeVerbFromMicroplanning


	/**
	 *  Called when a noun is renamed in the Lexicon
	 *  If the noun (oldNounName) was selected in any noun field, it is renamed (newNounName) also
	 */
	public void renameNounFromNounVectors(String oldNounName, String newNounName, Hashtable mainDB, Hashtable entityTypesHashtable) 
	{
		currentHashtable =mainDB;
		
		entityTypesHashtable.remove("Data Base");
		entityTypesHashtable.remove("Basic-entity-types");
		
		//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) entityTypesHashtable= " + entityTypesHashtable);
		
		NodeVector nodeVector;
		Vector nounVector;
		
		for (Enumeration enumer = entityTypesHashtable.keys(); enumer.hasMoreElements(); ) 
		{
			String node = enumer.nextElement().toString();
			// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current node= " + node);
			nodeVector = (NodeVector)currentHashtable.get(node);
			nounVector = (Vector)nodeVector.get(2);
			// System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			for (Enumeration e = nounVector.elements(); e.hasMoreElements(); ) 
			{
				String element = e.nextElement().toString();
				
				//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key&value= " + key + " = " + value);
				if (element.equalsIgnoreCase(oldNounName)) 
				{
					int i = nounVector.indexOf(element);
					//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key= " + key);
					nounVector.insertElementAt(newNounName, i);
					nounVector.removeElementAt(i+1);
				}
				else
				{
					//System.out.println("(QueryLexiconHashtable.renameNounFromNounVectors) NO VALUE FOUND");
				}
				//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			}
		}
	}//renameVerbFromMicroplanning



	/**
	 *  Called when a noun is removed in the Lexicon
	 *  If the noun (nounName) was selected in any noun fields, it is removed
	 */
	public void removeNounFromNounVectors(String nounName, Hashtable mainDB, Hashtable entityTypesHashtable) 
	{
		currentHashtable = mainDB;
		
		entityTypesHashtable.remove("Data Base");
		entityTypesHashtable.remove("Basic-entity-types");
		
		//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) entityTypesHashtable= " + entityTypesHashtable);
		
		NodeVector nodeVector;
		Vector nounVector;
		
		for (Enumeration enumer = entityTypesHashtable.keys(); enumer.hasMoreElements(); ) 
		{
			String node = enumer.nextElement().toString();
			//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current node= " + node);
			nodeVector = (NodeVector)currentHashtable.get(node);
			nounVector = (Vector)nodeVector.get(2);
			//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			for (Enumeration e = nounVector.elements(); e.hasMoreElements(); ) 
			{
				String element = e.nextElement().toString();
				
				//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key&value= " + key + " = " + value);
				if (element.equalsIgnoreCase(nounName)) 
				{
					int i = nounVector.indexOf(element);
					//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current key= " + key);
					nounVector.removeElementAt(i);
				}
				else
				{
					//System.out.println("(QueryLexiconHashtable.removeNounFromNounVectors) NO VALUE FOUND");
				}
				//System.out.println("(QueryLexiconHashtable.removeVerbFromMicroplanning) current micro= " + microPlanning.toString());
			}
		}
	}//removeVerbFromMicroplanning


  public JTable createDefaultTable(String tableType)
  {
	  String tense_tabletext = LangResources.getString(Mpiro.selectedLocale, "tense_tabletext");
	  String tenseAspect_tabletext = LangResources.getString(Mpiro.selectedLocale, "tenseAspect_tabletext");
	  String voice_tabletext = LangResources.getString(Mpiro.selectedLocale, "voice_tabletext");
	  String number_tabletext = LangResources.getString(Mpiro.selectedLocale, "number_tabletext");
	  String person_tabletext = LangResources.getString(Mpiro.selectedLocale, "person_tabletext");
	  String gender_tabletext = LangResources.getString(Mpiro.selectedLocale, "gender_tabletext");
	
	  String present_tabletext = LangResources.getString(Mpiro.selectedLocale, "present_tabletext");
	  String pastContinuous_tabletext = LangResources.getString(Mpiro.selectedLocale, "pastContinuous_tabletext");
	  String remotePast_tabletext = LangResources.getString(Mpiro.selectedLocale, "remotePast_tabletext");
	  String presentProgressive_tabletext = LangResources.getString(Mpiro.selectedLocale, "presentProgressive_tabletext");
	  String pastProgressive_tabletext = LangResources.getString(Mpiro.selectedLocale, "pastProgressive_tabletext");
	  String pastSimple_tabletext = LangResources.getString(Mpiro.selectedLocale, "pastSimple_tabletext");
	  String singular_tabletext = LangResources.getString(Mpiro.selectedLocale, "singular_tabletext");
	  String plural_tabletext = LangResources.getString(Mpiro.selectedLocale, "plural_tabletext");
	  String active_tabletext = LangResources.getString(Mpiro.selectedLocale, "active_tabletext");
	  String passive_tabletext = LangResources.getString(Mpiro.selectedLocale, "passive_tabletext");
	  String first_tabletext = LangResources.getString(Mpiro.selectedLocale, "first_tabletext");
	  String second_tabletext = LangResources.getString(Mpiro.selectedLocale, "second_tabletext");
	  String third_tabletext = LangResources.getString(Mpiro.selectedLocale, "third_tabletext");
	  String masculine_tabletext = LangResources.getString(Mpiro.selectedLocale, "masculine_tabletext");
	  String feminine_tabletext = LangResources.getString(Mpiro.selectedLocale, "feminine_tabletext");
	  String neuter_tabletext = LangResources.getString(Mpiro.selectedLocale, "neuter_tabletext");

    JTable defaultTable = null;
    if (tableType.equalsIgnoreCase("verb-italian-1"))
    {
	    String[][] row = 
	    {
	      {present_tabletext, singular_tabletext, first_tabletext},
	      {present_tabletext, singular_tabletext, second_tabletext},
	      {present_tabletext, singular_tabletext, third_tabletext},
	      {present_tabletext, plural_tabletext, first_tabletext},
	      {present_tabletext, plural_tabletext, second_tabletext},
	      {present_tabletext, plural_tabletext, third_tabletext},
	      {pastContinuous_tabletext, singular_tabletext, first_tabletext},
	      {pastContinuous_tabletext, singular_tabletext, second_tabletext},
	      {pastContinuous_tabletext, singular_tabletext, third_tabletext},
	      {pastContinuous_tabletext, plural_tabletext, first_tabletext},
	      {pastContinuous_tabletext, plural_tabletext, second_tabletext},
	      {pastContinuous_tabletext, plural_tabletext, third_tabletext},
	      {remotePast_tabletext, singular_tabletext, first_tabletext},
	      {remotePast_tabletext, singular_tabletext, second_tabletext},
	      {remotePast_tabletext, singular_tabletext, third_tabletext},
	      {remotePast_tabletext, plural_tabletext, first_tabletext},
	      {remotePast_tabletext, plural_tabletext, second_tabletext},
	      {remotePast_tabletext, plural_tabletext, third_tabletext}
	    };
      String[] column = {tenseAspect_tabletext, number_tabletext, person_tabletext};
      defaultTable = new JTable(row, column);
      defaultTable.setPreferredScrollableViewportSize(new Dimension(200, 288));
		}

    else if (tableType.equalsIgnoreCase("verb-italian-2"))
    {
      String[][] row = 
      {
        {masculine_tabletext, singular_tabletext},
        {masculine_tabletext, plural_tabletext},
        {feminine_tabletext, singular_tabletext},
        {feminine_tabletext, plural_tabletext}
      };

      String[] column = {gender_tabletext, number_tabletext};
      defaultTable = new JTable(row, column);
      defaultTable.setPreferredScrollableViewportSize(new Dimension(200, 65));
    }

    else if (tableType.equalsIgnoreCase("verb-greek-1"))
    {
      String[][] row = 
      {
	      {presentProgressive_tabletext, active_tabletext, singular_tabletext, first_tabletext},
	      {presentProgressive_tabletext, active_tabletext, singular_tabletext, second_tabletext},
	      {presentProgressive_tabletext, active_tabletext, singular_tabletext, third_tabletext},
	      {presentProgressive_tabletext, active_tabletext, plural_tabletext, first_tabletext},
	      {presentProgressive_tabletext, active_tabletext, plural_tabletext, second_tabletext},
	      {presentProgressive_tabletext, active_tabletext, plural_tabletext, third_tabletext},
	      {presentProgressive_tabletext, passive_tabletext, singular_tabletext, first_tabletext},
	      {presentProgressive_tabletext, passive_tabletext, singular_tabletext, second_tabletext},
	      {presentProgressive_tabletext, passive_tabletext, singular_tabletext, third_tabletext},
	      {presentProgressive_tabletext, passive_tabletext, plural_tabletext, first_tabletext},
	      {presentProgressive_tabletext, passive_tabletext, plural_tabletext, second_tabletext},
	      {presentProgressive_tabletext, passive_tabletext, plural_tabletext, third_tabletext},
	      {pastProgressive_tabletext, active_tabletext, singular_tabletext, first_tabletext},
	      {pastProgressive_tabletext, active_tabletext, singular_tabletext, second_tabletext},
	      {pastProgressive_tabletext, active_tabletext, singular_tabletext, third_tabletext},
	      {pastProgressive_tabletext, active_tabletext, plural_tabletext, first_tabletext},
	      {pastProgressive_tabletext, active_tabletext, plural_tabletext, second_tabletext},
	      {pastProgressive_tabletext, active_tabletext, plural_tabletext, third_tabletext},
	      {pastProgressive_tabletext, passive_tabletext, singular_tabletext, first_tabletext},
	      {pastProgressive_tabletext, passive_tabletext, singular_tabletext, second_tabletext},
	      {pastProgressive_tabletext, passive_tabletext, singular_tabletext, third_tabletext},
	      {pastProgressive_tabletext, passive_tabletext, plural_tabletext, first_tabletext},
	      {pastProgressive_tabletext, passive_tabletext, plural_tabletext, second_tabletext},
	      {pastProgressive_tabletext, passive_tabletext, plural_tabletext, third_tabletext},
	      {pastSimple_tabletext, active_tabletext, singular_tabletext, first_tabletext},
	      {pastSimple_tabletext, active_tabletext, singular_tabletext, second_tabletext},
	      {pastSimple_tabletext, active_tabletext, singular_tabletext, third_tabletext},
	      {pastSimple_tabletext, active_tabletext, plural_tabletext, first_tabletext},
	      {pastSimple_tabletext, active_tabletext, plural_tabletext, second_tabletext},
	      {pastSimple_tabletext, active_tabletext, plural_tabletext, third_tabletext},
	      {pastSimple_tabletext, passive_tabletext, singular_tabletext, first_tabletext},
	      {pastSimple_tabletext, passive_tabletext, singular_tabletext, second_tabletext},
	      {pastSimple_tabletext, passive_tabletext, singular_tabletext, third_tabletext},
	      {pastSimple_tabletext, passive_tabletext, plural_tabletext, first_tabletext},
	      {pastSimple_tabletext, passive_tabletext, plural_tabletext, second_tabletext},
	      {pastSimple_tabletext, passive_tabletext, plural_tabletext, third_tabletext}
			};
	    String[] column = {tense_tabletext, voice_tabletext, number_tabletext, person_tabletext};
	    defaultTable = new JTable(row, column);
	    defaultTable.setPreferredScrollableViewportSize(new Dimension(200, 577));
		}

    else if (tableType.equalsIgnoreCase("verb-greek-2"))
    {
      String[][] row = 
      {
        {masculine_tabletext},
        {feminine_tabletext},
        {neuter_tabletext}
      };

      String[] column = {gender_tabletext};
      defaultTable = new JTable(row, column);
      defaultTable.setPreferredScrollableViewportSize(new Dimension(200, 49));
    }
    defaultTable.setEnabled(false);
    return defaultTable;
	}

}


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
