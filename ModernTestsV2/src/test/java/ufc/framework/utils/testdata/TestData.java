package ufc.framework.utils.testdata;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import ufc.framework.model.EyesConfigBean;

public class TestData {

  private static final String TEST_PROPS_FILEPATH = "testdata.properties";
  private static final String EYES_CONFIGS_FILEPATH = "testdata/configurations.csv";
  private static Properties props = new Properties();

  public static Properties getProperties() {
    if (props.isEmpty()) {
      try {
        props.load(ResourceHelper.getResourceAsStream(TEST_PROPS_FILEPATH));
      } catch (IOException ex) {
        throw new RuntimeException(ex.getMessage());
      }
    }
    return props;
  }

  public static List<EyesConfigBean> getEyesConfigurations() {
    return CsvHelper.beanBuilder(EYES_CONFIGS_FILEPATH, EyesConfigBean.class);
  }

}
