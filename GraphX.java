import java.util.*;

// ****************BLACKBOX START*****************
// START COPYING FROM HERE

class Hash {
    private Map<String, Integer> keyMap = new HashMap<>();

    public Hash() {}

    private int hashInternal(int x, int y, int z) {
        String key = x + "," + y + "," + z;
        if (!keyMap.containsKey(key)) {
            int newHash = keyMap.size();
            keyMap.put(key, newHash);
        }
        return keyMap.get(key);
    }

    public int hash(int x) {
        return hashInternal(x, 0, 0);
    }

    public int hash(int x, int y) {
        return hashInternal(x, y, 0);
    }

    public int hash(int x, int y, int z) {
        return hashInternal(x, y, z);
    }
}

class Graph {
    boolean isDirected;
    List<List<int[]>> adj;
    int n;
    static final int N = 5000000;
    Hash h;

    public Graph(int n, boolean isDirected) {
        this.n = n;
        this.isDirected = isDirected;
        this.h = new Hash();
        this.adj = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public Graph(int n) {
        this(n, true);
    }

    public int hash(int u, int v) {
        return h.hash(u, v);
    }

    public int hash(int u, int v, int k) {
        return h.hash(u, v, k);
    }

    // int node
    public void addEdge(int uR, int vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }
    public void addEdge(int uR, int vR) { addEdge(uR, vR, 0); }

    // 2-tuple node
    public void addEdge(int uX, int uY, int vX, int vY, int c) {
        int u = h.hash(uX, uY);
        int v = h.hash(vX, vY);
        addEdgeInternal(u, v, c);
    }
    public void addEdge(int uX, int uY, int vX, int vY) { addEdge(uX, uY, vX, vY, 0); }

    // 3-tuple node
    public void addEdge(int uX, int uY, int uZ, int vX, int vY, int vZ, int c) {
        int u = h.hash(uX, uY, uZ);
        int v = h.hash(vX, vY, vZ);
        addEdgeInternal(u, v, c);
    }
    public void addEdge(int uX, int uY, int uZ, int vX, int vY, int vZ) {
        addEdge(uX, uY, uZ, vX, vY, vZ, 0);
    }

    private void addEdgeInternal(int u, int v, int c) {
        addEdgeWeighted(u, v, c);
        if (!isDirected) {
            addEdgeWeighted(v, u, c);
        }
    }

    private void addEdgeWeighted(int u, int v, int c) {
        adj.get(u).add(new int[]{v, c});
    }
}

class BFS {
    private int[] minDistFromSource;
    private boolean[] visited;
    private Graph g;

    public BFS(Graph g) {
        this.g = g;
        clear();
    }

    public void clear() {
        minDistFromSource = new int[Graph.N];
        visited = new boolean[Graph.N];
        Arrays.fill(minDistFromSource, -1);
        Arrays.fill(visited, false);
    }

    // run from int source
    public void run(int sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }

    // run from 2-tuple source
    public void run(int sX, int sY) {
        int source = g.h.hash(sX, sY);
        runInternal(source);
    }

    // run from 3-tuple source
    public void run(int sX, int sY, int sZ) {
        int source = g.h.hash(sX, sY, sZ);
        runInternal(source);
    }

    // minDist for int node
    public int minDist(int targetR) {
        int target = g.h.hash(targetR);
        return minDistFromSource[target];
    }

    // minDist for 2-tuple node
    public int minDist(int tX, int tY) {
        int target = g.h.hash(tX, tY);
        return minDistFromSource[target];
    }

    // minDist for 3-tuple node
    public int minDist(int tX, int tY, int tZ) {
        int target = g.h.hash(tX, tY, tZ);
        return minDistFromSource[target];
    }

    // isVisited for int node
    public boolean isVisited(int targetR) {
        int target = g.h.hash(targetR);
        return visited[target];
    }

    // isVisited for 2-tuple node
    public boolean isVisited(int tX, int tY) {
        int target = g.h.hash(tX, tY);
        return visited[target];
    }

    // isVisited for 3-tuple node
    public boolean isVisited(int tX, int tY, int tZ) {
        int target = g.h.hash(tX, tY, tZ);
        return visited[target];
    }

    private void runInternal(int source) {
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        visited[source] = true;
        minDistFromSource[source] = 0;

        while (!q.isEmpty()) {
            int curNode = q.poll();
            for (int[] neighbor : g.adj.get(curNode)) {
                int adjNode = neighbor[0];
                if (!visited[adjNode]) {
                    visited[adjNode] = true;
                    minDistFromSource[adjNode] = minDistFromSource[curNode] + 1;
                    q.add(adjNode);
                }
            }
        }
    }
}

// END COPYING HERE
// ********************BLACKBOX END******************


// ---- USAGE (main) ----
public class GraphX {
    public static void main(String[] args) {

        // Example 1: Integer nodes
        Graph g1 = new Graph(5, false);
        g1.addEdge(0, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2, 3);
        g1.addEdge(3, 4);

        BFS bfs1 = new BFS(g1);
        bfs1.run(0);
        System.out.println("Integer nodes BFS from 0:");
        for (int node = 0; node < 5; node++) {
            System.out.println("  dist to " + node + " = " + bfs1.minDist(node));
        }

        // Example 2: 2D tuple nodes (grid coordinates)
        Graph g2 = new Graph(9, false);
        g2.addEdge(0, 0, 0, 1);
        g2.addEdge(0, 1, 0, 2);
        g2.addEdge(0, 2, 1, 2);

        BFS bfs2 = new BFS(g2);
        bfs2.run(0, 0);
        System.out.println("\n2D tuple nodes BFS from (0,0):");
        int[][] nodes2D = {{0,0},{0,1},{0,2},{1,2}};
        for (int[] node : nodes2D) {
            System.out.println("  dist to (" + node[0] + "," + node[1] + ") = "
                    + bfs2.minDist(node[0], node[1]));
        }

        // Example 3: 3D tuple nodes
        Graph g3 = new Graph(4, false);
        g3.addEdge(0, 0, 0, 0, 0, 1);
        g3.addEdge(0, 0, 1, 0, 1, 1);

        BFS bfs3 = new BFS(g3);
        bfs3.run(0, 0, 0);
        System.out.println("\n3D tuple nodes BFS from (0,0,0):");
        int[][] nodes3D = {{0,0,0},{0,0,1},{0,1,1}};
        for (int[] node : nodes3D) {
            System.out.println("  dist to (" + node[0] + "," + node[1] + "," + node[2] + ") = "
                    + bfs3.minDist(node[0], node[1], node[2]));
        }
    }
}