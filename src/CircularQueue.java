public class CircularQueue<T> {
	T[] elements;
	int size;
	int first;
	int last;
	int capacity;
	
	public CircularQueue(int capacity) {
		 elements = (T[])new Object[capacity];
		 this.capacity = capacity;
		 first = -1;
		 last = -1;
	}
	
	public CircularQueue() {
		this(10);
	}
	
	public int size() {
		return size;		
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void resize() {
		T[] new_elements = (T[]) new Object[2* capacity];
		if (last < first) {
			last = last + capacity;
		}
		for (int i = first; i<= last; i++) {
			new_elements[i] = elements[i%capacity];
		}
		capacity = capacity * 2;
		elements = new_elements;
	}
	
	public void enqueue(T value) {
		if (size == capacity) {
			resize();
		}
		last = (last +1) % capacity;
		elements[last] = value;
		if (size == 0) {
			first = 0;
		}
		size++;		
	}
	
	public T dequeue() throws Exception{
		if (size == 0) {
			throw new Exception("Empty queue");
		}
		T ret = elements[first];
		first = (first + 1) % capacity;
		size--;
		return ret;		
	}	
}
