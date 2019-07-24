package org.resistancerus.graph;

import java.util.Collection;
import java.util.List;

/**
 * Graph interface which provides ability to add vertex, add edge and calculate path between two vertices.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public interface Graph<V> {

    /**
     * Adds new vertex to graph.
     */
    void addVertex(final V vertex);

    /**
     * Returns all vertices of the graph.
     * @return List of V objects
     */
    Collection<V> getVertices();

    /**
     * Adds new edge to graph. New vertices will be added if they do not exist.
     */
    void addEdge(final V start, final V end);

    /**
     * Returns a path between two vertices.
     * @return Stringed list of edges between two provided vertices or empty list if path does not exist.
     */
    String getPath(final V start, final V end);
}
