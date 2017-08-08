#include <memory>
#include <cassert>
#include "BinarySearchTreeTest.h"
#include "BinarySearchTree.h"
#include "LinkedList.h"

using namespace std;

shared_ptr<BSTNode<int>> A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P;

void create_sample_BST() {
    D = make_shared<BSTNode<int>>(2);
    E = make_shared<BSTNode<int>>(5);    
    C = make_shared<BSTNode<int>>(3, D, E);
    D->parent = C;
    E->parent = C;
    H = make_shared<BSTNode<int>>(13);    
    M = make_shared<BSTNode<int>>(31);
    N = make_shared<BSTNode<int>>(41);    
    P = make_shared<BSTNode<int>>(53);
    O = make_shared<BSTNode<int>>(47, nullptr, P);
    P->parent = O;
    L = make_shared<BSTNode<int>>(29, nullptr, M);
    M->parent = L;
    K = make_shared<BSTNode<int>>(37, L, N);
    L->parent = K;
    N->parent = K;
    J = make_shared<BSTNode<int>>(23, nullptr, K);
    K->parent = J;
    I = make_shared<BSTNode<int>>(43, J, O);
    J->parent = I;
    O->parent = I;
    G = make_shared<BSTNode<int>>(17, H, nullptr);
    H->parent = G;
    F = make_shared<BSTNode<int>>(11, nullptr, G);
    G->parent = F;
    B = make_shared<BSTNode<int>>(7, C, F);    
    C->parent = B;
    F->parent = B;
    A = make_shared<BSTNode<int>>(19, B, I); 
    B->parent = A;
    I->parent = A;
}

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
    cout << "Test find successor" << endl;
    create_sample_BST();
    assert(find_successor_BST(A) == J);
    assert(find_successor_BST(D) == C);
    assert(find_successor_BST(P) == nullptr);
}

void test_modify_binary_search_tree() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    BinarySearchTree<int> BST(A);
    BST.insert(57);
    assert(is_BST(static_pointer_cast<BTNode<int>>(BST.getRoot())));
    BST.printTree();
    BST.erase(19);
    assert(is_BST(static_pointer_cast<BTNode<int>>(BST.getRoot())));
    BST.printTree();
}

void test_find_first_equal_k() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    BinarySearchTree<int> BST(A);    
    BST.find_first_equal_k_iterative(23)->printTree();
    BST.find_first_equal_k_recursive(23)->printTree();
    BST.find_first_equal_k_iterative(7)->printTree();
    BST.find_first_equal_k_recursive(7)->printTree();
    shared_ptr<BSTNode<int>> Q(make_shared<BSTNode<int>>(7));
    E->right = Q;
    Q->parent = E;
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    BST.find_first_equal_k_iterative(7)->printTree();
    BST.find_first_equal_k_recursive(7)->printTree();
}

void test_search_min_first_BST() {
    shared_ptr<BSTNode<int>> A(make_shared<BSTNode<int>>(7));
    shared_ptr<BSTNode<int>> B(make_shared<BSTNode<int>>(11));
    shared_ptr<BSTNode<int>> C(make_shared<BSTNode<int>>(5, A, B));
    shared_ptr<BSTNode<int>> D(make_shared<BSTNode<int>>(17));
    shared_ptr<BSTNode<int>> E(make_shared<BSTNode<int>>(23));
	shared_ptr<BSTNode<int>> F(make_shared<BSTNode<int>>(19, E, nullptr));
    shared_ptr<BSTNode<int>> G(make_shared<BSTNode<int>>(13, D, F));
	shared_ptr<BSTNode<int>> H(make_shared<BSTNode<int>>(3, nullptr, C));
	shared_ptr<BSTNode<int>> I(make_shared<BSTNode<int>>(2, H, G));
	assert(search_min_first_BST(I, 11));
	assert(search_min_first_BST(I, 23));
	assert(!search_min_first_BST(I, 10));
}

void test_find_first_larger_than() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    assert(find_first_larger_than_k_with_k_exist(A, 23) == L);
    assert(find_first_larger_than_k_with_k_exist(A, 32) == nullptr);
}

void test_build_BST() {
    vector<int> v = {1, 3, 5, 6, 7, 9, 12, 15, 25, 34, 35, 45, 67};
    shared_ptr<BSTNode<int>> root = build_BST(v);
    assert(is_BST(static_pointer_cast<BTNode<int>>(root)));
    root->printTree();
}

void test_build_BST_from_sorted_doubly_list() {
    shared_ptr<node_t<int>> A(make_shared<node_t<int>>(1));
    shared_ptr<node_t<int>> B(make_shared<node_t<int>>(3));
    shared_ptr<node_t<int>> C(make_shared<node_t<int>>(5));
    shared_ptr<node_t<int>> D(make_shared<node_t<int>>(6));
    shared_ptr<node_t<int>> E(make_shared<node_t<int>>(7));
    shared_ptr<node_t<int>> F(make_shared<node_t<int>>(9));
    shared_ptr<node_t<int>> G(make_shared<node_t<int>>(12));
    shared_ptr<node_t<int>> H(make_shared<node_t<int>>(15));
    shared_ptr<node_t<int>> I(make_shared<node_t<int>>(25));
    shared_ptr<node_t<int>> J(make_shared<node_t<int>>(34));
    shared_ptr<node_t<int>> K(make_shared<node_t<int>>(35));
    shared_ptr<node_t<int>> L(make_shared<node_t<int>>(45));
    A->next = B;
    B->next = C;
    C->next = D;
    D->next = E;
    E->next = F;
    F->next = G;
    G->next = H;
    H->next = I;
    I->next = J;
    J->next = K;
    K->next = L;
    
    shared_ptr<BSTNode<int>> root = build_BST_from_sorted_doubly_list(A, count_len(A));
    assert(is_BST(static_pointer_cast<BTNode<int>>(root)));
    root->printTree();
}

void test_BST_to_doubly_list() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    A->printTree();
    shared_ptr<BSTNode<int>> head = BST_to_doubly_list(A);
    print_as_doubly_list(head);
}

void test_merge_BSTs() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    
    shared_ptr<BSTNode<int>> H1(make_shared<BSTNode<int>>(1));
    shared_ptr<BSTNode<int>> I1(make_shared<BSTNode<int>>(6));
    shared_ptr<BSTNode<int>> J1(make_shared<BSTNode<int>>(10));
    shared_ptr<BSTNode<int>> K1(make_shared<BSTNode<int>>(14));
    shared_ptr<BSTNode<int>> L1(make_shared<BSTNode<int>>(22));
    shared_ptr<BSTNode<int>> M1(make_shared<BSTNode<int>>(26));
    shared_ptr<BSTNode<int>> D1(make_shared<BSTNode<int>>(4, H1, I1));
    shared_ptr<BSTNode<int>> E1(make_shared<BSTNode<int>>(12, J1, K1));
    shared_ptr<BSTNode<int>> F1(make_shared<BSTNode<int>>(18));
    shared_ptr<BSTNode<int>> G1(make_shared<BSTNode<int>>(24, L1, M1));
    shared_ptr<BSTNode<int>> B1(make_shared<BSTNode<int>>(8, D1, E1));
    shared_ptr<BSTNode<int>> C1(make_shared<BSTNode<int>>(20, F1, G1));
    shared_ptr<BSTNode<int>> A1(make_shared<BSTNode<int>>(16, B1, C1));
    assert(is_BST(static_pointer_cast<BTNode<int>>(A1)));
    
    shared_ptr<BSTNode<int>> root = merge_BSTs(A, A1);
    assert(is_BST(static_pointer_cast<BTNode<int>>(root)));
    root->printTree();
}

void test_find_k_largest_in_BST() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    vector<int> k_elements = find_k_largest_in_BST(A, 3);
    for (int a: k_elements) {
        cout << a << " ";
    }
    cout << endl;
}

void test_rebuild_BST_from_preorder() {
    vector<int> preorder = {16, 8, 4, 1, 6, 12, 10, 14, 20, 18, 24, 22, 26};
    shared_ptr<BSTNode<int>> root = rebuild_BST_preorder(preorder);
    assert(is_BST(static_pointer_cast<BTNode<int>>(root)));
    root->printTree();
    shared_ptr<BSTNode<int>> root2 = rebuild_BST_from_preorder2(preorder);
    assert(is_BST(static_pointer_cast<BTNode<int>>(root2)));
    root2->printTree();
}

void test_find_LCA() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    assert(find_LCA(A, M, N)->data == 37);
    assert(find_LCA(A, M, P)->data == 43);
    assert(find_LCA(A, E, P)->data == 19);
}

void test_ancestor_descendant() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    assert(is_r_s_descendant_ancestor_of_m(A, K, J));
    assert(!is_r_s_descendant_ancestor_of_m(I, P, J));
    assert(!is_r_s_descendant_ancestor_of_m(C, M, E));
}

void test_range_query_on_BST() {
    create_sample_BST();
    assert(is_BST(static_pointer_cast<BTNode<int>>(A)));
    for (auto node: range_query_on_BST(A, 10, 30)) {
        cout << node->data << " ";
    }
    cout << endl;
}

void test_find_min_distance_sorted_arrays() {
    vector<vector<int>> v = {{1,5,9,13}, {2,10,16,22},{6,26,46,66},{7,17,27,37}};
    cout << find_min_distance_sorted_arrays(v) << endl;
}

void test_binary_search_tree() {
    test_is_BST();
    test_find_successor_BST();
    test_modify_binary_search_tree();
    test_find_first_equal_k();
    test_search_min_first_BST();
    test_find_first_larger_than();
    test_build_BST();
    test_build_BST_from_sorted_doubly_list();
    test_BST_to_doubly_list();
    test_merge_BSTs();
    test_find_k_largest_in_BST();
    test_rebuild_BST_from_preorder();
    test_find_LCA();
    test_ancestor_descendant();
    test_range_query_on_BST();
    test_find_min_distance_sorted_arrays();
}
