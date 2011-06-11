/***************

<p>Title: </p>

<p>Description:
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2011 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)

***************/


package gr.demokritos.iit.eleon.interfaces;

import gr.demokritos.iit.eleon.struct.EleonStruc;

public interface OntoExtension
{


	public boolean isRealTime();
	
	
	/**
	 * Sets the previous extension in the pipeline 
	 * @param OntoExtension previous object in the pipeline
	 */
	public void setPrevious( OntoExtension prev );

	
	/**
	 * Performs the functionality that this extension provides
	 * and writes the results in the QueryHashtable object that
	 * is answered by getExtension()  
	 */
	public void rebind();
	

	/**
	 * Gets the output object of this extension.
	 * This must be null, if rebind() has bever been called. 
	 * @param QueryHashtable the output object
	 */
	public EleonStruc getExtension();

        public void reloadTree(boolean set);
	
	
	
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
