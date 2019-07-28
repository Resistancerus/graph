package org.resistancerus.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * PathCalculator utility class.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public abstract class PathCalculator {
    private static Logger logger = LoggerFactory.getLogger(GraphImpl.class);

    /**
     * Returns a path between two vertices of the graph.
     * @param graph       - graph to calculate path for.
     * @param source      - source vertex.
     * @param destination - destination vertex.
     * @return Stringed list of edges between two provided vertices or empty list if path does not exist.
     * @see Graph
     */
    public static <V> Path<V> getPath(final Graph<V> graph,
                                      final V source,
                                      final V destination) {
        if (graph == null || source == null || destination == null) {
            throw new IllegalArgumentException("Graph, source and destination vertices could not be null.");
        }

        if (!graph.hasVertex(source) || isDestinationReachable(graph, destination)) {
            logger.error("Source or destination vertices does not belong to graph.");
            return new Path<>();
        }

        if (isSourceReachable(graph, source) || isDestinationReachable(graph, destination)) {
            logger.error("Source or destination vertex is not reachable.");
            return new Path<>();
        }

        if (graph.hasEdge(source, destination)) {
            LinkedList<Edge<V>> result = new LinkedList<>();
            result.add(new Edge<>(source, destination));
            logger.debug("Path in graph {} between {} and {} is: {}", graph, source, destination, result);
            return new Path<>(result);
        }

        final Path<V> result = getPathBFS(graph, source, destination);
        logger.debug("Path in graph {} between {} and {} is: {}", graph, source, destination, result);
        return result;
    }

    /**
     * Returns a path between two vertices of the graph calculated via BFS algorithm.
     * @param graph       - graph to calculate path for.
     * @param source      - source vertex.
     * @param destination - destination vertex.
     * @return Stringed list of edges between two provided vertices or empty list if path does not exist.
     * @see Graph
     */
    private static <V> Path<V> getPathBFS(final Graph<V> graph,
                                          final V source,
                                          final V destination) {
        final LinkedList<V> queue = new LinkedList<>();
        final Map<V, Boolean> visited = new HashMap<>();
        final Map<V, V> predecessor = new HashMap<>();

        graph.getVertices().forEach(vertex -> {
            visited.put(vertex, false);
            predecessor.put(vertex, null);
        });

        visited.put(source, true);
        queue.add(source);

        while (!queue.isEmpty()) {
            final V current = queue.removeFirst();
            for (final V adjacent : graph.getAdjacentVertices(current)) {
                if (visited.get(adjacent)) {
                    continue;
                }

                visited.put(adjacent, true);
                predecessor.put(adjacent, current);

                if (adjacent.equals(destination)) {
                    return createPathObject(predecessor, destination);
                }

                queue.addLast(adjacent);
            }
        }

        return new Path<>();
    }

    /**
     * Checks if destination vertex is reachable.
     * @param destination vertex to check.
     * @return true if destination vertex is reachable.
     * @see Graph
     */
    private static <V> boolean isDestinationReachable(final Graph<V> graph, final V destination) {
        return !graph.isDirected() && isEmpty(graph.getAdjacentVertices(destination));
    }

    /**
     * Checks if source vertex is reachable.
     * @param source vertex to check.
     * @return true if source vertex is reachable.
     * @see Graph
     */
    private static <V> boolean isSourceReachable(final Graph<V> graph, final V source) {
        return isEmpty(graph.getAdjacentVertices(source));
    }

    /**
     * Returns a path between two vertices of the graph.
     * @param collection collection to check.
     * @return true if collection is empty or null, false otherwise.
     */
    private static boolean isEmpty(final Collection collection) {
        return collection != null && collection.isEmpty();
    }

    /**
     * Returns a path between two vertices of the graph.
     * @param previousVertexMap contains pairs of vertex and its predecessor vertex.
     * @param finalVertex destination vertex of the path.
     * @return Path object containing list of Edge<V> objects.
     * @see Path
     */
    private static <V> Path<V> createPathObject(final Map<V, V> previousVertexMap, final V finalVertex) {
        final LinkedList<Edge<V>> result = new LinkedList<>();

        V current = finalVertex;
        while (previousVertexMap.get(current) != null) {
            result.addFirst(new Edge<>(previousVertexMap.get(current), current));
            current = previousVertexMap.get(current);
        }
        return new Path<>(result);
    }
}
