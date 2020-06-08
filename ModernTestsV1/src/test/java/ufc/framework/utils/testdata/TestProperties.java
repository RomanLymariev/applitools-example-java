package ufc.framework.utils.testdata;

public class TestProperties {

  private static final String BASE_URL_KEY = "base.url";
  private static final String API_KEY = "applitools.api.key";
  private static final String BATCH_KEY = "applitools.batch.name";

  // --- Test data ---
  public static String getUrl() {
    return get(BASE_URL_KEY);
  }

  public static String getApiKey() {
    return get(API_KEY);
  }

  public static String getBatchKey() {
    return get(BATCH_KEY);
  }

  // --- Reusable ----
  private static String get(String varKey) {
    return TestData.getProperties().getProperty(varKey);
  }

}