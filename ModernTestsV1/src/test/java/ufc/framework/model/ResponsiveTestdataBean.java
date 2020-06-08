package ufc.framework.model;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;

public class ResponsiveTestdataBean implements Serializable {

  @CsvBindByName(column = "Test name")
  private String testName;

  @CsvBindByName(column = "Element locator")
  private String elementLocator;

  @CsvBindByName(column = "Strategy")
  private String locatorStrategy;

  public String getTestName() {
    return testName;
  }

  public String getElementLocator() {
    return elementLocator;
  }

  public String getLocatorStrategy() {
    return locatorStrategy;
  }

}
