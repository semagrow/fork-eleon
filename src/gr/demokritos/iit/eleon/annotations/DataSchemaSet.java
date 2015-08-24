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


package gr.demokritos.iit.eleon.annotations;

import gr.demokritos.iit.eleon.MainShell;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.rdf.model.Resource;



/**
 * @author Giannis Mouchakis
 *
 */
public class DataSchemaSet {
	
	public static final String schema_folder;
	
	static {
		
		//shema_folder
		String path = MainShell.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String jar_path = null;
		try {
			jar_path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		assert jar_path != null;
		String eleon_directory = (new File(jar_path)).getParent() + File.separator;
		schema_folder = eleon_directory + "resources" + File.separator + "schemas" + File.separator;
		
	}
	
	public static final String bibo = "bibo";
	public static final String crop = "crop";
	public static final String dct = "dcterms";
	public static final String europeana = "europeana";
	public static final String foaf = "foaf";
	public static final String natural_europe = "natural europe";
	public static final String organic_edunet = "organic edunet";
	public static final String skos = "skos";
	public static final String t4f = "trees 4 future";
	public static final String voar = "voa3r";
	public static final String rdf_syntax_ns_22 = "22-rdf-syntax-ns";
	public static final String rdf_schema = "rdf-schema";
	public static final String dcelements = "dcelements";
	public static final String eurocris_ontologies_cerif_1_3 = "eurocris_ontologies_cerif_1.3";
	public static final String owl = "owl";
	
	
	public static final String[] schemaLabels = { 
		bibo,
		crop,
		dct,
		europeana,
		foaf,
		natural_europe,
		organic_edunet,
		skos,
		t4f,
		voar,
		rdf_syntax_ns_22,
		rdf_schema,
		dcelements,
		eurocris_ontologies_cerif_1_3,
		owl
		};
	
	public static final Resource[] schemaURIs = { 
		MainShell.shell.data.createResource("http://purl.org/ontology/bibo/"),
		MainShell.shell.data.createResource("http://ontologies.seamless-ip.org/crop.owl"),
		MainShell.shell.data.createResource("http://purl.org/dc/terms/"),
		MainShell.shell.data.createResource("https://raw.githubusercontent.com/europeana/corelib/master/corelib-solr-definitions/src/main/resources/eu/rdf/edm.owl"),
		MainShell.shell.data.createResource("http://xmlns.com/foaf/0.1/"),
		MainShell.shell.data.createResource("http://other.collections.natural-europe.eu/ne-ontology-v01.owl"),
		MainShell.shell.data.createResource("http://data.organic-edunet.eu/lom_ontology_organicEdunet.owl"),
		MainShell.shell.data.createResource("http://www.w3.org/2004/02/skos/core"),
		MainShell.shell.data.createResource("http://rdf.demokritos.gr/2014/t4f"),
		MainShell.shell.data.createResource("https://raw.githubusercontent.com/davidmartinmoncunill/voa3r/master/etc/resourceont.owl"),
		MainShell.shell.data.createResource("http://www.w3.org/1999/02/22-rdf-syntax-ns"),
		MainShell.shell.data.createResource("http://www.w3.org/2000/01/rdf-schema#"),
		MainShell.shell.data.createResource("http://purl.org/dc/elements/1.1/"),
		MainShell.shell.data.createResource("http://www.eurocris.org/ontologies/cerif/1.3#"),
		MainShell.shell.data.createResource("http://www.w3.org/2002/07/owl#")
		};
	
	public static final File[] schemaFiles = { 
		new File (schema_folder + "bibo.rdf"),
		new File (schema_folder + "crop.owl"),
		new File (schema_folder + "dcterms.rdf"),
		new File (schema_folder + "edm.owl"),
		new File (schema_folder + "foaf.rdf"),
		new File (schema_folder + "ne-ontology-v01.owl"),
		new File (schema_folder + "organicEdunet.owl"),
		new File (schema_folder + "skos.rdf"),
		new File (schema_folder + "t4f.owl"),
		new File (schema_folder + "voar.owl"),
		new File (schema_folder + "22-rdf-syntax-ns.rdf"),
		new File (schema_folder + "rdf-schema.rdf"),
		new File (schema_folder + "dcelements.rdf"),
		new File (schema_folder + "eurocris_ontologies_cerif_1.3.rdf"),
		new File (schema_folder + "owl.rdf")
		};
	
	// Current selection
	List<Resource> containingSchemas = new ArrayList<Resource>();
		
	public DataSchemaSet()
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
		if (containingSchemas.isEmpty()) {
			str = "[]";
		} else {
			str = "[";
			for (int i=0; i<containingSchemas.size()-1; i++) {
				str += getLabelFromResource(containingSchemas.get(i)) + ", ";
			}
			str += getLabelFromResource(containingSchemas.get(containingSchemas.size()-1)) + "]";
		}
		return str; 
	}
	

	
	/*
	 * Specifics
	 */
	
	public void setContainingSchemas( List<Resource> schema_to_set )
	{
		this.containingSchemas.clear();
		this.containingSchemas.addAll(schema_to_set);
	}
	
	public List<Resource> getContainingSchemas()
	{
		return containingSchemas;
	}
	
	public static Resource getResourceFromLabel(String label) {
		for (int i=0; i<schemaLabels.length; i++) {
			if (schemaLabels[i].equals(label)) {
				return schemaURIs[i]; 
			}
		}
		return null;
	}
	
	public static File getFileFromLabel(String label) {
		for (int i=0; i<schemaLabels.length; i++) {
			if (schemaLabels[i].equals(label)) {
				return schemaFiles[i]; 
			}
		}
		return null;
	}
	
	public static String getLabelFromResource(Resource resource) {
		for (int i=0; i<schemaURIs.length; i++) {
			if (schemaURIs[i].equals(resource)) {
				return schemaLabels[i]; 
			}
		}
		return null;
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
