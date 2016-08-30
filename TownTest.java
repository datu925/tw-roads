import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import java.util.ArrayList;
import org.junit.Test;

public class TownTest {
  @Test
  public void getAdjacenciesshouldReturnArrayList() {
    Town town = new Town();

    assertThat(town.getAdjacencies(), instanceOf(ArrayList.class));
  }

  @Test
  public void getAdjacenciesListShouldContainAdjacencies() {
    Town town = new Town();

    town.addAdjacency("B", 5);
    assertThat(town.getAdjacencies().get(0), instanceOf(Adjacency.class));
  }
}