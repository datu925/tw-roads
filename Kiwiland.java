public class Kiwiland {

  public static void main(String[] args) {

    String fileName = args[0];
    InputReader reader = new InputReader();
    String[] roadList = reader.readGraphFile(fileName);

    Graph graph = new Graph();

    graph.createGraphFromRoadList(roadList);


    System.out.println(graph.getRouteDistance(new String[] {"A", "B", "C"}));
    System.out.println(graph.getRouteDistance(new String[] {"A", "D"}));
    System.out.println(graph.getRouteDistance(new String[] {"A", "D", "C"}));
    System.out.println(graph.getRouteDistance(new String[] {"A", "E", "B", "C", "D"}));
    System.out.println(graph.getRouteDistance(new String[] {"A", "E", "D"}));
    System.out.println(graph.getNumberOfPathsStops("C", "C", 1, 3));
    System.out.println(graph.getNumberOfPathsStops("A", "C", 4, 4));
    System.out.println(graph.getShortestPath("A", "C"));
    System.out.println(graph.getShortestPath("B", "B"));
    System.out.println(graph.getNumberOfPathsDistance("C", "C", 29));

  }
}