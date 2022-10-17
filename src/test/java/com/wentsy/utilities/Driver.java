package com.wentsy.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Driver {
    private final ConfigurationReader configurationReader;
    private final RemoteWebDriver remoteWebDriver;

    public Driver(ConfigurationReader configurationReader) {
        this.configurationReader = configurationReader;
        this.remoteWebDriver = createRemoteWebDriver();
    }

    private static final InheritableThreadLocal<RemoteWebDriver> driverPool = new InheritableThreadLocal<>();

    public RemoteWebDriver get() {
        return this.remoteWebDriver;
    }

    public RemoteWebDriver createRemoteWebDriver() {
        if (driverPool.get() == null) {
            String browser = this.configurationReader.getProperty("browser");
            switch (browser) {
                case "chrome":
                    driverPool.set(createChromeDriver(false));
                    break;
                case "chrome-headless":
                    driverPool.set(createChromeDriver(true));
                    break;
                case "remote-chrome-win":
                    driverPool.set(createRemoteWebDriver(new ChromeOptions(), Platform.WINDOWS, "chrome"));
                    break;
                case "remote-chrome-linux":
                    driverPool.set(createRemoteWebDriver(new ChromeOptions(), Platform.LINUX, "chrome"));
                    break;
                case "firefox":
                    driverPool.set(createFirefoxDriver(false));
                    break;
                case "firefox-headless":
                    driverPool.set(createFirefoxDriver(true));
                    break;
                case "remote-firefox-linux":
                    driverPool.set(createRemoteWebDriver(new FirefoxOptions(), Platform.LINUX, "firefox"));
                    break;
                case "remote-firefox-win":
                    driverPool.set(createRemoteWebDriver(new FirefoxOptions(), Platform.WINDOWS, "firefox"));
                    break;
                case "ie":
                    driverPool.set(createIeDriver());
                    break;
                case "edge":
                    driverPool.set(createEdgeDriver());
                    break;
                case "safari":
                    driverPool.set(createSafariDriver());
                    break;
                case "remote-safari":
                    driverPool.set(createRemoteWebDriver(new SafariOptions(), Platform.LINUX, "safari"));
                    break;
                default:
                    throw new RuntimeException("Unknown driver for " + browser + " browser");
            }
        }
        return driverPool.get();
    }

    private RemoteWebDriver createChromeDriver(boolean isHeadless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", setChromeSettings());

        WebDriverManager.chromedriver().setup();
        if (isHeadless) {
            return new ChromeDriver(chromeOptions.setHeadless(true).addArguments("window-size=1920,1080"));
        }
        try {
            return new ChromeDriver(chromeOptions);
        } catch (WebDriverException e) {
            return new ChromeDriver(chromeOptions);
        }
    }

    private RemoteWebDriver createFirefoxDriver(boolean isHeadless) {
        WebDriverManager.firefoxdriver().setup();
        if (isHeadless) {
            return new FirefoxDriver(new FirefoxOptions().setHeadless(true).addArguments("window-size=1920,1080"));
        }
        try {
            return new FirefoxDriver();
        } catch (WebDriverException e) {
            return new ChromeDriver();
        }
    }

    private RemoteWebDriver createRemoteWebDriver(MutableCapabilities options, Platform platform, String browser) {
        URL url = tryBuildUrl(this.configurationReader.getProperty("selenium.grid.url"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setPlatform(platform);
        capabilities.setBrowserName(browser);
        capabilities.setCapability("headless", true);
        options.merge(capabilities);
        try {
            return new RemoteWebDriver(url, options);
        } catch (WebDriverException e) {
            return new RemoteWebDriver(url, options);
        }
    }

    private RemoteWebDriver createSafariDriver() {
        checkOSType("mac", SafariDriver.class);
        WebDriverManager.getInstance(SafariDriver.class).setup();
        try {
            return new SafariDriver();
        } catch (WebDriverException e) {
            return new ChromeDriver();
        }

    }

    private RemoteWebDriver createEdgeDriver() {
        checkOSType("windows", EdgeDriver.class);
        WebDriverManager.edgedriver().setup();
        try {
            return new EdgeDriver();
        } catch (WebDriverException e) {
            return new ChromeDriver();
        }
    }

    private RemoteWebDriver createIeDriver() {
        checkOSType("windows", InternetExplorerDriver.class);
        WebDriverManager.iedriver().setup();
        try {
            return new InternetExplorerDriver();
        } catch (WebDriverException e) {
            return new ChromeDriver();
        }
    }

    private URL tryBuildUrl(String value) {
        try {
            return new URL(value);
        } catch (MalformedURLException exc) {
            throw new RuntimeException("Unable to build url with: \"" + value + "\"", exc);
        }
    }

    private <T extends RemoteWebDriver> void checkOSType(String restrictedOs, Class<T> wantedDriver) {
        String os = this.configurationReader.getProperty("os.name");
        if (!os.toLowerCase().contains(restrictedOs))
            throw new WebDriverException("Unable to use " + wantedDriver.getSimpleName() + " with a " + os + " operating system");
    }

    private Map<String, Object> setChromeSettings() {
        Map<String, Object> prefs = new HashMap<>();
        Map<String, Object> profile = new HashMap<>();
        Map<String, Object> contentSettings = new HashMap<>();

        contentSettings.put("media_stream", 1);
        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);

        return prefs;
    }

    public void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }
}
