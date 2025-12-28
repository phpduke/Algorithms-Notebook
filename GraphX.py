import collections

# **************** BLACKBOX START *****************
# START COPYING FROM HERE

class Hash:
    def __init__(self):
        # Maps a tuple (x, y, z) to a unique integer ID
        self.hash_table = {}

    def get_hash(self, x):
        """
        Mimics the overloaded hash() methods from C++.
        Accepts int, tuple(2), or tuple(3).
        """
        key = None
        
        if isinstance(x, int):
            # C++: hash(int x) -> hash({x, 0, 0})
            key = (x, 0, 0)
        elif isinstance(x, tuple):
            if len(x) == 2:
                # C++: hash(tuple<int,int> x) -> hash({get<0>(x), get<1>(x), 0})
                key = (x[0], x[1], 0)
            elif len(x) == 3:
                # C++: hash(tuple<int,int,int> x)
                key = x
        
        if key is None:
            raise ValueError("Unsupported key type. Must be int, (int, int), or (int, int, int)")

        if key in self.hash_table:
            return self.hash_table[key]
        
        new_hash = len(self.hash_table)
        self.hash_table[key] = new_hash
        return new_hash

class Graph:
    def __init__(self, n, is_directed=True):
        self.n = n
        self.is_directed = is_directed
        self.h = Hash()
        self.N = 5000000
        # Using defaultdict is more efficient in Python than pre-allocating a list of 5 million
        # Maps node_id -> list of (neighbor_id, cost)
        self.adj = collections.defaultdict(list)

    def hash_args(self, *args):
        """
        Mimics Graph::hash(u, v) and Graph::hash(u, v, k)
        Usage: g.hash_args(u, v) or g.hash_args(u, v, k)
        """
        if len(args) == 2:
            return self.h.get_hash((args[0], args[1]))
        elif len(args) == 3:
            return self.h.get_hash((args[0], args[1], args[2]))
        return -1

    def add_edge(self, uR, vR, c=0):
        """
        Mimics the overloaded add_edge methods. 
        uR and vR can be int, (u, v), or (u, v, k).
        """
        u = self.h.get_hash(uR)
        v = self.h.get_hash(vR)
        self._add_edge_internal(u, v, c)

    def _add_edge_internal(self, u, v, c=0):
        self._add_edge_weighted_undirected(u, v, c)
        if not self.is_directed:
            self._add_edge_weighted_undirected(v, u, c)

    def _add_edge_weighted_undirected(self, u, v, c):
        self.adj[u].append((v, c))

class BFS:
    def __init__(self, g):
        self.g = g
        self.min_dist_from_source = {}
        self.visited = set()
        self.clear()

    def clear(self):
        # In Python, we use dicts/sets rather than fixed vectors
        self.min_dist_from_source = {} 
        self.visited = set()

    def run(self, sourceR):
        source = self.g.h.get_hash(sourceR)
        self._run_internal(source)

    def min_dist(self, targetR):
        target = self.g.h.get_hash(targetR)
        return self._min_dist_internal(target)

    def is_visited(self, targetR):
        target = self.g.h.get_hash(targetR)
        return self._is_visited_internal(target)

    def _run_internal(self, source):
        q = collections.deque([source])
        
        self.visited.add(source)
        self.min_dist_from_source[source] = 0

        while q:
            cur_node = q.popleft()
            
            # Access neighbors
            if cur_node in self.g.adj:
                for adj_node, weight in self.g.adj[cur_node]:
                    if adj_node not in self.visited:
                        self.visited.add(adj_node)
                        self.min_dist_from_source[adj_node] = self.min_dist_from_source[cur_node] + 1
                        q.append(adj_node)

    def _min_dist_internal(self, target):
        return self.min_dist_from_source.get(target, -1)

    def _is_visited_internal(self, target):
        return target in self.visited

# END COPYING HERE
# ******************** BLACKBOX END ******************
