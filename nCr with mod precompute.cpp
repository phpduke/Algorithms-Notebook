long long fact[N], ifact[N], inv[N];
void _pre() {
    fact[1] = fact[0] = 1; ifact[0] = ifact[1] = 1; inv[0] = inv[1] = 1;
    for (int i = 1; i < N; ++i) fact[i] = (fact[i - 1] * i) % mod;
    for (int i = 2; i < N; ++i) inv[i] = (((-(mod / i) * inv[mod % i]) % mod) + mod) % mod;
    for (int i = 2; i < N; ++i) ifact[i] = (ifact[i - 1] * inv[i]) % mod;
}
long long C(int n, int k) {
    if (k > n) return 0;
    if (n < 0 || k < 0) return 0;
    return (fact[n] * ((ifact[n - k] * ifact[k]) % mod)) % mod;
}
