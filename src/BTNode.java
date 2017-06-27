import java.util.*;

public class BTNode<T> {
	T data;
	BTNode<T> left, right, parent;
	int key;
	Integer cachedHash;
	private boolean locked;
	private int numChildrenLocks;
	public int size;
	public boolean visited = false;
	
	public BTNode(T __data) {
		data = __data;
		locked = false;
		numChildrenLocks = 0;
		left = null;
		right = null;
		parent = null;		
	}
	
	public BTNode(T __data, BTNode<T> __left, BTNode<T> __right) {
		data = __data;
		left = __left;
		right = __right;
		key = __data.hashCode();
		cachedHash = null;	
	}	
	
	@Override
	public int hashCode() {
		if (this.cachedHash != null) {
			return this.cachedHash;			
		}
		int x = 3 * key;
		int y = this.left == null ? 5 : 5 * this.left.hashCode();
		int z = this.right == null ? 7 : 7 * this.right.hashCode();
		this.cachedHash = x + y + z;
		return this.cachedHash;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof BTNode)) {
			return false;
		}
		BTNode<T> n = (BTNode<T>)o;
		if (n == null || key != n.key) {
			return false;
		}
		return (left == n.left && right == n.right);
	}
	
	public static Map<BTNode, BTNode> nodeToCanonicalNode = new HashMap<BTNode, BTNode>();
	
	public static BTNode getCanonical(BTNode n) {
		BTNode lc = (n.left == null) ? null: getCanonical(n.left);
		BTNode rc = (n.right == null) ? null : getCanonical(n.right);
		BTNode nc = new BTNode(n.data, lc, rc);
		if (nodeToCanonicalNode.containsKey(nc)) {
			return nodeToCanonicalNode.get(nc);
		}
		nodeToCanonicalNode.put(nc, nc);
		return nc;
	}
	
	public boolean isLock() {
		return locked;
	}
	
	public void lock() {
		if (numChildrenLocks == 0 && locked == false) {
			BTNode<T> n = parent;
			while (n != null) {
				if (n.locked) {
					return;
				}
				n = n.parent;
			}
			locked = true;
			n = parent;
			while (n != null) {
				++n.numChildrenLocks;
				n = n.parent;
			}
		}
	}
	
	public void unlock() {
		if (locked) {
			locked = false;
			BTNode<T> n = parent;
			while (n != null) {
				--n.numChildrenLocks;
				n = n.parent;
			}
		}
	}
	
	
}
