package ch.voegele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CycleTest {

    /**
     * Test simple graph with one cycle
     * Test passes, if they find the cycle
     */
    @Test
    public void CycleTestSimple() {
        var graph = new ListGraph();

        graph.addDirectedEdge(0, 1);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(2, 3);
        graph.addDirectedEdge(3, 4);
        graph.addDirectedEdge(4, 0);
        graph.addDirectedEdge(6, 5);
        graph.addDirectedEdge(5, 3);

        var result = graph.findCycle(6);
        assertEquals(5, result.lambda);
        assertEquals(2, result.mu);
        assertTrue(result.isCyclic);
    }

    /**
     * Test graph with a dead-end on a node in the cycle.
     * Test to check, if the haare or tortoise get stuck in the dead-end
     * Test passes if they do not get stuck and find the cycle
     */
    @Test
    public void CycleTestDeadEnd() {
        var graph = new ListGraph();

        graph.addDirectedEdge(0, 1);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(3, 4);
        graph.addDirectedEdge(4, 0);
        graph.addDirectedEdge(6, 5);
        graph.addDirectedEdge(5, 3);

        var result = graph.findCycle(6);
        assertEquals(4, result.lambda);
        assertEquals(2, result.mu);
        assertTrue(result.isCyclic);
    }

    /**
     * Test a graph with two dead-ends
     * Test to check, if the haare or tortoise do not get stuck two separate dead-ends
     * passes if they do not get stuck and find the cycle
     */
    @Test
    public void CycleTestTwoDeadEnds() {
        var graph = new ListGraph();

        graph.addDirectedEdge(0, 1);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(3, 4);
        graph.addDirectedEdge(4, 0);
        graph.addDirectedEdge(6, 5);
        graph.addDirectedEdge(5, 3);
        graph.addDirectedEdge(4, 7);

        var result = graph.findCycle(6);
        assertEquals(4, result.lambda);
        assertEquals(2, result.mu);
        assertTrue(result.isCyclic);
    }

    /**
     * Test a graph with no cycles
     * passes if no cycle is found
     */
    @Test
    public void CycleTestNoCycle() {
        var graph = new ListGraph();

        graph.addDirectedEdge(0, 1);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(1, 3);
        graph.addDirectedEdge(3, 4);

        var result = graph.findCycle(0);
        assertEquals(-1, result.lambda);
        assertEquals(0, result.mu);
        assertFalse(result.isCyclic);
    }

    /**
     * Test with two cycles.
     * This test showed that it needs a adaption to get all cycles, if the cycles share edges.
     */
    @Test
    public void CycleTestTwoCycles() {
        var graph = new ListGraph();

        graph.addDirectedEdge(0, 1);
        graph.addDirectedEdge(1, 2);
        graph.addDirectedEdge(2, 3);
        graph.addDirectedEdge(3, 0);
        graph.addDirectedEdge(1, 4);
        graph.addDirectedEdge(4, 0);
        graph.addVertex(6);

        var results = graph.findAllCycles();
        for (var result : results) {
            assertTrue(result.isCyclic);
            result.printPath();
        }
        assertEquals(2, results.size());
    }

    /**
     * Test if loops are detected as cycles.
     * passes if they are detected
     */
    @Test
    public void CycleTestLoop() {
        var graph = new ListGraph();

        graph.addDirectedEdge(1, 1);

        var result = graph.findCycle(1);
        result.printPath();

        assertEquals(1, result.lambda);
        assertTrue(result.isCyclic);
    }

    /**
     * Test with one large cycle, where all vertices have a rank of 2
     */
    @Test
    public void CycleTestBigGraph() {
        var graph = new ListGraph();
        var size = 1000;

        for (int i = 0; i < size - 1; i++) {
            graph.addDirectedEdge(i, i + 1);
        }
        graph.addDirectedEdge(size - 1, 0);

        var result = graph.findCycle(0);
        result.printPath();

        assertEquals(1000, result.lambda);
        assertTrue(result.isCyclic);
    }

    /**
     * Test with one large cycle,
     * where all odd vertices have a rank of 2
     * and all even vertices have a rank of 4. They are always connected to the adjacent even vertices.
     */
    @Test
    public void CycleTestBigGraphManyCycles() {
        var graph = new ListGraph();
        var size = 1000;

        for (int i = 0; i < size - 1; i++) {
            graph.addDirectedEdge(i, i + 1);
//            if (i % 2 == 0)
//                graph.addDirectedEdge(i + 2, i);

        }
        graph.addDirectedEdge(size - 1, 0);
        graph.addDirectedEdge(0, size - 2);

        var results = graph.findCycle(0);

        results.printPath();

//        for (var result : results) {
//            result.printPath();
//        }

//        assertEquals(1000, result.lambda);
//        assertTrue(result.isCyclic);
    }


}