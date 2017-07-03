import java.util.*;

public class LRUCache <K, V>{
	HashMap<K, V> cache;
	Queue<K> IdList;
	int capacity;
	
	public LRUCache(int capacity) {
		this.capacity = capacity;
		cache = new HashMap<K, V>();
		IdList = new LinkedList();
	}
	
	V get(K key) {
		if (cache.containsKey(key)) {
			V val = cache.get(key);
			IdList.remove(key);
			IdList.add(key);
			return val;
		}
		return null;
	}
	
	void add(K key, V value) {
		if (IdList.size() == capacity) {
			K removed_id = IdList.poll();
			cache.remove(removed_id);
		}
		IdList.add(key);
		cache.put(key, value);
	}

}
