import org.junit.*;
import static org.junit.Assert.*;

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
}
