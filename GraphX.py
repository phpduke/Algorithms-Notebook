from collections import deque, defaultdict

#****************BLACKBOX START*****************
#START COPYING FROM HERE

class Hash:
    def __init__(self):
        self.mp = {}
        self.cnt = 0

    def hash(self, *args):
        key = tuple(args) + (0,) * (3 - len(args))
        if key not in self.mp:
            self.mp[key] = self.cnt
            self.cnt += 1
        return self.mp[key]


class Graph:
    def __init__(self, directed=True):
        self.directed = directed
        self.h = Hash()
        self.adj = defaultdict(list)

    def add_edge(self, uR, vR):
        u = self.h.hash(*uR) if isinstance(uR, tuple) else self.h.hash(uR)
        v = self.h.hash(*vR) if isinstance(vR, tuple) else self.h.hash(vR)
        self.adj[u].append(v)
        if not self.directed:
            self.adj[v].append(u)


class BFS:
    def __init__(self, g):
        self.g = g
        self.dist = {}

    def run(self, sourceR):
        src = self.g.h.hash(*sourceR) if isinstance(sourceR, tuple) else self.g.h.hash(sourceR)
        self.dist = {src: 0}
        q = deque([src])

        while q:
            cur = q.popleft()
            for nxt in self.g.adj[cur]:
                if nxt not in self.dist:
                    self.dist[nxt] = self.dist[cur] + 1
                    q.append(nxt)

    def min_dist(self, targetR):
        t = self.g.h.hash(*targetR) if isinstance(targetR, tuple) else self.g.h.hash(targetR)
        return self.dist.get(t, -1)

    def is_visited(self, targetR):
        t = self.g.h.hash(*targetR) if isinstance(targetR, tuple) else self.g.h.hash(targetR)
        return t in self.dist

#END COPYING HERE
#****************BLACKBOX END******************
