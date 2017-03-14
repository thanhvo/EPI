import org.junit.*;
import static org.junit.Assert.*;

public class LinkedListTest {
	@Test
	public void test_Median() {
		System.out.println("Testing median of a list");
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> zero1 = new Node<Integer>(0);
		Node<Integer> zero2 = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		Node<Integer> six = new Node<Integer>(6);
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		/* Test case 1: all items in the list are identical */
		zero.next = zero1;
		zero1.next = zero2;
		zero2.next = zero;
		assertEquals(list.getMedian(zero),0);
		assertEquals(list.getMedian(zero1),0);
		
		/* Test case 2 */
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = zero;
		assertEquals(list.getMedian(zero),3);
		assertEquals(list.getMedian(one),3);
		assertEquals(list.getMedian(three),3);
		assertEquals(list.getMedian(four),3);
				
	}
	@Test
	public void test_has_cycle() {
		System.out.println("Testing has_cycle function");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> head = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		head.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = three;
		Node<Integer> n = list.has_cycle_using_set(head);
		assertEquals(n.data.intValue(), 3);
		Node<Integer> n2 = list.has_cycle(head);
		assertEquals(n2.data.intValue(), 3);
		five.next = null;
		assertEquals(list.has_cycle_using_set(head), null);
		assertEquals(list.has_cycle(head), null);
	}
	
	@Test
	public void test_overlapping_lists() {
		System.out.println("Testing overlapping lists");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		Node<Integer> six = new Node<Integer>(6);
		Node<Integer> seven = new Node<Integer>(7);
		Node<Integer> eight = new Node<Integer>(8);
		Node<Integer> nine = new Node<Integer>(9);
		
		/* Test case 1: two lists do not overlap */
		zero.next = two;
		two.next = seven;
		one.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		assertEquals(list.overlapping_no_cycle_lists(zero,one),null);
		
		/* Test case 2 */
		two.next = four;
		assertEquals(list.overlapping_no_cycle_lists(zero,one).data.intValue(), 4);
		
		/*Test case 3: two lists overlap */ 
		zero.next = one;
		one.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		nine.next = five;
		two.next = three;
		three.next = nine;
		assertEquals(list.overlapping_lists(zero, two).data.intValue(), 5);
	}
	
	@Test
	public void test_even_odd_merge() {
		System.out.println("Testing even odd merging");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		Node<Integer> six = new Node<Integer>(6);
		Node<Integer> seven = new Node<Integer>(7);
		Node<Integer> eight = new Node<Integer>(8);
		Node<Integer> nine = new Node<Integer>(9);
		Node<Integer> ten = new Node<Integer>(10);
		
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		
		list.print(zero);
		/* Test case 1: even number of nodes */
		list.print(list.even_odd_merge(zero));
		
		/* Test case 2: empty list */
		assertEquals(list.even_odd_merge(null), null);
		
		/* Test case 3: odd number of nodes */
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		nine.next = ten;
		list.print(list.even_odd_merge(zero));
	}
	
	@Test
	public void test_remove_kth_last() throws Exception{
		System.out.println("Testing remove kth last");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		Node<Integer> six = new Node<Integer>(6);
		Node<Integer> seven = new Node<Integer>(7);
		Node<Integer> eight = new Node<Integer>(8);
		Node<Integer> nine = new Node<Integer>(9);
		Node<Integer> ten = new Node<Integer>(10);
		
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		nine.next = ten;
		
		list.print(zero);
		list.print(list.remove_kth_last(zero, 5));
		list.print(list.remove_kth_last(zero, 10));
	}
	
	@Test
	public void test_reverse_list() {
		System.out.println("Testing reverse list");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		Node<Integer> six = new Node<Integer>(6);
		Node<Integer> seven = new Node<Integer>(7);
		Node<Integer> eight = new Node<Integer>(8);
		Node<Integer> nine = new Node<Integer>(9);
		Node<Integer> ten = new Node<Integer>(10);
		
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		nine.next = ten;
		
		list.print(zero);
		list.print(list.reverse_list(zero));
	}
	
	@Test
	public void test_isPalindrome() {
		System.out.println("Testing if a list is palindrome");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		Node<Integer> four2 = new Node<Integer>(4);
		Node<Integer> three2 = new Node<Integer>(3);
		Node<Integer> two2 = new Node<Integer>(2);
		Node<Integer> one2 = new Node<Integer>(1);
		Node<Integer> zero2 = new Node<Integer>(0);
		
		/* Test case 1: a palindrome list */
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = four2;
		four2.next = three2;
		three2.next = two2;
		two2.next = one2;
		one2.next = zero2;
		assertEquals(list.isPalindrome(zero), true);
		
		/* Test case 2: a list is not palindrome */
		one2.next = new Node<Integer>(1);
		assertEquals(list.isPalindrome(zero), false);
	}
	
	@Test
	public void test_zipping_linked_lists() {
		System.out.println("Testing zipping linked list");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		Node<Integer> four = new Node<Integer>(4);
		Node<Integer> five = new Node<Integer>(5);
		Node<Integer> six = new Node<Integer>(6);
		Node<Integer> seven = new Node<Integer>(7);
		Node<Integer> eight = new Node<Integer>(8);
		Node<Integer> nine = new Node<Integer>(9);
		Node<Integer> ten = new Node<Integer>(10);
		
		/* Test case 1: the list has an even number of nodes */
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		
		list.print(list.zipping_linked_lists(zero));
		
		/* Test case 2: the list has an odd number of nodes */
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		nine.next = ten;
		
		list.print(list.zipping_linked_lists(zero));
	}
	
	@Test 
	public void test_posting_list() {
		System.out.println("Testing posting list.");
		LinkedList<Integer> list = new LinkedList<Integer>();
		Node<Integer> zero = new Node<Integer>(0);
		Node<Integer> one = new Node<Integer>(1);
		Node<Integer> two = new Node<Integer>(2);
		Node<Integer> three = new Node<Integer>(3);
		zero.next = one;
		zero.jump = two;
		one.next = two;
		one.jump = three;
		two.next = three;
		two.jump = one;
		three.next = null;
		three.jump = three;		
		Node<Integer> cZero = list.copy_posting_list(zero);
		Node<Integer> cOne = cZero.next;
		Node<Integer> cTwo = cOne.next;
		Node<Integer> cThree = cTwo.next;
		assertEquals(cZero.data.intValue(), 0);
		assertEquals(cOne.data.intValue(), 1);
		assertEquals(cTwo.data.intValue(), 2);
		assertEquals(cThree.data.intValue(), 3);
		assertEquals(cZero.jump.data.intValue(), 2);
		assertEquals(cOne.jump.data.intValue(), 3);
		assertEquals(cTwo.jump.data.intValue(), 1);
		assertEquals(cThree.jump.data.intValue(), 3);
		assertEquals(zero.next.data.intValue(), 1);
		assertEquals(one.next.data.intValue(), 2);
		assertEquals(two.next.data.intValue(), 3);
		assertEquals(three.next, null);
		assertEquals(zero.jump.data.intValue(), 2);
		assertEquals(one.jump.data.intValue(), 3);
		assertEquals(two.jump.data.intValue(), 1);
		assertEquals(three.jump.data.intValue(), 3);
	}

}