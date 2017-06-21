#ifndef SEARCH_H
#define SEARCH_H

#include <vector>
#include <functional>
#include <iostream>

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

int search_index_value_equal(const vector<int> &A);

template <typename T, typename Comp>
pair<int, int> find_pair_using_comp(const vector<T> &A, const T &k, Comp comp) {
    pair<int, int> ret(0, A.size() -1);
    while(ret.first < ret.second && comp(A[ret.first], 0)) {
        ++ret.first;
    }
    while(ret.first < ret.second && comp(A[ret.second], 0)) {
        --ret.second;
    }
    while (ret.first < ret.second) {
        if ((A[ret.first] + A[ret.second]) == k) {
            return ret;
        }
        else if (comp(A[ret.first] + A[ret.second], k)) {
            do {
                ++ret.first;
            } while ((ret.first < ret.second) && comp(A[ret.first], 0));
        } else {
            do {
                --ret.second;
            } while ((ret.first < ret.second) && comp(A[ret.second], 0));
        }
    }
    return {-1, -1};
}

template <typename T>
pair<int, int> find_pos_neg_pair(const vector<T> &A, const T &k) {
    pair<int, int> ret(A.size() -1, A.size() -1);
    while (ret.first >= 0 && A[ret.first] < 0) {
        --ret.first;
    } 
    while (ret.first >= 0 && ret.second >= 0) {
        if (A[ret.first] + A[ret.second] == k) {
            return ret;
        } else if ((A[ret.first] + A[ret.second]) > k) {
            do {
                --ret.first;
            } while (ret.first >= 0 && A[ret.first] < 0);
        } else {
            do {
                --ret.second;
            } while (ret.second >= 0 && A[ret.second] >= 0);
        }        
    }
    return {-1, -1};
}

template <typename T>
pair<int, int> find_pair_sum_k(const vector<T> &A, const T &k) {
    pair<int, int> ret = find_pos_neg_pair(A, k);
    if (ret.first == -1 && ret.second == -1) {
        return k >= 0 ? find_pair_using_comp(A, k, less<T>()):
                find_pair_using_comp(A, k, greater_equal<T>());
    }
    return ret;
}

template <typename T>
int search_smallest(const vector<T> &A) {
    int l = 0, r = A.size() -1;
    while(l < r) {
        int m = l + ((r-l) >> 1);
        if (A[m] > A[r]) {
            l = m + 1;
        } else {
            r = m;
        }
    }
    return l;
}

template <typename T>
int binary_search_unknown_len(const vector<T> &A, const T &k) {
    int p = 0;
    while (true) {
        try {
            T val = A.at((1 << p) -1);
            if (val == k) {
                return (1 << p) -1;
            } else if (val > k) {
                break;
            }
        } catch (exception &e) {
            break;
        }
        ++p;
    }
    
    int l = (1 << (p -1));
    int r = (1 << p) -2;
    
    while (l <= r) {
        int m = l + ((r-l) >> 1);
        try {
            T val = A.at(m);
            if (val == k) {
                return m;
            } else if (val > k) {
                r = m - 1;                
            } else {
                l = m + 1;
            }
        } catch (exception &e) {
            r = m - 1;
        }
    }
    return -1;
}

#endif