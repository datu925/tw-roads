import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

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
    Town currentTown;
    String nextTownName;
    ArrayList<Adjacency> currentTownAdjacencies;
    Adjacency adj;
    boolean foundRoad;

    for (int i = 0; i < town_names.length - 1; i++) {

      currentTown = graph.get(town_names[i]);
      if (currentTown == null) {
        return "INVALID TOWN NAME - TOWN NOT FOUND";
      }

      nextTownName = town_names[i + 1];
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

  public String getNumberOfPathsConditions(String start_town, String end_town, PathConditions conditions) {

    Object[] temp;
    String townName;
    int distance;
    int num_stops;
    int totalPaths = 0;
    ArrayList<Adjacency> currentTownAdjacencies = new ArrayList<Adjacency>();
    Deque<Object[]> deque = new ArrayDeque<Object[]>();
    deque.addFirst(new Object[] {start_town, 0, 0});

    while (deque.size() > 0) {
      temp = deque.pop();
      townName = temp[0].toString();
      distance = (Integer) temp[1];
      num_stops = (Integer) temp[2];

      if (townName.equals(end_town) && conditions.terminalConditionsSatisfiedBy(num_stops, distance)) {
        totalPaths = totalPaths + 1;
      }
      if (conditions.currentConditionsSatisfiedBy(num_stops, distance)) {
        currentTownAdjacencies = graph.get(townName).getAdjacencies();
        for (int i = 0; i < currentTownAdjacencies.size(); i++) {
          deque.addFirst(new Object[] {currentTownAdjacencies.get(i).getTarget(), distance + currentTownAdjacencies.get(i).getWeight(), num_stops + 1});
        }
      }
    }
    return Integer.toString(totalPaths);

  }

  public String getNumberOfPathsStops(String start_town, String end_town, int min_stops, int max_stops) {

    PathConditions conditions = new PathConditions();
    conditions.setMinStops(min_stops);
    conditions.setMaxStops(max_stops);
    return getNumberOfPathsConditions(start_town, end_town, conditions);
  }


  public String getNumberOfPathsDistance(String start_town, String end_town, int max_distance) {

    PathConditions conditions = new PathConditions();
    conditions.setMinDistance(0);
    conditions.setMaxDistance(max_distance);
    return getNumberOfPathsConditions(start_town, end_town, conditions);
  }



  public String getShortestPath(String start_town, String end_town) {

    Comparator<Object[]> comp = new Comparator<Object[]>() {
      public int compare(Object[] obj1, Object[] obj2) {
        int dist1 = (Integer) obj1[1];
        int dist2 = (Integer) obj2[1];
        if (dist1 == dist2) {
          return 0;
        } else if (dist1 > dist2) {
          return 1;
        } else {
          return -1;
        }
      }
    };

    Set<String> unvisited = new HashSet<String>();

    Hashtable<String, Integer> distances = new Hashtable<String, Integer>();

    for (String key: graph.keySet()) {
      distances.put(key, Integer.MAX_VALUE);
      unvisited.add(key);
    }

    PriorityQueue<Object[]> queue = new PriorityQueue<Object[]>(graph.size(), comp);
    queue.add(new Object[] {start_town, distances.get(start_town)});

    Object[] temp;
    String townName;
    Adjacency adj;
    int distance;
    int proposed_distance;
    ArrayList<Adjacency> currentTownAdjacencies = new ArrayList<Adjacency>();

    while (queue.size() > 0) {
      temp = queue.poll();
      townName = temp[0].toString();
      distance = (Integer) temp[1];

      if (townName == start_town && distance < Integer.MAX_VALUE) {
        return Integer.toString(distance);
      } else if (townName != start_town) {
        unvisited.remove(townName);
      }

      currentTownAdjacencies = graph.get(townName).getAdjacencies();
      for (int i = 0; i < currentTownAdjacencies.size(); i++) {
        adj = currentTownAdjacencies.get(i);
        if (townName == start_town) {
          proposed_distance = adj.getWeight();
        } else {
          proposed_distance = distances.get(townName) + adj.getWeight();
        }

        if (proposed_distance < distances.get(adj.getTarget())) {
          distances.put(adj.getTarget(), proposed_distance);
        }

        if (unvisited.contains(adj.getTarget())) {
          queue.remove(temp);
          queue.add(new Object[] {adj.getTarget(), distances.get(adj.getTarget())});
        }

      }

    }
    if (distances.get(end_town) == Integer.MAX_VALUE) {
      return "TARGET IS NOT REACHABLE FROM SOURCE";
    } else {

      // theoretically should never execute, since we would have returned earlier;
      return Integer.toString(distances.get(end_town));
    }
  }



  //   distances = {}
  //   @matrix.keys.each do |key|
  //     distances[key] = Float::INFINITY
  //   end
  //   distances[start_town] = 0
  //   visited = []
  //   current = start_town

  //   until current == end_town && distances[current] > 0
  //     visited << current
  //     @matrix[current].roads.each do |adj|
  //       proposed_distance = distances[current] + adj.weight
  //       if adj.target == start_town && start_town == end_town
  //         return proposed_distance
  //       else
  //         distances[adj.target] = [distances[adj.target], proposed_distance].min
  //       end
  //     end
  //     current = distances.reject{|key, value| visited.include? key}.min_by{|key, value| value}.first
  //   end
  //   distances[end_town]
  // end
}