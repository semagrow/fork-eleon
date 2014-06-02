/***************

<p>Title: Dataset Facet</p>

<p>Description:
This is the base class for all facets defined in this package. 
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

package gr.demokritos.iit.eleon.facets.dataset;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.annotations.AnnotationVocabulary;
import gr.demokritos.iit.eleon.facets.TreeFacet;


public abstract class DatasetFacet implements TreeFacet
{
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger( DatasetFacet.class );

	// TODO: These will be moved to the class representing the annotation schema
	static final String entityTop = "http://rdf.iit.demokritos.gr/2013/sevod#datasetTop";
	static final String propSubsumes = "http://rdfs.org/ns/void#subset";
	static final String Facet = "http://rdf.iit.demokritos.gr/2013/sevod#Facet";
	static final String propFacet = "http://rdf.iit.demokritos.gr/2013/sevod#facet";
	static final String propertyFacet = "http://rdf.iit.demokritos.gr/2013/sevod#propertyFacet";
	static final String propEntityFacet = "http://rdf.iit.demokritos.gr/2013/sevod#entityFacet";

	protected Tree myTree;
	protected MainShell myShell;
	protected DatasetFacet mySelf;
	private String title = "";
	private String info = "";

	protected DatasetFacet( MainShell shell )
	{
		this.mySelf = this;
		this.myShell = shell;
	}

	
	/*
	 * GETTERS AND SETTERS
	 */

	protected void setTitle( String title )
	{ this.title = (title == null)? "" : title; }

	protected void setInfo( String info )
	{ this.info = (info == null)? "" : info; }

	/*
	 * Facet IMPLEMENTATION
	 */


	@Override
	public String getTitle()
	{
		assert this.title != null;
		return this.title;
	}


	@Override
	public String getInfo()
	{
		assert this.info != null;
		return this.info;
	}

	
	@Override
	public void syncFrom( OntModel ont )
	{
		Individual top = ont.getIndividual( DatasetFacet.entityTop );
		
		// Get all statements ?r void:subset ?o svd:facet svd:propertyFacet
		OntProperty void_subset = ont.getOntProperty( DatasetFacet.propSubsumes );
		OntProperty svd_facet = ont.getOntProperty( DatasetFacet.propFacet );
		Individual svd_propertyFacet = ont.getIndividual( DatasetFacet.propertyFacet );

		List<Statement> todo = new ArrayList<Statement>();
		List<Resource> dangling = new ArrayList<Resource>();
		List<TreeItem> done = new ArrayList<TreeItem>();

		List<Resource> datasets = new ArrayList<Resource>();
		StmtIterator stmtss = ont.listStatements( null, svd_facet, svd_propertyFacet );
		while( stmtss.hasNext() ) {
			
			datasets.add( stmtss.next().getSubject() );
		}
		Iterator<Resource> it = datasets.iterator();
		while( it.hasNext() ) {
			Resource r = it.next();
			StmtIterator stmts = ont.listStatements( null, void_subset, r );
			boolean at_least_one = false;
			while( stmts.hasNext() ) {
				Statement stmt = stmts.next();
				logger.debug( "Doing Statement: %s", stmt.toString() );
				if( stmt.getSubject().equals(top) ) {
					Resource subSet = stmt.getObject().asResource();
					// top level nodes should always have an explicit owner statement
					DatasetNode n = makeNode( subSet, (String)null );
					TreeItem treeItem = new TreeItem( this.getRoot(), SWT.NONE );
					treeItem.setText( n.getLabel() );
					treeItem.setData( n );
					// TODO update owners' list
					done.add( treeItem );
					logger.debug( "Added under Root" );
				}
				else {
					todo.add( stmt );	
				}
				at_least_one = true;
			}
			if( ! at_least_one ) {
				dangling.add( r );
			}
		}
		
		boolean work_more = true;
		List<Statement> todo2;
		while( work_more ) {
			todo2 = new ArrayList<Statement>();

			for( Statement stmt : todo ) {
				Resource superSet = stmt.getSubject();
				Resource subSet = stmt.getObject().asResource();
				// look for superSet in done List
				TreeItem father = null;
				Iterator<TreeItem> it2 = done.iterator();
				while( (father == null) && it2.hasNext() ) {
					TreeItem treeItem = it2.next();
					DatasetNode doneNode = (DatasetNode)treeItem.getData();
					if( superSet.equals(doneNode.getResource() ) ) {
						// found my dad
						father = treeItem;
					}
				} // end looking for superSet in done List
				if( father != null ) {
					// create a new Node
					
					DatasetNode n = makeNode( subSet, ((DatasetNode)father.getData()).getOwner() );
					// look for a Tree item that points to the same Node
					// (this happens if already added under a different father node)
					Iterator<TreeItem> it3 = done.iterator();
					boolean found = false;
					while( !found && it3.hasNext() ) {
						TreeItem treeItem = it3.next();
						if( n.equals(treeItem.getData()) ) {
							// found a node that equals n
							// the new n should be thrown away 
							n = (DatasetNode)treeItem.getData();
							found = true;
						}
					}
					// add to tree
					TreeItem treeItem = new TreeItem( father, SWT.NONE );
					treeItem.setText( n.getLabel() );
					treeItem.setData( n );
					done.add( treeItem );
					logger.debug( "Added under %s", ((DatasetNode)father.getData()).getLabel() );
				}
				else {
					// keep for next iteration
					todo2.add( stmt );
				}
			} // end for statements : todo
			if( todo2.size() < todo.size() ) {
				todo.clear();
				todo = todo2;
				todo2 = new ArrayList<Statement>( todo.size() );
			}
			else {
				work_more = false;
			}
		}
		
		if( ! todo.isEmpty() ) {
			// TODO: There are dangling subset statements
		}
		
		if( ! dangling.isEmpty() ) {
			// TODO: There are dangling datasets
		}
	}


	@Override
	public void syncTo( OntModel ont )
	{
		// TODO Auto-generated method stub
	}

	
	/*
	 * TreeFacet IMPLEMENTATION
	 */

	@Override
	public Tree getTree() { return this.myTree; }
	
	@Override
	public TreeItem getRoot()
	{
		TreeItem[] q = this.myTree.getItems();
		if( (q != null) && (q.length > 0) ) { return q[0]; }
		else { return null; }
	}
	
	
	/*
	 * INTERNAL HELPERS 
	 */


	abstract protected DatasetNode makeNode( Resource dataset, String defaultOwner );
	
	protected void copyValues( Resource dataset, DatasetNode retv )
	{
		int i = 0;
		while( AnnotationVocabulary.property_qnames[MainShell.shell.activeAnnSchema][i] != null ) {
			String uri = AnnotationVocabulary.property_uris[MainShell.shell.activeAnnSchema][i];
			Property p = dataset.getModel().getProperty( uri );
			Statement stmt = dataset.getProperty( p );
			if( stmt != null ) {
				RDFNode v = stmt.getObject();
				if( v.canAs(Resource.class) ) {
					retv.property_values[1][i] = v;
				}
				else if( v.canAs(Literal.class) ) {
					com.hp.hpl.jena.datatypes.RDFDatatype dt = v.asLiteral().getDatatype();
					if( dt == null ) {
						// untyped literal, effectively a string
						retv.property_values[MainShell.shell.activeAnnSchema][i] = v.asLiteral().getLexicalForm();
					}
					else if( Integer.class.equals(dt.getJavaClass()) ) {
						retv.property_values[MainShell.shell.activeAnnSchema][i] = new Integer( v.asLiteral().getInt() );
					}
					else {
						retv.property_values[MainShell.shell.activeAnnSchema][i] = v.asLiteral().getLexicalForm();
					}
				}
			}
			else {
				retv.property_values[MainShell.shell.activeAnnSchema][i] = null;
			}
			++i;
		}
	}


}
