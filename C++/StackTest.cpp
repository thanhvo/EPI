#include "Stack.h"
#include "StackTest.h"
#include <cassert>
#include <iostream>

using namespace std;

void test_max_stack() {
    cout << "Testing max stack" << endl;
    Stack<int> stack;
    stack.push(1);
    stack.push(3);
    stack.push(9);
    stack.push(5);
    assert(stack.max() == 9);
    assert(stack.pop() == 5);
    assert(stack.max() == 9);
    assert(stack.pop() == 9);
    assert(stack.max() == 3);    
}

void test_stack() {
    test_max_stack();
}