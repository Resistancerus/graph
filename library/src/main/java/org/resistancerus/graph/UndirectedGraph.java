package org.resistancerus.graph;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class UndirectedGraph implements Graph {

    private List<Edge> edges = new ArrayList<>();
    private Map<Vertex, List<Vertex>> verticesConnectionsMap = new HashMap<>();

    public void addVertex(final Vertex vertex) {
        verticesConnectionsMap.computeIfAbsent(vertex, k -> new ArrayList<>());
    }

    public void addEdge(final Edge edge) {
        edges.add(edge);
        if (verticesConnectionsMap.get(edge.getStart()) == null) {
            verticesConnectionsMap.put(edge.getStart(), singletonList(edge.getEnd()));
        } else {
            verticesConnectionsMap.get(edge.getStart()).add(edge.getEnd());
        }

        if (verticesConnectionsMap.get(edge.getEnd()) == null) {
            verticesConnectionsMap.put(edge.getEnd(), singletonList(edge.getStart()));
        } else {
            verticesConnectionsMap.get(edge.getEnd()).add(edge.getStart());
        }
    }

    @Override
    public List<Edge> getPath(final Vertex start, final Vertex end) {

        if (start == null || end == null) {
            return emptyList();
        }

        if (verticesConnectionsMap.get(start) == null || verticesConnectionsMap.get(end) == null) {
            return emptyList();
        }

        if (start == end) {
            final Optional<Edge> resultEdge = edges.stream().filter(edge -> edge.getStart().equals(edge.getEnd()) && edge.getStart().equals(start)).findFirst();
            if (resultEdge.isPresent()) {
                return singletonList(resultEdge.get());
            }
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

    private List<Vertex> getPath(final Vertex start, final Vertex end, final List<Vertex> path) {
        path.add(start);
        if (start == end) {
            return path;
        }
        for (final Vertex current : verticesConnectionsMap.get(start)) {
            if (!path.contains(current)) {
                path.addAll(getPath(current, end, path));
            }
        }
        return path;
    }
}
