package singlylinkedlist;

import java.util.Set;

public class SinglyLinkedList {

    private Entry header;

    private int size = 0;

    /*
     * Builders ---------------------------
     */
    public SinglyLinkedList(){
        header = new Entry();
        size =0;
    }

    public void add() {
        Entry current = header.next;
        Entry previous = header;

        while(current!=null){
            previous = current;
            current = current.next;
        }

        Entry n = new Entry();
        n.element = new SerializableObject();
        n.next = current;
        previous.next = n;
        size++;
    }

    /*
     * -----------------------------------------
     */

    public boolean isEmpty() {
        return size == 0 && header.next == null;
    }

    public int getSize() {
        return size;
    }

    public boolean repOK_Complete() {
        if (!repOK_header())
            return false;

        if (!repOK_acyclic())
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
        return true;
    }

    public boolean repOK_acyclic() {
        Set<Entry> visited = new java.util.HashSet<Entry>();
        visited.add(header);
        Entry current = header;

        while (true) {
            Entry next = current.next;
            if (next == null)
                break;

            if (next.element == null)
                return false;

            if (!visited.add(next))
                return false;

            current = next;
        }
        return true;
    }

    public boolean repOK_size() {
        Set<Entry> visited = new java.util.HashSet<>();
        visited.add(header);
        Entry current = header;

        while (true) {
            Entry next = current.next;
            if (next == null)
                break;

            if (next.element == null)
                break;

            if (!visited.add(next))
                break;

            current = next;
        }
        return visited.size() - 1 == size;
    }

    public boolean repOK_header_acyclic() {
        if (!repOK_header())
            return false;
        if (!repOK_acyclic())
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

    public boolean repOK_acyclic_size() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public String toString() {
        String res = "(";
        if (header != null) {
            Entry cur = header.next;
            while (cur != null && cur != header) {
                res += cur.toString();
                cur = cur.next;
            }
        }
        return res + ")";
    }

}
