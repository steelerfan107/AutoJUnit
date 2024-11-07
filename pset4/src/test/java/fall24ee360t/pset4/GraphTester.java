package fall24ee360t.pset4;

import static org.junit.Assert.*;
import java.util.TreeSet;
import java.util.Set;
import org.junit.Test;

public class GraphTester {

    // Tests for method "addEdge" in class "Graph"

    // Test adding an edge between two nodes in a small graph
    @Test
    public void testAddEdge0() {
        Graph g = new Graph(2);
        g.addEdge(0, 1);
        assertEquals(g.toString(), "numNodes: 2\nedges: [[false, true], [false, false]]");
    }

    // Test adding an edge from node to itself (self-loop)
    @Test
    public void testAddEdge1() {
        Graph g = new Graph(2);
        g.addEdge(0, 0);
        assertEquals(g.toString(), "numNodes: 2\nedges: [[true, false], [false, false]]");
    }

    // Test adding an edge where both nodes are out of bounds (negative index)
    @Test
    public void testAddEdge2() {
        Graph g = new Graph(2);
        g.addEdge(-1, -1); // Should not add an edge
        assertEquals(g.toString(), "numNodes: 2\nedges: [[false, false], [false, false]]");
    }

    // Test adding an edge where 'from' node is out of bounds
    @Test
    public void testAddEdge3() {
        Graph g = new Graph(3);
        g.addEdge(3, 1); // 'from' node is out of bounds
        assertEquals(g.toString(), "numNodes: 3\nedges: [[false, false, false], [false, false, false], [false, false, false]]");
    }

    // Test adding an edge where 'to' node is out of bounds
    @Test
    public void testAddEdge4() {
        Graph g = new Graph(3);
        g.addEdge(1, 3); // 'to' node is out of bounds
        assertEquals(g.toString(), "numNodes: 3\nedges: [[false, false, false], [false, false, false], [false, false, false]]");
    }

    // Tests for method "reachable" in class "Graph"

    // Test single node graph where the node can reach itself
    @Test
    public void testReachable0() {
        Graph g = new Graph(1);
        Set<Integer> nodes = new TreeSet<Integer>();
        nodes.add(0);
        assertTrue(g.reachable(nodes, nodes));
    }

    // Test graph with two nodes and an edge between them, source can reach target
    @Test
    public void testReachable1() {
        Graph g = new Graph(2);
        g.addEdge(0, 1);
        Set<Integer> sources = new TreeSet<Integer>();
        sources.add(0);
        Set<Integer> targets = new TreeSet<Integer>();
        targets.add(1);
        assertTrue(g.reachable(sources, targets));
    }

    // Test graph with two nodes and no edge, source cannot reach target
    @Test
    public void testReachable2() {
        Graph g = new Graph(2);
        Set<Integer> sources = new TreeSet<Integer>();
        sources.add(0);
        Set<Integer> targets = new TreeSet<Integer>();
        targets.add(1);
        assertFalse(g.reachable(sources, targets));
    }

    // Test graph with three nodes and a path through multiple edges
    @Test
    public void testReachable3() {
        Graph g = new Graph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        Set<Integer> sources = new TreeSet<Integer>();
        sources.add(0);
        Set<Integer> targets = new TreeSet<Integer>();
        targets.add(2);
        assertTrue(g.reachable(sources, targets));
    }

    // Test graph where sources contain an invalid node
    @Test
    public void testReachable4() {
        Graph g = new Graph(3);
        Set<Integer> sources = new TreeSet<Integer>();
        sources.add(3); // Invalid node
        Set<Integer> targets = new TreeSet<Integer>();
        targets.add(1);
        assertFalse(g.reachable(sources, targets));
    }

    // Test graph where targets contain an invalid node
    @Test
    public void testReachable5() {
        Graph g = new Graph(3);
        Set<Integer> sources = new TreeSet<Integer>();
        sources.add(0);
        Set<Integer> targets = new TreeSet<Integer>();
        targets.add(3); // Invalid node
        assertFalse(g.reachable(sources, targets));
    }
}