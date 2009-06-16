package gr.demokritos.iit.eleon.um;


import java.util.Vector;


//===================================================================
// UMVisitImpEmu
//
// Implements the UMVisit interface through calls to an emulation
// of PersServer, running on main memory (no persistent storage).
//===================================================================
public class UMVisitImpEmu implements UMVisit {
    //personalization server emulation variables
    Structures s;  //the main memory structures holding personilization data

//initializers
    public UMVisitImpEmu(Structures s) {
        this.s = s;
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
       UNameVals val = new UNameVals();
       val.userType = userType;
       boolean added = s.users.add(userName, val);  //fails if userName already exists
       if ( ! added) throw new UMException("User " + userName + " already exists");
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
       UNameVals val = new UNameVals();
       val.userType = userType;
       val.targetLang = targetLanguage;
       boolean added = s.users.add(userName, val);  //fails if userName already exists
       if ( ! added) throw new UMException("User " + userName + " already exists");
   }

   /**
   * Check that a user is already registered.
   * @param userName The identifier of the user. 
   * @return True if the user is already registered; false otherwise.
   * @throws UMException() if anything goes wrong.   
   */
   public boolean checkUserExists(String userName) throws UMException {
       return s.users.existsKey(userName);
   }

   /**
   * Delete a user.
   * @param userName The identifier of the user.
   * @throws UMException() if anything goes wrong.   
   */
   public void deleteUser(String userName) throws UMException {
       //remove all references to the specific user
       for (int i=0; i < s.assimilation.size(); i++) {
           DoubleKey key = (DoubleKey) s.assimilation.getKey(i);
           if (key.equalsFieldOne(userName)) {
               s.assimilation.remove(i);
               i = i-1;  //vector shifted entries left, current position is again occupied
           }
       }
       for (int i=0; i < s.entityProp.size(); i++) {
           DoubleKey key = (DoubleKey) s.entityProp.getKey(i);
           if (key.equalsFieldOne(userName)) {
               s.entityProp.remove(i);
               i = i-1;  //vector shifted entries left, current position is again occupied
           }
       }
       for (int i=0; i < s.microCount.size(); i++) {
           DoubleKey key = (DoubleKey) s.microCount.getKey(i);
           if (key.equalsFieldOne(userName)) {
               s.microCount.remove(i);
               i = i-1;  //vector shifted entries left, current position is again occupied
           }
       }
       for (int i=0; i < s.wordCount.size(); i++) {
           DoubleKey key = (DoubleKey) s.wordCount.getKey(i);
           if (key.equalsFieldOne(userName)) {
               s.wordCount.remove(i);
               i = i-1;  //vector shifted entries left, current position is again occupied
           }
       }
       //remove user with its values (properties)
       s.users.remove(userName);
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
       float result = 0;  //any value here
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the first priority value in the stereotype
       DoubleKey key_1 = new DoubleKey(userType, factId);
       FFVals val_1 = (FFVals) s.factProp.getVal(key_1);
       if (val_1 != null)
           result = val_1.interest;
       //if not there, check for the second priority value
       if (val_1 == null || result == -1) {
           DoubleKey key_2 = new DoubleKey(userType, functor);
           FFVals val_2 = (FFVals) s.functorProp.getVal(key_2);
           if (val_2 != null)
               result = val_2.interest;
           if (val_2 == null || result == -1)
               throw new UMException("No associated record for fact or functor");
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
       float result = 0;  //any value here
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the first priority value in the stereotype
       DoubleKey key_1 = new DoubleKey(userType, factId);
       FFVals val_1 = (FFVals) s.factProp.getVal(key_1);
       if (val_1 != null)
           result = val_1.importance;
       //if not there, check for the second priority value
       if (val_1 == null || result == -1) {
           DoubleKey key_2 = new DoubleKey(userType, functor);
           FFVals val_2 = (FFVals) s.functorProp.getVal(key_2);
           if (val_2 != null)
               result = val_2.importance;
           if (val_2 == null || result == -1)
               throw new UMException("No associated record for fact or functor");
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
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the value in the stereotype
       DoubleKey key = new DoubleKey(userType, lexicalItemId);
       Float val = (Float) s.approprLexical.getVal(key);
       if (val == null) throw new UMException("No associated record");
       return val.floatValue();
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
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the value in the stereotype
       DoubleKey key = new DoubleKey(userType, microPlanningId);
       Float val = (Float) s.approprMicroPlan.getVal(key);
       if (val == null) throw new UMException("No associated record");
       return val.floatValue();
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
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the value in the stereotype
       DoubleKey key = new DoubleKey(userType, schemaId);
       Float val = (Float) s.approprSchema.getVal(key);
       if (val == null) throw new UMException("No associated record");
       return val.floatValue();
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
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the value in the stereotype
       UTypeVals val = (UTypeVals) s.userTypes.getVal(userType);
       if (val == null) throw new UMException("No associated record");
       return val.numOfFacts;
   }
   
   /** 
   * Get the maximum number of facts to aggregate per sentence in descriptions for a 
   * particular user.
   * @param userName The identifier of the user.
   * @return Returns the maximum number of facts to aggregate per sentence (a positive integer).
   * @throws UMException() if anything goes wrong.   
   **/
   public int getMaxFactsPerSentence(String userName) throws UMException {
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the value in the stereotype
       UTypeVals val = (UTypeVals) s.userTypes.getVal(userType);
       if (val == null) throw new UMException("No associated record");
       return val.factsPerSentence;
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
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the value in the stereotype
       UTypeVals val = (UTypeVals) s.userTypes.getVal(userType);
       if (val == null) throw new UMException("No associated record");
       return val.numOfForwPointers;
   }

   /** 
   * Get the desired voice for a particular user (e.g. male voice, female voice).
   * @param userName The identifier of the user.
   * @return Returns the identifier of the voice. 
   * @throws UMException() if anything goes wrong.   
   **/
   public String getVoice(String userName) throws UMException {
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the value in the stereotype
       UTypeVals val = (UTypeVals) s.userTypes.getVal(userType);
       if (val == null) throw new UMException("No associated record");
       return val.voice;
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
       UNameVals val = (UNameVals) s.users.getVal(userName);
       if (val == null) throw new UMException("User " + userName + " does not exist");
       val.targetLang = targetLanguage;
   }
   
   /** 
   * Get the target language for a particular user.
   * @param userName The identifier of the user.
   * @return Returns the identifier of the target language. 
   * @throws UMException() if anything goes wrong.   
   **/
   public String getTargetLanguage(String userName) throws UMException {
       UNameVals val = (UNameVals) s.users.getVal(userName);
       if (val == null) throw new UMException("No associated record for user " + userName);
       return val.targetLang.equals("")? null: val.targetLang;
   }
         
   /**
   * Change the type of a particular user.
   * @param userName The identifier of the user.
   * @param userType The new type of the user.
   * @throws UMException() if anything goes wrong.   
   **/
   public void changeUserType(String userName, String userType) throws UMException {
       UNameVals val = (UNameVals) s.users.getVal(userName);
       if (val == null) throw new UMException("User " + userName + " does not exist");
       val.userType = userType;
   }

   /**
   * Get the type of a particular user.
   * @param userName The identifier of the user.
   * @return Returns the type of the user.
   * @throws UMException() if anything goes wrong.   
   **/
   public String getUserType(String userName) throws UMException {
       UNameVals val = (UNameVals) s.users.getVal(userName);
       if (val == null) throw new UMException("No associated record for user " + userName);
       return val.userType.equals("")? null: val.userType;
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
       float assimil = -1;  //means uninitialized
       //check value in user personal profile
       DoubleKey key = new DoubleKey(userName, factId);
       Float val = (Float) s.assimilation.getVal(key);
       if (val != null)  //record exists
           assimil = val.floatValue();
       if (assimil != -1)  //record exists and assimilation entry is initialized
           return assimil;
       //assimilation uninitialized - find initial value
       float initAssim = 0;  //any value here
       //get the stereotype (there exists exactly one) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //get the first priority value in the stereotype
       DoubleKey key_1 = new DoubleKey(userType, factId);
       FFVals val_1 = (FFVals) s.factProp.getVal(key_1);
       if (val_1 != null)
           initAssim = val_1.initAssimil;
       //if not there, check for the second priority value
       if (val_1 == null || initAssim == -1) {
           DoubleKey key_2 = new DoubleKey(userType, functor);
           FFVals val_2 = (FFVals) s.functorProp.getVal(key_2);
           if (val_2 != null)
               initAssim = val_2.initAssimil;
           if (val_2 == null || initAssim == -1)
               throw new UMException("No associated initial assimilation for fact or functor");
       }
       //insert initial value in record
       if (val == null) s.assimilation.add(key, new Float(initAssim));
       else s.assimilation.updateVal(new Float(initAssim), key);
       return initAssim;
   }

   /**
   * Find out how many times an entity has been mentioned to a user.
   * @param entityName The identifier of the entity. 
   * @param userName The identifier of the user.
   * @return Returns the number of times the entity has been mentioned to the user. 
   * @throws UMException() if anything goes wrong.     
   **/
   public int getMentionedCount(String entityName, String userName) throws UMException {
       DoubleKey key = new DoubleKey(userName, entityName);
       EntityVals val = (EntityVals) s.entityProp.getVal(key);
       if (val == null) return 0;
       return val.mentioned;
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
       DoubleKey key = new DoubleKey(userName, microPlanningId);
       Integer val = (Integer) s.microCount.getVal(key);
       if (val == null) return 0;
       return val.intValue();
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
       DoubleKey key = new DoubleKey(userName, wordId);
       Integer val = (Integer) s.wordCount.getVal(key);
       if (val == null) return 0;
       return val.intValue();
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
       //get the stereotype (only one exists) of the specified user
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval == null) throw new UMException("No user entry for " + userName);
       String userType = uval.userType;
       if (userType.equals("")) throw new UMException("No user type for " + userName);
       //for each fact, get the assimilation rate in the stereotype
       //(or, if it does not exist, that of the corresponding functor)
       //and then increase the fact assimilation in the
       //user personal profile by the corresponding rate.
       //if the fact assimilation has not been initialized (value -1)
       //initialize it first from the 'initalAssimilation' value
       //of the fact or the corresponding functor in the stereotype
       //of the specific user (also check 'getAssimilationFact()').
       for (int i=0; i < conveyedFacts.length; i++) {
           float assimRate = -1;  //means uninitialized
           float initAssim = -1;  //means uninitialized
           //get the first priority values in the stereotype
           DoubleKey key_1 = new DoubleKey(userType, conveyedFacts[i]);
           FFVals val_1 = (FFVals) s.factProp.getVal(key_1);
           if (val_1 != null) {
               assimRate = val_1.assimilRate;
               initAssim = val_1.initAssimil;
           }
           //if not there, check for the second priority value
           if (assimRate == -1 || initAssim == -1) {
               DoubleKey key_2 = new DoubleKey(userType, conveyedFunctors[i]);
               FFVals val_2 = (FFVals) s.functorProp.getVal(key_2);
               if (val_2 != null) {
                   if (assimRate == -1) assimRate = val_2.assimilRate;
                   if (initAssim == -1) initAssim = val_2.initAssimil;
               }
               if (assimRate == -1 || initAssim == -1)
                   throw new UMException("No record for fact or functor");
           }
           //ensure assimilation is initialized and set new value
           float assimil = -1;  //means uninitialized
           DoubleKey key = new DoubleKey(userName, conveyedFacts[i]);
           Float val = (Float) s.assimilation.getVal(key);
           if (val != null)  //record exists
               assimil = val.floatValue();
           if (assimil == -1) {  //assimilation uninitialized
               //insert final value in record
               if (val == null) s.assimilation.add(key, new Float(initAssim + assimRate));
               else s.assimilation.updateVal(new Float(initAssim + assimRate), key);
           }
           else  //assimilation already initialized, update value
               s.assimilation.updateVal(new Float(assimil + assimRate), key);
       }
       //for each mentioned entity, increase the corresponding
       //'mentioned' feature in the user personal profile by 1
       for (int k=0; k < mentionedEntities.length; k++) {
           DoubleKey key = new DoubleKey(userName, mentionedEntities[k]);
           s.entityProp.add(key, new EntityVals());  //fails if key already exists
           ((EntityVals)(s.entityProp.getVal(key))).mentioned += 1;
       }
       //for each micro plan, increase the corresponding
       //'used' feature in the user personal profile by 1
       for (int n=0; n < usedMicroPlanningExpressions.length; n++) {
           DoubleKey key = new DoubleKey(userName, usedMicroPlanningExpressions[n]);
           boolean added = s.microCount.add(key, new Integer(1));  //fails if key already exists
           if ( ! added) {
               int count = ((Integer)(s.microCount.getVal(key))).intValue();
               s.microCount.updateVal(new Integer(count+1), key);
           }
       }
       //for each word, increase the corresponding
       //'used' feature in the user personal profile by 1
       for (int m=0; m < usedWords.length; m++) {
           DoubleKey key = new DoubleKey(userName, usedWords[m]);
           boolean added = s.wordCount.add(key, new Integer(1));  //fails if key already exists
           if ( ! added) {
               int count = ((Integer)(s.wordCount.getVal(key))).intValue();
               s.wordCount.updateVal(new Integer(count+1), key);
           }
       }
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
       //set the current focus value in personal mode
       UNameVals val = (UNameVals) s.users.getVal(userName);
       if (val == null) throw new UMException("User " + userName + " does not exist");
       val.currFocus = entityName;
       //increase the corresponding entity focus feature value in personal mode
       DoubleKey key = new DoubleKey(userName, entityName);
       s.entityProp.add(key, new EntityVals());  //fails if key already exists
       ((EntityVals)(s.entityProp.getVal(key))).focused += 1;
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
       UNameVals val = (UNameVals) s.users.getVal(userName);
       if (val == null) throw new UMException("No associated record for user " + userName);
       return val.currFocus.equals("")? null: val.currFocus;
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
       //get current focus
       String currFoc = "";  //same as default value in UNameVals
       UNameVals uval = (UNameVals) s.users.getVal(userName);
       if (uval != null) currFoc = uval.currFocus;
       //get previous foci, current focus excluded in count
       Vector foci = new Vector(50,50);
       for (int i=0; i < s.entityProp.size(); i++) {
           DoubleKey key = (DoubleKey) s.entityProp.getKey(i);
           if (key.equalsFieldOne(userName)) {
               String entity = key.fTwo();
               int fCount = ((EntityVals)(s.entityProp.getVal(i))).focused;
               if (fCount > 1 || (fCount > 0 && ( ! entity.equals(currFoc))))
                   foci.add(entity);
           }
       }
       String[] result = new String[foci.size()];
       for (int j=0; j < foci.size(); j++)
           result[j] = (String) foci.get(j);
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
       DoubleKey key = new DoubleKey(userName, entityName);
       EntityVals val = (EntityVals) s.entityProp.getVal(key);
       if (val == null) return 0;
       return val.focused;
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
