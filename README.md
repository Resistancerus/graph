Simple Graph Library
====================

Overview
---------

This simple graph library was created as a test project for interview. 
The library allows to:
* create directed or undirected graph
* calculate path as a list of edges between two vertices in the graph.

It provides:

__GraphFactory__

It is a factory class for Graph<V> instance creation via static methods:
* createGraph(boolean directed) - loopsAllowed is false by default.
* createGraph(boolean directed, boolean loopsAllowed)

_directed_ flag enables directed edges support. It influences path calculation.
_loopsAllowed_ flag enables loop edges creation.

_Usage:_

```java
    final Graph<MyVertex> = GraphFactory.createGraph(false, false);
```

_Note:_ Graph implementation is not thread-safe (according basic requirements).

__Graph<V> interface__

Interface representing graph object. It could store vertices of any user-defined type.
The interface provides the following methods:

* addVertex(V vertex)        - adds new vertex to graph. Returns true if succeded.
* hasVertex(V vertex)        - checks whether vertex exists in the graph. 
* removeVertex(V vertex)     - removes vertex from the graph. Returns true if succeded.
* addEdge(V start, V end)    - adds new edge to graph. Both vertices must exist. Returns true if succeded.
* hasEdge(V start, V end)    - checks whether edge exists in the graph.
* removeEdge(V start, V end) - removes edge from the graph.
* boolean isDirected()       - checks if graph supports directed edges.
* boolean areLoopsAllowed()  - checks if graph supports loop edges.

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

__Path Calculator__

This utility class is used to calculate path in a graph via:

* getPath(Graph V graph, V start, V end) - returns a list of edges between two provided vertices in a provided graph as Path object.

_Note:_ Loops are not usually presented in resulted path even if they are allowed. The single loop edge is returned only if we are getting path for its' vertex.

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

        Path<MyVertex> result = getPath(graph, a, c);
```

__Path<V>__

This class is used as a return value for getPath method. Its objects contain a list of Edge<V> objects.

__Edge<V>__

This class is used to represent edges in a result of path calculation. It contains source and target vertices of V type.
