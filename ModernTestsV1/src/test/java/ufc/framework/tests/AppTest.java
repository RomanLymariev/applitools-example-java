package ufc.framework.tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ufc.framework.pages.MainPage;
import ufc.framework.utils.testdata.TestData;
import ufc.framework.utils.testdata.TestProperties;

public class AppTest {

  private static Eyes eyes;
  private static WebDriver webDriver;
  private static VisualGridRunner runner;

  private static final String SEARCH_CRITERIA_TEXT = "Black";

  @BeforeAll
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

  @Test
  public void task1_responsiveDesignTest() {
    webDriver.get(TestProperties.getUrl());
    eyes.open(webDriver, "Cross-Device Elements Test", "Task 1");

    eyes.checkWindow();
  }

  @Test
  public void task2_shoppingExperienceTest() {
    webDriver.get(TestProperties.getUrl());
    eyes.open(webDriver, "Filter Results", "Task 2");

    MainPage mainPage = new MainPage(webDriver);
    mainPage.applySearchFilters(SEARCH_CRITERIA_TEXT);

    eyes.checkElement(mainPage.getProductGridBy());
  }

  @Test
  public void task3_productDetailsTest() {
    webDriver.get(TestProperties.getUrl());
    eyes.open(webDriver, "Product Details", "Task 3");

    new MainPage(webDriver)
        .applySearchFilters(SEARCH_CRITERIA_TEXT)
        .selectGridProduct(0);

    eyes.checkWindow();
  }

  @AfterEach
  public void closeEyesAsync() {
    eyes.closeAsync();
  }

  @AfterAll
  public static void tearDown() {
    webDriver.quit();
    logTestResults();
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

  private static void logTestResults() {
    System.out.println(runner.getAllTestResults(false));
  }

}

