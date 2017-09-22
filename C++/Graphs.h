#ifndef GRAPHS_H
#define GRAPHS_H

#include <iostream>
#include <vector>

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

#endif