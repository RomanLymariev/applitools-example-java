package ufc.framework.tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ufc.framework.utils.testdata.TestData;
import ufc.framework.utils.testdata.TestProperties;

public class AppTest {

  private static Eyes eyes;
  private static WebDriver webDriver;
  private static VisualGridRunner runner;

  @BeforeClass
  public static void setUp() {
    runner = new VisualGridRunner(6);
    webDriver = new ChromeDriver();
    eyes = new Eyes(runner);

    Configuration config = new Configuration();
    config.setApiKey(TestProperties.getApiKey());
    config.setBatch(new BatchInfo(TestProperties.getBatchKey()));
    loadBrowsersAndDevicesConfigurations(config);

    eyes.setConfiguration(config);
  }

  @Test()
  public void responsiveDesignTest() {
    webDriver.get(TestProperties.getUrl());
    eyes.open(webDriver, "Cross-Device Elements Test", "Test 1");
    eyes.check(Target.window().fully());
    eyes.closeAsync();
  }

  @AfterClass
  public static void tearDown() {
    // Close the browser
    webDriver.quit();

    // we pass false to this method to suppress the exception that is thrown if we find visual differences
    TestResultsSummary allTestResults = runner.getAllTestResults(false);
    System.out.println(allTestResults);
  }

  // ---
  private static void loadBrowsersAndDevicesConfigurations(Configuration config) {
    TestData.getEyesConfigurations().forEach(eyesConfig ->
    {
      if (eyesConfig.getDeviceName() == null) {
        config.addBrowser(eyesConfig.getWidth(), eyesConfig.getHeight(), eyesConfig.getBrowserType());
      } else {
        config.addDeviceEmulation(eyesConfig.getDeviceName());
      }
    });
  }

}

