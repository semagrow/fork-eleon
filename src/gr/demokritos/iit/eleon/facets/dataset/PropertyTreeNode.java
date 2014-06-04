/***************

<p>Title: Property Tree Node</p>

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

@author Giannis Mouchakis (SemaGrow 2014)

***************/

package gr.demokritos.iit.eleon.facets.dataset;

import gr.demokritos.iit.eleon.facets.TreeFacetNode;

import com.hp.hpl.jena.rdf.model.Resource;


public class PropertyTreeNode extends DatasetNode implements TreeFacetNode
{
	private Resource ontProperty = null;

	/**
	 * @param ontProperty
	 */
	public PropertyTreeNode( Resource res, DatasetFacet myFacet, Resource ontProperty )
	{
		super( res, myFacet );
		this.ontProperty = ontProperty;
	}
	
	
	/**
	 * @return the ontProperty
	 */
	public Resource getProperty()
	{ return ontProperty; }

	/**
	 * @param ontProperty the ontProperty to set
	 */
	public void setProperty( Resource ontProperty )
	{ this.ontProperty = ontProperty; }


}
