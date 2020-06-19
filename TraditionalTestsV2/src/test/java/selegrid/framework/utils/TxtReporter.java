package selegrid.framework.utils;

import java.util.ArrayList;
import selegrid.framework.model.ConfigBean;
import selegrid.framework.utils.testdata.TestData;

public class TxtReporter {

  private static final String TEST_REPORT_FILENAME_TEMPLATE = "task%s_%s_%sx%s_results.txt";
  private static final String STRING_TEMPLATE =
      "Task: %s, Test Name: %s, DOM Id: %s, Viewport: %s, Browser: %s, Device: %s, Status: %s";

  private ArrayList<String> testReport = new ArrayList<String>();

  public TxtReporter() {
  }

  public boolean reportVerification(ConfigBean config, int task, String testName, String domId, boolean comparisonResult) {
    this.testReport.add(String.format(
        STRING_TEMPLATE, task, testName, domId, config.getViewport(), config.getBrowser(), config.getDevice(), comparisonResult ? "Pass" : "Fail"
    ));
    //returns the result so that it can be used for further Assertions in the test code.
    return comparisonResult;
  }

  public void writeReportToFile(int task, ConfigBean config) {
    String filename = String.format(TEST_REPORT_FILENAME_TEMPLATE, task, config.getBrowser(), config.getWidth(), config.getHeight());
    FileHelper.writeToFile(this.testReport, filename);
  }

  public static void removeTemporaryReports() {
    FileHelper.deleteFiles("*_results.txt");
  }

  public static void consolidateReports(String resultFile) {
    FileHelper.consolidateFilesToOne(resultFile, "*_results.txt");
    removeTemporaryReports();
  }

  public static void cleanConsolidatedReport() {
    FileHelper.deleteFiles(TestData.getReportFilename());
  }

}
