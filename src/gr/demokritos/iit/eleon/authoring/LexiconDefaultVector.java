//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.util.*;


/**
 * <p>Title: LexiconDefaultVector</p>
 * <p>Description: The table vectors for creating the italian and greek tables in LEXICON tab</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class LexiconDefaultVector extends Vector 
{
	public LexiconDefaultVector(String name) 
	{
	  if (name.equalsIgnoreCase("verb-italian-1"))
	  {
	    addElement(new LexiconFieldData("Present", "Singular", "1st", "", false));
	    addElement(new LexiconFieldData("Present", "Singular", "2nd", "", false));
	    addElement(new LexiconFieldData("Present", "Singular", "3rd", "", false));
	    addElement(new LexiconFieldData("Present", "Plural", "1st", "", false));
	    addElement(new LexiconFieldData("Present", "Plural", "2nd", "", false));
	    addElement(new LexiconFieldData("Present", "Plural", "3rd", "", false));
	    addElement(new LexiconFieldData("Past continuous", "Singular", "1st", "", false));
	    addElement(new LexiconFieldData("Past continuous", "Singular", "2nd", "", false));
	    addElement(new LexiconFieldData("Past continuous", "Singular", "3rd", "", false));
	    addElement(new LexiconFieldData("Past continuous", "Plural", "1st", "", false));
	    addElement(new LexiconFieldData("Past continuous", "Plural", "2nd", "", false));
	    addElement(new LexiconFieldData("Past continuous", "Plural", "3rd", "", false));
	    addElement(new LexiconFieldData("Remote past", "Singular", "1st", "", false));
	    addElement(new LexiconFieldData("Remote past", "Singular", "2nd", "", false));
	    addElement(new LexiconFieldData("Remote past", "Singular", "3rd", "", false));
	    addElement(new LexiconFieldData("Remote past", "Plural", "1st", "", false));
	    addElement(new LexiconFieldData("Remote past", "Plural", "2nd", "", false));
	    addElement(new LexiconFieldData("Remote past", "Plural", "3rd", "", false));
		}
		else if (name.equalsIgnoreCase("verb-italian-2"))
		{
		  addElement(new LexiconFieldData("Masculine", "Singular", "", false));
		  addElement(new LexiconFieldData("Masculine", "Plural", "", false));
		  addElement(new LexiconFieldData("Feminine", "Singular", "", false));
		  addElement(new LexiconFieldData("Feminine", "Plural", "", false));
		}
    else if (name.equalsIgnoreCase("verb-greek-1"))
    {
	    addElement(new LexiconFieldData("Present progressive", "Active", "Singular", "1st", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Active", "Singular", "2nd", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Active", "Singular", "3rd", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Active", "Plural", "1st", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Active", "Plural", "2nd", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Active", "Plural", "3rd", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Passive", "Singular", "1st", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Passive", "Singular", "2nd", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Passive", "Singular", "3rd", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Passive", "Plural", "1st", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Passive", "Plural", "2nd", "", false));
	    addElement(new LexiconFieldData("Present progressive", "Passive", "Plural", "3rd", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Active", "Singular", "1st", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Active", "Singular", "2nd", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Active", "Singular", "3rd", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Active", "Plural", "1st", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Active", "Plural", "2nd", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Active", "Plural", "3rd", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Passive", "Singular", "1st", "", false));
	    addElement(new LexiconFieldData("Past progressive", "Passive", "Singular", "2nd", "", false));
      addElement(new LexiconFieldData("Past progressive", "Passive", "Singular", "3rd", "", false));
      addElement(new LexiconFieldData("Past progressive", "Passive", "Plural", "1st", "", false));
      addElement(new LexiconFieldData("Past progressive", "Passive", "Plural", "2nd", "", false));
      addElement(new LexiconFieldData("Past progressive", "Passive", "Plural", "3rd", "", false));
      addElement(new LexiconFieldData("Past simple", "Active", "Singular", "1st", "", false));
      addElement(new LexiconFieldData("Past simple", "Active", "Singular", "2nd", "", false));
      addElement(new LexiconFieldData("Past simple", "Active", "Singular", "3rd", "", false));
      addElement(new LexiconFieldData("Past simple", "Active", "Plural", "1st", "", false));
      addElement(new LexiconFieldData("Past simple", "Active", "Plural", "2nd", "", false));
      addElement(new LexiconFieldData("Past simple", "Active", "Plural", "3rd", "", false));
      addElement(new LexiconFieldData("Past simple", "Passive", "Singular", "1st", "", false));
      addElement(new LexiconFieldData("Past simple", "Passive", "Singular", "2nd", "", false));
      addElement(new LexiconFieldData("Past simple", "Passive", "Singular", "3rd", "", false));
      addElement(new LexiconFieldData("Past simple", "Passive", "Plural", "1st", "", false));
      addElement(new LexiconFieldData("Past simple", "Passive", "Plural", "2nd", "", false));
      addElement(new LexiconFieldData("Past simple", "Passive", "Plural", "3rd", "", false));
		}
		else if (name.equalsIgnoreCase("verb-greek-2"))
		{
		  addElement(new LexiconFieldData("Masculine", "", false));
		  addElement(new LexiconFieldData("Feminine", "", false));
		  addElement(new LexiconFieldData("Neuter", "", false));
		}
  }
}