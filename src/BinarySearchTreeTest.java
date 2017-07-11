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
}
