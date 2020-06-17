package selegrid.framework.utils.testdata;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import java.util.List;

public class CsvHelper {

  public static <T> List<T> beanBuilder(String relativeResourcePath, Class expectedBeanClass) {
    HeaderColumnNameMappingStrategy ms = new HeaderColumnNameMappingStrategy();
    ms.setType(expectedBeanClass);

    CsvToBean cb = new CsvToBeanBuilder(ResourceHelper.getResourceFileReader(relativeResourcePath))
        .withType(expectedBeanClass)
        .withMappingStrategy(ms)
        .withSeparator(';')
        .build();

    return cb.parse();
  }

}
