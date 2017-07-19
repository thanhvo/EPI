import org.junit.*;
import static org.junit.Assert.*;

public class BinarySearchTreeTest {
	@Test
	public void test_is_BST() {
		BTNode<Integer> A = new BTNode<Integer>(19);
		BTNode<Integer> B = new BTNode<Integer>(7);
		BTNode<Integer> C = new BTNode<Integer>(3);
		BTNode<Integer> D = new BTNode<Integer>(2);
		BTNode<Integer> E = new BTNode<Integer>(5);
		BTNode<Integer> F = new BTNode<Integer>(11);
		BTNode<Integer> G = new BTNode<Integer>(17);
		BTNode<Integer> H = new BTNode<Integer>(13);
		BTNode<Integer> I = new BTNode<Integer>(43);
		BTNode<Integer> J = new BTNode<Integer>(23);
		BTNode<Integer> K = new BTNode<Integer>(37);
		BTNode<Integer> L = new BTNode<Integer>(29);
		BTNode<Integer> M = new BTNode<Integer>(31);
		BTNode<Integer> N = new BTNode<Integer>(41);
		BTNode<Integer> O = new BTNode<Integer>(47);
		BTNode<Integer> P = new BTNode<Integer>(53);
		A.left = B;
		A.right = I;
		B.left = C;
		B.right = F;
		C.left = D;
		C.right = E;
		F.right = G;
		G.left = H;
		I.left = J;
		I.right = O;
		J.right = K;
		K.left = L;
		K.right = N;
		L.right = M;
		O.right = P;
		assert(BinarySearchTree.isBST(A));
	}
	
	@Test
	public void test_successor() {
		BSTNode<Integer> A = new BSTNode<Integer>(19);
		BSTNode<Integer> B = new BSTNode<Integer>(7);
		BSTNode<Integer> C = new BSTNode<Integer>(3);
		BSTNode<Integer> D = new BSTNode<Integer>(2);
		BSTNode<Integer> E = new BSTNode<Integer>(5);
		BSTNode<Integer> F = new BSTNode<Integer>(11);
		BSTNode<Integer> G = new BSTNode<Integer>(17);
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> I = new BSTNode<Integer>(43);
		BSTNode<Integer> J = new BSTNode<Integer>(23);
		BSTNode<Integer> K = new BSTNode<Integer>(37);
		BSTNode<Integer> L = new BSTNode<Integer>(29);
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> O = new BSTNode<Integer>(47);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		A.left = B;
		B.parent = A;
		A.right = I;
		I.parent = A;
		B.left = C;
		C.parent = B;
		B.right = F;
		F.parent = B;
		C.left = D;
		D.parent = C;
		C.right = E;
		E.parent = C;
		F.right = G;
		G.parent = F;
		G.left = H;
		H.parent = G;
		I.left = J;
		J.parent = I;
		I.right = O;
		O.parent = I;
		J.right = K;
		K.parent = J;
		K.left = L;
		L.parent = K;
		K.right = N;
		N.parent = K;
		L.right = M;
		M.parent = L;
		O.right = P;
		P.parent = O;
		assertEquals(BinarySearchTree.successor(A), J);
		assertEquals(BinarySearchTree.successor(B), F);
		assertEquals(BinarySearchTree.successor(C), E);
		assertEquals(BinarySearchTree.successor(G), A);		
	}
	
	@Test
	public void test_BST_modification() throws Exception{
		BSTNode<Integer> A = new BSTNode<Integer>(19);
		BSTNode<Integer> B = new BSTNode<Integer>(7);
		BSTNode<Integer> C = new BSTNode<Integer>(3);
		BSTNode<Integer> D = new BSTNode<Integer>(2);
		BSTNode<Integer> E = new BSTNode<Integer>(5);
		BSTNode<Integer> F = new BSTNode<Integer>(11);
		BSTNode<Integer> G = new BSTNode<Integer>(17);
		BSTNode<Integer> H = new BSTNode<Integer>(13);
		BSTNode<Integer> I = new BSTNode<Integer>(43);
		BSTNode<Integer> J = new BSTNode<Integer>(23);
		BSTNode<Integer> K = new BSTNode<Integer>(37);
		BSTNode<Integer> L = new BSTNode<Integer>(29);
		BSTNode<Integer> M = new BSTNode<Integer>(31);
		BSTNode<Integer> N = new BSTNode<Integer>(41);
		BSTNode<Integer> O = new BSTNode<Integer>(47);
		BSTNode<Integer> P = new BSTNode<Integer>(53);
		A.left = B;
		A.right = I;
		B.left = C;
		B.right = F;
		C.left = D;
		C.right = E;
		F.right = G;
		G.left = H;
		I.left = J;
		I.right = O;
		J.right = K;
		K.left = L;
		K.right = N;
		L.right = M;
		O.right = P;
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
}
