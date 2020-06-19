package selegrid.framework.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import selegrid.framework.model.ConfigBean;
import selegrid.framework.pages.MainPage;

import static selegrid.framework.utils.WebDriverHelper.initRemoteWebDriver;

public class MainPageTests extends BaseTest {

  private static final String SEARCH_INPUT_KEY = "search_input";
  private static final String SEARCH_PLACEHOLDER_KEY = "search_placeholder_text";
  private static final String START_SEARCH_KEY = "start_search_button";
  private static final String WISHLIST_ICON_KEY = "wishlist_icon";
  private static final String CART_SIZE_KEY = "cart_size";
  private static final String TOOLBOX_OPEN_FILTERS_KEY = "toolbox_open_filters";
  private static final String TOOLBOX_GRID_KEY = "toolbox_grid_view";
  private static final String TOOLBOX_LIST_KEY = "toolbox_list_view";
  private static final String TOOLTIPS_KEY = "item_tooltips";
  private static final String QUICK_LINKS_EXPANDED_KEY = "quick_links_expanded";
  private static final String TOOLTIPS_COUNT_WITH_HOVER = "tooltips_count_with_hover";

  @ParameterizedTest
  @MethodSource("provideConfigurations")
  public void task1_ElementsVisibilityCheck(ConfigBean config) {
    RemoteWebDriver webDriver = initRemoteWebDriver(config);
    new TestReporter(1, config, new MainPage(webDriver));

    TestReporter checker = new TestReporter(1, config, new MainPage(webDriver));
    checker.checkVisibility(SEARCH_INPUT_KEY)
        .checkVisibility(SEARCH_PLACEHOLDER_KEY)
        .checkVisibility(START_SEARCH_KEY)
        .checkVisibility(WISHLIST_ICON_KEY)
        .checkVisibility(CART_SIZE_KEY)
        .checkVisibility(TOOLBOX_OPEN_FILTERS_KEY)
        .checkVisibility(TOOLBOX_GRID_KEY)
        .checkVisibility(TOOLBOX_LIST_KEY)
        .checkVisibility(QUICK_LINKS_EXPANDED_KEY)
        .checkVisibility(TOOLTIPS_KEY);

    //bonus test
    new MainPage(webDriver).hoverOverGridItem();
    checker.checkVisibleTooltipsWhenItemIsHoveredOver(TOOLTIPS_KEY, TOOLTIPS_COUNT_WITH_HOVER)

        .tearDown();
    webDriver.close();
  }

}
