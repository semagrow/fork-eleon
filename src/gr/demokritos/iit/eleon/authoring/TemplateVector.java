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


package gr.demokritos.iit.eleon.authoring;

import java.util.Hashtable;
import java.util.Vector;

public class TemplateVector extends Vector
{
	public TemplateVector() 
	{
		Hashtable englishValues = new Hashtable();
		Hashtable italianValues = new Hashtable();
		Hashtable greekValues = new Hashtable();
		addElement(englishValues);
		addElement(italianValues);
		addElement(greekValues);
	}
    
     public TemplateVector(Vector v)
  {
	  super(v);
     }

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
