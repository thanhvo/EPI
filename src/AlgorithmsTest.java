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
		WeightedTreeNode A = new WeightedTreeNode();
		WeightedTreeNode B = new WeightedTreeNode();
		WeightedTreeNode C = new WeightedTreeNode();
		WeightedTreeNode D = new WeightedTreeNode();
		WeightedTreeNode E = new WeightedTreeNode();
		WeightedTreeNode F = new WeightedTreeNode();
		WeightedTreeNode G = new WeightedTreeNode();
		WeightedTreeNode H = new WeightedTreeNode();
		WeightedTreeNode I = new WeightedTreeNode();
		WeightedTreeNode J = new WeightedTreeNode();
		WeightedTreeNode K = new WeightedTreeNode();
		WeightedTreeNode M = new WeightedTreeNode();
		WeightedTreeNode N = new WeightedTreeNode();
		WeightedTreeNode O = new WeightedTreeNode();
		WeightedTreeNode P = new WeightedTreeNode();
		WeightedTreeNode Q = new WeightedTreeNode();
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
	
	@Test
	public void test_number_of_ways() {
		for (int i = 1; i <= 10; i ++) {
			for (int j = 1 ; j <= 10; j++) {
				System.out.print(Algorithms.number_of_ways(i, j) + " ");
			}
			System.out.println();
		}
		boolean[][] B = {
				{false, false, false, false, true},
		        {true, false, false, false, false},
		        {false, false, true, false, false},
		        {false, false, true, false, true},
		        {false, false, true, false, false}
		};
		assertEquals(Algorithms.number_of_ways_with_obstacles(5, 5, B), 3);
	}
	
	@Test
	public void test_fisherman_path() {
		System.out.println("Fisherman path");
		int[][] sea = {{1,2,4,5,7},
				   {3,4,5,8,2},
				   {2,2,2,2,2},
				   {1,3,5,7,9},
				   {2,4,6,8,10}};
		System.out.println(Algorithms.optimum_fisherman_path(sea, 5, 5));
	}
	
	@Test
	public void test_pick_up_coins() {
		int[] V = {1,2,3,4,5,3,2,1};
		System.out.println("Pick up coins");
		System.out.println(Algorithms.pick_up_coins(V));
	}
	
	@Test
	public void test_minimize_power() {
		TreeNode A = new TreeNode();
		TreeNode B = new TreeNode();
		TreeNode C = new TreeNode();
		TreeNode D = new TreeNode();
		TreeNode E = new TreeNode();
		A.addChild(B);
		A.addChild(C);
		B.addChild(D);
		B.addChild(E);
		assertEquals(Algorithms.minimize_power(A), 13);
	}
	
	@Test
	public void test_calculate_optimal_2D_tree() {
		System.out.println("Calculate 2D tree");
		int[][] image1 = {{0,0}, 
                		  {0,0}};
		int[][] image2 = {{1,1}, 
                		  {1,1}};                                 
		int[][] image3 = {{1,1,1},
						  {1,1,1},
						  {1,1,1}
						 };
		int[][] image4 = {{1,0}};                                
		int[][] image5 = {{1, 1, 1, 0, 1},
						  {0, 0, 1, 1, 1},
						  {0, 1, 1, 1, 1},
						  {1, 1, 1, 1, 0},
						  {0, 1, 1, 0, 1}
						  };
		ImageTreeNode root1 = Algorithms.calculate_optimal_2D_tree(image1);
		root1.print();
		ImageTreeNode root2 = Algorithms.calculate_optimal_2D_tree(image2);
		root2.print();
		ImageTreeNode root3 = Algorithms.calculate_optimal_2D_tree(image3);
		root3.print();
		ImageTreeNode root4 = Algorithms.calculate_optimal_2D_tree(image4);
		root4.print();
		ImageTreeNode root5 = Algorithms.calculate_optimal_2D_tree(image5);
		root5.print();
	}
	
	@Test
	public void test_minimum_waiting_time() {
		System.out.println("Minimize waiting time");
		int[] service_time = {1,4,2,3,5};
		System.out.println(Algorithms.minimum_waiting_time(service_time));
	}
	
	private void print_assignment(boolean[][] assignment) {
		for (int i = 0; i < assignment.length; i++) {
			for (int j = 0; j < assignment[i].length; j++) {
				System.out.print(assignment[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void test_find_feasible_job_assignment() {
		System.out.println("Job assignment");    
	    // Test case 1: infeasible
	    System.out.println("Test case 1");
	    int[] tasks1 = {4,2,3};
	    int[] servers1 = {6,2,1};
	    boolean[][] assignment1 = Algorithms.find_feasible_job_assignment(tasks1, servers1);
	    print_assignment(assignment1);
	    
	    // Test case 2: feasible
	    System.out.println("Test case 2");
	    int[] tasks2 = {1,1,1};
	    int[] servers2 = {1,1,1};
	    boolean[][] assignment2 = Algorithms.find_feasible_job_assignment(tasks2, servers2);
	    print_assignment(assignment2);
	    
	    // Test case 3: feasible
	    System.out.println("Test case 3");
	    int[] tasks3 = {1,2,3};
	    int[] servers3 = {2,3,4};
	    boolean[][] assignment3 = Algorithms.find_feasible_job_assignment(tasks3, servers3);
	    print_assignment(assignment3);
	}
	
	private void print_load_assignment(int[] assign) {
		for (int a: assign) {
			System.out.print(a + " ");
		}
		System.out.println();
	}
	
	@Test
	public void test_load_balancing() {
		System.out.println("Load balancing");
	    int[] b1 = {2,3,5,7,11,13};
	    int[] assign1 = Algorithms.decide_load_balancing(b1, 5);
	    print_load_assignment(assign1);
	    int[] b2 = {1,2,3,3,4,2,3,4};
	    int[] assign2 = Algorithms.decide_load_balancing(b2, 5);
	    print_load_assignment(assign2);
	}
	
	@Test 
	public void test_first_fit() {
		System.out.println("Find first fit");
		TournamentTree tree = new TournamentTree(4, 10);
	    tree.insert(0, 2);
	    System.out.println(tree.getCapacity());
	    tree.insert(1, 5);
	    System.out.println(tree.getCapacity());
	    tree.insert(2, 3);
	    System.out.println(tree.getCapacity());
	    tree.insert(1, 5);
	    System.out.println(tree.getCapacity()); 
	    tree.insert(3, 10);
	    System.out.println(tree.getCapacity()); 
	    assertEquals(tree.insert(4,11),false);
	    tree.insert(4, 8);
	    System.out.println(tree.getCapacity()); 
	    assertEquals(tree.insert(3, 10), false);
	    assertEquals(tree.insert(5, 6), false);
	}
	
	@Test
	public void test_huffman_encoding() {
		System.out.println("Huffman encoding");
		/*  Due to incorrect conversion from string to floating point type, double, this algorithm does not work as expected.
		 ** The example below illustrates how arithmetic operations on floating point numbers can go easily go wrong. 	
		 */
		System.out.println(0.1 + 0.1 + 0.1);
		Symbol[] symbols = {new Symbol('a',0.1), new Symbol('b',0.05),new Symbol('c',0.1), new Symbol('d',0.2), new Symbol('e',0.05), new Symbol('f',0.5)};
	    Algorithms.Huffman_encoding(symbols);
	    for (Symbol s: symbols) {
	        s.print();
	    }
	}
}
