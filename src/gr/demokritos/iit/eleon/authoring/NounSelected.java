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
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.JTree;
import java.util.*;
import java.awt.Dimension; //theofilos

import java.awt.event.*;
import java.io.*;
import javax.swing.event.*;
import java.io.IOException;

import javax.swing.text.*;

import java.awt.Color; //theofilos

//Edited and enhanced by Theofilos Nickolaou
public class NounSelected extends JScrollPane 
{
  JTextPane tp; //theofilos

  public NounSelected(Vector vec) 
  {
		tp = new JTextPane(); //theofilos 
		tp.setPreferredSize(new Dimension(250, 40)); //theofilos
		tp.setMaximumSize(new Dimension(250, 40)); //theofilos
		tp.setMinimumSize(new Dimension(250, 40)); //theofilos
		SimpleAttributeSet att_set = new SimpleAttributeSet(); //theofilos
		//##########################
    Document doc = tp.getStyledDocument(); //theofilos
		//############################
			
		tp.setBackground(new Color(190,190,190)); //theofilos
		setViewportView(tp);

		for (int k=0; k<vec.size(); k++) 
		{
			try     //theofilos
			{     //theofilos
      	doc.insertString(doc.getLength(), vec.elementAt(k).toString(), att_set);   //theofilos
    	}   //theofilos
    	catch (BadLocationException e)   //theofilos
    	{  //theofilos
      	System.err.println("Bad location");  //theofilos
      	return;   //theofilos
    	}   //theofilos
		 //  tp.append(vec.elementAt(k).toString()); //theofilos
		}
		//tp.setLineWrap(true);   //theofilos
		//tp.setWrapStyleWord(true);   //theofilos
		tp.setEditable(false); 
	}

  public void updateNouns(Vector v,String parent) 
  {
     
             
      
      SimpleAttributeSet att_set = new SimpleAttributeSet();  //theofilos
    Document doc = tp.getStyledDocument();  //theofilos

		// clear text area
		tp.setText("");
		// get parent-node's nounVector
		//DefaultMutableTreeNode parent = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
                String last=DataBasePanel.last.toString();
                if (last.equalsIgnoreCase("Data Base")) return;
               // DefaultMutableTreeNode parent = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
                   if(last.substring(0,last.length()-1).endsWith("_occur"))
                {
                    last=last.substring(0, last.length()-7);
                }
               //  Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                  for (int k=0; k<v.size(); k++) 
		{
			String noun = v.elementAt(k).toString();
                // Enumeration allTypesNames=allEntityTypes.keys();
                   Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
                 while(allTypesNames.hasMoreElements())
                    {//DefaultMutableTreeNode nextNode=null;
                      // Object nextEl=allTypesNames.nextElement();
               DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                        
                         
                        if (nextEl.toString().startsWith(last+"_occur")||nextEl.toString().equalsIgnoreCase(last))
                        {System.out.println("LLLLLLLLLLLLLK"+nextEl.toString());
                   parent=nextEl.getParent().toString();
                   System.out.println(noun+"   "+nextEl.getParent().toString());
		NodeVector pn = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(parent.toString());
		Vector pNounVector = (Vector)pn.elementAt(2);
try 
				{
		if (doc.getText(0,doc.getLength()).indexOf(noun)==-1){
                    
			// check if noun comes from parent-node's nounVector
                   //     if (doc.getText(0,doc.getLength()).indexOf(noun)== -1){
			if (pNounVector.contains(noun)) 
			{System.out.println("@@@"+noun);
				StyleConstants.setForeground(att_set, Color.BLUE);   //theofilos
				//theofilos
	    		doc.insertString(doc.getLength(), noun + "*" + "  ", att_set);//theofilos
	  		 //theofilos
	  		//theofilos
				StyleConstants.setForeground(att_set, Color.BLACK);//theofilos
				 //tp.append(noun + "*" + "  ");   //theofilos
				TreePreviews.dbnp.nounLabel.setText(
							LangResources.getString(Mpiro.selectedLocale, "nounsThatCanBeUsedToDescribe_text") +
		          												" \"" +DataBasePanel.last.toString() + "\" " +
																			LangResources.getString(Mpiro.selectedLocale, "meansInherited_text"));                                                                
			} 
			 }//theofilos
    		//catch (BadLocationException e) 
    		//{//theofilos
      		//System.err.println("Bad location");//theofilos
      		//return;//theofilos
    		//}//theofilos
				//tp.append(noun + "  ");//theofilos
			//}
		
}
	
                        catch (BadLocationException e) 
	  		{//theofilos
	    		System.err.println("Bad location");//theofilos
	    		return;//theofilos
	  		}}}}
  
  for (int k=0; k<v.size(); k++) 
		{
			String noun = v.elementAt(k).toString();
		try {	if (doc.getText(0,doc.getLength()).indexOf(noun)==-1){
                            
				
			//	{//theofilos
      		doc.insertString(doc.getLength(), noun + "  ", att_set);//theofilos
                }}   
                catch (BadLocationException e) 
	  		{//theofilos
	    		System.err.println("Bad location");//theofilos
	    		return;//theofilos
	  		}
    		}
		}
  
  
  public void updateNouns(Vector v) 
  {
		SimpleAttributeSet att_set = new SimpleAttributeSet();  //theofilos
    Document doc = tp.getStyledDocument();  //theofilos

		// clear text area
		tp.setText("");
		// get parent-node's nounVector
		//DefaultMutableTreeNode parent = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
                String last=DataBasePanel.last.toString();
               // DefaultMutableTreeNode parent = (DefaultMutableTreeNode)DataBasePanel.last.getParent();
                   if(last.substring(0,last.length()-1).endsWith("_occur"))
                {
                    last=last.substring(0, last.length()-7);
                }
               //  Hashtable allEntityTypes = (Hashtable) Mpiro.win.struc.getEntityTypesAndEntitiesHashtableFromMainDBHashtable("Entity type");
                  for (int k=0; k<v.size(); k++) 
		{
			String noun = v.elementAt(k).toString();
                // Enumeration allTypesNames=allEntityTypes.keys();
                   Enumeration allTypesNames= DataBasePanel.top.preorderEnumeration();
                 while(allTypesNames.hasMoreElements())
                    {//DefaultMutableTreeNode nextNode=null;
                      // Object nextEl=allTypesNames.nextElement();
               DefaultMutableTreeNode nextEl=(DefaultMutableTreeNode) allTypesNames.nextElement();
                        
                         
                        if (nextEl.toString().startsWith(last+"_occur")||(nextEl.toString().equalsIgnoreCase(last)))
                        {System.out.println("###############"+nextEl.toString());
                   String parent=nextEl.getParent().toString();
                   System.out.println(noun+"   "+nextEl.getParent().toString());
		NodeVector pn = (NodeVector)Mpiro.win.struc.getEntityTypeOrEntity(Mpiro.win.struc.nameWithoutOccur(parent.toString()));
		Vector pNounVector = (Vector)pn.elementAt(2);
try 
				{
		if (doc.getText(0,doc.getLength()).indexOf(noun)==-1){
                    
			// check if noun comes from parent-node's nounVector
                   //     if (doc.getText(0,doc.getLength()).indexOf(noun)== -1){
			if (pNounVector.contains(noun)) 
			{System.out.println("@@@"+noun);
				StyleConstants.setForeground(att_set, Color.BLUE);   //theofilos
				//theofilos
	    		doc.insertString(doc.getLength(), noun + "*" + "  ", att_set);//theofilos
	  		 //theofilos
	  		//theofilos
				StyleConstants.setForeground(att_set, Color.BLACK);//theofilos
				 //tp.append(noun + "*" + "  ");   //theofilos
				TreePreviews.dbnp.nounLabel.setText(
							LangResources.getString(Mpiro.selectedLocale, "nounsThatCanBeUsedToDescribe_text") +
		          												" \"" +DataBasePanel.last.toString() + "\" " +
																			LangResources.getString(Mpiro.selectedLocale, "meansInherited_text"));                                                                
			} 
			 }//theofilos
    		//catch (BadLocationException e) 
    		//{//theofilos
      		//System.err.println("Bad location");//theofilos
      		//return;//theofilos
    		//}//theofilos
				//tp.append(noun + "  ");//theofilos
			//}
		
}
	
                        catch (BadLocationException e) 
	  		{//theofilos
	    		System.err.println("Bad location");//theofilos
	    		return;//theofilos
	  		}}}}
  
  for (int k=0; k<v.size(); k++) 
		{
			String noun = v.elementAt(k).toString();
		try {	if (doc.getText(0,doc.getLength()).indexOf(noun)==-1){
                            
				
			//	{//theofilos
      		doc.insertString(doc.getLength(), noun + "  ", att_set);//theofilos
                }}   
                catch (BadLocationException e) 
	  		{//theofilos
	    		System.err.println("Bad location");//theofilos
	    		return;//theofilos
	  		}
    		}
  
  }
  

	public Vector getNouns() 
	{
		Vector nounVector = new Vector();
		String nounString = tp.getText(); //theofilos was JTextArea.With JTextPane will it do the same thing?
		StringTokenizer toc = new StringTokenizer(nounString);
		while (toc.hasMoreTokens()) 
		{
			String noun = toc.nextToken().toString();
			if (noun.equalsIgnoreCase("/")) {}
			else 
			{
				nounVector.addElement(noun);
			}
		}
		return nounVector;
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
