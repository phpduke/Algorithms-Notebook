struct flow_graph{//Dinic Fast Flow - spoj
    int MAX_V,E,s,t,head,tail;
    int *cap,*to,*next,*last,*dist,*q,*now;
    flow_graph(){}
    flow_graph(int V, int MAX_E){
        MAX_V = V; E = 0;
        cap = new int[2*MAX_E], to = new int[2*MAX_E], next = new int[2*MAX_E];
        last = new int[MAX_V], q = new int[MAX_V], dist = new int[MAX_V], now = new int[MAX_V];
        fill(last,last+MAX_V,-1);
    }
    void clear(){
        fill(last,last+MAX_V,-1);
        E = 0;
    }
    void add_edge(int u, int v, int uv, int vu = 0){
        to[E] = v, cap[E] = uv, next[E] = last[u]; last[u] = E++;
        to[E] = u, cap[E] = vu, next[E] = last[v]; last[v] = E++;
    }
	bool bfs(){
		fill(dist,dist+MAX_V,-1);
		head = tail = 0;
		q[tail] = t; ++tail;
		dist[t] = 0;
		while(head<tail){
			int v = q[head]; ++head;
			for(int e = last[v];e!=-1;e = next[e]){
				if(cap[e^1]>0 && dist[to[e]]==-1){
					q[tail] = to[e]; ++tail;
					dist[to[e]] = dist[v]+1;
				}
			}
		}
		return dist[s]!=-1;
	}
	int dfs(int v, int f){
		if(v==t) return f;
		for(int &e = now[v];e!=-1;e = next[e]){
			if(cap[e]>0 && dist[to[e]]==dist[v]-1){
				int ret = dfs(to[e],min(f,cap[e]));
				if(ret>0){
					cap[e] -= ret;
					cap[e^1] += ret;
					return ret;
				}
			}
		}
		return 0;
	}
	long long max_flow(int source, int sink){
		s = source; t = sink;
		long long f = 0;
		int x;
		while(bfs()){
			for(int i = 0;i<MAX_V;++i) now[i] = last[i];
			while(true){
				x = dfs(s,INT_MAX);
				if(x==0) break;
				f += x;
			}
		}
		return f;
	}
}G;
int main(){
	int V,E,u,v,c;
	scanf("%d %d",&V,&E);
	G = flow_graph(V,E);
	for(int i = 0;i<E;++i){
		scanf("%d %d %d",&u,&v,&c);
		G.add_edge(u-1,v-1,c,c);
	}
	printf("%lld\n",G.max_flow(0,V-1));
	return 0;
}
// Simple Edmond Karp
const int oo = 1000000009;
class SimpleMaxFlow {
    public:
    int n, s, t;
    vector<vector<int> > initial_cap, cap;
    vector<int> vis, where;
    SimpleMaxFlow(int _n) {
        n = _n + 1;
        initial_cap = vector<vector<int> >(n, vector<int>(n));
        cap = vector<vector<int> >(n, vector<int>(n));
    }
    void addEdge(int a, int b, int c) {initial_cap[a][b] = cap[a][b] = c;}
    int dfs(int x, int f) {
        if(vis[x]) return 0;
        if(x == t) return f;
        vis[x] = 1;
        int ret = 0;
        for(int i = 0; i < n; ++i) if(!vis[i] && cap[x][i] > 0) {
            where[i] = x;
            ret = max(ret, dfs(i, min(f, cap[x][i])));
        }
        return ret;
    }
    int dfs_aug() {
        vis = vector<int>(n, 0);
        where = vector<int>(n, -1);
        return dfs(s, oo);
    }
    int bfs_aug() {
        vis = vector<int>(n, 0);
        where = vector<int>(n, -1);
        queue<pair<int, int> > q;
        q.push(make_pair(s, oo));
        vis[s] = 1;
        while(!q.empty()) {
            pair<int, int> cur = q.front(); q.pop();
            if(cur.fi == t) return cur.se;
            vis[cur.fi] = 1;
            for(int i = 0; i < n; ++i) if(cap[cur.fi][i] > 0 && !vis[i]) {
                where[i] = cur.fi;
                vis[i] = 1;
                q.push(make_pair(i, min(cur.se, cap[cur.fi][i])));
            }
        } return 0;
    }
    int pfs_aug() {
        vis = vector<int>(n, 0);
        where = vector<int>(n, -1);
        priority_queue<pair<int, int> > pq;
        pq.push(make_pair(s, oo));
        vis[s] = 1;
        while(!pq.empty()) {
            pair<int, int> cur = pq.top(); pq.pop();
            if(cur.fi == t) return cur.se;
            vis[cur.fi] = 1;
            for(int i = 0; i < n; ++i) if(cap[cur.fi][i] > 0 && !vis[i]) {
                where[i] = cur.fi;
                vis[i] = 1;
                pq.push(make_pair(i, min(cur.se, cap[cur.fi][i])));
            }
        }   return 0;
    }
    int flow(int _s, int _t) {
        s = _s; t = _t;
        int f = 0;
        while(int inc = pfs_aug()) {
            f += inc;
            int cur = t;
            while(where[cur] > -1) {
                cap[where[cur]][cur] -= inc;
                cap[cur][where[cur]] += inc;
                cur = where[cur];
            }
        }
        return f;
    }
    /*void disp() {
        cerr << endl<< "Flow from " << s << " to " << t << endl;
        for(int i = 0; i < n; ++i) for(int j = 0; j < n; ++j) if(initial_cap[i][j] > 0) {cerr << i << " " << j << " " << cap[i][j] << "/" << initial_cap[i][j] << endl;}
        cerr << endl;
    }*/};
