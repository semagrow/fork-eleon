/***************

<p>Title: Main class</p>

<p>Description:
Entry point for the ELEON application.
</p>

<p>
This file is part of the ELEON Ontology Authoring and Enrichment Tool.<br>
Copyright (c) 2001-2011 National Centre for Scientific Research "Demokritos"<br>
Please see at the bottom of this file for license details.
</p>

@author Dimitris Spiliotopoulos (MPIRO, 2001-2004)
@author Kostas Stamatakis
@author Theofilos Nikolaou
@author Maria Prospathopoulou
@author Spyros Kallonis
@author Dimitris Bilidas (XENIOS & INDIGO, 2007-2009; RoboSKEL 2010-2011)
@author Stasinos Konstantopoulos (INDIGO, 2009; RoboSKEL 2011)

***************/


package gr.demokritos.iit.eleon.authoring;


import java.awt.*;
import java.util.*;
import java.util.zip.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

import gr.demokritos.iit.eleon.ui.StoriesPanel;
import gr.demokritos.iit.eleon.ui.lang.LangChooser;


public class Mpiro
{

	/* keeps the only instance */
	static public Mpiro obj = null;
	static public gr.demokritos.iit.eleon.ui.ELEONWindow win;
	
	/* all images used in the UI */
    Image image_top;
    Image image_topa;
    Image image_topb;
    Image image_basic;
    Image image_built;
    Image image_closed;
    Image image_leaf;
    public Image image_robotbrain;
    Image image_generic;
    Image image_noun;
    Image image_verb;
    Image image_l;
    Image image_n;
    Image image_v;
    
    Image image_enLeaf, image_itLeaf, image_grLeaf;
    Image image_uk, image_greece, image_italy;
    public Image image_3dUK, image_3dGreece, image_3dItaly;

    Image image_independent;
    public Image image_mpirotext;
    Image image_hadra;
    public Image image_corner;
    public Image image_mpiro;
    public Image image_new;
    public Image image_open;
    public Image image_save;

    public Image image_owl;
    public Image image_owlexport;
    public Image image_owlimport;
    Image image_owlClassChecked;
    Image image_owlClassUnchecked;

    public Image image_exportText;
    
    //Image image_exportXML;
    public Image image_user, image_users;
    public Image image_robot, image_robots, image_robotsAndUsers;
    
    public Image image_exit;
    Image image_comboEIG;
    Image image_comboEI;
    Image image_comboEG;
    Image image_comboIG;
    Image image_comboE;
    Image image_comboI;
    Image image_comboG;
    Image image_comboX;


	void init()
	{
		ClassLoader ld = this.getClass().getClassLoader();
		Toolkit tk = Toolkit.getDefaultToolkit();
		String pref = "gr/demokritos/iit/eleon/ui/images/";
		
		image_top = tk.createImage(ld.getResource(pref+"top.gif"));
		image_topa = tk.createImage(ld.getResource(pref+"topa.gif"));
		image_topb = tk.createImage(ld.getResource(pref+"topb.gif"));
		image_basic = tk.createImage(ld.getResource(pref+"basic.gif"));
		image_built = tk.createImage(ld.getResource(pref+"built.gif"));
		image_closed = tk.createImage(ld.getResource(pref+"closed.gif"));
		image_leaf = tk.createImage(ld.getResource(pref+"leaf.gif"));
		image_robotbrain = tk.createImage(ld.getResource(pref+"robotcharac.jpg"));
		image_generic = tk.createImage(ld.getResource(pref+"generic.gif"));
		image_noun = tk.createImage(ld.getResource(pref+"noun.gif"));
		image_verb = tk.createImage(ld.getResource(pref+"verb.gif"));
		image_l = tk.createImage(ld.getResource(pref+"l.gif"));
		image_n = tk.createImage(ld.getResource(pref+"n.gif"));
		image_v = tk.createImage(ld.getResource(pref+"v.gif"));
		
		image_enLeaf = tk.createImage(ld.getResource(pref+"enLeaf.gif"));
		image_itLeaf = tk.createImage(ld.getResource(pref+"itLeaf.gif"));
		image_grLeaf = tk.createImage(ld.getResource(pref+"grLeaf.gif"));
		
		image_uk = tk.createImage(ld.getResource(pref+"uk.gif"));
		image_greece = tk.createImage(ld.getResource(pref+"greece.gif"));
		image_italy = tk.createImage(ld.getResource(pref+"italy.gif"));
		
		image_independent = tk.createImage(ld.getResource(pref+"independent.gif"));
		image_mpirotext = tk.createImage(ld.getResource(pref+"mpirotext1.jpg"));
		image_hadra = tk.createImage(ld.getResource(pref+"hadra.jpg"));
		image_corner = tk.createImage(ld.getResource(pref+"corner.jpg"));
		image_mpiro = tk.createImage(ld.getResource(pref+"mpiro.gif"));
		image_new = tk.createImage(ld.getResource(pref+"ITnew.gif"));
		image_open = tk.createImage(ld.getResource(pref+"ITopen.gif"));
		image_save = tk.createImage(ld.getResource(pref+"ITsave.gif"));

		image_owl = tk.createImage(ld.getResource(pref+"ITowl.gif"));
		image_owlexport = tk.createImage(ld.getResource(pref+"ITowlExport.gif"));
		image_owlimport = tk.createImage(ld.getResource(pref+"ITowlImport.gif"));
		image_owlClassChecked = tk.createImage(ld.getResource(pref+"owlClassChecked.gif"));
		image_owlClassUnchecked = tk.createImage(ld.getResource(pref+"owlClassUnchecked.gif"));

		image_exportText = tk.createImage(ld.getResource(pref+"ITexportText.gif"));

		//image_exportXML = tk.createImage(jar.getResource(pref+"ITexportXML.gif"));
		image_robot = tk.createImage(ld.getResource(pref+"robot.gif"));
		image_robots = tk.createImage(ld.getResource(pref+"robots.gif"));
		image_robotsAndUsers = tk.createImage(ld.getResource(pref+"robotsAndUsers.gif"));
    
		image_exit = tk.createImage(ld.getResource(pref+"ITexit.gif"));
		image_user = tk.createImage(ld.getResource(pref+"ITuser.jpg"));
		image_users = tk.createImage(ld.getResource(pref+"ManyUsers.png"));
		image_comboEIG = tk.createImage(ld.getResource(pref+"comboEIG.jpg"));
		image_comboEI = tk.createImage(ld.getResource(pref+"comboEI.jpg"));
		image_comboEG = tk.createImage(ld.getResource(pref+"comboEG.jpg"));
		image_comboIG = tk.createImage(ld.getResource(pref+"comboIG.jpg"));
		image_comboE = tk.createImage(ld.getResource(pref+"comboE.jpg"));
		image_comboI = tk.createImage(ld.getResource(pref+"comboI.jpg"));
		image_comboG = tk.createImage(ld.getResource(pref+"comboG.jpg"));
		image_comboX = tk.createImage(ld.getResource(pref+"comboX.jpg"));
		
		image_3dUK = tk.createImage(ld.getResource(pref+"3d_uk_m.gif"));
		image_3dItaly = tk.createImage(ld.getResource(pref+"3d_it_m.gif"));
		image_3dGreece = tk.createImage(ld.getResource(pref+"3d_gr_m.gif"));
	}
	
    // A.I 28/10/02 don't create Structures here, do it via pageFactory later
    // public static Structures emulator = new Structures();

    public static String selectedFont = "Dialog";
    public static boolean debugMode = false;
    public static boolean alltabsMode = false;
    public static boolean resetInteractionHistory = true;
    public static boolean resetInteractionHistoryBeforeEachPreview = true;
   // public static uk.ac.ed.ltg.exprimo.workbench.Factory pageFactory;

    
    //static final long serialVersionUID= 7753555422102686221L;
    // public static MpiroRpcServer eeee;

    public static Color colorG = new Color(100, 100, 100);
    public static Font font = new Font(Mpiro.selectedFont, Font.BOLD, 10);
    public static Border border = new CompoundBorder(new EtchedBorder(EtchedBorder.RAISED),
        new EtchedBorder(EtchedBorder.LOWERED));

    //public static JTabbedPane tabbedPane;
    public static String loadedDomain = new String("");
    public static ZipFile domainZip = null; //spiliot
    public static ZipFile resourcesZip = null; //spiliot
    static StoriesPanel storiespanel;
    
   // public static ComparisonTree comparTree = new ComparisonTree();
  //  public static ComparisonTree statisticalTree = new ComparisonTree();

    final JFileChooser d = new JFileChooser(System.getProperty("user.dir"));

    //private JMenuItem resetInteractionHistoryEmulatorItem;
    public static JCheckBoxMenuItem resetInteractionHistoryBeforeEachPreviewItem; //maria
    public static JCheckBoxMenuItem showAllPagesInPreviewsItem; //maria

    public static JRadioButtonMenuItem exportToDefaultItem; //maria
    public static JRadioButtonMenuItem exportToChoiceItem; //maria
    //public static JRadioButtonMenuItem notAutomaticExportItem; //maria

    ////////////////////////////////

    public static Locale enLocale = new Locale("en", "US");
    public static Locale grLocale = new Locale("el", "GR");
    public static Locale itLocale = new Locale("it", "IT");

    public static Locale selectedLocale;

    public static String activatedPreviewLanguage = "";
    public static boolean restart = false; //spiliot

    public static boolean needExportToEmulator = false; //maria
    public static boolean needExportToExprimo = true; //maria
    public static File jardir = new File(System.getProperty("user.dir")); //maria


    public static void main( String args[] )
    {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("DEBUG")) {
                Mpiro.debugMode = true;
            }
            else if (args[0].equalsIgnoreCase("ALLTABS")) {
                Mpiro.alltabsMode = true;
            }
            else {
                Mpiro.selectedFont = args[0];
            }
            //System.out.println(args[0]);
            //Font givenFont = new Font(args[0], Font.PLAIN, 10);
            //System.out.println("()+++  " + givenFont);
            //String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(Locale.ENGLISH);
            //for (int i=0; i<fonts.length; i++)
            //{
            //    System.out.println("()---  " + fonts[0]);
            //}
            //System.out.println("()+++  " + Mpiro.selectedFont);
        }
        Mpiro.obj = new Mpiro();
        Mpiro.obj.init();
        new LangChooser();
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
