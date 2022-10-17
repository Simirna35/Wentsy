package com.wentsy.utilities;

import com.wentsy.pages.*;

public class Pages {
    private final LoginPage loginPage;
    private final EventsPage eventsPage;
    private final ServicesPage servicesPage;

    private final EntertainementPage entertainementPage;

    private final VenuePage venuePage;


    public Pages(BrowserUtils browserUtils, Driver driver, ConfigurationReader configurationReader) {
        this.loginPage = new LoginPage(browserUtils,driver,configurationReader);
        this.eventsPage = new EventsPage(browserUtils,driver,configurationReader);
        this.servicesPage = new ServicesPage(browserUtils,driver,configurationReader);
        this.entertainementPage = new EntertainementPage(browserUtils,driver,configurationReader);
        this.venuePage = new VenuePage(browserUtils,driver,configurationReader);



    }

    public LoginPage loginPage() {return this.loginPage;}
    public EventsPage eventsPage() {return this.eventsPage;}

    public ServicesPage servicesPage(){return this.servicesPage;}

    public EntertainementPage entertainementPage(){return this.entertainementPage; }

    public VenuePage venuePage () {return this.venuePage;}

}
