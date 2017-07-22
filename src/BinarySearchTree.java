
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
	private static<T extends Comparable<T>> Pair<BSTNode<T>, Node<T>> build_BST_from_sorted_doubly_list_helper(Node<T> L, int s, int e) {
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

}
