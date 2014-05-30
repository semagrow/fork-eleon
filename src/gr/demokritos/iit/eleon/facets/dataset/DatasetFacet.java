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

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import gr.demokritos.iit.eleon.MainShell;
import gr.demokritos.iit.eleon.facets.TreeFacet;


public abstract class DatasetFacet implements TreeFacet
{
	// TODO: These will be moved to the class representing the annotation schema
	static final String entityTop = "http://rdf.iit.demokritos.gr/2013/sevod#datasetTop";
	static final String propSubsumes = "http://rdfs.org/ns/void#subset";
	static final String propPropertyDataset = "http://rdf.iit.demokritos.gr/2013/sevod#PropertyDataset";
	static final String propEntityDataset = "http://rdf.iit.demokritos.gr/2013/sevod#EntityDataset";

	protected Tree myTree;
	protected MainShell myShell;
	protected DatasetFacet mySelf;
	private String title = "";
	private String info = "";

	protected DatasetFacet()
	{
		this.mySelf = this;
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

}
