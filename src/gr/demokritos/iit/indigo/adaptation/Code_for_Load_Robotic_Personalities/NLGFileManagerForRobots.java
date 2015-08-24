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
