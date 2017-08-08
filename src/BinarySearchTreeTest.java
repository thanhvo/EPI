import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

public class BinarySearchTreeTest {
	BSTNode<Integer> A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P;
	private void create_sample_BST() {
		D = new BSTNode<Integer>(2);
		E = new BSTNode<Integer>(5);		
		H = new BSTNode<Integer>(13);
		G = new BSTNode<Integer>(17, H, null);
		F = new BSTNode<Integer>(11, null, G);		
		M = new BSTNode<Integer>(31);
		L = new BSTNode<Integer>(29, null, M);
		N = new BSTNode<Integer>(41);
		K = new BSTNode<Integer>(37, L, N);
		P = new BSTNode<Integer>(53);
		O = new BSTNode<Integer>(47, null, P);
		J = new BSTNode<Integer>(23, null, K);
		I = new BSTNode<Integer>(43, J, O);
		C = new BSTNode<Integer>(3, D, E);
		B = new BSTNode<Integer>(7, C, F);
		A = new BSTNode<Integer>(19, B, I);
	}
	
	@Test
	public void test_is_BST() {
		create_sample_BST();		
		assert(BinarySearchTree.isBST(A));
	}
	
	@Test
	public void test_successor() {
		create_sample_BST();		
		assertEquals(BinarySearchTree.successor(A), J);
		assertEquals(BinarySearchTree.successor(B), F);
		assertEquals(BinarySearchTree.successor(C), E);
		assertEquals(BinarySearchTree.successor(G), A);		
	}
	
	@Test
	public void test_BST_modification() throws Exception{
		create_sample_BST();
		
		BinarySearchTree.insert(A, 57);		
		assert(BinarySearchTree.isBST(A));
		A.printTree();
		
		BSTNode<Integer> root = BinarySearchTree.delete(A, 19);
		assert(BinarySearchTree.isBST(root));
		root.printTree();
		
		root = BinarySearchTree.insert(root, 19);
		assert(BinarySearchTree.isBST(root));
		root.printTree();
		
		root = BinarySearchTree.insert(root, 43);
		assert(BinarySearchTree.isBST(root));
		root.printTree();
	}
	
	@Test
	public void test_search_BST() {
		create_sample_BST();
		assert(BinarySearchTree.isBST(A));
		assertEquals(BinarySearchTree.find_first_equal_k_iterative(A, 19), A);
		assertEquals(BinarySearchTree.find_first_equal_k_iterative(A, 13), H);
		assertEquals(BinarySearchTree.find_first_equal_k_iterative(A, 37), K);
		assertEquals(BinarySearchTree.find_first_equal_k_iterative(A, 20), null);
		assertEquals(BinarySearchTree.find_first_equal_k_iterative(A, 7), B);
		assertEquals(BinarySearchTree.find_first_equal_k_recursive(A, 19), A);
		assertEquals(BinarySearchTree.find_first_equal_k_recursive(A, 13), H);
		assertEquals(BinarySearchTree.find_first_equal_k_recursive(A, 37), K);
		assertEquals(BinarySearchTree.find_first_equal_k_recursive(A, 20), null);
		assertEquals(BinarySearchTree.find_first_equal_k_recursive(A, 7), B);
		BSTNode<Integer> Q = new BSTNode<Integer>(7);
		E.right = Q;
		Q.parent = E;
		assert(BinarySearchTree.isBST(A));
		assertEquals(BinarySearchTree.find_first_equal_k_iterative(A, 7), Q);
		assertEquals(BinarySearchTree.find_first_equal_k_recursive(A, 7), Q);		
	}
	
	@Test
	public void test_first_min_search() {
		BSTNode<Integer> A = new BSTNode<Integer>(7);
		BSTNode<Integer> B = new BSTNode<Integer>(11);
		BSTNode<Integer> C = new BSTNode<Integer>(5, A, B);
		BSTNode<Integer> D = new BSTNode<Integer>(17);
		BSTNode<Integer> E = new BSTNode<Integer>(23);
		BSTNode<Integer> F = new BSTNode<Integer>(19, E, null);
		BSTNode<Integer> G = new BSTNode<Integer>(13, D, F);
		BSTNode<Integer> I = new BSTNode<Integer>(3, null, C);
		BSTNode<Integer> J = new BSTNode<Integer>(2, I, G);
		assertEquals(BinarySearchTree.min_first_search(J, 11), B);
		assertEquals(BinarySearchTree.min_first_search(J, 23), E);
		assertEquals(BinarySearchTree.min_first_search(J, 10), null);
	}
	
	@Test
	public void test_first_larger() {
		create_sample_BST();
		assertEquals(BinarySearchTree.find_first_larger_than_k_with_k_exist(A, 23), L);
		assertEquals(BinarySearchTree.find_first_larger_than_k_with_k_exist(A, 32), null);
	}
	
	@Test
	public void test_construct_BST() {
		Integer[] A = {1, 3, 5, 6, 7, 9, 12, 15, 25, 34, 35, 45, 67};
		BSTNode<Integer> root = BinarySearchTree.construct_BST(A);
		root.printTree();
	}
	
	@Test
	public void test_build_BST_from_linked_list() {
		Node<Integer> A = new Node<Integer>(1);
		Node<Integer> B = new Node<Integer>(3);
		Node<Integer> C = new Node<Integer>(5);
		Node<Integer> D = new Node<Integer>(6);
		Node<Integer> E = new Node<Integer>(7);
		Node<Integer> F = new Node<Integer>(9);
		Node<Integer> G = new Node<Integer>(12);
		Node<Integer> H = new Node<Integer>(15);
		Node<Integer> I = new Node<Integer>(25);
		Node<Integer> J = new Node<Integer>(34);
		Node<Integer> K = new Node<Integer>(35);
		Node<Integer> L = new Node<Integer>(45);
		A.next = B;
		B.next = C;
		C.next = D;
		D.next = E;
		E.next = F;
		F.next = G;
		G.next = H;
		H.next = I;
		I.next = J;
		J.next = K;
		K.next = L;
		BSTNode<Integer> root = BinarySearchTree.build_BST_from_sorted_doubly_list(A);
		assert(BinarySearchTree.isBST(root));
		root.printTree();
	}
	
	@Test
	public void test_convert_BST_to_doubly_linked_list() {
		create_sample_BST();
		assert(BinarySearchTree.isBST(A));
		Node<Integer> first = BinarySearchTree.convert_BST_to_doubly_linked_list(A);
		LinkedListT.print(first);
	}
	
	@Test
	public void test_merge_BSTs() {
		create_sample_BST();
		assert(BinarySearchTree.isBST(A));
		
		BSTNode<Integer> H1 = new BSTNode<Integer>(1);
		BSTNode<Integer> I1 = new BSTNode<Integer>(6);
		BSTNode<Integer> J1 = new BSTNode<Integer>(10);
		BSTNode<Integer> K1 = new BSTNode<Integer>(14);
		BSTNode<Integer> L1 = new BSTNode<Integer>(22);
		BSTNode<Integer> M1 = new BSTNode<Integer>(26);
		BSTNode<Integer> D1 = new BSTNode<Integer>(4, H1, I1);
		BSTNode<Integer> E1 = new BSTNode<Integer>(12, J1, K1);
		BSTNode<Integer> F1 = new BSTNode<Integer>(18);
		BSTNode<Integer> G1 = new BSTNode<Integer>(24, L1, M1);
		BSTNode<Integer> B1 = new BSTNode<Integer>(8, D1, E1);
		BSTNode<Integer> C1 = new BSTNode<Integer>(20, F1, G1);
		BSTNode<Integer> A1 = new BSTNode<Integer>(16, B1, C1);
		assert(BinarySearchTree.isBST(A1));
		
		BSTNode<Integer> root = BinarySearchTree.merge_BSTs(A, A1);
		assert(BinarySearchTree.isBST(root));
		root.printTree();
	}
	
	@Test
	public void test_find_k_largest_elements() {
		System.out.println("Find k largest elements");
		create_sample_BST();
		assert(BinarySearchTree.isBST(A));
		List<Integer> list = BinarySearchTree.find_k_largest_elements(A, 3);
		for (Integer val: list)
			System.out.print(val + " ");
		System.out.println();
	}
	
	@Test
	public void test_build_BST_from_pre_order() {
		ArrayList<Integer> pre_order = new ArrayList<>(Arrays.asList(16, 8, 4, 1, 6, 12, 10, 14, 20, 18, 24, 22, 26));
		BSTNode<Integer> root = BinarySearchTree.build_BST_from_pre_order(pre_order);
		assert(BinarySearchTree.isBST(root));
		root.printTree();
		BSTNode<Integer> root2 = BinarySearchTree.rebuild_BST_from_preorder2(pre_order);
		assert(BinarySearchTree.isBST(root2));
		root2.printTree();
	}
	
	@Test
	public void test_LCA() {
		create_sample_BST();
		assert(BinarySearchTree.isBST(A));
		assertEquals(BinarySearchTree.LCA(M, N), K);
		assertEquals(BinarySearchTree.LCA(M, P), I);
		assertEquals(BinarySearchTree.LCA(E, P), A);
	}
	
	@Test
	public void test_ancestor_descendant() {
		create_sample_BST();
		assert(BinarySearchTree.isBST(A));
		assert(BinarySearchTree.is_r_s_descendant_ancestor_of_m(A, K, J));
		assert(!BinarySearchTree.is_r_s_descendant_ancestor_of_m(I, P, J));
		assert(!BinarySearchTree.is_r_s_descendant_ancestor_of_m(C, M, E));
	}
	
	@Test
	public void test_range_query() {
		System.out.println("Range Querry");
		create_sample_BST();
		assert(BinarySearchTree.isBST(A));
		
		for (BSTNode<Integer> node : BinarySearchTree.range_query(A, 10, 20)) {
			System.out.print(node.data + " " );
		}
		System.out.println();
		
		for (BSTNode<Integer> node : BinarySearchTree.range_query(A, 5, 35)) {
			System.out.print(node.data + " " );
		}
		System.out.println();
	}
	
	@Test
	public void test_find_min_distance_sorted_arrays() {		
		System.out.println("Find min distance");
		ArrayList<Integer> arr1 = new ArrayList<Integer>(Arrays.asList(1,5,9,13));
		ArrayList<Integer> arr2 = new ArrayList<Integer>(Arrays.asList(2,10,16,22));
		ArrayList<Integer> arr3 = new ArrayList<Integer>(Arrays.asList(6,26,46,66));
		ArrayList<Integer> arr4 = new ArrayList<Integer>(Arrays.asList(7,17,27,37));
		ArrayList<ArrayList<Integer>> arrs = new ArrayList<ArrayList<Integer>>(Arrays.asList(arr1, arr2, arr3, arr4));
		assertEquals(BinarySearchTree.find_min_distance_sorted_arrays(arrs), 4);		
	}
}
