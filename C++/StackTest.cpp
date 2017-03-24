#include <cassert>
#include <iostream>
#include <memory>
#include "Stack.h"
#include "StackTest.h"
#include "StackApp.h"
#include "BSTNode.h"

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

void test_print_BST() {
    shared_ptr<BSTNode<int>> n1(make_shared<BSTNode<int>>(37));
    shared_ptr<BSTNode<int>> n2(make_shared<BSTNode<int>>(29));
    shared_ptr<BSTNode<int>> n3(make_shared<BSTNode<int>>(43));
    shared_ptr<BSTNode<int>> n4(make_shared<BSTNode<int>>(23));
    shared_ptr<BSTNode<int>> n5(make_shared<BSTNode<int>>(31));
    shared_ptr<BSTNode<int>> n6(make_shared<BSTNode<int>>(41));
    shared_ptr<BSTNode<int>> n7(make_shared<BSTNode<int>>(47));
    shared_ptr<BSTNode<int>> n8(make_shared<BSTNode<int>>(53));
    n1->left = n2;
    n1->right = n3;
    n2->left = n4;
    n2->right = n5;
    n3->left = n6;
    n3->right = n7;
    n7->right = n8;
    print_BST_in_sorted_order(n1);
}

void test_stack() {
    test_max_stack();
    test_eval_RPN();
    test_print_BST();
    move_tower_hanoi(4);
}