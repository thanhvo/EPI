#include <numeric>
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

int max_rectangle_submatrix(const vector<vector<bool>> &A) {
    // DP table stores (h,w) for each (i,j)
    vector<vector<MaxHW>> table(A.size(), vector<MaxHW>(A.front().size()));
    for (int i = A.size() - 1; i >= 0; --i) {
        for (int j = A[i].size() - 1; j >= 0; --j) {
            // Find the largest h such that (i,j) to (i+h-1, j) are feasible 
            // Find the alrgest w such that (i,j) to (i, j+w-1) are feasible
            table[i][j] = A[i][j] ? 
                MaxHW{ i+1 < (int)A.size() ? table[i+1][j].h + 1 : 1, j+1 < (int)A[i].size() ? table[i][j+1].w + 1: 1} : 
                MaxHW{0,0};
        }
    }
    int max_rect_area = 0;
    for (int i = 0; i < (int)A.size(); ++i) {
        for (int j = 0; j < (int)A.size(); ++j) {
            // Process (i, j) if it is feasible and is possible to update max_rect_area
            if (A[i][j] && table[i][j].w * table[i][j].h > max_rect_area) {
                int min_width = numeric_limits<int>::max();
                for (int a = 0; a < table[i][j].h; ++a) {
                    min_width = min (min_width, table[i+a][j].w);
                    max_rect_area = max(max_rect_area, min_width * (a +1));
                }
            }
        }
    }
    return max_rect_area;
}

int max_square_submatrix(const vector<vector<bool>> &A) {
    // DP table stores (h,w) for each (i, j)
    vector<vector<MaxHW>> table(A.size(), vector<MaxHW>(A.front().size()));
    for (int i = A.size() - 1; i >= 0; --i) {
        for (int j = A[i].size() -1; j >= 0; --j) {
            // Find the largest h such that (i, j) to (i + h -1, j) are feasible
            // Find the largest w such that (i, j + w -1) are feasible
            table[i][j] = A[i][j] ? MaxHW { i + 1 < (int)A.size() ? table[i + 1][j].h + 1 : 1, 
                                            j + 1 < (int)A[i].size() ? table[i][j + 1].w + 1 : 1}:
                                    MaxHW {0, 0};
                                            
        }
    }
    // A table stores the length of largest square for each (i, j)
    vector<vector<int>> s(A.size(), vector<int>(A.front().size(), 0));
    int max_square_area = 0;
    for (int i = A.size() - 1; i >= 0; --i) {
        for (int j = A[i].size() -1; j >= 0; --j) {
            int side = min(table[i][j].h, table[i][j].w);
            if (A[i][j]) {
                // Get the length of largest square with bottom-left corner (i, j)
                if (i + 1 < (int)A.size() && j + 1 < (int)A[i+1].size()) {
                    side = min(s[i+1][j+1]+1, side);
                }
                s[i][j] = side;
                max_square_area = max(max_square_area, side * side);
            }
        }
    }
    return max_square_area;
}

int max_rectangle_submatrix2(const vector<vector<bool>> &A) {
    vector<vector<int>> table (A.size(), vector<int>(A.front().size()));
    for (int i = A.size() -1; i >= 0; --i) {
        for (int j = A[i].size() -1; j >= 0; --j) {
            table[i][j] = A[i][j] ? (i +1) < (int)A.size() ? table[i+1][j] + 1 : 1 : 0;
        }
    }
    // Find the max among all instances of the largest rectangle
    int max_rect_area = 0;
    for (const vector<int> & t : table) {
        max_rect_area = max(max_rect_area, calculate_largest_rectangle(t));
    }
    return max_rect_area;
}

bool match_helper(const vector<vector<int>> &A, const vector<int> &S, 
    unordered_set<tuple<int, int, int>, HashTuple> cache, int i, int j, int len) {
    if ((int)S.size() == len) {
        return true;
    }
    if ( i < 0 || i >= (int)A.size() || j < 0 || j >= (int)A[i].size() || cache.find({i,j,len}) != cache.cend()) {
        return false;
    }
    if (A[i][j] == S[len] && (match_helper(A, S, cache, i-1, j, len+1) ||
                            match_helper(A, S, cache, i+1, j, len+1) ||
                            match_helper(A, S, cache, i, j-1, len+1) ||
                            match_helper(A, S, cache, i, j+1, len+1))){
        return true;
    }
    cache.insert({i,j,len});
    return false;
}

bool match(const vector<vector<int>> &A, const vector<int> &S) {
    unordered_set<tuple<int, int, int>, HashTuple> cache;
    for (int i = 0; i < (int)A.size(); ++i) {
        for (int j = 0; j < (int)A[i].size(); ++j) {
            if (match_helper(A, S, cache, i, j, 0)) {
                return true;
            }
        }
    }
    return false;
}

int leveshtein_distance(string A, string B) {
    // Try to reduce the space usage
    if (A.size() < B.size()) {
        swap(A, B);
    }
    vector<int> D(B.size() + 1);
    // Initialization
    iota(D.begin(), D.end(), 0);
    for (int i = 1; i <= A.size(); ++i) {
        int pre_i_1_j_1 = D[0];
        // Stores the value of D[i-1][j-1]
        D[0] = i;
        for (int j = 1; j <= B.size(); ++j) {
            int pre_i_1_j = D[j];
            // Stores the value of D[i-1][j]
            D[j] = A[i-1] == B[j-1] ? pre_i_1_j_1 : 1 + min(pre_i_1_j_1, min(D[j-1], D[j]));
            // Previous D[i-1][j] will become next D[i-1][j-1]
            pre_i_1_j_1 = pre_i_1_j;
        }
    }
    return D.back();
}
