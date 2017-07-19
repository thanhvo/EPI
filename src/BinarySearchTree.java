
public class BinarySearchTree {
	private static<T extends Comparable> boolean isBST_helper(BTNode<T> root, T lower, T upper) {
		if (root == null) return true;
		if (lower.compareTo(root.data) > 0 || upper.compareTo(root.data) < 0) {
			return false;
		}
		return isBST_helper(root.left, lower, root.data) && isBST_helper(root.right, root.data, upper);
	}
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

}
