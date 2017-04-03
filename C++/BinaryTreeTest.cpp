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

void test_non_k_balanced_node() {
    shared_ptr<BTNode<int>> A(make_shared<BTNode<int>>(314));
    shared_ptr<BTNode<int>> B(make_shared<BTNode<int>>(6));
    shared_ptr<BTNode<int>> C(make_shared<BTNode<int>>(271));
    shared_ptr<BTNode<int>> D(make_shared<BTNode<int>>(28));
    shared_ptr<BTNode<int>> E(make_shared<BTNode<int>>(0));
    shared_ptr<BTNode<int>> F(make_shared<BTNode<int>>(561));
    shared_ptr<BTNode<int>> G(make_shared<BTNode<int>>(3));
    shared_ptr<BTNode<int>> H(make_shared<BTNode<int>>(17));
    shared_ptr<BTNode<int>> I(make_shared<BTNode<int>>(6));
    shared_ptr<BTNode<int>> J(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> K(make_shared<BTNode<int>>(1));
    shared_ptr<BTNode<int>> L(make_shared<BTNode<int>>(461));
    shared_ptr<BTNode<int>> M(make_shared<BTNode<int>>(641));
    shared_ptr<BTNode<int>> N(make_shared<BTNode<int>>(257));
    shared_ptr<BTNode<int>> O(make_shared<BTNode<int>>(271));
    shared_ptr<BTNode<int>> P(make_shared<BTNode<int>>(28));
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
    assert(find_non_k_balanced_node(A, 3)->data == 2);    
}

void test_symmetric_binary_tree() {
    /* Test case 1: a symmetric binary tree */
    shared_ptr<BTNode<int>> A(make_shared<BTNode<int>>(314));
    shared_ptr<BTNode<int>> B(make_shared<BTNode<int>>(6));
    shared_ptr<BTNode<int>> C(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> D(make_shared<BTNode<int>>(3));
    shared_ptr<BTNode<int>> E(make_shared<BTNode<int>>(6));
    shared_ptr<BTNode<int>> F(make_shared<BTNode<int>>(2));
    shared_ptr<BTNode<int>> G(make_shared<BTNode<int>>(3));
    A->left = B;
    A->right = E;
    B->right = C;
    C->right = D;
    E->left = F;
    F->left = G;
    assert(is_symmetric(A));
    
    /* Test case 2: asymmetric binary tree */
    C->data = 561;
    assert(!is_symmetric(A));
    
    /* Test case 3: a symmetric binary tree */
    F->data = 561;
    assert(is_symmetric(A));
    
    /* Test case 4: asymmetric binary tree */
    F->left = nullptr;
    assert(!is_symmetric(A));
}

void test_lock_binary_tree() {
	shared_ptr<BTNode<string>> A(make_shared<BTNode<string>>("A"));
	shared_ptr<BTNode<string>> B(make_shared<BTNode<string>>("B"));
	shared_ptr<BTNode<string>> C(make_shared<BTNode<string>>("C"));
	shared_ptr<BTNode<string>> D(make_shared<BTNode<string>>("D"));
	shared_ptr<BTNode<string>> E(make_shared<BTNode<string>>("E"));
	shared_ptr<BTNode<string>> F(make_shared<BTNode<string>>("F"));
	shared_ptr<BTNode<string>> G(make_shared<BTNode<string>>("G"));
	shared_ptr<BTNode<string>> H(make_shared<BTNode<string>>("H"));
	shared_ptr<BTNode<string>> I(make_shared<BTNode<string>>("I"));
	shared_ptr<BTNode<string>> J(make_shared<BTNode<string>>("J"));
	shared_ptr<BTNode<string>> K(make_shared<BTNode<string>>("K"));
	shared_ptr<BTNode<string>> L(make_shared<BTNode<string>>("L"));
	shared_ptr<BTNode<string>> M(make_shared<BTNode<string>>("M"));		
	A->left = B;
	B->parent = A;
	A->right = C;
	C->parent = A;
	B->left = D;
	D->parent = B;
	B->right = E;
	E->parent = B;
	C->left = F;
	F->parent = C;		
	C->right = G;
	G->parent = C;
	D->left = H;
	H->parent = D;
	D->right = I;
	I->parent = D;
	E->left = J;
	J->parent = E;
	E->right = K;
	K->parent = E;
	G->left = L;
	L->parent = G;
	G->right = M;
	M->parent = G;
	assert(L->isLock() == false);
	L->lock();
	assert(L->isLock() == true);
	G->lock(); /* fails because L is locked */
	assert(G->isLock() == false);
	assert(F->isLock() == false);
	F->lock();
	assert(F->isLock() == true);
	C->lock(); /* fails because F is locked */
	assert(C->isLock() == false);
	assert(E->isLock() == false);
	E->lock();
	assert(E->isLock() == true);
	B->lock(); /* fails because E is locked */
	assert(B->isLock() == false);
	H->lock();
	assert(H->isLock() == true);

}

void test_binary_tree() {
    test_balanced_binary_tree(); 
    test_non_k_balanced_node();
	test_lock_binary_tree();
}