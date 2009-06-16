/*
 * propertiesHashtableRecord.java
 *
 * Created on 14 ������� 2006, 4:34 ��
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package gr.demokritos.iit.eleon.authoring;
import java.util.*;
/**
 *
 * @author dbilid
 */
public class PropertiesHashtableRecord extends Vector{
    static final long serialVersionUID= 7753555422102686221L;
    /** Creates a new instance of propertiesHashtableRecord 
      NEW-USER-FIELD=[ [DOMAIN], [RANGE], [SUB], [SUPER], [EQUIVALENT], INVERSE, functional, inverse-functional, transitive, symmetric, {microplans}, [templateVector] ]
     */
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
                Vector allUserTypesVector = QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
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
               
                add("");
                add("false");
                 Hashtable robots=new Hashtable();
                try{
                Vector allRobotTypesVector = QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
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
                Vector allUserTypesVector = QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
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
                add("");
                add("false");
                 Hashtable robots=new Hashtable();
                //try{
                Vector allRobotTypesVector = QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
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
                Vector allUserTypesVector = QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
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
                add("");
                add("false");
                 Hashtable robots=new Hashtable();
                //try{
                Vector allRobotTypesVector = QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
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
                Vector allUserTypesVector = QueryUsersHashtable.getUsersVectorFromMainUsersHashtable();
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
                add("");
                add("false");
                 Hashtable robots=new Hashtable();
                //try{
                Vector allRobotTypesVector = QueryUsersHashtable.getRobotsVectorFromRobotsHashtable();
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
             Vector supertypes=QueryHashtable.getAllSupertypes(type);
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
    
}
