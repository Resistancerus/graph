package org.resistancerus.graph.entity;

/**
 * Simple implementation of vertex for tests.
 * @author Malishevskii Oleg
 * @version 1.0
 */
public class TestVertex {
    private String name;

    public TestVertex(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
