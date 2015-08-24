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
 * <p>Title: DTDResolver</p>
 * <p>Description: Resolves the relative DTD path</p>
 * @author Jo Calder and Amy Isard
 * @version 1.0
 */

package gr.demokritos.iit.eleon.authoring;

import java.util.Hashtable;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import java.io.File; 
import java.io.*;
import java.util.zip.*;

/**
   When an XML document is parsed, this class intercepts the default
   entity handling mechanism, so as to be able to resolve indirect
   references to the local file system.  

   Resolution to the Exprimo library DTDs is achieved by substition of
   the system identifier prefix "http://ltg.ed.ac.uk/exprimo/dtds"
   with the appropriate file from the mpiro_resources.zip archive

 */

public class DTDResolver implements EntityResolver 
{

	static final String systemPrefix = "http://ltg.ed.ac.uk/exprimo/dtds";
	//    static String SLASH = (String)System.getProperty("file.separator");
	static String SLASH = "/";
	
	static String exprimoDTDDirectory = "exprimo" + SLASH + "lib" + SLASH + "DTDs";
	
	/**
	 * Map references to Exprimo DTDs (given by the appropriate
	 * identifier and the presence of
	 * "http://ltg.ed.ac.uk/exprimo/dtds/" in the DTD identifier).
	 * @param publicId the public identifier (not used in this method
	 * but must be there to override the appropriate method in
	 * EntityResolver)
	 * @param systemId the system identifier, which is actually used
	 * @return an InputSource for the appropriate file */

	public InputSource resolveEntity(String publicId, String systemId) 
	{
	   
		if (! systemId.startsWith(systemPrefix)) 
		{
			return null; 
		}
		
		int count = systemPrefix.length(); 
		
		String fname = exprimoDTDDirectory + systemId.substring(count, systemId.length());
		
		InputSource inputSource = null;
		
		try 
		{
			ZipEntry zippo = null;
			if (Mpiro.resourcesZip != null) 
			{
			   zippo = Mpiro.resourcesZip.getEntry(fname);
			}
			if (zippo == null && Mpiro.domainZip != null) 
			{
			   zippo = Mpiro.domainZip.getEntry(fname);
			}
			if (zippo == null) 
			{
			   System.out.println("DTDResolver.resolveEntity: couldn't find " + fname + " in available zip files");
			   return null;
			}
			InputStream inputStream = Mpiro.resourcesZip.getInputStream(zippo);
			inputSource = new InputSource(inputStream);
			inputSource.setSystemId(systemId);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return inputSource;
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
