#include <cassert>
#include <vector>
#include <iostream>

#include "Search.h"
#include "SearchTest.h"

using namespace std;

void test_search_first() {
    vector<int> a = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
    assert(search_first(a, 108) == 3);
    assert(search_first(a, 285) == 6);
    assert(search_first(a, -15) == -1);
    assert(search_first(a, 402) == -1);
    assert(search_first(a, -14) == 0);
    assert(search_first(a, 401) == 9);
}

void test_search_first_larger() {
    vector<int> a = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
    assert(search_first_larger(a, 108) == 5);
    assert(search_first_larger(a, 285) == 9);
    assert(search_first_larger(a, -15) == 0);
    assert(search_first_larger(a, 402) == -1);
    assert(search_first_larger(a, -14) == 1);
    assert(search_first_larger(a, 401) == -1);
}

void test_search_index_value_equal() {
    vector<int> a = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
    assert(search_index_value_equal(a) == 2);
}

void test_find_pair_sum_k() {
    vector<int> v = {-49, 75, 103, -147, 164, -197, -238, 314, 348, -422};
    pair<int, int> p = find_pair_sum_k(v, 167);
    cout << p.first << "\t" << p.second << endl;
    p = find_pair_sum_k(v, -196);    
    cout << p.first << "\t" << p.second << endl;
    p = find_pair_sum_k(v, 423);    
    cout << p.first << "\t" << p.second << endl;
    p = find_pair_sum_k(v, 0);    
    cout << p.first << "\t" << p.second << endl;
}

void test_search_smallest() {
    vector<int> v = {378, 478, 550, 631, 103, 203, 220, 234, 279, 368};
    assert(search_smallest(v) == 4);
}

void test_binary_search_unknown_len() {
    vector<int> a = {1, 3, 4, 5, 6, 8, 9, 11, 12, 13, 14, 16, 19, 20, 25, 34, 40, 46, 49, 53, 56, 67, 74, 89, 100};
    assert(binary_search_unknown_len(a, 6) == 4);
    assert(binary_search_unknown_len(a, 12) == 8);
    assert(binary_search_unknown_len(a, 99) == -1);
    assert(binary_search_unknown_len(a, 101) == -1);    
}

void test_completion_search() {
    vector<double> v = {90, 30, 100, 40, 20};
    double target = 210;
    assert(completion_search(v, 210) == 60);
}

void test_find_kth_in_two_sorted_arrays() {
    vector<int> a = {1,2,3,4,5,6,7};
    vector<int> b = {2,3,4,6,8,10};
    assert(find_kth_in_two_sorted_arrays(a, b, 3) == 2);
    assert(find_kth_in_two_sorted_arrays(a, b, 4) == 3);
    assert(find_kth_in_two_sorted_arrays(a, b, 5) == 3);
    assert(find_kth_in_two_sorted_arrays(a, b, 6) == 4);
}

void test_square_root() {
    cout << square_root(2.0) << endl;
}

void test_matrix_search() {
    vector<vector<int>> matrix = { {1,2,3,4},
                                   {3,5,6,9},
                                   {7,10,11,14},
                                   {9,12,15,20}};
    assert(matrix_search(matrix, 3));
    assert(matrix_search(matrix, 16) == false);
    assert(matrix_search(matrix, 6));
    assert(matrix_search(matrix, 17) == false);
}

void test_find_min_max() {
    vector<int> v = {2, 5, 3, 6, 7, 9};
    pair<int, int> p = find_min_max(v);
    assert(p.first == 2);
    assert(p.second == 9);
}

void test_find_kth_largest() {
    vector<int> v = {1,10,3,9,15,21,4,34};
    assert(find_kth_largest(v, 1) == 34);
    assert(find_kth_largest(v, 3) == 15);
}

void test_search() {
    test_search_first();
    test_search_first_larger();
    test_search_index_value_equal();
    test_find_pair_sum_k();
    test_search_smallest();
    test_binary_search_unknown_len();
    test_completion_search();
    test_find_kth_in_two_sorted_arrays();
    test_square_root();
    test_matrix_search();
    test_find_kth_largest();
}