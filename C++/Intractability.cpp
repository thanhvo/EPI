#include <numeric>
#include <iostream>
#include <unordered_set>
#include <cmath>
#include <iterator>
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

bool valid_to_add(const vector<vector<int>> &A, const int &i, const int &j, const int &val) {
    // Check row constraints
    for (int k = 0; k < (int)A.size(); ++k) {
        if (val == A[k][j]) {
            return false;
        }
    }
    // Check column constraints
    for (int k = 0; k < (int)A.size(); ++k) {
        if (val == A[i][k]) {
            return false;
        }
    }
    // Check region constrains
    int region_size = sqrt(A.size());
    int I = i / region_size, J = j / region_size;
    for (int a = 0; a < region_size; ++a) {
        for (int b = 0; b < region_size; ++b) {
            if (val == A[region_size*I+a][region_size*J+b]) {
                return false;
            }
        }
    }
    return true;
}

bool solve_sudoku_helper(vector<vector<int>> &A, int i, int j) {
    if (i == (int)A.size()) {
        i = 0; // start a new row
        if (++j == (int)A[i].size()) {
            return true; // entire matrix has been filled without conflict
        }
    }
    // Skip nonempty entries
    if (A[i][j] != 0) {
        return solve_sudoku_helper(A, i+1, j);
    }
    for (int val = 1; val <= (int)A.size(); ++val) {
        // Note: practically, it's substantically quicker to check if entryval conflicts
        // with any of the constrains if we add it at (i, j) before adding it, rather 
        // than adding it and then calling is_valid_sudoku. The reason is that we know 
        // we are starting with a valid configuration, and the only entry which can cause
        // a problem is entryval at (i,j).
        if (valid_to_add(A, i, j, val)) {
            A[i][j] = val;
            if (solve_sudoku_helper(A, i+1, j)) {
                return true;
            }
        }        
    }
    A[i][j] = 0; // undo assignment
    return false;    
}

// Check if a partially filled matrix has any conflicts
bool is_valid_sudoku(const vector<vector<int>> &A) {
    // Check row constrains
    for (int i = 0; i < (int)A.size(); ++i) {
        vector<bool> is_present(A.size() +1, false);
        for (int j = 0; j < (int)A.size(); ++j) {
            if (A[i][j] != 0 && is_present[A[i][j]] == true) {
                cout << "Row violation" << endl;
                return false;
            } else {
                is_present[A[i][j]] = true;
            }
        }
    }
    // Check column constrains
    for (int j = 0; j < (int)A.size(); ++j) {
        vector<bool> is_present(A.size() + 1, false);
        for (int i = 0; i < (int)A.size(); ++i) {
            if (A[i][j] != 0 && is_present[A[i][j]] == true) {
                cout << "Column violation" << endl;
                return false;
            } else {
                is_present[A[i][j]] = true;
            }
        }
    }
    // Check region constrains
    int region_size = sqrt(A.size());
    for (int I = 0; I < region_size; ++I) {
        for (int J = 0; J < region_size; ++J) {
            vector<bool> is_present(A.size()+1, false);
            for (int i = 0; i < region_size; ++i) {
                for (int j = 0; j < region_size; ++j) {
                    if (A[region_size*I+i][region_size*J+j] != 0 && is_present[A[region_size*I+i][region_size*J+j]]) {
                        cout << "Region violation" << endl;
                        return false;
                    } else {
                        is_present[A[region_size*I+i][region_size*J+j]] = true;
                    }
                }
            }
        }
    }
    return true;
}

bool solve_sudoku(vector<vector<int>> &A) {
    if (is_valid_sudoku(A) == false) {
        cout << "Initial configuration violates constrains." << endl;
        return false;
    }
    if (solve_sudoku_helper(A,0,0)) {
        for(int i = 0; i < (int)A.size(); ++i) {
            copy(A[i].begin(), A[i].end(), ostream_iterator<int>(cout, " "));
            cout << endl;
        }
        return true;
    } else {
        cout << "No solution exists." << endl;
        return false;
    }
}