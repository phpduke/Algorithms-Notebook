import java.util.*;

/**
 * GraphX.java
 *
 * Complete Java port of the provided C++ GraphX "blackbox".
 * - Hash maps int / pair / triple into a shared integer id space.
 * - Graph supports addEdge for int/pair/triple keys.
 * - BFS supports run(...) and query methods for min distance and visited.
 *
 * Note: Implementation uses lazy adjacency storage (HashMap) instead of preallocating
 * a huge array to keep memory practical in Java while preserving behaviour.
 */
public class GraphX {

    /* ---------- Key classes for pair/triple semantics ---------- */

    public static final class TripleKey {
        public final int a, b, c;
        public TripleKey(int a, int b, int c) { this.a = a; this.b = b; this.c = c; }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TripleKey)) return false;
            TripleKey t = (TripleKey) o;
            return a == t.a && b == t.b && c == t.c;
        }
        @Override public int hashCode() {
            int res = 17;
            res = 31 * res + a;
            res = 31 * res + b;
            res = 31 * res + c;
            return res;
        }
        @Override public String toString() { return "{" + a + "," + b + "," + c + "}"; }
    }

    public static final class PairKey {
        public final int a, b;
        public PairKey(int a, int b) { this.a = a; this.b = b; }
        public TripleKey toTriple() { return new TripleKey(a, b, 0); }
        @Override public String toString() { return "(" + a + "," + b + ")"; }
    }

    /* ---------- Hash (maps keys -> consecutive ints) ---------- */

    public static final class Hash {
        private final Map<TripleKey, Integer> table = new HashMap<>();
        public Hash() {}

        // single int -> maps to triple (x,0,0)
        public int hash(int x) {
            return hash(new TripleKey(x, 0, 0));
        }
        // pair -> maps to triple (a,b,0)
        public int hash(PairKey p) {
            return hash(p.toTriple());
        }
        // triple -> canonical
        public int hash(TripleKey t) {
            Integer id = table.get(t);
            if (id != null) return id;
            int newId = table.size();
            table.put(t, newId);
            return newId;
        }
    }

    /* ---------- adjacency pair (to, weight) ---------- */

    public static final class AdjPair {
        public final int to;
        public final int weight;
        public AdjPair(int to, int weight) { this.to = to; this.weight = weight; }
    }

    /* ---------- Graph ---------- */

    public static final class Graph {
        private final boolean isDirected;
        // lazy adjacency list: nodeId -> list of (neighbor, weight)
        private final Map<Integer, ArrayList<AdjPair>> adj;
        public final int n; // user-provided n (kept for API consistency)
        public final int N = 5_000_000; // kept as config constant (not forcibly allocated)
        public final Hash h;

        public Graph(int n_) { this(n_, true); }
        public Graph(int n_, boolean isDirected_) {
            this.n = n_;
            this.isDirected = isDirected_;
            this.adj = new HashMap<>(); // lazy; more memory-friendly than preallocating N lists
            this.h = new Hash();
        }

        // convenience: mirror C++ hash(u,v) & hash(u,v,k)
        public int hash(int u, int v) { return h.hash(new TripleKey(u, v, 0)); }
        public int hash(int u, int v, int k) { return h.hash(new TripleKey(u, v, k)); }

        // add edge overloads (int, PairKey, TripleKey). weight defaults to 0.
        public void addEdge(int uR, int vR) { addEdge(uR, vR, 0); }
        public void addEdge(int uR, int vR, int c) {
            int u = h.hash(uR);
            int v = h.hash(vR);
            addEdgeInternal(u, v, c);
        }

        public void addEdge(PairKey uR, PairKey vR) { addEdge(uR, vR, 0); }
        public void addEdge(PairKey uR, PairKey vR, int c) {
            int u = h.hash(uR);
            int v = h.hash(vR);
            addEdgeInternal(u, v, c);
        }

        public void addEdge(TripleKey uR, TripleKey vR) { addEdge(uR, vR, 0); }
        public void addEdge(TripleKey uR, TripleKey vR, int c) {
            int u = h.hash(uR);
            int v = h.hash(vR);
            addEdgeInternal(u, v, c);
        }

        // internal: add and possibly reverse if undirected (mirrors C++ behavior)
        private void addEdgeInternal(int u, int v, int c) {
            addEdgeWeighted(u, v, c);
            if (!isDirected) {
                addEdgeWeighted(v, u, c);
            }
        }

        private void addEdgeWeighted(int u, int v, int c) {
            ArrayList<AdjPair> list = adj.computeIfAbsent(u, k -> new ArrayList<>());
            list.add(new AdjPair(v, c));
        }

        // Accessors to adjacency for advanced usage/testing
        public List<AdjPair> neighborsOf(int hashedNode) {
            return adj.getOrDefault(hashedNode, new ArrayList<>());
        }
    }

    /* ---------- BFS ---------- */

    public static final class BFS {
        private final Map<Integer, Integer> minDist; // node -> distance (unvisited absent -> -1)
        private final Set<Integer> visited;
        private final Graph g;

        public BFS(Graph g_) {
            this.g = g_;
            this.minDist = new HashMap<>();
            this.visited = new HashSet<>();
            clear();
        }

        public void clear() {
            minDist.clear();
            visited.clear();
        }

        // run overloads: int / PairKey / TripleKey
        public void run(int sourceR) {
            int source = g.h.hash(sourceR);
            runInternal(source);
        }
        public void run(PairKey sourceR) {
            int source = g.h.hash(sourceR);
            runInternal(source);
        }
        public void run(TripleKey sourceR) {
            int source = g.h.hash(sourceR);
            runInternal(source);
        }

        // minDist overloads
        public int minDist(int targetR) {
            int target = g.h.hash(targetR);
            return minDistInternal(target);
        }
        public int minDist(PairKey targetR) {
            int target = g.h.hash(targetR);
            return minDistInternal(target);
        }
        public int minDist(TripleKey targetR) {
            int target = g.h.hash(targetR);
            return minDistInternal(target);
        }

        // isVisited overloads
        public boolean isVisited(int targetR) {
            int target = g.h.hash(targetR);
            return isVisitedInternal(target);
        }
        public boolean isVisited(PairKey targetR) {
            int target = g.h.hash(targetR);
            return isVisitedInternal(target);
        }
        public boolean isVisited(TripleKey targetR) {
            int target = g.h.hash(targetR);
            return isVisitedInternal(target);
        }

        // BFS core (unweighted edges; distance measured in number of edges)
        private void runInternal(int source) {
            // reset
            minDist.clear();
            visited.clear();

            Queue<Integer> q = new ArrayDeque<>();
            q.add(source);
            visited.add(source);
            minDist.put(source, 0);

            while (!q.isEmpty()) {
                int cur = q.poll();
                int curDist = minDist.get(cur);
                List<AdjPair> neighbors = g.neighborsOf(cur);
                for (AdjPair p : neighbors) {
                    int nxt = p.to;
                    if (!visited.contains(nxt)) {
                        visited.add(nxt);
                        minDist.put(nxt, curDist + 1);
                        q.add(nxt);
                    }
                }
            }
        }

        private int minDistInternal(int target) {
            return minDist.getOrDefault(target, -1);
        }
        private boolean isVisitedInternal(int target) {
            return visited.contains(target);
        }
    }

    /* ---------- Example usage (main) ---------- */

    public static void main(String[] args) {
        // Simple example to show parity with the original blackbox usage.
        // Create a small undirected graph and run BFS.
        Graph g = new Graph(10, false); // undirected
        // add edges using plain ints (these will be hashed internally)
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(10, 5);

        // add edges using PairKey
        PairKey p1 = new PairKey(100, 200);
        PairKey p2 = new PairKey(100, 201);
        g.addEdge(p1, p2);

        // add edges using TripleKey
        TripleKey t1 = new TripleKey(7, 8, 9);
        TripleKey t2 = new TripleKey(7, 8, 10);
        g.addEdge(t1, t2);

        BFS bfs = new BFS(g);
        bfs.run(1);
        System.out.println("dist(1->2) = " + bfs.minDist(2)); // should be 1
        System.out.println("dist(1->4) = " + bfs.minDist(4)); // should be 3
        System.out.println("visited(4) = " + bfs.isVisited(4));

        // BFS from a PairKey node
        bfs.run(p1);
        System.out.println("dist(pair p1 -> p2) = " + bfs.minDist(p2));

        // BFS from a triple node
        bfs.run(t1);
        System.out.println("dist(triple t1 -> t2) = " + bfs.minDist(t2));
    }
}
