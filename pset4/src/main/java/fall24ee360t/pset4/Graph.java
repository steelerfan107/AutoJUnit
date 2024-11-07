package fall24ee360t.pset4;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
public class Graph {
    private int numNodes; // number of nodes in the graph
    private boolean[][] edges;
    // edges[i][j] is true if and only if there is an edge from node i to node j
    // class invariant: edges != null; edges is a square matrix;
    // numNodes >= 0; numNodes is number of rows in edges
    public Graph(int size) {
        numNodes = size;
        // your code goes here
        // ...
        edges = new boolean[size][size];
    }
    @Override
    public String toString() {
        return "numNodes: " + numNodes + "\n" + "edges: " + Arrays.deepToString(edges);
    }
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != Graph.class) return false;
        return toString().equals(o.toString());
    }

    public void addEdge(int from, int to) {
        // postcondition: adds a directed edge "from" -> "to" to this graph
        // your code goes here
        // ...
        if (from < 0 || from > this.numNodes -1 || to < 0 || to > this.numNodes - 1)
            return;
        edges[from][to] = true;
    }
    public boolean reachable(Set<Integer> sources, Set<Integer> targets) {
        if (sources == null || targets == null) throw new IllegalArgumentException();
        // postcondition: returns true if (1) "sources" does not contain an illegal node,
        // (2) "targets" does not contain an illegal node, and
        // (3) for each node "m" in set "targets", there is some
        // node "n" in set "sources" such that there is a directed
        // path that starts at "n" and ends at "m" in "this"; and
        // false otherwise
        // your code goes here
        for (int source : sources) {
            if (source < 0 || source > numNodes - 1) {
                return false;
            }
        }
        for (int target : targets) {
            if (target < 0 || target > numNodes - 1) {
                return false;
            }
        }

        Set<Integer> visited = new HashSet<>();
        for (int source : sources) {
            dfs(source, visited);
        }

        for (int target : targets) {
            if (!visited.contains(target)) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int node, Set<Integer> visited) {
        if (visited.contains(node)) return;
        visited.add(node);
        for (int i = 0; i < numNodes; i++) {
            if (edges[node][i]) {
                dfs(i, visited);
            }
        }
    }
}