/***************

<p>Title: Persistence Backend Interface</p>

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
@author Giannis Mouchakis (SemaGrow 2014)

***************/

package gr.demokritos.iit.eleon.persistence;

import gr.demokritos.iit.eleon.facets.Facet;
import gr.demokritos.iit.eleon.facets.dataset.EntityInclusionTreeFacet;
import gr.demokritos.iit.eleon.facets.dataset.PropertyTreeFacet;

import java.io.IOException;

import org.eclipse.swt.widgets.Tree;


public interface PersistenceBackend
{
	public String getLabel();

	public void setBackend( Object parameter )
	throws IllegalArgumentException;

	public Object getBackend();

	public void open()
	throws IOException;

	/**
	 * Saves the given facets to this PersistenceBackend
	 * @param facets The facets that should be saved.
	 * @return
	 * @throws IOException
	 */
	public boolean save( Facet[] facets )
	throws IOException;

	/**
	 * Loads the property tree facet
	 * @param facet
	 */
	public void buildPropertyTree( PropertyTreeFacet facet );

	/**
	 * Loads the entity inclusion tree facet
	 * @param facet
	 */
	public void buildEntityTree( EntityInclusionTreeFacet facet );

	

}
