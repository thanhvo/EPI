#ifndef SEARCH_H
#define SEARCH_H

#include <vector>
#include <functional>
#include <iostream>
#include <limits>
#include <algorithm>
#include <random>

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

double completion_search(vector<double> &A, const double &budget);

template <typename T>
T find_kth_in_two_sorted_arrays(const vector<T> &A, const vector<T> &B, const int &k) {
    int l = max(0, static_cast<int>(k - B.size()));
    int u = min(static_cast<int>(A.size()), k);
    while (l < u) {
        int x = l + ((u-l) >> 1);
        T A_x_1 = (x <= 0) ? numeric_limits<T>::min() : A[x - 1];
        T A_x = (x >= (int)A.size() ? numeric_limits<T>::max() : A[x]);
        T B_k_x_1 = (k - x <= 0 ? numeric_limits<T>::min() : B[k-x-1]);
        T B_k_x = ( k -x >= (int)B.size() ? numeric_limits<T>::max() : B[k-x]);
        if (A_x < B_k_x_1) {
            l = x + 1;
        } else if (A_x_1 > B_k_x){
            u = x -1;
        } else {
            return max(A_x_1, B_k_x_1);
        }
    }
    T A_l_1 = l <= 0 ? numeric_limits<T>::min() : A[l - 1];
    T B_k_l_1 = k -l - 1 < 0 ? numeric_limits<T>::min() : B[k -l -1];
    return max(A_l_1, B_k_l_1);
}

double square_root(const double &x);

template <typename T>
bool matrix_search(const vector<vector<T>> &A, const T &x) {
    int r = 0, c = A[0].size() -1;
    while (r < (int)A.size() && c >= 0) {
        if (A[r][c] == x) {
            return true;
        } else if (A[r][c] < x) {
            ++r;
        } else {
            --c;
        }
    }
    return false;
}

template <typename T>
pair<T, T> find_min_max(const vector<T> &A) {
    if (A.size() <= 1) {
        return {A.front(), A.front()};
    }
    pair<T,T> min_max_pair = minmax(A[0], A[1]);
    for (int i = 2; i + 1 < (int)A.size(); i += 2) {
        pair<T, T> local_pair = minmax(A[i], A[i+1]);
        min_max_pair = {min(min_max_pair.first, local_pair.first), max(min_max_pair.second, local_pair.second)};
    }
    if (A.size() & 1) {
        min_max_pair = {min(min_max_pair.first, A.back()), max(min_max_pair.second, A.back())};
    }
    return min_max_pair;
}

template <typename T>
int partition(vector<T> &A, const int &l, const int &r, const int& pivot) {
    T pivot_value = A[pivot];
    int larger_index = l;
    swap(A[pivot], A[r]);
    for (int i = l; i < r; ++i) {
        if (A[i] > pivot_value) {
            swap(A[i], A[larger_index++]);
        }
    }
    swap(A[r], A[larger_index]);
    return larger_index;
}

template <typename T>
T find_kth_largest(vector<T> A, const int &k) {
    int l = 0, r = A.size() -1;
    while (l <= r) {
        default_random_engine gen((random_device())());
        uniform_int_distribution<int>dis(l,r);
        int p = partition(A, l, r, dis(gen));
        if (p == k -1) {
            return A[p];
        } else if (p > k -1) {
            r = p - 1;
        } else {
            l = p + 1;
        }
    }
}

#endif