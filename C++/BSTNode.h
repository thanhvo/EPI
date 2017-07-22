#ifndef BSTNODE_H
#define BSTNODE_H

#include <memory>
#include "BTNode.h"

using namespace std;

template <typename T>
class BSTNode : public BTNode<T> {
public:
        BSTNode(): BTNode<T>(){}
        BSTNode(T __data): BTNode<T>(__data) {}
        BSTNode(T __data, shared_ptr<BSTNode<T>> __left, shared_ptr<BSTNode<T>> __right):
            BTNode<T>(__data, __left, __right) {}
        shared_ptr<BSTNode<T>> getRight() {
            return static_pointer_cast<BSTNode<T>>(this->right);            
        }
        shared_ptr<BSTNode<T>> getLeft() {
            return static_pointer_cast<BSTNode<T>>(this->left);    
        }
        shared_ptr<BSTNode<T>> getParent() {
            return static_pointer_cast<BSTNode<T>>(this->parent);    
        }        
};

#endif