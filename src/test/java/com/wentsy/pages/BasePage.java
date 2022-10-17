package com.wentsy.pages;

import com.wentsy.utilities.BrowserUtils;
import com.wentsy.utilities.ConfigurationReader;
import com.wentsy.utilities.Driver;
import com.wentsy.utilities.Pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;



public class BasePage {

    private static final Logger LOG = LogManager.getLogger(BasePage.class);
    private final BrowserUtils browserUtils;
    private final Driver driver;
    private final ConfigurationReader configurationReader;

    public BasePage(BrowserUtils browserUtils, Driver driver, ConfigurationReader configurationReader) {
        this.browserUtils = browserUtils;
        this.driver = driver;
        this.configurationReader = configurationReader;
        PageFactory.initElements(getDriver().get(), this);
    }

    public BrowserUtils getBrowserUtils() {
        return this.browserUtils;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public ConfigurationReader getConfigurationReader() {
        return this.configurationReader;
    }

    public Pages getPages() {
        return new Pages(getBrowserUtils(), getDriver(), getConfigurationReader());
    }

    public void clickToElementByText(String text) {
        String path = "(//nav//*[contains(text(),\"" + text + "\")])[1]";

        WebElement moduleElement = getDriver().get().findElement(By.xpath(path));
        moduleElement.click();
    }

    public void sendTextToTextBox(String data, String textbox) {
        String path = "//input[@placeholder='" + textbox + "']";

        WebElement textBoxElement = getDriver().get().findElement(By.xpath(path));
        textBoxElement.click();
        textBoxElement.clear();
        textBoxElement.sendKeys(data);
    }

    public void clickToButton(String button) {
        String path = "//*[normalize-space(text())=\"" + button + "\"]";

        WebElement buttonElement = getDriver().get().findElement(By.xpath(path));
        buttonElement.click();

        LOG.info("Clicked to button: {}", button);
    }

    public boolean isMessageDisplayed(String message) {
        String path = "//*[normalize-space(text())=\"" + message + "\"]";

        WebElement messageElement;
        try {
            messageElement = getDriver().get().findElement(By.xpath(path));
        } catch (NoSuchElementException e) {
            return false;
        }
        LOG.info("Located element to to get message: {}", path);
        return messageElement.isDisplayed();
    }

    public String getErrorMessage() {
        String path = "#error-element-password";

        WebElement messageElement = getDriver().get().findElement(By.cssSelector(path));

        LOG.info("Located element to to get message: {}", path);
        return messageElement.getText();
    }
}
