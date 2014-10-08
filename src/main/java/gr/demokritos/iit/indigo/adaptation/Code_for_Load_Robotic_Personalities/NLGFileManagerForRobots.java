package gr.demokritos.iit.indigo.adaptation.Code_for_Load_Robotic_Personalities;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;
import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.util.iterator.*;
import com.hp.hpl.jena.datatypes.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatchFilter;
import org.w3c.dom.*;
import java.io.*;
import java.util.List;



public class NLGFileManagerForRobots
{
    public Model model = null;
    public Resource [] prettyTypes;
    public RDFWriter NLGWriter;
    public RDFReader NLGReader;
    
    public static String owlnlNS = "http://www.owlnl.com/owlnl#";
    public String xmlbase = "";
    public String namespace = "";
    //public String path = "file:C:/Documents and Settings/galanisd/��������� ��������/rdf_Files/";
    //----------------------------------------------------------------------------        
    public NLGFileManagerForRobots(String xb)
    {
        model = ModelFactory.createDefaultModel();
        xmlbase = xb;
        namespace = xb + "#";
        NLGReader = model.getReader("RDF/XML-ABBREV");
        NLGWriter = model.getWriter("RDF/XML-ABBREV");
                
    }
    
    //public String getPath(){
    //    return this.path;
    //}
    
    public Model getModel()
    {
        return this.model;
    }
    
    public ExtendedIterator get(Property p )
    {
        StmtIterator iter = model.listStatements(null, p, (RDFNode)null);
        
        if(iter != null && iter.hasNext())
        {
            RDFList userTypesList = (RDFList)iter.nextStatement().getObject().as(RDFList.class);
            return userTypesList.iterator();
        }
        else
        {
            return null;
        }
    }
    //----------------------------------------------------------------------------    
    public void setPrettyTypes(Resource [] pT)
    {
        prettyTypes = pT;
    }
    //----------------------------------------------------------------------------    
    public void read(String path, String RDFfile)
    {  
        //NLGReader.read(model, RDFfile);
        model = ModelFactory.createDefaultModel();
        File f = new File(path + RDFfile);
        
        if(f.exists())
            model.read("file:" + path + RDFfile);
        
    }
    
    public void read(String RDFfileAbsolutePath)
    {  
        //NLGReader.read(model, RDFfile);
        model = ModelFactory.createDefaultModel();
        model.read("file:" + RDFfileAbsolutePath);
    }    
    //----------------------------------------------------------------------------    
    public void write()
    {
                
        NLGWriter.setProperty("xmlbase", xmlbase);
        NLGWriter.setProperty("tab","4");
        //NLGWriter.setProperty("relativeURIs","");
        //NLGWriter.setProperty("blockRules", "propertyAttr");
        NLGWriter.setProperty("showXMLDeclaration","true");
        NLGWriter.setProperty("prettyTypes", prettyTypes);
        
        System.out.println("==RDF/XML serialization==");
        //NLGWriter.write(model,System.out,xmlbase);
        NLGWriter.write(model,System.out, xmlbase);
        
    }
        
    //----------------------------------------------------------------------------
    public void CloseModel()
    {
        model.close();
    }      
}