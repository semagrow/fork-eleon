/*
 * OntStackElement.java
 *
 * Created on 23 ���������� 2007, 4:42 ��
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.StoriesVector;

import java.util.*;
import java.io.*;

public class OntStackElement extends Vector 
{
	//public class NodeVector extends Vector implements Serializable {

	// Elements of a databaseTree BasicType or EntityType node
	public Vector databaseTableVector;
	public Vector upperVector;
	public Vector nounVector;
	public StoriesVector storiesVector;
	public TemplateVector templateVector;
	public Hashtable microPlanningValues;
	//public Hashtable userModellingValues;	
	// Elements of a databaseTree Entity node
	public Vector independentFieldsVector;
	public Vector englishFieldsVector;
	public Vector italianFieldsVector;
	public Vector greekFieldsVector;	
	// Elements of a lexTree node
	public Vector lexTableVector;

  // 1. A simple constructor
  public OntStackElement(Vector v) 
  {
	  super(v);
  }

  // 2. A constructor for BasicTypes and EntityTypes
  public OntStackElement(String Type, String Name) 
  {
		add(Type);
                add(Name);
		//addElement(userModellingValues);
  }
  public boolean equals(Object ob){
      OntStackElement ose=new OntStackElement(null,null);
      try{
          ose=(OntStackElement) ob;
      }
      catch(java.lang.ClassCastException ex){
          return false;
      }
      if (this.elementAt(0).toString().equalsIgnoreCase(ose.elementAt(0).toString()))
      return true;
      else
          return false;
  }
}
