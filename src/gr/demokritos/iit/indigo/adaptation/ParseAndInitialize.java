package gr.demokritos.iit.indigo.adaptation;
 
import java.io.*;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.Iterator;
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

import gr.aueb.cs.nlg.NLFiles.*;
        
public class ParseAndInitialize {
	
	private String file;
	private String path;
	
	private String username;	//for the connection with the mysql DB 
    private String password;
    private String xeniosPath;	       //the path for Xenios DB, which is like: "jdbc:mysql://localhost/xenios"
    private String PerServerPath;	   // the path for PerServer like above ...
	
	private Vector allInstances=new Vector();
	private Vector UserTypeVector=new Vector();
	private Vector PropertiesVector=new Vector();  
	private Vector RepetitionVector=new Vector();
	private Vector MicroplanVector=new Vector();  
	private Vector ClassVector=new Vector();
	private Vector RepetitionClassVector=new Vector();
	private Vector forPerServerVector=new Vector();
	private Vector StereotypeVector=new Vector();
	
	 //constructor
    public ParseAndInitialize(String file, String path, String username, String password, String xeniosPath, String PerServerPath) {
        this.file = file;
        this.path = path;
        this.username=username;
        this.password=password;
        this.xeniosPath=xeniosPath;
        this.PerServerPath=PerServerPath;
    
    }
	
	
	
	
	// load user modelling info from rdf file
    public void parseConcatenated(){
        
       
        
        // read file
        UserModellingManager UMM = new UserModellingManager("");
        UMM.read(path,file);
                   
        // load user types parameters
        ExtendedIterator userTypes = UMM.get(UMM.UserTypesProperty);
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
                       
            String UserType = currentUserType.getURI();
            String temp=UserType;  temp=temp.substring(temp.indexOf('#')+1,temp.length());
            conTemp=temp;
            if(!StereotypeVector.contains(conTemp)){StereotypeVector.addElement(conTemp);}
     
            
            String MFPS = currentUserType.getProperty(UMM.MaxFactsPerSentenceProperty).getLiteral().getString();
            conUsersType=conTemp.concat("#MFPS->"+MFPS); 
            //conUsersType="('MFPS','"+temp+"',"+MFPS+",NULL,NULL)";
            UserTypeVector.addElement(conUsersType);
            	
            //If Voice Sythesizer is male->SVnum=1, female->SVnum=2, child->SVnum=3
            String SV = currentUserType.getProperty(UMM.SynthesizerVoiceProperty).getLiteral().getString();
            int SVnum; 
            if (SV.equals("male")) {SVnum=1;} else if (SV.equals("female")){SVnum=2;} else SVnum=3;
            conUsersType=conTemp.concat("#SV->"+SVnum);
            UserTypeVector.addElement(conUsersType);
           
            
            String FPS = currentUserType.getProperty(UMM.FactsPerPageProperty).getLiteral().getString();
            conUsersType=conTemp.concat("#FPS->"+FPS); 
            UserTypeVector.addElement(conUsersType);
              
			
        }



        // load repetitions and interest
        ExtendedIterator prps = UMM.get(UMM.PropertiesInterestsRepetitionsProperty);
        Model m = UMM.getModel();


        double CDPIassimRate=0,IPIassimRate=0,DPIassimRate=0;
        double CDPIassimScore=0,IPIassimScore=0,DPIassimScore=0;
        
    //for each property
    while(prps.hasNext()){//for each property
    Resource currentProp = (Resource)prps.next();
    
    String currentPropURI = currentProp.getURI();             
    String temp=currentPropURI;  temp=temp.substring(temp.indexOf('#')+1,temp.length());
    conProperty=temp;
                    
    // load default property repetitions-interests 
    StmtIterator iter = null;
    iter = m.listStatements(currentProp, UMM.DPInterestRepetitionsProperty, (RDFNode)null);
    
    
    Hashtable DPI=new Hashtable();
    Hashtable CDPI=new Hashtable();
    
    while(iter.hasNext()){
   	    Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp1=UT;  temp1=temp1.substring(temp1.indexOf('#')+1,temp1.length());                 
        conUser=temp1;
        
        //load repetition
        String rep = bNode.getProperty(UMM.RepetitionsValueProperty).getLiteral().getString();
        
        //if repetion=0 ->assimScore=1,assimRate=0 else assimScore=0,assimRate=1/repetions
        DPIassimScore=Double.parseDouble(rep);
        if(DPIassimScore==0){DPIassimScore=1; DPIassimRate=0;}
        else{DPIassimRate=1/DPIassimScore;  DPIassimScore=0; }
        
        DPI.put(conUser+"#Score", DPIassimScore);
        DPI.put(conUser+"#Rate", DPIassimRate);
        
        conTempProperty=conUser; 
        conTempProperty=conTempProperty.concat("#"+conProperty+"->"+DPIassimScore+"&"+DPIassimRate);
        if(!RepetitionVector.contains(conTempProperty)){RepetitionVector.addElement(conTempProperty);}
    	
        //load interest
        String Interest = bNode.getProperty(UMM.InterestValueProperty).getLiteral().getString();
        conTempProperty=conUser;
        conTempProperty=conTempProperty.concat("#"+conProperty+"->"+Interest);
        if(!PropertiesVector.contains(conTempProperty)){PropertiesVector.addElement(conTempProperty);}
    }
    
    // load default class repetitions-interests
    iter =  m.listStatements(currentProp , UMM.CDPInterestRepetitionsProperty, (RDFNode)null);
                 
    while(iter.hasNext())
    {                 
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
        
        String forOwlClass =  ((Resource)bNode.getProperty(UMM.forOwlClassProperty).getObject().as(Resource.class)).getURI();
        String temp1=forOwlClass;  temp1=temp1.substring(temp1.indexOf('#')+1,temp1.length());                 
        
        
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp2=UT;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());                 
              
         
//      load Repetition
        String rep = bNode.getProperty(UMM.RepetitionsValueProperty).getLiteral().getString();                                  
        CDPIassimScore=Double.parseDouble(rep);
        if(CDPIassimScore==0){CDPIassimScore=1; CDPIassimRate=0;}
        else{CDPIassimRate=1/CDPIassimScore;  CDPIassimScore=0; }
        
        CDPI.put(temp2+"#Score", CDPIassimScore);
        CDPI.put(temp2+"#Rate", CDPIassimRate);
        
        conTempProperty=temp2+"#"+conProperty+"."+temp1+"->"+CDPIassimScore+"&"+CDPIassimRate;
        if(!RepetitionVector.contains(conTempProperty)){RepetitionVector.addElement(conTempProperty);}
    
        //load Interest
        String Interest = bNode.getProperty(UMM.InterestValueProperty).getLiteral().getString(); 
        conTempProperty=temp2+"#"+conProperty+"."+temp1+"->"+Interest;
        if(!PropertiesVector.contains(conTempProperty)){PropertiesVector.addElement(conTempProperty);}
    
    }    
    
    // load instances repetitions-interests
    iter =  m.listStatements(currentProp , UMM.IPInterestRepetitionsProperty, (RDFNode)null);
    
    while(iter.hasNext())
    {
        
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
        
        String forInstance =  ((Resource)bNode.getProperty(UMM.forInstanceProperty).getObject().as(Resource.class)).getURI();
        String temp1=forInstance;  temp1=temp1.substring(temp1.indexOf('#')+1,temp1.length());                 
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp2=UT;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());                 
        
        //load Repetition
        String rep = bNode.getProperty(UMM.RepetitionsValueProperty).getLiteral().getString();
        IPIassimScore=Double.parseDouble(rep);
        //if is -1(which means default value) take CDPI AssimRate
        if(IPIassimScore==-1){IPIassimRate=(Double)CDPI.get(temp2+"#Rate"); 
        					  IPIassimScore=(Double)CDPI.get(temp2+"#Score");
        }else if(IPIassimScore==0){IPIassimScore=1; IPIassimRate=0;
        }else{IPIassimRate=1/IPIassimScore;  IPIassimScore=0; }
        	
        
        conTempProperty=temp2+"#"+conProperty+"."+temp1+"->"+IPIassimScore+"&"+IPIassimRate;                     
        if(!RepetitionVector.contains(conTempProperty)){RepetitionVector.addElement(conTempProperty);}           
    
        //load Interest
        String Interest = bNode.getProperty(UMM.InterestValueProperty).getLiteral().getString();
        conTempProperty=temp2+"#"+conProperty+"."+temp1+"->"+Interest;                     
        if(!PropertiesVector.contains(conTempProperty)){PropertiesVector.addElement(conTempProperty);}   
    }

 }//for each property



 ExtendedIterator Approps = UMM.get(UMM.AppropriatenessProperty);          
 
 while(Approps.hasNext())
 {
    Resource microplan = (Resource)Approps.next();             
    String micropanURI = microplan.getURI();

                
    StmtIterator iter =  m.listStatements( microplan , UMM.AppropProperty, (RDFNode)null);

    String temp3=micropanURI;  temp3=temp3.substring(temp3.indexOf('#')+1,temp3.length());
    conTempMicroplan=temp3;
    
    while(iter.hasNext())
    {
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
                         
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp2=UT;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());
         
        String appr = bNode.getProperty(UMM.AppropValueProperty).getLiteral().getString();
        
        conMicroplan=temp2+"#"+conTempMicroplan+"->"+appr;
        if(!MicroplanVector.contains(conMicroplan)){MicroplanVector.addElement(conMicroplan);}
    }
    

               
 }
          
                      
//----------------------------------------------------------

//load class repetition-Interest
ExtendedIterator ClassRepIter = UMM.get(UMM.ClassInterestsRepetitionsProperty);

double DefCLASSassimRate=0,CLASSassimRate=0;
double DefCLASSassimScore=0,CLASSassimScore=0;

while(ClassRepIter.hasNext())
{
    Resource classRes = (Resource)ClassRepIter.next();
           
    String classResURI = classRes.getURI();
    String temp=classResURI;  temp=temp.substring(temp.indexOf('#')+1,temp.length());
    conClass=temp;
    
    // load defaults Class Repetions-Interests
    StmtIterator iter =  m.listStatements( classRes , UMM.DInterestRepetitionsProperty, (RDFNode)null);

    Hashtable DefClass=new Hashtable();
    
    while(iter.hasNext())
    {                 
    
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
                         
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp2=UT;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());
     
        //Load Repetition
        String rep = bNode.getProperty(UMM.RepetitionsValueProperty).getLiteral().getString();
        
//      if repetion=0 ->assimScore=1,assimRate=0 else assimScore=0,assimRate=1/repetions
        DefCLASSassimScore=Double.parseDouble(rep);
        if(DefCLASSassimScore==0){DefCLASSassimScore=1; DefCLASSassimRate=0;}
        else{DefCLASSassimRate=1/DefCLASSassimScore;  DefCLASSassimScore=0; }
        
        DefClass.put(temp2+"#Score", DefCLASSassimScore);
        DefClass.put(temp2+"#Rate", DefCLASSassimRate);
        
        conTempClass=temp2; 
        conTempClass=conTempClass.concat("#"+conClass+"->"+DefCLASSassimScore+"&"+DefCLASSassimRate);
        if(!RepetitionClassVector.contains(conTempClass)){RepetitionClassVector.addElement(conTempClass);}
    
        //Load Interest
        String Interest = bNode.getProperty(UMM.InterestValueProperty).getLiteral().getString();
        conTempClass=temp2+"#"+conClass+"->"+Interest;
        if(!ClassVector.contains(conTempClass)){ClassVector.addElement(conTempClass);}
    }
    
    
    
    // load Instance rep
    iter =  m.listStatements( classRes , UMM.IInterestRepetitionsProperty, (RDFNode)null);
    
    while(iter.hasNext())
    {                              
        Statement t = iter.nextStatement();
        Resource bNode = (Resource)t.getObject().as(Resource.class);
                         
        String forInstance = ((Resource)bNode.getProperty(UMM.forInstanceProperty).getObject().as(Resource.class)).getURI();
        String temp2=forInstance;  temp2=temp2.substring(temp2.indexOf('#')+1,temp2.length());
         
        
//      take all unique instances
        if(!allInstances.contains(temp2)){allInstances.addElement(temp2);}
        
        String UT = ((Resource)bNode.getProperty(UMM.forUserTypeProperty).getObject().as(Resource.class)).getURI();
        String temp3=UT;  temp3=temp3.substring(temp3.indexOf('#')+1,temp3.length());                 
        
        
        //Load Repetition
        String rep = bNode.getProperty(UMM.RepetitionsValueProperty).getLiteral().getString();
        if (rep.equals("")) {rep="-1";}
        
       CLASSassimScore=Double.parseDouble(rep);
        //if is null(which means default value) take DefCLASS AssimRate
        if(CLASSassimScore==-1){CLASSassimRate=(Double)DefClass.get(temp3+"#Rate"); 
        			CLASSassimScore=(Double)DefClass.get(temp3+"#Score");
        }else if(CLASSassimScore==0){CLASSassimScore=1; CLASSassimRate=0;
        }else{CLASSassimRate=1/CLASSassimScore;  CLASSassimScore=0; }
        
        conTempClass=temp3+"#"+conClass+"."+temp2+"->"+CLASSassimScore+"&"+CLASSassimRate; 
        if(!RepetitionClassVector.contains(conTempClass)){RepetitionClassVector.addElement(conTempClass);}
    
    
        //Load Instance
        String Interest = bNode.getProperty(UMM.InterestValueProperty).getLiteral().getString();
        conTempClass=temp3+"#"+conClass+"."+temp2+"->"+Interest;
        if(!ClassVector.contains(conTempClass)){ClassVector.addElement(conTempClass);}
    
    
    }
}

//----------------------------------------------------------

//for PerServer all the information from paragraph-2 of the RDF (That for the Property info)
for (int i=0; i<PropertiesVector.size(); i++){
	Object tempor=PropertiesVector.get(i);
	String temp=tempor.toString();
	String outofinterest=temp.substring(0, temp.indexOf("->"));
	
	if(outofinterest.startsWith("Adult#")){
	outofinterest=outofinterest.substring(outofinterest.indexOf("Adult#")+6, outofinterest.length());
		if(!forPerServerVector.contains(outofinterest)){forPerServerVector.addElement(outofinterest);}
		}
} 

/*

//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\
///////////////////////////// TA VECTORS EXOUN OLH THN PLHROFORIA //////////////////////////////
/////////////////////////////      KAI TA GRAFW STO output\       //////////////////////////////
//\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\/\


System.out.println("To StereotypeVector exei: "+StereotypeVector.size()+"\n-----------------------");
(new File("output\\StereotypeVector.txt")).delete(); 
for (int i=0; i<StereotypeVector.size(); i++){
try {
    BufferedWriter out = new BufferedWriter(new FileWriter("output\\StereotypeVector.txt", true));
    Object temp=StereotypeVector.get(i);
    out.write(temp.toString()+"\n");
    out.close();
} catch (IOException e) {
System.out.println("Eskasa sto PropertyVector");
	}
}  



System.out.println("\n\nTo UserVector exei: "+UserTypeVector.size()+"\n-----------------------");
	
	(new File("output\\UserVector.txt")).delete(); 
	for (int i=0; i<UserTypeVector.size(); i++){
	  try {
	        BufferedWriter out = new BufferedWriter(new FileWriter("output\\UserVector.txt", true));
	        Object temp=UserTypeVector.get(i);
	        out.write(temp.toString()+"\n");
	        out.close();
	    } catch (IOException e) {
	    System.out.println("Eskasa sto UserVector");
	    	}
  }

System.out.println("\n\nTo PropertyVector exei: "+PropertiesVector.size()+"\n-----------------------");
	(new File("output\\PropertyVector.txt")).delete(); 
	for (int i=0; i<PropertiesVector.size(); i++){
	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("output\\PropertyVector.txt", true));
        Object temp=PropertiesVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto PropertyVector");
    	}
}  

	
System.out.println("\n\nTo RepetitionVector exei: "+RepetitionVector.size()+"\n-----------------------");
	(new File("output\\RepetitionVector.txt")).delete(); 
	for (int i=0; i<RepetitionVector.size(); i++){
	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("output\\RepetitionVector.txt", true));
        Object temp=RepetitionVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto RepetitionVector");
    	}
} 
	
	
System.out.println("\n\nTo MicroVector exei: "+MicroplanVector.size()+"\n-----------------------");
	(new File("output\\MicroVector.txt")).delete(); 
	for (int i=0; i<MicroplanVector.size(); i++){
	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("output\\MicroVector.txt", true));
        Object temp=MicroplanVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto MicroVector");
    	}
} 


System.out.println("\n\nTo ClassVector exei: "+ClassVector.size()+"\n-----------------------");
	(new File("output\\ClassVector.txt")).delete(); 
	for (int i=0; i<ClassVector.size(); i++){
	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("output\\ClassVector.txt", true));
        Object temp=ClassVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto ClassVector");
    	}	
}

//-------------------------------------------------------
	System.out.println("\n\nTo RepetitionClassVector exei: "+RepetitionClassVector.size()+"\n-----------------------");
	(new File("output\\RepetitionClassVector.txt")).delete(); 
	for (int i=0; i<RepetitionClassVector.size(); i++){
	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("output\\RepetitionClassVector.txt", true));
        Object temp=RepetitionClassVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto RepetitionClassVector");
    	}
} 

//------------------------------------------------------	

	System.out.println("\n\nTo UniqueInstVector exei: "+allInstances.size()+"\n-----------------------");
	(new File("output\\UniqueInstVector.txt")).delete();	
	for (int i=0; i<allInstances.size(); i++){

	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("output\\UniqueInstVector.txt", true));
        Object temp=allInstances.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto UniqueInstVector");
    	}
	}   

	
System.out.println("\n\nTo forPerServerVector exei: "+forPerServerVector.size()+"\n-----------------------");
	(new File("output\\forPerServerVector.txt")).delete();	
	for (int i=0; i<forPerServerVector.size(); i++){
	//System.out.println(forPerServerVector.get(i));

	try {
        BufferedWriter out = new BufferedWriter(new FileWriter("output\\forPerServerVector.txt", true));
        Object temp=forPerServerVector.get(i);
        out.write(temp.toString()+"\n");
        out.close();
    } catch (IOException e) {
    System.out.println("Eskasa sto forPerSeverVector");
    }  
	
	}

	*/
	
//########################################################################################    
//#### I HAVE THE NECESARY INFORMATION, I START TO FILL DATABASE TABLES ##################
//########################################################################################
    

  
	
	DBInitialize b=new DBInitialize();
	b.Initialize(username, password, xeniosPath);
    
	b.DepositStereotypes(StereotypeVector);
    b.DepositProperties(PropertiesVector);
    b.DepositUserTypeProprties(UserTypeVector);
    b.DepositMicroplans(MicroplanVector);
    b.DepositClassProperties(ClassVector);
    b.DepositInstances(allInstances);
    b.DepositAssimilationRates(RepetitionVector);
    b.DepositClassAssimilationRates(RepetitionClassVector);
    
    InitializePerServer a=new InitializePerServer();
    a.InitializePSever(username, password, PerServerPath);
    a.DepositUpFeatures(forPerServerVector);
    a.DepositStereotypes(StereotypeVector);
    a.DepositInstances(allInstances);
    a.DepositStereotypeProfilesProperties(StereotypeVector, forPerServerVector);
    a.DepositStereotypeProfilesInstances(StereotypeVector, allInstances);

    

    }//void parseConcatenated

    


}//end of class
