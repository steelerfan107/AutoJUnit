package disjset;

import java.util.BitSet;

public class DisjSet {

    /*
     * Builders ---------------------------
     */
    public DisjSet(int n) {
        if (n < 1)
            throw new IllegalArgumentException("The int n should be greater than 0");
        elements = new Record[n];
        size = n;
        makeSet();
    }

    private void makeSet() {
        for (int i=0; i<size; i++)
        {
            // Initially, all elements are in
            // their own set.
            elements[i] = new Record();
            elements[i].parent = i;
        }
    }

    // Returns representative of x's set
    private int find(int x) {
        // Finds the representative of the set
        // that x is an element of
        if (elements[x].parent!=x) {
            // if x is not the parent of itself
            // Then x is not the representative of
            // his set,
            elements[x].parent = find(elements[x].parent);

            // so we recursively call Find on its parent
            // and move i's node directly under the
            // representative of this set
        }

        return elements[x].parent;
    }

    // Unites the set that includes x and the set
    // that includes x
    public void union(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size)
            throw new IllegalArgumentException("The int x and y should be between 0 and " + (size - 1));
        // Find representatives of two sets
        int xRoot = find(x), yRoot = find(y);

        // Elements are in the same set, no need
        // to unite anything.
        if (xRoot == yRoot)
            return;

        // If x's rank is less than y's rank
        if (elements[xRoot].rank < elements[yRoot].rank)

            // Then move x under y  so that depth
            // of tree remains less
            elements[xRoot].parent = yRoot;

            // Else if y's rank is less than x's rank
        else if (elements[yRoot].rank < elements[xRoot].rank)

            // Then move y under x so that depth of
            // tree remains less
            elements[yRoot].parent = xRoot;

        else // if ranks are the same
        {
            // Then move y under x (doesn't matter
            // which one goes where)
            elements[yRoot].parent = xRoot;

            // And increment the the result tree's
            // rank by 1
            elements[xRoot].rank = elements[xRoot].rank + 1;
        }
    }
    /*
     * -----------------------------------------
     */

    // helper class
    private static class Record {

        private int parent;

        private int rank;

        public Record() {

        }

        public Record(Record rec) {
            parent = rec.parent;
            rank = rec.rank;
        }

    }

    // end of helper class

    private Record[] elements;

    private int size;

    // toString()
    // public String toString() {
    // Record[] mirror = new Record[size];
    // for (int i = 0; i < size; i++)
    // mirror[i] = new Record(elements[i]);
    //
    // StringBuffer str = new StringBuffer("[");
    //
    // for (int i = 0; i < size; i++)
    // if (mirror[i].parent != -1) {
    // int k = findForToString(i);
    // if (i == 0)
    // str.append("{" + i);
    // else
    // str.append(", {" + i);
    // mirror[i].parent = -1;
    // for (int j = i + 1; j < size; j++)
    // if ((mirror[j].parent != -1) && (findForToString(j) == k)) {
    // str.append(", " + j);
    // mirror[j].parent = -1;
    // }
    // str.append("}");
    // }
    //
    // str.append("]");
    // return str.toString();
    // }

    // a "find" method that does not use path compression; needed for toString()
    // in order not to modify the structure while printing it
    // private int findForToString(int el) {
    // int temp = el;
    // while (elements[temp].parent != temp)
    // temp = elements[temp].parent;
    // return temp;
    // }

    public boolean repOK_alldifferent() {
        int n = size - 1;
        // for (int n = 1; n < size; n++) // generates fewer candidates, but
        // slower
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j <= n; j++)
                if (elements[i] == elements[j])
                    return false;
        return true;
    }

    // methods used by Korat
    public boolean repOK_Complete() {
        if (!repOK_size())
            return false;

        if (!repOK_alldifferent())
            return false;

        if (!repOK_parentrank())
            return false;

        if (!repOK_roots())
            return false;

        return true;
    }

    public boolean repOK_size() {
        return size == elements.length;
    }

    public boolean repOK_parentrank() {
        BitSet seenParent = new BitSet(size);
        for (int i = 0; i < size; i++) {
            int parentID = elements[i].parent;
            if (parentID < 0 || parentID >= size)
                return false;
            if (parentID != i) {
                int parentRank = elements[parentID].rank;
                if (parentRank <= elements[i].rank)
                    return false;
                if (elements[i].rank == parentRank - 1)
                    seenParent.set(parentID);
            }
        }
        return true;
    }

    public boolean repOK_roots() {
        int numRoots = 0, numElRankZero = 0;
        BitSet seenParent = new BitSet(size);
        for (int i = 0; i < size; i++) {
            int parentID = elements[i].parent;
            if (parentID < 0 || parentID >= size)
                break;
            if (parentID != i) {
                int parentRank = elements[parentID].rank;
                if (parentRank <= elements[i].rank)
                    break;
                if (elements[i].rank == parentRank - 1)
                    seenParent.set(parentID);
            } else
                numRoots += 1;
        }

        for (int i = 0; i < size; i++)
            if (!seenParent.get(i) && elements[i].rank == 0)
                numElRankZero += 1;

        if (numRoots > numElRankZero)
            return false;
        return true;
    }

    public boolean repOK_size_alldifferent() {
        if (!repOK_size())
            return false;
        if (!repOK_alldifferent())
            return false;
        return true;
    }

    public boolean repOK_size_parentrank() {
        if (!repOK_size())
            return false;
        if (!repOK_parentrank())
            return false;
        return true;
    }

    public boolean repOK_size_roots() {
        if (!repOK_size())
            return false;
        if (!repOK_roots())
            return false;
        return true;
    }

    public boolean repOK_alldifferent_parentrank() {
        if (!repOK_alldifferent())
            return false;
        if (!repOK_parentrank())
            return false;
        return true;
    }

    public boolean repOK_alldifferent_roots() {
        if (!repOK_alldifferent())
            return false;
        if (!repOK_roots())
            return false;
        return true;
    }

    public boolean repOK_parentrank_roots() {
        if (!repOK_parentrank())
            return false;
        if (!repOK_roots())
            return false;
        return true;
    }

    public boolean repOK_size_alldifferent_parentrank() {
        if (!repOK_size())
            return false;
        if (!repOK_alldifferent())
            return false;
        if (!repOK_parentrank())
            return false;
        return true;
    }

    public boolean repOK_size_alldifferent_roots() {
        if (!repOK_size())
            return false;
        if (!repOK_alldifferent())
            return false;
        if (!repOK_roots())
            return false;
        return true;
    }

    public boolean repOK_size_parentrank_roots() {
        if (!repOK_size())
            return false;
        if (!repOK_parentrank())
            return false;
        if (!repOK_roots())
            return false;
        return true;
    }

    public boolean repOK_alldifferent_parentrank_roots() {
        if (!repOK_alldifferent())
            return false;
        if (!repOK_parentrank())
            return false;
        if (!repOK_roots())
            return false;
        return true;
    }
}
