#include <string>
#include <cassert>
#include <iostream>
#include "HashUtil.h"

using namespace std;

void test_find_closest_petition() {
    vector<string> s = {"All", "work", "and", "no", "play", "makes", "for", "no", "work", "no", "fun", "and", "no", "results"};
    assert(find_closest_repetition(s) == 2);
}
void test_hash() {
    test_find_closest_petition();
}
