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

void test_defective_jugs() {
    vector<Jug> jugs = {{230, 240}, {290, 310}, {500, 520}};
    assert(check_feasible(jugs, 2100, 2200));    
    assert(check_feasible(jugs, 2200, 2300) == false);
    assert(check_feasible(jugs, 2100, 2150) == false);
}

void test_sudoku() {
    vector<vector<int>> v1 = {
        {6,3,9,5,7,4,1,8,2},
        {5,4,1,8,2,9,3,7,6},
        {7,8,2,6,1,3,9,5,4},
        {1,9,8,4,6,7,5,2,3},
        {3,6,5,9,8,2,4,1,7},
        {4,2,7,1,3,5,8,6,9},
        {9,5,6,7,4,8,2,3,1},
        {8,1,3,2,9,6,7,4,5},
        {2,7,4,3,5,1,6,9,8}
    };
    vector<vector<int>> v2 = {
        {0,0,0,2,6,0,7,0,1},
        {6,8,0,0,7,0,0,9,0},
        {1,9,0,0,0,4,5,0,0},
        {8,2,0,1,0,0,0,4,0},
        {0,0,4,6,0,2,9,0,0},
        {0,5,0,0,0,3,0,2,8},
        {0,0,9,3,0,0,0,7,4},
        {0,4,0,0,5,0,0,3,6},
        {7,0,3,0,1,8,0,0,0}
    };
    assert(solve_sudoku(v1));
    cout << endl;
    assert(solve_sudoku(v2));    
}

void test_intractability() {
    test_ties_election();
    test_knapsack();
    test_minimize_difference();
    test_defective_jugs();
    test_sudoku();
}