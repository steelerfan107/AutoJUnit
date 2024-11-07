package doublylinkedlist;

import java.util.Set;

public class DoublyLinkedList {

    private Entry header;

    private int size = 0;

    /*
     * Builders ---------------------------
     */
    public DoublyLinkedList() {
        header = new Entry();
        header.element = null;
        header.next = header;
        header.previous = header;
        size = 0;
    }

    public void add() {
        Entry n = new Entry();
        n.element = new ListObject();
        n.next = header;
        n.previous = header.previous;
        header.previous.next = n;
        header.previous = n;
        size++;
    }
    /*
     * -----------------------------------------
     */

    public boolean repOK_Complete() {

        if (!repOK_header())
            return false;

        if (!repOK_cyclic())
            return false;

        if (!repOK_size())
            return false;

        return true;
    }

    public boolean repOK_header() {
        if (header == null)
            return false;
        if (header.element != null)
            return false;
        if (size == 0)
            return header.next == header && header.previous == header;
        return true;
    }

    public boolean repOK_cyclic() {
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
            return false; // // maybe not needed (also in SortedList.java)
        return true;
    }

    public boolean repOK_size() {
        Set<Entry> visited = new java.util.HashSet<>();
        visited.add(header);
        Entry current = header;
        while (true) {
            current = current.next;
            if (!visited.add(current))
                break;
        }
        return visited.size()-1 == size;
    }

    public boolean repOK_header_cyclic() {
        if (!repOK_header())
            return false;
        if (!repOK_cyclic())
            return false;
        return true;
    }

    public boolean repOK_header_size() {
        if (!repOK_header())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_cyclic_size() {
        if (!repOK_cyclic())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public String toString() {
        String res = "";
        Entry cur = this.header;
        while (cur != null) {
            res += cur.toString();
            cur = cur.next;
            if (cur == header)
                break;
        }
        return res;
    }

}
