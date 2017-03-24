import java.util.*;

public class StackT<T extends Comparable> {
	int id;
	int size;
	Node<T> first;
	
	public int getID() {
		return id;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public StackT(int id) {
		size = 0;
		this.id = id;
		first = null;
	}
	
	public StackT() {
		size = 0;
		first = null;		
	}
	
	public T pop() throws Exception{
		if (first == null)
			throw new Exception("Can not pop from an empty stack");
		T res = first.data;
		first = first.next;
		size--;
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
		size++;
	}
	
	public T max() throws Exception{
		if (first == null)
			throw new Exception("The stack is empty");
		return first.max;
	}	
}
