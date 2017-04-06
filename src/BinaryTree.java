import java.util.*;

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
	
	private static<T extends Comparable> boolean isSymmetric(BTNode<T> left, BTNode<T> right) {
		if (left == null && right == null) {
			return true;
		}
		if (left == null || right == null) {
			return false;
		}
		if (left.data.compareTo(right.data) != 0) {
			return false;
		}
		return isSymmetric(left.right, right.left) && isSymmetric(left.left, right.right);
	}
	
	public static<T extends Comparable> boolean isSymmetric(BTNode<T> root) {
		if (root == null) {
			return true;
		}
		return isSymmetric(root.left, root.right);
	}
	
	public static<T> void morris_inorder_traversal(BTNode<T> root) {
		BTNode<T> n = root;
		while (n != null) {
			if (n.left != null) {
				BTNode<T> pre = n.left;
				while (pre.right != null && pre.right != n) {
					pre = pre.right;
				}
				if (pre.right != null) {
					pre.right = null;
					System.out.print(n.data + " ");
					n = n.right;
				} else {
					pre.right = n;
					n = n.left;
				}
			} else {
				System.out.print(n.data + " ");
				n = n.right;
			}
		}
		System.out.println();
	}
	
	public static<T> void inorder_traversal(BTNode<T> root) {
		BTNode<T> n = root;
		/* We use a variable to keep track the direction. 
		 * 0: starting
		 * 1: move left
		 * 2: move right
		 * 3: left up
		 * 4: right up 
		 */
		int direction = 0;
		while (n != null) {
			if (direction < 3) {
				if (n.left != null) {
					n = n.left;
					direction = 1;
				} else {
					System.out.print(n.data + " ");
					if (n.right != null) {
						n = n.right;
						direction = 2;
					} else {						
						n = n.parent;
						if (direction == 1) {
							direction = 3;
						} else {
							direction = 4;
						}
					}
				}
			} else if (direction == 3) {
				System.out.print(n.data + " ");
				if (n.right != null) {
					n = n.right;
					direction = 2;
				} else {
					BTNode<T> parent = n.parent;
					if (parent != null && parent.left == n) {
						direction = 3;
					} else {
						direction = 4;
					}
					n = parent;
				}
			} else {
				BTNode<T> parent = n.parent;
				if (parent != null && parent.left == n) {
					direction = 3;
				} else  {
					direction = 4;
				}
				n = parent;
			}
		}
		System.out.println();
	}
	
	public static<T> BTNode<T> kth_inorder_traversal(BTNode<T> root, int k) {
		if (root == null || root.size < k) {
			return null;
		}
		if(root.left == null) {
			if (k == 1) {
				return root;
			}
			return kth_inorder_traversal(root.right, k-1);
		} else {
			if (root.left.size >= k) {
				return kth_inorder_traversal(root.left, k);
			} else if (root.left.size == k -1) {
				return root;
			} else {
				return kth_inorder_traversal(root.right, k - root.left.size -1);
			}
		}
	}
	
	public static BTNode<String> constructFromTraversal(String inorder, String preorder) {
		if (inorder == null || inorder.isEmpty()) {
			return null;
		}
		int i = preorder.indexOf(",");
		String data = "", lInorder = "", rInorder = "", lPreorder = "", rPreorder = "";
		if (i >0) {
			data = preorder.substring(0, i);
		} else {
			data = preorder;
		}
		BTNode<String> root = new BTNode<String>(data);
		int j = inorder.indexOf(data);
		if (j > 1) {
			lInorder = inorder.substring(0, j-1);
		}
		if (j + data.length() + 1 < inorder.length()) {
			rInorder = inorder.substring(j + data.length() +1);
		}
		if (!lInorder.isEmpty()) {
			lPreorder = preorder.substring(data.length() + 1, data.length() + 1 + lInorder.length());			
		}
		if (!rInorder.isEmpty()) {
			rPreorder = preorder.substring(preorder.length() - rInorder.length());
		}
		root.left = constructFromTraversal(lInorder, lPreorder);
		root.right = constructFromTraversal(rInorder, rPreorder);
		return root;		
	}
}
