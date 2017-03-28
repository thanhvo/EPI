public class IQueue {
	long data;
	int size;
	
	public IQueue() {
		data = 0;
		size = 0;
	}
	
	public void enqueue(int val) {
		data = data * 10 + val;
		size++;		
	}
	
	public int dequeue() throws Exception{
		if (size == 0) {
			throw new Exception("Empty Queue");
		}
		int ret = (int) (data/Math.pow(10.0, size -1));
		data -= Math.pow(10.0, size-1)* ret;		
		size--;
		return ret;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
}
