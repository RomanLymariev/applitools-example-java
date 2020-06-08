package ufc.framework.model;

import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;

public class EyesConfigBean implements Serializable {
  @CsvBindByName(column = "Browser")
  private BrowserType browserType;

  @CsvBindByName(column = "Width")
  private int width;

  @CsvBindByName(column = "Height")
  private int height;

  @CsvBindByName(column = "Device")
  private String device;

  @CsvBindByName(column = "DeviceName")
  private DeviceName deviceName;

  public BrowserType getBrowserType() {
    return this.browserType;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getDevice() {
    return device;
  }

  public DeviceName getDeviceName() {
      return deviceName;
  }

}
