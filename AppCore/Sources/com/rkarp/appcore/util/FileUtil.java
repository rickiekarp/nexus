package com.rkarp.appcore.util;

import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class FileUtil {

    public static String readManifestProperty(Manifest manifest, String key) {
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(key);
    }

    public static String readManifestPropertyFromJar(String jarPath, String key) throws IOException {
        JarFile jar = new JarFile(jarPath);
        Manifest manifest = jar.getManifest();
        Attributes attributes = manifest.getMainAttributes();
        jar.close();
        return attributes.getValue(key);
    }


    public static List<String> readManifestPropertiesFromJar(String jarPath, String... keys) throws IOException {
        List<String> values = new ArrayList<>();
        for (String key : keys) {
            values.add(getManifestAttributes(jarPath).getValue(key));
        }
        return values;
    }

    private static Attributes getManifestAttributes(String jarPath) throws IOException {
        JarFile jar = new JarFile(jarPath);
        Manifest manifest = jar.getManifest();
        Attributes attributes = manifest.getMainAttributes();
        jar.close();
        return attributes;
    }

    public static File[] getListOfFiles(File selectedDirectory) {
        //list all files in start directory
        return selectedDirectory.listFiles();
    }

    public static void moveFile(Path moveFrom, Path moveTo) {
        if (!moveTo.toFile().exists()) { moveTo.toFile().mkdirs(); }
        try {
            Files.move(moveFrom, moveTo.resolve(moveFrom.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); }
            else { new ExceptionHandler(Thread.currentThread(), e); }
        }
    }

    public static String readFirstLineFromFile(File filepath) throws IOException {
        BufferedReader brTest = new BufferedReader(new FileReader(filepath));
        return brTest.readLine();
    }

    public static void writeFile(byte[] data, String filepath) {
        FileOutputStream out;
        try {
            out = new FileOutputStream(filepath);
            out.write(data);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
