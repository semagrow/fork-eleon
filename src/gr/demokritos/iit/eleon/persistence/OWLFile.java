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

<p>Title: OWL File</p>

<p>Description:
Persistence backend, at local OWL/RDF file.
</p>


***************/


package gr.demokritos.iit.eleon.persistence;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.annotations.AnnotationVocabulary;
import gr.demokritos.iit.eleon.facets.Facet;

import java.io.IOException;


public class OWLFile implements PersistenceBackend
{
	private String filename = null;
	private String label = null;
	private MainShell myShell;


	/*
	 * Constructors 
	 */

	public OWLFile( MainShell shell )
	{
		this.myShell = shell;
	}
	

	/*
	 * Getters and Setters 
	 */


	@Override
	public String getLabel() { return this.label; }

	@Override
	public String getBackend()
	{ return this.filename; }

	
	/*
	 * PersistenceBackend implementation
	 */


	@Override
	public void open( Object parameter )
	throws IOException, IllegalArgumentException
	{
		try { this.filename = (String)parameter; }
		catch( ClassCastException ex ) {
			throw new IllegalArgumentException( "Argument should be a String", ex );
		}

		if( this.myShell.data == null ) {
			this.myShell.data = AnnotationVocabulary.getNewModel( AnnotationVocabulary.NONE );
		}
		else {
			this.myShell.data.removeAll();
		}
		

		try {
			loadSchemas();
			java.io.FileInputStream file = new java.io.FileInputStream( this.filename );
			java.io.BufferedInputStream buf = new java.io.BufferedInputStream( file );
			String formats[] = { "TTL", "RDF/XML", "N-TRIPLE", "N3" };
			boolean ok = false;
			for( String format : formats ) {
				try {
					this.myShell.data.read( buf, "", format );
					buf.close();
					ok = true;
					break;
				}
				catch( org.apache.jena.riot.RiotException ex ) {
					// do nothing
				}
			}
			if( !ok ) {
				throw new IOException( "File " + this.filename + " is not in a known format." );
			}
		}
		catch( java.io.FileNotFoundException ex ) {
			// This should almost never happen: the filename is selected
			// from a filesystem browser, but somebody might delete the
			// file after selecting and before opening.
			throw new IOException( "File not found: " + this.filename, ex );
		}

		// TODO: find the label
		this.label = "LABEL";
		this.myShell.annotators.syncFrom( this.myShell.data );
	}

	
	@Override
	public boolean save( Facet[] facets, Object parameter )
	throws IOException, IllegalArgumentException
	{
		if( parameter != null ) {
			try { this.filename = (String)parameter; }
			catch( ClassCastException ex ) {
				throw new IllegalArgumentException( "Argument should be a String", ex );
			}
		}
		if( this.filename == null ) { return false; }

		this.myShell.data = AnnotationVocabulary.getNewModel( this.myShell.activeAnnSchema );
		
		for( Facet facet : facets ) {
			facet.syncTo( this.myShell.data );
		}
		this.myShell.annotators.syncTo( this.myShell.data );
		
		java.io.FileOutputStream file = new java.io.FileOutputStream( this.filename );
		java.io.BufferedOutputStream buf = new java.io.BufferedOutputStream( file );
		this.myShell.data.write( buf, "TTL" );
		buf.close();
		return true;
	}


	private void loadSchemas()
	throws IOException
	{
		// TODO: only load what is needed
		java.io.BufferedInputStream io =
				new java.io.BufferedInputStream(
						ClassLoader.getSystemClassLoader().getResourceAsStream( "void.rdf" ) );
		this.myShell.data.read( io, "", "RDF/XML" );
		io.close();
		
		io = new java.io.BufferedInputStream(
						ClassLoader.getSystemClassLoader().getResourceAsStream( "sevod.ttl" ) );
		this.myShell.data.read( io, "", "TTL" );
		io.close();
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
