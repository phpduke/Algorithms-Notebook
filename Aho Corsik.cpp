#define inc(i,a,b)      for(int i=a;i<=b;++i)
#define pb              push_back
 
const int maxWords = 506, maxLyrics = 106, maxWordLength = 5006, maxLyricLength = 50006;
 
struct node {
    int occ, failIndegree;
    map<char,node*> edge;
    node* fail;
   
    node():occ(0),failIndegree(0),fail(NULL) {}
}root;
 
int numWords, numLyrics;
node* id[maxWords];
 
void preComputation();
void searchTarget();
void getOccurances();
void buildTrie();
void buildAutomaton();
 
int main() {
    scanf("%d",&numWords);
    preComputation();
   
    scanf("%d",&numLyrics);
    inc(i,1,numLyrics) {
        searchTarget();
    }
    getOccurances();
   
    inc(i,0,numWords-1) {
        printf("%d\n",id[i]->occ);
    }
}
 
void buildTrie() {
    char word[maxWordLength], c;
   
    inc(i,0,numWords-1) {
        cin >> word;
        id[i] = &root;
       
        for(int j=0;word[j];++j) {
            c = word[j];
            assert(isalnum(c) || c=='-');
            if(!id[i]->edge.count(c)) {
                id[i]->edge[c] = new node;
            }
            id[i] = id[i]->edge[c];
        }
    }
}
void buildAutomaton() {
    queue<node*> q;
   
    vector<char> alpha;
    inc(i,'a','z') alpha.pb(i);
    inc(i,'A','Z') alpha.pb(i);
    inc(i,'0','9') alpha.pb(i);
    alpha.pb('-');
   
    // Gives root more failIndegree so that it isn't added to queue in getOccurances()
    root.failIndegree = 1;
   
    inc(i,0,alpha.size()-1) {
        if(!root.edge.count(alpha[i])) {
            root.edge[alpha[i]] = &root;
        }
        else {
            root.edge[alpha[i]]->fail = &root;
            (root.failIndegree)++;
            q.push(root.edge[alpha[i]]);
        }
    }
   
    while(!q.empty()) {
        node* p = q.front();
        q.pop();
       
        map<char,node*>::iterator it = p->edge.begin();
        for(;it!=p->edge.end();++it) {
            node *u = it->second;
            char c = it->first;
           
            node *v = p->fail;
            while(!v->edge.count(c)) {
               
                v = v->fail;
            }
            u->fail = v->edge[c];
            (v->edge[c]->failIndegree)++;
           
            q.push(u);
        }
    }
}
void preComputation() {
    buildTrie();
    buildAutomaton();
}
void searchTarget() {
    char lyric[maxLyricLength];
    scanf(" %s",lyric);
   
    node* curr = &root;
    char c;
    for(int i=0;lyric[i];++i) {
        c = lyric[i];
        while(!curr->edge.count(c)) {
            assert(curr->fail != NULL);
            curr = curr->fail;
        }
        curr = curr->edge[c];
        (curr->occ)++;
    }
}
void getOccurances() {
    queue<node*> q;
   
    queue<node*> bfs;
    bfs.push(&root);
    while(!bfs.empty()) {
        node* u = bfs.front();
        bfs.pop();
       
        if(!u->failIndegree) q.push(u);
       
        map<char,node*>::iterator it = u->edge.begin();
        for(;it!=u->edge.end();++it) {
            node* v = it->second;
            if(v!=(&root)) bfs.push(v);
        }
    }
   
    while(!q.empty()) {
        node* u = q.front();
        q.pop();
       
        assert(u->fail != NULL);
        node* v = u->fail;
       
        (v->failIndegree)--;
        if(!v->failIndegree) {
            assert(v!=(&root));
            q.push(v);
        }
       
        v->occ += u->occ;
    }
}
