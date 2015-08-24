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


package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.authoring.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.lang.Object.*;


public class QueryOptionsHashtable extends Object implements Serializable 
{
	public Hashtable mainOptionsHashtable;
	
	/** Create the main options hashtable. (for initialisation purposes only) */
	public QueryOptionsHashtable() 
	{
	  mainOptionsHashtable = new Hashtable();
	  mainOptionsHashtable.put("versionTool.main", "3.0");
	  mainOptionsHashtable.put("versionTool.working", "3.0.0");
	  mainOptionsHashtable.put("java.version", System.getProperty("java.version"));
	  mainOptionsHashtable.put("java.specification.version", System.getProperty("java.specification.version"));
	  addPServerAddressToMainOptionsHashtable("143.233.6.3", "1111");
          mainOptionsHashtable.put("baseURI","http://localhost");
	  //System.out.println(mainOptionsHashtable.toString());
	} // createMainOptionsHashtable
        
public QueryOptionsHashtable(String baseURI){
     mainOptionsHashtable = new Hashtable();
	  mainOptionsHashtable.put("versionTool.main", "3.0");
	  mainOptionsHashtable.put("versionTool.working", "3.0.0");
	  mainOptionsHashtable.put("java.version", System.getProperty("java.version"));
	  mainOptionsHashtable.put("java.specification.version", System.getProperty("java.specification.version"));
	  addPServerAddressToMainOptionsHashtable("143.233.6.3", "1111");
          mainOptionsHashtable.put("baseURI",baseURI);
}


/** Add or replace the selected pserver address to mainOptionsHashtable  */
	public void addPServerAddressToMainOptionsHashtable(String pserverIP, String pserverPort) 
	{
		mainOptionsHashtable.put("pserverAddress", new Vector());
		Vector pserverAddressVector = (Vector)mainOptionsHashtable.get("pserverAddress");
		pserverAddressVector.addElement(pserverIP);
		pserverAddressVector.addElement(pserverPort);
		//System.out.println(mainOptionsHashtable.toString());
	} // addPServerAddressToMainOptionsHashtable


	public void setBaseURI(String URI) 
	{
		mainOptionsHashtable.put("baseURI",URI);

	} 
        
        public String getBaseURI() 
	{
            System.out.println((String)mainOptionsHashtable.get("baseURI"));
		return (String)mainOptionsHashtable.get("baseURI");

	} 


	/** get the selected pserver address to mainOptionsHashtable for the specific doamin */
	public Vector getPServerAddressFromMainOptionsHashtable() 
	{
		Vector pserverAddressVector = (Vector)mainOptionsHashtable.get("pserverAddress");
		return pserverAddressVector;
	} // getPServerAddressFromMainOptionsHashtable

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
