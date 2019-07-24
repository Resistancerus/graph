package org.resistancerus.graph;

/**
 * Factory class to get instances of directed or undirected graph.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public class GraphFactory<V> {

    /**
     * @return Graph instance.
     * @see Graph
     */
    public Graph<V> createGraph(final boolean directed) {
        return new GraphImpl<>(directed);
    }
}
