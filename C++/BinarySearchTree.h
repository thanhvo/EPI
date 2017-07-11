#ifndef BINARYSEACHTREE_H
#define BINARYSEARCHTREE_H

#include <memory>
#include <limits>
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

#endif