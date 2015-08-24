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


package gr.demokritos.iit.eleon.struct;

import gr.demokritos.iit.eleon.interfaces.OntoExtension;

public class ExplicitOntoData implements OntoExtension
{
	
	private EleonStruc explicitUserData;

	public ExplicitOntoData( EleonStruc data )
	{
		explicitUserData = data;
	}

	@Override
	public void setPrevious( OntoExtension prev ) { }

	@Override
	public EleonStruc getExtension()
	{
		return explicitUserData;
	}

	@Override
	public boolean isRealTime() { return true; }

	@Override
	public void rebind() { }

        @Override
	public void reloadTree(boolean set) { }

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
