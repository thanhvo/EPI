import java.util.*;

public class HeapUtil {	
	public static<T extends Comparable> List<T> merge_arrays(T[][] S) {
		Comparator<Pair<T, Integer>> comparator = 
				new Comparator<Pair<T, Integer>>() {
			@Override
			public int compare(Pair<T, Integer> p1, Pair<T, Integer> p2) {
				return (p1.first.compareTo(p2.first));
			}
		};
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
}
