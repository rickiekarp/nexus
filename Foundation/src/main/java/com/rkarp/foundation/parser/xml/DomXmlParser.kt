package com.rkarp.foundation.parser.xml

import org.w3c.dom.Document
import org.xml.sax.SAXException
import java.io.File
import java.io.IOException
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class DomXmlParser {

    /**
     * Parses the given file as a DOM document
     * @param xmlFile File to parse
     * @return DOM Document of the xml
     */
    fun parse(xmlFile: File): Document? {
        val dbFactory = DocumentBuilderFactory.newInstance()
        val dBuilder: DocumentBuilder
        var doc: Document? = null
        try {
            dBuilder = dbFactory.newDocumentBuilder()
            doc = dBuilder.parse(xmlFile)

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc!!.documentElement.normalize()
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return doc
    }
}