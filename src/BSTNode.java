
public class BSTNode<T extends Comparable<T>> extends BTNode<T> implements Comparable<BSTNode<T>>{
	public BSTNode() {
		super();
	}
	
	public BSTNode(T data) {
		super(data);
	}
	
	public BSTNode(T data, BSTNode<T> __left, BSTNode<T> __right) {
		super(data, __left, __right);
	}
	
	public int compareTo(BSTNode<T> other) {
		return data.compareTo(other.data);
	}
	
	public BSTNode<T> getLeft() {
		return (BSTNode<T>)left;
	}
	
	public BSTNode<T> getRight() {
		return (BSTNode<T>)right;
	}
	
	public BSTNode<T> getParent() {
		return (BSTNode<T>)parent;
	}
}
