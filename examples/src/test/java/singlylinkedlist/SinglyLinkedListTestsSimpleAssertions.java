package singlylinkedlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SinglyLinkedListTestsSimpleAssertions {

  @Test
  public void testEmptyList() {
    SinglyLinkedList list = new SinglyLinkedList();
    assertTrue(list.isEmpty());
    assertEquals("()", list.toString());
    String s1 = list.toString();
    String s2 = "()";
    assertEquals(s1, s2);
  }

  @Test
  public void testEmptyOneElem() {
    SinglyLinkedList list = new SinglyLinkedList();
    list.add();
    assertFalse(list.isEmpty());
    assertEquals(list.getSize(), 1);
  }

  @Test
  public void testEmptyTwoElem() {
    SinglyLinkedList list = new SinglyLinkedList();
    list.add();
    list.add();
    assertFalse(list.isEmpty());
    assertEquals(list.getSize(), 2);
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
    assertFalse(list.isEmpty());
    assertEquals(list.getSize(), 6);
  }

}
