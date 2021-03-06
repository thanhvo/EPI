#ifndef ALGORITHMS_H
#define ALGORITHMS_H

#include <vector>
#include <iostream>
#include <utility>
#include <cmath>
#include <tuple>
#include <limits>
#include <algorithm>
#include <memory>
#include <stack>
#include <unordered_set>
#include <unordered_map>
#include <queue>

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

pair<Point, Point> find_closest_pair_points(vector<Point> P);

class TreeNode {
    public:
        TreeNode(){}
        vector<pair<shared_ptr<TreeNode>, double>> edges;
        void addChild(shared_ptr<TreeNode> child, double edge) {
            edges.emplace_back(child, edge);
        }
};

double compute_diameter(const shared_ptr<TreeNode> &T);

template <typename T>
pair<int, int> find_maximum_subarray(const vector<T> &A) {
    // A[range.first : range.second -1] will be maximum subarray
    pair<int, int> range(0, 0);
    int min_idx = -1;
    T min_sum = 0, sum = 0;
    T max_sum = numeric_limits<T>::min();
    for (unsigned int i= 0; i < A.size(); ++i) {
        sum += A[i];
        if (sum < min_sum) {
            min_sum = sum, min_idx = i;
        }
        if (sum - min_sum > max_sum) {
            max_sum = sum - min_sum;
            range = {min_idx+1, i+1};
        }
    }
    return range;
}

// Calculate the non-circular solution
template <typename T>
T find_max_subarray(const vector<T> &A) {
    T maximum_till = 0, maximum = 0;
    for (const T &a: A) {
        maximum_till = max (a, a + maximum_till);
        maximum= max (maximum, maximum_till);
    }
    return maximum;
}

// Calculate the solution which is circular
template <typename T>
T find_circular_max_subarray(const vector<T> &A) {
    // Maximum subarray sum starts at index 0 and ends at or before index i
    vector<T> maximum_begin;
    T sum = A.front();
    maximum_begin.emplace_back(sum);
    for (unsigned int i = 1; i < A.size(); ++i) {
        sum += A[i];
        maximum_begin.emplace_back(max(maximum_begin.back(), sum));
    }
    // Maximum subarray sum starts at index i+1 and ends at the last element
    vector<T> maximum_end(A.size());
    maximum_end.back() = 0;
    sum = 0;
    for (int i = (int)A.size() - 2; i >= 0; --i) {
        sum += A[i+1];
        maximum_end[i] = max(maximum_end[i+1], sum);
    }
    // Calculate the maximum subarray which is circular 
    T circular_max = 0;
    for (unsigned int i = 0; i < A.size(); ++i) {
        circular_max = max (circular_max, maximum_begin[i] + maximum_end[i]);
    }
    return circular_max;
}

template <typename T>
T max_subarray_sum_in_circular(const vector<T> &A) {
    return max(find_max_subarray(A), find_circular_max_subarray(A));
} 

template <typename T>
T find_optimum_subarray_using_comp(const vector<T> &A, const T& (*comp)(const T&, const T&)) {
    T till = 0, overall = 0;
    for (const T &a : A) {
        till = comp(a, a + till);
        overall = comp(overall, till);
    }
    return overall;
}

template <typename T>
T max_subarray_in_circular(const vector<T> &A) {
    // Find the max in non-circular case and circular case
    return max(find_optimum_subarray_using_comp(A, max), // non-circular case
               accumulate(A.cbegin(), A.cend(), 0) - find_optimum_subarray_using_comp(A, min)); // circular case
}

template <typename T>
vector<T> longest_nondecreasing_sub_sequence(const vector<T> &A) {
    // Empty array
    if (A.empty() == true) {
        return A;
    }
    vector<int> longest_length(A.size(), 1), previous_index(A.size(), -1);
    int max_length_idx = 0;
    for (unsigned int i = 1; i < A.size(); ++i) {
        for (unsigned int j = 0; j < i; ++j) {
            if (A[i] >= A[j] && longest_length[j] + 1 > longest_length[i]) {
                longest_length[i] = longest_length[j] + 1;
                previous_index[i] = j;
            }
        }
        // Record the index where longest subsequence ends
        if (longest_length[i] > longest_length[max_length_idx]) {
            max_length_idx = i;            
        }
    }
    // Build the longest nondecreasing subsequence 
    int max_length = longest_length[max_length_idx];
    vector<T> ret(max_length);
    while (max_length > 0) {
        ret[--max_length] = A[max_length_idx];
        max_length_idx = previous_index[max_length_idx];
    }
    return ret;
}

template <typename T>
int longest_nondecreasing_sub_sequence2(const vector<T> &A) {
    vector<T> tail_values;
    for (const T &a: A) {
        auto it = upper_bound(tail_values.begin(), tail_values.end(), a);
        if (it == tail_values.end()) {
            tail_values.emplace_back(a);
        } else {
            *it = a;
        }        
    }
    return tail_values.size();
}

template <typename T>
pair<int, int> find_longest_subarray_less_equal_k(const vector<T> &A, const T &k) {
    // Build the prefix sum according to A
    vector<T> prefix_sum;
    partial_sum(A.cbegin(), A.cend(), back_inserter(prefix_sum));
    vector<T> min_prefix_sum(prefix_sum);
    for (int i = min_prefix_sum.size() - 2; i >= 0; --i) {
        min_prefix_sum[i] = min(min_prefix_sum[i], min_prefix_sum[i+1]);
    }
    pair<int, int> arr_idx(0, upper_bound(min_prefix_sum.cbegin(), min_prefix_sum.cend(), k) 
        - min_prefix_sum.cbegin() -1);
    for (int i = 0; i < (int)prefix_sum.size(); ++i) {
        int idx = upper_bound(min_prefix_sum.cbegin(), min_prefix_sum.cend(), k + prefix_sum[i])
            - min_prefix_sum.cbegin() - 1;
        if (idx - i - 1 > arr_idx.second - arr_idx.first) {
            arr_idx = {i + 1, idx};
        }
    }
    return arr_idx;
}

template <typename T>
T calculate_largest_rectangle(const vector<T> &A) {
    // Calculate T
    stack<int> S;
    vector<int> L;
    for (int i = 0; i < (int)A.size(); ++i) {
        while (!S.empty() && A[S.top()] >= A[i]) {
            S.pop();
        }
        L.emplace_back(S.empty() ? -1 : S.top());
        S.emplace(i);
    }
    // Clear stack for calculating R
    while (!S.empty()) {
        S.pop();
    }
    vector<int> R(A.size());
    for (int i = A.size() - 1; i >= 0; --i) {
        while (!S.empty() && A[S.top()] >= A[i]) {
            S.pop();
        }
        R[i] = S.empty() ? A.size() : S.top();
        S.emplace(i);
    }
    // For each A[i], find its maximum area include it
    T max_area = 0;
    for (int i = 0; i < (int)A.size(); ++i) {
        max_area = max(max_area, A[i] * (R[i] - L[i] -1));
    }
    return max_area;
}

class MaxHW {
    public:
        int h,w;        
};

int max_rectangle_submatrix(const vector<vector<bool>> &A);

int max_rectangle_submatrix2(const vector<vector<bool>> &A);

int max_square_submatrix(const vector<vector<bool>> &A);

class HashTuple {
    public:
        size_t operator() (const tuple <int, int, int> &t) const {
            return hash<int>()(get<0>(t)) ^ hash<int>()(get<1>(t)) ^ hash<int>()(get<2>(t));
        }    
};

bool match(const vector<vector<int>> &A, const vector<int> &S);

int leveshtein_distance(string A, string B);

vector<string> word_breaking(const string &s, const unordered_set<string> &dict);

int find_pretty_printing(const vector<string> &W, const int &L);

int compute_binomial_coefficients(const int &n, const int &k);

int count_combinations(const int &k, const vector<int> &score_ways);

int count_permuations(const int &k, const vector<int> &score_ways);

int number_of_ways(const int &n, const int &m);

int number_of_ways_with_obstacles(const int &n, const int &m, const vector<vector<bool>> &B);

template <typename T>
T maximize_fishing(vector<vector<T>> A) {
    for (int i = 0; i < (int)A.size(); ++i) {
        for (int j = 0; j < (int)A[i].size(); ++j) {
            A[i][j] += max(i < 1 ? 0 : A[i-1][j], j < 1 ? 0 : A[i][j-1]);
        }
    }
    return A.back().back();
}

template <typename CoinType>
CoinType pick_up_coins_helper(const vector<CoinType> &C, const int &a, const int &b, vector<vector<CoinType>> &T) {
    if (a > b) return 0;
    if (T[a][b] == -1) {
        T[a][b] = max(C[a] + min(pick_up_coins_helper(C, a+2, b, T), pick_up_coins_helper(C, a+1, b-1, T)),
                C[b] + min(pick_up_coins_helper(C, a+1, b-1, T), pick_up_coins_helper(C, a, b-2, T)));
    }
    return T[a][b];
}

template <typename CoinType>
CoinType pick_up_coins(vector<CoinType> &C) {
    vector<vector<CoinType>> T(C.size(), vector<int>(C.size(), -1));
    return pick_up_coins_helper(C, 0, C.size()-1, T);
}

class ImagePoint {
    public:
        int i, j;
        const bool operator>(const ImagePoint &that) const {
            return i > that.i || j > that.j;
        }
        // Equal function for hash
        const bool operator==(const ImagePoint& that) const {
            return i == that.i && j == that.j;
        }
        void print() const {
            cout << i << " " << j << endl;
        }
};

// Hash function for Point
class HashPoint {
    public:
        const size_t operator()(const ImagePoint &p) const {
            return hash<int>()(p.i) ^ hash<int>()(p.j);
        }
};

class ImageTreeNode {
    public:
        int node_num; // stores the number of nodes in its subtree
        ImagePoint lower_left, upper_right;
        ImageTreeNode(int __node_num, ImagePoint __lower_left, ImagePoint __upper_right): 
            node_num(__node_num), lower_left(__lower_left), upper_right(__upper_right){} 
        // Store the SW, NW, NE, and SE rectangles if color is mixed
        vector<shared_ptr<ImageTreeNode>> children;
        void print_with_prefix(string prefix) {
            cout << prefix << " (" << lower_left.i << "," << lower_left.j << ")\t(" << upper_right.i << "," << upper_right.j << ")" << endl;
            for (shared_ptr<ImageTreeNode> child: children) {
                if (child != nullptr)
                    child->print_with_prefix(prefix + "\t");
            }
        }
        void print() {
            print_with_prefix("");
        }
};

shared_ptr<ImageTreeNode> calculate_optimal_2D_tree(const vector<vector<int>> &image);

template <typename T>
T minimum_waiting_time(vector<T> service_time) {
    // Sort the query time in increasing order
    sort(service_time.begin(), service_time.end());
    T waiting = 0;
    for (unsigned int i = 0; i < service_time.size(); ++i) {
        waiting += service_time[i] * (service_time.size() - (i+1));
    }
    return waiting;
}

vector<vector<bool>> find_feasible_job_assignment(const vector<int> &T, const vector<int> &S);

vector<int> decide_load_balancing(vector<int> user_file_size, const int &server_num);

template <typename ItemType, typename CapacityType>
class TournamentTree {
    private:
        class TreeNode {
            public:
                    CapacityType cap; // leaf: remaining capacity in the box
                    // non-leaf: max remaining capacity in the subtree
                    vector<ItemType> items; // stores the items in the leaf node
        };
        // Store the complete binary tree. For tree[i], left subtree is tree[2i+1] 
        // and the right subtree is tree[2i+2].
        vector<TreeNode> tree;
        // Recursively inserts item in the tournment tree 
        bool insertHelper(const int &idx, const ItemType &item, const CapacityType &cap) {
            if (cap > tree[idx].cap) return false;
            int left = (idx << 1) + 1;
            int right = (idx << 1) + 2;
            if (left < (int)tree.size()) { // internal node
                insertHelper(tree[left].cap >= cap ? left : right, item, cap);
                tree[idx].cap = max(tree[left].cap, tree[right].cap);
            } else { // leaf node
                tree[idx].cap -= cap;
                tree[idx].items.emplace_back(item);
            }
            return true;
        }
    public:
        // n items and each box has unit_cap
        TournamentTree(int n, const CapacityType &unit_cap): // Complete tree with n leafs has 2n-1 nodes
            tree(vector<TreeNode>((n<<1)-1, {unit_cap})) {}
        bool insert(const ItemType &item, const CapacityType &item_cap) {
            return insertHelper(0, item, item_cap);
        }
        int getCapacity() {
            return tree[0].cap;
        }
};

class Symbol {
    public:
        char c;
        double prob;
        string code;
        void print() {
            cout << c << "->" + code << endl;
        }
};

class BinaryTree {
    public:
        double prob;
        shared_ptr<Symbol> s;
        BinaryTree *left, *right;
};

class Compare {
    public:
        const bool operator() (const BinaryTree* lhs, const BinaryTree* rhs) const {
            return lhs->prob > rhs->prob;
        }
};

void Huffman_encoding(vector<Symbol> &symbols);

#endif