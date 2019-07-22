package org.resistancerus.graph;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


/**
 * Implementation of undirected graph.
 * @author Malishevskii Oleg
 * @version 1.0
 */
class UndirectedGraph implements Graph {

    private final List<Edge> edges = new ArrayList<>();
    private final Map<Vertex, List<Vertex>> verticesConnectionsMap = new HashMap<>();

    // TODO return copy of data
    public List<Edge> getEdges() {
        return this.edges;
    }

    // TODO return copy of data
    public List<Vertex> getVertices() {
        return new ArrayList<>(this.verticesConnectionsMap.keySet());
    }

    public void addVertex(final Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex could not be null.");
        }
        verticesConnectionsMap.computeIfAbsent(vertex, k -> new ArrayList<>());
    }

    public void addEdge(final Edge edge) {
        if (edge == null || edge.getStart() == null || edge.getEnd() == null) {
            throw new IllegalArgumentException("Edge or one of its points could not be null.");
        }

        if (edges.contains(edge)) {
            return;
        }

        edges.add(edge);

        verticesConnectionsMap.computeIfAbsent(edge.getStart(), k -> new ArrayList<>());
        addConnectedVertex(edge.getStart(), edge.getEnd());

        if (edge.getStart().equals(edge.getEnd())) {
            return;
        }

        verticesConnectionsMap.computeIfAbsent(edge.getEnd(), k -> new ArrayList<>());
        addConnectedVertex(edge.getEnd(), edge.getStart());
    }

    private void addConnectedVertex(final Vertex vertex, final Vertex connectedVertex) {
        if (verticesConnectionsMap.get(vertex) != null && !verticesConnectionsMap.get(vertex).contains(connectedVertex)) {
            verticesConnectionsMap.get(vertex).add(connectedVertex);
        }
    }

    // TODO reimplement with BFS
    @Override
    public List<Edge> getPath(final Vertex start, final Vertex end) {

        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and End vertices could not be null.");
        }

        if (verticesConnectionsMap.get(start) == null  || verticesConnectionsMap.get(end) == null) {
            throw new IllegalArgumentException("Start or End vertices does not belong to graph.");
        }

        if (isEmpty(verticesConnectionsMap.get(start)) || isEmpty(verticesConnectionsMap.get(end))) {
            return emptyList();
        }

        if (start == end) {
            final Optional<Edge> resultEdge = edges.stream().filter(edge -> edge.getStart().equals(edge.getEnd()) && edge.getStart().equals(start)).findFirst();
            if (resultEdge.isPresent()) {
                return singletonList(resultEdge.get());
            } else {
                return emptyList();
            }
        }

        if (verticesConnectionsMap.get(start).contains(end)) {
            return singletonList(
                    edges.stream()
                            .filter(edge -> edge.getStart().equals(start) && edge.getEnd().equals(end)
                                    || edge.getStart().equals(end) && edge.getEnd().equals(start))
                            .findFirst()
                            .get());
        }

        final List<Vertex> result = getPath(start, end, new ArrayList<>());
        final List<Edge> edgesResult = new ArrayList<>();
        for (int i = 0; i < result.size() - 1; ++i) {
            final int j = i;
            Optional<Edge> matchingEdge = edges.stream().filter(edge -> edge.getStart().equals(result.get(j)) && edge.getEnd().equals(result.get(j + 1))
                    || edge.getStart().equals(result.get(j + 1)) && edge.getEnd().equals(result.get(j))).findFirst();
            matchingEdge.ifPresent(edgesResult::add);
        }

        return edgesResult;
    }

    private boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    private List<Vertex> getPath(final Vertex start, final Vertex end, final List<Vertex> path) {
        path.add(start);
        if (start == end) {
            return path;
        }
        for (final Vertex current : verticesConnectionsMap.get(start)) {
            if (!path.contains(current)) {
                final List<Vertex> additionalPath = getPath(current, end, path);
                if (additionalPath != null) {
                    return additionalPath;
                }
            }
        }
        return null;
    }
}
