import java.util.*;

// ✅ Triple class for 3D hashing (like tuple<int,int,int>)
class Triple {
    int a, b, c;

    public Triple(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Triple)) return false;
        Triple other = (Triple) o;
        return a == other.a && b == other.b && c == other.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}

// ✅ Hash class to convert tuples to unique ints
class Hash {
    Map<Triple, Integer> hashTable = new HashMap<>();
    int counter = 0;

    int hash(int x) {
        return hash(new Triple(x, 0, 0));
    }

    int hash(int x, int y) {
        return hash(new Triple(x, y, 0));
    }

    int hash(int x, int y, int z) {
        return hash(new Triple(x, y, z));
    }

    int hash(Triple t) {
        return hashTable.computeIfAbsent(t, k -> counter++);
    }
}

// ✅ Graph class with custom hash and edge support
class Graph {
    boolean isDirected;
    List<List<Pair>> adj;
    int N = 500000; // you can resize if needed
    Hash h = new Hash();

    public Graph(boolean isDirected) {
        this.isDirected = isDirected;
        adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
    }

    void addEdge(int u, int v, int weight) {
        int hashedU = h.hash(u);
        int hashedV = h.hash(v);
        addEdgeInternal(hashedU, hashedV, weight);
    }

    void addEdge(Triple u, Triple v, int weight) {
        int hashedU = h.hash(u);
        int hashedV = h.hash(v);
        addEdgeInternal(hashedU, hashedV, weight);
    }

    private void addEdgeInternal(int u, int v, int weight) {
        adj.get(u).add(new Pair(v, weight));
        if (!isDirected) {
            adj.get(v).add(new Pair(u, weight));
        }
    }

    static class Pair {
        int node, weight;

        public Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }
}

// ✅ BFS traversal with minDist and isVisited
class BFS {
    Graph g;
    int[] minDist;
    boolean[] visited;

    public BFS(Graph g) {
        this.g = g;
        minDist = new int[g.N];
        Arrays.fill(minDist, -1);
        visited = new boolean[g.N];
    }

    void run(int source) {
        int s = g.h.hash(source);
        runInternal(s);
    }

    void run(Triple source) {
        int s = g.h.hash(source);
        runInternal(s);
    }

    private void runInternal(int source) {
        Queue<Integer> q = new LinkedList<>();
        visited[source] = true;
        minDist[source] = 0;
        q.offer(source);

        while (!q.isEmpty()) {
            int curr = q.poll();
            for (Graph.Pair p : g.adj.get(curr)) {
                int adjNode = p.node;
                if (!visited[adjNode]) {
                    visited[adjNode] = true;
                    minDist[adjNode] = minDist[curr] + 1;
                    q.offer(adjNode);
                }
            }
        }
    }

    int minDist(int target) {
        return minDist[g.h.hash(target)];
    }

    int minDist(Triple target) {
        return minDist[g.h.hash(target)];
    }

    boolean isVisited(int target) {
        return visited[g.h.hash(target)];
    }

    boolean isVisited(Triple target) {
        return visited[g.h.hash(target)];
    }
}

// ✅ Sample usage
public class BlackboxDemo {
    public static void main(String[] args) {
        Graph g = new Graph(false); // false = undirected

        // Add normal node edges
        g.addEdge(1, 2, 1);
        g.addEdge(2, 3, 1);

        // Add custom tuple-based nodes
        g.addEdge(new Triple(4, 5, 0), new Triple(7, 8, 0), 1);
        g.addEdge(new Triple(7, 8, 0), new Triple(9, 9, 9), 1);

        BFS bfs = new BFS(g);

        // Run BFS from simple node
        bfs.run(1);
        System.out.println("Distance from 1 to 3: " + bfs.minDist(3)); // should be 2
        System.out.println("Is node 3 visited? " + bfs.isVisited(3));

        // Run BFS from a tuple node
        bfs = new BFS(g);
        bfs.run(new Triple(4, 5, 0));
        System.out.println("Distance to (9,9,9): " + bfs.minDist(new Triple(9, 9, 9))); // should be 2
        System.out.println("Visited (9,9,9)? " + bfs.isVisited(new Triple(9, 9, 9)));
    }
}