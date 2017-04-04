import org.junit.*;
import static org.junit.Assert.*;

public class BinaryTreeTest {
	@Test
	public void test_balanced_binary_tree() {
		BTNode<Integer> zero = new BTNode<Integer>(0);
		BTNode<Integer> one = new BTNode<Integer>(1);
		BTNode<Integer> two = new BTNode<Integer>(2);
		BTNode<Integer> three = new BTNode<Integer>(3);
		BTNode<Integer> four = new BTNode<Integer>(4);
		BTNode<Integer> five = new BTNode<Integer>(5);
		BTNode<Integer> six = new BTNode<Integer>(6);
		BTNode<Integer> seven = new BTNode<Integer>(7);
		
		/* Test case 1 */
		zero.left = one;
		zero.right = two;
		one.left = three;
		one.right = four;
		two.left = five;
		two.right = six;
		five.left = seven;
		assertEquals(BinaryTree.isBlanced(zero), true);
		
		/*Test case 2*/
		two.right = null;
		seven.right = six;
		assertEquals(BinaryTree.isBlanced(zero), false);
	}
	
	@Test
	public void test_k_balanced_binary_tree() {
		BTNode<Integer> A = new BTNode<Integer>(314);
		BTNode<Integer> B = new BTNode<Integer>(6);
		BTNode<Integer> C = new BTNode<Integer>(6);
		BTNode<Integer> D = new BTNode<Integer>(28);
		BTNode<Integer> E = new BTNode<Integer>(0);
		BTNode<Integer> F = new BTNode<Integer>(561);
		BTNode<Integer> G = new BTNode<Integer>(3);
		BTNode<Integer> H = new BTNode<Integer>(17);
		BTNode<Integer> I = new BTNode<Integer>(6);
		BTNode<Integer> J = new BTNode<Integer>(2);
		BTNode<Integer> K = new BTNode<Integer>(1);
		BTNode<Integer> L = new BTNode<Integer>(461);
		BTNode<Integer> M = new BTNode<Integer>(641);
		BTNode<Integer> N = new BTNode<Integer>(257);
		BTNode<Integer> O = new BTNode<Integer>(271);
		BTNode<Integer> P = new BTNode<Integer>(28);
		A.left = B;
		A.right = I;
		B.left = C;
		B.right = F;
		C.left = D;
		C.right = O;
		F.right = G;
		G.left = H;
		I.left = J;
		I.right = O;
		J.right = K;
		K.left = L;
		K.right = N;
		L.right = M;
		O.right = P;
		assertEquals(BinaryTree.find_k_unbalanced_node(A, 3).data.intValue(), 2);	
	}
	
	@Test 
	public void test_symmetric_binary_tree() {
		/* Test case 1: Symmetric binary tree */
		BTNode<Integer> A = new BTNode<Integer>(314);
		BTNode<Integer> B = new BTNode<Integer>(6);
		BTNode<Integer> C = new BTNode<Integer>(2);
		BTNode<Integer> D = new BTNode<Integer>(3);
		BTNode<Integer> E = new BTNode<Integer>(6);
		BTNode<Integer> F = new BTNode<Integer>(2);
		BTNode<Integer> G = new BTNode<Integer>(3);
		A.left = B;
		A.right = E;
		B.right = C;
		C.right = D;
		E.left = F;
		F.left = G;
		assertEquals(BinaryTree.isSymmetric(A),true);
		
		/* Test case 2: asymmetric binary tree */
		C.data = 561;
		assertEquals(BinaryTree.isSymmetric(A),false);
		
		/* Test case 3: symmetric binary tree */
		F.data = 561;
		assertEquals(BinaryTree.isSymmetric(A),true);
		
		/* Test case 4: asymmetric binary tree */
		F.left = null;
		assertEquals(BinaryTree.isSymmetric(A),false);
	}
	
	@Test
	public void test_lock_binary_tree() {
		BTNode<String> A = new BTNode<String>("A");
		BTNode<String> B = new BTNode<String>("B");
		BTNode<String> C = new BTNode<String>("C");
		BTNode<String> D = new BTNode<String>("D");
		BTNode<String> E = new BTNode<String>("E");
		BTNode<String> F = new BTNode<String>("F");
		BTNode<String> G = new BTNode<String>("G");
		BTNode<String> H = new BTNode<String>("H");
		BTNode<String> I = new BTNode<String>("I");
		BTNode<String> J = new BTNode<String>("J");
		BTNode<String> K = new BTNode<String>("K");
		BTNode<String> L = new BTNode<String>("L");
		BTNode<String> M = new BTNode<String>("M");
		A.left = B;
		B.parent = A;
		A.right = C;
		C.parent = A;
		B.left = D;
		D.parent = B;
		B.right = E;
		E.parent = B;
		C.left = F;
		F.parent = C;		
		C.right = G;
		G.parent = C;
		D.left = H;
		H.parent = D;
		D.right = I;
		I.parent = D;
		E.left = J;
		J.parent = E;
		E.right = K;
		K.parent = E;
		G.left = L;
		L.parent = G;
		G.right = M;
		M.parent = G;
		assertEquals(L.isLock(), false);
		L.lock();
		assertEquals(L.isLock(), true);
		G.lock(); /* fails because L is locked */
		assertEquals(G.isLock(), false);
		assertEquals(F.isLock(), false);
		F.lock();
		assertEquals(F.isLock(), true);
		C.lock(); /* fails because F is locked */
		assertEquals(C.isLock(), false);
		assertEquals(E.isLock(), false);
		E.lock();
		assertEquals(E.isLock(), true);
		B.lock(); /* fails because E is locked */
		assertEquals(B.isLock(), false);
		H.lock();
		assertEquals(H.isLock(), true);
	}
	
	@Test
	public void test_morris_inorder_traversal() {
		BTNode<String> A = new BTNode<String>("A");
		BTNode<String> B = new BTNode<String>("B");
		BTNode<String> C = new BTNode<String>("C");
		BTNode<String> D = new BTNode<String>("D");
		BTNode<String> E = new BTNode<String>("E");
		BTNode<String> F = new BTNode<String>("F");
		BTNode<String> G = new BTNode<String>("G");
		BTNode<String> H = new BTNode<String>("H");
		BTNode<String> I = new BTNode<String>("I");
		BTNode<String> J = new BTNode<String>("J");
		BTNode<String> K = new BTNode<String>("K");
		BTNode<String> L = new BTNode<String>("L");
		A.left = B;
		A.right = C;
		B.left = D;
		B.right = E;
		D.left = H;
		E.left = I;
		E.right = J;
		C.left = F;
		C.right = G;
		F.right = K;
		G.right = L;
		BinaryTree.morris_inorder_traversal(A);
	}
}
