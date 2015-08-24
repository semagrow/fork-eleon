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

import java.io.*;
import java.util.*;
import java.util.zip.*;
import org.w3c.dom.*;

/**
 * <p>Title: Prepositions</p>
 * <p>Description: Fetches available prepositions from
 * domain-independent and domain-dependent files</p>
 * @author Amy Isard
 * @version 1.0 */

public class Prepositions 
{
	//private static String SLASH = (String)System.getProperty("file.separator");
	private static String SLASH = "/";
	private static Hashtable encodings = new Hashtable();
	private static Hashtable currentPrepositionsHashtable = new Hashtable();
	private static Hashtable loadedPrepositionsHashtable = new Hashtable();
	
	private static MpiroZip domainZip = null;
	private static MpiroZip independentZip = null;
	
	public static void reset(String domain, ArrayList languages) 
	{
		encodings.put("English", "ISO-8859-1");
		encodings.put("Italian", "ISO-8859-1");
		encodings.put("Greek", "ISO-8859-7");
		if (Mpiro.domainZip != null) 
		{
			domainZip = new MpiroZip(Mpiro.domainZip.getName());
		}
		Iterator langIter = languages.iterator();
		while (langIter.hasNext()) 
		{
			String lang = langIter.next().toString();
			loadPrepositionsForLanguage(lang);
		}
	}
    
	public static void loadPrepositionsForLanguage(String language) 
	{
		String independentFileString = System.getProperty("user.dir") + SLASH + "mpiro_resources.zip";
		File independentFile = new File(independentFileString);
		if (!independentFile.exists()) 
		{
			return;
		}
		independentZip = new MpiroZip(independentFileString);
		ArrayList prepSpellings = new ArrayList();	
		if (independentZip != null) 
		{
			String domainIndependentPrepositionsFile = "exprimo" + SLASH + "Resources" + SLASH + "Lexicons"
				+ SLASH + language + SLASH + "prepositions.xml";

			MpiroDocument independentMpiroDoc = new MpiroDocument();
			independentMpiroDoc.loadDocumentFromZip(independentZip, domainIndependentPrepositionsFile);
			Document independentDoc = independentMpiroDoc.getDocument();
	    
			if (independentDoc != null) 
			{
				NodeList spellingNodes = independentDoc.getElementsByTagName("spelling");
				
				for (int i =0; i < spellingNodes.getLength(); i++) 
				{
					Node spellingNode = spellingNodes.item(i);
					String spellingString = ((Element)spellingNode).getAttribute("is").toLowerCase();
		    
					if (spellingString != null && !spellingString.equals("") && !prepSpellings.contains(spellingString)) 
					{
						prepSpellings.add(spellingString);
					}
				}
			}
		}

		if (domainZip != null) 
		{
			String domainDependentPrepositionsFile = "exprimo" + SLASH + "Resources" + SLASH + "Lexicons"
				+ SLASH + language + SLASH + "domain.xml";
	    
	    MpiroDocument dependentMpiroDoc = new MpiroDocument();
	    dependentMpiroDoc.loadDocumentFromZip(domainZip, domainDependentPrepositionsFile);
	    Document dependentDoc = dependentMpiroDoc.getDocument();
	    
	    if (dependentDoc != null) 
	    {
				NodeList dependentNodes = dependentDoc.getElementsByTagName("def-lexical-item");
		
				for (int i =0; i < dependentNodes.getLength(); i++) 
				{
					Node dependentNode = dependentNodes.item(i);
					if ((dependentNode instanceof Element) && ((Element)dependentNode).getAttribute("id").endsWith("-prep")) 
					{
						NodeList newSpellingNodes = ((Element)dependentNode).getElementsByTagName("spelling");
						Node spellingNode = newSpellingNodes.item(0);
						if (spellingNode != null) 
						{
							String spellingString = ((Element)spellingNode).getAttribute("is").toLowerCase();
							if (spellingString != null && !spellingString.equals("")
									&& !prepSpellings.contains(spellingString)) 
							{
								prepSpellings.add(spellingString);
							}
						}
					}
				}
			}
		}
	
		Collections.sort(prepSpellings);
		currentPrepositionsHashtable.put(language, prepSpellings);
		loadedPrepositionsHashtable.put(language, (ArrayList)prepSpellings.clone());
	}

	public static void savePrepositionsToZip(ArrayList languages) 
	{
		if (Mpiro.domainZip == null) 
		{
			return;
		}
		domainZip = new MpiroZip(Mpiro.domainZip.getName());
		Iterator langIter = languages.iterator();
		boolean addedAnyPrep = false;
	
		while (langIter.hasNext()) 
		{
			String language = (String)langIter.next();
			ArrayList currentPrepList = (ArrayList)currentPrepositionsHashtable.get(language);
			
			if (currentPrepList == null || currentPrepList.isEmpty()) 
			{
				return;
			}

	    String domainDependentPrepositionsFile = "exprimo" + SLASH + "Resources" + SLASH + "Lexicons"
				+ SLASH + language + SLASH + "domain.xml";

	    MpiroDocument dependentMpiroDoc = new MpiroDocument();
	    dependentMpiroDoc.loadDocumentFromZip(domainZip, domainDependentPrepositionsFile);
	    Document dependentDoc = dependentMpiroDoc.getDocument();
	    
	    if (dependentDoc == null) 
	    {
				System.err.println("Cannot save new prepositions for " + language + ", no domain lexicon file found");
				return;
	    }

	    Element docEl = dependentDoc.getDocumentElement();
	    
	    ArrayList loadedPreps = (ArrayList)loadedPrepositionsHashtable.get(language);
	    Iterator prepIter = currentPrepList.iterator();

	    boolean addedPrep = false;

	    while (prepIter.hasNext()) 
	    {
				String newPrepSpelling = (String)prepIter.next();

				if (!loadedPreps.contains(newPrepSpelling)) 
				{
			    addedPrep = true;
			    addedAnyPrep = true;
			    
			    Element defLexItem = dependentDoc.createElement("def-lexical-item");
			    defLexItem.setAttribute("id", newPrepSpelling + "-prep");
			    docEl.appendChild(defLexItem);
			    
			    Element spelling = dependentDoc.createElement("spelling");
			    spelling.setAttribute("is", newPrepSpelling);
			    defLexItem.appendChild(spelling);
		    
			    Element grammaticalFeatures = dependentDoc.createElement("grammatical-features");
			    defLexItem.appendChild(grammaticalFeatures);
			    
			    Element featureRef = dependentDoc.createElement("feature-ref");
			    featureRef.setAttribute("name", "preposition");
			    grammaticalFeatures.appendChild(featureRef);
			    
			    Element concepts = dependentDoc.createElement("concepts");
			    defLexItem.appendChild(concepts);
		    
			    Element concept = dependentDoc.createElement("concept");
			    concept.setAttribute("name", "NIL");
			    concepts.appendChild(concept);
				}
			}

			if (addedPrep) 
			{
				dependentMpiroDoc.setDocument(dependentDoc);
				domainZip.addDocument(dependentMpiroDoc, domainDependentPrepositionsFile, encodings.get(language).toString());
			}
		}
		if (addedAnyPrep) 
		{
			domainZip.save();
		}
	}


	public static ArrayList getCurrentPrepositionsList(String language) 
	{
		return (ArrayList)currentPrepositionsHashtable.get(language);
	}

	public static boolean addPrepositionToList(String prep, String language) 
	{
		ArrayList tempCurrentList = (ArrayList)currentPrepositionsHashtable.get(language);
		
		while (prep.startsWith(" ")) 
		{
			prep = prep.substring(1, prep.length());
		}
		while (prep.endsWith(" ")) 
		{
			prep = prep.substring(0, prep.length() - 1);
		}
		if (!prep.equals("")) 
		{
			if (tempCurrentList != null && !tempCurrentList.contains(prep)) 
			{
				tempCurrentList.add(prep);
				Collections.sort(tempCurrentList);
				currentPrepositionsHashtable.put(language, tempCurrentList);
				
				return true;
			}
		}
		return false;
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
