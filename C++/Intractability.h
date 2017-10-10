#ifndef INTRACTIBILITY_H
#define INTRACTIBILITY_H

#include <vector>
#include <unordered_set>

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

class Jug {
    public:
        int low, high;
};

class PairEqual {
    public:
        const bool operator() (const pair<int, int> &a, const pair<int, int> &b) const {
            return a.first == b.first && a.second == b.second;
        }
};

class  HashPair {
    public:
        const size_t operator() (const pair<int, int> &p) const {
            return hash<int>()(p.first) ^ hash<int>()(p.second);
        }
};

bool check_feasible(const vector<Jug>& jugs, const int& L, const int& H);

#endif