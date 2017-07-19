#include <memory>
#include <cassert>
#include "BinarySearchTreeTest.h"
#include "BTNode.h"
#include "BinarySearchTree.h"

using namespace std;

void test_is_BST() {
    shared_ptr<BTNode<int>> D(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> E(make_shared<BTNode<int>>(5));    
    shared_ptr<BTNode<int>> H(make_shared<BTNode<int>>(13));    
    shared_ptr<BTNode<int>> M(make_shared<BTNode<int>>(31));
    shared_ptr<BTNode<int>> N(make_shared<BTNode<int>>(41));    
    shared_ptr<BTNode<int>> P(make_shared<BTNode<int>>(53));
    shared_ptr<BTNode<int>> O(make_shared<BTNode<int>>(47, nullptr, P));
    shared_ptr<BTNode<int>> L(make_shared<BTNode<int>>(29, nullptr, M));
    shared_ptr<BTNode<int>> K(make_shared<BTNode<int>>(37, L, N));
    shared_ptr<BTNode<int>> J(make_shared<BTNode<int>>(23, nullptr, K));
    shared_ptr<BTNode<int>> I(make_shared<BTNode<int>>(43, J, O));
    shared_ptr<BTNode<int>> G(make_shared<BTNode<int>>(17, H, nullptr));
    shared_ptr<BTNode<int>> F(make_shared<BTNode<int>>(11, nullptr, G));
    shared_ptr<BTNode<int>> C(make_shared<BTNode<int>>(3, D, E));
    shared_ptr<BTNode<int>> B(make_shared<BTNode<int>>(7, C, F));    
    shared_ptr<BTNode<int>> A(make_shared<BTNode<int>>(19, B, I));
    assert(is_BST(A));
    assert(is_BST_inorder_traversal(A));
    assert(is_BST_queue(A));
    
    /* Modify the tree */
    G->right = H;
    G->left = nullptr;
    assert(!is_BST(A));
}

void test_find_successor_BST() {    
    shared_ptr<BSTNode<int>> D(make_shared<BSTNode<int>>(2));
    shared_ptr<BSTNode<int>> E(make_shared<BSTNode<int>>(5));    
    shared_ptr<BSTNode<int>> H(make_shared<BSTNode<int>>(13));    
    shared_ptr<BSTNode<int>> M(make_shared<BSTNode<int>>(31));
    shared_ptr<BSTNode<int>> N(make_shared<BSTNode<int>>(41));    
    shared_ptr<BSTNode<int>> P(make_shared<BSTNode<int>>(53));
    shared_ptr<BSTNode<int>> O(make_shared<BSTNode<int>>(47, nullptr, P));
    shared_ptr<BSTNode<int>> L(make_shared<BSTNode<int>>(29, nullptr, M));
    shared_ptr<BSTNode<int>> K(make_shared<BSTNode<int>>(37, L, N));
    shared_ptr<BSTNode<int>> J(make_shared<BSTNode<int>>(23, nullptr, K));
    shared_ptr<BSTNode<int>> I(make_shared<BSTNode<int>>(43, J, O));
    shared_ptr<BSTNode<int>> G(make_shared<BSTNode<int>>(17, H, nullptr));
    shared_ptr<BSTNode<int>> F(make_shared<BSTNode<int>>(11, nullptr, G));
    shared_ptr<BSTNode<int>> C(make_shared<BSTNode<int>>(3, D, E));
    shared_ptr<BSTNode<int>> B(make_shared<BSTNode<int>>(7, C, F));    
    shared_ptr<BSTNode<int>> A(make_shared<BSTNode<int>>(19, B, I));
    assert(find_successor_BST(A)->data == J->data);
    assert(find_successor_BST(D)->data == C->data);
    assert(find_successor_BST(P) == nullptr);
}

void test_modify_binary_search_tree() {
    shared_ptr<BSTNode<int>> D(make_shared<BSTNode<int>>(2));
    shared_ptr<BSTNode<int>> E(make_shared<BSTNode<int>>(5));    
    shared_ptr<BSTNode<int>> H(make_shared<BSTNode<int>>(13));    
    shared_ptr<BSTNode<int>> M(make_shared<BSTNode<int>>(31));
    shared_ptr<BSTNode<int>> N(make_shared<BSTNode<int>>(41));    
    shared_ptr<BSTNode<int>> P(make_shared<BSTNode<int>>(53));
    shared_ptr<BSTNode<int>> O(make_shared<BSTNode<int>>(47, nullptr, P));
    shared_ptr<BSTNode<int>> L(make_shared<BSTNode<int>>(29, nullptr, M));
    shared_ptr<BSTNode<int>> K(make_shared<BSTNode<int>>(37, L, N));
    shared_ptr<BSTNode<int>> J(make_shared<BSTNode<int>>(23, nullptr, K));
    shared_ptr<BSTNode<int>> I(make_shared<BSTNode<int>>(43, J, O));
    shared_ptr<BSTNode<int>> G(make_shared<BSTNode<int>>(17, H, nullptr));
    shared_ptr<BSTNode<int>> F(make_shared<BSTNode<int>>(11, nullptr, G));
    shared_ptr<BSTNode<int>> C(make_shared<BSTNode<int>>(3, D, E));
    shared_ptr<BSTNode<int>> B(make_shared<BSTNode<int>>(7, C, F));    
    shared_ptr<BSTNode<int>> A(make_shared<BSTNode<int>>(19, B, I));
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    BinarySearchTree<int> BST(A);
    BST.insert(57);
    assert(is_BST(static_pointer_cast<BTNode<int>>(BST.getRoot())));
    BST.printTree();
    BST.erase(19);
    assert(is_BST(static_pointer_cast<BTNode<int>>(BST.getRoot())));
    BST.printTree();
}

void test_binary_search_tree() {
    test_is_BST();
    test_find_successor_BST();
    test_modify_binary_search_tree();
}