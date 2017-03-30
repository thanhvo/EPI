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
}
