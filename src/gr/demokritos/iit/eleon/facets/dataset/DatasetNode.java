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

<p>Title: Dataset Node</p>

<p>Description:
This is the base class for all node types defined in this package. 
</p>

***************/


package gr.demokritos.iit.eleon.facets.dataset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.annotations.AnnotationVocabulary;
import gr.demokritos.iit.eleon.annotations.DataSchemaSet;
import gr.demokritos.iit.eleon.annotations.NominalSet;
import gr.demokritos.iit.eleon.facets.TreeFacetNode;


public class DatasetNode implements TreeFacetNode
{
	
	public Object[][] property_values = new Object[2][20];
	private final DatasetFacet myFacet;
	private final Resource res;

	public DatasetNode( Resource res, DatasetFacet myFacet )
	{
		super();
		this.myFacet = myFacet;
		if( res == null ) {
			String urn = "urn:uuid:" + UUID.randomUUID().toString();
			com.hp.hpl.jena.graph.Node node =
					com.hp.hpl.jena.graph.NodeFactory.createURI( urn );
			this.res = this.myFacet.myShell.data.wrapAsResource( node );
		}
		else {
			this.res = res;
		}
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
	
	
	/*
	 *  Object IMPLEMENTATION
	 */


	@Override
	public boolean equals( Object that )
	{
		if( that instanceof DatasetNode ) {
			Resource thisResource = this.getResource();
			Resource thatResource = ((DatasetNode)that).getResource();
			if( thatResource == null ) { return thisResource == null; }
			else { return thatResource.equals( thisResource ); }
		}
		else {
			return false;
		}
	}
	

	/*
	 * FacetNode IMPLEMENTATION
	 */
	
	public Resource getResource()
	{ return this.res; }

	public DatasetFacet getFacet()
	{ return this.myFacet; }

	public Resource getOwner()
	{
		return (Resource)property_values[MainShell.shell.activeAnnSchema][0];
	}
	
	public void setOwner( Resource owner )
	{
		property_values[MainShell.shell.activeAnnSchema][0] = owner;
	}


	/**
	 * @return Returns the human-readable label
	 * @return label
	 */

	public String getLabel()
	{
		return (String)this.property_values[MainShell.shell.activeAnnSchema][1];
	}
	
	public void setLabel( String label )
	{
		this.property_values[MainShell.shell.activeAnnSchema][1] = label;
	}
	
	public String getDescription()
	{
		return (String)this.property_values[MainShell.shell.activeAnnSchema][2];
	}
	
	public Object getValue( String qname )
	{
		Object retv = null;
		int i = 0;
		while( (retv == null) &&
				(AnnotationVocabulary.property_qnames[MainShell.shell.activeAnnSchema][i] != null) ) {
			if( AnnotationVocabulary.property_qnames[MainShell.shell.activeAnnSchema][i].equals(qname) ) {
				retv = this.property_values[MainShell.shell.activeAnnSchema][i];
			}
			++i;
		}
		return retv;
	}
	
	public void setValue( String qname, Object value )
	{
		int i = 0;
		boolean done = false;
		while( !done && (AnnotationVocabulary.property_qnames[MainShell.shell.activeAnnSchema][i] != null) ) {
			if( AnnotationVocabulary.property_qnames[MainShell.shell.activeAnnSchema][i].equals(qname) ) {
				this.property_values[MainShell.shell.activeAnnSchema][i] = value;
				done = true;
			}
			++i;
		}
	}
	
	public void syncTo( OntModel model )
	{
		// TODO: maintain dirty bits and only sync changes
		int i = 0;
		while( AnnotationVocabulary.property_qnames[MainShell.shell.activeAnnSchema][i] != null ) {
			String uri = AnnotationVocabulary.property_uris[MainShell.shell.activeAnnSchema][i];
			Property p = model.getProperty( uri );
			Object value = this.property_values[MainShell.shell.activeAnnSchema][i];
			List<Statement> stmts;
			if( value == null ) {
				stmts = Collections.emptyList();
			}
			else if ( value instanceof NominalSet ) {
				stmts = new ArrayList<Statement>();
				for (Resource nominal_class : ((NominalSet) value).getContainingNominals()) {
					Statement stmt = model.createStatement( this.res, p, nominal_class );
					stmts.add( stmt );
				}
			} 
			else if ( value instanceof DataSchemaSet ) {
				stmts = new ArrayList<Statement>();
				for (Resource data_schema : ((DataSchemaSet) value).getContainingSchemas()) {
					Statement stmt = model.createStatement( this.res, p, data_schema );
					stmts.add( stmt );
				}
			}
			else if( value instanceof Resource ) {
				Statement stmt = model.createStatement( this.res, p, (Resource)value );
				stmts = Collections.singletonList( stmt );
			}
			else if( value instanceof Integer ) {
				Statement stmt = model.createLiteralStatement( this.res, p, ((Integer)value).intValue() );
				stmts = Collections.singletonList( stmt );
			}
			else if( value instanceof String ) {
				Statement stmt = model.createLiteralStatement( this.res, p, (String)value );
				stmts = Collections.singletonList( stmt );
			} 
			else {
				// TODO: warning
				stmts = Collections.emptyList();
			}
			
			for( Statement stmt : stmts ) {
				if( stmt != null ) { model.add( stmt ); }
			}
			++i;
		}
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
