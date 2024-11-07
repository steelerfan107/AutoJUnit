package disjset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DisjSetTests {

  @Test
  public void testEmptyList() {
    DisjSet set = new DisjSet(1);
    assertTrue(set.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    DisjSet set = new DisjSet(1);
    set.union(0, 0);
    assertTrue(set.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    DisjSet set = new DisjSet(2);
    set.union(0, 0);
    set.union(1, 1);
    assertTrue(set.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    DisjSet set = new DisjSet(7);
    set.union(0, 0);
    set.union(1, 1);
    set.union(2, 2);
    set.union(3, 3);
    set.union(4, 4);
    set.union(5, 5);
    set.union(6, 6);
    assertTrue(set.repOK_Complete());
  }

}
