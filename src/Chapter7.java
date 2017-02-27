import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

class Node {
	int val;
	Node next;
	public Node(int val) {
		this.val = val;
	}
}

public class Chapter7 {
	
	Node has_cycle_using_set(Node head) {
		Set<Node> set = new HashSet<Node>();
		Node node = head;
		while(node != null) {
			if (set.contains(node))
				return node;
			set.add(node);
			node = node.next;
		}
		return node;		
	}
	
	Node has_cycle(Node head) {
		Node fast= head, slow = head;
		while(slow != null && slow.next != null && 
			  fast != null && fast.next != null && 
			  fast.next.next != null) {
			slow = slow.next; 
			fast = fast.next.next;
			if (slow == fast) {
				int cycle_len = 0;
				do {
					++cycle_len;
					fast= fast.next;
				} while(slow != fast);
				slow = head; fast = head;
				while(cycle_len > 0) {
					fast = fast.next;
					cycle_len--;
				}
				while(slow != fast) {
					slow = slow.next;
					fast = fast.next;
				}
				return slow;
			}
		}
		return null;
	}
	
	@Test
	public void test_has_cycle() {
		System.out.println("Testing has_cycle function");
		Node head = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		head.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = three;
		Node n = has_cycle_using_set(head);
		assertEquals(n.val, 3);
		Node n2 = has_cycle(head);
		assertEquals(n2.val, 3);
		five.next = null;
		assertEquals(has_cycle_using_set(head), null);
		assertEquals(has_cycle(head), null);
	}
	
	int getMedian(Node node) {
		int len = 1;
		Node n = node.next, head = node;
		while(n != node) {
			if (n.val <= head.val)
				head = n;
			len++;
			n = n.next;
		}		
		n = head;		
		for (int i = 0; i < len/2 -1; i++)
			n = n.next;
		if (len % 2 == 1)
			return n.next.val;
		else
			return (n.val + n.next.val)/2;
	}
	
	@Test
	public void test_Median() {
		System.out.println("Testing median of a list");
		Node zero = new Node(0);
		Node zero1 = new Node(0);
		Node zero2 = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		
		/* Test case 1: all items in the list are identical */
		zero.next = zero1;
		zero1.next = zero2;
		zero2.next = zero;
		assertEquals(getMedian(zero),0);
		assertEquals(getMedian(zero1),0);
		
		/* Test case 2 */
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = zero;
		assertEquals(getMedian(zero),3);
		assertEquals(getMedian(one),3);
		assertEquals(getMedian(three),3);
		assertEquals(getMedian(four),3);
				
	}
	
	Node overlapping_no_cycle_lists(Node h1, Node h2) {
		if (h1 == null || h2 == null)
			return null;
		int len1 =1, len2 = 1;
		Node n1 = h1, n2 = h2;
		while(n1 != null) {
			n1 = n1.next;
			len1++;
		}
		while(n2 != null) {
			n2 = n2.next;
			len2++;
		}
		n1 = h1; n2 = h2;
		if(len1 > len2) {
			for(int i = 0; i < len1 - len2; i++)
				n1 = n1.next;
		} else {
			for (int i =0; i < len2 - len1; i++)
				n2 = n2.next;
		}
		while(n1 != n2) {
			n1 = n1.next;
			n2 = n2.next;
		}
		return n1;
	}
	
	Node overlapping_lists(Node h1, Node h2) {
		Node s1 = has_cycle(h1);
		Node s2 = has_cycle(h2);
		if (s1 == null && s2 == null) {
			return overlapping_no_cycle_lists(h1, h2);
		} else if (s1 != null && s2 != null) {
			Node temp = s2;
			do {
				temp = temp.next;
			} while (temp != s1 && temp != s2);
			if (temp == s1)
				return s1;			
		}
		return null;
	}
	
	@Test
	public void test_overlapping_lists() {
		System.out.println("Testing overlapping lists");
		Node zero = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		Node eight = new Node(8);
		Node nine = new Node(9);
		
		/* Test case 1: two lists do not overlap */
		zero.next = two;
		two.next = seven;
		one.next = three;
		three.next = four;
		four.next = five;
		five.next = six;		
		assertEquals(overlapping_no_cycle_lists(zero,one),null);
		
		/* Test case 2 */
		two.next = four;
		assertEquals(overlapping_no_cycle_lists(zero,one).val, 4);
		
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
		assertEquals(overlapping_lists(zero, two).val, 5);
	}
	
	Node even_odd_merge (Node head) {
		if (head == null)
			return null;		
		Node even = head, odd = head.next, firstOdd = head.next;
		while(even != null && odd != null) {
			if (odd.next == null) {
				even.next = firstOdd;
				return head;
			} else {
				even.next = odd.next;
				even = even.next;
				odd.next = even.next;
				odd = odd.next;				
			}
		}
		even.next = firstOdd;
		return head;
	}
	
	public void print(Node node) {
		while (node != null) {
			System.out.print(node.val + "\t");
			node = node.next;
		}
		System.out.println();
	}
	
	@Test
	public void test_even_odd_merge() {
		System.out.println("Testing even odd merging");
		Node zero = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		Node eight = new Node(8);
		Node nine = new Node(9);
		Node ten = new Node(10);
		
		zero.next = one;
		one.next = two;
		two.next = three;
		three.next = four;
		four.next = five;
		five.next = six;
		six.next = seven;
		seven.next = eight;
		eight.next = nine;
		
		print(zero);
		/* Test case 1: even number of nodes */
		print(even_odd_merge(zero));
		
		/* Test case 2: empty list */
		assertEquals(even_odd_merge(null), null);
		
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
		print(even_odd_merge(zero));
	}
	
	Node remove_kth_last(Node head, int k) throws Exception{
		Node ahead = head;
		while (ahead != null && k != 0) {
			ahead = ahead.next;
			k--;
		}
		if (k != 0) 
			throw new Exception("Not enough node in the list");
		Node pre = null, curr = head;
		while (ahead != null) {
			pre = curr;
			curr = curr.next;
			ahead = ahead.next;
		}
		if (pre != null) {
			pre.next = curr.next;
			return head;
		}
		else 
			return head.next;			
	}
	
	@Test
	public void test_remove_kth_last() throws Exception{
		System.out.println("Testing remove kth last");
		Node zero = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		Node eight = new Node(8);
		Node nine = new Node(9);
		Node ten = new Node(10);
		
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
		
		print(zero);
		print(remove_kth_last(zero, 5));
		print(remove_kth_last(zero, 10));
	}
	
	Node reverse_list(Node head) {
		if (head == null)
			return null;
		Node pre = head, curr = head.next;
		head.next = null;
		while(curr != null) {
			Node next = curr.next;
			curr.next = pre;
			pre = curr;
			curr = next;
		}
		return pre;
	}
	
	@Test
	public void test_reverse_list() {
		System.out.println("Testing reverse list");
		Node zero = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		Node four = new Node(4);
		Node five = new Node(5);
		Node six = new Node(6);
		Node seven = new Node(7);
		Node eight = new Node(8);
		Node nine = new Node(9);
		Node ten = new Node(10);
		
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
		
		print(zero);
		print(reverse_list(zero));
	}
	
}