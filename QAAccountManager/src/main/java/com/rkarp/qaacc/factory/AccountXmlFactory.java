package com.rkarp.qaacc.factory;

import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;
import com.rkarp.qaacc.model.Account;
import com.rkarp.qaacc.settings.AppConfiguration;
import com.rkarp.qaacc.view.AccountEditDialog;
import com.rkarp.qaacc.view.AccountOverview;
import com.rkarp.qaacc.view.BugReportSettings;
import com.rkarp.qaacc.view.MainLayout;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;

public class AccountXmlFactory {

    private static DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

    private static void createAccXML(int projID, String name, String mail, String level, String alliance) throws TransformerConfigurationException, ParserConfigurationException, MalformedURLException {

        File dataDir = Configuration.config.getConfigDirFile();
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        LogFileHandler.logger.log(Level.INFO, "Accounts file doesn't exist. Creating...");
        DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // root element
        Element rootElement = doc.createElement("accountlist");
        doc.appendChild(rootElement);

        // account element
        Element favAcc = doc.createElement("favAcc");
        favAcc.setAttribute(AppConfiguration.projectData.get(projID).getProjectAttribute(), String.valueOf("-1"));
        rootElement.appendChild(favAcc);

        // account element
        Element account = doc.createElement("account");
        account.setAttribute("id", "0");
        rootElement.appendChild(account);

        // accname element
        Element accname = doc.createElement("name");
        accname.appendChild(doc.createTextNode(name));
        account.appendChild(accname);

        // accmail element
        Element accmail = doc.createElement("mail");
        accmail.appendChild(doc.createTextNode(mail));
        account.appendChild(accmail);

        // acclevel element
        Element acclevel = doc.createElement("level");
        acclevel.appendChild(doc.createTextNode(level));
        account.appendChild(acclevel);

        // alliance element
        Element eAlliance = doc.createElement("alliance");
        eAlliance.appendChild(doc.createTextNode(alliance));
        account.appendChild(eAlliance);


        // write content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(dataDir.getPath() + File.separator + AppConfiguration.projectData.get(projID).getProjectXML()));

        try {
            transformer.transform(source, result);
        } catch (TransformerException e1) {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }

        MainLayout.status.setText(LanguageController.getString("accSaved"));
        LogFileHandler.logger.log(Level.INFO, "File created in " + String.valueOf(dataDir.getPath()) + "!");
    }

    public static void addAccount(int projID, String name, String mail, String level, String alliance) throws TransformerException, ParserConfigurationException, IOException, SAXException {

        if (new File(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projID).getProjectXML()).exists()) {
            {
                try {
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                    Document doc = docBuilder.parse(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projID).getProjectXML());

                    NodeList nList = doc.getElementsByTagName("account");

                    Node accounts = doc.getElementsByTagName("accountlist").item(0); // get accountlist node by tag name

                    // append a new node to accountlist
                    Element account = doc.createElement("account");
                    account.setAttribute("id", String.valueOf(nList.getLength()));
                    accounts.appendChild(account);

                    Element accname = doc.createElement("name");
                    accname.appendChild(doc.createTextNode(name));
                    account.appendChild(accname);

                    Element accmail = doc.createElement("mail");
                    accmail.appendChild(doc.createTextNode(mail));
                    account.appendChild(accmail);

                    Element acclevel = doc.createElement("level");
                    acclevel.appendChild(doc.createTextNode(level));
                    account.appendChild(acclevel);

                    Element eAlliance = doc.createElement("alliance");
                    eAlliance.appendChild(doc.createTextNode(alliance));
                    account.appendChild(eAlliance);


                    // write the content into xml file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    //transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projID).getProjectXML());
                    transformer.transform(source, result);

                    LogFileHandler.logger.log(Level.INFO, nList.getLength() + " accounts stored in " + String.valueOf(AppConfiguration.projectData.get(projID).getProjectXML()) + "!");
                    MainLayout.status.setText(LanguageController.getString("accSaved"));

                } catch (ParserConfigurationException | TransformerException | IOException | SAXException e1) {
                    if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                }
            }
        }
        else
        {
            createAccXML(projID, name, mail, level, alliance);
        }
    }

    //loads all accounts of a project
    public static void loadAccData(int pjIdx) {
        try {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(pjIdx).getProjectXML());

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("account");

            LogFileHandler.logger.log(Level.INFO, "READING: " + AppConfiguration.projectData.get(pjIdx).getProjectXML());

            if (nList.getLength() == 0)
            {
                LogFileHandler.logger.log(Level.INFO, "No account found in " + AppConfiguration.projectData.get(pjIdx).getProjectXML());
            }
            else
            {
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        AppConfiguration.accountData.add(new Account(
                                eElement.getElementsByTagName("name").item(0).getTextContent(),
                                eElement.getElementsByTagName("mail").item(0).getTextContent(),
                                eElement.getElementsByTagName("level").item(0).getTextContent(),
                                eElement.getElementsByTagName("alliance").item(0).getTextContent()));
                    }
                }
                LogFileHandler.logger.log(Level.INFO, "Accounts loaded successfully!");
            }
        }
        catch (FileNotFoundException e1)
        {
            LogFileHandler.logger.log(Level.INFO, "Accounts file could not be found!");
        }
        catch (Exception e1)
        {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }

    public static void readAccountNameFromXML(int currentPJ) throws MalformedURLException {

        try {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(currentPJ).getProjectXML());

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("account");

            if (nList.getLength() == 0)
            {
                BugReportSettings.accountCB.setDisable(true);
                BugReportSettings.accountCB.setValue(LanguageController.getString("no_account_found"));
            }
            else
            {
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        AppConfiguration.accountList.addAll(String.valueOf(eElement.getElementsByTagName("name").item(0).getTextContent()));
                    }
                }
                BugReportSettings.accountCB.setItems(AppConfiguration.accountList);
                if (AppConfiguration.projectData.get(currentPJ).getProjectAccBookmarkIdx() >= 0) { BugReportSettings.accountCB.setValue(AppConfiguration.accountList.get(AppConfiguration.projectData.get(currentPJ).getProjectAccBookmarkIdx())); }
            }
        }
        catch (FileNotFoundException e)
        {
            BugReportSettings.accountCB.setDisable(true);
            BugReportSettings.accountCB.setValue(LanguageController.getString("xml_not_found"));
        }
        catch (Exception e1)
        {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }

    //returns the bookmarked account ID of a project
    static int getFavAccID(int projectID) throws TransformerException, ParserConfigurationException, IOException, SAXException {
        if (new File(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projectID).getProjectXML()).exists()) {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projectID).getProjectXML());
            return Integer.parseInt(doc.getElementsByTagName("favAcc").item(0).getAttributes().getNamedItem(AppConfiguration.projectData.get(projectID).getProjectAttribute()).getNodeValue());
        }
        else { return -1; }
    }

    //returns the bookmarked account name of a project
    public static String getFavAccName(int projectID) throws MalformedURLException {
        try {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projectID).getProjectXML());
            doc.getDocumentElement().normalize();

            Node accNode = doc.getElementsByTagName("account").item(AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx()).getFirstChild();
            return accNode.getTextContent();
        }
        catch (ArrayIndexOutOfBoundsException | FileNotFoundException e1)
        {
            //ignore
        }
        catch (NullPointerException e1)
        {
            LogFileHandler.logger.log(Level.WARNING, "account index error!");
            AppConfiguration.projectData.get(projectID).setProjectAccBookmarkIdx(-1);
        }
        catch (Exception e1)
        {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
        return "";
    }

    //removes the account of the given project & index
    public static void removeAccountFromXml(int projectID, int selectedIndex) throws MalformedURLException {
        try {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projectID).getProjectXML());

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("account");
            Element element = (Element) doc.getElementsByTagName("account").item(selectedIndex);

            //compares selected account with saved account and updates the variable accordingly
            if (Integer.parseInt(element.getAttribute("id")) < AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx()) {
                LogFileHandler.logger.config("change_favAcc: " + AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx() + " -> " + (AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx() - 1));
                AppConfiguration.projectData.get(projectID).setProjectAccBookmarkIdx(projectID - 1);
            }
            else if (Integer.parseInt(element.getAttribute("id")) == AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx()) {
                LogFileHandler.logger.config("change_favAcc: " + AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx() + " -> " + -1);
                LogFileHandler.logger.config("change_selectedAcc: " + AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx() + " -> NONE");
                AppConfiguration.projectData.get(projectID).setProjectAccBookmarkIdx(-1);
                AppConfiguration.projectData.get(projectID).setProjectAccBookmarkName("");
            }

            doc.getElementsByTagName("favAcc").item(0).getAttributes().getNamedItem(AppConfiguration.projectData.get(projectID).getProjectAttribute()).setNodeValue(String.valueOf(AppConfiguration.projectData.get(projectID).getProjectAccBookmarkIdx()));

            //removes the element
            element.getParentNode().removeChild(element);
            LogFileHandler.logger.log(Level.INFO, "Account deleted {" + element.getAttribute("id") + ", " + element.getFirstChild().getTextContent() + "}");

            //iterate through nodeList and update the attributes
            for (int e = 0; e < nList.getLength(); e++) {

                // Get the account element by tag name directly
                Node nodes_new = doc.getElementsByTagName("account").item(e);

                // update account attribute
                NamedNodeMap attr = nodes_new.getAttributes();
                Node nodeAttr = attr.getNamedItem("id");
                nodeAttr.setTextContent(String.valueOf(e));
            }

            // write content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projectID).getProjectXML());
            try {
                transformer.transform(source, result);
                AccountOverview.status.setStyle("-fx-text-fill: red;");
                AccountOverview.status.setText(LanguageController.getString("accDeleted"));
            } catch (TransformerException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }

            AccountOverview.nameTF.setText("");
            AccountOverview.mailTF.setText("");
            AccountOverview.lvTF.setText("");
            AccountOverview.alliTF.setText("");
            if (nList.getLength() == 0) { AccountOverview.editAcc.setDisable(true); AccountOverview.delAcc.setDisable(true); }
            if (nList.getLength() == 1) { AccountOverview.accCount.setText("1 " + LanguageController.getString("acc_loaded")); }
            else { AccountOverview.accCount.setText(String.valueOf(nList.getLength()) + " " + LanguageController.getString("accs_loaded")); }
        }
        catch (Exception e1)
        {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }

    public static void saveAccXml(int projID, int i) throws MalformedURLException {
        try {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projID).getProjectXML());
            doc.getDocumentElement().normalize();

            Element eName = (Element) doc.getElementsByTagName("account").item(i).getChildNodes().item(0);
            Element eMail = (Element) doc.getElementsByTagName("account").item(i).getChildNodes().item(1);
            Element eLevel = (Element) doc.getElementsByTagName("account").item(i).getChildNodes().item(2);
            Element eAlliance = (Element) doc.getElementsByTagName("account").item(i).getChildNodes().item(3);
            eName.setTextContent(AccountEditDialog.accNameTF.getText());
            eMail.setTextContent(AccountEditDialog.accMailTF.getText());
            eLevel.setTextContent(AccountEditDialog.accLevelTF.getText());
            eAlliance.setTextContent(AccountEditDialog.accAllianceTF.getText());

            // write content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(Configuration.config.getConfigDirFile() + File.separator + AppConfiguration.projectData.get(projID).getProjectXML());
            try {
                transformer.transform(source, result);
                AccountOverview.status.setStyle("-fx-text-fill: #55c4fe;");
                AccountOverview.status.setText(LanguageController.getString("accSaved"));
                LogFileHandler.logger.log(
                        Level.INFO,
                        "{" + i + ","
                        + eName.getTextContent() + ","
                        + eMail.getTextContent() + ","
                        + eLevel.getTextContent() + ","
                        + eAlliance.getTextContent()
                        + "} - success!"
                );
            } catch (TransformerException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        } catch (Exception e1)
        {
            AccountOverview.accCount.setText("ERROR");
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }
}