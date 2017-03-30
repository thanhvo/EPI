#ifndef BTNODE_H
#define BTNODE_H

#include <memory>

using namespace std;

template<typename T>
class BTNode {
    public:
        T data;
        shared_ptr<BTNode<T>> left;
        shared_ptr<BTNode<T>> right;
      
        BTNode(T __data) {
            data = __data;
            left = nullptr;
            right = nullptr;
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

#endif