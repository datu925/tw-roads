public class Adjacency {
  private String target;
  private int weight;

  public void setWeight(int w) {
    if (w > 0) {
      weight = w;
    }
  }

  public int getWeight() {
    return weight;
  }


  public void setTarget(String t) {
    target = t;
  }

  public String getTarget() {
    return target;
  }
}