struct DSU {
    //int gans=0;
    int root(int v){return par[v] < 0 ? v : (par[v] = root(par[v]));}
    void merge(int x,int y){
            if((x = root(x)) == (y = root(y)))     return ;
    	    if(par[y] < par[x])	swap(x, y);
        	par[x] += par[y];
        	par[y] = x;
        	//int tmp=val[x]+val[y];
        	//val[x]=val[y]=tmp;
        	//gans=max(gans,tmp);
    }
} dsu;

//Initially par[i]=-1 for all. 
//If each block has initial size (see TWOFL in cc)
// -par[v]=size of block representing v. 
