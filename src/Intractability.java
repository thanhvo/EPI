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
}
