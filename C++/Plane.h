#ifndef PLANE_H
#define PLANE_H

#include <functional>
#include <numeric>
#include <unordered_map>
#include <unordered_set>
#include <utility>
#include <algorithm>

using namespace std;

class Point {
    public:
        int x, y;
        // Equal function for hash
        const bool operator==(const Point &that) const{
            return x == that.x && y == that.y;
        }
};

// Hash function for Point
class HashPoint{
    public:
        const size_t operator() (const Point &p) const {
            hash<int> hash;
            return hash(p.x) ^ hash(p.y);
        }
};

// Find the greatest common divisor
int GCD(int a, int b) {
    while ( a > 0 && b > 0) {
        if (a >= b) {
            a = a % b;
        } else {
            b = b % a;
        }
    }
    return max(a, b);
}

//Line function of two points, a and b, and the equation is
// y = x(b.y - a.y)/ (b.x - a.x) + (b.x * a.y - a.x * b.y)/(b.x - a.x) 
class Line {
    private:
        static pair<int, int> get_canonical_fractional(int a, int b) {
            int d = GCD(abs(a), abs(b));
            a/= d, b /= d;
            return b < 0 ? make_pair(-a, -b) : make_pair(a, b);
        }
    public:
        // Store the numerator and denominator pair of slope unless the line is 
        // parallel to y-axis that we store 1/0
        pair<int, int> slope;
        // Store the numerator and denominator pair of the y-intercept unless
        // the line is parallel to y-axis that we store the x-intercept
        pair<int, int> intercept;
        Line(const Point &a, const Point &b): 
            slope (a.x != b.x ? get_canonical_fractional(b.y - a.y, b.x - a.x) : make_pair(1, 0)), 
            intercept (a.x != b.x ? get_canonical_fractional(b.x*a.y - a.x*b.y, b.x - a.x): make_pair(a.x,1 )) {}
        // Equal function for hash
        const bool operator==(const Line &that) const {
            return slope == that.slope && intercept == that.intercept;
        }
};

// Hash function for line
class HashLine {
    public: 
        const size_t operator() (const Line &l) const {
            hash<int> hash_int;
            return hash_int(l.slope.first) ^ hash_int(l.slope.second) ^ hash_int(l.intercept.first) ^ hash_int(l.intercept.second);
        }
};

Line find_line_with_most_points(const vector<Point> &p) {
    // Add all possible lines into hash table
    unordered_map <Line, unordered_set<Point, HashPoint>, HashLine> table;
    for (unsigned int i = 0; i < p.size(); ++i) {
        for (unsigned int j = i+1; j < p.size(); ++j) {
            Line l(p[i], p[j]);
            table[l].emplace(p[i]), table[l].emplace(p[j]);
        }
    }
    // Return the line with most points have passed
    return max_element(table.cbegin(), table.cend(), [](const pair<Line, unordered_set<Point, HashPoint>> &a,
            const pair<Line, unordered_set<Point, HashPoint>> &b) {
                return a.second.size() < b.second.size();
            })->first;
}

#endif