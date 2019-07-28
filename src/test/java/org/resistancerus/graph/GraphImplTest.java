package org.resistancerus.graph;

import org.junit.Before;
import org.junit.Test;
import org.resistancerus.graph.entity.TestVertex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.resistancerus.graph.GraphFactory.createGraph;

/**
 * Tests of Graph interface implementation.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public class GraphImplTest {

    private Graph<TestVertex> graph;

    @Before
    public void setUp() {
        graph = createGraph(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddNullVertex() {
        graph.addVertex(null);
    }

    @Test
    public void testAddValidVertex() {
        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);

        assertTrue(graph.hasVertex(a));
    }

    @Test
    public void testAddValidVertexSeveralTimes() {
        final TestVertex a = new TestVertex("a");

        assertTrue(graph.addVertex(a));
        assertFalse(graph.addVertex(a));
        assertFalse(graph.addVertex(a));

        assertTrue(graph.hasVertex(a));
    }

    @Test
    public void testRemoveExistingVertex() {
        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);
        assertTrue(graph.hasVertex(a));

        graph.removeVertex(a);
        assertFalse(graph.hasVertex(a));
    }

    @Test
    public void testRemoveNotExistingVertex() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        graph.addVertex(a);
        assertTrue(graph.hasVertex(a));

        assertFalse(graph.removeVertex(b));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullVertex() {
        graph.removeVertex(null);
    }

    @Test
    public void testAddValidEdge() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        assertTrue(graph.addEdge(a, b));
        assertTrue(graph.hasEdge(a ,b));
        assertTrue(graph.hasEdge(b, a));
    }

    @Test
    public void testAddValidDirectedEdge() {
        graph = createGraph(true);

        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        assertTrue(graph.addEdge(a, b));
        assertTrue(graph.hasEdge(a ,b));
        assertFalse(graph.hasEdge(b, a));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddEdgeWithNullVertex() {
        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);
        graph.addEdge(null, a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddEdgeWithNotExistingVertex() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addEdge(a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddEdgeWithNullVertices() {
        graph.addEdge(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddLoopEdgeWhenItIsNotAllowed() {
        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);
        graph.addEdge(a, a);
    }

    @Test
    public void testAddLoopEdgeWhenItIsAllowed() {
        graph = createGraph(false, true);

        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);

        assertTrue(graph.addEdge(a, a));
        assertTrue(graph.hasEdge(a, a));
    }

    @Test
    public void testRemoveExistingEdge() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(a, b);
        assertTrue(graph.hasEdge(a, b));

        assertTrue(graph.removeEdge(a, b));
        assertFalse(graph.hasEdge(a, b));
    }

    @Test
    public void testRemoveNotExistingEdge() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        assertFalse(graph.removeEdge(a, b));
    }

    @Test
    public void testRemoveEdgeWithNotExistingEnd() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addEdge(a, b);

        assertFalse(graph.removeEdge(a, c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullEdge() {
        graph.removeEdge(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveEdgeWithNullVertex() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.removeEdge(a, null);
    }
}