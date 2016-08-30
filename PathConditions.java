public class PathConditions {
  private Integer min_stops;
  private Integer max_stops;
  private Integer min_distance;
  private Integer max_distance;

  public void setMinStops(int ms) {
    min_stops = ms;
  }

  public void setMaxStops(int mxs) {
    max_stops = mxs;
  }

  public void setMinDistance(int md) {
    min_distance = md;
  }

  public void setMaxDistance(int mds) {
    max_distance = mds;
  }

  public boolean terminalConditionsSatisfiedBy(Integer stops, Integer distance) {
    if (min_stops != null) {
      return stops >= min_stops && stops <= max_stops && distance > 0;
    } else {
      return distance >= min_distance && distance <= max_distance && distance > 0;
    }
  }

  public boolean currentConditionsSatisfiedBy(Integer stops, Integer distance) {
    if (min_stops != null) {
      return stops <= max_stops;
    } else {
      return distance <= max_distance;
    }
  }
}