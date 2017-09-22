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

void test_graphs() {
    test_search_maze();
    test_transform_string();
}