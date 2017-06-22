import org.junit.*;
import static org.junit.Assert.*;

public class SearchTest {
	@Test
	public void test_find() {
		Integer[] a = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
		assertEquals(Search.find(a, 108), 3);
		assertEquals(Search.find(a, 285), 6);
		assertEquals(Search.find(a, -20), -1);
		assertEquals(Search.find(a, -403), -1);
		assertEquals(Search.find(a, -14), 0);
		assertEquals(Search.find(a, 401), 9);
	}
	
	@Test
	public void test_first_larger() {
		Integer[] a = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
		assertEquals(Search.first_larger_element(a, 402), -1);
		assertEquals(Search.first_larger_element(a, 101), 3);
		assertEquals(Search.first_larger_element(a, -10), 2);
		assertEquals(Search.first_larger_element(a, 1), 2);
		assertEquals(Search.first_larger_element(a, 2), 3);
	}
	
	@Test
	public void test_element_equals_to_index() {
		int[] a = {-14, -10, 2, 108, 108, 243, 285, 285, 285, 401};
		assertEquals(Search.find_element_equals_to_its_index(a), 2);		
	}
	
	@Test
	public void test_find_pair_sum() {
		int[] a = {-49, 75, 103, -147, 164, -197, -238, 314, 348, -422};
		Pair<Integer, Integer> p = Search.find_pair_sum_k(a, 167);
		System.out.println(p.first + " " + p.second);
		p = Search.find_pair_sum_k(a, -196);
		System.out.println(p.first + " " + p.second);
		p = Search.find_pair_sum_k(a, 423);
		System.out.println(p.first + " " + p.second);
		p = Search.find_pair_sum_k(a, 0);
		System.out.println(p.first + " " + p.second);
	}
	
	@Test
	public void test_min_cyclic_array() {
		int[] a = {378, 478, 550, 631, 103, 203, 220, 234, 279, 368};
		assertEquals(Search.min_cyclic_array(a), 4);
	}
	
	@Test
	public void test_search_unknown_length_array() {
		Integer[] a = {1, 2, 4, 5, 6, 8, 9, 11, 20, 32, 45, 67, 78, 89, 93, 102};
		assertEquals(Search.search_unknown_length_array(a, 5), 3);
		assertEquals(Search.search_unknown_length_array(a, 93), 14);
		assertEquals(Search.search_unknown_length_array(a, 100), -1);
	}
	
	@Test
	public void test_find_cut_off_cap() {
		double[] a = {90, 30, 100, 40, 20};
		double target = 210;
		System.out.println(Search.find_cut_off_cap(a, target));
		target = 120;
		System.out.println(Search.find_cut_off_cap(a, target));
	}
	
	@Test
	public void test_search_kth_element() {
		Integer[] A = {1,2,3,4,5,6,7};
		Integer[] B = {2,3,5,7,9,10,11};
		assertEquals(Search.search_kth_element(A, B, 3).intValue(), 2);
		assertEquals(Search.search_kth_element(A, B, 4).intValue(), 3);
		assertEquals(Search.search_kth_element(A, B, 5).intValue(), 3);
		assertEquals(Search.search_kth_element(A, B, 6).intValue(), 4);
		assertEquals(Search.search_kth_element(A, B, 8).intValue(), 5);
		assertEquals(Search.search_kth_element(A, B, 9).intValue(), 6);
	}
	
	@Test
	public void test_square_root() {
		System.out.println(Search.square_root(2.0));
	}
}
