package selegrid.framework.tests;

import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import selegrid.framework.model.ConfigBean;
import selegrid.framework.utils.TxtReporter;
import selegrid.framework.utils.testdata.TestData;
import selegrid.framework.utils.testdata.TestProperties;

public class BaseTest {

  private static boolean runOnce = false;

  @BeforeAll
  public static void cleanReports() {
    if (!runOnce) {
      runOnce = true;
      TxtReporter.cleanConsolidatedReport();
      TxtReporter.removeTemporaryReports();
    }
  }

  @AfterAll
  public static void consolidateReport() {
    TxtReporter.consolidateReports(TestData.getReportFilename());
  }

  protected static List<ConfigBean> provideConfigurations() {
    return TestProperties.getTestConfigurations();
  }

}
