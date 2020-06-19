package selegrid.framework.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileHelper {

  static void writeToFile(List<String> list, String filePath) {
    try {
      FileWriter writer = new FileWriter(filePath);
      for (String str : list) {
        writer.write(str + System.lineSeparator());
      }
      writer.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  static void consolidateFilesToOne(String resultFile, String glob) {
    consolidateFilesToOne("", resultFile, glob);
  }

  static void consolidateFilesToOne(String directory, String resultFile, String glob) {
    List<String> consolidatedContent = new ArrayList<>();
    DirectoryStream<Path> stream = readFilesInDirectory(directory, glob);

    //read consolidated content if exists
    try {
      consolidatedContent.addAll(Files.readAllLines(Paths.get(directory + resultFile)));
    } catch (IOException e) {
    }

    //read temporary reports content
    stream.forEach(path -> {
      try {
        consolidatedContent.addAll(Files.readAllLines(path));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    //apply sorting
    consolidatedContent.sort(String::compareTo);

    //write to file
    writeToFile(consolidatedContent, directory + resultFile);
  }

  static void deleteFiles(String glob) {
    deleteFiles("", glob);
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
