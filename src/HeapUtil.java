import java.lang.reflect.Array;
import java.util.*;

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
	
	public static<T extends Comparable> T[] sort_k_up_down_array(T[] a, int k) {
		int n = (k +1)/2;
		int[] low = new int[n];
		int[] high = new int[n];
		int j = 0;
		for (int i = 0; i < n; i++) {
			low[i] = j;
			while (j < (a.length -1) && a[j].compareTo(a[j+1]) <= 0) {
				j++;
			}
			while (j < (a.length -1) && a[j].compareTo(a[j+1]) >= 0) {
				j++;
			}
			high[i] = (i < n-1) ? j -1 : j;			
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
}
