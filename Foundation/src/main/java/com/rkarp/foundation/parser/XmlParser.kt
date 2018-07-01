package com.rkarp.foundation.parser

import org.w3c.dom.Document
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class XmlParser {
    companion object {
        /**
         * Converts a given XML String to a DOM Object and returns the document
         */
        fun stringToDom(xmlString: String) : Document {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            return builder.parse(InputSource(StringReader(xmlString)))
        }

        /**
         * Returns the NodeList of the given xml file path and the given element tag name
         * @param path Xml file path
         * @param elementTagName Element tag name to look for
         * @return NodeList of all elements with the given elementTagName
         */
        fun getNodeListFrom(path: String, elementTagName: String) : NodeList? {
            val doc: Document
            try {
                doc = stringToDom(FileParser.readFileAndReturnString(path))
            } catch (e: SAXException) {
                e.printStackTrace()
                return null
            } catch (e: ParserConfigurationException) {
                e.printStackTrace()
                return null
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }

            doc.documentElement.normalize()
            return doc.getElementsByTagName(elementTagName)
        }
    }
}