package org.resistancerus.graph;

/**
 * Graph interface which provides ability to add vertex, add edge and calculate path between two vertices.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public interface Graph<V> {

    /**
     * Adds new vertex to graph.
     */
    boolean addVertex(final V vertex);

    /**
     * Removes vertex from the graph.
     */
    boolean removeVertex(final V vertex);

    /**
     * Checks whether vertex exists in the graph.
     */
    boolean hasVertex(final V vertex);

    /**
     * Adds new edge to graph. Both vertices must exist.
     */
    boolean addEdge(final V start, final V end);

    /**
     * Removes edge from the graph. If directed edges are not supported, reverse edge will also be deleted.
     */
    boolean removeEdge(final V start, final V end);

    /**
     * Checks edge already exists in the graph. If directed edges are not supported, reverse edge existence will also be checked.
     */
    boolean hasEdge(final V start, final V end);

    /**
     * Checks if graph supports directed edges.
     */
    boolean isDirected();

    /**
     * Checks if graph supports loop edges.
     */
    boolean areLoopsAllowed();

    /**
     * Returns a path between two vertices.
     * @return Stringed list of edges between two provided vertices or empty list if path does not exist.
     */
    String getPath(final V start, final V end);
}
