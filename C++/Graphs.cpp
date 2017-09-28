#include <array>
#include <queue>
#include <limits>
#include <unordered_map>
#include <stack>
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

bool DFS(GraphVertex* cur, const GraphVertex* pre) {
    // Visiting a gray vertex means a cycle 
    if (cur->color == GraphVertex::gray) {
        return true;
    }
    cur->color = GraphVertex::gray; // marks current vertex as a gray one
    // Traverse the neighbor vertices
    for (GraphVertex* &next: cur->edges) {
        if (next != pre && next->color != GraphVertex::black) {
            if (DFS(next, cur)) {
                return true;
            }
        }
    }
    cur->color = GraphVertex::black; //marks current vertex as black
    return false;
}

bool is_graph_2_exists(vector<GraphVertex*> &G) {
    if (G.empty() == false) {
        return DFS(G.front(), nullptr);
    }
    return true;
}

bool DFS(GraphVertex* cur, const GraphVertex* pre, int time) {
    cur->discovery = ++time, cur->leaving = numeric_limits<int>::max();
    for (GraphVertex* next: cur->edges) {   
        if (next != pre) {
            if (next->discovery != 0) { // back edge
                cur->leaving = min(cur->leaving, next->discovery);
            } else { // forward edge
                if (DFS(next, cur, time) == false) {
                    return false;
                }
                cur->leaving = min(cur->leaving, next->leaving);
            }
        }
    }    
    return (pre == nullptr || cur->leaving < cur->discovery);    
}

bool is_graph_2_for_all(vector<GraphVertex*> &G) {
    if (G.empty() == false) {
        return DFS(G.front(), nullptr, 0);
    }
    return true;
}

void DFS(GraphVertex* cur, const int &time, vector<GraphVertex*> &contacts) {
    for (GraphVertex* &next: cur->edges) {
        if (next->visitTime != time) {
            next->visitTime = time;
            contacts.emplace_back(next);
            DFS(next, time, contacts);
        }
    }
}

void transitive_closure(vector<GraphVertex*> &G) {
    // Build extended contacts for each vertex
    for (int i = 0; i < (int)G.size(); ++i) {
        if (G[i]->visitTime != i) {
            G[i]->visitTime = i;
            DFS(G[i], i, G[i]->extendedContacts);
        }
    }
}

void DFS(GraphVertex &u) {
    for (GraphVertex* &v : u.edges) {
        if (v->group == -1) {
            v->group = u.group;
            DFS(*v);
        }
    }
}

bool are_constraints_satisfied (const vector<Constraint> &E, // Equality constraints
    const vector<Constraint> &I) { // Inequality constraints
    unordered_map<int, GraphVertex> G;
    // Build graph G according to E
    for (const Constraint &e : E) {
        G[e.a].edges.emplace_back(&G[e.b]);
        G[e.b].edges.emplace_back(&G[e.a]);
    }
    // Assign group index for each connected component 
    int group_count = 0;
    for (pair<int, GraphVertex> vertex : G) {
        if(vertex.second.group == -1) { // is a unvisited vertex
            vertex.second.group = group_count++;
            // assign a group index
            DFS(vertex.second);
        }
    }
    // Examine each inequality constraint to see if there is a violation
    for (const Constraint &i : I) {
        if (G[i.a].group == G[i.b].group) {
            return false;
        }
    }
    return true;
}

void DFS(GraphVertex* cur, stack<GraphVertex*> &vertex_order) {
    cur->visited = true;
    for (GraphVertex* &next: cur->edges) {
        if (next->visited == false) {
            DFS(next, vertex_order);
        }
    }
    vertex_order.emplace(cur);
}

stack<GraphVertex*> build_topological_ordering(vector<GraphVertex> &G) {
    stack<GraphVertex*> vertex_order;
    for (GraphVertex &g: G) {
        if (g.visited == false) {
            DFS(&g, vertex_order);
        }
    }
    return vertex_order;
}

int find_longest_path(stack<GraphVertex*> &vertex_order) {
    int max_distance = 0;
    while (vertex_order.empty() == false) {
        GraphVertex* u = vertex_order.top();
        max_distance = max(max_distance, u->maxDistance);
        for (GraphVertex* &v : u->edges) {
            v->maxDistance = max(v->maxDistance, u->maxDistance + 1);
        }
        vertex_order.pop();
    }
    return max_distance;
}

int find_largest_number_teams(vector<GraphVertex> &G) {
    stack<GraphVertex*> vertex_order(build_topological_ordering(G));
    return find_longest_path(vertex_order);
}
     