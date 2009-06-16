//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.util.*;

/**
 * <p>Title: DefaultVector</p>
 * <p>Description: The default vectors used for the models of all Database tables</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Kostas Stamatakis, Dimitris Spiliotopoulos
 * @version 1.0
 */
public class DefaultVector extends Vector 
{

  /**
   * 1st constructor for BasicTypes and EntityTypes
   * @param parentName The name of the parent entity-type
   */
     public DefaultVector(Vector s)
  {
         super(s);
         
     }
  public DefaultVector(String parentName) 
  {
		if (parentName.equalsIgnoreCase(DataBasePanel.top.toString())) 
		{
			//System.out.println(DataBasePanel.top.toString());
			addElement(new FieldData("Subtype-of", "type", false, ""));
		} 
		else 
		{
			//String parent = DataBasePanel.last.getParent().toString();
			addElement(new FieldData("Subtype-of", parentName, false, ""));
		}
		addElement(new FieldData("title", "string", false, ""));
		addElement(new FieldData("name", "special", false, ""));
		addElement(new FieldData("shortname", "special", false, ""));
		addElement(new FieldData("notes", "string", false, ""));
		addElement(new FieldData("gender", "special", false, ""));
		addElement(new FieldData("number", "special", false, ""));
		addElement(new FieldData("images", "special", true, ""));
  }

  /**
   * 2nd constructor for Entities (langID equals to:
   * @param parentName The name of the parent entity-type
   * @param currentNodeName The name og the current selected node
   * @param langID Equals to: 0 for language-independent fields, 1 for english fields, 2 for italian fields, 3 for greek fields
   */
  public DefaultVector(String parentName, String currentNodeName, int langID) 
  {
	  if (langID == 0) // language independent fields
	  {
		  addElement(new FieldData("entity-id", currentNodeName));
		  addElement(new FieldData("type", parentName));
		  addElement(new FieldData("images", "Select images ....."));
	  }
	  if (langID == 1) // english fields
	  {
		  addElement(new FieldData("title", ""));
		  addElement(new FieldData("name", ""));
		  addElement(new FieldData("shortname", ""));
		  addElement(new FieldData("notes", ""));
		  addElement(new FieldData("gender", ""));
		  addElement(new FieldData("number", "singular"));
	  }
	  if (langID == 2) // italian fields
	  {
		  addElement(new FieldData("title", ""));
		  addElement(new FieldData("name", ""));
		  addElement(new FieldData("grammatical gender of name", ""));
		  addElement(new FieldData("shortname", ""));
		  addElement(new FieldData("grammatical gender of shortname", ""));
		  addElement(new FieldData("notes", ""));
		  addElement(new FieldData("number", "singular"));
	  }
	  if (langID == 3) // greek fields
	  {
		  addElement(new FieldData("title", ""));
		  addElement(new FieldData("name (nominative)", ""));
		  addElement(new FieldData("name (genitive)", ""));
		  addElement(new FieldData("name (accusative)", ""));
		  addElement(new FieldData("grammatical gender of name", ""));
		  addElement(new FieldData("shortname (nominative)", ""));
		  addElement(new FieldData("shortname (genitive)", ""));
		  addElement(new FieldData("shortname (accusative)", ""));
		  addElement(new FieldData("grammatical gender of shortname", ""));
		  addElement(new FieldData("notes", ""));
		  addElement(new FieldData("number", "singular"));
	  }
  }
}
