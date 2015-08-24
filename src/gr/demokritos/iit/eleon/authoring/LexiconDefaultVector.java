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
