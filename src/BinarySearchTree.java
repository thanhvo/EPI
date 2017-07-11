
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

}
