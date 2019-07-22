package org.resistancerus.graph;

import org.junit.Before;
import org.junit.Test;
import org.resistancerus.graph.entity.TestEdge;
import org.resistancerus.graph.entity.TestVertex;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UndirectedGraphTest {

    private Graph graph;

    @Before
    public void setUp() {
        this.graph = new UndirectedGraph();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddNullVertex() {
        this.graph.addVertex(null);
    }

    @Test
    public void testAddValidVertex() {
        final Vertex a = new TestVertex("a");
        graph.addVertex(a);

        final UndirectedGraph undirectedGraph = (UndirectedGraph)graph;

        assertEquals(1L, undirectedGraph.getVertices().size());
        assertTrue(undirectedGraph.getVertices().contains(a));
    }

    @Test
    public void testAddValidVertexSeveralTimes() {
        final Vertex a = new TestVertex("a");

        graph.addVertex(a);
        graph.addVertex(a);
        graph.addVertex(a);

        final UndirectedGraph undirectedGraph = (UndirectedGraph)graph;

        assertEquals(1L, undirectedGraph.getVertices().size());
        assertTrue(undirectedGraph.getVertices().contains(a));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddNullEdge() {
        graph.addEdge(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionOnAddEdgeWithNullVertex() {
        graph.addEdge(new TestEdge(null, new TestVertex("a")));
    }

    @Test
    public void testAddValidEdge() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Edge abEdge = new TestEdge(a, b);

        graph.addEdge(abEdge);

        final UndirectedGraph undirectedGraph = (UndirectedGraph)graph;

        assertEquals(1L, undirectedGraph.getEdges().size());
        assertTrue(undirectedGraph.getEdges().contains(abEdge));
    }

    @Test
    public void testAddValidEdgeSeveralTimes() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Edge abEdge = new TestEdge(a, b);

        graph.addEdge(abEdge);
        graph.addEdge(abEdge);
        graph.addEdge(abEdge);

        final UndirectedGraph undirectedGraph = (UndirectedGraph)graph;

        assertEquals(1L, undirectedGraph.getEdges().size());
        assertTrue(undirectedGraph.getEdges().contains(abEdge));
    }

    @Test
    public void testAddLoopEdge() {
        final Vertex a = new TestVertex("a");
        final Edge loopEdge = new TestEdge(a, a);

        graph.addEdge(loopEdge);

        final UndirectedGraph undirectedGraph = (UndirectedGraph)graph;

        assertEquals(1L, undirectedGraph.getEdges().size());
        assertTrue(undirectedGraph.getEdges().contains(loopEdge));
    }

    @Test
    public void testAddLoopEdgeSeveralTimes() {
        final Vertex a = new TestVertex("a");
        final Edge loopEdge = new TestEdge(a, a);

        graph.addEdge(loopEdge);
        graph.addEdge(loopEdge);
        graph.addEdge(loopEdge);

        final UndirectedGraph undirectedGraph = (UndirectedGraph)graph;

        assertEquals(1L, undirectedGraph.getEdges().size());
        assertTrue(undirectedGraph.getEdges().contains(loopEdge));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenTwoNullVertices() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Vertex c = new TestVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.getPath(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathBetweenValidAndNullVertices() {
        final Vertex a = new TestVertex("a");
        graph.addVertex(a);

        graph.getPath(a, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathWhenOneVertexNotInGraph() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");

        graph.addVertex(a);

        graph.getPath(a, b);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetPathWhenBothVerticesNotInGraph() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Vertex c = new TestVertex("c");

        graph.addVertex(c);

        graph.getPath(a, b);
    }

    @Test
    public void testGetPathBetweenTwoIdenticalVerticesWithoutLoop() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");

        graph.addEdge(new TestEdge(a, b));

        final List<Edge> result = graph.getPath(a, a);
        assertEquals(0L, result.size());
    }

    @Test
    public void testGetPathBetweenTwoIdenticalVerticesWithLoop() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");

        final Edge aLoop = new TestEdge(a, a);
        final Edge abEdge = new TestEdge(a, b);

        graph.addEdge(aLoop);
        graph.addEdge(abEdge);

        final List<Edge> result = graph.getPath(a, a);
        assertEquals(1L, result.size());
        assertEquals(aLoop, result.get(0));
    }

    @Test
    public void testGetPathBetweenTwoNotConnectedVertices() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");

        graph.addVertex(a);
        graph.addVertex(b);

        final List<Edge> result = graph.getPath(a, b);
        assertEquals(0L, result.size());
    }

    @Test
    public void testGetPathBetweenConnectedAndNotConnectedVertices() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Vertex c = new TestVertex("c");

        graph.addVertex(b);
        graph.addEdge(new TestEdge(a, c));

        assertEquals(0L, graph.getPath(a, b).size());
        assertEquals(0L, graph.getPath(c, b).size());
    }

    @Test
    public void testGetPathForVerticesOfOneEdge() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Vertex c = new TestVertex("c");

        final Edge abEdge = new TestEdge(a, b);
        final Edge acEdge = new TestEdge(a, c);

        graph.addEdge(abEdge);
        graph.addEdge(acEdge);

        final List<Edge> result = graph.getPath(a, b);
        assertEquals(1L, result.size());
        assertTrue(result.contains(abEdge));
    }

    // TODO debug!!!
    @Test
    public void testGetPathBetweenTwoConnectedVertices() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Vertex c = new TestVertex("c");

        final Edge abEdge = new TestEdge(a, b);
        final Edge bcEdge = new TestEdge(b, c);

        graph.addEdge(abEdge);
        graph.addEdge(bcEdge);

        final List<Edge> result = graph.getPath(a, c);
        assertEquals(2L, result.size());
        assertEquals(abEdge, result.get(0));
    }

    // TODO debug!!!
    @Test
    public void testGetLongPathBetweenTwoConnectedVertices() {
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Vertex c = new TestVertex("c");
        final Vertex d = new TestVertex("d");
        final Vertex e = new TestVertex("e");
        final Vertex f = new TestVertex("f");

        final Edge abEdge = new TestEdge(a, b);
        final Edge acEdge = new TestEdge(a, c);
        final Edge adEdge = new TestEdge(a, d);
        final Edge dfEdge = new TestEdge(d, f);
        final Edge ceEdge = new TestEdge(c, e);

        graph.addEdge(abEdge);
        graph.addEdge(acEdge);
        graph.addEdge(adEdge);
        graph.addEdge(dfEdge);
        graph.addEdge(ceEdge);

        final List<Edge> result = graph.getPath(a, f);
        assertEquals(2L, result.size());
        assertTrue(result.contains(adEdge));
        assertTrue(result.contains(dfEdge));
    }
}