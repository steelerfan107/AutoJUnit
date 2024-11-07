package dag;

import java.util.*;


/**
 * <p>DAG class represents directed acyclic graphs.
 * </p> 
 * 
 * <p> DAGs are characterized by having directed edges between nodes,
 * and not having directed cycles, i.e. for any vertex v, there is no 
 * nonempty directed path that starts and ends on v.
 * </p>
 * 
 * <p>
 * Nodes of a DAG are represented by DAGNode class, and edges are represented
 * by children field of DAGNode.
 * </p> 
 * 
 * @see DAGNode
 */
public class DAG {

    /*
     * Builders ---------------------------
     */
    public DAG() {
        size = 0;
    }

    public void addRandomNode() {
        Random r = new Random();
        DAGNode node = new DAGNode();
        if (r.nextInt(2) == 0) {
            // Add the node to the roots
            nodes.add(node);
            size++;
        } else {
            // Add the node as a child of a random node
            if (size == 0) {
                nodes.add(node);
                size++;
            } else {
                DAGNode parent = nodes.get(r.nextInt(nodes.size()));
                parent.addChild(node);
                size++;
            }
        }
    }
    /*
     * -----------------------------------------
     */

    /**
     * Nodes list contains all nodes of a DAG
     */
    //transient fields are skipped during visualization 
    private List<DAGNode> nodes = new LinkedList<>();
    
    public List<DAGNode> getNodes() { return nodes; }

    /**
     * Number of nodes in a DAG. 
     */
    private int size;

    public int getSize() {
        return size;
    }
    
    /**
     * Checks for basic DAG properties. Checks if graph contains loops. If it
     * does, returns false, otherwise returns true. 
     */
    public boolean repOK_Complete() {
        if (!repOK_nocycles())
            return false;
        if (!repOK_size())
            return false;
        return true;
    }

    public boolean repOK_nocycles() {
        Set<DAGNode> visited = new HashSet<>();
        Set<DAGNode> path = new HashSet<>();
        for (DAGNode node : getNodes()) {
            if (!visited.contains(node)) {
                if (!node.repOK1(path, visited)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean repOK_size() {
        Set<DAGNode> visited = new HashSet<>();
        Set<DAGNode> path = new HashSet<>();
        for (DAGNode node : getNodes()) {
            if (!visited.contains(node)) {
                node.repOK1(path, visited);
            }
        }
        if (size != visited.size())
            return false;
        return true;
    }

}
