package selegrid.framework.tests;

import org.assertj.core.api.SoftAssertions;
import selegrid.framework.model.ConfigBean;
import selegrid.framework.pages.AnyPage;
import selegrid.framework.utils.TxtReporter;
import selegrid.framework.utils.testdata.TestData;

import static selegrid.framework.utils.testdata.TestData.getExpectedVisibility;
import static selegrid.framework.utils.testdata.TestData.getExpectedVisibleCount;

public class TestReporter {

  private int task;
  private AnyPage page;
  private TxtReporter reporter;
  private ConfigBean config;
  private SoftAssertions softly = new SoftAssertions();

  TestReporter(int task, ConfigBean config, AnyPage page) {
    this.task = task;
    this.config = config;
    this.reporter = new TxtReporter();
    this.page = page;
  }

  TestReporter checkVisibility(String elementKey) {
    String testTitle = String.format("Check %s visibility", elementKey.replaceAll("_", " "));
    boolean comparisonResult = (page.isElementVisible(elementKey) == getExpectedVisibility(elementKey, config));

    assertAndReport(testTitle, comparisonResult);
    return this;
  }

  TestReporter checkStyling(String elementAttributeKey) {
    String[] keys = elementAttributeKey.split(".css.");
    String testTitle = String.format("Check %s css attribute: %s", keys[0].replaceAll("_", " "), keys[1]);
    boolean comparisonResult = (page.getElementCssAttribute(elementAttributeKey).equalsIgnoreCase(TestData.getExpectedCssAttribute(elementAttributeKey)));

    assertAndReport(testTitle, comparisonResult);
    return this;
  }

  TestReporter checkVisibleTooltipsWhenItemIsHoveredOver(String elementsKey, String elementsCountKey) {
    String testTitle = String.format("Check %s", elementsCountKey.replaceAll("_", " "));
    boolean comparisonResult = (page.visibleElementsCount(elementsKey) == getExpectedVisibleCount(elementsCountKey, config));

    assertAndReport(testTitle, comparisonResult);
    return this;
  }

  TestReporter assertAndReport(String testTitle, boolean comparisonResult) {
    softly.assertThat(reporter.reportVerification(config, task, testTitle, page.getCurrentElementDomId(), comparisonResult));
    return this;
  }

  void tearDown() {
    softly.assertAll();
    reporter.writeReportToFile(task, config);
  }

}
