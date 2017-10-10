#include <numeric>
#include <iostream>
#include <unordered_set>
#include "Intractability.h"

using namespace std;

// V contains the number of votes for each state
long ties_election(const vector<int> &V) {
    int total_votes = accumulate(V.cbegin(), V.cend(), 0);
    // No way to tie if the total number of votes is odd
    if (total_votes & 1) {
        return 0;
    }
    vector<vector<long>> table(V.size() + 1, vector<long>(total_votes + 1 , 0));
    table[0][0] = 1; // base condition: 1 way to reach 0
    for (unsigned int i = 0; i < V.size(); ++i) {
        for (int j = 0; j <= total_votes; ++j) {
            table[i+1][j] = table[i][j] + (j >= V[i] ? table[i][j - V[i]] : 0);
        }
    }
    return table[V.size()][total_votes >> 1];
}

int minimize_difference(const vector<int> &A) {
    int sum = accumulate(A.cbegin(), A.cend(), 0);
    unordered_set<int> is_Ok;
    is_Ok.emplace(0);
    for (const int & item: A) {
        for (int v = sum >> 1; v >= item; --v) {
            if (is_Ok.find(v-item) != is_Ok.cend()) {
                is_Ok.emplace(v);
            }
        }
    }
    // Find the first i from middle where is_Ok[i] == true
    for (int i = sum >> 1; i > 0; --i) {
        if (is_Ok.find(i) != is_Ok.cend()) {
            return (sum - i) - 1;
        }
    }
    return sum; // one thief takes all
}

bool check_feasible_helper(const vector<Jug> &jugs, const int &L, const int &H, 
    unordered_set<pair<int,int>, HashPair, PairEqual> &c) {
    if (L > H || c.find({L, H}) != c.end() || (L < 0 && H < 0)) {
        return false;
    }
    // Check the volume for each jug to see if it is possible
    for (const Jug &j: jugs) {
        if ((L <= j.low && j.high <= H) || // base case: j is contained in [L, H]
            check_feasible_helper(jugs, L - j.low, H - j.high, c)) {
                return true;
        }
    }
    c.emplace(L, H); // marks this as impossible
    return false;
}

bool check_feasible(const vector<Jug>& jugs, const int& L, const int& H) {
    unordered_set<pair<int, int>, HashPair, PairEqual> cache;
    return check_feasible_helper(jugs, L, H, cache);
}
