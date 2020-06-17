package selegrid.framework.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileHelper {

  static void writeToFile(List<String> list, String filepath) {
    deleteFileIfExists(new File(filepath).toPath());

    try {
      FileWriter writer = new FileWriter(filepath);
      for (String str : list) {
        writer.write(str + System.lineSeparator());
      }
      writer.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  static void consolidateFilesToOne(String directory, String resultFile, String glob) {
    List<String> consolidatedContent = new ArrayList<>();

    Path output = Paths.get(directory + resultFile);
    deleteFileIfExists(output);

    DirectoryStream<Path> stream = readFilesInDirectory(directory, glob);
    stream.forEach(path -> {
      try {
        consolidatedContent.addAll(Files.readAllLines(path));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    writeToFile(consolidatedContent, directory + resultFile);
  }

  static void deleteFiles(String directory, String glob) {
    DirectoryStream<Path> stream = readFilesInDirectory(directory, glob);
    stream.forEach(path ->
        deleteFileIfExists(path)
    );
  }

  private static DirectoryStream<Path> readFilesInDirectory(String directory, String glob) {
    try {
      return Files.newDirectoryStream(Paths.get(directory), glob);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  private static void deleteFileIfExists(Path filepath) {
    try {
      Files.deleteIfExists(filepath);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

}
