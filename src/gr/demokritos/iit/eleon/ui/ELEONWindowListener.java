/***************

<p>Title: ELEON Window Listener</p>

<p>Description: </p>

<p>Copyright (c) 2001-2004 National Centre for Scientific Research "Demokritos"</p>

@author Dimitris Spiliotopoulos (MPIRO, 2001-2004)

***************/

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


package gr.demokritos.iit.eleon.ui;


import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.Mpiro;
import gr.demokritos.iit.eleon.authoring.MpiroFileFilter;
import gr.demokritos.iit.eleon.authoring.QueryHashtable;
import gr.demokritos.iit.eleon.authoring.QueryLexiconHashtable;
import gr.demokritos.iit.eleon.authoring.QueryOptionsHashtable;
import gr.demokritos.iit.eleon.authoring.QueryUsersHashtable;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ELEONWindowListener extends WindowAdapter
implements WindowListener
{
        public void windowClosing(WindowEvent e) {
            Object[] options = {
                LangResources.getString(Mpiro.selectedLocale, "yes_text"),
                LangResources.getString(Mpiro.selectedLocale, "no_text"),
                LangResources.getString(Mpiro.selectedLocale, "cancel_text")
            };
            int j = JOptionPane.showOptionDialog(Mpiro.win,
                                                 LangResources.getString(Mpiro.selectedLocale, "wouldYouLikeToSaveTheChanges-ETC_dialog"),
                                                 LangResources.getString(Mpiro.selectedLocale, "warning_dialog"),
                                                 JOptionPane.DEFAULT_OPTION,
                                                 JOptionPane.QUESTION_MESSAGE,
                                                 new ImageIcon( Mpiro.obj.image_mpirotext ),
                                                 options,
                                                 options[0]);
            if (j == 0) {
                Mpiro.win.d.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                MpiroFileFilter filter = new MpiroFileFilter();
                Mpiro.win.d.setFileFilter(filter);
                int returnVal = Mpiro.win.d.showSaveDialog( Mpiro.win );
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fileName = Mpiro.win.d.getSelectedFile().toString();
                    fileName = MpiroFileFilter.checkExtension(fileName, ".mpiro");
                    try {
                        FileOutputStream output = new FileOutputStream(fileName);
                        ObjectOutputStream p = new ObjectOutputStream(output);
                        p.writeObject( (Hashtable) QueryOptionsHashtable.mainOptionsHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.mainDBHashtable);
                        
                        p.writeObject( (Hashtable) QueryLexiconHashtable.mainLexiconHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUsersHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUserModelHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainUserModelStoryHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.propertiesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.valueRestrictionsHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.equivalentClassesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.superClassesHashtable);
                        p.writeObject( (Hashtable) QueryHashtable.annotationPropertiesHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.robotsHashtable);
                        p.writeObject( (Hashtable) QueryUsersHashtable.mainRobotsModelHashtable);
                        p.writeObject(QueryHashtable.robotCharVector);
                        p.writeObject(QueryHashtable.robotCharValuesHashtable);
                        p.flush();
                        p.close();
                    }
                    catch (java.io.IOException IOE) {
                        System.out.println(" |||| Save-domain Exception |||| " + IOE);
                        IOE.printStackTrace();
                    }
                    System.exit(0);
                }
                else {
                    return;
                }
                System.exit(0);
            }
            if (j == 1) {
                System.exit(0);
            }
            else {
                return;
            }
            System.exit(0);
        }
    }

