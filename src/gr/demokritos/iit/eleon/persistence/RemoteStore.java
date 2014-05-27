/***************

<p>Title: Remote Store</p>

<p>Description:
Persistence backend, at remote SPARQL store.
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


package gr.demokritos.iit.eleon.persistence;

import gr.demokritos.iit.eleon.facets.Facet;

import java.io.IOException;
import java.util.List;

import org.eclipse.swt.widgets.Tree;

public class RemoteStore implements PersistenceBackend
{
	private String label = null;


	/*
	 * Constructors 
	 */

	public RemoteStore()
	{
		
	}
	

	/*
	 * Getters and Setters 
	 */


	@Override
	public String getLabel() { return this.label; }

	@Override
	public void setBackend( Object parameter )
	{
		// TODO Auto-generated method stub
	}


	/*
	 * PersistenceBackend implementation
	 */


	@Override
	public void open( Object backendParam )
	throws IllegalArgumentException, IOException
	{
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public boolean save( Facet[] facets )
	throws IOException
	{
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void buildPropertyTree( Tree treePerProperty )
	{
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void buildEntityTree( Tree entityTree )
	{
		// TODO Auto-generated method stub
		
	}

}
