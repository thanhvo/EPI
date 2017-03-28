import java.util.*;

public class QueueByStacks<T> {
	private Stack<T> A, B;
	
	public QueueByStacks() {
		A = new Stack<T>();
		B = new Stack<T>();
	}
	
	public void enqueue(T value) {
		A.push(value);
	}
	
	public T dequeue() throws Exception{
		if (B.isEmpty()) {
			while(!A.empty()) {
				B.push(A.pop());
			}
		}
		if (!B.isEmpty()) {
			return B.pop();
		}
		throw new Exception("Empty Queue.");
	}
	
	public boolean isEmpty() {
		return A.isEmpty() && B.isEmpty();
	}
}
