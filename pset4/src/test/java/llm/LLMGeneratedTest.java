package llm;

import static org.junit.Assert.*;
import java.util.TreeSet;
import java.util.Set;
import org.junit.Test;
import fall24ee360t.pset4.*;

public class LLMGeneratedTest {

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