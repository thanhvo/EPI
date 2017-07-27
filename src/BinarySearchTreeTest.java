import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

public class BinarySearchTreeTest {
	@Test
	public void test_is_BST() {
		BSTNode<Integer> D = new BSTNode<Integer>(2);
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);		
		assert(BinarySearchTree.isBST(A));
	}
	
	@Test
	public void test_successor() {
		BSTNode<Integer> D = new BSTNode<Integer>(2);
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);
		
		assertEquals(BinarySearchTree.successor(A), J);
		assertEquals(BinarySearchTree.successor(B), F);
		assertEquals(BinarySearchTree.successor(C), E);
		assertEquals(BinarySearchTree.successor(G), A);		
	}
	
	@Test
	public void test_BST_modification() throws Exception{
		BSTNode<Integer> D = new BSTNode<Integer>(2);
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);
		
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
		BSTNode<Integer> D = new BSTNode<Integer>(2);		
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);
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
		BSTNode<Integer> D = new BSTNode<Integer>(2);		
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);
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
		BSTNode<Integer> D = new BSTNode<Integer>(2);		
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);
		assert(BinarySearchTree.isBST(A));
		Node<Integer> first = BinarySearchTree.convert_BST_to_doubly_linked_list(A);
		LinkedListT.print(first);
	}
	
	@Test
	public void test_merge_BSTs() {
		BSTNode<Integer> D = new BSTNode<Integer>(2);		
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);
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
		BSTNode<Integer> D = new BSTNode<Integer>(2);		
		BSTNode<Integer> E = new BSTNode<Integer>(5);		
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> G = new BSTNode<Integer>(17, H, null);
		BSTNode<Integer> F = new BSTNode<Integer>(11, null, G);		
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> L = new BSTNode<Integer>(29, null, M);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> K = new BSTNode<Integer>(37, L, N);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		BSTNode<Integer> O = new BSTNode<Integer>(47, null, P);
		BSTNode<Integer> J = new BSTNode<Integer>(23, null, K);
		BSTNode<Integer> I = new BSTNode<Integer>(43, J, O);
		BSTNode<Integer> C = new BSTNode<Integer>(3, D, E);
		BSTNode<Integer> B = new BSTNode<Integer>(7, C, F);
		BSTNode<Integer> A = new BSTNode<Integer>(19, B, I);
		assert(BinarySearchTree.isBST(A));
		List<Integer> list = BinarySearchTree.find_k_largest_elements(A, 3);
		for (Integer val: list)
			System.out.print(val + " ");
		System.out.println();
	}
}
