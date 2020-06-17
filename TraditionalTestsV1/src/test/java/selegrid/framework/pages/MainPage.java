package selegrid.framework.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selegrid.framework.utils.testdata.TestData;

public class MainPage {

  private static WebDriver webDriver;

  private static final By SIDEBAR_FILTERS_BY = By.id("sidebar_filters");
  private static final By FILTER_BUTTON_BY = By.id("filterBtn");
  private static final By SHOW_FILTERS_BUTTON_BY = By.id("ti-filter");
  private static final By PRODUCT_GRID_BY = By.cssSelector("#product_grid");
  private static final By PRODUCT_IMG_BY = By.className("img-fluid");
  private static final By GRID_ITEM_BY = By.className("grid_item");

  private static final String FILTER_BY_TEXT_TEMPLATE = "//label[contains(text(), \"%s\")]";

  private static String currentElementDomId = "not initialized";

  public MainPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

  // --- navigation ---
  public By getProductGridBy() {
    return PRODUCT_GRID_BY;
  }

  public MainPage applySearchFilters(String filter) {
    String[] filters = {filter};
    applySearchFilters(filters);

    return this;
  }

  public MainPage applySearchFilters(String[] filters) {
    openSidebarFiltersIfNeeded();

    for (int i = 0; i < filters.length; i++) {
      applyFilterByText(filters[i]);
    }

    clickApplyFilters();

    return this;
  }

  public MainPage selectGridProduct(int gridPosition) {
    List<WebElement> productsInGrid = webDriver.findElement(PRODUCT_GRID_BY)
        .findElements(PRODUCT_IMG_BY);

    if (gridPosition <= productsInGrid.size()) {
      productsInGrid
          .get(gridPosition)
          .click();
    } else {
      throw new RuntimeException(String.format("Cannot select grid product %s. Grid displays %s products.",
          gridPosition,
          productsInGrid.size()));
    }

    return this;
  }

  // --- actions ---
  public void hoverOverGridItem() {
    Actions action = new Actions(webDriver);
    action.moveToElement(webDriver.findElement(GRID_ITEM_BY))
        .pause(500)
        .build()
        .perform();
  }

  // --- filtering ---
  private void openSidebarFiltersIfNeeded() {
    if (!webDriver.findElement(SIDEBAR_FILTERS_BY).isDisplayed()) {
      webDriver.findElement(SHOW_FILTERS_BUTTON_BY).click();
      new WebDriverWait(webDriver, 10)
          .until(
              ExpectedConditions.visibilityOfElementLocated(SIDEBAR_FILTERS_BY));
    }
  }

  private void applyFilterByText(String text) {
    webDriver
        .findElement(SIDEBAR_FILTERS_BY)
        .findElement(getFilterByText(text))
        .click();
  }

  private void clickApplyFilters() {
    webDriver.findElement(FILTER_BUTTON_BY).click();
  }

  private By getFilterByText(String text) {
    return By.xpath(String.format(FILTER_BY_TEXT_TEMPLATE, text));
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
    currentElementDomId = webDriver.findElement(by).getAttribute("id");
    return (int) webDriver.findElements(by)
        .stream()
        .filter(WebElement::isDisplayed)
        .count();
  }

  private boolean isVisible(By by) {
    boolean isVisible;
    currentElementDomId = "not initialized";
    try {
      isVisible = webDriver.findElement(by).isDisplayed();
      currentElementDomId = getElementIdByBy(by);
    } catch (NotFoundException ex) {
      isVisible = false;
      //uncomment for debugging purpose
      //throw new RuntimeException(ex.getMessage());
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
