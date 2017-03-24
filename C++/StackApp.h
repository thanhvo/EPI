#ifndef STACKAPP_H
#define STACKAPP_H

#include <string>
#include <memory>
#include <vector>
#include <utility>
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

void move_tower_hanoi(const int &n);

template <typename T>
vector <pair<int, T>> examine_buildings_with_sunset(istringstream &sin) {
    int idx = 0;
    T height;
    vector<pair<int, T>> buildings_with_sunset;
    while (sin >> height) {
        while (!buildings_with_sunset.empty() && height >= buildings_with_sunset.back().second) {
            buildings_with_sunset.pop_back();
        }
        buildings_with_sunset.emplace_back(idx++, height);
    }
    return buildings_with_sunset;
}

#endif