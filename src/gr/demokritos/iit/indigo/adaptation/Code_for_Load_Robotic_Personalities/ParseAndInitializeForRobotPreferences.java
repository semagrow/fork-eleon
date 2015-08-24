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

import java.io.*;

import java.util.Vector;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.vocabulary.RDF;

public class ParseAndInitializeForRobotPreferences {
	
	private String file;
	private String path;
	
	private String username;	//for the connection with the mysql DB 
    private String password;
    private String xeniosPath;	       //the path for Xenios DB, which is like: "jdbc:mysql://localhost/xenios"
    
	
	
	private Vector UserTypeVector=new Vector();
	private Vector PropertiesVector=new Vector();  
	private Vector ClassVector=new Vector();
	private Vector StereotypeVector=new Vector();
	
	 //constructor
    public ParseAndInitializeForRobotPreferences(String file, String path, String username, String password, String xeniosPath) {
        this.file = file;
        this.path = path;
        this.username=username;
        this.password=password;
        this.xeniosPath=xeniosPath;
        
        
    }
	
	
	
	
	
	// load Robotic modelling info from rdf file
    public void parseConcatenatedForRobotPreferences(){
        
       
        
        // read file
        UserModellingManagerForRobots UMM = new UserModellingManagerForRobots("");
        UMM.read(path,file);
                   
        // load user types parameters
        ExtendedIterator userTypes = UMM.get(UMM.RobotTypesProperty);
        String conUsersType;
        String conTemp;
        
        String conProperty;
        String conTempProperty;
        String conUser;
        
        String conTempMicroplan;
        String conMicroplan;
        
        String conClass;
        String conTempClass;
        
        while(userTypes.hasNext()){
            Resource currentUserType = (Resource)userTypes.next();
             
            
            //pairnw to RoboticName (to 8ewro san Stereotype) kai ta bazw sto Vector "StereotypeVector"
            String UserType = currentUserType.getURI();
            String temp=UserType;  temp=temp.substring(temp.indexOf('#')+1,temp.length());
            conTemp=temp;
            if(!StereotypeVector.contains(conTemp)){StereotypeVector.addElement(conTemp);}
     
            //fortwnw ta 5 tags kai ta bazw sto Vector "UserTypeVector"
            String Openness = currentUserType.getProperty(UMM.Openness_tag).getLiteral().getString();
            conUsersType=conTemp.concat("#Openness->"+Openness); 
            //conUsersType="('MFPS','"+temp+"',"+MFPS+",NULL,NULL)";
            UserTypeVector.addElement(conUsersType);
            	
            String Conscientiousness = currentUserType.getProperty(UMM.Conscientiousness_tag).getLiteral().getString();
            conUsersType=conTemp.concat("#Conscientiousness->"+Conscientiousness); 
            //conUsersType="('MFPS','"+temp+"',"+MFPS+",NULL,NULL)";
            UserTypeVector.addElement(conUsersType);
              
            String Extraversion = currentUserType.getProperty(UMM.Extraversion_tag).getLiteral().getString();
            conUsersType=conTemp.concat("#Extraversion->"+Extraversion); 
            //conUsersType="('MFPS','"+temp+"',"+MFPS+",NULL,NULL)";
            UserTypeVector.addElement(conUsersType);
        
            String Agreeableness = currentUserType.getProperty(UMM.Agreeableness_tag).getLiteral().getString();
            conUsersType=conTemp.concat("#Agreeableness->"+Agreeableness); 
            //conUsersType="('MFPS','"+temp+"',"+MFPS+",NULL,NULL)";
            UserTypeVector.addElement(conUsersType);
            
            String NaturalReactions = currentUserType.getProperty(UMM.NaturalReactions_tag).getLiteral().getString();
            conUsersType=conTemp.concat("#NaturalReactions->"+NaturalReactions); 
            //conUsersType="('MFPS','"+temp+"',"+MFPS+",NULL,NULL)";
            UserTypeVector.addElement(conUsersType);
        
        }



//      ----------------------------------------------------------
//      ------------------- PROPERTY-PREFERENCE ------------------
//      ----------------------------------------------------------        
        
        
        // load Property-Preferences
        ExtendedIterator prps = UMM.get(UMM.RobotsPreference_tag);
        Model m = UMM.getModel();
        
        
    //for each property
    while(prps.hasNext()){//for each property
    Resource currentProp = (Resource)prps.next();
        
    //pairnw to Property sto "conProperty"
    String currentPropURI = currentProp.getURI();             
    String temp=currentPropURI;  temp=temp.substring(temp.indexOf('#')+1,temp.length());
    conProperty=temp;
    
    
    
    
    // load default property preferences 
    StmtIterator iter = null;
    iter = m.listStatements(currentProp, UMM.DPPreference_tag, (RDFNode)null);
        
    while(iter.hasNext()){
   	    Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp1=UT;  temp1=temp1.substring(temp1.indexOf('#')+1,temp1.length());                 
        conUser=temp1;
        
            	
        //load preference
        String Preference = bNode.getProperty(UMM.Preference_tag).getLiteral().getString();
        conTempProperty=conUser;
        conTempProperty=conTempProperty.concat("#"+conProperty+"->"+Preference);
        if(!PropertiesVector.contains(conTempProperty)){PropertiesVector.addElement(conTempProperty);}
    }
    
    
    
    
    
    
    // load default class preference
    iter =  m.listStatements(currentProp , UMM.CDPPreference_tag, (RDFNode)null);
                 
    while(iter.hasNext())
    {                 
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
        
        String forOwlClass =  ((Resource)bNode.getProperty(UMM.forOwlClassProperty).getObject().as(Resource.class)).getURI();
        String temp1=forOwlClass;  temp1=temp1.substring(temp1.indexOf('#')+1,temp1.length());                 
        
        
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp2=UT;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());                 
              
         
    
        //load Interest
        String Interest = bNode.getProperty(UMM.PreferenceValue_tag).getLiteral().getString(); 
        conTempProperty=temp2+"#"+conProperty+"."+temp1+"->"+Interest;
        if(!PropertiesVector.contains(conTempProperty)){PropertiesVector.addElement(conTempProperty);}
    
    }
    
    
        
    
    
    // load instances preference
    iter =  m.listStatements(currentProp , UMM.IPPreference_tag, (RDFNode)null);
    
    while(iter.hasNext())
    {
        
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
        
        String forInstance =  ((Resource)bNode.getProperty(UMM.forInstanceProperty).getObject().as(Resource.class)).getURI();
        String temp1=forInstance;  temp1=temp1.substring(temp1.indexOf('#')+1,temp1.length());                 
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp2=UT;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());                 
        
                
    
        //load Interest
        String Interest = bNode.getProperty(UMM.PreferenceValue_tag).getLiteral().getString();
        conTempProperty=temp2+"#"+conProperty+"."+temp1+"->"+Interest;                     
        if(!PropertiesVector.contains(conTempProperty)){PropertiesVector.addElement(conTempProperty);}   
    }

 }//for each property


                     
//----------------------------------------------------------
//  ------------ CLASS PREFERENCE (subtypeOf) --------------
//  --------------------------------------------------------

//load class repetition-Interest
ExtendedIterator ClassRepIter = UMM.get(UMM.ClassPreference_tag);



while(ClassRepIter.hasNext())
{
    Resource classRes = (Resource)ClassRepIter.next();
           
    String classResURI = classRes.getURI();
    String temp=classResURI;  temp=temp.substring(temp.indexOf('#')+1,temp.length());
    conClass=temp;
    
    
    // load defaults Class Preference
    StmtIterator iter =  m.listStatements( classRes , UMM.DPreference_tag, (RDFNode)null);
    
    while(iter.hasNext())
    {                 
    
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
                         
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp2=UT;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());
     
            
        //Load Preference
        String Interest = bNode.getProperty(UMM.InterestValueProperty).getLiteral().getString();
        conTempClass=temp2+"#"+conClass+"->"+Interest;
        if(!ClassVector.contains(conTempClass)){ClassVector.addElement(conTempClass);}
    }
    
    
    
    // load Instance 
    iter =  m.listStatements( classRes , UMM.IPreference_tag, (RDFNode)null);
    
    while(iter.hasNext())
    {                              
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
                         
        String forInstance = ((Resource)bNode.getProperty(UMM.forInstanceProperty).getObject().as(Resource.class)).getURI();
        String temp2=forInstance;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp3=UT;  temp3=temp3.substring(temp3.indexOf('#')+1,temp3.length());                 
        
        
        
    
    
        //Load Instance Preference
        String Interest = bNode.getProperty(UMM.Preference_tag).getLiteral().getString();
        conTempClass=temp3+"#"+conClass+"."+temp2+"->"+Interest;
        if(!ClassVector.contains(conTempClass)){ClassVector.addElement(conTempClass);}
    
    
    }
}



/*

//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
///////////////////////////// TA VECTORS EXOUN OLH THN PLHROFORIA //////////////////////////////
/////////////////////////////      KAI TA GRAFW STO output\       //////////////////////////////
//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\


System.out.println("To StereotypeVector exei: "+StereotypeVector.size()+"\n-----------------------");
(new File("Robotic_output\\StereotypeVector.txt")).delete(); 
for (int i=0; i<StereotypeVector.size(); i++){
try {
    BufferedWriter out = new BufferedWriter(new FileWriter("Robotic_output\\StereotypeVector.txt", true));
    Object temp=StereotypeVector.get(i);
    out.write(temp.toString()+"\n");
    out.close();
} catch (IOException e) {
System.out.println("Eskasa sto PropertyVector");
	}
}  



System.out.println("\n\nTo UserVector exei: "+UserTypeVector.size()+"\n-----------------------");
	
	(new File("Robotic_output\\UserVector.txt")).delete(); 
	for (int i=0; i<UserTypeVector.size(); i++){
	  try {
	        BufferedWriter out = new BufferedWriter(new FileWriter("Robotic_output\\UserVector.txt", true));
	        Object temp=UserTypeVector.get(i);
	        out.write(temp.toString()+"\n");
	        out.close();
	    } catch (IOException e) {
	    System.out.println("Eskasa sto UserVector");
	    	}
  }

System.out.println("\n\nTo PropertyVector exei: "+PropertiesVector.size()+"\n-----------------------");
	(new File("Robotic_output\\PropertyVector.txt")).delete(); 
	for (int i=0; i<PropertiesVector.size(); i++){
	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("Robotic_output\\PropertyVector.txt", true));
        Object temp=PropertiesVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto PropertyVector");
    	}
}  

	


System.out.println("\n\nTo ClassVector exei: "+ClassVector.size()+"\n-----------------------");
	(new File("Robotic_output\\ClassVector.txt")).delete(); 
	for (int i=0; i<ClassVector.size(); i++){
	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("Robotic_output\\ClassVector.txt", true));
        Object temp=ClassVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto ClassVector");
    	}	
}




*/

	
	
//########################################################################################    
//#### I HAVE THE NECESARY INFORMATION, I START TO FILL DATABASE TABLES ##################
//########################################################################################
    

  
	
	
	DBLoad b=new DBLoad(username, password, xeniosPath);
	
    
	b.DepositStereotypes(StereotypeVector);
    b.DepositProperties(PropertiesVector);
    b.DepositUserTypeProprties(UserTypeVector);
    b.DepositClassProperties(ClassVector);
              

    }//void parseConcatenated

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
