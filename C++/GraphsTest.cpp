#include <vector>
#include <cassert>
#include "GraphsTest.h"
#include "Graphs.h"

using namespace std; 

void test_search_maze() {
    vector<vector<int>> maze = {
      {0,0,0,1,1},
      {0,0,1,1,1},
      {0,0,0,1,1},
      {1,1,0,0,1},
      {1,0,0,0,0}
    };
    Coordinate start(0,0);
    Coordinate end(4,4);
    vector<Coordinate> path = search_maze(maze, start, end);
    for (Coordinate c : path) {
        c.print();
    }
    cout << endl;
}

void test_transform_string() {
    unordered_set<string> dictionary ({"bat","eat","fat","hat","mat","oat","pat","rat","sat","tat",
        "vat","cot","cut","cab","cad","cam","can","cap","car","caw","cat","bet","bit","but",
        "baa","bad","bag","bah","ban","bar","bay","ear","fit","fad","fag","fan","far"});
    assert(transform_string(dictionary, "cat", "car") == 1);
    assert(transform_string(dictionary, "cat", "far") == 2);
    assert(transform_string(dictionary, "cat", "fat") == 1);
    assert(transform_string(dictionary, "cat", "fap") == -1);
}

void test_board_placement() {
    GraphVertex A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T;
    A.edges = {&B, &I};
    B.edges = {&A, &C, &J};
    C.edges = {&B, &D, &K};
    D.edges = {&C, &E, &L};
    E.edges = {&D, &F};
    F.edges = {&E, &G, &L, &M};
    G.edges = {&F, &H};
    I.edges = {&A, &N};
    J.edges = {&B, &I, &K, &O};
    K.edges = {&C, &J, &L};
    L.edges = {&D, &F, &K};
    M.edges = {&F, &H, &S};
    N.edges = {&I, &O};
    O.edges = {&J, &N, &P};
    P.edges = {&O, &Q};
    Q.edges = {&L, &P, &R};
    R.edges = {&Q, &S};
    S.edges = {&M, &R, &T};
    T.edges = {&H, &S};
    vector<GraphVertex> Graph = {A};
    assert(is_any_placement_feasible(Graph));    
}

void test_graphs() {
    test_search_maze();
    test_transform_string();
    test_board_placement();
}