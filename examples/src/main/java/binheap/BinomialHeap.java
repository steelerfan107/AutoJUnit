package binheap;

import java.util.HashSet;


@SuppressWarnings("unused")
public class BinomialHeap  {

    /*
     * Builders ---------------------------
     */
    public BinomialHeap() {
        Nodes = null;
        size = 0;
    }

    public void insert(int value) {
        BinomialHeapNode temp = new BinomialHeapNode(value);
        if (Nodes == null) {
            Nodes = temp;
            size = 1;
        } else {
            unionNodes(temp);
            size++;
        }
    }

    private void unionNodes(/* @ nullable @ */BinomialHeapNode binHeap) {
        merge(binHeap);

        BinomialHeapNode prevTemp = null, temp = Nodes, nextTemp = Nodes.sibling;

        while (nextTemp != null) {
            if ((temp.degree != nextTemp.degree) || ((nextTemp.sibling != null) && (nextTemp.sibling.degree == temp.degree))) {
                prevTemp = temp;
                temp = nextTemp;
            } else {
                if (temp.key <= nextTemp.key) {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                } else {
                    if (prevTemp == null) {
                        Nodes = nextTemp;
                    } else {
                        prevTemp.sibling = nextTemp;
                    }
                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }

            nextTemp = temp.sibling;
        }
    }

    private void merge(/* @ nullable @ */BinomialHeapNode binHeap) {
        BinomialHeapNode temp1 = Nodes, temp2 = binHeap;
        while ((temp1 != null) && (temp2 != null)) {
            if (temp1.degree == temp2.degree) {
                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            } else {
                if (temp1.degree < temp2.degree) {
                    if ((temp1.sibling == null) || (temp1.sibling.degree > temp2.degree)) {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    } else {
                        temp1 = temp1.sibling;
                    }
                } else {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;
                    if (tmp == Nodes) {
                        Nodes = temp1;
                    }
                }
            }
        }

        if (temp1 == null) {
            temp1 = Nodes;
            while (temp1.sibling != null) {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        }

    }
    /*
     * -----------------------------------------
     */

    // internal class BinomialHeapNode
    private static class BinomialHeapNode {

        public BinomialHeapNode(int k) {
            key = k;
            degree = 0;
            parent = null;
            sibling = null;
            child = null;
        }

        private int key; // element in current node

        // depth of the binomial tree having the current node as its root
        private int degree;

        // pointer to the parent of the current node
        private BinomialHeapNode parent;

        // pointer to the next binomial tree in the list
        private BinomialHeapNode sibling;

        // pointer to the first child of the current node
        private BinomialHeapNode child;

        public int getSize() {
            return (1 + ((child == null) ? 0 : child.getSize()) + ((sibling == null) ? 0
                    : sibling.getSize()));
        }

        private BinomialHeapNode reverse(BinomialHeapNode sibl) {
            BinomialHeapNode ret;
            if (sibling != null)
                ret = sibling.reverse(this);
            else
                ret = this;
            sibling = sibl;
            return ret;
        }

        public String toString() {
            BinomialHeapNode temp = this;
            String ret = "";
            while (temp != null) {
                ret += "(";
                if (temp.parent == null)
                    ret += "Parent: null";
                else
                    ret += "Parent: " + temp.parent.key;
                ret += "  Degree: " + temp.degree + "  Key: " + temp.key + ") ";
                if (temp.child != null)
                    ret += temp.child.toString();
                temp = temp.sibling;
            }
            if (parent == null)
                ret += " ";
            return ret;
        }

        // procedures used by Korat
        private boolean repCheckWithRepetitions(int key_, int degree_,
                Object parent_, HashSet<BinomialHeapNode> nodesSet) {

            BinomialHeapNode temp = this;

            int rightDegree = 0;
            if (parent_ == null) {
                while ((degree_ & 1) == 0) {
                    rightDegree += 1;
                    degree_ /= 2;
                }
                degree_ /= 2;
            } else
                rightDegree = degree_;

            while (temp != null) {
                if ((temp.degree != rightDegree) || (temp.parent != parent_)
                        || (temp.key < key_) || (nodesSet.contains(temp)))
                    return false;
                else {
                    nodesSet.add(temp);
                    if (temp.child == null) {
                        temp = temp.sibling;

                        if (parent_ == null) {
                            if (degree_ == 0)
                                return (temp == null);
                            while ((degree_ & 1) == 0) {
                                rightDegree += 1;
                                degree_ /= 2;
                            }
                            degree_ /= 2;
                            rightDegree++;
                        } else
                            rightDegree--;
                    } else {
                        boolean b = temp.child.repCheckWithRepetitions(
                                temp.key, temp.degree - 1, temp, nodesSet);
                        if (!b)
                            return false;
                        else {
                            temp = temp.sibling;

                            if (parent_ == null) {
                                if (degree_ == 0)
                                    return (temp == null);
                                while ((degree_ & 1) == 0) {
                                    rightDegree += 1;
                                    degree_ /= 2;
                                }
                                degree_ /= 2;
                                rightDegree++;
                            } else
                                rightDegree--;
                        }
                    }
                }
            }

            return true;
        }

        private boolean repCheckWithoutRepetitions(int key_,
                HashSet<Integer> keysSet, int degree_, // equal keys not allowed
                Object parent_, HashSet<BinomialHeapNode> nodesSet) {
            BinomialHeapNode temp = this;

            int rightDegree = 0;
            if (parent_ == null) {
                while ((degree_ & 1) == 0) {
                    rightDegree += 1;
                    degree_ /= 2;
                }
                degree_ /= 2;
            } else
                rightDegree = degree_;

            while (temp != null) {
                if ((temp.degree != rightDegree) || (temp.parent != parent_)
                        || (temp.key <= key_) || (nodesSet.contains(temp))
                        || (keysSet.contains(new Integer(temp.key)))) {
                    return false;
                } else {
                    nodesSet.add(temp);
                    keysSet.add(new Integer(temp.key));
                    if (temp.child == null) {
                        temp = temp.sibling;

                        if (parent_ == null) {
                            if (degree_ == 0)
                                return (temp == null);
                            while ((degree_ & 1) == 0) {
                                rightDegree += 1;
                                degree_ /= 2;
                            }
                            degree_ /= 2;
                            rightDegree++;
                        } else
                            rightDegree--;
                    } else {
                        boolean b = temp.child.repCheckWithoutRepetitions(
                                temp.key, keysSet, temp.degree - 1, temp,
                                nodesSet);
                        if (!b)
                            return false;
                        else {
                            temp = temp.sibling;

                            if (parent_ == null) {
                                if (degree_ == 0)
                                    return (temp == null);
                                while ((degree_ & 1) == 0) {
                                    rightDegree += 1;
                                    degree_ /= 2;
                                }
                                degree_ /= 2;
                                rightDegree++;
                            } else
                                rightDegree--;
                        }
                    }
                }
            }

            return true;
        }

        public boolean repOk(int size) {
            // replace 'repCheckWithoutRepetitions' with
            // 'repCheckWithRepetitions' if you don't want to allow equal keys
            return repCheckWithRepetitions(0, size, null,
                    new HashSet<BinomialHeapNode>());
        }

        boolean checkDegree(int degree) {
            for (BinomialHeapNode current = this.child; current != null; current = current.sibling) {
                degree--;
                if (current.degree != degree)
                    return false;
                if (!current.checkDegree(degree))
                    return false;
            }
            return (degree == 0);
        }

        boolean isHeapified() {
            for (BinomialHeapNode current = this.child; current != null; current = current.sibling) {
                if (!(key <= current.key))
                    return false;
                if (!current.isHeapified())
                    return false;
            }
            return true;
        }

        boolean isTree(java.util.Set<NodeWrapper> visited,
                BinomialHeapNode parent) {
            if (this.parent != parent)
                return false;
            for (BinomialHeapNode current = this.child; current != null; current = current.sibling) {
                if (!visited.add(new NodeWrapper(current)))
                    return false;
                if (!current.isTree(visited, this))
                    return false;
            }
            return true;
        }
    }

    private static final class NodeWrapper {
        BinomialHeapNode node;

        NodeWrapper(BinomialHeapNode n) {
            this.node = n;
        }

        public boolean equals(Object o) {
            if (!(o instanceof NodeWrapper))
                return false;
            return node == ((NodeWrapper) o).node;
        }

        public int hashCode() {
            return System.identityHashCode(node);
        }
    }

    // end of helper class BinomialHeapNode

    private BinomialHeapNode Nodes;

    private int size;

    public int getSize() {
        return size;
    }

    public String toString() {
        if (Nodes == null)
            return size + "\n()\n";
        else
            return size + "\n" + Nodes.toString();
    }

    public boolean repOK_degrees() {
        int degree_ = size;
        int rightDegree = 0;
        for (BinomialHeapNode current = Nodes; current != null; current = current.sibling) {
            if (degree_ == 0)
                return false;
            while ((degree_ & 1) == 0) {
                rightDegree++;
                degree_ /= 2;
            }
            if (current.degree != rightDegree)
                return false;
            if (!current.checkDegree(rightDegree))
                return false;
            rightDegree++;
            degree_ /= 2;
        }
        return (degree_ == 0);
    }

    public boolean repOK_heapified() {
        for (BinomialHeapNode current = Nodes; current != null; current = current.sibling) {
            if (!current.isHeapified())
                return false;
        }
        return true;
    }

    public boolean repOK_Complete() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        // checks that the degrees of all trees are binomial
        if (!repOK_degrees())
            return false;
        // checks that keys are heapified
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty() {
        if (size == 0 && Nodes != null)
            return false;
        if (size != 0 && Nodes == null)
            return false;
        return true;
    }

    public boolean repOK_acyclic() {
        // checks that list of trees has no cycles
        java.util.Set<NodeWrapper> visited = new java.util.HashSet<>();
        for (BinomialHeapNode current = Nodes; current != null; current = current.sibling) {
            // checks that the list has no cycle
            if (!visited.add(new NodeWrapper(current)))
                return false;
            if (!current.isTree(visited, null))
                return false;
        }
        return true;
    }

    public boolean repOK_size() {
        java.util.Set<NodeWrapper> visited = new java.util.HashSet<>();
        for (BinomialHeapNode current = Nodes; current != null; current = current.sibling) {
            // checks that the list has no cycle
            if (!visited.add(new NodeWrapper(current)))
                break;
            if (!current.isTree(visited, null))
                break;
        }
        // checks that the total size is consistent
        if (visited.size() != size)
            return false;
        return true;
    }

    public boolean repOK_empty_acyclic() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
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

    public boolean repOK_empty_degrees() {
        if (!repOK_empty())
            return false;
        if (!repOK_degrees())
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

    public boolean repOK_acyclic_size() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_acyclic_degrees() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_acyclic_heapified() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_size_degrees() {
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_size_heapified() {
        if (!repOK_size())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_degrees_heapified() {
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_acyclic_size() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_empty_acyclic_degrees() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_empty_acyclic_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_size_degrees() {
        if (!repOK_empty())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_empty_size_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_degrees_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_acyclic_size_degrees() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_acyclic_size_heapified() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_acyclic_degrees_heapified() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_size_degrees_heapified() {
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_acyclic_size_degrees() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        return true;
    }

    public boolean repOK_empty_acyclic_size_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_acyclic_degrees_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_empty_size_degrees_heapified() {
        if (!repOK_empty())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

    public boolean repOK_acyclic_size_degrees_heapified() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_degrees())
            return false;
        if (!repOK_heapified())
            return false;
        return true;
    }

}
