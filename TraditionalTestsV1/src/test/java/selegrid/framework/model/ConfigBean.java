package selegrid.framework.model;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;

public class ConfigBean implements Serializable {

  @CsvBindByName(column = "Browser")
  private String browser;

  @CsvBindByName(column = "Width")
  private int width;

  @CsvBindByName(column = "Height")
  private int height;

  @CsvBindByName(column = "Device")
  private String device;

  @Override
  public String toString() {
    return String.format("Config{%s %s %s}", browser, getViewport(), device);
  }

  public String getBrowser() {
    return this.browser;
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

  public String getViewport() {return String.format("%sx%s", width, height);}

}
