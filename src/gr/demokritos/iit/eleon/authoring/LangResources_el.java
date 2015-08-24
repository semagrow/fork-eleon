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


/**
* <p>Title: LangResources_el</p>
* <p>Description: The greek resource bundle</p>
* <p>Copyright: Copyright (c) 2002-2009</p>
* <p>Company: NCSR "Demokritos"</p>
* @author Dimitris Spiliotopoulos (2002)
* @author Maria Prospathopoulou and Theofilos Nickolaou (2004)
* @version 1.0
*/

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class LangResources_el extends ListResourceBundle
{
	public Object[][] getContents()
	{
		return contents;
  }

  private Object[][] contents =
  {
		// the main buttons
  	{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΝΕΟ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("open_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΑΝΟΙΓΜΑ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("save_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΑΠΟΘ/ΣΗ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΕΞΑΓΩΓΗ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exit_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΕΞΟΔΟΣ") },


		// other buttons
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ok_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΕΝΤΑΞΕΙ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("yes_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΝΑΙ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΟΧΙ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancel_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΑΚΥΡΟ") },


		// the tabs
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usertypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΤΥΠΟΙ_ΧΡΗΣΤΩΝ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("database_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΒΑΣΗ_ΔΕΔΟΜΕΝΩΝ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("lexicon_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΛΕΞΙΚΟ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("documentplanning_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΣΧΕΔΙΑΣΜΟΣ_ΕΓΓΡΑΦΩΝ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("stories_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΙΣΤΟΡΙΕΣ") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("macronodes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΜΑΚΡΟΚΟΜΒΟΙ") },


		// the legend
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("legend_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Υπόμνημα") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("user_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(user)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Χρηστών") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("basic_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Βασικοί") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("entity_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(entity)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Οντοτήτων") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("entities_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Οντότητες") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("generic_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γενικές") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("data_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(data)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Δεδομένων") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nouns_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ουσιαστικά") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verbs_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ρήματα") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("language_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γλωσσικά") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("specific_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εξαρτημένα") },


		// the menus and menu items
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("file_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Αρχείο") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("options_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Επιλογές") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("lookandfeel_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Όψη_&_Αίσθηση") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editPServerAddress_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Τροποποίηση_διεύθυνσης_εξυπηρετητή_εξατομίκευσης") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activatePreviewLanguage_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ενεργοποίηση_γλώσσας_προεπισκόπησης") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistory_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Διαγραφή_προϊστορίας_αλληλεπίδρασης") },
	  { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryEmulator_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Διαγραφή_προϊστορίας_αλληλεπίδρασης_για_τον_EMULATOR") },
	  { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryBeforeEachPreview_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Διαγραφή_προϊστορίας_αλληλεπίδρασης_πριν_από_κάθε_προεπισκόπηση") },
	  { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("showAllPagesInPreviews_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Εμφάνιση_όλων_των_σελίδων_κατά_την_προεπισκόπηση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("automaticExport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Αυτόματη_εξαγωγή") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToDefault_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_...σε_προεπιλεγμένο_φάκελο") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToChoice_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_...σε_άλλο_φάκελο") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("notAutomaticExport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Απενεργοποίηση_αυτόματης_εξαγωγής") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Αγγλικά") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ιταλικά") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ελληνικά") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("shutDownAuthoringTool_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Shut_down_Authoring_tool") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("help_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Βοήθεια") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("about_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Σχετικά") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Νέο") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("open_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Άνοιγμα") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("saveas_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Αποθήκευση_ως...") },
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("owlexport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Εξαγωγή_σε_OWL") },  //kallonis
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("owlimport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Εισαγωγή_από_OWL") },  //kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Εξαγωγή...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exit_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Έξοδος") },


		// the global (Database & Stories tabs ONLY) user-modelling dialog
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("userModelling_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μοντελοποίηση_χρηστών") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usertypesSmall_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι_χρηστών") },
		//{ "interest_text", "ενδιαφέρον" },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("importance_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("σπουδαιότητα") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("repetitions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("επαναλήψεις") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editing_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Επεξεργασία_") },


		// the global dialogs (DDialog 1-13, DirectoryChooser 14-17, ExportDialog 18-19,
		//                     IPChooser 20, OptionsEditDialog 21-22)
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectNewDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλογή_νέας_ημερομηνίας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickToEnterTwoDatesFromTo_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εισαγωγή_2_ημερομηνιών_(από/έως)_____") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectToClearEntry_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή_τιμής_πεδίου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("currentDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ισχύουσα_ημερομηνία:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Νέα_ημερομηνία:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectNewDimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλογή_νέου_μεγέθους") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newDimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Νέo_μέγεθος:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectMultipleEntities_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλογή_πολλαπλών_τύπων") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("multipleEntities_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πολλαπλοί_τύποι:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectImages_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλογή_εικόνων") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newImages_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Νέες_εικόνες:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addImage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_εικόνας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeImage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή_εικόνας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectDirectoryToExportTheCurrentDomain_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλέξτε_κατάλογο_για_την_εξαγωγή:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectExportDirectory_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλογή_φακέλου_εξαγωγής") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancelExport_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ακύρωση_εξαγωγής") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportDomain_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εξαγωγή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("mpiroExportSelection_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("M-PIRO_Επιλογές_εξαγωγής") },
    //{ "pleaseSelectAnExportDestination_text", " Παρακαλώ επιλέξτε προορισμό:" },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Εξαγωγή_των:_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Αγγλικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ιταλικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ελληνικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("users_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_μοντέλων_χρηστών_τοπικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usersToServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_μοντέλων_χρηστών_στον_εξυπηρετητή_εξατομίκευσης") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("restartGenerationEngineAfterExport_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Επανεκκίνηση_της_μηχανής_παραγωγής_μετά_την_εξαγωγή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSelectAnIPAddressAndPort_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Παρακαλώ_επιλέξτε_διεύθυνση_IP_και_Port:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editPersonalisationServerAddress_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τροποποίηση_διεύθυνσης_εξυπηρετητή_εξατομίκευσης") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANewIPAddressAndPort_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Παρακαλώ_εισάγετε_νέα_διεύθυνση_IP_και_Port:") },


		// the global rightclicks
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("rename_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μετονομασία") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("delete_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickTheHierarchyToYourLeft_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Επιλέξτε_την_ιεραρχία_στην_αριστερή_πλευρά.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickanEntityOrAnEntityTypeNode_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλέξτε_τον_κόμβο_μιας_οντότητας_ή_ενός_τύπου_οντοτήτων.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editInterestImportanceRepetitions_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_σπουδαιότητας,_επαναλήψεων_...") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanOneMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_μικροσχεδίου_\"1\"") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanTwoMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_μικροσχεδίου_\"2\"") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanThreeMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_μικροσχεδίου_\"3\"") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanFourMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_μικροσχεδίου_\"4\"") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanFiveMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_μικροσχεδίου_\"5\"") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αγγλικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ιταλικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ελληνικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("appropriateness_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Καταλληλότητα") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("engl_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αγγλικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ital_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ιταλικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gre_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ελληνικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameNode_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μετονομασία_κόμβου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newName_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Νέα_ονομασία:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("errorWhileGeneratingPreview-ETC_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρουσιάστηκε_λάθος_κατά_την_προεπισκόπηση._(Μήπως_κάνατε_αλλαγές_και_δεν_επιλέξατε_\"Φάκελος/Εξαγωγή/Exprimo\"_πριν_την_προεπισκόπηση;)") },


    // the user-types tab
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("maximumFactsPerSentence_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Μήκος_προτάσεων(πεδία_ανά_πρόταση):") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("factsPerPage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Μήκος_παραγράφων(πεδία_ανά_παράγραφο):") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("linksPerPage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Σύνδεσμοι_ανά_παράγραφο:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("synthesizerVoice_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Φωνή_συνθέτη:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewUser_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_νέου_τύπου_χρήστη") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addUser_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_τύπου_χρήστη") },


		// the database tab
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fields_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πεδία") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fillerTypes_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύπος_τιμής") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("many_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πολλά") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microplanning_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μικρο-σχεδιασμός") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fillers_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τιμές") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("textPreviewArea_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Περιοχή_προεπισκόπησης_κειμένου") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editNouns_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αλλαγή...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nounsThatCanBeUsedToDescribe_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ουσιαστικά_για_την_περιγραφή_του_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("meansInherited_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(*_σημαίνει_κληρονομημένο)") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("languageIndependentFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γλωσσικά-ανεξάρτητα_πεδία_του_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αγγλικά_πεδία_του_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ιταλικά_πεδία_του_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ελληνικά_πεδία_του_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewBasicType_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_νέου_βασικού_τύπου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addSubtype_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_υπο-τύπου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addEntity_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_οντότητας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addEntitiesFromDB_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_οντοτήτων_από_μια_βάση_δεδομένων") }, //kallonis
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addGenericEntity_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_γενικής_οντότητας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("upperModelTypes_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι_του_Upper_Model") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewEnglish_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προεπισκόπηση_Αγγλικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewItalian_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προεπισκόπηση_Ιταλικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewGreek_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προεπισκόπηση_Ελληνικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertNewFieldBefore_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εισαγωγή_νέου_πεδίου_πριν_από_το_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertNewFieldAfter_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εισαγωγή_νέου_πεδίου_μετά_από_το_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameField_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μετονομασία_πεδίου_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeField_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή_πεδίου_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewFieldEndOfTable_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εισαγωγή_νέου_πεδίου_(τέλος_πίνακα)") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addBasicType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_βασικού_τύπου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameOfTheBasicType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ονομασία_βασικού_τύπου:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("linksToUpperModelTypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Σύνδεσμοι_με_τύπους_του_Upper_Model") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("upperModelTypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι_του_Upper_Model") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addUpperModelType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_τύπου_upper_model") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameOfTheUpperModelType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ονομασία_τύπου_upper_model:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("customizeAboveList_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσαρμογή_λίστας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addAnItemInTheList_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_νέου_στοιχείου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeSelectedItem_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή_επιλεγμένου_στοιχείου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("otherLexiconNouns_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Λοιπά_ουσιαστικά") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inheritedNouns_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Κληρονομημένα_(*)_ουσιαστικά") },


		// the microplanning panels
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αγγλική_μορφή_του") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ιταλική_μορφή_του") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ελληνική_μορφή_του") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microplan_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("μικροσχεδίου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forField_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("για_το_πεδίο_") },
		// { "englishMicroplanningExpressionForField_text", "   Αγγλικός μικρο-σχεδιασμός για πεδίο: " },
		// { "italianMicroplanningExpressionForField_text", "   Ιταλικός μικρο-σχεδιασμός για πεδίο: " },
		// { "greekMicroplanningExpressionForField_text", "   Ελληνικός μικρο-σχεδιασμός για πεδίο: " },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clausePlan_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Σχέδιο_πρότασης") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("template_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Φόρμα") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("doNotUseForThisLanguage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Δε_χρησιμοποιείται_για_τη_γλώσσα_αυτή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("showAdvancedOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προχωρημένες_επιλογές") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ρήμα") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("voice_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Φωνή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("active_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ενεργητική") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παθητική") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("mood_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Έγκλιση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("indicative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Οριστική") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("imperative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προστακτική") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("subjunctive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Υποτακτική") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nonfinite_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Απαρέμφατο/Μετοχή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tense_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Χρόνος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("past_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρελθόν") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("present_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρόν") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("future_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μέλλον") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("reversible_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Αντιστροφή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("subjectObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_υποκειμένου/αντικειμένου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("true_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ναι") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("false_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Όχι") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preposition_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Πρόθεση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("beforeObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_πριν_το_αντικείμενο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Αναφορική_έκφραση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forSubject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_υποκειμένου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_αντικειμένου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("caseOfReferring_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Πτώση_αναφορικής") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("expressionForSubject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_έκφρασης_υποκειμένου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("expressionForObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_έκφρασης_αντικειμένου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preAdjunct_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Κείμενο_στην_αρχή_της_πρότασης") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("postAdjunct_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Κείμενο_στο_τέλος_της_πρότασης") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("adverb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Επίρρημα") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aggregationAllowed_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ομαδοποίηση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromEnglish_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πάρε_τις_τιμές_των_Αγγλικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromItalian_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πάρε_τις_τιμές_των_Ιταλικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromGreek_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πάρε_τις_τιμές_των_Ελληνικών") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aspect_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ποιόν_ενεργείας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("simple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Στιγμιαίο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("progressive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εξακολουθητικό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("perfect_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Συντελεσμένο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("advancedOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προχωρημένες_επιλογές") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("chooseAVerbIdentifier_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επιλέξτε_ένα_ρήμα") },


    // the template panels
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("string_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Συμβολοσειρά") },
    //{ "referringExpression_text", "Αναφορική έκφραση" },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringOwnerExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αναφορά_στον_ιδιοκτήτη_του_πεδίου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringFillerExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αναφορά_στην_τιμή_του_πεδίου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("semantics_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Σημασιολογία") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("type_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύπος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("case_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πτώση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Θέση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertSlot_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εισαγωγή_θέσης") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("beforeSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("πριν_την_επιλεγμένη") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("afterSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("μετά_την_επιλεγμένη") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("επιλεγμένης_θέσης") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fieldOwner_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Οντότητα-ιδιοκτήτης_πεδίου") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fieldFiller_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τιμή_του_πεδίου") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("auto_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αυτόματο") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("name_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Όνομα") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pronoun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αντωνυμία") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("typeWithDefiniteArticle_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύπος_με_οριστικό_άρθρο") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("typeWithIndefiniteArticle_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύπος_με_αόριστο_άρθρο") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ονομαστική") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("genitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γενική") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("accusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αιτιατική") },


    // the lexicon tab
    //
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewNoun_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_νέου_ουσιαστικού") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewVerb_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_νέου_ρήματος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNoun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_ουσιαστικού") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addVerb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσθήκη_ρήματος") },


    // the noun panels
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("languageIndependentInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γλωσσικά_ανεξάρτητες_πληροφορίες") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("appropriateness_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Καταλληλότητα:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πληροφορίες_για_τα_Αγγλικά_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πληροφορίες_για_τα_Ιταλικά_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πληροφορίες_για_τα_Ελληνικά_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("baseForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Κύριος_τύπος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominativeSingularForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Ονομαστική_ενικού") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominativePluralForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Ονομαστική_πληθυντικού") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("grammatical_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Γραμματικό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gender_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__γένος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("masculine_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αρσενικό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("feminine_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Θηλυκό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("neuter_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ουδέτερο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("countable_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Αριθμητό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("yes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ναι") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Όχι") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inflection_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Κλίση") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inflected_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Κλιτό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("notInflected_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Άκλιτο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("advancedSpellingOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προχωρημένες_επιλογές_ορθογραφίας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkTheBoxToModify_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Για_να_τροποποιήσετε_τον_προτεινόμενο_τύπο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theDefaultSuggestion_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("πρέπει_να_επιλέξετε_το_κουτί_δίπλα_του.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("spelling_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Ορθογραφία") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("plural_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πληθυντικός_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularNominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ονομαστική_ενικού_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularGenitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γενική_ενικού_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularAccusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αιτιατική_ενικού_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralNominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ονομαστική_πληθυντικού_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralGenitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γενική_πληθυντικού_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralAccusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αιτιατική_πληθυντικού_:__") },


    // the verb panels
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("updateFields_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Ενημέρωση_πεδίων_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Μεταβατικό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("2ndPersonOfBaseForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__2ο_πρόσωπο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("3rdPersonSingular_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("3ο_ενικό_πρόσωπο_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("simplePast_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αόριστος_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("presentParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μετοχή_ενεστώτα_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastParticiple2_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μετοχή_αορίστου_:__") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("spellingOfVerbForms_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("___Ορθογραφία_ρηματικών_τύπων") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkToChange1_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("	Σημειώστε_εδώ_για_να_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkToChange2_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("αλλάξετε_τον_αντίστοιχο_τύπο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("___Μετοχή_αορίστου") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activeInfinitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Ενεργητικό_απαρέμφατο_:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passiveInfinitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Παθητικό_απαρέμφατο_:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activeParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Ενεργητική_μετοχή_:") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passiveParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Παθητική_μετοχή") },


    // the verb tables
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tenseAspect_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Χρόνος/Ποιόν_ενεργείας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tense_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Χρόνος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("voice_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Φωνή") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("number_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αριθμός") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("person_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πρόσωπο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verbForm_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ρηματικός_τύπος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("change_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αλλαγή;") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gender_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γένος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("participleForm_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύπος_μετοχής") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("present_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ενεστώτας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastContinuous_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρατατικός") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("remotePast_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αόριστος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("presentProgressive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ενεστώτας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastProgressive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρατατικός") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastSimple_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αόριστος") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singular_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ενικός") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("plural_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πληθυντικός") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("active_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ενεργητική") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παθητική") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("first_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("1ο") },
   	{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("second_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("2ο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("third_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("3ο") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("masculine_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αρσενικό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("feminine_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Θηλυκό") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("neuter_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ουδέτερο") },


    // the stories tab
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editEnglishStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_αγγλικού_κειμένου_ιστορίας...") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editItalianStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_ιταλικού_κειμένου_ιστορίας...") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editGreekStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Επεξεργασία_ελληνικού_κειμένου_ιστορίας...") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewStoryEndOfTable_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εισαγωγή_νέας_ιστορίας_(τέλος_πίνακα)") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameStory_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μετονομασία_ιστορίας_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameStory_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Μετονομασία_ιστορίας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeStory_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή_ιστορίας_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("storyPreviewArea_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Περιοχή_προεπισκόπησης_ιστορίας") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("story_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ιστορία") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("language_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Γλώσσα") },


    // the message dialogs
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aNewDirectoryPicturesHasBeenCreatedInYourUserDir-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ένας_νέος_φάκελος_\"pictures\"_δημιουργήθηκε_μέσα_στον") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("τρέχοντα_φάκελο._Όλες_οι_εικόνες_θα_πρέπει_να_υπάρχουν_εκεί.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionAnErrorHasOccuredWhileAutocompleting-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσοχή!_Προέκυψαν_λάθη_στην_ενημέρωση_των_πεδίων_ορθογραφίας") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("για_το_ρήμα_") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedEntityType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσοχή!_Υπάρχει_ένας_ανώνυμος_τύπος_οντότητας.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedEntity_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσοχή!_Υπάρχει_μια_ανώνυμη_οντότητα.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedNounNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσοχή!_Υπάρχει_ένας_ανώνυμος_κόμβος_ουσιαστικού.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedVerbNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσοχή!_Υπάρχει_ένας_ανώνυμος_κόμβος_ρήματος.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedUserNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσοχή!_Υπάρχει_ένας_ανώνυμος_κόμβος_χρήστη.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cannotExportANewOrUnsavedDomain_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Δε_μπορεί_να_γίνει_εξαγωγή_ενός_νέου_ή_μη_αποθηκευμένου_domain.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAFieldName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Απαγορεύονται_τα_κενά_στα_ονόματα_πεδίων!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Απαγορεύονται_τα_κενά_στα_ονόματα!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAStoryName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Απαγορεύονται_τα_κενά_στα_ονόματα_ιστοριών!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheField_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρακαλώ_δώστε_ένα_όνομα_για_το_πεδίο.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρακαλώ_δώστε_ένα_όνομα_για_τον_κόμβο.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheStory_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρακαλώ_δώστε_ένα_όνομα_για_την_ιστορία.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveADate-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρακαλώ_δώστε_μια_ημερομηνία_(αριθμό_μόνο,_έως_και_12_ψηφία)") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyANameAndAtLeastOneUpperModelType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρακαλώ_ορίστε_ένα_όνομα_και_τουλάχιστον_έναν_τύπο_του_Upper_Model") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyAtLeastOneUpperModelType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρακαλώ_ορίστε_τουλάχιστον_έναν_τύπο_του_Uupper_Model") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyAName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Παρακαλώ_δώστε_ένα_όνομα") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theDateContainsTheFollowingInvalidCharacters_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Η_ημερομηνία_περιέχει_τους_παρακάτω_απαγορευμένους_χαρακτήρες") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theNameContainsTheFollowingInvalidCharacters_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Το_όνομα_περιέχει_τους_παρακάτω_απαγορευμένους_χαρακτήρες") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theNewPersonalisationServerAddressWillBeRecorded-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Η_νέα_διεύθυνση_του_εξυπηρετητή_εξατομίκευσης_θα_καταγραφεί") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("την_επόμενη_φορά_που_το_παρόν_domain_θα_αποθηκευτεί.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereAreNoNounsInTheLexicon_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Δεν_υπάρχουν_ουσιαστικά_στο_Λεξικό!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereIsAnUnnamedStoryField-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Υπάρχει_ένα_ανώνυμο_πεδίο_ιστορίας!") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πρέπει_να_μετονομαστεί_πριν_συνεχίσετε") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereIsAnUnnamedUserField-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Υπάρχει_ένα_ανώνυμο_πεδίο_χρήστη!") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Πρέπει_να_μετονομαστεί_πριν_συνεχίσετε") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisFieldNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αυτό_το_όνομα_πεδίου_υπάρχει_ήδη!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αυτό_το_όνομα_υπάρχει_ήδη!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameStartWithNumber_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Το_όνομα_δεν_μπορεί_να_ξεκινά_με_αριθμό!") },//kallonis
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameStartWithUM_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Το_όνομα_δεν_μπορεί_να_ξεκινά_με_'UM_'!") },//kallonis
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisStoryNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αυτό_το_όνομα_ιστορίας_υπάρχει_ήδη!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisUpperTypeNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αυτό_το_όνομα_Upper_Type_υπάρχει_ήδη!") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transferComplete_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Η_μεταφορά_ολοκληρώθηκε.") },
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transferFailed_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Η_μεταφορά_ΑΠΕΤΥΧΕ") },
  	{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("youAreAboutToDeleteTheEntireDomain_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εάν_προχωρήσετε_θα_διαγραφεί_ολόκληρη_η_συλλογή!")},
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("warning_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Προειδοποίηση")},
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("wouldYouLikeToSaveTheChanges-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Θέλετε_να_αποθηκεύσετε_τις_αλλαγές_που_κάνατε_στην_παρούσα_συλλογή;")},
    { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nothingToExport_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Δεν_έχετε_επιλέξει_τίποτα_για_εξαγωγή!")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εξαγωγή")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToExprimo_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εξαγωγή_στο_Exprimo")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToPEmulator_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εξαγωγή_στον_Personalization_Emulator")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToPServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Εξαγωγή_στον_Personalization_Server")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διαγραφή_προϊστορίας_αλληλεπίδρασης_για_τον_Personalization_Server")},
		//{"exitWarning_dialog", "Θέλετε να αποθηκεύσετε τις αλλαγές σας στη παρούσα συλλογή;" },
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("warning_title"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προσοχή")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancel_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Άκυρο")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("search_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αναζήτηση")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("not_found_in_search_msg"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Το_κείμενο_που_εισαγάγατε_δεν_βρέθηκε!")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_input_msg"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Δεν_εισαγάγατε_κείμενο!")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("user_types_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι_χρηστών")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_user_type_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ΝέοςΤύποςΧρήστη")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("number_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Αριθμός")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("date_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ημερομηνία")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("string_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Συμβολοσειρά")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("dimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Διάσταση")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("domain_dependent_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Λεξικό_συλλογής")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ουσιαστικό")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ρήμα")},
		//{"database_text", "Βάση Δεδομένων"},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("basic_entity_types_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Βασικοί-τύποι-οντοτήτων")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("data_types_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τύποι-δεδομένων")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exhibit_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Έκθεμα")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("historical_period_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Ιστορική_περίοδος")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("place_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τόπος")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("technique_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Τεχνική")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("style_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Στυλ")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("material_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Υλικό")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("person_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Άτομο")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_field_deletion"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Είσαστε_σίγουροι_ότι_θέλετε_να_διαγραφεί_το_πεδίο")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_deletion_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Είσαστε_σίγουροι_ότι_θέλετε_να_διαγράψετε_το_")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_deletion_dialog_2"),java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Είσαστε_σίγουροι_ότι_θέλετε_να_το_διαγράψετε_αυτό;")},
            {java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Addexistingtype_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Addexistingtype_action")},
		
{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preview_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Προεπισκόπηση")},

	};
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
