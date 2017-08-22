#include "Algorithms.h"

double distance(const Point &a, const Point &b) {
    return sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
}

// Return the closest two points and its distance as a tuple
tuple <Point, Point, double> brute_force(const vector<Point> &P, const int &s, const int &e) {
    tuple<Point, Point, double> ret;
    get<2>(ret) = numeric_limits<double>::max();
    for (int i = s; i < e; ++i) {
        for (int j = i +1; j < e; ++j) {
            double dis = distance(P[i], P[j]);
            if (dis < get<2>(ret)) {
                ret = {P[i], P[j], dis};
            }
        }
    }
    return ret;
}

// Return the closest two points and its distance as a tuple
tuple<Point, Point, double> find_closest_pair_in_remain(vector<Point> &P, const double &d) {
    sort(P.begin(), P.end(), [](const Point &a, const Point &b) -> bool {
        return a.y < b.y;
    });
    // At most six points in P
    tuple<Point, Point, double> ret;
    get<2>(ret) = numeric_limits<double>::max();
    for (unsigned int i = 0; i < P.size(); ++i) {
        for (unsigned int j = i +1; j < P.size() && P[j].y - P[i].y < d; ++j) {
            double dis = distance(P[i], P[j]);
            if (dis < get<2>(ret)) {
                ret = {P[i], P[j], dis};
            }
        }
    }
    return ret;
}

// Return the closest two points and its distance as a tuple
tuple <Point, Point, double> find_closest_pair_points_helper(const vector<Point> &P, const int &s, const int &e) {
    if (e - s <= 3) {
        return brute_force(P, s, e);
    }
    int mid = (e + s) >> 1;
    auto l_ret = find_closest_pair_points_helper(P, s, mid);
    auto r_ret = find_closest_pair_points_helper(P, mid, e);
    auto min_l_r = get<2>(l_ret) < get<2>(r_ret) ? l_ret : r_ret;
    vector<Point> remain; // stores the points whose x-dis < min_d;
    for (const Point &p: P) {
        if (abs(p.x - P[mid].x) < get<2>(min_l_r)) {
            remain.emplace_back(p);
        }
    }
    auto mid_ret = find_closest_pair_in_remain(remain, get<2>(min_l_r));
    return get<2>(mid_ret) < get<2>(min_l_r) ? mid_ret : min_l_r;
}

pair<Point, Point> find_closest_pair_points(vector<Point> P) {
    sort(P.begin(), P.end(), [](const Point &a, const Point &b) -> bool {
        return a.x < b.x;
    });
    auto ret = find_closest_pair_points_helper(P, 0, P.size());
    return {get<0>(ret), get<1>(ret)};
}

// Return (height, diameter) pair
pair <double, double> compute_height_and_diameter(const shared_ptr<TreeNode> &r) {
    double diameter = numeric_limits<double>::min();
    array<double, 2> height = {0.0, 0.0};
    // Stores the max 2 heights
    for (const pair<shared_ptr<TreeNode>, double> &e: r->edges) {
        pair<double, double> h_d = compute_height_and_diameter(e.first);
        if (h_d.first + e.second > height[0]) {
            height[1] = height[0];
            height[0] = h_d.first + e.second;
        } else if (h_d.first + e.second > height[1]) {
            height[1] = h_d.first + e.second;
        }
        diameter = max(diameter, h_d.second);
    }
    return {height[0], max(diameter, height[0] + height[1])};
}

double compute_diameter(const shared_ptr<TreeNode> &T) {
    return T ? compute_height_and_diameter(T).second : 0.0;
}