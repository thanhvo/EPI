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

vector<string> word_breaking(const string &s, const unordered_set<string> &dict) {
    // T[i] stores the length of the last string which composed of S(0,i)
    vector<int> T(s.size(), 0);
    for (int i = 0; i < (int)s.size(); ++i) {
        // Set T[i] if S(0,i) is a valid word
        if (dict.find(s.substr(0, i+1)) != dict.cend()) {
            T[i] = i + 1;
        }
        // Set T[i] if T[j != 0 and s(j+1, i) is a valid word
        for (int j = 0; j < i && T[i] == 0; ++j) {
            if (T[j] != 0 && dict.find(s.substr(j +1, i -j)) != dict.cend()) {
                T[i] = i - j;
            }
        }
    }
    vector<string> ret;
    // S can be assembled by valid words
    if (T.back()) {
        int idx = s.size() - 1;
        while (idx >= 0) {
            ret.emplace_back(s.substr(idx- T[idx] + 1, T[idx]));
            idx -= T[idx];
        }
        reverse(ret.begin(), ret.end());
    }
    return ret;
}

int find_pretty_printing(const vector<string> &W, const int &L) {
    // Calculate M(i)
    vector<long> M(W.size(), numeric_limits<long>::max());
    for (int i = 0; i < M.size(); ++i) {
        int b_len = L - W[i].size();
        M[i] = min((i -1 < 0 ? 0 : M[i-1]) + (1 << b_len), M[i]);
        for (int j = i -1; j >= 0; --j) {
            b_len -= (W[j].size() + 1);
            if (b_len < 0) break;
            M[i] = min((j -1 < 0 ? 0 : M[j-1]) + ( 1 << b_len), M[i]);
        }
    }
    // Find the minimum cost without considering the last line
    long min_mess = (W.size() >= 2 ? M[W.size() -2] : 0);
    int b_len = L - W.back().size();
    for (int i = W.size() - 2; i >= 0; --i) {
        b_len -= (W[i].size() + 1);
        if (b_len < 0) {
            return min_mess;
        }
        min_mess = min(min_mess, (i -1 < 0 ? 0 : M[i-1]));
    }
    return min_mess;
}

int compute_binomial_coefficients(const int &n, const int &k) {
    vector<vector<int>> table (n +1, vector<int>(k +1));
    // Basic case: C(i,0) = 1
    for (int i = 0; i <= n; ++i) {
        table[i][0] = 1;
    }
    //Basic case: C(i,i) = 1
    for (int i = 1; i <= k; ++i) {
        table[i][i] = 1;
    }
    // C(i,j) = C(i-1, j) + C(i-1, j-1)
    for (int i = 2; i <= n; ++i) {
        for (int j = 1; j < i && j <= k; ++j) {
            table[i][j] = table[i-1][j] + table[i-1][j-1];
        }
    }
    return table[n][k];
}

int count_combinations(const int &k, const vector<int> &score_ways) {
    vector<int> combinations(k +1, 0);
    combinations[0] = 1; // 1 way to reach 0
    for (const int &score: score_ways) {
        for (int j = score; j <= k; ++j) {
            combinations[j] += combinations[j - score];
        }
    }
    return combinations[k];
}

int count_permuations(const int &k, const vector<int> &score_ways) {
    vector<int> permutations(k +1, 0);
    permutations[0] = 1; // 1 way to reach 0
    for (int i = 0; i <= k; ++i) {
        for (const int & score: score_ways) {
            if (i >= score) {
                permutations[i] += permutations[i - score];
            }
        }
    }
    return permutations[k];
}

int number_of_ways(const int &n, const int &m) {
    vector<vector<int>> A(n, vector<int>(m, 0));
    A[0][0] = 1; // 1 way to start from (0,0)
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            A[i][j] += (i < 1 ? 0 : A[i-1][j] + (j < 1 ? 0 : A[i][j-1]));
        }
    }
    return A.back().back();
}

// Given the dimensions of A, n and m, and B, return the number of ways from A[0][0] to A[n-1][m-1] considering obstacles
int number_of_ways_with_obstacles(const int &n, const int &m, const vector<vector<bool>> &B) {
    vector<vector<int>> A(n, vector<int>(m, 0));
    // No way to start from (0,0) if B[0][0] == true
    A[0][0] = !B[0][0];
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            if (B[i][j] == 0) {
                A[i][j] += (i < 1 ? 0 : A[i-1][j]) + (j < 1 ? 0 : A[i][j-1]);
            }
        }
    }
    return A.back().back();
}

bool is_monochromatic(const vector<vector<int>> &image_sum, const ImagePoint &lower_left, const ImagePoint &upper_right) {
    //lower_left.print();
    //upper_right.print();
    int pixel_sum = image_sum[upper_right.i][upper_right.j];
    if (lower_left.i >= 1)  {
        pixel_sum -= image_sum[lower_left.i -1][upper_right.j];
    }
    if (lower_left.j >= 1) {
        pixel_sum -= image_sum[upper_right.i][lower_left.j-1];
    }
    if (lower_left.i >= 1 && lower_left.j >= 1) {
        pixel_sum += image_sum[lower_left.i-1][lower_left.j-1];
    }
    //cout << pixel_sum << endl;
    return pixel_sum == 0 || // totally white
           pixel_sum == (upper_right.i - lower_left.i + 1) * // totally black
                        (upper_right.j - lower_left.j + 1);
}

shared_ptr<ImageTreeNode> calculate_optimal_2D_tree_helper(const vector<vector<int>> &image, const vector<vector<int>> &image_sum,
        const ImagePoint &lower_left, const ImagePoint &upper_right, 
        unordered_map<ImagePoint, unordered_map<ImagePoint, shared_ptr<ImageTreeNode>, HashPoint>, HashPoint> &table) {
    // Illegal rectangle region, return empty node
    if (lower_left > upper_right) {
        return shared_ptr<ImageTreeNode>(new ImageTreeNode{0, lower_left, upper_right});
    }    
    if (table.find(lower_left) == table.cend() || table[lower_left].find(upper_right) == table[lower_left].cend()) {
        if (is_monochromatic(image_sum, lower_left, upper_right)) {
            shared_ptr<ImageTreeNode> p(new ImageTreeNode{1,lower_left, upper_right});
            table[lower_left][upper_right] = p;
        } else {
            shared_ptr<ImageTreeNode> p(new ImageTreeNode{numeric_limits<int>::max(), lower_left, upper_right});
            for (int s = lower_left.i; s <= upper_right.i ; ++s) {
                for (int t = lower_left.j; t <= upper_right.j; ++t) {
                    if (lower_left.i == s && lower_left.j == t) continue;
                    vector<shared_ptr<ImageTreeNode>> children = {
                        // SW rectangle
                        calculate_optimal_2D_tree_helper(image, image_sum, lower_left, ImagePoint{s-1, t-1}, table),
                        // NW tectangle
                        calculate_optimal_2D_tree_helper(image, image_sum, ImagePoint{lower_left.i, t}, ImagePoint{s-1, upper_right.j}, table),
                        // NE rectangle
                        calculate_optimal_2D_tree_helper(image, image_sum, ImagePoint{s,t}, upper_right, table),
                        // SE rectangle
                        calculate_optimal_2D_tree_helper(image, image_sum, ImagePoint{s, lower_left.j}, ImagePoint{upper_right.i, t-1}, table)
                    };
                    int node_num = 1; // itself
                    for (shared_ptr<ImageTreeNode> &child: children) {
                        node_num += child->node_num;
                        // Remove the child contains no node
                        if (child->node_num == 0) {
                            child = nullptr;
                        }
                    }
                    if (node_num < p->node_num) {
                        p->node_num = node_num;
                        p->children = children;
                    }
                    
                }
            }
            table[lower_left][upper_right] = p;
        }        
    }
    return table[lower_left][upper_right];    
}

shared_ptr<ImageTreeNode> calculate_optimal_2D_tree(const vector<vector<int>> &image) {
    vector<vector<int>> image_sum(image);
    for (int i = 0; i < (int)image.size(); ++i) {
        partial_sum(image_sum[i].cbegin(), image_sum[i].cend(), image_sum[i].begin());
        for (int j = 0; i > 0 && j < (int)image[i].size(); ++j) {
            image_sum[i][j] += image_sum[i-1][j];
        }
    }
    unordered_map<ImagePoint, unordered_map<ImagePoint, shared_ptr<ImageTreeNode>, HashPoint>, HashPoint> table;
    return calculate_optimal_2D_tree_helper(image, image_sum, 
        ImagePoint{0,0}, ImagePoint{static_cast<int>(image.size() -1), static_cast<int>(image[0].size() -1)}, table);
}

