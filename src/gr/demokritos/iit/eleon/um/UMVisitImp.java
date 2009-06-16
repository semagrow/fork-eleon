package gr.demokritos.iit.eleon.um;


import java.net.*;
import java.util.*;


//===================================================================
// UMVisitImp
//
// Implements the UMVisit interface through calls to PersServer,
// a personalization server functioning as a web server.
//===================================================================
public class UMVisitImp implements UMVisit {
    //personalization server communication variables
    InetAddress dst;  //personalization server IP address 
    int prt;          //personalization server port
    boolean post;     //if true, HTTP POST method, else GET
    int timeout;      //timeout period for reading server response, 0 for infinite

//initializers
    public UMVisitImp(InetAddress dst, int prt) {
        this.dst = dst;
        this.prt = prt;
        post = true;    //method POST for safety
        timeout = 15000;       //in milliseconds
    }

// ****************** methods to create new users and check if users exist *******************

   /**
   * Register a new user.
   * @param userName The identifier of the new user.
   * @param userType The initial type of the user. This may be changed at later 
   * stages with changeUserType().
   * @throws UMException() if anything goes wrong.   
   */
   public void newUser(String userName, String userType) throws UMException {
       //IMPORTANT: features and stereotypes must already have been inserted!!!
       PSClientRequest pc;
       //add user to stereotype mode (userType is the user stereotype)
       String request = "/ster?com=addusr&usr=" + userName + "&" + userType + "=";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //add user to personal mode
       String request_1 = "/pers?com=setusr&usr=" + userName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /**
   * Register a new user and set its target language.
   * @param userName The identifier of the new user.
   * @param userType The initial type of the user. This may be changed at later 
   * stages with changeUserType().
   * @param targetLanguage The identifier of the target language. 
   * @throws UMException() if anything goes wrong.   
   */
   public void newUser(String userName, String userType, String targetLanguage) throws UMException {
       //IMPORTANT: features and stereotypes must already have been inserted!!!
       PSClientRequest pc;
       //add user to stereotype mode (userType is the user stereotype)
       String request = "/ster?com=addusr&usr=" + userName + "&" + userType + "=";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //add user to personal mode and set the target language feature in a single operation
       String ftrName = "targetLanguage";
       String value = targetLanguage;
       String request_1 = "/pers?com=setusr&usr=" + userName + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /**
   * Check that a user is already registered.
   * @param userName The identifier of the user. 
   * @return True if the user is already registered; false otherwise.
   * @throws UMException() if anything goes wrong.   
   */
   public boolean checkUserExists(String userName) throws UMException {
       PSClientRequest pc;
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       boolean exists = pc.getRows() > 0? true: false;
       return exists;
   }

   /**
   * Delete a user.
   * @param userName The identifier of the user.
   * @throws UMException() if anything goes wrong.   
   */
   public void deleteUser(String userName) throws UMException {
       PSClientRequest pc;
       //remove user personal features
       String request = "/pers?com=remusr&usr=" + userName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //remove user reference to stereotype (user type)
       String request_1 = "/ster?com=remusr&" + userName + "=*";
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }


// ******************************** interest, importance ************************************
// With the current version of UMAuthoring, these are effectively enquiries about the 
// stereotypes. We only need to access the personal profiles of the users to get their 
// current user types. 
// Note: there are no methods to enquire about the assimilation rate of entities and facts.

   /** 
   * Get the interest of a fact for a particular user. (How interesting we think the user 
   * will find the fact.)
   * @param factId The identifier of the fact we want the interest of.
   * @param functor  The functor of the fact. If there is no particular interest value for the
   * fact, the interest value for its functor will be returned.
   * @param userName The identifier of the user.
   * @return Returns a float value indicating the interest of the fact. Larger values indicate
   * more interesting facts.
   * @throws UMException() if anything goes wrong. 
   **/
   public float getInterestFact(String factId, String functor, String userName) throws UMException {
       PSClientRequest pc;
       String ftrOne = factId + ".interest";
       String ftrTwo = functor + ".interest";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the first priority value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrOne;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() != 0)
           value = pc.getValue(0, 1);
       //if not there, check for the second priority value
       else {
           String request_2 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrTwo;
           pc = new PSClientRequest(dst, prt, request_2, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           if (pc.getRows() == 0) throw new UMException("No entry found");
           value = pc.getValue(0, 1);
       }
       float result;
       try {
           result = Float.parseFloat(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
 
   /** 
   * Get the importance of a fact for a particular user. (How important it is for the information 
   * provider to convey the fact to the users.)
   * @param factId The fact we want the importance of.
   * @param functor The functor of the fact. If there is no particular importance value for 
   * the fact, the importance of its functor will be returned.
   * @param userName The identifier of the user.
   * @return Returns a float value indicating the importance of the fact. Larger values indicate
   * more important facts.
   * @throws UMException() if anything goes wrong.   
   **/
   public float getImportanceFact(String factId, String functor, String userName) throws UMException {
       PSClientRequest pc;
       String ftrOne = factId + ".importance";
       String ftrTwo = functor + ".importance";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the first priority value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrOne;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() != 0)
           value = pc.getValue(0, 1);
       //if not there, check for the second priority value
       else {
           String request_2 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrTwo;
           pc = new PSClientRequest(dst, prt, request_2, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           if (pc.getRows() == 0) throw new UMException("No entry found");
           value = pc.getValue(0, 1);
       }
       float result;
       try {
           result = Float.parseFloat(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   

// *************** appropriateness of words, micro-planning expressions, schemata ***********************
// With the current version of UMAuthoring, these are effectively enquiries about the stereotypes. 
// We only need to access the personal profiles of the users to get their current user types. 
// Unlike interest and importance, here there is no equivalent of entity types or functors.
      
   /** 
   * Find out how appropriate it is to use a word in texts for a particular user.
   * @param lexicalItemId The identifier of the lexical item, as in the corresponding lexicon 
   * entry (e.g. "vessel-noun-1").
   * @param userName The identifier of the user.
   * @return Returns a float value representing the appropriateness of the lexical item. 
   * Positive values indicate appropriate words, with greater values indicating more
   * appropriate words. Negative values indicate inappropriate words, with smaller 
   * values indicating more inappropriate words. For the sake of variation, 
   * the generator may not always choose the applicable word with the highest 
   * appropriateness score, but it will generally try to use words whose appropriateness is 
   * positive (unless there is none of them), and from those, preference will be given to 
   * the words with the highest appropriateness scores. 
   * @throws UMException() if anything goes wrong.   
   **/
   public float getWordAppropriateness(String lexicalItemId, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = lexicalItemId + ".appropriateness";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       float result;
       try {
           result = Float.parseFloat(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   /** 
   * Find out how appropriate it is to use a micro-planning expression in texts for a 
   * particular user.
   * @param microPlanningId The identifier of the micro-planning expression. 
   * @param userName The identifier of the user.
   * @return Returns a float value representing the appropriateness of the micro-planning 
   * expression. Positive values indicate appropriate micro-planning expressions, with 
   * greater values indicating more appropriate expressions. Negative values indicate 
   * inappropriate micro-planning expressions, with smaller values indicating more 
   * inappropriate expressions. For the sake of variation, the generator may not always 
   * choose the applicable micro-planning expressions with the highest appropriateness 
   * score, but it will generally try to use expressions whose appropriateness is positive 
   * (unless there is none of them), and from those, preference will be given to the 
   * expressions with the highest appropriateness scores. 
   * @throws UMException() if anything goes wrong.   
   **/
   public float getMicroPlanningAppropriateness(String microPlanningId, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = microPlanningId + ".appropriateness";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       float result;
       try {
           result = Float.parseFloat(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   /** 
   * Find out how appropriate it is to use a text-planning schema in texts for a user type. 
   * @param schemaId The identifier of the text-planning schema. 
   * @param userName The identifier of the user. 
   * @return Returns a float value representing the appropriateness of the schema. Positive 
   * values indicate appropriate schemata, with greater values indicating more appropriate 
   * schemata. Negative values indicate inappropriate schemata, with smaller values 
   * indicating more inappropriate schemata. For the sake of variation, the generator may 
   * not always choose the applicable schema with the highest appropriateness score, but it 
   * will generally try to use schemata whose appropriateness is positive (unless 
   * there is none of them), and from those, preference will be given to the schemata 
   * with the highest appropriateness scores. 
   * @throws UMException() if anything goes wrong.   
   **/
   public float getTextPlanningAppropriateness(String schemaId, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = schemaId + ".appropriateness";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       float result;
       try {
           result = Float.parseFloat(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   
// ***************** numberOfFacts, maxFactsPerSentence, numberOfForwardPointers, voice *******************
// With the current version of UMAuthoring, these are effectively enquiries about the stereotypes. 
// We only need to access the personal profiles of the users to get their current user types. Unlike 
// other values, these values are not associated with entities, facts, words, micro-planning expressions, 
// or schemata.
   
   /** 
   * Get the number of facts to include in each description for a particular user. 
   * @param userName The identifier of the user.
   * @return Returns the number of facts (a non-negative integer).
   * @throws UMException() if anything goes wrong.   
   **/
   public int getNumberOfFacts(String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = "numberOfFacts";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       int result;
       try {
           result = Integer.parseInt(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   /** 
   * Get the maximum number of facts to aggregate per sentence in descriptions for a 
   * particular user.
   * @param userName The identifier of the user.
   * @return Returns the maximum number of facts to aggregate per sentence (a positive integer).
   * @throws UMException() if anything goes wrong.   
   **/
   public int getMaxFactsPerSentence(String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = "factsPerSentence";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       int result;
       try {
           result = Integer.parseInt(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   /**
   * Get the total number of forward pointers to include in each description presented to a 
   * particular user.  
   * @param userName The identifier of the user.
   * @return Returns the number of forward pointers to include in each description. This is
   * always a non-negative integer. It includes both pointers generated by log analysis 
   * (as reported by getForwardPointers()), and pointers that the text generator may produce 
   * by looking up the database (e.g. exhibits belonging to the same historical period).
   * @throws UMException() if anything goes wrong.   
   **/
   public int getNumberOfForwardPointers(String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = "numberOfForwardPointers";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       int result;
       try {
           result = Integer.parseInt(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }

   /** 
   * Get the desired voice for a particular user (e.g. male voice, female voice).
   * @param userName The identifier of the user.
   * @return Returns the identifier of the voice. 
   * @throws UMException() if anything goes wrong.   
   **/
   public String getVoice(String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = "voice";
       String value;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //get the value in the stereotype
       String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       return value;
   }
   

// ***************************** targetLanguage, userType ***********************************
// These methods refer to the personal profiles of the users.   
   
   /** 
   * Set the target language for a particular user.
   * @param targetLanguage The identifier of the new target language. 
   * @param userName The identifier of the user.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setTargetLanguage(String targetLanguage, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = "targetLanguage";
       String value = targetLanguage;
       //set the feature value in personal mode
       String request = "/pers?com=setusr&usr=" + userName + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }
   
   /** 
   * Get the target language for a particular user.
   * @param userName The identifier of the user.
   * @return Returns the identifier of the target language. 
   * @throws UMException() if anything goes wrong.   
   **/
   public String getTargetLanguage(String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = "targetLanguage";
       String value;
       //get the feature value in personal mode
       String request = "/pers?com=getusr&usr=" + userName + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       return value;
   }
         
   /**
   * Change the type of a particular user.
   * @param userName The identifier of the user.
   * @param userType The new type of the user.
   * @throws UMException() if anything goes wrong.   
   **/
   public void changeUserType(String userName, String userType) throws UMException {
       PSClientRequest pc;
       //remove old user references to stereotype (can be only one) and add the new one
       String request = "/ster?com=remusr&" + userName + "=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       String request_1 = "/ster?com=addusr&usr=" + userName + "&" + userType + "=";
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }

   /**
   * Get the type of a particular user.
   * @param userName The identifier of the user.
   * @return Returns the type of the user.
   * @throws UMException() if anything goes wrong.   
   **/
   public String getUserType(String userName) throws UMException {
       PSClientRequest pc;
       //get the stereotype (there exists exactly one) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String userType = pc.getValue(0, 0);
       return userType;
   }


// ************************************ assimilation ********************************************
// These methods refer to the personal profiles of the users. When a user is created, the 
// assimilation values of the user for all the facts are set to -1. This signifies that the
// assimilation for a fact is not yet initialized. The initialization is based on the value
// 'initialAssimilation' stored in the stereotype of the user for that fact, or for the
// corresponding functor. The initialazation of uninitialized (value -1) facts takes place
// either when asking for the assimilation value of a fact, or when updating that value.
// The counters that show how many times each entity has been mentioned to each user are set to 0. 

   /**
   * Get the assimilation of a fact for a particular user. (To what degree we think the
   * user knows the fact.)
   * @param factId The identifier of the fact we want the assimilation of.
   * @param functor The functor of the fact. This is needed to determine
   * the initial assimilation of the fact, if the initial assimilation of the
   * fact has been specified for all the facts with a particular functor,
   * rather than individually. (all that in case the assimilation of the fact
   * has not been initialized yet; value -1).
   * @param userName The identifier of the user.
   * @return Returns a float value indicating the assimilation of the fact.
   * Larger values indicate larger degrees of assimilation.
   * @throws UMException() if anything goes wrong.
   **/
   public float getAssimilationFact(String factId, String functor, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = factId + ".assimilation";
       String value;
       //get the feature value in personal mode
       String request = "/pers?com=getusr&usr=" + userName + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       float result;
       try {
           result = Float.parseFloat(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       //check whether assimilation of the fact is uninitialized, if yes initialize it
       if (result == -1) {  //fact assimilation uninitialized
           String ftrOne = factId + ".initialAssimilation";
           String ftrTwo = functor + ".initialAssimilation";
           String initValue;
           //get the stereotype (there exists exactly one) of the specified user
           String request_1 = "/ster?com=getusr&usr=" + userName + "&str=*";
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
           String stereot = pc.getValue(0, 0);
           //get the first priority value in the stereotype
           String request_2 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrOne;
           pc = new PSClientRequest(dst, prt, request_2, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           if (pc.getRows() != 0)
               initValue = pc.getValue(0, 1);
           //if not there, check for the second priority value
           else {
               String request_3 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrTwo;
               pc = new PSClientRequest(dst, prt, request_3, post, timeout);
               if (pc.isError()) throw new UMException(pc.getErrorMessage());
               if (pc.getRows() == 0) throw new UMException("No entry found");
               initValue = pc.getValue(0, 1);
           }
           //this is the initial assimilation value for the fact under the specific stereotype
           try {
               result = Float.parseFloat(initValue);
           } catch(NumberFormatException e) {
               throw new UMException("Value not numeric");
           }
           //initialize the fact assimilation for the specific user
           String request_4 = "/pers?com=setusr&usr=" + userName + "&" + ftrName + "=" + initValue;
           pc = new PSClientRequest(dst, prt, request_4, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
       }
       return result;
   }

   /**
   * Find out how many times an entity has been mentioned to a user.
   * @param entityName The identifier of the entity. 
   * @param userName The identifier of the user.
   * @return Returns the number of times the entity has been mentioned to the user. 
   * @throws UMException() if anything goes wrong.     
   **/
   public int getMentionedCount(String entityName, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = entityName + ".mentioned";
       String value;
       //get the feature value in personal mode
       String request = "/pers?com=getusr&usr=" + userName + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       int result;
       try {
           result = Integer.parseInt(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   /**
   * Find out how many times a micro-planning expresssion has been used in
   * texts presented to a particular user.
   * @param microPlanningId The identifier of the micro-planning expression.
   * @param userName The identifier of the user.
   * @return Returns the number of times the micro-planning expression has
   * been used.
   * @throws UMException() if anything goes wrong.
   **/
   public int getMicroPlanningCount(String microPlanningId, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = microPlanningId + ".used";
       String value;
       //get the feature value in personal mode
       String request = "/pers?com=getusr&usr=" + userName + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       int result;
       try {
           result = Integer.parseInt(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }

   /**
   * Find out how many times a word has been used in texts presented to a
   * particular user.
   * @param wordId The identifier of the word.
   * @param userName The identifier of the user.
   * @return Returns the number of times the word has been used.
   * @throws UMException() if anything goes wrong.
   **/
   public int getWordCount(String wordId, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = wordId + ".used";
       String value;
       //get the feature value in personal mode
       String request = "/pers?com=getusr&usr=" + userName + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       int result;
       try {
           result = Integer.parseInt(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   /**
   * Inform the user model that information has been presented to a user
   * which conveys the provided collection of facts via the provided
   * micro-planning expressions and which mentioned the given entities
   * and used the given microplanning expressions and words.
   * @param mentionedEntities Array containing the identifiers of all the
   * entities that were mentioned. If the identifier of the same entity
   * appears several times in the array, it will be counted as having been
   * mentioned equally many times.
   * @param conveyedFacts Array containing the identifiers of all the
   * facts that were conveyed. The user's assimilation scores of these
   * facts are incremented by the corresponding assimilation rates. If the
   * identifier of a fact appears several times in the array, its
   * assimilation is increased equally many times.
   * @param conveyedFunctors Array containing the corresponding functors
   * of the facts in conveyedFacts. The length of this array must be equal
   * to the length of conveyedFacts.
   * @param usedMicroPlanningExpressions Array containing the identifiers
   * of all the micro-planning expressions that were used to convey the
   * facts. The user model counts how many times
   * each micro-planning expression has been used in texts presented to
   * the particular user. If the identifier of a micro-planning expression
   * appears several times in the array, the corresponding counter will
   * be incremented equally many times.
   * @param usedWords Array containing the identifiers of all the words
   * that were used in the text. The user model counts how many times each
   * word has been used in texts presented to the particular user. If the
   * identifier of a word appears several times in the array, the
   * corresponding counter will be incremented equally many times.
   * @param userName The identifier of the user.
   * @throws UMException() if anything goes wrong.
   **/
   public void update(String[] mentionedEntities, String[] conveyedFacts,
                      String[] conveyedFunctors, String[] usedMicroPlanningExpressions,
                      String[] usedWords, String userName) throws UMException {
       PSClientRequest pc;
       if (conveyedFacts.length != conveyedFunctors.length)
           throw new UMException("Functors do not match Facts");
       //get the stereotype (only one exists) of the specified user
       String request = "/ster?com=getusr&usr=" + userName + "&str=*";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No user type for user " + userName);
       String stereot = pc.getValue(0, 0);
       //for each fact, get the assimilation rate in the stereotype
       //(or, if it does not exist, that of the corresponding functor)
       //and then increase the fact assimilation in the
       //user personal profile by the corresponding rate.
       //if the fact assimilation has not been initialized (value -1)
       //initialize it first from the 'initalAssimilation' value
       //of the fact or the corresponding functor in the stereotype
       //of the specific user (also check 'getAssimilationFact()').
       StringBuffer temp = new StringBuffer();
       temp.append("/pers?com=incval&usr=" + userName);
       for (int i=0; i < conveyedFacts.length; i++) {
           String fact = conveyedFacts[i];
           String functor = conveyedFunctors[i];
           getAssimilationFact(fact, functor, userName);  //initialize if uninitialized
           String ftrOne = fact + ".assimilationRate";
           String ftrTwo = functor + ".assimilationRate";
           String assRate;
           String request_1 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrOne;
           pc = new PSClientRequest(dst, prt, request_1, post, timeout);
           if (pc.isError()) throw new UMException(pc.getErrorMessage());
           if (pc.getRows() != 0)
               assRate = pc.getValue(0, 1);
           //if not there, check for the second priority value
           else {
               String request_2 = "/ster?com=getstr&str=" + stereot + "&ftr=" + ftrTwo;
               pc = new PSClientRequest(dst, prt, request_2, post, timeout);
               if (pc.isError()) throw new UMException(pc.getErrorMessage());
               if (pc.getRows() == 0) throw new UMException("No entry found");
               assRate = pc.getValue(0, 1);
           }
           String assFtrName = fact + ".assimilation";
           temp.append("&" + assFtrName + "=" + assRate);
       }
       String request_3 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_3, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //for each mentioned entity, increase the corresponding
       //'mentioned' feature in the user personal profile by 1
       temp.setLength(0);  //reset
       temp.append("/pers?com=incval&usr=" + userName);
       for (int j=0; j < mentionedEntities.length; j++) {
           String entity = mentionedEntities[j];
           String mtnFtrName = entity + ".mentioned";
           String increment = "1";
           temp.append("&" + mtnFtrName + "=" + increment);
       }
       String request_4 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_4, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //for each used micro plan, increase the corresponding
       //'used' feature in the user personal profile by 1
       temp.setLength(0);  //reset
       temp.append("/pers?com=incval&usr=" + userName);
       for (int j=0; j < usedMicroPlanningExpressions.length; j++) {
           String microPlan = usedMicroPlanningExpressions[j];
           String mtnFtrName = microPlan + ".used";
           String increment = "1";
           temp.append("&" + mtnFtrName + "=" + increment);
       }
       String request_5 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_5, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //for each used word, increase the corresponding
       //'used' feature in the user personal profile by 1
       temp.setLength(0);  //reset
       temp.append("/pers?com=incval&usr=" + userName);
       for (int j=0; j < usedWords.length; j++) {
           String word = usedWords[j];
           String mtnFtrName = word + ".used";
           String increment = "1";
           temp.append("&" + mtnFtrName + "=" + increment);
       }
       String request_6 = temp.substring(0);
       pc = new PSClientRequest(dst, prt, request_6, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
   }


// ***************************************** focus  ***********************************************
// These methods refer to the personal profiles of the users.
       
   /** 
   * Inform the user model that a user has focused on a new entity, i.e. that we have a 
   * new description focus. This method is to be invoked before generating text for the new entity. 
   * It must also be invoked prior to generating any text for the very first entity the 
   * user focuses on. 
   * @param entityName The identifier of the new entity the user has focused on. 
   * @param userName The identifier of the user.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setNewFocus(String entityName, String userName) throws UMException {
       PSClientRequest pc;
       //set the current focus value in personal mode
       String ftrName = "currentFocus";
       String value = entityName;
       String request = "/pers?com=setusr&usr=" + userName + "&" + ftrName + "=" + value;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       //increase the corresponding entity focus feature value in personal mode
       String ftrName_1 = entityName + ".focus";
       String increment = "1";
       String request_1 = "/pers?com=incval&usr=" + userName + "&" + ftrName_1 + "=" + increment;
       pc = new PSClientRequest(dst, prt, request_1, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());       
   }

   /** 
   * Returns the current focus of a user. 
   * @param userName The identifier of the user.
   * @return Returns the identifier of the entity that is the current focus of the user. If 
   * there is no focus (e.g. if the user is just starting to use the system and no 
   * description focus has been selected), nil is returned.
   * @throws UMException() if anything goes wrong.   
   **/
   public String getCurrentFocus(String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = "currentFocus";
       String value;
       //get the feature value in personal mode
       String request = "/pers?com=getusr&usr=" + userName + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       return value.equals("")? null: value;
   }
   
   /** 
   * Finds out which entities a user has focused on in the past.
   * @param userName The identifier of the user.
   * @return Returns an array containing the identifiers of the entities that the user has 
   * focused on in the past. The array is to be viewed as a set: even if the user has 
   * focused on an entity several times, the entity will appear only once in the array; and 
   * the order of the array's elements does not provide any information about the order in which 
   * the entities were focused on. The array does not include the current focus. 
   * @throws UMException() if anything goes wrong.   
   **/
   public String[] getPreviousFoci(String userName) throws UMException {
       PSClientRequest pc;
       //retrieve current focus in order to exclude it
       String currFocus;
       int cFCount;
       try {
           currFocus = getCurrentFocus(userName);
           cFCount = getFocusCount(currFocus, userName);
        } catch(UMException e) {
           currFocus = "";
           cFCount = 0;
        }
        if (currFocus == null) currFocus = "";  //just in case
       //get all entities user has visited ordered by times visited desc
       String request = "/pers?com=sqlusr&whr=up_user:'" + userName 
           + "'|and|up_feature|like|'%.focus'|and|up_numvalue>0|order|by|up_numvalue|desc";
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       int rows = pc.getRows();
       Vector buffer = new Vector(rows);
       for (int i=0; i < rows; i++) {
           String ftrName = pc.getValue(i, 1);
           String entityName = ftrName.substring(0, ftrName.length()-6);  //ommit ".focus"
           if (( ! entityName.equals(currFocus)) || (entityName.equals(currFocus) && cFCount > 1))
               buffer.add(entityName);
       }
       //result may be smaller than 'rows' by one entry (the current focus)
       String[] result = new String[buffer.size()];
       for (int j=0; j < buffer.size(); j++)
           result[j] = (String) buffer.get(j);
       return result;
   }
   
   /**
   * Reports how many times an entity has been the focus of a user.
   * @param entityName The identifier of the entity. 
   * @param userName The identifier of the user.
   * @return Returns the number of times the entity has been the focus of the user, 
   * including in the count the current focus.
   * @throws UMException() if anything goes wrong.    
   **/
   public int getFocusCount(String entityName, String userName) throws UMException {
       PSClientRequest pc;
       String ftrName = entityName + ".focus";
       String value;
       //get the feature value in personal mode
       String request = "/pers?com=getusr&usr=" + userName + "&ftr=" + ftrName;
       pc = new PSClientRequest(dst, prt, request, post, timeout);
       if (pc.isError()) throw new UMException(pc.getErrorMessage());
       if (pc.getRows() == 0) throw new UMException("No entry found");
       value = pc.getValue(0, 1);
       int result;
       try {
           result = Integer.parseInt(value);
       } catch(NumberFormatException e) {
           throw new UMException("Value not numeric");
       }
       return result;
   }
   
   
// ********************************* log analysis for the next version *******************************   
// This is a hook for functionality that will be added in the next version of the UM subsystem.
   
   /** 
   * Get a list of entities to point to at the end of the current description for a particular
   * user. This list is intended to contain pointers generated by log analysis (as in "Other 
   * people who followed your route also visited ..."). The text planner may decide to include 
   * additional pointers, based on the contents of the database (e.g. "Other statues that were 
   * sculpted by ... include ..."). This method is to be implemented in the second version of the 
   * user modeling subsystem. 
   * @param userName The identifier of the user.
   * @return Returns an array containing identifiers of entities, ordered by descending degree 
   * of prominance. If we don't have enough space in the current description to include pointers 
   * to all the entities of the array, we should at least try to include pointers to the first 
   * entities in the array. In the first version of the UM subsystem, the array will always be empty.
   * @throws UMException() if anything goes wrong.   
   **/
   public String[] getForwardPointers(String userName) throws UMException {
       String[] result = new String[0];
       return result;
   }
}
