package org.resistancerus.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Implementation of Graph interface.
 * @author Malishevskii Oleg
 * @version 1.0
 */

class GraphImpl<V> implements Graph<V> {

    private Logger logger = LoggerFactory.getLogger(GraphImpl.class);

    private boolean directed;
    private boolean loopsAllowed;
    private final Map<V, List<V>> adjacentVerticesMap = new HashMap<>();

    GraphImpl(final boolean directed, boolean loopsAllowed) {
        this.directed = directed;
        this.loopsAllowed = loopsAllowed;
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public boolean areLoopsAllowed() {
        return loopsAllowed;
    }

    @Override
    public boolean addVertex(final V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex could not be null.");
        }

        if (hasVertex(vertex)) {
            logger.info("Vertex {} already exist in the graph.", vertex);
            return false;
        }

        adjacentVerticesMap.put(vertex, new ArrayList<>());

        logger.info("Added new vertex: {}", vertex);

        return true;
    }

    @Override
    public boolean hasVertex(final V vertex) {
        return adjacentVerticesMap.keySet().contains(vertex);
    }

    @Override
    public boolean removeVertex(final V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Couldn't remove null vertex.");
        }

        if (!hasVertex(vertex)) {
            logger.error("Couldn't remove vertex which is not in graph.");
            return false;
        }

        adjacentVerticesMap.remove(vertex);
        adjacentVerticesMap.forEach((v, adjacentVertices) -> adjacentVertices.remove(vertex));

        logger.info("Removed vertex: " + vertex);
        return true;
    }

    @Override
    public boolean addEdge(final V start, final V end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end vertex could not be null.");
        }

        if (!hasVertex(start) || !hasVertex(end)) {
            throw new IllegalArgumentException("Start or end vertex does not belong to graph.");
        }

        if (hasEdge(start, end)) {
            logger.info("Edge {} - {} already exist in the graph.", start, end);
            return false;
        }

        if (start.equals(end) && !loopsAllowed) {
            throw new IllegalArgumentException("Loop creation is not allowed.");
        }

        addAdjacentVertex(start, end);
        if (start.equals(end)) {
            logger.info("Added loop edge: {} - {}", start, end);
            return true;
        }

        if (!directed) {
            addAdjacentVertex(end, start);
        }

        logger.info("Added edge: {} - {}", start, end);
        return true;
    }

    @Override
    public boolean hasEdge(final V start, final V end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end vertex could not be null.");
        }

        if (!hasVertex(start) || !hasVertex(end)) {
            logger.error("Start or end vertex does not belong to graph.");
            return false;
        }

        return directed
                ? adjacentVerticesMap.get(start).contains(end)
                : adjacentVerticesMap.get(start).contains(end) || adjacentVerticesMap.get(end).contains(start);
    }

    @Override
    public boolean removeEdge(final V start, final V end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end vertex could not be null.");
        }

        if (!hasEdge(start, end)) {
            logger.error("Couldn't delete not existing edge.");
            return false;
        }

        adjacentVerticesMap.forEach((v, adjacentVertices) -> {
            adjacentVertices.remove(start);
            adjacentVertices.remove(end);
        });

        logger.info("Removed edge: {} - {}", start, end);

        return true;
    }

    private void addAdjacentVertex(final V currentVertex, final V adjacentVertex) {
        if (!adjacentVerticesMap.get(currentVertex).contains(adjacentVertex)) {
            adjacentVerticesMap.get(currentVertex).add(adjacentVertex);
        }
    }

    @Override
    public String getPath(final V source, final V destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Start and end vertices could not be null.");
        }

        if (adjacentVerticesMap.get(source) == null  || adjacentVerticesMap.get(destination) == null) {
            throw new IllegalArgumentException("Start or end vertices does not belong to graph.");
        }

        if (isEmpty(adjacentVerticesMap.get(source))
                || !directed && isEmpty(adjacentVerticesMap.get(destination))) {
            throw new IllegalArgumentException("Start or end vertex is not reachable.");
        }

        if (adjacentVerticesMap.get(source).contains(destination)) {
            return createEdge(source, destination);
        }

        final String result = getPathBFS(source, destination);
        logger.info("Path between {} and {} is: {}", source, destination, result);

        return result;
    }

    private boolean isEmpty(final Collection collection) {
        return collection == null || collection.isEmpty();
    }

    private String createEdge(final V start, final V end) {
        return start + " - " + end;
    }

    private String getPathBFS(final V source, final V destination) {
        final LinkedList<V> queue = new LinkedList<>();
        final Map<V, Boolean> visited = new HashMap<>();
        final Map<V, V> predecessor = new HashMap<>();

        adjacentVerticesMap.keySet().forEach(vertex -> {
            visited.put(vertex, false);
            predecessor.put(vertex, null);
        });

        visited.put(source, true);
        queue.add(source);

        while (!queue.isEmpty()) {
            final V current = queue.removeFirst();
            for (final V adjacent : adjacentVerticesMap.get(current)) {
                if (visited.get(adjacent)) {
                    continue;
                }

                visited.put(adjacent, true);
                predecessor.put(adjacent, current);

                if (adjacent.equals(destination)) {
                    return getPathString(predecessor, destination);
                }

                queue.addLast(adjacent);
            }
        }

        return "";
    }

    private String getPathString(final Map<V, V> predecessors, final V end) {
        final LinkedList<String> result = new LinkedList<>();
        V current = end;
        while (predecessors.get(current) != null) {
            result.addFirst(createEdge(predecessors.get(current), current));
            current = predecessors.get(current);
        }
        return String.join(", ", result);
    }

    @Override
    public String toString() {
        return "Graph: " + adjacentVerticesMap;
    }

    private class Edge {
        private V source;
        private V destination;

        Edge(final V source, final V destination) {
            this.source = source;
            this.destination = destination;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge that = (Edge) o;
            return Objects.equals(source, that.source) &&
                    Objects.equals(destination, that.destination);
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, destination);
        }
    }
}
