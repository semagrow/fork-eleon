package gr.demokritos.iit.eleon.authoring;

import java.util.Hashtable;
import java.util.Vector;

public class TemplateVector extends Vector
{
	public TemplateVector() 
	{
		Hashtable englishValues = new Hashtable();
		Hashtable italianValues = new Hashtable();
		Hashtable greekValues = new Hashtable();
		addElement(englishValues);
		addElement(italianValues);
		addElement(greekValues);
	}

}
