# GraphX.py
# Clean Pythonic version of GraphX.cpp

from collections import defaultdict, deque


class Hash:
    def __init__(self):
        self._table = {}
        self._counter = 0

    def get(self, key):
        if isinstance(key, int):
            key = (key, 0, 0)
        elif isinstance(key, tuple):
            if len(key) == 2:
                key = (key[0], key[1], 0)
            elif len(key) == 3:
                key = key
            else:
                raise TypeError("Invalid tuple length for key.")
        else:
            raise TypeError("Invalid key type.")

        if key not in self._table:
            self._table[key] = self._counter
            self._counter += 1

        return self._table[key]

    def __call__(self, key):
        return self.get(key)


class Graph:
    def __init__(self, is_directed=True):
        self.is_directed = is_directed
        self.h = Hash()
        self.adj = defaultdict(list)

    def _id(self, x):
        return self.h(x)

    def add_edge(self, u, v, weight=0):
        uid = self._id(u)
        vid = self._id(v)
        self.adj[uid].append((vid, weight))
        if not self.is_directed:
            self.adj[vid].append((uid, weight))


class BFS:
    def __init__(self, graph):
        self.g = graph
        self.clear()

    def clear(self):
        self.dist = {}
        self.vis = {}

    def run(self, source):
        start = self.g.h(source)

        q = deque([start])
        self.dist = {start: 0}
        self.vis = {start: True}

        while q:
            cur = q.popleft()
            for nxt, _ in self.g.adj[cur]:
                if nxt not in self.vis:
                    self.vis[nxt] = True
                    self.dist[nxt] = self.dist[cur] + 1
                    q.append(nxt)

    def min_dist(self, target):
        tid = self.g.h(target)
        return self.dist.get(tid, -1)

    def is_visited(self, target):
        tid = self.g.h(target)
        return self.vis.get(tid, False)
