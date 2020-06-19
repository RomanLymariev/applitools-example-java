package selegrid.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import selegrid.framework.utils.testdata.TestData;

public abstract class AnyPage {

  protected WebDriver webDriver;
  protected String currentElementDomId;

  protected AnyPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  // --- elements visibility and attributes ---
  public String getCurrentElementDomId() {
    return currentElementDomId;
  }

  public boolean isElementVisible(String elementKey) {
    return isVisible(getElementByByKey(elementKey));
  }

  public int visibleElementsCount(String elementKey) {
    By by = getElementByByKey(elementKey);
    currentElementDomId = getElementIdByBy(by);
    return (int) webDriver.findElements(by)
        .stream()
        .filter(WebElement::isDisplayed)
        .count();
  }

  //elements css attributes
  public String getElementCssAttribute(String elementCssAttrKey) {
    String[] keys = elementCssAttrKey.split(".css.");
    By elementBy = getElementByByKey(keys[0]);
    String cssAttribute = keys[1];

    return webDriver.findElement(elementBy).getCssValue(cssAttribute);
  }

  private boolean isVisible(By by) {
    boolean isVisible;
    currentElementDomId = "n/a";
    try {
      isVisible = webDriver.findElement(by).isDisplayed();
      currentElementDomId = getElementIdByBy(by);
    } catch (NotFoundException ex) {
      isVisible = false;
    }
    return isVisible;
  }

  private By getElementByByKey(String elementKey) {
    String xpath = TestData.getElementXpath(elementKey);
    String className = TestData.getElementClassName(elementKey);
    String id = TestData.getElementId(elementKey);

    if (xpath != null) {
      return By.xpath(xpath);
    } else if (className != null) {
      return By.className(className);
    } else if (id != null) {
      return By.id(id);
    } else {
      throw new RuntimeException(
          String.format("Element locator cannot be identified: %s. Please check available strategies and testdata.properties.", elementKey)
      );
    }
  }

  private String getElementIdByBy(By by) {
    return webDriver.findElement(by).getAttribute("id");
  }

}
