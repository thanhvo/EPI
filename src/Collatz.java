import java.util.*;
import java.util.concurrent.*;
import java.math.*;

public class Collatz {
	public static final int NTHREADS = 100;
	public static final int RANGESIZE = 30;
	
	// Check an individual number
	public static boolean CollatzCheck(BigInteger x, Set<BigInteger> visited) {
		if (x.equals(BigInteger.ONE)) {
			return true;
		} else if (visited.contains(x)) {
			return false;
		}
		visited.add(x);
		if (x.getLowestSetBit() == 1) { // odd number
			return CollatzCheck(new BigInteger("3").multiply(x).add(BigInteger.ONE), visited);
			
		} else { // even number
			return CollatzCheck(x.shiftRight(1), visited); // divide by 2
		}
		
	}
	
	public static boolean CollatzCheck(int x, Set<BigInteger> visited) {
		BigInteger b = new BigInteger(new Integer(x).toString());
		return CollatzCheck(b, visited);
	}
	
	public static ExecutorService execute(int N) {
		// Uses the Executor framework for task assignment and load balancing
		List<Thread> threads = new ArrayList<Thread>();
		ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
		for (int i = 0; i < (N/RANGESIZE); ++i) {
			Runnable worker = new MyRunnable(i * RANGESIZE + 1, (i+1)* RANGESIZE);
			executor.execute(worker);
		}
		executor.shutdown();
		return executor;
	}
	
	public static void main(String[] args) {
		Collatz.execute(100000);
	}
}
