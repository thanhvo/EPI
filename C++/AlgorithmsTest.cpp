#include <cassert>
#include "Algorithms.h"
#include "AlgorithmsTest.h"

void test_drawing_skylines() {
    vector<Skyline<int, int>> skylines = {{1,2,3}, {2,4,2}, {3,4,3}, {1,5,1}};
    vector<Skyline<int, int>> drawn_skylines = drawing_skylines(skylines);
    for (auto skyline : drawn_skylines) 
        skyline.print();
}

void test_count_inversions() {
    vector<int> v = {4,3,1,5,8,7,10,9};
    assert(count_inversions(v) == 5);    
}

void test_algorithms() {
    test_drawing_skylines();
    test_count_inversions();
}