package gr.demokritos.iit.eleon.authoring;

import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import javax.swing.*;

public class IconOwlData extends IconData{

    protected OntClass owlClass;

    public IconOwlData(Icon icon, Icon openIcon, Object data, OntClass ontClass) {
        super(icon, openIcon, data);
        owlClass = ontClass;
    }

    public IconOwlData(Icon icon, Object data, OntClass ontClass) {
        super(icon, data);
        owlClass = ontClass;
    }

    public OntClass getOntClass(){
        return owlClass;
    }
}
