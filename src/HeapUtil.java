import java.lang.reflect.Array;
import java.util.*;
import java.io.*;

public class HeapUtil {	
	
	public static<T extends Comparable> List<T> merge_arrays(T[][] S) {
		Comparator<Pair<T, Integer>> comparator = (p1, p2) -> p1.first.compareTo(p2.first);
		PriorityQueue<Pair<T, Integer>> min_heap = new PriorityQueue<Pair<T, Integer>>(10, comparator);
		int[] S_idx = new int[S.length];
		for (int i = 0; i < S.length; i++) {
			if (S[i].length > 0) {
				min_heap.add(new Pair<T, Integer>(S[i][0], i));
				S_idx[i] = 1;
			}
		}
		
		List<T> ret = new ArrayList<T>();
		while (!min_heap.isEmpty()) {
			Pair<T, Integer> p = min_heap.poll();
			ret.add(p.first);
			if (S_idx[p.second] < S[p.second].length) {
				min_heap.add(new Pair<T, Integer>(S[p.second][S_idx[p.second]++], p.second));				
			}
		}
		return ret;		
	}
	
	public static<T extends Comparable> T[] sort_k_up_down_array(T[] a) {
		int[] low = new int[a.length];
		int[] high = new int[a.length];		
		int j = 0, n = 0;
		while (j < a.length -1) {
			low[n] = j;
			while (j < (a.length -1) && a[j].compareTo(a[j+1]) <= 0) {
				j++;
			}
			while (j < (a.length -1) && a[j].compareTo(a[j+1]) >= 0) {
				j++;
			}
			high[n] = (j < (a.length -1)) ? j -1 : j;
			n++;
		}
		T[] b = (T[])Array.newInstance(a.getClass().getComponentType(), a.length);
		Comparator<Pair<T, Integer>> comparator = (p1, p2) -> p1.first.compareTo(p2.first);
		PriorityQueue<Pair<T, Integer>> min_heap = new PriorityQueue<Pair<T, Integer>>(n, comparator);
		
		/* Initialize the heap with the minimum values at both ends */
		for (int i = 0; i < n; i++) {
			if (a[low[i]].compareTo(a[high[i]]) <= 0) {
				min_heap.add(new Pair(a[low[i]],i));
				low[i]++;
			} else {
				min_heap.add(new Pair(a[high[i]], i));
				high[i]--;
			}
		}
		
		int idx = 0;
		while (idx < a.length && !min_heap.isEmpty()) {
			Pair<T, Integer> p = min_heap.poll();
			b[idx++] = p.first;
			int pos = p.second.intValue();
			if (low[pos] <= high[pos]) {
				if (a[low[pos]].compareTo(a[high[pos]]) <= 0) {
					min_heap.add(new Pair(a[low[pos]], pos));
					low[pos]++;
				} else {
					min_heap.add(new Pair(a[high[pos]], pos));
					high[pos]--;
				}
			}
		}
		return b;
	}
	
	public static <T extends Comparable> void print_kth_largest_item(ObjectInputStream input, OutputStream output, int k) 
			throws ClassNotFoundException, IOException {
		PriorityQueue<T> min_heap = new PriorityQueue<T>(k);
		ObjectOutputStream writer = new ObjectOutputStream(output);
		for (int i = 0; i < k; i++) {
			T item = (T)input.readObject(); 
			min_heap.add(item);
			//System.out.println(min_heap.peek());
			writer.writeObject(min_heap.peek());
		}
		
		while (true) {
			try {
				T item = (T)input.readObject();
				T top = min_heap.peek();
				if (item.compareTo(top) < 0) {
					//System.out.println(top);
					writer.writeObject(top);					
				} else {
					min_heap.poll();
					min_heap.add(item);
					//System.out.println(min_heap.peek());
					writer.writeObject(min_heap.peek());					
				}
			} catch (EOFException ex) {
				break;
			} finally {
				writer.flush();
			}
		}
	}
	
	public static<T extends Comparable> List<T> approximate_sort(List<T> list, int k) {
		PriorityQueue<T> min_heap = new PriorityQueue<T>(k);
		Iterator<T> it = list.iterator();
		for (int i = 0; i < k; i++) {
			min_heap.add(it.next());
		}
		List<T> ret = new ArrayList<T>();
		while (it.hasNext()) {
			ret.add(min_heap.poll());
			min_heap.add(it.next());
		}
		while(!min_heap.isEmpty()) {
			ret.add(min_heap.poll());
		}
		return ret;
	}
}
