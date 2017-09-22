#include <vector>
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

void test_graphs() {
    test_search_maze();
}