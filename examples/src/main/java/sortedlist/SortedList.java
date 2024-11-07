package sortedlist;

import java.util.Set;

/**
 * SortedList: singly sorted linked list implementation took from Korat.
 * Added builders:
 *     - SortedList()
 *     - add(int value)
 *
 */
@SuppressWarnings("unchecked")
public class SortedList {

  private static class Entry {
    Object element;

    Entry next;

    Entry previous;

    public String toString() {
      return "[" + (element != null ? element.toString() : "null") + "]";
    }
  }

  private Entry header;

  private int size = 0;

  /*
   * Builders -----------------------------------------
   */
  public SortedList(){
    header = new Entry();
    header.element = null;
    header.next = header;
    header.previous = header;
    size = 0;
  }

  public void add(Object value){
    if (value == null)
      throw new IllegalArgumentException("null");
    if (!(value instanceof Comparable))
      throw new IllegalArgumentException("not comparable");

    Entry n = new Entry();
    n.element = value;

    Entry current = header.next;
    Entry previous = header;

    if (size==0) {
      header.next = n;
      header.previous = n;
      n.previous = header;
      n.next = header;
    } else {
      int toVisit = size;
      while (current.element != null && ((Comparable<Object>)current.element).compareTo(value) <= 0 && toVisit > 0) {
        previous = current;
        current = current.next;
        toVisit--;
      }
      n.next = current;
      if (current!=null)
        current.previous = n;
      previous.next = n;
      n.previous = previous;
    }
    size++;
  }
  /*
   * -------------------------------------------------------
   */

  /**
   * Complete RepOK method checking the following properties:
   * 1. Acyclic
   * 2. Size
   * 3. Ordering
   * @return true if the representation is OK
   */
  public boolean repOK_Complete() {
    // check header
    if (!repOK_header())
      return false;

    // check that is cyclic
    if (!repOK_acyclic())
      return false;

    // check size
    if (!repOK_size())
      return false;

    // check ordering
    if (!repOK_ordering())
      return false;

    return true;
  }

  /**
   * RepOK method checking the following properties:
   * 1. Header is ok
   * @return true if the representation is OK
   */
  public boolean repOK_header() {
    return header != null && header.element == null;
  }

  /**
   * RepOK method checking the following properties:
   * 1. The list is not acyclic
   * @return true if the representation is OK
   */
  public boolean repOK_acyclic() {
    Set<Entry> visited = new java.util.HashSet<>();
    visited.add(header);
    Entry current = header;
    while (true) {
      Entry next = current.next;
      if (next == null)
        return false;
      if (next.previous != current)
        return false;
      current = next;
      if (!visited.add(next))
        break;
    }
    if (current != header)
      return false; // maybe not needed
    return true;
  }

  /**
   * RepOK method checking the following properties:
   * 1. The size is ok
   * @return true if the representation is OK
   */
  public boolean repOK_size() {
    Set<Entry> visited = new java.util.HashSet<>();
    visited.add(header);
    Entry current = header;
    while (true) {
      Entry next = current.next;
      if (next == null)
        return false;
      if (next.previous != current)
        return false;
      current = next;
      if (!visited.add(next))
        break;
    }
    return visited.size() - 1 == size;
  }

  /**
   * RepOK method checking the following properties:
   * 1. The list is sorted
   * @return true if the representation is OK
   */
  public boolean repOK_ordering() {
    Entry current;
    if ((header.next != header)
        && (!(header.next.element instanceof Comparable)))
      return false;
    for (current = header.next; current.next != header; current = current.next) {
      if ((!(current.next.element instanceof Comparable))
          || (((Comparable) current.element).compareTo((Comparable) current.next.element) > 0))
        return false;
    }
    return true;
  }

  /**
   * RepOK method checking the following properties:
   * 1. Header is ok
   * 2. The list is acyclic
   * @return true if the representation is OK
   */
  public boolean repOK_header_acyclic() {
    // check header
    if (!repOK_header())
      return false;

    // check that is cyclic
    if (!repOK_acyclic())
      return false;

    return true;
  }

  /**
   * RepOK method checking the following properties:
   * 1. Header is ok
   * 2. The size is ok
   * @return true if the representation is OK
   */
  public boolean repOK_header_size() {
    // check header
    if (!repOK_header())
      return false;

    // check size
    if (!repOK_size())
      return false;

    return true;
  }

  /**
   * RepOK method checking the following properties:
   * 1. Header is ok
   * 2. The order is ok
   * @return true if the representation is OK
   */
  public boolean repOK_header_ordering() {
    // check header
    if (!repOK_header())
      return false;

    // check ordering
    if (!repOK_ordering())
      return false;

    return true;
  }

  public boolean repOK_acyclic_size() {
    if (!repOK_acyclic())
      return false;
    if (!repOK_size())
      return false;
    return true;
  }

  public boolean repOK_acyclic_ordering() {
    if (!repOK_acyclic())
      return false;
    if (!repOK_ordering())
      return false;
    return true;
  }

  public boolean repOK_size_ordering() {
    if (!repOK_size())
      return false;
    if (!repOK_ordering())
      return false;
    return true;
  }

  public boolean repOK_header_acyclic_size() {
    if (!repOK_header())
      return false;
    if (!repOK_acyclic())
      return false;
    if (!repOK_size())
      return false;
    return true;
  }

  public boolean repOK_header_acyclic_ordering() {
    if (!repOK_header())
      return false;
    if (!repOK_acyclic())
      return false;
    if (!repOK_ordering())
      return false;
    return true;
  }

  public boolean repOK_header_size_ordering() {
    if (!repOK_header())
      return false;
    if (!repOK_size())
      return false;
    if (!repOK_ordering())
      return false;
    return true;
  }

  public boolean repOK_acyclic_size_ordering() {
    if (!repOK_acyclic())
      return false;
    if (!repOK_size())
      return false;
    if (!repOK_ordering())
      return false;
    return true;
  }

}
