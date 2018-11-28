package net.rickiekarp.foundation.core.localization

import net.rickiekarp.foundation.logger.Log
import net.rickiekarp.foundation.parser.xml.DomXmlParser
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.util.*

abstract class AbstractLocalizationManager {

    /**
     * Loads the nodelist of the corresponding translation file
     */
    protected fun getTranslationsForLocale(classLoader: ClassLoader, locale: String): NodeList? {
        val parser = DomXmlParser()
        var nList: NodeList? = null
        val translationFolder = getTranslationFileFolder(locale)
        try {

            val translationFileUrl = classLoader.getResource("localization/$translationFolder/ios.xml")
            val xmlFile = File(translationFileUrl.path)
            nList = parser.parse(xmlFile)!!.getElementsByTagName("string")
            Log.DEBUG.info("Loaded translations for locale: $locale")
        } catch (e: Exception) {
            Log.DEBUG.error("Translations could not be loaded for locale: $locale (${e.message})")
        }

        return nList
    }

    private fun getTranslationFileFolder(locale: String): String {
        return when (locale) {
            "en" -> "values"
            else -> "values-$locale"
        }
    }

    /**
     * Returns the text string of the given textID
     * If no text string can be found, the textID is returned.
     */
    fun getText(textID: String, nList: NodeList?): String {
        if (nList == null) {
            Log.DEBUG.warn("Missing NodeList, please check the translation path!")
            return textID
        }
        var textContent: String? = null
        for (temp in 0 until nList.length) {
            val nNode = nList.item(temp)
            if (nNode.nodeType == Node.ELEMENT_NODE) {
                val eElement = nNode as Element
                if (eElement.getAttribute("name") == textID) {
                    textContent = eElement.textContent
                    break
                }
            }
        }
        return if (textContent == null) {
            Log.DEBUG.warn("No text with textID $textID could be found!")
            textID
        } else {
            textContent = trimQuotationMarks(textContent)
            Log.DEBUG.trace("getText: $textContent ($textID)")
            textContent
        }
    }

    fun getText(properties: Properties, textID: String): String {
        val locaValue = properties.getProperty(textID)
        if (locaValue == null || locaValue.isEmpty()) {
            Log.DEBUG.warn("No text with textID $textID could be found!")
            return textID
        }
        return locaValue
    }

    /**
     * Trims quotation marks of the given string
     * @param s String to trim
     * @return Trimmed string
     */
    private fun trimQuotationMarks(s: String): String {
        return if (s[0] == '"') {
            s.substring(1, s.length - 1)
        } else s
    }
}
