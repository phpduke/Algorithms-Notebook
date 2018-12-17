#include <bits/stdc++.h>
using namespace std;
#define pb push_back
#define ll long long int
 
typedef struct node {
	int l,r,x,y;
	ll val;
	struct node *ul,*ur,*lll,*lr;
} node;
 
int ar[1030][1030];
ll kam,zyada;
 
node* build(int l,int r,int x,int y) {
	//cout<<l<<" "<<r<<" "<<x<<" "<<y<<endl;
	if(l>r || x>y) return NULL;
	node* ne = (node*)malloc(sizeof(node));
	ne->l = l;
	ne->r = r;
	ne->y = y;
	ne->x = x;
	if(l==r&&x==y) {
		ne->val = ar[l][x];
		return ne;
	}
	ne->ul = build(l,(l+r)/2,x,(x+y)/2);
	if(ne->ul)
		ne->val += ne->ul->val;//.pb(ne->ul->ve[i]);
	ne->ur = build(l,(l+r)/2,(x+y)/2+1,y);
	if(ne->ur)
		ne->val += ne->ur->val;//.pb(ne->ur->ve[i]);
	ne->lll = build((l+r)/2+1,r,x,(x+y)/2);
	if(ne->lll)
		ne->val += ne->lll->val;//.pb(ne->ur->ve[i]);
	ne->lr = build((l+r)/2+1,r,(x+y)/2+1,y);
	if(ne->lr)
		ne->val += ne->lr->val;//.pb(ne->ur->ve[i]);
	return ne;
}
 
void query(node *t,int l1,int r1,int x1,int y1,int l2,int r2,int x2,int y2) {
//	cout<<l1<<" "<<r1<<" "<<x1<<" "<<y1<<endl;
	if(l1>r1||x1>y1) return;
	if(l1>=l2 && r1<=r2 && x1>=x2 && y1<=y2) {
		kam += t->val;
		return;
	}
	if(l2<=(l1+r1)/2 && x2<=(x1+y1)/2)
		query(t->ul,l1,(l1+r1)/2,x1,(x1+y1)/2,l2,r2,x2,y2);
	if(l2<=(l1+r1)/2 && y2>(x1+y1)/2)
		query(t->ur,l1,(l1+r1)/2,(x1+y1)/2+1,y1,l2,r2,x2,y2);
	if(r2>(l1+r1)/2 && x2<=(x1+y1)/2)
		query(t->lll,(l1+r1)/2+1,r1,x1,(x1+y1)/2,l2,r2,x2,y2);
	if(r2>(l1+r1)/2 && y2>(x1+y1)/2)
		query(t->lr,(l1+r1)/2+1,r1,(x1+y1)/2+1,y1,l2,r2,x2,y2);
}
 
void update(node *t,int l1,int r1,int x1,int y1,int l2,int x2,int val1,int val2) {
	//cout<<l1<<" "<<r1<<" "<<x1<<" "<<y1<<endl;
	if(l1>r1||x1>y1) return;
	t->val += val2;
	t->val -= val1;
	if(l1==r1&&x1==y1) return;
	if(l2<=(l1+r1)/2 && x2<=(x1+y1)/2)
		update(t->ul,l1,(l1+r1)/2,x1,(x1+y1)/2,l2,x2,val1,val2);
	if(l2<=(l1+r1)/2 && x2>(x1+y1)/2)
		update(t->ur,l1,(l1+r1)/2,(x1+y1)/2+1,y1,l2,x2,val1,val2);
	if(l2>(l1+r1)/2 && x2<=(x1+y1)/2)
		update(t->lll,(l1+r1)/2+1,r1,x1,(x1+y1)/2,l2,x2,val1,val2);
	if(l2>(l1+r1)/2 && x2>(x1+y1)/2)
		update(t->lr,(l1+r1)/2+1,r1,(x1+y1)/2+1,y1,l2,x2,val1,val2);
}
 
int main() {
	ios::sync_with_stdio(false);cin.tie(0);
	int t;
	cin>>t;
	while(t--) {
		node* head = (node*)malloc(sizeof(node));
		int i,j,k,l,n,x,q,y,r;
		string s;
		cin>>n;
		for(i=0;i<n;i++)
			for(j=0;j<n;j++)
				ar[i][j]=0;
		head = build(0,n-1,0,n-1);
		cin>>s;
		while(s[0]=='S') {
			cin>>l>>r>>x;
			kam=0;
			if(s[1]=='U') {
				cin>>y;
				query(head,0,n-1,0,n-1,l,x,r,y);
				cout<<kam<<endl;
			}
			else {
				update(head,0,n-1,0,n-1,l,r,ar[l][r],x);
				ar[l][r] = x;
			}
			cin>>s;
		}
	}
	return 0;
}
