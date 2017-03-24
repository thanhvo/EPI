import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

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
		//assertEquals(StackUtil.evaluate_RPN("1,2,+,3,+,2,-,3"),4);
	}
	
	@Test
	public void test_printBST() {
		System.out.println("Printing BST");
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
	
	@Test
	public void test_move_Rings() throws Exception{
		System.out.println("Moving items between stacks");
		StackT<Integer> s1 = new StackT<Integer>(1);
		StackT<Integer> s2 = new StackT<Integer>(2);
		StackT<Integer> s3 = new StackT<Integer>(3);
		s1.push(4);
		s1.push(3);
		s1.push(2);
		s1.push(1);
		StackUtil.moveRings(s1,s2,s3);
		assertEquals(s1.isEmpty(), true);
		assertEquals(s3.isEmpty(), true);
		assertEquals(s2.pop().intValue(), 1);
		assertEquals(s2.pop().intValue(), 2);
		assertEquals(s2.pop().intValue(), 3);
		assertEquals(s2.pop().intValue(), 4);
		assertEquals(s2.isEmpty(), true);
	}
	
	@Test
	public void test_buildings_with_sunset() {
		System.out.println("Buildings with sunset");
		String s = "9 8 6 3 5 1";
		StringToIntegerTransformer transformer = new StringToIntegerTransformer();
		Stack<Pair<Integer,Integer>> buildings_with_sunset = StackUtil.examine_buildings_with_sunset(s, transformer);
		Pair<Integer,Integer> pair = buildings_with_sunset.pop();
		assertEquals(pair.first.intValue(), 5);
		assertEquals(pair.second.intValue(), 1);
		pair = buildings_with_sunset.pop();
		assertEquals(pair.first.intValue(), 4);
		assertEquals(pair.second.intValue(), 5);
		pair = buildings_with_sunset.pop();
		assertEquals(pair.first.intValue(), 2);
		assertEquals(pair.second.intValue(), 6);
		pair = buildings_with_sunset.pop();
		assertEquals(pair.first.intValue(), 1);
		assertEquals(pair.second.intValue(), 8);
		pair = buildings_with_sunset.pop();
		assertEquals(pair.first.intValue(), 0);
		assertEquals(pair.second.intValue(), 9);
		assertEquals(buildings_with_sunset.isEmpty(), true);		
	}
}
