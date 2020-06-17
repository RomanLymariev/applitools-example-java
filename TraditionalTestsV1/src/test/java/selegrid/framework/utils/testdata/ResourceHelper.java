package selegrid.framework.utils.testdata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;

public class ResourceHelper {

  static String getAbsolutePathToResource(String relativePathInResourceFolder) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(relativePathInResourceFolder);

    return (url == null) ? "" : url.getPath();
  }

   static InputStream getResourceAsStream(String relativePathInResourceFolder) {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(relativePathInResourceFolder);
  }

  static FileReader getResourceFileReader(String relativePathInResourceFolder) {
    try {
      return new FileReader(getAbsolutePathToResource(relativePathInResourceFolder));
    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

}
