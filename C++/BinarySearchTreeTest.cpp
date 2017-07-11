#include <memory>
#include <cassert>
#include "BinarySearchTreeTest.h"
#include "BTNode.h"
#include "BinarySearchTree.h"

using namespace std;

void test_is_BST() {
    shared_ptr<BTNode<int>> A(make_shared<BTNode<int>>(19));
    shared_ptr<BTNode<int>> B(make_shared<BTNode<int>>(7));
    shared_ptr<BTNode<int>> C(make_shared<BTNode<int>>(3));
    shared_ptr<BTNode<int>> D(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> E(make_shared<BTNode<int>>(5));
    shared_ptr<BTNode<int>> F(make_shared<BTNode<int>>(11));
    shared_ptr<BTNode<int>> G(make_shared<BTNode<int>>(17));
    shared_ptr<BTNode<int>> H(make_shared<BTNode<int>>(13));
    shared_ptr<BTNode<int>> I(make_shared<BTNode<int>>(43));
    shared_ptr<BTNode<int>> J(make_shared<BTNode<int>>(23));
    shared_ptr<BTNode<int>> K(make_shared<BTNode<int>>(37));
    shared_ptr<BTNode<int>> L(make_shared<BTNode<int>>(29));
    shared_ptr<BTNode<int>> M(make_shared<BTNode<int>>(31));
    shared_ptr<BTNode<int>> N(make_shared<BTNode<int>>(41));
    shared_ptr<BTNode<int>> O(make_shared<BTNode<int>>(47));
    shared_ptr<BTNode<int>> P(make_shared<BTNode<int>>(53));
    A->left = B;
    A->right = I;
    B->left = C;
    B->right = F;
    C->left = D;
    C->right = E;
    F->right = G;
    G->left = H;
    I->left = J;
    I->right = O;
    J->right = K;
    K->left = L;
    K->right = N;
    L->right = M;
    O->right = P;
    assert(is_BST(A));
    
    /* Modify the tree */
    G->right = H;
    G->left = nullptr;
    assert(!is_BST(A));
}

void test_binary_search_tree() {
    test_is_BST();
}