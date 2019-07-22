Simple Graph Library
====================

Overview
---------

This library was created as a test project for interview. It contains the following classes:

*GraphFactory*

It is a simple util class providing two static methods returning Graph object:

* GraphFactory::getDirectedGraph() to create an instance of directed Graph.
* GraphFactory::getUndirectedGraph() to create an instance of undirected Graph.

*Graph*

Represents graph object. It provides the following interface:

* Graph::addVertex(Vertex v) to add new vertex.
* Graph::addEdge(Edge e) to add new edge. Its verices wil be added to graph automatically if not exist.
* Graph::getPath(Vertex start, Vertex end) - returns a list of edges between two provided vertices.

_Note 1:_ The library does not provide default implementation for Vertex and Edge, so users should implement Vertex and Edge interfaces on their own. 

_Note 2:_ Graph object is not thread-safe yet.

*Vertex*

Represents graph node. Default implementation is not provided.

*Edge*

Represents graph edge between two vertices. Default implementation is not provided. It has two methods:

* Edge::getStart() - returns first Vertex.
* Edge::getEnd() - returns second Vertex.

Usage
-----
```java
        final Graph graph = GraphFactory.getUndirectedGraph();
        
        final Vertex a = new TestVertex("a");
        final Vertex b = new TestVertex("b");
        final Vertex c = new TestVertex("c");

        final Edge abEdge = new TestEdge(a, b);
        final Edge acEdge = new TestEdge(a, c);

        graph.addEdge(abEdge);
        graph.addEdge(acEdge);

        final List<Edge> result = graph.getPath(a, c);
```
