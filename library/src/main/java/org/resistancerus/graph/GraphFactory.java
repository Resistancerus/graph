package org.resistancerus.graph;

/**
 * Factory class to get instances of directed or undirected graph.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public abstract class GraphFactory {

    /**
     * @return DirectedGraph object.
     * @see DirectedGraph
     */
    public static Graph getDirectedGraph() {
        return new DirectedGraph();
    }

    /**
     * @return UndirectedGraph object.
     * @see UndirectedGraph
     */
    public static Graph getUndirectedGraph() {
        return new UndirectedGraph();
    }
}
