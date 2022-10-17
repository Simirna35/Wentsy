package com.wentsy.stepDefinitions;

import com.wentsy.utilities.BrowserUtils;
import com.wentsy.utilities.Driver;
import com.wentsy.utilities.Pages;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertEquals;

public class LoginStepDefinitions extends BaseStepDefinitions {

    private static final Logger LOG = LogManager.getLogger(LoginStepDefinitions.class);

    public LoginStepDefinitions(BrowserUtils browserUtils, Driver driver, Pages pages) {
        super(browserUtils, driver, pages);
    }

    @Given("Navigate to main page")
    public void navigateToMainPage() {
        getPages().loginPage().navigateToMainPage();
    }

    @When("Login to application with valid credentials")
    public void loginToApplicationWithValidCredentials() {
        getPages().loginPage().loginToApplicationWithValid();
    }

    @When("Login to application with invalid credentials {string} and {string}")
    public void loginToApplicationWithInvalidCredentialsAnd(String username, String password) {
        getPages().loginPage().loginToApplicationWithInvalid(username, password);
    }

    @Then("Verify username {string} is displayed")
    public void verifyUsernameIsDisplayed(String expectedUserName) {
        String hello = expectedUserName.substring(0, expectedUserName.indexOf(' '));
        String name = expectedUserName.substring(expectedUserName.indexOf(' ') + 1);

        expectedUserName = hello + "\n" + name;
        String actualUserName = getPages().loginPage().getUserText();

        assertEquals("Verification is failed! Expected username did not matched to actual!", expectedUserName, actualUserName);
        LOG.info("Username is displayed: {}", actualUserName);
    }
}
