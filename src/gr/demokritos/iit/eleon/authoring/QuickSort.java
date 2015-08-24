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

public class QuickSort 
{

	/**
	 *  The implementation of QuickSort for a vector (thisVect)
	 */
	public static Vector quickSort (int l, int r, Vector thisVect) 
	{
	  int result;
	  int j = l;
	  int k = r+1;
	  String S_token = (String) thisVect.elementAt(j);
	  String j_token = null;
	  String k_token = null;
	  String hold_token;
	  while (j < k) 
    {
	    while (true) 
	    {
				j++;
				if (j == k) break;
				j_token = (String) thisVect.elementAt(j);
				result = j_token.toUpperCase().compareTo(S_token.toUpperCase());
				if (result >= 0 ) break;
	    }
      while(true) 
      {
				k--;
				if (k == l) break;
				k_token = (String) thisVect.elementAt(k);
				result = k_token.toUpperCase().compareTo(S_token.toUpperCase());
				if (result <= 0) break;
      }
      if (j < k) 
      {
				// interchange Vector[j] <-> Vector[k]
				hold_token = j_token;
				if (j_token == null || k_token == null) 
				{
				  //System.out.println("\nERROR in quicksort!\n");
				  //System.exit(0);
				}
				thisVect.setElementAt(k_token, j);
				thisVect.setElementAt(hold_token, k);
      }
		}
    // interchange Vector[l] <-> Vector[k]
    hold_token = (String) thisVect.elementAt(l);
    thisVect.setElementAt(k_token, l);
    thisVect.setElementAt(hold_token, k);
    if (l < k-1)
       thisVect = quickSort (l,k-1, thisVect);
    if (k+1 < r)
       thisVect = quickSort (k+1,r, thisVect);
    return thisVect;
	}   /////// tempVect = quickSort(0, tempVect.size()-1, tempVect); ////////

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
