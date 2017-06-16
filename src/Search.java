
public class Search {
	public static<T extends Comparable> int find(T[] a, T x) {
		if (a == null || a.length == 0)
			return -1;
		int start = 0;
		int end = a.length-1;
		int res = -1;
		while (start <= end) {
			int mid = start + ((end - start) >> 1);
			if (a[mid].compareTo(x) < 0) {
				start = mid +1;
			} else if (a[mid].compareTo(x) > 0) {
				end = mid -1;
			} else {
				res = mid;
				end = mid -1;
			}
		}
		return res;
	}
}
