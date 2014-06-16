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
* <p>Title: LangResources_it</p>
* <p>Description: The italian resource bundle</p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: NCSR "Demokritos"</p>
* @author Dimitris Spiliotopoulos
* @version 1.0
*/
//Edited and enhanced by Maria Prospathopoulou and Theofilos Nickolaou
public class LangResources_it extends ListResourceBundle
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
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("NUOVO") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("open_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("APRI") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("save_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("SALVA") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ESPORTA") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exit_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ESCI") },

		//
		// other buttons
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ok_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("OK") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("yes_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("SI") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("NO") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancel_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("CANCELLA") },

		//
		// the tabs
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usertypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("TIPI_DI_UTENTE") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("database_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("DATABASE") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("lexicon_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("LESSICO") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("documentplanning_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("DOCUMENTO_DI_PROGETTAZIONE") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("stories_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("STORIE") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("macronodes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("MACRONODI") },

		//
		// the legends
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("legend_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Legenda") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("user_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Utente") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(user)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("basic_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Base") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("entity_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Entit�") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(entity)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Tipi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("entities_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Entit�") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("generic_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Generico") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("data_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Dati") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(data)types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("types_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nouns_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nomi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verbs_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Verbi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("language_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Linguaggio") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("specific_legendtext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Specifico") },

		//
		// the menu items
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("file_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_File") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("options_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Opzioni") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("lookandfeel_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Guarda_e_senti") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editPServerAddress_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Modifica_l'indirizzo_del_Server_Personale") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activatePreviewLanguage_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Attiva_anteprima_lingua") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistory_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Risetta_interazioni_recenti") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryEmulator_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Risetta_interazioni_recenti_per_SIMULATORE") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryBeforeEachPreview_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Resetta_interazioni_recenti_prima_di_ogni_anteprima") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("showAllPagesInPreviews_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Mostra_tutte_le_pagine_nell'anteprima") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("automaticExport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Esporta_automaticamente") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToDefault_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_al_file_di_default") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToChoice_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_ad_altro_file") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("notAutomaticExport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Disattiva_l'esportazione_automatica") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Inglese") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Italiano") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Greco") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("shutDownAuthoringTool_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Chiudi_lo_strumento_di_Authoring") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("help_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Aiuto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("about_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Pi�_informazione") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Nuovo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("open_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Apri") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("saveas_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Salva_come...") },
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("owlexport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Esporta_OWL") }, //kallonis
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("owlimport_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Enporta_OWL") }, //kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Esporta...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exit_menu"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Esci") },

		//
		// the global (Database & Stories tabs ONLY) user-modelling dialog
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("userModelling_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Caratteristiche_utente") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usertypesSmall_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi-utente") },
		//{ "interest_text", "interesse" },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("importance_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("importanza") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("repetitions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ripetizioni") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editing_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Modifica_") },

		//
		// the global dialogs (DDialog 1-13, DirectoryChooser 14-17, ExportDialog 18-19,
		//                     IPChooser 20, OptionsEditDialog 21-22)
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectNewDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_nuova_Data") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickToEnterTwoDatesFromTo_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Clicca_per_inserire_2_date_(da/a)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectToClearEntry_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_per_cancellare_(i_dati_inseriti)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("currentDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Data_Corrente:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newDate_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nuova_Data:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectNewDimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_nuova_Dimensione") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newDimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nuova_Dimensione:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectMultipleEntities_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_entit�_multiple") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("multipleEntities_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Scelte_multiple") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectImages_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_immagini") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newImages_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nuove_immagini:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addImage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_immagine") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeImage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rimuovi_immagine") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectDirectoryToExportTheCurrentDomain_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_directory_per_esportare_il_corrente_dominio:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selectExportDirectory_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_export_directory") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancelExport_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Cancella_esporta") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportDomain_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Esporta_il_dominio") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("mpiroExportSelection_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("M-PIRO_Selezione_Export") },
		//{ "pleaseSelectAnExportDestination_text", " Seleziona una destinazione di esportazione:" },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Esporta_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Inglese")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Italiano")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Greco")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("users_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Tipi_di_utente")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("usersToServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi_di_utente_al_server")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("restartGenerationEngineAfterExport_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Riavvia_il_motore_di_generazione_dopo_aver_esportato")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSelectAnIPAddressAndPort_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Seleziona_un_indirizzo_IP_e_una_Porta:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editPersonalisationServerAddress_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Modifica_un_indirizzo_server_personalizzato") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANewIPAddressAndPort_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Dai_un_nuovo_indirizzo_IP_e_una_Porta:") },


		//
		// the global rightclicks and texts
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("rename_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rinomina") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("delete_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Elimina") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickTheHierarchyToYourLeft_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Clicca_la_gerarchia_alla_tua_sinistra") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clickanEntityOrAnEntityTypeNode_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Clicca_un'entit�_o_un'entit�-_tipo_nodo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editInterestImportanceRepetitions_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Modifica_importanza,_ripetizioni...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanOneMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Meodifica_microprogetto_\"1\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanTwoMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Meodifica_microprogetto_\"2\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanThreeMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Meodifica_microprogetto_\"3\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanFourMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Meodifica_microprogetto_\"4\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editMicroplanFiveMenu_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Meodifica_microprogetto_\"5\"") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("english_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Inglese") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italian_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Italiano") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greek_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Greco") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("appropriateness_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Appropriatezza") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("engl_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("___Inglese") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("ital_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Italiano") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gre_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Greco") },
		//{ "editEnglishMicroplanning_action", "Modifica Micro-progettazione inglese..." },
		//{ "editItalianMicroplanning_action", " Modifica Micro-progettazione italiana..." },
		//{ "editGreekMicroplanning_action", " Modifica Micro-progettazione greca..." },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameNode_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rinomina_il_nodo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newName_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Nuovo_nome:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("errorWhileGeneratingPreview-ETC_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Si_e_verificato_un_errore_durante_la_generazione_dell'anteprima.(Hai_fatto_cambiamenti_senza_selezionare_\"File/Export/Exprimo\"_prima_dell'anteprima?)") },


		//
		// the user-types tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("maximumFactsPerSentence_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Massimo_di_fatti_per_frase:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("factsPerPage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Fatti_per_pagina:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("linksPerPage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Links_per_pagina:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("synthesizerVoice_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Voce_sintetizzata:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewUser_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_nuovo_tipo_di_utente") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addUser_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_tipo_di_utente") },



		//
		// the database tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fields_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Campi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fillerTypes_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi_di_filler") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("many_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Molti") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microplanning_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Micro-pianificazione") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fillers_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Fillers") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("textPreviewArea_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Zona_di_anteprima_di_testo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editNouns_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Modifica_nomi_...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nounsThatCanBeUsedToDescribe_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nomi_che_possono_essere_usati_per_descrivere") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("meansInherited_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("(*_significa_ereditato)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("languageIndependentFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Campi_indipendenti_dalla_lingua") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("campi_Inglesi_di_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("campi_Italiani_di_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekFieldsOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("campi_Greci_di_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewBasicType_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_nuovo_tipo_base") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addSubtype_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_sotto-tipo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addEntity_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_entit�") },
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addEntitiesFromDB_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_entit\u00E0_da_un_database") }, //kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addGenericEntity_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_entit�_generale") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("upperModelTypes_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi_di_modelli_superiori") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewEnglish_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Anteprima_Inglese") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewItalian_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Anteprima_Italiana") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("previewGreek_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Anteprima_Greca") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertNewFieldBefore_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Inserisci_un_nuovo_campo_prima_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertNewFieldAfter_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Inserisci_un_nuovo_campo_dopo_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameField_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rinomina_il_campo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeField_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rimuovi_il_campo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewFieldEndOfTable_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_nuovo_campo_(fine_della_tabella)") },

		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addBasicType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_tipo_base") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameOfTheBasicType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nome_del_tipo_base:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("linksToUpperModelTypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Links_ai_tipi_di_modelli_superiori") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("upperModelTypes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi_di_modelli_superiori") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("newUpperModelType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_tipo_di_modello_superiore") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameOfTheUpperModelType_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nome_del_tipo_di_modello_superiore:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("customizeAboveList_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Personalizza_la_lista_di_sopra") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addAnItemInTheList_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_dato_alla_lista") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeSelectedItem_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rimuovi_il_dato_selezionato") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("otherLexiconNouns_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Altri_nomi_di_lessico") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inheritedNouns_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nomi_(*)_ereditati") },


		// the microplanning panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Versione_inglese_di") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Versione_italiana_di") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekVersionOf_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Versione_greca_di") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microplan_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("microprogetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forField_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("per_campo_") },
		//{ "englishMicroplanningExpressionForField_text", "   Espressione inglese di micro-pianificazione per campo: " },
		//{ "italianMicroplanningExpressionForField_text", "   Espressione italiana di micro-pianificazione per campo: " },
		//{ "greekMicroplanningExpressionForField_text", "   Espressione greca di micro-pianificazione per campo: " },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("clausePlan_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Piano_di_proposizione") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("template_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Template") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("doNotUseForThisLanguage_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_usare_per_questa_lingua") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("showAdvancedOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Mostra_opzioni_avanzate") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Verbo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("voice_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Voce") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("active_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("mood_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Modo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("indicative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Indicativo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("imperative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Imperativo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("subjunctive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Congiuntivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nonfinite_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_finito") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tense_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Tempo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("past_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passato") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("present_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Presente") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("future_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Futuro") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("reversible_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Invertibile") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("subjectObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_soggetto/oggetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("true_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Vero") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("false_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Falso") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preposition_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Preposizione") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("beforeObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_prima_dell'oggetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Espressione_di_riferimento") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forSubject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_per_soggetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("forObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_per_oggetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("caseOfReferring_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Caso_di_riferimento") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("expressionForSubject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_espressione_per_soggetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("expressionForObject_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_espressione_per_oggetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preAdjunct_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Pre-aggiunto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("postAdjunct_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Post-aggiunto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("adverb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Avverbio") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aggregationAllowed_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Aggregazione_permessa") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromEnglish_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Prendi_valori_dall'Inglese") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromItalian_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Prendi_valori_dall'Italiano_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("getValuesFromGreek_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Prendi_valori_dal_Greco_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aspect_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Aspetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("simple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Semplice") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("progressive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Progressivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("perfect_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Perfetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("advancedOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Opzioni_Avanzate") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("chooseAVerbIdentifier_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Scegli_un_identificatore_verbale") },


		// the template panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Slot") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("string_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Stringa") },
		//{ "referringExpression_text", "Espressione di riferimento" },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringOwnerExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Riferimento_al_possessore") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("referringFillerExpression_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Riferimento_al_riempitore_di_campo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("semantics_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Semantica") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("type_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("case_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Caso") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("insertSlot_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Inserisci_slot") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("beforeSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selezionato_prima") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("afterSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("selezionato_dopo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeSelected_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rimuovi_la_selezione") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("slot") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fieldOwner_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Possessore_del_campo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("fieldFiller_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Riempitore_del_campo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("auto_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Auto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("name_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nome") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pronoun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Pronome") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("typeWithDefiniteArticle_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Con_articolo_determinativo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("typeWithIndefiniteArticle_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Con_articolo_indeterminativo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nominativo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("genitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Genitivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("accusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Accusativo") },


		//
		// the lexicon tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewNoun_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_nuovo_nome") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewVerb_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_un_nuovo_verbo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNoun_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_nome") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addVerb_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_verbo") },


		// the noun panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("languageIndependentInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_informazione_indipendente_dal_Linguaggio_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("appropriateness_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Appropriatezza:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("englishSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Informazione_specifica-Inglese") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("italianSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Informazione_specifica-Italiana") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("greekSpecificInformation_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Informazione_specifica-Greca") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("baseForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__forma_base") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominativeSingularForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Forma_nominativo_singolare_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nominativePluralForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Forma_nominativo_plurale_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("grammatical_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Grammaticale") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gender_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__genere") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("masculine_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Maschile") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("feminine_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Femminile") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("neuter_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Neutro") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("countable_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Numerabile") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("yes_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Si") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("No") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inflection_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Flessione") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("inflected_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Flesso") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("notInflected_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_flesso") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("advancedSpellingOptions_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Opzioni_di_ortografia_avanzata") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkTheBoxToModify_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Seleziona_la_casella_da_modificare") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theDefaultSuggestion_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("suggerimento_di_default.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("spelling_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Ortografia") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("plural_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Plurale_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularNominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nominativo_Singolare_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularGenitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Genitivo_Singolare_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singularAccusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Accusativo_Singolare_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralNominative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nominativo_Plurale_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralGenitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Genitivo_Plurale_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pluralAccusative_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Accusativo_Plurale_:__") },

		// the verb panels
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("updateFields_button"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Aggiorna_campi_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__Transitivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("2ndPersonOfBaseForm_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("__seconda_persona_della_forma_base") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("3rdPersonSingular_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("terza_persona_singolare_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("simplePast_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Passato_semplice:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("presentParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Participio_Presente:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastParticiple2_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Participio_Passato_:__") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("spellingOfVerbForms_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("___Ortografia_delle_forme_verbali") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkToChange1_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("	Seleziona_qui_per_cambiare") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("checkToChange2_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("	la_forma_corrispondente") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("___Participio_Passato_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activeInfinitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Infinito_attivo:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passiveInfinitive_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Infinito_passivo:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("activeParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Participio_attivo_:") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passiveParticiple_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("____Participio_passivo") },


		// the verb tables
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tenseAspect_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tempo/Aspetto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("tense_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tempo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("voice_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Voce") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("number_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Numero") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("person_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Persona") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("verbForm_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Forma_verbale") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("change_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Cambia") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("gender_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Genere") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("participleForm_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Forma_di_participio") },


		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("present_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Presente") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastContinuous_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passato_continuo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("remotePast_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passato_remoto") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("presentProgressive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Presente_progressivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastProgressive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passato_progressivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pastSimple_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passato_semplice") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("singular_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Singolare") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("plural_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Plurale") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("active_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("passive_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Passivo") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("first_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("prima") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("second_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("seconda") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("third_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("terza") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("masculine_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Maschile") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("feminine_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Femminile") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("neuter_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Neutro") },

		//
		// the stories tab
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editEnglishStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Modifica_il_testo_della_storia_Inglese...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editItalianStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Modifica_il_testo_della_storia_Italiana...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("editGreekStoryText_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Modifica_il_testo_della_storia_Greca...") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("addNewStoryEndOfTable_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Aggiungi_nuova_storia_(fine_della_tabella)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameStory_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rinomina_storia_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("renameStory_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rinomina_storia") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("removeStory_action"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Rimuovi_storia_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("storyPreviewArea_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Zona_di_anteprima_di_storia") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("story_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Storia") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("language_tabletext"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Linguaggio") },


		//
		// the message dialogs
		//
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("aNewDirectoryPicturesHasBeenCreatedInYourUserDir-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Una_nuova_directory_\"pictures\"_e_stata_creata_nella_tua_directory_") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("user._Vi_saranno_salvate_tutte_le_immagini_esposte.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionAnErrorHasOccuredWhileAutocompleting-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attenzione!_Uno_o_pi�_errori_possono_essere_fatti_tentando_") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("di_aggiornare_i_campi_di_ortografia_per_il_verbo_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedEntityType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attenzione!_Hai_un_tipo_di_entit�_senza_nome.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedEntity_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attenzione!_Hai_un'entit�_senza_nome.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedNounNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attenzione!_Hai_un_sostantivo-nodo_senza_nome.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedVerbNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attenzione!_Hai_un_verbo-nodo_senza_nome.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("attentionYouHaveAnUnnamedUserNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attenzione!_Hai_un_nodo-utente_senza_nome.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cannotExportANewOrUnsavedDomain_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_si_pu�_esportare_un_dominio_nuovo_o_non_salvato") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAFieldName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_sono_permessi_spazi_in_un_campo_del_nome!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Non_sono_permessi_spazi_per_un_nome!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("noSpacesAreAllowedForAStoryName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Non_sono_permessi_spazi_per_il_nome_della_storia!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheField_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Dai_un_nome_al_campo.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheNode_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Dai_un_nome_al_nodo.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveANameForTheStory_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Dai_un_nome_alla_storia.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseGiveADate-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Dai_una_Data_(solo_numeri,_fino_a_12_cifre)") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyANameAndAtLeastOneUpperModelType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Specifica_un_nome_e_almeno_un_tipo_di_modello_superiore") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyAtLeastOneUpperModelType_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Specifica_almeno_un_tipo_di_modello_superiore") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("pleaseSpecifyAName_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Specificare_un_nome") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theDateContainsTheFollowingInvalidCharacters_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("La_Data_contiene_i_seguenti_caratteri_invalidi") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theNameContainsTheFollowingInvalidCharacters_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Il_nome_contiene_i_seguenti_caratteri_invalidi_") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("theNewPersonalisationServerAddressWillBeRecorded-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Il_nuovo_indirizzo_del_server_personale_sar�_registrato") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("la_prossima_volta_che_il_dominio_corrente_verr�_salvato.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereAreNoNounsInTheLexicon_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_ci_sono_nomi_nel_Lessico!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereIsAnUnnamedStoryField-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("C'e'_un_campo_di_storia_senza_nome!") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Bisogna_rinominare_prima_di_procedere") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thereIsAnUnnamedUserField-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("C'e'_un_campo_utente_senza_nome!") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("\n") + java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Bisogna_rinominare_prima_di_procedere") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisFieldNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Questo_campo_di_nome_esiste_gi�!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Questo_nome_esiste_gi�!") },
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameStartWithNumber_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Il_nome_non_deve_iniziare_con_un_numero!") },//kallonis
        { java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nameStartWithUM_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Il_nome_non_deve_iniziare_con_'UM_'!") },//kallonis
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisStoryNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Questo_nome_di_storia_esiste_gi�!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("thisUpperTypeNameAlreadyExists_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Questo_tipo_superiore_di_nome_esiste_gi�!") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transferComplete_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Trasferimento_completo.") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("transferFailed_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Trasferimento_FALLITO") },
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("youAreAboutToDeleteTheEntireDomain_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Stai_per_cancellare_tutto_il_dominio!")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("warning_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Attenzione")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("wouldYouLikeToSaveTheChanges-ETC_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Vuoi_salvare_i_cambiamenti_al_tuo_dominio_corrente")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("nothingToExport_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_hai_scelto_niente_da_esportare!")},

		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("export_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("_Esporta")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToExprimo_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Esporta_a_Exprimo")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToPEmulator_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Esporta_al_Simulatore_di_Personalizzazione")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("exportToPServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Esporta_al_Server_di_Personalizzazione")},
		{ java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("resetInteractionHistoryServer_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Risetta_Interazioni_Recenti_per_Server")},

		//{"exitWarning_dialog", "???Would you like to save the changes to your current domain???" },
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("warning_title"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Attenzione")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("cancel_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Cancella")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("search_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Cerca")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("not_found_in_search_msg"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Il_testo_specificato_non_e_stato_trovato!")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("no_input_msg"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Non_e_stato_inserito_nessun_testo!")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("user_types_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Tipi_di_utente")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("new_user_type_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Nuovo_Tipo_di_Utente")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("number_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Number")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("date_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Date")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("string_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("String")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("dimension_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Dimension")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("domain_dependent_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Lessico_dipendente_dal_dominio")},
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
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_field_deletion"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Sei_sicuro_di_voler_cancellare")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_deletion_dialog"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Sei_sicuro_di_voler_cancellare_")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("confirm_deletion_dialog_2"),java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Sei_sicuro_di_voler_cancellare?")},
		{java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("preview_text"), java.util.ResourceBundle.getBundle("gr/demokritos/iit/mpiro/authoring/LangResources").getString("Anteprima")},

	};
}
