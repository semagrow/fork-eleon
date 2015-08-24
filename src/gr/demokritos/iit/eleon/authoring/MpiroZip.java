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

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * <p>Title: MpiroZip</p>
 * <p>Description: Contains methods for creating or updating a zip
 * archive of M-PIRO domain information, from MpiroDocuments</p>
 * @author Amy Isard
 * @version 1.0 */

public class MpiroZip 
{
	private static String DEFAULT_ENCODING = "ISO-8859-1";
	//    private static String SLASH = System.getProperty("file.separator");
	private static String SLASH = "/";
	private String zipFileName = new String();
	private Hashtable fileStrings = new Hashtable();
	private Hashtable fileEncodings = new Hashtable();
	private Hashtable zipContents = new Hashtable();
	
	public MpiroZip(String zipFile) 
	{
		zipFileName = zipFile;
	}


	/**
	 * Add an MpiroDocument to the list so that it will be included
	 * when this MpiroZip is written
	 * @param MpDoc the mpirodocument to be added
	 * @param file the full path of the location the document should be saved to
	 * @param encoding the character encoding which should be used for
	 * this document
	 */
	public void addDocument(MpiroDocument mpDoc, String file, String encoding) 
	{
		// Uses this toString() method because of encoding issues, see
		// comment above the MpiroDocument.toString() method
		String printString = mpDoc.toString(encoding);
		fileStrings.put(file, printString);
		fileEncodings.put(file, encoding);
	}


	/**
	 * Save the MpiroZip to a file (the filename was set when the
	 * MpiroZip was created
	 */
	public void save() 
	{
		File oldZipFile = new File(zipFileName);
		if (oldZipFile.exists()) 
		{
			//************************		
			try 
			{
		    boolean succ = oldZipFile.delete();
		    makeNewZip(zipFileName);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			//****************************
			//processExistingZip(zipFileName);
		}
		else 
		{
			makeNewZip(zipFileName);
		}
	}


	/**
	 * Update an existing zip file with the new entries
	 * @param oldZipFileName the name of the existing zip file
	 */
	private boolean processExistingZip(String oldZipFileName) 
	{
		boolean success = true;
		ZipFile oldZipFile = null;
		try 
		{
			oldZipFile = new ZipFile(oldZipFileName);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			success = false;
		}
	
		// Create a temporary zip file and output stream
		
		String newZipFileName = oldZipFileName + ".tmp";
		ZipOutputStream newZipStream = null;
		try 
		{
			newZipStream = new ZipOutputStream (new FileOutputStream(newZipFileName));
		}
		catch (IOException e) 
		{
		  e.printStackTrace();
		  success = false;
		}
		
		if (oldZipFile == null) 
		{
		  System.err.println("Failed to write zip, couldn't process existing zip file");
		  return false;
		}

		// loop through the entries in the existing zip file

		Enumeration oldEntries = oldZipFile.entries();
		while (oldEntries.hasMoreElements()) 
		{
		  ZipEntry oldEntry = (ZipEntry)oldEntries.nextElement();
		  String oldEntryName = oldEntry.getName();
		
		  // if an entry in the old zip file matches one on the list
		  // of files to be updated, output a new entry to the
		  // temporary new zip file
		  if (fileStrings.containsKey(oldEntryName)) 
		  {
				boolean tmpSuccess  = addNewEntry(newZipStream, oldEntryName);
				if (!tmpSuccess) 
				{
				  success = false;
				}
				fileStrings.remove(oldEntryName);
			}
			
	    // if an entry on the old zip file is not on the list of
	    // new entries, copy it unchanged to the temporary new zip
	    // file
	    else 
			{
				boolean tmpSuccess = copyOldEntry(oldZipFile, newZipStream, oldEntry);
				if (!tmpSuccess) 
				{
					success = false;
				}
			} 
		}
		boolean tmpSuccess = addNewEntries(newZipStream);
		if (!tmpSuccess) 
		{
		  success = false;
		}
		try 
		{
		  newZipStream.close();
		}
		catch (Exception e) 
		{
		  success = false;
		  e.printStackTrace();
		}
	
		// If there were no problems with creating the new temporary
		// file, remove the old file and copy the temporary one to the
		// old file name
		if (success) 
		{
			try 
			{
				File oldFile = new File(oldZipFileName);
				oldFile.delete();
				File newFile = new File(newZipFileName);
				newFile.renameTo(oldFile);
				System.out.println("Succesfully updated " + oldFile);
	    }
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return true;
		}
		else 
		{
			System.err.println("Something went wrong, did not update " + oldZipFileName);
			return false;
		}
	}


	/** Create a new zip file
	 * @param zip The name of the file to be created
	 */
	private void makeNewZip(String zip) 
	{
		try 
		{
			ZipOutputStream zipOutput = new ZipOutputStream (new FileOutputStream(zip));
			addNewEntries(zipOutput);
			zipOutput.close();
			System.out.println("Succesfully created new " + zip);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}


	/** Add the entries which have been stored in the list
	 * @param zos The output stream for the zip file
	 */
	private boolean addNewEntries(ZipOutputStream zos) 
	{
		boolean success = true;
		Enumeration newEntries = fileStrings.keys();
		while (newEntries.hasMoreElements()) 
		{
			String newEntryName = newEntries.nextElement().toString();
			boolean tmpSuccess = addNewEntry(zos, newEntryName);
			if (!tmpSuccess) 
			{
				success = false;
			}
		}
		return success;
	}


  /** Add a new entry to the zip file
   * @param stream the output stream to write the entry to
   * @param fileName the name of the file to be created
   */
	private boolean addNewEntry(ZipOutputStream stream, String fileName) 
	{
		boolean success = true;
		String text = fileStrings.get(fileName).toString();
		String encoding = fileEncodings.get(fileName).toString();
		try 
		{
			ZipEntry ent = new ZipEntry(fileName);
			ent.setComment(encoding);
			ent.setMethod(ZipEntry.DEFLATED);
			stream.putNextEntry(ent);
			stream.write(text.getBytes(encoding));
			stream.closeEntry();
		}
		catch (IOException e) 
		{
			success = false;
			e.printStackTrace();
		}
		return success;
	}


	/**
	 * Copy an entry unchanged from the existing zip file into a new
	 * zip file.  Done with byte arrays because I think it's the
	 * fastest way
	 * @param oldZipFile the existing zip file
	 * @param newOUtputstream the output stream for the new zip file
	 * @param ent the entry to be copied */
	private boolean copyOldEntry(ZipFile oldZipFile, ZipOutputStream newOutputStream, ZipEntry ent) 
	{
		boolean success = true;
		InputStream inputStream = null;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try 
		{
			// create an imput stream specifically for this ZipEntry,
			// from the ZipFile it is in
			
			inputStream = oldZipFile.getInputStream(ent);
			
			// read the contents of this ZipEntry and put them in the
			// ByteArrayOutputStream
			
			byte [] b = new byte [8092];
			int n;
			while ((n = inputStream.read(b)) > -1) 
			{
				baos.write(b, 0, n);
			}

			// convert the ByteArrayOutputStream to a byte array
			
			byte[] buf = baos.toByteArray();
			
			// write the new byte array to the new zip file
			
			ent.setMethod(ZipEntry.DEFLATED);
			newOutputStream.putNextEntry(ent);
			newOutputStream.write(buf);
			newOutputStream.closeEntry();
		}

		catch (IOException e) 
		{
			success = false;
			e.printStackTrace();
		}
		return success;
	}

    
	/**
	 * load a file from the zip and store its contents as a
	 * inputStream (which will be used by MpiroDocument to parse the
	 * document
	 * @param fileNames, a String (one file) or ArrayList (several files)
	 */
	public void loadFiles(Object fileNames) 
	{
		ArrayList names;
		if (fileNames instanceof String) 
		{
			names = new ArrayList();
			names.add(fileNames);
		}
		else if (fileNames instanceof ArrayList) 
		{
			names = (ArrayList)fileNames;
		}
		else 
		{
			return;
		}

		ZipFile zf;
		
		try 
		{
			zf = new ZipFile(zipFileName);
		}
		
		catch (java.util.zip.ZipException z) 
		{
			z.printStackTrace();
			return;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return;
		}

		Iterator fileNameIter = names.iterator();
		while (fileNameIter.hasNext()) 
		{
			String fileName = fileNameIter.next().toString();
			try 
			{
				ZipEntry zippo = zf.getEntry(fileName);
				if (zippo == null) 
				{
					System.out.println("Could not find file " + fileName + " in zip file " + zipFileName);
				}
				else 
				{
					String encoding = zippo.getComment();
					if (encoding == null) 
					{
						encoding = DEFAULT_ENCODING;
					}
					fileEncodings.put(fileName, encoding);
					InputStream is = zf.getInputStream(zippo);
					
					zipContents.put(fileName, is);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public InputStream getFile(String file) 
	{
		loadFiles(file);
		return (InputStream)zipContents.get(file);
	}
	
	public String getName() 
	{
		return zipFileName;
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
