
public class BinaryTree {
	
	public static<T> int get_balanced_height(BTNode<T> root) {
		if (root == null) { 
			return -1;
		}
		int l_height = get_balanced_height(root.left);
		if (l_height == -2) {
			return -2;
		}			
		int r_height = get_balanced_height(root.right);
		if (r_height == -2) {
			return -2;
		}
		if (Math.abs(l_height - r_height) > 1) {
			return -2;
		}
		return Math.max(l_height, r_height) + 1;		
	}
	
	public static<T> boolean isBlanced(BTNode<T> root) {
		return (get_balanced_height(root) != -2);
	}
}
