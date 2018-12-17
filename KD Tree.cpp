typedef long long ntype;
const ntype sentry = numeric_limits<ntype>::max();
struct point {// point structure for 2D-tree, can be extended to 3D
    ntype x, y;
    point(ntype xx = 0, ntype yy = 0) : x(xx), y(yy) {}
};
bool operator==(const point &a, const point &b) {return a.x == b.x && a.y == b.y;}
bool on_x(const point &a, const point &b) {return a.x < b.x;} // sorts points on x-coordinate
bool on_y(const point &a, const point &b) {return a.y < b.y;} // sorts points on y-coordinate
ntype pdist2(const point &a, const point &b) {ntype dx = a.x-b.x, dy = a.y-b.y;return dx*dx + dy*dy;}// squared distance between points

struct bbox{// bounding box for a set of points
    ntype x0, x1, y0, y1;
    bbox() : x0(sentry), x1(-sentry), y0(sentry), y1(-sentry) {}
    void compute(const vector<point> &v) {// computes bounding box from a bunch of points
        for (int i = 0; i < v.size(); ++i) {
            x0 = min(x0, v[i].x);   x1 = max(x1, v[i].x);
            y0 = min(y0, v[i].y);   y1 = max(y1, v[i].y);
        }
    }
    ntype distance(const point &p) {// squared distance between a point and this bbox, 0 if inside
        if (p.x < x0) {
            if (p.y < y0)       return pdist2(point(x0, y0), p);
            else if (p.y > y1)  return pdist2(point(x0, y1), p);
            else                return pdist2(point(x0, p.y), p);
        }
        else if (p.x > x1) {
            if (p.y < y0)       return pdist2(point(x1, y0), p);
            else if (p.y > y1)  return pdist2(point(x1, y1), p);
            else                return pdist2(point(x1, p.y), p);
        }
        else {
            if (p.y < y0)       return pdist2(point(p.x, y0), p);
            else if (p.y > y1)  return pdist2(point(p.x, y1), p);
            else                return 0;
        }
    }
};
struct kdnode {// stores a single node of the kd-tree, either internal or leaf
    bool leaf;      // true if this is a leaf node (has one point)
    point pt;       // the single point of this is a leaf
    bbox bound;     // bounding box for set of points in children
    kdnode *first, *second; // two children of this kd-node
    kdnode() : leaf(false), first(0), second(0) {}
    ~kdnode() { if (first) delete first; if (second) delete second; }
    ntype intersect(const point &p) {return bound.distance(p);}// intersect a point with this node (returns squared distance)   
    void construct(vector<point> &vp){    // recursively builds a kd-tree f1rom a given cloud of points     
        bound.compute(vp);  // compute bounding box for points at this node               
        if (vp.size() == 1) {// if we're down to one point, then we're a leaf node
            leaf = true;
            pt = vp[0];
        }
        else {
            // split on x if the bbox is wider than high (not best heuristic...) else // otherwise split on y-coordinate
            if (bound.x1-bound.x0 >= bound.y1-bound.y0) sort(vp.begin(), vp.end(), on_x); else sort(vp.begin(), vp.end(), on_y);
            // divide by taking half the array for each child(not best performance if many duplicates in the middle)
            int half = vp.size()/2;
            vector<point> vl(vp.begin(), vp.begin()+half);
            vector<point> vr(vp.begin()+half, vp.end());
            first = new kdnode();   first->construct(vl);
            second = new kdnode();  second->construct(vr);            
        }
    }
};
struct kdtree{// simple kd-tree class to hold the tree and handle queries
    kdnode *root;
    kdtree(const vector<point> &vp) {// constructs a kd-tree from a points (copied here, as it sorts them)
        vector<point> v(vp.begin(), vp.end());
        root = new kdnode();
        root->construct(v);
    }
    ~kdtree() { delete root; }
    // recursive search method returns squared distance to nearest point
    ntype search(kdnode *node, const point &p){
        if (node->leaf) {
            // commented special case tells a point not to find itself
//            if (p == node->pt) return sentry;
//            else               
                return pdist2(p, node->pt);
        }
        ntype bfirst = node->first->intersect(p);
        ntype bsecond = node->second->intersect(p);
        // choose the side with the closest bounding box to search first
        // (note that the other side is also searched if needed)
        if (bfirst < bsecond) {
            ntype best = search(node->first, p);
            if (bsecond < best) best = min(best, search(node->second, p));
            return best;
        } else {
            ntype best = search(node->second, p);
            if (bfirst < best) best = min(best, search(node->first, p));
            return best;
        }
    } 
    // squared distance to the nearest 
    ntype nearest(const point &p) {return search(root, p);}
};
int main() {// some basic test code here
    vector<point> vp;// generate some random points for a kd-tree
    for (int i = 0; i < 100000; ++i) vp.push_back(point(rand()%100000, rand()%100000));
    kdtree tree(vp);
    for (int i = 0; i < 10; ++i) {// query some points
        point q(rand()%100000, rand()%100000);
        cout<<"Closest squared distance to (" << q.x << ", " << q.y << ")"<< " is " << tree.nearest(q) << endl;
    }    
}
