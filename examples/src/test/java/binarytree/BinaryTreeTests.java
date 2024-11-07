package binarytree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryTreeTests {

  @Test
  public void testEmptyList() {
    BinaryTree tree = new BinaryTree();
    assertTrue(tree.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    BinaryTree tree = new BinaryTree();
    tree.insert(1);
    assertTrue(tree.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    BinaryTree tree = new BinaryTree();
    tree.insert(1);
    tree.insert(2);
    assertTrue(tree.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    BinaryTree tree = new BinaryTree();
    tree.insert(1);
    tree.insert(2);
    tree.insert(3);
    tree.insert(4);
    tree.insert(5);
    tree.insert(6);
    tree.insert(7);
    assertTrue(tree.repOK_Complete());
  }

}
