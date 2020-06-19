package ufc.framework.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

  private static WebDriver webDriver;

  private static final By SIDEBAR_FILTERS_BY = By.id("sidebar_filters");
  private static final By FILTER_BUTTON_BY = By.id("filterBtn");
  private static final By SHOW_FILTERS_BUTTON_BY = By.id("ti-filter");
  private static final By PRODUCT_GRID_BY = By.cssSelector("#product_grid");
  private static final By PRODUCT_IMG_BY = By.className("img-fluid");

  private static final String FILTER_BY_TEXT_TEMPLATE = "//label[contains(text(), \"%s\")]";

  public MainPage(WebDriver webDriver) {
    this.webDriver = webDriver;
  }

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

    if (gridPosition <= productsInGrid.size() ) {
      productsInGrid
          .get(gridPosition)
          .click();
    }
      else
        throw new RuntimeException(String.format("Cannot select grid product %s. Grid displays %s products.",
            gridPosition,
            productsInGrid.size()));

    return this;
  }

  // ---
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
