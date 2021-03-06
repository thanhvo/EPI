import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class BinaryTreeTest {
	BTNode<Integer> A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P;
	
	private void create_binary_tree() {
		A = new BTNode<Integer>(314);
		B = new BTNode<Integer>(6);
		C = new BTNode<Integer>(6);
		D = new BTNode<Integer>(28);
		E = new BTNode<Integer>(0);
		F = new BTNode<Integer>(561);
		G = new BTNode<Integer>(3);
		H = new BTNode<Integer>(17);
		I = new BTNode<Integer>(6);
		J = new BTNode<Integer>(2);
		K = new BTNode<Integer>(1);
		L = new BTNode<Integer>(461);
		M = new BTNode<Integer>(641);
		N = new BTNode<Integer>(257);
		O = new BTNode<Integer>(271);
		P = new BTNode<Integer>(28);
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
	}
	
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
		LockableBTNode<String> A = new LockableBTNode<String>("A");
		LockableBTNode<String> B = new LockableBTNode<String>("B");
		LockableBTNode<String> C = new LockableBTNode<String>("C");
		LockableBTNode<String> D = new LockableBTNode<String>("D");
		LockableBTNode<String> E = new LockableBTNode<String>("E");
		LockableBTNode<String> F = new LockableBTNode<String>("F");
		LockableBTNode<String> G = new LockableBTNode<String>("G");
		LockableBTNode<String> H = new LockableBTNode<String>("H");
		LockableBTNode<String> I = new LockableBTNode<String>("I");
		LockableBTNode<String> J = new LockableBTNode<String>("J");
		LockableBTNode<String> K = new LockableBTNode<String>("K");
		LockableBTNode<String> L = new LockableBTNode<String>("L");
		LockableBTNode<String> M = new LockableBTNode<String>("M");
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
	
	@Test 
	public void test_inorder_traversal() {
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
		B.parent = A;
		A.right = C;
		C.parent = A;
		B.left = D;
		D.parent = B;
		B.right = E;
		E.parent = B;
		D.left = H;
		H.parent = D;
		E.left = I;
		I.parent = E;
		E.right = J;
		J.parent = E;
		C.left = F;
		F.parent = C;
		C.right = G;
		G.parent = C;
		F.right = K;
		K.parent = F;
		G.right = L;
		L.parent = G;
		BinaryTree.inorder_traversal(A);
	}
	
	@Test
	public void test_kth_inorder_traversal() {
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
		A.size = 12;
		B.left = D;
		B.right = E;
		B.size = 6;
		D.left = H;
		D.size = 2;
		H.size = 1;
		E.left = I;
		E.right = J;
		E.size = 3;
		I.size = 1;
		J.size = 1;
		C.left = F;
		C.right = G;
		C.size = 5;
		F.right = K;
		F.size = 2;
		K.size = 1;
		G.right = L;
		G.size = 2;
		L.size = 1;
		assertEquals(BinaryTree.kth_inorder_traversal(A, 1).data, "H");
		assertEquals(BinaryTree.kth_inorder_traversal(A, 2).data, "D");
		assertEquals(BinaryTree.kth_inorder_traversal(A, 7).data, "A");
		assertEquals(BinaryTree.kth_inorder_traversal(A, 9).data, "K");
		assertEquals(BinaryTree.kth_inorder_traversal(A, 12).data, "L");
	}
	
	@Test
	public void test_construct_binary_tree() {
		BTNode<String> H = BinaryTree.constructFromTraversal("F,B,A,E,H,C,D,I,G", "H,B,F,E,A,C,D,G,I");
		assertEquals(H.data, "H");
		BTNode<String> B = H.left;
		assertEquals(B.data, "B");
		BTNode<String> C = H.right;
		assertEquals(C.data, "C");
		BTNode<String> F = B.left;
		assertEquals(F.data, "F");
		BTNode<String> E = B.right;
		assertEquals(E.data, "E");
		BTNode<String> A = E.left;
		assertEquals(A.data, "A");
		BTNode<String> D = C.right;
		assertEquals(D.data, "D");
		BTNode<String> G = D.right;
		assertEquals(G.data, "G");
		BTNode<String> I = G.left;
		assertEquals(I.data, "I");
	}
	
	@Test
	public void test_construct_preorder() {
		List<String> list = new ArrayList<String>();
		list.add("H");
		list.add("B");
		list.add("F");
		list.add(null);
		list.add(null);
		list.add("E");
		list.add("A");
		list.add(null);
		list.add(null);
		list.add(null);
		list.add("C");
		list.add(null);
		list.add("D");
		list.add(null);
		list.add("G");
		list.add("I");
		list.add(null);
		list.add(null);
		list.add(null);
		BTNode<String> H = BinaryTree.reconstruct_preorder(list);
		assertEquals(H.data, "H");
		BTNode<String> B = H.left;
		assertEquals(B.data, "B");
		BTNode<String> C = H.right;
		assertEquals(C.data, "C");
		BTNode<String> F = B.left;
		assertEquals(F.data, "F");
		BTNode<String> E = B.right;
		assertEquals(E.data, "E");
		BTNode<String> A = E.left;
		assertEquals(A.data, "A");
		BTNode<String> D = C.right;
		assertEquals(D.data, "D");
		BTNode<String> G = D.right;
		assertEquals(G.data, "G");
		BTNode<String> I = G.left;
		assertEquals(I.data, "I");		
	}
	
	@Test
	public void test_get_leaf_nodes() {
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
		List<BTNode<Integer>> list = BinaryTree.get_leaf_nodes(A);
		ListIterator<BTNode<Integer>> it = list.listIterator();
		assertEquals(it.next().data.intValue(), 28);
		assertEquals(it.next().data.intValue(), 0);
		assertEquals(it.next().data.intValue(), 17);
		assertEquals(it.next().data.intValue(), 641);
		assertEquals(it.next().data.intValue(), 257);
		assertEquals(it.next().data.intValue(), 28);
		assertEquals(it.hasNext(), false);
	}
	
	@Test
	public void test_exterior_nodes() {
		BTNode<Integer> A = new BTNode<Integer>(314);
		BTNode<Integer> B = new BTNode<Integer>(6);
		BTNode<Integer> C = new BTNode<Integer>(271);
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
		List<BTNode<Integer>> list = BinaryTree.exterior_nodes(A);
		ListIterator<BTNode<Integer>> it = list.listIterator();
		assertEquals(it.next().data.intValue(), 314);
		assertEquals(it.next().data.intValue(), 6);
		assertEquals(it.next().data.intValue(), 271);
		assertEquals(it.next().data.intValue(), 28);
		assertEquals(it.next().data.intValue(), 0);
		assertEquals(it.next().data.intValue(), 17);
		assertEquals(it.next().data.intValue(), 641);
		assertEquals(it.next().data.intValue(), 257);
		assertEquals(it.next().data.intValue(), 28);
		assertEquals(it.next().data.intValue(), 271);
		assertEquals(it.next().data.intValue(), 6);
		assertEquals(it.hasNext(), false);
	}
	
	@Test
	public void test_LCA() {
		create_binary_tree();
		assertEquals(BinaryTree.LCA(A, E, H), B);
		assertEquals(BinaryTree.LCA(A, D, M), A);
		assertEquals(BinaryTree.LCA(A, H, G), G);
	}
	
	@Test
	public void test_serialize_binary_tree() {
		create_binary_tree();
		A.printTree();
		String s = BinaryTree.serialize(A);
		System.out.println(s);
		BinaryTree.deserialize(s).printTree();
	}
}
