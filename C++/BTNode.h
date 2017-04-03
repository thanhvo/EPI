#ifndef BTNODE_H
#define BTNODE_H

#include <memory>

using namespace std;

template<typename T>
class BTNode {
	private:
		bool locked;
		int numChildrenLocks;
	public:
        T data;
        shared_ptr<BTNode<T>> left;
        shared_ptr<BTNode<T>> right;
		shared_ptr<BTNode<T>> parent;
      
        BTNode(T __data) {
            data = __data;
			locked = false;
			numChildrenLocks = 0;
            left = nullptr;
            right = nullptr;
			parent = nullptr;
        }
		
		const bool &isLock(void) const {
			return locked;
		}
		
		void lock(void) {
			if (numChildrenLocks == 0 && locked == false) {
				shared_ptr<BTNode<T>> n = parent;
				/* Check if any ancestor is locked. If so, return right away */
				while (n) {
					if (n->locked) {
						return;
					}
					n = n->parent;
				}
				locked = true;
				n = parent;
				while (n) {
					++n->numChildrenLocks;
					n = n->parent;
				}
			}
		}
		
		void unlock(void) {
			if (locked) {
				locked = false;
				shared_ptr<BTNode<T>> n = parent;
				while (n) {
					--n->numChildrenLocks;
					n = n->parent;
				}
			}
		}
    
};

template <typename T>
int get_balanced_height(const shared_ptr<BTNode<T>> &n) {
    if (!n) {
        return -1;
    }
    int l_height = get_balanced_height(n->left);
    if (l_height == -2) {
        return -2;
    }
    int r_height = get_balanced_height(n->right);
    if (r_height == -2) {
        return -2;
    }
    if (abs(l_height - r_height) > 1) {
        return -2;
    }
    return max(l_height, r_height) + 1;
}

template <typename T>
bool is_balanced_binary_tree(const shared_ptr<BTNode<T>> &n) {
    return get_balanced_height(n) != -2;
}

template <typename T>
pair<shared_ptr<BTNode<T>>, int> find_non_k_balanced_node_helper(
    const shared_ptr<BTNode<T>> &n, const int &k
) {
    if (!n) {
        return {nullptr, 0};
    }
    auto L = find_non_k_balanced_node_helper<T>(n->left, k);
    if (L.first) {
        return L;
    }
    auto R = find_non_k_balanced_node_helper<T>(n->right, k);
    if (R.first) {
        return R;
    }
    int node_num = L.second + R.second + 1;
    if (abs(L.second - R.second) > k) {
        return {n, node_num};
    }
    return {nullptr, node_num};
}

template <typename T>
shared_ptr<BTNode<T>> find_non_k_balanced_node(
    const shared_ptr<BTNode<T>> &n, const int &k
){
    return find_non_k_balanced_node_helper<T>(n, k).first;
    
}

template <typename T>
bool is_symmetric_helper( const shared_ptr<BTNode<T>> &l, const shared_ptr<BTNode<T>> &r) {
    if (!l && !r) {
        return true;
    } else if (l && r) {
        return l->data == r->data && is_symmetric_helper<T>(l->left, r->right)
        && is_symmetric_helper<T>(l->right, r->left);
    } else {
        return false;
    }    
}

template <typename T>
bool is_symmetric(const shared_ptr<BTNode<T>> &n) {
    return (!n || is_symmetric_helper<T>(n->left, n->right));
}

#endif