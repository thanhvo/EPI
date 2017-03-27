
public class BTNode<T extends Comparable> implements Comparable{
	T data;
	BTNode<T> left;
	BTNode<T> right;
	
	public BTNode(T __data) {
		data = __data;
		left = null;
		right = null;
	}
	
	public int compareTo(BTNode<T> node) {
		return data.compareTo(node.data);
	}

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}				
}
