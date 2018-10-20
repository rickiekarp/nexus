package net.rickiekarp.botlib.locator;

import net.rickiekarp.core.debug.LogFileHandler;
import net.rickiekarp.botlib.BotLauncher;
import net.rickiekarp.botlib.PluginConfig;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Waiter {

    public synchronized static WebElement waitForElement(final int timeOutInSeconds, By byString) {
        final WebElement[] elem = new WebElement[1];
        WebElementLocator locator = new WebElementLocator();

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable beeper = () -> {
            elem[0] = locator.getElement(byString);

            if (elem[0] != null) {
                scheduler.shutdownNow();
            }
        };
        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 1, TimeUnit.SECONDS);
        scheduler.schedule(() -> {
            beeperHandle.cancel(true);
            scheduler.shutdown();
        }, timeOutInSeconds, TimeUnit.SECONDS);

        try {
            scheduler.awaitTermination(timeOutInSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return elem[0];
    }

    /**
     * Tries to find the element of a given By String for a given time duration and returns it
     * @param timeout Duration to check for the given element
     * @param byString byString to search for
     * @return Element of the given byString
     */
    public static WebElement waitForElementToAppear(long timeout, By byString) {
        long currentTime = System.currentTimeMillis() / 1000;
        long endTime = currentTime + timeout;
        WebElement element = null;

        boolean whetherTimeout;
        while (true) {
            whetherTimeout = System.currentTimeMillis() / 1000 > endTime;
            if (whetherTimeout) {
                LogFileHandler.logger.warning("no element found by " + byString);
                break;
            } else {
                try {
                    switch (PluginConfig.botType) {
                        case ANDROID:
                            AndroidDriver androidDriver = (AndroidDriver) BotLauncher.getRunnerInstance().get();
                            element = androidDriver.findElement(byString);
                            break;
                        case FIREFOX:
                            MarionetteDriver firefoxDriver = (MarionetteDriver) BotLauncher.getRunnerInstance().get();
                            element = firefoxDriver.findElement(byString);
                            break;
                        case CHROME:
                            ChromeDriver chromeDriver = (ChromeDriver) BotLauncher.getRunnerInstance().get();
                            element = chromeDriver.findElement(byString);
                            break;
                        default:
                            throw new RuntimeException("Invalid bot type! Check your settings!");
                    }
                } catch (Exception e) {
                    //ignore
                }
                if (element != null) {
                    break;
                }
            }

            //prevent spamming of server requests
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return element;
    }
}
