//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

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