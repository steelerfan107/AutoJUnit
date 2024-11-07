package redblacktree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedBlackTreeTests {

  @Test
  public void testEmptyTree() {
    RedBlackTree rbt = new RedBlackTree();
    assertTrue(rbt.repOK_Complete());
  }

  @Test
  public void testOneElem() {
    RedBlackTree rbt = new RedBlackTree();
    rbt.insert(10);
    assertTrue(rbt.repOK_Complete());
  }

  @Test
  public void testTwoElem() {
    RedBlackTree rbt = new RedBlackTree();
    rbt.insert(10);
    rbt.insert(20);
    assertTrue(rbt.repOK_Complete());
  }

  @Test
  public void testManyElems() {
    RedBlackTree rbt = new RedBlackTree();
    rbt.insert(10);
    rbt.insert(20);
    rbt.insert(30);
    rbt.insert(40);
    rbt.insert(15);
    rbt.insert(25);
    assertTrue(rbt.repOK_Complete());
  }


}
