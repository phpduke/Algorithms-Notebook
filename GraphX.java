import java.util.*;

//****************BLACKBOX START*****************
//START COPYING FROM HERE

class HashBox {
    private final Map<List<Integer>, Integer> map = new HashMap<>();
    private int cnt = 0;

    int hash(int x) {
        return hash(new int[]{x, 0, 0});
    }

    int hash(int x, int y) {
        return hash(new int[]{x, y, 0});
    }

    int hash(int x, int y, int z) {
        return hash(new int[]{x, y, z});
    }

    private int hash(int[] arr) {
        List<Integer> key = Arrays.asList(arr[0], arr[1], arr[2]);
        if (!map.containsKey(key)) {
            map.put(key, cnt++);
        }
        return map.get(key);
    }
}

class Graph {
    boolean directed;
    HashBox h = new HashBox();

    // Lazy adjacency construction for memory efficiency
    Map<Integer, ArrayList<Integer>> adj = new HashMap<>();

    Graph(boolean directed) {
        this.directed = directed;
    }

    void add_edge(int uR, int vR) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        add_internal(u, v);
    }

    void add_edge(int u1, int u2, int v1, int v2) {
        int u = h.hash(u1, u2);
        int v = h.hash(v1, v2);
        add_internal(u, v);
    }

    void add_edge(int u1, int u2, int u3, int v1, int v2, int v3) {
        int u = h.hash(u1, u2, u3);
        int v = h.hash(v1, v2, v3);
        add_internal(u, v);
    }

    private void add_internal(int u, int v) {
        adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
        if (!directed) {
            adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
    }
}

class BFS {
    Graph g;
    Map<Integer, Integer> dist = new HashMap<>();

    BFS(Graph g) {
        this.g = g;
    }

    void run(int sourceR) {
        int src = g.h.hash(sourceR);
        run_internal(src);
    }

    void run(int x, int y) {
        int src = g.h.hash(x, y);
        run_internal(src);
    }

    void run(int x, int y, int z) {
        int src = g.h.hash(x, y, z);
        run_internal(src);
    }

    private void run_internal(int src) {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(src);
        dist.clear();
        dist.put(src, 0);

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int nxt : g.adj.getOrDefault(cur, Collections.emptyList())) {
                if (!dist.containsKey(nxt)) {
                    dist.put(nxt, dist.get(cur) + 1);
                    q.add(nxt);
                }
            }
        }
    }

    int min_dist(int targetR) {
        int t = g.h.hash(targetR);
        return dist.getOrDefault(t, -1);
    }

    boolean is_visited(int targetR) {
        int t = g.h.hash(targetR);
        return dist.containsKey(t);
    }
}

//END COPYING HERE
//****************BLACKBOX END******************
