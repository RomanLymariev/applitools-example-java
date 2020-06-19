package selegrid.framework.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import selegrid.framework.model.ConfigBean;
import selegrid.framework.pages.MainPage;
import selegrid.framework.pages.ProductDetailsPage;

import static selegrid.framework.utils.WebDriverHelper.initRemoteWebDriver;

public class ShoppingTests extends BaseTest {

  private static final String SEARCH_CRITERIA_TEXT = "Black";
  private static final int EXPECTED_ITEM_COUNT = 2;

  private static final String SKU_KEY = "sku";
  private static final String CORRECT_NEW_PRICE_KEY = "correct_new_price";
  private static final String VALID_RATING_KEY = "valid_rating";
  private static final String INVALID_RATING_KEY = "invalid_rating";
  private static final String CORRECT_SMALL_SIZE_OPTION_KEY = "small_size_option";

  private static final String OLD_PRICE_DECORATION_LINE_KEY = "old_price.css.text-decoration-line";
  private static final String OLD_PRICE_DECORATION_COLOR_KEY = "old_price.css.text-decoration-color";

  @ParameterizedTest
  @MethodSource("provideConfigurations")
  public void task2_shoppingTest(ConfigBean config) {
    RemoteWebDriver webDriver = initRemoteWebDriver(config);
    MainPage mainPage = new MainPage(webDriver);
    int actualCount = mainPage.applySearchFilters(SEARCH_CRITERIA_TEXT).getGridElementsCount();

    new TestReporter(2, config, mainPage).assertAndReport
        (
            String.format("Filter by '%s'. Ensure %s products are displayed", SEARCH_CRITERIA_TEXT, EXPECTED_ITEM_COUNT),
            (actualCount == EXPECTED_ITEM_COUNT)
        )
        .tearDown();

    webDriver.close();
  }

  @ParameterizedTest
  @MethodSource("provideConfigurations")
  public void task3_productDetailsTest(ConfigBean config) {
    RemoteWebDriver webDriver = initRemoteWebDriver(config);

    ProductDetailsPage pdp = new MainPage(webDriver)
        .applySearchFilters(SEARCH_CRITERIA_TEXT)
        .selectGridProductByPosition(0);

    TestReporter checker = new TestReporter(3, config, pdp);
    checker.checkVisibility(SKU_KEY)
        .checkVisibility(CORRECT_NEW_PRICE_KEY)
        .checkVisibility(VALID_RATING_KEY)
        .checkVisibility(CORRECT_SMALL_SIZE_OPTION_KEY)

        .checkStyling(OLD_PRICE_DECORATION_LINE_KEY)
        .checkStyling(OLD_PRICE_DECORATION_COLOR_KEY)

        //V2
        .checkVisibility(INVALID_RATING_KEY)

        .tearDown();
    webDriver.close();
  }

}
