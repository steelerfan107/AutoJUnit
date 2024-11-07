package dag;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DAGTests {

  @Test
  public void testEmptyList() {
    DAG dag = new DAG();
    assertTrue(dag.repOK_Complete());
  }

  @Test
  public void testEmptyOneElem() {
    DAG dag = new DAG();
    dag.addRandomNode();
    assertTrue(dag.repOK_Complete());
  }

  @Test
  public void testEmptyTwoElem() {
    DAG dag = new DAG();
    dag.addRandomNode();
    dag.addRandomNode();
    assertTrue(dag.repOK_Complete());
  }

  @Test
  public void testEmptyManyElem() {
    DAG dag = new DAG();
    dag.addRandomNode();
    dag.addRandomNode();
    dag.addRandomNode();
    dag.addRandomNode();
    dag.addRandomNode();
    dag.addRandomNode();
    assertTrue(dag.repOK_Complete());
  }

}
