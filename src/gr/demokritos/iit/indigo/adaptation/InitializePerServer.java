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


package gr.demokritos.iit.indigo.adaptation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class InitializePerServer {

	private String userName;
	private String password;
	private String url;
	
	
public void InitializePSever(String usrName,String pwd,String urll){
	
	Connection conn = null;

	userName=usrName;
	password=pwd;
	url=urll;
	
	try
     {

         Class.forName ("com.mysql.jdbc.Driver").newInstance ();
         conn = DriverManager.getConnection (url, userName, password);
         System.out.println("\n*** PerServer ***");
         System.out.println("*****************\nDatabase connection established on PerServer to Drop data from it");
     
         Statement Stmt = conn.createStatement();
         int count;
    
         count=Stmt.executeUpdate("DELETE FROM ftrgroup_features");
         System.out.println(count+" rows were effected from Table \"ftrgroup_features\"");

         count=Stmt.executeUpdate("DELETE FROM ftrgroups");
         System.out.println(count+" rows were effected from Table \"ftrgroups\"");
         
         count=Stmt.executeUpdate("DELETE FROM user_profiles");
         System.out.println(count+" rows were effected from Table \"user_profiles\"");
         
         count=Stmt.executeUpdate("DELETE FROM stereotype_profiles");
         System.out.println(count+" rows were effected from Table \"stereotype_profiles\"");
         
         count=Stmt.executeUpdate("DELETE FROM stereotype_users");
         System.out.println(count+" rows were effected from Table \"stereotype_users\"");
         
         count=Stmt.executeUpdate("DELETE FROM stereotypes");
         System.out.println(count+" rows were effected from Table \"stereotypes\"");
         
         count=Stmt.executeUpdate("DELETE FROM up_features");
         System.out.println(count+" rows were effected from Table \"up_features\"");
         
         count=Stmt.executeUpdate("DELETE FROM users");
         System.out.println(count+" rows were effected from Table \"users\"");
         
         conn.close();
         Stmt.close();
         System.out.println ("Database connection closed on PerServer to Drop data from it");
     }
     catch (Exception e)
     {
         System.err.println ("Connection have problem on PerServer to Drop info");
     }
	
}//End of Class void InitializePerSever



public void DepositUpFeatures(Vector forPerServerVector){
	Connection conn = null;

	try
     {

		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        conn = DriverManager.getConnection (url, userName, password);
         System.out.println ("------------\nDatabase connection established to Deposit Up_Features to PerServer");
     
         Statement Stmt = conn.createStatement();
         int count;
         
         //create forPerServerVector's contents to apropriate form for inserting eg. ('Adult'),.. 
         String rows="";
         for (int i=0; i<forPerServerVector.size(); i++){
        	  Object temp=forPerServerVector.get(i);
        	  //if is the last element in Vector, Don't put "," after ")" 
        	  if(i==forPerServerVector.size()-1){rows=rows+"('"+temp.toString()+"','0',0)"; }
           	  else {rows=rows+"('"+temp.toString()+"','0',0),";}
           	         	  
        	 }
         
         
         count=Stmt.executeUpdate("INSERT INTO up_features VALUES "+rows);
         System.out.println(count+" rows were effected Depositing \"Up_Features\"");
                  
         conn.close();
         Stmt.close();
         System.out.println ("Database connection closed to Deposit Up_Features to PerServer");
     }
     catch (Exception e)
     {
         System.err.println ("Connection have problem to Deposit Up_Features to PerServer");
     }
	
	
	
	}//End of void DepositUpFeatures


public void DepositInstances(Vector UniqueInstance){
	Connection conn = null;
		
		try
	    {

			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	       conn = DriverManager.getConnection (url, userName, password);
	        System.out.println ("------------\nDatabase connection established to Deposit Instances to PerServer");
	        System.out.println("REMEMBER: That the instances are represented like \"Inst.name_of_instacne\" ");
	        Statement Stmt = conn.createStatement();
	        int count; 
	    
	        //create UniqueInstance's contents to apropriate form for inserting eg. ('Adult'),.. 
	        String rows="";
	        
	        for (int i=0; i<UniqueInstance.size(); i++){
	       	  Object temp=UniqueInstance.get(i); 
	       	  
	       	  //if is the last element in Vector, Don't put "," after ")" 
	       	  if(i==(UniqueInstance.size()-1)){rows=rows+"('Inst."+temp.toString()+"','0',0)"; }
	       	  else {rows=rows+"('Inst."+temp.toString()+"','0',0),";}
	          	         	  
	       	 }//end of for
	     
	        count=Stmt.executeUpdate("INSERT INTO up_features VALUES "+rows);
	        System.out.println(count+" rows were effected Depositing \"up_features\"");
	                 
	        conn.close();
	        Stmt.close();
	        System.out.println ("Database connection closed to Deposit Instances to up_features of PerServer");
	    }
	    catch (Exception e)
	    {
	        System.err.println ("Connection have problem to Deposit Instances to up_features of PerServer");
	    }
		
		
		
	}//End of void DepositClassProperties


public void DepositStereotypes(Vector Stereotypes){
	Connection conn = null;

	try
     {

		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        conn = DriverManager.getConnection (url, userName, password);
         System.out.println ("------------\nDatabase connection established to Deposit Stereotypes to PerServer");
     
         Statement Stmt = conn.createStatement();
         int count;
         
         //create StereotypeVector's contents to apropriate form for inserting eg. ('Adult'),.. 
         String rows="";
         for (int i=0; i<Stereotypes.size(); i++){
        	  Object temp=Stereotypes.get(i);
        	  //if is the last element in Vector, Don't put "," after ")" 
        	  if(i==Stereotypes.size()-1){rows=rows+"('"+temp.toString()+"')"; }
           	  else {rows=rows+"('"+temp.toString()+"'),";}
           	         	  
        	 }
         
         
         count=Stmt.executeUpdate("INSERT INTO stereotypes VALUES "+rows);
         System.out.println(count+" rows were effected Depositing \"Stereotypes\"");
                  
         conn.close();
         Stmt.close();
         System.out.println ("Database connection closed to Deposit Stereotypes to PerServer");
     }
     catch (Exception e)
     {
         System.err.println ("Connection have problem to Deposit Stereotypes to PerServer");
     }
	
	
	
	}//void DepositInfoToDb
//----------------------------------------------------------------------
//----------------------------------------------------------------------

public void DepositStereotypeProfilesProperties(Vector Stereotypes,Vector forPerServerVector){
	Connection conn = null;

	try
     {

		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        conn = DriverManager.getConnection (url, userName, password);
         System.out.println ("------------\nDatabase connection established to Deposit StereotypeProfilesProperties(forPerServerVector) to PerServer");
     
         Statement Stmt = conn.createStatement();
	
         int count;
         
         //create StereotypeVector's contents to apropriate form for inserting eg. ('Adult'),.. 
         String rows="";
         for (int i=0; i<Stereotypes.size(); i++){
        	  Object stereotype=Stereotypes.get(i);
        	  System.out.println("Stereotype: "+stereotype.toString());
        	  rows="";
        	 
        	  
        	  for (int j=0; i<forPerServerVector.size(); j++){
            	  Object ftr=forPerServerVector.get(j);
            	  //if is the last element in Vector, Don't put "," after ")" 
            	  if(j==forPerServerVector.size()-1){rows=rows+"('"+stereotype.toString()+"','"+ftr.toString()+"','0',0)"; 
            	  			count=Stmt.executeUpdate("INSERT INTO stereotype_profiles VALUES "+rows);
            	  			System.out.println(count+" rows were effected Depositing \"StereotypesProfiles\"");
            	  			break;}
               	  else {rows=rows+"('"+stereotype.toString()+"','"+ftr.toString()+"','0',0),";}
               	         	  
            	 }
           	         	  
        	 }//End of outer-for
         
         
       conn.close();
       Stmt.close();
         System.out.println ("Database connection closed to Deposit StereotypeProfilesProperties(forPerServerVector) to PerServer");
     }
     catch (Exception e)
     {
         System.err.println ("Connection have problem to Deposit StereotypeProfilesProperties(forPerServerVector) to PerServer");
     }
	
	
	
	}//End of void DepositStereotypeProfilesProperties

//***************************************************************************
//***************************************************************************

public void DepositStereotypeProfilesInstances(Vector Stereotypes,Vector UniqueInstance){
	
	Connection conn = null;

	try
     {

		Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        conn = DriverManager.getConnection (url, userName, password);
         System.out.println ("------------\nDatabase connection established to Deposit StereotypeProfilesInstances(forPerServerVector) to PerServer");
     
         Statement Stmt = conn.createStatement();
	
         int count;
         
         //create StereotypeVector's contents to apropriate form for inserting eg. ('Adult'),.. 
         String rows="";
         for (int i=0; i<Stereotypes.size(); i++){
        	  Object stereotype=Stereotypes.get(i);
        	  System.out.println("Stereotype: "+stereotype.toString());
        	  rows="";
        	 
        	  
        	  for (int j=0; i<UniqueInstance.size(); j++){
            	  Object inst=UniqueInstance.get(j);
            	  //if is the last element in Vector, Don't put "," after ")" 
            	  if(j==UniqueInstance.size()-1){rows=rows+"('"+stereotype.toString()+"','Inst."+inst.toString()+"','0',0)"; 
            	  			count=Stmt.executeUpdate("INSERT INTO stereotype_profiles VALUES "+rows);
            	  			System.out.println(count+" rows were effected Depositing \"StereotypesProfiles\"");
            	  			break;}
               	  else {rows=rows+"('"+stereotype.toString()+"','Inst."+inst.toString()+"','0',0),";}
               	         	  
            	 }
           	         	  
        	 }//End of outer-for
         
         
       conn.close();
       Stmt.close();
         System.out.println ("Database connection closed to Deposit StereotypeProfilesInstances(forPerServerVector) to PerServer");
     }
     catch (Exception e)
     {
         System.err.println ("Connection have problem to Deposit StereotypeProfilesInstances(forPerServerVector) to PerServer");
     }
	

	}//End of DepositStereotypeProfilesInstances


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

