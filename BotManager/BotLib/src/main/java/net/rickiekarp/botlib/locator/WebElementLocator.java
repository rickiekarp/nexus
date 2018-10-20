package net.rickiekarp.botlib.locator;

import net.rickiekarp.botlib.BotLauncher;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class WebElementLocator {
    private WebDriver driver;

    WebElementLocator() {
        driver = (WebDriver) BotLauncher.getRunnerInstance().get();
    }

    WebElement getElement(By byString) {
        try {
            return driver.findElement(byString);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

}
