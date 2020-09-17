package utils;

import com.applitools.eyes.MatchLevel;

import java.util.UUID;

public class params {

    //public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    public static String EYES_KEY = System.getenv("APPLITOOLS_API_KEY");

    public static String EYES_URL = "https://eyes.applitools.com";

    public static String GRID_URL = "http://localhost:4444/wd/hub";

    private static String name = "Appltiools";
    private static String suffix = " CI";
    public static Boolean changePage = false;  // to change the content for demo purposes
    public static String APP_NAME = name + suffix;
    public static String TEST_NAME = name + suffix;
    public static String URL_FILE = "src/main/resources/" + name + ".csv";
    public static MatchLevel MATCH_MODE = MatchLevel.LAYOUT;
    public static Boolean FULL_SCREEN = true;
    public static Boolean DISABLE_EYES = false;
    public static int MATCH_TIMEOUT = 1000;

    public static String SAUCE_UN = getSauceUN();
    public static String SAUCE_KEY = getSauceKey();


    public static String BATCH_NAME = getBatchName();
    public static String BATCH_ID = getBatchId();

    private static String getBatchName(){
        String batchName = name + suffix;
        String envName = System.getenv("APPLITOOLS_BATCH_NAME");

        if(envName != null) batchName = envName;

        return batchName;
    }

    public static String getBatchId(){
        String batchId = null;

        if(System.getenv("APPLITOOLS_BATCH_ID") != null) {
            batchId = System.getenv("APPLITOOLS_BATCH_ID");
            System.out.println("Batch Id found in environment: " + batchId);
        } else {
            UUID uuid = java.util.UUID.randomUUID();
            batchId = "CIDemo " + uuid.toString();
            System.out.println("Batch Id generated: " + batchId);
        }

        return batchId;
    }

    public static String getSauceUN(){
        String sauceUN;
        if(System.getenv("SAUCE_UN") != null) {
            sauceUN = System.getenv("SAUCE_UN");
            System.out.println("Sauce UN found in environment: " + sauceUN);
        } else {
            sauceUN = "applitools-dev";
            System.out.println("Sauce UN hardcoded!! : " + sauceUN);
        }
        return sauceUN;
    }

    public static String getSauceKey(){
        String sauceKey;
        if(System.getenv("SAUCE_KEY") != null) {
            sauceKey = System.getenv("SAUCE_KEY");
            System.out.println("Sauce key found in environment: " + sauceKey);
        } else {
            sauceKey = "7f853c17-24c9-4d8f-a679-9cfde5b43951";
            System.out.println("Sauce key hardcoded!! : " + sauceKey);
        }
        return sauceKey;
    }
}

