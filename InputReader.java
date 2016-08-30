import java.io.*;

public class InputReader {
  private String fileContents;
  private String[] roadList;


  public void readFile(String fileName) {
    String line = null;
    String contents = "";

    try {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while ((line = bufferedReader.readLine()) != null) {
        contents = contents + line;
      }
      bufferedReader.close();

      fileContents = contents;
    } catch (FileNotFoundException e) {
      System.out.println("Could not find file.");
    } catch (IOException e) {
      System.out.println("Error reading file.");
    }
  }

  public String[] parseRoadList() {
    roadList = fileContents.split(", ");
    return roadList;
  }
}