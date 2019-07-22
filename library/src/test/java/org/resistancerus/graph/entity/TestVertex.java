package org.resistancerus.graph.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.resistancerus.graph.Vertex;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestVertex implements Vertex {
    private String name;
}
