package binarytree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class BinaryTree {

    /*
     * Builders ---------------------------
     */
    public BinaryTree() {

    }

    public void insert(int value) {
        Node z = new Node();
        z.info = value;

        Node y = null;
        for (Node x = root; x != null;) {
            y = x;
            if (x.info>z.info)
                x = x.left;
            else
                x = x.right;
        }

        z.left = z.right = null;

        if (y==null) {
            root = z;
        } else {

            if (y.info>z.info)	{ y.left = z; }
            else 				{ y.right = z; }

        }

        size++;

    }
    /*
     * -----------------------------------------
     */

    public static class Node {
        private int info;
        private Node left;
        private Node right;
    }

    private Node root;
    private int size;

    public boolean repOK_Complete() {
        if (!repOK_root())
            return false;
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_root() {
        if (root == null)
            return size == 0;
        return true;
    }

    public boolean repOK_acyclic() {
        // checks that tree has no cycle
        if (root == null)
            return true;
        Set<Node> visited = new HashSet<>();
        visited.add(root);
        LinkedList<Node> workList = new LinkedList<>();
        workList.add(root);
        while (!workList.isEmpty()) {
            Node current = (Node) workList.removeFirst();
            if (current.left != null) {
                if (!visited.add(current.left))
                    return false;
                workList.add(current.left);
            }
            if (current.right != null) {
                if (!visited.add(current.right))
                    return false;
                workList.add(current.right);
            }
        }
        return true;
    }

    public boolean repOK_size() {
        if (root == null)
            return size == 0;
        Set<Node> visited = new HashSet<>();
        visited.add(root);
        LinkedList<Node> workList = new LinkedList<>();
        workList.add(root);
        while (!workList.isEmpty()) {
            Node current = (Node) workList.removeFirst();
            if (current.left != null) {
                if (!visited.add(current.left))
                    break;
                workList.add(current.left);
            }
            if (current.right != null) {
                if (!visited.add(current.right))
                    break;
                workList.add(current.right);
            }
        }
        // checks that size is consistent
        return (visited.size() == size);
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

    public boolean repOK_acyclic_size() {
        if (!repOK_acyclic())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

}
