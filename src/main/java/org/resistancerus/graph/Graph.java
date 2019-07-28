package org.resistancerus.graph;

import java.util.Set;

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
     * Returns a set of graph vertices.
     */
    Set<V> getVertices();

    /**
     * Returns a set of adjacent vertices for provided vertices or null.
     */
    Set<V> getAdjacentVertices(final V vertex);
}
