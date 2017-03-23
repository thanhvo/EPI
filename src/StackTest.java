import org.junit.*;
import static org.junit.Assert.*;

public class StackTest {
	@Test
	public void test_max_stack() throws Exception{
		System.out.println("Testing max stack");
		StackT<Integer> stack = new StackT<Integer>();
		stack.push(1);
		assertEquals(stack.max().intValue(), 1);
		stack.push(4);
		assertEquals(stack.max().intValue(), 4);
		stack.push(3);
		assertEquals(stack.max().intValue(), 4);
		stack.push(5);
		assertEquals(stack.max().intValue(), 5);
		assertEquals(stack.pop().intValue(), 5);
		assertEquals(stack.max().intValue(), 4);
		stack.push(2);
		assertEquals(stack.max().intValue(), 4);
		assertEquals(stack.pop().intValue(), 2);
		assertEquals(stack.max().intValue(), 4);
	}
	
	@Test
	public void test_evaluate_RPN() throws Exception {
		System.out.println("Testing RPN evaluation");
		assertEquals(StackUtil.evaluate_RPN("1,2,+"),3);
		assertEquals(StackUtil.evaluate_RPN("1,2,+,3,+,2,-"),4);
		/* Throws Exception */
		assertEquals(StackUtil.evaluate_RPN("1,2,+,3,+,2,-,3"),4);
	}
	
	@Test
	public void test_printBST() {
		System.out.println("Testing priting BST");
		BSTNode<Integer> n1 = new BSTNode<Integer>(37);
		BSTNode<Integer> n2 = new BSTNode<Integer>(29);
		BSTNode<Integer> n3 = new BSTNode<Integer>(43);
		BSTNode<Integer> n4 = new BSTNode<Integer>(23);
		BSTNode<Integer> n5 = new BSTNode<Integer>(31);
		BSTNode<Integer> n6 = new BSTNode<Integer>(41);
		BSTNode<Integer> n7 = new BSTNode<Integer>(47);
		BSTNode<Integer> n8 = new BSTNode<Integer>(53);
		n1.left = n2;
		n1.right = n3;
		n2.left = n4;
		n2.right = n5;
		n3.left = n6;
		n3.right = n7;
		n7.right = n8;
		StackUtil.printBST(n1);
	}
}
