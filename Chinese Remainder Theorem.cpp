/* \texttt{chinese(a, m, b, n)} returns a number $x$, such that
 * $x\equiv a \pmod m$ and $x\equiv b \pmod n$. For not
 * coprime $n, m$, use \texttt{chinese\_common}. Note that all numbers must be less than
 * $2^{31}$ if you have Z = unsigned long long.
 * Status: Works
 * Time: $\log(m + n)$
 */
ll euclid(ll a, ll b, ll &x, ll &y) {
	if (b) { ll d = euclid(b, a % b, y, x);
		return y -= a/b * x, d; }
	return x = 1, y = 0, a;
}
template<class Z> Z chinese(Z a, Z m, Z b, Z n) {
	Z x, y; euclid(m, n, x, y);
	Z ret = a * (y + m) % m * n + b * (x + n) % n * m;
	if (ret >= m * n) ret -= m * n;
	return ret;
}

template<class Z> Z chinese_common(Z a, Z m, Z b, Z n) {
	Z d = gcd(m, n);
	if (((b -= a) %= n) < 0) b += n;
	if (b % d) return -1; // No solution
	return d * chinese(Z(0), m/d, b/d, n/d) + a;
}
