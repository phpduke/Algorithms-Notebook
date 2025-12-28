# GraphX Python - Usage Guide

A Python implementation of the GraphX library for creating and analyzing graphs with support for both directed and undirected weighted graphs, featuring BFS traversal and flexible node identification.

## Table of Contents
- [Installation](#installation)
- [Core Components](#core-components)
- [Quick Start](#quick-start)
- [Detailed Usage](#detailed-usage)
  - [Hash Class](#hash-class)
  - [Graph Class](#graph-class)
  - [BFS Class](#bfs-class)
- [Examples](#examples)
- [API Reference](#api-reference)

## Installation

Simply import the module into your Python project:

```python
from graphx import Hash, Graph, BFS
```

**Requirements:** Python 3.6+ (uses type hints and deque from collections)

## Core Components

GraphX provides three main classes:

1. **Hash** - Maps tuples to unique integer IDs
2. **Graph** - Creates and manages directed/undirected weighted graphs
3. **BFS** - Performs Breadth-First Search traversal

## Quick Start

Here's a simple example to get you started:

```python
from graphx import Graph, BFS

# Create an undirected graph with 5 nodes
g = Graph(n=5, is_directed=False)

# Add edges
g.add_edge(0, 1, weight=5)
g.add_edge(1, 2, weight=3)
g.add_edge(2, 3, weight=7)

# Run BFS from node 0
bfs = BFS(g)
bfs.run(source=0)

# Get minimum distance to node 3
distance = bfs.min_dist(3)
print(f"Distance from 0 to 3: {distance}")  # Output: 3

# Check if node was visited
if bfs.is_visited(3):
    print("Node 3 was reached!")
```

## Detailed Usage

### Hash Class

The `Hash` class provides a mapping from tuples to unique integer IDs.

```python
from graphx import Hash

h = Hash()

# Hash integers
id1 = h.hash(5)          # Returns 0
id2 = h.hash(10)         # Returns 1
id3 = h.hash(5)          # Returns 0 (same as before)

# Hash 2-tuples
id4 = h.hash((1, 2))     # Returns 2
id5 = h.hash((3, 4))     # Returns 3

# Hash 3-tuples
id6 = h.hash((1, 2, 3))  # Returns 4
```

**Key Features:**
- Consistent hashing: same input always returns same ID
- Supports int, 2-tuple, and 3-tuple inputs
- Automatically normalizes inputs to 3-tuples internally

### Graph Class

The `Graph` class supports both directed and undirected weighted graphs.

#### Creating a Graph

```python
from graphx import Graph

# Directed graph
g_directed = Graph(n=10, is_directed=True)

# Undirected graph
g_undirected = Graph(n=10, is_directed=False)
```

#### Adding Edges

```python
# Simple integer nodes
g.add_edge(0, 1, weight=5)

# Tuple nodes (useful for grid-based graphs)
g.add_edge((0, 0), (0, 1), weight=1)
g.add_edge((0, 1), (1, 1), weight=2)

# 3-tuple nodes
g.add_edge((0, 0, 0), (1, 1, 1), weight=10)

# Default weight is 0
g.add_edge(2, 3)  # weight=0
```

**Directed vs Undirected:**
- **Directed**: edge from u → v only
- **Undirected**: edges created in both directions (u ↔ v)

### BFS Class

The `BFS` class performs Breadth-First Search traversal.

#### Basic BFS

```python
from graphx import Graph, BFS

# Create graph
g = Graph(n=5, is_directed=True)
g.add_edge(0, 1)
g.add_edge(1, 2)
g.add_edge(2, 3)

# Initialize BFS
bfs = BFS(g)

# Run from source node 0
bfs.run(source=0)

# Query results
print(bfs.min_dist(3))      # Minimum distance to node 3
print(bfs.is_visited(3))    # True if node 3 was reached
```

#### Clearing and Re-running

```python
# Run BFS from node 0
bfs.run(0)
print(bfs.min_dist(3))  # Distance from 0 to 3

# Clear state and run from different source
bfs.clear()
bfs.run(1)
print(bfs.min_dist(3))  # Distance from 1 to 3
```

## Examples

### Example 1: Simple Path Graph

```python
from graphx import Graph, BFS

# Create a directed path: 0 → 1 → 2 → 3
g = Graph(n=4, is_directed=True)
g.add_edge(0, 1)
g.add_edge(1, 2)
g.add_edge(2, 3)

bfs = BFS(g)
bfs.run(0)

print(f"Distance to node 1: {bfs.min_dist(1)}")  # 1
print(f"Distance to node 2: {bfs.min_dist(2)}")  # 2
print(f"Distance to node 3: {bfs.min_dist(3)}")  # 3
```

### Example 2: Undirected Weighted Graph

```python
from graphx import Graph, BFS

# Create an undirected weighted graph
g = Graph(n=5, is_directed=False)
g.add_edge(0, 1, weight=10)
g.add_edge(0, 2, weight=5)
g.add_edge(1, 3, weight=7)
g.add_edge(2, 3, weight=3)

bfs = BFS(g)
bfs.run(0)

# BFS finds shortest path by number of edges, not weight
print(f"Distance 0 to 3: {bfs.min_dist(3)}")  # 2 (via node 1 or 2)
```

**Note:** BFS finds the shortest path by **number of edges**, not by weight sum. For weighted shortest paths, use Dijkstra's algorithm.

### Example 3: Grid Graph with Tuple Nodes

```python
from graphx import Graph, BFS

# Create a 3x3 grid graph using (row, col) tuples
g = Graph(n=9, is_directed=False)

# Add horizontal edges
for row in range(3):
    for col in range(2):
        g.add_edge((row, col), (row, col + 1))

# Add vertical edges
for col in range(3):
    for row in range(2):
        g.add_edge((row, col), (row + 1, col))

# Find distance from top-left to bottom-right
bfs = BFS(g)
bfs.run((0, 0))
distance = bfs.min_dist((2, 2))
print(f"Distance from (0,0) to (2,2): {distance}")  # 4
```

### Example 4: Detecting Connected Components

```python
from graphx import Graph, BFS

# Graph with two separate components
g = Graph(n=6, is_directed=False)

# Component 1: nodes 0, 1, 2
g.add_edge(0, 1)
g.add_edge(1, 2)

# Component 2: nodes 3, 4, 5
g.add_edge(3, 4)
g.add_edge(4, 5)

# Run BFS from node 0
bfs = BFS(g)
bfs.run(0)

# Check which nodes are reachable
for node in range(6):
    if bfs.is_visited(node):
        print(f"Node {node} is in same component as 0")
    else:
        print(f"Node {node} is NOT reachable from 0")
```

### Example 5: Graph with Cycles

```python
from graphx import Graph, BFS

# Create a cyclic graph
g = Graph(n=4, is_directed=True)
g.add_edge(0, 1)
g.add_edge(1, 2)
g.add_edge(2, 3)
g.add_edge(3, 1)  # Cycle back to node 1

bfs = BFS(g)
bfs.run(0)

# BFS handles cycles correctly
print(f"Distance to 1: {bfs.min_dist(1)}")  # 1
print(f"Distance to 2: {bfs.min_dist(2)}")  # 2
print(f"Distance to 3: {bfs.min_dist(3)}")  # 3
```

## API Reference

### Hash Class

```python
h = Hash()
```

**Methods:**
- `hash(x)` - Hash an int or tuple to unique integer ID
  - Parameters: `x` (int | tuple[int, int] | tuple[int, int, int])
  - Returns: `int` (unique ID)

### Graph Class

```python
g = Graph(n, is_directed=True)
```

**Constructor:**
- `n` (int) - Number of nodes
- `is_directed` (bool) - True for directed, False for undirected (default: True)

**Methods:**
- `add_edge(u, v, weight=0)` - Add an edge to the graph
  - `u` - Source node (int or tuple)
  - `v` - Destination node (int or tuple)
  - `weight` (int) - Edge weight (default: 0)

**Attributes:**
- `n` - Number of nodes
- `is_directed` - Graph type
- `adj` - Adjacency list (internal use)
- `h` - Hash instance (internal use)

### BFS Class

```python
bfs = BFS(graph)
```

**Constructor:**
- `graph` (Graph) - Graph instance to traverse

**Methods:**
- `run(source)` - Run BFS from source node
  - `source` - Starting node (int or tuple)

- `min_dist(target)` - Get minimum distance to target
  - `target` - Target node (int or tuple)
  - Returns: `int` (-1 if unreachable)

- `is_visited(target)` - Check if node was visited
  - `target` - Target node (int or tuple)
  - Returns: `bool`

- `clear()` - Reset BFS state for re-running

## Testing

Run the included test suite:

```bash
python test_graphx.py
```

The test suite includes:
- Hash functionality tests
- Graph creation and edge addition tests
- BFS traversal tests
- Integration tests

## Performance Notes

- The graph uses a pre-allocated adjacency list of size 5,000,000
- Suitable for most applications with millions of nodes
- BFS has O(V + E) time complexity
- Hash lookups are O(1) on average

## Common Patterns

### Pattern 1: Check Reachability

```python
g = Graph(n=10, is_directed=True)
# ... add edges ...

bfs = BFS(g)
bfs.run(source=0)

if bfs.is_visited(target):
    print(f"There is a path from 0 to {target}")
else:
    print(f"No path exists from 0 to {target}")
```

### Pattern 2: Find Shortest Path Distance

```python
g = Graph(n=10, is_directed=False)
# ... add edges ...

bfs = BFS(g)
bfs.run(source=start)

distance = bfs.min_dist(end)
if distance == -1:
    print("No path exists")
else:
    print(f"Shortest path has {distance} edges")
```

### Pattern 3: Multiple BFS Runs

```python
g = Graph(n=10, is_directed=False)
# ... add edges ...

bfs = BFS(g)

# Run from multiple sources
sources = [0, 5, 9]
for src in sources:
    bfs.clear()
    bfs.run(src)
    # Process results...
```

## Limitations

- BFS finds shortest path by **edge count**, not by weight
- For weighted shortest paths, implement Dijkstra's or Bellman-Ford
- Maximum node capacity is 5,000,000 (can be modified by changing `N` in Graph class)
- No built-in path reconstruction (only distance and reachability)

## License

This is a Python port of the C++ GraphX library.
