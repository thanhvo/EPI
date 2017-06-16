#ifndef SEARCH_H
#define SEARCH_H

#include <vector>

using namespace std;

template <typename T> int search_first(const vector<T> &A, const T &k) {
    int l= 0, r = A.size() - 1, res = -1;
    while (l <= r) {
        int m = l + ((r - l) >> 1);
        if (A[m] > k) {
            r = m - 1;        
        } else if (A[m] == k) {
            res = m, r = m-1;
        } else {
            l = m + 1;
        }
    }
    return res;
}

template <typename T>
int search_first_larger(const vector<T> &A, const T &k) {
    int l = 0, r = A.size() -1, res = -1;
    while (l <= r) {
        int m = l + ((r - l) >> 1);
        if (A[m] > k) {
            res = m, r = m - 1;
        } else {
            l = m + 1;
        }
    }
    return res;
}

#endif