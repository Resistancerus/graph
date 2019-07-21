package org.resistancerus.graph;

import java.util.List;

public interface Graph {

    void addVertex(final Vertex vertex);

    void addEdge(final Edge edge);

    List<Edge> getPath(final Vertex start, final Vertex end);
}
