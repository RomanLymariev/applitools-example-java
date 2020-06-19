package selegrid.framework.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage extends AnyPage {

  private static final By SIDEBAR_FILTERS_BY = By.id("sidebar_filters");
  private static final By FILTER_BUTTON_BY = By.id("filterBtn");
  private static final By SHOW_FILTERS_BUTTON_BY = By.id("ti-filter");
  private static final By PRODUCT_GRID_BY = By.cssSelector("#product_grid");
  private static final By PRODUCT_IMG_BY = By.className("img-fluid");
  private static final By GRID_ITEM_BY = By.className("grid_item");

  private static final String FILTER_BY_TEXT_TEMPLATE = "//label[contains(text(), \"%s\")]";

  public MainPage(WebDriver webDriver) {
    super(webDriver);
  }

  // --- navigation ---
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

  public ProductDetailsPage selectGridProductByPosition(int gridPosition) {
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
    return new ProductDetailsPage(webDriver);
  }

  public int getGridElementsCount() {
    return webDriver.findElements(GRID_ITEM_BY).size();
  }

  // --- actions ---
  public MainPage hoverOverGridItem() {
    Actions action = new Actions(webDriver);
    action.moveToElement(webDriver.findElement(GRID_ITEM_BY))
        .pause(500)
        .build()
        .perform();
    return this;
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

}
