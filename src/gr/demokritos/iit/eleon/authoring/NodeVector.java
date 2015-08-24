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
        if(v.size()==6){
             databaseTableVector = (DefaultVector)this.elementAt(0);
		upperVector =(Vector)this.elementAt(1);
		nounVector = (Vector)this.elementAt(2);
		storiesVector = new StoriesVector();
		templateVector = (TemplateVector)this.elementAt(4);
		microPlanningValues = (Hashtable)this.elementAt(5);
		//userModellingValues = new Hashtable();
         }
      if(v.size()==4){
          databaseTableVector=(Vector)this.elementAt(0);
             upperVector = (Vector)this.elementAt(1);
		nounVector = (Vector)this.elementAt(2);
		storiesVector = new StoriesVector();
        independentFieldsVector = (Vector)databaseTableVector.elementAt(0);
		englishFieldsVector = (Vector)databaseTableVector.elementAt(1);
		italianFieldsVector = (Vector)databaseTableVector.elementAt(2);
		greekFieldsVector = (Vector)databaseTableVector.elementAt(3);
      }

      if(v.size()==1){
          lexTableVector=(Vector)this.elementAt(0);
      }
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
