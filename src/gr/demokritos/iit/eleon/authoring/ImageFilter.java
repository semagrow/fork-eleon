//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * <p>Title: ImageFilter</p>
 * <p>Description: A file filter allowing only image files to be shown</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: NCSR "Demokritos"</p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class ImageFilter extends FileFilter 
{
	// Accept all directories and all IMAGE files.
	public boolean accept(File f) 
	{
	  if (f.isDirectory()) 
	  {
	  	return true;
	  }

		String extension = getExtension(f);
		if (extension != null) 
		{
	    if (extension.equals("jpeg") ||
	        extension.equals("jpg") ||
	        extension.equals("gif") ||
	        extension.equals("png") ||
	        extension.equals("tiff") ||
	        extension.equals("tif") ||
	        extension.equals("bmp") ||
	        extension.equals("xpm") )
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }
		}
		return false;
	}


	// The description of this filter
	public String getDescription() 
	{
		return "Image files";
	}

  public String getExtension(File f) 
  {
	  String ext = null;
	  String s = f.getName();
	  int i = s.lastIndexOf(".");
	  if (i>0 && i < s.length()-1) 
	  {
	  	ext = s.substring(i+1).toLowerCase();
	  }
	  return ext;
  }

  /*
  public static String checkExtension(String file, String extension) {
      String ext = extension;
      String s = file;
      if (s.substring(s.length()-6).equalsIgnoreCase(ext)) {
           return s;

      } else {
           String newname = s + ".mpiro";
           return newname;
      }
  }
  */
}