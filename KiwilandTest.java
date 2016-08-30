import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import java.util.ArrayList;
import java.util.Hashtable;
import org.junit.Test;

public class KiwilandTest {
  @Test
  public void getRouteDistance() {
    Kiwiland kiwi = new Kiwiland();
    Hashtable<String, Town> adjacencies = new Hashtable<String, Town>();
    Town a = new Town("A");
    Town b = new Town("B");
    Town c = new Town("C");
    a.addAdjacency("B", 5);
    b.addAdjacency("C", 5);
    adjacencies.put("A", a);
    adjacencies.put("B", b);
    adjacencies.put("C", c);

    String[] arg = new String[] {"A", "B"};
    String[] arg1 = new String[] {"A", "C"};
    String[] arg2 = new String[] {"D", "C"};

    assertEquals("Returns value with correct path", kiwi.getRouteDistance(adjacencies, arg), "5");
    assertEquals("Returns NSR with incorrect path", kiwi.getRouteDistance(adjacencies, arg1), "NO SUCH ROUTE");
    assertEquals("Returns invalid with incorrect town", kiwi.getRouteDistance(adjacencies, arg2), "INVALID TOWN NAME - TOWN NOT FOUND");
  }


  @Test
  public void getNumberOfPaths() {
    Kiwiland kiwi = new Kiwiland();
    Hashtable<String, Town> adjacencies = new Hashtable<String, Town>();
    Town a = new Town("A");
    Town b = new Town("B");
    Town c = new Town("C");
    a.addAdjacency("B", 5);
    b.addAdjacency("C", 5);
    a.addAdjacency("C", 10);
    adjacencies.put("A", a);
    adjacencies.put("B", b);
    adjacencies.put("C", c);


    assertEquals("Returns correct value", kiwi.getNumberOfPaths(adjacencies, "A", "C", 1, 10), "2");
    assertEquals("Returns 0 when no paths", kiwi.getNumberOfPaths(adjacencies, "C", "A", 2, 10), "0");
    assertEquals("Filters out paths that are too long", kiwi.getNumberOfPaths(adjacencies, "A", "C", 1, 1), "1");
  }


  @Test
  public void getNumberOfPathsDistance() {
    Kiwiland kiwi = new Kiwiland();
    Hashtable<String, Town> adjacencies = new Hashtable<String, Town>();
    Town a = new Town("A");
    Town b = new Town("B");
    Town c = new Town("C");
    a.addAdjacency("B", 5);
    b.addAdjacency("C", 4);
    a.addAdjacency("C", 10);
    adjacencies.put("A", a);
    adjacencies.put("B", b);
    adjacencies.put("C", c);


    assertEquals("Filters out paths that are too long", kiwi.getNumberOfPathsDistance(adjacencies, "A", "C", 9), "1");
    assertEquals("Includes paths as long as maximum", kiwi.getNumberOfPathsDistance(adjacencies, "A", "C", 10), "2");
    assertEquals("Returns 0 when no paths", kiwi.getNumberOfPathsDistance(adjacencies, "A", "C", 3), "0");
  }



  @Test
  public void getShortestPath() {
    Kiwiland kiwi = new Kiwiland();
    Hashtable<String, Town> adjacencies = new Hashtable<String, Town>();
    Town a = new Town("A");
    Town b = new Town("B");
    Town c = new Town("C");
    a.addAdjacency("B", 5);
    b.addAdjacency("C", 4);
    a.addAdjacency("C", 10);
    adjacencies.put("A", a);
    adjacencies.put("B", b);
    adjacencies.put("C", c);


    assertEquals("Finds shortest path", kiwi.getShortestPath(adjacencies, "A", "C"), "9");
    // assertEquals("Gives up when no path", kiwi.getShortestPath(adjacencies, "C", "A"), "TARGET IS NOT REACHABLE FROM SOURCE");
  }

}