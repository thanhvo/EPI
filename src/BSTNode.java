
public class BSTNode<T extends Comparable<T>> extends BTNode<T> implements Comparable<BSTNode<T>>{
	public BSTNode(T data) {
		super(data);
		visited = false;
	}
	
	public int compareTo(BSTNode<T> other) {
		return data.compareTo(other.data);
	}	
}
