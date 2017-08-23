import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class AlgorithmsTest {
	@Test
	public void test_drawing_skylines() {
		List<Skyline<Integer, Integer>> skylines = Arrays.asList(new Skyline(1,2,3), new Skyline(2,4,2), new Skyline(3,4,3), new Skyline(1,5,1));
	    List<Skyline<Integer, Integer>> drawn_skylines = Algorithms.draw_skylines(skylines);
	    for (Skyline<Integer, Integer> skyline : drawn_skylines) 
	        skyline.print();
	}
	
	@Test
	public void test_count_inversions() {
		List<Integer> v = Arrays.asList(4,3,1,5,8,7,10,9);
		assertEquals(Algorithms.count_inversions(v), 5);
	}
	
	@Test
	public void test_find_closest_pair_points() {
		ArrayList<Point> P = new ArrayList<Point>(Arrays.asList(new Point(1,2), new Point(3,4), new Point(2,3), new Point(5,8), new Point(4,4)));
		Pair<Point, Point> pair = Algorithms.find_closest_pair_points(P);
		pair.first.print();
		pair.second.print();
	}
	
	@Test
	public void test_tree_diameter() {
		TreeNode A = new TreeNode();
		TreeNode B = new TreeNode();
		TreeNode C = new TreeNode();
		TreeNode D = new TreeNode();
		TreeNode E = new TreeNode();
		TreeNode F = new TreeNode();
		TreeNode G = new TreeNode();
		TreeNode H = new TreeNode();
		TreeNode I = new TreeNode();
		TreeNode J = new TreeNode();
		TreeNode K = new TreeNode();
		TreeNode M = new TreeNode();
		TreeNode N = new TreeNode();
		TreeNode O = new TreeNode();
		TreeNode P = new TreeNode();
		TreeNode Q = new TreeNode();
		B.addChild(C, 7);
		B.addChild(A, 14);
		B.addChild(G, 3);
		C.addChild(D, 4);
		D.addChild(E, 6);
		C.addChild(F, 3);
		G.addChild(H, 2);
		G.addChild(I, 1);
		I.addChild(J, 6);
		I.addChild(K, 4);
		K.addChild(M, 4);
		K.addChild(N, 2);
		N.addChild(O, 1);
		N.addChild(P, 2);
		N.addChild(Q, 3);
		assert(Algorithms.compute_diameter(B) == 31);
	}
	
	@Test
	public void test_find_maximum_subarray() {
		int[] A = {904, 40, 523, 12, -335, -385, -124, 481, -31};
		Pair<Integer, Integer> range = Algorithms.find_maximum_subarray(A);
		assertEquals(range.first.intValue(), 0);
		assertEquals(range.second.intValue(), 3);
		assertEquals(Algorithms.max_subarray_sum_in_circular(A), 1929);
	}
}
