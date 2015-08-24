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

<p>Title: Persistence Backend Interface</p>
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
