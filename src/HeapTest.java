import org.junit.*;
import org.junit.Assert.*;
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
}
