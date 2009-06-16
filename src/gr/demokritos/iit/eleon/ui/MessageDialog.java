//  "M-PIRO AUTHORING TOOL v.4.4"
//  Copyright (c) 2001, 2002, 2003, 2004
//  Software and Knowledge Engineering Laboratory,
//  Institute of Informatics and Telecommunications,
//  National Centre for Scientific Research "Demokritos", Greece.

package gr.demokritos.iit.eleon.ui;

import gr.demokritos.iit.eleon.authoring.LangResources;
import gr.demokritos.iit.eleon.authoring.Mpiro;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.IOException;
import javax.swing.tree.*;
import javax.swing.JTree;

/* */
public class MessageDialog
    extends JFrame {
    public static String aNewDirectoryPicturesHasBeenCreatedInYourUserDir_ETC_dialog = LangResources.getString(Mpiro.selectedLocale,
        "aNewDirectoryPicturesHasBeenCreatedInYourUserDir-ETC_dialog");
    public static String attentionAnErrorHasOccuredWhileAutocompleting_ETC_dialog = LangResources.getString(Mpiro.selectedLocale,
        "attentionAnErrorHasOccuredWhileAutocompleting-ETC_dialog");
    public static String attentionYouHaveAnUnnamedEntityType_dialog = LangResources.getString(Mpiro.selectedLocale,
        "attentionYouHaveAnUnnamedEntityType_dialog");
    public static String attentionYouHaveAnUnnamedEntity_dialog = LangResources.getString(Mpiro.selectedLocale, "attentionYouHaveAnUnnamedEntity_dialog");
    public static String attentionYouHaveAnUnnamedNounNode_dialog = LangResources.getString(Mpiro.selectedLocale, "attentionYouHaveAnUnnamedNounNode_dialog");
    public static String attentionYouHaveAnUnnamedVerbNode_dialog = LangResources.getString(Mpiro.selectedLocale, "attentionYouHaveAnUnnamedVerbNode_dialog");
    public static String attentionYouHaveAnUnnamedUserNode_dialog = LangResources.getString(Mpiro.selectedLocale, "attentionYouHaveAnUnnamedUserNode_dialog");
    public static String cannotExportANewOrUnsavedDomain_dialog = LangResources.getString(Mpiro.selectedLocale, "cannotExportANewOrUnsavedDomain_dialog");
    public static String noSpacesAreAllowedForAFieldName_dialog = LangResources.getString(Mpiro.selectedLocale, "noSpacesAreAllowedForAFieldName_dialog");
    public static String noSpacesAreAllowedForAName_dialog = LangResources.getString(Mpiro.selectedLocale, "noSpacesAreAllowedForAName_dialog");
    public static String noSpacesAreAllowedForAStoryName_dialog = LangResources.getString(Mpiro.selectedLocale, "noSpacesAreAllowedForAStoryName_dialog");
    public static String pleaseGiveANameForTheField_dialog = LangResources.getString(Mpiro.selectedLocale, "pleaseGiveANameForTheField_dialog");
    public static String pleaseGiveANameForTheNode_dialog = LangResources.getString(Mpiro.selectedLocale, "pleaseGiveANameForTheNode_dialog");
    public static String pleaseGiveANameForTheStory_dialog = LangResources.getString(Mpiro.selectedLocale, "pleaseGiveANameForTheStory_dialog");
    public static String pleaseGiveADate_ETC_dialog = LangResources.getString(Mpiro.selectedLocale, "pleaseGiveADate-ETC_dialog");
    public static String pleaseSpecifyANameAndAtLeastOneUpperModelType_dialog = LangResources.getString(Mpiro.selectedLocale,
        "pleaseSpecifyANameAndAtLeastOneUpperModelType_dialog");
    public static String pleaseSpecifyAtLeastOneUpperModelType_dialog = LangResources.getString(Mpiro.selectedLocale,
        "pleaseSpecifyAtLeastOneUpperModelType_dialog");
    public static String pleaseSpecifyAName_dialog = LangResources.getString(Mpiro.selectedLocale, "pleaseSpecifyAName_dialog");
    public static String theDateContainsTheFollowingInvalidCharacters_dialog = LangResources.getString(Mpiro.selectedLocale,
        "theDateContainsTheFollowingInvalidCharacters_dialog");
    public static String theNameContainsTheFollowingInvalidCharacters_dialog = LangResources.getString(Mpiro.selectedLocale,
        "theNameContainsTheFollowingInvalidCharacters_dialog");
    public static String theNewPersonalisationServerAddressWillBeRecorded_ETC_dialog = LangResources.getString(Mpiro.selectedLocale,
        "theNewPersonalisationServerAddressWillBeRecorded-ETC_dialog");
    public static String thereAreNoNounsInTheLexicon_dialog = LangResources.getString(Mpiro.selectedLocale, "thereAreNoNounsInTheLexicon_dialog");
    public static String thereIsAnUnnamedStoryField_ETC_dialog = LangResources.getString(Mpiro.selectedLocale, "thereIsAnUnnamedStoryField-ETC_dialog");
    public static String thereIsAnUnnamedUserField_ETC_dialog = LangResources.getString(Mpiro.selectedLocale, "thereIsAnUnnamedUserField-ETC_dialog");
    public static String thisFieldNameAlreadyExists_dialog = LangResources.getString(Mpiro.selectedLocale, "thisFieldNameAlreadyExists_dialog");
    public static String thisNameAlreadyExists_dialog = LangResources.getString(Mpiro.selectedLocale, "thisNameAlreadyExists_dialog");
    public static String thisStoryNameAlreadyExists_dialog = LangResources.getString(Mpiro.selectedLocale, "thisStoryNameAlreadyExists_dialog");
    public static String thisUpperTypeNameAlreadyExists_dialog = LangResources.getString(Mpiro.selectedLocale, "thisUpperTypeNameAlreadyExists_dialog");
    public static String transferComplete_dialog = LangResources.getString(Mpiro.selectedLocale, "transferComplete_dialog");
    public static String transferFailed_dialog = LangResources.getString(Mpiro.selectedLocale, "transferFailed_dialog");
    public static String nameStartWithNumber_dialog = LangResources.getString(Mpiro.selectedLocale, "nameStartWithNumber_dialog"); //kallonis
    public static String nameStartWithUM_dialog = LangResources.getString(Mpiro.selectedLocale, "nameStartWithUM_dialog"); //kallonis

    public MessageDialog(Component parentComponent, String message) {
        super.setIconImage(Mpiro.obj.image_corner);
        JOptionPane.showMessageDialog(parentComponent,
                                      message,
                                      "M-PIRO INFORMATION SERVICE",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
}
