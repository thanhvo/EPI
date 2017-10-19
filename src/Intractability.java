import java.util.*;

public class Intractability {
	// V contains the number of votes for each state
	public static long ties_election(int[] V) {
		int total_votes = 0;
		for (int v : V) total_votes += v;
		// No way to tie if the total number of votes is odd
		if ((total_votes & 1) != 0) return 0;
		long[][] table = new long[V.length + 1][total_votes + 1];
		table[0][0] = 1; //base condition: 1 way to reach 0
		for (int i = 0; i < V.length; ++i) {
			for (int j = 0; j <= total_votes; ++j) {
				table[i+1][j] = table[i][j] + (j >= V[i] ? table[i][j - V[i]] : 0);
			}
		}
		return table[V.length][total_votes >> 1];
	}
	
	// Knapsack problem: find a set of items which total weight is less than or equal the capacity of the knapsack and 
	// maximize the total value of all items.
	public static int knapsack(int w, int[][] items) {
		int[] V = new int[w+1];
		for (int i = 0; i < items.length; ++i) {
			for (int j = w; j >= items[i][0]; --j) {
				V[j] = Math.max(V[j], V[j - items[i][0]] + items[i][1]);
			}
		}
		return V[w];
	}
	
	// Divide a set into two sets and minimize the difference between two sums of elements in two sets.
	public static int minimize_difference(int[] A) {
		int sum = 0;
		for (int i = 0; i < A.length; i++) {
			sum += A[i];
		}
		Set<Integer> is_Ok = new HashSet<Integer>();
		is_Ok.add(0);
		for (int item: A) {
			for (int v = sum >> 1; v >= item; --v) {
				is_Ok.add(v);
			}
		}
		// Find the first i from middle where is_Ok[i] == true
		for (int i = sum >> 1; i > 0; --i) {
			if (is_Ok.contains(i)) {
				return (sum -i ) -1;
			}
		}
		return sum; // one thief takes all
	}
	
	private static boolean check_feasible_helper(List<Jug> jugs, int L, int H, HashSet<Pair<Integer, Integer>> c) {
		if (L > H || c.contains(new Pair(L, H)) || (L < 0 && H < 0)) {
			return false;
		}
		// Check the volume for each jug to see if it is possible
		for (Jug j : jugs) {
			if ((L <= j.low && j.high <= H) || // base case: j is contained in [L,H]
				check_feasible_helper(jugs, L - j.low, H - j.high, c)) {
				return true;
			}
		}
		c.add(new Pair(L, H)); // marks this as impossible
		return false;
	}
	
	public static boolean check_feasible(List<Jug> jugs, int L, int H) {
		HashSet<Pair<Integer, Integer>> cache = new HashSet<Pair<Integer, Integer>>();
		return check_feasible_helper(jugs, L, H, cache);
	}
	
	private static boolean valid_to_add(int[][] A, int i, int j, int val) {
		// Check row constrains
		for (int k = 0; k < A.length; ++k) {
			if (val == A[k][j]) {
				return false;
			}
		}
		// Check column constrains
		for (int k = 0; k < A.length; ++k) {
			if (val == A[i][k]) {
				return false;
			}
		}
		// Check region constrains
		int region_size = (int)Math.sqrt(A.length);
		int I = i/region_size, J = j/region_size;
		for (int a = 0; a < region_size; ++a) {
			for (int b = 0; b < region_size; ++b) {
				if (val == A[region_size* I + a][region_size* J + b]) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean solve_sudoku_helper(int[][] A, int i, int j) {
		if (i == A.length) {
			i = 0; // start a new row
			if (++j == A[i].length) {
				return true; // entire matrix has been filled without conflict
			}
		}
		// Skip nonempty entries
		if (A[i][j] != 0) {
			return solve_sudoku_helper(A,i+1,j);
		}
		for (int val = 1; val <= A.length; ++val) {
			// Note: practically, it's substantially quicker to check if entryval conflicts with any of the constrains
			// if we add it at (i,j) before adding it, rather than adding it and then calling is_valid_sudoku. The 
			// reason is that we know we are starting with a valid configuration, and the only entry which can cause
			// a problem is entryval at (i,j)
			if (valid_to_add(A,i,j,val)) {
				A[i][j] = val;
				if (solve_sudoku_helper(A,i+1,j)) {
					return true;
				}
			}
		}
		A[i][j] = 0; // undo assignment
		return false;
	}
	
	// Check if a partially filled matrix has any conflicts
	public static boolean is_valid_sudoku(int[][] A) {
		// Check row constrains
		for(int i = 0; i < A.length; ++i) {
			boolean[] is_present = new boolean[A.length + 1];
			for (int j = 0; j < A.length; j++) {
				if(A[i][j] != 0 && is_present[A[i][j]]) {
					return false;
				} else {
					is_present[A[i][j]] = true;
				}
			}
		}
		// Check column constrains
		for (int j = 0; j < A.length; ++j) {
			boolean[] is_present = new boolean[A.length + 1];
			for (int i = 0; i < A.length; i++) {
				if (A[i][j] != 0 && is_present[A[i][j]]) {
					return false;
				} else {
					is_present[A[i][j]] = true;
				}
			}
		}
		
		// Check region constrains
		int region_size = (int)Math.sqrt(A.length);
		for (int I = 0; I < region_size; ++I) {
			for (int J =0; J < region_size; ++J) {
				boolean[] is_present = new boolean[A.length + 1];
				for (int i = 0; i < region_size; ++i) {
					for (int j = 0; j < region_size; ++j) {
						if (A[region_size*I+i][region_size*J +j] != 0 && is_present[A[region_size*I+i][region_size*J+j]]) {
							return false;
						} else {
							is_present[A[region_size*I+i][region_size*J+j]] = true;
						}
					}
				}
			}
		}
		return true;
	}
	
	public static boolean solve_sudoku(int[][] A) {
		if (is_valid_sudoku(A) == false) {
			System.out.println("Intial configuration violates constrains.");
			return false;
		}
		if (solve_sudoku_helper(A,0,0)) {
			for (int i = 0; i < A.length; ++i) {
				for (int val : A[i]) {
					System.out.print(val + " ");
				}
				System.out.println();
			}
			return true;
		} else {
			System.out.println("No solution exists.");
			return false;
		}
	}
	
	private static int evaluate(LinkedList<Integer> operand_list, LinkedList<Character> oper_list) {
		// Evaluate '*' first
		List<Integer> last_operands = new LinkedList<Integer>(operand_list);
		ListIterator<Integer> operand_it = last_operands.listIterator();
		for (char oper: oper_list) {
			if (oper == '*') {
				int cur = operand_it.next();
				operand_it.remove();
				int next = operand_it.next();
				operand_it.remove();
				//System.out.println(cur + " " + next);
				operand_it.add(next *cur);
				operand_it.previous();
			} else {
				operand_it.next();
			}
		}
		// Evaluate '+' second
		int sum = 0;
		for (int val: last_operands) sum += val;
		return sum;
	}
	
	// Debugging
	private static void printOperands(LinkedList<Integer> operand_list) {
		for (int i: operand_list) System.out.print(i + " ");
		System.out.println();
	}
	
	// Debugging
	private static void printOperators(LinkedList<Character> oper_list) {
		for (char c: oper_list) System.out.print(c + " ");
		System.out.println();
	}
	
	private static boolean exp_synthesis_helper(int[] A, int K, LinkedList<Integer> operand_list, LinkedList<Character> oper_list,
			int cur, int level) {
		cur = cur * 10 + A[level] - '0';
		if (level == A.length -1) {
			operand_list.add(cur);
			if (evaluate(operand_list, oper_list) == K) {
				ListIterator<Integer> operand_it = operand_list.listIterator();
				System.out.print(operand_it.next());
				for (char oper: oper_list) {
					System.out.print(" " + oper + " " + operand_it.next());
				}
				System.out.println(" = " + K);
				return true;
			}
			operand_list.removeLast();
		} else {
			// No operator
			if (exp_synthesis_helper(A, K, operand_list, oper_list, cur, level +1)) {
				return true;
			}
			// Add operator '+'
			operand_list.add(cur);
			String s = "";
			for (int i = level + 1; i < A.length; i++) {
				s += (A[i] -'0');
			}
			if (K - evaluate(operand_list, oper_list) <= Integer.valueOf(s)) { // pruning
				oper_list.add('+');
				if (exp_synthesis_helper(A, K, operand_list, oper_list, 0, level +1)) {
					return true;
				}
				oper_list.removeLast(); // revert
			}
			operand_list.removeLast(); // revert
			// Add operator '*'
			operand_list.add(cur);
			oper_list.add('*');
			if (exp_synthesis_helper(A, K, operand_list, oper_list, 0, level +1)) {
				return true;
			}
			operand_list.removeLast(); // revert
			oper_list.removeLast(); // revert
		}
		return false;
	}
	
	public static void exp_synthesis(int[] A, int K) {
		LinkedList<Character> oper_list = new LinkedList<Character>();
		LinkedList<Integer> operand_list = new LinkedList<Integer>();
		if (exp_synthesis_helper(A, K, operand_list, oper_list, 0, 0) == false) {
			System.out.println("No answer");
		}
	}
			
}
