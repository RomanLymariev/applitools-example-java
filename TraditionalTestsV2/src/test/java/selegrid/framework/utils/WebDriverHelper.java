package selegrid.framework.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import selegrid.framework.model.ConfigBean;
import selegrid.framework.utils.testdata.TestData;

public class WebDriverHelper {
  public static RemoteWebDriver initRemoteWebDriver(ConfigBean config) {
    RemoteWebDriver webDriver = new RemoteWebDriver(
        TestData.getGridUrl(),
        getDesiredCapabilities(config));

    webDriver.get(TestData.getBaseUrl());
    webDriver.manage().window().setSize(new Dimension(config.getWidth(), config.getHeight()));

    return webDriver;
  }

  private static DesiredCapabilities getDesiredCapabilities(ConfigBean browserConfig) {
    DesiredCapabilities capability = new DesiredCapabilities();
    capability.setBrowserName(browserConfig.getBrowser());
    return capability;
  }
}
