#ifndef BINARYSEACHTREE_H
#define BINARYSEARCHTREE_H

#include <memory>
#include <limits>
#include <queue>
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
        node = node->getRight();
        while (node->left) {
            node = node->getLeft();
        }
        return node;
    }
    // Find the first parent which is larger than n
    while (node->parent && node->parent->right == node) {
        node = node->getParent();
    }
    // Return nullptr means n is the largest in this BST
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

#endif