#ifndef ALGORITHMS_H
#define ALGORITHMS_H

#include <vector>
#include <iostream>
#include <utility>
#include <cmath>
#include <tuple>
#include <limits>
#include <algorithm>

using namespace std;

template <typename CoordType, typename HeightType>
class Skyline {
    public:
        CoordType left, right;
        HeightType height;
        void print() {
            cout << "left = " << left << ", right = " << right << ", height = " << height << endl;
        }
};

template <typename CoordType, typename HeightType>
void merge_intersect_skylines(vector<Skyline<CoordType, HeightType>> &merged, Skyline<CoordType, HeightType> &a, int &a_idx,
    Skyline<CoordType, HeightType> &b, int &b_idx) {
    if (a.right <= b.right) {
        if (a.height > b.height) {
            if (b.right != a.right) {
                merged.emplace_back(a), ++a_idx;
                b.left = a.right;
            } else {
                ++b_idx;
            }
        } else if (a.height == b.height) {
            b.left = a.left, ++a_idx;
        } else { // a.height < b.height
            if (a.left != b.left) {
                merged.emplace_back(Skyline<CoordType, HeightType>{a.left, b.left, a.height});
            }
            ++a_idx;
        }
    } else { // a.right > b.right
        if (a.height >= b.height) {
            ++b_idx;
        } else {
            if (a.left != b.left) {
                merged.emplace_back(Skyline<CoordType, HeightType>{a.left, b.left, a.height});
            }
            a.left = b.right;
            merged.emplace_back(b), ++b_idx;
        }
    }
}

template <typename CoordType, typename HeightType> 
vector<Skyline<CoordType, HeightType>> merge_skylines(vector<Skyline<CoordType, HeightType>> &L, vector<Skyline<CoordType, HeightType>> &R) {
    int i = 0, j = 0;
    vector<Skyline<CoordType, HeightType>> merged;
    while (i < (int)L.size() && j < (int)R.size()) {
        if (L[i].right < R[j].left) {
            merged.emplace_back(L[i++]);            
        } else if (R[j].right < L[i].left) {
            merged.emplace_back(R[j++]);
        } else if (L[i].left <= R[j].left) {
            merge_intersect_skylines(merged, L[i], i, R[j], j);
        } else { // R[i].left > R[j].left
            merge_intersect_skylines(merged, R[j], j, L[i], i);
        }        
    }
    copy(L.cbegin() + i, L.cend(), back_inserter(merged));
    copy(R.cbegin() + j, R.cend(), back_inserter(merged));
    return merged;           
}

template <typename CoordType, typename HeightType>
vector <Skyline<CoordType, HeightType>> drawing_skylines_helper(vector<Skyline<CoordType, HeightType>> &skylines, 
    const int &start, const int &end) {
    if (end - start <= 1) { // 0 or 1 skyline, just copy it
        return {skylines.cbegin() + start, skylines.cbegin() + end};
    }
    int mid = start + ((end - start) >> 1);
    auto L = drawing_skylines_helper(skylines, start, mid);
    auto R = drawing_skylines_helper(skylines, mid, end);
    return merge_skylines(L, R);    
}

template <typename CoordType, typename HeightType>
vector<Skyline<CoordType, HeightType>> drawing_skylines(vector<Skyline<CoordType, HeightType>> skylines) {
    return drawing_skylines_helper(skylines, 0, skylines.size());
}

template <typename T>
int merge(vector<T> &A, const int &start, const int &mid, const int &end) {
    vector<T> sorted_A;
    int left_start = start, right_start = mid, invert_count = 0;
    while (left_start < mid && right_start < end) {
        if (A[left_start] <= A[right_start]) {
            sorted_A.emplace_back(A[left_start++]);
        } else {
            // A[left_start : mid -1] will be the inversions
            invert_count += mid - left_start;
            sorted_A.emplace_back(A[right_start++]);
        }
    }
    copy(A.begin() + left_start, A.begin() + mid, back_inserter(sorted_A));
    copy(sorted_A.begin(), sorted_A.end(), A.begin() + start);
    return invert_count;    
}

template <typename T>
int count_inversions_helper(vector<T> &A, const int &start, const int &end) {
    if (end - start <= 1) {
        return 0;
    }
    int mid = start + ((end - start) >> 1);
    return count_inversions_helper(A, start, mid) + count_inversions_helper(A, mid, end) + merge(A, start, mid, end);
}

template <typename T>
int count_inversions(vector<T> &A) {
    return count_inversions_helper(A, 0, A.size());
}

class Point {
    public:
        int x, y;
        void print() {
            cout << x << " " << y << endl;
        }
};

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

#endif