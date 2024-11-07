package binheap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinomialHeapTests {

  @Test
  public void testEmptyList() {
    BinomialHeap heap = new BinomialHeap();
    assertTrue(heap.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    BinomialHeap heap = new BinomialHeap();
    heap.insert(1);
    assertTrue(heap.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    BinomialHeap heap = new BinomialHeap();
    heap.insert(1);
    heap.insert(2);
    assertTrue(heap.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    BinomialHeap heap = new BinomialHeap();
    heap.insert(1);
    heap.insert(2);
    heap.insert(3);
    heap.insert(4);
    heap.insert(5);
    heap.insert(6);
    heap.insert(7);
    assertTrue(heap.repOK_Complete());
  }

}
