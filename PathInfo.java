public class PathInfo {
  private String townName;
  private int distance;
  private int numStops;

  public PathInfo(String tn, int d, int n) {
    this.townName = tn;
    this.distance = d;
    this.numStops = n;
  }

  public PathInfo(String tn, int d) {
    this.townName = tn;
    this.distance = d;
  }

  public String getName() {
    return townName;
  }

  public int getDistance() {
    return distance;
  }

  public int getNumStops() {
    return numStops;
  }
}