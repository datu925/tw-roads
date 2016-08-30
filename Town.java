import java.util.ArrayList;

public class Town {
  private String name;
  private ArrayList<Adjacency> adjacencies = new ArrayList<Adjacency>();

  public Town(String n) {
    this.name = n;
  }

  public Town() {
    this.name = "undefined";
  }

  public void setName(String n) {
    name = n;
  }

  public String getName() {
    return name;
  }

  public void addAdjacency(String target, int weight) {
    Adjacency a = new Adjacency();
    a.setTarget(target);
    a.setWeight(weight);
    adjacencies.add(a);
  }

  public ArrayList<Adjacency> getAdjacencies() {
    return adjacencies;
  }
}