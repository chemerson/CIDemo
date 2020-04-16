package utils;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class drivers {

    static Map<String,ChromeDriver> chromeDrivers = new Hashtable<String,ChromeDriver>();
    static Map<String,RemoteWebDriver> remoteWebDrivers = new Hashtable<String,RemoteWebDriver>();

    public static RemoteWebDriver sauce() {

        RemoteWebDriver driver = null;

        String sauceURL = "https://ondemand.saucelabs.com/wd/hub";

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", params.SAUCE_UN);
        sauceOpts.setCapability("accessKey", params.SAUCE_KEY);
        sauceOpts.setCapability("seleniumVersion", "3.141.59");
        sauceOpts.setCapability("name", "url scan");
        sauceOpts.setCapability("screenResolution", "2560x1600");


        List<String> tags = Arrays.asList("sauceDemo", "demoTest", "module4", "javaTest");
        sauceOpts.setCapability("tags", tags);
        sauceOpts.setCapability("maxDuration", 3600);
        sauceOpts.setCapability("commandTimeout", 600);
        sauceOpts.setCapability("idleTimeout", 1000);
        sauceOpts.setCapability("build", "Jenkins/Sauce Demo (CME)");

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setExperimentalOption("w3c", true);

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("sauce:options", sauceOpts);
        capabilities.setCapability("goog:chromeOptions", chromeOpts);
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("platformVersion", "Windows 10");
        capabilities.setCapability("browserVersion", "latest");

        try {
            driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return driver;

    }

    public static ChromeDriver getLocalChrome(String threadId){

        if (chromeDrivers == null || !chromeDrivers.containsKey(threadId)) {

            ChromeOptions cOptions = new ChromeOptions();
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");  //work for Travis, commenting for Jenkins

            cOptions.addArguments("--headless");
            cOptions.addArguments("--disable-popup-blocking");
            cOptions.addArguments("--disable-default-apps");
            cOptions.addArguments("--start-maximized");
            cOptions.addArguments("--disable-infobars");
            cOptions.addArguments("–-disable-notifications");
            cOptions.addArguments("--dom-automation");

            ChromeDriver driver = new ChromeDriver(cOptions);
            chromeDrivers.put(threadId, driver);
        }
        return chromeDrivers.get(threadId);
    }

    public static RemoteWebDriver getGrid(String threadId, String browserName){

        RemoteWebDriver driver = null;

        if (remoteWebDrivers == null || !remoteWebDrivers.containsKey(threadId)) {

            if(browserName.equalsIgnoreCase("CHROME")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-default-apps");
                options.addArguments("--disable-infobars");
                options.addArguments("–-disable-notifications");
                //options.addArguments("--dom-automation");
                options.setCapability(CapabilityType.BROWSER_NAME, "chrome");
                options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                try {
                    driver = new RemoteWebDriver(new URL(params.GRID_URL), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            if(browserName.equalsIgnoreCase("FIREFOX")) {
                FirefoxOptions options = new FirefoxOptions();
                options.setCapability(CapabilityType.BROWSER_NAME, "firefox");
                options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
                try {
                    driver = new RemoteWebDriver(new URL(params.GRID_URL), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            remoteWebDrivers.put(threadId, driver);
        }
        return remoteWebDrivers.get(threadId);
    }
}
