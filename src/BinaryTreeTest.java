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
}
