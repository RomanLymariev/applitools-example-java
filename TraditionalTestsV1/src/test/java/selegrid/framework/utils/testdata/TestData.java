package selegrid.framework.utils.testdata;

import java.net.MalformedURLException;
import java.net.URL;
import selegrid.framework.model.ConfigBean;

public class TestData {

  private static final String BASE_URL_KEY = "base.url";
  private static final String GRID_URL_KEY = "grid.url";
  private static final String REPORT_FILENAME_KEY = "report.filename";

  // --- Test data ---
  public static String getBaseUrl() {
    return get(BASE_URL_KEY);
  }

  public static URL getGridUrl() {
    URL url = null;
    try {
      url = new URL(get(GRID_URL_KEY));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }

  public static String getReportFilename() {
    return get(REPORT_FILENAME_KEY);
  }

  public static boolean getExpectedVisibility(String key, ConfigBean configBean) {
    return Boolean.parseBoolean(get(String.format("%s.%s", key, configBean.getWidth())));
  }

  public static int getExpectedVisibleCount(String key, ConfigBean configBean) {
    return Integer.parseInt(get(String.format("%s.%s", key, configBean.getWidth())));
  }

  public static String getElementXpath(String key) {
    return get(key.concat(".xpath"));
  }

  public static String getElementClassName(String key) {
    return get(key.concat(".class"));
  }

  public static String getElementId(String key) {
    return get(key.concat(".id"));
  }

  public static String getElementCss(String key) {
    return get(key.concat(".css"));
  }

  // --- Reusable ----
  private static String get(String varKey) {
    return TestProperties.getProperties().getProperty(varKey);
  }

}