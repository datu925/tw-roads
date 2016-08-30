import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;

public class Kiwiland {


  public String getRouteDistance(Hashtable<String, Town> adjacencies, String[] town_names) {

    int total = 0;
    Town currentTown = new Town();
    String nextTownName;
    ArrayList<Adjacency> currentTownAdjacencies = new ArrayList<Adjacency>();
    Adjacency adj;
    boolean foundRoad = false;

    for (int i = 0; i < town_names.length - 1; i++) {
      foundRoad = false;
      currentTown = adjacencies.get(town_names[i]);
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

  public String getNumberOfPaths(Hashtable<String, Town> adjacencies, String start_town, String end_town, int min_stops, int max_stops) {

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
        currentTownAdjacencies = adjacencies.get(townName).getAdjacencies();
        for (int i = 0; i < currentTownAdjacencies.size(); i++) {
          deque.addFirst(new Object[] {currentTownAdjacencies.get(i).getTarget(), num_stops + 1});
        }
      }
    }
    return Integer.toString(totalPaths);
  }


  public String getNumberOfPathsDistance(Hashtable<String, Town> adjacencies, String start_town, String end_town, int max_distance) {

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
        currentTownAdjacencies = adjacencies.get(townName).getAdjacencies();
        for (int i = 0; i < currentTownAdjacencies.size(); i++) {
          deque.addFirst(new Object[] {currentTownAdjacencies.get(i).getTarget(), distance + currentTownAdjacencies.get(i).getWeight()});
        }
      }
    }
    return Integer.toString(totalPaths);
  }


  public String getShortestPath(Hashtable<String, Town> adjacencies, String start_town, String end_town) {

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
        currentTownAdjacencies = adjacencies.get(townName).getAdjacencies();
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



  public static void main(String[] args) {

    String fileName = args[0];
    InputReader reader = new InputReader();
    reader.readFile(fileName);
    String[] roadList = reader.parseRoadList();

    String source;
    String target;
    int weight;

    Hashtable<String, Town> adjacencies = new Hashtable<String, Town>();

    for(int i = 0; i < roadList.length; i++) {
      String[] roadInfo = roadList[i].split("");
      source = roadInfo[0];
      target = roadInfo[1];
      weight = Integer.parseInt(roadInfo[2]);

      if (adjacencies.get(source) == null) {
        Town town = new Town();
        town.setName(source);
        adjacencies.put(source, town);
      }
      if (adjacencies.get(target) == null) {
        Town town = new Town();
        town.setName(target);
        adjacencies.put(target, town);
      }

      adjacencies.get(source).addAdjacency(target, weight);

    }

    Kiwiland kiwi = new Kiwiland();
    String[] arg = new String[] {"A", "B", "C"};
    String[] arg1 = new String[] {"A", "D"};
    String[] arg2 = new String[] {"A", "D", "C"};
    String[] arg3 = new String[] {"A", "E", "B", "C", "D"};
    String[] arg4 = new String[] {"A", "E", "D"};


    System.out.println(kiwi.getRouteDistance(adjacencies, arg));
    System.out.println(kiwi.getRouteDistance(adjacencies, arg1));
    System.out.println(kiwi.getRouteDistance(adjacencies, arg2));
    System.out.println(kiwi.getRouteDistance(adjacencies, arg3));
    System.out.println(kiwi.getRouteDistance(adjacencies, arg4));
    System.out.println(kiwi.getNumberOfPaths(adjacencies, "C", "C", 2, 3));
    System.out.println(kiwi.getNumberOfPaths(adjacencies, "A", "C", 4, 4));
    System.out.println(kiwi.getShortestPath(adjacencies, "A", "C"));
    System.out.println(kiwi.getShortestPath(adjacencies, "B", "B"));
    System.out.println(kiwi.getNumberOfPathsDistance(adjacencies, "C", "C", 29));

  }
}