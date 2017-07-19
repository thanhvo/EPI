#ifndef BTNODE_H
#define BTNODE_H

#include <memory>
#include <iostream>
#include <vector>
#include <stack>
#include <list>
#include <algorithm>
#include <unordered_set>

using namespace std;

template<typename T>
class BTNode {
	public:
        T data;
        shared_ptr<BTNode<T>> left;
        shared_ptr<BTNode<T>> right;
		shared_ptr<BTNode<T>> parent;
		int size;
      
        BTNode(T __data) {
            data = __data;			
            left = nullptr;
            right = nullptr;
			parent = nullptr;
        }
		
		BTNode(T __data, shared_ptr<BTNode<T>> __left, shared_ptr<BTNode<T>> __right) {
			data = __data;
			left = __left;
			right = __right;
            if (left) left->parent = make_shared<BTNode<T>>(*this);
            if (right) right->parent = make_shared<BTNode<T>>(*this);
            parent = nullptr;
		}
        
        BTNode(T __data, shared_ptr<BTNode<T>> __left, shared_ptr<BTNode<T>> __right, shared_ptr<BTNode<T>> __parent) {
			data = __data;
			left = __left;
			right = __right;
            parent = __parent;
		}
        
        void printTree() {
            if (right) {
                right->printTree(true, "");
            }
            printNodeValue();
            if (left) {
                left->printTree(false, "");
            }
        }
        
        virtual ~BTNode() {}
    
    private:
        void printNodeValue() {
            if (!data) {
                cout << "<null>";
            } else {
                cout << data;
            }
            cout << endl;
        }
        
        // Use string and not stringbuffer on purpose as we need to change the indent at each recursion
        void printTree(bool isRight, string indent) { 
            if (right) {
                right->printTree(true, indent + (isRight ? "        " : " |      "));
            }
            cout << indent;
            if (isRight) {
                cout << " /";
            } else {
                cout << " \\";
            }
            cout << "----- ";
            printNodeValue();
            if (left) {
                left->printTree(false, indent + (isRight ? " |      " : "        "));
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

template <typename T>
void morris_inorder_traversal(shared_ptr<BTNode<T>> n) {
	while (n) {
		if (n->left) {
			shared_ptr<BTNode<T>> pre = n->left;
			while(pre->right && pre->right != n) {
				pre = pre->right;
			}
			if (pre->right) {
				pre->right = nullptr;
				cout << n->data << " ";
				n = n->right;
			} else {
				pre->right = n;
				n = n->left;
			}
		} else {
			cout << n->data << " ";
			n = n->right;
		}
	}
	cout << endl;
}

template <typename T>
void inorder_traversal(const shared_ptr<BTNode<T>> &r) {
	if (!r) {
		return;
	}
	shared_ptr<BTNode<T>> prev = nullptr, curr = r, next;
	while (curr) {
		if (!prev || prev->left == curr || prev->right == curr) {
			if (curr->left) {
				next = curr->left;
			} else {
				cout << curr->data << " ";
				next = (curr->right ? curr->right: curr->parent);
			}
		} else if (curr->left == prev) {
			cout << curr->data << " ";
			next = (curr->right ? curr->right : curr->parent);
		} else {
			next = curr->parent;
		}
		prev = curr;
		curr = next;
	}
	cout << endl;
}

template <typename T>
shared_ptr<BTNode<T>> find_kth_node_binary_tree(shared_ptr<BTNode<T>> r, int k) {
	while (k && r) {
		int left_size = r->left ? r->left->size : 0;
		if (left_size < k - 1) {
			k -= (left_size + 1);
			r = r->right;
		} else if (left_size == k - 1) {
			return r;
		} else {
			r = r->left;
		}
	}
}

template <typename T>
shared_ptr<BTNode<T>> reconstruct_pre_in_orders_helper
	(const vector<T> &pre, const int &pre_s, const int &pre_e,
	const vector<T> &in, const int &in_s, const int &in_e
) {
	if (pre_e > pre_s && in_e > in_s) {
		auto it = find(in.cbegin() + in_s, in.cbegin() + in_e, pre[pre_s]);
		int left_tree_size = it - (in.cbegin() + in_s);
		return shared_ptr<BTNode<T>>( new BTNode<T> {
			pre[pre_s],
			reconstruct_pre_in_orders_helper<T>(pre, pre_s + 1, pre_s + 1 + left_tree_size,
			in, in_s, it - in.cbegin()),
			reconstruct_pre_in_orders_helper<T>(pre, pre_s + 1 + left_tree_size,
			pre_e, in, it - in.cbegin() + 1, in_e)
		});
	}
	return nullptr;
}

template <typename T>
shared_ptr<BTNode<T>> reconstruct_pre_in_orders( const vector<T> &pre, const vector<T> &in) {
	return reconstruct_pre_in_orders_helper(pre, 0, pre.size(), in, 0, in.size());
}

template <typename T>
shared_ptr<BTNode<T>> reconstruct_post_in_orders_helper(
	const vector<T> &post, const int &post_s, const int &post_e,
	const vector<T> &in, const int &in_s, const int &in_e
) {
	if (post_e > post_s && in_e > in_s) {
		auto it = find(in.cbegin() + in_s, in.begin() + in_e, post[post_e -1]);
		int left_tree_size = it - (in.cbegin() + in_s);
		return shared_ptr<BTNode<T>>( new BTNode<T> {
			post[post_e-1],
			reconstruct_post_in_orders_helper<T>(post, post_s, post_s + left_tree_size, 
				in, in_s, it - in.cbegin()),
			reconstruct_post_in_orders_helper<T>(post, post_s + left_tree_size, post_e - 1, 
				in, it - in.cbegin() + 1, in_e)
		});		
	}
	return nullptr;
}

template <typename T>
shared_ptr<BTNode<T>> reconstruct_post_in_orders(const vector<T> &post, const vector<T> &in) {
	return reconstruct_post_in_orders_helper(post, 0, post.size(), in, 0, in.size());
}

template <typename T>
shared_ptr<BTNode<T>> reconstruct_preorder( const vector<shared_ptr<T>> &preorder) {
	stack<shared_ptr<BTNode<T>>> s;
	for (auto it = preorder.crbegin(); it != preorder.crend(); ++it) {
		if (!(*it)) {
			s.emplace(nullptr);
		} else {
			shared_ptr<BTNode<T>> l = s.top();
			s.pop();
			shared_ptr<BTNode<T>> r = s.top();
			s.pop();
			s.emplace(new BTNode<T>{*(*it), l, r});
		}
	}
	return s.top();
}

template <typename T> 
void connect_leaves_helper(const shared_ptr<BTNode<T>> &n, list<shared_ptr<BTNode<T>>> &L) {
	if (n) {
		if (!n->left && !n->right) {
			L.push_back(n);
		} else {
			connect_leaves_helper(n->left, L);
			connect_leaves_helper(n->right, L);
		}
	}
}

template <typename T>
list<shared_ptr<BTNode<T>>> connect_leaves(const shared_ptr<BTNode<T>> &n) {
	list<shared_ptr<BTNode<T>>> L;
	connect_leaves_helper(n, L);
	return L;
}

template <typename T>
void left_boundary_b_tree(const shared_ptr<BTNode<T>> &n, const bool &is_boundary) {
	if (n) {
		if (is_boundary || (!n->left && !n->right)) {
			cout << n->data << ' ';
		}
		left_boundary_b_tree(n->left, is_boundary);
		left_boundary_b_tree(n->right, is_boundary && !n->left);
	}
}

template <typename T>
void right_boundary_b_tree(const shared_ptr<BTNode<T>> &n, const bool &is_boundary) {
	if (n) {
		right_boundary_b_tree(n->left, is_boundary && !n->right);
		right_boundary_b_tree(n->right, is_boundary && !n->right);
		if (is_boundary || (!n->left && !n->right)) {
			cout << n->data << ' ';
		}
	}
}

template <typename T>
void exterior_binary_tree(const shared_ptr<BTNode<T>> &root) {
	if (root) {
		cout << root->data << ' ';
		left_boundary_b_tree(root->left, true);
		right_boundary_b_tree(root->right, true);
	}
	cout << endl;
}

template<typename T>
shared_ptr<BTNode<T>> LCA(const shared_ptr<BTNode<T>> &n, const shared_ptr<BTNode<T>> &a, const shared_ptr<BTNode<T>> &b) {
    if (!n) {
        return nullptr;
    }
    else if ( n == a || n == b) {
        return n;
    }
    auto l_res = LCA(n->left, a, b), r_res = LCA(n->right, a, b);
    if (l_res && r_res) {
        return n;
    } else {
        return l_res ? l_res : r_res;
    }
} 

template <typename T>
int get_depth(shared_ptr<BTNode<T>> n) {
    int d = 0;
    while (n) {
        ++d;
        n = n->parent;
    }
    return d;
}

template <typename T>
shared_ptr<BTNode<T>> LCA_with_parents(
    shared_ptr<BTNode<T>> a, shared_ptr<BTNode<T>> b    
) {
    int depth_a = get_depth(a);
    int depth_b = get_depth(b);
    int depth_diff = depth_a - depth_b;
    if (depth_b > depth_a) {
        swap(a,b);
        depth_diff = depth_b - depth_a;
    }
    
    // Advance deeper node first    
    while (depth_diff--) {
        a = a->parent;
    }
    while (a != b) {
        a = a->parent;
        b = b->parent;
    }
    return a;
}

template <typename T>
shared_ptr<BTNode<T>> LCA_close_nodes(
    shared_ptr<BTNode<T>> a, shared_ptr<BTNode<T>> b
) {
    unordered_set<shared_ptr<BTNode<T>>> hash;
    while (a || b) {
        if (a) {
            if (hash.emplace(a).second == false) {
                return a;
            }
            a = a->parent;
        }
        if (b) {
            if (hash.emplace(b).second == false) {
                return b;
            }
            b = b->parent;
        }
    }
    throw invalid_argument("Two nodes are not in the same tree.");
}

#endif