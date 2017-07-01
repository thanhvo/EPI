#ifndef HASHUTIL_H
#define HASHUTIL_H

#include <vector>

using namespace std;

int find_closest_repetition(const vector<string> &s);

void find_anagrams(const vector<string> &dictionary);

vector<string> search_frequent_items(istringstream &sin, const int &k);

pair<int, int> find_smallest_subarray_covering_subset(const vector<string> &A, const vector<string> &Q);

#endif