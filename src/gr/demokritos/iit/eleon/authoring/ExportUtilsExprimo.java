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


package gr.demokritos.iit.eleon.authoring;


import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;
import gr.demokritos.iit.eleon.ui.lang.gr.GreekAccentUtils;
import java.util.*;



import org.w3c.dom.*;

/**
 * <p>Title: ExportUtilsExprimo</p>
 * <p>Description: Contains all methods for exporting the domain to xml (EXPRIMO)</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Amy Isard, Dimitris Spiliotopoulos
 * @version 1.0
 */

public class ExportUtilsExprimo 
{
	//private static String SLASH = System.getProperty("file.separator");
	private static String SLASH = "/";
	private static String RESOURCES_DIR = "exprimo" + SLASH + "Resources";
	private static String DOMAINS_DIR = RESOURCES_DIR + SLASH + "Domains";
	private static String LEXICONS_DIR= RESOURCES_DIR + SLASH + "Lexicons";
	private static String PREDICATES_FILENAME = "predicates.xml";
	private static String INSTANCES_FILENAME = "instances.xml";
	private static String TYPES_FILENAME = "types.xml";
	
	private String domainName = "mpiro_domain";
	private Hashtable domainDirs;
	private Hashtable lexiconDirs;
	private String lexiconFileName;
	
	private static String SYSTEM_DOCTYPE_PATH = "http://ltg.ed.ac.uk/exprimo/dtds/";
	private static String LEXICON_DTD = "lexicon.dtd";
	private static String LEXICON_PUBLIC_ID = "//J Calder//Systemic lexicons//";
	private static String PREDICATES_DTD = "def-predicate.dtd";
	private static String PREDICATES_PUBLIC_ID = "-//J Calder, I Hughson//MPIRO predicates//";
	private static String INSTANCES_DTD = "def-object.dtd";
	private static String INSTANCES_PUBLIC_ID = "-//J Calder, I Hughson//MPIRO domain population//";
	private static String TYPES_DTD = "def-taxonomy.dtd";
	private static String TYPES_PUBLIC_ID = "//J Calder///MPIRO domain organization//";
	
	private MpiroZip mpiroZip = null;
	
	private Hashtable lexiconDocs;
	
	private Document typesDoc = null;
        

  // element names

  // lexicon
  private static String LEXICON = "lexicon";
  private static String LEXICAL_ITEM = "def-lexical-item";

  private static String SPELLING = "spelling";
  private static String GRAMMATICAL_FEATURES = "grammatical-features";
  private static String AND_FR = "andFR";
  private static String FEATURE_REF = "feature-ref";
  private static String CONCEPT_LIST = "concepts";
  private static String CONCEPT = "concept";
  private static String SPELLING_EXCEPTION_LIST = "spelling-exceptions";
  private static String SPELLING_EXCEPTION = "spelling-exception";

  // types
  private static String TYPES = "def-types";
  private static String BASIC_TYPE = "def-basic-type";
  private static String TAXONOMY = "def-taxonomy";

  private static String SUBTYPES = "subtypes";
  private static String FILLER_ITEM = "filleritem";
  private static String DOMAIN = "domain";
  private static String HEAD = "head";
  private static String UM_LINK = "um-link";
  private static String TYPE = "type";

  // predicates
  private static String PREDICATES = "def-predicates";
  private static String PREDICATE = "defpredicate";

  private static String OBJECTS = "def-objects";
  private static String OBJECT_STRUCTURE = "defobject-structure";
  private static String OBJECT = "defobject";
  private static String DEF_ROLE = "def-role";
  private static String ROLE = "role";

  private static String EXPRESSION = "expression";
  private static String VERB = "verb";
  private static String TENSE = "tense";
  private static String ARG1FEATURE = "arg1-feature";
  private static String ARG2FORM = "arg2-form";
  private static String SET_FILLER = "set-filler";
  private static String ARG1 = "arg1";
  private static String ARG2 = "arg2";

  private static String TEMPLATE = "template";
  private static String TEMPLATE_BODY = "template-body";
  private static String UNIT_SPECIFICATION = "unit-specification";
  private static String FIELD = "field";
  private static String SPACE = "space";
  private static String STRING = "string";

  // attribute names

  private static String ID = "id";
  private static String IS = "is";
  private static String NAME = "name";
  private static String SHORTNAME = "shortname";
  private static String SLOT = "slot";
  private static String FILLER = "filler";
  private static String SOURCE = "source";
  private static String VALUE = "value";
  private static String CLASS = "class";
  private static String SUBCLASS = "subclass";
  private static String GENERIC = "generic";

  // attribute values

  private static String AGGREGATION_NOT_ALLOWED = "aggregation-not-allowed";
  private static String AGGREGATION_ALLOWED = "aggregation-allowed";
  private static String T = "T";
  private static String FEMALE = "female";
  private static String MALE = "male";
  private static String SEM = "sem";

  private static Hashtable encodings = new Hashtable();
  private static Hashtable abbreviatedLangs = new Hashtable();

  private static boolean warnings = false;
  private static String warningsText = "";
  private static ExportWarningsWindow warningsWindow;


  private Hashtable allNounsHashtable;
  private Vector nounsVector;
  private Vector verbsVector;

  private Hashtable allEntityTypesHashtable;
  private Hashtable noRemoveAllEntityTypesHashtable;
  private Hashtable allEntitiesHashtable;
  private Hashtable allNonGenericEntitiesHashtable;
  private Hashtable allGenericEntitiesHashtable;

  private Vector allBasicTypesVector;
  private Vector allUserTypesVector;
  private Vector allEntityTypesVector;
  private NodeVector basicEntityTypesEntry;

  private static HashMap dateWordsHashMap = new HashMap(3);

	public ExportUtilsExprimo(String domain, ArrayList languageList) 
	{
		System.out.println("domain " + domain + ", languages " + languageList);
		
		domainName = domain +  "_domain";
		mpiroZip = new MpiroZip(domainName + ".zip");
		//	lexiconFileName = domainName + ".xml";
		lexiconFileName = "domain.xml";
		
		domainDirs = new Hashtable();
		lexiconDirs = new Hashtable();
		lexiconDocs = new Hashtable();

		Iterator langIter = languageList.iterator();
		while (langIter.hasNext()) 
		{
	    String lang = langIter.next().toString();
	    //	    domainDirs.put(lang, DOMAINS_DIR + SLASH + lang + SLASH + domainName);
	    domainDirs.put(lang, DOMAINS_DIR + SLASH + lang + SLASH + "domain");
	    lexiconDirs.put(lang, LEXICONS_DIR + SLASH + lang);
	    MpiroDocument lexiconDoc = new MpiroDocument();
	    lexiconDocs.put(lang, lexiconDoc);
		}
				    
		allNounsHashtable = Mpiro.win.struc.getNounsHashtable();
		nounsVector = Mpiro.win.struc.getNounsVectorFromMainLexiconHashtable();
		verbsVector = Mpiro.win.struc.getVerbsVectorFromMainLexiconHashtable();
		
		allEntityTypesHashtable = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		noRemoveAllEntityTypesHashtable = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
		allEntitiesHashtable = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
		allNonGenericEntitiesHashtable = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity");
		allGenericEntitiesHashtable = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Generic");
		
		allBasicTypesVector = Mpiro.win.struc.getChildrenVectorFromMainDBHashtable("Basic-entity-types",
											  "Entity type",
											  allEntityTypesHashtable);
		allUserTypesVector = (Vector)Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		allEntityTypesVector = (Vector)Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types",
													   "Entity type",
													   allEntityTypesHashtable,
													   allEntityTypesHashtable);
		basicEntityTypesEntry = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity("Basic-entity-types");
	
		encodings.put("English", "ISO-8859-1");
		encodings.put("Italian", "ISO-8859-1");
		encodings.put("Greek", "ISO-8859-7");
	
		abbreviatedLangs.put("English", "EN");
		abbreviatedLangs.put("Italian", "IT");
		abbreviatedLangs.put("Greek", "GR");

		HashMap englishDateWords = new HashMap();
		HashMap italianDateWords = new HashMap();
		HashMap greekDateWords = new HashMap();
		englishDateWords.put("AD", "A.D.");
		italianDateWords.put("AD", "d.C.");
		greekDateWords.put("AD", "�.�.");
		englishDateWords.put("BC", "B.C.");
		italianDateWords.put("BC", "a.C.");
		greekDateWords.put("BC", "�.�.");
		englishDateWords.put("between", "between");
		italianDateWords.put("between", "il periodo compreso tra");
		greekDateWords.put("between", "�������");
		englishDateWords.put("yearNumberThe", "");
		italianDateWords.put("yearNumberThe", "il ");
		greekDateWords.put("yearNumberThe", "�� ");
		englishDateWords.put("centuryNumberThe", "the");
		italianDateWords.put("centuryNumberThe", "il");
		greekDateWords.put("centuryNumberThe", "���");
		englishDateWords.put("and", "and");
		italianDateWords.put("and", "e");
		greekDateWords.put("and", "���");
		englishDateWords.put("early", "the early");
		italianDateWords.put("early", "gli inizi del");
		greekDateWords.put("early", "��� ����� ���");
		englishDateWords.put("late", "the late");
		italianDateWords.put("late", "la fine del");
		greekDateWords.put("late", "�� ���� ���");
		englishDateWords.put("century", "century");
		italianDateWords.put("century", "secolo");
		greekDateWords.put("century", "�����");
		englishDateWords.put("circa", "circa");
		italianDateWords.put("circa", "circa");
		greekDateWords.put("circa", "�������");
		englishDateWords.put("first-quarter", "the first quarter of the");
		italianDateWords.put("first-quarter", "il primo quarto del");
		greekDateWords.put("first-quarter", "�� ����� ������� ���");
		englishDateWords.put("second-quarter", "the second quarter of the");
		italianDateWords.put("second-quarter", "il secondo quarto del");
		greekDateWords.put("second-quarter", "�� ������� ������� ���");
		englishDateWords.put("third-quarter", "the third quarter of the");
		italianDateWords.put("third-quarter", "il terzo quarto del");
		greekDateWords.put("third-quarter", "�� ����� ������� ���");
		englishDateWords.put("last-quarter", "the last quarter of the");
		italianDateWords.put("last-quarter", "il ultimo quarto del");
		greekDateWords.put("last-quarter", "�� ��������� ������� ���");
		
		dateWordsHashMap.put("English", englishDateWords);
		dateWordsHashMap.put("Italian", italianDateWords);
		dateWordsHashMap.put("Greek", greekDateWords);
	}	

	public void createLexicon(String language) 
	{
		if (language.equals("English")) 
			{createEnglishLexicon();}
		
		else if (language.equals("Italian")) 
			{createItalianLexicon();}
		
		else if (language.equals("Greek")) 
			{createGreekLexicon();}
		
		else 
			{System.err.println("No code in ExportUtilsExprimo to create lexicon for " + language);}
	}

	/**
	 * Create the English lexicon
	 */
	private void createEnglishLexicon() 
	{
	
		/* Create the XML document for the English lexicon */
		
		MpiroDocument englishLexiconMpiroDoc = (MpiroDocument)lexiconDocs.get("English");
		englishLexiconMpiroDoc.setPublicId(LEXICON_PUBLIC_ID + abbreviatedLangs.get("English"));
		englishLexiconMpiroDoc.setSystemId(SYSTEM_DOCTYPE_PATH + LEXICON_DTD);
		Document englishLexiconDoc = englishLexiconMpiroDoc.getDocument();
		
		Element lexiconElement = englishLexiconDoc.createElement(LEXICON);
		englishLexiconDoc.appendChild(lexiconElement);
		
		Enumeration nounsVectorEnum = nounsVector.elements();
		while (nounsVectorEnum.hasMoreElements()) 
		{
	    String noun = nounsVectorEnum.nextElement().toString();
	    Hashtable currentNounValues = Mpiro.win.struc.showValues(noun, "English");
	    
	    // the spelling from "enbasetext"
	
	    String spelling = currentNounValues.get("enbasetext").toString();
	    if (spelling.equals("")) 
	    {
				sendWarning("English: no spelling defined for noun " + noun);
				System.out.println("Warning: English: found empty spelling for " + noun);
	    }

	    // the grammatical features from "countable"

	    String countable = new String();
	    String countableValue = currentNounValues.get("countable").toString();
	    if (countableValue.equalsIgnoreCase("No")) 
			{
				countable = "mass-noun";
			}
			
	    else 
	    {
				countable = "count-noun";
	    }

	    // the concept
	    
	    ArrayList nounConceptList = Mpiro.win.struc.arrayListReturnAllEntityTypesContainingThisNoun(noun, (Hashtable)noRemoveAllEntityTypesHashtable.clone());
	    
	    ArrayList nounFeatures = new ArrayList();
	    nounFeatures.add("noun");
	    nounFeatures.add("common-noun");
	    nounFeatures.add(countable);

	    /* Add noun to the XML document */
	    
	    Element nounLexicalItemElement = startLexicalItem(englishLexiconDoc, noun, spelling);
	    lexiconElement.appendChild(nounLexicalItemElement);
	    
	    Element nounGrammFeatsElement = englishLexiconDoc.createElement(GRAMMATICAL_FEATURES);
	    nounLexicalItemElement.appendChild(nounGrammFeatsElement);

	    Element nounGrammFeatListElement = englishLexiconDoc.createElement(AND_FR);
	    nounGrammFeatsElement.appendChild(nounGrammFeatListElement);
	    
	    Iterator nounFeatIter = nounFeatures.iterator();
	    while (nounFeatIter.hasNext()) 
	    {
				String feat = nounFeatIter.next().toString();
				if (!feat.equals("")) 
				{
			    Element featureRefElement = englishLexiconDoc.createElement(FEATURE_REF);
			    featureRefElement.setAttribute(NAME, feat);
			    nounGrammFeatListElement.appendChild(featureRefElement);
				}
	    }
	    
	    Element nounConceptListElement = concepts(englishLexiconDoc, nounConceptList, noun);
	    nounLexicalItemElement.appendChild(nounConceptListElement);
	    
	    if (currentNounValues.get("encb").toString().equalsIgnoreCase("true")) 
	    {
				Element nounSpellingExceptionList = englishLexiconDoc.createElement(SPELLING_EXCEPTION_LIST);
				nounLexicalItemElement.appendChild(nounSpellingExceptionList);
				
				String pluralnoun = currentNounValues.get("enpluraltext").toString();
				
				Element nounSpellingExceptionElement = spellingException(englishLexiconDoc, pluralnoun, "plural-noun");
				nounSpellingExceptionList.appendChild(nounSpellingExceptionElement);
	    }
		}
	
		Enumeration verbsVectorEnum = verbsVector.elements();
		while (verbsVectorEnum.hasMoreElements()) 
		{
	    /* Find values for a verb */
	
	    String verb = verbsVectorEnum.nextElement().toString();
	    Hashtable currentVerbValues = Mpiro.win.struc.showValues(verb, "English");
	
	    // the spelling from "enbasetext"

	    String spelling = currentVerbValues.get("vbasetext").toString();

	    if (inUpperModel(verb, false).equals("true")) 
	    {
				System.err.println("Warning: English: verb " + verb + " already defined in Upper Model");
				if (spelling.equals("")) 
				{
					continue;
				}
	    }
	    if (spelling.equals("")) 
	    {
				sendWarning("English: no spelling defined for verb " + verb);
	    }

	    // the grammatical features by checking for auxiliary verbs & from "transitive"

	    String verbType = new String();
	    if ( (spelling.equalsIgnoreCase("be")) || (spelling.equalsIgnoreCase("have")) ) 
	    {
				verbType = "auxverb";
	    }
	    
	    else 
	    {
				verbType = "lexverb";
	    }
	    
	    String transitive = new String();
	    String transitiveValue = currentVerbValues.get("transitive").toString();
	    if (transitiveValue.equalsIgnoreCase("Yes")) 
	    {
				transitive = "transitive-verb";
	    }
	    
	    else if (transitiveValue.equalsIgnoreCase("No")) 
	    {
				transitive = "intransitive-verb";
	    }
	    
	    ArrayList verbFeatures = new ArrayList();
	    verbFeatures.add(verbType);
	    verbFeatures.add(transitive);
	    
	    Hashtable verbExceptions = new Hashtable();
	    
	    if (currentVerbValues.get("vcb1").toString().equalsIgnoreCase("true")) 
	    {
				verbExceptions.put("thirdpersonsingular", currentVerbValues.get("thirdpstext"));
	    }
	    
	    if (currentVerbValues.get("vcb2").toString().equalsIgnoreCase("true")) 
	    {
				verbExceptions.put("past-verb", currentVerbValues.get("sipatext"));
	    }
	    
	    if (currentVerbValues.get("vcb3").toString().equalsIgnoreCase("true")) 
	    {
				verbExceptions.put("gerund-verb", currentVerbValues.get("prpatext"));
	    }
	    
	    if (currentVerbValues.get("vcb4").toString().equalsIgnoreCase("true")) 
	    {
				verbExceptions.put("participle-verb", currentVerbValues.get("papatext"));
	    }
	
	    /* Add verb to the XML document */

	    Element verbLexicalItemElement = startLexicalItem(englishLexiconDoc, verb, spelling);
	    lexiconElement.appendChild(verbLexicalItemElement);
	    
	    Element verbGrammFeatsElement = englishLexiconDoc.createElement(GRAMMATICAL_FEATURES);
	    verbLexicalItemElement.appendChild(verbGrammFeatsElement);

	    Element verbGrammFeatListElement = englishLexiconDoc.createElement(AND_FR);
	    verbGrammFeatsElement.appendChild(verbGrammFeatListElement);

	    Iterator verbFeatsIter = verbFeatures.iterator();
	    while (verbFeatsIter.hasNext()) 
	    {
				String feat = verbFeatsIter.next().toString();
				if (!feat.equals("")) 
				{
			    Element featureRefElement = englishLexiconDoc.createElement(FEATURE_REF);
			    featureRefElement.setAttribute(NAME, feat);
			    verbGrammFeatListElement.appendChild(featureRefElement);
				}
	    }
	    
	    ArrayList verbConceptList = new ArrayList();
	    Element verbConceptListElement = concepts(englishLexiconDoc, verbConceptList, "NIL");
	    verbLexicalItemElement.appendChild(verbConceptListElement);

	    if (!verbExceptions.isEmpty()) 
	    {
				Element verbSpellingExceptionList = englishLexiconDoc.createElement(SPELLING_EXCEPTION_LIST);
				verbLexicalItemElement.appendChild(verbSpellingExceptionList);
				
				Enumeration verbExceptionsEnum = verbExceptions.keys();
				while (verbExceptionsEnum.hasMoreElements()) 
				{
			    String featName = verbExceptionsEnum.nextElement().toString();
			    String spellString = verbExceptions.get(featName).toString();
			    Element verbSpellingExceptionElement = spellingException(englishLexiconDoc, spellString, featName);
			    verbSpellingExceptionList.appendChild(verbSpellingExceptionElement);
				}
	    }
		}

		/* Add the English lexicon to the MpiroZip */

		englishLexiconMpiroDoc.setDocument(englishLexiconDoc);
	
		String englishZipFile = lexiconDirs.get("English") + SLASH + lexiconFileName;
		mpiroZip.addDocument(englishLexiconMpiroDoc, englishZipFile, "ISO-8859-1");
	
	} // createEnglishLexicon


	/**
	 * Create the Italian lexicon
	 */
	private void createItalianLexicon() 
	{
		/* Create the XML document for the Italian lexicon */
		
		MpiroDocument italianLexiconMpiroDoc = (MpiroDocument)lexiconDocs.get("Italian");
		italianLexiconMpiroDoc.setPublicId(LEXICON_PUBLIC_ID + abbreviatedLangs.get("Italian"));
		italianLexiconMpiroDoc.setSystemId(SYSTEM_DOCTYPE_PATH + LEXICON_DTD);
		Document italianLexiconDoc = italianLexiconMpiroDoc.getDocument();
		
		Element lexiconElement = italianLexiconDoc.createElement(LEXICON);
		italianLexiconDoc.appendChild(lexiconElement);
		
		Enumeration nounsVectorEnum = nounsVector.elements();
		while (nounsVectorEnum.hasMoreElements()) 
		{
	    String noun = nounsVectorEnum.nextElement().toString();
	    Hashtable currentNounValues = Mpiro.win.struc.showValues(noun, "Italian");
	    
	    // the spelling from "itbasetext"
	    
	    String spelling = currentNounValues.get("itbasetext").toString();
	    if (spelling.equals("")) 
	    {
				sendWarning("Italian: no spelling defined for noun " + noun);
				System.out.println("Warning: Italian: found empty spelling for " + noun);
	    }
	    
	    // the grammatical features from "countable" and "itgender"
	    
	    String itgender = new String();
	    String itgenderValue = currentNounValues.get("itgender").toString();
	    if (itgenderValue.equalsIgnoreCase("Masculine")) 
	    {
				itgender = "masculine-noun";
	    }
	    
	    else if (itgenderValue.equalsIgnoreCase("Feminine")) 
	    {
				itgender = "feminine-noun";
	    }
	    String countable = new String();
	    String countableValue = currentNounValues.get("countable").toString();
	    if (countableValue.equalsIgnoreCase("No")) 
	    {
				countable = "mass-noun";
	    }
	    
	    else 
	    {
				countable = "count-noun";
	    }
	    
	    // the concept
	    
	    ArrayList nounConceptList = Mpiro.win.struc.arrayListReturnAllEntityTypesContainingThisNoun(noun, (Hashtable)noRemoveAllEntityTypesHashtable.clone());
	    
	    ArrayList nounFeatures = new ArrayList();
	    nounFeatures.add("noun");
	    nounFeatures.add("common-noun");
	    nounFeatures.add(countable);
	    nounFeatures.add(itgender);

	    /* Add noun to the XML document */

	    Element nounLexicalItemElement = startLexicalItem(italianLexiconDoc, noun, spelling);
	    lexiconElement.appendChild(nounLexicalItemElement);
	    
	    Element nounGrammFeatsElement = italianLexiconDoc.createElement(GRAMMATICAL_FEATURES);
	    nounLexicalItemElement.appendChild(nounGrammFeatsElement);
	    
	    Element nounGrammFeatListElement = italianLexiconDoc.createElement(AND_FR);
	    nounGrammFeatsElement.appendChild(nounGrammFeatListElement);
	    
	    Iterator nounFeatIter = nounFeatures.iterator();
	    while (nounFeatIter.hasNext()) 
	    {
				String feat = nounFeatIter.next().toString();
				if (!feat.equals("")) 
				{
			    Element featureRefElement = italianLexiconDoc.createElement(FEATURE_REF);
			    featureRefElement.setAttribute(NAME, feat);
			    nounGrammFeatListElement.appendChild(featureRefElement);
				}
	    }

	    Element nounConceptListElement = concepts(italianLexiconDoc, nounConceptList, noun);
	    nounLexicalItemElement.appendChild(nounConceptListElement);


	    if (currentNounValues.get("itcb").toString().equalsIgnoreCase("true")) 
	    {
				Element nounSpellingExceptionList = italianLexiconDoc.createElement(SPELLING_EXCEPTION_LIST);
				nounLexicalItemElement.appendChild(nounSpellingExceptionList);
				
				String pluralnoun = currentNounValues.get("itpluraltext").toString();
				
				Element nounSpellingExceptionElement = spellingException(italianLexiconDoc, pluralnoun, "plural-noun");
				nounSpellingExceptionList.appendChild(nounSpellingExceptionElement);
	    }
		}
		Enumeration verbsVectorEnum = verbsVector.elements();
		while (verbsVectorEnum.hasMoreElements()) 
		{

	    /* Find values for a verb */

	    String verb = verbsVectorEnum.nextElement().toString();
	    Hashtable currentVerbValues = Mpiro.win.struc.showValues(verb, "Italian");

	    // the spelling from "itbasetext"

	    String spelling = currentVerbValues.get("vbasetext").toString();

	    if (inUpperModel(verb, false).equals("true")) 
	    {
				System.err.println("Warning: Italian: verb " + verb + " already defined in Upper Model");
				if (spelling.equals("")) 
				{
					continue;
				}
	    }

	    if (spelling.equals("")) 
	    {
				sendWarning("Italian: no spelling defined for verb " + verb);
	    }

	    // the grammatical features by checking for auxiliary verbs & from "transitive"

	    String verbType = new String();
	    if ( (spelling.equalsIgnoreCase("essere")) || (spelling.equalsIgnoreCase("avere")) ) 
	    {
				verbType = "auxverb";
	    }
	    
	    else 
	    {
				verbType = "lexverb";
	    }
	    String transitive = new String();
	    String transitiveValue = currentVerbValues.get("transitive").toString();
	    if (transitiveValue.equalsIgnoreCase("Yes")) 
	    {
				transitive = "transitive-verb";
	    }
	    
	    else if (transitiveValue.equalsIgnoreCase("No")) 
	    {
				transitive = "intransitive-verb";
	    }

	    ArrayList verbFeatures = new ArrayList();
	    verbFeatures.add(verbType);
	    verbFeatures.add(transitive);

	    ArrayList verbExceptionSpellings = new ArrayList();
	    ArrayList verbExceptionFeatures = new ArrayList();

	    Vector table1 = (Vector)currentVerbValues.get("vTable");
	    Vector table2 = (Vector)currentVerbValues.get("pTable");
	    
	    Iterator table1Iter = table1.iterator();
	    while (table1Iter.hasNext()) 
	    {
				Vector row = (Vector)table1Iter.next();

				ArrayList featureList = new ArrayList();
				if (row.elementAt(4).toString().equalsIgnoreCase("true")) 
				{
		    	if (row.elementAt(0).toString().equalsIgnoreCase("Present")) 
		    	{
						featureList.add("present-verb");
		    	}
		    	
		    	else if (row.elementAt(0).toString().equalsIgnoreCase("Past continuous")) 
		    	{
						featureList.add("past-verb");
						featureList.add("progressive-verb");
		    	}
		    	
		    	else if (row.elementAt(0).toString().equalsIgnoreCase("Remote past")) 
		    	{
						featureList.add("past-verb");
						featureList.add("simple-verb");
		    	}
		    	
			    if (row.elementAt(1).toString().equalsIgnoreCase("Singular")) 
			    {
						featureList.add("singular-verb");
			    }
			    
			    else if (row.elementAt(1).toString().equalsIgnoreCase("Plural")) 
			    {
						featureList.add("plural-verb");
			    }
			    
			    if (row.elementAt(2).toString().equalsIgnoreCase("1st")) 
			    {
						featureList.add("firstperson");
			    }
			    
			    else if (row.elementAt(2).toString().equalsIgnoreCase("2nd")) 
			    {
						featureList.add("secondperson");
			    }
			    
			    else if (row.elementAt(2).toString().equalsIgnoreCase("3rd")) 
			    {
						featureList.add("thirdperson");
			    }
			    verbExceptionFeatures.add(featureList);
			    verbExceptionSpellings.add(row.elementAt(3).toString());
				}
	    }//end while
	    
	    Iterator table2Iter = table2.iterator();
	    while (table2Iter.hasNext()) 
	    {
				Vector row = (Vector)table2Iter.next();
				
				ArrayList featureList = new ArrayList();
				if (row.elementAt(3).toString().equalsIgnoreCase("true")) 
				{
			    if (row.elementAt(0).toString().equalsIgnoreCase("Masculine")) 
			    {
						featureList.add("participle-verb");
						featureList.add("masculine-verb");
			    }
			    
			    else if (row.elementAt(0).toString().equalsIgnoreCase("Feminine")) 
			    {
						featureList.add("participle-verb");
						featureList.add("feminine-verb");
			    }
			    
			    if (row.elementAt(1).toString().equalsIgnoreCase("Singular")) 
			    {
						featureList.add("singular-verb");
			    }
			    
			    else if (row.elementAt(1).toString().equalsIgnoreCase("Plural")) 
			    {
						featureList.add("plural-verb");
			    }
			    verbExceptionFeatures.add(featureList);
			    verbExceptionSpellings.add(row.elementAt(2).toString());
				}
	    }

	    /* Add verb to the XML document */

	    Element verbLexicalItemElement = startLexicalItem(italianLexiconDoc, verb, spelling);
	    lexiconElement.appendChild(verbLexicalItemElement);
	    
	    Element verbGrammFeatsElement = italianLexiconDoc.createElement(GRAMMATICAL_FEATURES);
	    verbLexicalItemElement.appendChild(verbGrammFeatsElement);

	    Element verbGrammFeatListElement = italianLexiconDoc.createElement(AND_FR);
	    verbGrammFeatsElement.appendChild(verbGrammFeatListElement);

	    Iterator verbFeatsIter = verbFeatures.iterator();
	    while (verbFeatsIter.hasNext()) 
	    {
				String feat = verbFeatsIter.next().toString();
				if (!feat.equals("")) 
				{
			    Element featureRefElement = italianLexiconDoc.createElement(FEATURE_REF);
			    featureRefElement.setAttribute(NAME, feat);
			    verbGrammFeatListElement.appendChild(featureRefElement);
				}
	    }

	    ArrayList verbConceptList = new ArrayList();
	    Element verbConceptListElement = concepts(italianLexiconDoc, verbConceptList, "NIL");
	    verbLexicalItemElement.appendChild(verbConceptListElement);

	    if (!verbExceptionSpellings.isEmpty()) 
	    {
				Element verbSpellingExceptionList = italianLexiconDoc.createElement(SPELLING_EXCEPTION_LIST);
				verbLexicalItemElement.appendChild(verbSpellingExceptionList);
				for (int i = 0; i < verbExceptionSpellings.size(); i++) 
				{
			    Element verbSpellingExceptionElement = spellingException(italianLexiconDoc,
										     (String)verbExceptionSpellings.get(i),
										     (ArrayList)verbExceptionFeatures.get(i));
			    verbSpellingExceptionList.appendChild(verbSpellingExceptionElement);
				}
	    }
		}//end while

		/* Add the Italian lexicon to the MpiroZip */
	
		italianLexiconMpiroDoc.setDocument(italianLexiconDoc);
	
		String italianZipFile = lexiconDirs.get("Italian") + SLASH + lexiconFileName;
		mpiroZip.addDocument(italianLexiconMpiroDoc, italianZipFile, "ISO-8859-1");

	} // createItalianLexicon


	/**
	 * Create the Greek lexicon
	 */
	private void createGreekLexicon() 
	{
	
		/* Create the XML document for the English lexicon */
		
		MpiroDocument greekLexiconMpiroDoc = (MpiroDocument)lexiconDocs.get("Greek");
		greekLexiconMpiroDoc.setPublicId(LEXICON_PUBLIC_ID + abbreviatedLangs.get("Greek"));
		greekLexiconMpiroDoc.setSystemId(SYSTEM_DOCTYPE_PATH + LEXICON_DTD);
		Document greekLexiconDoc = greekLexiconMpiroDoc.getDocument();
		
		Element lexiconElement = greekLexiconDoc.createElement(LEXICON);
		greekLexiconDoc.appendChild(lexiconElement);

		Enumeration nounsVectorEnum = nounsVector.elements();
		while (nounsVectorEnum.hasMoreElements()) 
		{
			String noun = nounsVectorEnum.nextElement().toString();
			Hashtable currentNounValues = Mpiro.win.struc.showValues(noun, "Greek");
			
			String grbasetext = currentNounValues.get("grbasetext").toString();
			String grpluraltext = currentNounValues.get("grpluraltext").toString();
			
			// the grammatical features from "countable", "grgender", "grinflection"
			
			String countable = new String();
			String countableValue = currentNounValues.get("countable").toString();
			if (countableValue.equalsIgnoreCase("No")) 
	    {
				countable = "mass-noun";
	    }
	    
	    else 
	    {
				countable = "count-noun";
	    }
	    
	    String grgender = new String();
	    String grgenderValue = currentNounValues.get("grgender").toString();
	    if (grgenderValue.equalsIgnoreCase("Masculine")) 
	    {
				grgender = "masculine-noun";
	    }
	    
	    else if (grgenderValue.equalsIgnoreCase("Feminine")) 
	    {
				grgender = "feminine-noun";
	    }
	    
	    else if (grgenderValue.equalsIgnoreCase("Neuter")) 
	    {
				grgender = "neuter-noun";
	    }
	    
	    String spelling = new String();
	    ArrayList additionalGrammaticalFeatures = new ArrayList();

	    String grinflection = new String();
	    String grinflectionValue = currentNounValues.get("grinflection").toString();
	    if (grinflectionValue.equalsIgnoreCase("Inflected")) 
	    {
				grinflection = "inflected-noun";
				
				// the spelling from "grbasetext"
				
				if (grbasetext.equalsIgnoreCase("")) 
				{
					spelling = grbasetext;
				}
				else  
				{
					if (grgender.equalsIgnoreCase("neuter-noun")) 
					{
						spelling = GreekAccentUtils.removeAccentFromWord(grbasetext);
					}
					
					else 
					{
						spelling = GreekAccentUtils.removeAccentFromWord(GreekAccentUtils.findNounStem(grbasetext));
					}
					
					String accentFeature = GreekAccentUtils.findAccentFeature(grbasetext);
					String declensionFeature = GreekAccentUtils.findDeclensionFeature(grbasetext, grgenderValue);
					
					additionalGrammaticalFeatures.add(accentFeature);
					additionalGrammaticalFeatures.add(declensionFeature);
					
					String twoEndingNounType = new String("");
					String isosyllableType = new String("");
					if (declensionFeature.equalsIgnoreCase("two-endings-noun")) 
					{
						twoEndingNounType = GreekAccentUtils.findTwoEndingNounType(grbasetext, grpluraltext);
						additionalGrammaticalFeatures.add(twoEndingNounType);
					}
					if (twoEndingNounType.equalsIgnoreCase("isosyllava")) 
					{
						isosyllableType = GreekAccentUtils.findIsosyllableType(grpluraltext);
						additionalGrammaticalFeatures.add(isosyllableType);
					}
				}//else
	    }//if
	    else if (grinflectionValue.equalsIgnoreCase("Not inflected")) 
	    {
				grinflection = "lexicalised-noun";
				
				// the spelling from "grbasetext"
				spelling = grbasetext;
	    }

	    if (spelling.equals("")) 
	    {
				sendWarning("Greek: no spelling defined for noun " + noun);
				System.err.println("Warning: Greek: found empty spelling for " + noun);
	    }
	    // the concept
	    
	    ArrayList nounConceptList = Mpiro.win.struc.arrayListReturnAllEntityTypesContainingThisNoun(noun, (Hashtable)noRemoveAllEntityTypesHashtable.clone());
	    
	    ArrayList nounFeatures = new ArrayList();
	    nounFeatures.add("noun");
	    nounFeatures.add("common-noun");
	    nounFeatures.add(countable);
	    nounFeatures.add(grgender);
	    nounFeatures.add(grinflection);
	    
	    Iterator additionalGrammaticalFeaturesIter = additionalGrammaticalFeatures.iterator();
	    while (additionalGrammaticalFeaturesIter.hasNext()) 
	    {
				nounFeatures.add(additionalGrammaticalFeaturesIter.next().toString());
	    }
	    
	    /* Add noun to the XML document */
	    
	    Element nounLexicalItemElement = startLexicalItem(greekLexiconDoc, noun, spelling);
	    lexiconElement.appendChild(nounLexicalItemElement);
	    
	    Element nounGrammFeatsElement = greekLexiconDoc.createElement(GRAMMATICAL_FEATURES);
	    nounLexicalItemElement.appendChild(nounGrammFeatsElement);
	    
	    Element nounGrammFeatListElement = greekLexiconDoc.createElement(AND_FR);
	    nounGrammFeatsElement.appendChild(nounGrammFeatListElement);
	    
	    Iterator nounFeatIter = nounFeatures.iterator();
	    while (nounFeatIter.hasNext()) 
	    {
				String feat = nounFeatIter.next().toString();
				if (!feat.equals("")) 
				{
			    Element featureRefElement = greekLexiconDoc.createElement(FEATURE_REF);
			    featureRefElement.setAttribute(NAME, feat);
			    nounGrammFeatListElement.appendChild(featureRefElement);
				}
			}
	    
	    Element nounConceptListElement = concepts(greekLexiconDoc, nounConceptList, noun);
	    nounLexicalItemElement.appendChild(nounConceptListElement);

	    ArrayList nounExceptionSpellings = new ArrayList();
	    ArrayList nounExceptionFeats = new ArrayList();

	    if (currentNounValues.get("cb1").toString().equalsIgnoreCase("true")) 
	    {
				nounExceptionSpellings.add(currentNounValues.get("grsntext").toString());
				ArrayList theseFeats = new ArrayList();
				theseFeats.add("singular-noun");
				theseFeats.add("nominative-noun");
				nounExceptionFeats.add(theseFeats);
	    }
	    
	    if (currentNounValues.get("cb2").toString().equalsIgnoreCase("true")) 
	    {
				nounExceptionSpellings.add(currentNounValues.get("grsgtext").toString());
				ArrayList theseFeats = new ArrayList();
				theseFeats.add("singular-noun");
				theseFeats.add("genitive-noun");
				nounExceptionFeats.add(theseFeats);
	    }

	    if (currentNounValues.get("cb3").toString().equalsIgnoreCase("true")) 
	    {
				nounExceptionSpellings.add(currentNounValues.get("grsatext").toString());
				ArrayList theseFeats = new ArrayList();
				theseFeats.add("singular-noun");
				theseFeats.add("accusative-noun");
				nounExceptionFeats.add(theseFeats);
	    }
	
	    if (currentNounValues.get("cb4").toString().equalsIgnoreCase("true")) 
	    {
				nounExceptionSpellings.add(currentNounValues.get("grpntext").toString());
				ArrayList theseFeats = new ArrayList();
				theseFeats.add("plural-noun");
				theseFeats.add("nominative-noun");
				nounExceptionFeats.add(theseFeats);
	    }

	    if (currentNounValues.get("cb5").toString().equalsIgnoreCase("true")) 
	    {
				nounExceptionSpellings.add(currentNounValues.get("grpgtext").toString());
				ArrayList theseFeats = new ArrayList();
				theseFeats.add("plural-noun");
				theseFeats.add("genitive-noun");
				nounExceptionFeats.add(theseFeats);
	    }

	    if (currentNounValues.get("cb6").toString().equalsIgnoreCase("true")) 
	    {
				nounExceptionSpellings.add(currentNounValues.get("grpatext").toString());
				ArrayList theseFeats = new ArrayList();
				theseFeats.add("plural-noun");
				theseFeats.add("accusative-noun");
				nounExceptionFeats.add(theseFeats);
	    }

	    if (!nounExceptionSpellings.isEmpty()) 
	    {
				Element nounSpellingExceptionList = greekLexiconDoc.createElement(SPELLING_EXCEPTION_LIST);
				nounLexicalItemElement.appendChild(nounSpellingExceptionList);
				
				for (int i = 0; i < nounExceptionSpellings.size(); i++) 
				{
			    Element nounSpellingExceptionElement = spellingException(greekLexiconDoc,
										     (String)nounExceptionSpellings.get(0),
										     (ArrayList)nounExceptionFeats.get(0));
			    nounSpellingExceptionList.appendChild(nounSpellingExceptionElement);
				}
	    }
		}//while

		Enumeration verbsVectorEnum = verbsVector.elements();
		while (verbsVectorEnum.hasMoreElements()) 
		{
			/* Find values for a verb */
			
			String verb = verbsVectorEnum.nextElement().toString();
			Hashtable currentVerbValues = Mpiro.win.struc.showValues(verb, "Greek");
			
			// the spelling from "vbasetext"
			
			String vbasetext = currentVerbValues.get("vbasetext").toString();
			String vbasetext2 = currentVerbValues.get("vbasetext2").toString();
			String spelling = new String();
			if (vbasetext.equalsIgnoreCase("")) 
			{
				spelling = vbasetext;
			}
		    
	    else 
	    {
				spelling = GreekAccentUtils.removeAccentFromWord(GreekAccentUtils.findVerbStem(vbasetext));
	    }
	
			if (inUpperModel(verb, false).equals("true")) 
			{
				System.err.println("Warning: Greek: verb " + verb + " already defined in Upper Model");
				if (spelling.equals("")) 
				{
				  continue;
				}
			}
	    if (spelling.equals("")) 
	    {
				sendWarning("Greek: no spelling defined for verb " + verb);
	    }
	
	    // the grammatical features by checking for auxiliary verbs & from "transitive"
	
	    String verbType = new String();
	
	    if ( (vbasetext.equalsIgnoreCase("�����")) || (vbasetext.equalsIgnoreCase("���")) ) 
	    {
				verbType = "auxverb";
	    }
	    
	    else 
	    {
				verbType = "lexverb";
	    }
		    
	    String transitive = new String();
	    String transitiveValue = currentVerbValues.get("transitive").toString();
	    if (transitiveValue.equalsIgnoreCase("Yes")) 
	    {
				transitive = "transitive-verb";
	    }
	    else if (transitiveValue.equalsIgnoreCase("No")) 
	    {
				transitive = "intransitive-verb";
	    }
	    
		  String conjugationFeature = GreekAccentUtils.findConjugationFeature(vbasetext);
		  String classFeature = new String("");
		  if (conjugationFeature.equalsIgnoreCase("second-conjugation")) 
		  {
				classFeature = GreekAccentUtils.findClassFeature(vbasetext2);
		  }
	
	    ArrayList verbFeatures = new ArrayList();
	    verbFeatures.add(verbType);
	    verbFeatures.add(transitive);
	    verbFeatures.add(conjugationFeature);
	    verbFeatures.add(classFeature);
	
	    ArrayList verbExceptionSpellings = new ArrayList();
	    ArrayList verbExceptionFeatures = new ArrayList();
	
	    Vector table1 = (Vector)currentVerbValues.get("vTable");
	    Vector table2 = (Vector)currentVerbValues.get("pTable");
	
	    Iterator table1Iter = table1.iterator();
	
	    while (table1Iter.hasNext()) 
	    {
				Vector row = (Vector)table1Iter.next();
	
				ArrayList featureList = new ArrayList();
				if (row.elementAt(5).toString().equalsIgnoreCase("true")) 
				{
			    if (row.elementAt(0).toString().equalsIgnoreCase("Present progressive")) 
			    {
						featureList.add("present-verb");
						featureList.add("progressive-verb");
			    }
			    
			    else if (row.elementAt(0).toString().equalsIgnoreCase("Past progressive")) 
			    {
						featureList.add("past-verb");
						featureList.add("progressive-verb");
			    }
			    
			    else if (row.elementAt(0).toString().equalsIgnoreCase("Past simple")) 
			    {
						featureList.add("past-verb");
						featureList.add("simple-verb");
			    }
			    
			    if (row.elementAt(1).toString().equalsIgnoreCase("Active")) 
			    {
						featureList.add("active-verb");
			    }
			    
			    else if (row.elementAt(1).toString().equalsIgnoreCase("Passive")) 
			    {
						featureList.add("passive-verb");
			    }
			    
			    if (row.elementAt(2).toString().equalsIgnoreCase("Singular")) 
			    {
						featureList.add("singular-verb");
			    }
			    
			    else if (row.elementAt(2).toString().equalsIgnoreCase("Plural")) 
			    {
						featureList.add("plural-verb");
			    }
			    
			    if (row.elementAt(3).toString().equalsIgnoreCase("1st")) 
			    {
						featureList.add("firstperson");
			    }
			    
			    else if (row.elementAt(3).toString().equalsIgnoreCase("2nd")) 
			    {
						featureList.add("secondperson");
			    }
			    
			    else if (row.elementAt(3).toString().equalsIgnoreCase("3rd")) 
			    {
						featureList.add("thirdperson");
			    }
			    verbExceptionFeatures.add(featureList);
			    verbExceptionSpellings.add(row.elementAt(4).toString());
				}
			}//while
	
	    // Infinitive1, infinitive2, active participle
	    if (currentVerbValues.get("cb1").toString().equalsIgnoreCase("true")) 
	    {
				ArrayList featureList = new ArrayList();
				featureList.add("infinitive");
				featureList.add("active-verb");
				verbExceptionFeatures.add(featureList);
				verbExceptionSpellings.add(currentVerbValues.get("infText").toString());
	    }
	
	    if (currentVerbValues.get("cb2").toString().equalsIgnoreCase("true")) 
	    {
				ArrayList featureList = new ArrayList();
				featureList.add("infinitive");
				featureList.add("passive-verb");
				verbExceptionFeatures.add(featureList);
				verbExceptionSpellings.add(currentVerbValues.get("infText2").toString());
	    }
	
	    if (currentVerbValues.get("cb3").toString().equalsIgnoreCase("true")) 
	    {
				ArrayList featureList = new ArrayList();
				featureList.add("participle-verb");
				featureList.add("active-verb");
				verbExceptionFeatures.add(featureList);
				verbExceptionSpellings.add(currentVerbValues.get("apText").toString());
	    }
	    // table2
	    Iterator table2Iter = table2.iterator();
	    while (table2Iter.hasNext()) 
	    {
				Vector row = (Vector)table2Iter.next();
		
				ArrayList featureList = new ArrayList();
				if (row.elementAt(2).toString().equalsIgnoreCase("true")) 
				{
			    if (row.elementAt(0).toString().equalsIgnoreCase("Masculine")) 
			    {
						featureList.add("participle-verb");
						featureList.add("passive-verb");
						featureList.add("masculine-verb");
			    }
			    
			    else if (row.elementAt(0).toString().equalsIgnoreCase("Feminine")) 
			    {
						featureList.add("participle-verb");
						featureList.add("passive-verb");
						featureList.add("feminine-verb");
			    }
			    
			    else if (row.elementAt(0).toString().equalsIgnoreCase("Neuter")) 
			    {
						featureList.add("participle-verb");
						featureList.add("passive-verb");
						featureList.add("neuter-verb");
			    }
		
			    verbExceptionFeatures.add(featureList);
			    verbExceptionSpellings.add(row.elementAt(1).toString());
				}
			}
		    
	    /* Add verb to the XML document */
	
	    Element verbLexicalItemElement = startLexicalItem(greekLexiconDoc, verb, spelling);
	    lexiconElement.appendChild(verbLexicalItemElement);
	    
	    Element verbGrammFeatsElement = greekLexiconDoc.createElement(GRAMMATICAL_FEATURES);
	    verbLexicalItemElement.appendChild(verbGrammFeatsElement);
	
	    Element verbGrammFeatListElement = greekLexiconDoc.createElement(AND_FR);
	    verbGrammFeatsElement.appendChild(verbGrammFeatListElement);
	
	    Iterator verbFeatsIter = verbFeatures.iterator();
	    while (verbFeatsIter.hasNext()) 
	    {
				String feat = verbFeatsIter.next().toString();
				if (!feat.equals("")) 
				{
			    Element featureRefElement = greekLexiconDoc.createElement(FEATURE_REF);
			    featureRefElement.setAttribute(NAME, feat);
			    verbGrammFeatListElement.appendChild(featureRefElement);
				}
			}
	
	    ArrayList verbConceptList = new ArrayList();
	    Element verbConceptListElement = concepts(greekLexiconDoc, verbConceptList, "NIL");
	    verbLexicalItemElement.appendChild(verbConceptListElement);
	
	    if (!verbExceptionSpellings.isEmpty()) 
	    {
				Element verbSpellingExceptionList = greekLexiconDoc.createElement(SPELLING_EXCEPTION_LIST);
				verbLexicalItemElement.appendChild(verbSpellingExceptionList);
				for (int i = 0; i < verbExceptionSpellings.size(); i++) 
				{
			    Element verbSpellingExceptionElement = spellingException(greekLexiconDoc,
										     (String)verbExceptionSpellings.get(i),
										     (ArrayList)verbExceptionFeatures.get(i));
			    verbSpellingExceptionList.appendChild(verbSpellingExceptionElement);
				}
			}
		}//while

		/* Add the Greek lexicon to the MpiroZip */
	
		greekLexiconMpiroDoc.setDocument(greekLexiconDoc);
	
		String greekZipFile = lexiconDirs.get("Greek") + SLASH + lexiconFileName;
		mpiroZip.addDocument(greekLexiconMpiroDoc, greekZipFile, "ISO-8859-7");
	} // createGreekLexicon


	/**
	 * Create the types.gram
	 * @param languages the languages to export
	 */
	
	public void createTypesGram(ArrayList languages) 
	{
		MpiroDocument mpiroTypesDoc = new MpiroDocument();
	
		mpiroTypesDoc.setSystemId(SYSTEM_DOCTYPE_PATH + TYPES_DTD);
		typesDoc = mpiroTypesDoc.getDocument();
	
		Element typesElement = typesDoc.createElement(TYPES);
		typesDoc.appendChild(typesElement);
	
		Enumeration allBasicTypesVectorEnum = allBasicTypesVector.elements();
		while (allBasicTypesVectorEnum.hasMoreElements()) 
		{
	    String basicEntityType = allBasicTypesVectorEnum.nextElement().toString();
	    String printBasicEntityType = inUpperModel(basicEntityType, true);
	    String upperType = "";
	    
	    NodeVector nv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(basicEntityType);
	    Vector upperVector = (Vector)nv.get(1);
	    int upperVectorSize = upperVector.size();
	    if (upperVectorSize != 1) 
	    {
				System.out.println("|||| Warning: more than one um-link defined for this type, using the first one ||||");
	    }
	    
	    // we add this if to account for ILEX
	    if (upperVector.firstElement().toString().equalsIgnoreCase("3D-physical-object")) 
	    {
				upperType = ("z3d-phys-object");
	    }
	    
	    else 
	    {
				upperType = upperVector.firstElement().toString();
	    }

	    Element basicTypeElement = typesDoc.createElement(BASIC_TYPE);
	    typesElement.appendChild(basicTypeElement);

	    Element domainElement = typesDoc.createElement(DOMAIN);
	    domainElement.setAttribute(IS, domainName);
	    basicTypeElement.appendChild(domainElement);

	    Element headElement = typesDoc.createElement(HEAD);
	    headElement.setAttribute(IS, printBasicEntityType.toLowerCase());
	    basicTypeElement.appendChild(headElement);

	    Element umLinkElement = typesDoc.createElement(UM_LINK);
	    umLinkElement.setAttribute(IS, upperType);
	    basicTypeElement.appendChild(umLinkElement);

	    processTaxonomies(basicEntityType, typesElement);
		}

		// Output document to three XML files and add them to the MpiroZip
	
		mpiroTypesDoc.setDocument(typesDoc);	
		Iterator languageIter = languages.iterator();
		while (languageIter.hasNext()) 
		{
	    String language = languageIter.next().toString();
	    mpiroTypesDoc.setPublicId(TYPES_PUBLIC_ID + abbreviatedLangs.get(language));
	    String typesZipFile = domainDirs.get(language) + SLASH + TYPES_FILENAME;
	    mpiroZip.addDocument(mpiroTypesDoc, typesZipFile, "ISO-8859-1");
		}
	} // createTypesGram


	/**
	 * Create the predicates.gram which includes the expressions
	 * @param languages the languages to export
	 */
	public void createPredicatesGram(ArrayList languages) 
	{
		Hashtable predicatesDocuments = new Hashtable();
		Iterator languagesIter = languages.iterator();
		
		while (languagesIter.hasNext()) 
		{
	    String language = languagesIter.next().toString();

	    MpiroDocument predicatesMpiroDoc = new MpiroDocument();
	    predicatesMpiroDoc.setPublicId(PREDICATES_PUBLIC_ID + abbreviatedLangs.get(language));
	    predicatesMpiroDoc.setSystemId(SYSTEM_DOCTYPE_PATH + PREDICATES_DTD);
	    Document predicatesDoc = predicatesMpiroDoc.getDocument();

	    Element predicatesElement = predicatesDoc.createElement(PREDICATES);
	    predicatesDoc.appendChild(predicatesElement);

	    Element predicateElement = predicatesDoc.createElement(PREDICATE);
	    predicateElement.setAttribute(IS, TYPE);
	    predicatesElement.appendChild(predicateElement);

	    Element expressionElement = predicatesDoc.createElement(EXPRESSION);
	    predicateElement.appendChild(expressionElement);
	    
	    Element verbElement = predicatesDoc.createElement(VERB);
	    verbElement.setAttribute(IS, "be-aux");
	    expressionElement.appendChild(verbElement);

	    Element arg1FeatureElement = predicatesDoc.createElement(ARG1FEATURE);
	    arg1FeatureElement.setAttribute(IS, "positional-reference");
	    expressionElement.appendChild(arg1FeatureElement);

	    Element arg2FormElement = predicatesDoc.createElement(ARG2FORM);
	    arg2FormElement.setAttribute(IS, "classifying-np");
	    expressionElement.appendChild(arg2FormElement);

	    predicatesDocuments.put(language, predicatesMpiroDoc);
		}
	
		Enumeration allEntityTypesVectorEnum = allEntityTypesVector.elements();
		while (allEntityTypesVectorEnum.hasMoreElements()) 
		{
	    String entityType = allEntityTypesVectorEnum.nextElement().toString();
	    String printEntityType = inUpperModel(entityType, true);

	    Vector entityTypeChildrenVector = Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(entityType, "Entity type", noRemoveAllEntityTypesHashtable);

	    Iterator documentIter = predicatesDocuments.keySet().iterator();
	    while (documentIter.hasNext()) 
	    {
				MpiroDocument mpiroPredicatesDoc = (MpiroDocument)predicatesDocuments.get(documentIter.next().toString());
				Document predicatesDoc = mpiroPredicatesDoc.getDocument();
				Element predicatesElement = predicatesDoc.getDocumentElement();

				// for now, only do subclass (type) for exhibit and person (the others don't seem to be used)
				if (entityType.equals("exhibit") || entityType.equals("person")) 
				{
			    Element predicateElement = predicatesDoc.createElement(PREDICATE);
			    predicateElement.setAttribute(IS, TYPE);
			    predicatesElement.appendChild(predicateElement);
	
			    Element arg1Element = predicatesDoc.createElement(ARG1);
			    arg1Element.setAttribute(IS, printEntityType.toLowerCase());
			    predicateElement.appendChild(arg1Element);
	
			    Element expressionElement = predicatesDoc.createElement(EXPRESSION);
			    predicateElement.appendChild(expressionElement);
			    
			    Element verbElement = predicatesDoc.createElement(VERB);
			    verbElement.setAttribute(IS, "be-aux");
			    expressionElement.appendChild(verbElement);

		    	if (entityType.equals("exhibit")) 
		    	{
						Element arg1FeatureElement = predicatesDoc.createElement(ARG1FEATURE);
						arg1FeatureElement.setAttribute(IS, "positional-reference");
						expressionElement.appendChild(arg1FeatureElement);
		    	}
		    	
		    	else if (entityType.equals("person")) 
		    	{
						Element tenseElement = predicatesDoc.createElement(TENSE);
						tenseElement.setAttribute(IS, "past");
						expressionElement.appendChild(tenseElement);
		    	}
			    Element arg2FormElement = predicatesDoc.createElement(ARG2FORM);
			    arg2FormElement.setAttribute(IS, "classifying-np");
			    expressionElement.appendChild(arg2FormElement);
				}
	    }//while
		}//while

		///////////////////////////////////////////////////
	
		allEntityTypesHashtable.remove("Data Base");
		allEntityTypesHashtable.remove("Basic-entity-types");

		for (Enumeration k = allEntityTypesHashtable.keys(), e = allEntityTypesHashtable.elements(); k.hasMoreElements(); ) 
		{
	    String entitytype = k.nextElement().toString();
            
	    String printEntityType = inUpperModel(entitytype, true);
	    String parent = e.nextElement().toString();
	    NodeVector env = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entitytype);	   
	    Vector entitytypeDBTable = (Vector)env.elementAt(0);
            
	    Hashtable microHashtable = env.getMicroPlanningValues();
	    //Hashtable test=QueryHashtable.propertiesHashtable;
	    Vector templateVector = env.getTemplateVector();
	    if(entitytype.substring(0,entitytype.length()-1).endsWith("_occur")) 
            {
             /*    Iterator documentIter = predicatesDocuments.keySet().iterator();
			    
			    while (documentIter.hasNext()) 
			    {
						String language = documentIter.next().toString();
						MpiroDocument mpiroPredicatesDoc = (MpiroDocument)predicatesDocuments.get(language);
						Document predicatesDoc = mpiroPredicatesDoc.getDocument();
						Element predicatesElement = predicatesDoc.getDocumentElement();
                                                NodeList pred = predicatesElement.getChildNodes();
                                                for(int o=0;o<pred.getLength();o++){
                                                Element pred1=(Element) pred.item(o).cloneNode(true);
                                                Element argument1=(Element) pred1.getFirstChild();
                                                System.out.println(argument1.getAttribute("is"));
                                                if (parent.equalsIgnoreCase(argument1.getAttribute("is"))){
                                                    argument1.setAttribute("is",entitytype.substring(0,entitytype.length()-7));
                                                    predicatesElement.appendChild(pred1);
                                                }
                                                
                                              //  System.out.println("++++++++++GFDDGDFGHDFHFD"+argument1.getAttribute("is"));
                                                
                            }}*/
                continue;
            }
	    // create a vector containing all fields for the parent entity-type
	    Vector allFieldsInParent = new Vector();
            
            //if(parent.substring(0,parent.length()-1).endsWith("_occur")) parent=parent.substring(0,parent.length()-7);
            
            Hashtable all= Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("entity type");
            Enumeration allkeys=all.keys();
            Enumeration allelements=all.elements();
            while (allkeys.hasMoreElements()){
                String nextel=allkeys.nextElement().toString();
                String par=allelements.nextElement().toString();
                if (nextel.equalsIgnoreCase(entitytype)||nextel.startsWith(entitytype+"_occur")){
                    
            NodeVector pnv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(par);
	    Vector parentDBTable = (Vector)pnv.elementAt(0);
	    	    Enumeration parentDBTableEnum = parentDBTable.elements();
	    while (parentDBTableEnum.hasMoreElements()) 
	    {
				Vector rowVector = (Vector)parentDBTableEnum.nextElement();
				String field = rowVector.get(0).toString();
                                if (!allFieldsInParent.contains(field))
				allFieldsInParent.addElement(field);
	    }
            }}
	    // export all fields that are not contained in the parent entity-type
	    Enumeration entitytypeDBTableEnum = entitytypeDBTable.elements();
	    while (entitytypeDBTableEnum.hasMoreElements()) 
	    {
				Vector rowVector = (Vector)entitytypeDBTableEnum.nextElement();
				String field = rowVector.get(0).toString();
				String printField = inUpperModel(field, true);
				String filler = rowVector.get(1).toString();
				String printFiller = inUpperModel(filler, true);
				String setValued = rowVector.get(2).toString();
				String setfiller = new String("nil");
		
				if (setValued.equalsIgnoreCase("true")) 
				{
				    setfiller = "t";
				}
		
				if (!allFieldsInParent.contains(field)) 
				{
			    Iterator documentIter = predicatesDocuments.keySet().iterator();
			    
			    while (documentIter.hasNext()) 
			    {
						String language = documentIter.next().toString();
						MpiroDocument mpiroPredicatesDoc = (MpiroDocument)predicatesDocuments.get(language);
						Document predicatesDoc = mpiroPredicatesDoc.getDocument();
						Element predicatesElement = predicatesDoc.getDocumentElement();
						
						ArrayList microplans = new ArrayList();
                                                Vector property=(Vector) Mpiro.win.struc.getProperty(field);
			 Hashtable fieldHash=(Hashtable) property.elementAt(10);
                                                Vector TempVector=(Vector) property.elementAt(11);
						for (int i = 1; i < 6; i++) 
						{
                                     String micro = (String)fieldHash.get(i + ":" + field + ":SELECTION:" + language);
					//    String micro = (String)microHashtable.get(i + ":" + field + ":SELECTION:" + language);
					    if (micro != null && !micro.equalsIgnoreCase("NoMicroPlanning")) 
					    {
								microplans.add(new String(Integer.toString(i)));
					    }
						}

						if (microplans.isEmpty()) 
						{
					    // output a dummy predicate because the author hasn't defined one
		
					    Element predicateElement = predicatesDoc.createElement(PREDICATE);
					    predicateElement.setAttribute(IS, "undefined-" + printField.toLowerCase());
					    predicatesElement.appendChild(predicateElement);
		
					    Element setFillerElement = predicatesDoc.createElement(SET_FILLER);
					    setFillerElement.setAttribute(IS, setfiller.toLowerCase());
					    predicateElement.appendChild(setFillerElement);
					    
					    Element expressionElement = predicatesDoc.createElement(EXPRESSION);
					    predicateElement.appendChild(expressionElement);
					    
					    Element templateElement = predicatesDoc.createElement(TEMPLATE);
					    expressionElement.appendChild(templateElement);
					    
					    Element templateBodyElement = predicatesDoc.createElement(TEMPLATE_BODY);
					    templateElement.appendChild(templateBodyElement);
					    
					    Element spaceElement = predicatesDoc.createElement(SPACE);
					    templateBodyElement.appendChild(spaceElement);
					    
					    Element stringElement = predicatesDoc.createElement(STRING);

                                            templateBodyElement.appendChild(stringElement);
					    
					    Text textChild =  predicatesDoc.createTextNode(printField.toUpperCase() + ":");
					    stringElement.appendChild(textChild);
			    
			    		sendWarning(language + ": no microplanning expression for field " + field);
                                        Object obddd=Mpiro.win.struc.getEntityTypeOrEntity(entitytype);
                                        System.out.print("fff");
                                        
						}
			    
						Iterator microIter = microplans.iterator();
						while (microIter.hasNext()) 
						{
					    int i = Integer.parseInt((String)microIter.next());
				
					    Element predicateElement = predicatesDoc.createElement(PREDICATE);
					    if (microplans.size() == 1) 
					    {
								predicateElement.setAttribute(IS, printField.toLowerCase());
					    }
					    
					    else 
					    {
								predicateElement.setAttribute(IS, printField.toLowerCase() + i);
					    }
					    predicatesElement.appendChild(predicateElement);
		
					    Element arg1Element = predicatesDoc.createElement(ARG1);
					    arg1Element.setAttribute(IS, printEntityType.toLowerCase());
					    predicateElement.appendChild(arg1Element);

					    if ((!filler.equalsIgnoreCase("Date")) &&
						 			(!filler.equalsIgnoreCase("Dimension")) &&
						 			(!filler.equalsIgnoreCase("Number")) &&
						 			(!filler.equalsIgnoreCase("String"))) 
						 	{
								Element arg2Element = predicatesDoc.createElement(ARG2);
								arg2Element.setAttribute(IS, printFiller.toLowerCase());
								predicateElement.appendChild(arg2Element);
					    }

					    Element setFillerElement = predicatesDoc.createElement(SET_FILLER);
					    setFillerElement.setAttribute(IS, setfiller.toLowerCase());
					    predicateElement.appendChild(setFillerElement);
			    
					    // Expressions created here
					    
					    Element expressionElement = null;
                                           if (fieldHash.get(i + ":" + field + ":SELECTION:" + language).toString().equalsIgnoreCase("Clause")) 
					  //  if (microHashtable.get(i + ":" + field + ":SELECTION:" + language).toString().equalsIgnoreCase("Clause")) 
					    {
                                               expressionElement = createMicroplanningExpressionExport(fieldHash, field, i, language, predicatesDoc);
								//expressionElement = createMicroplanningExpressionExport(microHashtable, field, i, language, predicatesDoc);
					    }
					    else if (fieldHash.get(i + ":" + field + ":SELECTION:" + language).toString().equalsIgnoreCase("Template"))
					    //else if (microHashtable.get(i + ":" + field + ":SELECTION:" + language).toString().equalsIgnoreCase("Template")) 
					    {
                                                expressionElement = createTemplateExpressionExport(TempVector, field, i, language, predicatesDoc);
						//		expressionElement = createTemplateExpressionExport(templateVector, field, i, language, predicatesDoc);
					    }
                                            try{
			    		predicateElement.appendChild(expressionElement);
                                            } catch(NullPointerException e2){}
                                          
                                        }//while
		    	}//while
				}//if
	    }//while

	    // output the predicates to XML files and add them to the MpiroZip

	    Iterator documentIter = predicatesDocuments.keySet().iterator();
	    while (documentIter.hasNext()) 
	    {
				String language = documentIter.next().toString();
				MpiroDocument mpiroDoc = (MpiroDocument)predicatesDocuments.get(language);
		
				String zipFile = domainDirs.get(language) + SLASH + PREDICATES_FILENAME;
				mpiroZip.addDocument(mpiroDoc, zipFile, encodings.get(language).toString());
	    }
		}//for
                
                
                for (Enumeration k = allEntityTypesHashtable.keys(), e = allEntityTypesHashtable.elements(); k.hasMoreElements(); ) 
		{
	    String entitytype = k.nextElement().toString();
            
	    String printEntityType = inUpperModel(entitytype, true);
	    String parent = e.nextElement().toString();
	    NodeVector env = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entitytype);	   
	    Vector entitytypeDBTable = (Vector)env.elementAt(0);
            
	    Hashtable microHashtable = env.getMicroPlanningValues();
	    
	    Vector templateVector = env.getTemplateVector();
	    if(entitytype.substring(0,entitytype.length()-1).endsWith("_occur")) 
            {
                 Iterator documentIter = predicatesDocuments.keySet().iterator();
			    
			    while (documentIter.hasNext()) 
			    {
						String language = documentIter.next().toString();
						MpiroDocument mpiroPredicatesDoc = (MpiroDocument)predicatesDocuments.get(language);
						Document predicatesDoc = mpiroPredicatesDoc.getDocument();
						Element predicatesElement = predicatesDoc.getDocumentElement();
                                                NodeList pred = predicatesElement.getChildNodes();
                                                for(int o=0;o<pred.getLength();o++){
                                                Element pred1=(Element) pred.item(o).cloneNode(true);
                                                Element argument1=(Element) pred1.getFirstChild();
                                                System.out.println(argument1.getAttribute("is"));
                                                if (parent.equalsIgnoreCase(argument1.getAttribute("is"))){
                                                    argument1.setAttribute("is",entitytype.substring(0,entitytype.length()-7));
                                                    predicatesElement.appendChild(pred1);
                                                }
                                                
                                              //  System.out.println("++++++++++GFDDGDFGHDFHFD"+argument1.getAttribute("is"));
                                                
                            }}
                continue;
            }
                 Iterator documentIter = predicatesDocuments.keySet().iterator();
	    while (documentIter.hasNext()) 
	    {
				String language = documentIter.next().toString();
				MpiroDocument mpiroDoc = (MpiroDocument)predicatesDocuments.get(language);
		
				String zipFile = domainDirs.get(language) + SLASH + PREDICATES_FILENAME;
				mpiroZip.addDocument(mpiroDoc, zipFile, encodings.get(language).toString());
	    }
                }
                
                
	} // createPredicatesGram and expressions


	/**
	 * Create an instances.xml file for each language which is in the list
	 * @param languages the languages to export
	 */
	public void createInstancesGramAndMsgcat(ArrayList languages) 
	{
		Hashtable instancesDocuments = new Hashtable();
		Iterator languagesIter = languages.iterator();
		
		while (languagesIter.hasNext()) 
		{
	    String language = languagesIter.next().toString();
	    
	    MpiroDocument instancesMpiroDoc = new MpiroDocument();
	    instancesDocuments.put(language, instancesMpiroDoc);
	    Document instancesDoc = instancesMpiroDoc.getDocument();


	    Element instancesElement = instancesDoc.createElement(OBJECTS);
	    instancesDoc.appendChild(instancesElement);
		}
		// create a vector containing all fields for the "Basic-entity-types"
		Vector allFieldsInBasicEntityTypesEntryVector = new Vector();
		
		Vector basicEntityTypesEntryDBTable = (Vector)basicEntityTypesEntry.getDatabaseTableVector();
		Enumeration basicEntityTypesEntryDBTableEnum = basicEntityTypesEntryDBTable.elements();
		while (basicEntityTypesEntryDBTableEnum.hasMoreElements()) 
		{
	    Vector basicEntityTypesEntryDBTableRowVector = (Vector)basicEntityTypesEntryDBTableEnum.nextElement();
	    String basicEntityTypesEntryDBTableRowVectorField = basicEntityTypesEntryDBTableRowVector.get(0).toString();
	    allFieldsInBasicEntityTypesEntryVector.addElement(basicEntityTypesEntryDBTableRowVectorField);
		}
	
		Enumeration allBasicTypesVectorEnum = allBasicTypesVector.elements();
		while (allBasicTypesVectorEnum.hasMoreElements()) 
		{
	    String basicEntityType = allBasicTypesVectorEnum.nextElement().toString();
	    String printBasicEntityType = inUpperModel(basicEntityType, true);
	    
	    // create XML (part 1)
	    Hashtable objectEls = new Hashtable();
	    Iterator instancesDocIter = instancesDocuments.keySet().iterator();
	    while (instancesDocIter.hasNext()) 
	    {
				String language = instancesDocIter.next().toString();
				MpiroDocument mpiroInstancesDoc = (MpiroDocument)instancesDocuments.get(language);
				Document instancesDoc = mpiroInstancesDoc.getDocument();
				Element instancesElement = instancesDoc.getDocumentElement();
				
				Element objectElement = instancesDoc.createElement(OBJECT_STRUCTURE);
				objectElement.setAttribute(IS, printBasicEntityType.toLowerCase());
				instancesElement.appendChild(objectElement);
				objectEls.put(language, objectElement);
	    }
	    
	    NodeVector nv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(basicEntityType);
	    Vector dbTable = (Vector)nv.getDatabaseTableVector();
	    Enumeration dbTableEnum = dbTable.elements();

	    while (dbTableEnum.hasMoreElements()) 
	    {
				Vector rowVector = (Vector)dbTableEnum.nextElement();
				String field = rowVector.get(0).toString();
				if (!allFieldsInBasicEntityTypesEntryVector.contains(field)) 
				{
			    String filler = rowVector.get(1).toString();
			    // create XML (part 2)
			    makeBasicTypeRoleElements(objectEls, field, filler);
				}
	    }
	    // check all children of the basic-entity-type to export their fields under the basic-entity-type
	    
	    Vector childrenEntityTypes = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable(basicEntityType, "Entity type", noRemoveAllEntityTypesHashtable, noRemoveAllEntityTypesHashtable);
	    Enumeration childrenEntityTypesEnum = childrenEntityTypes.elements();
	    while (childrenEntityTypesEnum.hasMoreElements()) 
	    {
				String childEntityType = childrenEntityTypesEnum.nextElement().toString();
				String parentEntityType = allEntityTypesHashtable.get(childEntityType).toString();
				NodeVector env = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(childEntityType);
				NodeVector pnv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(parentEntityType);
				Vector entitytypeDBTable = env.getDatabaseTableVector();
				Vector parentDBTable = pnv.getDatabaseTableVector();
				
				// create a vector containing all fields for the parent entity-type
				Vector allFieldsInParent = new Vector();
				Enumeration parentDBTableEnum = parentDBTable.elements();
				while (parentDBTableEnum.hasMoreElements()) 
				{
			    Vector parentRowVector = (Vector)parentDBTableEnum.nextElement();
			    String parentField = parentRowVector.get(0).toString();
			    allFieldsInParent.addElement(parentField);
				}
				// export all fields that are not contained in the parent entity-type
				Enumeration entitytypeDBTableEnum = entitytypeDBTable.elements();
				while (entitytypeDBTableEnum.hasMoreElements()) 
				{
			    Vector entityRowVector = (Vector)entitytypeDBTableEnum.nextElement();
			    String entityField = entityRowVector.get(0).toString();
			    if (!allFieldsInParent.contains(entityField)) 
			    {
						String entityFiller = entityRowVector.get(1).toString();
						// create XML (part 3)
						makeBasicTypeRoleElements(objectEls, entityField, entityFiller);
			    }
				}
	    }//while
		}//while

		//
		// <!-- EXHIBITS -->
		//
	
		// Enumerating all entities

		Enumeration allEntitiesHashtableEnum = allEntitiesHashtable.keys();
	
		while (allEntitiesHashtableEnum.hasMoreElements()) 
		{ // while 1
	    String entity = allEntitiesHashtableEnum.nextElement().toString();

	    String classOfEntity = findClassAndSubclassOfEntity(entity, "class");
	    String printClassOfEntity = inUpperModel(classOfEntity, true);

	    String subClassOfEntity = findClassAndSubclassOfEntity(entity, "subclass");
	    String printSubClassOfEntity = inUpperModel(subClassOfEntity, true);
		
	    // Create XML for object elements and class, subclass, generic roles
	    Hashtable objectElements = makeObjectElements(instancesDocuments, entity.toLowerCase());
	    makeRoleElements(objectElements, CLASS, printClassOfEntity.toLowerCase());
	    makeRoleElements(objectElements, SUBCLASS, printSubClassOfEntity.toLowerCase());
		
	    if (entity.startsWith("Generic-")) 
	    {
				makeRoleElements(objectElements, GENERIC, T);
	    }

	    NodeVector nv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(entity);
	    Vector independentFieldsVector = nv.getIndependentFieldsVector();
	    Hashtable dependentFieldsVectors = new Hashtable();
	    dependentFieldsVectors.put("English", nv.getEnglishFieldsVector());
	    dependentFieldsVectors.put("Italian", nv.getItalianFieldsVector());
	    dependentFieldsVectors.put("Greek", nv.getGreekFieldsVector());

	    // adding the fields from independent table
	    Enumeration independentFieldsVectorEnum = independentFieldsVector.elements();
	    while (independentFieldsVectorEnum.hasMoreElements()) 
	    { // while 2
				Vector row = (Vector)independentFieldsVectorEnum.nextElement();
				String field = row.get(0).toString();
				String filler = row.get(1).toString();
				if ( // if 1
		    	(!field.equalsIgnoreCase("entity-id")) &&
		    	(!field.equalsIgnoreCase("type")) &&
		    	(!field.equalsIgnoreCase("images"))) 
		    {
		    	if ( // if 2
						(!filler.equalsIgnoreCase("")) &&
						(!filler.startsWith("Select "))) 
					{
						if (findFillerTypeOfEntityField(entity, field).equalsIgnoreCase("Date")) 
						{ // if 3
							// this is Date
							makeRoleElements(objectElements, field, createExportDates(filler, languages));
						}
						
						else if (findFillerTypeOfEntityField(entity, field).equalsIgnoreCase("Dimension")) 
						{
							// this is Dimension
							makeRoleElements(objectElements, field, createExportDimensions(filler, languages));
						}
						
						else 
						{
							String printField = inUpperModel(field, true);
							makeRoleElements(objectElements, printField.toLowerCase(), filler.toLowerCase());
							// export to file (part 3)
						} // if 3
	    		} // if 2
				} // if 1
	    } // while 2
		
	    // adding the fields from English table

	    Iterator languageIter = languages.iterator();
	    while (languageIter.hasNext()) 
	    { // while 2
				String language = languageIter.next().toString();
				Element objectElement = (Element)objectElements.get(language);
				Enumeration dependentFieldsVectorEnum = ((Vector)dependentFieldsVectors.get(language)).elements();
				Vector checkNameAndShortName = new Vector();
				String numberLexicon = new String();
				String nameLexicon = new String();
				String nameGrammaticalGenderLexicon = new String();
				String shortnameLexicon = new String();
				String shortnameGrammaticalGenderLexicon = new String();
				String namenomLexicon = new String();
				String namegenLexicon = new String();
				String nameaccLexicon = new String();
				String shortnamenomLexicon = new String();
				String shortnamegenLexicon = new String();
				String shortnameaccLexicon = new String();

				while (dependentFieldsVectorEnum.hasMoreElements()) 
				{ // while 3
			    Vector row = (Vector)dependentFieldsVectorEnum.nextElement();
			    String field = row.get(0).toString();
			    String filler = row.get(1).toString();
			    if (!filler.equalsIgnoreCase("")) 
			    { // if 1	    
						if (field.equalsIgnoreCase("gender")) 
						{
					    if (filler.equalsIgnoreCase("masculine")) 
					    {
								makeRoleElement(objectElement, field, MALE);
					    }
					    
					    else if (filler.equalsIgnoreCase("feminine")) 
					    {
								makeRoleElement(objectElement, field, FEMALE);
					    }
						}	
						else if (field.equalsIgnoreCase("number")) 
						{
			    		if (filler.equalsIgnoreCase("plural")) 
			    		{
								makeRoleElement(objectElement, field, filler + "-thing");
								numberLexicon = filler;
			    		}
						}
			    
						else if (field.equalsIgnoreCase("name")) 
						{
					    checkNameAndShortName.add(field);
					    nameLexicon = filler;
					    makeRoleElement(objectElement, field, filler);
						}
			    
						else if (field.equalsIgnoreCase("grammatical gender of name")) 
						{
					    if (checkNameAndShortName.contains("name") || checkNameAndShortName.contains("name (nominative)")) 
					    {
								nameGrammaticalGenderLexicon = filler;
					    }
						}
			    
						else if (field.equalsIgnoreCase("shortname")) 
						{
					    checkNameAndShortName.add(field);
					    shortnameLexicon = filler;
					    makeRoleElement(objectElement, field, filler);
						}
			    
						else if (field.equalsIgnoreCase("grammatical gender of shortname")) 
						{
					    if (checkNameAndShortName.contains("shortname") || checkNameAndShortName.contains("shortname (nominative)")) 
					    {
								shortnameGrammaticalGenderLexicon = filler;
					    }
						}
			    
						else if (field.equalsIgnoreCase("name (nominative)")) 
						{
					    checkNameAndShortName.add(field);
					    namenomLexicon = filler;
					    makeRoleElement(objectElement, NAME, filler);
						}
			    
						else if (field.equalsIgnoreCase("name (genitive)")) 
						{
					    if (checkNameAndShortName.contains("name (nominative)")) 
					    {
								namegenLexicon = filler;
					    }
						}
			    
						else if (field.equalsIgnoreCase("name (accusative)")) 
						{
					    if (checkNameAndShortName.contains("name (nominative)")) 
					    {
								nameaccLexicon = filler;
					    }
						}
			    
						else if (field.equalsIgnoreCase("shortname (nominative)")) 
						{
					    checkNameAndShortName.add(field);
					    shortnamenomLexicon = filler;
					    makeRoleElement(objectElement, SHORTNAME, filler);
						}
						    
						else if (field.equalsIgnoreCase("shortname (genitive)")) 
						{
					    if (checkNameAndShortName.contains("shortname (nominative)")) 
					    {
								shortnamegenLexicon = filler;
					    }
						}
						    
						else if (field.equalsIgnoreCase("shortname (accusative)")) 
						{
					    if (checkNameAndShortName.contains("shortname (nominative)")) 
					    {
								shortnameaccLexicon = filler;
					    }
						}

						else 
						{
							/*
							  if ((filler.indexOf('<') > -1) || (filler.indexOf('&') > -1) || (filler.indexOf('"') > -1)) {
							  filler = replaceIllegalCharactersForXml(filler);
							  }
							*/
							String printField = inUpperModel(field, true);
							
							if (!(printField.equalsIgnoreCase("title") || printField.equalsIgnoreCase("notes"))) 
							{
								filler = addFullStopAndRemoveTrailingSpaces(printField, filler);
								makeRoleElement(objectElement, printField.toLowerCase(), filler);
							}
						} // if 2
		    	} // if 1
				}// while 3
				if (language.equals("Italian")) 
				{
			    if (checkNameAndShortName.contains("name")) 
			    {
						appendProperNounToItalianLexicon("name", entity, nameLexicon, nameGrammaticalGenderLexicon, numberLexicon);
			    }
			    if (checkNameAndShortName.contains("shortname")) 
			    {
						appendProperNounToItalianLexicon("shortname", entity, shortnameLexicon, shortnameGrammaticalGenderLexicon, numberLexicon);
			    }
				}
				
				else if (language.equals("Greek")) 
				{
			    if (checkNameAndShortName.contains("name (nominative)")) 
			    {
						appendProperNounToGreekLexicon("name", entity, namenomLexicon, namegenLexicon, nameaccLexicon, nameGrammaticalGenderLexicon, numberLexicon);
			    }
			    if (checkNameAndShortName.contains("shortname (nominative)")) 
			    {
						appendProperNounToGreekLexicon("shortname", entity, shortnamenomLexicon, shortnamegenLexicon, shortnameaccLexicon, shortnameGrammaticalGenderLexicon, numberLexicon);
			    }
				}
	    }// while 2
		}// while 1
	
		// output the instances to XML documents and add them to the MpiroZip
    
		Iterator documentIter = instancesDocuments.keySet().iterator();
		while (documentIter.hasNext()) 
		{
	    String language = documentIter.next().toString();
	    MpiroDocument mpiroDoc = (MpiroDocument)instancesDocuments.get(language);
	    mpiroDoc.setPublicId(INSTANCES_PUBLIC_ID + abbreviatedLangs.get(language));
	    mpiroDoc.setSystemId(SYSTEM_DOCTYPE_PATH + INSTANCES_DTD);
	
	    String zipFile = domainDirs.get(language) + SLASH + INSTANCES_FILENAME;
	    mpiroZip.addDocument(mpiroDoc, zipFile, encodings.get(language).toString());
		}
	      
		if (languages.contains("Italian")) 
		{
	    String italianZipFile = lexiconDirs.get("Italian") + SLASH + lexiconFileName;
	    mpiroZip.addDocument((MpiroDocument)lexiconDocs.get("Italian"), italianZipFile, "ISO-8859-1");
		}
		
		if (languages.contains("Greek")) 
		{
	    String greekZipFile = lexiconDirs.get("Greek") + SLASH + lexiconFileName;
	    mpiroZip.addDocument((MpiroDocument)lexiconDocs.get("Greek"), greekZipFile, "ISO-8859-7");
		}   
	} // createInstancesGramAndMsgcat


  /**
   * Find the filler type of an entity or generic-entity field
   * @param entity the name of the entity or generic-entity
   * @param field the name of the field
   * @return the name of the filler
   */

  private String findFillerTypeOfEntityField(String entity, String field) 
  {
		String returnString = new String();
		
		String parent = allEntitiesHashtable.get(entity).toString();
		NodeVector nv = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(parent);
		Vector dbTableVector = (Vector)nv.getDatabaseTableVector();
		Enumeration dbTableVectorEnum = dbTableVector.elements();
		while (dbTableVectorEnum.hasMoreElements()) 
		{
			Vector row = (Vector)dbTableVectorEnum.nextElement();
			String parentField = row.get(0).toString();
			String parentFiller = row.get(1).toString();
			if (parentField.equalsIgnoreCase(field)) 
			{
				return parentFiller;
			}
		}
		return returnString;
	}
	
	
  /**
   * Find the entity-type immediate ancestor (subclass) or
   * the basic-entity-type ancestor (class) of an entity or generic-entity
   * @param entity the name of the entity or generic-entity
   * @param classOrSubclass "class" or "subclass"
   * @return the name of the ancestor entity-type or basic-entity-type
   */
  private String findClassAndSubclassOfEntity(String entity, String classOrSubclass) 
  {
		String returnString = new String();
		
		String parent = allEntitiesHashtable.get(entity).toString();
		if (classOrSubclass.equalsIgnoreCase("class")) 
		{	
			Enumeration allBasicTypesVectorEnum = allBasicTypesVector.elements();
			while (allBasicTypesVectorEnum.hasMoreElements()) 
			{
				String basicType = allBasicTypesVectorEnum.nextElement().toString();
				
				Vector basicTypeFullPathChildrenEntitiesVector = Mpiro.win.struc.getFullPathChildrenVectorFromMainDBHashtable(basicType, "Entity+Generic", noRemoveAllEntityTypesHashtable, allEntitiesHashtable);
				if (basicTypeFullPathChildrenEntitiesVector.contains(entity)) 
				{
					returnString = basicType;
					return returnString;
				}
			}
		}
		else if (classOrSubclass.equalsIgnoreCase("subclass")) 
		{
			returnString = parent;
		}
		return returnString;
  }

  private Hashtable createExportDates(String date, ArrayList languages) 
  {
		Hashtable returnDates = new Hashtable();
		Iterator languageIter = languages.iterator();
		while (languageIter.hasNext()) 
		{
	    StringBuffer returnDate = new StringBuffer();
	    String language = languageIter.next().toString();

	    boolean circa = false;
	    if (date.startsWith("between")) 
	    {
				boolean singleBCAD = false;
				String date1 = date.substring(date.indexOf("(")+1 , date.indexOf(")"));
				String date2 = date.substring(date.lastIndexOf("(")+1 , date.lastIndexOf(")"));
				if (date1.substring(date1.length()-2).equals(date2.substring(date2.length()-2))) 
				{
		    	singleBCAD = true;
				}
				
				if (date1.startsWith("c.") || date2.startsWith("c.")) 
				{
					circa = true;
				}
				
				if (circa && language.equals("Greek")) 
				{
			    returnDate.append(((HashMap)dateWordsHashMap.get(language)).get("circa").toString());
			    returnDate.append(" ");
				}
				returnDate.append(((HashMap)dateWordsHashMap.get(language)).get("between").toString());
				returnDate.append(" ");
				if (language.equals("Greek")) 
				{
			    returnDate.append("��");
			    returnDate.append(" ");
				}
				
				if (circa && language.equals("English")) 
				{
			    returnDate.append(((HashMap)dateWordsHashMap.get(language)).get("circa").toString());
			    returnDate.append(" ");
				}
				returnDate.append(normalizeDate(date1, language, singleBCAD));
				returnDate.append(" ");
				returnDate.append(((HashMap)dateWordsHashMap.get(language)).get("and").toString());
				returnDate.append(" ");
				returnDate.append(normalizeDate(date2, language, false));
	    }//if
	    else 
	    {
				String date1 = date.substring(date.indexOf("(")+1 , date.indexOf(")"));
				if (date1.startsWith("c.")) 
				{
			    circa = true;
			    if (language.equals("English") || language.equals("Greek")) 
			    {
						returnDate.append(((HashMap)dateWordsHashMap.get(language)).get("circa").toString());
						returnDate.append(" ");
			    }
				}
				if (language.equals("Greek")) 
				{
			    returnDate.append("��");
			    returnDate.append(" ");
				}
				returnDate.append(normalizeDate(date1, language, false));
	    }
	    if (language.equals("Italian") && circa) 
	    {
				returnDate.append(" ");
				returnDate.append(((HashMap)dateWordsHashMap.get(language)).get("circa").toString());
	    }
	    returnDates.put(language, returnDate.toString());
		}//while
		return returnDates;
	}//createExportDates


  private static String normalizeDate(String date, String language, boolean eliminateBCAD) 
  {
		StringTokenizer tokenizedDate = new StringTokenizer(date);
		ArrayList dateArray = new ArrayList();
		while (tokenizedDate.hasMoreTokens()) 
		{
	    String dateToken = tokenizedDate.nextToken();
	    dateArray.add(dateToken);
		}
		StringBuffer returnString = new StringBuffer();
		String circa = "";
		String number = "";
		String bcad = "";
		String timePeriod = "";
		String modifier = "";
		if (dateArray.get(0).toString().equals("c.")) 
		{
	    number = dateArray.get(1).toString();
	    dateArray.remove(0);
	    dateArray.remove(0);
		}
		
		else 
		{
	    number = dateArray.get(0).toString();
	    dateArray.remove(0);
		}
		timePeriod = dateArray.get(0).toString();
		dateArray.remove(0);
		if (dateArray.size() > 1) 
		{
	    modifier = dateArray.get(0).toString();
	    dateArray.remove(0);
		}
		bcad = dateArray.get(0).toString();
		dateArray.remove(0);

		if (timePeriod.equals("year")) 
		{

	    returnString.append(((HashMap)dateWordsHashMap.get(language)).get("yearNumberThe").toString());
	    returnString.append(number);
	    if (!eliminateBCAD) 
	    {
				returnString.append(" ");
				returnString.append(((HashMap)dateWordsHashMap.get(language)).get(bcad).toString());
	    }
		}
		
		else if (timePeriod.equals("century")) 
		{
	    if (!modifier.equals("")) 
	    {
				returnString.append(((HashMap)dateWordsHashMap.get(language)).get(modifier).toString());
	    }
	    
	    else 
	    {
				returnString.append(((HashMap)dateWordsHashMap.get(language)).get("centuryNumberThe").toString());
	    }
	    returnString.append(" ");
	    if (!language.equals("Italian")) 
	    {
				returnString.append(number);
	    }
	    returnString.append(getOrdinal(number, language, modifier));
	    returnString.append(" ");
	    returnString.append(((HashMap)dateWordsHashMap.get(language)).get(timePeriod).toString());
	    if (!eliminateBCAD) 
	    {
				returnString.append(" ");
				returnString.append(((HashMap)dateWordsHashMap.get(language)).get(bcad).toString());
	    }
		}
		return returnString.toString();
	}//normalizeDate

  private static String getOrdinal(String number, String language, String modifier) 
  {
		String returnString = "";
		if (language.equals("Greek")) 
		{
	    if (modifier.equals("")) 
	    {
				returnString = "�";
	    }
	    
	    else 
	    {
				returnString = "��";
	    }
		}
		else if (language.equals("English")) 
		{
	    if (number.equals("1")) 
	    {
				returnString = "st";
	    }
	    
	    else if (number.equals("2")) 
	    {
				returnString = "nd";
	    }
	    
	    else if (number.equals("3")) 
	    {
				returnString = "rd";
	    }
	    
	    else 
	    {
				returnString = "th";
	    }
		}
		else if (language.equals("Italian")) 
		{
	    if (number.equals("1")) 
	    {
				returnString = "primo";
	    }
	    
	    else if (number.equals("2")) 
	    {
				returnString = "secondo";
	    }
	    
	    else if (number.equals("3")) 
	    {
				returnString = "terzo";
	    }
	    
	    else if (number.equals("4")) 
	    {
				returnString = "quarto";
	    }
	    
	    else if (number.equals("5")) 
	    {
				returnString = "quinto";
	    }
	    
	    else if (number.equals("6")) 
	    {
				returnString = "sesto";
	    }
	    
	    else if (number.equals("7")) 
	    {
				returnString = "settimo";
	    }
	    
	    else if (number.equals("8")) 
	    {
				returnString = "ottavo";
	    }
	    
	    else if (number.equals("9")) 
	    {
				returnString = "nono";
	    }
		}
		return returnString;
	}//getOrdinal

  private static Hashtable createExportDimensions(String dimension, ArrayList languages) 
  {
		StringTokenizer tokenizedDimension = new StringTokenizer(dimension);
		int length = tokenizedDimension.countTokens();
		String number = "";
		String unit = "";
		String wordUnit = "";
		if (length >= 2) 
		{
	    number = tokenizedDimension.nextToken();
	    for (int i = 0; i < length -2; i++) 
	    {
				number = number + tokenizedDimension.nextToken();
	    }
	    unit = tokenizedDimension.nextToken();
		}
		else 
		{
	    number = "0";
	    unit = tokenizedDimension.nextToken();
		}
		Hashtable englishDim = new Hashtable();
		englishDim.put("kgr", "kilogram");
		englishDim.put("Greek", "gram");
		englishDim.put("m", "metre");
		englishDim.put("cm", "centimetre");
		englishDim.put("mm", "millimetre");
		englishDim.put("sqrm", "square metre");
	
		Hashtable returnDimensions = new Hashtable();
		Iterator languageIter = languages.iterator();
		while (languageIter.hasNext()) 
		{
	    String language = languageIter.next().toString();
	    if (number.equals("1")) 
	    {
				if (language.equalsIgnoreCase("English")) 
				{
					wordUnit = (String)englishDim.get(unit);
				}
				else if (language.equalsIgnoreCase("Greek")) 
				{
			    if (unit.equals("kgr")) 
			    {
						wordUnit = "����";
			    }
			    else if (unit.equals("Greek")) 
			    {
						wordUnit = "���������";
			    }
			    else if (unit.equals("m")) 
			    {
						wordUnit = "�����";
			    }
			    else if (unit.equals("cm")) 
			    {
						wordUnit = "��������";
			    }
			    else if (unit.equals("mm")) 
			    {
						wordUnit = "��������";
			    }
			    else if (unit.equals("sqrm")) 
			    {
						wordUnit = "����������� �����";
			    }
				}
				else if (language.equalsIgnoreCase("Italian")) 
				{
			    if (unit.equals("kgr")) 
			    {
						wordUnit = "chilogrammo";
			    }
			    else if (unit.equals("Greek")) 
			    {
						wordUnit = "grammo";
			    }
			    else if (unit.equals("m")) 
			    {
						wordUnit = "metro";
			    }
			    else if (unit.equals("cm")) 
			    {
						wordUnit = "centimetro";
			    }
			    else if (unit.equals("mm")) 
			    {
						wordUnit = "millimetro";
			    }
			    else if (unit.equals("sqrm")) 
			    {
						wordUnit = "metro quadrato";
			    }
				}
			}//if
	    else 
	    {
				if (language.equalsIgnoreCase("English")) 
				{
		    	wordUnit = (String)englishDim.get(unit) + "s";
				}
				else if (language.equalsIgnoreCase("Greek")) 
				{
			    if (unit.equals("kgr")) 
			    {
						wordUnit = "����";
			    }
			    else if (unit.equals("Greek")) 
			    {
						wordUnit = "���������";
			    }
			    else if (unit.equals("m")) 
			    {
						wordUnit = "�����";
			    }
			    else if (unit.equals("cm")) 
			    {
						wordUnit = "��������";
			    }
			    else if (unit.equals("mm")) 
			    {
						wordUnit = "��������";
			    }
			    else if (unit.equals("sqrm")) 
			    {
						wordUnit = "����������� �����";
			    }
				}
				else if (language.equalsIgnoreCase("Italian")) 
				{
			    if (unit.equals("kgr")) 
			    {
						wordUnit = "chilogrammi";
			    }
			    else if (unit.equals("Greek")) 
			    {
						wordUnit = "grammi";
			    }
			    else if (unit.equals("m")) 
			    {
						wordUnit = "metri";
			    }
			    else if (unit.equals("cm")) 
			    {
						wordUnit = "centimetri";
			    }
			    else if (unit.equals("mm")) 
			    {
						wordUnit = "millimetri";
			    }
			    else if (unit.equals("sqrm")) 
			    {
						wordUnit = "metri quadrati";
			    }
				}
	    }
	    if (wordUnit == null) 
	    {
				wordUnit = "";
	    }
	    returnDimensions.put(language,  number + " " + wordUnit);
		}//while
		return returnDimensions;
	}//createExportDimensions


  /**
   * Append a proper noun entry to lexicon.italian
   * Used in : ExportUtilsExprimo.createInstancesGramAndMsgcat()
   * @param nameOrShortName "name" or "shortname"
   * @param entityName the name of the entity
   * @param name the name of the noun
   * @param nameGrammaticalGender the grammatical gender of noun
   * @param nameNumber the number of noun
   */

  private void appendProperNounToItalianLexicon(String nameOrShortName, String entityName, String name, String nameGrammaticalGender, String nameNumber) 
  {
		Document italianLexiconDoc = ((MpiroDocument)lexiconDocs.get("Italian")).getDocument();
	
    String appendedName = new String();
    if (nameOrShortName.equalsIgnoreCase("name")) 
    {
	    appendedName = "-noun";
		}
		else if (nameOrShortName.equalsIgnoreCase("shortname")) 
		{
			appendedName = "-shortnoun";
		}
		String grammaticalGender = new String("");
		if (!nameGrammaticalGender.equalsIgnoreCase("")) 
		{
			grammaticalGender = nameGrammaticalGender + "-noun";
		}
		String number = new String("");
		if (!nameNumber.equalsIgnoreCase("")) 
		{
			number = nameNumber + "-noun";
		}

		ArrayList nounFeatures = new ArrayList();
		nounFeatures.add("noun");
		nounFeatures.add(grammaticalGender);
		nounFeatures.add(number);

		Element lexiconElement = italianLexiconDoc.getDocumentElement();
		Element nounLexicalItemElement = startLexicalItem(italianLexiconDoc, entityName + appendedName, name);
		lexiconElement.appendChild(nounLexicalItemElement);
		    
		Element nounGrammFeatsElement = italianLexiconDoc.createElement(GRAMMATICAL_FEATURES);
		nounLexicalItemElement.appendChild(nounGrammFeatsElement);
		
		Element nounGrammFeatListElement = italianLexiconDoc.createElement(AND_FR);
		nounGrammFeatsElement.appendChild(nounGrammFeatListElement);
		
		
		Iterator featuresIterator = nounFeatures.iterator();
		while (featuresIterator.hasNext()) 
		{
	    String thisFeature = featuresIterator.next().toString();
	    if (!thisFeature.equals("")) 
	    {
				Element featureRefElement = italianLexiconDoc.createElement(FEATURE_REF);
				featureRefElement.setAttribute(NAME, thisFeature);
				nounGrammFeatListElement.appendChild(featureRefElement);
	    }
		}

		ArrayList nounConceptList = new ArrayList();
		nounConceptList.add(entityName);
		Element nounConceptListElement = concepts(italianLexiconDoc, nounConceptList, entityName);
		nounLexicalItemElement.appendChild(nounConceptListElement);
	}//appendProperNounToItalianLexicon
	
	
	/**
	 *  Append a proper noun entry to lexicon.greek
	 *  Used in : ExportUtilsExprimo.createInstancesGramAndMsgcat()
	 */
	/**
	 * Append a proper noun entry to lexicon.greek
	 * Used in : ExportUtilsExprimo.createInstancesGramAndMsgcat()
	 * @param nameOrShortName "name" or "shortname"
	 * @param entityName the name of the entity
	 * @param namenom the nominative name of the noun
	 * @param namegen the genitive name of the noun
	 * @param nameacc the accusative name of the noun
	 * @param nameGrammaticalGender the grammatical gender of noun
	 * @param nameNumber the number of noun
	 */
	private void appendProperNounToGreekLexicon(String nameOrShortName, String entityName, String namenom, String namegen, String nameacc, String nameGrammaticalGender, String nameNumber) 
	{
		Document greekLexiconDoc = ((MpiroDocument)lexiconDocs.get("Greek")).getDocument();
		
		String appendedName = new String();
		if (nameOrShortName.equalsIgnoreCase("name")) 
		{
			appendedName = "-noun";
		}
		else if (nameOrShortName.equalsIgnoreCase("shortname")) 
		{
			appendedName = "-shortnoun";
		}
		String grammaticalGender = new String("");
		if (!nameGrammaticalGender.equalsIgnoreCase("")) 
		{
			grammaticalGender = nameGrammaticalGender + "-noun";
		}
		String number = new String("");
		if (!nameNumber.equalsIgnoreCase("")) 
		{
			number = nameNumber + "-noun";
		}

		ArrayList nounFeatures = new ArrayList();
		nounFeatures.add("noun");
		nounFeatures.add(grammaticalGender);
		nounFeatures.add(number);
		
		Element lexiconElement = greekLexiconDoc.getDocumentElement();
		Element nounLexicalItemElement = startLexicalItem(greekLexiconDoc, entityName + appendedName, namenom);
		lexiconElement.appendChild(nounLexicalItemElement);
		    
		Element nounGrammFeatsElement = greekLexiconDoc.createElement(GRAMMATICAL_FEATURES);
		nounLexicalItemElement.appendChild(nounGrammFeatsElement);
		
		Element nounGrammFeatListElement = greekLexiconDoc.createElement(AND_FR);
		nounGrammFeatsElement.appendChild(nounGrammFeatListElement);
	
		Iterator featuresIterator = nounFeatures.iterator();
		while (featuresIterator.hasNext()) 
		{
	    String thisFeature = featuresIterator.next().toString();
	    if (!thisFeature.equals("")) 
	    {
				Element featureRefElement = greekLexiconDoc.createElement(FEATURE_REF);
				featureRefElement.setAttribute(NAME, thisFeature);
				nounGrammFeatListElement.appendChild(featureRefElement);
	    }
		}

		ArrayList nounConceptList = new ArrayList();
		nounConceptList.add(entityName);
		Element nounConceptListElement = concepts(greekLexiconDoc, nounConceptList, entityName);
		nounLexicalItemElement.appendChild(nounConceptListElement);
	
		// the spelling exceptions
	
		ArrayList exceptionSpellings = new ArrayList();
		ArrayList exceptionFeats = new ArrayList();
		if (!namegen.equalsIgnoreCase("")) 
		{
	    exceptionSpellings.add(namegen);
	    exceptionFeats.add("genitive-noun");
		}
		
		if (!nameacc.equalsIgnoreCase("")) 
		{
	    exceptionSpellings.add(nameacc);
	    exceptionFeats.add("accusative-noun");
		}
	
		if (!exceptionSpellings.isEmpty()) 
		{
	    Element spellingExceptionList = greekLexiconDoc.createElement(SPELLING_EXCEPTION_LIST);
	    nounLexicalItemElement.appendChild(spellingExceptionList);
	    for (int i = 0; i < exceptionSpellings.size(); i++) 
	    {
				Element verbSpellingExceptionElement = spellingException(greekLexiconDoc,
											 (String)exceptionSpellings.get(i),
											 (String)exceptionFeats.get(i));
				spellingExceptionList.appendChild(verbSpellingExceptionElement);
	    }
		}
	}//appendProperNounToGreekLexicon



  /**
   * Exports microplanning expressions to expressions.gram, italian-expression.gram, greek-expression.gram
   * Used in: ExportUtilsExprimo.createExpressionsGram()
   * @param microPlanningHashtable the microplanning hashtable
   * @param field the field name
   * @param num the microplan number
   * @param language the language
   */
  private static Element createMicroplanningExpressionExport(Hashtable microPlanningHashtable, String field, int num, String language, Document predicatesDoc) 
  {
		String number = new Integer(num).toString();
		Hashtable features = new Hashtable();

		// Verb, Voice, Tense, Aspect, Adverb, Mood, Reversible create
		// XML elements with the same name, but lower case.
		// Exceptions go in the hashtable
	
		features.put("Prep", "arg2-prep");
		features.put("Preadj", "adjunct1");
		features.put("Postadj", "adjunct2");
			
		Element expressionElement = predicatesDoc.createElement(EXPRESSION);
		
		Iterator microPlanningIter = microPlanningHashtable.keySet().iterator();
		while (microPlanningIter.hasNext()) 
		{
	    String keyString = microPlanningIter.next().toString();
	    String valueString = microPlanningHashtable.get(keyString).toString();
	    StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
	    String thisNumber = keyStringTokens.nextToken();
	    String thisField = keyStringTokens.nextToken();
	    String keyStringAttribute = keyStringTokens.nextToken();
	    String thisLanguage = keyStringTokens.nextToken();
	    if (!(thisNumber.equals(number) && thisField.equalsIgnoreCase(field) && thisLanguage.equalsIgnoreCase(language))) 
	    {
				continue;
	    }
	    
	    if (keyStringAttribute.equals("SELECTION")) 
	    {
				continue;
	    }
	    
	    if (valueString == null) 
	    {
				continue;
	    }
	    
	    if ((keyStringAttribute.equalsIgnoreCase("Aggreg")) && (valueString.equalsIgnoreCase("aggFalse")) ) 
	    {
				Element featureRefElement = predicatesDoc.createElement(FEATURE_REF);
				featureRefElement.setAttribute(NAME, AGGREGATION_NOT_ALLOWED);
				expressionElement.appendChild(featureRefElement);
	    }
	    else if (keyStringAttribute.equalsIgnoreCase("Verb")
							|| keyStringAttribute.equalsIgnoreCase("Voice")
							|| keyStringAttribute.equalsIgnoreCase("Tense")
							|| keyStringAttribute.equalsIgnoreCase("Aspect")
							|| (!valueString.equalsIgnoreCase("")
							&& (keyStringAttribute.equalsIgnoreCase("Prep")
							|| keyStringAttribute.equalsIgnoreCase("Preadj")
							|| keyStringAttribute.equalsIgnoreCase("Postadj")
							|| keyStringAttribute.equalsIgnoreCase("Adverb")))
							|| (keyStringAttribute.equalsIgnoreCase("Mood")
							&& !valueString.equalsIgnoreCase("Indicative"))) 
			{
				Object elName = features.get(keyStringAttribute);
				String elementName = "";
				if (elName != null) 
				{
					elementName = elName.toString();
				}
				
				else 
				{
					elementName = keyStringAttribute.toLowerCase();
				}
				Element featureElement = predicatesDoc.createElement(elementName);
				featureElement.setAttribute(IS, valueString.toLowerCase());
				expressionElement.appendChild(featureElement);
	    }

	    else if ( (keyStringAttribute.equalsIgnoreCase("Reversible")) && (!valueString.equalsIgnoreCase("revFalse")) ) 
	    {
				Element featureElement = predicatesDoc.createElement(keyStringAttribute.toLowerCase());
				featureElement.setAttribute(IS, "t");
				expressionElement.appendChild(featureElement);
	    }

	    else if (keyStringAttribute.equalsIgnoreCase("Referobj")) 
	    {
				String attr = "";
				System.out.println("valuestring---------"+valueString);
				if (valueString.equalsIgnoreCase("Name")) 
				{
					attr = "refer-by-name";
				}
				else if (valueString.equalsIgnoreCase("Pronoun")) 
				{
					attr = "refer-by-pronoun";
				}
				if (!attr.equals("")) 
				{
			    Element featureElement = predicatesDoc.createElement(ARG2FORM);
			    featureElement.setAttribute(IS, attr);
			    expressionElement.appendChild(featureElement);
				}
	    }

	    else if ( (!valueString.equalsIgnoreCase("")) && (!valueString.equalsIgnoreCase("Indicative")) && (!valueString.equalsIgnoreCase("revFalse")) && (!valueString.equalsIgnoreCase("Auto"))) 
	    {
				//System.out.println("No code for outputting info on " + keyStringAttribute + " : " + valueString);
	    }
		}//while
		return expressionElement;
	} // createMicroplanningExpressionExport



  /**
   * Exports template expressions to expressions.gram, italian-expression.gram, greek-expression.gram
   * Used in: ExportUtilsExprimo.createExpressionsGram()
   * @param templateVector the template vector
   * @param field the field name
   * @param num the microplan number
   * @param language the language
   * @param p the prinwriter
   */
  private static Element createTemplateExpressionExport(Vector templateVector, String field, int num, String language, Document predicatesDoc) 
  {
		Element expressionElement = predicatesDoc.createElement(EXPRESSION);
		Element templateElement = predicatesDoc.createElement(TEMPLATE);
		expressionElement.appendChild(templateElement);
	
		Hashtable languageTemplateHashtable = new Hashtable();
		if (language.equalsIgnoreCase("English")) 
		{
			languageTemplateHashtable = (Hashtable)templateVector.get(0);
		}
		else if (language.equalsIgnoreCase("Italian")) 
		{
			languageTemplateHashtable = (Hashtable)templateVector.get(1);
		}
		else 
		{ //if (language.equalsIgnoreCase("Greek"))
			languageTemplateHashtable = (Hashtable)templateVector.get(2);
		}
		if (!languageTemplateHashtable.containsKey(num + ":" + field)) 
		{
			return null;
		}
		boolean hasFullStop = false;
		String agg = AGGREGATION_NOT_ALLOWED;
		if (languageTemplateHashtable.containsKey(num + ":" + field + ":Aggreg")) 
		{
	    String aggregationValue = (String)languageTemplateHashtable.get(num + ":" + field + ":Aggreg");
	    if (aggregationValue.equalsIgnoreCase("True")) 
	    {
				agg = AGGREGATION_ALLOWED;
	    }
		}
	
		Element aggElement = predicatesDoc.createElement(FEATURE_REF);
		aggElement.setAttribute(NAME, agg);
		templateElement.appendChild(aggElement);
	
		Element templateBodyElement = predicatesDoc.createElement(TEMPLATE_BODY);
		templateElement.appendChild(templateBodyElement);
	
		Vector languageTemplateVector = (Vector)languageTemplateHashtable.get(num + ":" + field);
	
		if (languageTemplateVector.size() != 0) 
		{
	    Hashtable slot;
	    for (int i=0; i < languageTemplateVector.size(); i++) 
	    {
				slot = (Hashtable)languageTemplateVector.get(i);
				String selectionString = slot.get("SELECTION").toString();
        if (selectionString.equalsIgnoreCase("string")) 
        {
          if (slot.containsKey("string")) 
          {
            String slotString = slot.get("string").toString();
            if (!slotString.equalsIgnoreCase("")) 
            {
				      if (!(slotString.equals("."))) 
				      {
							  Element spaceElement = predicatesDoc.createElement(SPACE);
							  templateBodyElement.appendChild(spaceElement);
			      	}
				      if (slotString.equals(".") || slotString.endsWith(".")) 
				      {
					  		hasFullStop = true;
				      }
				      Element stringElement = predicatesDoc.createElement(STRING);
				      templateBodyElement.appendChild(stringElement);
				      Text textChild =  predicatesDoc.createTextNode(slotString);
				      stringElement.appendChild(textChild);
				      if (!slotString.equals(".")) 
				      {
							  Element spaceElement = predicatesDoc.createElement(SPACE);
							  templateBodyElement.appendChild(spaceElement);
				      }
						}
					}
				}
				else if (selectionString.equalsIgnoreCase("referring")) 
				{
		      String slotSemantics = new String();
		      String slotType = new String();
		      String slotCase = new String();
          if (slot.containsKey("semantics")) 
          {
          	slotSemantics = slot.get("semantics").toString();
          }
          else 
          {
          	slotSemantics = "Field owner";
          }
          if (slot.containsKey("type")) 
          {
            String value = slot.get("type").toString();
            if (value.equalsIgnoreCase("Auto")) {slotType = "referring-np";}
            else if (value.equalsIgnoreCase("Name")) {slotType = "refer-by-name";}
            else if (value.equalsIgnoreCase("Pronoun")) {slotType = "refer-by-pronoun";}
            else if (value.equalsIgnoreCase("Type with definite article")) {slotType = "refer-by-type";}
            else if (value.equalsIgnoreCase("Type with indefinite article")) {slotType = "classifying-np";}
          }
          else 
          {
          	slotType = "referring-np";
          }

          if (slot.containsKey("grCase")) 
          {
            String value = slot.get("grCase").toString();
            if (value.equalsIgnoreCase("Nominative")) {slotCase = "nominative-np";}
            else if (value.equalsIgnoreCase("Genitive")) {slotCase = "genitive-np";}
            else if (value.equalsIgnoreCase("Accusative")) {slotCase = "accusative-np";}
          }
          else 
          {
          	slotCase = "nominative-np";
          }

					// handling an exception
					if (slotSemantics.equalsIgnoreCase("Field filler") && (slotType.equalsIgnoreCase("refer-by-name"))) 
					{
						Element spaceElement = predicatesDoc.createElement(SPACE);
						templateBodyElement.appendChild(spaceElement);
						
						Element fieldElement = predicatesDoc.createElement(FIELD);
						fieldElement.setAttribute(SOURCE, ARG2);
						templateBodyElement.appendChild(fieldElement);
					}

					else 
					{
						Element unitSpecElement = predicatesDoc.createElement(UNIT_SPECIFICATION);
						templateBodyElement.appendChild(unitSpecElement);
						
						Element featureRefElement1 = predicatesDoc.createElement(FEATURE_REF);
						featureRefElement1.setAttribute(NAME, slotType);
						unitSpecElement.appendChild(featureRefElement1);
						
						Element featureRefElement2 = predicatesDoc.createElement(FEATURE_REF);
						featureRefElement2.setAttribute(NAME, slotCase);
						unitSpecElement.appendChild(featureRefElement2);
						
						Element roleElement = predicatesDoc.createElement(ROLE);
						roleElement.setAttribute(NAME, SEM);
						unitSpecElement.appendChild(roleElement);
						
						String val = "";
						Element valueElement = predicatesDoc.createElement(VALUE);
					  if (slotSemantics.equalsIgnoreCase("Field owner")) 
					  {
							val = ARG1;
					  }
					  else if (slotSemantics.equalsIgnoreCase("Field filler")) 
					  {
							val = ARG2;
					  }
						valueElement.setAttribute(NAME, val);
						roleElement.appendChild(valueElement);
					}
				}
			} // for
	    if (! (hasFullStop)) 
	    {
				Element stringElement = predicatesDoc.createElement(STRING);
				templateBodyElement.appendChild(stringElement);
				Text textChild =  predicatesDoc.createTextNode(".");
				stringElement.appendChild(textChild);
	    }
		} // if
		return expressionElement;
	} // createTemplateExpressionExport

    /*
    private static String replaceIllegalCharactersForXml(String originalString) 
    {
			int lastAmpIndex = -1;
			while (originalString.indexOf('&', lastAmpIndex + 1) > -1) {
	    int ampIndex = originalString.indexOf('&', lastAmpIndex + 1);
	    StringBuffer newString = new StringBuffer(originalString);
	    newString.replace(ampIndex, ampIndex + 1, "&amp;");
	    originalString = newString.toString();
	    lastAmpIndex = ampIndex + 2;
		}
			while (originalString.indexOf('<') > -1) 
			{
	    StringBuffer newString = new StringBuffer(originalString);
	    newString.replace(originalString.indexOf('<'), originalString.indexOf('<') + 1, "&lt;");
	    originalString = newString.toString();
		}
		while (originalString.indexOf('"') > -1) 
		{
	    StringBuffer newString = new StringBuffer(originalString);
	    newString.replace(originalString.indexOf('"'), originalString.indexOf('"') + 1, "&quot;");
	    originalString = newString.toString();
		}
		return originalString;
    }
    */

	static String inUpperModel(String entity, boolean replace) 
	{
		String printEntity = "";
	
		if (replace) 
		{
			printEntity = "a-" + entity;
		}
		else 
		{
			printEntity = "true";
		}
	
		if (entity.equalsIgnoreCase("city")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("country")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("region")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("island")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("marble")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("silver")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("gold")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("be-aux")) 
		{
			return printEntity;
		}
		else if (entity.equalsIgnoreCase("make-verb")) 
		{
			return printEntity;
		}
		return entity;
	}//inUpperModel



  private void processTaxonomies(String entityType, Element typesElement) 
  {

       if (entityType.substring(0,entityType.length()-1).endsWith("_occur"))
                                    return;
		String printEntityType = inUpperModel(entityType, true);
	
		Vector entityTypeChildrenVector = Mpiro.win.struc.getChildrenVectorFromMainDBHashtable(entityType, "Entity type", noRemoveAllEntityTypesHashtable);
		if (!entityTypeChildrenVector.isEmpty()) 
		{

	    Element taxonomyElement = typesDoc.createElement(TAXONOMY);
	    typesElement.appendChild(taxonomyElement);

	    Element typeElement = typesDoc.createElement(TYPE);
	    typeElement.setAttribute(IS, printEntityType);
	    taxonomyElement.appendChild(typeElement);

	    Element subTypesElement = typesDoc.createElement(SUBTYPES);
	    taxonomyElement.appendChild(subTypesElement);

	    Enumeration entityTypeChildrenVectorEnum = entityTypeChildrenVector.elements();

	    while (entityTypeChildrenVectorEnum.hasMoreElements()) 
	    {
				String entityTypeChildrenVectorEnumElement = (String)entityTypeChildrenVectorEnum.nextElement();
                                if (entityTypeChildrenVectorEnumElement.substring(0,entityTypeChildrenVectorEnumElement.length()-1).endsWith("_occur"))
                                    entityTypeChildrenVectorEnumElement=entityTypeChildrenVectorEnumElement.substring(0,entityTypeChildrenVectorEnumElement.length()-7);
				String printEntityTypeChildrenVectorEnumElement = inUpperModel(entityTypeChildrenVectorEnumElement, true);
		
				Element fillerItemElement = typesDoc.createElement(FILLER_ITEM);
				fillerItemElement.setAttribute(IS, printEntityTypeChildrenVectorEnumElement);
				subTypesElement.appendChild(fillerItemElement);
	    }

	    entityTypeChildrenVectorEnum = entityTypeChildrenVector.elements();
	    while (entityTypeChildrenVectorEnum.hasMoreElements()) 
	    {
				String entityTypeChildrenVectorEnumElement = (String)entityTypeChildrenVectorEnum.nextElement();
				processTaxonomies(entityTypeChildrenVectorEnumElement, typesElement);
	    }
		}
	}//processTaxonomies



  public void showWarnings() 
  {
		if (Mpiro.debugMode) {warnings = true;}
		if (warnings) 
		{
	    ExportWarningsWindow warningsWindow = new ExportWarningsWindow();
	    warningsWindow.addText(warningsText);
		}
	}//showWarnings

 
	private static void sendWarning(String s) 
	{
		warningsText  = warningsText + "\n" + s;
		warnings = true;
	}


	private static String addFullStopAndRemoveTrailingSpaces(String field, String filler) 
	{
		while (filler.endsWith(" ")) 
		{
			filler = filler.substring(0, filler.length() -1);
		}
		while (filler.startsWith(" ")) 
		{
			filler = filler.substring(1, filler.length());
		}
		if (!(field.equalsIgnoreCase("name") || field.equalsIgnoreCase("shortname"))) 
		{
	    if (!(filler.endsWith(".") || filler.endsWith("!") || filler.endsWith("?"))) 
	    {
				filler = filler.concat(".");
	    }
		}
		return filler;
	}


  private static Element spellingException(Document doc, String spelling, Object feat) 
  {
		Element spellEx = doc.createElement(SPELLING_EXCEPTION);
	
		if ((feat instanceof ArrayList) && ((ArrayList)feat).size() == 1) 
		{
			feat = ((ArrayList)feat).get(0).toString();
		}

		if (feat instanceof String) 
		{
	    Element grammFeat =  doc.createElement(GRAMMATICAL_FEATURES);
	    spellEx.appendChild(grammFeat);
	    
	    Element ref = doc.createElement(FEATURE_REF);
	    ref.setAttribute(NAME, feat.toString());
	    grammFeat.appendChild(ref);
		}

		else if (feat instanceof ArrayList) 
		{
			Element grammFeatList = doc.createElement(GRAMMATICAL_FEATURES);
			spellEx.appendChild(grammFeatList);
			
			Element andFR = doc.createElement(AND_FR);
			grammFeatList.appendChild(andFR);
			
			Iterator featIter = ((ArrayList)feat).iterator();
			while (featIter.hasNext()) 
			{
				String thisFeat = featIter.next().toString();
				if (!thisFeat.equals("")) 
				{
				  Element ref = doc.createElement(FEATURE_REF);
				  ref.setAttribute(NAME, thisFeat.toString());
				  andFR.appendChild(ref);
				}
			}
		}

		Element spellingEl = doc.createElement(SPELLING);
		spellingEl.setAttribute(IS, spelling);
		spellEx.appendChild(spellingEl);
	
		return spellEx;
	}//spellingException


  private static Element concepts(Document doc, ArrayList concepts, String word) //creates <concepts>
  {
		if (concepts.isEmpty()) 
		{
	    if (word.equals("NIL")) 
	    {
				concepts.add("NIL");
	    }
	    else 
	    {
				System.out.println("Warning: English: found empty concept for " + word);
                                if (word.lastIndexOf('-')!= -1)
				word = word.substring(0, word.lastIndexOf('-'));
				concepts.add(word);
				System.out.println("setting concept to " + word);
	    }
		}

		Element conceptList = doc.createElement(CONCEPT_LIST);
	
		// for the moment, just add the first concept, I need to fix
		// the code in exprimo to deal properly with mulitple
		// concepts, then reactivate the commented code below
	
		// begin code to be replaced later
	
		Element conceptEl = doc.createElement(CONCEPT);
		String concept = concepts.get(0).toString();
		String printConcept = inUpperModel(concept, true);
	
		conceptEl.setAttribute(NAME, printConcept.toLowerCase());
		conceptList.appendChild(conceptEl);

		// end code to be replaced later
	
		/* begin replacment code
	
		Iterator conceptIter = concepts.iterator();
		while (conceptIter.hasNext()) {
		    Element conceptEl = doc.createElement(CONCEPT);
		    String concept = conceptIter.next().toString();
		    String printConcept = inUpperModel(concept, true);
	
		    conceptEl.setAttribute(NAME, printConcept.toLowerCase());
		    conceptList.appendChild(conceptEl);
		}
	
		end replacement code */

		return conceptList;
	}//concepts
				       
				       
	private static Element startLexicalItem(Document doc, String id, String spelling) 
	{
		Element lexicalItemEl = doc.createElement(LEXICAL_ITEM);
		lexicalItemEl.setAttribute(ID, id);
		
		Element spellingEl = doc.createElement(SPELLING);
		spellingEl.setAttribute(IS, spelling);
		lexicalItemEl.appendChild(spellingEl);
	
		return lexicalItemEl;
	}//startLexicalItem


  private static void makeBasicTypeRoleElements(Hashtable objectElements, String field, String filler) 
  {
		Iterator objectElIter = objectElements.keySet().iterator();
		while (objectElIter.hasNext()) 
		{
	    String language = objectElIter.next().toString();
	    Element objectElement = (Element)objectElements.get(language);

	    Document instancesDoc = objectElement.getOwnerDocument();
	    Element instancesElement = instancesDoc.getDocumentElement();

	    Element roleElement = instancesDoc.createElement(DEF_ROLE);
	    String fillerToExport = new String();
	    if ((filler.equalsIgnoreCase("String"))) 
	    {
				fillerToExport = filler;
	    }
	    
	    // Dimension and Date are not handled properly in Exprimo, so we output them as type String for now
	    else if ((filler.equalsIgnoreCase("Dimension")) ||
		     (filler.equalsIgnoreCase("Date")) ||
		     (filler.equalsIgnoreCase("Number"))) 
			{
				fillerToExport = "STRING";
	    }
	    else 
	    {
				fillerToExport = "ENTITY-ID";
	    }
	    String printField = inUpperModel(field, true);
	    roleElement.setAttribute(SLOT, printField);
	    roleElement.setAttribute(TYPE, fillerToExport.toLowerCase());
	    objectElement.appendChild(roleElement);
		}		
	}//makeBasicTypeRoleElements
	

	private static void makeRoleElements(Hashtable objectElements, String slot, String filler) 
	{
		Iterator objectElementsIter = objectElements.keySet().iterator();
		while (objectElementsIter.hasNext()) 
		{
	    String language = objectElementsIter.next().toString();
	    Element objectElement = (Element)objectElements.get(language);
	    makeRoleElement(objectElement, slot, filler);
		}
	}
    
    
  private static void makeRoleElements(Hashtable objectElements, String slot, Hashtable fillers) 
  {
		Iterator objectElementsIter = objectElements.keySet().iterator();
		while (objectElementsIter.hasNext()) 
		{
	    String language = objectElementsIter.next().toString();
	    String filler = fillers.get(language).toString();
	    Element objectElement = (Element)objectElements.get(language);
	    makeRoleElement(objectElement, slot, filler);
		}
  }
    
  private static void makeRoleElement(Element objectElement, String slot, String filler) 
  {
		Document instancesDoc = objectElement.getOwnerDocument();
		Element roleElement  = instancesDoc.createElement(ROLE);
		roleElement.setAttribute(SLOT, slot);
		roleElement.setAttribute(FILLER, filler);
		objectElement.appendChild(roleElement);
  }
    
  private static Hashtable makeObjectElements(Hashtable instancesDocuments, String is) 
  {
		Hashtable objectElements = new Hashtable();
		Iterator instancesDocIter = instancesDocuments.keySet().iterator();
		while (instancesDocIter.hasNext()) 
		{
	    String language = instancesDocIter.next().toString();
	    MpiroDocument mpiroInstancesDoc = (MpiroDocument)instancesDocuments.get(language);
	    Document instancesDoc = mpiroInstancesDoc.getDocument();
	    Element instancesElement = instancesDoc.getDocumentElement();
	    
	    Element objectElement = instancesDoc.createElement(OBJECT);
	    objectElement.setAttribute(IS, is);
	    instancesElement.appendChild(objectElement);
	    
	    objectElements.put(language, objectElement);
		}
		return objectElements;
  }

	public void saveToZip(ArrayList languageList) 
	{
		mpiroZip.save();
		Prepositions.savePrepositionsToZip(languageList);
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

