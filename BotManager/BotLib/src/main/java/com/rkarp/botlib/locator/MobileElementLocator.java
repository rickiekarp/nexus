package com.rkarp.botlib.locator;

import com.rkarp.botlib.BotLauncher;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

class MobileElementLocator {
    private AppiumDriver driver;

    MobileElementLocator() {
        driver = (AppiumDriver) BotLauncher.getRunnerInstance().get();
    }

    WebElement getElement(By byString) {
        try {
            return driver.findElement(byString);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
