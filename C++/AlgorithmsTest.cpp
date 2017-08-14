#include "Algorithms.h"
#include "AlgorithmsTest.h"

void test_drawing_skylines() {
    vector<Skyline<int, int>> skylines = {{1,2,3}, {2,4,2}, {3,4,3}, {1,5,1}};
    vector<Skyline<int, int>> drawn_skylines = drawing_skylines(skylines);
    for (auto skyline : drawn_skylines) 
        skyline.print();
}

void test_algorithms() {
    test_drawing_skylines();
}