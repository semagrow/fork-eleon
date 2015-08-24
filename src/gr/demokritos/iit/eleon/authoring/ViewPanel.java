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

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public class ViewPanel extends JPanel
{
	public ViewPanel(ImageIcon ii, String commentText, int fontSize)
	{
		setLayout(new GridBagLayout());
		setBorder(new EmptyBorder(5,5,5,5));
		setBackground(new Color(255,255,255));
		
		JLabel iconButton = new JLabel();
		iconButton.setIcon(ii);
		iconButton.setPreferredSize(new Dimension(ii.getIconWidth(), ii.getIconHeight()));
		//JButton iconButton = new JButton(ii);
		//iconButton.setPreferredSize(new Dimension(135,186));
		//iconButton.setBackground(new Color(255,255,255));
		
	  JEditorPane comment = new JEditorPane();
	  comment.setContentType("text/html");

    if (commentText.length() < 100)
    {
	    comment.setText("<html><body marginwidth=\"0\" marginheight=\"0\" " +
	                    "topmargin=\"0\" leftmargin=\"0\"><table border=\"0\" " +
	                    "cellpading=\"0\" width=100%><tr><td><font size=\"" +
	                    fontSize +
	                    "px\" face=\"" + Mpiro.selectedFont + "\">" +
	                    commentText +
	                    "</font></td></tr></table>" +
	                    "</body></html>");
    }
    else
    {
	    comment.setText("<html><body marginwidth=\"0\" marginheight=\"0\" " +
	                    "topmargin=\"0\" leftmargin=\"0\"><table border=\"0\" " +
	                    "cellpading=\"0\" width=100%><tr><td><font size=\"" +
	                    fontSize +
	                    "px\" face=\"" + Mpiro.selectedFont + "\">" +
	                    commentText +
	                    "</font></td></tr></table>" +
	                    "<p align=left><font size=2>-------------------<br>&copy;2002 Mpiro</font></p>" +
	                    "</body></html>");
    }
    int len = comment.getText().length();
    //Rectangle len = comment.getBounds();
    //int lenTier = len.height;
    int lenTier = len/2 - 100;

    comment.setPreferredSize(new Dimension(240, lenTier));
    comment.setEditable(false);
    comment.select(0,0);

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.NORTH;
    c.weightx = 0.0; c.weighty = 0.0;
    this.add(iconButton, c);
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0; c.weighty = 1.0;
    c.gridx=1;
    this.add(comment, c);
	}

  public ViewPanel(ImageIcon ii, String[] commentText, int fontSize)
  {
	  setLayout(new GridBagLayout());
	  setBorder(new EmptyBorder(5,5,5,5));
	  setBackground(new Color(255,255,255));
	
	  JLabel iconButton = new JLabel();
	  iconButton.setIcon(ii);
	  iconButton.setPreferredSize(new Dimension(ii.getIconWidth(),ii.getIconHeight()));
	  //JButton iconButton = new JButton(ii);
	  //iconButton.setPreferredSize(new Dimension(135,186));
	  //iconButton.setBackground(new Color(255,255,255));
	
	  JEditorPane comment = new JEditorPane();
	  comment.setContentType("text/html");

    /*if (commentText[1].length() < 100)
    {
        comment.setText("<html><body marginwidth=\"0\" marginheight=\"0\" " +
                        "topmargin=\"0\" leftmargin=\"0\"><table border=\"0\" " +
                        "cellpading=\"0\" width=100%><tr><td><font size=\"" +
                        fontSize +
                        "px\" face=\"" + Mpiro.selectedFont + "\">" +
                        commentText[0] + "<br><br>" +
                        commentText[1] + "<br>" +
                        commentText[2] +
                        "</font></td></tr></table>" +
                        "</body></html>");
    }
		else*/
		//      {
    comment.setText("<html><body marginwidth=\"0\" marginheight=\"0\" " +
                    "topmargin=\"0\" leftmargin=\"0\"><table border=\"0\" " +
                    "cellpading=\"0\" width=100%><tr><td><font size=\"" +
                    fontSize +
                    "px\" face=\"" + Mpiro.selectedFont + "\"><b>" +
                    commentText[0] + "</b><br><br>" +
                    commentText[1] + "<br><br>" +
                    "<hr noshade width=50% color=c0c0c0>" +
                    commentText[2] +
                    "</font></td></tr></table>" +
                    "</body></html>");
  //      }


    int len = comment.getText().length();
    //Rectangle len = comment.getBounds();
    //int lenTier = len.height;
    int lenTier = len/2 - 30;

    comment.setPreferredSize(new Dimension(240, lenTier));
    //System.out.println(this.getHeight() + "   " + this.getWidth());
    //comment.setPreferredSize(new Dimension(this.getHeight(), this.getWidth()));
    comment.setEditable(false);
    comment.select(0,0);

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.NONE;
    c.anchor = GridBagConstraints.NORTH;
    c.weightx = 0.0; c.weighty = 0.0;
    this.add(iconButton, c);
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0; c.weighty = 1.0;
    c.gridx=1;
    this.add(comment, c);
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
