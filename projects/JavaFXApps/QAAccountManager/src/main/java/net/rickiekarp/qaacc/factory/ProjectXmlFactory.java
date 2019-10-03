package net.rickiekarp.qaacc.factory;

import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.qaacc.model.Projects;
import net.rickiekarp.qaacc.settings.AppConfiguration;
import net.rickiekarp.qaacc.view.BugReportSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class ProjectXmlFactory {

    private static DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

    public static void getSettings() throws MalformedURLException, URISyntaxException {

        if (new File(Configuration.config.getConfigDirFile() + File.separator + File.separator + "projects.xml").exists()) {
            readSettingsXML(new File(Configuration.config.getConfigDirFile() + File.separator + "data"), false);
        } else {
            createConfigXML(Configuration.config.getConfigDirFile());
        }
    }

    public static void createConfigXML(File datafile) throws MalformedURLException {
        if (!datafile.exists()) {
            datafile.mkdir();
        }

            try {
                DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                // root element
                Element rootElement = doc.createElement("settings");
                doc.appendChild(rootElement);

                // projects element
                Element projects = doc.createElement("proj");
                projects.setAttribute("selected", "-1");
                rootElement.appendChild(projects);

                // projects element
                Element proj[] = new Element[AppConfiguration.projects.length];
                for (int i = 0; i < AppConfiguration.projects.length; i++) {
                    proj[i] = doc.createElement("project");
                    proj[i].appendChild(doc.createTextNode(AppConfiguration.projects[i]));
                    projects.appendChild(proj[i]);
                    AppConfiguration.projectData.add(new Projects(i, AppConfiguration.projects[i], "accounts_" + AppConfiguration.projects[i].toLowerCase() + ".xml", AppConfiguration.projects[i].toLowerCase() + "Idx", -1, ""));
                }

                // acronym element
                Element acronym = doc.createElement("name");
                acronym.appendChild(doc.createTextNode(""));
                rootElement.appendChild(acronym);

                // browser element
                Element browser = doc.createElement("browser");
                browser.appendChild(doc.createTextNode(""));
                rootElement.appendChild(browser);

                // server element
                Element server = doc.createElement("srv");
                server.appendChild(doc.createTextNode("0"));
                rootElement.appendChild(server);

                // language element
                Element language = doc.createElement("loca");
                language.appendChild(doc.createTextNode("0"));
                rootElement.appendChild(language);

                // name scheme element
                Element nScheme = doc.createElement("namescheme");
                rootElement.appendChild(nScheme);

                // name scheme element
                Element nScheme1 = doc.createElement("dept");
                nScheme1.appendChild(doc.createTextNode("qa"));
                nScheme.appendChild(nScheme1);

                // name scheme element
                Element nScheme2 = doc.createElement("mail_pref");
                nScheme2.appendChild(doc.createTextNode("mailcheck+"));
                nScheme.appendChild(nScheme2);

                // name scheme element
                Element nScheme3 = doc.createElement("mail_end");
                nScheme3.appendChild(doc.createTextNode("@placeholder.com"));
                nScheme.appendChild(nScheme3);

                // name scheme element
                Element nScheme4 = doc.createElement("pass");
                nScheme4.appendChild(doc.createTextNode("test1"));
                nScheme.appendChild(nScheme4);


                // write content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT,"yes");
//                transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(datafile.getPath() + File.separator + "project.xml");

                try {
                    transformer.transform(source, result);
                } catch (TransformerException e1) {
                    if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
                }

                readSettingsXML(datafile, true);

            } catch (Exception e1)
            {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
    }

    public static void readSettingsXML(File dataDir, boolean wasJustCreated) throws MalformedURLException {

        try {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(dataDir + File.separator + "project.xml"));

            doc.getDocumentElement().normalize();

            //reads the projects from the xml and adds them to a list
            if (!wasJustCreated) {
                NodeList projList = doc.getElementsByTagName("project");

                String projects[] = new String[projList.getLength()];

                for (int i = 0; i < projList.getLength(); i++) {
                    Node nNode = projList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        projects[i] = nNode.getTextContent();
                        AppConfiguration.projectData.add(new Projects(i, projects[i], "accounts_" + projects[i].toLowerCase() + ".xml", projects[i].toLowerCase() + "Idx", -1, ""));
                    }
                }
            }

            //selected project
            int proj = Integer.parseInt(doc.getElementsByTagName("proj").item(0).getAttributes().getNamedItem("selected").getNodeValue());
            if (AppConfiguration.projectData.size() <= proj) { AppConfiguration.pjCfgSelection = -1; } else { AppConfiguration.pjCfgSelection = proj; }

            //name scheme
            String namescheme_dept = doc.getElementsByTagName("dept").item(0).getTextContent();
            if (namescheme_dept == null) { AppConfiguration.dept = ""; } else { AppConfiguration.dept = namescheme_dept; }

            String mail_pref = doc.getElementsByTagName("mail_pref").item(0).getTextContent();
            if (mail_pref == null) { AppConfiguration.mail_pref = ""; } else { AppConfiguration.mail_pref = mail_pref; }

            String mail_end = doc.getElementsByTagName("mail_end").item(0).getTextContent();
            if (mail_end == null) { AppConfiguration.mail_end = ""; } else { AppConfiguration.mail_end = mail_end; }

            String pass = doc.getElementsByTagName("pass").item(0).getTextContent();
            if (pass == null) { AppConfiguration.pass = ""; } else { AppConfiguration.pass = pass; }

            //user initials
            String acr = doc.getElementsByTagName("name").item(0).getTextContent();
            if (acr == null) { AppConfiguration.acronym = ""; } else { AppConfiguration.acronym = acr; }

            //browser
            String browser = doc.getElementsByTagName("browser").item(0).getTextContent();
            if (browser == null) { AppConfiguration.browser = ""; } else { AppConfiguration.browser = browser; }

            //server
            int server = Integer.parseInt(doc.getElementsByTagName("srv").item(0).getTextContent());
            switch (server)
            {
                case 0:  AppConfiguration.srvSel = 0; break;
                case 1:  AppConfiguration.srvSel = 1; break;
                default: AppConfiguration.srvSel = 0; break;
            }

            //localization language
            int templ_language = Integer.parseInt(doc.getElementsByTagName("loca").item(0).getTextContent());
            switch (templ_language)
            {
                case 0:  AppConfiguration.locaSel = 0; break;
                case 1:  AppConfiguration.locaSel = 1; break;
                default: AppConfiguration.locaSel = 0; break;
            }

            for (int i = 0; i < AppConfiguration.projectData.size(); i++) {
                AppConfiguration.projectData.get(i).setProjectAccBookmarkIdx(AccountXmlFactory.getFavAccID(i));
                AppConfiguration.projectData.get(i).setProjectAccBookmarkName(AccountXmlFactory.getFavAccName(i));
            }


        } catch (Exception e1)
        {
//            if (Constants.debugVersion) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }

    public static void saveSettings() throws MalformedURLException, FileNotFoundException {

        File cfgXml = new File(Configuration.config.getJarFile().getParent() + File.separator + "data");

        if (new File(cfgXml.getPath() + File.separator + "project.xml").exists()) {

            try {
                DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new File(cfgXml + File.separator + "project.xml"));

                doc.getDocumentElement().normalize();

                doc.getElementsByTagName("proj").item(0).getAttributes().getNamedItem("selected").setNodeValue(String.valueOf(AppConfiguration.pjCfgSelection));

                if (Configuration.debugState) { doc.getElementsByTagName("dbg").item(0).setTextContent("1"); }
                else { doc.getElementsByTagName("dbg").item(0).setTextContent("0"); }

                if (Configuration.logState) { doc.getElementsByTagName("logs").item(0).setTextContent("1"); }
                else { doc.getElementsByTagName("logs").item(0).setTextContent("0"); }

                doc.getElementsByTagName("theme").item(0).setTextContent(String.valueOf(Configuration.themeState));

                doc.getElementsByTagName("colorscheme").item(0).setTextContent(String.valueOf(Configuration.colorScheme));

                if (Configuration.animations) { doc.getElementsByTagName("anim").item(0).setTextContent("1"); }
                else { doc.getElementsByTagName("anim").item(0).setTextContent("0"); }

                doc.getElementsByTagName("name").item(0).setTextContent(AppConfiguration.acronym);
                doc.getElementsByTagName("browser").item(0).setTextContent(AppConfiguration.browser);
                doc.getElementsByTagName("srv").item(0).setTextContent(String.valueOf(AppConfiguration.srvSel));
                doc.getElementsByTagName("loca").item(0).setTextContent(String.valueOf(AppConfiguration.locaSel));

                doc.getElementsByTagName("locale").item(0).getAttributes().getNamedItem("ID").setNodeValue(String.valueOf(Configuration.language));

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(cfgXml, "project.xml"));
                transformer.transform(source, result);
                LogFileHandler.logger.log(Level.INFO, "config.save:Success");
            } catch (Exception e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        }
        else
        {
            createConfigXML(cfgXml.getParentFile());
            saveSettings();
        }
    }

    /** adds a new project **/
    public static void addProject(String projectName) throws MalformedURLException, FileNotFoundException {

        if (new File(Configuration.config.getJarFile().getPath() + File.separator + "project.xml").exists()) {


            try {
                DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(Configuration.config.getJarFile().getPath() + File.separator + "project.xml");
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("project");
                Node projects = doc.getElementsByTagName("proj").item(0);

                // project element
                Element proj = doc.createElement("project");
                proj.appendChild(doc.createTextNode(projectName));
                projects.appendChild(proj);

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT,"yes");
//                transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, "2");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(Configuration.config.getJarFile().getPath() + File.separator + "project.xml");
                transformer.transform(source, result);

                //adds new project to projectData list
                AppConfiguration.projectData.add(new Projects(nList.getLength() + 1, projectName, "accounts_" + projectName.toLowerCase() + ".xml", projectName.toLowerCase() + "Idx", -1, ""));

            } catch (ParserConfigurationException | SAXException | IOException | TransformerException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        }
        else
        {
            createConfigXML(Configuration.config.getJarFile().getParentFile());
            addProject(projectName);
        }
    }

    public static void saveBugTemplateSettings(int pjState) throws MalformedURLException, FileNotFoundException {

        File cfgXml = new File(Configuration.config.getJarFile().getParent() + File.separator + "data");

        if (new File(cfgXml.getPath() + File.separator + "project.xml").exists()) {
            try {
                DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
                Document cfgdoc = dBuilder.parse(new File(cfgXml + File.separator + "project.xml"));

                cfgdoc.getDocumentElement().normalize();

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                cfgdoc.getElementsByTagName("browser").item(0).setTextContent(BugReportSettings.browserTF.getText());
                cfgdoc.getElementsByTagName("srv").item(0).setTextContent(String.valueOf(BugReportSettings.serverCB.getSelectionModel().getSelectedIndex()));
                cfgdoc.getElementsByTagName("loca").item(0).setTextContent(String.valueOf(BugReportSettings.locaCB.getSelectionModel().getSelectedIndex()));


                // write the content into xml file
                DOMSource cfgSrc = new DOMSource(cfgdoc);
                StreamResult result = new StreamResult(new File(cfgXml, "project.xml"));
                transformer.transform(cfgSrc, result);

                if (pjState >= 0) {
                    if (new File(Configuration.config.getJarFile().getParent() + File.separator + "data" + File.separator + AppConfiguration.projectData.get(pjState).getProjectXML()).exists()) {

                        Document accDoc = dBuilder.parse(Configuration.config.getJarFile().getParent() + File.separator + "data" + File.separator + AppConfiguration.projectData.get(pjState).getProjectXML());
                        accDoc.getDocumentElement().normalize();

                        accDoc.getElementsByTagName("favAcc").item(0).getAttributes().getNamedItem(AppConfiguration.projectData.get(pjState).getProjectAttribute()).setNodeValue(String.valueOf(BugReportSettings.accountCB.getSelectionModel().getSelectedIndex()));

                        DOMSource accSrc = new DOMSource(accDoc);
                        StreamResult result2 = new StreamResult(Configuration.config.getJarFile().getParent() + File.separator + "data" + File.separator + AppConfiguration.projectData.get(pjState).getProjectXML());
                        transformer.transform(accSrc, result2);
                    }
                }


                LogFileHandler.logger.log(Level.INFO, "config.save:Success");
            } catch (Exception e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        }
        else
        {
            createConfigXML(Configuration.config.getJarFile().getParentFile());
            saveBugTemplateSettings(pjState);
        }

    }
}