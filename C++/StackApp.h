#ifndef STACKAPP_H
#define STACKAPP_H

#include <string>
#include <memory>
#include "BSTNode.h"

using namespace std;

int eval_RPN(const string &s);

template<typename T>
void print_BST_in_sorted_order(const shared_ptr<BSTNode<T>> &n) {
    stack<shared_ptr<BSTNode<T>>> s;
    shared_ptr<BSTNode<T>> curr = n;
    while(!s.empty() || curr) {
        if (curr) {
            s.push(curr);
            curr = curr->left;
        } else {
            curr = s.top();
            s.pop();
            cout << curr->data << endl;
            curr = curr->right;
        }
    }
}

#endif