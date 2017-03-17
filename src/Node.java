public class Node<T> {
	T data;
	Node<T> next;
	Node<T> jump;
	T max;
	
	public Node(T data) {
		this.data = data;
		next = null;
		jump = null;
		max = null;
	}
	
	public Node(T data, Node<T> next, Node<T> jump) {
		this.data = data;
		this.next = next;
		this.jump = jump;
		max = null;
	}
}