import java.util.*;

//****************BLACKBOX START*****************
//START COPYING FROM HERE

class Hash {

    private Map<Triple, Integer> hashTable;

    public Hash() {
        hashTable = new HashMap<>();
    }

    public int hash(int x) {
        return hash(new Triple(x, 0, 0));
    }

    public int hash(Pair x) {
        return hash(new Triple(x.first, x.second, 0));
    }

    public int hash(Triple x) {
        if (hashTable.containsKey(x)) {
            return hashTable.get(x);
        }
        int newHash = hashTable.size();
        hashTable.put(x, newHash);
        return newHash;
    }
}

/* ---------- Helper Classes ---------- */

class Pair {
    int first, second;

    Pair(int f, int s) {
        first = f;
        second = s;
    }
}

class Triple {
    int first, second, third;

    Triple(int f, int s, int t) {
        first = f;
        second = s;
        third = t;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Triple)) return false;
        Triple t = (Triple) o;
        return first == t.first && second == t.second && third == t.third;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}

/* ---------- Graph Class ---------- */

class Graph {

    boolean isDirected;
    ArrayList<ArrayList<Pair>> adj;
    int n;
    int N = 5_000_000;
    Hash h;

    Graph(int n_, boolean isDirected_) {
        n = n_;
        isDirected = isDirected_;
        h = new Hash();
        adj = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
    }

    int hash(int u, int v) {
        return h.hash(new Pair(u, v));
    }

    int hash(int u, int v, int k) {
        return h.hash(new Triple(u, v, k));
    }

    void addEdge(int uR, int vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }

    void addEdge(Pair uR, Pair vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }

    void addEdge(Triple uR, Triple vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }

    private void addEdgeInternal(int u, int v, int c) {
        addEdgeWeighted(u, v, c);
        if (!isDirected) {
            addEdgeWeighted(v, u, c);
        }
    }

    private void addEdgeWeighted(int u, int v, int c) {
        adj.get(u).add(new Pair(v, c));
    }
}

/* ---------- BFS Class ---------- */

class BFS {

    ArrayList<Integer> minDistFromSource;
    boolean[] visited;
    Graph g;

    BFS(Graph g_) {
        g = g_;
        clear();
    }

    void clear() {
        minDistFromSource = new ArrayList<>(Collections.nCopies(g.N, -1));
        visited = new boolean[g.N];
    }

    void run(int sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    void run(Pair sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    void run(Triple sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    int minDist(int targetR) {
        int target = g.h.hash(targetR);
        return minDistFromSource.get(target);
    }

    int minDist(Pair targetR) {
        int target = g.h.hash(targetR);
        return minDistFromSource.get(target);
    }

    int minDist(Triple targetR) {
        int target = g.h.hash(targetR);
        return minDistFromSource.get(target);
    }

    boolean isVisited(int targetR) {
        int target = g.h.hash(targetR);
        return visited[target];
    }

    boolean isVisited(Pair targetR) {
        int target = g.h.hash(targetR);
        return visited[target];
    }

    boolean isVisited(Triple targetR) {
        int target = g.h.hash(targetR);
        return visited[target];
    }

    private void runInternal(int source) {
        Queue<Integer> q = new LinkedList<>();
        q.add(source);

        visited[source] = true;
        minDistFromSource.set(source, 0);

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (Pair p : g.adj.get(cur)) {
                int adjNode = p.first;
                if (!visited[adjNode]) {
                    visited[adjNode] = true;
                    minDistFromSource.set(adjNode, minDistFromSource.get(cur) + 1);
                    q.add(adjNode);
                }
            }
        }
    }
}

//END COPYING HERE
//****************BLACKBOX END******************
