package com.graphX.graphX;

import com.graphX.graphX.helper.Pair;
import com.graphX.graphX.helper.Triple;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    boolean isDirected;

    public List<List<Pair<Integer, Integer>>> adjacencyList;
    public int n;
    public Hash h;

    public Graph(int n) {
        this.n = n;
        this.isDirected = true;
        initialize();
    }

    public Graph(int n, boolean isDirected) {
        this.n = n;
        this.isDirected = isDirected;
        initialize();
    }

    public int hash(int u, int v) {
        return h.hash(new Pair<>(u, v));
    }

    public int hash(int u, int v, int k) {
        return h.hash(new Triple<>(u, v, k));
    }

    public void addEdge(int u, int v) {
        addEdge(u, v, 0);
    }

    public void addEdge(Pair<Integer, Integer> u, Pair<Integer, Integer> v) {
        addEdge(u, v, 0);
    }

    public void addEdge(Triple<Integer, Integer, Integer> u, Triple<Integer, Integer, Integer> v) {
        addEdge(u, v, 0);
    }

    public void addEdge(int uR, int vR, int w) {
        int u = h.hash(uR);
        int v = h.hash(vR);

        addEdgeInternal(u, v, w);
    }

    public void addEdge(Pair<Integer, Integer> uR, Pair<Integer, Integer> vR, int w) {
        int u = h.hash(uR);
        int v = h.hash(vR);

        addEdgeInternal(u, v, w);
    }

    public void addEdge(Triple<Integer, Integer, Integer> uR, Triple<Integer, Integer, Integer> vR, int w) {
        int u = h.hash(uR);
        int v = h.hash(vR);

        addEdgeInternal(u, v, w);
    }

    private void initialize() {
        adjacencyList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        h = new Hash();

    }

    private void addEdgeInternal(int u, int v) {
        addEdgeInternal(u, v, 0);
    }

    private void addEdgeInternal(int u, int v, int w) {
        addEdgeWeightedUndirected(u, v, w);
        if (!isDirected) {
            addEdgeWeightedUndirected(v, u, w);
        }
    }

    private void addEdgeWeightedUndirected(int u, int v, int w) {
        Pair<Integer, Integer> p = new Pair<>(v, w);
        adjacencyList.get(u).add(p);
    }

}
