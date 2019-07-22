package org.resistancerus.graph.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.resistancerus.graph.Edge;
import org.resistancerus.graph.Vertex;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEdge implements Edge {
    private Vertex start;
    private Vertex end;

    @Override
    public Vertex getStart() {
        return start;
    }

    @Override
    public Vertex getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestEdge other = (TestEdge) o;
        return Objects.equals(start, other.start) && Objects.equals(end, other.end)
                || Objects.equals(start, other.end) && Objects.equals(end, other.start);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Edge " + start + " - " + end;
    }
}
