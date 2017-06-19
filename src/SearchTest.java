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
}
