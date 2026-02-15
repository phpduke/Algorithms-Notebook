

//COPY THE BLACKBOX, there is no need to change anything in it.
//Check the main function at bottom for USAGE

//****************BLACKBOX START*****************
//START COPYING FROM HERE

import java.util.*;

class Pair {
    int first, second;
    
    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return first == pair.first && second == pair.second;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}

class Triple {
    int first, second, third;
    
    public Triple(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triple triple = (Triple) o;
        return first == triple.first && second == triple.second && third == triple.third;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}

class EdgePair {
    int node;
    int weight;
    
    public EdgePair(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}

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

class Graph {
    private boolean isDirected;
    public List<List<EdgePair>> adj;
    public int n;
    public int N = 5000000;
    public Hash h;
    
    public Graph(int n_, boolean isDirected_) {
        n = n_;
        isDirected = isDirected_;
        adj = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }
        h = new Hash();
    }
    
    public Graph(int n_) {
        this(n_, true);
    }
    
    public int hash(int u, int v) {
        return h.hash(new Pair(u, v));
    }
    
    public int hash(int u, int v, int k) {
        return h.hash(new Triple(u, v, k));
    }
    
    public void addEdge(int uR, int vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }
    
    public void addEdge(int uR, int vR) {
        addEdge(uR, vR, 0);
    }
    
    public void addEdge(Pair uR, Pair vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }
    
    public void addEdge(Pair uR, Pair vR) {
        addEdge(uR, vR, 0);
    }
    
    public void addEdge(Triple uR, Triple vR, int c) {
        int u = h.hash(uR);
        int v = h.hash(vR);
        addEdgeInternal(u, v, c);
    }
    
    public void addEdge(Triple uR, Triple vR) {
        addEdge(uR, vR, 0);
    }
    
    private void addEdgeInternal(int u, int v, int c) {
        addEdgeWeightedUndirected(u, v, c);
        if (!isDirected) {
            addEdgeWeightedUndirected(v, u, c);
        }
    }
    
    private void addEdgeWeightedUndirected(int u, int v, int c) {
        EdgePair p = new EdgePair(v, c);
        adj.get(u).add(p);
    }
}

class BFS {
    private int[] minDistFromSource;
    private boolean[] visited;
    private Graph g;
    
    public BFS(Graph g_) {
        g = g_;
        clear();
    }
    
    public void clear() {
        minDistFromSource = new int[g.N];
        Arrays.fill(minDistFromSource, -1);
        visited = new boolean[g.N];
        Arrays.fill(visited, false);
    }
    
    public void run(int sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }
    
    public void run(Pair sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }
    
    public void run(Triple sourceR) {
        int source = g.h.hash(sourceR);
        runInternal(source);
    }
    
    public int minDist(int targetR) {
        int target = g.h.hash(targetR);
        return minDistInternal(target);
    }
    
    public int minDist(Pair targetR) {
        int target = g.h.hash(targetR);
        return minDistInternal(target);
    }
    
    public int minDist(Triple targetR) {
        int target = g.h.hash(targetR);
        return minDistInternal(target);
    }
    
    public boolean isVisited(int targetR) {
        int target = g.h.hash(targetR);
        return isVisitedInternal(target);
    }
    
    public boolean isVisited(Pair targetR) {
        int target = g.h.hash(targetR);
        return isVisitedInternal(target);
    }
    
    public boolean isVisited(Triple targetR) {
        int target = g.h.hash(targetR);
        return isVisitedInternal(target);
    }
    
    private void runInternal(int source) {
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        
        visited[source] = true;
        minDistFromSource[source] = 0;
        
        while (!q.isEmpty()) {
            int curNode = q.poll();
            for (int i = 0; i < g.adj.get(curNode).size(); i++) {
                int adjNode = g.adj.get(curNode).get(i).node;
                if (visited[adjNode] == false) {
                    visited[adjNode] = true;
                    minDistFromSource[adjNode] = minDistFromSource[curNode] + 1;
                    q.add(adjNode);
                }
            }
        }
    }
    
    private int minDistInternal(int target) {
        return minDistFromSource[target];
    }
    
    private boolean isVisitedInternal(int target) {
        return visited[target];
    }
}

//END COPYING HERE
//********************BLACKBOX END******************