#include <bits/stdc++.h>
using namespace std;


//COPY THE BLACKBOX, there is no need to change anything in it.
//Check the main function at bottom for USAGE

//****************BLACKBOX START*****************
//START COPYING FROM HERE

typedef int ll;

class Graph {
	
	bool is_directed;
	
	public:
		vector<vector<pair<int,ll>>>adj;
	    int n,N=2000000;
		Graph(int n_, bool is_directed_){
			n=n_; is_directed = is_directed_;
			adj.resize(N,vector<pair<int,ll>>());
		}

		int hash(int u, int v){
			return u*1873+v;
		}
		int hash(int u, int v, int k){
			return k*1873*1873+u*1873+v;
		}
		bool node_has_edges(int u) {
			return (adj[u].size()!=0);
		}
		bool node_has_edges(int u, int v) {
			int x = hash(u,v);
			return (adj[x].size()!=0);
		}
		bool node_has_edges(int u, int v, int k) {
			int x = hash(u,v,k);
			return (adj[x].size()!=0);
		}
		
		void add_edge(int u, int v, ll c=0){
			add_edge_weighted_undirected(u,v,c);
			if(!is_directed)
				add_edge_weighted_undirected(v,u,c);
		}
		void add_edge(int ui, int uj, int vi, int vj, ll c=0){
			int u=hash(ui,uj), v=hash(vi,vj);
			add_edge_weighted_undirected(u,v,c);
			if(!is_directed)
				add_edge_weighted_undirected(v,u,c);
		}
		void add_edge(int ui, int uj,int uk, int vi, int vj, int vk, ll c=0){
			int u=hash(ui,uj,uk), v=hash(vi,vj,vk);
			add_edge_weighted_undirected(u,v,c);
			if(!is_directed)
				add_edge_weighted_undirected(v,u,c);
		}

	private :
		
		void add_edge_weighted_undirected(int u, int v, ll c) {
			pair<int,ll>p = make_pair(v,c);
			adj[u].push_back(p);
		}
	
};

class BFS { 
    vector<ll>min_dist_from_source;
    vector<bool> visited;

    public:
        BFS(Graph *g_) {
            g = g_;
            min_dist_from_source.resize(g->N,-1);
            visited.resize(g->N, false);
        }
		Graph *g;


     void run(int source) {
			queue<int> q;
			q.push(source);
			
			visited[source] = true;
			min_dist_from_source[source] = 0;
			
			while(!q.empty()) {
				int cur_node = q.front();
				for (unsigned int i = 0; i < (g->adj[cur_node]).size(); ++i) {
					int adj_node =  (g->adj[cur_node])[i].first;
					if (visited[adj_node] == false) {
						visited[adj_node] = true;
						min_dist_from_source[adj_node] = min_dist_from_source[cur_node] + 1;
						q.push(adj_node);
					}
				}
				q.pop();
			}
			
			return;
        }
        
        void run(int sourcei, int sourcej){
        	int source = (g->hash)(sourcei, sourcej);
        	run(source);
        }
        void run(int sourcei, int sourcej, int sourcek){
        	int source = (g->hash)(sourcei, sourcej, sourcek);
        	run(source);
        }
        
        int min_dist(int targeti, int targetj){
        	int target = (g->hash)(targeti,targetj);
        	return min_dist_from_source[target];
        }
        int min_dist(int targeti,int targetj,int targetk){
        	int target = (g->hash)(targeti,targetj,targetk);
        	return min_dist_from_source[target];
        }
        int min_dist(int target){
        	return min_dist_from_source[target];
        }
        
        bool is_visisted(int targeti,int targetj){
        	int target = (g->hash)(targeti,targetj);
        	return visited[target];
        }
        bool is_visisted(int targeti,int targetj,int targetk){
        	int target = (g->hash)(targeti,targetj,targetk);
        	return visited[target];
        }
        bool is_visisted(int target){
        	return visited[target];
        }

};
//END COPYING HERE
//********************BLACKBOX END******************
int main() {
   // initaitise a directed graph with 4 nodes, nodes are 0-indexed
  Graph g(4, true);
  // initaitise an un-directed graph with 4 nodes, nodes are 0-indexed
  Graph g(4, false);
  
  //DIRECTED GRAPH : add edges `Node 0 -> Node 4` and `Node 1 -> Node 3`
  g.add_edge(0,4);
  g.add_edge(1,3);
  
  //UNDIRECT GRAPH : add edges between `Node 0 -- Node 4` and `Node 1 -- Node 3`
  g.add_edge(0,4);
  g.add_edge(1,3);

 //DIRECTED GRAPH 2D (useful for grid problems): add edges `Node {0,1} -> Node {2,4}` and `Node {3,1} -> Node {3,3}`
  g.add_edge(0,1,2,4);
  g.add_edge(3,1,3,3);
  
  //UNDIRECT GRAPH 2D (useful for grid problems): add edges between `Node {0,1} -- Node {2,4}` and `Node {3,1} -- Node {3,3}`
  g.add_edge(0,1,2,4);
  g.add_edge(3,1,3,3);
  
  
  
  
  //*Do BFS on the graph g*
  BFS bfs(&g);
  
  //BFS on 1D Graph
	//start bfs on `Node 2`
	bfs.run(2);
	//get minimum distance of `Node 4` from source node (minimum distance is -1 if `Node 4` is unreacable  from soure node)
	int min_d = bfs.min_dist(4);
	//check if `Node 4` is visited aka reachable from source node
	bool is_reachable = bfs.is_visisted(4);


  //BFS on 2D Graph
	//start bfs on `Node {1,4}`
	 bfs.run(1,4);
	 //get minimum distance of `Node {2,3}` from source node (minimum distance is -1 if `Node {2,3}` is unreacable  from soure node)
	int min_d = bfs.min_dist(2,3);
	//check if `Node {2,3}` is visited aka reachable from source node
	bool is_reachable = bfs.is_visisted(2,3);
	 
	

  return 0;
}

/*NOTES
1. [IMP for P2 & P4] If you call bfs.run again (even with a different source node), the previous run's minimum distance and visited is maintained
2. The Nodes are 0-indexed.
*/
