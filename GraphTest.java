import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import java.util.ArrayList;
import java.util.Hashtable;
import org.junit.Test;

public class GraphTest {

  public Graph createTestGraph(String[] graph_args) {
    Graph graph = new Graph();
    graph.createGraphFromRoadList(graph_args);
    return graph;
  }

  @Test
  public void getRouteDistance() {
    String[] graph_args = new String[] {"AB5","BC5"};
    Graph graph = createTestGraph(graph_args);

    String[] arg = new String[] {"A", "B"};
    String[] arg1 = new String[] {"A", "C"};
    String[] arg2 = new String[] {"D", "C"};

    assertEquals("Returns value with correct path", graph.getRouteDistance(arg), "5");
    assertEquals("Returns NSR with incorrect path", graph.getRouteDistance(arg1), "NO SUCH ROUTE");
    assertEquals("Returns invalid with incorrect town", graph.getRouteDistance(arg2), "INVALID TOWN NAME - TOWN NOT FOUND");
  }


  @Test
  public void getNumberOfPathsStops() {
    String[] graph_args = new String[] {"AB5","BC5", "AC10"};
    Graph graph = createTestGraph(graph_args);


    assertEquals("Returns correct value", graph.getNumberOfPathsStops("A", "C", 1, 10), "2");
    assertEquals("Returns 0 when no paths", graph.getNumberOfPathsStops("C", "A", 2, 10), "0");
    assertEquals("Filters out paths that are too long", graph.getNumberOfPathsStops("A", "C", 1, 1), "1");
  }


  @Test
  public void getNumberOfPathsDistance() {
    String[] graph_args = new String[] {"AB5","BC4","AC10"};
    Graph graph = createTestGraph(graph_args);


    assertEquals("Filters out paths that are too long", graph.getNumberOfPathsDistance("A", "C", 9), "1");
    assertEquals("Includes paths as long as maximum", graph.getNumberOfPathsDistance("A", "C", 10), "2");
    assertEquals("Returns 0 when no paths", graph.getNumberOfPathsDistance("A", "C", 3), "0");
  }



  @Test
  public void getShortestPath() {
    String[] graph_args = new String[] {"AB5","BC4","AC10"};
    Graph graph = createTestGraph(graph_args);

    assertEquals("Finds shortest path", graph.getShortestPath("A", "C"), "9");
    assertEquals("Gives up when no path", graph.getShortestPath("C", "A"), "TARGET IS NOT REACHABLE FROM SOURCE");
  }

}