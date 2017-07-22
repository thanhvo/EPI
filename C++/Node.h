#ifndef NODE_H
#define NODE_H

#include <memory>

using namespace std;

template <typename T>
class node_t {
    public:
        T data;
        shared_ptr <node_t<T>> next;
        shared_ptr <node_t<T>> jump; 
        node_t(T __data) {
            data = __data;
            next = nullptr;
            jump = nullptr;
        }
        
        node_t(T __data, shared_ptr<node_t<T>> __next, shared_ptr<node_t<T>> __jump) {
            data = __data;
            next = __next;
            jump = __jump;
        }        
};

#endif