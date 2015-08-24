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
import gr.demokritos.iit.eleon.struct.QueryHashtable;
import gr.demokritos.iit.eleon.struct.QueryProfileHashtable;
import java.util.*;

public class PropertiesHashtableRecord extends Vector{
  //  static final long serialVersionUID= 7753555422102686221L;
    /** Creates a new instance of propertiesHashtableRecord 
      NEW-USER-FIELD=[ [DOMAIN], [RANGE], [SUB], [SUPER], [EQUIVALENT], INVERSE, functional, inverse-functional, transitive, symmetric, {microplans}, [templateVector] ]
     */


    public PropertiesHashtableRecord(Vector v)
  {
	  super(v);
  }

    public PropertiesHashtableRecord(boolean dom) {
       // Vector prop=new Vector();
             Vector domain= new Vector();
            // domain.add(DataBasePanel.last.toString());
             add(domain);
                Vector range=new Vector();
              //  range.add("String");
                add(range);
                add(new Vector());
                add(new Vector());
                add(new Vector());
                add("");
                add("false");
                add("false");
                add("false");
                add("false");
                add(new Hashtable());
                add(new TemplateVector());
                Hashtable users=new Hashtable();
                try{
                Vector allUserTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
		while (allUserTypesVectorEnum.hasMoreElements())
		{
		  String user = allUserTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                  v.add("3");
                  v.add("1");
                  users.put(user,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(users);
                }
                catch(java.lang.ArrayIndexOutOfBoundsException aioob){
                    String user="NewUserType";
                    Vector v=new Vector();
                  v.add("3");
                  v.add("3");
                  v.add("1");
                  users.put(user,v);
                  add(users);
                }
               
                add("1");
                add("false");
                 Hashtable robots=new Hashtable();
                try{
                Vector allRobotTypesVector = Mpiro.win.struc.getRobotsVectorFromUsersHashtable();
		Enumeration allRobotTypesVectorEnum = allRobotTypesVector.elements();
		while (allRobotTypesVectorEnum.hasMoreElements())
		{
		  String robot = allRobotTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                 // v.add("3");
              //    v.add("1");
                  robots.put(robot,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(robots);
                }
                catch(java.lang.ArrayIndexOutOfBoundsException aioob){
                    String robot="NewProfile";
                    Vector v=new Vector();
                  v.add("3");
                //  v.add("3");
               //   v.add("1");
                  robots.put(robot,v);
                  add(robots);
                }
	
    }
    
    public PropertiesHashtableRecord() {
       // Vector prop=new Vector();
             Vector domain= new Vector();
             domain.add(DataBasePanel.last.toString());
             add(domain);
                Vector range=new Vector();
                range.add("String");
                add(range);
                add(new Vector());
                add(new Vector());
                add(new Vector());
                add("");
                add("false");
                add("false");
                add("false");
                add("false");
                add(new Hashtable());
                add(new TemplateVector());
                Hashtable users=new Hashtable();
                Vector allUserTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
		while (allUserTypesVectorEnum.hasMoreElements())
		{
		  String user = allUserTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                  v.add("3");
                  v.add("1");
                  users.put(user,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(users);
                add("1");
                add("false");
                 Hashtable robots=new Hashtable();
                //try{
                Vector allRobotTypesVector = Mpiro.win.struc.getRobotsVectorFromUsersHashtable();
		Enumeration allRobotTypesVectorEnum = allRobotTypesVector.elements();
		while (allRobotTypesVectorEnum.hasMoreElements())
		{
		  String robot = allRobotTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                 // v.add("3");
              //    v.add("1");
                  robots.put(robot,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(robots);
               // }
              //  catch(java.lang.ArrayIndexOutOfBoundsException aioob){
              //      String user="NewRobotType";
              //      Vector v=new Vector();
               //   v.add("3");
                //  v.add("3");
               //   v.add("1");
              //    robots.put(robot,v);
              //    add(robotss);
                //}
	
    }
    
    
    
     public PropertiesHashtableRecord(String range1) {
       // Vector prop=new Vector();
             Vector domain= new Vector();
             domain.add(DataBasePanel.last.toString());
             add(domain);
                Vector range=new Vector();
                range.add(range1);
                add(range);
                add(new Vector());
                add(new Vector());
                add(new Vector());
                add("");
                add("false");
                add("false");
                add("false");
                add("false");
                add(new Hashtable());
                add(new TemplateVector());
                Hashtable users=new Hashtable();
                Vector allUserTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
		while (allUserTypesVectorEnum.hasMoreElements())
		{
		  String user = allUserTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                  v.add("3");
                  v.add("1");
                  users.put(user,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(users);
                add("1");
                add("false");
                 Hashtable robots=new Hashtable();
                //try{
                Vector allRobotTypesVector = Mpiro.win.struc.getRobotsVectorFromUsersHashtable();
		Enumeration allRobotTypesVectorEnum = allRobotTypesVector.elements();
		while (allRobotTypesVectorEnum.hasMoreElements())
		{
		  String robot = allRobotTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                 // v.add("3");
              //    v.add("1");
                  robots.put(robot,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(robots);
    }
     
     public PropertiesHashtableRecord(String range1, String Domain1) {
       // Vector prop=new Vector();
             Vector domain= new Vector();
             domain.add(Domain1);
             add(domain);
                Vector range=new Vector();
                range.add(range1);
                add(range);
                add(new Vector());
                add(new Vector());
                add(new Vector());
                add("");
                add("false");
                add("false");
                add("false");
                add("false");
                add(new Hashtable());
                add(new TemplateVector());
                Hashtable users=new Hashtable();
                Vector allUserTypesVector = Mpiro.win.struc.getUsersVectorFromMainUsersHashtable();
		Enumeration allUserTypesVectorEnum = allUserTypesVector.elements();
		while (allUserTypesVectorEnum.hasMoreElements())
		{
		  String user = allUserTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                  v.add("3");
                  v.add("1");
                  users.put(user,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(users);
                add("1");
                add("false");
                 Hashtable robots=new Hashtable();
                //try{
                Vector allRobotTypesVector = Mpiro.win.struc.getRobotsVectorFromUsersHashtable();
		Enumeration allRobotTypesVectorEnum = allRobotTypesVector.elements();
		while (allRobotTypesVectorEnum.hasMoreElements())
		{
		  String robot = allRobotTypesVectorEnum.nextElement().toString();
                  Vector v=new Vector();
                  v.add("3");
                 // v.add("3");
              //    v.add("1");
                  robots.put(robot,v);
		  //System.out.println("()---- " + user);
		  
		}
                add(robots);
    }
     
     public boolean hasTypeInItsDomain(String type){
         Vector domain=(Vector)this.elementAt(0);
         if (domain.contains(type))
             return true;
             Vector supertypes=Mpiro.win.struc.getAllSupertypes(type);
             for(int i=0;i<supertypes.size();i++){
                 if (domain.contains(supertypes.elementAt(i).toString()))
             return true;
             }
             return false;
         
     }
     
      public Vector getRange() 
  {
	  return (Vector)this.elementAt(1);
  }
      
            public Vector getDomain() 
  {
	  return (Vector)this.elementAt(0);
  }

    void removeFromRange(String oldValue) {
        Vector range=(Vector)this.elementAt(1);
        range.remove(oldValue);
    }

    void addToRange(String svalue) {
       Vector range=(Vector)this.elementAt(1);
        range.add(svalue);
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
