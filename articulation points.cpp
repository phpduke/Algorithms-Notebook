//Solution for : https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial/ [e-maxx]
//INPUT : vector<int> g[100010]; Works on disconnected undirected graph too
#include<bits/stdc++.h>
#define pb push_back
using namespace std;
int u,v,n,m,timez;
vector<bool> vis;
vector<int> g[100010],low,d,cutpoints;
vector<pair<int,int> > bridges;
void dfs_cutpoints(int x,int par=-1) {
    vis[x]=true;
    low[x]=d[x]=timez++;
    for (int i=0;i<g[x].size();i++) {
        if (g[x][i]==par) continue;
        if (vis[g[x][i]]) {
            low[x]=min(low[x],low[g[x][i]]);
        } else {
            dfs_cutpoints(g[x][i],x);
            low[x]=min(low[x],low[g[x][i]]);
            if (low[g[x][i]]>=d[x] && par!=-1) cutpoints.pb(x);
        }
    }
    if (par==-1 && g[x].size()>1) cutpoints.pb(x);
}
void find_cutpoints() {
    timez=0;
    vis.assign(n,false),low.assign(n,-1),d.assign(n,-1);
    for (int i=0;i<n;i++) if (!vis[i]) dfs_cutpoints(i);
}
void dfs_bridges(int x,int par=-1) {
    vis[x]=true;
    low[x]=d[x]=timez++;
    for (int i=0;i<g[x].size();i++) {
        if (g[x][i]==par) continue;
        if (vis[g[x][i]]) {
            low[x]=min(low[x],low[g[x][i]]);
        } else {
            dfs_bridges(g[x][i],x);
            low[x]=min(low[x],low[g[x][i]]);
            if (low[g[x][i]]>d[x]) bridges.pb(make_pair(x,g[x][i]));
        }
    }
} 
void find_bridges() {
    timez=0;
    vis.assign(n,false),low.assign(n,-1),d.assign(n,-1);
    for (int i=0;i<n;i++) if (!vis[i]) dfs_bridges(i);
}
void outputsort() {
    sort(cutpoints.begin(),cutpoints.end());
    for (int i=0;i<bridges.size();i++) 
        if (bridges[i].first>bridges[i].second) swap(bridges[i].first,bridges[i].second);
    sort(bridges.begin(),bridges.end());
}
int main() {ios::sync_with_stdio(false);cin.tie(0);
    cin>>n>>m;
    for (int i=0;i<m;i++) cin>>u>>v,g[u].pb(v),g[v].pb(u);
    find_cutpoints(); find_bridges(); outputsort();
    cout<<cutpoints.size()<<"\n";
    for (auto cp: cutpoints) cout<<cp<<" ";cout<<"\n";
    cout<<bridges.size()<<"\n";
    for (auto br: bridges) cout<<br.first<<" "<<br.second<<"\n";
}
