#ifndef HEAPAPP_H
#define HEAPAPP_H

#include <queue>
#include <utility>

using namespace std;

template <typename T> 
class Compare {
    public:
        const bool operator()(const pair<T,int> &lhs, const pair<T,int> &rhs) const {
            return lhs.first > rhs.first;
        }
};

template <typename T>
vector<T> merge_arrays(const vector<vector<T>> &S) {
    priority_queue <pair<T, int>, vector<pair<T, int>>, Compare<T> > min_heap;
    vector<int> S_idx(S.size(), 0);
    for (unsigned int i = 0; i< S.size(); i++) {
        if (S[i].size() > 0) {
            min_heap.emplace(S[i][0], i);
            S_idx[i] = 1;
        }
    }
    vector<T> ret;
    while (!min_heap.empty()) {
        pair<T,int> p = min_heap.top();
        ret.emplace_back(p.first);
        if (S_idx[p.second] < (int)S[p.second].size()) {
            min_heap.emplace(S[p.second][S_idx[p.second]++], p.second);
        }
        min_heap.pop();
    }
    return ret;
}

template <typename T>
vector <T> sort_k_increasing_descreasing_array(const vector<T> &A) {
    // Decompose A into a set of sorted arrays
    vector<vector<T>> S;
    bool is_increasing = true;
    unsigned int start_idx = 0;
    for (unsigned int i=1; i < A.size(); ++i) {
        if ((A[i-1] < A[i] && !is_increasing) || 
            (A[i-1] >= A[i] && is_increasing)) {
                if (is_increasing) {
                    S.emplace_back(A.cbegin() + start_idx, A.cbegin() + i);
                } else {
                    S.emplace_back(A.crbegin() + A.size() - i, A.crbegin() + A.size() - start_idx);
                }
                start_idx = i;
                is_increasing = !is_increasing;
            }
    }
    if (start_idx < A.size()) {
        if (is_increasing) {
            S.emplace_back(A.cbegin() + start_idx, A.cend());
        } else {
            S.emplace_back(A.crbegin(), A.crbegin() + A.size() - start_idx);
        }
    }
    return merge_arrays(S);
}

#endif