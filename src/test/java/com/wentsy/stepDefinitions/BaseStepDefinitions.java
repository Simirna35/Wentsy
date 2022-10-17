package com.wentsy.stepDefinitions;

import com.wentsy.utilities.BrowserUtils;
import com.wentsy.utilities.Driver;
import com.wentsy.utilities.Pages;

public class BaseStepDefinitions {
    private final BrowserUtils browserUtils;
    private final Driver driver;
    private final Pages pages;

    public BaseStepDefinitions(BrowserUtils browserUtils, Driver driver, Pages pages) {
        this.browserUtils = browserUtils;
        this.driver = driver;
        this.pages = pages;
    }

    public BrowserUtils getBrowserUtils(){return this.browserUtils;}

    public Driver getDriver(){return this.driver;}

    public Pages getPages(){return this.pages;}
}
