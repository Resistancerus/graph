package org.resistancerus.graph;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

/**
 * Edge object containing source and destination vertices.
 * @author Malishevskii Oleg
 * @version 1.0
 */
@AllArgsConstructor
@EqualsAndHashCode
public class Edge<V> {
    @Getter
    private V source;

    @Getter
    private V destination;

    @Override
    public String toString() {
        return source + " - " + destination;
    }
}
