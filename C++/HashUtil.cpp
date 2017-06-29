#include <unordered_map>
#include <limits>
#include <iostream>
#include <algorithm>
#include <iterator>
#include <sstream>
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