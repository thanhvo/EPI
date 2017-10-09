
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
}
