//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.


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