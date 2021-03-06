import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class SortTest {
	@Test
	public void test_indirect_sort() {
		String input = "/home/thanh/workspace/EPI/input/A Patriotic Fourth: What Does That Mean Now? - The New York Times.txt";
	    String output = "/home/thanh/workspace/EPI/output/A Patriotic Fourth: What Does That Mean Now? - The New York Times_2.txt";
	    Sort.indirect_sort(input, output);
	}
	
	@Test
	public void test_counting_sort() {
		ArrayList<Person<Integer>> people = new ArrayList<Person<Integer>>();
		people.add(new Person<Integer>(2,"Thanh"));
		people.add(new Person<Integer>(3,"ThuNgan"));
		people.add(new Person<Integer>(1,"Khanh"));
		people.add(new Person<Integer>(2, "Duong"));
		people.add(new Person<Integer>(3,"Hoa"));
		people.add(new Person<Integer>(1,"Phuong"));
				
	    Sort.counting_sort(people);
	    
	    for ( Person<Integer> p: people) {
	        System.out.print(p.name + " ");
	    }
	    System.out.println();
	}
	
	@Test
	public void test_intersect_sorted_arrays() {
		List<Integer> A = new ArrayList<Integer>();
		List<Integer> B = new ArrayList<Integer>();
		A.add(1);
		A.add(4);
		A.add(7);
		A.add(10);
		B.add(0);
		B.add(2);
		B.add(4);
		B.add(6);
		B.add(10);
		List<Integer> C = Sort.intersect_sorted_arrays(A, B);
		for (Integer c: C) {
			System.out.print(c + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_max_concurrent_events() {
		List<Event> events = new ArrayList<Event>();
		events.add(new Event (1,5));
		events.add(new Event(6,10));
		events.add(new Event(11, 13));
		events.add(new Event(2, 7));
		events.add(new Event(14,15));
		events.add(new Event(8,9));
		events.add(new Event(12,15));
		events.add(new Event(4,5));
		events.add(new Event(9,17));
		assertEquals(Sort.getMaxConcurrentEvents(events), 3);
		List<Interval<Integer>> intervals = new ArrayList<Interval<Integer>>();
		intervals.add(new Interval<Integer>(1,5));
		intervals.add(new Interval<Integer>(6,10));
		intervals.add(new Interval<Integer>(11, 13));
		intervals.add(new Interval<Integer>(2, 7));
		intervals.add(new Interval<Integer>(14,15));
		intervals.add(new Interval<Integer>(8,9));
		intervals.add(new Interval<Integer>(12,15));
		intervals.add(new Interval<Integer>(4,5));
		intervals.add(new Interval<Integer>(9,17));
		assertEquals(Sort.find_max_concurrent_events(intervals), 3);
	}
	
	@Test
	public void test_union_intervals() {
		List<Interval<Integer>> intervals = new ArrayList<Interval<Integer>>();
		intervals.add(new Interval<Integer>(1,5));
		intervals.add(new Interval<Integer>(7,10));
		intervals.add(new Interval<Integer>(11, 13));
		intervals.add(new Interval<Integer>(14,15));
		intervals.add(new Interval<Integer>(8,9));
		intervals.add(new Interval<Integer>(12,15));
		intervals.add(new Interval<Integer>(3,5));
		intervals.add(new Interval<Integer>(9,17));
		List<Interval<Integer>> union = Sort.union_intervals(intervals);
		for (Interval<Integer> i: union) {
			System.out.print(i);
		}
		System.out.println();
	}
	
	@Test
	public void test_find_minimum_visits() {
		List<Interval<Integer>> intervals = new ArrayList<Interval<Integer>>();
		intervals.add(new Interval<Integer>(1,5));
		intervals.add(new Interval<Integer>(6,10));
		intervals.add(new Interval<Integer>(11, 13));
		intervals.add(new Interval<Integer>(2, 7));
		intervals.add(new Interval<Integer>(14,15));
		intervals.add(new Interval<Integer>(8,9));
		intervals.add(new Interval<Integer>(12,15));
		intervals.add(new Interval<Integer>(4,5));
		intervals.add(new Interval<Integer>(9,17));
		List<Integer> visits = Sort.find_minimun_visits(intervals);
		for (Integer visit: visits) {
			System.out.print(visit + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_3_sum() {
		int[] A = {-3, 4, -2, 3, 12, 5, 9, 1, 3};
	    assert(Sort.has_3_sum(A, -1));
	    assert(Sort.has_3_sum(A, 5));
	    assert(!Sort.has_3_sum(A, 37));
	    assert(!Sort.has_3_sum(A, -10));
	    assert(Sort.has_3_sum(A, -3));
	}
}
