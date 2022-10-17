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
import static org.junit.Assert.assertTrue;

public class GeneralStepDefinitions extends BaseStepDefinitions {

    private static final Logger LOG = LogManager.getLogger(GeneralStepDefinitions.class);

    public GeneralStepDefinitions(BrowserUtils browserUtils, Driver driver, Pages pages) {
        super(browserUtils, driver, pages);
    }

    //    @Given("User click to {string} (module)(page)(murat kurt)")
    @Given("User click to {string} module/page")
    public void userClickToModule(String text) {
        getPages().eventsPage().clickToElementByText(text);
    }

    @Given("User enter {string} into {string} textbox")
    public void userEnterIntoTextbox(String data, String textbox) {
        getPages().eventsPage().sendTextToTextBox(data, textbox);
    }

    @When("Click to {string} button")
    public void clickToButton(String button) {
        getPages().loginPage().clickToButton(button);
    }

    @Then("Verify to {string} message")
    public void verifyToMessage(String message) {
        boolean isMessageDisplayed = getPages().loginPage().isMessageDisplayed(message);

        assertTrue("Verification is failed! Expected message is not displayed!", isMessageDisplayed);
        LOG.info("The message is displayed: {}", message);
    }

    @Then("Verify that error message {string} is displayed")
    public void verifyThatErrorMessageIsDisplayed(String expectedErrorMessage) {
        String actualErrorMessage = getPages().loginPage().getErrorMessage();
        System.out.println("actualErrorMessage = " + actualErrorMessage);
        assertEquals("Verification is failed! Expected error message is not displayed! Actual: " + actualErrorMessage,
                expectedErrorMessage, actualErrorMessage);
        LOG.info("The error message is displayed: {}", expectedErrorMessage);
    }
}
