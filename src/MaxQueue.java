import java.util.*;

public class MaxQueue<T extends Comparable> {
	Queue<T> Q;
	Deque<T> D;
	
	public MaxQueue() {
		Q = new LinkedList<T>();
		D = new LinkedList<T>();
	}
	
	public void enqueue(T x) {
		Q.add(x);
		while (!D.isEmpty() && D.getLast().compareTo(x) < 0) {
			D.removeLast();
		}
		D.addLast(x);
	}
	
	public T dequeue() throws Exception{
		if (!Q.isEmpty()) {
			T ret = Q.peek();
			if (ret == D.getFirst()) {
				D.removeFirst();
			}
			Q.poll();
			return ret;
		}
		throw new Exception("Empty queue");
	}
	
	public T max() throws Exception{
		if (!D.isEmpty()) {
			return D.getFirst();
		}
		throw new Exception("Empty queue");
	}
	
	public boolean isEmpty() {
		return Q.isEmpty();
	}
}
