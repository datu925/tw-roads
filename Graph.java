import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class Graph {
  private Hashtable<String, Town> graph = new Hashtable<String, Town>();

  public void addTown(Town town) {
    if (graph.get(town.getName()) == null) {
      graph.put(town.getName(), town);
    }
  }

  public void createGraphFromRoadList(String[] roadList) {
    String source;
    String target;
    int weight;

    for(int i = 0; i < roadList.length; i++) {
      String[] roadInfo = roadList[i].split("");
      source = roadInfo[0];
      target = roadInfo[1];
      weight = Integer.parseInt(roadInfo[2]);

      if (graph.get(source) == null) {
        graph.put(source, new Town(source));
      }

      if (graph.get(target) == null) {
        graph.put(target, new Town(target));
      }

      graph.get(source).addAdjacency(target, weight);

    }
  }

  public Hashtable<String, Town> getGraph() {
    return graph;
  }



  public String getRouteDistance(String[] town_names) {

    int total = 0;
    Town currentTown = new Town();
    String nextTownName;
    ArrayList<Adjacency> currentTownAdjacencies = new ArrayList<Adjacency>();
    Adjacency adj;
    boolean foundRoad = false;

    for (int i = 0; i < town_names.length - 1; i++) {
      foundRoad = false;
      currentTown = graph.get(town_names[i]);
      if (currentTown == null) {
        return "INVALID TOWN NAME - TOWN NOT FOUND";
      }
      nextTownName = town_names[i + 1];
      currentTownAdjacencies = currentTown.getAdjacencies();
      for (int j = 0; j < currentTownAdjacencies.size(); j++) {
        if (currentTownAdjacencies.get(j).getTarget().equals(nextTownName)) {
          total = total + currentTownAdjacencies.get(j).getWeight();
          foundRoad = true;
          break;
        }
      }
      if (!foundRoad) {
        return "NO SUCH ROUTE";
      }
    }
    return Integer.toString(total);
  }

  public String getNumberOfPaths(String start_town, String end_town, int min_stops, int max_stops) {

    Object[] temp;
    String townName;
    int num_stops;
    int totalPaths = 0;
    ArrayList<Adjacency> currentTownAdjacencies = new ArrayList<Adjacency>();
    Deque<Object[]> deque = new ArrayDeque<Object[]>();
    deque.addFirst(new Object[] {start_town, 0});

    while (deque.size() > 0) {
      temp = deque.pop();
      townName = temp[0].toString();
      num_stops = (Integer) temp[1];

      if (townName.equals(end_town) && num_stops >= min_stops) {
        totalPaths = totalPaths + 1;
      }
      if (num_stops < max_stops) {
        currentTownAdjacencies = graph.get(townName).getAdjacencies();
        for (int i = 0; i < currentTownAdjacencies.size(); i++) {
          deque.addFirst(new Object[] {currentTownAdjacencies.get(i).getTarget(), num_stops + 1});
        }
      }
    }
    return Integer.toString(totalPaths);
  }


  public String getNumberOfPathsDistance(String start_town, String end_town, int max_distance) {

    Object[] temp;
    String townName;
    int distance;
    int totalPaths = 0;
    ArrayList<Adjacency> currentTownAdjacencies = new ArrayList<Adjacency>();
    Deque<Object[]> deque = new ArrayDeque<Object[]>();
    deque.addFirst(new Object[] {start_town, 0});

    while (deque.size() > 0) {
      temp = deque.pop();
      townName = temp[0].toString();
      distance = (Integer) temp[1];

      if (townName.equals(end_town) && distance <= max_distance && distance > 0) {
        totalPaths = totalPaths + 1;
      }
      if (distance < max_distance) {
        currentTownAdjacencies = graph.get(townName).getAdjacencies();
        for (int i = 0; i < currentTownAdjacencies.size(); i++) {
          deque.addFirst(new Object[] {currentTownAdjacencies.get(i).getTarget(), distance + currentTownAdjacencies.get(i).getWeight()});
        }
      }
    }
    return Integer.toString(totalPaths);
  }


  public String getShortestPath(String start_town, String end_town) {

    // currently assumes that end_town is actually reachable from start_town. Would like to come back to this assumption later

    Object[] temp;
    String townName;
    Adjacency adj;
    int distance;
    double shortest_distance = Double.POSITIVE_INFINITY;
    int totalPaths = 0;
    ArrayList<Adjacency> currentTownAdjacencies = new ArrayList<Adjacency>();
    Deque<Object[]> deque = new ArrayDeque<Object[]>();
    deque.addFirst(new Object[] {start_town, 0});

    while (deque.size() > 0) {
      temp = deque.pop();
      townName = temp[0].toString();
      distance = (Integer) temp[1];

      if (townName.equals(end_town) && distance < shortest_distance && distance > 0) {
        shortest_distance = distance;
      }
      if (distance < shortest_distance) {
        currentTownAdjacencies = graph.get(townName).getAdjacencies();
        for (int i = 0; i < currentTownAdjacencies.size(); i++) {
          adj = currentTownAdjacencies.get(i);
          deque.addFirst(new Object[] {adj.getTarget(), distance + adj.getWeight()});
        }
      }
    }
    if (shortest_distance == Double.POSITIVE_INFINITY) {
      return "TARGET IS NOT REACHABLE FROM SOURCE";
    } else {
      return Integer.toString((int)shortest_distance);
    }
  }
}