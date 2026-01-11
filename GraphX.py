from collections import deque
from typing import Tuple, Dict, List


ll = int


class Hash:
    def __init__(self):
        self.hash_table: Dict[Tuple[int, int, int], int] = {}

    def hash(self, *args):
        if len(args) == 1:
            key = (args[0], 0, 0)
        elif len(args) == 2:
            key = (args[0], args[1], 0)
        elif len(args) == 3:
            key = (args[0], args[1], args[2])
        else:
            raise ValueError("Invalid number of arguments")

        if key in self.hash_table:
            return self.hash_table[key]

        new_hash = len(self.hash_table)
        self.hash_table[key] = new_hash
        return new_hash


class Graph:
    def __init__(self, n: int, is_directed: bool = True):
        self.n = n
        self.is_directed = is_directed
        self.N = 5_000_000
        self.adj: List[List[Tuple[int, ll]]] = [[] for _ in range(self.N)]
        self.h = Hash()

    def hash(self, *args):
        return self.h.hash(*args)

    def add_edge(self, uR, vR, c: ll = 0):
        u = self.h.hash(*uR) if isinstance(uR, tuple) else self.h.hash(uR)
        v = self.h.hash(*vR) if isinstance(vR, tuple) else self.h.hash(vR)
        self._add_edge_internal(u, v, c)

    def _add_edge_internal(self, u: int, v: int, c: ll):
        self._add_edge_weighted_undirected(u, v, c)
        if not self.is_directed:
            self._add_edge_weighted_undirected(v, u, c)

    def _add_edge_weighted_undirected(self, u: int, v: int, c: ll):
        self.adj[u].append((v, c))


class BFS:
    def __init__(self, g: Graph):
        self.g = g
        self.clear()

    def clear(self):
        self.min_dist_from_source = [-1] * self.g.N
        self.visited = [False] * self.g.N

    def run(self, sourceR):
        source = self.g.h.hash(*sourceR) if isinstance(sourceR, tuple) else self.g.h.hash(sourceR)
        self._run_internal(source)

    def min_dist(self, targetR):
        target = self.g.h.hash(*targetR) if isinstance(targetR, tuple) else self.g.h.hash(targetR)
        return self.min_dist_from_source[target]

    def is_visited(self, targetR):
        target = self.g.h.hash(*targetR) if isinstance(targetR, tuple) else self.g.h.hash(targetR)
        return self.visited[target]

    def _run_internal(self, source: int):
        q = deque()
        q.append(source)

        self.visited[source] = True
        self.min_dist_from_source[source] = 0

        while q:
            cur_node = q.popleft()
            for adj_node, _ in self.g.adj[cur_node]:
                if not self.visited[adj_node]:
                    self.visited[adj_node] = True
                    self.min_dist_from_source[adj_node] = (
                        self.min_dist_from_source[cur_node] + 1
                    )
                    q.append(adj_node)
