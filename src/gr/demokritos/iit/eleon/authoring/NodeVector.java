//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import gr.demokritos.iit.eleon.ui.StoriesVector;

import java.util.*;
import java.io.*;

public class NodeVector extends Vector 
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
  public NodeVector(Vector v) 
  {
	  super(v);
  }

  // 2. A constructor for BasicTypes and EntityTypes
  public NodeVector(String parentNodeName) 
  {
		databaseTableVector = new DefaultVector(parentNodeName);
		upperVector = new Vector();
		nounVector = new Vector();
		storiesVector = new StoriesVector();
		templateVector = new TemplateVector();
		microPlanningValues = new Hashtable();
		//userModellingValues = new Hashtable();
		
		addElement(databaseTableVector); // a single vector
		addElement(upperVector);
		addElement(nounVector);
		addElement(storiesVector);
		addElement(templateVector);
		addElement(microPlanningValues);
		//addElement(userModellingValues);
  }

  // 3. A constructor for Entities
  public NodeVector(String parentNodeName, String currentNodeName) 
  {
		databaseTableVector = new Vector(); // an empty vector
		independentFieldsVector = new DefaultVector(parentNodeName, currentNodeName, 0);
		englishFieldsVector = new DefaultVector(parentNodeName, currentNodeName, 1);
		italianFieldsVector = new DefaultVector(parentNodeName, currentNodeName, 2);
		greekFieldsVector = new DefaultVector(parentNodeName, currentNodeName, 3);
		
		databaseTableVector.addElement(independentFieldsVector);
		databaseTableVector.addElement(englishFieldsVector);
		databaseTableVector.addElement(italianFieldsVector);
		databaseTableVector.addElement(greekFieldsVector);
		
		upperVector = new Vector();
		nounVector = new Vector();
		storiesVector = new StoriesVector();
		
		addElement(databaseTableVector); // a vector of four vectors
		addElement(upperVector);
		addElement(nounVector);
		addElement(storiesVector);
  }

  // 4. A constructor for Nouns & Verbs
  public NodeVector() 
  {
		lexTableVector = new Vector();
		addElement(lexTableVector);
	}

  // Methods to get all the elements of a nodeVector
  // --- for BasicTypes and EntityTypes --- //
  public Vector getDatabaseTableVector() 
  {
	  return databaseTableVector;
  }
  
  public Vector getUpperVector() 
  {
	  return upperVector;
  }
  
  public Vector getNounVector() 
  {
	  return nounVector;
  }
	  
  public StoriesVector getStoriesVector() 
  {
	  return storiesVector;
  }
  
  public TemplateVector getTemplateVector() 
  {
	  return templateVector;
  }
  
  public Hashtable getMicroPlanningValues() 
  {
	  return microPlanningValues;
  }

  // --- and for Entities --- //
  public Vector getIndependentFieldsVector() 
  {
	  return independentFieldsVector;
  }
	  
  public Vector getEnglishFieldsVector() 
  {
	  return englishFieldsVector;
  }
  
  public Vector getItalianFieldsVector() 
  {
	  return italianFieldsVector;
  }
  
  public Vector getGreekFieldsVector() 
  {
	  return greekFieldsVector;
  }
  
  public boolean independentFieldsVectorContainsField(String FieldName){
      for(int i=0;i<independentFieldsVector.size();i++){
          FieldData fd=(FieldData)independentFieldsVector.elementAt(i);
          if(fd.elementAt(0).toString().equals(FieldName))
              return true;
      }
      return false;
  }
  
   public boolean greekFieldsVectorContainsField(String FieldName){
      for(int i=0;i<greekFieldsVector.size();i++){
          FieldData fd=(FieldData)greekFieldsVector.elementAt(i);
          if(fd.elementAt(0).toString().equals(FieldName))
              return true;
      }
      return false;
  }
   
   /*to be used only for entities, removes the field data with the given 
    *field name from independentFieldsVector (vectorNo=1) or 
    *englishFieldsVector (VectorNo=2) or italianFieldsVector (VectorNo=3)
    *or greekFieldsVector(VectorNo=4)*/
   public void removeFieldData(String fieldName, int vectorNo){
      Vector v=new Vector();
      if(vectorNo==1)
          v=this.independentFieldsVector;
      if(vectorNo==2)
          v=this.englishFieldsVector;
      if(vectorNo==3)
          v=this.italianFieldsVector;
      if(vectorNo==4)
          v=this.greekFieldsVector;
      for(int i=0;i<v.size();i++){
          FieldData fd=(FieldData) v.elementAt(i);
          if(fd.m_field.equals(fieldName)){
              v.removeElementAt(i);
          return;}
      }
  }
   
    public void renameFieldData(String fieldName, String newName, int vectorNo){
      Vector v=new Vector();
      if(vectorNo==1)
          v=this.independentFieldsVector;
      if(vectorNo==2)
          v=this.englishFieldsVector;
      if(vectorNo==3)
          v=this.italianFieldsVector;
      if(vectorNo==4)
          v=this.greekFieldsVector;
      for(int i=0;i<v.size();i++){
          FieldData fd=(FieldData) v.elementAt(i);
          if(fd.m_field.equals(fieldName)){
              fd.setElementAt(newName, 0);
          return;}
      }
  }
   
   /*to be used only for entities, adds the field data with the given 
    *field name to independentFieldsVector (vectorNo=1) or 
    *englishFieldsVector (VectorNo=2) or italianFieldsVector (VectorNo=3)
    *or greekFieldsVector(VectorNo=4)*/
   public void addFieldData(FieldData field, int vectorNo){
      Vector v=new Vector();
      if(vectorNo==1)
          v=this.independentFieldsVector;
      if(vectorNo==2)
          v=this.englishFieldsVector;
      if(vectorNo==3)
          v=this.italianFieldsVector;
      if(vectorNo==4)
          v=this.greekFieldsVector;
     v.add(field);
  }

} // class

