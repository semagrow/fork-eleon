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

import java.io.IOException;


import org.eclipse.swt.widgets.Tree;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


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


	// TODO: extends the schema, to avoid having to re-build the tree 
	@Override
	public void buildPropertyTree( Tree propertyTree )
	{
		ExtendedIterator<OntProperty> it = this.ont.listAllOntProperties();
		while( it.hasNext() ) {
			OntProperty p = it.next();
			ExtendedIterator<? extends OntProperty> it2 = p.listSuperProperties( true );
			while( it2.hasNext() ) {
				OntProperty superp = it2.next();
				// TODO
			}
		}
	}
	

	@Override
	public void buildEntityTree( Tree entityTree )
	{
		/*
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		NodeList nodeList = null;
		try {
			XPathExpression expr = xpath.compile("//facet[@type=\"per_entity\"]");
			nodeList = (NodeList) expr.evaluate( doc, XPathConstants.NODESET );
		}
		catch( XPathExpressionException ex ) {
			// Should never happen
		}
		assert nodeList != null;

		Element eElement = (Element) nodeList.item(0);
		Element root = (Element) eElement.getFirstChild();
		NodeList childrenList = root.getChildNodes();
		for( int i = 0; i < childrenList.getLength(); i++ ) {
			createTreeFromDOM(childrenList.item(i), entityTree, "per_property", entityTree.getItems()[0]);
		}
		entityTree.moveAbove(null);
		*/
	}



}
