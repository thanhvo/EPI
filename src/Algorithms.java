import java.util.*;

public class Algorithms {
	
	private static class Tuple<CoordType extends Comparable<CoordType>, HeightType extends Comparable<HeightType> > {
		public Skyline<CoordType, HeightType> a;
		public Iterator<Skyline<CoordType, HeightType>> a_it;
		public Skyline<CoordType, HeightType> b;
		public Iterator<Skyline<CoordType, HeightType>> b_it;
		public Tuple(Skyline<CoordType, HeightType> a, Iterator<Skyline<CoordType, HeightType>> a_it, Skyline<CoordType, HeightType> b, Iterator<Skyline<CoordType, HeightType>> b_it) {
			this.a = a;
			this.b = b;
			this.a_it = a_it;
			this.b_it = b_it;
		}
	}
	
	private static<CoordType extends Comparable<CoordType>, HeightType extends Comparable<HeightType>> 
		void merge_intersect_skylines(List<Skyline<CoordType, HeightType>> merged, Tuple<CoordType, HeightType> t) {
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
		Tuple<CoordType, HeightType> t = new Tuple<CoordType, HeightType>(null, L.iterator(), null, R.iterator());
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
}
