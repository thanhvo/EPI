import java.util.*;

public class MedianHeap<T extends Comparable> {
	/* We will preserve one invariant: 
	 * The size of max_heap (smaller elements) is bigger or equal to the size of min_heap (bigger elements), but not more than 1 items.
	 */
	PriorityQueue<T> min_heap;
	PriorityQueue<T> max_heap;
	
	public MedianHeap() {
		min_heap = new PriorityQueue<T>();
		max_heap = new PriorityQueue<T>(Collections.reverseOrder());
	}
	
	public void add(T item) {
		if (max_heap.isEmpty()) {
			max_heap.add(item);
		}
		if (max_heap.peek().compareTo(item) >= 0) {
			if (max_heap.size() == min_heap.size()) {
				max_heap.add(item);
			} else {
				min_heap.add(max_heap.poll());
				max_heap.add(item);
			}
		} else {
			if (max_heap.size() > min_heap.size()) {
				min_heap.add(item);
			} else {
				max_heap.add(min_heap.poll());
				min_heap.add(item);
			}
		}
	}
	
	public T getMedian() {
		return max_heap.peek();
	}
}
