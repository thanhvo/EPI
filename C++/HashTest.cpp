#include <string>
#include <cassert>
#include <iostream>
#include <sstream>
#include <iterator>
#include "HashUtil.h"

using namespace std;

void test_find_closest_petition() {
    vector<string> s = {"All", "work", "and", "no", "play", "makes", "for", "no", "work", "no", "fun", "and", "no", "results"};
    assert(find_closest_repetition(s) == 2);
}

void test_anagrams() {
    string values = "dears dares reads impinged impeding ogre gore ergo rowdies dowries weirdos pole lope"
                " review viewer ginkgos gingkos strange garnets aides ideas aside gutsiest gustiest"
                " damson nomads tacit attic edges sedge pulsing plugins endows snowed cones scone sated stead dates";
    stringstream ss(values);
    istream_iterator<string> begin(ss);
    istream_iterator<string> end;
    vector<string> v(begin, end);
    find_anagrams(v);
}

void test_hash() {
    test_find_closest_petition();
    test_anagrams();
}
