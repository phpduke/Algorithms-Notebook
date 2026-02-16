package com.graphX.graphX;

import com.graphX.graphX.helper.Pair;
import com.graphX.graphX.helper.Triple;

import java.util.*;

public class BFS {
    List<Integer> minDistanceFromSource;
    List<Boolean> visited;

    Graph graph;
    int n;

    public BFS(Graph graph) {
        this.graph = graph;
        this.n = graph.n;
    }

    public void clear() {
        minDistanceFromSource = new ArrayList<>(Collections.nCopies(n, -1));
        visited = new ArrayList<>(Collections.nCopies(n, false));
    }

    public void run(int sourceR) {
        int source = graph.h.hash(sourceR);
        bfs(source);
    }

    public void run(Pair<Integer, Integer> sourceR) {
        int source = graph.h.hash(sourceR);
        bfs(source);
    }

    public void run(Triple<Integer, Integer, Integer> sourceR) {
        int source = graph.h.hash(sourceR);
        bfs(source);
    }

    public int minDistance(int targetR) {
        int target = graph.h.hash(targetR);
        return minDistInternal(target);
    }

    public int minDistance(Pair<Integer, Integer> targetR) {
        int target = graph.h.hash(targetR);
        return minDistInternal(target);
    }

    public int minDistance(Triple<Integer, Integer, Integer> targetR) {
        int target = graph.h.hash(targetR);
        return minDistInternal(target);
    }

    public boolean isVisited(int source) {
        return isVisitedInternal(source);
    }
    public boolean isVisited(Pair<Integer, Integer> sourceR) {
        int source = graph.h.hash(sourceR);
        return isVisitedInternal(source);
    }
    public boolean isVisited(Triple<Integer, Integer, Integer> sourceR) {
        int source = graph.h.hash(sourceR);
        return isVisitedInternal(source);
    }


    private void bfs(int source) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);

        minDistanceFromSource.set(source, 0);

        while (!queue.isEmpty()) {
            int current = queue.peek();

            for (int i = 0; i < graph.adjacencyList.get(current).size(); i++) {
                int adjNode = graph.adjacencyList.get(current).get(i).first;
                if (!visited.get(adjNode)) {
                    visited.set(adjNode, true);
                    minDistanceFromSource.set(adjNode, minDistanceFromSource.get(current) + 1);
                    queue.add(adjNode);
                }
            }

            queue.poll();
        }
    }

    private int minDistInternal(int target) {
        return minDistanceFromSource.get(target);
    }

    private boolean isVisitedInternal(int target) {
        return visited.get(target);
    }

}
