package net.rickiekarp.core.controller


import net.rickiekarp.core.debug.LogFileHandler
import net.rickiekarp.core.settings.Configuration
import java.io.IOException
import java.util.Locale
import java.util.Properties

/**
 * Language controller class.
 */
object LanguageController {

    private var prop: Properties

    init {
        prop = Properties()
    }

    /**
     * Checks which language is used on the system and sets program specific language variables respectively.
     */
    @JvmStatic
    val currentLocale: Int
        get() {
            when (Configuration.CURRENT_LOCALE.toString()) {
                "en" -> return 0
                "de" -> return 1
                else -> return 0
            }
        }

    /**
     * Checks which language is used on the system and returns the respective integer.
     */
    @JvmStatic
    val locale: Int
        get() {
            when (System.getProperty("user.language")) {
                "en" -> return 0
                "de" -> return 1
                else -> return 0
            }
        }

    /**
     * Loads the language .properties file
     */
    @JvmStatic
    fun loadLangFile() {
        try {
            val utf8in = LanguageController::class.java.classLoader.getResourceAsStream("language_packs/language_" + Configuration.CURRENT_LOCALE + ".properties")

            try {
                prop.load(utf8in)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            utf8in.close()
        } catch (e1: NullPointerException) {
            LogFileHandler.logger.warning("Error")
            //            e1.printStackTrace();
        } catch (e1: IOException) {
            LogFileHandler.logger.warning("Error")
        }

    }

    /**
     * Returns the text string of the given TextID
     * If no text string can be found, the TextID is returned.
     */
    @JvmStatic
    fun getString(textID: String): String {
        try {
            // get the property value
            val value = prop.getProperty(textID)

            return if (value.isNullOrBlank()) {
                textID
            } else {
                value
            }
        } catch (e1: NullPointerException) {
            LogFileHandler.logger.warning("Error when loading text ID: $textID")
            return textID
        }

    }

    /**
     * Checks which language is used on the system and sets program specific language variables respectively.
     */
    @JvmStatic
    fun setCurrentLocale() {
        when (Configuration.language) {
            0 -> Configuration.CURRENT_LOCALE = Locale.ENGLISH
            1 -> Configuration.CURRENT_LOCALE = Locale.GERMAN
            else -> Configuration.CURRENT_LOCALE = Locale.ENGLISH
        }
    }
}
