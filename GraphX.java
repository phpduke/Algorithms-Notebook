//COPY THE BLACKBOX, there is no need to change anything in it.
//Check the main function at bottom for USAGE

//****************BLACKBOX START*****************
//START COPYING FROM HERE

import java.util.*;

// Equivalent of typedef int ll;
typedef class LL {
    // Just using int directly in Java
}

// Hash class: maps (x,y,z) → unique integer
class Hash {
    private Map<String, Integer> hashTable = new HashMap<>();

    // Hash single int
    public int hash(int x) {
        return hash(x, 0, 0);
    }

    // Hash pair (x, y)
    public int hash(int x, int y) {
        return hash(x, y, 0);
    }

    // Hash triple (x, y, z)
    public int hash(int x, int y, int z) {
        String key = x + "," + y + "," + z;
        if (hashTable.containsKey(key))
            return hashTable.get(key);

        int newHash = hashTable.size();
        hashTable.put(key, newHash);
        return newHash;
    }
}

// Graph class
class Graph {
    boolean isDirected;
    public List<List<Pair>> adj;
    public int n;
    public int N = 5000000; // max possible nodes
    public Hash h;

    // Pair class for (neighbor, weight)
    static class Pair {
        int v;
        int w;
        Pair(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }

    Graph(int n_, boolean isDirected_) {
        n = n_;
        isDirected = isDirected_;
        h = new Hash();
        adj = new ArrayList<>();
        for (int i = 0; i < N; i++)
            adj.add(new ArrayList<>());
    }

    public int hash(int u, int v) {
        return h.hash(u, v);
    }

    public int hash(int u, int v, int k) {
        return h.hash(u, v, k);
    }

    // Add edge using raw ints
    public void addEdge(int uR, int vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }

    private void addEdgeInternal(int u, int v, int c) {
        addEdgeWeighted(u, v, c);
        if (!isDirected)
            addEdgeWeighted(v, u, c);
    }

    private void addEdgeWeighted(int u, int v, int c) {
        adj.get(u).add(new Pair(v, c));
    }
}

// BFS class
class BFS {
    private int[] minDist;
    private boolean[] visited;
    private Graph g;

    BFS(Graph g_) {
        g = g_;
        clear();
    }

    // Reset arrays
    public void clear() {
        minDist = new int[g.N];
        Arrays.fill(minDist, -1);
        visited = new boolean[g.N];
    }

    // Run BFS from int node
    public void run(int sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    private void runInternal(int source) {
        Queue<Integer> q = new LinkedList<>();
        q.add(source);

        visited[source] = true;
        minDist[source] = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (Graph.Pair p : g.adj.get(cur)) {
                int adjNode = p.v;
                if (!visited[adjNode]) {
                    visited[adjNode] = true;
                    minDist[adjNode] = minDist[cur] + 1;
                    q.add(adjNode);
                }
            }
        }
    }

    public int minDist(int targetR) {
        int target = g.h.hash(targetR);
        return minDist[target];
    }

    public boolean isVisited(int targetR) {
        int target = g.h.hash(targetR);
        return visited[target];
    }
}

//END COPYING HERE
//********************BLACKBOX END******************
