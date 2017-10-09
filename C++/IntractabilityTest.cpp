#include <cassert>
#include <iostream>
#include "Intractability.h"
#include "IntractabilityTest.h"

using namespace std;

void test_ties_election() {
    vector<int> V = {1,3,5,7,2};
    assert(ties_election(V));
    vector<int> L = {1,3,5,7,12};
    assert(ties_election(L) == false);
}

void test_knapsack() {
    int w = 130;
    vector<pair<int,int>> V = {{20, 65}, {8,35}, {60, 245}, {55, 195}, {40, 65}, {70, 150}, {85, 275},
        {25, 155}, {30, 120}, {65, 320}, {75, 75}, {10, 40}, {95, 200}, {50, 100}, {40, 220}, {10, 99}};
    assert(knapsack<int>(w, V) == 695);
}

void test_minimize_difference() {
    vector<int> V = {65, 35, 245, 195, 65, 150, 275, 155, 120, 320, 75, 40, 200, 100, 220, 99};
    assert(minimize_difference(V) == 65 + 275 + 320 + 200 + 220 + 99);
}

void test_intractability() {
    test_ties_election();
    test_knapsack();
    test_minimize_difference();
}