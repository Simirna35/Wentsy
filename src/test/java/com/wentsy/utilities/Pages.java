package com.wentsy.utilities;

import com.wentsy.pages.EventsPage;
import com.wentsy.pages.LoginPage;
import com.wentsy.pages.ServicesPage;

public class Pages {
    private final LoginPage loginPage;
    private final EventsPage eventsPage;
    private final ServicesPage servicesPage;

    public Pages(BrowserUtils browserUtils, Driver driver, ConfigurationReader configurationReader) {
        this.loginPage = new LoginPage(browserUtils,driver,configurationReader);
        this.eventsPage = new EventsPage(browserUtils,driver,configurationReader);
        this.servicesPage = new ServicesPage(browserUtils,driver,configurationReader);
    }

    public LoginPage loginPage() {return this.loginPage;}
    public EventsPage eventsPage() {return this.eventsPage;}

    public ServicesPage servicesPage(){return this.servicesPage;}
}
