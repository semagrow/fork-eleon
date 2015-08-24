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


/***************

<p>Description:
This class represents filler types restricted to a closed set of resources.
</p>


***************/


package gr.demokritos.iit.eleon.annotations;

import gr.demokritos.iit.eleon.MainShell;

import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Resource;


public class NominalSet
{

	// All possible values
	public static String[] availableNomilas = {"class1" , "class2", "class3", "class4", "class5"};

	private static final String cropPref = "http://ontologies.seamless-ip.org/crop.owl#";
	public static final String[] cropClassNames = { "Crop", "CropGroup", "SimpleCropGroup",
		"ProductGroup", "ProductType", "Product", "CropProduct" };

	private static final String t4fPref = "http://www.semagrow.eu/schemas/t4f#";
	public static final String[] t4fClassNames = { "Sensor", "SensorMeasurement" };
	
	private static final String organicEdunetPref = "http://data.organic-edunet.eu/";
	public static final String[] organicEdunetNames = { "Annotation", "Classification", "Contribution", "DateTime" , 
		"Duration" , "Educational" , "Identifier" , "LangString" , "LearningObject" , "LifeCycleContribution" ,	"MetaMetadataContribution" ,
		"Relation" , "SingleTechnicalRequirement" , "Taxon" , "TaxonPath" , "TechnicalRequirement" , "VCard" };
	
	private static final String europeanaPref = "http://www.europeana.eu/schemas/edm/";
	public static final String[] europeanaClassNames = { "Agent", "EuropeanaAggregation", "EuropeanaObject", "Event", "InformationResource",
		"NonInformationResource", "PhysicalThing", "Place", "ProvidedCHO", "TimeSpan", "WebResource" };

	private static final String naturalEuropePref = "http://www.natural-europe.eu/ontology#" ;
	public static final String[] naturalEuropeNames = { "3D", "CHO", "Collection", "DigitalObject", "Image", "Observation", "Sound",
		"Species", "Specimen", "Text", "Unit", "Video" };
	
	public static final Resource[] cropClasses;
	public static final Resource[] t4fClasses;
	public static final Resource[] organicEdunetClasses;
	public static final Resource[] europeanaClasses;
	public static final Resource[] naturalEuropeClasses;
	
	static {
		cropClasses = new Resource[ cropClassNames.length ];
		for( int i=0; i<cropClassNames.length; ++i ) {
			Resource r = MainShell.shell.data.createResource( cropPref + cropClassNames[i] );
			cropClasses[i] = r;
		}
		
		t4fClasses = new Resource[ t4fClassNames.length ];
		for( int i=0; i<t4fClassNames.length; ++i ) {
			Resource r = MainShell.shell.data.createResource( t4fPref + t4fClassNames[i] );
			t4fClasses[i] = r;
		}
		
		organicEdunetClasses = new Resource[ organicEdunetNames.length ];
		for( int i=0; i<organicEdunetNames.length; ++i ) {
			Resource r = MainShell.shell.data.createResource( organicEdunetPref + organicEdunetNames[i] );
			organicEdunetClasses[i] = r;
		}
		
		europeanaClasses = new Resource[ europeanaClassNames.length ];
		for( int i=0; i<europeanaClassNames.length; ++i ) {
			Resource r = MainShell.shell.data.createResource( europeanaPref + europeanaClassNames[i] );
			europeanaClasses[i] = r;
		}
		
		naturalEuropeClasses = new Resource[ naturalEuropeNames.length ];
		for( int i=0; i<naturalEuropeNames.length; ++i ) {
			Resource r = MainShell.shell.data.createResource( naturalEuropePref + naturalEuropeNames[i] );
			naturalEuropeClasses[i] = r;
		}
	}

	
	// Current selection
	List<Resource> containingNominals = new ArrayList<Resource>();
	
	public NominalSet()
	{
		super();
	}

	
	/*
	 * Object IMPLEMENTATION
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{ 
		String str = null;
		if (containingNominals.isEmpty()) {
			str = "[]";
		} else {
			str = "[";
			for (int i=0; i<containingNominals.size()-1; i++) {
				str += containingNominals.get(i).getLocalName() + ", ";
			}
			str += containingNominals.get(containingNominals.size()-1).getLocalName() + "]";
		}
		return str; 
	}
	

	
	/*
	 * Specifics
	 */
	
	public void setContainingNominals( List<Resource> nominals_to_set )
	{
		this.containingNominals.clear();
		this.containingNominals.addAll(nominals_to_set);
	}
	
	public List<Resource> getContainingNominals()
	{
		return containingNominals;
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
