package org.resistancerus.graph;

/**
 * Factory class to get instances of directed or undirected graph.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public abstract class GraphFactory {

    /**
     * @return Graph instance.
     * @param directed specifies directed edges support.
     * @param loopsAllowed specifies loop edges support.
     * @see Graph
     */
    public static <V> Graph<V> createGraph(final boolean directed, final boolean loopsAllowed) {
        return new GraphImpl<>(directed, loopsAllowed);
    }

    /**
     * @return Graph instance.
     * @param directed specifies directed edges support.
     * @see Graph
     */
    public static <V> Graph<V> createGraph(final boolean directed) {
        return createGraph(directed, false);
    }
}
