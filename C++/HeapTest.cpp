#include "HeapTest.h"
#include "HeapApp.h"
#include <vector>
#include <iostream>
#include <cassert>

using namespace std;

void test_merge_arrays() {
    vector<vector<int>> v;
    v.push_back({1,5,6,9,11});
    v.push_back({23,45,56,79,192});
    v.push_back({2,4,8,10,12});
    v.push_back({15,20,35, 40});
    vector<int> merged = merge_arrays(v);
    for (int val : merged) {
        cout << val << " ";
    }
    cout << endl;
}

void test_sort_k_increasing_decreasing_array() {
    vector<int> a = {57,131,493,294,221,339,418,452,442,190};
    vector<int> b = sort_k_increasing_descreasing_array(a);
    for (int val: b) {
        cout << val << " ";
    }
    cout << endl;
}

void test_heap_stack_queue() {
    HeapStack<int> stack;
    stack.push(1);
    stack.push(2);
    stack.push(3);
    assert(stack.top() == 3);
    stack.pop();
    assert(stack.top() == 2);
    stack.pop();
    assert(stack.top() == 1);
    stack.pop();
    assert(stack.empty());
    
    HeapQueue<int> queue;
    queue.push(1);
    queue.push(2);
    queue.push(3);
    assert(queue.front() == 1);
    queue.pop();
    assert(queue.front() == 2);
    queue.pop();
    assert(queue.front() == 3);
    queue.pop();
    assert(queue.empty());
}

void test_k_closest_stars() {
    string stringvalues ="0,0.0,0.0,0.0\n1,0.0,0.0,1.0\n2,1.0,0.0,0.0\n3,0.0,1.0,0.0\n4,0.0,1.0,0.0\n5,3.0,1.0,0.0\n6,3.0,1.0,2.0\n7,8.0,1.0,9.0\n8,2.0,1.0,0.0\n";  
    istringstream ss(stringvalues);
    vector<Star> closest_stars = find_closest_k_stars(ss, 4);
    for (Star star : closest_stars) {
        cout << star.ID << " ";
    }
    cout << endl;
}

void test_kth_largest_stream() {
    cout << "kth largerst stream" << endl;
    string stringvalues = "10 5 3 2 1 6 9 11 23 17 99";
    istringstream ss(stringvalues);
    find_kth_largest_stream<int>(ss, 3);
}

void test_approximate_sort() {
    cout << "Approximate sort: " << endl;
    string stringvalues = "10 5 3 7 23 33 99 80 100 60";
    istringstream ss(stringvalues);
    approximate_sort<int>(ss, 4);    
}

void test_k_closest_median() {
    cout << "k closest median" << endl;
    vector<int> v = {7, 14, 10, 12, 2, 11, 29, 3, 4};
    vector<int> u = find_k_closest_to_median(v, 5);
    for (int i : u) {
        cout << i << endl;
    }
}

void test_online_median() {
    cout << "Online median" << endl;
    string values = "1 2 3 5 10 9 8 7";
    istringstream ss(values);
    online_median<int>(ss);
}

void test_generate_first_k() {
    cout << "Generate first k" << endl;
    vector<Num> v = generate_first_k(10);
    for (Num n: v) {
        cout << n.val_ << endl;
    }
}

void test_compare_kth_largest() {
    vector<int> v = {16, 14, 10, 8, 7, 9, 3, 2, 4, 1};
    assert(compare_k_th_largest_heap(v, 3, 9) == 1);
    assert(compare_k_th_largest_heap(v, 3, 10) == 0);
    assert(compare_k_th_largest_heap(v, 3, 11) == -1);
}

void test_heap() {
    test_merge_arrays();
    test_sort_k_increasing_decreasing_array();
    test_heap_stack_queue();
    test_k_closest_stars();
    test_kth_largest_stream();
    test_approximate_sort();
    test_k_closest_median();
    test_online_median();
    test_generate_first_k();
    test_compare_kth_largest();
}