/***************

<p>Title: Annotation Vocabulary</p>

<p>Description:

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

@author Stasinos Konstantopoulos (INDIGO, 2009; RoboSKEL 2011; SemaGrow 2012-2014)

***************/


package gr.demokritos.iit.eleon.annotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationVocabulary
{
	private List<String> requirements;
	public static final Map<String,AnnotationVocabulary> loadedVocabularies =
			new HashMap<String,AnnotationVocabulary>(10);
	public static final String[] knownVocabularies = { "VoID", "Sevod" };
	
	static public String[][] property_qnames = new String[2][20];
	static public String[][] property_uris = new String[2][20];
	static public Object[][] property_value_types = new Object[2][20];
	static public boolean[][] property_is_functional = new boolean[2][20];
	static public boolean[][] property_is_visible = new boolean[2][20];
	static public boolean[][] property_is_editable = new boolean[2][20];
	static public String[] annotation_schema_names = {"VoID", "Sevod: VoID SemaGrow extension"};
	static {
		// Internal
		int i=0;
		property_qnames[0][i] = "wdrs:issuedby";
		property_uris[0][i] = "http://www.w3.org/2007/05/powder-s#issuedby";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = false;
		property_is_editable[0][i] = false;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];
		
		// Shared Void and Sevod properties
		++i;
		property_qnames[0][i] = "dc:title";
		property_uris[0][i] = "http://purl.org/dc/terms/title";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];
		
		++i;
		property_qnames[0][i] = "dc:description";
		property_uris[0][i] = "http://purl.org/dc/terms/description";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "dc:creator";
		property_uris[0][i] = "http://purl.org/dc/terms/creator";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "dc:subject";
		property_uris[0][i] = "http://purl.org/dc/terms/subject";
		property_value_types[0][i] = String.class; //TODO: DBPedia, SKOS, any URI
		property_is_functional[0][i] = false;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:sparqlEndpoint";
		property_uris[0][i] = "http://rdfs.org/ns/void#sparqlEndpoint";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:vocabulary";
		property_uris[0][i] = "http://rdfs.org/ns/void#vocabulary";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = false;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:class";
		property_uris[0][i] = "http://rdfs.org/ns/void#class";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:property";
		property_uris[0][i] = "http://rdfs.org/ns/void#property";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:uriRegexPattern";
		property_uris[0][i] = "http://rdfs.org/ns/void#uriRegexPattern";
		property_value_types[0][i] = String.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:triples";
		property_uris[0][i] = "http://rdfs.org/ns/void#triples";
		property_value_types[0][i] = Integer.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:distinctSubjects";
		property_uris[0][i] = "http://rdfs.org/ns/void#distinctSubjects";
		property_value_types[0][i] = Integer.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		++i;
		property_qnames[0][i] = "void:distinctObjects";
		property_uris[0][i] = "http://rdfs.org/ns/void#distinctObjects";
		property_value_types[0][i] = Integer.class;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];

		/*
		++i;
		property_qnames[0][i] = "";
		property_uris[0][i] = "http://rdfs.org/ns/void#";
		property_value_types[0][i] = ;
		property_is_functional[0][i] = true;
		property_is_visible[0][i] = true;
		property_is_editable[0][i] = true;
		property_qnames[1][i] = property_qnames[0][i];
		property_uris[1][i] = property_uris[0][i];
		property_value_types[1][i] = property_value_types[0][i];
		property_is_functional[1][i] = property_is_functional[0][i];
		property_is_visible[1][i] = property_is_visible[0][i];
		property_is_editable[1][i] = property_is_editable[0][i];
		 */

		++i;
		// End of VoID
		property_qnames[0][i] = null;
		property_uris[0][i] = null;
		property_value_types[0][i] = null;
		// Sevod extension
		property_qnames[1][i] = "svd:subjectRegexPattern";
		property_uris[1][i] = "http://rdf.iit.demokritos.gr/2013/sevod#subjectRegexPattern";
		property_value_types[1][i] = Integer.class;
		property_is_functional[1][i] = true;	
		property_is_visible[1][i] = true;
		property_is_editable[1][i] = true;
		
		++i;
		property_qnames[1][i] = "svd:subjectClass";
		property_uris[1][i] = "http://rdf.iit.demokritos.gr/2013/sevod#subjectClass";
		property_value_types[1][i] = String.class;
		property_is_functional[1][i] = true;	
		property_is_visible[1][i] = true;
		property_is_editable[1][i] = true;
		
		++i;
		property_qnames[1][i] = "svd:objectRegexPattern";
		property_uris[1][i] = "http://rdf.iit.demokritos.gr/2013/sevod#objectRegexPattern";
		property_value_types[1][i] = Integer.class;
		property_is_functional[1][i] = true;	
		property_is_visible[1][i] = true;
		property_is_editable[1][i] = true;
		
		++i;
		property_qnames[1][i] = "svd:objectClass";
		property_uris[1][i] = "http://rdf.iit.demokritos.gr/2013/sevod#objectClass";
		property_value_types[1][i] = String.class;
		property_is_functional[1][i] = true;	
		property_is_visible[1][i] = true;
		property_is_editable[1][i] = true;
		
		++i;
		property_qnames[1][i] = null;
		property_uris[1][i] = null;
		property_value_types[1][i] = null;
		property_is_visible[1][i] = true;
		property_is_editable[1][i] = true;
	}
	
	public static AnnotationVocabulary get( String vocName )
	{
		AnnotationVocabulary o =
				AnnotationVocabulary.loadedVocabularies.get( vocName );
		if( o == null ) {
			o = new AnnotationVocabulary( vocName );
		}
		return o;
	}

	public AnnotationVocabulary( String vocabulary )
	{
		requirements = new ArrayList<String>();

		if( vocabulary == null ) {
			// explode
		}
		else if( vocabulary.equals("VoID") ) {
			this.requirements.add( "dc" );
		}
		else if( vocabulary.equals("Sevod") ) {
			this.requirements.add( "dc" );
			this.requirements.add( "void" );
		}
		else {
			// it's alright, it's just a base vocabulary
		}

		for( String s : requirements ) {
			AnnotationVocabulary.get( s );
		}
	}
	

}
