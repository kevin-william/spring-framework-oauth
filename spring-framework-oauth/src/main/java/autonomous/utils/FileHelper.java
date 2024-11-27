package autonomous.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHelper {
  public static String getFileContent(String path) {
    StringBuilder contentBuilder = new StringBuilder();

    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
      String line;
      while ((line = br.readLine()) != null) {
        contentBuilder.append(line).append("\n");

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    String fileContent = contentBuilder.toString();
    return fileContent;
  }
}
