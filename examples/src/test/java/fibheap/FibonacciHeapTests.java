package fibheap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FibonacciHeapTests {

  @Test
  public void testEmptyList() {
    FibonacciHeap fibheap = new FibonacciHeap();
    assertTrue(fibheap.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    FibonacciHeap fibheap = new FibonacciHeap();
    fibheap.insert(0);
    assertTrue(fibheap.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    FibonacciHeap fibheap = new FibonacciHeap();
    fibheap.insert(0);
    fibheap.insert(1);
    assertTrue(fibheap.repOK_Complete());
    fibheap.removeMin();
    assertTrue(fibheap.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    FibonacciHeap fibheap = new FibonacciHeap();
    fibheap.insert(0);
    fibheap.insert(1);
    fibheap.insert(2);
    fibheap.insert(3);
    fibheap.insert(-4);
    fibheap.insert(5);
    fibheap.insert(6);
    assertTrue(fibheap.repOK_Complete());
    fibheap.removeMin();
    assertTrue(fibheap.repOK_Complete());
    fibheap.removeMin();
    assertTrue(fibheap.repOK_Complete());
  }

}
