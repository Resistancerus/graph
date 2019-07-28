Simple Graph Library
====================

Overview
---------

This simple graph library was created as a test project for interview. 
The library allows to create a graph with a user-defined vertices.

It provides:

__GraphFactory__

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

_Note:_ Graph implementation is not thread-safe (according basic requirements).

__Graph<V> interface__

Iterface representing graph object. It could store vertices of any user-defined type.
The interface provides the following methods:

* addVertex(V vertex)        - adds new vertex to graph. Returns true if succeded.
* hasVertex(V vertex)        - checks whether vertex exists in the graph. 
* removeVertex(V vertex)     - removes vertex from the graph. Returns true if succeded.
* addEdge(V start, V end)    - adds new edge to graph. Both vertices must exist. Returns true if succeded.
* hasEdge(V start, V end)    - checks whether edge exists in the graph.
* removeEdge(V start, V end) - removes edge from the graph.
* boolean isDirected()       - checks if graph supports directed edges.
* boolean areLoopsAllowed()  - checks if graph supports loop edges.
* getPath(V start, V end)    - returns a list of edges between two provided vertices as a comma-separated values within String.

_Usage:_

```java
        final MyVertex a = new MyVertex("a");
        final MyVertex b = new MyVertex("b");
        final MyVertex c = new MyVertex("c");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.addEdge(a, b);
        graph.addEdge(b, c);
```

