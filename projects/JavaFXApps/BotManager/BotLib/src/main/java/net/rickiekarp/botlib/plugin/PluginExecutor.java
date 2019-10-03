package net.rickiekarp.botlib.plugin;

import net.rickiekarp.core.debug.DebugHelper;
import net.rickiekarp.core.debug.ExceptionHandler;
import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.core.settings.Configuration;
import net.rickiekarp.botlib.model.PluginData;
import net.rickiekarp.botlib.runner.BotRunner;
import javafx.application.Platform;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.Policy;

public class PluginExecutor {

    public static void executeLayoutSetter(BotRunner runner, PluginData plugin) throws Exception {
        Policy.setPolicy(new PluginPolicy());
        System.setSecurityManager(new SecurityManager());

        File authorizedJarFile = new File(Configuration.config.getPluginDirFile() + File.separator + plugin.getPluginName() + ".jar");
        ClassLoader authorizedLoader;
        authorizedLoader = URLClassLoader.newInstance(new URL[] { authorizedJarFile.toURI().toURL() });
        BotPlugin authorizedBotPlugin;
        authorizedBotPlugin = (BotPlugin) authorizedLoader.loadClass(plugin.getPluginClazz()).newInstance();
        authorizedBotPlugin.setLayout(runner);
    }

    public static void executePlugin(BotRunner runner, PluginData plugin) throws Exception {
        Policy.setPolicy(new PluginPolicy());
        System.setSecurityManager(new SecurityManager());

        File authorizedJarFile = new File(Configuration.config.getPluginDirFile() + File.separator + plugin.getPluginName() + ".jar");
        ClassLoader authorizedLoader;
        authorizedLoader = URLClassLoader.newInstance(new URL[] { authorizedJarFile.toURI().toURL() });
        BotPlugin authorizedBotPlugin;
        LogFileHandler.logger.info("Starting " + plugin.getPluginType() + " bot - " + plugin.getPluginName() + " (" + plugin.getPluginOldVersion() + ")");
        authorizedBotPlugin = (BotPlugin) authorizedLoader.loadClass(plugin.getPluginClazz()).newInstance();
        authorizedBotPlugin.run(runner);
    }

    public static void executePlugin(PluginData plugin) {
        Policy.setPolicy(new PluginPolicy());
        System.setSecurityManager(new SecurityManager());

        try {
            File authorizedJarFile = new File(Configuration.config.getPluginDirFile() + File.separator + plugin.getPluginName() + ".jar");
            ClassLoader authorizedLoader;
            authorizedLoader = URLClassLoader.newInstance(new URL[] { authorizedJarFile.toURI().toURL() });
            Plugin authorizedPlugin;
            authorizedPlugin = (Plugin) authorizedLoader.loadClass(plugin.getPluginClazz()).newInstance();
            Platform.runLater(authorizedPlugin::run);
        } catch (Exception e) {
            if (DebugHelper.DEBUGVERSION) { e.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e); }
        }
    }

}
