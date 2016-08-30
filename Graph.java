import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;

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
      String roadInfo = roadList[i];
      source = roadInfo.substring(0, 1);
      target = roadInfo.substring(1, 2);
      weight = Integer.parseInt(roadInfo.substring(2));

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



  public String getRouteDistance(String[] townNames) {

    int total = 0;
    Town currentTown;
    String nextTownName;
    ArrayList<Adjacency> currentTownAdjacencies;
    Adjacency adj;
    boolean foundRoad;

    for (int i = 0; i < townNames.length - 1; i++) {

      currentTown = graph.get(townNames[i]);
      if (currentTown == null) {
        return "INVALID TOWN NAME - TOWN NOT FOUND";
      }

      nextTownName = townNames[i + 1];
      currentTownAdjacencies = currentTown.getAdjacencies();
      foundRoad = false;
      for (int j = 0; j < currentTownAdjacencies.size(); j++) {
        adj = currentTownAdjacencies.get(j);
        if (adj.getTarget().equals(nextTownName)) {
          total = total + adj.getWeight();
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

  public String getNumberOfPathsConditions(String startTown, String endTown, PathConditions conditions) {

    // breadth-first search for solutions while the current path still meets conditions
    PathInfo current;
    String townName;
    int distance, numStops;
    int totalPaths = 0;
    ArrayList<Adjacency> currentTownAdjacencies;
    Deque<PathInfo> deque = new ArrayDeque<PathInfo>();
    deque.addFirst(new PathInfo(startTown, 0, 0));

    while (deque.size() > 0) {
      current = deque.pop();
      townName = current.getName();
      distance = current.getDistance();
      numStops = current.getNumStops();

      if (townName.equals(endTown) && conditions.terminalConditionsSatisfiedBy(numStops, distance)) {
        totalPaths = totalPaths + 1;
      }
      if (conditions.currentConditionsSatisfiedBy(numStops, distance)) {
        currentTownAdjacencies = graph.get(townName).getAdjacencies();
        for (int i = 0; i < currentTownAdjacencies.size(); i++) {
          deque.addFirst(new PathInfo(currentTownAdjacencies.get(i).getTarget(), distance + currentTownAdjacencies.get(i).getWeight(), numStops + 1));
        }
      }
    }
    return Integer.toString(totalPaths);

  }

  public String getNumberOfPathsStops(String startTown, String endTown, int minStops, int maxStops) {

    PathConditions conditions = new PathConditions();
    conditions.setMinStops(minStops);
    conditions.setMaxStops(maxStops);
    return getNumberOfPathsConditions(startTown, endTown, conditions);
  }


  public String getNumberOfPathsDistance(String startTown, String endTown, int max_distance) {

    PathConditions conditions = new PathConditions();
    conditions.setMinDistance(0);
    conditions.setMaxDistance(max_distance);
    return getNumberOfPathsConditions(startTown, endTown, conditions);
  }


  public String getShortestPath(String startTown, String endTown) {

    // dijkstra's algorithm using priority queue

    PathInfo current;
    String townName;
    Adjacency adj;
    int distance;
    int proposed_distance;
    ArrayList<Adjacency> currentTownAdjacencies;
    Set<String> unvisited = new HashSet<String>();
    Hashtable<String, Integer> distances = new Hashtable<String, Integer>();

    for (String key: graph.keySet()) {
      distances.put(key, Integer.MAX_VALUE);
      unvisited.add(key);
    }
    distances.put(startTown, 0);

    PriorityQueue<PathInfo> queue = new PriorityQueue<PathInfo>(graph.size(), new DistanceComparator());
    queue.add(new PathInfo(startTown, distances.get(startTown)));


    while (queue.size() > 0) {
      current = queue.poll();
      townName = current.getName();
      distance = current.getDistance();

      if (townName.equals(endTown) && distance > 0) {
        return Integer.toString(distance);
      } else if (!townName.equals(startTown)) {
        unvisited.remove(townName);
      }

      currentTownAdjacencies = graph.get(townName).getAdjacencies();
      for (int i = 0; i < currentTownAdjacencies.size(); i++) {
        adj = currentTownAdjacencies.get(i);
        proposed_distance = distances.get(townName) + adj.getWeight();


        if (proposed_distance < distances.get(adj.getTarget())) {
          distances.put(adj.getTarget(), proposed_distance);
        }

        if (unvisited.contains(adj.getTarget())) {
          if (adj.getTarget().equals(startTown)) {
            queue.add(new PathInfo(adj.getTarget(), proposed_distance));
          } else {
            queue.add(new PathInfo(adj.getTarget(), distances.get(adj.getTarget())));
          }
        }

      }

    }

    if (distances.get(endTown) == Integer.MAX_VALUE) {
      return "TARGET IS NOT REACHABLE FROM SOURCE";
    } else {
      return Integer.toString(distances.get(endTown));
    }
  }

}