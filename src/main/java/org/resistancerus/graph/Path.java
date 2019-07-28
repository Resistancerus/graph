package org.resistancerus.graph;

import lombok.*;

import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Path containing list of Edges.
 * @author Malishevskii Oleg
 * @version 1.0
 * @see Edge
 */

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Path<V> {

    @Getter
    @Setter
    private List<Edge<V>> edges = new LinkedList<>();

    @Override
    public String toString() {
        return String.join(", ", edges.stream().map(Object::toString).collect(toList()));
    }
}
