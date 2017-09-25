#ifndef GRAPHS_H
#define GRAPHS_H

#include <iostream>
#include <vector>
#include <unordered_set>
#include <queue>

using namespace std; 

class Coordinate {
    public:
            int x, y;
            const bool operator == (const Coordinate &that) const {
                return (x == that.x && y == that.y);
            }
            void print() {
                cout << "(" << x << " " << y << ")";
            }
            Coordinate(int __x, int __y): x(__x), y(__y) {}
};

vector<Coordinate> search_maze(vector<vector<int>> maze, const Coordinate &s, const Coordinate &e);

int transform_string(unordered_set<string> D, const string &s, const string &t);

class GraphVertex {
    public:
        int d;
        vector<GraphVertex*> edges;
        GraphVertex(void): d(-1) {}
};

bool BFS(GraphVertex* s);

bool is_any_placement_feasible(vector<GraphVertex> &G);

#endif