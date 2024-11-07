package doublylinkedlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoublyLinkedListTests {

  @Test
  public void testEmptyList() {
    DoublyLinkedList list = new DoublyLinkedList();
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    DoublyLinkedList list = new DoublyLinkedList();
    list.add();
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    DoublyLinkedList list = new DoublyLinkedList();
    list.add();
    list.add();
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    DoublyLinkedList list = new DoublyLinkedList();
    list.add();
    list.add();
    list.add();
    list.add();
    list.add();
    list.add();
    assertTrue(list.repOK_Complete());
  }

}
