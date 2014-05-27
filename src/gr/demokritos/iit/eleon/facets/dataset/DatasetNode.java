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

public class DatasetNode {
	
	public Object[][] property_values = new Object[2][20];
	static public String[][] property_names = new String[2][20];
	static public Object[][] property_value_types = new Object[2][20];
	static public boolean[][] property_is_functional = new boolean[2][20];
	static public String[] annotation_schema_names = {"VoID", "VoID/SemaGrow extension"};
	static {
		property_names[0][0] = "dc:title";
		property_value_types[0][0] = String.class;
		property_is_functional[0][0] = true;

		property_names[0][1] = "void:endpoint";
		property_value_types[0][1] = String.class;
		property_is_functional[0][1] = true;
		
		property_names[0][2] = "void:vocabulary";
		property_value_types[0][2] = String.class;
		property_is_functional[0][2] = false;

		property_names[0][3] = "void:triples";
		property_value_types[0][3] = Integer.class;
		property_is_functional[0][3] = true;

		property_names[0][4] = "void:distinctSubjects";
		property_value_types[0][4] = Integer.class;
		property_is_functional[0][4] = true;

		property_names[0][5] = "void:distinctObjects";
		property_value_types[0][5] = Integer.class;
		property_is_functional[0][5] = true;
		
		property_names[0][6] = null;
		property_value_types[0][6] = null;
		//property_is_functional[0][6] = (Boolean) null;
		
		property_names[1][0] = "dc:title";
		property_value_types[1][0] = String.class;
		property_is_functional[1][0] = true;

		property_names[1][1] = "void:endpoint";
		property_value_types[1][1] = String.class;
		property_is_functional[1][1] = true;
		
		property_names[1][2] = "void:vocabulary";
		property_value_types[1][2] = String.class;
		property_is_functional[1][2] = false;

		property_names[1][3] = "void:triples";
		property_value_types[1][3] = Integer.class;
		property_is_functional[1][3] = true;

		property_names[1][4] = "void:distinctSubjects";
		property_value_types[1][4] = Integer.class;
		property_is_functional[1][4] = true;

		property_names[1][5] = "void:distinctObjects";
		property_value_types[1][5] = Integer.class;
		property_is_functional[1][5] = true;
		
		property_names[1][6] = null;
		property_value_types[1][6] = null;
		//property_is_functional[0][6] = (Boolean) null;
	}
	
	private Integer void_triples = null;
	private Integer void_distinctSubjects = null;
	private Integer void_distinctObjects = null;
	private String dc_creator = null;
	private String void_sparqlEnpoint = null;
	private String dc_title = null;
	
	/**
	 * 
	 */
	public DatasetNode() {
		super();
	}
	
	/**
	 * @param void_triples
	 * @param void_distinctSubjects
	 * @param void_distinctObjects
	 * @param dc_creator
	 * @param void_sparqlEnpoint
	 */
	public DatasetNode(Integer void_triples, Integer void_distinctSubjects,
			Integer void_distinctObjects, String dc_creator, String void_sparqlEnpoint, String dc_title) {
		super();
		this.void_triples = void_triples;
		this.void_distinctSubjects = void_distinctSubjects;
		this.void_distinctObjects = void_distinctObjects;
		this.dc_creator = dc_creator;
		this.void_sparqlEnpoint = void_sparqlEnpoint;
		this.dc_title = dc_title;
	}

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
	 * @return the dc_creator
	 */
	public String getDc_creator() {
		return dc_creator;
	}

	/**
	 * @param dc_creator the dc_creator to set
	 */
	public void setDc_creator(String dc_creator) {
		this.dc_creator = dc_creator;
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
	
	/**
	 * @return true if dc_creator has been set, false if not
	 */
	public boolean hasDc_creator() {
		if (dc_creator != null) {
			return true;
		} else {
			return false;
		}
	}

}
