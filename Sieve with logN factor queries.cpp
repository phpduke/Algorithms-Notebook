int lp [ 1000010 ] ;
vector < int > pr ;
void sieve(int N) {
    for ( int i = 2 ; i <= N ; ++ i ) {
    	if ( lp [ i ] == 0 ) {
    		lp [ i ] = i ;
    		pr. push_back ( i ) ;
    	}
    	for ( int j = 0 ; j < ( int ) pr. size ( ) && pr [ j ] <= lp [ i ] && i * pr [ j ] <= N ; ++ j )
    		lp [ i * pr [ j ] ] = pr [ j ] ;
    }
}

void factorquery(int x) {
    cout<<lp[x]<<","; //
    if (x<2) return;
    int y=lp[x];
    while (x>1 && x%y==0) x/=y;
    if (x<2) return;
    factorquery(x);
}


// sieve( N ); N<=10^7. Using lp[] we can prime factorize nos <=10^7 in O(log N) instead of usual O(sqrt(N)).
