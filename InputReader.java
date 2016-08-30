import java.io.*;

public class InputReader {
  private String fileContents;
  private String[] roadList;


  public String readFile(String fileName) {
    String line = null;
    String contents = "";

    try {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      while ((line = bufferedReader.readLine()) != null) {
        contents = contents + line;
      }
      bufferedReader.close();

      return contents;
    } catch (FileNotFoundException e) {
      System.out.println("Could not find file.");
      return null;
    } catch (IOException e) {
      System.out.println("Error reading file.");
      return null;
    }
  }

  public String[] parseRoadList(String contents) {
    if (contents != null) {
      roadList = contents.split(", ");
      return roadList;
    } else {
      return null;
    }
  }

  public String[] readGraphFile(String fileName) {
    InputReader reader = new InputReader();
    String contents = reader.readFile(fileName);
    return reader.parseRoadList(contents);
  }

  // public String[] readQueryFile(String fileName) {
  //   InputReader reader = new InputReader();
  //   String contents = reader.readFile(filename);
  //   return reader.parseRoadList(contents);
  // }

}