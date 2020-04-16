import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.FileLogger;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.params;

import java.util.concurrent.TimeUnit;

public class Sauce {

    protected RemoteWebDriver driver;
    protected Eyes eyes;

    private static final String BATCH_NAME = params.BATCH_NAME;
    private static final String BATCH_ID = params.BATCH_ID;
    private static final String APP_NAME = params.APP_NAME;

    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @Test(priority = 1, alwaysRun = true, enabled = true)
    public void CheckURL(String platformName ,String platformVersion,
                         String browserName, String browserVersion) {

        Integer i=0;
        String testName = params.TEST_NAME;

        eyes.setMatchLevel(params.MATCH_MODE);
        eyes.setStitchMode(StitchMode.CSS);
        eyes.setSendDom(true);
        eyes.open(driver,APP_NAME, testName, new RectangleSize(1200, 600));

        tests.urlscan.scanlist(driver, eyes, params.URL_FILE);

        TestResults testResult = eyes.close(false);
        System.out.println("Applitools Test Results");
        System.out.println(testResult.toString());
    }


    @Parameters({"platformName", "platformVersion", "browserName", "browserVersion"})
    @BeforeClass(alwaysRun = true)
    public void baseBeforeClass(String platformName ,String platformVersion,
                                String browserName, String browserVersion) {

        String threadId = Long.toString(Thread.currentThread().getId());
        long before = System.currentTimeMillis();

        eyes = utils.myeyes.getEyes(threadId);
        eyes.setLogHandler(new FileLogger("log/Eyes_LC.log",true,true));
        eyes.setServerUrl(params.EYES_URL);

        BatchInfo batchInfo = new BatchInfo(BATCH_NAME);
        if(BATCH_ID!=null) batchInfo.setId(BATCH_ID);

        eyes.setBatch(batchInfo);

        driver = utils.drivers.sauce();
        driver.manage().timeouts().setScriptTimeout(90, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(90, TimeUnit.SECONDS);

        System.out.println("START THREAD ID - " + Thread.currentThread().getId() + " " + browserName + " " + browserVersion);
        System.out.println("baseBeforeClass took " + (System.currentTimeMillis() - before) + "ms");
    }

    @AfterClass(alwaysRun = true)
    public void baseAfterClass() {

        if (driver != null) {
            long before = System.currentTimeMillis();
            if(!eyes.getIsOpen()) eyes.abort();
            driver.quit();
            System.out.println("Driver quit took " + (System.currentTimeMillis() - before) + "ms");
        }


    }
}
