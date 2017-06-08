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

void test_heap() {
    test_merge_arrays();
    test_sort_k_increasing_decreasing_array();
}