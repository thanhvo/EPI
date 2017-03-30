
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
	
	public static<T> int size(BTNode<T> root) {
		if (root == null) {
			return 0;
		}
		return size(root.left) + size(root.right) + 1;
	}
		
	private static<T> Pair<BTNode<T>, Integer> find_k_unbalanced_node_helper(BTNode<T> root, int k) {
		if (root == null)
			return new Pair(null, 0);
		Pair<BTNode<T>, Integer> L = find_k_unbalanced_node_helper(root.left, k);
		if (L.first != null) {
			return L;
		}
		Pair<BTNode<T>, Integer> R = find_k_unbalanced_node_helper(root.right, k);
		if (R.first != null) {
			return R;
		}
		int node_num = L.second.intValue() + R.second.intValue() + 1;
		if (Math.abs(L.second.intValue() - R.second.intValue()) > k) {
			return new Pair<BTNode<T>, Integer>(root,new Integer(node_num));
		}
		return new Pair(null, new Integer(node_num));		
	}
	
	public static<T> BTNode<T> find_k_unbalanced_node(BTNode<T> root, int k) {
		return find_k_unbalanced_node_helper(root, k).first;
	}
	
	public static<T> boolean isBlanced(BTNode<T> root) {
		return (get_balanced_height(root) != -2);
	}
}
