package org.resistancerus.graph.entity;

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
