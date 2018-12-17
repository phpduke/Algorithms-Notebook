struct point {lint x,y;} pa[M],pb[M];
lint na,nb;
lint isleft(point A,point B,point C) {
	return (ll(B.x-A.x)*ll(C.y-A.y)-ll(C.x-A.x)*ll(B.y-A.y));
}
bool pt_in_poly(point p) {
	int a=1,b=na-1,c;
	if (isleft(pa[0],pa[a],pa[b])<=0) swap(a,b);
	if (isleft(pa[0],pa[a],p)<=0 || isleft(pa[0],pa[b],p)>=0) return false;
	while (abs(a-b)>1) {
		c=(a+b)/2;
		if (isleft(pa[0],pa[c],p)<=0) b=c; else a=c;
	}
	return (isleft(pa[a],pa[b],p)>0);
}
int main() {
	cin>>na;
	for (int i=0;i<na;i++) cin>>pa[i].x>>pa[i].y;
	cin>>nb;
	for (int i=0;i<nb;i++) cin>>pb[i].x>>pb[i].y;
	for (int i=0;i<nb;i++) if (!pt_in_poly(pb[i])) {cout<<"NO";return 0;}
	cout<<"YES";
}
