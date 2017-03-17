import java.util.*;

class LinkedList<T extends Comparable> {
	Node<T> has_cycle_using_set(Node<T> head) {
		Set<Node<T>> set = new HashSet<Node<T>>();
		Node<T> node = head;
		while(node != null) {
			if (set.contains(node))
				return node;
			set.add(node);
			node = node.next;
		}
		return node;		
	}
	
	Node<T> has_cycle(Node<T> head) {
		Node<T> fast= head, slow = head;
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
	
	int getMedian(Node<Integer> node) {
		int len = 1;
		Node<Integer> n = node.next, head = node;
		while(n != node) {
			if (n.data <= head.data)
				head = n;
			len++;
			n = n.next;
		}		
		n = head;		
		for (int i = 0; i < len/2 -1; i++)
			n = n.next;
		if (len % 2 == 1)
			return n.next.data;
		else
			return (n.data + n.next.data)/2;
	}	
	
	Node<T> overlapping_no_cycle_lists(Node<T> h1, Node<T> h2) {
		if (h1 == null || h2 == null)
			return null;
		int len1 =1, len2 = 1;
		Node<T> n1 = h1, n2 = h2;
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
	
	Node<T> overlapping_lists(Node<T> h1, Node<T> h2) {
		Node<T> s1 = has_cycle(h1);
		Node<T> s2 = has_cycle(h2);
		if (s1 == null && s2 == null) {
			return overlapping_no_cycle_lists(h1, h2);
		} else if (s1 != null && s2 != null) {
			Node<T> temp = s2;
			do {
				temp = temp.next;
			} while (temp != s1 && temp != s2);
			if (temp == s1)
				return s1;			
		}
		return null;
	}
	
	Node<T> even_odd_merge (Node<T> head) {
		if (head == null)
			return null;		
		Node<T> even = head, odd = head.next, firstOdd = head.next;
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
	
	public void print(Node<T> node) {
		while (node != null) {
			System.out.print(node.data + "\t");
			node = node.next;
		}
		System.out.println();
	}
	
	
	
	Node<Integer> remove_kth_last(Node<Integer> head, int k) throws Exception{
		Node<Integer> ahead = head;
		while (ahead != null && k != 0) {
			ahead = ahead.next;
			k--;
		}
		if (k != 0) 
			throw new Exception("Not enough node in the list");
		Node<Integer> pre = null, curr = head;
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
	
	Node<T> reverse_list(Node<T> head) {
		if (head == null)
			return null;
		Node<T> pre = head, curr = head.next;
		head.next = null;
		while(curr != null) {
			Node<T> next = curr.next;
			curr.next = pre;
			pre = curr;
			curr = next;
		}
		return pre;
	}
	
	boolean isPalindrome(Node<T> head) throws Exception{
		StackT<T> stack = new StackT<T>();
		Node<T> node = head;
		while(node != null) {
			stack.push(node.data);
			node = node.next;
		}
		node = head;
		while (node != null) {
			if (node.data.equals(stack.pop()))
				return false;
			node = node.next;
		}
		return true;
	}	
	
	public Node<T> zipping_linked_lists(Node<T> L) {
		Node<T> slow = L, fast = L, pre_slow = null;
		while (fast != null) {
			fast = fast.next;
			if (fast != null) {
				pre_slow = slow;
				fast = fast.next;
				slow = slow.next;
			}			
		}
		if (pre_slow == null)
			return L;
		pre_slow.next = null;
		Node<T> reverse = reverse_list((slow));
		Node<T> curr = L;
		while (curr != null && reverse != null) {
			Node<T> temp = curr.next;
			curr.next = reverse;
			curr = temp;
			if (curr != null) {
				temp = reverse.next;
				reverse.next = curr;
				reverse = temp;
				
			}
		}
		return L;
	}
	
	public int size(Node<T> head) {
		int size = 0;
		Node<T> node = head;
		/* Get the size of the list */
		while (node != null) {
			size ++;
			node = node.next;
		}
		return size;
	}
	
	public Node<T> copy_posting_list(Node<T> head) {
		if (head == null)
			return null;
		Node<T> node = head;		
		
		/* Create the copy list. The next pointer of each node in the new list point to 
		 * the next node of the node it copies from. The next pointer of the original node point to 
		 * the copied node.  
		 */
		while(node != null) {
			Node<T> temp = new Node<T>(node.data, node.next, null);			
			node.next = temp;
			node = temp.next;
		}		
		/* Update the jump pointers for the copied list */
		node = head;
		while(node != null) {
			if (node.jump != null)
				node.next.jump = node.jump.next;
			node = node.next.next;
						
		}
		/* Set the copying list back to original */ 
		node = head;
		Node<T> copied = node.next;
		while(node.next != null) {
			Node<T> temp = node.next;
			node.next = temp.next;
			node = temp;
		}
		return copied;
	}
	
}

