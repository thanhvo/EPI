#include <array>
#include "Graphs.h"

// Check cur is within maze and is a white pixel 
bool is_feasible(const Coordinate &cur, const vector<vector<int>> &maze) {
    return cur.x >= 0 && cur.x < (int)maze.size() && cur.y >= 0 && cur.y < (int)maze[cur.x].size() 
            && maze[cur.x][cur.y] == 0;
}

// Perform DFS to find a feasible path
bool search_maze_helper(vector<vector<int>> &maze, const Coordinate &cur, 
    const Coordinate &e, vector<Coordinate> &path) {
    if (cur == e) {
        return true;
    }    
    const array<array<int, 2>, 4> shift = {0,1,0,-1,1,0,-1,0};
    for (const array<int,2> &s: shift) {
        Coordinate next{cur.x + s[0], cur.y + s[1]};
        if (is_feasible(next, maze)) {
            maze[next.x][next.y] = 1;
            path.emplace_back(next);
            if (search_maze_helper(maze, next, e, path)) {
                return true;
            }
            path.pop_back();
        }
    }
    return false;    
}

vector<Coordinate> search_maze(vector<vector<int>> maze, const Coordinate &s, const Coordinate &e) {
    vector<Coordinate> path;
    maze[s.x][s.y] = 1;
    path.emplace_back(s);
    if (search_maze_helper(maze, s, e, path) == false) {
        path.pop_back();
    }
    return path; // empty path means no paht form s to e
}
     