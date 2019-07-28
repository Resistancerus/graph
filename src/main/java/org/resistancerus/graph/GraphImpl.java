package org.resistancerus.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;

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
    private final Map<Edge, String> pathsCache = new HashMap<>();

    GraphImpl(final boolean directed, boolean loopsAllowed) {
        this.directed = directed;
        this.loopsAllowed = loopsAllowed;
    }

    public boolean isDirected() {
        return directed;
    }

    public boolean isLoopsAllowed() {
        return loopsAllowed;
    }

    @Override
    public void addVertex(final V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex could not be null.");
        }

        adjacentVerticesMap.computeIfAbsent(vertex, adjacentVertices -> {
            logger.info("Added new vertex {}", vertex);
            return new ArrayList<>();
        });
    }

    @Override
    public Collection<V> getVertices() {
        return unmodifiableCollection(adjacentVerticesMap.keySet());
    }

    @Override
    public void addEdge(final V start, final V end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end could not be null.");
        }

        if (start == end && !loopsAllowed) {
            throw new IllegalArgumentException("Loop creation is not allowed.");
        }

        adjacentVerticesMap.computeIfAbsent(start, adjacentVertices -> new ArrayList<>());
        addAdjacentVertex(start, end);

        if (start == end) {
            logger.info("Added loop edge {} - {}", start, end);
            return;
        }

        adjacentVerticesMap.computeIfAbsent(end, adjacentVertices -> new ArrayList<>());
        if (!directed) {
            addAdjacentVertex(end, start);
        }

        logger.info("Added edge {} - {}", start, end);
    }

    @Override
    public String getPath(final V source, final V destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Start and End vertices could not be null.");
        }

        if (adjacentVerticesMap.get(source) == null  || adjacentVerticesMap.get(destination) == null) {
            throw new IllegalArgumentException("Start or End vertices does not belong to graph.");
        }

        if (isEmpty(adjacentVerticesMap.get(source))
                || !directed && isEmpty(adjacentVerticesMap.get(destination))) {
            throw new IllegalArgumentException("Start or End vertices are not reachable.");
        }

        if (adjacentVerticesMap.get(source).contains(destination)) {
            return createEdge(source, destination);
        }

        final Edge cachedPathKey = new Edge(source, destination);
        if (pathsCache.get(cachedPathKey) != null) {
            return pathsCache.get(cachedPathKey);
        }

        final String result = getPathBFS(source, destination);
        logger.info("Path between {} and {} is: {}", source, destination, result);

        pathsCache.putIfAbsent(new Edge(source, destination), result);

        return result;
    }

    private void addAdjacentVertex(final V currentVertex, final V adjacentVertex) {
        if (!adjacentVerticesMap.get(currentVertex).contains(adjacentVertex)) {
            adjacentVerticesMap.get(currentVertex).add(adjacentVertex);
        }
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
