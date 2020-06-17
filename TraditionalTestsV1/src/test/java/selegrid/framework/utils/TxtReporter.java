package selegrid.framework.utils;

import java.util.ArrayList;
import selegrid.framework.model.ConfigBean;

public class TxtReporter {

  private static final String TEST_REPORT_FILENAME_TEMPLATE = "%s_%sx%s_results.txt";
  private static final String STRING_TEMPLATE =
      "Task: %s, Test Name: %s, DOM Id: %s, Browser: %s, Viewport: %s, Device: %s, Status: %s";

  private ArrayList<String> testReport = new ArrayList<String>();

  public TxtReporter() {
  }

  public boolean reportVerification(ConfigBean config, int task, String testName, String domId, boolean comparisonResult) {
    this.testReport.add(String.format(
        STRING_TEMPLATE, task, testName, domId, config.getBrowser(), config.getViewport(), config.getDevice(), comparisonResult ? "Pass" : "Fail"
    ));
    //returns the result so that it can be used for further Assertions in the test code.
    return comparisonResult;
  }

  public void writeReportToFile(ConfigBean config) {
    String filename = String.format(TEST_REPORT_FILENAME_TEMPLATE, config.getBrowser(), config.getWidth(), config.getHeight());
    FileHelper.writeToFile(this.testReport, filename);
  }

  public static void removeReports() {
    FileHelper.deleteFiles("", "*_results.txt");
  }

  public static void consolidateReports(String resultFile) {
    FileHelper.consolidateFilesToOne("", resultFile, "*_results.txt");
  }

}
