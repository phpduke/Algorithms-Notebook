# COPY THE BLACKBOX, there is no need to change anything in it.
# Check the main function at bottom for USAGE

# ****************BLACKBOX START*****************
# START COPYING FROM HERE

from collections import defaultdict, deque
from typing import Tuple, Union, Optional

class Hash:
    def __init__(self):
        self.hash_table = {}
    
    def hash(self, x):
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
            raise ValueError("Input must be int or tuple")
        return self.hash_table.setdefault(key, len(self.hash_table))


class Graph:
    def __init__(self, n, is_directed=True):
        self.n = n
        self.is_directed = is_directed
        self.h = Hash()
        self.adj = defaultdict(list)
    
    def hash(self, *args):
        if len(args) == 2:
            return self.h.hash((args[0], args[1]))
        elif len(args) == 3:
            return self.h.hash((args[0], args[1], args[2]))
        else:
            raise ValueError("hash() takes 2 or 3 arguments")
    
    def add_edge(self, u_r, v_r, c=0):
        u = self.h.hash(u_r)
        v = self.h.hash(v_r)
        self._add_edge_internal(u, v, c)
    
    def _add_edge_internal(self, u, v, c=0):
        self.adj[u].append((v, c))  
        if not self.is_directed:
            self.adj[v].append((u, c))


class BFS:
    def __init__(self, g):
        self.g = g
        self.clear()
    
    def clear(self):
        self.min_dist_from_source = {}
        self.visited = set()
    
    def run(self, source_r):
        source = self.g.h.hash(source_r)
        self._run_internal(source)
    
    def min_dist(self, target_r):
        target = self.g.h.hash(target_r)
        return self.min_dist_from_source.get(target, -1)  
    
    def is_visited(self, target_r):
        target = self.g.h.hash(target_r)
        return target in self.visited  
    
    def _run_internal(self, source):
        q = deque([source])
        self.visited.add(source)
        self.min_dist_from_source[source] = 0

        while q:
            cur = q.popleft()
            if cur not in self.g.adj:
                continue
                
            for adj, _ in self.g.adj[cur]:
                if adj not in self.visited:
                    self.visited.add(adj)
                    self.min_dist_from_source[adj] = self.min_dist_from_source[cur] + 1
                    q.append(adj)

# END COPYING HERE
# ********************BLACKBOX END******************


# USAGE EXAMPLE:
if __name__ == "__main__":
    g = Graph(5, is_directed=True)
    
    g.add_edge(1, 2)
    g.add_edge(2, 3)
    g.add_edge(1, 3)
    g.add_edge((1, 2), (3, 4))
    
    bfs = BFS(g)
    bfs.run(1)
    
    print(f"Distance from 1 to 3: {bfs.min_dist(3)}")
    print(f"Is 2 visited? {bfs.is_visited(2)}")
    
    # Extra test for tuple nodes
    bfs.clear()
    bfs.run((1, 2))
    print(f"Distance from (1,2) to (3,4): {bfs.min_dist((3, 4))}")