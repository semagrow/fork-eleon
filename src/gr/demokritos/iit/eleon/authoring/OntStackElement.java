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
