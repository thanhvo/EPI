
public class BSTNode<T extends Comparable> {
	T data;
	BSTNode<T> left;
	BSTNode<T> right;
	boolean visited;
	
	public BSTNode(T data) {
		this.data = data;
		left = null;
		right = null;
		visited = false;
	}
	
	public boolean printable() {
		return (left == null || left.visited);
	}
	
}
