package utils;

import com.applitools.eyes.MatchLevel;

public class params {

    //public static String EYES_KEY = System.getProperty("eyesAPIKey", "DEFAULT_TOKEN");
    public static String EYES_KEY = System.getenv("APPLITOOLS_API_KEY");

    public static String EYES_URL = "https://eyes.applitools.com";

    public static String GRID_URL = "http://localhost:4444/wd/hub";

    private static String name = "url";
    private static String suffix = " CI";
    public static Boolean changePage = false;  // to change the content for demo purposes
    public static String APP_NAME = name + suffix;
    public static String TEST_NAME = name + suffix;
    public static String URL_FILE = "src/main/resources/" + name + ".csv";
    public static MatchLevel MATCH_MODE = MatchLevel.LAYOUT;
    public static Boolean FULL_SCREEN = true;
    public static Boolean DISABLE_EYES = false;
    public static int MATCH_TIMEOUT = 1000;

    public static String SAUCE_UN = System.getenv("SAUCE_UN");
    public static String SAUCE_KEY = System.getenv("SAUCE_KEY");


    public static String BATCH_NAME = getBatchName();
    public static String BATCH_ID = getBatchId();

    private static String getBatchName(){
        String batchName = name + suffix;
        String envName = System.getenv("APPLITOOLS_BATCH_NAME");

        if(envName != null) batchName = envName;

        return batchName;
    }

    private static String getBatchId(){
        String batchId = null;

        if(System.getenv("APPLITOOLS_BATCH_ID") != null) {
            batchId = System.getenv("APPLITOOLS_BATCH_ID");
            System.out.println("Batch Id found in environment: " + batchId.toString());
        }

        return batchId;
    }
}

