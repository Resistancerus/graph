Simple Graph Library
====================

Overview
---------

This simple graph library was created as a test project for interview. 
The library allows to create a graph with a user-defined vertices.

It provides:

__Graph<V> interface__

Represents graph object. It contains the following methods:
It could store vertices of any user-defined type.

* addVertex(V vertex)     - adds new vertex to graph. 
* addEdge(V start, V end) - adds new edge to graph. Its verices wil be added to graph automatically if not exist. Loop edges are allowed.
* getPath(V start, V end) - returns a list of edges between two provided vertices as a comma-separated values within String.
* getVertices()           - returns unmodifiable collection of V vertices.

_Note:_ Graph objects are not thread-safe (according basic requirements).

_Usage:_

```java       
        final MyVertex a = new MyVertex("a");
        final MyVertex b = new MyVertex("b");
        final MyVertex c = new MyVertex("c");

        graph.addEdge(a, b);
        graph.addEdge(b, c);

        final String result = graph.getPath(a, c); // result will be "a - b, b - c"
```

__GraphFactory class__

It is a factory class for Graph<V> instance creation via static methods:
* createGraph(boolean directed)
* createGraph(boolean directed, boolean loopsAllowed)

_directed_ flag enables directed edges support. It influences path calculation.
_loopsAllowed_ flag enables loop edges creation.

_Usage:_

```java
    final GraphFactory<MyVertex> factory = new GraphFactory<>();
    final Graph<MyVertex> = factory.createGraph(false);
```
