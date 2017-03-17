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
}
