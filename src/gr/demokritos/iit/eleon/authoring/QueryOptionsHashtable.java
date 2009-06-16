//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

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
	public static Hashtable mainOptionsHashtable;
	
	/** Create the main options hashtable. (for initialisation purposes only) */
	public static void createMainOptionsHashtable() 
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
        


/** Add or replace the selected pserver address to mainOptionsHashtable  */
	public static void addPServerAddressToMainOptionsHashtable(String pserverIP, String pserverPort) 
	{
		mainOptionsHashtable.put("pserverAddress", new Vector());
		Vector pserverAddressVector = (Vector)mainOptionsHashtable.get("pserverAddress");
		pserverAddressVector.addElement(pserverIP);
		pserverAddressVector.addElement(pserverPort);
		//System.out.println(mainOptionsHashtable.toString());
	} // addPServerAddressToMainOptionsHashtable


	public static void setBaseURI(String URI) 
	{
		mainOptionsHashtable.put("baseURI",URI);

	} 
        
        public static String getBaseURI() 
	{
            System.out.println((String)mainOptionsHashtable.get("baseURI"));
		return (String)mainOptionsHashtable.get("baseURI");

	} 


	/** get the selected pserver address to mainOptionsHashtable for the specific doamin */
	public static Vector getPServerAddressFromMainOptionsHashtable() 
	{
		Vector pserverAddressVector = (Vector)mainOptionsHashtable.get("pserverAddress");
		return pserverAddressVector;
	} // getPServerAddressFromMainOptionsHashtable

}//class