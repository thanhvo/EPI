import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
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
		Integer[] b = HeapUtil.sort_k_up_down_array(a);
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
		Integer[] d = HeapUtil.sort_k_up_down_array(c);
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
	
	@Test
	public void test_Heap_Stack_Queue() throws Exception{
		/* Test for stack */ 
		HeapStack<Integer> stack = new HeapStack();
		stack.push(1);
		stack.push(4);
		stack.push(10);
		assertEquals(stack.pop().intValue(), 10);
		assertEquals(stack.pop().intValue(), 4);
		assertEquals(stack.pop().intValue(), 1);
		
		/* Test for queue */
		HeapQueue<Integer> queue = new HeapQueue();
		queue.enqueue(1);
		queue.enqueue(4);
		queue.enqueue(10);
		assertEquals(queue.dequeue().intValue(), 1);
		assertEquals(queue.dequeue().intValue(), 4);
		assertEquals(queue.dequeue().intValue(), 10);		
	}
	
	@Test
	public void test_k_closest_stars() {
		List<Star> starList = new ArrayList<Star>();
		starList.add(new Star(0, 0.0, 0.0, 0.0));
		starList.add(new Star(1, 0.0, 0.0, 1.0));
		starList.add(new Star(2, 1.0, 0.0, 0.0));
		starList.add(new Star(3, 0.0, 1.0, 0.0));
		starList.add(new Star(4, 0.0, 1.0, 0.0));
		starList.add(new Star(5, 3.0, 1.0, 0.0));
		starList.add(new Star(6, 3.0, 1.0, 2.0));
		starList.add(new Star(7, 8.0, 1.0, 9.0));
		starList.add(new Star(8, 2.0, 1.0, 0.0));
		starList.add(new Star(9, -3.0, 1.0, -6.0));
		List<Star> closestStars = Star.k_closest_stars(starList, 4);
		for (Star star: closestStars) {
			System.out.print(star.getId() + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_kth_largest_item() throws FileNotFoundException, IOException, ClassNotFoundException {		
		String inputFileName = "/home/thanh/workspace/EPI/input/kth_largest_item.dat";
		String outputFileName = "/home/thanh/workspace/EPI/output/kth_largest_item.out";
		Integer[] list = {13, 23, 1, 4, 5, 6, 20, 100, 2, 3};
		/* Write to the input file */
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(inputFileName));
		for (Integer i: list) {
			stream.writeObject(i);
		}
		stream.flush();
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(inputFileName));
		FileOutputStream output = new FileOutputStream(outputFileName);
		HeapUtil.print_kth_largest_item(input, output, 3);
	}
	
	@Test
	public void test_appoximate_sort() {
		List<Integer> list = Arrays.asList(10, 2, 3, 20, 50, 100, 34, 99, 80);
		List<Integer> sortedList = HeapUtil.approximate_sort(list, 3);
		Iterator<Integer> it = sortedList.iterator();
		assertEquals(it.next().intValue(), 2);
		assertEquals(it.next().intValue(), 3);
		assertEquals(it.next().intValue(), 10);
		assertEquals(it.next().intValue(), 20);
		assertEquals(it.next().intValue(), 34);
		assertEquals(it.next().intValue(), 50);
		assertEquals(it.next().intValue(), 80);
		assertEquals(it.next().intValue(), 99);
		assertEquals(it.next().intValue(), 100);
	}
	
	@Test
	public void test_Median_Heap() {
		int[] input = {2,3,4,1,5,7,10,9};
		MedianHeap<Integer> heap = new MedianHeap<Integer>();
		for (int i: input) {
			heap.add(i);
			System.out.print(heap.getMedian() + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_kth_largest_heap() {
		Integer[] max_heap = {16, 14, 10, 8, 7, 9, 3, 2, 4, 1};
		assertEquals(HeapUtil.compare_kth_largest_heap(max_heap, 3, 10), 0);
		assertEquals(HeapUtil.compare_kth_largest_heap(max_heap, 3, 9), 1);
		assertEquals(HeapUtil.compare_kth_largest_heap(max_heap, 3, 11), -1);
	}
}
