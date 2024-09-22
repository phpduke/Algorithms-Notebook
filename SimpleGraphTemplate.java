import java.io.*;
import java.util.*;

public class GraphTemplate {
    static class FastReader {
        private final InputStream in;
        private final byte[] buffer = new byte[1024];
        private int ptr = 0;
        private int buflen = 0;

        public FastReader() {
            in = System.in;
        }

        private boolean hasNextByte() {
            if (ptr < buflen) {
                return true;
            } else {
                ptr = 0;
                try {
                    buflen = in.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return buflen > 0;
            }
        }

        private int readByte() {
            return hasNextByte() ? buffer[ptr++] : -1;
        }

        private boolean isWhiteSpace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public int nextInt() {
            int n = 0;
            boolean minus = false;
            int c;
            while ((c = readByte()) != -1 && isWhiteSpace(c));
            if (c == '-') {
                minus = true;
                c = readByte();
            }
            do {
                n = n * 10 + (c - '0');
            } while ((c = readByte()) >= '0' && c <= '9');
            return minus ? -n : n;
        }

        public String next() {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = readByte()) != -1 && isWhiteSpace(c));
            do {
                sb.appendCodePoint(c);
            } while ((c = readByte()) != -1 && !isWhiteSpace(c));
            return sb.toString();
        }
    }

    static class Graph {
        private final List<List<Integer>> adjList;
        private final int V;

        public Graph(int vertices) {
            this.V = vertices;
            adjList = new ArrayList<>(V);
            for (int i = 0; i < V; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        public void addEdge(int u, int v) {
            adjList.get(u).add(v);
            adjList.get(v).add(u); // Remove this line for directed graphs
        }

        // Returns an array with the shortest distances from the start node to all other nodes
        public int[] bfs(int start) {
            boolean[] visited = new boolean[V];
            int[] distance = new int[V];
            Arrays.fill(distance, -1); // -1 indicates that the node is not reachable

            Queue<Integer> queue = new LinkedList<>();
            queue.offer(start);
            visited[start] = true;
            distance[start] = 0;

            while (!queue.isEmpty()) {
                int current = queue.poll();

                for (int neighbor : adjList.get(current)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        distance[neighbor] = distance[current] + 1;
                        queue.offer(neighbor);
                    }
                }
            }

            return distance;
        }
    }

    public static void main(String[] args) {
        FastReader fr = new FastReader();
        PrintWriter out = new PrintWriter(System.out);

        int V = fr.nextInt(); // Number of vertices
        int E = fr.nextInt(); // Number of edges

        Graph graph = new Graph(V);

        for (int i = 0; i < E; i++) {
            int u = fr.nextInt();
            int v = fr.nextInt();
            graph.addEdge(u, v);
        }

        int start = fr.nextInt(); // Starting node for BFS

        int[] distances = graph.bfs(start);

        // Output the shortest distance to all nodes
        for (int i = 0; i < distances.length; i++) {
            out.println("Shortest distance from node " + start + " to node " + i + " is " + distances[i]);
        }

        out.close();
    }
}
