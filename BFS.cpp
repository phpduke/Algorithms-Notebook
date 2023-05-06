#include <iostream>
#include <queue>
#include <vector>
#include <iomanip>

using namespace std;

//****************BLACKBOX START*****************
//START COPYING FROM HERE
class Graph {
  public:
   
    Graph(int num_nodes) 
      : adj_list(num_nodes), dist(num_nodes, -1) {}

    void add_edge(int start, int end);
    vector<vector<int>> adj_list;
    vector<int> dist;
};

void Graph::add_edge(int start, int end) {
  adj_list[start].push_back(end);
}

vector<int> BFS(Graph& g, int source) {
  vector<bool> visited(g.adj_list.size(), false);
  queue<int> q;
  q.push(source);
 
  visited[source] = true;
  g.dist[source] = 0;

  while(!q.empty()) {
    int cur_node = q.front();
    vector<int> cur_node_adj = g.adj_list[cur_node];
   
    for (unsigned int i = 0; i < cur_node_adj.size(); ++i) {
      int adj_node = cur_node_adj[i];
      if (visited[adj_node] == false) {
        visited[adj_node] = true;
        g.dist[adj_node] = g.dist[cur_node] + 1;
        q.push(adj_node);
      }
    }
    q.pop();
  }
  
  return g.dist;
}
//END COPYING HERE
//********************BLACKBOX END******************


int main() {
   // initaitise a graph with 4 nodes, nodes are 0-indexed
  Graph g(4);
  
  //DIRECTED GRAPH : add edges `Node 0 -> Node 4` and `Node 1 -> Node 3`
  g.add_edge(0,4);
  g.add_edge(1,3);
  
  //UNDIRECT GRAPH : add edges between `Node 0 -- Node 4` and `Node 1 -- Node 3`
  g.add_edge(0,4);
  g.add_edge(4,0);
  g.add_edge(1,3);
  g.add_edge(3,1);
 
  //do BFS on the graph g start at Node 2
  //an array of size "number of nodes" is returned with the minimum distance from Node 2 to each node.
  vector<int>min_dist = BFS(g, 2);
 

  return 0;
}
