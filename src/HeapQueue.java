import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapQueue<T> {
	int itemNum;
	PriorityQueue<Pair<T, Integer>> min_heap;
	
	public HeapQueue() {
		itemNum = 0;
		Comparator<Pair<T, Integer>> comparator = (p1, p2)-> p1.second.compareTo(p2.second);
		min_heap = new PriorityQueue<Pair<T, Integer>>(10, comparator);
	}
	
	public void enqueue(T item) {
		itemNum++;
		min_heap.add(new Pair(item, new Integer(itemNum)));
	}
	
	public T dequeue() throws Exception{
		if (min_heap.isEmpty()) {
			throw new Exception("The stack is empty");
		}
		return min_heap.poll().first;
	}
}
