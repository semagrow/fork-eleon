package gr.demokritos.iit.eleon.um;


import java.net.*;
import java.util.*;


//===================================================================
// UMAuthoringImp
//
// Implements the UMAuthoring interface through calls to PersServer,
// a personalization server functioning as a web server.
//===================================================================
public class UMAuthoringImp implements UMAuthoring {
    //personalization server communication variables
    InetAddress dst;  //personalization server IP address 
    int prt;          //personalization server port
    boolean post;     //if true, HTTP POST method, else GET
    int timeout;      //timeout period for reading server response, 0 for infinite

//initializers
    public UMAuthoringImp(InetAddress dst, int prt) {
        this.dst = dst;
        this.prt = prt;
        post = true;    //method POST for safety
        timeout = 15000;       //in milliseconds
    }


// ************* methods to initialise the UM subsystem and to create user types ***********
         
   /**
   * Erase all the information in the personalisation server (both stereotypes and user
   * profiles), and prepare it to receive new information via the UMAuthoring interface.
   * @param entityNames An array containing the identifiers of all the entities.
   * @param factIds An array containing the identifiers of all the facts.
   * @param microPlanIds An array containing the identifiers of all the micro plans.
   * @param wordIds An array containing the identifiers of all the words.
   * @throws UMException() if anything goes wrong.
   **/
   public void initialize(String[] entityNames, String[] factIds, String[] microPlanIds,
                          String[] wordIds) throws UMException {
       PSClientRequest pc;
       if (entityNames == null || factIds == null)
           throw new UMException("Null method parameter");
       //remove all data from PersServer DB
       String request = "/pers?com=remftr&ftr=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       String request_1 = "/ster?com=remstr";
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //initialize personal mode by adding all features
       //(in more than one separate requests to avoid excessive size)
       StringBuffer temp = new StringBuffer();
       temp.setLength(0);
       temp.append("/pers?com=addftr");
       temp.append("&currentFocus=");
       temp.append("&targetLanguage=");
       for (int i=0; i < entityNames.length; i++) {
           String ftrName = entityNames[i] + ".mentioned";
           temp.append("&");
           temp.append(ftrName);
           temp.append("=");
           temp.append("0");  //default value
       }
       String request_2 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       temp.setLength(0);
       temp.append("/pers?com=addftr");
       for (int i=0; i < entityNames.length; i++) {
           String ftrName = entityNames[i] + ".focus";
           temp.append("&");
           temp.append(ftrName);
           temp.append("=");
           temp.append("0");  //default value
       }
       String request_3 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_3, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());

       // Dimitris: I divided the facts into as many groups as calculated
       // for each group to have 100 elements. This is to prevent errors
       // when exporting data to Personalization Server

       int hundredsInteger = factIds.length / 100;
       int moduloInteger = factIds.length % 100;
       //System.out.println("(hundredsInteger)---  " + hundredsInteger);
       //System.out.println("(moduloInteger)---  " + moduloInteger);

       int counter = 0;
       while (counter < hundredsInteger) {
	   temp.setLength(0);
	   temp.append("/pers?com=addftr");
	   for (int i=counter*100; i<(counter+1)*100; i++) {
	       String ftrName = factIds[i] + ".assimilation";
	       temp.append("&");
	       temp.append(ftrName);
	       temp.append("=");
	       temp.append("-1");  //flag signifying non-initialization
       }
       String request_4 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_4, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       
          counter++;
       }

       if (moduloInteger > 0)
       {
          temp.setLength(0);
          temp.append("/pers?com=addftr");

          for (int i=counter*100; i<(counter*100)+moduloInteger; i++)
          {
             String ftrName = factIds[i] + ".assimilation";
             temp.append("&");
             temp.append(ftrName);
             temp.append("=");
             temp.append("-1");  //flag signifying non-initialization
          }
          String request_5 = temp.substring(0);
          pc = new PSClientRequest(dst, prt, request_5, post, timeout);
          if (pc.isError()) throw new UMException(pc.getErrorMessage());
       }

       temp.setLength(0);
       temp.append("/pers?com=addftr");
       for (int i=0; i < microPlanIds.length; i++) {
           String ftrName = microPlanIds[i] + ".used";
           temp.append("&");
           temp.append(ftrName);
           temp.append("=");
           temp.append("0");  //default value
       }
       String request_6 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_6, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       temp.setLength(0);
       temp.append("/pers?com=addftr");
       for (int i=0; i < wordIds.length; i++) {
           String ftrName = wordIds[i] + ".used";
           temp.append("&");
           temp.append(ftrName);
           temp.append("=");
           temp.append("0");  //default value
       }
       String request_7 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_7, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /**
   * Create a new user type.
   * @param userType The name of the new user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void newUserType(String userType) throws UMException {
       PSClientRequest pc;
       //add a new stereotype (userType is the user stereotype)
       String request = "/ster?com=addstr&str=" + userType;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   

// ************************* interest, importance, assimilation rate ***********************
// These are all parts of the stereotypes, and they are set for individual facts or functors.

   /** 
   * Set the interest of a fact for a user type. (How interesting we think users of this type 
   * will find the fact.)
   * @param factId The identifier of the fact we want to set the interest of.
   * @param interest The new interest value of the fact. Larger values indicate more 
   * interesting facts.
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.
   **/
   public void setInterestFact(String factId, float interest, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = factId + ".interest";
       String value = Float.toString(interest);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /**
   * Set the interest of all the facts with a particular functor for a user type.
   * @param functor The functor of the facts we want to set the interest of.
   * @param interest The new interest value of the facts. Larger values indicate more 
   * interesting facts.
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.
   **/
   public void setInterestFunctor(String functor, float interest, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = functor + ".interest";
       String value = Float.toString(interest);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /** 
   * Set the importance of a fact for a user type. (How important it is for the 
   * information provider to convey the fact to users of this type.)
   * @param factId The identifier of the fact we want to set the importance of.
   * @param importance The new importance value of the fact. Larger values indicate more 
   * important facts.
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setImportanceFact(String factId, float importance, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = factId + ".importance";
       String value = Float.toString(importance);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /** 
   * Set the importance of all the facts with a particular functor for a user type. 
   * @param functor The functor of the facts we want to set the importance of.
   * @param importance The new importance value of the facts. Larger values indicate more 
   * important facts.
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.
   **/
   public void setImportanceFunctor(String functor, float importance, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = functor + ".importance";
       String value = Float.toString(importance);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /** 
   * Set the assimilation rate of a fact for a user type. (How much the assimilation 
   * of the fact increases whenever it is conveyed to users of this type.)
   * @param factId The identifier of the fact we want to set the assimilation rate of.
   * @param assimilationRate The new assimilation rate of the fact. This must be a non-negative float.
   * Larger values indicate larger degrees of assimilation. 
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setAssimilationRateFact(String factId, float assimilationRate, String userType) throws UMException {   
       PSClientRequest pc;
       String ftrName = factId + ".assimilationRate";
       String value = Float.toString(assimilationRate);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /** 
   * Set the assimilation rate of all the facts with a particular functor for a user type.
   * @param functor The functor of the facts we want to set the assimilation rate of.
   * @param assimilationRate The new assimilation rate of the facts. This must be a non-negative float.
   * Larger values indicate larger degrees of assimilation. 
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.   
   **/
   public void setAssimilationRateFunctor(String functor, float assimilationRate, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = functor + ".assimilationRate";
       String value = Float.toString(assimilationRate);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /**
   * Set the initial assimilation of a fact for the specified user type.
   * @param factId The identifier of the fact we want to set the assimilation of.
   * @param initAssimilation The new initial assimilation of the fact. This must be a non-negative float.
   * Larger values indicate larger degrees of assimilation.
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.
   **/
   public void setInitialAssimilationFact(String factId, float initAssimilation, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = factId + ".initialAssimilation";
       String value = Float.toString(initAssimilation);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /**
   * Set the initial assimilation of all the facts with a particular functor
   * for the specified user type.
   * @param functor The functor of the facts we want to set the assimilation of.
   * @param initAssimilation The new initial assimilation of the facts. This must be a non-negative float.
   * Larger values indicate larger degrees of assimilation.
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.
   **/
   public void setInitialAssimilationFunctor(String functor, float initAssimilation, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = functor + ".initialAssimilation";
       String value = Float.toString(initAssimilation);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }


// ************************* assimilation: direct assignment ***********************
// Override UMVisit.update() and assign a value for the assimilation of fact(s)
// directly, in the personal profiles of users (not the stereotypes).

   /**
   * Set the assimilation of specified fact(s) for all users of the specified user type.
   * @param factIds The identifier(s) of the fact(s) we want to set the assimilation of.
   * @assimilation The new assimilation of the fact(s). This must be a non-negative float.
   * Larger values indicate larger degrees of assimilation.
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.
   **/
   public void setAssimilationFact(String[] factIds, float assimilation, String userType) throws UMException {
       PSClientRequest pc;
       if (factIds == null)
           throw new UMException("Null method parameter");
       //get all users of specified user type
       String request = "/ster?com=sqlusr&whr=su_stereotype:'" + userType + "'";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       int rows = pc.getRows();
       String[] userNames = new String[rows];
       for (int i=0; i < rows; i++)
           userNames[i] = pc.getValue(i, 0);
       //for each user, update assimilation of fact(s) to specified value
       StringBuffer temp = new StringBuffer();
       for (int i=0; i < userNames.length; i++) {
           temp.setLength(0);
           temp.append("/pers?com=setusr&usr=");
           temp.append(userNames[i]);
           for (int j=0; j < factIds.length; j++) {
               String ftrName = factIds[j] + ".assimilation";
               temp.append("&");
               temp.append(ftrName);
               temp.append("=");
               temp.append(Float.toString(assimilation));  //specified new value
           }
           String request_i = temp.substring(0);
           pc = new PSClientRequest(dst, prt, request_i, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
       }
   }


// *************** appropriateness of words, micro-planning expressions, schemata ***********************
// These are all parts of the stereotypes, and they are set for individual words, micro-planning 
// expressions or schemata. (Unlike interest, importance, and assimilation rate, here there is no 
// equivalent of setting the values for entire entity types or functors.)
   
   /** 
   * Set the appropriateness of a word for a user type.
   * @param lexicalItemId The identifier of the lexical item, as in the corresponding lexicon 
   * entry (e.g. "vessel-noun-1").
   * @param appropriateness The new appropriateness of the lexical item. Positive values 
   * indicate appropriate words, with greater values indicating more appropriate words. 
   * Negative values indicate inappropriate words, with smaller values indicating more 
   * inappropriate words. For the sake of variation, the generator may not always choose 
   * the applicable word with the highest appropriateness score, but it will generally 
   * try to use words whose appropriateness is positive (unless there is none of them), 
   * and from those, preference will be given to the words with the highest 
   * appropriateness scores. 
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setWordAppropriateness(String lexicalItemId, float appropriateness, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = lexicalItemId + ".appropriateness";
       String value = Float.toString(appropriateness);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /** 
   * Set the appropriateness of a micro-planning expression for a user type.  
   * @param microPlanningId The identifier of the micro-planning expression. 
   * @param appropriateness The new appropriateness of the micro-planning expression. 
   * Positive values indicate appropriate micro-planning expressions, with 
   * greater values indicating more appropriate expressions. Negative values indicate 
   * inappropriate micro-planning expressions, with smaller values indicating more 
   * inappropriate expressions. For the sake of variation, the generator may not always 
   * choose the applicable micro-planning expressions with the highest appropriateness 
   * score, but it will generally try to use expressions whose appropriateness is positive 
   * (unless there is none of them), and from those, preference will be given to the 
   * expressions with the highest appropriateness scores. 
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.   
   **/
   public void setMicroPlanningAppropriateness(String microPlanningId, float appropriateness, String userType) throws UMException {    
       PSClientRequest pc;
       String ftrName = microPlanningId + ".appropriateness";
       String value = Float.toString(appropriateness);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /** 
   * Set the appropriateness of a text-planning schema for a user type. 
   * @param schemaId The identifier of the text-planning schema. 
   * @return appropriateness The new appropriateness of the schema. Positive 
   * values indicate appropriate schemata, with greater values indicating more appropriate 
   * schemata. Negative values indicate inappropriate schemata, with smaller values 
   * indicating more inappropriate schemata. For the sake of variation, the generator may 
   * not always choose the applicable schema with the highest appropriateness score, but it 
   * will generally try to use schemata whose appropriateness is positive (unless 
   * there is none of them), and from those, preference will be given to the schemata 
   * with the highest appropriateness scores. 
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.   
   **/
   public void setTextPlanningAppropriateness(String schemaId, float appropriateness, String userType) throws UMException {   
       PSClientRequest pc;
       String ftrName = schemaId + ".appropriateness";
       String value = Float.toString(appropriateness);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   
// ***************** numberOfFacts, maxFactsPerSentence, numberOfForwardPointers, voice *******************
// These are all parts of the stereotypes. Unlike other values, they are not associated with entities,
// facts, words, micro-planning expressions, or schemata. 

   /** 
   * Set the number of facts to include in each description for a user type. 
   * @param numberOfFacts The new number of facts. This must be a non-negative integer.
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setNumberOfFacts(int numberOfFacts, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = "numberOfFacts";
       String value = Integer.toString(numberOfFacts);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /** 
   * Set the maximum number of facts to aggregate per sentence for a user type.
   * @param factsPerSentence The maximum number of facts to aggregate per sentence (a positive integer).
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setMaxFactsPerSentence(int factsPerSentence, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = "factsPerSentence";
       String value = Integer.toString(factsPerSentence);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /**
   * Set the total number of forward pointers to include in each description for a user type. 
   * @param NumberOfForwardPointers The number of forward pointers to include in each 
   * description. This must be a non-negative integer. It includes both pointers generated by 
   * log analysis (as reported by getForwardPointers()), and pointers that the text generator 
   * may produce by looking up the database (e.g. exhibits belonging to the same historical period).
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.   
   **/
   public void setNumberOfForwardPointers(int numberOfForwardPointers, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = "numberOfForwardPointers";
       String value = Integer.toString(numberOfForwardPointers);
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /** 
   * Set the desired voice (e.g. male voice, female voice) for a user type.
   * @param voice The identifier of the new voice.  
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.   
   **/
   public void setVoice(String voice, String userType) throws UMException {
       PSClientRequest pc;
       String ftrName = "voice";
       String value = voice;
       //check if stereotype feature exists, if not add it with specified value
       String request = "/ster?com=getstr&str=" + userType + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       if ( ! exists) {
           String request_1 = "/ster?com=addstr&str=" + userType + "&" + ftrName + "=" + value;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           return;
       }
       //else if it exists, set the stereotype feature value to specified value
       String request_2 = "/ster?com=setstr&str=" + userType + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_2, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
}
