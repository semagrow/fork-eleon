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

import java.io.IOException;


public interface PersistenceBackend
{
	public String getLabel();

	public Object getBackend();

	public void open( Object parameter )
	throws IOException, IllegalArgumentException;


	/**
	 * Saves the given facets to this PersistenceBackend.
	 * @param facets The facets that should be saved.
	 * @param parameter The backend parameters to use (e.g., filename, connection details).
	 * If null, the same parameters are used as when this backend was open'ed.
	 * If not null, the backend parameters in this instance are updated, and will be used
	 * in subsequent invocations with null parameter.
	 * @return true if successfully saved, false otherwise
	 * @throws IOException
	 * @throws IllegalArgumentException if {@param parameter} is not an instance
	 * of the class specified by each implementation of this interface
	 */

	public boolean save( Facet[] facets, Object parameter )
	throws IOException, IllegalArgumentException;


}
