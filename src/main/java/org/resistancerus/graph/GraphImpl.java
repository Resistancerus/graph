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
    
    private final Map<V, Set<V>> adjacentVerticesMap = new HashMap<>();

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
    public Set<V> getVertices() {
        return Collections.unmodifiableSet(adjacentVerticesMap.keySet());
    }

    @Override
    public Set<V> getAdjacentVertices(V vertex) {
        return adjacentVerticesMap.containsKey(vertex) ? Collections.unmodifiableSet(adjacentVerticesMap.get(vertex)) : null;
    }

    @Override
    public boolean addVertex(final V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex could not be null.");
        }

        if (hasVertex(vertex)) {
            logger.debug("Vertex {} already exist in the graph.", vertex);
            return false;
        }

        adjacentVerticesMap.put(vertex, new HashSet<>());

        logger.debug("Added new vertex: {}", vertex);

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

        logger.debug("Removed vertex: " + vertex);
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
            logger.debug("Edge {} - {} already exist in the graph.", start, end);
            return false;
        }

        if (start.equals(end) && !loopsAllowed) {
            throw new IllegalArgumentException("Loop creation is not allowed.");
        }

        addAdjacentVertex(start, end);
        if (start.equals(end)) {
            logger.debug("Added loop edge: {} - {}", start, end);
            return true;
        }

        if (!directed) {
            addAdjacentVertex(end, start);
        }

        logger.debug("Added edge: {} - {}", start, end);
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

        logger.debug("Removed edge: {} - {}", start, end);

        return true;
    }

    @Override
    public String toString() {
        return "Graph: " + adjacentVerticesMap;
    }

    private void addAdjacentVertex(final V currentVertex, final V adjacentVertex) {
        if (!adjacentVerticesMap.get(currentVertex).contains(adjacentVertex)) {
            adjacentVerticesMap.get(currentVertex).add(adjacentVertex);
        }
    }
}
