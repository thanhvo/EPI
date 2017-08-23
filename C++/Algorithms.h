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

#endif