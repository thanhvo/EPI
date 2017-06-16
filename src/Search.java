
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
	
	public static<T extends Comparable> int first_larger_element(T[] a, T x) {
		int start = 0;
		int end = a.length -1;
		int res = -1;
		while (start <= end) {
			int mid = start + ((end- start) >> 1);
			if (a[mid].compareTo(x) <= 0) {
				start = mid +1;
			} else {
				res = mid;
				end = mid - 1;
			}
		}
		return res;
	}
	
	public static int find_element_equals_to_its_index(int[] a) {
		int start = 0;
		int end = a.length -1;
		while (start <= end) {
			int mid = start + ((end - start) >> 1);
			if (a[mid] == mid) {
				return mid;
			} else if (a[mid] < mid) {
				start = mid + 1;
			} else {
				end = mid -1;
			}
		}
		return -1;
	}
}
