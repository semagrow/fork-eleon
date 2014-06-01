/***************

<p>Title: Nominal Set</p>

<p>Description:
This class represents filler types restricted to a closed set of resources.
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


package gr.demokritos.iit.eleon.annotations;

import java.util.ArrayList;
import java.util.List;


public class NominalSet
{

	// All possible values
	public static String[] availableNomilas = {"class1" , "class2", "class3", "class4", "class5"};

	// Current selection
	List<String> containingNominals = new ArrayList<String>();
	
	public NominalSet()
	{
		super();
	}

	
	/*
	 * Object IMPLEMENTATION
	 */
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{ return containingNominals.toString(); }
	

	
	/*
	 * Specifics
	 */
	
	public void setContainingNominals( List<String> containingNominals )
	{
		this.containingNominals = new ArrayList<String>(containingNominals); 
	}
	
	public List<String> getContainingNominals()
	{
		return containingNominals;
	}
	
}
