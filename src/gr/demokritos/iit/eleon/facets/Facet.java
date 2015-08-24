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


package gr.demokritos.iit.eleon.facets;

import com.hp.hpl.jena.ontology.OntModel;


public interface Facet
{
	/**
	 * Returns the title of this facet
	 * @return
	 */
	public String getTitle();

	/**
	 * Returns a short description of this facet
	 * and its functionality, appropriate for
	 * help items such as hover balloons.
	 * @return
	 */
	public String getInfo();

	/**
	 * Tracks whether edits have been made since
	 * the most recent sync from the underlying data. 
	 * @return true if the in-memory objects contain
	 * information other than what was loaded from
	 * the persistency object that supports them.
	 */
	public boolean isDirty();

	/**
	 * Responds whether this facet can be automatically
	 * generated from data or only holds manually authored
	 * information. Simply loading previously saved information
	 * is not meant to be interpreted as auto-filling.  
	 * @return
	 */
	public boolean isAutoFilled();

	/**
	 * Responds whether this facet can be edited. This
	 * refers to the faceted browsing, not the property
	 * values: A read-only facet might give access to editable
	 * property fields, but may not be edited itself.
	 * @return
	 */
	public boolean isEditable();

	/**
	 * Initializes the facet. Must be called before any other method. 
	 * @param autoFill if true, the facet will be auto-filled;
	 * @throws IllegalArgumentException if auto-filling was requested
	 * from a facet that cannot be auto-filled
	 */
	public void init( boolean autoFill )
	throws IllegalArgumentException;

	/**
	 * Initializes the facet. It auto-fills the facet, if possible.  
	 */
	public void init();

	/**
	 * Updates the auto-filled parts of this facet. If editable,
	 * no manually edited information is altered or lost. In case of
	 * inconsistency, the manually edited information overrides auto-filling.
	 * A clean auto-fill can be achieved by calling init( true ).
	 * @throws IllegalArgumentException if this facet cannot be auto-filled
	 */
	public void update()
	throws IllegalArgumentException;

	/**
	 * Synchronizes the facet contents with the underlying data,
	 * by loading data from the model into the facet.
	 * @param model
	 */
	public void syncFrom(  OntModel model );
	
	/**
	 * Synchronizes the facet contents with the underlying data,
	 * by saving data in the facet to the model.
	 * @param model
	 */
	public void syncTo( OntModel ont );
	
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
