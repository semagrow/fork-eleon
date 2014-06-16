//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.profiles.User;
import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;
import java.net.URL;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.reflect.Array;
import java.lang.String;

import gr.demokritos.iit.eleon.ui.ELEONWindow;
import gr.demokritos.iit.eleon.ui.MessageDialog;
import gr.demokritos.iit.eleon.um.*;

/**
 * <p>Title: ExportUtilsPServer</p>
 * <p>Description: The methods that export to the personalisation server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class ExportUtilsPServer 
{
	private static String urlString;
	//create an export progress monitor counter
	private static int counter;
	//private final UMAuthoring input;

	/**
	 * establishing a trial connection to the pserver to see if it works ok
	 * @param pserverIP the IP of the pers server
	 * @param pserverPort the port of the pers server
	 * @return "true" if ok, "false" if there is a problem
	 */
  public static boolean establishConnectionWithPServer(String pserverIP, String pserverPort) 
  {
	  urlString = "http://" + pserverIP + ":" + pserverPort + "/";
	  HttpURLConnection urlConn = null;
	  int rescode = 0;
	  String resMessage = "";
	
	  // Open connection & get response-code
	  try
    {
      URL url = new URL(urlString);
      urlConn = (HttpURLConnection)url.openConnection();
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      urlConn.setFollowRedirects(true);
      rescode = urlConn.getResponseCode();
      resMessage = urlConn.getResponseMessage();
    }
    catch(Exception e)
    {
    	System.err.println("[PageInfo] Exception: " + e + " URL: " + urlString);
    }
    //System.err.println("**************** RESTYPE: " + resType);

    /* Check if the webpage is an error page or not  */
    if  (rescode == HttpURLConnection.HTTP_OK)
    {
    	return true;
    }
    
    else
    {
			if ( (rescode == HttpURLConnection.HTTP_BAD_REQUEST) )
			{
				new MessageDialog(Mpiro.win.tabbedPane, "(ERROR 400) Bad Request: " + urlString
				                                    + "\n" + "Export aborted!");
			}
			
			else if ( (rescode == HttpURLConnection.HTTP_UNAUTHORIZED) )
			{
				new MessageDialog(Mpiro.win.tabbedPane, "(ERROR 401) Unauthorized: " + urlString
				                                    + "\n" + "Export aborted!");
			}
			
			else if ( (rescode == HttpURLConnection.HTTP_FORBIDDEN) )
			{
				new MessageDialog(Mpiro.win.tabbedPane, "(ERROR 403) Forbidden: " + urlString
				                                    + "\n" + "Export aborted!");
			}
			
			else if ( (rescode == HttpURLConnection.HTTP_NOT_FOUND) )
			{
				new MessageDialog(Mpiro.win.tabbedPane, "(ERROR 404) Page not found: " + urlString
				                                    + "\n" + "Export aborted!");
			}
			
			else if ( rescode == 0 )
			{
				new MessageDialog(Mpiro.win.tabbedPane,  "(ERROR) Cannot find the url: " + urlString
				                                    + "\n" + "Export aborted!");
			}
			
			else
			{
				new MessageDialog(Mpiro.win.tabbedPane,  "(ERROR " + rescode + ") " + urlString
				                                    + "\n" + "Please contact your administrator");
			}
			return false;
		}
	}



	/**
	 * The method that exports everything to the personalisation server
	 * @param pserverIP the IP of the server
	 * @param pserverPort the port of the server
	 * @throws UMException
	 */
  public static void exportToServer(String pserverIP, String pserverPort) throws UMException 
	{
		Hashtable allNounsHashtable = Mpiro.win.struc.getNounsHashtable();
		Hashtable allVerbsHashtable = Mpiro.win.struc.getVerbsHashtable();
		Vector allNounsVector = Mpiro.win.struc.getNounsVectorFromMainLexiconHashtable();
		Vector allVerbsVector = Mpiro.win.struc.getVerbsVectorFromMainLexiconHashtable();
		Hashtable allFieldsAndContainingEntityTypesHashtable = Mpiro.win.struc.returnAllFieldsAndContainingEntityTypes();
		
		Vector entitynamesVector = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity+Generic");
		Vector allUserTypesVector = (Vector)Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Vector allEntityTypesVector = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
		Vector allEntitiesVector = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity");
		Vector allGenericEntitiesVector = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Generic");
		
		InetAddress serverIP = null;
    try
    {
    	serverIP = InetAddress.getByName(pserverIP);
    }
    catch (Exception u)
    {
    	System.out.println("(ExportUtilsPServer.exportToServer)---- " + u);
    }

    Integer port = new Integer(pserverPort);
    int serverPort = port.intValue();

    UMAuthoring input = new UMAuthoringImp(serverIP, serverPort);
    UMVisit visit = new UMVisitImp(serverIP, serverPort);

    counter = 0;

		// Creating the Export progress monitor //////////////////////////////
		// Calculate the maximum value for the progress bar
		int maximumValue = 6;
		
		maximumValue = maximumValue + allNounsVector.size();
		//	maximumValue = maximumValue + allVerbsVector.size();
		maximumValue = maximumValue + allFieldsAndContainingEntityTypesHashtable.size();
		maximumValue = maximumValue + Mpiro.win.struc.mainUserModelHashtableSize();
		//maximumValue = maximumValue + QueryProfileHashtable.mainUserModelStoryHashtable.size();

		// Show the export progress monitor frame
		ExportProgressMonitor progress = new ExportProgressMonitor(0, maximumValue, 0);
		progress.exprimoOrEmulatorsLabel.setText(LangResources.getString(Mpiro.selectedLocale,"exportToPServer_text" ));	//maria
		progress.show();
		progress.updateOKButton(false);
		//progress.updateProgressLabel("Reseting the personalization server", 3);		//maria
		progress.updateProgressBar(++counter, 3);									//maria
        
    ///////////////////////////////////////////////////////////
    // initialize
    ///////////////////////////////////////////////////////////

    //
    // Construct the "entitynamesArray"
    //

    String[] entitynamesArray = new String[entitynamesVector.size()];
    for (int i=0; i<entitynamesVector.size(); i++)
    {
    	entitynamesArray[i] = entitynamesVector.get(i).toString().toLowerCase();
    }

    //System.out.println("()---- " + entitynamesArray.length);
    //for (int i=0; i<entitynamesArray.length; i++)
    //{
    //	System.out.println(i + " ()---- " + entitynamesArray[i]);
    //}

    //
    // Construct the "factIdsArray"
    //
    Vector factIdsVector = new Vector(); // first we put all factIds in a vector

    progress.updateProgressBar(++counter, 3);		//maria
    // (1)  Get all fields' fieldnames from the entities in mainUserModelHashtable
    Enumeration mainUserModelHashtableEnumKeys = Mpiro.win.struc.mainUserModelHashtableKeys();
    while (mainUserModelHashtableEnumKeys.hasMoreElements())
    {
      String fieldname = mainUserModelHashtableEnumKeys.nextElement().toString();
      Hashtable fieldnameHashtable = (Hashtable)Mpiro.win.struc.getPropertyFromMainUserModelHashtable(fieldname);
      Enumeration fieldnameHashtableEnumKeys = fieldnameHashtable.keys();
      while (fieldnameHashtableEnumKeys.hasMoreElements())
      {
	      String entitytypeOrEntityName = fieldnameHashtableEnumKeys.nextElement().toString();
	      if (entitynamesVector.contains(entitytypeOrEntityName)) //i.e. it is entity or generic-entity ONLY
	      {
					// Dimitris adding correcting code for
					//  "name" --> "called"  AND upper-model-words --> a-upper-model-words
					fieldname = ExportUtilsExprimo.inUpperModel(fieldname, true);
					
					if (fieldname.equalsIgnoreCase("name"))
          {
          	fieldname = "called";
          }
					// Dimitris end of correcting code
					
					factIdsVector.addElement("field:" + fieldname + ":" + entitytypeOrEntityName);
					//System.out.println("()---- " + "field:" + fieldname + ":" + entitytypeOrEntityName);
				}
			}
		}
		progress.updateProgressBar(++counter, 3);		//maria

		/*
    // (2)  Get all stories' storynames from the entity-types AND entities in mainUserModelStoryHashtable

    Enumeration mainUserModelStoryHashtableEnumKeys = QueryProfileHashtable.mainUserModelStoryHashtable.keys();
    while (mainUserModelStoryHashtableEnumKeys.hasMoreElements())
    {
      String entitytypeOrEntityName = mainUserModelStoryHashtableEnumKeys.nextElement().toString();
      Hashtable entitytypeOrEntityNameHashtable = (Hashtable)QueryProfileHashtable.mainUserModelStoryHashtable.get(entitytypeOrEntityName);
      Enumeration entitytypeOrEntityNameHashtableEnumKeys = entitytypeOrEntityNameHashtable.keys();
      while (entitytypeOrEntityNameHashtableEnumKeys.hasMoreElements())
      {
        String story = entitytypeOrEntityNameHashtableEnumKeys.nextElement().toString();
        factIdsVector.addElement("story:" + story + ":" + entitytypeOrEntityName);
        //System.out.println("()---- " + "story:" + story + ":" + entitytypeOrEntityName);
      }
    }
		*/
    //System.out.println("()---- " + factIdsVector.toString());
    //System.out.println("()---- " + factIdsVector.size());

    String[] factIdsArray = new String[factIdsVector.size()]; // then we create the factIdsArray
    for (int i=0; i<factIdsVector.size(); i++)
    {
    	factIdsArray[i] = factIdsVector.get(i).toString().toLowerCase();
    }

		Vector microPlanIdsVector = new Vector();
		Vector microPlanIdsVectorForArray = new Vector();
		Hashtable microPlanFieldsAndHashtables = new Hashtable();
		for (Enumeration k = allFieldsAndContainingEntityTypesHashtable.keys(); k.hasMoreElements();)
		{
			String field = k.nextElement().toString();
			String nodename = allFieldsAndContainingEntityTypesHashtable.get(field).toString();
			Vector entitytypeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(nodename);
			Hashtable microplanHashtable = (Hashtable)entitytypeVector.get(5);
			microPlanFieldsAndHashtables.put(field, microplanHashtable);
			for (int i=1; i<6; i++) 
			{
				String microplanNumber = new Integer(i).toString();
				// only add microplans to vector if they have been specified
				if (!((String)microplanHashtable.get(microplanNumber + ":" + field + ":SELECTION:English")).equalsIgnoreCase("NoMicroPlanning")) 
				{
				  microPlanIdsVectorForArray.add("mp:" + field + ":" + microplanNumber + ":en");
				  Vector temp = new Vector();
				  temp.add(field);
				  temp.add(microplanNumber);
				  temp.add("en");
				  microPlanIdsVector.add(temp);
				}
				
				if (!((String)microplanHashtable.get(microplanNumber + ":" + field + ":SELECTION:Greek")).equalsIgnoreCase("NoMicroPlanning")) 
				{
				  microPlanIdsVectorForArray.add("mp:" + field + ":" + microplanNumber + ":el");
				  Vector temp = new Vector();
				  temp.add(field);
				  temp.add(microplanNumber);
				  temp.add("el");
				  microPlanIdsVector.add(temp);
				}
				
				if (!((String)microplanHashtable.get(microplanNumber + ":" + field + ":SELECTION:Italian")).equalsIgnoreCase("NoMicroPlanning")) 
				{
				  microPlanIdsVectorForArray.add("mp:" + field + ":" + microplanNumber + ":it");
				  Vector temp = new Vector();
				  temp.add(field);
				  temp.add(microplanNumber);
				  temp.add("it");
				  microPlanIdsVector.add(temp);
				}
			}
		}
		String[] microPlanIdsArray = new String[microPlanIdsVectorForArray.size()];

		for (int i=0; i<microPlanIdsVectorForArray.size(); i++) 
		{
			microPlanIdsArray[i] = microPlanIdsVectorForArray.get(i).toString().toLowerCase();
			//System.err.println(microPlanIdsVectorForArray.get(i).toString().toLowerCase());
		}

		String[] wordIdsArray = new String[allNounsVector.size()];
		for (int i=0; i<allNounsVector.size(); i++)
		{
		  wordIdsArray[i] = allNounsVector.get(i).toString().toLowerCase();
		}

		// Reset the server, providing all the ids of entities and facts:
		input.initialize(entitynamesArray, factIdsArray, microPlanIdsArray, wordIdsArray);
		//progress.updateProgressLabel("Adding the new user types", 3);	//maria
		progress.updateProgressBar(++counter, 3);						//maria
		
		///////////////////////////////////////////////////////////
		// Create the user types
		///////////////////////////////////////////////////////////
		
		//Vector allUserTypesVector = (Vector)Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Enumeration allUserTypesVectorEnum1 = allUserTypesVector.elements();
		while (allUserTypesVectorEnum1.hasMoreElements())
		{
		  String usertype = allUserTypesVectorEnum1.nextElement().toString();
		  input.newUserType("ut:" + usertype);
		  if (!visit.checkUserExists("auth-" + usertype))
		  {
	      visit.newUser("auth-" + usertype, "ut:" + usertype);
	      System.out.println("(New user added)---  " + "auth-" + usertype);
		  }
		}

    //progress.updateProgressLabel("Adding user type information", 3);	//maria
    progress.updateProgressBar(++counter, 3);							//maria
    ///////////////////////////////////////////////////////////
    // For every user-type:
    // Create the maximum number of facts per generated page
    // Create the number of facts per generated page
    // Create the number of links (forward pointers) per generated page
    // Create the synthesizer voice
    ///////////////////////////////////////////////////////////

		Enumeration allUserTypesVectorEnum2 = allUserTypesVector.elements();
		while (allUserTypesVectorEnum2.hasMoreElements())
		{
		  String usertype = allUserTypesVectorEnum2.nextElement().toString();
		  User userVector = (User)Mpiro.win.struc.getUser(usertype);
		
		  Integer maxFactsPerSentence = new Integer(userVector.getMaxFactsPerSentence());
		  Integer numberOfFacts = new Integer(userVector.getFactsPerPage());
		  Integer numberOfForwardPointers = new Integer(userVector.getLinks());
		  String voice = userVector.getSynthVoice();
		
		  input.setMaxFactsPerSentence(maxFactsPerSentence.intValue(), "ut:" + usertype);
		  input.setNumberOfFacts(numberOfFacts.intValue(), "ut:" + usertype);
		  input.setNumberOfForwardPointers(numberOfForwardPointers.intValue(), "ut:" + usertype);
		  input.setVoice(voice, "ut:" + usertype);
		}

		//progress.updateProgressLabel("Adding lexicon entry information", 3);	//maria
		progress.updateProgressBar(++counter, 3);							//maria
		///////////////////////////////////////////////////////////
		// For every lexicon entry:
		// Set the word appropriateness for every user
		///////////////////////////////////////////////////////////
		
		// Set appropriateness for nouns
		Enumeration allNounsVectorEnum = allNounsVector.elements();
		while (allNounsVectorEnum.hasMoreElements())
		{
		  String noun = allNounsVectorEnum.nextElement().toString();
		  Hashtable currentNounHashtable = (Hashtable)allNounsHashtable.get(noun);
		  Hashtable currentNounIndependentHashtable = (Hashtable)currentNounHashtable.get("Independent");
		  for (Enumeration k = currentNounIndependentHashtable.keys(), e = currentNounIndependentHashtable.elements() ; k.hasMoreElements();)
		  {
	      String usertype = k.nextElement().toString();
	      Integer appropriateness = new Integer(e.nextElement().toString());
	      input.setWordAppropriateness("word:" + noun, appropriateness.floatValue(), "ut:" + usertype);
		  }
		  //progress.updateProgressLabel("Setting appropriateness for: " + noun, 3);	//maria
		  progress.updateProgressBar(++counter, 3);									//maria
		}
		
		/*
    // Set appropriateness for verbs
    Enumeration allVerbsVectorEnum = allVerbsVector.elements();
    while (allVerbsVectorEnum.hasMoreElements())
    {
      String verb = allVerbsVectorEnum.nextElement().toString();
      Hashtable currentVerbHashtable = (Hashtable)allVerbsHashtable.get(verb);
      Hashtable currentVerbIndependentHashtable = (Hashtable)currentVerbHashtable.get("Independent");
      for (Enumeration k = currentVerbIndependentHashtable.keys(), e = currentVerbIndependentHashtable.elements() ; k.hasMoreElements();)
      {
        String usertype = k.nextElement().toString();
        Integer appropriateness = new Integer(e.nextElement().toString());
        input.setWordAppropriateness("word:" + verb, appropriateness.floatValue(), "ut:" + usertype);
      }
      progress.updateProgressLabel("Setting appropriateness for: " + verb);
      progress.updateProgressBar(++counter);
    }
		*/

		///////////////////////////////////////////////////////////
		// For every field entry:
		// Set the microplanning appropriateness for every user
		///////////////////////////////////////////////////////////
		
		// microPlanIdsVector only contains the ids for microplans
		// which have been specified (i.e. do not say "no
		// microplanning") so we don't have to check for that again
		String lastField = "";
		for (Iterator k = microPlanIdsVector.iterator(); k.hasNext();)
		{
			Vector thisMicroplanVector = (Vector)k.next();
			String field = (String)thisMicroplanVector.get(0);
			String oldFieldName = field;
			
			Hashtable microplanHashtable = (Hashtable)microPlanFieldsAndHashtables.get(field);
			if (!field.equals(lastField)) 
			{
				//progress.updateProgressLabel("Setting microplanning appropriateness for: " + field, 3);	//maria
				progress.updateProgressBar(++counter, 3);												//maria
			}

			Enumeration allUserTypesVectorEnum3 = allUserTypesVector.elements();
			while (allUserTypesVectorEnum3.hasMoreElements())
			{
				String usertype = allUserTypesVectorEnum3.nextElement().toString();		
				String microplanNumber = (String)thisMicroplanVector.get(1);
				//String microplanNumber = new Integer(i).toString();
				Vector valuesVector = Mpiro.win.struc.getAppropriatenessValuesVector(oldFieldName, microplanNumber, usertype, microplanHashtable);
				if (((String)thisMicroplanVector.get(2)).equals("en")) 
				{
				  Integer englishAppropriateness = new Integer(valuesVector.get(0).toString());
				  //		    System.err.println("mp:" + field + ":" + microplanNumber + ":en" +  englishAppropriateness +  "ut:" + usertype);
				  input.setMicroPlanningAppropriateness("mp:" + field + ":" + microplanNumber + ":en", englishAppropriateness.floatValue(), "ut:" + usertype);
				}
				else if (((String)thisMicroplanVector.get(2)).equals("it")) 
				{
			    Integer italianAppropriateness = new Integer(valuesVector.get(1).toString());
			    //		    System.err.println("mp:" + field + ":" + microplanNumber + ":it" +  italianAppropriateness + "ut:" + usertype);
			    input.setMicroPlanningAppropriateness("mp:" + field + ":" + microplanNumber + ":it", italianAppropriateness.floatValue(), "ut:" + usertype);
				}
				else if (((String)thisMicroplanVector.get(2)).equals("el")) 
				{
			    Integer greekAppropriateness = new Integer(valuesVector.get(2).toString());
			    //		    System.err.println("mp:" + field + ":" + microplanNumber + ":el" +  greekAppropriateness +  "ut:" + usertype);
			    input.setMicroPlanningAppropriateness("mp:" + field + ":" + microplanNumber + ":el", greekAppropriateness.floatValue(), "ut:" + usertype);
				}
			}
			lastField = field;
		}//for


		///////////////////////////////////////////////////////////
		// For every field entry:
		// Set the interest, importance, assimilation-rate for every functor ("field:" + fieldname)
		// Set the interest, importance, assimilation-rate for every fact  ("field:" + fieldname + ":" + entityname)
		//
		// (!! Exception : when fieldname==subtype-of then functor becomes ("field:" + fieldname + ":" + entitytypename) )
		///////////////////////////////////////////////////////////
		
		// First of all we enter a default set of values for "type" and "called"
		// "called" is used instead of both "name" and "shortname"
    Enumeration allUserTypesVectorEnum4 = allUserTypesVector.elements();
    while (allUserTypesVectorEnum4.hasMoreElements())
    {
	    String usertype = allUserTypesVectorEnum4.nextElement().toString();
	    input.setInterestFunctor("field:" + "type", 3.0f, "ut:" + usertype);
	    input.setImportanceFunctor("field:" + "type", 3.0f, "ut:" + usertype);
	    input.setAssimilationRateFunctor("field:" + "type", 1.0f, "ut:" + usertype);
	    input.setInitialAssimilationFunctor("field:" + "type", 0.0f, "ut:" + usertype);
	
	    input.setInterestFunctor("field:" + "called", 3.0f, "ut:" + usertype);
	    input.setImportanceFunctor("field:" + "called", 3.0f, "ut:" + usertype);
	    input.setAssimilationRateFunctor("field:" + "called", 1.0f, "ut:" + usertype);
	    input.setInitialAssimilationFunctor("field:" + "called", 0.0f, "ut:" + usertype);
		}


		Enumeration mainUserModelHashtableEnum = Mpiro.win.struc.mainUserModelHashtableKeys();
		while (mainUserModelHashtableEnum.hasMoreElements()) // while 1
		{
		  String field = mainUserModelHashtableEnum.nextElement().toString();
		  //progress.updateProgressLabel("Setting user modelling values for: " + field, 3);	//maria
		  progress.updateProgressBar(++counter, 3);										//maria

      Hashtable fieldHashtable = (Hashtable)Mpiro.win.struc.getPropertyFromMainUserModelHashtable(field);
      Enumeration fieldHashtableEnum = fieldHashtable.keys();
      while (fieldHashtableEnum.hasMoreElements()) // while 2
      {
        // owner is an entity-type and/or entity that have the field
        String owner = fieldHashtableEnum.nextElement().toString();
        Hashtable ownerHashtable = (Hashtable)fieldHashtable.get(owner);
        Enumeration ownerHashtableEnum = ownerHashtable.keys();
        while (ownerHashtableEnum.hasMoreElements()) // while 3
        {
          String usertype = ownerHashtableEnum.nextElement().toString();
          Vector usertypeVector = (Vector)ownerHashtable.get(usertype);
          String interestString = usertypeVector.get(0).toString();
          String importanceString = usertypeVector.get(1).toString();
          String repetitionsString = usertypeVector.get(2).toString();

          if (field.equalsIgnoreCase("name"))
          {
	        	field = "called";
	        }
	
	        if (!field.equalsIgnoreCase("Subtype-of"))
	        {
						// Amy changing names of things which also appear in the upper model
						field = ExportUtilsExprimo.inUpperModel(field, true);

            if (allEntityTypesVector.contains(owner))
            {
	            Integer interest = new Integer(interestString);
	            Integer importance = new Integer(importanceString);
	            Integer repetitions = new Integer(repetitionsString);
	            input.setInterestFunctor("field:" + field, interest.floatValue(), "ut:" + usertype);
	            input.setImportanceFunctor("field:" + field, importance.floatValue(), "ut:" + usertype);
	            // if (repetitions==0) {1/repetitions is invalid}
	            if (repetitions.intValue()==0)
	            {
                input.setAssimilationRateFunctor("field:" + field, 1.0f, "ut:" + usertype);
                input.setInitialAssimilationFunctor("field:" + field, 1.0f, "ut:" + usertype);
	            }
	            
              else
              {
                input.setAssimilationRateFunctor("field:" + field, 1.0f/repetitions.floatValue(), "ut:" + usertype);
                input.setInitialAssimilationFunctor("field:" + field, 0.0f, "ut:" + usertype);
              }///////////////////////////////////////////////
						}
            else if (allGenericEntitiesVector.contains(owner))
            {
              if (!interestString.equals(""))
              {
                Integer interest = new Integer(interestString);
                input.setInterestFact("field:" + field + ":" + owner.toLowerCase(), interest.floatValue(), "ut:" + usertype);
              }
              
              else if (field.equalsIgnoreCase("type"))
              {
                  input.setInterestFact("field:" + field + ":" + owner.toLowerCase(), 0.0f, "ut:" + usertype);
              }
              
              if (!importanceString.equals(""))
              {
                Integer importance = new Integer(importanceString);
                input.setImportanceFact("field:" + field + ":" + owner.toLowerCase(), importance.floatValue(), "ut:" + usertype);
              }
              
              else if (field.equalsIgnoreCase("type"))
              {
              	input.setImportanceFact("field:" + field + ":" + owner.toLowerCase(), 0.0f, "ut:" + usertype);
              }

              if (!repetitionsString.equals(""))
              {
                Integer repetitions = new Integer(repetitionsString);
                // if (repetitions==0) {1/repetitions is invalid}
                if (repetitions.intValue()==0)
                {
                  input.setAssimilationRateFact("field:" + field + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
                  input.setInitialAssimilationFact("field:" + field + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
                }
                else
                {
                  input.setAssimilationRateFact("field:" + field + ":" + owner.toLowerCase(), 1.0f/repetitions.floatValue(), "ut:" + usertype);
                  input.setInitialAssimilationFact("field:" + field + ":" + owner.toLowerCase(), 0.0f, "ut:" + usertype);
                }///////////////////////////////////////////////
              }
              else if (field.equalsIgnoreCase("type"))
              {
                input.setAssimilationRateFact("field:" + field + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
                input.setInitialAssimilationFact("field:" + field + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
              }
						}

            else if (allEntitiesVector.contains(owner))
            {
              if (!interestString.equals(""))
              {
                Integer interest = new Integer(interestString);
                input.setInterestFact("field:" + field + ":" + owner.toLowerCase(), interest.floatValue(), "ut:" + usertype);
              }
              
              if (!importanceString.equals(""))
              {
                Integer importance = new Integer(importanceString);
                input.setImportanceFact("field:" + field + ":" + owner.toLowerCase(), importance.floatValue(), "ut:" + usertype);
              }
              
	            if (!repetitionsString.equals(""))
	            {
	              Integer repetitions = new Integer(repetitionsString);
	              // if (repetitions==0) {1/repetitions is invalid}
	              if (repetitions.intValue()==0)
	              {
                  input.setAssimilationRateFact("field:" + field + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
                  input.setInitialAssimilationFact("field:" + field + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
	              }
	              
	              else
	              {
                  input.setAssimilationRateFact("field:" + field + ":" + owner.toLowerCase(), 1.0f/repetitions.floatValue(), "ut:" + usertype);
                  input.setInitialAssimilationFact("field:" + field + ":" + owner.toLowerCase(), 0.0f, "ut:" + usertype);
	              }///////////////////////////////////////////////
							}
						}
            else
            {
            	System.out.println("(ExportUtilsPServer)---- " + "Alert in line 488");
            }
					}//if
					else // if (field.equalsIgnoreCase("Subtype-of"))
					{
						if (allEntityTypesVector.contains(owner))
						{
							// Amy changing names of things which also appear in the upper model
							String printOwner = ExportUtilsExprimo.inUpperModel(owner, true);
							
							Integer interest = new Integer(interestString);
							Integer importance = new Integer(importanceString);
							Integer repetitions = new Integer(repetitionsString);

              input.setInterestFunctor("field:" + field + ":" + printOwner.toLowerCase(), interest.floatValue(), "ut:" + usertype);
              input.setImportanceFunctor("field:" + field + ":" + printOwner.toLowerCase(), importance.floatValue(), "ut:" + usertype);
              // if (repetitions==0) {1/repetitions is invalid}
              if (repetitions.intValue()==0)
              {
                input.setAssimilationRateFunctor("field:" + field + ":" + printOwner.toLowerCase(), 1.0f, "ut:" + usertype);
                input.setInitialAssimilationFunctor("field:" + field + ":" + printOwner.toLowerCase(), 1.0f, "ut:" + usertype);
              }
              
              else
              {
                input.setAssimilationRateFunctor("field:" + field + ":" + printOwner.toLowerCase(), 1.0f/repetitions.floatValue(), "ut:" + usertype);
                input.setInitialAssimilationFunctor("field:" + field + ":" + printOwner.toLowerCase(), 0.0f, "ut:" + usertype);
              }///////////////////////////////////////////////
						}
            else if (allEntitiesVector.contains(owner))
            {
            	System.out.println("(ExportUtilsPServer)---- " + "Alert in line 526");
            }
            
            else
            {
            	System.out.println("(ExportUtilsPServer)---- " + "Alert in line 530");
            }
					}
				} // while 3
			} // while 2
		} // while 1

		// Stories are not used at the moment
		
		/*
		///////////////////////////////////////////////////////////
		// For every story entry:
		// Set the interest, importance, assimilation-rate
		// for a dummy functor ("story")
		// for every fact ("story:" + storyname + ":" + entitytypeOrEntityName)
		//
		///////////////////////////////////////////////////////////
		
		// the dummy functor "story"
		Enumeration allUserTypesVectorEnum5 = allUserTypesVector.elements();
		while (allUserTypesVectorEnum5.hasMoreElements())
		{
		  String usertype = allUserTypesVectorEnum5.nextElement().toString();
		  input.setInterestFunctor("story", 1.0f, "ut:" + usertype);
		  input.setImportanceFunctor("story", 1.0f, "ut:" + usertype);
		  input.setAssimilationRateFunctor("story", 1.0f, "ut:" + usertype);
		  input.setInitialAssimilationFunctor("story", 1.0f, "ut:" + usertype);
		}

		// the stories fields (facts)
		Enumeration mainUserModelStoryHashtableEnum = QueryProfileHashtable.mainUserModelStoryHashtable.keys();
		while (mainUserModelStoryHashtableEnum.hasMoreElements()) // while 1
		{
		  // "owner" is an entity-type or entity
		  String owner = mainUserModelStoryHashtableEnum.nextElement().toString();
		  Hashtable ownerHashtable = (Hashtable)QueryProfileHashtable.mainUserModelStoryHashtable.get(owner);
		  Enumeration ownerHashtableEnum = ownerHashtable.keys();
		  while (ownerHashtableEnum.hasMoreElements()) // while 2
		  {
		    String story = ownerHashtableEnum.nextElement().toString();
		    Hashtable storyHashtable = (Hashtable)ownerHashtable.get(story);
		    Enumeration storyHashtableEnum = storyHashtable.keys();
		    while (storyHashtableEnum.hasMoreElements()) // while 3
		    {
		      String usertype = storyHashtableEnum.nextElement().toString();
		      Vector usertypeVector = (Vector)storyHashtable.get(usertype);
		      String interestString = usertypeVector.get(0).toString();
		      String importanceString = usertypeVector.get(1).toString();
		      String repetitionsString = usertypeVector.get(2).toString();
		
		      Integer interest = new Integer(interestString);
		      Integer importance = new Integer(importanceString);
		      Integer repetitions = new Integer(repetitionsString);
		
		      input.setInterestFact("story:" + story + ":" + owner.toLowerCase(), interest.floatValue(), "ut:" + usertype);
		      input.setImportanceFact("story:" + story + ":" + owner.toLowerCase(), importance.floatValue(), "ut:" + usertype);
		      // if (repetitions==0) {1/repetitions is invalid}
		      if (repetitions.intValue()==0)
		      {
		        input.setAssimilationRateFact("story:" + story + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
		        input.setInitialAssimilationFact("story:" + story + ":" + owner.toLowerCase(), 1.0f, "ut:" + usertype);
		      }
		      else
		      {
		        input.setAssimilationRateFact("story:" + story + ":" + owner.toLowerCase(), 1.0f/repetitions.floatValue(), "ut:" + usertype);
		        input.setInitialAssimilationFact("story:" + story + ":" + owner.toLowerCase(), 0.0f, "ut:" + usertype);
		      }///////////////////////////////////////////////
		
		    } // while 3
		    progress.updateProgressLabel("Setting user modelling values for story: " + story);
		  } // while 2
		  progress.updateProgressBar(++counter);
		} // while 1
		*/

		//progress.updateProgressLabel("Export finished sucessfully.", 3);	//maria
		progress.updateProgressBar(++counter, 3);							//maria
		progress.updateOKButton(true);
	}//exportToServer


	public static void resetInteractionHistory(String pserverIP, String pserverPort) throws UMException 
	{
		Vector allUserTypesVector = (Vector)Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		InetAddress serverIP = null;
    try
    {
    	serverIP = InetAddress.getByName(pserverIP);
    }
    catch (Exception u)
    {
    	System.out.println("(ExportUtilsPServer.resetInteractionHistory)---- " + u);
    }

		Integer port = new Integer(pserverPort);
		int serverPort = port.intValue();
		
		UMVisit visit = new UMVisitImp(serverIP, serverPort);
		
		// Creating the Export progress monitor //////////////////////////////
		
		// Calculate the maximum value for the progress bar
		int maximumValue = 2;
		// maximumValue = maximumValue + Mpiro.win.struc.getUsersVectorFromMainUsersHashtable().size();
		maximumValue = maximumValue + allUserTypesVector.size();
		
		// Show the export progress monitor frame ////////////////////////////
		ExportProgressMonitor progress = new ExportProgressMonitor(0, maximumValue, 0);
		progress.exprimoOrEmulatorsLabel.setText(LangResources.getString(Mpiro.selectedLocale,"resetInteractionHistoryServer_text" ));	//maria
		progress.show();
		progress.updateOKButton(false);

		///////////////////////////////////////////////////////////
		// Reset the dummy "auth-USERTYPE" users
		///////////////////////////////////////////////////////////
		
		//progress.updateProgressLabel("Resetting user types", 3);	//maria
		progress.updateProgressBar(++counter, 3);					//maria
		
		//Vector allUserTypesVector = (Vector)Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Enumeration allUserTypesVectorEnum1 = allUserTypesVector.elements();
		while (allUserTypesVectorEnum1.hasMoreElements())
		{
      String usertype = allUserTypesVectorEnum1.nextElement().toString();
      if (visit.checkUserExists("auth-" + usertype))
      {
        //System.out.println("(TargetLanguage)---  " + visit.getTargetLanguage("auth-" + usertype));
        visit.deleteUser("auth-" + usertype);
        //System.out.println("(deleted user)---  " + "auth-" + usertype);
        visit.newUser("auth-" + usertype, "ut:" + usertype);
        //System.out.println("(New user added)---  " + "auth-" + usertype);
      }
      //progress.updateProgressLabel("Resetting: " + usertype, 3);	//maria
      progress.updateProgressBar(++counter, 3);					//maria
		}
    //progress.updateProgressLabel("Reset finished sucessfully.", 3);	//maria
    progress.updateProgressBar(++counter, 3);						//maria
    progress.updateOKButton(true);
	}

}	  //class
