#include <iostream>
#include <vector>
#include <queue>

using namespace std;

template <typename T>
class Graph {
private:
    vector<vector<T>> adj_list;
    bool directed;

public:
    Graph(int n, bool is_directed = false) : adj_list(n), directed(is_directed) {}

    void add_edge(const T& u, const T& v) {
        adj_list[u].push_back(v);
        if (!directed) {
            adj_list[v].push_back(u);
        }
    }

    vector<int> bfs(const T& start, const vector<bool>& landmines) {
        vector<int> distance(adj_list.size(), -1);
        distance[start] = 0;

        queue<T> q;
        q.push(start);

        while (!q.empty()) {
            T current_node = q.front();
            q.pop();

            for (const T& neighbor : adj_list[current_node]) {
                if (distance[neighbor] == -1 && !landmines[neighbor]) {
                    distance[neighbor] = distance[current_node] + 1;
                    q.push(neighbor);
                }
            }
        }

        return distance;
    }
};

int main() {
    int N, M;
    cin >> N >> M;

    int source, target;
    cin >> source >> target;

    vector<bool> landmines(N);
    for (int i = 0; i < N; ++i) {
        int has_landmine;
        cin >> has_landmine;
        landmines[i] = (has_landmine == 1);
    }

    Graph<int> g(N, false);  // Undirected graph

    for (int i = 0; i < M; ++i) {
        int u, v;
        cin >> u >> v;
        g.add_edge(u, v);
    }

    vector<int> distances = g.bfs(source, landmines);

    cout << distances[target] << "\n";

    return 0;
}
