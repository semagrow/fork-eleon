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


package gr.demokritos.iit.indigo.adaptation.Code_for_Load_Robotic_Personalities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class DBLoad {

	private String userName;
	private String password;
	private String url;
	
	
	
	//Constructor
	public DBLoad(String usrName,String pwd,String urll){
	
	this.userName=usrName;
	this.password=pwd;
	this.url=urll;
	     
	}//End of Constructor

public void DepositStereotypes(Vector Stereotypes){
	Connection conn = null;

	try
     {

		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        conn = DriverManager.getConnection (url, userName, password);
         System.out.println ("------------\nDatabase connection established to Deposit Robotic-Stereotypes to DB");
     
         Statement Stmt = conn.createStatement();
         int count;
         
         //create StereotypeVector's contents to apropriate form for inserting eg. ('Adult'),.. 
         String rows="";
         for (int i=0; i<Stereotypes.size(); i++){
        	  Object temp=Stereotypes.get(i);
        	  //if is the last element in Vector, Don't put "," after ")" 
        	  if(i==Stereotypes.size()-1){rows=rows+"('"+"RobotType:"+temp.toString()+"')"; }
           	  else {rows=rows+"('"+"RobotType:"+temp.toString()+"'),";}
           	         	  
        	 }
         
         
         count=Stmt.executeUpdate("INSERT INTO stereotypes VALUES "+rows);
         System.out.println(count+" rows were effected Depositing \"Robotic-Stereotypes\"");
                  
         conn.close();
         Stmt.close();
         System.out.println ("Database connection closed to Deposit Robotic-Stereotypes to DB");
     }
     catch (Exception e)
     {
         System.err.println ("Connection have problem to Deposit Robotic-Stereotypes");
     }
	
	
	
	}//void DepositInfoToDb



public void DepositProperties(Vector Properties){
	Connection conn = null;
	
	try
    {
		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
       conn = DriverManager.getConnection (url, userName, password);
        System.out.println ("------------\nDatabase connection established to Deposit Robotic-Preferences to DB");
    
        Statement Stmt = conn.createStatement();
        int count; 
    
        //create PropertiesVector's contents to apropriate form for inserting eg. ('Adult'),.. 
        String rows="";
        String UserType,ftr,interest,UserTypeNewUserType;
        for (int i=0; i<Properties.size(); i++){
       	  Object temp=Properties.get(i); 
       	  
       	  UserType=temp.toString().substring(0, temp.toString().indexOf('#')); 
UserType="RobotType:"+UserType;
       	  ftr=temp.toString().substring(temp.toString().indexOf('#')+1, temp.toString().indexOf("->"));
       	  interest=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().length());
       	  //if interest=-1(haven't specified for that property give it interest=NULL)
       	  if(interest.equals("-1")){interest="NULL";}

       	  
       	  //if is the last element in Vector, Don't put "," after ")" 
       	  if(i==(Properties.size()-1)){rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL)"; }
       	  else {rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL),";}
          	         	  
       	 }//end of for
     
        
        count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
        System.out.println(count+" rows were effected Depositing \"Robotic-Preferences\"");
                 
        conn.close();
        Stmt.close();
        System.out.println ("Database connection closed to Deposit Robotic-Preferences to DB");
    }
    catch (Exception e)
    {
        System.err.println ("Connection have problem to Deposit Robotic-Preferences");
    }
	

	}//end of void DepositProperties



public void DepositUserTypeProprties(Vector UserVector){
	
Connection conn = null;
	
	try
    {

		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
       conn = DriverManager.getConnection (url, userName, password);
        System.out.println ("------------\nDatabase connection established to Deposit Robotic-Personalities Proprties to DB");
    
        Statement Stmt = conn.createStatement();
        int count; 
    
        //create UserVector's contents to apropriate form for inserting eg. ('Adult'),.. 
        String rows="";
        String UserType,ftr,interest;
        for (int i=0; i<UserVector.size(); i++){
       	  Object temp=UserVector.get(i); 

       	  
       	  UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
UserType="RobotType:"+UserType;       	  
       	  ftr=temp.toString().substring(temp.toString().indexOf('#')+1, temp.toString().indexOf("->"));
       	  interest=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().length());
       	  
       	  
       	  //if is the last element in Vector, Don't put "," after ")" 
       	  if(i==(UserVector.size()-1)){rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL)"; }
       	  else {rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL),";}
          	         	  
       	 }//end of for
     
        count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
        System.out.println(count+" rows were effected Depositing \"Robotic-Personalities\"");
                 
        conn.close();
        Stmt.close();
        System.out.println ("Database connection closed to Deposit Robotic-Personalities to DB");
    }
    catch (Exception e)
    {
        System.err.println ("Connection have problem to Deposit Robotic-Personalities");
    }
	
	
	


}//End of void DepositTypeProprties


public void DepositClassProperties(Vector ClassVector){
	
Connection conn = null;
	
	try
    {

		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
       conn = DriverManager.getConnection (url, userName, password);
        System.out.println ("------------\nDatabase connection established to Deposit Robotic Class(subtype_of) Proprties to DB");
    
        Statement Stmt = conn.createStatement();
        int count; 
    
        //create ClassVector's contents to apropriate form for inserting eg. ('Adult'),.. 
        String rows="";
        String UserType,ftr,interest;
        for (int i=0; i<ClassVector.size(); i++){
       	  Object temp=ClassVector.get(i); 

       	  
       	  UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
UserType="RobotType:"+UserType;       	  
       	  ftr=temp.toString().substring(temp.toString().indexOf('#'), temp.toString().indexOf("->"));
       	  interest=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().length());
       	  if(interest.equals("-1")){interest="NULL";}
       	
       	  
       	  //if is the last element in Vector, Don't put "," after ")" 
       	  if(i==(ClassVector.size()-1)){rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL)"; }
       	  else {rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL),";}
          	         	  
       	 }//end of for
     
        count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
        System.out.println(count+" rows were effected Depositing \"Robotic Class(subtype_of) Proprties\"");
                 
        conn.close();
        Stmt.close();
        System.out.println ("Database connection closed to Deposit Robotic Class(subtype_of) Proprties to DB");
    }
    catch (Exception e)
    {
        System.err.println ("Connection have problem to Deposit Class(subtype_of) Proprties");
    }


}//End of void DepositClassProperties

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
