#include <bits/stdc++.h>
using namespace std;
#define ll long long
typedef struct node {
	struct node *l,*r;
	ll size,lazy,sum,val,prior;
}node;
 
typedef node* pnode;
pnode head;
 
ll sz(pnode t) {
	return t?t->size:0;
}
 
void upd_sz(pnode t) {
	if(t) t->size = sz(t->l)+1+sz(t->r);
}
 
void lazy(pnode t) {
	if(!t || !t->lazy) return;
	t->val += t->lazy;
	t->sum += t->lazy*sz(t);
	if(t->l) t->l->lazy += t->lazy;
	if(t->r) t->r->lazy += t->lazy;
	t->lazy = 0;
}
 
void reset(pnode t) {
	if(!t) return;
	t->sum = t->val;
}
 
void combine(pnode &t,pnode l,pnode r) {
	if(!l || !r) return void (t=l?l:r);
	t->sum = l->sum+r->sum;
}
 
void operation(pnode t) {
	if(!t) return;
	reset(t);
	lazy(t->l);lazy(t->r);
	combine(t,t->l,t);
	combine(t,t,t->r);
}
 
void split(pnode t,pnode &l,pnode &r,ll pos,ll add=0) {
	if(!t) return void(l = r = NULL);
	ll cur_pos = add + sz(t->l);
	lazy(t);
	if(cur_pos <= pos) split(t->r,t->r,r,pos,cur_pos+1),l=t;
	else split(t->l,l,t->l,pos,add),r=t;
	upd_sz(t);
	operation(t);
}
void merge(pnode &t,pnode l,pnode r) {
	lazy(l);lazy(r);
	if(!l || !r) t=l?l:r;
	else if(l->prior > r->prior) merge(l->r,l->r,r),t=l;
	else merge(r->l,l,r->l),t=r;
	upd_sz(t);
	operation(t);
}
 
void init(pnode t,ll val) {
	t->l = t->r = NULL;
	t->val = t->sum = val;
	t->size = 1,t->lazy=0;
	t->prior = rand();
}
 
void range_update(pnode t,ll l,ll r,ll val) {
	pnode L,mid,R;
	split(t,L,mid,l-1);
	split(mid,t,R,r-l);
	t->lazy += val;
	merge(mid,L,t);
	merge(t,mid,R);
}
 
ll range_query(pnode t,ll l,ll r) {
	pnode L,mid,R;
	split(t,L,mid,l-1);
	split(mid,t,R,r-l);
	ll ans=t->sum;
	merge(mid,L,t);
	merge(t,mid,R);
	return ans;
}
 
int main() {
	ll t;
	cin>>t;
	while(t--) {
		ll i,j,k,l,n,m;
		cin>>n>>m;
		for(i=0;i<n;i++) {
			pnode te = new(node);
			init(te,0);
			if(i)
				merge(head,head,te);
			else
				head = te;
		}
		while(m--) {
			cin>>j>>k>>l;
			if(j)
				cout<<range_query(head,k-1,l-1)<<endl;
			else {
				cin>>j;
				range_update(head,k-1,l-1,j);
			}
		}
	}
	return 0;
}
