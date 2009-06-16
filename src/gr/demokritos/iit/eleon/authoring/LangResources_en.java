//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.authoring;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


/**
* <p>Title: LangResources_en</p>
* <p>Description: The english resource bundle</p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: NCSR "Demokritos"</p>
* @author Dimitris Spiliotopoulos
* @version 1.0
*/
//Edited and enhanced by Maria Prospathopoulou and Theofilos Nickolaou
public class LangResources_en extends ListResourceBundle
{
	public Object[][] getContents()
	{
		return contents;
	}

        
	private Object[][] contents =
	{
		//
		// the main buttons
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("NEW") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("open_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("OPEN") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("save_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("SAVE") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("EXPORT") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exit_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("EXIT") },

		//
		// other buttons
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ok_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("OK") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("yes_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("YES") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("NO") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancel_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("CANCEL") },

		//
		// the tabs
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usertypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("USER_TYPES") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("database_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("DATABASE") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("lexicon_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("LEXICON") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("documentplanning_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("DOCUMENT_PLANNING") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("stories_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("STORIES") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("macronodes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("MACRONODES") },

		//
		// the legends
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("legend_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Legend") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("user_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("User") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(user)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("basic_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Basic") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("entity_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Entity") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(entity)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("entities_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Entities") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("generic_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Generic") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("data_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Data") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(data)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nouns_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nouns") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verbs_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Verbs") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("language_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Language") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("specific_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Specific") },

		//
		// the menus and menu items
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("file_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_File") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("options_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Options") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("lookandfeel_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Look_&_Feel") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editPServerAddress_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Edit_Personalisation_Server_address") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activatePreviewLanguage_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Activate_preview_language") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistory_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Reset_interaction_history") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryEmulator_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Reset_interaction_history_for_EMULATOR") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryBeforeEachPreview_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Reset_interaction_history_before_each_preview") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("showAllPagesInPreviews_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Show_all_pages_in_previews") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("automaticExport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Automatic_export") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToDefault_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_...to_default_folder") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToChoice_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_...to_other_folder") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("notAutomaticExport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Deactivate_automatic_export") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_English") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Italian") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Greek") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("shutDownAuthoringTool_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Shut_down_Authoring_tool") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("help_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Help") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("about_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_About") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_New") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("open_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Open") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("saveas_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Save_as...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("owlexport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Export_to_OWL") },  //kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("owlimport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Import_from_OWL") },  //kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Export...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exit_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Exit") },

		//
		// the global (Database & Stories tabs ONLY) user-modelling dialog
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("userModelling_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("User_modelling") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usertypesSmall_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("User-types") },
		//{ "interest_text", "interest" },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("importance_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("importance") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("repetitions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("repetitions") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editing_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Editing_") },

		//
		// the global dialogs (DDialog 1-13, DirectoryChooser 14-17, ExportDialog 18-19,
		//                     IPChooser 20, OptionsEditDialog 21-22)
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectNewDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Select_new_Date") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickToEnterTwoDatesFromTo_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Click_to_enter_2_dates_(from/to)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectToClearEntry_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Select_to_clear_entry") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("currentDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Current_Date:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("New_Date:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectNewDimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Select_new_Dimension") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newDimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("New_Dimension:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectMultipleEntities_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Select_multiple_entities") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("multipleEntities_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Multile_items") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectImages_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Select_images") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newImages_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("New_images:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addImage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_image") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeImage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Remove_image") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectDirectoryToExportTheCurrentDomain_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Select_directory_to_export_the_current_domain:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectExportDirectory_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Select_export_directory") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancelExport_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Cancel_export") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportDomain_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Export_domain") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("mpiroExportSelection_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("M-PIRO_Export_Selection") },
		//{ "pleaseSelectAnExportDestination_text", " Please select an export destination:" },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Export_of:_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_English")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Italian")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Greek")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("users_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_user_models_local")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usersToServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_user_models_to_user_modelling_server")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("restartGenerationEngineAfterExport_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Restart_generetion_engine_after_export")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSelectAnIPAddressAndPort_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Please_select_an_IP_address_and_Port:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editPersonalisationServerAddress_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_personalisation_server_address") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANewIPAddressAndPort_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Please_give_a_new_IP_address_and_Port:") },


		//
		// the global rightclicks and texts
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("rename_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rename") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("delete_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Delete") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickTheHierarchyToYourLeft_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Click_the_hierarchy_to_your_left") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickanEntityOrAnEntityTypeNode_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Click_an_entity_or_an_entity-type_node") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editInterestImportanceRepetitions_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_importance,_repetitions_...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanOneMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_microplan_\"1\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanTwoMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_microplan_\"2\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanThreeMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_microplan_\"3\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanFourMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_microplan_\"4\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanFiveMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_microplan_\"5\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("English") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Italian") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Greek") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("appropriateness_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Appropriateness") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("engl_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("______English") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ital_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Italian") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gre_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Greek") },
		//{ "editEnglishMicroplanning_action", "Edit English Micro-planning..." },
		//{ "editItalianMicroplanning_action", "Edit Italian Micro-planning..." },
		//{ "editGreekMicroplanning_action", "Edit Greek Micro-planning..." },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameNode_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rename_node") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newName_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_New_name:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("errorWhileGeneratingPreview-ETC_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Error_while_generating_preview._(Did_you_make_changes_without_selecting_\"File/Export/Exprimo\"_before_the_preview?)") },


		//
		// the user-types tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("maximumFactsPerSentence_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Maximum_facts_per_sentence:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("factsPerPage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Facts_per_page:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("linksPerPage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Links_per_page:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("synthesizerVoice_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Synthesizer_voice:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewUser_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_new_user_type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addUser_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_user_type") },



		//
		// the database tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fields_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Fields") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fillerTypes_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Filler-types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("many_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Many") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microplanning_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Micro-planning") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fillers_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Fillers") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("textPreviewArea_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Text_preview_area") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editNouns_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_nouns_...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nounsThatCanBeUsedToDescribe_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nouns_that_can_be_used_to_describe_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("meansInherited_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(*_means_inherited)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("languageIndependentFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Language-independent_fields_of_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("English_fields_of_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Italian_fields_of_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_fields_of_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewBasicType_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_new_basic_type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addSubtype_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_sub-type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addEntity_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_entity") },
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addEntitiesFromDB_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_entities_from_a_database") }, //kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addGenericEntity_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_generic_entity") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("upperModelTypes_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Upper_model_types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewEnglish_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Preview_English") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewItalian_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Preview_Italian") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewGreek_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Preview_Greek") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertNewFieldBefore_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Insert_new_field_before_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertNewFieldAfter_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Insert_new_field_after_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameField_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rename_field_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeField_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Remove_field_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewFieldEndOfTable_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_new_field_(end_of_table)") },

		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addBasicType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_basic_type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameOfTheBasicType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Name_of_the_basic_type:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("linksToUpperModelTypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Links_to_upper_model_types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("upperModelTypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Upper_model_types") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addUpperModelType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_upper_model_type") }, //kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newUpperModelType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_upper_model_type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameOfTheUpperModelType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Name_of_upper_model_type:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("customizeAboveList_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Customize_above_list") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addAnItemInTheList_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_an_item_in_the_list") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeSelectedItem_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Remove_selected_item") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("otherLexiconNouns_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Other_lexicon_nouns") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inheritedNouns_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Inherited_(*)_nouns") },


		// the microplanning panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("English_version_of") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Italian_version_of") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Greek_version_of") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microplan_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microplan") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forField_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("for_field_") },
		//{ "englishMicroplanningExpressionForField_text", "   English micro-planning expression for field: " },
		//{ "italianMicroplanningExpressionForField_text", "   Italian micro-planning expression for field: " },
		//{ "greekMicroplanningExpressionForField_text", "   Greek micro-planning expression for field: " },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clausePlan_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Clause_plan") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("template_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Template") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("doNotUseForThisLanguage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Do_not_use_for_this_language") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("showAdvancedOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Show_advanced_options") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Verb") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("voice_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Voice") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("active_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Active") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("mood_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Mood") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("indicative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Indicative") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("imperative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Imperative") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("subjunctive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Subjunctive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nonfinite_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nonfinite") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tense_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Tense") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("past_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Past") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("present_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Present") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("future_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Future") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("reversible_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Reversible") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("subjectObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_subject/object") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("true_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("True") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("false_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("False") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preposition_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Preposition") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("beforeObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_before_object") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Referring_expression") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forSubject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_for_subject") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_for_object") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("caseOfReferring_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Case_of_referring") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("expressionForSubject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_expression_for_subject") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("expressionForObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_expression_for_object") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preAdjunct_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Pre-adjunct") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("postAdjunct_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Post-adjunct") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("adverb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Adverb") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aggregationAllowed_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Aggregation_allowed") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromEnglish_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Get_values_from_English") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromItalian_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Get_values_from_Italian") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromGreek_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Get_values_from_Greek") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aspect_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Aspect") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("simple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Simple") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("progressive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Progressive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("perfect_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Perfect") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("advancedOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Advanced_Options") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("chooseAVerbIdentifier_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Choose_a_verb_identifier") },


		// the template panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Slot") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("string_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("String") },
		//{ "referringExpression_text", "Referring expression" },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringOwnerExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Referring_to_owner_expression") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringFillerExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Referring_to_field_filler_expression") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("semantics_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Semantics") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("type_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("case_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Case") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertSlot_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Insert_slot") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("beforeSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("before_selected") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("afterSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("after_selected") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Remove_selected") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fieldOwner_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Field_owner") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fieldFiller_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Field_filler") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("auto_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Auto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("name_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Name") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pronoun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Pronoun") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("typeWithDefiniteArticle_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Type_with_definite_article") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("typeWithIndefiniteArticle_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Type_with_indefinite_article") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nominative") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("genitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Genitive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("accusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Accusative") },


		//
		// the lexicon tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewNoun_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_new_noun") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewVerb_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_new_verb") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNoun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_noun") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addVerb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_verb") },


		// the noun panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("languageIndependentInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Language-independent_information") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("appropriateness_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Appropriateness:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("English-specific_information") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Italian-specific_information") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Greek-specific_information") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("baseForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Base_form") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominativeSingularForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Nominative_singular_form") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominativePluralForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Nominative_plural_form") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("grammatical_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Grammatical") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gender_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__gender") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("masculine_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Masculine") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("feminine_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Feminine") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("neuter_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Neuter") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("countable_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Countable") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("yes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Yes") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("No") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inflection_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Inflection") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inflected_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Inflected") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("notInflected_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Not_inflected") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("advancedSpellingOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Advanced_spelling_options") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkTheBoxToModify_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Check_the_box_to_modify") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theDefaultSuggestion_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("the_default_suggestion.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("spelling_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Spelling") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("plural_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Plural_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularNominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Singular_Nominative_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularGenitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Singular_Genitive_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularAccusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Singular_Accusative_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralNominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Plural_Nominative_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralGenitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Plural_Genitive_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralAccusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Plural_Accusative_:__") },

		// the verb panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("updateFields_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Update_fields_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Transitive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("2ndPersonOfBaseForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__2nd_person_of_base_form") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("3rdPersonSingular_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("3rd_person_singular_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("simplePast_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Simple_past_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("presentParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Present_participle_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastParticiple2_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Past_participle_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("spellingOfVerbForms_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("___Spelling_of_verb_forms") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkToChange1_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("	Check_here_to_change_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkToChange2_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("	the_corresponding_form") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("___Past_participle") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activeInfinitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Active_Infinitive_:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passiveInfinitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Passive_Infinitive_:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activeParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Active_participle_:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passiveParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Passive_participle") },


		// the verb tables
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tenseAspect_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tense/Aspect") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tense_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tense") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("voice_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Voice") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("number_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Number") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("person_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Person") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verbForm_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Verb_form") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("change_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Change?") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gender_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Gender") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("participleForm_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Participle_form") },


		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("present_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Present") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastContinuous_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Past_continuous") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("remotePast_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Remote_past") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("presentProgressive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Present_progressive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastProgressive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Past_progressive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastSimple_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Past_simple") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singular_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Singular") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("plural_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Plural") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("active_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Active") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passive") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("first_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("1st") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("second_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("2nd") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("third_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("3rd") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("masculine_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Masculine") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("feminine_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Feminine") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("neuter_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Neuter") },

		//
		// the stories tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editEnglishStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_English_story_text...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editItalianStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_Italian_story_text...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editGreekStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Edit_Greek_story_text...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewStoryEndOfTable_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Add_new_story_(end_of_table)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameStory_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rename_story_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameStory_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rename_story") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeStory_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Remove_story_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("storyPreviewArea_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Story_preview_area") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("story_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Story") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("language_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Language") },


		//
		// the message dialogs
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aNewDirectoryPicturesHasBeenCreatedInYourUserDir-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("A_new_directory_\"pictures\"_has_been_created_in_your") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("current_directory._All_exhibit_images_should_be_stored_there.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionAnErrorHasOccuredWhileAutocompleting-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attention!_One_or_more_errors_may_have_occured_trying_to") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("update_the_spelling_fields_for_the_verb_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedEntityType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attention!_You_have_an_unnamed_entity-type.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedEntity_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attention!_You_have_an_unnamed_entity.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedNounNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attention!_You_have_an_unnamed_noun-node.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedVerbNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attention!_You_have_an_unnamed_verb-node.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedUserNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attention!_You_have_an_unnamed_user-node.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cannotExportANewOrUnsavedDomain_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Cannot_export_a_new_or_unsaved_domain") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAFieldName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("No_spaces_are_allowed_for_a_field_name!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("No_spaces_are_allowed_for_a_name!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAStoryName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("No_spaces_are_allowed_for_a_story_name!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheField_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Please_give_a_name_for_the_field.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Please_give_a_name_for_the_node.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheStory_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Please_give_a_name_for_the_story.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveADate-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Please_give_a_Date_(number_only,_up_to_12_digits)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyANameAndAtLeastOneUpperModelType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Please_specify_a_name_and_at_least_one(1)_upper_model_type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyAtLeastOneUpperModelType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Please_specify_at_least_one(1)_upper_model_type") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyAName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Please_specify_a_name") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theDateContainsTheFollowingInvalidCharacters_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("The_Date_contains_the_following_invalid_characters") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theNameContainsTheFollowingInvalidCharacters_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("The_name_contains_the_following_invalid_characters") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theNewPersonalisationServerAddressWillBeRecorded-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("The_new_personalisation_server_address_will_be_recorded") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("the_next_time_the_current_domain_is_saved.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereAreNoNounsInTheLexicon_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("There_are_no_nouns_in_the_Lexicon!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereIsAnUnnamedStoryField-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("There_is_an_unnamed_story_field!") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("It_must_be_renamed_before_proceeding") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereIsAnUnnamedUserField-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("There_is_an_unnamed_user_field!") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("It_must_be_renamed_before_proceeding") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisFieldNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("This_field_name_already_exists!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("This_name_already_exists!") },
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameStartWithNumber_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("The_name_cannot_start_with_a_number!") },//kallonis
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameStartWithUM_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("The_name_cannot_start_with_'UM_'!") },//kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisStoryNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("This_story_name_already_exists!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisUpperTypeNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("This_upper-type-name_already_exists!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transferComplete_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Transfer_complete.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transferFailed_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Transfer_FAILED") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("youAreAboutToDeleteTheEntireDomain_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("You_are_about_to_Delete_the_entire_domain!")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("warning_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Warning")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("wouldYouLikeToSaveTheChanges-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Would_you_like_to_Save_the_changes_to_your_current_domain?")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nothingToExport_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("You_haven't_chosen_anything_to_export!")},

		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Export")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToExprimo_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Export_to_Exprimo")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToPEmulator_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Export_to_Personalization_Emulator")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToPServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Export_to_Personalization_Server")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Reset_Interaction_History_for_Server")},

		//{"exitWarning_dialog", "Would you like to save the changes to your current domain?" },
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("warning_title"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Warning")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancel_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Cancel")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("search_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Search")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("not_found_in_search_msg"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("The_specified_text_was_not_found!")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_input_msg"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("You_did_not_enter_any_text!")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("user_types_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("User_types")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_user_type_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("NewUserType")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("number_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Number")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("date_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Date")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("string_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("String")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("dimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Dimension")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("domain_dependent_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Domain-dependent_lexicon")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Noun")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Verb")},
		//{"database_text", "Data Base"},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("basic_entity_types_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Basic-entity-types")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("data_types_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Data-types")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exhibit_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Exhibit")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("historical_period_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Historical-period")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("place_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Place")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("technique_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Technique")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("style_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Style")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("material_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Material")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("person_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Person")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_field_deletion"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Are_you_sure_you_want_to_delete_the_field")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_deletion_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Are_you_sure_you_want_to_delete_")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_deletion_dialog_2"),java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Are_you_sure_you_want_to_delete_this?")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preview_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Preview")},
            {java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Addexistingtype_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Addexistingtype_action")},




	};
}
