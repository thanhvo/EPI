#include <unordered_map>
#include <unordered_set>
#include <limits>
#include <iostream>
#include <algorithm>
#include <iterator>
#include <sstream>
#include <list>
#include "HashUtil.h"

using namespace std;

int find_closest_repetition(const vector<string> &s) {
    unordered_map<string, int> string_to_location;
    int closest_distance = numeric_limits<int>::max();
    for ( int i = 0; i < (int)s.size(); i++) {
        auto it = string_to_location.find(s[i]);
        if (it != string_to_location.end()) {
            closest_distance = min(closest_distance, i - it->second);
        }
        string_to_location[s[i]] = i;
    }
    return closest_distance;
}

void find_anagrams(const vector<string> &dictionary) {
    unordered_map<string, vector<string>> hash;
    for (const string &s: dictionary){
        string sorted_str(s);
        sort(sorted_str.begin(), sorted_str.end());
        hash[sorted_str].emplace_back(s);
    }
    for (const pair<string, vector<string>> &p : hash) {
        if (p.second.size() >=2) {
            copy(p.second.begin(), p.second.end(), ostream_iterator<string>(cout, " "));
            cout << endl;
        }
    }
}

vector<string> search_frequent_items(istringstream &sin, const int &k) {
    string buf;
    unordered_map<string, int> hash;
    int n = 0;
    while (sin >> buf) {
        ++hash[buf], ++n;
        // Detecting k+1 items in hash, at least one of them must have exactly 1 in it.
        // We will discard those k + 1 items by 1 for each
        if (hash.size() == (unsigned int)(k + 1)) {
            auto it = hash.begin();
            while (it != hash.end()) {
                if (--(it->second) == 0) {
                    hash.erase(it++);
                } else {
                    ++it;
                }
            }
        }
    }
    
    // Reset hash for the following counting
    for (auto &it: hash) {
        it.second = 0;
    }
    // Reset the stream and read it again
    sin.clear();
    sin.seekg(0, ios::beg);
    // Count the occurence of each candidate word
    while (sin >> buf) {
        auto it = hash.find(buf);
        if (it != hash.end()) {
            ++it->second;
        }
    }
    vector<string> ret;
    for (const pair<string, int> &it : hash) {
        // Select the word which occurs >= n/k times
        if (it.second >= static_cast<double>(n)/k) {
            ret.emplace_back(it.first);
        }
    }
    return ret;
}

pair<int, int> find_smallest_subarray_covering_subset(const vector<string> &A, const vector<string> &Q) {
    unordered_set<string> dict(Q.cbegin(), Q.cend());
    unordered_map<string, int> count_Q;
    int l =0, r = 0;
    pair<int, int> res(-1, -1);
    while (r < static_cast<int> (A.size())) {
        // Keep moving r until it reaches end or count_Q has |Q| items
        while (r < static_cast<int> (A.size()) && count_Q.size() < Q.size()) {
            if (dict.find(A[r]) != dict.end()) {
                ++count_Q[A[r]];
            }
            ++r;
        }
        if (count_Q.size() == Q.size() && ((res.first == -1 && res.second == -1) || r -1 -l < res.second - res.first)) {
            res = {l, r -1};
        }
        // Keep moving l until it reaches end or count_Q has less than |Q| items
        while (l < static_cast<int>(A.size()) && count_Q.size() == Q.size()) {
            if (dict.find(A[l]) != dict.end()) {
                auto it = count_Q.find(A[l]);
                if (--(it->second) == 0) {
                    count_Q.erase(it);
                    if ((res.first == -1 && res.second == -1) || r -1 -l < res.second - res.first) {
                        res = {l, r-1};
                    }
                }
            }
            ++l;
        }
    }
    return res;
}

pair<int, int> improved_find_smallest_subarray_covering_subset(istringstream &sin, const vector<string> &Q) {
    list<int> loc; // Tracking the last occurrence (index) of each string in Q
    unordered_map <string, list<int>::iterator> dict;
    for (const string &s: Q) {
        dict.emplace(s, loc.end());        
    }
    pair<int, int> res(-1, -1);
    int idx = 0;
    string s;
    while (sin >> s) {
        auto it = dict.find(s);
        if (it != dict.end()) { // s is in Q
            if (it->second != loc.end()) {
                loc.erase(it->second);
            }
            loc.emplace_back(idx);
            it->second = --loc.end();
        }
        if (loc.size() == Q.size() && // find |Q| keywords
            ((res.first == -1 && res.second == -1) || idx - loc.front() < res.second - res.first) ) {
                res = {loc.front(), idx};
        }
        ++idx;
    }
    return res;
}

pair<int, int> find_smallest_sequentially_covering_subset(
    const vector<string> &A, const vector<string> &Q) {
    unordered_map <string, int> K;
    vector<int> L(Q.size(), -1), D(Q.size(), numeric_limits<int>::max());
    // Initialize K
    for (unsigned int i = 0; i < Q.size(); ++i) {
        K.emplace(Q[i], i);        
    }
    pair<int, int> res(-1, A.size()); // default value
    for (unsigned int i = 0; i < A.size(); ++i) {
        auto it = K.find(A[i]);
        if (it != K.cend()) {
            if (it->second == 0) { // first one, no predecessor
                D[0] = 1; // base condition
            } else if (D[it->second -1] != numeric_limits<int>::max()) {
                D[it->second] = i - L[it->second -1] + D[it->second -1];                
            }
            L[it->second] = i;
            if (it->second == Q.size() - 1 && D.back() < res.second - res.first + 1) {
                res = { i - D.back() + 1, i};
            }
        }
    }
    return res;        
}