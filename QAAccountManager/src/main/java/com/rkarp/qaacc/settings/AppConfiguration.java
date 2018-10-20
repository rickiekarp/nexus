package com.rkarp.qaacc.settings;

import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.core.settings.LoadSave;
import net.rickiekarp.core.util.CommonUtil;
import com.rkarp.qaacc.model.Account;
import com.rkarp.qaacc.model.Projects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class AppConfiguration {

    /** settings variables **/
    @LoadSave
    public static int pjCfgSelection;
    @LoadSave
    public static String acronym;
    @LoadSave
    public static String browser;
    @LoadSave
    public static int srvSel = 0;
    @LoadSave
    public static int locaSel = 0;

    /** account data **/
    public static ObservableList<Account> accountData = FXCollections.observableArrayList();

    /** project data **/
    public static String[] projects = { "A", "B", "C", "D" };
    public static ObservableList<Projects> projectData = FXCollections.observableArrayList();
    public static int pjState;

    /** all account name scheme details are listed here **/
    @LoadSave
    public static String dept;
    @LoadSave
    public static String mail_pref;
    @LoadSave
    public static String mail_end;
    @LoadSave
    public static String pass;

    /** bug template settings **/
    public static String found_in_version = "";
    public static ObservableList<String> server = FXCollections.observableArrayList("QA-Test", "DEV-Test");
    public static ObservableList<String> locale = FXCollections.observableArrayList("English", "Deutsch");
    public static ObservableList<String> loca = FXCollections.observableArrayList("English", "Deutsch");
    //TODO: Remove account list and use accountData instead
    public static ObservableList<String> accountList = FXCollections.observableArrayList();

    /** bug template **/
    public static String[] bugtemplate = {
            "Browser & -Version: ",
            "Found in Version: ",
            "Occured on: ",
            "Occured at: ",
            "Account: ",
            "Language: ",
            "Reproducibility: ",
            "Repro:",
            "- ",
            "Currently:",
            "Expected:",
            " or any" };

    /** sets string to clipboard **/
    public static void setStringToClipboard(String stringContent) {
        StringSelection stringSelection = new StringSelection(stringContent);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public static void readProjectData() {
        NodeList projList = Configuration.config.getSettingsXmlFactory().getNodeList("project");

        if (projList.getLength() == 0) {
            for (int i = 0; i < projects.length; i++) {
//                proj[i] = doc.createElement("project");
//                proj[i].appendChild(doc.createTextNode(projects[i]));
//                projects.appendChild(proj[i]);
                projectData.add(new Projects(i, projects[i], "accounts_" + projects[i].toLowerCase() + ".xml", projects[i].toLowerCase() + "Idx", -1, ""));
            }
        }
        String projects[] = new String[projList.getLength()];

        for (int i = 0; i < projList.getLength(); i++) {
            Node nNode = projList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                projects[i] = nNode.getTextContent();
                AppConfiguration.projectData.add(new Projects(i, projects[i], "accounts_" + projects[i].toLowerCase() + ".xml", projects[i].toLowerCase() + "Idx", -1, ""));
            }
        }
    }

    /**
     *  returns bug template
     *  only called in menu bar
     **/
    public static String onCopyBugtemplate(int projectInt) {

        String accName;
        String orAny;
        if (projectData.get(projectInt).getProjectAccBookmarkName().equals("")) { accName = ""; orAny = ""; }
        else { accName = projectData.get(projectInt).getProjectAccBookmarkName(); orAny = bugtemplate[11]; }

        return
                bugtemplate[0] + browser + "\n" +
                bugtemplate[1] + found_in_version + "\n" +
                bugtemplate[2] + server.get(srvSel) + "\n" +
                bugtemplate[3] + CommonUtil.getTime("HH:mm") + "\n" +
                bugtemplate[4] + accName + orAny + "\n" +
                bugtemplate[5] + loca.get(locaSel) + bugtemplate[11] + "\n" +
                bugtemplate[6] + "\n\n" +
                bugtemplate[7] + "\n" +
                bugtemplate[8] + "\n\n" +
                bugtemplate[9] + "\n" +
                bugtemplate[8] + "\n\n" +
                bugtemplate[10] + "\n" +
                bugtemplate[8];
    }
}