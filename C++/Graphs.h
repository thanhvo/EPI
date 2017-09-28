#ifndef GRAPHS_H
#define GRAPHS_H

#include <iostream>
#include <vector>
#include <unordered_set>
#include <queue>
#include <limits>

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
        enum Color {white, gray, black} color; 
        int d; // degree 
        int discovery, leaving; // discovery and leaving time
        int visitTime;
        int group; // represents the connected component it belongs
        vector<GraphVertex*> edges;
        vector<GraphVertex*> extendedContacts;
        GraphVertex(void): d(-1), discovery(0), leaving(numeric_limits<int>::max()), visitTime(-1), group(-1) {}
};

bool BFS(GraphVertex* s);

bool is_any_placement_feasible(vector<GraphVertex> &G);

bool DFS(GraphVertex* cur, const GraphVertex* pre);

bool is_graph_2_exists(vector<GraphVertex> &G);

bool is_graph_2_for_all(vector<GraphVertex*> &G);

void DFS(GraphVertex* cur, const int &time, vector<GraphVertex*> &contacts);

void transitive_closure(vector<GraphVertex*> &G) ;

class Constraint {
    public:
        int a, b;
};

bool are_constraints_satisfied (const vector<Constraint> &E, // Equality constraints
    const vector<Constraint> &I);

#endif