package org.resistancerus.graph;

import org.junit.Before;
import org.junit.Test;
import org.resistancerus.graph.entity.TestVertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphImplTest {

    private Graph<TestVertex> graph;

    @Before
    public void setUp() {
        graph = GraphFactory.createGraph(false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddNullVertex() {
        graph.addVertex(null);
    }

    @Test
    public void testAddValidVertex() {
        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);

        final GraphImpl graphImpl = (GraphImpl)graph;

        assertEquals(1L, graphImpl.getVertices().size());
        assertTrue(graphImpl.getVertices().contains(a));
    }

    @Test
    public void testAddValidVertexSeveralTimes() {
        final TestVertex a = new TestVertex("a");

        graph.addVertex(a);
        graph.addVertex(a);
        graph.addVertex(a);

        final GraphImpl graphImpl = (GraphImpl)graph;

        assertEquals(1L, graphImpl.getVertices().size());
        assertTrue(graphImpl.getVertices().contains(a));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddEdgeWithNullVertex() {
        graph.addEdge(null, new TestVertex("a"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddEdgeWithNullVertices() {
        graph.addEdge(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddLoopEdgeWhenItIsNotAllowed() {
        final TestVertex a = new TestVertex("a");

        graph.addEdge(a, a);
    }

    @Test
    public void testAddLoopEdgeWhenItIsAllowed() {
        graph = GraphFactory.createGraph(false, true);
        final TestVertex a = new TestVertex("a");

        graph.addEdge(a, a);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenTwoNullVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.getPath(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenValidAndNullVertices() {
        final TestVertex a = new TestVertex("a");
        graph.addVertex(a);

        graph.getPath(a, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathWhenOneVertexNotInGraph() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);

        graph.getPath(a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathWhenBothVerticesNotInGraph() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(c);

        graph.getPath(a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenTwoNotConnectedVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.getPath(a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenConnectedAndNotConnectedVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addVertex(b);
        graph.addEdge(a, c);

        graph.getPath(a, b);
    }

    @Test
    public void testGetPathForVerticesOfOneEdge() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addEdge(a, b);
        graph.addEdge(a, c);

        String result = graph.getPath(a, b);
        assertEquals("a - b", result);
    }

    @Test
    public void testGetPathBetweenTwoIdenticalVerticesWithoutLoop() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addEdge(a, b);

        final String result = graph.getPath(a, a);
        assertEquals("", result);
    }

    @Test
    public void testGetPathBetweenTwoIdenticalVerticesWithLoop() {
        graph = GraphFactory.createGraph(false, true);

        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");

        graph.addEdge(a, a);
        graph.addEdge(a, b);

        final String result = graph.getPath(a, a);
        assertEquals("a - a", result);
    }

    @Test
    public void testGetUndirectedPathBetweenTwoConnectedVertices() {
        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addEdge(a, b);
        graph.addEdge(b, c);

        final String result = graph.getPath(a, c);
        assertEquals("a - b, b - c", result);
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

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(a, d);
        graph.addEdge(d, f);
        graph.addEdge(f, g);
        graph.addEdge(c, e);
        graph.addEdge(g, h);

        final String result = graph.getPath(a, f);
        assertEquals("a - d, d - f", result);
    }

    @Test
    public void testGetDirectedPathBetweenTwoConnectedVerticesIsNotAvailable() {
        graph = GraphFactory.createGraph(true);

        final TestVertex a = new TestVertex("a");
        final TestVertex b = new TestVertex("b");
        final TestVertex c = new TestVertex("c");

        graph.addEdge(a, b);
        graph.addEdge(c, b);

        assertEquals("", graph.getPath(a, c));
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

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(a, d);
        graph.addEdge(d, f);
        graph.addEdge(f, g);
        graph.addEdge(c, e);
        graph.addEdge(h, g);

        final String result = graph.getPath(a, g);
        assertEquals("a - d, d - f, f - g", result);
    }
}