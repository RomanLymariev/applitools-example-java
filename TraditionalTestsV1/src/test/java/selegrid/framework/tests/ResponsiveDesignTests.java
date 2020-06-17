package selegrid.framework.tests;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import selegrid.framework.model.ConfigBean;
import selegrid.framework.pages.MainPage;
import selegrid.framework.utils.TxtReporter;
import selegrid.framework.utils.testdata.TestData;
import selegrid.framework.utils.testdata.TestProperties;

import static selegrid.framework.utils.WebDriverHelper.initRemoteWebDriver;
import static selegrid.framework.utils.testdata.TestData.getExpectedVisibility;
import static selegrid.framework.utils.testdata.TestData.getExpectedVisibleCount;

public class ResponsiveDesignTests {

  private static final String SEARCH_INPUT_KEY = "search_input";
  private static final String SEARCH_PLACEHOLDER_KEY = "search_placeholder_text";
  private static final String START_SEARCH_KEY = "start_search_button";
  private static final String WISHLIST_ICON_KEY = "wishlist_icon";
  private static final String CART_SIZE_KEY = "cart_size";
  private static final String TOOLBOX_OPEN_FILTERS_KEY = "toolbox_open_filters";
  private static final String TOOLBOX_GRID_KEY = "toolbox_grid_view";
  private static final String TOOLBOX_LIST_KEY = "toolbox_list_view";
  private static final String TOOLTIPS_KEY = "item_tooltips";
  private static final String QUICK_LINKS_EXPANDED_KEY = "quick_links_expanded";
  private static final String TOOLTIPS_COUNT_WITH_HOVER = "tooltips_count_with_hover";

  private static List<ConfigBean> provideConfigurations() {
    return TestProperties.getTestConfigurations();
  }

  @BeforeAll
  public static void cleanReports() {
    TxtReporter.removeReports();
  }

  @ParameterizedTest
  @MethodSource("provideConfigurations")
  public void task1_ElementsVisibilityCheck(ConfigBean config) {
    RemoteWebDriver webDriver = initRemoteWebDriver(config);
    VisibilityChecker checker = new VisibilityChecker(config, new MainPage(webDriver));

    checker.checkElementVisibilityAndReport(SEARCH_INPUT_KEY);
    checker.checkElementVisibilityAndReport(SEARCH_PLACEHOLDER_KEY);
    checker.checkElementVisibilityAndReport(START_SEARCH_KEY);
    checker.checkElementVisibilityAndReport(WISHLIST_ICON_KEY);
    checker.checkElementVisibilityAndReport(CART_SIZE_KEY);
    checker.checkElementVisibilityAndReport(TOOLBOX_OPEN_FILTERS_KEY);
    checker.checkElementVisibilityAndReport(TOOLBOX_GRID_KEY);
    checker.checkElementVisibilityAndReport(TOOLBOX_LIST_KEY);
    checker.checkElementVisibilityAndReport(QUICK_LINKS_EXPANDED_KEY);
    checker.checkElementVisibilityAndReport(TOOLTIPS_KEY);

    //bonus test
    checker.checkVisibleTooltipsWhenItemIsHoveredOver();

    // --- V2 ----
    //checker.checkElementVisibilityAndReport(SIDEBAR_FILTERS_KEY);

    checker.tearDown();
    webDriver.close();
  }

  @AfterAll
  public static void consolidateReport() {
    TxtReporter.consolidateReports(TestData.getReportFilename());
  }

  class VisibilityChecker {

    private final int TASK = 1;
    private MainPage mainPage;
    private TxtReporter reporter;
    private ConfigBean config;
    private SoftAssertions softly = new SoftAssertions();

    VisibilityChecker(ConfigBean config, MainPage mainPage) {
      this.config = config;
      this.reporter = new TxtReporter();
      this.mainPage = mainPage;
    }

    void checkElementVisibilityAndReport(String elementKey) {
      String testTitle = String.format("Check %s visibility", elementKey.replaceAll("_", " "));
      boolean actualVisibility = mainPage.isElementVisible(elementKey);
      String currentElementDomId = mainPage.getCurrentElementDomId();
      boolean expectedVisibility = getExpectedVisibility(elementKey, config);

      boolean comparisonResult = (actualVisibility == expectedVisibility);

      softly.assertThat(reporter.reportVerification(config, TASK, testTitle, currentElementDomId, comparisonResult));
    }

    void checkVisibleTooltipsWhenItemIsHoveredOver() {
      String testTitle = String.format("Check %s", TOOLTIPS_COUNT_WITH_HOVER.replaceAll("_", " "));
      String currentElementDomId = mainPage.getCurrentElementDomId();
      int expectedVisibleCount = getExpectedVisibleCount(TOOLTIPS_COUNT_WITH_HOVER, config);

      mainPage.hoverOverGridItem();
      int actualVisibleCount = mainPage.visibleElementsCount(TOOLTIPS_KEY);
      boolean comparisonResult = (actualVisibleCount == expectedVisibleCount);

      softly.assertThat(reporter.reportVerification(config, TASK, testTitle, currentElementDomId, comparisonResult));
    }

    void tearDown() {
      softly.assertAll();
      reporter.writeReportToFile(config);
    }

  }

}
