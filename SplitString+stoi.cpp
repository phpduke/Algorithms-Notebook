#include <bits/stdc++.h>
using namespace std;
template<typename Out>
void split(const string &s, char delim, Out result) {
    stringstream ss(s);
    string item;
    while (std::getline(ss, item, delim)) {
        *(result++) = item;
    }
}

vector<string> split(const string &s, char delim) {
    vector<string> elems;
    split(s, delim, back_inserter(elems));
    return elems;
}
vector<string> cur;
int main() {
	cur=split("Hey|0|YO|5252",'|');
	for (auto x:cur) {
		if (x[0]=='5') {
			cout<<stoi(x)+10<<endl;
		} else cout<<x<<endl;
	}
	return 0;
}
