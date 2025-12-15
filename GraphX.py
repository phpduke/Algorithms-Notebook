import heapq
from collections import deque


class NodeIndexer:
    __slots__ = ("map",)

    def __init__(self):
        self.map = {}

    def get_or_create(self, key):
        idx = self.map.get(key)
        if idx is None:
            idx = len(self.map)
            self.map[key] = idx
        return idx

    def get(self, key):
        return self.map.get(key)

    def size(self):
        return len(self.map)


class Graph:
    __slots__ = ("directed", "indexer", "adj")

    def __init__(self, directed=True):
        self.directed = directed
        self.indexer = NodeIndexer()
        self.adj = []

    def _ensure(self, u):
        if u >= len(self.adj):
            self.adj.extend([] for _ in range(u + 1 - len(self.adj)))

    def add_edge(self, u, v, w=0):
        # normalize nodes → tuple
        if not isinstance(u, tuple):
            u = (u,)
        if not isinstance(v, tuple):
            v = (v,)

        ui = self.indexer.get_or_create(u)
        vi = self.indexer.get_or_create(v)

        self._ensure(ui)
        self._ensure(vi)

        self.adj[ui].append((vi, w))
        if not self.directed:
            self.adj[vi].append((ui, w))

    def id_of(self, node):
        if not isinstance(node, tuple):
            node = (node,)
        idx = self.indexer.get(node)
        if idx is None:
            raise ValueError("Node not present")
        return idx

    def size(self):
        return self.indexer.size()


class BFS:
    __slots__ = ("g", "dist")

    def __init__(self, g):
        self.g = g
        self.dist = []

    def run(self, source):
        if not isinstance(source, tuple):
            source = (source,)
        s = self.g.id_of(source)
        n = self.g.size()

        dist = [-1] * n
        dist[s] = 0
        q = deque([s])

        while q:
            u = q.popleft()
            for v, _ in self.g.adj[u]:
                if dist[v] == -1:
                    dist[v] = dist[u] + 1
                    q.append(v)

        self.dist = dist

    def min_dist(self, target):
        if not isinstance(target, tuple):
            target = (target,)
        return self.dist[self.g.id_of(target)]


class Dijkstra:
    __slots__ = ("g", "dist")

    def __init__(self, g):
        self.g = g
        self.dist = []

    def run(self, source):
        if not isinstance(source, tuple):
            source = (source,)
        s = self.g.id_of(source)
        n = self.g.size()

        dist = [float("inf")] * n
        dist[s] = 0
        pq = [(0, s)]

        while pq:
            d, u = heapq.heappop(pq)

            if d != dist[u]:
                continue

            for v, w in self.g.adj[u]:
                nd = d + w
                if nd < dist[v]:
                    dist[v] = nd
                    heapq.heappush(pq, (nd, v))

        self.dist = dist

    def min_dist(self, target):
        if not isinstance(target, tuple):
            target = (target,)
        return self.dist[self.g.id_of(target)]


# ======================
# Example
# ======================

if __name__ == "__main__":
    g = Graph(directed=True)

    g.add_edge(1, 2, 5)
    g.add_edge(1, 3, 3)
    g.add_edge(2, 4, 6)
    g.add_edge(3, 4, 2)
    g.add_edge(4, 5, 8)

    bfs = BFS(g)
    bfs.run(1)
    print("BFS 1 → 5:", bfs.min_dist(5))

    d = Dijkstra(g)
    d.run(1)
    print("Dijkstra 1 → 5:", d.min_dist(5))

    g.add_edge((0, 0), (0, 1), 10)
    g.add_edge((0, 1), (1, 1), 15)

    d.run((0, 0))
    print("Distance to (1,1):", d.min_dist((1, 1)))
