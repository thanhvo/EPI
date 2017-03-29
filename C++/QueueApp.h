#ifndef QUEUEAPP_H
#define QUEUEAPP_H

#include <memory>
#include <queue>
#include <iostream>
#include "BTNode.h"
#include "TrafficElement.h"

using namespace std;

template <typename T>
void print_binary_tree_level_order(const shared_ptr<BTNode<T>> &n) {
    if (!n) {
        return;
    }
    queue<shared_ptr<BTNode<T>>> q;
    q.emplace(n);
    while (!q.empty()) {
        cout << q.front()->data << ' ';
        if (q.front()->left) {
            q.emplace(q.front()->left);
        }
        if (q.front()->right) {
            q.emplace(q.front()->right);
        }
        q.pop();
    }
    cout << endl;
}

void traffic_volumes(const vector<TrafficElement>& A, const int& w);

#endif