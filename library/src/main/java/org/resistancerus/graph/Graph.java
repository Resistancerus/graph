package org.resistancerus.graph;

import java.util.List;

/**
 * @author Malishevskii Oleg
 * @version 1.0
 */
public interface Graph {

    /**
     * Adds new Vertex to graph.
     * @see Vertex
     */
    void addVertex(final Vertex vertex);

    /**
     * Adds new Edge to graph. New vertices will be added if they do not exist.
     * @see Edge
     */
    void addEdge(final Edge edge);

    /**
     * Adds new Edge to graph. New vertices will be added if they do not exist.
     * @return list of edges between two provided vertices or empty list if path does not exist.
     */
    List<Edge> getPath(final Vertex start, final Vertex end);
}
