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

	static final String propSubsumes = "http://rdfs.org/ns/void#subset";
	static final String Facet = "http://rdf.iit.demokritos.gr/2013/sevod#Facet";
	static final String propFacet = "http://rdf.iit.demokritos.gr/2013/sevod#facet";

	private final Resource top;
	private final Property subsumes;
	protected final Property facet;

	protected Tree myTree;
	protected MainShell myShell;
	protected DatasetFacet mySelf;
	private String title = "";
	private String info = "";
	
	

	protected DatasetFacet( MainShell shell )
	{
		this.mySelf = this;
		this.myShell = shell;
		this.top = this.myShell.data.createResource( AnnotationVocabulary.entityTop );
		this.subsumes = this.myShell.data.createProperty( DatasetFacet.propSubsumes );
		this.facet = this.myShell.data.createProperty( DatasetFacet.propFacet );
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
	public boolean isDirty()
	{
		//TODO: dirty bit
		return true;
	}


	@Override
	public void init( boolean autoFill )
	throws IllegalArgumentException
	{
		// I do not know how to autofill; should be overridden by auto-filling extensions
		if( autoFill ) { throw new IllegalArgumentException( "Cannot auto-fill" ); }

		this.myTree = new Tree( this.myShell, SWT.BORDER );
		this.myTree.setBounds(318, 84, 369, 578);
		TreeItem root = new TreeItem( myTree, SWT.NONE );
		root.setText("root");
		DatasetNode n = this.makeNode( this.top, null );
		root.setData( n );
	}


	@Override
	public void init()
	{
		this.init( this.isAutoFilled() );
	}

		
	@Override
	public void syncFrom( OntModel ont )
	{
		// Get all statements ?r void:subset ?o
		OntProperty void_sub = ont.getOntProperty( DatasetFacet.propSubsumes );

		List<Resource> datasets = new ArrayList<Resource>();
		StmtIterator stmtss = ont.listStatements( null, void_sub, (RDFNode)null );
		while( stmtss.hasNext() ) {
			Statement s = stmtss.next();
			RDFNode o = s.getObject();
			if( o.canAs(Resource.class) ) { datasets.add( o.asResource() ); }
			else {
				logger.warn( "Non-URI resource value in statement %s", s );
			}
		}
		syncFrom( ont, datasets );
	}


	public void syncFrom( OntModel ont, List<Resource> datasets )
	{
		Individual top = ont.getIndividual( AnnotationVocabulary.entityTop );
		OntProperty void_subset = ont.getOntProperty( DatasetFacet.propSubsumes );
		
		List<Statement> todo = new ArrayList<Statement>();
		List<Resource> dangling = new ArrayList<Resource>();
		List<TreeItem> done = new ArrayList<TreeItem>();

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
					DatasetNode n = makeNode( subSet, (Resource)null );
					TreeItem treeItem = new TreeItem( this.getRoot(), SWT.NONE );
					treeItem.setText( n.getLabel() );
					treeItem.setData( n );
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
			logger.warn( "Subset statements that could not be handled: %s", todo.toString() );
		}
		
		if( ! dangling.isEmpty() ) {
			logger.warn( "Dataset instances that could not be handled: %s", dangling.toString() );
		}
	}


	@Override
	public void syncTo( OntModel model )
	{
		TreeItem[] q = this.myTree.getItems();
		List<TreeItem> queue = new ArrayList<TreeItem>();
		for( TreeItem treeItem : q ) { queue.add( treeItem ); }

		while( ! queue.isEmpty() ) {
			System.out.println("XX" + queue);
			TreeItem treeItem = queue.get( 0 );
			queue.remove( 0 );
			TreeItem[] qq = treeItem.getItems();
			for( TreeItem tt : qq ) { queue.add( tt ); }
			
			DatasetNode myself = (DatasetNode)treeItem.getData();
			TreeItem parentItem = treeItem.getParentItem();
			if( parentItem != null ) {
				// only for non-root nodes
				DatasetNode parent = (DatasetNode)parentItem.getData();
				model.add( parent.getResource(), this.subsumes, myself.getResource() );
			}
			if( myself == null ) {
				// I am the root
			}
			else {
				myself.syncTo( model );
				write_facet( model, myself );
			}
		}
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


	abstract protected DatasetNode makeNode( Resource dataset, Resource defaultOwner );
	abstract protected void write_facet( OntModel model, DatasetNode node );
	
	protected void copyValues( Resource dataset, DatasetNode node )
	{
		int i = 0;
		while( AnnotationVocabulary.property_qnames[MainShell.shell.activeAnnSchema][i] != null ) {
			String uri = AnnotationVocabulary.property_uris[MainShell.shell.activeAnnSchema][i];
			Property p = dataset.getModel().getProperty( uri );
			Statement stmt = dataset.getProperty( p );
			if( stmt != null ) {
				RDFNode v = stmt.getObject();
				if( v.canAs(Resource.class) ) {
					node.property_values[1][i] = v;
				}
				else if( v.canAs(Literal.class) ) {
					com.hp.hpl.jena.datatypes.RDFDatatype dt = v.asLiteral().getDatatype();
					if( dt == null ) {
						// untyped literal, effectively a string
						node.property_values[MainShell.shell.activeAnnSchema][i] = v.asLiteral().getLexicalForm();
					}
					else if( Integer.class.equals(dt.getJavaClass()) ) {
						node.property_values[MainShell.shell.activeAnnSchema][i] = new Integer( v.asLiteral().getInt() );
					}
					else {
						node.property_values[MainShell.shell.activeAnnSchema][i] = v.asLiteral().getLexicalForm();
					}
				}
			}
			else {
				node.property_values[MainShell.shell.activeAnnSchema][i] = null;
			}
			++i;
		}
	}




}
