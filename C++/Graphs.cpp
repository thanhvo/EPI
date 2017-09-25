#include <array>
#include <queue>
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

// Use BFS to find the least steps of transformation
int transform_string(unordered_set<string> D, const string &s, const string &t) {
    queue<pair<string, int>> q;
    D.erase(s); // mark s as visited by erasing it in D
    q.emplace(s, 0);
    while (!q.empty()) {
        pair<string, int> f(q.front());
        // Return if we find a match
        if (f.first == t) {
            return f.second; // number of steps to reach t
        }
        // Try all possible transformations of f.first
        string str = f.first;
        for (unsigned int i = 0; i < str.size(); ++i) {
            for (int j = 0; j < 26; j++) { // iterates through 'a' ~ 'z'
                str[i] = 'a' + j; // change the (i+1)-th char of str 
                auto it(D.find(str));
                if (it != D.end()) {
                    D.erase(it); // mark str as visited by erasing it
                    q.emplace(str, f.second + 1);
                }
            }
            str[i] = f.first[i]; // revert the change of str
        }
        q.pop();
    }
    return -1;
}

bool BFS(GraphVertex* s) {
    queue<GraphVertex*> q;
    q.emplace(s);
    while(q.empty() == false) {
        for (GraphVertex* &t: q.front()->edges) {
            if (t->d == -1) { // Unvisited vertex
                t->d = q.front()->d + 1;
                q.emplace(t);
            } else if (t->d == q.front()->d) {
                return false;
            }
        }
        q.pop();
    }
    return true;
}

bool is_any_placement_feasible(vector<GraphVertex> &G) {
    for (GraphVertex &v: G) {
        if (v.d == -1) { // unvisited vertex
            v.d = 0;
            if (BFS(&v) == false) {
                return false;
            }
        }        
    }
    return true;
}
     