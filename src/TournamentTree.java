import java.util.*;
public class TournamentTree {
	private class TreeNode {
		public int cap; // leaf: remaining capacity in the box
		// non-leaf: max remaining in the subtree
		public List<Integer> items; // stores the items in the leaf node
		public TreeNode(int cap) {
			this.cap = cap;
			items = new ArrayList<Integer>();
		}
	}
	// Store the complete binary tree. For tree[i], left subtree is tree[2i+1], and right subtree is tree[2i+2].
	private List<TreeNode> tree;
	// Recursively inserts item in tournament tree
	private boolean insertHelper(int idx, int item, int cap) {
		if (tree.get(idx).cap < cap) return false;
		int left = (idx << 1) + 1;
		int right = (idx << 1) + 2;
		if (left < tree.size()) { // internal node
			insertHelper(tree.get(left).cap >= cap ? left : right, item, cap);
			tree.get(idx).cap = Math.max(tree.get(left).cap, tree.get(right).cap);			
		} else { // leaf node
			tree.get(idx).cap -= cap;
			tree.get(idx).items.add(item);
		}
		return true;
	}
	// n items, and each box has unit_cap
	public TournamentTree(int n, int unit_cap) {
		tree = new ArrayList<TreeNode>();
		for (int i = 0; i < 2* n -1; i++) {
			tree.add(new TreeNode(unit_cap));
		}
	}
	
	public boolean insert(int item, int item_cap) {
		return insertHelper(0, item, item_cap);
	}
	
	public int getCapacity() {
		return tree.get(0).cap;
	}
}
