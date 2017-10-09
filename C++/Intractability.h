#ifndef INTRACTIBILITY_H
#define INTRACTIBILITY_H

#include <vector>

using namespace std;

long ties_election(const vector<int> &V);

template <typename ValueType>
ValueType knapsack(const int &w, const vector<pair<int, ValueType>> &items) {
    vector<ValueType> V(w+ 1, 0);
    for (unsigned int i = 0; i < items.size(); ++i) {
        for (int j = w; j >= items[i].first; --j) {
            V[j] = max(V[j], V[j - items[i].first] + items[i].second);
        }
    }
    return V[w];
}

int minimize_difference(const vector<int> &A);

#endif