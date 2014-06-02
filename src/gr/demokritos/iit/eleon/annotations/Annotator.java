/***************

<p>Title: Annotator</p>

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

***************/


package gr.demokritos.iit.eleon.annotations;

import gr.demokritos.iit.eleon.MainShell;

import java.util.HashMap;

import com.hp.hpl.jena.rdf.model.Resource;


public class Annotator implements Comparable<Annotator>
{
	static private final String uriPrefix = "http://eleon.iit.demokritos.gr/user#";
	static private final HashMap<String,Annotator> masterList;
	static {
		masterList = new HashMap<String,Annotator>( 10 );
	}
	
	private final Resource uri;
	private final String login;
	
	private Annotator( Resource uri, String login )
	{
		this.uri = uri;
		this.login = login;
	}


	/*
	 * INSTANCE FACTORY
	 */


	/**
	 * This method returns an {@link Annotator} object by the
	 * given id. 
	 * @param id The local identifier of the Annotator
	 * @return
	 */

	static public Annotator getAnnotator( String id )
	{
		if( (id == null) || (id.length() == 0) ) { return null; }

		Annotator retv = Annotator.masterList.get( id );
		if( retv == null ) {
			Resource u = MainShell.shell.ont.createResource( uriPrefix + id );
			retv = new Annotator( u, id );
			Annotator.masterList.put( id, retv );
		}
		return retv;
	}


	static public Annotator getAnnotator( Resource u, String id )
	throws IllegalArgumentException
	{
		if( (id == null) || (id.length() == 0) ) { return null; }

		Annotator retv = Annotator.masterList.get( id );
		if( retv == null ) {
			retv = new Annotator( u, id );
			Annotator.masterList.put( id, retv );
		}
		else if( ! retv.getResource().equals(u) ) {
			// id is used for a different annotator
			throw new IllegalArgumentException( "Local identifier \"" + id + "\" is not unique: already used for " + retv.getResource().toString() );
		}
		return retv;
	}


	/*
	 * Object IMPLEMENTATION
	 */
	

	public boolean equals( Object that )
	{
		if( that == null ) { return false; }
		else if( that instanceof Annotator ) {
			return ((Annotator)that).login.equals( this.login );
		}
		else { return false; }
	}

	
	
	/*
	 * Comparable IMPLEMENTATION
	 */

	
	@Override
	public int compareTo( Annotator that )
	{
		return this.login.compareTo( that.login );
	}
	

	/*
	 * Annotator METHODS 
	 */
	
	Resource getResource() { return this.uri; }
	String getLogin() { return this.login; }

}
