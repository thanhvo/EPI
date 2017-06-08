#include "HeapTest.h"
#include "HeapApp.h"
#include <vector>
#include <iostream>

using namespace std;

void test_merge_arrays() {
    vector<vector<int>> v;
    v.push_back({1,5,6,9,11});
    v.push_back({23,45,56,79,192});
    v.push_back({2,4,8,10,12});
    v.push_back({15,20,35, 40});
    vector<int> merged = merge_arrays(v);
    for (int val : merged) {
        cout << val << " ";
    }
    cout << endl;
}

void test_sort_k_increasing_decreasing_array() {
    vector<int> a = {57,131,493,294,221,339,418,452,442,190};
    vector<int> b = sort_k_increasing_descreasing_array(a);
    for (int val: b) {
        cout << val << " ";
    }
    cout << endl;
}

void test_heap() {
    test_merge_arrays();
    test_sort_k_increasing_decreasing_array();
}