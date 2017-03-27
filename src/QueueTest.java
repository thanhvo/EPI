import org.junit.*;
import static org.junit.Assert.*;

public class QueueTest {
	
	@Test
	public void test_printBirnaryTree() {
		System.out.println("Printing Binary Tree");
		BTNode<Integer> n0 = new BTNode<Integer>(0);
		BTNode<Integer> n1 = new BTNode<Integer>(1);
		BTNode<Integer> n2 = new BTNode<Integer>(1);
		BTNode<Integer> n3 = new BTNode<Integer>(2);
		BTNode<Integer> n4 = new BTNode<Integer>(2);
		BTNode<Integer> n5 = new BTNode<Integer>(2);
		BTNode<Integer> n6 = new BTNode<Integer>(2);
		BTNode<Integer> n7 = new BTNode<Integer>(3);
		BTNode<Integer> n8 = new BTNode<Integer>(3);
		BTNode<Integer> n9 = new BTNode<Integer>(3);
		BTNode<Integer> n10 = new BTNode<Integer>(3);
		n0.left = n1;
		n0.right = n2;
		n1.left = n3;
		n1.right = n4;
		n2.left = n5;
		n2.right = n6;
		n4.left = n7;
		n4.right = n8;
		n6.left = n9;
		n6.right = n10;
		QueueUtil.printBinaryTree(n0);		
	}

}
