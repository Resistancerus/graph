package org.resistancerus.graph;

/**
 * @author Malishevskii Oleg
 * @version 1.0
 */
public interface Edge {

    /**
     * @return start Vertex object
     * @see Vertex
     */
    Vertex getStart();

    /**
     * @return end Vertex object
     * @see Vertex
     */
    Vertex getEnd();

    // TODO add direction.
}
