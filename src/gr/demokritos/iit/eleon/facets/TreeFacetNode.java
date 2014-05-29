/***************

<p>Title: Facet Interface</p>

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

package gr.demokritos.iit.eleon.facets;

public interface TreeFacetNode {
	
	/**
	 * 
	 * @return the name of the author that created this node.
	 */
	String getAuthor();
/*	
	*//**
	 * the title of the node.
	 * @return
	 *//*
	String getTitle();
	
	*//**
	 * 
	 * @return the sparqlEndpoint//TODO:I can do better than that later.
	 *//*
	String getEndpoint();
	
	//TODO:add javadoc
	Object[][] getProperty_values();
	String[][] getProperty_names();
	Object[][] getProperty_value_types();
	boolean[][] getProperty_is_functional();
*/
}
