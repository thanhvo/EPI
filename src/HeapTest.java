import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class HeapTest {
	@Test
	public void test_merge_arrays() {
		Integer[][] array = {{1,5,7}, {3,6,9}, {4, 8, 10}};
		List<Integer> merged = HeapUtil.merge_arrays(array);
		for (Integer val: merged) {
			System.out.print(val + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_sort_k_up_down_array() {
		/* Test case 1 */
		Integer[] a = {57, 131, 493, 294, 221, 339, 418, 452, 442, 190};
		Integer[] b = HeapUtil.sort_k_up_down_array(a, 4);
		assertEquals(b[0].intValue(), 57);
		assertEquals(b[1].intValue(), 131);
		assertEquals(b[2].intValue(), 190);
		assertEquals(b[3].intValue(), 221);
		assertEquals(b[4].intValue(), 294);
		assertEquals(b[5].intValue(), 339);
		assertEquals(b[6].intValue(), 418);
		assertEquals(b[7].intValue(), 442);
		assertEquals(b[8].intValue(), 452);
		assertEquals(b[9].intValue(), 493);
		
		/*Test case 2 */
		Integer[] c = {5, 6, 4, 3, 8, 9, 1, 2, 7};
		Integer[] d = HeapUtil.sort_k_up_down_array(c, 5);
		assertEquals(d[0].intValue(), 1);
		assertEquals(d[1].intValue(), 2);
		assertEquals(d[2].intValue(), 3);
		assertEquals(d[3].intValue(), 4);
		assertEquals(d[4].intValue(), 5);
		assertEquals(d[5].intValue(), 6);
		assertEquals(d[6].intValue(), 7);
		assertEquals(d[7].intValue(), 8);
		assertEquals(d[8].intValue(), 9);
	}
}
