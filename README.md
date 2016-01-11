# Centrality Service

The calculation code is divided in four main namespaces:

* __graph__: receives the list of edges and takes care of graph construction and manipulation
* __bfs-shortest-path__: implements the calculation of the shortest paths using **Breadth First Search**
* __score__: has the functions for calculating the scores based on the distances passed to it and the flags for fraudulent clients  
* __core__: puts it all together to make the operations required (ranking, add edge and mark client as fraudulent) available

I decided to maintain the shortest path calculation in its own namespace instead of putting it in the graph namespace because, this way, it is decoupled from the graph and can be changed easily (if we decide to use a better algorithm for instance). The graph is also decoupled from the way the data enters the application - via file or input. It only cares about receiving a list of edges.
I implemented an adjacency-lists structure to keep the graph and decided to use a map instead of a vector to make the initial construction easier, otherwise, I would have to read the whole file to allocate the needed vector - and since the access to items in clojure maps is pretty decent, I thought using a map instead wouldn’t be a problem.

Since the graph is unweighted, I implemented the shortest path using Breadth First Search. I thought about implementing the Dijkstra algorithm and preparing for weighted edges, but it performs worse than BFS (O(V*(E+V)) x O(V^3)) so my program would be prepared for a more generic graph, but would have a worse performance. With more time and access to the articles listed on shortest path wikipedia entry I could implement one of the all-pairs shortest path algorithms and have a performance of O(EV) (Thorup 1999) instead of running breadth first search for every vertice in the graph as I did.

The other two namespaces are:

* __util__: simple utility to read the edges from the edges.txt file in resources
* __handler__: maps the routes to the operations that need to be made

I didn’t put many comments in the code because I believe that readable code is better than comments and I tried to maintain the code as readable as possible. I only did that in places where I felt it was really necessary. 

## Installation

Download from http://example.com/FIXME.

## Endpoints

**GET /ranking** - ranks the clients

**POST /add-edge/:v1/to/:v2** - adds an edge from v1 to v2

**POST /mark-as-fraudulent/:client** - marks a client as fraudulent 

### Possible

* Maybe save some state in a database instead of keeping all the state in refs?
* Some validation in the parameters of the request to provide better errors than just "Internal error" for everything