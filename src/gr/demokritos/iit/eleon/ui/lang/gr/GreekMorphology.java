//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

//  Author: Aggeliki Dimitromanolaki (adimit@iit.demokritos.gr)
//  Greek morphological generator for Exprimo generation system



package gr.demokritos.iit.eleon.ui.lang.gr;

import gr.demokritos.iit.eleon.authoring.QueryLexiconHashtable;
import java.util.*;


/**
* Class for the morphological realization of Greek words.
*/
public class GreekMorphology 
{
	static String baseform;
	static Vector features;
	static String spelling;
	static int length;
	
	static String getSpellingStringNoun(String noun, String baseform)
	{
		String spellingString;
		try
		{
			Hashtable currentNounValues = QueryLexiconHashtable.showValues(noun, "Greek");
			String grgenderValue = currentNounValues.get("grgender").toString();
			if (grgenderValue.equalsIgnoreCase("Neuter"))
			{
			  spellingString = GreekAccentUtils.removeAccentFromWord(baseform);
			}
			else
			{
			  spellingString = GreekAccentUtils.removeAccentFromWord(GreekAccentUtils.findNounStem(baseform));
			}
		}
		catch (Exception ex)
		{
		  System.out.println("(Greek Morphology)---  " + "Cannot process \"" + baseform + "\"");
		  spellingString = "";
		}
		return spellingString;

	}


	static String getSpellingStringVerb(String baseform)
	{
	  String spellingString;
	  try
	  {
              System.out.println(GreekAccentUtils.findVerbStem(baseform));
             spellingString=GreekAccentUtils.findVerbStem(baseform);
             for(int i=0;i<spellingString.length();i++)
             {
                 char r=spellingString.charAt(i);
                 if (GreekAccentUtils.isAccentedVowel(r)){
                     spellingString=GreekAccentUtils.removeAccentFromWord(spellingString);
                     break;
                 }
             }
         //     System.out.println(GreekAccentUtils.removeAccentFromWord(GreekAccentUtils.findVerbStem(baseform)));
	//		spellingString = GreekAccentUtils.removeAccentFromWord(GreekAccentUtils.findVerbStem(baseform));
	  }
	  catch (Exception ex)
	  {
	    System.out.println("(Greek Morphology)---  " + "Cannot process \"" + baseform + "\"");
	    spellingString = "";
	  }
	  return spellingString;
	}


	/**
	* Returns a vector containing all features for a spelling field of a noun
	* @param noun The noun id
	* @param language The language ("English", "Italian", or "Greek")
	* @param extraSpecificFeatures The extra features needed for each spelling field
	* @return The vector containing all features
	*/
	static Vector getFeaturesVectorNoun(String noun, String language, String extraSpecificFeatures)
	{
		Vector featuresVector = new Vector();
		featuresVector.add("noun");
		featuresVector.add("common-noun");
		
		Hashtable currentNounValues = QueryLexiconHashtable.showValues(noun, language);
		
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
		featuresVector.add(countable);

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
		featuresVector.add(grgender);

		String spelling = new String();
		String accentFeature = new String();
		String declensionFeature = new String();
		
		String grinflection = new String();
		String grinflectionValue = currentNounValues.get("grinflection").toString();
		if (grinflectionValue.equalsIgnoreCase("Inflected"))
		{
			grinflection = "inflected-noun";
			featuresVector.add(grinflection);
			
			// the spelling from "grbasetext"
			if (grbasetext.equalsIgnoreCase(""))
			{
				spelling = grbasetext;
			}
			else
			{
	      try
	      {
					spelling = GreekAccentUtils.removeAccentFromWord(GreekAccentUtils.findNounStem(grbasetext));
					accentFeature = GreekAccentUtils.findAccentFeature(grbasetext);
					declensionFeature = GreekAccentUtils.findDeclensionFeature(grbasetext, grgenderValue);
					featuresVector.add(accentFeature);
					featuresVector.add(declensionFeature);
				}
				catch (Exception ex)
				{
					System.out.println("(Typing error in)---  " + noun);
					spelling = "";
					accentFeature = "";
					declensionFeature = "";
				}
				String twoEndingNounType = new String("");
				String isosyllableType = new String("");
				if (declensionFeature.equalsIgnoreCase("two-endings-noun"))
				{
				  twoEndingNounType = GreekAccentUtils.findTwoEndingNounType(grbasetext, grpluraltext);
				  featuresVector.add(twoEndingNounType);
				}
				if (twoEndingNounType.equalsIgnoreCase("isosyllava"))
				{
				  isosyllableType = GreekAccentUtils.findIsosyllableType(grpluraltext);
				  featuresVector.add(isosyllableType);
				}
			}
		}
		else if (grinflectionValue.equalsIgnoreCase("Not inflected"))
		{
		  grinflection = "lexicalised-noun";
		  featuresVector.add(grinflection);
		}
    featuresVector.add(extraSpecificFeatures.substring(0, extraSpecificFeatures.indexOf(" ")));
    featuresVector.add(extraSpecificFeatures.substring(extraSpecificFeatures.indexOf(" ")+1, extraSpecificFeatures.length()));

    return featuresVector;
	}


	/**
	* Returns a vector containing all features for a spelling field of a verb
	* @param verb The verb id
	* @param language The language ("English", "Italian", or "Greek")
	* @param extraSpecificFeatures The extra features needed for each spelling field
	* @return The vector containing all features
	*/
	static Vector getFeaturesVectorVerb(String verb, String language, Vector extraSpecificFeatures)
	{
	  Vector featuresVector = new Vector();

		Hashtable currentVerbValues = QueryLexiconHashtable.showValues(verb, "Greek");
		// the spelling from "vbasetext"
		String vbasetext = currentVerbValues.get("vbasetext").toString();
		String vbasetext2 = currentVerbValues.get("vbasetext2").toString();
		String spelling = new String();
		
		
		// the grammatical features by checkiong for auxiliary verbs & from "transitive"
		String verbType = new String();
		if ( (vbasetext.equalsIgnoreCase("είμαι")) || (vbasetext.equalsIgnoreCase("έχω")) )
		{
		  verbType = "auxverb";
		}
		else
		{
		  verbType = "lexverb";
		}
		featuresVector.add(verbType);
		
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
		featuresVector.add(transitive);

		String conjugationFeature = GreekAccentUtils.findConjugationFeature(vbasetext);
		featuresVector.add(conjugationFeature);
		String classFeature = new String("");
		if (conjugationFeature.equalsIgnoreCase("second-conjugation"))
		{
		  classFeature = GreekAccentUtils.findClassFeature(vbasetext2);
		  featuresVector.add(classFeature);
		}
		
		Enumeration extraSpecificFeaturesEnum = extraSpecificFeatures.elements();
		while (extraSpecificFeaturesEnum.hasMoreElements())
		{
		  featuresVector.add(extraSpecificFeaturesEnum.nextElement().toString());
		}
		return featuresVector;
	}


	/**
	* Gives the inflected form of a lexical unit.
	* @param baseformString The base form of the lexical unit
	* @param featuresVector The vector of features for the lexical unit
	* @param spellingString The unaccented stem derived from the base form
	* @return The inflected form of lexical unit.
	*/
	static String getInflectedForm(String baseformString, Vector featuresVector, String spellingString) 
	{
		baseform = baseformString;
		features = featuresVector;
		spelling = spellingString;
		length = spelling.length();

		//System.out.println("()---  " + "==" + LexiconPanel.parent.toString() + "==============");
		//System.out.println("()---  " + features.toString());
		//System.out.println("()---  " + spelling);
		//System.out.println("()---  " + length);
		//System.out.println("()---  " + "==========================================");

		if ( isTrue("inflected-noun") || isTrue("inflected-determiner") || isTrue("inflected-pronoun") )
			return getInflectedGreekNominalForm();

		else if ( isTrue("lexverb") ||
		  				isTrue("do-aux") ||
		  				isTrue("have-aux"))
			return getInflectedGreekVerbForm();

		else if ( isTrue("inflected-adjective"))
	    return getInflectedGreekAdjectiveForm();

		else if ( isTrue("be-aux"))
			return getInflectedGreekBeForm();

		else
	    return baseform;
	};


	static boolean isTrue(String s) 
	{
		if (features.isEmpty() == false)
			return features.contains(s);
		return false;
	};
	
	
	static boolean hasSuffix(String s) 
	{
		return spelling.endsWith(s);
	};
	
	static boolean isSubstring(String superS, String sub) 
	{
		return superS.indexOf(sub) > -1;
	};



	/**
	* Tests if the lexical item is a verb that requires prefix.
	* @param lexitem The lexical item.
	* @return true if the lexical item is a verb that requires prefix, and false otherwise.
	
	    public boolean requiresPrefix(String lexitem) 
	    {
	      //if (cutLastConsonant(cutLastVowel(spelling)) == "")
	      if (isTrue("lexverb") && cutLastConsonant(cutLastVowel(spelling)) == "")
	         return true;
	      return false;
	    }
	*/


	/** Removes all trailing Greek consonants of a word.
	*   @param s A string, containing the characters of the word in reverse order. For
	*            example, the word "λέβης" is given as "ςηβέλ".
	*   @return A string which is the same as arg, except that all trailing Greek consonants are
	*           removed from the beginning of the string (i.e. end of word).
	**/
	static String cutLastConsonant(String s) 
	{
		if( s.length() >= 3 &&
				isConsonant(s.charAt(0)) == true &&
				isConsonant(s.charAt(1)) == true &&
				isConsonant(s.charAt(2)) == true
		 	) 
		{
			return s.substring(3);
		}
      
    if( s.length() >= 2 &&
				isConsonant(s.charAt(0)) == true &&
				isConsonant(s.charAt(1)) == true
     	) 
		{
    	return s.substring(2);
    }
      
    if( s.length() >= 1 &&
				isConsonant(s.charAt(0)) == true
     	) 
		{
    	return s.substring(1);
    }
    return s;
	}


	/** Removes all trailing Greek consonants of a word, and then the last simple or composite Greek vowel
	*   from the end of the word.
	*   @param s A string, containing the characters of the word in reverse order. For
	*            example, the word "λεξη" is given as "ηξελ".
	*   @return A string which is the same as arg, except that (i) first all trailing Greek consonants are
	*           removed from the beginning of the string (i.e. end of word), and
	*           (ii) then the first simple or composite Greek vowel is removed from the beginning of the string
	*           (i.e. end of word).
	**/
	static String cutLastVowel(String s) 
	{
	  if(isCompositeVowel(s.charAt(1), s.charAt(0)) == true) 
	  {
	  	return s.substring(2);
	  }
	  
	  if(isVowel(s.charAt(0)) == true) 
	  {
	  	return s.substring(1);
	  }
	  
	  if( isConsonant(s.charAt(0)) == true &&
				isCompositeVowel(s.charAt(2), s.charAt(1)) == true
	   	) 
		{
	  	return s.substring(3);
	  }
      
    if( isConsonant(s.charAt(0)) == true &&
				isVowel(s.charAt(1)) == true
			) 
		{
    	return s.substring(2);
    }
    
    if( isConsonant(s.charAt(0)) == true &&
				isConsonant(s.charAt(1)) == true &&
				isVowel(s.charAt(2)) == true
			) 
		{
    	return s.substring(3);
    }
    return s.substring(4);
	}


	/** Checks if a character is a Greek vowel.
	*   @param c The character to check.
	*   @return Returns true if c is a Greek vowel; false otherwise.
	**/
	static boolean isVowel(char c) 
	{
	  if(c == 'α' || c == 'ε' || c == 'ο' || c == 'η' || c == 'ι' || c == 'υ' || c == 'ω' ||
	     c == 'ϊ' || c == 'ϋ' || c == 'ΐ' || c == 'ΰ' ||
	     c == 'ά' || c == 'έ' || c == 'ό' || c == 'ή' || c == 'ί' || c == 'ύ' || c == 'ώ') 
		{
	  	return true;
	  }
	  return false;
	}


	/** Checks if a character is an accented Greek vowel.
	*   @param c The character to check.
	*   @return Returns true if c is an accented Greek vowel; false otherwise.
	**/
	static boolean isAccentedVowel(char c) 
	{
		if(c == 'ΐ' || c == 'ΰ' ||
	     c == 'ά' || c == 'έ' || c == 'ό' || c == 'ή' || c == 'ί' || c == 'ύ' || c == 'ώ') 
		{
	  	return true;
	  }
	  return false;
	}


	/** Checks if a character is a Greek consonant.
	*   @param c The character to check.
	*   @return Returns true if c is a Greek consonant; false otherwise.
	**/
	static boolean isConsonant(char c) 
	{
	  if(c == 'β' || c == 'γ' || c == 'δ' || c == 'ζ' || c == 'θ' || c == 'κ' || c == 'λ' ||
	     c == 'μ' || c == 'ν' || c == 'ξ' || c == 'π' || c == 'ς' ||
	     c == 'ρ' || c == 'σ' || c == 'τ' || c == 'φ' || c == 'χ' || c == 'ψ') 
		{
			return true;
	  }
	  return false;
	}


	/** Checks if two successive letters constitute a composite Greek vowel or not.
	*   @param c1 The first letter
	*   @param c2 The second letter.
	*   @return Returns true if the letters consitute a composite Greek vowel; false otherwise.
	**/
	static boolean isCompositeVowel(char c1, char c2) 
	{
	  if(
	      (c2 == 'ι' && (c1 == 'α' || c1 == 'ε' || c1 == 'ο' || c1 == 'υ')) ||
	      (c2 == 'υ' && (c1 == 'α' || c1 == 'ε' || c1 == 'ο'))
	     ) 
		{
	  	return true;
	  }
	  return false;
	}



	/**
	* Finds the inflected form of nouns, determiners and pronouns (adjectives will be added shortly).
	* @return The inflected form of nouns, determiners and pronouns as a string.
	*/
	static String getInflectedGreekNominalForm() 
	{
		if	(
					(isTrue("singular-noun") &&
						(
         			(isTrue("feminine-noun") && isTrue("two-endings-noun") && isTrue("genitive-noun") ) ||
         			(isTrue("feminine-noun") && isTrue("three-endings-noun") && isTrue("nominative-noun") ) ||
         			(isTrue("masculine-noun") && isTrue("nominative-noun") )
         		)
         	) ||
         	(isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") && isTrue("masculine-noun") && isTrue("nominative-noun") )||
         	(isTrue("singular-determiner") && isTrue("inflected-determiner") && isTrue("masculine-determiner") && isTrue("nominative-determiner") )
				)

	    //return pluralNoun();
	    return greekNounSSuffix();

		else if	(
							(isTrue("singular-noun") &&
              	(
              		(isTrue("feminine-noun") && isTrue("two-endings-noun") && (isTrue("nominative-noun") || isTrue("accusative-noun")) ) ||
              		(isTrue("neuter-noun") && (isTrue("nominative-noun") || isTrue("accusative-noun")) ) ||
              		(isTrue("three-endings-noun") && isTrue("accusative-noun") ) ||
              		(isTrue("masculine-noun") && isTrue("two-endings-noun") && (isTrue("genitive-noun") || isTrue("accusative-noun")) )
              	)
              ) ||
              (	isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") &&
								(isTrue("feminine-noun") || isTrue("neuter-noun")) && 
								(isTrue("nominative-noun") || isTrue("accusative-noun")) 
							)||
              (	isTrue("singular-determiner") && isTrue("inflected-determiner") &&
								(((isTrue("feminine-determiner") || isTrue("neuter-determiner")) && (isTrue("nominative-determiner") || isTrue("accusative-determiner"))) ||
									(isTrue("masculine-determiner") && isTrue("accusative-determiner")) ) 
							)
						)

			//return pluralNoun();
			return greekNounEmptySuffix();

		else if (isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") &&
		         isTrue("masculine-noun") && isTrue("accusative-noun"))
		
			return greekNounNSuffix();


		// genitive singular 1. Pronouns (strong forms will be handled below)
		else if (
		        	(isTrue("singular-noun") && isTrue("inflected-noun") && (isTrue("three-endings-noun") || isTrue("neuter1")) && isTrue("genitive-noun")) ||
		        	(isTrue("singular-determiner") && isTrue("inflected-determiner") && isTrue("not-feminine-determiner") && isTrue("genitive-determiner"))
		        )
		
			return greekNounGenSingASuffix();
		
		
		// nominative plural 1
		else if (
		         	(isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("three-endings-noun") && isTrue("nominative-noun")) ||
		         	(isTrue("plural-determiner") && isTrue("inflected-determiner") && isTrue("masculine-determiner") && isTrue("nominative-determiner")) ||
		         	(isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("masculine-noun") && isTrue("nominative-noun"))
		        )
		
			return greekNounNomPlASuffix();



		// genitive plural 1. Pronouns (strong forms will be handled below)
    else if (
            	(isTrue("plural-noun") && isTrue("inflected-noun") && (isTrue("three-endings-noun") || isTrue("not-ancient-like") || isTrue("neuter1") || isTrue("neuter2")) && isTrue("genitive-noun")) ||
            	(isTrue("plural-determiner") && isTrue("inflected-determiner") && isTrue("genitive-determiner"))
            )

			return greekNounGenPlurASuffix();



		// accusative plural 1
    else if (
             	(isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("three-endings-noun") && isTrue("accusative-noun")) ||
             	(isTrue("plural-determiner") && isTrue("inflected-determiner") && isTrue("masculine-determiner") && isTrue("accusative-determiner")) ||
             	(isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("masculine-noun") && isTrue("accusative-noun"))
            )

			return greekNounAccPlASuffix();



		// nominative plural 2
		else if (
             	(isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("not-ancient-like") && (isTrue("nominative-noun") || isTrue("accusative-noun"))) ||
             	(isTrue("plural-determiner") && isTrue("inflected-determiner") && isTrue("feminine-determiner") && (isTrue("nominative-determiner") || isTrue("accusative-determiner"))) ||
             	(isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("feminine-noun") && (isTrue("nominative-noun") || isTrue("accusative-noun")))
            )

			return greekNounNomPlBSuffix();



		// nominative plural 3
    else if (isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("ancient-like") && (isTrue("nominative-noun") || isTrue("accusative-noun")))

			return greekNounNomPlCSuffix();



		// genitive plural 2
    else if (isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("ancient-like") && isTrue("genitive-noun"))

			return greekNounGenPlurBSuffix();



		// nominative plural neuter 1
    else if (
             	(isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("neuter1") && (isTrue("nominative-noun") || isTrue("accusative-noun"))) ||
             	(isTrue("plural-determiner") && isTrue("inflected-determiner") && isTrue("neuter-determiner") && (isTrue("nominative-determiner") || isTrue("accusative-determiner"))) ||
             	(isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("neuter-noun") && (isTrue("nominative-noun") || isTrue("accusative-noun")))
            )

			return greekNounNomPlNeutASuffix();



		// nominative plural neuter 2
    else if (isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("neuter2") && (isTrue("nominative-noun") || isTrue("accusative-noun")))

			return greekNounNomPlNeutBSuffix();




		// genitive singular 2
    else if(isTrue("singular-noun") && isTrue("inflected-noun") && isTrue("neuter2") && isTrue("genitive-noun"))

			return greekNounGenSingBSuffix();



		// nominative plural 4
    else if(isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("anisosyllava") && (isTrue("masculine-noun") || isTrue("feminine-noun")) && (isTrue("nominative-noun") || isTrue("accusative-noun")))

			return greekNounNomPlDSuffix();



		// genitive plural 3
    else if (isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("anisosyllava") && (isTrue("masculine-noun") || isTrue("feminine-noun")) && isTrue("genitive-noun"))

			return greekNounGenPlurCSuffix();



		// genitive singular 3
    else if (isTrue("singular-noun") && isTrue("inflected-noun") && isTrue("neuter-noun") && isTrue("anisosyllava") && isTrue("genitive-noun"))

			return greekNounGenSingCSuffix();



		// nominative plural 5
    else if (isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("anisosyllava") && isTrue("neuter-noun") && (isTrue("nominative-noun") || isTrue("accusative-noun")))

			return greekNounNomPlESuffix();



		// genitive plural 4
    else if (isTrue("plural-noun") && isTrue("inflected-noun") && isTrue("anisosyllava") && isTrue("neuter-noun") && isTrue("genitive-noun"))

			return greekNounGenPlurDSuffix();



		// weak forms of personal pronouns
    else if (isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") && (isTrue("masculine-noun") || isTrue("neuter-noun")) && isTrue("genitive-noun") && isTrue("weak-form"))

			return greekNounWeakASuffix();



		else if (isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") && isTrue("feminine-noun") && isTrue("genitive-noun") && isTrue("weak-form"))

			return greekNounWeakBSuffix();



    else if (isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") && isTrue("masculine-noun") && isTrue("accusative-noun") && isTrue("weak-form"))

			return greekNounWeakCSuffix();



    else if (isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") && isTrue("feminine-noun") && isTrue("accusative-noun") && isTrue("weak-form"))

			return greekNounWeakDSuffix();



    else if (isTrue("pronoun") && isTrue("singular-noun") && isTrue("inflected-pronoun") && isTrue("neuter-noun") && isTrue("accusative-noun") && isTrue("weak-form"))

			return greekNounWeakESuffix();



    else if (isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("genitive-noun") && isTrue("weak-form"))

			return greekNounWeakFSuffix();



    else if (isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("masculine-noun") && isTrue("accusative-noun") && isTrue("weak-form"))

			return greekNounWeakGSuffix();



    else if (isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("feminine-noun") && isTrue("accusative-noun") && isTrue("weak-form"))

			return greekNounWeakHSuffix();



    else if (isTrue("pronoun") && isTrue("plural-noun") && isTrue("inflected-pronoun") && isTrue("neuter-noun") && isTrue("accusative-noun") && isTrue("weak-form"))

			return greekNounWeakISuffix();


		else return spelling;
	};



	static String greekNounSSuffix() 
	{
	  if (isTrue("oxutono"))
	  	return spelling + "ς@1";
	  else if (isTrue("paroxutono"))
	  	return spelling + "ς@2";
	  else if (isTrue("proparoxutono"))
	  	return spelling + "ς@3";
	  else return spelling + "ς";
	}


	static String greekNounEmptySuffix() 
	{
	  if (isTrue("oxutono"))
	  	return spelling + "@1";
	  else if (isTrue("paroxutono"))
	  	return spelling + "@2";
	  else if (isTrue("proparoxutono"))
	  	return spelling + "@3";
	  else return spelling;
	}
	
	
	static String greekNounNSuffix() 
	{
	  if (isTrue("oxutono"))
	  	return spelling + "ν@1";
	  else if (isTrue("paroxutono"))
	  	return spelling + "ν@2";
	  else if (isTrue("proparoxutono"))
	  	return spelling + "ν@3";
	  else return spelling + "ν";
	}


	static String greekNounGenSingASuffix() 
	{
	  if (hasSuffix("ι") || hasSuffix("υ"))
	  	return spelling + "ου@1";
	  else if (isTrue("oxutono"))
	  	return spelling + "υ@1";
	  else if (isTrue("paroxutono") || isTrue("proparoxutono"))
	  	return spelling + "υ@2";
	  else return spelling + "υ";
	}
	
	
	static String greekNounNomPlASuffix() 
	{
	  if (isTrue("oxutono"))
	  	return spelling + "ι@1";
	  else if (isTrue("paroxutono"))
	  	return spelling + "ι@2";
	  else if (isTrue("proparoxutono"))
	  	return spelling + "ι@3";
	  else return spelling + "ι";
	}


	static String greekNounGenPlurASuffix() 
	{
	  if (hasSuffix("ι") || hasSuffix("υ"))
	  	return spelling + "ων@1";
	  else if (hasSuffix("ος"))
	  	return spelling.substring(0, length-2) + "ων@1";
	  else if (isTrue("oxutono") || (isTrue("two-endings-noun") && isTrue("isosyllava") && isTrue("not-ancient-like")))
	  	return spelling.substring(0, length-1) + "ων@1";
	  else if (isTrue("paroxutono") || isTrue("proparoxutono"))
	  	return spelling.substring(0, length-1) + "ων@2";
	  else return spelling + "ων";
	}
	
	
	static String greekNounAccPlASuffix() 
	{
	  if (isTrue("oxutono"))
	  	return spelling + "υς@1";
	  else if (isTrue("paroxutono") || isTrue("proparoxutono"))
	  	return spelling + "υς@2";
	  else return spelling + "υς";
	}


	static String greekNounNomPlBSuffix() 
	{
	  if (isTrue("oxutono"))
	  	return spelling.substring(0, length-1) + "ες@1";
	  else if (isTrue("paroxutono"))
	  	return spelling.substring(0, length-1) + "ες@2";
	  else if (isTrue("proparoxutono"))
	  	return spelling.substring(0, length-1) + "ες@3";
	  else return spelling + "ες";
	}
	
	
	static String greekNounNomPlCSuffix() 
	{
	  if (hasSuffix("α"))
	  	return spelling.substring(0, length-2) + "εις@1";
	  else return spelling.substring(0, length-1) + "εις@2";
	}


	static String greekNounGenPlurBSuffix() 
	{
	  if (hasSuffix("α"))
	  	return spelling.substring(0, length-2) + "εων@2";
	  else return spelling.substring(0, length-1) + "εων@3";
	}
	
	
	static String greekNounNomPlNeutASuffix() 
	{
	  if (isTrue("oxutono") && hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "α@1";
	  else if (isTrue("paroxutono") && hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "α@2";
	  else if (isTrue("proparoxutono") && hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "α@3";
	  if (isTrue("oxutono") && hasSuffix("ι"))
	  	return spelling + "α@1";
	  else if ((isTrue("paroxutono") || isTrue("proparoxutono")) && hasSuffix("ι"))
	  	return spelling + "α@3";
	  else return spelling + "α";
	}


	static String greekNounGenSingBSuffix() 
	{
	  return spelling.substring(0, length-2) + "ους@2";
	}
	
	
	static String greekNounNomPlNeutBSuffix()  
	{
	  return spelling.substring(0, length-2) + "η@2";
	}


	static String greekNounNomPlDSuffix() 
	{
	  if (isTrue("oxutono"))
	  	return spelling + "δες@2";
	  else if (isTrue("paroxutono") || isTrue("proparoxutono"))
	  	return spelling + "δες@3";
	  else return spelling + "δες";
	}
	
	
	static String greekNounGenPlurCSuffix() 
	{
	  if (isTrue("oxutono"))
			return spelling + "δων@2";
	  else if (isTrue("paroxutono") || isTrue("proparoxutono"))
	  	return spelling + "δων@3";
	  else return spelling + "δων";
	}


	static String greekNounGenSingCSuffix() 
	{
	  if (hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "ατος@3";
	  else if (hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "τος@3";
	  else return spelling + "τος@3";
	}
	
	
	static String greekNounNomPlESuffix() 
	{
	  if (hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "ατα@3";
	  else if (hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "τα@3";
	  else return spelling + "τα@3";
	}


	static String greekNounGenPlurDSuffix() 
	{
	  if (hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "ατων@2";
	  else if (hasSuffix("ο"))
	  	return spelling.substring(0, length-1) + "των@2";
	  else return spelling + "των@2";
	}


	static String greekNounWeakASuffix() 
	{
		return "του";
	}
	
	
	static String greekNounWeakBSuffix() 
	{
		return "της";
	}
	
	
	static String greekNounWeakCSuffix() 
	{
		return "τον";
	}
	
	
	static String greekNounWeakDSuffix() 
	{
		return "την";
	}
	
	
	static String greekNounWeakESuffix() 
	{
		return "το";
	}


	static String greekNounWeakFSuffix() 
	{
		return "τους";
	}
	
	
	static String greekNounWeakGSuffix() 
	{
		return "τους";
	}
	
	
	static String greekNounWeakHSuffix() 
	{
		return "τις";
	}
	
	
	static String greekNounWeakISuffix() 
	{
		return "τα";
	}



	/**
	* Finds the inflected form of verbs.
	* @return The inflected form of verbs.
	*/
	static String getInflectedGreekVerbForm() 
	{
		// ACTIVE VOICE
		
		//Present tense
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("firstperson"))
			return greekVerbVSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("secondperson"))
			return greekVerbEisSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("thirdperson"))
			return greekVerbEiSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("firstperson"))
			return greekVerbOymeSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("secondperson"))
			return greekVerbEteSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("thirdperson"))
			return greekVerbOynSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("firstperson"))
			return greekVerbVASuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("secondperson"))
			return greekVerbAsSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("thirdperson"))
			return greekVerbASuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("firstperson"))
			return greekVerbAmeASuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("secondperson"))
			return greekVerbAteASuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("present-verb") && isTrue("thirdperson"))
			return greekVerbOynASuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("secondperson"))
			return greekVerbEisASuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("thirdperson"))
			return greekVerbEiASuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("firstperson"))
			return greekVerbOymeASuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("active-verb") && isTrue("present-verb") && isTrue("secondperson"))
			return greekVerbEiteSuffix();

		// Past progressive (prefixes are now handled)
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("firstperson"))
			return greekVerbAPSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("secondperson"))
			return greekVerbEsSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("thirdperson"))
			return greekVerbESuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("firstperson"))
			return greekVerbAmeSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("secondperson"))
			return greekVerbAteSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("thirdperson"))
			return greekVerbAnSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("firstperson"))
			return greekVerbOysaSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("secondperson"))
			return greekVerbOysesSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("thirdperson"))
			return greekVerbOyseuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("firstperson"))
			return greekVerbOysAmeSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("secondperson"))
			return greekVerbOysAteSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("thirdperson"))
			return greekVerbOysanASuffix();


		// Past simple (prefixes are now handled)
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("firstperson"))
			return greekVerbSaSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("secondperson"))
			return greekVerbSesSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("thirdperson"))
			return greekVerbSeSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("firstperson"))
			return greekVerbSameSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("secondperson"))
			return greekVerbSateSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("thirdperson"))
			return greekVerbSanSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("firstperson"))
			return greekVerbHsaSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("secondperson"))
			return greekVerbHsesSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("thirdperson"))
			return greekVerbHseuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("firstperson"))
			return greekVerbHsameSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("secondperson"))
			return greekVerbHsateSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("active-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("thirdperson"))
			return greekVerbHsanSuffix();

		// Infinitive
		if (isTrue("infinitive") && isTrue("first-conjugation") && isTrue("active-verb"))
			return greekVerbSeiSuffix();
		
		if (isTrue("infinitive") && isTrue("second-conjugation") && isTrue("active-verb"))
			return greekVerbHseiSuffix();
		
		// Participle
		if (isTrue("participle-verb") && isTrue("first-conjugation") && isTrue("active-verb"))
			return greekVerbOntasSuffix();
		
		if (isTrue("participle-verb") && isTrue("second-conjugation") && isTrue("active-verb"))
			return greekVerbVntasSuffix();


		// PASSIVE VOICE
		//Present tense
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("firstperson"))
			return greekVerbOmaiSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return greekVerbEsaiSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return greekVerbEtaiSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("firstperson"))
		  return greekVerbOmasteSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return greekVerbEsteSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return greekVerbOntaiSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("firstperson"))
		  return greekVerbIemaiSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return greekVerbIesaiSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return greekVerbIetaiSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("firstperson"))
		  return greekVerbIomasteSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return greekVerbIesteteASuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return greekVerbIoyntaiASuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("firstperson"))
		  return greekVerbOymaiSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return greekVerbEisaiSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return greekVerbEitaiSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("firstperson"))
		  return greekVerbOymasteASuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return greekVerbEisteSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return greekVerbOyntaiASuffix();

		// Past progressive (prefixes are now handled)
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("firstperson"))
		  return greekVerbOmounSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("secondperson"))
		  return greekVerbOsoynSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("thirdperson"))
		  return greekVerbOtanSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("firstperson"))
		  return greekVerbOmastanSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("secondperson"))
		  return greekVerbOsastanSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("thirdperson"))
		  return greekVerbOntanSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("firstperson"))
		  return greekVerbIomoynSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return greekVerbIosoynSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("thirdperson"))
		  return greekVerbIotanSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("firstperson"))
		  return greekVerbIomastanSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return greekVerbIosastanSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("first-class") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("thirdperson"))
		  return greekVerbIoyntanSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("firstperson"))
		  return greekVerbOymoynSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return greekVerbOysoynSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("thirdperson"))
		  return greekVerbOyntanSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("firstperson"))
		  return greekVerbOymastanSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("progressive-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return greekVerbOysastanSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("second-class") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("progressive-verb") && isTrue("thirdperson"))
		  return greekVerbOyntanSuffix();


		// Past simple (prefixes are now handled)
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("firstperson"))
		  return greekVerbUhkaSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("secondperson"))
		  return greekVerbUhkesSuffix();
		
		if (isTrue("singular-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("thirdperson"))
		  return greekVerbUhkeSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("firstperson"))
		  return greekVerbUhkameSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("secondperson"))
		  return greekVerbUhkateSuffix();
		
		if (isTrue("plural-verb") && isTrue("first-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("thirdperson"))
		  return greekVerbUhkanSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("firstperson"))
		  return greekVerbHuhkaSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("passive-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return greekVerbHuhkesSuffix();
		
		if (isTrue("singular-verb") && isTrue("second-conjugation") && isTrue("passive-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("thirdperson"))
		  return greekVerbHuhkeSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("passive-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("firstperson"))
		  return greekVerbHuhkameSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("passive-verb") && isTrue("simple-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return greekVerbHuhkateSuffix();
		
		if (isTrue("plural-verb") && isTrue("second-conjugation") && isTrue("passive-verb") && isTrue("past-verb") && isTrue("simple-verb") && isTrue("thirdperson"))
		  return greekVerbHuhkanSuffix();

		// Infinitive
		if (isTrue("infinitive") && isTrue("first-conjugation") && isTrue("passive-verb"))
		  return greekVerbUeiSuffix();
		
		if (isTrue("infinitive") && isTrue("second-conjugation") && isTrue("passive-verb"))
		  return greekVerbHueiSuffix();

		// Participle
		if (isTrue("participle-verb") && isTrue("masculine-verb") && isTrue("first-conjugation") && isTrue("passive-verb"))
		  return greekVerbMenosSuffix();
		
		if (isTrue("participle-verb") && isTrue("feminine-verb") && isTrue("first-conjugation") && isTrue("passive-verb"))
		  return greekVerbMenhSuffix();
		
		if (isTrue("participle-verb") && isTrue("neuter-verb") && isTrue("first-conjugation") && isTrue("passive-verb"))
		  return greekVerbMenoSuffix();
		
		if (isTrue("participle-verb") && isTrue("masculine-verb") && isTrue("second-conjugation") && isTrue("passive-verb"))
		  return greekVerbHmenosSuffix();
		
		if (isTrue("participle-verb") && isTrue("feminine-verb") && isTrue("second-conjugation") && isTrue("passive-verb"))
		  return greekVerbHmenhSuffix();
		
		if (isTrue("participle-verb") && isTrue("neuter-verb") && isTrue("second-conjugation") && isTrue("passive-verb"))
		  return greekVerbHmenoSuffix();

		return spelling;

	};


	//METHODS

  static String greekVerbVSuffix() 
  {
    return spelling + "ω@2";
  }

  static String greekVerbEisSuffix() 
  {
    return spelling + "εις@2";
  }

  static String greekVerbEiSuffix() 
  {
    return spelling + "ει@2";
  }

  static String greekVerbOymeSuffix() 
  {
    return spelling + "ουμε@3";
  }

  static String greekVerbEteSuffix() 
  {
    return spelling + "ετε@3";
  }

  static String greekVerbOynSuffix() 
  {
    return spelling + "ουν@2";
  }

  static String greekVerbVASuffix() 
  {
    return spelling + "ώ";
  }

  static String greekVerbAsSuffix() 
  {
    return spelling + "άς";
  }

  static String greekVerbASuffix() 
  {
    return spelling + "ά";
  }

  static String greekVerbAmeASuffix() 
  {
    return spelling + "άμε";
  }

  static String greekVerbAteASuffix() 
  {
    return spelling + "άτε";
  }

  static String greekVerbOynASuffix() 
  {
    return spelling + "ούν";
  }

  static String greekVerbEisASuffix() 
  {
    return spelling + "είς";
  }

  static String greekVerbEiASuffix() 
  {
    return spelling + "εί";
  }

  static String greekVerbOymeASuffix() 
  {
    return spelling + "ούμε";
  }

  static String greekVerbEiteSuffix() 
  {
    return spelling + "είτε";
  }


	// Past progressive (prefixes are now handled)
	static String greekVerbAPSuffix() 
	{
	  if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "α@3";
	  return spelling + "α@3";
	}
	
	static String greekVerbEsSuffix() 
	{
	  if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "ες@3";
	  return spelling + "ες@3";
	}
	
	static String greekVerbESuffix() 
	{
	  if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "ε@3";
	  return spelling + "ε@3";
	}
	
	static String greekVerbAmeSuffix() 
	{
	  return spelling + "αμε@3";
	}

  static String greekVerbAteSuffix() 
  {
    return spelling + "ατε@3";
  }

  static String greekVerbAnSuffix() 
  {
    if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
      return "ε" + spelling + "αν@3";
    return spelling + "αν@3";
  }

  static String greekVerbOysaSuffix() 
  {
    return spelling + "ούσα";
  }

  static String greekVerbOysesSuffix() 
  {
    return spelling + "ούσες";
  }

  static String greekVerbOyseuffix() 
  {
    return spelling + "ούσε";
  }

  static String greekVerbOysAmeSuffix() 
  {
    return spelling + "ούσαμε";
  }

  static String greekVerbOysAteSuffix() 
  {
    return spelling + "ούσατε";
  }

  static String greekVerbOysanASuffix() 
  {
    return spelling + "ούσαν";
  }


	// Past simple (prefixes are now handled)
	static String greekVerbSaSuffix() 
	{
		if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ξα@3";
	
	  else if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ξα@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ξα@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ξα@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ψα@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ψα@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ψα@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ψα@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "σα@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "σα@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "α@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling + "α@3";
	
	  else if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "σα@3";
	
	  else return spelling + "σα@3";
	}


	static String greekVerbSesSuffix() 
	{
	  if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ξες@3";
	
	  else if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ξες@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ξες@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ξες@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ψες@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ψες@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ψες@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ψες@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "σες@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "σες@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "ες@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling + "ες@3";
	
	  else if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "σες@3";
	
	  else return spelling + "σες@3";
	}


	static String greekVerbSeSuffix()
	{
	  if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ξε@3";
	
	  else if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ξε@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ξε@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ξε@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ψε@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ψε@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ψε@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ψε@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "σε@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "σε@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "ε@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling + "ε@3";
	
	  else if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "σε@3";
	
	  else return spelling + "σε@3";
	}


  static String greekVerbSameSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "ξαμε@3";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "ξαμε@3";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "ψαμε@3";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "σαμε@3";
    else if (hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling + "αμε@3";
    else return spelling + "σαμε@3";
  }


  static String greekVerbSateSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "ξατε@3";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "ξατε@3";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "ψατε@3";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "σατε@3";
    else if (hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling + "ατε@3";
    else return spelling + "σατε@3";
  }


  static String greekVerbSanSuffix() 
  {
	  if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ξαν@3";
	
	  else if ((hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ξαν@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ξαν@3";
	
	  else if ((hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ξαν@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "ψαν@3";
	
	  else if ((hasSuffix("π") || hasSuffix("β") || hasSuffix("φ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "ψαν@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-2) + "ψαν@3";
	
	  else if ((hasSuffix("πτ") || hasSuffix("φτ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-2) + "ψαν@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling.substring(0, length-1) + "σαν@3";
	
	  else if ((hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling.substring(0, length-1) + "σαν@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "αν@3";
	
	  else if ((hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	       && cutLastConsonant(cutLastVowel(spelling)).length() != 0)
	  	return spelling + "αν@3";
	
	  else if (cutLastConsonant(cutLastVowel(spelling)).length() == 0)
	    return "ε" + spelling + "σαν@3";
	
	  else return spelling + "σαν@3";
	}

  static String greekVerbHsaSuffix() 
  {
    return spelling + "ησα@3";
  }

  static String greekVerbHsesSuffix() 
  {
    return spelling + "ησες@3";
  }

  static String greekVerbHseuffix() 
  {
    return spelling + "ησε@3";
  }

  static String greekVerbHsameSuffix() 
  {
    return spelling + "ησαμε@3";
  }

  static String greekVerbHsateSuffix() 
  {
    return spelling + "ησατε@3";
  }

  static String greekVerbHsanSuffix() 
  {
    return spelling + "ησαν@3";
  }

	// Infinitive
	static String greekVerbSeiSuffix() 
	{
	  if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	  	return spelling.substring(0, length-1) + "ξει@2";
	  else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	  	return spelling.substring(0, length-2) + "ξει@2";
	  else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
	  	return spelling.substring(0, length-1) + "ψει@2";
	  else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("ων") || hasSuffix("θ"))
	  	return spelling.substring(0, length-1) + "σει@2";
	  else if (hasSuffix("λ") || hasSuffix("ρ") || hasSuffix("μ") || hasSuffix("ν"))
	  	return spelling + "ει@2";
	  else return spelling + "σει@2";
	}

	static String greekVerbHseiSuffix() 
	{
	  return spelling + "ησει@2";
	}
	
	
	// Participle
	static String greekVerbOntasSuffix() 
	{
	  return spelling + "οντας@3";
	}
	
	static String greekVerbVntasSuffix() 
	{
	  return spelling + "ωντας@2";
	}


	// PASSIVE VOICE
	//Present tense
	
	static String greekVerbOmaiSuffix() 
	{
	  return spelling + "ομαι@3";
	}
	
	static String greekVerbEsaiSuffix() 
	{
	  return spelling + "εσαι@3";
	}
	
	static String greekVerbEtaiSuffix() 
	{
	  return spelling + "εται@3";
	}
	
	static String greekVerbOmasteSuffix() 
	{
	  return spelling + "ομαστε@3";
	}
	
	static String greekVerbEsteSuffix() 
	{
	  return spelling + "εστε@3";
	}

  static String greekVerbOntaiSuffix() 
  {
    return spelling + "ονται@3";
  }

  static String greekVerbIemaiSuffix() 
  {
    return spelling + "ιέμαι";
  }

  static String greekVerbIesaiSuffix() 
  {
    return spelling + "ιέσαι";
  }

  static String greekVerbIetaiSuffix() 
  {
    return spelling + "ιεται";
  }

  static String greekVerbIomasteSuffix() 
  {
    return spelling + "ιόμαστε";
  }

  static String greekVerbIesteteASuffix() 
  {
    return spelling + "ιέστε";
  }

  static String greekVerbIoyntaiASuffix() 
  {
    return spelling + "ιούνται";
  }

  static String greekVerbOymaiSuffix() 
  {
    return spelling + "ούμαι";
  }

  static String greekVerbEisaiSuffix() 
  {
    return spelling + "είσαι";
  }

  static String greekVerbEitaiSuffix() 
  {
    return spelling + "είται";
  }

  static String greekVerbOymasteASuffix() 
  {
    return spelling + "ούμαστε";
  }

  static String greekVerbEisteSuffix() 
  {
    return spelling + "είστε";
  }

  static String greekVerbOyntaiASuffix() 
  {
    return spelling + "ούνται";
  }

	// Past progressive (prefixes are now handled)
	
	static String greekVerbOmounSuffix() 
	{
	  return spelling + "όμουν";
	}
	
	static String greekVerbOsoynSuffix() 
	{
	  return spelling + "όσουν";
	}
	
	static String greekVerbOtanSuffix() 
	{
	  return spelling + "όταν";
	}
	
	static String greekVerbOmastanSuffix() 
	{
	  return spelling + "όμασταν";
	}

  static String greekVerbOsastanSuffix() 
  {
    return spelling + "όσασταν";
  }

  static String greekVerbOntanSuffix() 
  {
    return spelling + "ονταν@3";
  }

  static String greekVerbIomoynSuffix() 
  {
    return spelling + "ιόμουν";
  }

  static String greekVerbIosoynSuffix() 
  {
    return spelling + "ιόσουν";
  }

  static String greekVerbIotanSuffix() 
  {
    return spelling + "ιόταν";
  }

  static String greekVerbIomastanSuffix() 
  {
    return spelling + "ιόμασταν";
  }

  static String greekVerbIosastanSuffix() 
  {
    return spelling + "ιόσασταν";
  }

  static String greekVerbIoyntanSuffix() 
  {
    return spelling + "ιούνταν";
  }

  static String greekVerbOymoynSuffix() 
  {
    return spelling + "ούμουν";
  }

  static String greekVerbOysoynSuffix() 
  {
    return spelling + "ούσουν";
  }

  static String greekVerbOyntanSuffix() 
  {
    return spelling + "ούνταν";
  }

  static String greekVerbOymastanSuffix() 
  {
    return spelling + "ούμασταν";
  }

  static String greekVerbOysastanSuffix() 
  {
    return spelling + "ούσασταν";
  }


	// Past simple (prefixes are now handled)
	
	static String greekVerbUhkaSuffix() 
	{
	  if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
	  	return spelling.substring(0, length-1) + "χτηκα@3";
	  else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
	  	return spelling.substring(0, length-2) + "χτηκα@3";
	  else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
	  	return spelling.substring(0, length-1) + "φτηκα@3";
	  else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
	  	return spelling.substring(0, length-1) + "στηκα@3";
	  else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
	  	return spelling.substring(0, length-1) + "θηκα@3";
	  else return spelling + "θηκα@3";
	}

  static String greekVerbUhkesSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "χτηκες@3";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "χτηκες@3";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "φτηκες@3";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "στηκες@3";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "θηκες@3";
    else return spelling + "θηκες@3";
  }

  static String greekVerbUhkeSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "χτηκε@3";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "χτηκε@3";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "φτηκε@3";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "στηκε@3";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "θηκε@3";
    else return spelling + "θηκε@3";
  }

  static String greekVerbUhkameSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "χτηκαμε@3";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "χτηκαμε@3";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "φτηκαμε@3";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "στηκαμε@3";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "θηκαμε@3";
    else return spelling + "θηκαμε@3";
  }

  static String greekVerbUhkateSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "χτηκατε@3";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "χτηκατε@3";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "φτηκατε@3";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "στηκατε@3";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "θηκατε@3";
    else return spelling + "θηκατε@3";
  }

  static String greekVerbUhkanSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "χτηκαν@3";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "χτηκαν@3";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "φτηκαν@3";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "στηκαν@3";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "θηκαν@3";
    else return spelling + "θηκαν@3";
  }

  static String greekVerbHuhkaSuffix() 
  {
    return spelling + "ήθηκα";
  }

  static String greekVerbHuhkesSuffix() 
  {
    return spelling + "ήθηκες";
  }

  static String greekVerbHuhkeSuffix() 
  {
    return spelling + "ήθηκε";
  }

  static String greekVerbHuhkameSuffix() 
  {
    return spelling + "ήθηκαμε";
  }

  static String greekVerbHuhkateSuffix() 
  {
    return spelling + "ήθηκατε";
  }

  static String greekVerbHuhkanSuffix() 
  {
    return spelling + "ήθηκαν";
  }

	// Infinitive
  static String greekVerbUeiSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "χτεί";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "χτεί";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "φτεί";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "στεί";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "θεί";
    else return spelling + "θεί";
  }

  static String greekVerbHueiSuffix() 
  {
    return spelling + "ηθεί";
  }

	// Participle
  static String greekVerbMenosSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "γμένος";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "γμένος";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "μμένος";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "σμένος";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "μένος";
    else return spelling + "μένος";
  }

  static String greekVerbMenhSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "γμένη";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "γμένη";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "μμένη";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "σμένη";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "μένη";
    else return spelling + "μένη";
  }

  static String greekVerbMenoSuffix() 
  {
    if (hasSuffix("κ") || hasSuffix("γ") || hasSuffix("χ") || hasSuffix("αζ") )
    	return spelling.substring(0, length-1) + "γμένο";
    else if (hasSuffix("χν") || hasSuffix("σσ") || hasSuffix("ττ"))
    	return spelling.substring(0, length-2) + "γμένο";
    else if (hasSuffix("π") || hasSuffix("πτ") || hasSuffix("β") || hasSuffix("φ") || hasSuffix("φτ"))
    	return spelling.substring(0, length-1) + "μμένο";
    else if (hasSuffix("ιζ") || hasSuffix("ην") || hasSuffix("θ"))
    	return spelling.substring(0, length-1) + "σμένο";
    else if (hasSuffix("ων") || hasSuffix("μ") || hasSuffix("ν"))
    	return spelling.substring(0, length-1) + "μένο";
    else return spelling + "μένο";
  }

  static String greekVerbHmenosSuffix() 
  {
    return spelling + "ημένος";
  }

  static String greekVerbHmenhSuffix() 
  {
    return spelling + "ημένη";
  }

  static String greekVerbHmenoSuffix() 
  {
    return spelling + "ημένο";
  }


	static String getInflectedGreekAdjectiveForm() 
	{
		return spelling;
	}


	static String getInflectedGreekBeForm() 
	{
		if (isTrue("singular-verb") && isTrue("present-verb") && isTrue("firstperson"))
		  return "είμαι";
		else if (isTrue("singular-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return "είσαι";
		else if (isTrue("singular-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return "είναι";
		else if (isTrue("plural-verb") && isTrue("present-verb") && isTrue("firstperson"))
		  return "είμαστε";
		else if (isTrue("plural-verb") && isTrue("present-verb") && isTrue("secondperson"))
		  return "είστε";
		else if (isTrue("plural-verb") && isTrue("present-verb") && isTrue("thirdperson"))
		  return "είναι";
		else if (isTrue("singular-verb") && isTrue("past-verb") && isTrue("firstperson"))
		  return "ήμουν";
		else if (isTrue("singular-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return "ήσουν";
		else if (isTrue("singular-verb") && isTrue("past-verb") && isTrue("thirdperson"))
		  return "ήταν";
		else if (isTrue("plural-verb") && isTrue("past-verb") && isTrue("firstperson"))
		  return "ήμαστε";
		else if (isTrue("plural-verb") && isTrue("past-verb") && isTrue("secondperson"))
		  return "ήσαστε";
		else if (isTrue("plural-verb") && isTrue("past-verb") && isTrue("thirdperson"))
		  return "ήταν";

		return null;
	};

}; //GreekMorphology