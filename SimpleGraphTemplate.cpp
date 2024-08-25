#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>

using namespace std;

template <typename T>
class Graph {
private:
    unordered_map<T, vector<T>> adj_list;
    bool directed;

public:
    Graph(bool is_directed = false) : directed(is_directed) {}

    void add_edge(const T& u, const T& v) {
        adj_list[u].push_back(v);
        if (!directed) {
            adj_list[v].push_back(u);
        }
    }

    vector<pair<T, int>> bfs(const T& start) {
        unordered_map<T, int> distance;
        for (const pair<T, vector<T>>& pair : adj_list) {
            distance[pair.first] = -1;
        }
        distance[start] = 0;

        queue<T> q;
        q.push(start);

        while (!q.empty()) {
            T current_node = q.front();
            q.pop();

            for (const T& neighbor : adj_list[current_node]) {
                if (distance[neighbor] == -1) {
                    distance[neighbor] = distance[current_node] + 1;
                    q.push(neighbor);
                }
            }
        }

        vector<pair<T, int>> distance_vec(distance.begin(), distance.end());
        return distance_vec;
    }

    void print_graph() const {
        for (const pair<T, vector<T>>& pair : adj_list) {
            cout << pair.first << ": ";
            for (const T& neighbor : pair.second) {
                cout << neighbor << " ";
            }
            cout << "\n";
        }
    }
};

// Example usage
int main() {
    Graph<int> g(false);  // Undirected graph

    g.add_edge(1, 2);
    g.add_edge(1, 3);
    g.add_edge(2, 4);
    g.add_edge(3, 5);
    g.add_edge(4, 5);

    // Print the adjacency list (for debugging)
    cout << "Adjacency List:\n";
    g.print_graph();

    // Perform BFS starting from node 1
    vector<pair<int, int>> distances = g.bfs(1);

    cout << "\nDistances from node 1:\n";
    for (int i = 0; i < distances.size(); ++i) {
        cout << "Node " << distances[i].first << ": Distance ";
        if (distances[i].second == -1) {
            cout << "Infinity";
        } else {
            cout << distances[i].second;
        }
        cout << "\n";
    }

    return 0;
}
