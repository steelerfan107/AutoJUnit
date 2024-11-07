package fibheap;

import java.util.HashSet;
import java.util.Set;

public class FibonacciHeap {

     private static class FibonacciHeapNode  {

        private int key; // the key of the node

         private int degree; // number of children of the node

         private FibonacciHeapNode parent; // the parent of the node

         private FibonacciHeapNode child; // a child of the node

         private FibonacciHeapNode left; // the node to the left of the current

        // node

         private FibonacciHeapNode right; // the node to the right of the current

        // node

        public FibonacciHeapNode(int c) {key = c; right=this; left=this;}

         private int mark; // a special mark

        public int getSize(FibonacciHeapNode fibNode) {
            int result = 1;
            if (child != null)
                result += child.getSize(child);
            if (right != fibNode)
                result += right.getSize(fibNode);
            return result;
        }

        public boolean contains(FibonacciHeapNode start, FibonacciHeapNode node) {
            FibonacciHeapNode temp = start;
            do {
                if (temp == node)
                    return true;
                else
                    temp = temp.right;
            } while (temp != start);
            return false;
        }

        public String toString() {
            FibonacciHeapNode temp = this;
            String ret = "";
            do {
                ret += "(";
                if (temp.parent == null)
                    ret += "Parent: null";
                else
                    ret += "Parent: " + temp.parent.key;
                ret += "  Key: " + temp.key + "  Degree: " + temp.degree + ") \n";
                if (temp.child != null)
                    ret += temp.child.toString();
                temp = temp.right;
            } while (temp != this);
            if (parent == null)
                ret += "\n";
            return ret;
        }

        private boolean isEqualTo(FibonacciHeapNode node) {
            FibonacciHeapNode tempThis = this;
            FibonacciHeapNode tempThat = node;
            do {
                if ((tempThis.key != tempThat.key)
                    || (tempThis.degree != tempThat.degree)
                    || (tempThis.mark != tempThat.mark)
                    || ((tempThis.child != null) && (tempThat.child == null))
                    || ((tempThis.child == null) && (tempThat.child != null))
                    || ((tempThis.child != null) && (!tempThis.child.isEqualTo(tempThat.child))))
                    return false;
                else {
                    tempThis = tempThis.right;
                    tempThat = tempThat.right;
                }
            } while (tempThis.right != this);
            return true;
        }

        public boolean equals(Object that) {
            if ((!(that instanceof FibonacciHeapNode)) || (that == null))
                return false;
            return isEqualTo((FibonacciHeapNode) that);
        }

        private FibonacciHeapNode findKey(FibonacciHeapNode start, int k) {
            FibonacciHeapNode temp = start;
            do
                if (temp.key == k)
                    return temp;
                else {
                    FibonacciHeapNode child_temp = null;
                    if ((temp.key < k) && (temp.child != null))
                        child_temp = temp.child.findKey(temp.child, k);
                    if (child_temp != null)
                        return child_temp;
                    else
                        temp = temp.right;
                } while (temp != start);
            return null;
        }

        private boolean numberOfChildrenIsCorrect() {
            if ((child == null) || (degree == 0))
                return ((child == null) && (degree == 0));
            else {
                HashSet<Integer> child_set = new HashSet<Integer>();
                FibonacciHeapNode temp_child = child;
                for (int i = 0; i < degree; i++) {
                    // Would crash for some reason... (I got a segmentation fault)
                    // if (!child_set.add(temp_child)) return false; // found a
                    // loop!
                    if (!child_set.add(temp_child.hashCode()))
                        return false; // found a loop!
                    temp_child = temp_child.right;
                    if (temp_child == null)
                        return false;
                }
                return (temp_child == child);
            }
        }

        // used by Korat
        public boolean repOK(FibonacciHeapNode start, FibonacciHeapNode par, int k,
                             HashSet<FibonacciHeapNode> set) {
            if ((!set.add(this)) || (parent != par) || (key < k)
                || (!numberOfChildrenIsCorrect()) || (right == null)
                || (right.left != this) || (left == null)
                || (left.right != this))
                return false;
            boolean b = true;
            if (child != null)
                b = child.repOK(child, this, key, new HashSet<>());
            if (!b)
                return false;
            if (right == start)
                return true;
            else
                return right.repOK(start, par, k, set);
        }

        private int numberOfChildren() {
            if (child == null)
                return 0;
            int num = 1;
            for (FibonacciHeapNode current = child.right; current != child; current = current.right) {
                num++;
            }
            return num;
        }

    }

    /*
     * Builders ---------------------------
     */
    public FibonacciHeap() {
        Nodes = null;
        minNode = null;
        size = 0;
    }

    public void insert(int c) {
        FibonacciHeapNode n = new FibonacciHeapNode(c);
        size++;
        Nodes = insert(n);
        if (minNode == null) minNode = Nodes;
        else 			 minNode = (minNode.key > c) ? Nodes : minNode;
    }

    private FibonacciHeapNode insert(FibonacciHeapNode x) {
        if (Nodes == null) {
            x.left = x;
            x.right = x;
        } else {
            Nodes.left.right = x;
            x.right = Nodes;
            x.left = Nodes.left;
            Nodes.left = x;
        }
        return x;
    }

    public FibonacciHeapNode removeMin() {
        FibonacciHeapNode z = minNode;
        if (z != null) {
            int i = z.degree;
            FibonacciHeapNode x = z.child;
            while (i > 0) {
                FibonacciHeapNode nextChild = x.right;
                x.left.right = x.right;
                x.right.left = x.left;
                x.left = minNode;
                x.right = minNode.right;
                minNode.right = x;
                x.right.left = x;
                x.parent = null;
                x = nextChild;
                i--;
            }
            z.left.right = z.right;
            z.right.left = z.left;
            if (z == z.right) {
                minNode = null;
            } else {
                minNode = z.right;
                consolidate();
            }
            // Update Nodes
            Nodes = minNode;
            size--;
        }
        return z;
    }

    private void consolidate() {
        int D = size + 1;
        FibonacciHeapNode A[] = new FibonacciHeapNode[D];

        {
            int i = 0;
            for (; i < D;) {
                A[i] = null;
                i++;
            }
        }

        int k = 0;
        FibonacciHeapNode x = minNode;
        if (x != null) {
            k++;
            for (x = x.right; x != minNode; x = x.right) {
                k++;
            }
        }
        while (k > 0) {
            int d = x.degree;
            FibonacciHeapNode rightNode = x.right;
            while (A[d] != null) {
                FibonacciHeapNode y = A[d];
                if (x.key > y.key) {
                    FibonacciHeapNode temp = y;
                    y = x;
                    x = temp;
                }
                link(y, x);
                A[d] = null;
                d++;
            }

            A[d] = x;
            x = rightNode;
            k--;
        }

        minNode = null;
        {
            int i = 0;
            for (; i < D;) {
                if (A[i] != null)
                    if (minNode != null) {
                        A[i].left.right = A[i].right;
                        A[i].right.left = A[i].left;
                        A[i].left = minNode;
                        A[i].right = minNode.right;
                        minNode.right = A[i];
                        A[i].right.left = A[i];
                        if (A[i].key < minNode.key) {
                            minNode = A[i];
                        }
                    } else {
                        minNode = A[i];
                    }
                i++;
            }
        }
    }

    private void link(FibonacciHeapNode node1, FibonacciHeapNode node2) {
        node1.left.right = node1.right;
        node1.right.left = node1.left;
        node1.parent = node2;
        if (node2.child == null) {
            node2.child = node1;
            node1.right = node1;
            node1.left = node1;
        } else {
            node1.left = node2.child;
            node1.right = node2.child.right;
            node2.child.right = node1;
            node1.right.left = node1;
        }
        node2.degree++;
    }
    /*
     * -----------------------------------------
     */
    private FibonacciHeapNode Nodes;

    private FibonacciHeapNode minNode;

    private int size;

    private int getMin() {
        FibonacciHeapNode temp = Nodes;
        int min = Nodes.key;
        do {
            if (temp.key < min)
                min = temp.key;
            temp = temp.right;
        } while (temp != Nodes);
        return min;
    }

    public String toString() {
        if (Nodes == null)
            return size + "\n()\n";
        else
            return size + "\n" + Nodes.toString();
    }

    private boolean checkDegrees(FibonacciHeapNode node) {
        FibonacciHeapNode current = node;
        do {
            if (current.numberOfChildren() != current.degree)
                return false;
            if (current.child != null)
                if (!checkDegrees(current.child))
                    return false;
            current = current.right;
        } while (current != node);
        return true;
    }

    private boolean isStructural(Set<FibonacciHeapNode> visited, FibonacciHeapNode node, FibonacciHeapNode parent) {
        FibonacciHeapNode current = node;
        do {
            if (current.parent != parent)
                return false;
            // if (!visited.add(new IdentityWrapper(current)))
            if (!visited.add(current))
                return false;
            if ((current.child != null)
                && (!isStructural(visited, current.child, current)))
                return false;
            if (current.right == null)
                return false;
            if (current.right.left != current)
                return false;
            current = current.right;
        } while (current != node);
        return true;
    }

    void touch(int key) {
    }
    private boolean checkHeapified(FibonacciHeapNode node) {
        touch(node.key);
        if (node.child == null)
            return true;
        FibonacciHeapNode current = node.child;
        do {
            if (current.key < node.key)
                return false;
            if (!checkHeapified(current))
                return false;
            current = current.right;
        } while (current != node.child);
        return true;
    }

    public boolean repOK_heapified() {
        if (Nodes == null)
            return true;
        FibonacciHeapNode current = Nodes;
        do {
            if (!checkHeapified(current))
                return false;
            current = current.right;
        } while (current != Nodes);
        return true;
    }

    public boolean repOK_Complete() {
        if (!repOK_empty())
            return false;
        // checks that structural constrainst are satisfied
        if (!repOK_structural())
            return false;
        // checks that minNode is in the list
        if (!repOK_minNode())
            return false;
        // checks that the total size is consistent
        if (!repOK_size())
            return false;
        // checks that the degrees of all trees are fibonacci
        if (!repOK_degrees())
            return false;
        // checks that keys are heapified
        if (!repOK_heapified())
            return false;
        // checks that getMin is consistent with minNode
        if (!repOK_getMin())
            return false;
        return true;
    }

    public boolean repOK_empty() {
        if ((Nodes == null) || (minNode == null))
            return ((Nodes == null) && (minNode == null) && (size == 0));
        return true;
    }

    public boolean repOK_structural() {
        if ((Nodes == null) || (minNode == null))
            return true;
        Set<FibonacciHeapNode> visited = new HashSet<>();
        if (!isStructural(visited, Nodes, null))
            return false;
        return true;
    }

    public boolean repOK_minNode() {
        if ((Nodes == null) || (minNode == null))
            return true;
        if (!Nodes.contains(Nodes, minNode))
            return false;
        return true;
    }

    public boolean repOK_size() {
        if (Nodes == null)
            return true;
        Set<FibonacciHeapNode> visited = new HashSet<>();
        isStructural(visited, Nodes, null);
        if (visited.size() != size)
            return false;
        return true;
    }

    public boolean repOK_degrees() {
        if (Nodes == null)
            return true;
        if (!checkDegrees(Nodes))
            return false;
        return true;
    }

    public boolean repOK_getMin() {
        if (Nodes == null || minNode == null)
            return true;
        if (getMin() != minNode.key)
            return false;
        return true;
    }

    public boolean repOK_degrees_empty() {
        if (!repOK_empty())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin() {
        if (!repOK_getMin())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified() {
        if (!repOK_heapified())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_degrees_minNode() {
        if (!repOK_minNode())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_degrees_size() {
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_degrees_structural() {
        if (!repOK_structural())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_minNode() {
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_empty_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_getMin_minNode() {
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_getMin_size() {
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_getMin_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_heapified_minNode() {
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_heapified_size() {
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_heapified_structural() {
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_minNode_size() {
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_minNode_structural() {
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_size_structural() {
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_minNode() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_minNode() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified_minNode() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_minNode() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified_minNode() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_minNode_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_minNode_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified_minNode() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified_size() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_minNode_size() {
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_getMin_minNode_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_size_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_heapified_minNode_size() {
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_heapified_minNode_structural() {
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_heapified_size_structural() {
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_minNode_size_structural() {
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_heapified() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_minNode() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified_minNode() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified_minNode() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_minNode_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified_minNode() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_minNode_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_minNode_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified_minNode_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified_minNode_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_minNode_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified_minNode_size() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified_minNode_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified_size_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_minNode_size_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_heapified_minNode_size_structural() {
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_heapified_minNode() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_heapified_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_heapified_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_minNode_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_minNode_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified_minNode_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified_minNode_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified_minNode_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_minNode_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_heapified_minNode_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_getMin_heapified_minNode_size_structural() {
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_heapified_minNode_size() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_heapified_minNode_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_heapified_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_getMin_minNode_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_empty_heapified_minNode_size_structural() {
if (!repOK_degrees())
            return false;
        if (!repOK_empty())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_degrees_getMin_heapified_minNode_size_structural() {
        if (!repOK_degrees())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }

    public boolean repOK_empty_getMin_heapified_minNode_size_structural() {
        if (!repOK_empty())
            return false;
        if (!repOK_getMin())
            return false;
        if (!repOK_heapified())
            return false;
        if (!repOK_minNode())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_structural())
            return false;
        return true;
    }
}