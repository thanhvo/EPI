import java.util.*;

public class HeapStack<T> {
	int itemNum;
	PriorityQueue<Pair<T, Integer>> max_heap;
	
	public HeapStack() {
		itemNum = 0;
		Comparator<Pair<T, Integer>> comparator = (p1, p2)-> p2.second.compareTo(p1.second);
		max_heap = new PriorityQueue<Pair<T, Integer>>(10, comparator);
	}
	
	public void push(T item) {
		itemNum++;
		max_heap.add(new Pair(item, new Integer(itemNum)));
	}
	
	public T pop() throws Exception{
		if (max_heap.isEmpty()) {
			throw new Exception("The stack is empty");
		}
		return max_heap.poll().first;
	}
}
