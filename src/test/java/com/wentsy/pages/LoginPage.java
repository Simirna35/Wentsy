package com.wentsy.pages;

import com.wentsy.utilities.BrowserUtils;
import com.wentsy.utilities.ConfigurationReader;
import com.wentsy.utilities.Driver;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.Logger;

public class LoginPage extends BasePage{

    private static final Logger LOG = LogManager.getLogger(LoginPage.class);
    @FindBy(id = "username")
    public WebElement usernameElement;

    @FindBy(id = "password")
    public WebElement passwordElement;

//    @FindBy(xpath = "//div/br/following-sibling::text()")
    @FindBy(css = ".header-user-name")
    public WebElement userElement;

    public LoginPage(BrowserUtils browserUtils, Driver driver, ConfigurationReader configurationReader) {
        super(browserUtils, driver, configurationReader);
    }

    public void navigateToMainPage() {
        String url = getConfigurationReader().getProperty("url");
        getDriver().get().get(url);
        LOG.info("Used the url to connect application: {}", url);

        //dismiss notification
        clickToButton("Weigeren");
    }

    public void loginToApplicationWithValid() {
        //get credentials paths
        String username = getConfigurationReader().getProperty("username");
        String password = getConfigurationReader().getProperty("password");

        login(username,password);
        LOG.info("Logged into application with credentials: {} | {}",username,password);
    }

    public void loginToApplicationWithInvalid(String username, String password) {
        login(username,password);
        LOG.info("Used credentials to try to login to application: {} | {}",username,password);
    }

    public void login(String username, String password){
        //click and send keys to username textbox
        usernameElement.click();
        usernameElement.sendKeys(username);

        //click and send keys to password textbox
        passwordElement.click();
        passwordElement.sendKeys(password);

        //click to connection button
        clickToButton("Doorgaan");
    }
    public String getUserText() {
        return userElement.getText();
    }

}
