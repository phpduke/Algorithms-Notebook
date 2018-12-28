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
    int q;cin >> q;
    while (q--) {
        unsigned p1, p2, len;
        cin >> p1 >> p2 >> len;
        int ans = 0;
        for (size_t i = 0; i < len / 64; i++)
            ans += __builtin_popcountll(a[p1 % 64][p1 / 64 + i] ^ b[p2 % 64][p2 / 64 + i]);
        for (size_t i = len / 64 * 64; i < len; i++)    ans += s1[p1 + i] ^ s2[p2 + i];
        printf("%d\n", ans);
    }
}
/////////////longest common substring+LCP_FUN/////
#define f first
#define s second
int suf[500005],sar[500005][20],lcp[500005],lev=1,mul=1,n;
int lcp_fun(int x,int y) { int ans=0;
        for(int k=lev-1;k>=0&&x<n&&y<n;k--) if(sar[x][k] == sar[y][k]) ans+=1<<k,x+=1<<k,y+=1<<k;
        return ans;
}
int main() {int i,j,k,l,m;
        vector<pair<pair<int,int>,int> > ve;
        string s,v;
        cin>>s>>v;
        m = v.size();
        s.pb('z'+1);
        for(i=0;i<m;i++) s.pb(v[i]);
        n = s.size();
        for(i=0;i<n;i++) sar[i][0] = s[i]-'a';
        for(;mul<=n;mul*=2,lev++) {
                ve.clear();
                for(i=0;i<n;i++) ve.pb(mp(mp(sar[i][lev-1],i+mul<n?sar[i+mul][lev-1]:-1),i));
                sort(ve.begin(),ve.end());
                for(i=0;i<n;i++)
                        sar[ve[i].s][lev] = i && ve[i].f.f == ve[i-1].f.f && ve[i].f.s == ve[i-1].f.s ? sar[ve[i-1].s][lev]:i;
        }
        for(i=0;i<n;i++) suf[sar[i][lev-1]] = i;
        int st=0,en=0,cn1=0,cn2=0,ans=0; m = n-m;
        if(suf[0]<m) cn1++; else cn2++;
        while(en+1<n-1) {
                en++;
                if(suf[en] < m) cn1++; else cn2++;
                while(st<n && suf[st] < m && cn1>1) st++,cn1--;
                while(st<n && suf[st] >= m && cn2>1) st++,cnt2--;
                //      cout<<st<<" "<<en<<" "<<lcp_fun(suf[st],suf[en])<<endl;
                if(cn1 && cn2) ans = max(ans,lcp_fun(suf[st],suf[en]));
        }cout<<ans<<endl;
}
