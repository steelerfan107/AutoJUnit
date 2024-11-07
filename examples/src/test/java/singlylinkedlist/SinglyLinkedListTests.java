package singlylinkedlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SinglyLinkedListTests {

  @Test
  public void testEmptyList() {
    SinglyLinkedList list = new SinglyLinkedList();
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    SinglyLinkedList list = new SinglyLinkedList();
    list.add();
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    SinglyLinkedList list = new SinglyLinkedList();
    list.add();
    list.add();
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    SinglyLinkedList list = new SinglyLinkedList();
    list.add();
    list.add();
    list.add();
    list.add();
    list.add();
    list.add();
    assertTrue(list.repOK_Complete());
  }

}
