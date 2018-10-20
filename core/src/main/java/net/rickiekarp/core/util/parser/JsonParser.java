package net.rickiekarp.core.util.parser;

import net.rickiekarp.core.debug.LogFileHandler;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;

public class JsonParser {

    public static JSONObject readJsonFromFile(File file) {
        if (file.exists()){
            try {
                InputStream is = new FileInputStream(file);
                String jsonTxt = IOUtils.toString(is, "utf-8");
                return new JSONObject(jsonTxt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            LogFileHandler.logger.warning("File does not exist at " + file.getPath());
        }
        return null;
    }

    public static void writeJsonObjectToFile(JSONObject obj, File outputFile, String fileName) {
        if (!outputFile.exists()) {
            if (!outputFile.mkdirs()) {
                System.out.println("Could not create directory at " + outputFile.getPath());
                return;
            }
        }

        try {
            try (FileWriter file = new FileWriter(outputFile + File.separator + fileName)) {
                file.write(obj.toString(4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
