//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.lang.gr.GreekAccentUtils;

import java.net.URL;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;



/**
 * <p>Title: ExportUtilsExprimo</p>
 * <p>Description: Contains all methods for exporting the domain to plain text (ILEX)</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class ExportUtilsIlex 
{
	static File mpirodomain;
	static File domPop;
	static File domSpec;
	static File english;
	static File italian;
	static File greek;
	
	static String inpackageFile = new String("(in-package :wb)");
	static String emptyspaceFile = new String("");
	static String deflexicalitemopen = new String("(def-lexical-item");
	static String defbasictypeopen = new String("(def-basic-type");
	static String deftaxonomyopen = new String("(def-taxonomy");
	static String defclose = new String(")");


	/**
	 * Create the export directories
	 * @param dirPath the path for the new directories to be created
	 */
	public static void createExportDirectories(String dirPath) 
	{
		String domainname = Mpiro.loadedDomain.substring(0, Mpiro.loadedDomain.length()-6);
		mpirodomain = new File(dirPath + "/" + domainname);
		mpirodomain.mkdir();
		
		domPop = new File(mpirodomain + "/DomPop");
		domPop.mkdir();
		domSpec = new File(mpirodomain + "/DomSpec");
		domSpec.mkdir();
		english = new File(mpirodomain + "/English");
		english.mkdir();
		italian = new File(mpirodomain + "/Italian");
		italian.mkdir();
		greek = new File(mpirodomain + "/Greek");
		greek.mkdir();
	} // createExportDirectories


  /**
   * Create the English lexicon
   */
  public static void createEnglishLexicon() 
  {
    // Getting the mainLexiconHashtable nouns hashtable
    Hashtable allNounsHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Nouns");
    String englishFile = english.toString() + "/English.lexicon";

    try
    {
	    //FileOutputStream output = new FileOutputStream(englishFile);
	    //PrintStream p = new PrintStream(output);
	    //OutputStream out= new FileOutputStream(englishFile);
	    //OutputStreamWriter p = new OutputStreamWriter(out, "8859_1");
	    //PrintWriter p = new PrintWriter(OutputStreamWriter(new FileOutputStream(englishFile), "ISO8859_1") );
	
	    OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(englishFile), "ISO8859_1");
	    PrintWriter p = new PrintWriter(output);
	
	    p.println(inpackageFile);
	    p.println(emptyspaceFile);


			// NOUNS
			p.println(";;; NOUNS");
			
			Vector nounsVector = QueryLexiconHashtable.getNounsVectorFromMainLexiconHashtable();
			Enumeration nounsVectorEnum = nounsVector.elements();
			while (nounsVectorEnum.hasMoreElements())
			{
	      String noun = nounsVectorEnum.nextElement().toString();
	      Hashtable currentNounValues = QueryLexiconHashtable.showValues(noun, "English");
	      // the spelling from "enbasetext"
	      String spelling = currentNounValues.get("enbasetext").toString();
	
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
        String concept = QueryHashtable.returnAllEntityTypesContainingThisNoun(noun);


				// putting info into the file
				p.println(emptyspaceFile);
				p.println(deflexicalitemopen);
				p.println("   :name " + noun);
				p.println("   :spelling \"" + spelling + "\"");
				p.println("   :grammatical-features   (noun common-noun " + countable + ")");

        if (currentNounValues.get("encb").toString().equalsIgnoreCase("true"))
        {
	        String pluralnoun = currentNounValues.get("enpluraltext").toString();
	
	        p.println("   :spelling-exceptions    (");
	        p.println("                           (plural-noun \"" + pluralnoun + "\")");
	        p.println("                           )");
        }
				p.println("   :concept   (" + concept + ")");
				p.println(defclose);
			}//while

			// VERBS
			p.println(emptyspaceFile);
			p.println(";;; VERBS");
			
			Vector verbsVector = QueryLexiconHashtable.getVerbsVectorFromMainLexiconHashtable();
			Enumeration verbsVectorEnum = verbsVector.elements();
			while (verbsVectorEnum.hasMoreElements())
			{
	      String verb = verbsVectorEnum.nextElement().toString();
	      Hashtable currentVerbValues = QueryLexiconHashtable.showValues(verb, "English");
	      // the spelling from "enbasetext"
	      String spelling = currentVerbValues.get("vbasetext").toString();
	
	      // the grammatical features by checkiong for auxiliary verbs & from "transitive"
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

				// putting info into the file
				p.println(emptyspaceFile);
				p.println(deflexicalitemopen);
				p.println("   :name " + verb);
				p.println("   :spelling \"" + spelling + "\"");
				p.println("   :grammatical-features   (" + verbType + " " + transitive + ")");


        if ( (currentVerbValues.get("vcb1").toString().equalsIgnoreCase("true")) ||
             (currentVerbValues.get("vcb2").toString().equalsIgnoreCase("true")) ||
             (currentVerbValues.get("vcb3").toString().equalsIgnoreCase("true")) ||
             (currentVerbValues.get("vcb4").toString().equalsIgnoreCase("true"))
           )
        {
	        p.println("   :spelling-exceptions    (");
	
	        if (currentVerbValues.get("vcb1").toString().equalsIgnoreCase("true"))
	        {
	          String thirdpstext = currentVerbValues.get("thirdpstext").toString();
	          p.println("                           (thirdpersonsingular \"" + thirdpstext + "\")");
	        }
	
	        if (currentVerbValues.get("vcb2").toString().equalsIgnoreCase("true"))
	        {
            String sipatext = currentVerbValues.get("sipatext").toString();
            p.println("                           (past-verb \"" + sipatext + "\")");
	        }

          if (currentVerbValues.get("vcb3").toString().equalsIgnoreCase("true"))
          {
            String prpatext = currentVerbValues.get("prpatext").toString();
            p.println("                           (gerund-verb \"" + prpatext + "\")");
          }

          if (currentVerbValues.get("vcb4").toString().equalsIgnoreCase("true"))
          {
            String papatext = currentVerbValues.get("papatext").toString();
            p.println("                           (participle-verb \"" + papatext + "\")");
          }
          p.println("                           )");
				}
				p.println(defclose);
			}//while
      p.flush();
      p.close();
		}
    catch (java.io.IOException IOE)
    {
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
    }
	} // createEnglishLexicon


  /**
   * Create the Italian lexicon
   */
  public static void createItalianLexicon() 
  {
	  // Getting the mainLexiconHashtable nouns hashtable
	  Hashtable allNounsHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Nouns");
	  String italianFile = italian.toString() + "/Italian.lexicon";
	
	  try
	  {
			//FileOutputStream output = new FileOutputStream(italianFile);
			//PrintStream p = new PrintStream(output);
			OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(italianFile), "ISO8859_3");
			PrintWriter p = new PrintWriter(output);
			
			p.println(inpackageFile);
			p.println(emptyspaceFile);
			
			// NOUNS
			p.println(";;; NOUNS");

      Vector nounsVector = QueryLexiconHashtable.getNounsVectorFromMainLexiconHashtable();
      Enumeration nounsVectorEnum = nounsVector.elements();
      while (nounsVectorEnum.hasMoreElements())
      {
        String noun = nounsVectorEnum.nextElement().toString();
        Hashtable currentNounValues = QueryLexiconHashtable.showValues(noun, "Italian");

        // the spelling from "enbasetext"
        String spelling = currentNounValues.get("itbasetext").toString();

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
				String concept = QueryHashtable.returnAllEntityTypesContainingThisNoun(noun);
				
				// putting info into the file
				p.println(emptyspaceFile);
				p.println(deflexicalitemopen);
				p.println("   :name " + noun);
				p.println("   :spelling \"" + spelling + "\"");
				p.println("   :grammatical-features   (noun common-noun " + countable + " " + itgender + ")");

				if (currentNounValues.get("itcb").toString().equalsIgnoreCase("true"))
				{
			    String pluralnoun = currentNounValues.get("itpluraltext").toString();
			
			    p.println("   :spelling-exceptions    (");
			    p.println("                           (plural-noun \"" + pluralnoun + "\")");
			    p.println("                           )");
				}
		    p.println("   :concept   (" + concept + ")");
		    p.println(defclose);
			}//while

			// VERBS
			p.println(emptyspaceFile);
			p.println(";;; VERBS");
			
			Vector verbsVector = QueryLexiconHashtable.getVerbsVectorFromMainLexiconHashtable();
			Enumeration verbsVectorEnum = verbsVector.elements();
			while (verbsVectorEnum.hasMoreElements())
			{
        String verb = verbsVectorEnum.nextElement().toString();
        Hashtable currentVerbValues = QueryLexiconHashtable.showValues(verb, "Italian");
        // the spelling from "enbasetext"
        String spelling = currentVerbValues.get("vbasetext").toString();

        // the grammatical features by checkiong for auxiliary verbs & from "transitive"
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

				// putting info into the file
				p.println(emptyspaceFile);
				p.println(deflexicalitemopen);
				p.println("   :name " + verb);
				p.println("   :spelling \"" + spelling + "\"");
				p.println("   :grammatical-features   (" + verbType + " " + transitive + ")");
				
				int countSpellingExceptions = 0;
				Vector table1 = (Vector)currentVerbValues.get("vTable");
				Vector table2 = (Vector)currentVerbValues.get("pTable");
				
				Enumeration table1Enum = table1.elements();
        while (table1Enum.hasMoreElements())
        {
          Vector row = (Vector)table1Enum.nextElement();
          if (row.elementAt(4).toString().equalsIgnoreCase("true"))
          {
          	countSpellingExceptions++;
          }
        }
        
        Enumeration table2Enum = table2.elements();
        while (table2Enum.hasMoreElements())
        {
          Vector row = (Vector)table2Enum.nextElement();
          if (row.elementAt(3).toString().equalsIgnoreCase("true"))
          {
          	countSpellingExceptions++;
          }
        }

        if (countSpellingExceptions > 0)
        {
          p.println("   :spelling-exceptions    (");
          String tenseCell = new String();
          String numberCell = new String();
          String personCell = new String();
          String genderCell = new String();
          String spellingException = new String();
          Enumeration table1Enum2 = table1.elements();
          while (table1Enum2.hasMoreElements())
					{
	          Vector row = (Vector)table1Enum2.nextElement();
	          if (row.elementAt(4).toString().equalsIgnoreCase("true"))
	          {
              if (row.elementAt(0).toString().equalsIgnoreCase("Present"))
              {
              	tenseCell = "present-verb ";
              }
              
              else if (row.elementAt(0).toString().equalsIgnoreCase("Past continuous"))
              {
              	tenseCell = "past-verb progressive-verb ";
              }
              
              else if (row.elementAt(0).toString().equalsIgnoreCase("Remote past"))
              {
              	tenseCell = "past-verb simple-verb ";
              }
              
              if (row.elementAt(1).toString().equalsIgnoreCase("Singular"))
              {
              	numberCell = "singular-verb ";
              }
              
              else if (row.elementAt(1).toString().equalsIgnoreCase("Plural"))
              {
              	numberCell = "plural-verb ";
              }
              
              if (row.elementAt(2).toString().equalsIgnoreCase("1st"))
              {
              	personCell = "firstperson)";
              }
              
              else if (row.elementAt(2).toString().equalsIgnoreCase("2nd"))
              {
              	personCell = "secondperson)";
              }
              
              else if (row.elementAt(2).toString().equalsIgnoreCase("3rd"))
              {
              	personCell = "thirdperson)";
              }
              spellingException = row.elementAt(3).toString();

              p.println("                           ((" + tenseCell + numberCell + personCell + " \"" + spellingException + "\")");
						}//if
					}//while

          Enumeration table2Enum2 = table2.elements();
          while (table2Enum2.hasMoreElements())
          {
            Vector row = (Vector)table2Enum2.nextElement();
            if (row.elementAt(3).toString().equalsIgnoreCase("true"))
            {
              if (row.elementAt(0).toString().equalsIgnoreCase("Masculine"))
              {
              	genderCell = "participle-verb masculine-verb ";
              }
              
              else if (row.elementAt(0).toString().equalsIgnoreCase("Feminine"))
              {
              	genderCell = "participle-verb feminine-verb ";
              }
              
              if (row.elementAt(1).toString().equalsIgnoreCase("Singular"))
              {
              	numberCell = "singular-verb)";
              }
              
              else if (row.elementAt(1).toString().equalsIgnoreCase("Plural"))
              {
              	numberCell = "plural-verb)";
              }
              spellingException = row.elementAt(2).toString();

              p.println("                           ((" + genderCell + numberCell + " \"" + spellingException + "\")");
						}//if
					}//while
					p.println("                           )");
				}//if
				p.println(defclose);
			}
	    p.println(emptyspaceFile);
	    p.println(";;; PROPER NOUNS");
	
	    p.flush();
	    p.close();
		}
    catch (java.io.IOException IOE)
    {
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
    }

	} // createItalianLexicon


  /** Create the Greek lexicon */
  public static void createGreekLexicon() 
  {
    // Getting the mainLexiconHashtable nouns hashtable
    Hashtable allNounsHashtable = (Hashtable)QueryLexiconHashtable.mainLexiconHashtable.get("Nouns");
    String greekFile = greek + "/Greek.lexicon";

    try
    {
			//FileOutputStream output = new FileOutputStream(greekFile);
			//PrintStream p = new PrintStream(output);
			OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(greekFile), "ISO8859_7");
			PrintWriter p = new PrintWriter(output);
			
			p.println(inpackageFile);
			p.println(emptyspaceFile);
			
			// NOUNS
			p.println(";;; NOUNS");

      Vector nounsVector = QueryLexiconHashtable.getNounsVectorFromMainLexiconHashtable();
      Enumeration nounsVectorEnum = nounsVector.elements();
      while (nounsVectorEnum.hasMoreElements())
      {
        String noun = nounsVectorEnum.nextElement().toString();
        Hashtable currentNounValues = QueryLexiconHashtable.showValues(noun, "Greek");

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
        String accentFeature = new String();
        String declensionFeature = new String();
        StringBuffer additionalGrammaticalFeatures = new StringBuffer("");

        String grinflection = new String();
        String grinflectionValue = currentNounValues.get("grinflection").toString();
        if (grinflectionValue.equalsIgnoreCase("Inflected"))
        {
          grinflection = "inflected-noun";
          // the spelling from "grbasetext"
          //System.out.println("()---- " + grbasetext);
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
            }
            catch (Exception ex)
            {
              System.out.println("(Typing error in)---  " + noun);
              spelling = "";
              accentFeature = "";
              declensionFeature = "";
            }

            additionalGrammaticalFeatures.append(" " + accentFeature + " " + declensionFeature);

            String twoEndingNounType = new String("");
            String isosyllableType = new String("");
            if (declensionFeature.equalsIgnoreCase("two-endings-noun"))
            {
              twoEndingNounType = GreekAccentUtils.findTwoEndingNounType(grbasetext, grpluraltext);
              additionalGrammaticalFeatures.append(" " + twoEndingNounType);
            }
            if (twoEndingNounType.equalsIgnoreCase("isosyllava"))
            {
              isosyllableType = GreekAccentUtils.findIsosyllableType(grpluraltext);
              additionalGrammaticalFeatures.append(" " + isosyllableType);
            }
            //System.out.println("()---- " + additionalGrammaticalFeatures.toString());
					}
				}
        else if (grinflectionValue.equalsIgnoreCase("Not inflected"))
        {
          grinflection = "lexicalised-noun";
          // the spelling from "grbasetext"
          spelling = grbasetext;
        }

				// the concept
				String concept = QueryHashtable.returnAllEntityTypesContainingThisNoun(noun);
				
				// putting info into the file
				p.println(emptyspaceFile);
				p.println(deflexicalitemopen);
				p.println("   :name " + noun);
				p.println("   :spelling \"" + spelling + "\"");
				p.println("   :grammatical-features   (noun common-noun " + countable + " " + grgender + " " + grinflection + additionalGrammaticalFeatures.toString() + ")");

        if (currentNounValues.get("cb1").toString().equalsIgnoreCase("true") ||
            currentNounValues.get("cb2").toString().equalsIgnoreCase("true") ||
            currentNounValues.get("cb3").toString().equalsIgnoreCase("true") ||
            currentNounValues.get("cb4").toString().equalsIgnoreCase("true") ||
            currentNounValues.get("cb5").toString().equalsIgnoreCase("true") ||
            currentNounValues.get("cb6").toString().equalsIgnoreCase("true")
           )
        {
					p.println("   :spelling-exceptions    (");

	        if (currentNounValues.get("cb1").toString().equalsIgnoreCase("true"))
	        {
            String grsntext = currentNounValues.get("grsntext").toString();
            p.println("                           ((singular-noun nominative-noun) \"" + grsntext + "\")");
	        }
	        
	        if (currentNounValues.get("cb2").toString().equalsIgnoreCase("true"))
	        {
            String grsgtext = currentNounValues.get("grsgtext").toString();
            p.println("                           ((singular-noun genitive-noun) \"" + grsgtext + "\")");
	        }
	        
	        if (currentNounValues.get("cb3").toString().equalsIgnoreCase("true"))
	        {
            String grsatext = currentNounValues.get("grsatext").toString();
            p.println("                           ((singular-noun accusative-noun) \"" + grsatext + "\")");
	        }
	        
	        if (currentNounValues.get("cb4").toString().equalsIgnoreCase("true"))
	        {
            String grpntext = currentNounValues.get("grpntext").toString();
            p.println("                           ((plural-noun nominative-noun) \"" + grpntext + "\")");
	        }
	        
	        if (currentNounValues.get("cb5").toString().equalsIgnoreCase("true"))
	        {
            String grpgtext = currentNounValues.get("grpgtext").toString();
            p.println("                           ((plural-noun genitive-noun) \"" + grpgtext + "\")");
	        }
	        
	        if (currentNounValues.get("cb6").toString().equalsIgnoreCase("true"))
	        {
            String grpatext = currentNounValues.get("grpatext").toString();
            p.println("                           ((plural-noun accusative-noun) \"" + grpatext + "\")");
	        }
	        p.println("                           )");
				}
        p.println("   :concept   (" + concept + ")");
        p.println(defclose);
			}//while


			// VERBS
			p.println(emptyspaceFile);
			p.println(";;; VERBS");
			
			Vector verbsVector = QueryLexiconHashtable.getVerbsVectorFromMainLexiconHashtable();
			Enumeration verbsVectorEnum = verbsVector.elements();
			while (verbsVectorEnum.hasMoreElements())
			{
	      String verb = verbsVectorEnum.nextElement().toString();
	      Hashtable currentVerbValues = QueryLexiconHashtable.showValues(verb, "Greek");
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
          try
          {
          	spelling = GreekAccentUtils.removeAccentFromWord(GreekAccentUtils.findVerbStem(vbasetext));
          }
          catch (Exception ex)
          {
            System.out.println("(Typing error in)---  " + verb);
            spelling = "";
          }
        }
        // the grammatical features by checkiong for auxiliary verbs & from "transitive"
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

				// putting info into the file
				p.println(emptyspaceFile);
				p.println(deflexicalitemopen);
				p.println("   :name " + verb);
				p.println("   :spelling \"" + spelling + "\"");
				p.println("   :grammatical-features   (" + verbType + " " + transitive + " " + conjugationFeature + " " + classFeature + ")");
				
				int countSpellingExceptions = 0;
				Vector table1 = (Vector)currentVerbValues.get("vTable");
				Vector table2 = (Vector)currentVerbValues.get("pTable");

        Enumeration table1Enum = table1.elements();
        while (table1Enum.hasMoreElements())
        {
          Vector row = (Vector)table1Enum.nextElement();
          if (row.elementAt(5).toString().equalsIgnoreCase("true"))
          {
          	countSpellingExceptions++;
          }
        }
        
        Enumeration table2Enum = table2.elements();
        while (table2Enum.hasMoreElements())
        {
          Vector row = (Vector)table2Enum.nextElement();
          if (row.elementAt(2).toString().equalsIgnoreCase("true"))
          {
          	countSpellingExceptions++;
          }
        }
        if (currentVerbValues.get("cb1").toString().equalsIgnoreCase("true")  ||
            currentVerbValues.get("cb2").toString().equalsIgnoreCase("true")  ||
            currentVerbValues.get("cb3").toString().equalsIgnoreCase("true")
           )
        {
        	countSpellingExceptions++;
        }

        if (countSpellingExceptions > 0)
        {
          p.println("   :spelling-exceptions    (");
          String tenseCell = new String();
          String voiceCell = new String();
          String numberCell = new String();
          String personCell = new String();
          String genderCell = new String();
          String spellingException = new String();
          Enumeration table1Enum2 = table1.elements();
          while (table1Enum2.hasMoreElements())
          {
            Vector row = (Vector)table1Enum2.nextElement();
            if (row.elementAt(5).toString().equalsIgnoreCase("true"))
            {
              if (row.elementAt(0).toString().equalsIgnoreCase("Present progressive"))
              {
              	tenseCell = "present-verb progressive-verb ";
              }
              
              else if (row.elementAt(0).toString().equalsIgnoreCase("Past progressive"))
              {
              	tenseCell = "past-verb progressive-verb ";
              }
              
              else if (row.elementAt(0).toString().equalsIgnoreCase("Past simple"))
              {
              	tenseCell = "past-verb simple-verb ";
              }
              
              if (row.elementAt(1).toString().equalsIgnoreCase("Active"))
              {
              	voiceCell = "active-verb ";
              }
                
              else if (row.elementAt(1).toString().equalsIgnoreCase("Passive"))
              {
              	voiceCell = "passive-verb ";
              }
              
              if (row.elementAt(2).toString().equalsIgnoreCase("Singular"))
              {
              	numberCell = "singular-verb ";
              }
                
              else if (row.elementAt(2).toString().equalsIgnoreCase("Plural"))
              {
              	numberCell = "plural-verb ";
              }
              
              if (row.elementAt(3).toString().equalsIgnoreCase("1st"))
              {
              	personCell = "firstperson)";
              }
              
              else if (row.elementAt(3).toString().equalsIgnoreCase("2nd"))
              {
              	personCell = "secondperson)";
              }
              
              else if (row.elementAt(3).toString().equalsIgnoreCase("3rd"))
              {
              	personCell = "thirdperson)";
              }
              spellingException = row.elementAt(4).toString();

              p.println("                           ((" + tenseCell + voiceCell + numberCell + personCell + " \"" + spellingException + "\")");
						}//if
					}//while

          // Infinitive1, infinitive2, active participle
          if (currentVerbValues.get("cb1").toString().equalsIgnoreCase("true"))
          {
            spellingException = currentVerbValues.get("infText").toString();
            p.println("                           ((" + "infinitive active-verb)" + " \"" + spellingException + "\")");
          }
          if (currentVerbValues.get("cb2").toString().equalsIgnoreCase("true"))
          {
            spellingException = currentVerbValues.get("infText2").toString();
            p.println("                           ((" + "infinitive passive-verb)" + " \"" + spellingException + "\")");
          }
          if (currentVerbValues.get("cb3").toString().equalsIgnoreCase("true"))
          {
            spellingException = currentVerbValues.get("apText").toString();
            p.println("                           ((" + "participle-verb active-verb)" + " \"" + spellingException + "\")");
          }


          // table2
          Enumeration table2Enum2 = table2.elements();
          while (table2Enum2.hasMoreElements())
          {
            Vector row = (Vector)table2Enum2.nextElement();
            if (row.elementAt(2).toString().equalsIgnoreCase("true"))
            {
              if (row.elementAt(0).toString().equalsIgnoreCase("Masculine"))
              {
              	genderCell = "participle-verb passive-verb masculine-verb ";
              }
              
              else if (row.elementAt(0).toString().equalsIgnoreCase("Feminine"))
              {
              	genderCell = "participle-verb passive-verb feminine-verb ";
              }
              
              else if (row.elementAt(0).toString().equalsIgnoreCase("Neuter"))
              {
              	genderCell = "participle-verb passive-verb neuter-verb ";
              }

              spellingException = row.elementAt(1).toString();
              p.println("                           ((" + genderCell + " \"" + spellingException + "\")");
						}
					}
					p.println("                           )");
				}
				p.println(defclose);
			}
			p.println(emptyspaceFile);
			p.println(";;; PROPER NOUNS");
			
			p.flush();
			p.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}

	} // createGreekLexicon


	/** Create the types.gram */
	public static void createTypesGram() 
	{
		String typesFile = domSpec.toString() + "/types.gram";
		
		try
		{
      //FileOutputStream output = new FileOutputStream(typesFile);
      //PrintStream p = new PrintStream(output);
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(typesFile), "ISO8859_1");
      PrintWriter p = new PrintWriter(output);

      p.println(inpackageFile);
      p.println(emptyspaceFile);

      // BASIC ENTITY TYPES
      p.println(";;; EXHIBIT HIERARCHY");
      String domainname = Mpiro.loadedDomain.substring(0, Mpiro.loadedDomain.length()-6);

      Vector allBasicTypesVector = QueryHashtable.getChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
      Enumeration allBasicTypesVectorEnum = allBasicTypesVector.elements();
      while (allBasicTypesVectorEnum.hasMoreElements())
      {
        String basicEntityType = allBasicTypesVectorEnum.nextElement().toString();
        StringBuffer upperTypes = new StringBuffer();

        NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(basicEntityType);
        Vector upperVector = (Vector)nv.get(1);
        int upperVectorSize = upperVector.size();
        if (upperVectorSize != 1)
        {
          upperTypes.append("(and");
          Enumeration upperVectorEnum = upperVector.elements();
          while (upperVectorEnum.hasMoreElements())
          {
            String upperVectorElement = upperVectorEnum.nextElement().toString();
            // we add this if to account for ILEX
            if (upperVectorElement.equalsIgnoreCase("3D-physical-object"))
            {
            	upperVectorElement = "3D-phys-object";
            }
            //
            upperTypes.append(" " + upperVectorElement);
        	}
          upperTypes.append(")");
				}
        else if (upperVectorSize == 1)
        {
          // we add this if to account for ILEX
          if (upperVector.firstElement().toString().equalsIgnoreCase("3D-physical-object"))
          {
          	upperTypes.append("3D-phys-object");
          }
          else
          {//
          	upperTypes.append(upperVector.firstElement().toString());
          }
        }
        // putting info into the file
        p.println(emptyspaceFile);
        p.println(defbasictypeopen);
        p.println("   :domain " + domainname);
        p.println("   :head " + basicEntityType);
        p.println("   :um-link " + upperTypes.toString());
        p.println(defclose);
			}//while


      // ENTITY TYPES (TAXONOMY)
      Vector fullPathChilrenEntityTypes = (Vector)QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
      Enumeration fullPathChilrenEntityTypesEnum = fullPathChilrenEntityTypes.elements();
      while (fullPathChilrenEntityTypesEnum.hasMoreElements())
      {
        String entityType = fullPathChilrenEntityTypesEnum.nextElement().toString();
        Vector entityTypeChildrenVector = QueryHashtable.getChildrenVectorFromMainDBHashtable(entityType, "Entity type");
        if (!entityTypeChildrenVector.isEmpty())
        {
          StringBuffer childrenString = new StringBuffer();
          Enumeration entityTypeChildrenVectorEnum = entityTypeChildrenVector.elements();
          while (entityTypeChildrenVectorEnum.hasMoreElements())
          {
            String child = entityTypeChildrenVectorEnum.nextElement().toString();
            childrenString.append(child + " ");
          }
          // putting info into the file
          p.println(emptyspaceFile);
          p.println(deftaxonomyopen);
          p.println("   :type " + entityType);
          p.println("   :subtypes (" + childrenString.toString().trim() + ")");
          p.println(defclose);
        }
      }
      p.flush();
      p.close();
  	}
	  catch (java.io.IOException IOE)
	  {
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
	  }

	} // createTypesGram



  /** Create the predicates.gram */
  public static void createPredicatesGram() 
  {
    String predicatesFile = domSpec.toString() + "/predicates.gram";

    try
    {
      //FileOutputStream output = new FileOutputStream(predicatesFile);
      //PrintStream p = new PrintStream(output);
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(predicatesFile), "ISO8859_1");
      PrintWriter p = new PrintWriter(output);

      p.println(inpackageFile);
      p.println(emptyspaceFile);

      Vector allUserTypesVector = (Vector)QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();

      // get a vector containing all entity-types
      Vector allEntityTypesVector = (Vector)QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
      Enumeration allEntityTypesVectorEnum = allEntityTypesVector.elements();
      while (allEntityTypesVectorEnum.hasMoreElements())
      {
        String entityType = allEntityTypesVectorEnum.nextElement().toString();

        StringBuffer interest = new StringBuffer();
        StringBuffer importance = new StringBuffer();
        StringBuffer assimilation = new StringBuffer();
        StringBuffer assimilationRate = new StringBuffer();

        Enumeration allUserTypesVectorEnum1 = allUserTypesVector.elements();
        while (allUserTypesVectorEnum1.hasMoreElements())
        {
          String usertype = allUserTypesVectorEnum1.nextElement().toString();
          Vector userModelValuesVector = QueryUsersHashtable.getUserModelValuesVector("Subtype-of", entityType, usertype);

          interest.append("(" + usertype + " " + userModelValuesVector.get(0).toString() + ") ");
          importance.append("(" + usertype + " " + userModelValuesVector.get(1).toString() + ") ");
          if (userModelValuesVector.get(2).toString().equalsIgnoreCase("0"))
          {
            assimilation.append("(" + usertype + " 1) ");
            assimilationRate.append("(" + usertype + " 1) ");
          }
          else
          {
            assimilation.append("(" + usertype + " 0) ");

            if (userModelValuesVector.get(2).toString().equalsIgnoreCase("1"))
            {
            	assimilationRate.append("(" + usertype + " 1) ");
            }
            
            else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("2"))
            {
            	assimilationRate.append("(" + usertype + " 0.5) ");
            }
            
            else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("3"))
            {
            	assimilationRate.append("(" + usertype + " 0.33) ");
            }
          }
				}

				/* SPECIAL CODE
				Vector exhibitPlusFullPathChildrenVector = (Vector)QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable("Exhibit", "Entity type");
				if (!exhibitPlusFullPathChildrenVector.contains(entityType))
				{
				    p.println(emptyspaceFile);
				    p.println("(defpredicate subclass");
				    p.println("   :arg1 " + entityType);
				    p.println("   :interest ( " + interest.toString() + ")");
				    p.println("   :importance ( " + importance.toString() + ")");
				    p.println("   :assimilation ( " + assimilation.toString() + ")");
				    p.println("   :assim-rate ( " + assimilationRate.toString() + ")");
				    p.println(defclose);
				}   */
        p.println(emptyspaceFile);
        p.println("(defpredicate subclass");
        p.println("   :arg1 " + entityType);
        p.println("   :interest ( " + interest.toString() + ")");
        p.println("   :importance ( " + importance.toString() + ")");
        p.println("   :assimilation ( " + assimilation.toString() + ")");
        p.println("   :assim-rate ( " + assimilationRate.toString() + ")");
        p.println(defclose);
			}
			///////////////////////////////////////////////////

			/* OLDER VERSION left here for reference ONLY
			// get a vector containing all basic-entity-types
			Vector allBasicTypesVector = QueryHashtable.getChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
			Enumeration allBasicTypesVectorEnum = allBasicTypesVector.elements();
			while (allBasicTypesVectorEnum.hasMoreElements())
			{
			    String basicEntityType = allBasicTypesVectorEnum.nextElement().toString();
			
			    // the "exhibit" is not considered
			    if (!basicEntityType.equalsIgnoreCase("exhibit"))
			    {
			        p.println(emptyspaceFile);
			        p.println("(defpredicate subclass");
			        p.println("   :arg1 " + basicEntityType);
			        p.println("   :assimilation " + "((default 1))");
			        p.println(defclose);
			    }
			}
			///////////////////////////////////////////////////
			*/


      Hashtable allEntityTypesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
      allEntityTypesHashtable.remove("Data Base");
      allEntityTypesHashtable.remove("Basic-entity-types");
      //System.out.println("()---- " + allEntityTypesHashtable.toString());

      for (Enumeration k = allEntityTypesHashtable.keys(), e = allEntityTypesHashtable.elements(); k.hasMoreElements(); )
      {
        String entitytype = k.nextElement().toString();
        String parent = e.nextElement().toString();
        NodeVector env = (NodeVector)QueryHashtable.mainDBHashtable.get(entitytype);
        NodeVector pnv = (NodeVector)QueryHashtable.mainDBHashtable.get(parent);
        Vector entitytypeDBTable = (Vector)env.elementAt(0);
        Vector parentDBTable = (Vector)pnv.elementAt(0);

        // create a vector containing all fields for the parent entity-type
        Vector allFieldsInParent = new Vector();
        Enumeration parentDBTableEnum = parentDBTable.elements();
        while (parentDBTableEnum.hasMoreElements())
        {
          Vector rowVector = (Vector)parentDBTableEnum.nextElement();
          String field = rowVector.get(0).toString();
          allFieldsInParent.addElement(field);
        }

        // export all fields that are not contained in the parent entity-type
        Enumeration entitytypeDBTableEnum = entitytypeDBTable.elements();
        while (entitytypeDBTableEnum.hasMoreElements())
        {
          Vector rowVector = (Vector)entitytypeDBTableEnum.nextElement();
          String field = rowVector.get(0).toString();
          String filler = rowVector.get(1).toString();
          String setValued = rowVector.get(2).toString();
          String setfiller = new String("nil");

          StringBuffer interest = new StringBuffer();
          StringBuffer importance = new StringBuffer();
          StringBuffer assimilation = new StringBuffer();
          StringBuffer assimilationRate = new StringBuffer();
          if (!allFieldsInParent.contains(field))
          {
            Enumeration allUserTypesVectorEnum2 = allUserTypesVector.elements();
            while (allUserTypesVectorEnum2.hasMoreElements())
            {
              String usertype = allUserTypesVectorEnum2.nextElement().toString();
              Vector userModelValuesVector = QueryUsersHashtable.getUserModelValuesVector(field, entitytype, usertype);

              interest.append("(" + usertype + " " + userModelValuesVector.get(0).toString() + ") ");
              importance.append("(" + usertype + " " + userModelValuesVector.get(1).toString() + ") ");
              if (userModelValuesVector.get(2).toString().equalsIgnoreCase("0"))
              {
                assimilation.append("(" + usertype + " 1) ");
                assimilationRate.append("(" + usertype + " 1) ");
              }
              else
              {
                assimilation.append("(" + usertype + " 0) ");

                if (userModelValuesVector.get(2).toString().equalsIgnoreCase("1"))
                {
                	assimilationRate.append("(" + usertype + " 1) ");
                }
                
                else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("2"))
                {
                	assimilationRate.append("(" + usertype + " 0.5) ");
                }
                
                else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("3"))
                {
                	assimilationRate.append("(" + usertype + " 0.33) ");
                }
              }
						}
					}
					
          if (setValued.equalsIgnoreCase("true"))
          {
              setfiller = "t";
          }
          
          if (!allFieldsInParent.contains(field))
          {
	          p.println(emptyspaceFile);
	          p.println("(defpredicate " + field);
	          p.println("   :arg1 " + entitytype);
	          if (
	               (!filler.equalsIgnoreCase("Date")) &&
	               (!filler.equalsIgnoreCase("Dimension")) &&
	               (!filler.equalsIgnoreCase("Number")) &&
	               (!filler.equalsIgnoreCase("String"))
	             )
	          {
	          	p.println("   :arg2 " + filler);
	          }
	          p.println("   :set-filler " + setfiller);
	
	          p.println("   :interest ( " + interest.toString() + ")");
	          p.println("   :importance ( " + importance.toString() + ")");
	          p.println("   :assimilation ( " + assimilation.toString() + ")");
	          p.println("   :assim-rate ( " + assimilationRate.toString() + ")");
	          p.println(defclose);
					}
				}
			}
      p.flush();
      p.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	} // createPredicatesGram


  /** Create the instances.gram and msgcat.english, msgcat.italian, msgcat.greek,  */
  public static void createInstancesGramAndMsgcat() 
  {
    String instancesFile = domPop.toString() + "/instances.gram";
    String msgcatEnglishFile = english.toString() + "/msgcat.english";
    String msgcatItalianFile = italian.toString() + "/msgcat.italian";
    String msgcatGreekFile = greek.toString() + "/msgcat.greek";

    try
    {
      //FileOutputStream output = new FileOutputStream(instancesFile);
      //PrintStream p = new PrintStream(output);
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(instancesFile), "ISO8859_1");
      PrintWriter p = new PrintWriter(output);

      //FileOutputStream output1 = new FileOutputStream(msgcatEnglishFile);
      //PrintStream p1 = new PrintStream(output1);
      OutputStreamWriter output1 = new OutputStreamWriter(new FileOutputStream(msgcatEnglishFile), "ISO8859_1");
      PrintWriter p1 = new PrintWriter(output1);

      //FileOutputStream output2 = new FileOutputStream(msgcatItalianFile);
      //PrintStream p2 = new PrintStream(output2);
      OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(msgcatItalianFile), "ISO8859_3");
      PrintWriter p2 = new PrintWriter(output2);

      //FileOutputStream output3 = new FileOutputStream(msgcatGreekFile);
      //PrintStream p3 = new PrintStream(output3);
      OutputStreamWriter output3 = new OutputStreamWriter(new FileOutputStream(msgcatGreekFile), "ISO8859_7");
      PrintWriter p3 = new PrintWriter(output3);

      p.println(inpackageFile);
      p.println(emptyspaceFile);

      // instances.gram
      Hashtable allEntityTypesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
      allEntityTypesHashtable.remove("Data Base");
      allEntityTypesHashtable.remove("Basic-entity-types");

      //
      // ;;; OBJECT STRUCTURES
      //
      p.println(";;; OBJECT STRUCTURES");
      p.println(emptyspaceFile);

			// create a vector containing all fields for the "Basic-entity-types"
			Vector allFieldsInBasicEntityTypesEntryVector = new Vector();
			NodeVector basicEntityTypesEntry = (NodeVector)QueryHashtable.mainDBHashtable.get("Basic-entity-types");
			Vector basicEntityTypesEntryDBTable = (Vector)basicEntityTypesEntry.getDatabaseTableVector();
			Enumeration basicEntityTypesEntryDBTableEnum = basicEntityTypesEntryDBTable.elements();
			while (basicEntityTypesEntryDBTableEnum.hasMoreElements())
			{
			  Vector basicEntityTypesEntryDBTableRowVector = (Vector)basicEntityTypesEntryDBTableEnum.nextElement();
			  String basicEntityTypesEntryDBTableRowVectorField = basicEntityTypesEntryDBTableRowVector.get(0).toString();
			  allFieldsInBasicEntityTypesEntryVector.addElement(basicEntityTypesEntryDBTableRowVectorField);
			}

      Vector allBasicTypesVector = QueryHashtable.getChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
      Enumeration allBasicTypesVectorEnum = allBasicTypesVector.elements();
      while (allBasicTypesVectorEnum.hasMoreElements())
      {
				String basicEntityType = allBasicTypesVectorEnum.nextElement().toString();
				
				// export to file (part 1)
				p.println(emptyspaceFile);
				p.println("(defobject-structure " + basicEntityType);

        NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(basicEntityType);
        Vector dbTable = (Vector)nv.getDatabaseTableVector();
        Enumeration dbTableEnum = dbTable.elements();
        while (dbTableEnum.hasMoreElements())
        {
          Vector rowVector = (Vector)dbTableEnum.nextElement();
          String field = rowVector.get(0).toString();

          if (!allFieldsInBasicEntityTypesEntryVector.contains(field))
          {
            String filler = rowVector.get(1).toString();
            String fillerToExport = new String();
            if (
                (filler.equalsIgnoreCase("Date")) ||
                (filler.equalsIgnoreCase("Dimension")) ||
                (filler.equalsIgnoreCase("Number")) ||
                (filler.equalsIgnoreCase("String"))
               )
            {
            	fillerToExport = ":" + filler.toLowerCase();
						}
            else
            {
            	fillerToExport = ":entity-id";
            }

            // export to file (part 2)
            p.println("   :" + field + " " + fillerToExport);
					}	
				}
				// check all children of the basic-entity-type to export their fields under the basic-entity-type
				Vector childrenEntityTypes = QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable(basicEntityType, "Entity type");
				
				Enumeration childrenEntityTypesEnum = childrenEntityTypes.elements();
				while (childrenEntityTypesEnum.hasMoreElements())
				{
				  String childEntityType = childrenEntityTypesEnum.nextElement().toString();
				  String parentEntityType = allEntityTypesHashtable.get(childEntityType).toString();
				  NodeVector env = (NodeVector)QueryHashtable.mainDBHashtable.get(childEntityType);
				  NodeVector pnv = (NodeVector)QueryHashtable.mainDBHashtable.get(parentEntityType);
				  Vector entitytypeDBTable = env.getDatabaseTableVector();
				  Vector parentDBTable = pnv.getDatabaseTableVector();
				
				  // create a vector containing all fields for the parent entity-type
				  Vector allFieldsInParent = new Vector();
				  Enumeration parentDBTableEnum = parentDBTable.elements();
				  while (parentDBTableEnum.hasMoreElements())
				  {
			      Vector rowVector = (Vector)parentDBTableEnum.nextElement();
			      String field = rowVector.get(0).toString();
			      allFieldsInParent.addElement(field);
				  }

          // export all fields that are not contained in the parent entity-type
          Enumeration entitytypeDBTableEnum = entitytypeDBTable.elements();
          while (entitytypeDBTableEnum.hasMoreElements())
          {
            Vector rowVector = (Vector)entitytypeDBTableEnum.nextElement();
            String field = rowVector.get(0).toString();

            if (!allFieldsInParent.contains(field))
            {
              String filler = rowVector.get(1).toString();
              String fillerToExport = new String();
              if (
                  (filler.equalsIgnoreCase("Date")) ||
                  (filler.equalsIgnoreCase("Dimension")) ||
                  (filler.equalsIgnoreCase("Number")) ||
                  (filler.equalsIgnoreCase("String"))
                 )
              {
								fillerToExport = ":" + filler.toLowerCase();
              }
              else
              {
              	fillerToExport = ":entity-id";
              }

              // export to file (part 3)
              p.println("   :" + field + " " + fillerToExport);
						}
					}
				}
	      // export to file (part 3)
	      p.println(defclose);
			}

	    //
	    // ;;; EXHIBITS
	    //
	    p.println(emptyspaceFile);
	    p.println(";;; OBJECTS");
	
	    // Enumerating all entities
	    Hashtable allEntitiesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
	    Enumeration allEntitiesHashtableEnum = allEntitiesHashtable.keys();
	    while (allEntitiesHashtableEnum.hasMoreElements()) // while 1
	    {
        String entity = allEntitiesHashtableEnum.nextElement().toString();
        //
        p.println(emptyspaceFile);
        p.println("(defobject " + entity);
        p.println("   :class " + findClassAndSubclassOfEntity(entity, "class"));
        p.println("   :subclass " + findClassAndSubclassOfEntity(entity, "subclass"));
        if (entity.startsWith("Generic-"))
        {
        	p.println("   :generic-p t");
        }

        NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(entity);
        Vector independentFieldsVector = nv.getIndependentFieldsVector();
        Vector englishFieldsVector = nv.getEnglishFieldsVector();
        Vector italianFieldsVector = nv.getItalianFieldsVector();
        Vector greekFieldsVector = nv.getGreekFieldsVector();

        // adding the fields from independent table
        Enumeration independentFieldsVectorEnum = independentFieldsVector.elements();
        while (independentFieldsVectorEnum.hasMoreElements()) // while 2
        {
          Vector row = (Vector)independentFieldsVectorEnum.nextElement();
          String field = row.get(0).toString();
          String filler = row.get(1).toString();

          if ( // if 1
              (!field.equalsIgnoreCase("entity-id")) &&
              (!field.equalsIgnoreCase("type")) &&
              (!field.equalsIgnoreCase("images"))
             )
          {
	          if ( // if 2
	              (!filler.equalsIgnoreCase("")) &&
	              (!filler.startsWith("Select "))
	             )
	          {
              if (findFillerTypeOfEntityField(entity, field).equalsIgnoreCase("Date")) // if 3
              {
                // this is Date
                p.println("   :" + field + " " + createExportDate(filler));
              }
              
              else if (findFillerTypeOfEntityField(entity, field).equalsIgnoreCase("Dimension"))
              {
                // this is Dimension
                p.println("   :" + field + " ((" + field.substring(field.lastIndexOf("-")+1) + " " + filler + "))");
              }
              
              else if (filler.indexOf(" ") > 0)
              {
                // this is a set-valued filler
                p.println("   :" + field + " (" + filler + ")");
              }
              
              else
              {
              	p.println("   :" + field + " " + filler);
              } // if 3
						} // if 2
					} // if 1
				} // while 2


        Vector stringFieldsEnteredInInstancesGram = new Vector();

        // adding the fields from English table
        Enumeration englishFieldsVectorEnum = englishFieldsVector.elements();
        while (englishFieldsVectorEnum.hasMoreElements()) // while 3
        {
          Vector row = (Vector)englishFieldsVectorEnum.nextElement();
          String field = row.get(0).toString();
          String filler = row.get(1).toString();
          if (!filler.equalsIgnoreCase("")) // if 1
          {
            if (field.equalsIgnoreCase("gender")) // if 2
            {
              if (filler.equalsIgnoreCase("masculine"))
              {
              	p.println("   :gender male");
              }
              
              else if (filler.equalsIgnoreCase("feminine"))
              {
              	p.println("   :gender female");
              }
            }
            
            else if (field.equalsIgnoreCase("number"))
            {
            	p.println("   :number " + filler);
            }
            
            else if (field.equalsIgnoreCase("title") ||
                     field.equalsIgnoreCase("notes"))
            {
            	// do nothing!
            }
            else
            {
              p.println("   :" + field + " \"#" + entity + "-" + field + "\"");
              stringFieldsEnteredInInstancesGram.add(field);
              p1.println("#" + entity + "-" + field + " " + filler);
            } // if 2
    			} // if 1
				}// while 3

	      // adding the fields from Italian table
	      Vector checkItalianNameAndShortName = new Vector();
	      String nameLexicon = new String();
	      String nameGrammaticalGenderLexicon = new String();
	      String shortnameLexicon = new String();
	      String shortnameGrammaticalGenderLexicon = new String();
	      String numberLexicon = new String();
	
	      Enumeration italianFieldsVectorEnum = italianFieldsVector.elements();
	      while (italianFieldsVectorEnum.hasMoreElements()) // while 4
	      {
          Vector row = (Vector)italianFieldsVectorEnum.nextElement();
          String field = row.get(0).toString();
          String filler = row.get(1).toString();
          if (!filler.equalsIgnoreCase("")) // if 1
          {
            if (field.equalsIgnoreCase("name"))
            {
              if (!stringFieldsEnteredInInstancesGram.contains(field))
              {
                p.println("   :" + field + " #" + entity + "-" + field);
                stringFieldsEnteredInInstancesGram.add(field);
              }
              checkItalianNameAndShortName.add(field);
              nameLexicon = filler;
              p2.println("#" + entity + "-" + "name" + " " + filler);
            }
            else if (field.equalsIgnoreCase("grammatical gender of name"))
            {
              if (checkItalianNameAndShortName.contains("name"))
              {
              	nameGrammaticalGenderLexicon = filler;
              }
            }
            else if (field.equalsIgnoreCase("shortname"))
            {
              if (!stringFieldsEnteredInInstancesGram.contains(field))
              {
                p.println("   :" + field + " \"#" + entity + "-" + field + "\"");
                stringFieldsEnteredInInstancesGram.add(field);
              }
              checkItalianNameAndShortName.add(field);
              shortnameLexicon = filler;
              p2.println("#" + entity + "-" + "name" + " " + filler);
            }
            else if (field.equalsIgnoreCase("grammatical gender of shortname"))
            {
              if (checkItalianNameAndShortName.contains("shortname"))
              {
              	shortnameGrammaticalGenderLexicon = filler;
              }
            }
            else if (field.equalsIgnoreCase("number")) // if 2
            {
            	numberLexicon = filler;
            }
            else if (field.equalsIgnoreCase("title") ||
                     field.equalsIgnoreCase("notes"))
            {
            	// do nothing!
            }
            else
            {
              if (!stringFieldsEnteredInInstancesGram.contains(field))
              {
	              p.println("   :" + field + " \"#" + entity + "-" + field + "\"");
	              stringFieldsEnteredInInstancesGram.add(field);
              }
              p2.println("#" + entity + "-" + field + " " + filler);
            } // if 2
					} // if 1
				}// while 4
        if (checkItalianNameAndShortName.contains("name"))
        {
        	appendProperNounToItalianLexicon("name", entity, nameLexicon, nameGrammaticalGenderLexicon, numberLexicon);
        }
        
        if (checkItalianNameAndShortName.contains("shortname"))
        {
        	appendProperNounToItalianLexicon("name", entity, shortnameLexicon, shortnameGrammaticalGenderLexicon, numberLexicon);
        }

        // adding the fields from Greek table
        Vector checkGreekNameAndShortName = new Vector();
        String namenomLexicon = new String();
        String namegenLexicon = new String();
        String nameaccLexicon = new String();
        String nameGrammaticalGenderLexicon2 = new String();
        String shortnamenomLexicon = new String();
        String shortnamegenLexicon = new String();
        String shortnameaccLexicon = new String();
        String shortnameGrammaticalGenderLexicon2 = new String();
        String numberLexicon2 = new String();

        Enumeration greekFieldsVectorEnum = greekFieldsVector.elements();
        while (greekFieldsVectorEnum.hasMoreElements()) // while 5
        {
          Vector row = (Vector)greekFieldsVectorEnum.nextElement();
          String field = row.get(0).toString();
          String filler = row.get(1).toString();
          if (!filler.equalsIgnoreCase("")) // if 1
          {
            if (field.equalsIgnoreCase("name (nominative)"))
            {
              if (!stringFieldsEnteredInInstancesGram.contains("name"))
              {
                p.println("   :" + "name" + " \"#" + entity + "-" + "name" + "\"");
                stringFieldsEnteredInInstancesGram.add(field);
              }
              checkGreekNameAndShortName.add(field);
              namenomLexicon = filler;
              p3.println("#" + entity + "-" + "name" + " " + filler);
            }
            
            else if (field.equalsIgnoreCase("name (genitive)"))
            {
              if (checkGreekNameAndShortName.contains("name (nominative)"))
              {
              	namegenLexicon = filler;
              }
            }
            
            else if (field.equalsIgnoreCase("name (accusative)"))
            {
              if (checkGreekNameAndShortName.contains("name (nominative)"))
              {
              	nameaccLexicon = filler;
              }
            }
            
            else if (field.equalsIgnoreCase("grammatical gender of name"))
            {
              if (checkGreekNameAndShortName.contains("name (nominative)"))
              {
              	nameGrammaticalGenderLexicon2 = filler;
              }
            }
            
            else if (field.equalsIgnoreCase("shortname (nominative)"))
            {
              if (!stringFieldsEnteredInInstancesGram.contains("shortname"))
              {
                p.println("   :" + "shortname" + " \"#" + entity + "-" + "shortname" + "\"");
                stringFieldsEnteredInInstancesGram.add(field);
              }
              checkGreekNameAndShortName.add(field);
              shortnamenomLexicon = filler;
              p3.println("#" + entity + "-" + "name" + " " + filler);
            }
            
            else if (field.equalsIgnoreCase("shortname (genitive)"))
            {
              if (checkGreekNameAndShortName.contains("shortname (nominative)"))
              {
              	shortnamegenLexicon = filler;
              }
            }
            
            else if (field.equalsIgnoreCase("shortname (accusative)"))
            {
              if (checkGreekNameAndShortName.contains("shortname (nominative)"))
              {
              	shortnameaccLexicon = filler;
              }
            }
            
            else if (field.equalsIgnoreCase("grammatical gender of shortname"))
            {
              if (checkItalianNameAndShortName.contains("shortname"))
              {
              	shortnameGrammaticalGenderLexicon2 = filler;
              }
            }
            
            else if (field.equalsIgnoreCase("number")) // if 2
            {
            	numberLexicon2 = filler;
            }
            
            else if (field.equalsIgnoreCase("title") ||
                     field.equalsIgnoreCase("notes"))
            {
            	// do nothing!
            }
            else
            {
              if (!stringFieldsEnteredInInstancesGram.contains(field))
              {
                p.println("   :" + field + " \"#" + entity + "-" + field + "\"");
                stringFieldsEnteredInInstancesGram.add(field);
              }
              p3.println("#" + entity + "-" + field + " " + filler);
            } // if 2
					} // if 1
				}// while 5
        if (checkGreekNameAndShortName.contains("name (nominative)"))
        {
        	appendProperNounToGreekLexicon("name", entity, namenomLexicon, namegenLexicon, nameaccLexicon, nameGrammaticalGenderLexicon2, numberLexicon2);
        }
        
        if (checkGreekNameAndShortName.contains("shortname (nominative)"))
        {
        	appendProperNounToGreekLexicon("shortname", entity, shortnamenomLexicon, shortnamegenLexicon, shortnameaccLexicon, shortnameGrammaticalGenderLexicon2, numberLexicon2);
        }
				p.println(defclose);
			} // while 1

      p.flush();
      p.close();
      p1.flush();
      p1.close();
      p2.flush();
      p2.close();
      p3.flush();
      p3.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	} // createInstancesGramAndMsgcat



  /** Create the stories.gram  */
  public static void createStoriesGram() 
  {
    String storiesFile = domPop.toString() + "/stories.gram";
    String msgcatEnglishFile = english.toString() + "/msgcat.english";
    String msgcatItalianFile = italian.toString() + "/msgcat.italian";
    String msgcatGreekFile = greek.toString() + "/msgcat.greek";

    try
    {
      //FileOutputStream output = new FileOutputStream(storiesFile);
      //PrintStream p = new PrintStream(output);
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(storiesFile), "ISO8859_1");
      PrintWriter p = new PrintWriter(output);

      //FileOutputStream output1 = new FileOutputStream(msgcatEnglishFile, true);
      //PrintStream p1 = new PrintStream(output1);
      OutputStreamWriter output1 = new OutputStreamWriter(new FileOutputStream(msgcatEnglishFile, true), "ISO8859_1");
      PrintWriter p1 = new PrintWriter(output1);

      //FileOutputStream output2 = new FileOutputStream(msgcatItalianFile, true);
      //PrintStream p2 = new PrintStream(output2);
      OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(msgcatItalianFile, true), "ISO8859_1");
      PrintWriter p2 = new PrintWriter(output2);

      //FileOutputStream output3 = new FileOutputStream(msgcatGreekFile, true);
      //PrintStream p3 = new PrintStream(output3);
      OutputStreamWriter output3 = new OutputStreamWriter(new FileOutputStream(msgcatGreekFile, true), "ISO8859_1");
      PrintWriter p3 = new PrintWriter(output3);

      p.println(inpackageFile);
      p.println(emptyspaceFile);

      Vector allUserTypesVector = (Vector)QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();

      Hashtable allEntityTypesHashtable = (Hashtable)QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
      allEntityTypesHashtable.remove("Data Base");
      allEntityTypesHashtable.remove("Basic-entity-types");
      Enumeration allEntityTypesHashtableEnum = allEntityTypesHashtable.keys();
      while (allEntityTypesHashtableEnum.hasMoreElements())
      {
        String entityType = allEntityTypesHashtableEnum.nextElement().toString();
        NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(entityType);
        Vector storiesVector = nv.getStoriesVector();
        Hashtable englishStories = (Hashtable)storiesVector.get(0);
        Hashtable italianStories = (Hashtable)storiesVector.get(1);
        Hashtable greekStories = (Hashtable)storiesVector.get(2);

        for (Enumeration k = englishStories.keys(), e = englishStories.elements(); k.hasMoreElements(); )
        {
          String story = k.nextElement().toString();
          if (!story.equalsIgnoreCase("New-story"))
          {
            String storyTextEnglish = englishStories.get(story).toString();
            String storyTextItalian = italianStories.get(story).toString();
            String storyTextGreek = greekStories.get(story).toString();

            StringBuffer interest = new StringBuffer();
            StringBuffer importance = new StringBuffer();
            StringBuffer assimilation = new StringBuffer();
            StringBuffer assimilationRate = new StringBuffer();
            Enumeration allUserTypesVectorEnum3 = allUserTypesVector.elements();
            while (allUserTypesVectorEnum3.hasMoreElements())
            {
              String usertype = allUserTypesVectorEnum3.nextElement().toString();
              Vector userModelValuesVector = QueryUsersHashtable.getUserModelStoryValuesVector(story, entityType, usertype);

              interest.append("(" + usertype + " " + userModelValuesVector.get(0).toString() + ") ");
              importance.append("(" + usertype + " " + userModelValuesVector.get(1).toString() + ") ");
              if (userModelValuesVector.get(2).toString().equalsIgnoreCase("0"))
              {
                assimilation.append("(" + usertype + " 1) ");
                assimilationRate.append("(" + usertype + " 1) ");
              }
              else
              {
                assimilation.append("(" + usertype + " 0) ");

                if (userModelValuesVector.get(2).toString().equalsIgnoreCase("1"))
                {
                	assimilationRate.append("(" + usertype + " 1) ");
                }
                else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("2"))
                {
                	assimilationRate.append("(" + usertype + " 0.5) ");
                }
                else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("3"))
                {
                	assimilationRate.append("(" + usertype + " 0.33) ");
                }
							}
						}
            p.println(emptyspaceFile);
            p.println("(defstory");
            p.println("   :id " + story);
            p.println("   :type " + entityType);
            p.println("   :interest ( " + interest.toString() + ")");
            p.println("   :importance ( " + importance.toString() + ")");
            p.println("   :assimilation ( " + assimilation.toString() + ")");
            p.println("   :assim-rate ( " + assimilationRate.toString() + ")");
            p.println("   :text " + "\"#story-" + entityType + "-" + story + "\"");
            p.println(defclose);

            p1.println("#story-" + entityType + "-" + story + " " + storyTextEnglish);
            p2.println("#story-" + entityType + "-" + story + " " + storyTextItalian);
            p3.println("#story-" + entityType + "-" + story + " " + storyTextGreek);
					}//if
				}//for
			}//while

      Hashtable allEntitiesHashtable = (Hashtable)QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
      Enumeration allEntitiesHashtableEnum = allEntitiesHashtable.keys();
      while (allEntitiesHashtableEnum.hasMoreElements())
      {
        String entity = allEntitiesHashtableEnum.nextElement().toString();
        NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(entity);
        Vector storiesVector = nv.getStoriesVector();
        Hashtable englishStories = (Hashtable)storiesVector.get(0);
        Hashtable italianStories = (Hashtable)storiesVector.get(1);
        Hashtable greekStories = (Hashtable)storiesVector.get(2);

        for (Enumeration k = englishStories.keys(), e = englishStories.elements(); k.hasMoreElements(); )
        {
          String story = k.nextElement().toString();
          if (!story.equalsIgnoreCase("New-story"))
          {
            String storyTextEnglish = englishStories.get(story).toString();
            String storyTextItalian = italianStories.get(story).toString();
            String storyTextGreek = greekStories.get(story).toString();

            StringBuffer interest = new StringBuffer();
            StringBuffer importance = new StringBuffer();
            StringBuffer assimilation = new StringBuffer();
            StringBuffer assimilationRate = new StringBuffer();
            Enumeration allUserTypesVectorEnum4 = allUserTypesVector.elements();
            while (allUserTypesVectorEnum4.hasMoreElements())
            {
              String usertype = allUserTypesVectorEnum4.nextElement().toString();
              Vector userModelValuesVector = QueryUsersHashtable.getUserModelStoryValuesVector(story, entity, usertype);

              interest.append("(" + usertype + " " + userModelValuesVector.get(0).toString() + ") ");
              importance.append("(" + usertype + " " + userModelValuesVector.get(1).toString() + ") ");
              if (userModelValuesVector.get(2).toString().equalsIgnoreCase("0"))
              {
                assimilation.append("(" + usertype + " 1) ");
                assimilationRate.append("(" + usertype + " 1) ");
              }
              else
              {
                assimilation.append("(" + usertype + " 0) ");

                if (userModelValuesVector.get(2).toString().equalsIgnoreCase("1"))
                {
                	assimilationRate.append("(" + usertype + " 1) ");
                }
                else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("2"))
                {
                	assimilationRate.append("(" + usertype + " 0.5) ");
                }
                else if (userModelValuesVector.get(2).toString().equalsIgnoreCase("3"))
                {
                	assimilationRate.append("(" + usertype + " 0.33) ");
                }
							}
						}
            p.println(emptyspaceFile);
            p.println("(defstory");
            p.println("   :id " + story);
            p.println("   :instance " + entity);
            p.println("   :interest ( " + interest.toString() + ")");
            p.println("   :importance ( " + importance.toString() + ")");
            p.println("   :assimilation ( " + assimilation.toString() + ")");
            p.println("   :assim-rate ( " + assimilationRate.toString() + ")");
            p.println("   :text " + "\"#story-" + entity + "-" + story + "\"");
            p.println(defclose);

            p1.println("#story-" + entity + "-" + story + " " + storyTextEnglish);
            p2.println("#story-" + entity + "-" + story + " " + storyTextItalian);
            p3.println("#story-" + entity + "-" + story + " " + storyTextGreek);
					}
				}
			}
      p.flush();
      p.close();
      p1.flush();
      p1.close();
      p2.flush();
      p2.close();
      p3.flush();
      p3.close();
		}
		catch (Exception ex)
		{
			System.out.println("|||| Exception ||||");
			ex.printStackTrace();
		}
	} // createStoriesGram()


  /** Create the expressions.gram  */
  public static void createExpressionsGram() 
  {
    String expressionsFile = domSpec.toString() + "/expressions.gram";
    String expressionsItalianFile = italian.toString() + "/italian-expression.gram";
    String expressionsGreekFile = greek.toString() + "/greek-expression.gram";

    try
    {
	    //FileOutputStream output1 = new FileOutputStream(expressionsFile);
	    //FileOutputStream output2 = new FileOutputStream(expressionsItalianFile);
	    //FileOutputStream output3 = new FileOutputStream(expressionsGreekFile);
	    //PrintStream p1 = new PrintStream(output1);
	    //PrintStream p2 = new PrintStream(output2);
	    //PrintStream p3 = new PrintStream(output3);
	
	    OutputStreamWriter output1 = new OutputStreamWriter(new FileOutputStream(expressionsFile), "ISO8859_1");
	    OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(expressionsItalianFile), "ISO8859_3");
	    OutputStreamWriter output3 = new OutputStreamWriter(new FileOutputStream(expressionsGreekFile), "ISO8859_7");
	
	    PrintWriter p1 = new PrintWriter(output1);
	    PrintWriter p2 = new PrintWriter(output2);
	    PrintWriter p3 = new PrintWriter(output3);
	
	    p1.println(inpackageFile);
	    p1.println(emptyspaceFile);
	    p2.println(inpackageFile);
	    p2.println(emptyspaceFile);
	    p3.println(inpackageFile);
	    p3.println(emptyspaceFile);

      p1.flush();
      p1.close();
      p2.flush();
      p2.close();
      p3.flush();
      p3.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}

    Hashtable allEntityTypesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
    allEntityTypesHashtable.remove("Data Base");
    allEntityTypesHashtable.remove("Basic-entity-types");

    for (Enumeration k = allEntityTypesHashtable.keys(), e = allEntityTypesHashtable.elements(); k.hasMoreElements(); )
    {
      String entitytype = k.nextElement().toString();
      String parent = e.nextElement().toString();
      NodeVector env = (NodeVector)QueryHashtable.mainDBHashtable.get(entitytype);
      NodeVector pnv = (NodeVector)QueryHashtable.mainDBHashtable.get(parent);
      Vector entitytypeDBTable = env.getDatabaseTableVector();
      Vector parentDBTable = pnv.getDatabaseTableVector();
      Hashtable microHashtable = env.getMicroPlanningValues();
			//System.out.println("()---- " + microHashtable.toString());//////////////////////////////////////////
      Vector templateVector = env.getTemplateVector();

      // create a vector containing all fields for the parent entity-type
      Vector allFieldsInParent = new Vector();
      Enumeration parentDBTableEnum = parentDBTable.elements();
      while (parentDBTableEnum.hasMoreElements())
      {
        Vector rowVector = (Vector)parentDBTableEnum.nextElement();
        String field = rowVector.get(0).toString();
        allFieldsInParent.addElement(field);
      }

      // export all fields that are not contained in the parent entity-type
      Enumeration entitytypeDBTableEnum = entitytypeDBTable.elements();
      while (entitytypeDBTableEnum.hasMoreElements())
      {
        Vector rowVector = (Vector)entitytypeDBTableEnum.nextElement();
        String field = rowVector.get(0).toString();

        if (!allFieldsInParent.contains(field))
        {
          //System.out.println("()---- " + entitytype);
          //System.out.println("()---- " + field);
          //String keyEnglish = field + ":Verb:English";
          //String keyItalian = field + ":Verb:Italian";
          //String keyGreek = field + ":Verb:Greek";
					/*
          if (!microHashtable.containsKey(keyEnglish))
          {
              QueryHashtable.updateHashtable(entitytype, field, "Verb", "English", "Choose a verb identifier");
          }
          if (!microHashtable.containsKey(keyItalian))
          {
              QueryHashtable.updateHashtable(entitytype, field, "Verb", "Italian", "Choose a verb identifier");
          }
          if (!microHashtable.containsKey(keyGreek))
          {
              QueryHashtable.updateHashtable(entitytype, field, "Verb", "Greek", "Choose a verb identifier");
          }
					*/
					//System.out.println("()---- " + entitytype);
					/* if (!microHashtable.get(keyEnglish).toString().equalsIgnoreCase("Choose a verb identifier"))
          {
              createMicroplanningExpressionExport(microHashtable, field, "English");
          }
          else
          {
              ExportUtilsIlex.createTemplateExpressionExport(templateVector, field, "English");
          }

          if (!microHashtable.get(keyItalian).toString().equalsIgnoreCase("Choose a verb identifier"))
          {
              createMicroplanningExpressionExport(microHashtable, field, "Italian");
          }
          else
          {
              ExportUtilsIlex.createTemplateExpressionExport(templateVector, field, "Italian");
          }

          if (!microHashtable.get(keyGreek).toString().equalsIgnoreCase("Choose a verb identifier"))
          {
              createMicroplanningExpressionExport(microHashtable, field, "Greek");
          }
          else
          {
              ExportUtilsIlex.createTemplateExpressionExport(templateVector, field, "Greek");
          }
					*/
					/*
          QueryHashtable.updateHashtable(entitytype, field, "SELECTION", "English", "Clause");
          QueryHashtable.updateHashtable(entitytype, field, "SELECTION", "Italian", "Clause");
          QueryHashtable.updateHashtable(entitytype, field, "SELECTION", "Greek", "Clause");
					*/

          if (microHashtable.get("1:" + field + ":SELECTION:English").toString().equalsIgnoreCase("Clause"))
          {
          	createMicroplanningExpressionExport(microHashtable, field, "English");
          }
          
          else if (microHashtable.get("1:" + field + ":SELECTION:English").toString().equalsIgnoreCase("Template"))
          {
          	ExportUtilsIlex.createTemplateExpressionExport(templateVector, field, "English");
          }

          if (microHashtable.get("1:" + field + ":SELECTION:Italian").toString().equalsIgnoreCase("Clause"))
          {
          	createMicroplanningExpressionExport(microHashtable, field, "Italian");
          }
          
          else if (microHashtable.get("1:" + field + ":SELECTION:Italian").toString().equalsIgnoreCase("Template"))
          {
          	ExportUtilsIlex.createTemplateExpressionExport(templateVector, field, "Italian");
          }

          if (microHashtable.get("1:" + field + ":SELECTION:Greek").toString().equalsIgnoreCase("Clause"))
          {
          	createMicroplanningExpressionExport(microHashtable, field, "Greek");
          }
          
          else if (microHashtable.get("1:" + field + ":SELECTION:Greek").toString().equalsIgnoreCase("Template"))
          {
          	ExportUtilsIlex.createTemplateExpressionExport(templateVector, field, "Greek");
          }
				}
			}
		}
	} // createExpressionsGram()



  /** Create the load-domain.txt  */
  public static void createLoadDomainTxt() 
  {
    String loadDomainTxt = mpirodomain.toString() + "/load-domain.txt";

    try
    {
      //FileOutputStream output = new FileOutputStream(loadDomainTxt);
      //PrintStream p = new PrintStream(output);
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(loadDomainTxt), "ISO8859_1");
      PrintWriter p = new PrintWriter(output);

      p.println(emptyspaceFile);
      p.println(";;; Information for load-domain.lisp");

      Vector allUserTypesVector = (Vector)QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();

      // a StringBuffer that will hold all entities that have a
      // field "type" with user-modelling value for "repetitions" == 1
      Vector entitiesVector = new Vector();

      // get a vector containing all entities
      Vector allEntitiesVector = (Vector)QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity+Generic");

      Enumeration allEntitiesVectorEnum = allEntitiesVector.elements();
      while (allEntitiesVectorEnum.hasMoreElements())
      {
        String entity = allEntitiesVectorEnum.nextElement().toString();
        String repetitions = new String("");

        Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
        while (allUserTypesVectorEnum.hasMoreElements())
        {
          String usertype = allUserTypesVectorEnum.nextElement().toString();
          Vector userModelValuesVector = QueryUsersHashtable.getUserModelValuesVector("type", entity, usertype);
          if (userModelValuesVector.get(2).toString().equalsIgnoreCase("0"))
          {
          	repetitions = "0";
          }
        }
        if (repetitions.equalsIgnoreCase("0"))
        {
        	entitiesVector.addElement(entity);
        }
			}

      p.println(emptyspaceFile);
      p.println("(setf *exception-assimilated-facts*");
      p.println("    (collect-unless-nil (entity '(");

      Enumeration entitiesVectorEnum = entitiesVector.elements();
      while (entitiesVectorEnum.hasMoreElements())
      {
        String entity = entitiesVectorEnum.nextElement().toString();
        p.println("        " + entity);
      }
      p.println("   ))");
      p.println("   (fetch-fact-with-pred entity 'subclass))");
      p.println(")");

      p.flush();
      p.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	} // createLoadDomainTxt



  /** Create domain-specific alterations  */
  public static void createDomainSpecificAlterations() 
  {
    String msgcatEnglishFile = english.toString() + "/msgcat.english";
    String msgcatItalianFile = italian.toString() + "/msgcat.italian";
    String msgcatGreekFile = greek.toString() + "/msgcat.greek";

    try
    {
      //FileOutputStream output1 = new FileOutputStream(msgcatEnglishFile, true);
      //PrintStream p1 = new PrintStream(output1);
      OutputStreamWriter output1 = new OutputStreamWriter(new FileOutputStream(msgcatEnglishFile, true), "ISO8859_1");
      PrintWriter p1 = new PrintWriter(output1);

      //FileOutputStream output2 = new FileOutputStream(msgcatItalianFile, true);
      //PrintStream p2 = new PrintStream(output2);
      OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(msgcatItalianFile, true), "ISO8859_3");
      PrintWriter p2 = new PrintWriter(output2);

      //FileOutputStream output3 = new FileOutputStream(msgcatGreekFile, true);
      //PrintStream p3 = new PrintStream(output3);
      OutputStreamWriter output3 = new OutputStreamWriter(new FileOutputStream(msgcatGreekFile, true), "ISO8859_7");
      PrintWriter p3 = new PrintWriter(output3);

      p1.println(emptyspaceFile);
      p2.println(emptyspaceFile);
      p3.println(emptyspaceFile);

      p1.println(";;;;; General");
      p1.println("#aypk as you probably know");
      p1.println("#fti For further information,");
      p1.println("#aam as already mentioned");
      p1.println("#other Other ");
      p1.println("#include  include:");
      p1.println("#olu  Original location unknown.");
      p1.println("#mu  Materials unknown.");
      p1.println("#painter Painter:");
      p1.println("#sculptor Scultptor:");
      p1.println("#potter Potter:");
      p1.println("#whamt We have also mentioned that");
      p1.println("#usually usually");
      p1.println("");
      p1.println(";;;;;; Forward pointers");
      p1.println("#exhibits-in exhibits in ");
      p1.println("#exhibits-created-during exhibits created during ");
      p1.println("#exhibits-located-in exhibits located in ");
      p1.println("#exhibits-found-in exhibits found in ");
      p1.println("#exhibits-originated-from exhibits which originated from ");
      p1.println("#exhibits-which-use exhibits which use ");
      p1.println("#exhibits-painted-by exhibits painted by ");
      p1.println("#exhibits-sculptured-by exhibits sculptured by ");
      p1.println("#exhibits-created-by exhibits created by ");
      p1.println("#exhibits-made-of exhibits made of ");

      p2.println(";;;;; General");
      p2.println("#aypk come probabilmente sapete");
      p2.println("#fti Per ulteriori informazioni,");
      p2.println("#aam come gi\u00E0 menzionato");
      p2.println("#other Altri ");
      p2.println("#include  sono:");
      p2.println("#ol Luogo d'origine:");
      p2.println("#olu Luogo d'origine sconosciuto.");
      p2.println("#mo Materiale:");
      p2.println("#mu  Materiali sconosciuti.");
      p2.println("#painter Pittore:");
      p2.println("#scultor Scultore:");
      p2.println("#sculptor Scultore:");
      p2.println("#potter Vasaio:");
      p2.println("#whamt Abbiamo inoltre menzionato che");
      p2.println("#what-we-see-is Ci\u00F2 che vediamo in questa foto \u00E8");
      p2.println("#the-name-of Il nome di");
      p2.println("#means significa");
      p2.println("");
      p2.println(";;;;;; Forward pointers");
      p2.println("#exhibits-in oggetti in ");
      p2.println("#exhibits-created-during oggetti creati durante ");
      p2.println("#exhibits-located-in oggetti che si trovano in ");
      p2.println("#exhibits-found-in oggetti trovati a ");
      p2.println("#exhibits-originated-from oggetti che provengono da ");
      p2.println("#exhibits-which-use oggetti che usano ");
      p2.println("#exhibits-painted-by oggetti dipinti da ");
      p2.println("#exhibits-sculptured-by oggetti scolpiti da ");
      p2.println("#exhibits-created-by oggetti creati da ");
      p2.println("#exhibits-made-of oggetti fatti in ");

      p3.println(";;;;; General");
      p3.println("#fti ��� ������������ �����������,");
      p3.println("#aam ���� ���� ��� ���������");
      p3.println("#other '���� ");
      p3.println("#include  �����:");
      p3.println("#olu ������� ���������� �������.");
      p3.println("#mu  ����� �������.");
      p3.println("#painter ��������:");
      p3.println("#scultor �������:");
      p3.println("#potter �������������:");
      p3.println("#whamt '������ ������ �������� ���");
      p3.println("");
      p3.println(";;;;;; Forward pointers");
      p3.println("#exhibits-in �������� ��� ������� �� ");
      p3.println("#exhibits-created-during �������� ��� �������������� ���� ");
      p3.println("#exhibits-located-in �������� ��� ���������� �� ");
      p3.println("#exhibits-found-in �������� ��� �������� �� ");
      p3.println("#exhibits-originated-from �������� ��� ����������� ��� ");
      p3.println("#exhibits-which-use ������ ��� ������� �� ");
      p3.println("#exhibits-painted-by ������ ��� ��������� ");
      p3.println("#exhibits-sculptured-by ���� ");
      p3.println("#exhibits-created-by �������� ��� �������������� ��� ");
      p3.println("#exhibits-made-of �������� ��� ����� �������� ��� ");

      p1.flush();
      p1.close();
      p2.flush();
      p2.close();
      p3.flush();
      p3.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	} // createDomainSpecificAlterations



  /**
  * Find the filler type of an entity or generic-entity field
  * @param entity the name of entity or generic-entity
  * @param field the name of field
  * @return the name of filler
  */
  static String findFillerTypeOfEntityField(String entity, String field) 
  {
    String returnString = new String();
    Hashtable allEntitiesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
    String parent = allEntitiesHashtable.get(entity).toString();

    NodeVector nv = (NodeVector)QueryHashtable.mainDBHashtable.get(parent);
    Vector dbTableVector = (Vector)nv.getDatabaseTableVector();
    Enumeration dbTableVectorEnum = dbTableVector.elements();
    while (dbTableVectorEnum.hasMoreElements())
    {
      Vector row = (Vector)dbTableVectorEnum.nextElement();
      String parentField = row.get(0).toString();
      String parentFiller = row.get(1).toString();
      if (parentField.equalsIgnoreCase(field))
      {
      	returnString = parentFiller;
      }
    }
    return returnString;
  }


	/**
	* Find the entity-type immediate ancestor (subclass) or
	* the basic-entity-type ancestor (class) of an entity or generic-entity
	* @param entity the name of entity or entity-type
	* @param classOrSubclass "class" or "subclass"
	* @return the name of ancestor basic-entity-type or entity-type
	*/
	static String findClassAndSubclassOfEntity(String entity, String classOrSubclass) 
	{
    String returnString = new String();
    Hashtable allEntitiesHashtable = QueryHashtable.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
    String parent = allEntitiesHashtable.get(entity).toString();

    if (classOrSubclass.equalsIgnoreCase("class"))
    {
      Vector allBasicTypesVector = QueryHashtable.getChildrenVectorFromMainDBHashtable("Basic-entity-types", "Entity type");
      Enumeration allBasicTypesVectorEnum = allBasicTypesVector.elements();
      while (allBasicTypesVectorEnum.hasMoreElements())
      {
        String basicType = allBasicTypesVectorEnum.nextElement().toString();
        Vector basicTypeFullPathChildrenEntitiesVector = QueryHashtable.getFullPathChildrenVectorFromMainDBHashtable(basicType, "Entity+Generic");
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



	/**
	 * create the export date used in "createInstancesGramAndMsgcat()"
	 * @param date the date string
	 * @return the date to export
	 */
	static String createExportDate(String date) 
	{
		StringBuffer returnString = new StringBuffer();
		
		if (date.substring(0, 7).equalsIgnoreCase("between"))
		{
	    String d1 = date.substring(date.indexOf("(")+1 , date.indexOf(")"));
	    String d2 = date.substring(date.lastIndexOf("(")+1 , date.lastIndexOf(")"));
	
	    // put all tokens in vectors
	    StringTokenizer dst1 = new StringTokenizer(d1);
	    Vector dv1 = new Vector();
	    while (dst1.hasMoreTokens()) 
	    {
	      String token = dst1.nextToken();
	      dv1.addElement(token);
	    }
	    StringTokenizer dst2 = new StringTokenizer(d2);
	    Vector dv2 = new Vector();
	    while (dst2.hasMoreTokens()) 
	    {
	      String token = dst2.nextToken();
	      dv2.addElement(token);
	    }
	    // constructing the returnStirng
	    String date1 = normaliseDateVector(dv1);
	    String date2 = normaliseDateVector(dv2);
	
	    returnString.append("(:between (" + date1 + ") (" + date2 + "))");
		}
		else
	  {
	    String d1 = date.substring(date.indexOf("(")+1 , date.indexOf(")"));
	
	    // put all tokens in vectors
	    StringTokenizer dst1 = new StringTokenizer(d1);
	    Vector dv1 = new Vector();
	    while (dst1.hasMoreTokens()) 
	    {
	      String token = dst1.nextToken();
	      dv1.addElement(token);
	    }
	    // constructing the returnStirng
	    String date1 = normaliseDateVector(dv1);
	    returnString.append("(" + date1 + ")");
	  }
	  return returnString.toString();
	}


	/**
	 * Normalise a date vector.
	 * Used in : ExportUtilsIlex.createExportDate(String date)
	 * @param dateVector the date vector
	 * @return the normalised date
	 */
  static String normaliseDateVector(Vector dateVector) 
  {
		StringBuffer returnString = new StringBuffer();
		String circa = new String();
		String number = new String();
		String bcad = new String();
		String timePeriod = new String();
		String modifier = new String();

    // the "circa"
    if (dateVector.firstElement().toString().equalsIgnoreCase("c."))
    {
      circa = "c.";
      number = dateVector.get(1).toString();
      dateVector.removeElementAt(0);
      dateVector.removeElementAt(0);
    }
    else
    {
      number = dateVector.get(0).toString();
      dateVector.removeElementAt(0);
    }

    // the "timePeriod" and its modifier
    timePeriod = dateVector.firstElement().toString();
    dateVector.removeElementAt(0);

    // the "BC/AD"
    bcad = dateVector.lastElement().toString();
    dateVector.removeElementAt(dateVector.size()-1);

    if (!dateVector.isEmpty())
    {
      modifier = dateVector.firstElement().toString();
      dateVector.removeElementAt(0);
    }

    // creating the date string
    if (circa.equalsIgnoreCase("c."))
    {
    	returnString.append("c. (");
    }

    if (timePeriod.equalsIgnoreCase("year"))
    {
    	returnString.append(":" + bcad + " " + number);
    }
    else
    {
      returnString.append(":" + timePeriod + " " + number + " " + bcad);
      if (!modifier.equalsIgnoreCase(""))
      {
      	returnString.append(" :" + modifier);
      }
    }
    if (circa.equalsIgnoreCase("c."))
    {
    	returnString.append(")");
    }
    return returnString.toString();
	}


	/**
	* Append a proper noun entry to lexicon.italian
	* Used in : ExportUtilsIlex.createInstancesGramAndMsgcat()
	* @param nameOrShortName "name" or "shortname"
	* @param entityName the name of the entity
	* @param name the name of the noun
	* @param nameGrammaticalGender the grammatical gender of noun
	* @param nameNumber the number of noun
	*/
  static void appendProperNounToItalianLexicon(String nameOrShortName, String entityName, String name, String nameGrammaticalGender, String nameNumber) 
  {
	  String italianFile = italian.toString() + "/Italian.lexicon";
	
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
    	grammaticalGender = " " + nameGrammaticalGender + "-noun";
    }

    String number = new String("");
    if (!nameNumber.equalsIgnoreCase(""))
    {
    	number = " " + nameNumber + "-noun";
    }

    try
    {
      //FileOutputStream output = new FileOutputStream(italianFile, true);
      //PrintStream p = new PrintStream(output);
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(italianFile, true), "ISO8859_3");
      PrintWriter p = new PrintWriter(output);

      p.println(emptyspaceFile);
      p.println(deflexicalitemopen);
      p.println("   :name " + entityName + appendedName);
      p.println("   :spelling \"" + name + "\"");
      p.println("   :grammatical-features   (noun" + grammaticalGender + number + ")");
      p.println("   :concept (" + entityName + ")");
      p.println(defclose);

      p.flush();
      p.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	}


	/**
	* Append a proper noun entry to lexicon.greek
	* Used in : ExportUtilsIlex.createInstancesGramAndMsgcat()
	* @param nameOrShortName "name" or "shortname"
	* @param entityName the name of the entity
	* @param namenom the nominative name of the noun
	* @param namegen the genitive name of the noun
	* @param nameacc the accusative name of the noun
	* @param nameGrammaticalGender the grammatical gender of noun
	* @param nameNumber the number of noun
	*/
  static void appendProperNounToGreekLexicon(String nameOrShortName, String entityName, String namenom, String namegen, String nameacc, String nameGrammaticalGender, String nameNumber) 
  {
	  String greekFile = greek + "/Greek.lexicon";
	
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
    	grammaticalGender = " " + nameGrammaticalGender + "-noun";
    }

    String number = new String("");
    if (!nameNumber.equalsIgnoreCase(""))
    {
    	number = " " + nameNumber + "-noun";
    }

    try
    {
      //FileOutputStream output = new FileOutputStream(greekFile, true);
      //PrintStream p = new PrintStream(output);
      OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(greekFile, true), "ISO8859_7");
      PrintWriter p = new PrintWriter(output);

      p.println(emptyspaceFile);
      p.println(deflexicalitemopen);
      p.println("   :name " + entityName + appendedName);
      p.println("   :spelling \"" + namenom + "\"");

      // the spelling exceptions
      if ( (!namegen.equalsIgnoreCase("")) || (!nameacc.equalsIgnoreCase("")) )
      {
        p.println("   :spelling-exceptions (");
        if (!namegen.equalsIgnoreCase(""))
        {
        	p.println("                        ((genitive-noun) \"" + namegen + "\")");
        }
        if (!nameacc.equalsIgnoreCase(""))
        {
        	p.println("                        ((accusative-noun) \"" + nameacc + "\")");
        }
        p.println("                        )");
      }
      p.println("   :grammatical-features   (noun" + grammaticalGender + number + ")");
      p.println("   :concept (" + entityName + ")");
      p.println(defclose);

      p.flush();
      p.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	}


	/**
	 * Exports microplanning expressions to expressions.gram, italian-expression.gram, greek-expression.gram
	 * Used in: ExportUtilsIlex.createExpressionsGram()
	 * @param microPlanningHashtable the microplanning hashtable
	 * @param field the field name
	 * @param num the microplan number
	 * @param language the language
	 */
  static void createMicroplanningExpressionExport(Hashtable microPlanningHashtable, String field, String language) 
  {
		//System.out.println("(createMicroplanningExpressionExport)---- " + field);
		String expressionsFile = domSpec.toString() + "/expressions.gram";
		String expressionsItalianFile = italian.toString() + "/italian-expression.gram";
		String expressionsGreekFile = greek.toString() + "/greek-expression.gram";
		
		String[] allAttributesEnglish = {"Verb", "Voice", "Tense", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub", "Casesub", "Referobj", "Caseobj", "Aggreg"};
		String[] allAttributesItalian = {"Verb", "Voice", "Tense", "Aspect", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub", "Casesub", "Referobj", "Caseobj", "Aggreg"};
		String[] allAttributesGreek = {"Verb", "Voice", "Tense", "Aspect", "Prep", "Preadj", "Postadj", "Adverb", "Mood", "Reversible", "Refersub", "Casesub", "Referobj", "Caseobj", "Aggreg"};
		
		Vector attributesVector = new Vector();
		
		try
		{
	    //FileOutputStream output1 = new FileOutputStream(expressionsFile, true);
	    //FileOutputStream output2 = new FileOutputStream(expressionsItalianFile, true);
	    //FileOutputStream output3 = new FileOutputStream(expressionsGreekFile, true);
	    //FileOutputStream output;
	
	    OutputStreamWriter output1 = new OutputStreamWriter(new FileOutputStream(expressionsFile, true), "ISO8859_1");
	    OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(expressionsItalianFile, true), "ISO8859_3");
	    OutputStreamWriter output3 = new OutputStreamWriter(new FileOutputStream(expressionsGreekFile, true), "ISO8859_7");
	    OutputStreamWriter output;
	
	    if (language.equalsIgnoreCase("English"))
	    {
        for (int i=0; i<allAttributesEnglish.length; i++)
        {
        	attributesVector.addElement("1:" + field + ":" + allAttributesEnglish[i] + ":" + language);
        }
        output = output1;
	    }
      else if (language.equalsIgnoreCase("Italian"))
      {
        for (int i=0; i<allAttributesItalian.length; i++)
        {
        	attributesVector.addElement("1:" + field + ":" + allAttributesItalian[i] + ":" + language);
        }
        output = output2;
      }

      else //if (language.equalsIgnoreCase("Greek"))
      {
        for (int i=0; i<allAttributesGreek.length; i++)
        {
        	attributesVector.addElement("1:" + field + ":" + allAttributesGreek[i] + ":" + language);
        }
        output = output3;
      }

      //PrintStream p = new PrintStream(output);
      PrintWriter p = new PrintWriter(output);

      p.println("(defexpression " + field);

      if (language.equalsIgnoreCase("Italian"))
      {
      	p.println("   :language :italian");
      }
      else if (language.equalsIgnoreCase("Greek"))
      {
      	p.println("   :language :greek");
      }

      Enumeration attributesVectorEnum = attributesVector.elements();

      // 2 special cases that get values from 2 entries each
      StringBuffer arg1class = new StringBuffer();
      StringBuffer arg2class = new StringBuffer();
      //////////////////////////////////////////////////////

      while (attributesVectorEnum.hasMoreElements())
      {
        String keyString = attributesVectorEnum.nextElement().toString();
				//System.out.println("()---- " + keyString);////////////////////////////////////////////////////
        String valueString = new String();
        if (microPlanningHashtable.containsKey(keyString))
        {
          valueString = microPlanningHashtable.get(keyString).toString();
          StringTokenizer keyStringTokens = new StringTokenizer(keyString, ":");
          keyStringTokens.nextToken();
          keyStringTokens.nextToken();
          String keyStringAttribute = keyStringTokens.nextToken();

          if (keyStringAttribute.equalsIgnoreCase("Verb"))
          {
          	p.println("   :verb " + valueString);
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Voice"))
          {
          	p.println("   :voice " + valueString);
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Tense"))
          {
          	p.println("   :tense " + valueString);
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Voice"))
          {
          	p.println("   :voice " + valueString);
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Aspect"))
          {
          	p.println("   :aspect " + valueString);
          }
          
          else if ( (keyStringAttribute.equalsIgnoreCase("Prep")) && (!valueString.equalsIgnoreCase("")) )
          {
          	p.println("   :arg2-prep \"" + valueString + "\"");
          }
          
          else if ( (keyStringAttribute.equalsIgnoreCase("Preadj")) && (!valueString.equalsIgnoreCase("")) )
          {
          	p.println("   :adjunct1 \"" + valueString + "\"");
          }
          
          else if ( (keyStringAttribute.equalsIgnoreCase("Postadj")) && (!valueString.equalsIgnoreCase("")) )
          {
          	p.println("   :adjunct2 \"" + valueString + "\"");
          }
          
          else if ( (keyStringAttribute.equalsIgnoreCase("Adverb")) && (!valueString.equalsIgnoreCase("")) )
          {
          	p.println("   :adverb \"" + valueString + "\"");
          }
          
          else if ( (keyStringAttribute.equalsIgnoreCase("Mood")) && (!valueString.equalsIgnoreCase("Indicative")) )
          {
          	p.println("   :mood " + valueString);
          }
          
          else if ( (keyStringAttribute.equalsIgnoreCase("Reversible")) && (!valueString.equalsIgnoreCase("revFalse")) )
          {
          	p.println("   :reversible t");
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Refersub"))
          {
            if (valueString.equalsIgnoreCase("Auto")) {arg1class.append("referring-np");}
            else if (valueString.equalsIgnoreCase("Name")) {arg1class.append("refer-by-name");}
            else if (valueString.equalsIgnoreCase("Pronoun")) {arg1class.append("refer-by-pronoun");}
            else if (valueString.equalsIgnoreCase("Type with definite article")) {arg1class.append("refer-by-type");}
            else if (valueString.equalsIgnoreCase("Type with indefinite article")) {arg1class.append("classifying-np");}
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Casesub"))
          {
            if (valueString.equalsIgnoreCase("Nominative")) {arg1class.append(" nominative-np");}
            else if (valueString.equalsIgnoreCase("Genitive")) {arg1class.append(" genitive-np");}
            else if (valueString.equalsIgnoreCase("Accusative")) {arg1class.append(" accusative-np");}
            p.println("   :arg1-class (:and " + arg1class.toString() + ")");
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Referobj"))
          {
            if (valueString.equalsIgnoreCase("Auto")) {arg2class.append("referring-np");}
            else if (valueString.equalsIgnoreCase("Name")) {arg2class.append("refer-by-name");}
            else if (valueString.equalsIgnoreCase("Pronoun")) {arg2class.append("refer-by-pronoun");}
            else if (valueString.equalsIgnoreCase("Type with definite article")) {arg2class.append("refer-by-type");}
            else if (valueString.equalsIgnoreCase("Type with indefinite article")) {arg2class.append("classifying-np");}
          }
          
          else if (keyStringAttribute.equalsIgnoreCase("Caseobj"))
          {
            if (valueString.equalsIgnoreCase("Nominative")) {arg2class.append(" nominative-np");}
            else if (valueString.equalsIgnoreCase("Genitive")) {arg2class.append(" genitive-np");}
            else if (valueString.equalsIgnoreCase("Accusative")) {arg2class.append(" accusative-np");}
            p.println("   :arg2-class (:and " + arg2class.toString() + ")");
          }
          
          else if ( (keyStringAttribute.equalsIgnoreCase("Aggreg")) && (valueString.equalsIgnoreCase("aggFalse")) )
          {
          	p.println("   :aggregation-not-allowed t");
          }
				}//if
			}//while
      p.println(defclose);
      p.flush();
      p.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	} // createMicroplanningExpressionExport



	/**
	 * Exports microplanning expressions to expressions.gram, italian-expression.gram, greek-expression.gram
	 * Used in: ExportUtilsIlex.createExpressionsGram()
	 * @param templateVector the template vector
	 * @param field the field name
	 * @param num the microplan number
	 * @param language the language
	 */
  static void createTemplateExpressionExport(Vector templateVector, String field, String language) 
  {
    String expressionsFile = domSpec.toString() + "/expressions.gram";
    String expressionsItalianFile = italian.toString() + "/italian-expression.gram";
    String expressionsGreekFile = greek.toString() + "/greek-expression.gram";

    try
    {
      //FileOutputStream output1 = new FileOutputStream(expressionsFile, true);
      //FileOutputStream output2 = new FileOutputStream(expressionsItalianFile, true);
      //FileOutputStream output3 = new FileOutputStream(expressionsGreekFile, true);
      //FileOutputStream output;

      OutputStreamWriter output1 = new OutputStreamWriter(new FileOutputStream(expressionsFile, true), "ISO8859_1");
      OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(expressionsItalianFile, true), "ISO8859_3");
      OutputStreamWriter output3 = new OutputStreamWriter(new FileOutputStream(expressionsGreekFile, true), "ISO8859_7");
      OutputStreamWriter output;

      Hashtable languageTemplateHashtable = new Hashtable();

      if (language.equalsIgnoreCase("English"))
      {
        output = output1;
        languageTemplateHashtable = (Hashtable)templateVector.get(0);
      }
      
      else if (language.equalsIgnoreCase("Italian"))
      {
        output = output2;
        languageTemplateHashtable = (Hashtable)templateVector.get(1);
      }

      else //if (language.equalsIgnoreCase("Greek"))
      {
        output = output3;
        languageTemplateHashtable = (Hashtable)templateVector.get(2);
      }
      //PrintStream p = new PrintStream(output);
      PrintWriter p = new PrintWriter(output);

      if (!languageTemplateHashtable.containsKey("1:" + field))
      {
      	return;
      }

      p.println("(defexpression " + field);

      if (language.equalsIgnoreCase("Italian"))
      {
      	p.println("   :language :italian");
      }
      else if (language.equalsIgnoreCase("Greek"))
      {
      	p.println("   :language :greek");
      }

      p.println("   :template (:is aggregation-not-allowed");
      p.println("              :orth (_ ");

			Vector languageTemplateVector = (Vector)languageTemplateHashtable.get("1:" + field);
			//int languageTemplateVectorSize = languageTemplateVector.size();
			if (languageTemplateVector.size() != 0)
			{
				//System.out.println(field + "()---  " + languageTemplateVector.toString());
				Hashtable slot;
				for (int i=0; i < languageTemplateVector.size(); i++)
				{
			    slot = (Hashtable)languageTemplateVector.get(i);
			    String selectionString = new String();
			    String slotString = new String();
			    String slotSemantics = new String();
			    String slotType = new String();
			    String slotCase = new String();

          selectionString = slot.get("SELECTION").toString();
          if (selectionString.equalsIgnoreCase("string"))
          {
            if (slot.containsKey("string"))
            {
              slotString = slot.get("string").toString();
              if (!slotString.equalsIgnoreCase(""))
              {
                int k = i+1;
                p.println("                      :x" + k + " \\\" \"" + slotString + "\" \\\"");
              }
            }
          }
          else if (selectionString.equalsIgnoreCase("referring"))
          {
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
              int k = i+1;
              p.println("                      :x" + k + " !$Arg2.Name");
            }
            else
            {
	            int k = i+1;
	            p.println("                      :x" + k + " (_");
	            p.println("                             :is (:and " + slotType + " " + slotCase + ")");
	            if (slotSemantics.equalsIgnoreCase("Field owner"))
	            {
	                p.println("                             :sem $Arg1");
	            }
	            else if (slotSemantics.equalsIgnoreCase("Field filler"))
	            {
	                p.println("                             :sem $Arg2");
	            }
	            p.println("                          )");
						}
					}
        } // for
        p.println("                    )");
        p.println("             )");
			} // if
      p.println(defclose);
      p.flush();
      p.close();
		}
		catch (java.io.IOException IOE)
		{
			System.out.println("|||| Exception ||||");
			IOE.printStackTrace();
		}
	} // createTemplateExpressionExport
}	  //class