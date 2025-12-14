import java.util.*;

final class NodeKey {
    private final int[] coords;
    private final int hash;

    NodeKey(int... coords) {
        this.coords = coords;
        this.hash = Arrays.hashCode(coords);
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NodeKey)) return false;
        return Arrays.equals(coords, ((NodeKey) o).coords);
    }
}

final class NodeIndexer {
    private final Map<NodeKey, Integer> map = new HashMap<>();

    int getOrCreate(int... coords) {
        return map.computeIfAbsent(new NodeKey(coords), k -> map.size());
    }

    Integer get(int... coords) {
        return map.get(new NodeKey(coords));
    }

    int size() {
        return map.size();
    }
}

class Graph {
    static class Edge {
        final int to;
        final long w;

        Edge(int t, long w) {
            this.to = t;
            this.w = w;
        }
    }

    private final boolean directed;
    private final NodeIndexer indexer = new NodeIndexer();
    private final List<List<Edge>> adj = new ArrayList<>();

    public Graph(boolean directed) {
        this.directed = directed;
    }

    private void ensure(int u) {
        while (adj.size() <= u) adj.add(new ArrayList<>());
    }

    public void addEdge(int from, int to, int w) {
        addEdge(new int[] {from}, new int[] {to}, w);
    }

    public void addEdge(int[] from, int[] to, long w) {
        int u = indexer.getOrCreate(from);
        int v = indexer.getOrCreate(to);

        ensure(u);
        ensure(v);

        adj.get(u).add(new Edge(v, w));
        if (!directed) adj.get(v).add(new Edge(u, w));
    }

    public void addEdge(int[] from, int[] to) {
        addEdge(from, to, 0);
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 0);
    }

    int idOf(int... node) {
        Integer id = indexer.get(node);
        if (id == null) throw new IllegalArgumentException("Node not present");
        return id;
    }

    int size() {
        return indexer.size();
    }

    List<Edge> edges(int u) {
        return adj.get(u);
    }
}

class Dijkstra {
    private final Graph g;
    private long[] dist;
    private boolean[] visited;

    public Dijkstra(Graph g) {
        this.g = g;
    }

    public void run(int... source) {
        int s = g.idOf(source);
        int n = g.size();

        dist = new long[n];
        visited = new boolean[n];
        Arrays.fill(dist, Long.MAX_VALUE);

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        dist[s] = 0;
        pq.add(new long[] {s, 0});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            int u = (int) cur[0];

            if (visited[u]) continue;
            visited[u] = true;

            for (Graph.Edge e : g.edges(u)) {
                if (!visited[e.to] && dist[u] + e.w < dist[e.to]) {
                    dist[e.to] = dist[u] + e.w;
                    pq.add(new long[] {e.to, dist[e.to]});
                }
            }
        }
    }

    public long minDist(int... target) {
        return dist[g.idOf(target)];
    }
}

class BFS {
    private final Graph g;
    private int[] dist;

    public BFS(Graph g) {
        this.g = g;
    }

    public void run(int... source) {
        int s = g.idOf(source);
        int n = g.size();

        dist = new int[n];
        Arrays.fill(dist, -1);

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(s);
        dist[s] = 0;

        while (!q.isEmpty()) {
            int u = q.poll();
            for (Graph.Edge e : g.edges(u)) {
                if (dist[e.to] == -1) {
                    dist[e.to] = dist[u] + 1;
                    q.add(e.to);
                }
            }
        }
    }

    public int minDist(int... target) {
        return dist[g.idOf(target)];
    }
}

public class GraphXBetter {
    public static void main(String[] args) {
        Graph g = new Graph(true);

        // Add weighted edges
        g.addEdge(1, 2, 5);
        g.addEdge(1, 3, 3);
        g.addEdge(2, 4, 6);
        g.addEdge(3, 4, 2);
        g.addEdge(4, 5, 8);

        // Run BFS from node 1
        BFS bfs = new BFS(g);
        bfs.run(1);
        System.out.println("BFS Distance from 1 to 5: " + bfs.minDist(5));

        // Run Dijkstra from node 1
        Dijkstra dijkstra = new Dijkstra(g);
        dijkstra.run(1);
        System.out.println("Dijkstra Distance from 1 to 5: " + dijkstra.minDist(5));

        // Example with tuple-like nodes (using coordinates)
        g.addEdge(new int[] {0, 0}, new int[] {0, 1}, 10);
        g.addEdge(new int[] {0, 1}, new int[] {1, 1}, 15);

        dijkstra.run(0, 0);
        System.out.println("Distance to (1,1): " + dijkstra.minDist(1, 1));
    }
}
