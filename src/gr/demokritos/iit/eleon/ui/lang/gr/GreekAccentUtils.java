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


package gr.demokritos.iit.eleon.ui.lang.gr;

/** File: GreekAccentUtils.java
*   Purpose: This program performs several operations on Greek words and is used in conjunction with
*            the lexicon and the morphological component. The operations it performs are the following:
*         1) It adds accents to the appropriate syllables of Greek words. Greek words are generated
*            in the form word@n, where word is the word without accent, and n is 1, 2, or 3, depending on
*            whether the accent is to be placed on the last, second last, or third last syllable counting
*            from the end of the word towards the left. This program contains methods to strip the "@n"
*            ending and to add an accent to the appropriate character of the word. If the word does not end
*            in "@n", it is returned unaltered.
*            Main method: addAccentToWord(string s). For example, addAccentToWord ("αγγειου@2") returns "αγγείου".
*         2) It removes the accent from the accented words. The words when entered to the lexicon, through the
*            authoring tool, are accented. The accent has to be removed before the words are processed by the
*            morphological component, since the accent positions changes depending on the inflection of the
*            word.
*            Main method: removeAccentFromWord(string s). For example, removeAccentFromWord("αγγείο")
*                         returns "αγγειο".
*         3) It finds the accent feature (oxutono, paroxutono or proparoxutono) of a Greek word. The
*            morphological
*            component needs this feature in order to make the appropriate changes regarding
*            the accent position.
*            Main method: findAccentFeature(string s). For example, findAccentFeature("αγγείο") returns
*            "paroxutono".
*         4) It finds the conjugation feature of a Greek verb, i.e. whether the verb belongs to the first or
*            the second conjugation.
*            Main method: findConjugationFeature(string s). For example findConjugationFeature("δημιουργώ")
*            returns "second-conjugation".
*         5) It finds the class feature of a second conjugation Greek verb, i.e. whether the verb belongs to
*            the first or the second class.
*            Main method: findClassFeature(string s). For example findClassFeature("δημιουργώ")
*            returns "second-class".
*         6) It finds the declension feature of a Greek noun, i.e. whether the noun has the feature
*            two-endings-noun or three-endings-noun.
*            Main method: findDeclensionFeature(string s). For example findDeclensionFeature("κούρος")
*            returns "three-endings-noun".
*         7) It finds the type feature of a two-endings Greek noun, i.e. whether the noun is
*            isosyllavo or anisosyllavo.
*            Main method: findTwoEndingNounType(string s). For example findTwoEndingNounType("άγαλμα")
*            returns "anisosyllava".
*         8) It finds the type feature of an isosyllavo Greek noun, i.e. whether the noun is
*            ancient-like, not-ancient-like, neuter1 or neuter2.
*            Main method: findIsosyllableType(string s). For example findTwoEndingNounType("αμφορέας")
*            returns "ancient-like".
*         9) It finds the stem of the noun removing the final "ς" from the end of
*            masculine or feminine nouns. Only for masculine or feminine nouns.
*            Main method: findNounStem(string s). For example findNounStem("αμφορέας")
*            returns "αμφορέα".
*         10)It finds the stem of the verb removing the ending ("ω", "ώ", "ομαι", "ιέμαι", "ώμαι",
*            "άμαι", "ούμαι") from the end of
*            the first person singular.
*            Main method: findVerbStem(string s). For example findVerbStem("δημιουργώ")
*            returns "δημιουργ".
*   Date last modified: June 14, 2001.
*   @author Aggeliki Dimitromanolaki (adimit@iit.demokritos.gr) and
*           Ion Androutsopoulos (ionandr@iit.demokritos.gr)
**/

public class GreekAccentUtils 
{
	/** Checks if a character is a Greek vowel.
	*   @param c The character to check.
	*   @return Returns true if c is a Greek vowel; false otherwise.
	**/
	public static boolean isVowel(char c) 
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
	public static boolean isAccentedVowel(char c) 
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
	public static boolean isConsonant(char c) 
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
	public static boolean isCompositeVowel(char c1, char c2) 
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


	/** Removes all trailing Greek consonants of a word, and then the last simple or composite Greek vowel
	*   from the end of the word.
	*   @param s A string, containing the characters of the word in reverse order. For
	*            example, the word "λεξη" is given as "ηξελ".
	*   @return A string which is the same as arg, except that (i) first all trailing Greek consonants are
	*           removed from the beginning of the string (i.e. end of word), and
	*           (ii) then the first simple or composite Greek vowel is removed from the beginning of the string
	*           (i.e. end of word).
	**/
	public static String cutLastVowel(String s) 
	{
		if (isCompositeVowel(s.charAt(1), s.charAt(0)) == true)
	  {
	  	return s.substring(2);
	  }
	  
    if (isVowel(s.charAt(0)) == true)
    {
    	return s.substring(1);
    }
    
    if (
         isConsonant(s.charAt(0)) == true &&
         isCompositeVowel(s.charAt(2), s.charAt(1)) == true
       )
    {
    	return s.substring(3);
		}
		
    if(
         isConsonant(s.charAt(0)) == true &&
         isVowel(s.charAt(1)) == true
      )
    {
    	return s.substring(2);
    }
    
    if(
         isConsonant(s.charAt(0)) == true &&
         isConsonant(s.charAt(1)) == true &&
         isVowel(s.charAt(2)) == true
      )
    {
    	return s.substring(3);
    }
		return s.substring(4);
	}


	/** Adds an accent to a single Greek character.
	*   @param c A single character
	*   @return If the character is a Greek vowel, the corresponding accented character is returned.
	*           Otherwise, c is returned.
	**/
	public static char addAccentToChar(char c) 
	{
	  if (c == 'α')
	  {
	  	return 'ά';
	  }
	  
	  else if (c == 'ε')
	  {
	  	return 'έ';
	  }
	  
    else if (c == 'η')
    {
    	return 'ή';
    }
    
    else if (c == 'ι')
    {
    	return 'ί';
    }
    
    else if (c == 'ο')
    {
    	return 'ό';
    }
    
    else if (c == 'υ')
    {
    	return 'ύ';
    }
    
    else if (c == 'ω')
    {
    	return 'ώ';
    }
    
    else if (c == 'ϊ')
    {
    	return 'ΐ';
    }
    
    else if (c == 'ϋ')
    {
			return 'ΰ';
    }
		return c;
	}


	/** Removes all trailing Greek consonants of a word, and then adds an accent to the last character of
	*   the word (if possible).
	*   @param s A string containing the characters of the word in reverse order. For
	*             example, the word "λεξη" is given as "ηξελ".
	*   @return A string which is the same as s, except that (i) first all trailing Greek consonants are
	*           removed from the beginning of the string (i.e. end of word), and
	*           (ii) an accent is added (if possible) to the first character of the string
	*           (i.e. the end of the word).
	**/
	public static String addAccentToLast(String s) 
	{
	  if(isConsonant(s.charAt(0)) == true && isVowel(s.charAt(1)) == true) 
		{
	  	return s.substring(1, 2).replace(s.charAt(1), addAccentToChar(s.charAt(1))).concat(s.substring(2));
	  }
	  
	  if(isConsonant(s.charAt(0)) == true && isConsonant(s.charAt(1)) == true && isVowel(s.charAt(2)) == true) 
		{
	  	return s.substring(2, 3).replace(s.charAt(2), addAccentToChar(s.charAt(2))).concat(s.substring(3));
	  }
	  
	  if(isConsonant(s.charAt(0)) == true && isConsonant(s.charAt(1)) == true && isConsonant(s.charAt(2)) == true && isVowel(s.charAt(3)) == true) 
		{
	  	return s.substring(3, 4).replace(s.charAt(3), addAccentToChar(s.charAt(3))).concat(s.substring(4));
	  }
      return s.substring(0, 1).replace(s.charAt(0), addAccentToChar(s.charAt(0))).concat(s.substring(1));
	}


	/** The character sequence contained in this string is replaced by the reverse of the
	*     sequence.
	*   @param s A string containing the characters of the word. E.g. "λεξη".
	*   @return A string which is the reverse of s. E.g. "ηξελ".
	**/
	public static String reverseString(String s) 
	{
	  StringBuffer a = new StringBuffer(s);
	  return a.reverse().toString();
	}


	/** Adds an accent to a particular syllable of an accent-less Greek word, but returns only the initial part
	*    of the resulting accented word up to (including) the accented vowel. For example, in the case of
	*    "λεξη", it returns "λέ".
	*   @param s A string containing the characters of the word. E.g., "λεξη".
	*   @param i Either 1, 2, or 3. 1 means that the accent is to be placed on the last syllable.
	*             2 means that the accent is to be placed on the second last syllable and 3 means
	*             that the accent is to be placed on the third last syllable .
	*   @return Returns a string, the initial part of the resulting accented word, up to (including)
	*           the accented vowel.
	**/
	public static String addAccentToInitialPart(String s, int i) 
	{
		String s1 = reverseString(s);
		if(i == 1) 
		{
			return reverseString(addAccentToLast(s1));
		}
		
		if(i == 2) 
		{
			return reverseString(addAccentToLast(cutLastVowel(s1)));
		}
		
		if(i == 3) 
		{
			return reverseString(addAccentToLast(cutLastVowel(cutLastVowel(s1))));
		}
		return s;
	}


	/** Adds an accent to a particular syllable of an accent-less Greek word.
	*   @param s   A string containing the characters of the accent-less Greek word. E.g., "λεξη".
	*   @param i   Either 1, 2, or 3. 1 means that the accent is to be placed on the last syllable.
	*              2 means that the accent is to be placed on the second last syllable etc.
	*   @return    Returns the word with the accent added.
	**/
	public static String addAccentToWordAux(String s, int i) 
	{
		String s1 = addAccentToInitialPart(s, i);
		String s2 = s.substring(addAccentToInitialPart(s, i).length());
		return s1.concat(s2);
	}


	/** Adds an accent to a particular syllable of an accent-less Greek word.
	*   @param s  A string containing the accent-less Greek word. The string
	*             must end with a "@" followed by "1", "2", or "3", depending on
	*             whether the accent is to be placed on the last, second last,
	*             or third last syllable counting from the end of the word towards the left. E.g., "λεξη@2".
	*   @return   Returns the word with the accent added.
	*             If the string does not end with a "@" plus "1"/"2"/"3", the string is returned unaltered.
	**/
	public static String addAccentToWord(String s) 
	{
		String s1 = reverseString(s);
		String s2 = reverseString(reverseString(s).substring(2));
		if(s1.charAt(0) == '1') 
		{
			return addAccentToWordAux(s2, 1);
		}
		
		if(s1.charAt(0) == '2') 
		{
			return addAccentToWordAux(s2, 2);
		}
		
		if(s1.charAt(0) == '3') 
		{
			return addAccentToWordAux(s2, 3);
		}
		return s;
	}



	/** Removes the accent from a single Greek character.
	*   @param c A single accented character
	*   @return If the character is an accented Greek vowel, the corresponding accent-less character is returned.
	*           Otherwise, c is returned.
	**/
	public static char removeAccentFromChar(char c) 
	{
		if(c == 'ά') 
			{return 'α';}
		
		if(c == 'έ') 
			{return 'ε';}
		
		if(c == 'ή') 
			{return 'η' ;}
		
		if(c == 'ί') 
			{return 'ι';}
		
		if(c == 'ό') 
			{return 'ο';}
		
		if(c == 'ύ') 
			{return 'υ';}
		
		if(c == 'ώ') 
			{return 'ω';}
		
		if(c == 'ΐ') 
			{return 'ϊ';}
		
		if(c == 'ΰ') 
			{return 'ϋ';}
			
		return c;
	}


	/** Removes the accent from an accented Greek word.
	*   @param s  A string containing the characters of the accented Greek word.
	*   @return   Returns the word string with the accent removed.
	*             If the word does not have an accent,
	*             the string is returned unaltered.
	**/
	public static String removeAccentFromWord(String s) 
	{
		String s1 = reverseString(s);
		if(isAccentedVowel(cutLastConsonant(s1).charAt(0)) == true)
		{
		  return reverseString(s1.replace(cutLastConsonant(s1).charAt(0), removeAccentFromChar(cutLastConsonant(s1).charAt(0))));
		}
		
		if(isAccentedVowel(cutLastConsonant(cutLastVowel(s1)).charAt(0))== true)
		{
		  return reverseString(s1.replace(cutLastConsonant(cutLastVowel(s1)).charAt(0), removeAccentFromChar(cutLastConsonant(cutLastVowel(s1)).charAt(0))));
		}
		
		if(isAccentedVowel(cutLastConsonant(cutLastVowel(cutLastConsonant(cutLastVowel(s1)))).charAt(0)) == true)
		{
		  return reverseString(s1.replace(cutLastConsonant(cutLastVowel(cutLastConsonant(cutLastVowel(s1)))).charAt(0), removeAccentFromChar(cutLastConsonant(cutLastVowel(cutLastConsonant(cutLastVowel(s1)))).charAt(0))));
		}
		return s;
	}



	/** Removes all trailing Greek consonants of a word.
	*   @param s A string, containing the characters of the word in reverse order. For
	*            example, the word "λέβης" is given as "ςηβέλ".
	*   @return A string which is the same as arg, except that all trailing Greek consonants are
	*           removed from the beginning of the string (i.e. end of word).
	**/
	public static String cutLastConsonant(String s) 
	{
		if(
		   isConsonant(s.charAt(0)) == true &&
		   isConsonant(s.charAt(1)) == true &&
		   isConsonant(s.charAt(2)) == true
			) 
		{
			return s.substring(3);
		}
		
		if(
		   isConsonant(s.charAt(0)) == true &&
		   isConsonant(s.charAt(1)) == true
			) 
		{
			return s.substring(2);
		}
		
		if(
		   isConsonant(s.charAt(0)) == true
			) 
		{
			return s.substring(1);
		}
		
		return s;
	}



	/** Finds the accent feature (oxutono, paroxutono or proparoxutono) of a Greek word.
	*   @param s A string, containing the characters of the word. E.g. "λέξη".
	*   @return One of the following strings: "oxutono" if the accent falls on the last syllable,
	*                                         "paroxutono" if the accent falls on the second last syllable,
	*                                         "proparoxutono" if the accent falls on the third last syllable.
	*                                         If there is no accent or if the accent is accidentally placed at
	*                                         another syllable, a message, which states that the system could
	*                                         not find an accent feature, is returned.
	**/
	public static String findAccentFeature(String s) 
	{
		String s1 = reverseString(s);
		if(isAccentedVowel(cutLastConsonant(s1).charAt(0)) == true) 
		{
			return "oxutono";
		}
		
		if(isAccentedVowel(cutLastConsonant(cutLastVowel(s1)).charAt(0)) == true) 
		{
			return "paroxutono";
		}
		
		if(isAccentedVowel(cutLastConsonant(cutLastVowel(cutLastConsonant(cutLastVowel(s1)))).charAt(0)) == true) 
		{
			return "proparoxutono";
		}
		return "";
	}



	/** Finds the conjugation feature of a Greek verb, i.e. whether the verb belongs to the first or
	*   the second conjugation.
	*   @param s A string, containing the characters of the verb. E.g. "δημιουργώ".
	*   @return One of the following strings: "first-conjugation" if the verb belongs to the first conjugation,
	*                                         "second-conjugation" if the verb belongs to the second conjugation.
	*                                         In any other case, a message, which states that the system could
	*                                         not find a conjugation feature, is returned.
	**/
	public static String findConjugationFeature(String s) 
	{
		if(s.endsWith("ω") == true || s.endsWith("ομαι") == true) 
		{
			return "first-conjugation";
		}
		
		if(s.endsWith("ώ") == true || s.endsWith("ιέμαι") == true || s.endsWith("ούμαι") == true) 
		{
			return "second-conjugation";
		}
		return "";
	}



	/** Finds the class feature of a second conjugation Greek verb, i.e. whether the verb belongs to the first or
	*   the second class of the second conjugation.
	*   @param s A string, containing the characters of second person singular form of the verb. E.g. "δημιουργείς".
	*   @return One of the following strings: "first-class" if the verb belongs to the first class,
	*                                         "second-class" if the verb belongs to the second class.
	*                                         In any other case, a message, which states that the system could
	*                                         not find a class feature, is returned.
	**/
	public static String findClassFeature(String s) 
	{
		if(s.endsWith("άς") == true || s.endsWith("ιέσαι") == true || s.endsWith("άσαι") == true) 
		{
			return "first-class";
		}
		
		if(s.endsWith("είς") == true || s.endsWith("είσαι") == true) 
		{
			return "second-class";
		}
		return "";
	}



	/** Finds the declension feature of a Greek masculine or feminine noun, i.e. whether the noun has the feature
	*   "three-endings-noun"
	*   or the feature "two-endings-noun". Only for masculine and feminine nouns, neuter nouns are always two-endings.
	*   @param s1 A string, containing the characters of the noun. E.g. "ζωγράφος".
	*   @param s2 Gender.
	*   @return One of the following strings: "three-endings-noun" if the noun has three different endings in each number,
	*                                         This declension includes only masculine or feminine nouns.
	*                                         "two-endings noun" if the noun has two different endings in each number.
	**/
	public static String findDeclensionFeature(String s1, String s2) 
	{
		if((s1.endsWith("ος") == true || 
			s1.endsWith("ός") == true) && 
			(s2.equalsIgnoreCase("masculine") || 
			s2.equalsIgnoreCase("feminine"))
			) 
		{
			return "three-endings-noun";
		}
		return "two-endings-noun";
	}



	/** Finds the type of a Greek two-endings-noun, i.e. whether the noun is isosyllavo or anisosyllavo.
	*   @param s1 A string, containing the characters of the Greek two-endings-noun in the nominative singular.
	*             E.g. "υδρία".
	*   @param s2 A string, containing the characters of the Greek two-endings-noun in the nominative plural.
	*             E.g. "υδρίες".
	*   @return One of the following strings: "isosyllava" if the noun has the same number of syllables in both numbers,
	*                                         "anisosyllava" if the noun has different number of syllables in each number.
	**/
	public static String findTwoEndingNounType(String s1, String s2) 
	{
		if((s2.length() - s1.length()) >= 2 && s2.endsWith("εις") == false) 
		{
			return "anisosyllava";
		}
		return "isosyllava";
	}



	/** Finds the type of a Greek isosyllavo noun, i.e. whether the noun is
	*   ancient-like, not-ancient-like, neuter1 or neuter2.
	*   @param s A string, containing the characters of the Greek isosyllavo noun in the nominative plural.
	*             E.g. "υδρίες".
	*   @return One of the following strings: "ancient-like" (masculine or feminine nouns only) if the noun follows ancient Greek declensions in some cases(plural -εις),
	*                                         "not-ancient-like" (masculine or feminine nouns only) if the above case does not hold (plural -ες),
	*                                         "neuter1" (neuter nouns only) if the noun ends in -ι or -ο (plural -α).
	*                                         "neuter2" (neuter nouns only) if the noun ends in -ος (plural -η).
	*                                         In any other case, a message, which states that the system could
	*                                         not find the isosyllable type feature, is returned.
	**/
	public static String findIsosyllableType(String s) 
	{
		if(s.endsWith("εις") == true || s.endsWith("είς") == true) 
		{
			return "ancient-like";
		}
		
		if(s.endsWith("ες") == true || s.endsWith("ές") == true) 
		{
			return "not-ancient-like";
		}
		
		if(s.endsWith("α") == true || s.endsWith("ά") == true) 
		{
			return "neuter1";
		}
		
		if(s.endsWith("η") == true || s.endsWith("ή") == true) 
		{
			return "neuter2";
		}
		return "";
	}



	/** Only for masculine or feminine nouns. Finds the stem of the noun removing the final "ς" from the end of
	*   masculine or feminine nouns.
	*   @param s A string containing the characters of the noun (nominative singular).
	*   @return If the noun ends in "ς", the "ς" is removed from the end of the string. In any other case,
	*           s is returned as it is.
	**/
	public static String findNounStem(String s) 
	{
		if(s.endsWith("ς") == true) 
		{
			return reverseString(reverseString(s).substring(1));
		}
		return s;
	}



	/** Only for verbs. Finds the stem of the verb removing the ending.
	*   @param s A string containing the characters of the noun (nominative singular).
	*   @return If the verb ends in "ω", "ώ", "ομαι", "ώμαι", "άμαι", "ιέμαι", "ούμαι", the ending is removed.
	*           In any other case, a message, which states the stem cannot be found, is returned.
	*
	**/
	public static String findVerbStem(String s) 
	{
		if(s.endsWith("ω") == true || s.endsWith("ώ") == true) 
		{
			return reverseString(reverseString(s).substring(1));
		}
		
		if(s.endsWith("ομαι") == true || 
			s.endsWith("ώμαι") == true || 
			s.endsWith("άμαι") == true || 
			s.endsWith("αμαι") == true
			) 
		{
			return reverseString(reverseString(s).substring(4));
		}
		
		if(s.endsWith("ιέμαι") == true || s.endsWith("ούμαι") == true) 
		{
			return reverseString(reverseString(s).substring(5));
		}
		return s;
	}

	/** For test purposes.
	**/
	//  public static static void main(String[] args) {
	//      GreekAccentUtils ga = new GreekAccentUtils();
	//      System.out.println(ga.findDeclensionFeature("βέλος", "neuter"));
	//   }
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
