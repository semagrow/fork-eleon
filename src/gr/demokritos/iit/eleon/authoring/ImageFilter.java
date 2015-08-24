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
