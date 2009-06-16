package gr.demokritos.iit.eleon.ui;

import java.util.Hashtable;
import java.util.Vector;

public class StoriesVector extends Vector 
{
	public StoriesVector() 
	{
		Hashtable englishStories = new Hashtable();
		Hashtable italianStories = new Hashtable();
		Hashtable greekStories = new Hashtable();
		englishStories.put("New-story", "");
		italianStories.put("New-story", "");
		greekStories.put("New-story", "");
		addElement(englishStories);
		addElement(italianStories);
		addElement(greekStories);
	}
}
