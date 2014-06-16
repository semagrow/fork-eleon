//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**
 * <p>Title: MpiroDocument</p>
 * <p>Description: Contains methods for creating an
 * MpiroDocument. loading an existing XML document from a zip archive,
 * and returnining a string representation of an XML document</p>
 * @author Amy Isard
 * @version 1.0 */

public class MpiroDocument 
{
	private DocumentType docType = null;
	private String docTypeName = null;
	private String systemId = null;
	private String publicId = null;
	
	private DocumentBuilder db = null;
	private Document doc = null;


	/**
	 * Create a new empty document and initalize the document
	 * builder */	
	public MpiroDocument() 
	{
		try 
		{
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    db = dbf.newDocumentBuilder();
	    db.setEntityResolver(new DTDResolver());
	    doc = db.newDocument();
		}
		catch(Exception e) 
		{
	    e.printStackTrace();
	    return;
		}
	}


	/** Read in an existing XML file from a zip archive
	 * @param zip, an MpiroZip
	 * @param fileName */
	public void loadDocumentFromZip(MpiroZip zip, String fileName) 
	{
		if (zip == null) 
		{
		  return;
		}
		//	System.out.println("trying to load " + fileName + " from " + zip);
		//	System.out.println("trying to load " + fileName + " from " + zip.getName());
		try 
		{
	    InputStream iStream = zip.getFile(fileName);
	    if (iStream == null) 
	  	{
				return;
	  	}
	    InputSource is = new InputSource(iStream);
	    if (is == null) 
	    {
				return;
	    }
	    if (db == null) 
	    {
				//System.out.println("Could not load document " + fileName + " from " + zip.getName());
				return;
	    }
	    doc = db.parse(is);
		}
		catch (Exception e) 
		{
	    e.printStackTrace();
	    return;
		}
		docType = doc.getDoctype();
		if (docType != null) 
		{
	    docTypeName = docType.getName();
	    publicId = docType.getPublicId();
	    systemId = docType.getSystemId();
		}
	}

	// A.I. 13/02/03 This method creates a string representation of
	// the document which has the right character encoding, and the
	// right declaration at the top, but it does it in a somewhat
	// nasty way.
	// Two alternative which we're not using and the reasons why:
	
	// 1. If the line
	// transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
	// was uncommented, and the encoding was iso-8859-7, it would be
	// output with entity references instead of characters, presumably
	// because the people who wrote the transformer code didn't bother
	// to include iso-8859-7 characters.  It's perfectly valid XML,
	// and exprimo reads it happily, but it's unreadable by humans.
	// Maybe in a future release of java they'll have changed this,
	// then this method can be used.
	
	// 2. The other obvious option is to save all files as as UTF-8,
	// which can then be read by exprimo.  But at the moment I (Amy)
	// don't have any way of reading or editing files in UTF-8, and we
	// still sometimes need to do hand inspection of the XML files

  /**
  * @param encoding, the character encoding of the document
  * @return a String representation of the XML document */
	public String toString(String encoding) 
	{
		Transformer transformer = null;
		String printString = null;
		try 
		{
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); 
			//transformer.setOutputProperty(OutputKeys.ENCODING, encoding); 
			if (systemId != null) 
			{
				transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemId);
	    }
	    
	    if (publicId != null) 
	    {
				transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, publicId);
	    }

			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			printString = sw.toString();
		} 
		catch( TransformerConfigurationException e ) 
		{
			e.printStackTrace();
		} 
		catch( TransformerException e ) 
    {
    	e.printStackTrace();
    }
		// This is the hack, see explanation above
		printString = printString.replaceAll("UTF-8", encoding);
		return printString;
	}

	/**
	@return the XML Document contained in this MpiroDocment */
	public Document getDocument() 
	{
		return doc;
	}

  public void setDocument(Document d) 
  {
		doc = d;
  }

  public void setPublicId(String pubId) 
  {
		publicId = pubId;
  }

  public void setSystemId(String sysId) 
  {
		systemId = sysId;
  }

}