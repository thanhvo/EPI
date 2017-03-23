#ifndef BSTNODE_H
#define BSTNODE_H

#include <memory>

using namespace std;

template<typename T>
class BSTNode {
    public:
        T data;
        shared_ptr <BSTNode<T>> left;
        shared_ptr <BSTNode<T>> right;     
        
        BSTNode(T __data) {
            data = __data;
            left = nullptr;
            right = nullptr;
        }
};

#endif