/***************

<p>Title: OWL File</p>

<p>Description:
Persistence backend, at local OWL/RDF file.
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


package gr.demokritos.iit.eleon.persistence;

import gr.demokritos.iit.eleon.facets.Facet;
import gr.demokritos.iit.eleon.facets.dataset.DatasetNode;
import gr.demokritos.iit.eleon.facets.dataset.EntityInclusionTreeFacet;
import gr.demokritos.iit.eleon.facets.dataset.EntityInclusionTreeNode;
import gr.demokritos.iit.eleon.facets.dataset.PropertyTreeFacet;
import gr.demokritos.iit.eleon.facets.dataset.PropertyTreeNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;


public class OWLFile implements PersistenceBackend
{
	private String filename = null;
	private OntModel ont = null;
	private String label = null;


	/*
	 * Constructors 
	 */

	public OWLFile()
	{
		
	}
	

	/*
	 * Getters and Setters 
	 */


	@Override
	public String getLabel() { return this.label; }

	@Override
	public void setBackend( Object parameter )
	{
		try { this.filename = (String)filename; }
		catch( ClassCastException ex ) {
			throw new IllegalArgumentException( "Argument should be a String", ex );
		}
	}

	@Override
	public String getBackend()
	{ return this.filename; }

	
	/*
	 * PersistenceBackend implementation
	 */


	@Override
	public void open()
	throws IOException
	{
		if( this.ont == null ) {
			this.ont = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
		}
		else {
			this.ont.removeAll();
		}

		try {
			java.io.FileInputStream file = new java.io.FileInputStream( this.filename );
			java.io.BufferedInputStream buf = new java.io.BufferedInputStream( file ); 
			this.ont.read( buf, "", "RDF/XML" );
			
		}
		catch( java.io.FileNotFoundException ex ) {
			// This should almost never happen: the filename is selected
			// from a filesystem browser, but somebody might delete the
			// file after selecting and before opening.
			throw new IOException( "File not found: " + this.filename, ex );
		}

		// TODO: find the label
		this.label = "LABEL";
}

	
	@Override
	public boolean save( Facet[] facets )
	throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void buildPropertyTree( PropertyTreeFacet facet )
	{
		// TODO Auto-generated method stub
	}


	@Override
	public void buildEntityTree( EntityInclusionTreeFacet facet )
	{
		// TODO Auto-generated method stub
	}

}
