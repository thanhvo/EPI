#include <memory>
#include <cassert>
#include "BTNode.h"
#include "BinaryTreeTest.h"

using namespace std;

void test_balanced_binary_tree() {
    shared_ptr<BTNode<int>> zero(make_shared<BTNode<int>>(0));
    shared_ptr<BTNode<int>> one(make_shared<BTNode<int>>(1));
    shared_ptr<BTNode<int>> two(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> three(make_shared<BTNode<int>>(3));
    shared_ptr<BTNode<int>> four(make_shared<BTNode<int>>(4));
    shared_ptr<BTNode<int>> five(make_shared<BTNode<int>>(5));
    shared_ptr<BTNode<int>> six(make_shared<BTNode<int>>(6));
    shared_ptr<BTNode<int>> seven(make_shared<BTNode<int>>(7));
    
    /* Test case 1 */
    zero->left = one;
    zero->right = two;
    one->left = three;
    one->right = four;
    two->left = five;
    two->right = six;
    five->left = seven;
    assert(is_balanced_binary_tree(zero));
    
    /*Test case 2 */
    two->right = nullptr;
    seven->right = six;
    assert(is_balanced_binary_tree(zero)==false);
}

void test_binary_tree() {
    test_balanced_binary_tree();    
}