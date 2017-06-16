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
    assert(search_first_larger(a, 108) == 4);
    assert(search_first_larger(a, 285) == 9);
    assert(search_first_larger(a, -15) == 0);
    assert(search_first_larger(a, 402) == -1);
    assert(search_first_larger(a, -14) == 1);
    assert(search_first_larger(a, 401) == -1);
}

void test_search() {
    test_search_first();    
}