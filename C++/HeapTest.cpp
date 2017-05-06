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

void test_heap() {
    test_merge_arrays();
}