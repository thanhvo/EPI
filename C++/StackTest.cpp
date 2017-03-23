#include "Stack.h"
#include "StackTest.h"
#include "StackApp.h"
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

void test_eval_RPN() {
    cout << "Testing reverse polish notion evaluation" << endl;
    assert(eval_RPN("1,2,+") == 3);
    assert(eval_RPN("1,2,+,3,+,2,-") == 4);
    //cout << eval_RPN("1,2,+,3,+,2,-,3") << endl; /* Invalid input */
}

void test_stack() {
    test_max_stack();
    test_eval_RPN();
}