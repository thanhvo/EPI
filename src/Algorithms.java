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
	
	private static Pair<Double, Double> compute_height_and_diameter(TreeNode r) {
		if (r == null || r.edges == null) {
			return new Pair(0.0, 0.0);
		}
		double diameter = Double.MIN_VALUE;
		double[] heights = new double[2]; // Stores the max 2 heights
		for (Pair<TreeNode, Double> e : r.edges) {
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
	
	public static double compute_diameter(TreeNode T) {
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
}
