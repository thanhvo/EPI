#include "HeapApp.h"
#include <array>
#include <iostream>

using namespace std;

vector<Star> find_closest_k_stars(istringstream &sin, const int &k) {
    priority_queue<Star, vector<Star>> max_heap;
    string line;
    while (getline(sin, line)) {
        stringstream line_stream(line);
        string buf;
        getline(line_stream, buf, ',');
        int ID = stoi(buf);
        array<double, 3> data;
        for (int i =0; i < 3; ++i) {
            getline(line_stream, buf, ',');
            data[i] = stod(buf);            
        }
        Star s {ID, data[0], data[1], data[2]};
        if (max_heap.size() == k) {
            Star far_star = max_heap.top();
            if (s < far_star) {
                max_heap.pop();
                max_heap.emplace(s);
            } 
        } else {
            max_heap.emplace(s);
        }        
    }
    vector<Star> closest_stars;
    while(!max_heap.empty()) {
        closest_stars.emplace_back(max_heap.top());
        max_heap.pop();
    }
    return closest_stars;
}