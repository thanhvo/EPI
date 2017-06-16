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

void test_search() {
    test_search_first();    
}