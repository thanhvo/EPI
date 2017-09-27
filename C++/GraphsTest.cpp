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

void test_cyclic_graph() {
    GraphVertex A,B,C,D,E,F,G;
    A.edges = {&B, &C};
    B.edges = {&A, &D, &E};
    C.edges = {&A, &F, &G};
    D.edges = {&B};
    E.edges = {&B};
    F.edges = {&C};
    G.edges = {&C};
    vector<GraphVertex*> Graph = {&A};
    // Test case 1: non-cyclic graph
    //assert(is_graph_2_exists(Graph) == false);
    // Test case 2: add a new edge to make the graph cyclic
    F.edges.push_back(&G);
    G.edges.push_back(&F);
    //assert(is_graph_2_exists(Graph));
    //assert(is_graph_2_for_all(Graph) == false);
    B.edges.push_back(&C);
    C.edges.push_back(&B);
    D.edges.push_back(&E);
    E.edges.push_back(&D);
    //assert(is_graph_2_for_all(Graph) == false);
    F.edges.push_back(&G);
    G.edges.push_back(&F);
    assert(is_graph_2_for_all(Graph));
}

void test_cyclic_graph_small() {
    GraphVertex A, B, C;
    A.edges = {&B, &C};
    C.edges = {&A, &B};
    B.edges = {&A, &C};
    vector<GraphVertex*> Graph = {&A};
    assert(is_graph_2_for_all(Graph));
}

void test_graphs() {
    test_search_maze();
    test_transform_string();
    test_board_placement();
    test_cyclic_graph();
    test_cyclic_graph_small();
}