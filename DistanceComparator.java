import java.util.Comparator;

public class DistanceComparator implements Comparator<PathInfo> {
  @Override
  public int compare(PathInfo info1, PathInfo info2) {
    int dist1 = info1.getDistance();
    int dist2 = info2.getDistance();
    if (dist1 == dist2) {
      return 0;
    } else if (dist1 > dist2) {
      return 1;
    } else {
      return -1;
    }
  }
}