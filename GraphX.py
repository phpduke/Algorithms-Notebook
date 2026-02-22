#COPY THE BLACKBOX, there is no need to change anything in it.
#Check the main function at bottom for USAGE

#****************BLACKBOX START*****************
#START COPYING FROM HERE


from collections import deque

# Hash class: maps (x,y,z) → unique integer
class Hash:
    def __init__(self):
        self.hash_table = {}

    def hash(self, x, y=0, z=0):
        key = (x, y, z)
        if key in self.hash_table:
            return self.hash_table[key]
        new_hash = len(self.hash_table)
        self.hash_table[key] = new_hash
        return new_hash


# Graph class
class Graph:
    def __init__(self, n, is_directed=True):
        self.n = n
        self.is_directed = is_directed
        self.N = 5000000
        self.h = Hash()
        self.adj = [[] for _ in range(self.N)]

    def add_edge(self, uR, vR, c=0):
        u = self.h.hash(uR)
        v = self.h.hash(vR)
        self._add_edge_internal(u, v, c)

    def _add_edge_internal(self, u, v, c):
        self._add_edge_weighted(u, v, c)
        if not self.is_directed:
            self._add_edge_weighted(v, u, c)

    def _add_edge_weighted(self, u, v, c):
        self.adj[u].append((v, c))


# BFS class
class BFS:
    def __init__(self, graph):
        self.g = graph
        self.clear()

    # Reset visited and distance arrays
    def clear(self):
        self.min_dist_from_source = [-1] * self.g.N
        self.visited = [False] * self.g.N

    # Run BFS from a source
    def run(self, sourceR):
        source = self.g.h.hash(sourceR)
        self._run_internal(source)

    def _run_internal(self, source):
        q = deque()
        q.append(source)

        self.visited[source] = True
        self.min_dist_from_source[source] = 0

        while q:
            cur = q.popleft()
            for adj_node, _ in self.g.adj[cur]:
                if not self.visited[adj_node]:
                    self.visited[adj_node] = True
                    self.min_dist_from_source[adj_node] = self.min_dist_from_source[cur] + 1
                    q.append(adj_node)

    # Get minimum distance
    def min_dist(self, targetR):
        target = self.g.h.hash(targetR)
        return self.min_dist_from_source[target]

    # Check if visited
    def is_visited(self, targetR):
        target = self.g.h.hash(targetR)
        return self.visited[target]

#END COPYING HERE
#********************BLACKBOX END******************
