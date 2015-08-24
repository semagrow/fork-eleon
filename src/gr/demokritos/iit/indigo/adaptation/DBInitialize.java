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

public class DBInitialize {
    
    private String userName;
    private String password;
    private String url;
    
    
    public void Initialize(String usrName,String pwd,String urll){
        Connection conn = null;
        
        userName=usrName;
        password=pwd;
        url=urll;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("\n*** XENIOS DB ***");
            System.out.println("*****************\nDatabase connection established to Drop data from DB");
            
            Statement Stmt = conn.createStatement();
            int count;
            
            count=Stmt.executeUpdate("DELETE FROM instances_used");
            System.out.println(count+" rows were effected from Table \"instances_used\"");
            
            count=Stmt.executeUpdate("DELETE FROM instances");
            System.out.println(count+" rows were effected from Table \"instances\"");
            
            count=Stmt.executeUpdate("DELETE FROM microplans");
            System.out.println(count+" rows were effected from Table \"microplans\"");
            
            count=Stmt.executeUpdate("DELETE FROM assimilation_score");
            System.out.println(count+" rows were effected from Table \"assimilation_score\"");
            
            count=Stmt.executeUpdate("DELETE FROM features");
            System.out.println(count+" rows were effected from Table \"features\"");
            
            count=Stmt.executeUpdate("DELETE FROM users");
            System.out.println(count+" rows were effected from Table \"users\"");
            
            count=Stmt.executeUpdate("DELETE FROM stereotypes");
            System.out.println(count+" rows were effected from Table \"stereotypes\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Drop data from DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Drop info");
        }
        
    }//End of void Initialize
    
    public void DepositStereotypes(Vector Stereotypes){
        Connection conn = null;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit Stereotypes to DB");
            
            Statement Stmt = conn.createStatement();
            int count;
            
            //create StereotypeVector's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            for (int i=0; i<Stereotypes.size(); i++){
                Object temp=Stereotypes.get(i);
                //if is the last element in Vector, Don't put "," after ")"
                if(i==Stereotypes.size()-1){rows=rows+"('"+temp.toString()+"')"; } else {rows=rows+"('"+temp.toString()+"'),";}
                
            }
            
            
            count=Stmt.executeUpdate("INSERT INTO stereotypes VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"Stereotypes\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit Stereotypes to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit Stereotypes");
        }
        
        
        
    }//void DepositInfoToDb
    
    
    
    public void DepositProperties(Vector Properties){
        Connection conn = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit Properties to DB");
            
            Statement Stmt = conn.createStatement();
            int count;
            
            //create PropertiesVector's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            String UserType,ftr,interest,UserTypeNewUserType;
            for (int i=0; i<Properties.size(); i++){
                Object temp=Properties.get(i);
                
                UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
                ftr=temp.toString().substring(temp.toString().indexOf('#')+1, temp.toString().indexOf("->"));
                interest=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().length());
                //if interest=-1(haven't specified for that property give it interest=NULL)
                if(interest.equals("-1")){interest="NULL";}
                
                
                //if is the last element in Vector, Don't put "," after ")"
                if(i==(Properties.size()-1)){rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL)"; } else {rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL),";}
                
            }//end of for
            
            
            count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"Properties\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit Properties to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit Properties");
        }
        
        
    }//end of void DepositProperties
    
    
    
    public void DepositUserTypeProprties(Vector UserVector){
        
        Connection conn = null;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit UserType Proprties to DB");
            
            Statement Stmt = conn.createStatement();
            int count;
            
            //create UserVector's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            String UserType,ftr,interest;
            for (int i=0; i<UserVector.size(); i++){
                Object temp=UserVector.get(i);
                
                
                UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
                ftr=temp.toString().substring(temp.toString().indexOf('#')+1, temp.toString().indexOf("->"));
                interest=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().length());
                
                
                //if is the last element in Vector, Don't put "," after ")"
                if(i==(UserVector.size()-1)){rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL)"; } else {rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL),";}
                
            }//end of for
            
            count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"UserType Proprties\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit UserType Proprties to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit UserType Proprties");
        }
        
        
        
        
        
    }//End of void DepositTypeProprties
    
    
    
    
    public void DepositMicroplans(Vector MicroplanVector){
        
        Connection conn = null;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit Microplans Proprties to DB");
            
            Statement Stmt = conn.createStatement();
            int count;
            
            //create MicroplanVector's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            String UserType,ftr,interest,NoMicroplan;
            for (int i=0; i<MicroplanVector.size(); i++){
                Object temp=MicroplanVector.get(i);
                
                
                
                UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
                ftr=temp.toString().substring(temp.toString().indexOf('#')+1, temp.toString().indexOf("->"));
                interest=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().length());
                
                
                //if is the last element in Vector, Don't put "," after ")"
                if(i==MicroplanVector.size()-1){rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL)"; } else {rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL),";}
                
            }//end of for
            
            count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"Microplans Proprties\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit Microplans Proprties to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit Microplans Proprties");
        }
        
    }//End of void DepositMicroplans
    
    
    
    
    public void DepositClassProperties(Vector ClassVector){
        
        Connection conn = null;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit Class(subtype_of) Proprties to DB");
            
            Statement Stmt = conn.createStatement();
            int count;
            
            //create ClassVector's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            String UserType,ftr,interest;
            for (int i=0; i<ClassVector.size(); i++){
                Object temp=ClassVector.get(i);
                
                
                UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
                ftr=temp.toString().substring(temp.toString().indexOf('#'), temp.toString().indexOf("->"));
                interest=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().length());
                if(interest.equals("-1")){interest="NULL";}
                
                
                //if is the last element in Vector, Don't put "," after ")"
                if(i==(ClassVector.size()-1)){rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL)"; } else {rows=rows+"('"+ftr+"','"+UserType+"',"+interest+",NULL,NULL),";}
                
            }//end of for
            
            count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"Class(subtype_of) Proprties\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit Class(subtype_of) Proprties to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit Class(subtype_of) Proprties");
        }
        
        
    }//End of void DepositClassProperties
    
//---------------------------------------------------------------------------------
    
    public void DepositInstances(Vector UniqueInstance){
        Connection conn = null;
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit Instances to DB");
            
            Statement Stmt = conn.createStatement();
            int count;
            
            //create UniqueInstance's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            
            for (int i=0; i<UniqueInstance.size(); i++){
                Object temp=UniqueInstance.get(i);
                
                //if is the last element in Vector, Don't put "," after ")"
                if(i==(UniqueInstance.size()-1)){rows=rows+"('"+temp.toString()+"')"; } else {rows=rows+"('"+temp.toString()+"'),";}
                
            }//end of for
            
            count=Stmt.executeUpdate("INSERT INTO instances VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"Instances\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit Instances to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit Instances Proprties");
        }
        
        
        
    }//End of void DepositClassProperties
    
//#############################################################
    
    public void DepositAssimilationRates(Vector RepetitionVector){
        Connection conn = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit �ssimilationRates to DB");
            
            Statement Stmt = conn.createStatement();
            int count=0;
            
            //create PropertiesVector's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            String UserType,ftr,interest,UserTypeNewUserType;
            for (int i=0; i<RepetitionVector.size(); i++){
                Object temp=RepetitionVector.get(i);
                
                UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
                ftr=temp.toString().substring(temp.toString().indexOf('#')+1, temp.toString().indexOf("->"));
                String Score=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().indexOf('&'));
                String Rate=temp.toString().substring(temp.toString().indexOf("&")+1, temp.toString().length());
                double initscore=Double.parseDouble(Score);
                double assimrate=Double.parseDouble(Rate);
                
                count=count+Stmt.executeUpdate("UPDATE features SET initscore='"+initscore+"',assimrate='"+assimrate+"' WHERE ftr='"+ftr+"' AND strp='"+UserType+"'");
                
                
            }//end of for
            
            
            //count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"Properties\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit AssimilationRates to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit AssimilationRates");
        }
        
        
    }//end of void DepositAssimilationRates
//-----------------------------------------------------------------------------------------------
    
    public void DepositClassAssimilationRates(Vector RepetitionClassVector){
        Connection conn = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("------------\nDatabase connection established to Deposit Class �ssimilationRates to DB");
            
            Statement Stmt = conn.createStatement();
            int count=0;
            
            //create PropertiesVector's contents to apropriate form for inserting eg. ('Adult'),..
            String rows="";
            String UserType,ftr,interest,UserTypeNewUserType;
            for (int i=0; i<RepetitionClassVector.size(); i++){
                Object temp=RepetitionClassVector.get(i);
                
                UserType=temp.toString().substring(0, temp.toString().indexOf('#'));
                ftr=temp.toString().substring(temp.toString().indexOf('#')+1, temp.toString().indexOf("->"));
                String Score=temp.toString().substring(temp.toString().indexOf("->")+2, temp.toString().indexOf('&'));
                String Rate=temp.toString().substring(temp.toString().indexOf("&")+1, temp.toString().length());
                double initscore=Double.parseDouble(Score);
                double assimrate=Double.parseDouble(Rate);
                
                count=count+Stmt.executeUpdate("UPDATE features SET initscore='"+initscore+"',assimrate='"+assimrate+"' WHERE ftr='#"+ftr+"' AND strp='"+UserType+"'");
                
                
            }//end of for
            
            
            //count=Stmt.executeUpdate("INSERT INTO features VALUES "+rows);
            System.out.println(count+" rows were effected Depositing \"Properties\"");
            
            conn.close();
            Stmt.close();
            System.out.println("Database connection closed to Deposit Class �ssimilationRates to DB");
        } catch (Exception e) {
            System.err.println("Connection have problem to Deposit Class �ssimilationRates");
        }
        
        
    }//end of void DepositClass�ssimilationRates


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
