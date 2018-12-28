const int LIM = 5000000; int phi[LIM];
void calculatePhi() {
	rep(i,0,LIM) phi[i] = i&1 ? i : i/2;
	for(int i = 3; i < LIM; i += 2) if(phi[i] == i)
		for(int j = i; j < LIM; j += i) phi[j] -= phi[j] / i;
}
//Mobius Inversion : Let required be {sum_of_g=1-to-Gmax}h(g)*cnt[g]
//Then h(g)={sum_of_d:d|g}f(d) //f(n)={sum_of_d:d|n}f(d)*mobius(n/d)
FOR(i,1,n) for (int j=1;j<=n;j+=i) f[j] += h[i] * µ[j/i];
void preprocess() { // Compute mobius fucntion mu[]
    FOR(i,1,111100) mu[i]=primechk[i]=1;primechk[1]=0;
    FOR(i,2,111100) {
        if(primechk[i]==0) continue;
        mu[i]=-mu[i];
        for (int j=2;i*j<=111100;j++) primechk[i*j]=0,mu[i*j]=(j%i==0?0:mu[i*j]=-mu[i*j];
    }
}
