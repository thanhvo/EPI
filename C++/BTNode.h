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

#endif