public class PathConditions {
  private Integer minStops;
  private Integer maxStops;
  private Integer minDistance;
  private Integer maxDistance;

  public void setMinStops(int ms) {
    minStops = ms;
  }

  public void setMaxStops(int mxs) {
    maxStops = mxs;
  }

  public void setMinDistance(int md) {
    minDistance = md;
  }

  public void setMaxDistance(int mds) {
    maxDistance = mds;
  }

  public boolean terminalConditionsSatisfiedBy(Integer stops, Integer distance) {
    if (minStops != null) {
      return stops >= minStops && stops <= maxStops && distance > 0;
    } else {
      return distance >= minDistance && distance <= maxDistance && distance > 0;
    }
  }

  public boolean currentConditionsSatisfiedBy(Integer stops, Integer distance) {
    if (minStops != null) {
      return stops <= maxStops;
    } else {
      return distance <= maxDistance;
    }
  }
}