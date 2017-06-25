#include <algorithm>
#include <numeric>
#include <string>
#include <sstream>

#include "Search.h" 

using namespace std;

int search_index_value_equal(const vector<int> &A) {
    int l = 0, r = A.size() -1;
    while(l <= r) {
        int m = l + ((r-l) >> 1);
        int val = A[m] - m;
        if (val == 0) {
            return m;
        } else if (val > 0) {
            r = m - 1;
        } else {
            l = m + 1;
        }
    }
    return -1;
}

double completion_search(vector<double> &A, const double &budget) {
    sort(A.begin(), A.end());
    vector<double> prefix_sum;
    partial_sum(A.cbegin(), A.cend(), back_inserter(prefix_sum));
    vector<double> costs;
    for (int i = 0; i < prefix_sum.size(); ++i) {
        costs.emplace_back(prefix_sum[i] + (A.size() - i -1) * A[i]);
    }
    auto lower = lower_bound(costs.cbegin(), costs.cend(), budget);
    if (lower == costs.cend()) {
        return -1.0;
    }
    if (lower == costs.cbegin()) {
        return budget/ A.size();
    }
    int idx = lower - costs.begin() - 1;
    return A[idx] + (budget -costs[idx])/(A.size() - idx -1);
}

int compare(const double &a, const double &b) {
    double diff = (a -b)/b;
    return diff < -numeric_limits<double>::epsilon() ? -1 : diff > numeric_limits<double>::epsilon() ? 1 : 0;
}

double square_root(const double &x) {
    double l, r;
    if (compare(x, 1.0) < 0) {
        l = x, r = 1.0;
    } else {
        l = 1.0, r = x;
    }
    while (compare(l,r) == -1) {
        double m = l + 0.5 * (r -l);
        double square_m = m * m;
        if (compare(square_m, x) == 0) {
            return m;
        } else if (compare(square_m, x) == 1) {
            r = m;
        } else {
            l = m;
        }
    }
    return l;    
}

pair<int, int> find_duplicate_missing( const vector<int> &A) {
    int miss_XOR_dup = 0;
    for (int i = 0; i < A.size(); ++i) {
        miss_XOR_dup ^= i ^ A[i];
    }
    int differ_bit = miss_XOR_dup & (~(miss_XOR_dup -1)), miss_or_dup = 0;
    for (int i = 0; i < A.size(); ++i) {
        if (i & differ_bit) {
            miss_or_dup ^= i;
        }
        if (A[i] & differ_bit) {
            miss_or_dup ^= A[i];
        }
    }
    for (const int &A_i : A) {
        if (A_i == miss_or_dup) {
            return {miss_or_dup, miss_or_dup ^ miss_XOR_dup};
        }        
    }
    return {miss_or_dup ^ miss_XOR_dup, miss_or_dup};
}

int find_element_appear_once(const vector<int> &A) {
    int ones = 0, twos = 0;
    int next_ones, next_twos;
    for (const int &i: A) {
        next_ones = (~i & ones) | (i & ~ones & ~twos);
        next_twos = (~i & twos) | (i & ones);
        ones = next_ones;
        twos = next_twos;
    }
    return ones;
}

string majority_search(istringstream &sin) {
    string candidate, buf;
    int count = 0;
    while (sin >> buf) {
        if(count == 0) {
            candidate = buf;
            count = 1;
        } else if (candidate == buf) {
            ++count;
        } else {
            --count;
        }
        
    }
    return candidate;
}
