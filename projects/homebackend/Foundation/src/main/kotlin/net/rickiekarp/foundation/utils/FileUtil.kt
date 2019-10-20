package net.rickiekarp.foundation.utils

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import net.rickiekarp.foundation.logger.Log

import java.io.*
import java.net.URL
import java.nio.charset.Charset

class FileUtil {
    companion object {

        /**
         * Creates the file path of the given file in case it does not exist.
         * @param file File to check
         */
        fun checkCreateDirectories(file: File) {
            if (!file.exists()) {
                if (file.mkdirs()) {
                    Log.DEBUG.debug("Created file: " + file.path)
                }
            }
        }

        /* JSON utility method */
        fun readJsonFromString(jsonString: String): JsonObject {
            val jelement = JsonParser.parseString(jsonString)
            return jelement.asJsonObject
        }

        /* JSON utility method */
        @Throws(IOException::class)
        fun readJsonFromFile(path: File): JsonObject {
            val rd = BufferedReader(FileReader(path))
            val jsonString = readAll(rd)
            val jelement = JsonParser.parseString(jsonString)
            return jelement.asJsonObject
        }

        /* JSON utility method */
        @Throws(IOException::class)
        fun readJsonFromInputStream(inputStream: InputStream): JsonObject {
            val rd = BufferedReader(InputStreamReader(inputStream))
            val jsonString = readAll(rd)
            val jelement = JsonParser.parseString(jsonString)
            return jelement.asJsonObject
        }

        /* JSON utility method */
        @Throws(IOException::class)
        private fun readJsonFromUrl(url: String): JsonObject {
            URL(url).openStream().use { `is` ->
                val rd = BufferedReader(InputStreamReader(`is`,
                        Charset.forName("UTF-8")))
                val jsonString = readAll(rd)
                val jelement = JsonParser.parseString(jsonString)
                return jelement.asJsonObject
            }
        }

        /* JSON utility method */
        @Throws(IOException::class)
        private fun readAll(rd: Reader): String {
            val sb = StringBuilder()
            var cp = rd.read()
            while (cp != -1) {
                sb.append(cp.toChar())
                cp = rd.read()
            }
            return sb.toString()
        }

        fun readJsonArrayFromString(jsonString: String): JsonArray {
            return JsonParser.parseString(jsonString).asJsonArray
        }

        fun writeJsonToFile(outputPath: String, fileName: String, jsonDataObject: Any) {
            val mapper = ObjectMapper()
            val writer = mapper.writer(DefaultPrettyPrinter())
            try {
                writer.writeValue(File(outputPath + File.separator + fileName + ".json"), jsonDataObject)
            } catch (e: IOException) {
                Log.DEBUG.error("Exception", e)
            }
        }

        @Throws(FileNotFoundException::class)
        fun clearFileContent(file: File) {
            val writer = PrintWriter(file)
            writer.close()
        }
    }

    /**
     * Writes the given text into a shell file and returns the path of the newly created file.
     * @param text Text that is written to the shell file
     * @param shellName File name of the new file
     * @return Path of newly created file
     */
    private fun writeToShFile(text: String, shellName: String): String {
        var file: File? = null
        try {
            val appDir = File(System.getProperty("user.dir"))
            file = File(appDir, shellName)
            val output = BufferedWriter(FileWriter(file))
            output.write(text)
            output.close()
        } catch (e: IOException) {
            Log.DEBUG.error("Exception", e)
        }

        return file!!.absolutePath
    }
}
