#pragma GCC target("popcnt")
#pragma GCC optimize("tree-vectorize")
#include <bits/stdc++.h>
using namespace std;
unsigned long long a[64][3300], b[64][3300];
string s1, s2;
int main(){
    cin >> s1 >> s2;
    for (int i = 0; i < s1.size(); i++)    {
        if (s1[i] == '0') continue;
        for (int j = 0; j < 64; j++) {
            if (i < j) continue;
            a[j][(i - j) / 64] |= 1ULL << (i - j) % 64;
        }
    }

    for (int i = 0; i < s2.size(); i++){
        if (s2[i] == '0') continue;
        for (int j = 0; j < 64; j++) {
            if (i < j) continue;
            b[j][(i - j) / 64] |= 1ULL << (i - j) % 64;
        }
    }
    int q;
    cin >> q;
    while (q--)
    {
        unsigned p1, p2, len;
        cin >> p1 >> p2 >> len;
        int ans = 0;
        for (size_t i = 0; i < len / 64; i++)
            ans += __builtin_popcountll(a[p1 % 64][p1 / 64 + i] ^ b[p2 % 64][p2 / 64 + i]);
        for (size_t i = len / 64 * 64; i < len; i++)    ans += s1[p1 + i] ^ s2[p2 + i];
        printf("%d\n", ans);
    }
}
