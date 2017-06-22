#include <algorithm>
#include <numeric>

#include "Search.h" 

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
