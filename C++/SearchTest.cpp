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

void test_search() {
    test_search_first();
    test_search_first_larger();
    test_search_index_value_equal();
    test_find_pair_sum_k();
}