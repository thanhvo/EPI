#include <unordered_map>
#include <limits>
#include <iostream>
#include <algorithm>
#include <iterator>
#include "HashUtil.h"

using namespace std;

int find_closest_repetition(const vector<string> &s) {
    unordered_map<string, int> string_to_location;
    int closest_distance = numeric_limits<int>::max();
    for (int i = 0; i < s.size(); i++) {
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