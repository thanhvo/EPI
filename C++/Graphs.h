#ifndef GRAPHS_H
#define GRAPHS_H

#include <iostream>
#include <vector>
#include <unordered_set>
#include <queue>
#include <limits>
#include <set>

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
        int maxDistance;
        bool visited;
        vector<GraphVertex*> edges;
        vector<GraphVertex*> extendedContacts;
        GraphVertex(void): d(-1), discovery(0), leaving(numeric_limits<int>::max()), 
            visitTime(-1), group(-1), maxDistance(1), visited(false) {}
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
    
int find_largest_number_teams(vector<GraphVertex> &G);

template <typename DistanceType>
class DistanceGraphVertex {
    public:
        pair<DistanceType, int> distance; // stores (dis, #edges) pair
        // stores (vertex, dis) pair
        vector<pair<DistanceGraphVertex<DistanceType>*, DistanceType>> edges;        
        DistanceGraphVertex* pred; // stores the predecessor in the shortest path        
        bool visited;
        int id; // stores the id of this vertex
        
        DistanceGraphVertex () :
            distance(numeric_limits<DistanceType>::max(), 0), pred(nullptr), id(-1), visited(false) {}
        
        DistanceGraphVertex (int __id) :
            distance(numeric_limits<DistanceType>::max(), 0), pred(nullptr), visited(false), id(__id) {}
        
        void addEdge(DistanceGraphVertex<DistanceType>* vertex, DistanceType dis) {
            edges.emplace_back(vertex,dis);
            distance.second++;
        }
};

template <typename DistanceType>
class Comp {
    public:
        const bool operator() (const DistanceGraphVertex<DistanceType>* lhs, 
                        const DistanceGraphVertex<DistanceType>* rhs) const {
            return lhs->distance.first < rhs->distance.first || (lhs->distance.first == rhs->distance.first 
                && lhs->distance.second < rhs->distance.second);
        }
};

template <typename DistanceType> 
void output_shortest_path(DistanceGraphVertex<DistanceType>* &v) {
    if (v) {
        output_shortest_path(v->pred);
        cout << v->id << " ";
    }
}

template <typename DistanceType>
void Dijkstra_shortest_path(vector<DistanceGraphVertex<DistanceType>> &G, 
    DistanceGraphVertex<DistanceType>* s, DistanceGraphVertex<DistanceType>* t) {
    // Initialization the distance of starting point 
    s->distance = {0,0};
    set<DistanceGraphVertex<DistanceType>*, Comp<DistanceType>> node_set;
    node_set.emplace(s);
    do {
        DistanceGraphVertex<DistanceType>* u = nullptr;
        // Extract the minimum distance vertex from heap
        while (node_set.empty() == false) {
            u = *node_set.cbegin();
            node_set.erase(node_set.cbegin());
            if (u->visited == false) {
                // found an unvisited node
                break;
            }
        }
        if (u) { // u is a valid vertex
            u->visited = true; // mark u as visited 
            // Relax neighboring vertices of u
            for (const auto & v: u->edges) {
                DistanceType v_distance = u->distance.first + v.second;
                int v_num_edges = u->distance.second + 1;
                if (v.first->distance.first > v_distance || 
                (v.first->distance.second > v_num_edges)) {
                    node_set.erase(v.first);
                    v.first->pred = u;
                    v.first->distance = {v_distance, v_num_edges};
                    node_set.emplace(v.first);
                }
            }
        } else { // u is not a valid vertex
            break;
        }
    } while (t->visited == false); // until t is visited
    // Output the shortest path with fewest edges
    output_shortest_path(t);
    cout << endl;
}

#endif