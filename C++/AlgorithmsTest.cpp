#include <cassert>
#include <vector>
#include <numeric>
#include "Algorithms.h"
#include "AlgorithmsTest.h"

void test_drawing_skylines() {
    vector<Skyline<int, int>> skylines = {{1,2,3}, {2,4,2}, {3,4,3}, {1,5,1}};
    vector<Skyline<int, int>> drawn_skylines = drawing_skylines(skylines);
    for (auto skyline : drawn_skylines) 
        skyline.print();
}

void test_count_inversions() {
    vector<int> v = {4,3,1,5,8,7,10,9};
    assert(count_inversions(v) == 5);    
}

void test_find_closest_pair_points() {
    vector<Point> v = {{1,2}, {3,4}, {2,3},{5,8}, {4,4}};
    pair<Point, Point> pair = find_closest_pair_points(v);
    pair.first.print();
    pair.second.print();
}

void test_tree_diameter() {
    shared_ptr<TreeNode> A = make_shared<TreeNode>();
    shared_ptr<TreeNode> B = make_shared<TreeNode>();
    shared_ptr<TreeNode> C = make_shared<TreeNode>();
    shared_ptr<TreeNode> D = make_shared<TreeNode>();
    shared_ptr<TreeNode> E = make_shared<TreeNode>();
    shared_ptr<TreeNode> F = make_shared<TreeNode>();
    shared_ptr<TreeNode> G = make_shared<TreeNode>();
    shared_ptr<TreeNode> H = make_shared<TreeNode>();
    shared_ptr<TreeNode> I = make_shared<TreeNode>();
    shared_ptr<TreeNode> J = make_shared<TreeNode>();
    shared_ptr<TreeNode> K = make_shared<TreeNode>();
    shared_ptr<TreeNode> M = make_shared<TreeNode>();
    shared_ptr<TreeNode> N = make_shared<TreeNode>();
    shared_ptr<TreeNode> P = make_shared<TreeNode>();
    shared_ptr<TreeNode> Q = make_shared<TreeNode>();
    shared_ptr<TreeNode> O = make_shared<TreeNode>();    
	B->addChild(C, 7);
	B->addChild(A, 14);
	B->addChild(G, 3);
	C->addChild(D, 4);
	D->addChild(E, 6);
	C->addChild(F, 3);
	G->addChild(H, 2);
	G->addChild(I, 1);
	I->addChild(J, 6);
	I->addChild(K, 4);
	K->addChild(M, 4);
	K->addChild(N, 2);
	N->addChild(O, 1);
	N->addChild(P, 2);
	N->addChild(Q, 3);
	assert(compute_diameter(B) == 31);    
}

void test_find_maximum_subarray() {
    vector<int> v = {904, 40, 523, 12, -335, -385, -124, 481, -31};
    auto range = find_maximum_subarray(v);
    cout << range.first << " " << range.second << endl;
    cout << max_subarray_sum_in_circular(v) << endl;
    cout << max_subarray_in_circular(v) << endl;
}

void test_longest_nondecreasing_subsequence() {
    vector<int> v = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9};
    vector<int> seq = longest_nondecreasing_sub_sequence(v);
    for (int i : seq) 
        cout << i << " ";
    cout << endl;
    assert(longest_nondecreasing_sub_sequence2(v) == 4);
}

void test_longest_subarray_less_equal() {
    vector<int> v = {431, -15, 639, 342, -14, 565, -924, 635, 167, -70};
    pair<int, int> p = find_longest_subarray_less_equal_k(v, 184);
    cout << p.first << " " << p.second << endl;
}

void test_largest_rectangle() {
    vector<int> v = {1,2,3,4,2,1};
    assert(calculate_largest_rectangle(v) == 8);
}

void test_max_area_submatrix() {
    vector<vector<bool>> A = {
        {true, true, true, true, false},
        {true, true, true, false, false},
        {true, true, true, true, true},
        {true, true, false, true, true},
        {true, true, true, true, true}
    };
    assert(max_rectangle_submatrix(A) == 10);
    assert(max_square_submatrix(A) == 9);
    assert(max_rectangle_submatrix2(A) == 10);
}

void test_algorithms() {
    test_drawing_skylines();
    test_count_inversions();
    test_find_closest_pair_points();
    test_tree_diameter();
    test_find_maximum_subarray();
    test_longest_nondecreasing_subsequence();
    test_longest_subarray_less_equal();
    test_largest_rectangle();
    test_max_area_submatrix();
}