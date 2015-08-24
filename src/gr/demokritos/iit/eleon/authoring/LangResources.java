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


/**
* <p>Title: LangResources</p>
* <p>Description: The main resource bundle</p>
* <p>Copyright: Copyright (c) 2001-2004 NCSR "Demokritos"</p>
* @author Dimitris Spiliotopoulos
* @author Maria Prospathopoulou
* @author Theofilos Nickolaou
*/

package gr.demokritos.iit.eleon.authoring;

import java.util.*;


public class LangResources 
{
	public static String getString(Locale locale, String key)
	{
	  ResourceBundle res = ResourceBundle.getBundle("gr/demokritos/iit/eleon/authoring/LangResources", locale);
	  return res.getString(key);
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
