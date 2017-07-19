import java.util.HashMap;
import java.util.Map;

public class CacheBTNode<T> extends BTNode<T> {
	int key;
	Integer cachedHash;
	
	public CacheBTNode(T __data) {
		super(__data);
		key = __data.hashCode();
		cachedHash = null;
	}
	
	public CacheBTNode(T __data, CacheBTNode<T> __left, CacheBTNode<T> __right) {
		super(__data);
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
		CacheBTNode<T> n = (CacheBTNode<T>)o;
		if (n == null || key != n.key) {
			return false;
		}
		return (left == n.left && right == n.right);
	}
	
	public static Map<CacheBTNode, CacheBTNode> nodeToCanonicalNode = new HashMap<CacheBTNode, CacheBTNode>();
	
	public static CacheBTNode getCanonical(BTNode n) {
		CacheBTNode lc = (n.left == null) ? null: getCanonical(n.left);
		CacheBTNode rc = (n.right == null) ? null : getCanonical(n.right);
		CacheBTNode nc = new CacheBTNode(n.data, lc, rc);
		if (nodeToCanonicalNode.containsKey(nc)) {
			return nodeToCanonicalNode.get(nc);
		}
		nodeToCanonicalNode.put(nc, nc);
		return nc;
	}
}
