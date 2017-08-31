import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import javax.swing.event.ListSelectionEvent;

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
		assertEquals(Algorithms.max_subarray_circular(A), 1929);
	}
	
	@Test
	public void test_longest_nondecreasing_subsequence() {
		List<Integer> list = Arrays.asList(0, 8, 4, 12, 2, 10, 6, 14, 1, 9);
		System.out.println(Algorithms.longest_nondecreasing_subsequence(list));
		assertEquals(Algorithms.longest_nondecreasing_subsequence2(list), 4);		
	}
	
	@Test
	public void test_find_longest_subarray_less_equal_k() {
		System.out.println("Longest subarray less equal k");		
		int[] v = {431, -15, 639, 342, -14, 565, -924, 635, 167, -70};
	    Pair<Integer, Integer> p = Algorithms.find_longest_subarray_less_equal_k(v, 184);
	    System.out.println( p.first + " " + p.second);
	}
	
	@Test
	public void test_largest_rectangle() {
		int[] A = {1,2,3,1,2};
		assertEquals(Algorithms.calculate_largest_rectangle(A), 5);
	}
	
	@Test
	public void test_max_rectangle_submatrix() {
		boolean[][] A = {
			{true, true, true, true, false},
	        {true, true, true, false, false},
	        {true, true, true, true, true},
	        {true, true, false, true, true},
	        {true, true, true, true, true}
		};
		assertEquals(Algorithms.max_rectangle_submatrix(A), 10);
		assertEquals(Algorithms.max_square_submatrix(A), 9);
		assertEquals(Algorithms.max_rectangle_submatrix2(A), 10);
	}
	
	@Test
	public void test_sequence() {
		int[][] A = {
				{1, 2, 3},
				{3, 4, 5},
				{5, 6, 7}
		};
		int[] S1 = {1,3,4,6};
		int[] S2 = {1,2,3,4};
		List<Pair<Integer, Integer>> seq1 = Algorithms.sequence(A, S1);
		System.out.println(seq1);
		assertEquals(Algorithms.sequence(A, S2), null);
	}
	
	@Test
	public void test_leveshtein_distance() {
		System.out.println(Algorithms.leveshtein_distance("hello", "hell"));
		System.out.println(Algorithms.leveshtein_distance("yes", "no"));
	}
	
	@Test
	public void test_break_words() {
		List<String> dict = Arrays.asList("hello", "goodbye", "there", "you", "go", "beautiful", "day");
		List<String> words = Algorithms.break_words("therebeautifulday", dict);
		for (String word : words) System.out.print(word + " ");
		System.out.println();
		assertEquals(Algorithms.break_words("hereyougo", dict), null);
	}
	
	@Test
	public void test_find_pretty_printing() {
		List<String> W = Arrays.asList("this", "is", "a", "beautiful", "day", ".", "I", "enjoy", "working", "from", "home");
		System.out.println(Algorithms.find_pretty_printing(W, 10));
	}
	
	@Test
	public void test_compute_binomial_coefficients() {
		for (int i = 0; i< 10; i++) {
			for (int j = 0; j < i; j++) {
				System.out.print(Algorithms.compute_binomial_coefficients(i, j) + " ");				
			}
			System.out.println();
		}
	}
	
	@Test
	public void test_num_of_plays() {
		int[] W = {2,3,7};
		assertEquals(Algorithms.num_of_plays(12, W), 4);
		assertEquals(Algorithms.num_of_plays(7, W), 2);
		assertEquals(Algorithms.count_combinations(12, W), 4);
		assertEquals(Algorithms.count_combinations(7, W), 2);
		System.out.println(Algorithms.count_permutations(12, W));
		System.out.println(Algorithms.count_permutations(7, W));
	}
}
