from collections import defaultdict, deque

# ****************BLACKBOX START*****************
# START COPYING FROM HERE

class Hash:
    def __init__(self):
        self.hash_table = {}

    def hash(self, x):
        # Normalize input to a tuple of 3 ints
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
            raise TypeError(f"Unsupported type: {type(x)}")

        if key not in self.hash_table:
            self.hash_table[key] = len(self.hash_table)
        return self.hash_table[key]


class Graph:
    N = 5000000

    def __init__(self, n, is_directed=True):
        self.n = n
        self.is_directed = is_directed
        self.adj = defaultdict(list)  # sparse, no need to pre-allocate N slots
        self.h = Hash()

    def hash(self, u, v, k=None):
        if k is None:
            return self.h.hash((u, v))
        return self.h.hash((u, v, k))

    def add_edge(self, u_raw, v_raw, c=0):
        u = self.h.hash(u_raw)
        v = self.h.hash(v_raw)
        self._add_edge_internal(u, v, c)

    def _add_edge_internal(self, u, v, c=0):
        self._add_edge_weighted_undirected(u, v, c)
        if not self.is_directed:
            self._add_edge_weighted_undirected(v, u, c)

    def _add_edge_weighted_undirected(self, u, v, c):
        self.adj[u].append((v, c))


class BFS:
    def __init__(self, graph):
        self.g = graph
        self.clear()

    def clear(self):
        self.min_dist_from_source = defaultdict(lambda: -1)
        self.visited = defaultdict(bool)

    def run(self, source_raw):
        source = self.g.h.hash(source_raw)
        self._run_internal(source)

    def min_dist(self, target_raw):
        target = self.g.h.hash(target_raw)
        return self.min_dist_from_source[target]

    def is_visited(self, target_raw):
        target = self.g.h.hash(target_raw)
        return self.visited[target]

    def _run_internal(self, source):
        q = deque()
        q.append(source)
        self.visited[source] = True
        self.min_dist_from_source[source] = 0

        while q:
            cur_node = q.popleft()
            for adj_node, _ in self.g.adj[cur_node]:
                if not self.visited[adj_node]:
                    self.visited[adj_node] = True
                    self.min_dist_from_source[adj_node] = self.min_dist_from_source[cur_node] + 1
                    q.append(adj_node)

# END COPYING HERE
# ********************BLACKBOX END******************


# ---- USAGE (main) ----
if __name__ == "__main__":
    # Example 1: Integer nodes
    g1 = Graph(n=5, is_directed=False)
    g1.add_edge(0, 1)
    g1.add_edge(1, 2)
    g1.add_edge(2, 3)
    g1.add_edge(3, 4)

    bfs1 = BFS(g1)
    bfs1.run(0)
    print("Integer nodes BFS from 0:")
    for node in range(5):
        print(f"  dist to {node} = {bfs1.min_dist(node)}")

    # Example 2: 2D tuple nodes (e.g., grid coordinates)
    g2 = Graph(n=9, is_directed=False)
    g2.add_edge((0, 0), (0, 1))
    g2.add_edge((0, 1), (0, 2))
    g2.add_edge((0, 2), (1, 2))

    bfs2 = BFS(g2)
    bfs2.run((0, 0))
    print("\n2D tuple nodes BFS from (0,0):")
    for node in [(0,0), (0,1), (0,2), (1,2)]:
        print(f"  dist to {node} = {bfs2.min_dist(node)}")

    # Example 3: 3D tuple nodes
    g3 = Graph(n=4, is_directed=False)
    g3.add_edge((0, 0, 0), (0, 0, 1))
    g3.add_edge((0, 0, 1), (0, 1, 1))

    bfs3 = BFS(g3)
    bfs3.run((0, 0, 0))
    print("\n3D tuple nodes BFS from (0,0,0):")
    for node in [(0,0,0), (0,0,1), (0,1,1)]:
        print(f"  dist to {node} = {bfs3.min_dist(node)}")