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

import gr.demokritos.iit.eleon.struct.QueryHashtable;
import java.net.URL;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.*;
import java.util.*;
import java.io.*;


/**
 * <p>Title: ExportUtilsWebinfo</p>
 * <p>Description: The following methods are used to create the following .xml files in
 * "exprimo/Resources/Webinfo/"
 * <p>imagesinfo.xml (containing information about entities/generic-entities and their "images")</p>
 * <p>englishinfo.xml (containing english specific information about entities/generic-entities and their "titles" and "notes")
 * <p>italianinfo.xml (containing italian specific information about entities/generic-entities and their "titles" and "notes")
 * <p>greekinfo.xml (containing greek specific information about entities/generic-entities and their "titles" and "notes")
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Dimitris Spiliotopoulos
 * @version 1.0
 */
public class ExportUtilsWebinfo 
{
	static File webinfo;
	static String emptyspaceFile = new String("");
	
	
	/**
	 * Create the export directories
	 * @param dirPath the path for the directory to be created
	 */
	public static void createExportDirectories(String dirPath) 
	{
		webinfo = new File(dirPath + "/Webinfo");
		webinfo.mkdir();
	} // createExportDirectories


	/** Create the Images web info file */
	public static void createImagesInfo() 
	{
		String imagesinfo = webinfo.toString() + "/" + "imagesinfo" + ".xml";
	
	  try
	  {
	    OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(imagesinfo), "ISO-8859-1");
	    PrintWriter p = new PrintWriter(output);
	    p.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
	
	    p.println(emptyspaceFile);
	
	    Hashtable allEntitiesHashtable = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
	    for (Enumeration k = allEntitiesHashtable.keys(), e = allEntitiesHashtable.elements(); k.hasMoreElements(); )
	    {
	      String entity = k.nextElement().toString();
	
	      p.println("<entity is=\"" + entity + "\">");
	
	      // Finding the "parents" //////////////////////////////////////////////
	      p.println("  <parents>");

				Vector allParentsVector = Mpiro.win.struc.getFullPathParentsVectorFromMainDBHashtable(entity, "Entity+Generic");
				Enumeration allParentsVectorEnum = allParentsVector.elements();
				while (allParentsVectorEnum.hasMoreElements())
				{
				  String parent = allParentsVectorEnum.nextElement().toString();
				  p.println("    <parent is=\"" + parent + "\"/>");
				}
				p.println("  </parents>");

        // Finding the "images" //////////////////////////////////////////////
        Vector entityNodeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entity);
        Vector entityTableVector = (Vector)entityNodeVector.get(0);
        Vector entityIndependentTableVector = (Vector)entityTableVector.get(0);

        // the "images" row is the 3rd
        Vector imagesRowVector = (Vector)entityIndependentTableVector.get(2);
        String imagesString = imagesRowVector.get(1).toString();

        p.println("  <images>");

				Vector imagesListVector = ExportUtilsWebinfo.determineImagesList(imagesString);
				Enumeration imagesListVectorEnum = imagesListVector.elements();
				while (imagesListVectorEnum.hasMoreElements())
				{
				  String image = imagesListVectorEnum.nextElement().toString();
				  p.println("    <image is=\"" + image + "\"/>");
				}
				p.println("  </images>");
				p.println("</entity>");
				p.println(emptyspaceFile);
			}
      p.flush();
      p.close();
		}
    catch (java.io.IOException IOE) 
    {
      System.out.println("|||| Exception ||||");
      IOE.printStackTrace();
    }
	}


	/**
	 * create the export date used in "createInstancesGramAndMsgcat()"
	 * @param imagesString the list of the images
	 * @return a vector containing all images in the imagesString
	 */
	static Vector determineImagesList(String imagesString) 
	{
	  Vector imagesListVector = new Vector();
	
	  if (imagesString.equalsIgnoreCase("Select images ....."))
	  {
	  	// do nothing
	  }
    else
    {
      StringTokenizer images = new StringTokenizer(imagesString, ">");
      while (images.hasMoreTokens())
      {
        String image = images.nextToken();
        imagesListVector.addElement(image.substring(1));
      }
    }
		return imagesListVector;
	}


	/** Create the English, Italian, Greek titles & notes web info files */
	public static void createLanguageInfo() 
	{
    String englishinfo = webinfo.toString() + "/" + "englishinfo" + ".xml";
    String italianinfo = webinfo.toString() + "/" + "italianinfo" + ".xml";
    String greekinfo = webinfo.toString() + "/" + "greekinfo" + ".xml";

    try 
    {
	    OutputStreamWriter output1 = new OutputStreamWriter(new FileOutputStream(englishinfo), "ISO-8859-1");
	    PrintWriter p1 = new PrintWriter(output1);
	    p1.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
	
	    OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(italianinfo), "ISO-8859-3");
	    PrintWriter p2 = new PrintWriter(output2);
	    p2.println("<?xml version=\"1.0\" encoding=\"ISO-8859-3\"?>");
	
	    OutputStreamWriter output3 = new OutputStreamWriter(new FileOutputStream(greekinfo), "ISO-8859-7");
	    PrintWriter p3 = new PrintWriter(output3);
	    p3.println("<?xml version=\"1.0\" encoding=\"ISO-8859-7\"?>");
	
	    p1.println(emptyspaceFile);
	    p2.println(emptyspaceFile);
	    p3.println(emptyspaceFile);
	
	    Hashtable allEntitiesHashtable = Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity+Generic");
	    for (Enumeration k = allEntitiesHashtable.keys(), e = allEntitiesHashtable.elements(); k.hasMoreElements(); )
	    {
	      String entity = k.nextElement().toString();
	      String parent = e.nextElement().toString();
	
	      Vector entityNodeVector = (Vector)Mpiro.win.struc.getEntityTypeOrEntity(entity);
	      Vector entityTableVector = (Vector)entityNodeVector.get(0);
	      Vector entityEnglishTableVector = (Vector)entityTableVector.get(1);
	      Vector entityItalianTableVector = (Vector)entityTableVector.get(2);
	      Vector entityGreekTableVector = (Vector)entityTableVector.get(3);
	
	      p1.println("<entity is=\"" + entity + "\">");
	      //p1.println("  <parent is=\"" + parent + "\">");
	      p2.println("<entity is=\"" + entity + "\">");
	      //p2.println("  <parent is=\"" + parent + "\">");
	      p3.println("<entity is=\"" + entity + "\">");
	      //p3.println("  <parent is=\"" + parent + "\">");
	
	      // the "title" row is "0"
	      Vector titleRowEnglishVector = (Vector)entityEnglishTableVector.get(0);
	      Vector titleRowItalianVector = (Vector)entityItalianTableVector.get(0);
	      Vector titleRowGreekVector = (Vector)entityGreekTableVector.get(0);
	      String titleEnglishString = titleRowEnglishVector.get(1).toString();
	      String titleItalianString = titleRowItalianVector.get(1).toString();
	      String titleGreekString = titleRowGreekVector.get(1).toString();
	
	      p1.println("  <title is=\"" + replaceStr(titleEnglishString,"\"","&quot;") + "\"/>");
	      p2.println("  <title is=\"" + replaceStr(titleItalianString,"\"","&quot;") + "\"/>");
	      p3.println("  <title is=\"" + replaceStr(titleGreekString,"\"","&quot;") + "\"/>");
	
	      // the "notes" row is "3" (english), "5" (italian), "9" (greek)
	      Vector notesRowEnglishVector = (Vector)entityEnglishTableVector.get(3);
	      Vector notesRowItalianVector = (Vector)entityItalianTableVector.get(5);
	      Vector notesRowGreekVector = (Vector)entityGreekTableVector.get(9);
	      String notesEnglishString = notesRowEnglishVector.get(1).toString();
	      String notesItalianString = notesRowItalianVector.get(1).toString();
	      String notesGreekString = notesRowGreekVector.get(1).toString();

        p1.println("  <notes is=\"" + replaceStr(notesEnglishString,"\"","&quot;") + "\"/>");
        p2.println("  <notes is=\"" + replaceStr(notesItalianString,"\"","&quot;") + "\"/>");
        p3.println("  <notes is=\"" + replaceStr(notesGreekString,"\"","&quot;") + "\"/>");

        p1.println("</entity>");
        p2.println("</entity>");
        p3.println("</entity>");

        p1.println(emptyspaceFile);
        p2.println(emptyspaceFile);
        p3.println(emptyspaceFile);
			}//for
      p1.flush();
      p1.close();

      p2.flush();
      p2.close();

      p3.flush();
      p3.close();
		}
		catch (java.io.IOException IOE) 
		{
		  System.out.println("|||| Exception ||||");
		  IOE.printStackTrace();
		}
	} // createLanguageInfo


	private static String replaceStr(String text, String rep, String with)
	{
	  StringBuffer buff = new StringBuffer();
	  int index=0;
	  int endindex=0;
	  do
	  {
      index=text.indexOf(rep,endindex);
      if(index > -1)
      {
        buff.append(text.substring(endindex,index));
        buff.append(with);
        endindex=index+rep.length();
      }
	  }
    while(index > -1);
    if(endindex > 0) buff.append(text.substring(endindex));

    if(buff.length()>0) return buff.toString();
    else return text;
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
