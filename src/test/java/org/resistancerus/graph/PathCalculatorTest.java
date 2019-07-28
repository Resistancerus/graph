package org.resistancerus.graph;

import org.junit.Before;
import org.junit.Test;
import org.resistancerus.graph.entity.TestVertex;

import static org.junit.Assert.*;
import static org.resistancerus.graph.PathCalculator.getPath;

/**
 * Tests for getPath static function.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public class PathCalculatorTest {

    private Graph<TestVertex> graph;
    
    @Before
    public void setUp() throws Exception {
        graph = GraphFactory.createGraph(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnGetPathForNullGraph() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        getPath(null, a, c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenTwoNullVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        getPath(graph, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenValidAndNullVertices() {
        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);

        getPath(graph, a, null);
    }

    @Test
    public void testGetPathWhenOneVertexNotInGraph() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);

        final Path<TestVertex> result = getPath(graph, a, b);
        assertTrue(result.getEdges().isEmpty());
    }

    @Test
    public void testGetPathWhenBothVerticesNotInGraph() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(c);

        final Path<TestVertex> result = getPath(graph, a, b);
        assertTrue(result.getEdges().isEmpty());
    }

    @Test
    public void testGetPathBetweenTwoNotConnectedVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        final Path<TestVertex> result = getPath(graph, a, b);
        assertTrue(result.getEdges().isEmpty());
    }

    @Test
    public void testGetPathBetweenConnectedAndNotConnectedVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.addEdge(a, c);

        final Path<TestVertex> result = getPath(graph, a, b);
        assertTrue(result.getEdges().isEmpty());
    }

    @Test
    public void testGetPathForVerticesOfOneEdge() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.addEdge(a, b);
        graph.addEdge(a, c);

        final Path<TestVertex> result = getPath(graph, a, b);
        assertEquals(1L, result.getEdges().size());
        assertEquals(new Edge<>(a, b), result.getEdges().get(0));
    }

    @Test
    public void testGetPathBetweenTwoIdenticalVerticesWithoutLoop() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(a, b);

        final Path<TestVertex> result = getPath(graph, a, a);
        assertTrue(result.getEdges().isEmpty());
    }

    @Test
    public void testGetPathBetweenTwoIdenticalVerticesWithLoop() {
        graph = GraphFactory.createGraph(false, true);

        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(a, a);
        graph.addEdge(a, b);

        final Path<TestVertex> result = getPath(graph, a, a);
        assertEquals(1L, result.getEdges().size());
        assertEquals(new Edge<>(a, a), result.getEdges().get(0));
    }

    @Test
    public void testGetUndirectedPathBetweenTwoConnectedVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.addEdge(a, b);
        graph.addEdge(b, c);

        final Path<TestVertex> result = getPath(graph, a, c);
        assertEquals(2L, result.getEdges().size());
        assertEquals(result.getEdges().get(0), new Edge<>(a, b));
        assertEquals(result.getEdges().get(1), new Edge<>(b, c));
    }

    @Test
    public void testGetUndirectedLongPathBetweenTwoConnectedVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");
        final TestVertex d = new TestVertex("d");
        final TestVertex e = new TestVertex("e");
        final TestVertex f = new TestVertex("f");
        final TestVertex g = new TestVertex("g");
        final TestVertex h = new TestVertex("h");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(a, d);
        graph.addEdge(d, f);
        graph.addEdge(f, g);
        graph.addEdge(c, e);
        graph.addEdge(g, h);

        final Path<TestVertex> result = getPath(graph, a, f);
        assertEquals(2L, result.getEdges().size());
        assertEquals(result.getEdges().get(0), new Edge<>(a, d));
        assertEquals(result.getEdges().get(1), new Edge<>(d, f));
    }

    @Test
    public void testGetDirectedPathBetweenTwoConnectedVerticesIsNotAvailable() {
        graph = GraphFactory.createGraph(true);

        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.addEdge(a, b);
        graph.addEdge(c, b);

        assertTrue(getPath(graph, a, c).getEdges().isEmpty());
    }

    @Test
    public void testGetDirectedPathBetweenTwoConnectedVertices() {
        graph = GraphFactory.createGraph(true);

        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");
        final TestVertex d = new TestVertex("d");
        final TestVertex e = new TestVertex("e");
        final TestVertex f = new TestVertex("f");
        final TestVertex g = new TestVertex("g");
        final TestVertex h = new TestVertex("h");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(a, d);
        graph.addEdge(d, f);
        graph.addEdge(f, g);
        graph.addEdge(c, e);
        graph.addEdge(h, g);

        final Path<TestVertex> result = getPath(graph, a, g);
        assertEquals(3L, result.getEdges().size());
        assertEquals(result.getEdges().get(0), new Edge<>(a, d));
        assertEquals(result.getEdges().get(1), new Edge<>(d, f));
        assertEquals(result.getEdges().get(2), new Edge<>(f, g));
    }
}