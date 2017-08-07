import java.util.*;

public class BinarySearchTree {
	private static class Lowest<T extends Comparable> implements Comparable<T> {
		public int compareTo(T other) {
			return -1;
		}
	}
	
	private static class Highest<T extends Comparable> implements Comparable<T> {
		public int compareTo(T other) {
			return 1;
		}
	}
	
	private static<T extends Comparable> boolean isBST_helper(BTNode<T> root, T lower, T upper) {
		if (root == null) return true;
		if (lower.compareTo(root.data) > 0 || upper.compareTo(root.data) < 0) {
			return false;
		}
		return isBST_helper(root.left, lower, root.data) && isBST_helper(root.right, root.data, upper);
	}
	
	public static<T extends Comparable> boolean isBST(BTNode<T> root) {
		T lowest = (T)new Lowest<T>();
		T highest = (T)new Highest<T>();
		return isBST_helper(root, lowest, highest);
	}
	
	public static<T extends Comparable<T>> BSTNode<T> successor(BSTNode<T> node) {
		BSTNode<T> successor = null;
		if (node.right != null) {
			successor = (BSTNode<T>)node.right;
			while (successor.left != null) {
				successor = (BSTNode<T>)successor.left;
			}			
		} else {
			successor = (BSTNode<T>)node.parent;
			while (successor != null && successor.compareTo(node) < 0) {
				successor = (BSTNode<T>)successor.parent;
			}
		}
		return successor;
	}
	
	public static <T extends Comparable<T>> BSTNode<T> insert(BSTNode<T> root, T data) {
		BSTNode<T> node = new BSTNode<T>(data);
		if (root == null) {
			return node;
		}
		// Found a duplicate node
		if (data.compareTo(root.data) == 0) {
			return root;
		}
		if (data.compareTo(root.data) < 0) {
			root.left = insert((BSTNode<T>)root.left, data);
		} else {
			root.right = insert((BSTNode<T>)root.right, data);
		}
		return root;
	}
	
	public static<T extends Comparable<T>> BSTNode<T> delete(BSTNode<T> root, T data) throws Exception{
		if (root == null) {
			throw new Exception("No such node.");
		}
		if (root.data.compareTo(data) == 0) {
			if (root.right == null) {
				return (BSTNode<T>)root.left;
			}
			BSTNode<T> newRoot = (BSTNode<T>)root.right;
			BSTNode<T> parent = root;
			while (newRoot.left != null) {
				parent = newRoot;
				newRoot = (BSTNode<T>)newRoot.left;
			}
			parent.left = null;
			newRoot.left = root.left;
			if (newRoot != root.right) {
				newRoot.right = root.right;
			}
			return newRoot;
		} else if (root.data.compareTo(data) < 0) {
			delete((BSTNode<T>)root.left, data);
		} else {
			delete((BSTNode<T>)root.right, data);
		}
		return root;
	}
	
	public static<T extends Comparable<T>> BSTNode<T> find_first_equal_k_recursive(BSTNode<T> root, T key) {
		if (root == null) return null;
		if (root.data.compareTo(key) == 0) {
			BSTNode<T> lResult = find_first_equal_k_recursive(root.getLeft(), key);
			if (lResult != null) return lResult;
			else return root;
		}
		if (key.compareTo(root.data) < 0) return find_first_equal_k_recursive(root.getLeft(), key);
		return find_first_equal_k_recursive(root.getRight(), key);
	}
	
	public static<T extends Comparable<T>> BSTNode<T> find_first_equal_k_iterative(BSTNode<T> root, T key) {
		BSTNode<T> node = root;
		BSTNode<T> first = null;
		while (node != null) {
			if (node.data.compareTo(key) == 0)  {
				first = node;
				node = node.getLeft();
			}
			else if (node.data.compareTo(key) > 0) node = node.getLeft();
			else node = node.getRight();
		}
		return first;
	}
	
	public static<T extends Comparable<T>> BSTNode<T> min_first_search(BSTNode<T> root, T key) {
		if (root == null || root.data.compareTo(key) > 0) return null;
		if (root.data.compareTo(key) == 0) return root;
		if (root.right == null || root.right.data.compareTo(key) > 0) 
			return min_first_search(root.getLeft(), key);
		return min_first_search(root.getRight(), key);		
	}
	
	public static<T extends Comparable<T>> BSTNode<T> find_first_larger_than_k_with_k_exist(BSTNode<T> root, T k) {
		boolean found_k = false;
		BSTNode<T> first = null;
		while (root != null) {
			if (root.data.compareTo(k) == 0) {
				found_k = true;
				root = root.getRight();
			} else if (root.data.compareTo(k) > 0) {
				first = root;
				root = root.getLeft();
			} else { // root.data < k
				root = root.getRight();
			}
		}
		return found_k ? first : null;
	}
	
	private static<T extends Comparable<T>> BSTNode<T> construct_BST_helper(T[] A, int start, int end) {
		if (start > end) return null;
		int mid = start + (end - start)/ 2;
		BSTNode<T> root = new BSTNode<T>(A[mid]);
		root.left = construct_BST_helper(A, start, mid-1);
		root.right = construct_BST_helper(A, mid +1, end);
		return root;
	}
	
	public static<T extends Comparable<T>> BSTNode<T> construct_BST(T[] A) {
		return construct_BST_helper(A, 0, A.length -1);
	}
	
	// Build a BST from (s+1)-th to the e-th node in L
	private static<T extends Comparable<T>> Pair<BSTNode<T>, Node<T>> build_BST_from_sorted_doubly_list_helper
			(Node<T> L, int s, int e) {
		BSTNode<T> curr = null;
		if (s < e) {
			int m = s + ((e-s)>>1);
			curr = new BSTNode<T>();
			Pair<BSTNode<T>, Node<T>> leftPair = build_BST_from_sorted_doubly_list_helper(L, s, m);
			curr.left = leftPair.first;
			L = leftPair.second;
			curr.data = L.data;
			L = L.next;
			Pair<BSTNode<T>, Node<T>> rightPair = build_BST_from_sorted_doubly_list_helper(L, m + 1, e);
			curr.right = rightPair.first;
			L = rightPair.second;
		}
		return new Pair(curr, L);
	}	
	
	public static<T extends Comparable<T>> BSTNode<T> build_BST_from_sorted_doubly_list(Node<T> L) {
		return build_BST_from_sorted_doubly_list_helper(L, 0, LinkedListT.size(L)).first;
	}
	
	// Build a BST from (s+1)-th to the e-th BST node in L
	private static<T extends Comparable<T>> Pair<BSTNode<T>, BSTNode<T>> build_BST_from_sorted_doubly_list_helper
			(BSTNode<T> L, int s, int e) {
		BSTNode<T> curr = null;
		if (s < e) {
			int m = s + ((e-s)>>1);
			curr = new BSTNode<T>();
			Pair<BSTNode<T>, BSTNode<T>> leftPair = build_BST_from_sorted_doubly_list_helper(L, s, m);
			curr.left = leftPair.first;
			L = leftPair.second;
			curr.data = L.data;
			L = L.getRight();
			Pair<BSTNode<T>, BSTNode<T>> rightPair = build_BST_from_sorted_doubly_list_helper(L, m + 1, e);
			curr.right = rightPair.first;
			L = rightPair.second;
		}
		return new Pair(curr, L);
	}
	
	private static <T extends Comparable<T>>int length(BSTNode<T> node) {
		int len = 0;
		while (node != null) {
			len++;
			node = node.getRight();
		}
		return len;
	}
	
	public static<T extends Comparable<T>> BSTNode<T> build_BST_from_sorted_doubly_list(BSTNode<T> L) {
		return build_BST_from_sorted_doubly_list_helper(L, 0, length(L)).first;
	}

	
	private static<T extends Comparable<T>> Pair<BSTNode<T>, BSTNode<T>> convert_BST_to_doubly_linked_list_helper_2(BSTNode<T> root) {
		if(root == null) {
			return null;
		}
		Pair<BSTNode<T>, BSTNode<T>> leftList = convert_BST_to_doubly_linked_list_helper_2(root.getLeft());
		Pair<BSTNode<T>, BSTNode<T>> rightList = convert_BST_to_doubly_linked_list_helper_2(root.getRight());
		BSTNode<T> curr = new BSTNode<T>(root.data);
		BSTNode<T> first, last;
		if (leftList != null) {
			leftList.second.right = curr;
			curr.left = leftList.second;
			first = leftList.first;
		} else {
			first = curr;
		} 
		if (rightList != null) {
			curr.right = rightList.first;
			rightList.first.left = curr;
			last = rightList.second;
		} else {
			last = curr;
		}
		last.right = first;
		first.left = last;
		return new Pair(first, last);
	}
	
	public static<T extends Comparable<T>> BSTNode<T> convert_BST_to_doubly_linked_list_2(BSTNode<T> root) {
		return convert_BST_to_doubly_linked_list_helper_2(root).first;
	}
	
	private static<T extends Comparable<T>> Pair<Node<T>, Node<T>> convert_BST_to_doubly_linked_list_helper(BSTNode<T> root) {
		if(root == null) {
			return null;
		}
		Pair<Node<T>, Node<T>> leftList = convert_BST_to_doubly_linked_list_helper(root.getLeft());
		Pair<Node<T>, Node<T>> rightList = convert_BST_to_doubly_linked_list_helper(root.getRight());
		Node<T> curr = new Node<T>(root.data);
		Node<T> first, last;
		if (leftList != null) {
			leftList.second.next = curr;
			curr.previous = leftList.second;
			first = leftList.first;
		} else {
			first = curr;
		} 
		if (rightList != null) {
			curr.next = rightList.first;
			rightList.first.previous = curr;
			last = rightList.second;
		} else {
			last = curr;
		}
		return new Pair(first, last);
	}
	
	public static<T extends Comparable<T>> Node<T> convert_BST_to_doubly_linked_list(BSTNode<T> root) {
		return convert_BST_to_doubly_linked_list_helper(root).first;
	}
		
	private static<T extends Comparable<T>> BSTNode<T> merge_sorted_lists(BSTNode<T> A, BSTNode<T> B) {
		BSTNode<T> head = null, tail = null;
		while (A != null && B != null) {
			if (A.compareTo(B) < 0) {
				if (head == null) {
					head = A; 
					tail = A;
				} else {
					tail.right = A;
					A.left = tail;
					tail = A;
				}
				A = A.getRight();
			} else {
				if (head == null) {
					head = B;
					tail = B;
				} else {
					tail.right = B;
					B.left = tail;
					tail = B;
				}
				B = B.getRight();
			}
		}
		if (A != null) {
			tail.right = A;
		}
		if (B != null) {
			tail.right = B;
		}
		return head;
	}
	
	private static<T extends Comparable<T>> void print_BST_as_list(BSTNode<T> node) {
		while (node != null) {
			System.out.print(node.data + " ");
			node = node.getRight();
		}
		System.out.println();
	}
	
	public static<T extends Comparable<T>> BSTNode<T> merge_BSTs(BSTNode<T> A, BSTNode<T> B) {
		A = convert_BST_to_doubly_linked_list_2(A);
		A.left.right = null;
		A.left = null;
		print_BST_as_list(A);
		B = convert_BST_to_doubly_linked_list_2(B);
		B.left.right = null;
		B.left = null;
		print_BST_as_list(B);
		
		BSTNode<T> C = merge_sorted_lists(A, B);
		print_BST_as_list(C);
		return build_BST_from_sorted_doubly_list(C);		
	}
	
	private static<T extends Comparable<T>> void 
		find_k_largest_elements_helper(BSTNode<T> root, int k, List<T> k_elements) {
		if (root != null && k_elements.size() < k) {
			// Perform reverse in-order traversal
			find_k_largest_elements_helper(root.getRight(), k, k_elements);
			if (k_elements.size() < k) {
				k_elements.add(root.data);
				find_k_largest_elements_helper(root.getLeft(), k, k_elements);
			}
		}
	}
	
	public static<T extends Comparable<T>> List<T> find_k_largest_elements(BSTNode<T> root, int k) {
		List<T> k_elements = new ArrayList<T>();
		find_k_largest_elements_helper(root, k, k_elements);
		return k_elements;	
	}
	
	private static<T extends Comparable<T>> BSTNode<T> build_BST_from_pre_order(ArrayList<T> pre_order, int start, int end) {
		if (start > end ) return null;
		BSTNode<T> root = new BSTNode(pre_order.get(start));
		int mid = start + 1;
		while (mid <= end && pre_order.get(mid).compareTo(root.data) < 0)
			mid++;
		root.left = build_BST_from_pre_order(pre_order, start+1, mid - 1);
		root.right = build_BST_from_pre_order(pre_order, mid, end);
		return root;
	}
	
	public static<T extends Comparable<T>> BSTNode<T> build_BST_from_pre_order(ArrayList<T> pre_order) {
		return build_BST_from_pre_order(pre_order, 0, pre_order.size() -1);
	}
	
	private static<T extends Comparable<T>> 
	Pair<BSTNode<T>, Integer> rebuild_BST_from_preorder_helper2(ArrayList<T> preorder, int idx, T min, T max) {
		if (idx == preorder.size()) {
			return null;
		}
		T curr = preorder.get(idx);
		if (min.compareTo(curr) > 0 || max.compareTo(curr) < 0) {
			return null;
		}
		++idx;
		Pair<BSTNode<T>, Integer> leftPair = rebuild_BST_from_preorder_helper2(preorder, idx, min, curr);
		Pair<BSTNode<T>, Integer> rightPair = null;
		if (leftPair == null)
			rightPair = rebuild_BST_from_preorder_helper2(preorder, idx, curr, max);
		else rightPair = rebuild_BST_from_preorder_helper2(preorder, leftPair.second.intValue(), curr, max);
		BSTNode<T> root = new BSTNode<T>(curr, leftPair != null ? leftPair.first : null, 
				rightPair != null ? rightPair.first : null);
		if (rightPair == null) 
			return new Pair(root, idx);
		return new Pair(root, rightPair.second.intValue());
	}
	
	public static <T extends Comparable<T>> BSTNode<T> rebuild_BST_from_preorder2(ArrayList<T> preorder) {
		int idx = 0;
		T lowest = (T)new Lowest<T>();
		T highest = (T)new Highest<T>();
		return rebuild_BST_from_preorder_helper2(preorder, idx, lowest, highest).first;
	}
	
	public static<T extends Comparable<T>> BSTNode<T> LCA(BSTNode<T> A, BSTNode<T> B) {
		BSTNode<T> LCA = null;
		BSTNode<T> parent = B;
		while (parent != null ) {
			if(A.compareTo(parent) <= 0 && B.compareTo(parent) >= 0) 
				LCA = parent;
			parent = parent.getParent();
		}
		return LCA;
	}
	
	public static <T extends Comparable<T>>  
		boolean is_r_s_descendant_ancestor_of_m(BSTNode<T> r, BSTNode<T> s, BSTNode<T> m) {
		BSTNode<T> curr_r = r, curr_s = s;
		boolean found_m = false;
		// Interleaving searches from r and s
		while (curr_r != s && curr_s != r && (curr_r != null || curr_s != null)) {
			if (curr_r == m || curr_s == m) {
				found_m = true;
			}
			if (curr_r != null)
				curr_r = curr_r.compareTo(s) > 0 ? curr_r.getLeft() : curr_r.getRight();
			 
			if (curr_s != null)
				curr_s = curr_s.compareTo(r) > 0 ? curr_s.getLeft() : curr_s.getRight();			
		}
		return (curr_r == s || curr_s == r) && found_m;				
	}
	
	private static <T extends Comparable<T>> void range_query_helper(BSTNode<T> root, T L, T U, List<BSTNode<T>> list) {
		if (root == null) return;
		if (root.data.compareTo(U) > 0) {
			range_query_helper(root.getLeft(), L, U, list);
		} else if (root.data.compareTo(L) < 0) {
			range_query_helper(root.getRight(), L, U, list);
		} else {
			list.add(root);
			range_query_helper(root.getLeft(), L, U, list);
			range_query_helper(root.getRight(), L, U, list);
		}
	}
	
	public static <T extends Comparable<T>> List<BSTNode<T>> range_query(BSTNode<T> root, T L, T U) {
		List<BSTNode<T>> list = new ArrayList<BSTNode<T>>();
		range_query_helper(root, L, U, list);
		return list;
	}
}
