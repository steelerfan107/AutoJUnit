package sortedlist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortedListTests {

  @Test
  public void testWithTryCatch() {
    try {
      SortedList list = new SortedList();
      list.add(1);
      list.add(3);
      list.add(2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testEmptyList() {
    SortedList list = new SortedList();
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testOneElem() {
    SortedList list = new SortedList();
    list.add(1);
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testMultipleElems0() {
    SortedList list = new SortedList();
    list.add(1);
    list.add(2);
    list.add(3);
    assertTrue(list.repOK_Complete());
  }

  @Test
  public void testMultipleElems1() {
    SortedList list = new SortedList();
    list.add(1);
    list.add(3);
    list.add(2);
    assertTrue(list.repOK_Complete());
  }

}
