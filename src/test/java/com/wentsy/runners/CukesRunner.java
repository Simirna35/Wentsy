package com.wentsy.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/default-html-reports.html",
                "com.wentsy.utilities.formatters.PrettyReports:target/cucumber-pretty-reports"
        },
        features = "src/test/resources/features/",
        glue = "com/wentsy/stepDefinitions",
        dryRun = false,
        tags = "@positiveLogin"
)
public class CukesRunner {
}
