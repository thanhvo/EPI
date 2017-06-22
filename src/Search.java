import java.util.*;

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
	
	private static Pair<Integer, Integer> find_pair_using_comp(int[] A, int k, Comparator<Integer> comparator) {
		Pair<Integer, Integer> ret = new Pair(0, A.length -1);
		while (ret.first < ret.second && comparator.compare(A[ret.first], 0) > 0) {
			ret.first++;			
		}
		while (ret.first < ret.second && comparator.compare(A[ret.second], 0) > 0) {
			--ret.second;
		}
		while (ret.first < ret.second) {
			if (A[ret.first] + A[ret.second] == k) {
				return ret;
			} else if (comparator.compare(A[ret.first] + A[ret.second], k) > 0) {
				do {
					++ret.first;
				} while (ret.first < ret.second && comparator.compare(A[ret.first], 0) > 0); 
			} else {
				do {
					--ret.second;
				} while (ret.first < ret.second && comparator.compare(A[ret.second], 0) > 0);
			}
		}
		return new Pair(-1, -1);
	}
	
	private static Pair<Integer, Integer> find_pos_neg_pair(int[] A, int k) {
		Pair<Integer, Integer> ret= new Pair(A.length -1, A.length -1);
		while (ret.first >= 0 && A[ret.first] <0) {
			--ret.first;
		}
		while(ret.second >= 0 && A[ret.second] >= 0) {
			--ret.second;
		}
		while (ret.first >= 0 && ret.second >= 0) {
			if (A[ret.first] + A[ret.second] == k) {
				return ret;
			} else if (A[ret.first] + A[ret.second] > k) {
				do {
					--ret.first;
				} while (ret.first >= 0 && A[ret.first] < 0);
			} else {
				do {
					--ret.second;
				} while (ret.second >= 0 && A[ret.second] > 0);
			}
		}
		return new Pair(-1, -1);
	}
	
	public static Pair<Integer, Integer> find_pair_sum_k(int[] A, int k) {
		Comparator<Integer> less = (i1, i2) -> i2.compareTo(i1);
		Comparator<Integer> greater_equal = (i1, i2) -> {
			if (i1.compareTo(i2) >= 0) return 1;
			else return -1;
		};
		Pair<Integer, Integer> ret = find_pos_neg_pair(A, k);
		if (ret.first == -1 && ret.second == -1) {
			return k >= 0 ? find_pair_using_comp(A, k, less) : find_pair_using_comp(A, k, greater_equal);
		}
		return ret;
	}
	
	public static int min_cyclic_array(int[] a) {
		int start = 0, end = a.length -1;
		while (start < end) {
			if (a[start] < a[end]) {
				return start;
			}
			int mid = start + (end - start) / 2;
			if (a[mid] > a[end]) {
				start = mid +1;
			} else {
				end = mid;
			}
		}
		return start;
	}
	
	public static<T extends Comparable> int search_unknown_length_array(T[] A, T k) {
		int p = 0;
		while (true) {
			try {
				T val = A[(1 << p) -1];
				if (val.compareTo(k) == 0) {
					return (1 <<p) -1;
				} else if (val.compareTo(k) > 0) {
					break;
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				break;
			}
			++p;
		}
		int start = (1 << (p-1));
		int end = (1 << p) - 2;
		while (start <= end) {
			int mid = start + ((end - start) >> 1);
			try {
				if (A[mid].compareTo(k) < 0) {
					start = mid + 1;
				} else if (A[mid].compareTo(k) == 0) {
					return mid;
				} else {
					end = mid - 1;
				}
			} catch (ArrayIndexOutOfBoundsException ex) {
				end = mid -1;
			}
		}
		return -1;
	}
	
	private static double min(double[] a) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < a.length; i++) {
			if (a[i] < min) min = a[i];
		}
		return min;
	}
	
	private static double max(double[] a) {
		double max = Double.MIN_VALUE;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) max = a[i];
		}
		return max;
	}
	
	private static double getCapSum(double[] a, double cap) {
		double sum = 0.0;
		for (int i = 0; i < a.length; i++) {
			sum += Math.min(a[i], cap);
		}
		return sum;
	}
	
	public static double find_cut_off_cap(double[] a, double target) {
		double min = min(a);
		double max = max(a);
		if (getCapSum(a, max) < target)
			return -1.0;
		while (min < max) {
			double mid = min + (max -min) * 0.5;
			double sum = getCapSum(a, mid);
			if (sum == target) {
				return mid;
			} else if (sum < target) {
				min = mid;
			} else {
				max = mid;
			}
		}
		return min;
	}
	
	private static<T extends Comparable> T search_kth_element_helper(T[] A, int startA, T[] B, int startB, int k) {
		if (k <= 0)
			return null;
		if (k == 1) {
			if (A[startA].compareTo(B[startB]) > 0) {
				return B[startB];
			} else {
				return A[startA];
			}
		}
		int mid = k/2;
		if (A[startA + mid -1].compareTo(B[startB + mid -1]) < 0) {
			return search_kth_element_helper(A, startA + mid, B, startB, k - mid);
		} else {
			return search_kth_element_helper(A, startA, B, startB + mid, k- mid);
		}
	}
	
	public static<T extends Comparable> T search_kth_element(T[] A, T[] B, int k) {
		return search_kth_element_helper(A, 0, B, 0, k);
	}
	
	private static int compare(double a, double b) {
		double epsilon = 1E-6;
		double diff = (a -b)/b;
		return diff < -epsilon ? -1: diff > epsilon ? 1 : 0;
	}
	
	public static double square_root(double x) {
		double low, high;
		if (compare(x, 1.0) < 0) {
			low = x; high = 1.0;
		} else {
			low = 1.0; high = x;
		}
		while ( compare(low, high) == -1) {
			double mid = low + 0.5 * (high - low);
			double square_mid = mid * mid;
			if (compare(square_mid, x) == 0) {
				return mid;
			} else if (compare(square_mid, x) == 1) {
				high = mid;
			} else {
				low = mid;
			}
		}
		return low;
	} 
}
