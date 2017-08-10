#ifndef BINARYSEACHTREE_H
#define BINARYSEARCHTREE_H

#include <memory>
#include <limits>
#include <queue>
#include <list>
#include <set>
#include <complex>
#include <map>
#include "Node.h"
#include "BSTNode.h"

using namespace std;

template <typename T>
bool is_BST_helper(const shared_ptr<BTNode<T>> & r, const T &lower, const T &upper) {
    if (!r) {
        return true;
    } else if (r->data < lower || r->data > upper) {
        return false;
    }
    return is_BST_helper(r->left, lower, r->data) && is_BST_helper(r->right, r->data, upper);
}

template <typename T>
bool is_BST(const shared_ptr<BTNode<T>> &r) {
    return is_BST_helper(r, numeric_limits<T>::min(), numeric_limits<T>::max());
}

template <typename T>
bool is_BST_inorder_traversal(shared_ptr<BTNode<T>> n) {
    // Store teh value of previous visited node
    int last = numeric_limits<T>::min();
    bool res = true;
    while(n) {
        if (n->left) {
            //Find the predecessor of n
            shared_ptr<BTNode<T>> pre = n-> left;
            while(pre->right && pre->right != n) {
                pre = pre->right;
            }
            // Build the successor link
            if (pre->right) { // pre->right == n
                // Revert the successor link if predecessor's successor is n
                pre->right = nullptr;
                if (last > n->data) {
                    res = false;
                }
                last = n->data;
                n = n->right;                
            } else { // if predecessor's successor is not n
                pre->right = n;
                n = n->left;
            }
        } else {
            if (last > n->data) {
                res = false;
            }
            last = n->data;
            n = n->right;
        }
    }
    return res;
}

template <typename T>
class QNode {
    public:
        shared_ptr<BTNode<T>> node;
        T lower, upper;
};

template <typename T>
bool is_BST_queue(const shared_ptr<BTNode<T>> &n) {
    queue<QNode<T>> q;
    q.emplace(QNode<T>{n, numeric_limits<T>::min(), numeric_limits<T>::max()});
    while (!q.empty()) {
        if (q.front().node) {
            if (q.front().node->data < q.front().lower || q.front().node->data > q.front().upper) {
                return false;
            }
            q.emplace(QNode<T>{q.front().node->left, q.front().lower, q.front().node->data});
            q.emplace(QNode<T>{q.front().node->right, q.front().node->data, q.front().upper});            
        }
        q.pop();
    }
    return true;
} 

template <typename T>
shared_ptr <BSTNode<T>> find_successor_BST(shared_ptr<BSTNode<T>> n) {
    shared_ptr<BSTNode<T>> node = n;
    if (node->right) {
        // Find the smallest element in n's right subtree
        //cout << node->data << endl;
        node = node->getRight();
        while (node->left) {
            node = node->getLeft();
            //cout << node->data << endl;
        }
        return node;
    }
    // Find the first parent which is larger than n
    while (node->parent && node->getParent()->getRight() == node) {
        //cout << node->data << endl;
        node = node->getParent();
    }
    // Return nullptr means n is the largest in this BST
    //cout << node->data << endl;
    return node->getParent();
}

template <typename T>
class BinarySearchTree {
    private:
        void clear(shared_ptr<BSTNode<T>> n) {
            if (n) {
                clear(n->getLeft());
                clear(n->getRight());
                n = nullptr;
            }
        }
        // Replace the link between par and child by new link
        void replaceParentChildLink(shared_ptr<BSTNode<T>> par, 
            shared_ptr<BSTNode<T>> child, shared_ptr<BSTNode<T>> new_link) {
            if (!par) {
                return;
            }
            if (par->left == child) {
                par->left = new_link;
            } else {
                par->right = new_link;
            }
        }
        shared_ptr<BSTNode<T>> root;
    public:
        BinarySearchTree(void): root(nullptr) {}
        BinarySearchTree(shared_ptr<BSTNode<T>> __root): root(__root) {}
        shared_ptr<BSTNode<T>> getRoot() {
            return root;
        }
        ~BinarySearchTree(void) {
            clear();
        }
        const bool empty(void) const {
            return !root;
        }
        void clear(void) {
            clear(root);
        }
        const bool insert(const T &key) {
            shared_ptr<BSTNode<T>> t = shared_ptr<BSTNode<T>>(new BSTNode<T>{key, nullptr, nullptr});
            shared_ptr<BSTNode<T>> par = nullptr;
            if (empty()) {
                root = t;
            } else {
                shared_ptr<BSTNode<T>> curr;
                curr = root;
                while (curr) {
                    par = curr;
                    if (t->data == curr->data) {
                        t = nullptr;
                        return false;
                    } else if (t->data < curr->data) {
                        curr = curr->getLeft();
                    } else {
                        curr = curr->getRight();
                    }
                }
                
                // Insert key according to key and par
                if (t->data < par->data) {
                    par->left = t;
                } else {
                    par->right = t;
                }
            }
            return true;
        }
        
        const bool erase(const T &key) {
            // Find the node with key
            shared_ptr<BSTNode<T>> curr = root, par = nullptr;
            while (curr && curr->data != key) {
                par = curr;
                curr = key < curr->data ? curr->getLeft() : curr->getRight();
            }
            // No node with key in this binary tree
            if (!curr) {
                return false;
            }
            if (curr->right) {
                // Find the minimum of the right subtree
                shared_ptr<BSTNode<T>> r_curr = curr->getRight(), r_par = curr;
                while (r_curr->left) {
                    r_par = r_curr;
                    r_curr = r_curr->getLeft();
                }
                // More links to erase the node
                replaceParentChildLink(par, curr, r_curr);
                replaceParentChildLink(r_par, r_curr, r_curr->getRight());
                r_curr->left = curr->left;
                r_curr->right = curr->right;
                // Update root link if needed
                if (root == curr) {
                    root = r_curr;
                }
            } else {
                // Update root link if needed
                if (root == curr) {
                    root = curr->getLeft();
                }
                replaceParentChildLink(par, curr, curr->getLeft());
            }
            curr = nullptr;
            return true;
        }
        
        const void printTree() {
            root->printTree();
        }
        
        shared_ptr<BSTNode<T>> find_first_equal_k_recursive(const shared_ptr<BSTNode<T>> &root, const T &k) {
            if (!root) {
                return nullptr; // No match
            } else if (root->data == k) {
                // Recursively search left subtree for first one == k
                shared_ptr<BSTNode<T>> n = find_first_equal_k_recursive(root->getLeft(), k);
                return n ? n : root;
            }
            // Search left or right tree according to root->data and k
            return find_first_equal_k_recursive(root->data < k ? root->getRight() : root->getLeft(), k);
        }
        
        shared_ptr<BSTNode<T>> find_first_equal_k_iterative(const T &k) {
            return find_first_equal_k_iterative(root, k);
        }
        
        shared_ptr<BSTNode<T>> find_first_equal_k_iterative(shared_ptr<BSTNode<T>> root, const T &k) {
            shared_ptr<BSTNode<T>> first = nullptr;
            while (root) {
                if (root->data < k) {
                    root = root->getRight();
                } else if (root->data > k) {
                    root = root->getLeft();
                } else { // r->data == k
                    // Search for the left most in the left subtree
                    first = root;
                    root = root->getLeft();
                }
            }
            return first;
        }
        
        shared_ptr<BSTNode<T>> find_first_equal_k_recursive(const T &k) {
            return find_first_equal_k_recursive(root, k);
        }
};

template <typename T>
bool search_min_first_BST(const shared_ptr<BSTNode<T>> &r, const T &k) {
    if (!r || r->data > k) {
        return false;
    } else if (r->data == k) {
        return true;
    }
    return search_min_first_BST(r->getLeft(), k) || search_min_first_BST(r->getRight(), k);
}

template <typename T>
shared_ptr<BSTNode<T>> find_first_larger_than_k_with_k_exist(shared_ptr<BSTNode<T>> r, const T &k) {
    bool found_k = false;
    shared_ptr<BSTNode<T>> first = nullptr;
    while (r) {
        if (r->data == k) {
            found_k = true;
            r = r->getRight();            
        } else if (r->data > k) {
            first = r;
            r = r->getLeft();
        } else { // r-> data < k
            r = r->getRight();
        }
    }
    return found_k ? first : nullptr;
}

// Build BST based on subarray A[start:end-1]
template <typename T>
shared_ptr<BSTNode<T>> build_BST_helper(const vector<T> &A, const int &start, const int &end) {
    if (start < end) {
        int mid = start + ((end - start)>>1);
        return shared_ptr<BSTNode<T>>(new BSTNode<T>{A[mid], 
            build_BST_helper(A, start, mid-1),
            build_BST_helper(A, mid +1, end)
        });
    }
    return nullptr;
}

template <typename T>
shared_ptr<BSTNode<T>> build_BST( const vector<T> &A) {
    return build_BST_helper(A, 0, A.size());
}

// Build a BST from (s+1)-th to e-th node in L
template <typename T>
shared_ptr<BSTNode<T>> build_BST_from_sorted_doubly_list_helper(shared_ptr<node_t<T>> &L, const int &s, const int &e) {
    shared_ptr<BSTNode<T>> curr = nullptr;
    if (s < e) {
        int m = s + ((e -s) >> 1);
        curr = shared_ptr<BSTNode<T>>(new BSTNode<T>());
        curr->left = build_BST_from_sorted_doubly_list_helper(L, s, m);
        curr->data = L->data;
        L = L->next;
        curr->right = build_BST_from_sorted_doubly_list_helper(L, m+1, e);        
    }
    return curr;
}

template <typename T>
shared_ptr<BSTNode<T>> build_BST_from_sorted_doubly_list(shared_ptr<node_t<T>> L, const int &n) {
    return build_BST_from_sorted_doubly_list_helper(L, 0, n);
}

// Build a BST from (s+1)-th to e-th node in L
template <typename T>
shared_ptr<BSTNode<T>> build_BST_from_sorted_doubly_list_helper_2(shared_ptr<BSTNode<T>> &L, const int &s, const int &e) {
    shared_ptr<BSTNode<T>> curr = nullptr;
    if (s < e) {
        int m = s + ((e -s) >> 1);
        curr = shared_ptr<BSTNode<T>>(new BSTNode<T>());
        curr->left = build_BST_from_sorted_doubly_list_helper_2(L, s, m);
        curr->data = L->data;
        L = L->getRight();
        curr->right = build_BST_from_sorted_doubly_list_helper_2(L, m+1, e);        
    }
    return curr;
}

template <typename T>
shared_ptr<BSTNode<T>> build_BST_from_sorted_doubly_list_2(shared_ptr<BSTNode<T>> L, const int &n) {
    return build_BST_from_sorted_doubly_list_helper_2(L, 0, n);
}

// Transform a BST into a circular sorted doubly linked list in-place, return the head of the list
template <typename T>
shared_ptr<BSTNode<T>> BST_to_doubly_list(const shared_ptr<BSTNode<T>> &n) {
    // Empty subtree
    if (!n) {
        return nullptr;
    }
    // Recursively build the list from left and right subtrees
    auto l_head(BST_to_doubly_list(n->getLeft()));
    auto r_head(BST_to_doubly_list(n->getRight()));
    // Append n to the list of left subtree
    shared_ptr<BSTNode<T>> l_tail = nullptr;
    if (l_head) {
        l_tail = l_head->getLeft();
        l_tail->right = n;
        n->left = l_tail;
        l_tail = n;
    } else {
        l_head = l_tail = n;
    }
    // Append the list from right subtree to n
    shared_ptr<BSTNode<T>> r_tail = nullptr;
    if (r_head) {
        r_tail = r_head->getLeft();
        l_tail->right = r_head;
        r_head->left = l_tail;
    } else {
        r_tail = l_tail;
    }
    r_tail->right = l_head, l_head->left = r_tail;
    return l_head;
}

// Print a binary search tree as a doubly list
template <typename T>
void print_as_doubly_list(shared_ptr<BSTNode<T>> node) {
    shared_ptr<BSTNode<T>> n = node;
    do {
        cout << n->data << " ";
        n = n->getRight();
    } while (n && n != node);
    cout << endl;
}

template <typename T>
void append_node(shared_ptr<BSTNode<T>> &head, shared_ptr<BSTNode<T>> &tail, shared_ptr<BSTNode<T>> &n) {
    if (head) {
        tail->right = n, n->left = tail;        
    } else {
        head = n;
    }
    tail = n;
}

template <typename T>
void append_node_and_advance(shared_ptr<BSTNode<T>> &head, shared_ptr<BSTNode<T>> &tail, shared_ptr<BSTNode<T>> &n) {
    append_node(head, tail, n);
    n = n->getRight(); // advance n
}

// Merge two sorted linked lists, return the head of the list
template <typename T>
shared_ptr<BSTNode<T>> merge_sorted_linked_lists(shared_ptr<BSTNode<T>> A, shared_ptr<BSTNode<T>> B) {
    shared_ptr<BSTNode<T>> sorted_list = nullptr, tail = nullptr;
    while (A && B) {
        append_node_and_advance(sorted_list, tail, A->data < B->data ? A : B);
    }
    // Append the remaining of A
    if (A) {
        append_node(sorted_list, tail, A);
    }
    // Append the remaining of B
    if (B) {
        append_node(sorted_list, tail, B);        
    }
    return sorted_list;
}

// Identify the length of BST as a linked list
template<typename T>
int count_len_BST(shared_ptr<BSTNode<T>> head) {
    int len = 0;
    while (head) {
        len++;
        head = head->getRight();
    }
    return len;
}

template <typename T>
shared_ptr<BSTNode<T>> merge_BSTs(shared_ptr<BSTNode<T>> A, shared_ptr<BSTNode<T>> B) {
    // Transform BSTs A and B into sorted doubly lists
    A = BST_to_doubly_list(A);
    B = BST_to_doubly_list(B);
    A->left->right = B->left->right = nullptr;
    A->left = B->left = nullptr;
    int len_A = count_len_BST(A);    
    int len_B = count_len_BST(B);    
    return build_BST_from_sorted_doubly_list_2(merge_sorted_linked_lists(A, B), len_A + len_B);
}

template <typename T>
void find_k_largest_in_BST_helper(const shared_ptr<BSTNode<T>> &r, const int &k, vector<T> &k_elements) {
    // Perform reverse inorder traversal
    if (r && (int)k_elements.size() < k) {
        find_k_largest_in_BST_helper(r->getRight(), k, k_elements);
        if ((int)k_elements.size() < k) {
            k_elements.emplace_back(r->data);
            find_k_largest_in_BST_helper(r->getLeft(), k, k_elements);
        }
    }
}

template <typename T>
vector<T> find_k_largest_in_BST(const shared_ptr<BSTNode<T>> &root, const int &k) {
    vector<T> k_elements;
    find_k_largest_in_BST_helper(root, k, k_elements);
    return k_elements;
}

// Build a BST based on preorder [s: e-1], return its root
template <typename T>
shared_ptr<BSTNode<T>> rebuild_BST_from_preorder_helper(const vector<T> &preorder, const int &s, const int &e) {
    if (s < e) {
        int x = s + 1;
        while (x < e && preorder[x] < preorder[s]) {
            ++x;
        }
        return shared_ptr<BSTNode<T>>(new BSTNode<T>(preorder[s], rebuild_BST_from_preorder_helper(preorder, s+1, x),
                rebuild_BST_from_preorder_helper(preorder, x, e)));
    }
    return nullptr;
}

// Given a preorder traversal of a BST, return its root 
template <typename T>
shared_ptr<BSTNode<T>> rebuild_BST_preorder(const vector<T> &preorder) {
    return rebuild_BST_from_preorder_helper(preorder, 0, preorder.size());
}

template <typename T>
shared_ptr<BSTNode<T>> rebuild_BST_from_preorder_helper2(const vector<T> &preorder, int &idx, const T &min, const T &max) {
    if (idx == (int)preorder.size()) {
        return nullptr;
    }
    T curr = preorder[idx];
    if (curr < min || curr > max) {
        return nullptr;
    }
    ++idx;
    shared_ptr<BSTNode<T>> root (new BSTNode<T> { curr, rebuild_BST_from_preorder_helper2(preorder, idx, min, curr),
    rebuild_BST_from_preorder_helper2(preorder, idx, curr, max)});
    return root;
}

template <typename T>
shared_ptr<BSTNode<T>> rebuild_BST_from_preorder2(const vector<T> &preorder) {
    int idx = 0;
    return rebuild_BST_from_preorder_helper2(preorder, idx, numeric_limits<T>::min(), numeric_limits<T>::max());
}

template <typename T>
shared_ptr<BSTNode<T>> find_LCA(shared_ptr<BSTNode<T>> x, const shared_ptr<BSTNode<T>> &s, const shared_ptr<BSTNode<T>> &b) {
    while (x->data < s->data || x->data > b->data) {
        if (x->data < s->data) {
            x = x->getRight(); // LCA must be in x's right child
        }
        if (x->data > b->data) {
            x = x->getLeft(); // LCA must be in x's left child
        }
    }
    // x->data >= s->data && x->data <= b->data
    return x; // x is LCA
}

template <typename T>
bool is_r_s_descendant_ancestor_of_m(const shared_ptr<BSTNode<T>> &r, const shared_ptr<BSTNode<T>> &s, const shared_ptr<BSTNode<T>> &m) {
    shared_ptr<BSTNode<T>> curr_r = r, curr_s = s;
    bool found_m = false;
    // Interleaving searches from r and s
    while (curr_r != s && curr_s != r && (curr_r || curr_s )) {
        if (curr_r == m || curr_s == m) {
            found_m = true;
        }
        if (curr_r != nullptr)
            curr_r = curr_r->data > s->data ? curr_r->getLeft(): curr_r->getRight();
        if (curr_s != nullptr)
            curr_s = curr_s->data > r->data ? curr_s->getLeft() : curr_s->getRight();
    }
    return (curr_r == s || curr_s == r) && found_m;    
}

template <typename T>
shared_ptr<BSTNode<T>> find_first_larger_equal_k(const shared_ptr<BSTNode<T>> &r, const T &k) {
    if (!r) {
        return nullptr;
    } else if (r->data >= k) {
        // Recursively search the left subtree for first one >= k
        auto n = find_first_larger_equal_k(r->getLeft(), k);
        return n ? n : r;
    }
    // r->data < k so search the right subtree
    return find_first_larger_equal_k(r->getRight(), k);    
}

template <typename T>
list<shared_ptr<BSTNode<T>>> range_query_on_BST(shared_ptr<BSTNode<T>> n, const T &L, const T &U) {
    list<shared_ptr<BSTNode<T>>> res;
    for (auto it = find_first_larger_equal_k(n, L); it && it->data <= U; it = find_successor_BST(it)) {
        res.emplace_back(it);
    }
    return res;
}

template <typename T>
class ArrData {
    public:
        int idx;
        T val;
        const bool operator<(const ArrData &a) const {
            if (val != a.val) {
                return val < a.val;
            } else {
                return idx < a.idx;
            }
        }
};

template <typename T>
T find_min_distance_sorted_arrays(const vector<vector<T>> &arrs) {
    // Pointers for each of arrs
    vector<int> idx(arrs.size(), 0);
    T min_dis = numeric_limits<T>::max();
    set<ArrData<T>> current_heads;
    // Each of arrs puts its minimum element into current_heads
    for (int i = 0; i < (int)arrs.size(); ++i) {
        if(idx[i] >= (int)arrs[i].size()) {
            return min_dis;
        }
        current_heads.emplace(ArrData<T> {i, arrs[i][idx[i]]});
    }
    while (true) {
        min_dis = min(min_dis, current_heads.crbegin()->val - current_heads.cbegin()->val);
        int tar = current_heads.cbegin()->idx;
        // Return if there is no remaining element in one array
        if (++idx[tar] >= (int)arrs[tar].size()){
            return min_dis;
        }
        current_heads.erase(current_heads.begin());
        current_heads.emplace(ArrData<T> {tar, arrs[tar][idx[tar]]});
    }
}

bool is_unit(const complex<int> &z) {
    return (z.real() == 1 && z.imag() == 0 || 
            z.real() == -1 && z.imag() == 0 ||
            z.real() == 0 && z.imag() == 1 ||
            z.real() == 0 && z.imag() == -1);
}

class ComplexCompare {
    public:
        const bool operator() (const complex<double> &lhs, const complex<double> &rhs) const {
            if (norm(lhs) != norm(rhs)) {
                return norm(lhs) < norm(rhs);
            } else if (lhs.real() != rhs.real()) {
                return lhs.real() < rhs.real();
            } else {
                return lhs.imag() < rhs.imag();
            }
        }
};

vector<complex<int>> generate_Guassian_primes(const int &n) {
    set<complex<double>, ComplexCompare> candidates;
    vector<complex<int>> primes;
    // Generate all possible Guassian prime candidates
    for (int i = -n; i <= n; i++) {
        for (int j = -n; j <= n; j++) {
            if (is_unit({i, j}) == false && abs(complex<double>(i, j)) != 0) {
                candidates.emplace(i,j);
            }
        }
    }
    while (candidates.empty() == false) {
        complex<double> p = *(candidates.begin());
        candidates.erase(candidates.begin());
        primes.emplace_back(p);
        int max_multiplier = n/ floor(sqrt(norm(p))) + 1;
        for (int i = max_multiplier; i >= -max_multiplier; --i) {
            for (int j = max_multiplier; j >= -max_multiplier; --j) {
                complex<double> x = {i, j};
                if (is_unit(x) == false) {
                    candidates.erase(x*p);
                }
            }
        }
    }
    return primes;
}

template <typename XaxisType, typename ColorType, typename HeightType> 
class LineSegment {
    public:
        XaxisType left, right; // specifies the interval
        ColorType color;
        HeightType height;
        const bool operator<(const LineSegment &that) const {
            return height < that.height;
        }
};

template <typename XaxisType, typename ColorType, typename HeightType>
class EndPoint {
    public:
        bool isLeft;
        const LineSegment<XaxisType, ColorType, HeightType> *l;
        const bool operator<(const EndPoint &that) const {
            return val() < that.val();
        }
        const XaxisType &val(void) const {
            return isLeft ? l->left : l->right;
        }    
};

template <typename XaxisType, typename ColorType, typename HeightType>
void calculate_view_from_above(const vector<LineSegment<XaxisType, ColorType, HeightType>> &A) {
    vector<EndPoint<XaxisType, ColorType, HeightType>> E;
    for (unsigned int i = 0; i < A.size(); ++i) {
        E.emplace_back(EndPoint<XaxisType, ColorType, HeightType>{true, &A[i]});
        E.emplace_back(EndPoint<XaxisType, ColorType, HeightType>{false, &A[i]});
    }
    sort(E.begin(), E.end());
    XaxisType prev_xaxis = E.front().val();
    // The first left end point
    shared_ptr<LineSegment<XaxisType, ColorType, HeightType>> prev = nullptr;
    map<HeightType, const LineSegment<XaxisType, ColorType, HeightType>*> T;
    for (const EndPoint<XaxisType, ColorType, HeightType> &e: E) {
        if(T.empty() == false && prev_xaxis != e.val()) {
            if (prev == nullptr) { // found first segment
                prev = shared_ptr<LineSegment<XaxisType, ColorType, HeightType>>( 
                    new LineSegment<XaxisType, ColorType, HeightType>{prev_xaxis, e.val(), T.crbegin()->second->color, T.crbegin()->second->height}
                );
            } else {
                cout << "[" << prev->left << ", " << prev->right << "]" << ", color = " << prev->color << ", height = " << prev->height << endl;
                *prev = {prev_xaxis, e.val(), T.crbegin()->second->color, T.crbegin()->second->height};
            }
        }
        prev_xaxis = e.val();
        if (e.isLeft == true) { // left end point
            T.emplace(e.l->height, e.l);
        } else {
            T.erase(e.l->height);
        }        
    }
    // Output the remaining segment if any
    if (prev) {
        cout << "[" << prev->left << ", " << prev->right << "]" << ", color = " << prev->color << ", height = " << prev->height << endl;
    }
}

#endif