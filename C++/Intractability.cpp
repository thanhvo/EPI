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