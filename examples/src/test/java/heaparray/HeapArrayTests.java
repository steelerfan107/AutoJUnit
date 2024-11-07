package heaparray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeapArrayTests {

  @Test
  public void testEmptyList() {
    HeapArray heaparray = new HeapArray();
    assertTrue(heaparray.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    HeapArray heaparray = new HeapArray();
    heaparray.add(0);
    assertTrue(heaparray.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    HeapArray heaparray = new HeapArray();
    heaparray.add(0);
    heaparray.add(5);
    assertTrue(heaparray.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    HeapArray heaparray = new HeapArray();
    heaparray.add(0);
    heaparray.add(5);
    heaparray.add(10);
    heaparray.add(2);
    heaparray.add(7);
    heaparray.add(12);
    heaparray.add(4);
    assertTrue(heaparray.repOK_Complete());
  }

}