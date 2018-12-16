#include <ext/pb_ds/assoc_container.hpp> // Common file
#include <ext/pb_ds/tree_policy.hpp> // Including tree_order_statistics_node_update
using namespace __gnu_pbds;
struct cmp {
    bool operator() (const int& a, const int& b) const {
        return a<b;
	}
};

tree<int, null_type, cmp, rb_tree_tag, tree_order_statistics_node_update> T;

T.find_by_order(k); // iterator to the k-th largest element (counting from zero)
T.order_of_key(x); // no of elements strictly smaller than x
