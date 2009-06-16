package gr.demokritos.iit.eleon.um;


import java.util.Vector;


//===================================================================
// UMAuthoringImpEmu
//
// Implements the UMAuthoring interface through calls to an emulation
// of PersServer, running on main memory (no persistent storage).
//===================================================================
public class UMAuthoringImpEmu implements UMAuthoring {
    //personalization server emulation variables
    Structures s;  //the main memory structures holding personilization data

//initializers
    public UMAuthoringImpEmu(Structures s) {
        this.s = s;
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
       //just remove all data, no need to do anything else
       s.clear();
   }

   /**
   * Create a new user type.
   * @param userType The name of the new user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void newUserType(String userType) throws UMException {
       s.userTypes.add(userType, new UTypeVals());  //fails if userType already exists
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
       DoubleKey key = new DoubleKey(userType, factId);
       s.factProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.factProp.getVal(key))).interest = interest;
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
       DoubleKey key = new DoubleKey(userType, functor);
       s.functorProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.functorProp.getVal(key))).interest = interest;
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
       DoubleKey key = new DoubleKey(userType, factId);
       s.factProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.factProp.getVal(key))).importance = importance;
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
       DoubleKey key = new DoubleKey(userType, functor);
       s.functorProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.functorProp.getVal(key))).importance = importance;
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
       DoubleKey key = new DoubleKey(userType, factId);
       s.factProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.factProp.getVal(key))).assimilRate = assimilationRate;
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
       DoubleKey key = new DoubleKey(userType, functor);
       s.functorProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.functorProp.getVal(key))).assimilRate = assimilationRate;
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
       DoubleKey key = new DoubleKey(userType, factId);
       s.factProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.factProp.getVal(key))).initAssimil = initAssimilation;
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
       DoubleKey key = new DoubleKey(userType, functor);
       s.functorProp.add(key, new FFVals());  //fails if key already exists
       ((FFVals)(s.functorProp.getVal(key))).initAssimil = initAssimilation;
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
       //get all users of specified user type
       Vector userNames = new Vector(50,20);
       int size = s.users.size();
       for (int i=0; i < size; i++) {
           String uType = ((UNameVals)(s.users.getVal(i))).userType;
           if (uType.equals(userType))
               userNames.add(s.users.getKey(i));
       }
       //for each (user,fact) pair update assimilation to specified value
       for (int k=0; k < userNames.size(); k++) {
           for (int j=0; j < factIds.length; j++) {
               DoubleKey key = new DoubleKey((String)userNames.get(k), factIds[j]);
               boolean added = s.assimilation.add(key, new Float(assimilation));  //fails if key already exists
               if ( ! added) s.assimilation.updateVal(new Float(assimilation), key);
           }
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
       DoubleKey key = new DoubleKey(userType, lexicalItemId);
       boolean added = s.approprLexical.add(key, new Float(appropriateness));  //fails if key already exists
       if ( ! added) s.approprLexical.updateVal(new Float(appropriateness), key);
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
       DoubleKey key = new DoubleKey(userType, microPlanningId);
       boolean added = s.approprMicroPlan.add(key, new Float(appropriateness));  //fails if key already exists
       if ( ! added) s.approprMicroPlan.updateVal(new Float(appropriateness), key);
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
       DoubleKey key = new DoubleKey(userType, schemaId);
       boolean added = s.approprSchema.add(key, new Float(appropriateness));  //fails if key already exists
       if ( ! added) s.approprSchema.updateVal(new Float(appropriateness), key);
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
       s.userTypes.add(userType, new UTypeVals());  //fails if userType already exists
       ((UTypeVals)(s.userTypes.getVal(userType))).numOfFacts = numberOfFacts;
   }

   /** 
   * Set the maximum number of facts to aggregate per sentence for a user type.
   * @param factsPerSentence The maximum number of facts to aggregate per sentence (a positive integer).
   * @param userType The user type.
   * @throws UMException() if anything goes wrong.   
   **/
   public void setMaxFactsPerSentence(int factsPerSentence, String userType) throws UMException {
       s.userTypes.add(userType, new UTypeVals());  //fails if userType already exists
       ((UTypeVals)(s.userTypes.getVal(userType))).factsPerSentence = factsPerSentence;
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
       s.userTypes.add(userType, new UTypeVals());  //fails if userType already exists
       ((UTypeVals)(s.userTypes.getVal(userType))).numOfForwPointers = numberOfForwardPointers;
   }

   /** 
   * Set the desired voice (e.g. male voice, female voice) for a user type.
   * @param voice The identifier of the new voice.  
   * @param userType The user type. 
   * @throws UMException() if anything goes wrong.   
   **/
   public void setVoice(String voice, String userType) throws UMException {
       s.userTypes.add(userType, new UTypeVals());  //fails if userType already exists
       ((UTypeVals)(s.userTypes.getVal(userType))).voice = voice;
   }
}
