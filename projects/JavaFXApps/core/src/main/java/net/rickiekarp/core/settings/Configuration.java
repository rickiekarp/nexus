package net.rickiekarp.core.settings;

import net.rickiekarp.core.AppContext;
import net.rickiekarp.core.controller.LanguageController;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.ui.windowmanager.ThemeSelector;
import net.rickiekarp.core.ui.windowmanager.Window;
import net.rickiekarp.core.util.CommonUtil;
import net.rickiekarp.core.util.FileUtil;
import javafx.geometry.Side;
import javafx.scene.paint.Color;
import net.rickiekarp.core.view.SettingsScene;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Contains general application information (Name, Version etc.)
 */
public class Configuration {
    public static Configuration config;
    private SettingsXmlFactory cfgXmlFactory;
    public SettingsXmlFactory getSettingsXmlFactory() { return cfgXmlFactory; }

    /** settings **/
    @LoadSave
    public static String host;
    @LoadSave
    public static int updateChannel;
    @LoadSave
    public static int language;
    @LoadSave
    public static int themeState;
    @LoadSave
    public static int colorScheme;
    @LoadSave
    public static boolean animations;
    @LoadSave
    public static boolean useSystemBorders;
    @LoadSave
    public static boolean logState;
    @LoadSave
    public static boolean showTrayIcon;

    /** advanced settings **/
    @LoadSave
    public static boolean debugState;
    @LoadSave
    public static Color decorationColor;
    @LoadSave
    public static Color shadowColorFocused;
    @LoadSave
    public static Color shadowColorNotFocused;
    @LoadSave
    public static Side tabPosition;

    /** locale **/
    public static Locale CURRENT_LOCALE;

    /** Jar location **/
    private File jarFile;
    public File getJarFile() {
        return jarFile;
    }
    public File getConfigDirFile() {
        return new File(getJarFile().getParentFile() + File.separator + "data");
    }
    public File getProfileDirFile() {
        return new File(getJarFile().getParentFile() + File.separator + "data" + File.separator + "profiles");
    }
    public File getPluginDirFile() {
        return new File(getConfigDirFile() + File.separator + "plugins");
    }
    public File getLogsDirFile() {
        return new File(getJarFile().getParentFile() + File.separator + "logs" + File.separator + CommonUtil.getTime("yyyy-MM-dd"));
    }
    public File getUpdatesDirFile() {
        return new File(getConfigDirFile() + File.separator + "update");
    }

    /** config file **/
    private String fileName;
    String getConfigFileName() {
        return fileName;
    }

    /**
     * Initialize configuration.
     * @param fileName the filename
     */
    public Configuration(String fileName, Class clazz) {
        this.fileName = fileName;

        try {
            jarFile = new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //set internal version number
        try {
            Manifest manifest = new JarFile(jarFile.getPath()).getManifest();
            AppContext.getContext().setInternalVersion(FileUtil.readManifestProperty(manifest, "Build-Time"));
        } catch (IOException e) {
            AppContext.getContext().setInternalVersion(CommonUtil.getDate("yyMMddHHmm"));
        }
    }

    /**
     * Try to load the configuration file.
     */
    public boolean load() {

        //instantiate SettingsXmlFactory
        cfgXmlFactory = new SettingsXmlFactory();

        //check if config file exists
        if (!new File(config.getConfigDirFile() + File.separator + fileName).exists()) {
            boolean isDirectoryCreated = Configuration.config.getConfigDirFile().exists();
            if (!isDirectoryCreated) {
                isDirectoryCreated = Configuration.config.getConfigDirFile().mkdir();
            }
            if (isDirectoryCreated) {
                cfgXmlFactory.createConfigXML();
            } else {
                return false;
            }
        }
        loadProperties(this.getClass());

        //set current Locale
        LanguageController.setCurrentLocale();

        //sets up the logger
        LogFileHandler.setupLogger();

        //starts logging
        LogFileHandler.startLogging();

        //post config set ups
        switch (colorScheme) {
            case 0:  Window.colorTheme = "darkgray"; break;
            case 1:  Window.colorTheme = "gray"; break;
            case 2:  Window.colorTheme = "black"; break;
            case 3:  Window.colorTheme = "red"; break;
            case 4:  Window.colorTheme = "orange"; break;
            case 5:  Window.colorTheme = "yellow"; break;
            case 6:  Window.colorTheme = "blue"; break;
            case 7:  Window.colorTheme = "magenta"; break;
            case 8:  Window.colorTheme = "purple"; break;
            case 9:  Window.colorTheme = "green"; break;
            default:  Window.colorTheme = "darkgray"; break;
        }
        return true;
    }

    /**
     * Assign properties to fields.
     */
    public void loadProperties(Class clazz) {
        try {
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(LoadSave.class)) {
                    String n = f.getName();
                    if (f.getType() == Boolean.TYPE) {
                        String s = cfgXmlFactory.getElementValue(n, clazz);
                        if (s != null) {
                            f.set(this,  Boolean.valueOf(s));
                        }
                    }
                    else  if (f.getType() == Integer.TYPE || f.getType() == Integer.class) {
                        String s = cfgXmlFactory.getElementValue(n, clazz);
                        if (s != null) {
                            f.set(this, Integer.valueOf(s));
                        }
                    }
                    else if (f.getType() == String.class) {
                        String s = cfgXmlFactory.getElementValue(n, clazz);
                        f.set(this, s);
                    }
                    else if (f.getType() == byte.class) {
                        String s = cfgXmlFactory.getElementValue(n, clazz);
                        f.set(this, Byte.valueOf(s));
                    }
                    else if (f.getType() == Color.class) {
                        String s = cfgXmlFactory.getElementValue(n, clazz);
                        f.set(this, Color.valueOf(s));
                    }
                    else if (f.getType() == Side.class) {
                        String s = cfgXmlFactory.getElementValue(n, clazz);
                        f.set(this, Side.valueOf(s));
                    }
                    else if (f.getType().isEnum()) {
                        String s = cfgXmlFactory.getElementValue(n, clazz);
                        if (s == null) { s = "NONE"; }
                        f.set(this, Enum.valueOf((Class<Enum>) f.getType(), s));
                    }
                    else {
                        LogFileHandler.logger.warning("Field type '" + f.getType() + "' not found!");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Try to save the configuration file.
     */
    public void save() {
        saveProperties(this.getClass());
    }

    /**
     * Try to save the configuration file.
     */
    public void saveProperties(Class clazz) {
        try {
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(LoadSave.class)) {
                    String n = f.getName();
                    Object o = f.get(this);
                    if (f.getType() == Color.class) {
                        cfgXmlFactory.setElementValue(n, ThemeSelector.getColorHexString((Color) o));
                    } else {
                        cfgXmlFactory.setElementValue(n, String.valueOf(o));
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Resets all settings to the default value.
     */
    public void setDefaults() {
        Field[] fields0 = LoadSave.class.getDeclaredFields();
        List<Field> list = new LinkedList<>();

        try {
            for (int i = 0; i < LoadSave.class.getDeclaredFields().length; i++) {
                for (Field f : this.getClass().getDeclaredFields()) {
                    if (f.getName().equals(fields0[i].getName())) {
                        //System.out.println(f.getName() + " cur: " + f.get(LoadSave.class) + " - def: " + fields0[i].get(LoadSave.class));

                        //add setting that has changed to a list
                        if (!f.get(LoadSave.class).equals(fields0[i].get(LoadSave.class))) {
                            list.add(f);
                        }

                        //Integer
                        if (f.getType() == Integer.TYPE) {
                            f.set(this, fields0[i].get(LoadSave.class));
                            break;
                        }

                        //Boolean
                        else if (f.getType() == Boolean.TYPE) {
                            f.set(this, fields0[i].get(LoadSave.class));
                            break;
                        }

                        //Color
                        else if (f.getType() == Color.class) {
                            f.set(this, fields0[i].get(LoadSave.class));
                            break;
                        }

                        //Side
                        else if (f.getType() == Side.class) {
                            f.set(this, fields0[i].get(LoadSave.class));
                            break;
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //update Settings GUI
        if (SettingsScene.settingsScene != null) { SettingsScene.settingsScene.updateGUI(list); }
    }
}