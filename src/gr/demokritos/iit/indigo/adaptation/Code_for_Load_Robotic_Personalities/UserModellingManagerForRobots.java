package gr.demokritos.iit.indigo.adaptation.Code_for_Load_Robotic_Personalities;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.iterator.*;
import com.hp.hpl.jena.datatypes.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatchFilter;

import java.io.*;
import java.util.List;


public class UserModellingManagerForRobots extends NLGFileManagerForRobots 
{
        
    // resources
    public static final String UserModellingRes = "UserModelling";
    public static final String UserTypeRes = "UserType";
    public static final String PropertyRes = "Property";
    public static final String MicroplanRes = "Microplan";
        
    // properties
    public static final String UserTypesPrp = "UserTypes";
      
    
    public static final String AppropriatenessPrp= "Appropriateness";
    public static final String AppropPrp = "Approp";
    
    public static final String forUserTypePrp = "forUserType";
    public static final String AppropValuePrp = "AppropValue";    
    
    public static final String forInstancePrp = "forInstance";
    public static final String forOwlClassPrp = "forOwlClass";
    
    public static final String InterestValuePrp = "InterestValue";
    public static final String RepetitionsValuePrp = "Repetitions";
    
    
    public static final String PropertiesInterestsRepetitionsPrp = "PropertiesInterestsRepetitions";
    
    public static final String DPInterestRepetitionsPrp = "DPInterestRepetitions";
    public static final String CDPInterestRepetitionsPrp = "CDPInterestRepetitions";
    public static final String IPInterestRepetitionsPrp = "IPInterestRepetitions";
                    
    
    public static final String ClassInterestsRepetitionsPrp = "ClassInterestsRepetitions";   
    
    public static final String MaxFactsPerSentencePrp = "MaxFactsPerSentence";
    public static final String SynthesizerVoicePrp = "SynthesizerVoice";
    public static final String FactsPerPagePrp = "FactsPerPage";
    
    public static final String DInterestRepetitionsPrp = "DInterestRepetitions";
    public static final String IInterestRepetitionsPrp = "IInterestRepetitions";
    
    
//  orizw ta tags gia na diavazw apo to RoboticUserModeling  
    public static final String RobotTypesPrp = "RobotTypes";  
    
//  orizw ta labels twn 5 tags tou Robotic-Modeling
    public static final String Openness = "Openness";
    public static final String Conscientiousness = "Conscientiousness";
    public static final String Extraversion = "Extraversion";
    public static final String Agreeableness = "Agreeableness";
    public static final String NaturalReactions = "NaturalReactions";
    
    
    //Gia ta tags pou sxetizontai me tin katalilotita twn properties tou Robotic Modeling
    public static final String RobotsPreference = "RobotsPreference";
    
    public static final String DPPreference = "DPPreference";
    public static final String Preference = "Preference";
    
    public static final String CDPPreference = "CDPPreference";
    public static final String PreferenceValue = "PreferenceValue";
    
    public static final String IPPreference = "IPPreference";
    
    
    
    
    public static final String ClassPreference = "ClassPreference";
    
    public static final String DPreference = "DPreference";
    
    public static final String IPreference = "IPreference";
    
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  
    
    
    
    
    // properties
    public Property UserTypesProperty = model.createProperty(owlnlNS + this.UserTypesPrp);
    public Property RobotTypesProperty = model.createProperty(owlnlNS + this.RobotTypesPrp);
    
    public Property AppropriatenessProperty = model.createProperty(owlnlNS + this.AppropriatenessPrp);
    public Property AppropProperty = model.createProperty(owlnlNS + this.AppropPrp);
    public Property forUserTypeProperty = model.createProperty(owlnlNS + this.forUserTypePrp);
    public Property AppropValueProperty = model.createProperty(owlnlNS + this.AppropValuePrp);
   
    public Property forInstanceProperty =  model.createProperty(owlnlNS + this.forInstancePrp);
        
    public Property  DPInterestRepetitionsProperty = model.createProperty(owlnlNS + this.DPInterestRepetitionsPrp);
    public Property  CDPInterestRepetitionsProperty = model.createProperty(owlnlNS + this.CDPInterestRepetitionsPrp);
    public Property  IPInterestRepetitionsProperty = model.createProperty(owlnlNS + this.IPInterestRepetitionsPrp);
    
    public Property  InterestValueProperty = model.createProperty(owlnlNS + this.InterestValuePrp);        
    public Property  RepetitionsValueProperty = model.createProperty(owlnlNS + this.RepetitionsValuePrp);
    
    public Property forOwlClassProperty = model.createProperty(owlnlNS + this.forOwlClassPrp);
    public Property PropertiesInterestsRepetitionsProperty = model.createProperty(owlnlNS + this.PropertiesInterestsRepetitionsPrp);
    
    
    public Property MaxFactsPerSentenceProperty = model.createProperty(owlnlNS + this.MaxFactsPerSentencePrp);
    public Property SynthesizerVoiceProperty = model.createProperty(owlnlNS + this.SynthesizerVoicePrp);
    public Property FactsPerPageProperty = model.createProperty(owlnlNS + this.FactsPerPagePrp);
    
    
    
    
    
    //orizw ta URI 5 tags tou Robotic-Modeling
    public Property Openness_tag = model.createProperty(owlnlNS + this.Openness);
    public Property Conscientiousness_tag = model.createProperty(owlnlNS + this.Conscientiousness);
    public Property Extraversion_tag = model.createProperty(owlnlNS + this.Extraversion);
    public Property Agreeableness_tag = model.createProperty(owlnlNS + this.Agreeableness);
    public Property NaturalReactions_tag = model.createProperty(owlnlNS + this.NaturalReactions);
    
    
    
    public Property RobotsPreference_tag = model.createProperty(owlnlNS + this.RobotsPreference);
    
    public Property DPPreference_tag = model.createProperty(owlnlNS + this.DPPreference);
    public Property Preference_tag = model.createProperty(owlnlNS + this.Preference);
    
    public Property CDPPreference_tag = model.createProperty(owlnlNS + this.CDPPreference);
    public Property PreferenceValue_tag = model.createProperty(owlnlNS + this.PreferenceValue);
    
    public Property IPPreference_tag = model.createProperty(owlnlNS + this.IPPreference);
    
    
    
    public Property ClassPreference_tag = model.createProperty(owlnlNS + this.ClassPreference);
    
    public Property DPreference_tag = model.createProperty(owlnlNS + this.DPreference);
    
    public Property IPreference_tag = model.createProperty(owlnlNS + this.IPreference);
    
    
    
    
    
    
    
    
    public Property ClassInterestsRepetitionsProperty = model.createProperty(owlnlNS + this.ClassInterestsRepetitionsPrp);
    
    public Property DInterestRepetitionsProperty = model.createProperty(owlnlNS + this.DInterestRepetitionsPrp);
    public Property IInterestRepetitionsProperty = model.createProperty(owlnlNS + this.IInterestRepetitionsPrp);
    
    
    public Resource MicroplansAndOrderingResourceType = null;
    
   
         
    //----------------------------------------------------------------------------
    public UserModellingManagerForRobots(String xb){
        super(xb);     
    }

    //----------------------------------------------------------------------------

    
    public static void main(String args[]){//main

    }
}