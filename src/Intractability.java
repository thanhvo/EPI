
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
}
