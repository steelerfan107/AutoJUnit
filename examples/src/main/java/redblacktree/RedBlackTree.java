package redblacktree;

import java.util.Set;

@SuppressWarnings("unchecked")
public class RedBlackTree {

    private Node root = null;

    private int size = 0;

    private static final int RED = 0;

    private static final int BLACK = 1;

    private static class Node  {

        int key;

        int value;

        Node left = null;

        Node right = null;

        Node parent;

        int color = BLACK;

    }

    /*
     * Builders ---------------------------
     */
    public RedBlackTree() {
        root = null;
        size = 0;
    }

    public void insert(int newKey) {
        Node z = new Node();
        z.key = newKey;

        Node y = null;
        for (Node x = root; x != null;) {
            y = x;
            if (x.key==z.key)
                return;
            if (x.key>z.key)
                x = x.left;
            else
                x = x.right;
        }

        z.parent = y;
        z.left = z.right = null;

        if (y==null) {
            root = z;
        } else {
            if (y.key==z.key)
                return;

            z.color = RED;

            if (y.key>z.key)	{ y.left = z; }
            else 				{ y.right = z; }

            insertFixUp(z);
        }

        size++;

    }

    private final Node parentOf(Node n) 					{ return n==null ? null : n.parent; }
    private final Node leftOf(Node n) 					{ return n==null ? null : n.left; }
    private final Node rightOf(Node n) 					{ return n==null ? null : n.right; }
    private final int colorOf(Node n) 				{ return n==null ? BLACK : n.color; }
    private final void setColor(Node n, int color)	{ if (n!=null) n.color = color; }

    private void insertFixUp(Node z) {
        while (z != null && z != root && z.parent.color == RED) {
            if (parentOf(z) == leftOf(parentOf(parentOf(z)))) {
                Node y = rightOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    z = parentOf(parentOf(z));
                } else {
                    if (z == rightOf(parentOf(z))) {
                        z = parentOf(z);
                        rotateLeft(z);
                    }
                    setColor(parentOf(z), BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    if (parentOf(parentOf(z)) != null)
                        rotateRight(parentOf(parentOf(z)));
                }
            } else {
                Node y = leftOf(parentOf(parentOf(z)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(z), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    z = parentOf(parentOf(z));
                } else {
                    if (z == leftOf(parentOf(z))) {
                        z = parentOf(z);
                        rotateRight(z);
                    }
                    setColor(parentOf(z),  BLACK);
                    setColor(parentOf(parentOf(z)), RED);
                    if (parentOf(parentOf(z)) != null)
                        rotateLeft(parentOf(parentOf(z)));
                }
            }
        }
        root.color = BLACK;
    }

    /**
     * From CLR.
     */
    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;
        y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x.parent.left == x)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    /**
     * From CLR.
     */
    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null)
            y.right.parent = x;
        y.parent = x.parent;
        if (x.parent == null)
            root = y;
        else if (x.parent.right == x)
            x.parent.right = y;
        else
            x.parent.left = y;
        y.right = x;
        x.parent = y;
    }
    /*
     * -----------------------------------------
     */

    // ------------------------ repOK ---------------------------//
    public boolean repOK_Complete() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        return repOK_keysAndValues();
    }

    public boolean repOK_root() {
        if (root == null)
            return size == 0;
        // RootHasNoParent
        if (root.parent != null)
            return debug("RootHasNoParent");
        return true;
    }

    public boolean repOK_acyclic() {
        if (root == null)
            return true;
        Set visited = new java.util.HashSet();
        visited.add(new Wrapper(root));
        java.util.LinkedList workList = new java.util.LinkedList();
        workList.add(root);
        while (!workList.isEmpty()) {
            Node current = (Node) workList.removeFirst();
            // Acyclic
            // // if (!visited.add(new Wrapper(current)))
            // // return debug("Acyclic");
            // Parent Definition
            Node cl = current.left;
            if (cl != null) {
                if (!visited.add(new Wrapper(cl)))
                    return debug("Acyclic");
                if (cl.parent != current)
                    return debug("parent_Input1");
                workList.add(cl);
            }
            Node cr = current.right;
            if (cr != null) {
                if (!visited.add(new Wrapper(cr)))
                    return debug("Acyclic");
                if (cr.parent != current)
                    return debug("parent_Input2");
                workList.add(cr);
            }
        }
        return true;
    }

    public boolean repOK_size() {
        if (root == null)
            return true;
        Set visited = new java.util.HashSet();
        visited.add(new Wrapper(root));
        java.util.LinkedList workList = new java.util.LinkedList();
        workList.add(root);
        while (!workList.isEmpty()) {
            Node current = (Node) workList.removeFirst();
            // Acyclic
            // // if (!visited.add(new Wrapper(current)))
            // // return debug("Acyclic");
            // Parent Definition
            Node cl = current.left;
            if (cl != null) {
                if (!visited.add(new Wrapper(cl)))
                    return debug("Acyclic");
                if (cl.parent != current)
                    return debug("parent_Input1");
                workList.add(cl);
            }
            Node cr = current.right;
            if (cr != null) {
                if (!visited.add(new Wrapper(cr)))
                    return debug("Acyclic");
                if (cr.parent != current)
                    return debug("parent_Input2");
                workList.add(cr);
            }
        }
        if (visited.size() != size)
            return debug("SizeOk");
        return true;
    }
    public boolean repOK_colors() {
        if (root == null)
            return true;
        // RedHasOnlyBlackChildren
        java.util.LinkedList workList = new java.util.LinkedList();
        workList.add(root);
        while (!workList.isEmpty()) {
            Node current = (Node) workList.removeFirst();
            Node cl = current.left;
            Node cr = current.right;
            if (current.color == RED) {
                if (cl != null && cl.color == RED)
                    return debug("RedHasOnlyBlackChildren1");
                if (cr != null && cr.color == RED)
                    return debug("RedHasOnlyBlackChildren2");
            }
            if (cl != null)
                workList.add(cl);
            if (cr != null)
                workList.add(cr);
        }
        // SimplePathsFromRootToNILHaveSameNumberOfBlackNodes
        int numberOfBlack = -1;
        workList = new java.util.LinkedList();
        workList.add(new Pair(root, 0));
        while (!workList.isEmpty()) {
            Pair p = (Pair) workList.removeFirst();
            Node e = p.e;
            int n = p.n;
            if (e != null && e.color == BLACK)
                n++;
            if (e == null) {
                if (numberOfBlack == -1)
                    numberOfBlack = n;
                else if (numberOfBlack != n)
                    return debug("SimplePathsFromRootToNILHaveSameNumberOfBlackNodes");
            } else {
                workList.add(new Pair(e.left, n));
                workList.add(new Pair(e.right, n));
            }
        }
        return true;
    }

    public boolean repOK_keysAndValues() {
        if (root == null)
            return true;
        // BST1 and BST2
        // this was the old way of determining if the keys are ordered
        // java.util.LinkedList workList = new java.util.LinkedList();
        // workList = new java.util.LinkedList();
        // workList.add(root);
        // while (!workList.isEmpty()) {
        // Entry current = (Entry)workList.removeFirst();
        // Entry cl = current.left;
        // Entry cr = current.right;
        // if (current.key==current.key) ;
        // if (cl != null) {
        // if (compare(current.key, current.maximumKey()) <= 0)
        // return debug("BST1");
        // workList.add(cl);
        // }
        // if (cr != null) {
        // if (compare(current.key, current.minimumKey()) >= 0)
        // return debug("BST2");
        // workList.add(cr);
        // }
        // }
        // this is the new (Alex's) way to determine if the keys are ordered
        if (!orderedKeys(root, null, null))
            return debug("BST");
        // touch values
        java.util.LinkedList workList = new java.util.LinkedList();
        workList.add(root);
        while (!workList.isEmpty()) {
            Node current = (Node) workList.removeFirst();

            if (current.left != null)
                workList.add(current.left);
            if (current.right != null)
                workList.add(current.right);
        }
        return true;
    }

    public boolean repOK_root_acyclic() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        return true;
    }

    public boolean repOK_root_size() {
        if (!repOK_root())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_root_colors() {
        if (!repOK_root())
            return false;
        if (!repOK_colors())
            return false;
        return true;
    }

    public boolean repOK_root_keysAndValues() {
        if (!repOK_root())
            return false;
        if (!repOK_keysAndValues())
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

    public boolean repOK_acyclic_colors() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_colors())
            return false;
        return true;
    }

    public boolean repOK_acyclic_keysAndValues() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_size_colors() {
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        return true;
    }

    public boolean repOK_size_keysAndValues() {
        if (!repOK_size())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_colors_keysAndValues() {
        if (!repOK_colors())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_root_acyclic_size() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_root_acyclic_colors() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_colors())
            return false;
        return true;
    }

    public boolean repOK_root_acyclic_keysAndValues() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_root_size_colors() {
        if (!repOK_root())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        return true;
    }

    public boolean repOK_root_size_keysAndValues() {
        if (!repOK_root())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_root_colors_keysAndValues() {
        if (!repOK_root())
            return false;
        if (!repOK_colors())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_acyclic_size_colors() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        return true;
    }

    public boolean repOK_acyclic_size_keysAndValues() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_acyclic_colors_keysAndValues() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_colors())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_size_colors_keysAndValues() {
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_root_acyclic_size_colors() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        return true;
    }

    public boolean repOK_root_acyclic_size_keysAndValues() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_root_size_colors_keysAndValues() {
        if (!repOK_root())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_root_acyclic_colors_keysAndValues() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_colors())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    public boolean repOK_acyclic_size_colors_keysAndValues() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        if (!repOK_colors())
            return false;
        if (!repOK_keysAndValues())
            return false;
        return true;
    }

    private boolean orderedKeys(Node e, Object min, Object max) {
        //if (e.key == -1)
        //    return false;
        if (((min != null) && (compare(e.key, min) <= 0))
                || ((max != null) && (compare(e.key, max) >= 0)))
            return false;
        if (e.left != null)
            if (!orderedKeys(e.left, min, e.key))
                return false;
        if (e.right != null)
            if (!orderedKeys(e.right, e.key, max))
                return false;
        return true;
    }

    private final boolean debug(String s) {
        // System.out.println(s);
        return false;
    }

    private final class Pair {
        Node e;

        int n;

        Pair(Node e, int n) {
            this.e = e;
            this.n = n;
        }
    }

    private static final class Wrapper {
        Node e;

        Wrapper(Node e) {
            this.e = e;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Wrapper))
                return false;
            return e == ((Wrapper) obj).e;
        }

        public int hashCode() {
            return System.identityHashCode(e);
        }
    }

    private int compare(Object k1, Object k2) {
        return ((Comparable) k1).compareTo(k2);
    }
}
