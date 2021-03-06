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

void test_transitive_closure() {
    GraphVertex A, B, C;
    A.edges = {&B, &C};
    C.edges = {&A};
    B.edges = {&A};
    vector<GraphVertex*> Graph = {&A, &B, &C};
    transitive_closure(Graph);
    for (GraphVertex* v: A.extendedContacts) {
        cout << v << " ";
    }
    cout << endl;
    for (GraphVertex* v: B.extendedContacts) {
        cout << v << " ";
    }
    cout << endl;
}

void test_graph_constraints() {
    vector<Constraint> E = {{0,1}, {1,2}, {3,4}, {3,5}};
    vector<Constraint> I = {{0,3}, {1,5}, {2,4}};
    assert(are_constraints_satisfied(E, I));
    E.emplace_back(Constraint{2,3});
    assert(!are_constraints_satisfied(E, I));
}

void test_largest_number_teams() {
    GraphVertex A,B,C,D,E,F,G;
    A.edges = {&B, &C, &D};
    B.edges = {&C,&D};
    C.edges = {&E};
    D.edges = {&E};
    E.edges = {&G};
    F.edges = {&G};
    vector<GraphVertex> Graph = {A, B, C, D, E, F, G};
    assert(find_largest_number_teams(Graph) == 5);    
}

void test_dijkstra() {
    cout << "Dijkstra algorithm to find the shortest path" << endl;
    DistanceGraphVertex<int> A(0),B(1),C(2),D(3),E(4),F(5),G(6),H(7),I(8),J(9),K(10),L(11),M(12),N(13);    
    A.addEdge(&C, 2);
    A.addEdge(&B, 3);
    B.addEdge(&A, 4);
    B.addEdge(&K, 1);
    C.addEdge(&E, 8);
    D.addEdge(&C, 5);
    D.addEdge(&H, 5);
    E.addEdge(&D, 7);
    F.addEdge(&G, 6);
    G.addEdge(&F, 7);
    G.addEdge(&H, 4);
    I.addEdge(&J, 6);
    J.addEdge(&F, 1);
    J.addEdge(&L, 7);
    K.addEdge(&I, 1);
    L.addEdge(&I, 9);
    M.addEdge(&N, 5);
    N.addEdge(&M, 12);
    vector<DistanceGraphVertex<int>> Graph = {A,B,C,D,E,F,G,H,I,J,K,L,M,N};
    //Dijkstra_shortest_path(Graph, &A, &H);
    Dijkstra_shortest_path(Graph, &B, &H);
}

void test_Floyd_Warshall() {
    cout << "Testing Floyd Warshall" << endl;
    vector<HighwaySection<int>> H = {{1, 2, 1}, {1, 4, 2}, {2, 3, 2}, {4, 3, 3}, {3, 5, 1}, 
                            {5, 6, 2}, {5, 8, 1}, {8, 7, 3}, {6, 7, 1}};
    vector<HighwaySection<int>> P = {{4, 6, 3}, {3, 7, 1}, {2, 5, 2}};
    HighwaySection<int> p = find_best_proposals(H, P, 1, 7, 9);
    p.print();
}

void test_arbitrage() {
    vector<vector<double>> G = {
        {1.0, 1.0/2.0, 1.0/3.0, 1.0/4.0},
        {2.0, 1.0, 2.0/3.0, 1.0/2.0},
        {3.0, 3.0/2.0, 1.0, 1.0/4.0},
        {4.0, 2.0, 4.0, 1.0}
    };
    assert(is_arbitrage_exists(G));
}

void test_graphs() {
    test_search_maze();
    test_transform_string();
    test_board_placement();
    test_cyclic_graph();
    test_cyclic_graph_small();
    test_transitive_closure();
    test_graph_constraints();
    test_largest_number_teams();
    test_dijkstra();
    test_Floyd_Warshall();
    test_arbitrage();
}