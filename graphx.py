from collections import deque
from typing import Tuple, Union, Dict, List, Optional


class Hash:
    """Hash class for mapping tuples to unique integer IDs."""
    
    def __init__(self):
        self.hash_table: Dict[Tuple, int] = {}
    
    def hash(self, x: Union[int, Tuple[int, int], Tuple[int, int, int]]) -> int:
        """Hash a value (int or tuple) to a unique integer ID."""
        if isinstance(x, int):
            key = (x, 0, 0)
        elif isinstance(x, tuple):
            if len(x) == 2:
                key = (x[0], x[1], 0)
            elif len(x) == 3:
                key = x
            else:
                raise ValueError("Tuple must have 2 or 3 elements")
        else:
            raise TypeError("Input must be int or tuple")
        
        if key not in self.hash_table:
            self.hash_table[key] = len(self.hash_table)
        
        return self.hash_table[key]


class Graph:
    """Graph class supporting directed and undirected weighted graphs."""
    
    def __init__(self, n: int, is_directed: bool = True):
        """
        Initialize graph.
        
        Args:
            n: Number of nodes
            is_directed: True for directed graph, False for undirected
        """
        self.n = n
        self.is_directed = is_directed
        self.N = 5000000
        self.adj: List[List[Tuple[int, int]]] = [[] for _ in range(self.N)]
        self.h = Hash()
    
    def hash(self, u: Union[int, Tuple[int, int]], 
             v: Optional[Union[int, Tuple[int, int]]] = None,
             k: Optional[int] = None) -> int:
        """Hash node identifier(s)."""
        if k is not None:
            return self.h.hash((u, v, k))
        elif v is not None:
            return self.h.hash((u, v))
        else:
            return self.h.hash(u)
    
    def add_edge(self, u: Union[int, Tuple[int, int], Tuple[int, int, int]], 
                 v: Union[int, Tuple[int, int], Tuple[int, int, int]], 
                 weight: int = 0):
        """
        Add an edge to the graph.
        
        Args:
            u: Source node (int or tuple)
            v: Destination node (int or tuple)
            weight: Edge weight (default 0)
        """
        u_hash = self.h.hash(u)
        v_hash = self.h.hash(v)
        self._add_edge_internal(u_hash, v_hash, weight)
    
    def _add_edge_internal(self, u: int, v: int, weight: int = 0):
        """Internal method to add edge."""
        self._add_edge_weighted_undirected(u, v, weight)
        if not self.is_directed:
            self._add_edge_weighted_undirected(v, u, weight)
    
    def _add_edge_weighted_undirected(self, u: int, v: int, weight: int):
        """Add weighted edge to adjacency list."""
        self.adj[u].append((v, weight))


class BFS:
    """Breadth-First Search implementation."""
    
    def __init__(self, graph: Graph):
        """
        Initialize BFS.
        
        Args:
            graph: Graph instance to perform BFS on
        """
        self.g = graph
        self.min_dist_from_source: List[int] = []
        self.visited: List[bool] = []
        self.clear()
    
    def clear(self):
        """Reset BFS state."""
        self.min_dist_from_source = [-1] * self.g.N
        self.visited = [False] * self.g.N
    
    def run(self, source: Union[int, Tuple[int, int], Tuple[int, int, int]]):
        """
        Run BFS from source node.
        
        Args:
            source: Source node (int or tuple)
        """
        source_hash = self.g.h.hash(source)
        self._run_internal(source_hash)
    
    def min_dist(self, target: Union[int, Tuple[int, int], Tuple[int, int, int]]) -> int:
        """
        Get minimum distance to target node.
        
        Args:
            target: Target node (int or tuple)
            
        Returns:
            Minimum distance from source to target (-1 if unreachable)
        """
        target_hash = self.g.h.hash(target)
        return self._min_dist_internal(target_hash)
    
    def is_visited(self, target: Union[int, Tuple[int, int], Tuple[int, int, int]]) -> bool:
        """
        Check if target node was visited.
        
        Args:
            target: Target node (int or tuple)
            
        Returns:
            True if node was visited during BFS
        """
        target_hash = self.g.h.hash(target)
        return self._is_visited_internal(target_hash)
    
    def _run_internal(self, source: int):
        """Internal BFS implementation."""
        q = deque([source])
        self.visited[source] = True
        self.min_dist_from_source[source] = 0
        
        while q:
            cur_node = q.popleft()
            for adj_node, _ in self.g.adj[cur_node]:
                if not self.visited[adj_node]:
                    self.visited[adj_node] = True
                    self.min_dist_from_source[adj_node] = self.min_dist_from_source[cur_node] + 1
                    q.append(adj_node)
    
    def _min_dist_internal(self, target: int) -> int:
        """Get minimum distance (internal)."""
        return self.min_dist_from_source[target]
    
    def _is_visited_internal(self, target: int) -> bool:
        """Check if visited (internal)."""
        return self.visited[target]
