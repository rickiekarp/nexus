package com.rkarp.botlib;

import com.rkarp.appcore.debug.LogFileHandler;
import com.rkarp.appcore.util.OSValidator;
import com.rkarp.appcore.view.MessageDialog;
import com.rkarp.botlib.model.PluginData;
import com.rkarp.botlib.plugin.PluginExecutor;
import com.rkarp.botlib.runner.BotRunner;
import com.rkarp.botlib.runner.BotRunnerBuilder;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import javafx.application.Platform;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

public class BotLauncher {
    private static BotRunner runner;

    private AppiumDriverLocalService appiumService;
    private GeckoDriverService geckoDriverService;
    private ChromeDriverService chromeDriverService;

    private boolean serviceRunning;
    public boolean isServiceRunning() {
        return serviceRunning;
    }

    public void createBotRunner(PluginData data) {
        runner = new BotRunnerBuilder().build(data);
    }

    public static BotRunner getRunnerInstance() {
        return runner;
    }

    /**
     * Sets up the capabilities and executes the bot
     */
    public void launch(PluginData plugin) throws Exception {
        if (PluginConfig.isBrowserBotPlugin) {
            runner.setCredentials(plugin.getPluginCredentials());
            PluginExecutor.executePlugin(runner, plugin);
        } else {
            PluginExecutor.executePlugin(plugin);
        }
    }

    private boolean bugCheck() {
        switch (PluginConfig.botPlatform) {
            case ANDROID:
                break;
            case WEB:
                if (PluginConfig.botType == null) {
                    Platform.runLater(() -> new MessageDialog(0, "Invalid browser selection!", 500, 300));
                    return true;
                }
                break;
        }
        return false;
    }

    /**
     * Starts the appium server / web driver for the respective platform
     * @throws IOException IOException
     */
    @SuppressWarnings("unchecked")
    public void startAppiumService() throws IOException {
        //check for available bot type
        if (bugCheck()) {
            return;
        }

        OSValidator.OperatingSystem os = OSValidator.getOS();
        String fileName = null;
        File driverFile;

        switch (PluginConfig.botType) {
            case CHROME:
                if (os != null) {
                    switch (os) {
                        case WINDOWS:   fileName = "chromedriver.exe";  break;
                        case UNIX:      fileName = "chromedriver";      break;
                        default:        fileName = "chromedriver";
                    }
                }

                driverFile = new File(BotConfig.getModulesDirFile() + File.separator + "driver" + File.separator + fileName);
                if (driverFile.exists()) {
                    System.setProperty("webdriver.chrome.driver", driverFile.getAbsolutePath());
                    chromeDriverService = ChromeDriverService.createDefaultService();
                    chromeDriverService.start();
                    runner.setDriverService(chromeDriverService);
                    serviceRunning = true; break;
                } else {
                    Platform.runLater(() -> new MessageDialog(0, "Could not find appropriate driver!", 400,200));
                    serviceRunning = false; break;
                }

            case FIREFOX:
                if (os != null) {
                    switch (os) {
                        case WINDOWS:   fileName = "geckodriver.exe";  break;
                        case UNIX:      fileName = "geckodriver";      break;
                        default:        fileName = "geckodriver";
                    }
                } else {
                    LogFileHandler.logger.warning("Could not detect OS!");
                    serviceRunning = false; break;
                }

                driverFile = new File(BotConfig.getModulesDirFile() + File.separator + "driver" + File.separator + fileName);
                if (driverFile.exists()) {
                    System.setProperty("webdriver.gecko.driver", driverFile.getAbsolutePath());

                    geckoDriverService = new GeckoDriverService.Builder()
                            .usingAnyFreePort()
                            .usingDriverExecutable(driverFile)
                            .build();
                    geckoDriverService.start();
                    runner.setDriverService(geckoDriverService);
                    serviceRunning = true; break;
                } else {
                    Platform.runLater(() -> new MessageDialog(0, "Could not find appropriate driver!", 400,200));
                    serviceRunning = false; break;
                }

            case ANDROID:
                String[] appiumLogLevel = {"error", "warn", "info", "debug"};
                File appiumPath = new File(BotConfig.getModulesDirFile() + File.separator + BotConfig.appiumBinary);
                if (appiumPath.exists()) {
                    appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                                    .usingDriverExecutable(new File(BotConfig.nodeBinary))
                                    .withAppiumJS(appiumPath)
                                    .withIPAddress("127.0.0.1")
                                    .usingAnyFreePort()
                                    .withArgument(GeneralServerFlag.LOG_LEVEL, appiumLogLevel[BotConfig.APPIUM_LOG_LEVEL])
                            //.withLogFile(new File("target/"+deviceUnderExecution+".log"))
                    );
                    appiumService.start();
                    runner.setDriverService(appiumService);
                    serviceRunning = true; break;
                } else {
                    Platform.runLater(() -> new MessageDialog(0, "Could not find appium module at " + appiumPath.getPath() + "!\nCheck your settings!", 750,300));
                    serviceRunning = false; break;
                }
            default: LogFileHandler.logger.warning("Invalid bot type!"); serviceRunning = false;
        }
    }

    /**
     * Stops the driver service of the current botType
     */
    public void stopAppiumService() {
        if (serviceRunning) {
            switch (PluginConfig.botType) {
                case CHROME:
                    chromeDriverService.stop(); break;
                case FIREFOX:
                    geckoDriverService.stop(); break;
                case ANDROID:
                    appiumService.stop(); break;
            }
        }
    }

    public static DesiredCapabilities getCapabilities(PluginData plugin) {
        DesiredCapabilities desiredCapabilities;

        switch (PluginConfig.botPlatform) {
            case NONE:
                LogFileHandler.logger.info("No platform selected!");
                return null;

            case WEB:
                switch (PluginConfig.botType) {
                    case CHROME:
                        //set up chrome options
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("user-data-dir=" + PluginConfig.chromeConfigDirectory);
                        options.addArguments("--profile-directory=" + PluginConfig.browserProfileName);
//                      options.setBinary(AppConfiguration.browserBinaryPath);

                        //set driver capabilities
                        desiredCapabilities = DesiredCapabilities.chrome();
                        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                        return desiredCapabilities;

                    case FIREFOX:
                        desiredCapabilities = DesiredCapabilities.firefox();
                        desiredCapabilities.setCapability(FirefoxDriver.PROFILE, new ProfilesIni().getProfile(PluginConfig.browserProfileName));
//                        desiredCapabilities.setCapability("marionette", true);
//                      desiredCapabilities.setCapability(FirefoxDriver.BINARY, AppConfiguration.browserBinaryPath);
                        return desiredCapabilities;

                    default:
                        LogFileHandler.logger.info("Invalid browser! Check your browser selection!");
                        return null;
                }

            case ANDROID:
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, BotConfig.DEVICE_NAME);
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, BotConfig.VERSION);
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "android");
                capabilities.setCapability(MobileCapabilityType.UDID, BotConfig.UDID);
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
//                capabilities.setCapability(MobileCapabilityType.APP, BotConfig.APP_PATH);
                capabilities.setCapability("appPackage", plugin.getPluginPackage());
                capabilities.setCapability("appActivity", plugin.getPluginActvity());
                return capabilities;

            default:
                LogFileHandler.logger.info("Invalid platform! Check your platform setting!");
                return null;
        }
    }
}
