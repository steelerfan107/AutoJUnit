package searchtree;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@SuppressWarnings("unchecked")
public class SearchTree {

  private static class Node {
    Node left; // left child

    Node right; // right child

    int info; // data

    Node(Node left, Node right, int info) {
      this.left = left;
      this.right = right;
      this.info = info;
    }

    Node(int info) {
      this.info = info;
    }

    public Node() {

    }

    public String toString() {
      Set visited = new HashSet();
      visited.add(this);
      return toString(visited);
    }

    private String toString(Set visited) {
      StringBuffer buf = new StringBuffer();
      // buf.append(" ");
      // buf.append(System.identityHashCode(this));
      buf.append(" {");
      if (left != null)
        if (visited.add(left))
          buf.append(left.toString(visited));
        else
          buf.append("!tree");

      buf.append("" + this.info + "");

      if (right != null)
        if (visited.add(right))
          buf.append(right.toString(visited));
        else
          buf.append("!tree");
      buf.append("} ");
      return buf.toString();
    }

    public boolean equals(Object that) {
      if (!(that instanceof Node))
        return false;
      Node n = (Node) that;
      // if (this.info.compareTo(n.info) != 0)
      if (this.info > (n.info))
        return false;
      boolean b = true;
      if (left == null)
        b = b && (n.left == null);
      else
        b = b && (left.equals(n.left));
      if (right == null)
        b = b && (n.right == null);
      else
        b = b && (right.equals(n.right));
      return b;
    }

  }

  private Node root; // root node

  private int size; // number of nodes in the tree

  /*
   * Builders ---------------------------
   */
  public SearchTree() {

  }

  public void insert(int value) {
    Node z = new Node();
    z.info = value;

    Node y = null;
    for (Node x = root; x != null;) {
      y = x;
      if (x.info==z.info)
        return;

      if (x.info>z.info)
        x = x.left;
      else
        x = x.right;
    }

    z.left = z.right = null;

    if (y==null) {
      root = z;
    } else {
      if (y.info==z.info)
        return;

      if (y.info>z.info)	{ y.left = z; }
      else 				{ y.right = z; }

    }

    size++;

  }
  /*
   * -----------------------------------------
   */
  public int size() {
    return size;
  }

  public boolean repOK_Complete() {
    // checks that empty tree has size zero
    if (root == null)
      return repOK_empty();
    // checks that the input is a tree
    if (!repOK_acyclic())
      return false;
    // checks that size is consistent
    if (!repOK_size())
      return false;
    // checks that data is ordered
    if (!repOK_ordered())
      return false;
    return true;
  }

  public boolean repOK_empty() {
    if (root == null)
      return size == 0;
    return true;
  }

  public boolean repOK_acyclic() {
    if (root == null)
      return true;
    Set<Node> visited = new HashSet<>();
    visited.add(root);
    LinkedList<Node> workList = new LinkedList<>();
    workList.add(root);
    while (!workList.isEmpty()) {
      Node current = (Node) workList.removeFirst();
      if (current.left != null) {
        // checks that the tree has no cycle
        if (!visited.add(current.left))
          return false;
        workList.add(current.left);
      }
      if (current.right != null) {
        // checks that the tree has no cycle
        if (!visited.add(current.right))
          return false;
        workList.add(current.right);
      }
    }
    return true;
  }

  public boolean repOK_size() {
    return numNodes(root) == size;
  }

  private int numNodes(Node n) {
    if (n == null)
      return 0;
    return 1 + numNodes(n.left) + numNodes(n.right);
  }

  public boolean repOK_ordered() {
    if (root == null)
      return true;
    return isOrdered(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private boolean isOrdered(Node n, int min, int max) {
    // if (n.info == null)
    // return false;
    if (n.info == (Integer.MIN_VALUE) || n.info == (Integer.MAX_VALUE))
      return false;
    // if ((min != null && n.info.compareTo(min) <= 0)
    // || (max != null && n.info.compareTo(max) >= 0))
    if ((min != Integer.MIN_VALUE && n.info <= (min)) || (max != Integer.MAX_VALUE && n.info >= (max)))

      return false;
    if (n.left != null)
      if (!isOrdered(n.left, min, n.info))
        return false;
    if (n.right != null)
      if (!isOrdered(n.right, n.info, max))
        return false;
    return true;
  }

  public String toString() {
    StringBuffer buf = new StringBuffer();
    buf.append("{");
    if (root != null)
      buf.append(root.toString());
    buf.append("}");
    return buf.toString();
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

  public boolean repOK_empty_ordered() {
    if (!repOK_empty())
      return false;
    if (!repOK_ordered())
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

  public boolean repOK_acyclic_ordered() {
    if (!repOK_acyclic())
      return false;
    if (!repOK_ordered())
      return false;
    return true;
  }

  public boolean repOK_size_ordered() {
    if (!repOK_size())
      return false;
    if (!repOK_ordered())
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

  public boolean repOK_empty_acyclic_ordered() {
    if (!repOK_empty())
      return false;
    if (!repOK_acyclic())
      return false;
    if (!repOK_ordered())
      return false;
    return true;
  }

  public boolean repOK_empty_size_ordered() {
    if (!repOK_empty())
      return false;
    if (!repOK_size())
      return false;
    if (!repOK_ordered())
      return false;
    return true;
  }

  public boolean repOK_acyclic_size_ordered() {
    if (!repOK_acyclic())
      return false;
    if (!repOK_size())
      return false;
    if (!repOK_ordered())
      return false;
    return true;
  }

}

