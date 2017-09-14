import java.util.*;

public class Algorithms {
	
	private static class SkylineTuple<CoordType extends Comparable<CoordType>, HeightType extends Comparable<HeightType> > {
		public Skyline<CoordType, HeightType> a;
		public Iterator<Skyline<CoordType, HeightType>> a_it;
		public Skyline<CoordType, HeightType> b;
		public Iterator<Skyline<CoordType, HeightType>> b_it;
		public SkylineTuple(Skyline<CoordType, HeightType> a, Iterator<Skyline<CoordType, HeightType>> a_it, Skyline<CoordType, HeightType> b, Iterator<Skyline<CoordType, HeightType>> b_it) {
			this.a = a;
			this.b = b;
			this.a_it = a_it;
			this.b_it = b_it;
		}
	}
	
	private static<CoordType extends Comparable<CoordType>, HeightType extends Comparable<HeightType>> 
		void merge_intersect_skylines(List<Skyline<CoordType, HeightType>> merged, SkylineTuple<CoordType, HeightType> t) {
		if (t.a.right.compareTo(t.b.right) <= 0) {
			if (t.a.height.compareTo(t.b.height) > 0) {
				if (t.b.right != t.a.right) {
					merged.add(t.a);
					t.b.left = t.a.right;
					if (t.a_it.hasNext()) t.a = t.a_it.next(); 
					else t.a = null;
				} else {
					if (t.b_it.hasNext()) t.b = t.b_it.next();
					else t.b = null;
				}
			} else if (t.a.height.compareTo(t.b.height) == 0) {
				t.b.left = t.a.left;
				if (t.a_it.hasNext()) t.a = t.a_it.next();
				else t.a = null;
			} else { // a.height < b.height
				if (t.a.left != t.b.left) {
					merged.add(new Skyline<CoordType, HeightType>(t.a.left, t.b.left, t.a.height));
				}
				if (t.a_it.hasNext()) t.a = t.a_it.next();
				else t.a = null;
			}
		} else { // a.right > b.right
			if (t.a.height.compareTo(t.b.height) >= 0) {
				if (t.b_it.hasNext()) t.b = t.b_it.next();
				else t.b = null;
			} else {
				if (t.a.left != t.b.left) {
					merged.add(new Skyline<CoordType, HeightType>(t.a.left, t.b.left, t.a.height));
				}
				t.a.left = t.b.right;
				merged.add(t.b);
				if (t.b_it.hasNext()) t.b = t.b_it.next();
				else t.b = null;
			}			
		}		
	}
	
	private static<CoordType extends Comparable<CoordType>, HeightType extends Comparable<HeightType>> List<Skyline<CoordType, HeightType>> 
		merge_skylines(List<Skyline<CoordType, HeightType>> L, List<Skyline<CoordType, HeightType>> R) {
		SkylineTuple<CoordType, HeightType> t = new SkylineTuple<CoordType, HeightType>(null, L.iterator(), null, R.iterator());
		List<Skyline<CoordType, HeightType>> merged = new ArrayList<Skyline<CoordType, HeightType>>();
		t.a = t.a_it.next();
		t.b = t.b_it.next();
		while (t.a != null && t.b != null) {			 
			if (t.a.right.compareTo(t.b.left) < 0) {
				merged.add(t.a);
				if (t.a_it.hasNext()) t.a = t.a_it.next();
				else t.a = null;
			} else if (t.b.right.compareTo(t.a.left) < 0) {
				merged.add(t.b);
				if (t.b_it.hasNext()) t.b = t.b_it.next();
				else t.b = null;
			} else if (t.a.left.compareTo(t.b.left) <= 0) {
				merge_intersect_skylines(merged, t);				
			} else {
				Skyline<CoordType, HeightType> temp = t.a;
				Iterator<Skyline<CoordType, HeightType>> temp_it = t.a_it;
				t.a = t.b;
				t.a_it = t.b_it;
				t.b = temp;
				t.b_it = temp_it;
				merge_intersect_skylines(merged, t);				
			}
		}
		
		if (t.a != null) merged.add(t.a);
		if (t.b != null) merged.add(t.b);
		
		while (t.a_it.hasNext()) merged.add(t.a_it.next());
		while (t.b_it.hasNext()) merged.add(t.b_it.next());
		return merged;
	}
	
	private static <CoordType extends Comparable, HeightType extends Comparable> List<Skyline<CoordType, HeightType>>
		draw_skylines_helper(List<Skyline<CoordType, HeightType>> skylines, int start, int end) {
		if (end - start <= 1) { // 0 or 1 skyline, just copy it
			ArrayList<Skyline<CoordType, HeightType>> list = new ArrayList<Skyline<CoordType, HeightType>>();
			if (end == start +1) list.add(skylines.get(start));
			return list;
		}
		int mid = start + ((end - start) >> 1);
		List<Skyline<CoordType, HeightType>> L = draw_skylines_helper(skylines, start, mid);
		List<Skyline<CoordType, HeightType>> R = draw_skylines_helper(skylines, mid, end);
		return merge_skylines(L, R);
	}
	
	public static <CoordType extends Comparable, HeightType extends Comparable>
		List<Skyline<CoordType, HeightType>> draw_skylines(List<Skyline<CoordType, HeightType>> skylines) {
		return draw_skylines_helper(skylines, 0, skylines.size());
	}
	
	private static<T extends Comparable<T>> int merge(List<T> A, int start, int mid, int end) {
		List<T> sorted_A = new ArrayList<T>();
		int left_start = start, right_start = mid, invert_count = 0;
		while (left_start < mid && right_start < end) {
			if (A.get(left_start).compareTo(A.get(right_start)) <= 0) {
				sorted_A.add(A.get(left_start++));
			} else {
				// A[left_start : mid -1] will be the inversions
				invert_count += mid - left_start;
				sorted_A.add(A.get(right_start++));
			}
		}
		sorted_A.addAll(A.subList(left_start, mid));
		sorted_A.addAll(A.subList(right_start, A.size()));
		for (int i = 0; i < end - start; i++)
			A.set(i + start, sorted_A.get(i));
		return invert_count;
	}
	
	public static<T extends Comparable<T>> int count_inversions_helper(List<T> A, int start, int end) {
		if (end - start <= 1) {
			return 0;
		}
		int mid = start + ((end - start) >> 1);
		return count_inversions_helper(A, start, mid) + count_inversions_helper(A, mid, end) + merge(A, start, mid, end);
	}
	
	public static<T extends Comparable<T>> int count_inversions(List<T> A) {
		return count_inversions_helper(A, 0, A.size());
	}
	
	public static double distance (Point a, Point b) {
		return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
	}
	
	// Return the closest two points and its distance as a tuple
	public static Tuple<Point, Point, Double> brute_force(ArrayList<Point> P, int s, int e) {
		Tuple<Point, Point, Double> ret = new Tuple<Point, Point, Double>();
		ret.third = Double.MAX_VALUE;
		for (int i = s; i < e; ++i) {
			for (int j = i + 1; j < e; ++j) {
				double dis = distance(P.get(i), P.get(j));
				if (dis < ret.third) {
					ret.first = P.get(i);
					ret.second = P.get(j);
					ret.third = dis; 
				}
			}
		}
		return ret;		
	}
	
	// Return the closest two points and its distance as a tuple
	private static Tuple<Point, Point, Double> find_closest_pair_in_remain(ArrayList<Point> P, double d) {
		Comparator<Point> comparator = (p1, p2)->(p1.y - p2.y);
		Collections.sort(P, comparator);
		// At most six points in P
		Tuple <Point, Point, Double> ret = new Tuple<Point, Point, Double>(null, null, Double.MAX_VALUE);
		for (int i = 0; i < P.size(); ++i) {
			for (int j = i + 1; j < P.size() && P.get(j).y - P.get(i).y < d; ++j) {
				double dis = distance(P.get(i), P.get(j));
				if (dis < ret.third) {
					ret.first = P.get(i);
					ret.second = P.get(j);
					ret.third = dis;
				}
			}
		}
		return ret;
	}
	
	// Return the closest two points and its distance as a tuple 
	private static Tuple<Point, Point, Double> find_closest_pair_points_helper(ArrayList<Point> P, int s, int e) {
		if (e - s <= 3) { // brute-force to find answer if there are <= 3 points
			return brute_force(P, s, e);
		}
		int mid = (s + e) >> 1;
		Tuple<Point, Point, Double> l_ret = find_closest_pair_points_helper(P, s, mid);
		Tuple<Point, Point, Double> r_ret = find_closest_pair_points_helper(P, mid, e);
		Tuple<Point, Point, Double> min_l_r = (l_ret.third < r_ret.third) ? l_ret : r_ret;
		ArrayList<Point> remain = new ArrayList<Point>(); // stores the points whose x - dis < min_d
		for (Point p : P) {
			if (Math.abs(p.x - P.get(mid).x) < min_l_r.third) {
				remain.add(p);
			}
		}
		Tuple<Point, Point, Double> mid_ret = find_closest_pair_in_remain(remain, min_l_r.third);
		return mid_ret.third < min_l_r.third ? mid_ret : min_l_r;
	}
	
	public static Pair<Point, Point> find_closest_pair_points(ArrayList<Point> P) {
		Comparator<Point> compartor = (p1, p2) -> p1.x - p2.x;
		Collections.sort(P, compartor);
		Tuple<Point, Point, Double> ret = find_closest_pair_points_helper(P, 0, P.size());
		return new Pair(ret.first, ret.second);
	}
	
	private static Pair<Double, Double> compute_height_and_diameter(WeightedTreeNode r) {
		if (r == null || r.edges == null) {
			return new Pair(0.0, 0.0);
		}
		double diameter = Double.MIN_VALUE;
		double[] heights = new double[2]; // Stores the max 2 heights
		for (Pair<WeightedTreeNode, Double> e : r.edges) {
			Pair<Double, Double> h_d = compute_height_and_diameter(e.first);
			if (h_d.first + e.second > heights[0]) {
				heights[1] = heights[0];
				heights[0] = h_d.first + e.second;
			} else if (h_d.first + e.second > heights[1]) {
				heights[1] = h_d.first + e.second;
			}
			diameter = Double.max(diameter, h_d.second);
		}
		return new Pair(heights[0], Double.max(diameter, heights[0] + heights[1]));
	}
	
	public static double compute_diameter(WeightedTreeNode T) {
		return T != null ? compute_height_and_diameter(T).second : 0.0;
	}
	
	public static Pair<Integer, Integer> find_maximum_subarray(int[] A) {
		// A[range.first : range.second] will be the maximum subarray
		Pair<Integer, Integer> range = new Pair(0, 0);
		int min_idx = -1;
		int min_sum = 0, sum = 0;
		int max_sum = Integer.MIN_VALUE;
		for (int i = 0; i < A.length; ++i) {
			sum += A[i];
			if (sum < min_sum) {
				min_sum = sum; 
				min_idx = i;
			}
			if (sum - min_sum > max_sum) {
				max_sum = sum - min_sum;
				range.first = min_idx +1;
				range.second = i;
			}
		}
		return range;
	}
	
	// Calculate the non-circular solution
	private static int find_max_subarray(int[] A) {
		int maximum_till = 0, maximum = 0;
		for (int a : A) {
			maximum_till = Math.max(a, a + maximum_till);
			maximum = Math.max(maximum, maximum_till);
		}
		return maximum;
	}
	
	// Calculate the solution which is circular
	private static int find_circular_max_subarray(int[] A) {
		// Maximum subarray sum starts at index 0 and ends at or before index i
		int[] maximum_begin = new int[A.length];
		int sum = A[0];
		maximum_begin[0] = sum;
		for (int i = 1; i < A.length; ++i) {
			sum += A[i];
			maximum_begin[i] = Math.max(maximum_begin[i - 1], sum);
			
		}
		// Maximum subarray sum starts index i + 1 and ends at the last element
		int[] maximum_end = new int[A.length];
		maximum_end[A.length -1] = 0;
		sum = 0;
		for (int i = A.length - 2; i >= 0; --i) {
			sum += A[i + 1];
			maximum_end[i] = Math.max(maximum_end[i + 1], sum);
		}
		// Calculate the maximum subarray which is circular 
		int circular_max = 0;
		for (int i = 0; i < A.length; ++i) {
			circular_max = Math.max(circular_max, maximum_begin[i] + maximum_end[i]);
		}
		return circular_max;
	}
	
	public static int max_subarray_sum_in_circular(int[] A) {
		return Math.max(find_max_subarray(A), find_circular_max_subarray(A));
	}
	
	private static interface Comp{
		int op(int a, int b);
	}
	
	private static int find_optimum_subarray_using_comp(int[] A, Comp comp) {
		int till = 0, overall = 0;
		for (int a : A) {
			till = comp.op(a, a + till);
			overall = comp.op(overall, till);
		}
		return overall;
	}
	
	private static int sum(int[] A) {
		int sum = 0;
		for (int a : A) sum += a;
		return sum;
	}
	
	public static int max_subarray_circular(int[] A) {
		Comp min = (a, b) -> a <= b ? a : b;
		Comp max = (a, b) -> a >= b ? a : b;
		// Find the max in non-circular case and circular case
		return max.op(find_optimum_subarray_using_comp(A, max),
			sum(A) - find_optimum_subarray_using_comp(A, min)); // circular case
	}
	
	public static<T extends Comparable<T>> List<T> longest_nondecreasing_subsequence(List<T> A) {
		// Empty array
		if (A.isEmpty()) return A;
		int[] longest_length = new int[A.size()];
		for (int i = 0; i < longest_length.length; i++) longest_length[i] = 1;
		int[] previous_index = new int[A.size()];
		for (int i = 0; i < previous_index.length; i++) previous_index[i] = -1;
		int max_length_idx = 0;
		for (int i = 0; i < A.size(); ++i) {
			for (int j = 0; j < i; ++j) {
				if (A.get(i).compareTo(A.get(j)) >= 0 && longest_length[j] + 1 > longest_length[i]) {
					longest_length[i] = longest_length[j] + 1;
					previous_index[i] = j;
				}
			}
			// Record the index where longest subsequence ends
			if (longest_length[i] > longest_length[max_length_idx]) {
				max_length_idx = i;
			}
		}
		// Build the longest nondecreasing subsequence 
		int max_length = longest_length[max_length_idx];
		List<T> ret = new ArrayList<T>(max_length);
		for (int i = 0; i < max_length; i++) {
			ret.add(0, A.get(max_length_idx));
			max_length_idx = previous_index[max_length_idx];			
		}
		return ret;
	}
	
	// Find the first element which is greater than a given value in a sorted list 
	private static <T extends Comparable<T>> int upper_bound(List<T> A, T a) {
		if (A == null || A.isEmpty()) return 0;
		if (A.get(A.size() - 1).compareTo(a) < 0) return A.size();
		int start = 0, end = A.size();
		while (start < end) {
			int mid = start + ((end -start) >> 1);
			if (A.get(mid).compareTo(a) > 0) {
				end = mid;				
			} else {
				start = mid +1;				
			}
		}
		return end;
	}
	
	private static int upper_bound(int[] A, int a) {
		if (A == null || A.length == 0) return 0;
		if (A[A.length - 1] < a) return A.length;
		int start = 0, end = A.length;
		while (start < end) {
			int mid = start + ((end -start) >> 1);
			if (A[mid] > a) {
				end = mid;				
			} else {
				start = mid +1;				
			}
		}
		return end;
	}
	
	
	public static<T extends Comparable<T>> int longest_nondecreasing_subsequence2(List<T> A) {
		List<T> tail_values = new ArrayList<T>();
		for ( T a : A) {
			int idx = upper_bound(tail_values, a);
			if (idx == tail_values.size()) {
				tail_values.add(a);
			} else {
				tail_values.set(idx, a);
			}
		}
		return tail_values.size();
	}
	
	public static Pair<Integer, Integer> find_longest_subarray_less_equal_k(int[] A, int k) {
		// Build the prefix sum according to A
		int[] prefix_sum = new int[A.length];
		int[] min_prefix_sum = new int[A.length];
		prefix_sum[0] = A[0];
		for (int i = 1; i < A.length; i++) {
			prefix_sum[i] = prefix_sum[i-1] + A[i];
			min_prefix_sum[i] = prefix_sum[i];
		}
		for (int i = min_prefix_sum.length - 2; i >= 0; --i) {
			min_prefix_sum[i] = Math.min(min_prefix_sum[i], min_prefix_sum[i+1]);
		}
		Pair<Integer, Integer> arr_idx = new Pair(0, upper_bound(min_prefix_sum, k) - 1);
		for (int i = 0; i < prefix_sum.length; ++i) {
			int idx = upper_bound(min_prefix_sum, k + prefix_sum[i]) - 1;
			if (idx - i - 1 > arr_idx.second - arr_idx.first) {
				arr_idx.first = i + 1;
				arr_idx.second = idx;
			}
		}
		return arr_idx;
	}
	
	public static int calculate_largest_rectangle(int[] A) {
		// Calculate L
		Stack<Integer> S = new Stack<Integer>();
		int[] L = new int[A.length];
		for (int i = 0; i < A.length; ++i) {
			while (!S.isEmpty() && A[S.peek()] >= A[i]) {
				S.pop();
			}
			L[i] = (S.isEmpty() ? -1 : S.peek());
			S.add(i);
		}
		// Clear stack for calculating R
		while (!S.isEmpty()) {
			S.pop();
		}
		int[] R = new int[A.length];
		for (int i = A.length -1; i >= 0; --i) {
			while (!S.isEmpty() && A[S.peek()] >= A[i]) {
				S.pop();
			}
			R[i]= S.isEmpty() ? A.length : S.peek();
			S.add(i);
		}
		// For each A[i], find its maximum area include it
		int max_area = 0;
		for (int i = 0; i < A.length; ++i) {
			max_area = Math.max(max_area, A[i]*(R[i] - L[i] -1));
		}
		return max_area;
	}
	
	private static class MaxHW {
		public int h,w ;
		public MaxHW(int h, int w) {
			this.h = h;
			this.w = w;
		}
	}
	
	public static int max_rectangle_submatrix(boolean [][] A) {
		// DP table stores (h, w) for each (i, j)
		MaxHW[][] table = new MaxHW[A.length][];
		for (int i = 0; i < table.length; i++)
			table[i] = new MaxHW[A[0].length];
		for (int i = A.length -1; i >= 0; --i) {
			for (int j = A[i].length -1; j >= 0; --j) {
				// Find the largest h such that (i, j) to (i+h-1, j) are feasible
				// Find the largest w such that (i,j) to (i, j+w-1) are feasible
				table[i][j] = A[i][j] ? new MaxHW(i + 1 < A.length ? table[i+1][j].h + 1 : 1, 
						j +1 < A[i].length ? table[i][j+1].w +1 : 1) : new MaxHW(0,0);			
			}
		}
		int max_rect_area = 0;
		for (int i = 0; i< A.length; ++i) {
			for (int j = 0; j < A.length; ++j) {
				// Process (i, j) if it is feasible and is possible to update max_rect_area
				if (A[i][j] && table[i][j].w * table[i][j].h > max_rect_area) {
					int min_width = Integer.MAX_VALUE;
					for (int a = 0; a < table[i][j].h; ++a) {
						min_width = Math.min(min_width, table[i+a][j].w);
						max_rect_area = Math.max(max_rect_area, min_width * (a +1));
					}
				}
			}
		}
		return max_rect_area;
	}
	
	public static int max_square_submatrix(boolean[][] A) {
		// DP table stores (h, w) for each (i, j)
		MaxHW[][] table = new MaxHW[A.length][];
		for (int i = 0; i < table.length; i++) 
			table[i] = new MaxHW[A[0].length];
		for (int i = A.length -1; i >= 0; --i) {
			for (int j = A[0].length - 1; j >= 0; --j) {
				// Find the largest h such that (i, j) to (i +h -1, j) are feasible
				// Find the largest w such that (i, j) to (i, j + w -1) are feasible
				table[i][j] = A[i][j] ? new MaxHW(i +1 < A.length ? table[i +1][j].h + 1 : 1,
						j + 1 < A.length ? table[i][j+1].w + 1: 1) : new MaxHW(0, 0);
			}
		}
		// A table stores the length of largest square for each (i, j) 
		int[][] s = new int[A.length][];
		for (int i = 0; i < A.length; ++i) {
			s[i] = new int[A[0].length];
		}
		int max_square_area = 0;
		for (int i = A.length - 1; i >= 0; --i) {
			for (int j = A[i].length -1; j >= 0; --j) {
				int side = Math.min(table[i][j].h, table[i][j].w);
				if (A[i][j]) {
					// Get the length of largest square with bottom-left corner (i, j)
					if ( i + 1 < A.length && j + 1 < A[i +1].length) {
						side = Math.min(s[i +1][j+1] + 1, side);
					}
					s[i][j] = side;
					max_square_area = Math.max(max_square_area, side * side);
				}
			}
		}
		return max_square_area;
	}
	
	public static int max_rectangle_submatrix2(boolean [][] A) {
		int[][] table = new int[A.length][];
		for (int i = 0; i < table.length; i++) {
			table[i] = new int[A[i].length];
		}
		for (int i = A.length -1; i >= 0; --i) {
			for (int j = A[i].length -1; j >= 0; --j) {
				table[i][j] = A[i][j] ? (i +1) < A.length ? table[i+1][j] + 1 : 1 : 0;
			}
		}
		// Find the max among all instances of the largest rectangle
		int max_rect_area = 0;
		for (int[] t : table) {
			max_rect_area = Math.max(max_rect_area, calculate_largest_rectangle(t));
		}
		return max_rect_area;
	}
	
	private static List<Pair<Integer, Integer>> sequence_with_starting_point(int[][] A, int i, int j, int[] S, int start) {
		if (start == S.length) {
			return new ArrayList<Pair<Integer, Integer>>();			
		}
		if (i < 0 || i >= A.length || j < 0 || j >= A[i].length || A[i][j] != S[start]) {
			return null;
		}
		List<Pair<Integer, Integer>> list = null;
		list = sequence_with_starting_point(A, i -1, j, S, start +1);
		if (list == null) list = sequence_with_starting_point(A, i +1, j, S, start +1);
		if (list == null) list = sequence_with_starting_point(A, i, j -1, S, start +1);
		if (list == null) list = sequence_with_starting_point(A, i, j+ 1, S, start +1);
		if (list == null) return null;
		list.add(0, new Pair(i, j));
		return list;
	}
	
	public static List<Pair<Integer, Integer>> sequence(int[][] A, int[] S) {
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A.length; j++) {
				List<Pair<Integer, Integer>> list = sequence_with_starting_point(A, i, j, S, 0);
				if (list != null) return list;
			}
		}
		return null;
	}
	
	public static int leveshtein_distance(String A, String B) {
		// Try to reduce the space usage
		if (A.length() < B.length()) {
			String temp = A;
			A = B;
			B = temp;
		}
		int[] D = new int[B.length() + 1];
		// Initialization
		for (int i =0; i < D.length; i++)
			D[i] = i;
		for (int i = 1; i <= A.length(); ++i) {
			int pre_i_1_j_1 = D[0];
			// Stores the value of D[i-1][j-1]
			D[0] = i;
			for (int j = 1; j <= B.length(); ++j) {
				int pre_i_1_j = D[j];
				// Stores the value of D[i-1][j]
				D[j] = A.charAt(i-1) == B.charAt(j-1) ? pre_i_1_j_1 : 1 + Math.min(pre_i_1_j_1, Math.min(D[j-1], D[j]));
				// Previous D[j-1][j] will become next D[i-1][j-1]
				pre_i_1_j_1 = pre_i_1_j;
			}
		}
		return D[D.length -1];
	}
	
	public static List<String> break_words(String word, List<String> dict) {
		int MAX_LENGTH = 20;
		for (int i = 0; i < Math.min(MAX_LENGTH, word.length()); i++) {
			String prefix = word.substring(0, i +1);
			List<String> words;
			if (dict.contains(prefix)) {
				if (i == word.length() -1) words = new ArrayList<String>(); 
				else words = break_words(word.substring(i+1), dict);
				if (words != null) {
					words.add(0, prefix);
					return words;
				}
			}
		}
		return null;
	}
	
	public static long find_pretty_printing(List<String> W, int L) {
		// Calculate M(i)
		long[] M = new long[W.size()];
		for (int i = 0; i < M.length; i++) M[i] = Long.MAX_VALUE;
		for (int i = 0; i < W.size(); ++i) {
			int b_len = L - W.get(i).length();
			M[i] = Math.min((i-1 < 0 ? 0 : M[i-1]) + (1 << b_len), M[i]);
			for (int j = i -1; j >= 0; --j) {
				b_len -= (W.get(j).length() + 1);
				if (b_len < 0) break;
				M[i] = Math.min((j -1 < 0 ? 0 : M[j-1]) + (1 << b_len), M[i]);
			}
		}
		// Find the minimum cost without considering the last line
		long min_mess = (W.size() >= 2 ? M[W.size() - 2]: 0);
		int b_len = L - W.get(W.size() -1).length();
		for ( int i = W.size() -2; i >= 0; --i) {
			b_len -= (W.get(i).length() + 1);
			if (b_len < 0) return min_mess;
			min_mess = Math.min(min_mess, (i -1 < 0 ? 0 : M[i-1]));
		}
		return min_mess;
	}
	
	public static int compute_binomial_coefficients(int n, int k) {
		int[][] table = new int[n+1][k+1];
		// Basic case: C(i,0) = 1
		for (int i = 0; i <= n; ++i) {
			table[i][0] = 1;
		}
		// Basic case: C(i,i) = 1
		for (int i = 1; i <= k; ++i) {
			table[i][i] = 1;
		}
		// C(i,j) = C(i-1, j) + C(i-1, j-1)
		for (int i = 2; i <= n; ++i) {
			for (int j = 1; j <i && j <= k; ++j){
				table[i][j] = table[i-1][j] + table[i-1][j-1];
			}
		}
		return table[n][k];
	}
	
	private static int num_of_plays(int S, int[] W, int k) {
		if (k == 0) {
			if (S % W[0] == 0) return 1;
			else return 0;
		}
		int num = 0;
		for (int i = 0; i <= S/W[k]; i++) {
			num+= num_of_plays(S - i*W[k], W, k -1);
		}
		return num;
	}
	
	public static int num_of_plays(int S, int[] W) {
		return num_of_plays(S, W, W.length -1);
	}
	
	public static int count_combinations(int k, int[] score_ways) {
		int[] combinations = new int[k + 1];
		combinations[0] = 1; // 1 way to reach 0
		for (int score: score_ways) {
			for (int j = score; j <= k; ++j) {
				combinations[j] += combinations[j - score];
			}
		}
		return combinations[k];
	}
	
	public static int count_permutations(int k, int[] score_ways) {
		int[] permutations = new int[k+1];
		permutations[0] = 1; // 1 way to reach 0
		for (int i = 0; i <= k; i++) {
			for (int score : score_ways) {
				if (i >= score) {
					permutations[i] += permutations[i - score];
				}
			}
		}
		return permutations[k];
	}
	
	public static int number_of_ways(int n, int m) {
		int[][] A = new int[n][m];
		A[0][0] = 1; // 1 way to start from (0,0)
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				A[i][j] += ( i < 1 ? 0 : A[i-1][j]) + (j < 1 ? 0 : A[i][j-1]);
			}
		}
		return A[n-1][m-1];
	}
	
	// Given the dimensions of A, n, and m, and B, return the number of ways from (0,0) to (n-1, m-1)
	// considering obstacles
	public static int number_of_ways_with_obstacles(int m, int n, boolean[][] B) {
		int[][] A = new int[n][m];
		// No way to start from (0,0) if B[0][0] == true
		A[0][0] = B[0][0] ? 0 : 1;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				if (!B[i][j]) {
					A[i][j] += (i < 1 ? 0 : A[i-1][j]) + ( j < 1 ? 0 : A[i][j-1]);
				}
			}
		}
		return A[m-1][n-1];
	}
	
	// Find the optimum path for a fisherman 
	public static int optimum_fisherman_path(int[][] A, int m, int n) {
		int[][] max_values = new int[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				max_values[i][j] = Math.max(i > 0 ? max_values[i-1][j]: 0, j > 0 ? max_values[i][j-1] : 0) + A[i][j];
			}
		}
		return max_values[m-1][n-1];
	}
	
	public static int pick_up_coins_helper(int[] C, int a, int b, int[][] T) {
		if (a > b) {
			return 0; // base condition			
		}
		if (T[a][b] == -1) {
			T[a][b] = Math.max(C[a] + Math.min(pick_up_coins_helper(C, a +2, b, T), pick_up_coins_helper(C, a+1, b-1, T)), 
					C[b] + Math.min(pick_up_coins_helper(C, a+1, b-1, T), pick_up_coins_helper(C, a, b-2, T)));
		}
		return T[a][b];
	}
	
	public static int pick_up_coins(int[] C) {
		int[][] T = new int[C.length][C.length];
		for (int i = 0; i < C.length; i++)
			for (int j = 0; j < C.length; j++)
				T[i][j] = -1;
		return pick_up_coins_helper(C, 0, C.length-1, T);
	}
	
	private static int minimize_power_helper(TreeNode root, HashMap<TreeNode, Integer> power_table) {
		if (power_table.containsKey(root)) return power_table.get(root);
		int val1 = 2*(root.children.size() + 1);
		for (TreeNode child: root.children) {
			val1 += minimize_power(child);
		}
		int val2 = root.children.size() + 1;
		for (TreeNode child: root.children) {
			val2 += 2 * (child.children.size() + 1);
			for (TreeNode grandChild : child.children) {
				val2 += minimize_power(grandChild);
			}
		}
		int ret = Math.min(val1, val2);
		power_table.put(root, ret);
		return ret;
	}
	
	public static int minimize_power(TreeNode root) {
		HashMap<TreeNode, Integer> power_table = new HashMap<TreeNode, Integer>();
		return minimize_power_helper(root, power_table);
	}
	
	private static boolean is_monochromatic(int[][] image_sum, ImagePoint lower_left, ImagePoint upper_right) {
		int pixel_sum = image_sum[upper_right.i][upper_right.j];
		if (lower_left.i >= 1) {
			pixel_sum -= image_sum[lower_left.i -1][upper_right.j];
		}
		if (lower_left.j >= 1) {
			pixel_sum -= image_sum[upper_right.i][lower_left.j -1];
		}
		if (lower_left.i >= 1 && lower_left.j >= 1) {
			pixel_sum += image_sum[lower_left.i -1][lower_left.j - 1];
		}
		return pixel_sum == 0 || // totally white
			   pixel_sum == (upper_right.i - lower_left.i + 1) * (upper_right.j - lower_left.j + 1); // totally black
	}
	
	private static ImageTreeNode calculate_optimal_2D_tree_helper(int[][] image, int[][] image_sum, 
			ImagePoint lower_left, ImagePoint upper_right,
			HashMap<ImagePoint, HashMap<ImagePoint, ImageTreeNode>> table) {
		// Illegal rectangle region, returns empty node 
		if (lower_left.compareTo(upper_right) > 0) {
			return new ImageTreeNode(0, lower_left, upper_right);
		}
		if (!table.containsKey(lower_left) || !table.get(lower_left).containsKey(upper_right)) {
			if (is_monochromatic(image_sum, lower_left, upper_right)) {
				ImageTreeNode p = new ImageTreeNode(1, lower_left, upper_right);
				if (!table.containsKey(lower_left)) {
					table.put(lower_left, new HashMap<ImagePoint, ImageTreeNode>());
				}
				table.get(lower_left).put(upper_right, p);
			} else {
				ImageTreeNode p = new ImageTreeNode(Integer.MAX_VALUE, lower_left, upper_right);
				for (int s = lower_left.i;  s <= upper_right.i; ++s) {
					for (int t = lower_left.j; t <= upper_right.j; ++t) {
						if (s == lower_left.i && t == lower_left.j) continue;
						List<ImageTreeNode> children = new ArrayList<ImageTreeNode>();
						// SW rectangle
						children.add(calculate_optimal_2D_tree_helper(image, image_sum, lower_left, new ImagePoint(s-1, t-1),table));
						// NW rectangle
						children.add(calculate_optimal_2D_tree_helper(image, image_sum, new ImagePoint(lower_left.i, t), new ImagePoint(s-1, upper_right.j), table));
						// NE rectangle
						children.add(calculate_optimal_2D_tree_helper(image, image_sum, new ImagePoint(s,t), upper_right, table));
						// SE rectangle
						children.add(calculate_optimal_2D_tree_helper(image, image_sum, new ImagePoint(s, lower_left.j), new ImagePoint(upper_right.i, t-1),table));
						int node_num = 1; //itself
						for (ImageTreeNode child : children) {
							//child.print();
							node_num += child.node_num;
							// Remove the child contains no node
							if (child.node_num == 0) child = null;
						}
						if (node_num < p.node_num) {
							p.node_num = node_num;
							p.children = children;
						}						
					}					
				}
				if (!table.containsKey(lower_left)) table.put(lower_left, new HashMap<ImagePoint, ImageTreeNode>());
				table.get(lower_left).put(upper_right, p);				
			}
		}
		ImageTreeNode p = table.get(lower_left).get(upper_right);
		return p;
	}
	
	public static ImageTreeNode calculate_optimal_2D_tree(int[][] image) {
		int[][] image_sum = image.clone();
		for (int i = 0; i < image.length; ++i) {
			for (int j = 1; j < image[i].length; j++) {
				image_sum[i][j] += image_sum[i][j-1];
			}
			for (int j = 0; i > 0 && j <image[i].length; ++j) {
				image_sum[i][j] += image_sum[i-1][j];
			}
		}
		HashMap<ImagePoint, HashMap<ImagePoint, ImageTreeNode>> table = new HashMap<ImagePoint, HashMap<ImagePoint, ImageTreeNode>>();
		return calculate_optimal_2D_tree_helper(image, image_sum, new ImagePoint(0,0), new ImagePoint(image.length - 1, image[0].length -1), table);
	}
}
