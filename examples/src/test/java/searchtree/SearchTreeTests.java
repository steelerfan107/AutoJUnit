package searchtree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTreeTests {

  @Test
  public void testEmptyTree() {
    SearchTree searchTree = new SearchTree();
    assertEquals(0, searchTree.size());
    assertTrue(searchTree.repOK_Complete());
  }

  @Test
  public void testOneElem() {
    SearchTree searchTree = new SearchTree();
    searchTree.insert(10);
    assertEquals(1, searchTree.size());
    assertTrue(searchTree.repOK_Complete());
  }

  @Test
  public void testMultipleElems0() {
    SearchTree searchTree = new SearchTree();
    searchTree.insert(1);
    searchTree.insert(2);
    searchTree.insert(3);
    assertEquals(3, searchTree.size());
    assertTrue(searchTree.repOK_Complete());
  }

  @Test
  public void testMultipleElems1() {
    SearchTree searchTree = new SearchTree();
    searchTree.insert(1);
    searchTree.insert(4);
    searchTree.insert(0);
    searchTree.insert(5);
    searchTree.insert(7);
    searchTree.insert(10);
    assertEquals(6, searchTree.size());
    assertTrue(searchTree.repOK_Complete());
  }

}
