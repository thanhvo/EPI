import java.util.*;

public class StackT<T extends Comparable> {
	Node<T> first;
	public StackT() {
		first = null;		
	}
	
	public T pop() throws Exception{
		if (first == null)
			throw new Exception("Can not pop from an empty stack");
		T res = first.data;
		first = first.next;
		return res;
	}
	
	public void push(T data) {
		Node<T> node = new Node<T>(data);
		if (first == null) {
			first = node;
			first.max = data;
		} else {
			if (data.compareTo(first.max) < 0) {
				node.max = first.max;
			} else {
				node.max = data;
			}
			node.next = first;
			first = node;
		}		
	}
	
	public T max() throws Exception{
		if (first == null)
			throw new Exception("The stack is empty");
		return first.max;
	}	
}
