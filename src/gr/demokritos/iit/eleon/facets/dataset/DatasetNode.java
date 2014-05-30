/***************

<p>Title: Dataset Node</p>

<p>Description:
This is the base class for all node types defined in this package. 
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.
<br> Copyright (c) 2001-2014 National Centre for Scientific Research "Demokritos"
</p>

<p>
ELEON is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
</p>

<p>
ELEON is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
</p>

<p>
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
</p>

@author Giannis Mouchakis (SemaGrow 2014)

***************/


package gr.demokritos.iit.eleon.facets.dataset;

import gr.demokritos.iit.eleon.facets.TreeFacetNode;


public class DatasetNode implements TreeFacetNode
{
	
	public Object[][] property_values = new Object[2][20];
	static public String[][] property_qnames = new String[2][20];
	static public String[][] property_uris = new String[2][20];
	static public Object[][] property_value_types = new Object[2][20];
	static public boolean[][] property_is_functional = new boolean[2][20];
	static public String[] annotation_schema_names = {"VoID", "VoID/SemaGrow extension"};
	static {
		// Shared Void and Sevod properties
		int i=0;
		property_qnames[0][i] = "dc:creator";
		property_uris[0][i] = "http://purl.org/dc/terms/creator";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		
		++i;
		property_qnames[0][i] = "dc:title";
		property_uris[0][i] = "http://purl.org/dc/terms/title";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		
		++i;
		property_qnames[0][i] = "void:sparqlEndpoint";
		property_uris[0][i] = "http://rdfs.org/ns/void#sparqlEndpoint";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];

		++i;
		property_qnames[0][i] = "void:vocabulary";
		property_uris[0][i] = "http://rdfs.org/ns/void#vocabulary";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = false;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];

		++i;
		property_qnames[0][i] = "void:class";
		property_uris[0][i] = "http://rdfs.org/ns/void#class";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];

		++i;
		property_qnames[0][i] = "void:property";
		property_uris[0][i] = "http://rdfs.org/ns/void#property";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];

		++i;
		property_qnames[0][i] = "void:triples";
		property_uris[0][i] = "http://rdfs.org/ns/void#triples";
		property_value_types[0][i] = Integer.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];

		++i;
		property_qnames[0][i] = "void:distinctSubjects";
		property_uris[0][i] = "http://rdfs.org/ns/void#distinctSubjects";
		property_value_types[0][i] = Integer.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];

		++i;
		property_qnames[0][i] = "void:distinctObjects";
		property_uris[0][i] = "http://rdfs.org/ns/void#distinctObjects";
		property_value_types[0][i] = Integer.class;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];

		/*
		++i;
		property_qnames[0][i] = "";
		property_uris[0][i] = "http://rdfs.org/ns/void#";
		property_value_types[0][i] = ;
		property_is_functional[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		 */

		++i;
		// End of VoID
		property_qnames[0][i] = null;
		property_uris[0][i] = null;
		property_value_types[0][i] = null;
		// Sevod extension
		property_qnames[1][i] = "svd:selectivity";
		property_uris[1][i] = "http://rdf.iit.demokritos.gr/2013/sevod#selectivity";
		property_value_types[1][i] = Integer.class;
		property_is_functional[1][i] = true;	
		
		++i;
		property_qnames[1][i] = "svd:cardinality";
		property_uris[1][i] = "http://rdf.iit.demokritos.gr/2013/sevod#cardinality";
		property_value_types[1][i] = Integer.class;
		property_is_functional[1][i] = true;	
		
		++i;
		property_qnames[1][i] = null;
		property_uris[1][i] = null;
		property_value_types[1][i] = null;

	}
	
	private Integer void_triples = null;
	private Integer void_distinctSubjects = null;
	private Integer void_distinctObjects = null;
	private String void_sparqlEnpoint = null;
	private String dc_title = null;
	
	private final DatasetFacet myFacet;

	public DatasetNode( DatasetFacet myFacet, String annotationSchema )
	{
		super();
		this.myFacet = myFacet;
/*		int i = 0;
		for (String annotation_shema_main : annotation_schema_names ) {
			if (annotation_shema_main.equals(annotationSchema)) {
				annotationSchemaIndex = i;
				break;
			}
			i++;
		}
		assert annotationSchemaIndex != -1;*/
	}
	
	/**
	 * @param void_triples
	 * @param void_distinctSubjects
	 * @param void_distinctObjects
	 * @param dc_creator
	 * @param void_sparqlEnpoint
	 */
	public DatasetNode(  DatasetFacet myFacet,
			Integer void_triples, Integer void_distinctSubjects, Integer void_distinctObjects,
			String author, String void_sparqlEnpoint, String dc_title )
	{
		super();
		this.myFacet = myFacet;
		this.void_triples = void_triples;
		this.void_distinctSubjects = void_distinctSubjects;
		this.void_distinctObjects = void_distinctObjects;
		//property_values[annotationSchemaIndex][0] = author;//TODO: annotationSchemaIndex has not been set here!
		this.void_sparqlEnpoint = void_sparqlEnpoint;
		this.dc_title = dc_title;
	}
	
	public DatasetFacet getFacet()
	{ return this.myFacet; }

	public String getAuthor() {
		//return (String) property_values[annotationSchemaIndex][0];
		return (String) property_values[1][0];
	}
	
	public void setAuthor(String author) {
		//property_values[annotationSchemaIndex][0] = author;
		property_values[1][0] = author;
	}

	/*public int getAnnotationSchemaIndex() {
		return this.annotationSchemaIndex;
	}*/
	
	/**
	 * @return the void_triples
	 */
	public Integer getVoid_triples() {
		return void_triples;
	}

	/**
	 * @param void_triples the void_triples to set
	 */
	public void setVoid_triples(Integer void_triples) {
		this.void_triples = void_triples;
	}
	
	/**
	 * @return the void_distinctSubjects
	 */
	public Integer getVoid_distinctSubjects() {
		return void_distinctSubjects;
	}

	/**
	 * @param void_distinctSubjects the void_distinctSubjects to set
	 */
	public void setVoid_distinctSubjects(Integer void_distinctSubjects) {
		this.void_distinctSubjects = void_distinctSubjects;
	}

	/**
	 * @return the void_distinctObjects
	 */
	public Integer getVoid_distinctObjects() {
		return void_distinctObjects;
	}

	/**
	 * @param void_distinctObjects the void_distinctObjects to set
	 */
	public void setVoid_distinctObjects(Integer void_distinctObjects) {
		this.void_distinctObjects = void_distinctObjects;
	}

	/**
	 * @return the void_sparqlEnpoint
	 */
	public String getVoid_sparqlEnpoint() {
		return void_sparqlEnpoint;
	}

	/**
	 * @param void_sparqlEnpoint the void_sparqlEnpoint to set
	 */
	public void setVoid_sparqlEnpoint(String void_sparqlEnpoint) {
		this.void_sparqlEnpoint = void_sparqlEnpoint;
	}

	/**
	 * @return the dc_title
	 */
	public String getDc_title() {
		return dc_title;
	}

	/**
	 * @param dc_title the dc_title to set
	 */
	public void setDc_title(String dc_title) {
		this.dc_title = dc_title;
	}

	/**
	 * @return true if void_triples has been set, false if not
	 */
	public boolean hasVoid_triples() {
		if (void_triples != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return true if void_distinctSubjects has been set, false if not
	 */
	public boolean hasVoid_distinctSubjects() {
		if (void_distinctSubjects != null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return true if void_distinctObjects has been set, false if not
	 */
	public boolean hasVoid_distinctObjects() {
		if (void_distinctObjects != null) {
			return true;
		} else {
			return false;
		}
	}
/*
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[][] getProperty_values() {
		return this.property_values;
	}

	@Override
	public String[][] getProperty_names() {
		return DatasetNode.property_names;
	}

	@Override
	public Object[][] getProperty_value_types() {
		return DatasetNode.property_value_types;
	}

	@Override
	public boolean[][] getProperty_is_functional() {
		return DatasetNode.property_is_functional;
	}
	*/

}
