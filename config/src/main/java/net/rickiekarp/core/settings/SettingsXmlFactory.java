package net.rickiekarp.core.settings;

import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.ui.windowmanager.ThemeSelector;
import javafx.scene.paint.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * This class handles reading and writing of the config.xml file.
 */
public class SettingsXmlFactory {

    /**
     * Saves the current state of the program to the config.xml file.
     */
    void createConfigXML() {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            doc.setXmlVersion("1.1");

            // root element
            Element rootElement = doc.createElement("settings");
            doc.appendChild(rootElement);

            // create all elements
            for (Field f : LoadSave.class.getDeclaredFields()) {
                createElement(doc, f.getName(), getFieldValueString(f));
            }
        } catch (Exception e)
        {
            if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e); }
        }
    }

    /**
     * Creates an xml element.
     * @param doc  the config.xml Document
     * @param name the value of the element
     */
    private void createElement(Document doc, String name, Object value) {
        Element root = doc.getDocumentElement();

        Element element = doc.createElement("entry");
        element.setAttribute("key", name);
        if (value != null) {
            element.appendChild(doc.createTextNode(String.valueOf(value)));
        }

        root.appendChild(element);

        // write content into xml file
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(Configuration.config.getConfigDirFile() + File.separator + Configuration.config.getConfigFileName());
            transformer.transform(source, result);
        } catch (TransformerException e) {
            if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e); }
        }
    }

    private Object getFieldValueString(Field f) {
        try {
            if (f.getType() == Color.class) {
                return ThemeSelector.getColorHexString(Color.valueOf(String.valueOf(f.get(LoadSave.class))));
            } else {
                return f.get(LoadSave.class);
            }
        } catch (IllegalAccessException e) {
            if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e); }
            return null;
        }
    }

    String getElementValue(String key, Class clazz) {
        File configFile = new File(Configuration.config.getConfigDirFile() + File.separator + Configuration.config.getConfigFileName());
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(configFile);
            doc.getDocumentElement().normalize();

            try {
                checkXmlNode(doc, clazz.getDeclaredField(key));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            NodeList nList = doc.getElementsByTagName("entry");

            for (int i = 0; i < nList.getLength(); i++) {
                if (nList.item(i).getAttributes().getNamedItem("key").getNodeValue().equals(key) && !nList.item(i).getTextContent().isEmpty()) {
                    return nList.item(i).getTextContent();
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void setElementValue(String key, String value) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(Configuration.config.getConfigDirFile() + File.separator + Configuration.config.getConfigFileName()));

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("entry");

            for (int i = 0; i < nList.getLength(); i++) {
                if (nList.item(i).getAttributes().getNamedItem("key").getNodeValue().equals(key)) {
                    nList.item(i).setTextContent(value);
                    break;
                }
            }

            // write content into xml file
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(Configuration.config.getConfigDirFile() + File.separator + Configuration.config.getConfigFileName());
                transformer.transform(source, result);
            } catch (TransformerException e1) {
                if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if all xml nodes exist. If a node does not exist it is created.
     * @param doc The xml Document
     */
    private void checkXmlNode(Document doc, Field f) {
        NodeList nList = doc.getElementsByTagName("entry");
        boolean insertNewKey = true;
        for (int i = 0; i < nList.getLength(); i++) {
            if (nList.item(i).getAttributes().getNamedItem("key").getNodeValue().equals(f.getName())) {
                insertNewKey = false;
                break;
            }
        }
        if (insertNewKey) {
            try {
                createElement(doc, f.getName(), getFieldValueString(LoadSave.class.getDeclaredField(f.getName())));
            } catch (NoSuchFieldException e) {
                createElement(doc, f.getName(), getFieldValueString(f));
            }
        }
    }

    /**
     * Checks if all xml nodes exist. If a node does not exist it is created.
     * @param doc The xml Document
     */
    @Deprecated
    private void checkXmlNodes(Document doc) {
        Field[] fields = LoadSave.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (doc.getElementsByTagName("entry").item(i) == null) {
                createElement(doc, fields[i].getName(), getFieldValueString(fields[i]));
            }
        }
    }

    /**
     * Returns a NodeList of a given node name
     * @param nodeName Node name
     * @return Node List of the given node name
     */
    public NodeList getNodeList(String nodeName) {
        File configFile = new File(Configuration.config.getConfigDirFile() + File.separator + Configuration.config.getConfigFileName());
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(configFile);
            doc.getDocumentElement().normalize();
            return doc.getElementsByTagName(nodeName);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e); }
            return null;
        }
    }
}