import java.util.*;
import java.math.*;

// Perform basic unit of work
public class MyRunnable implements Runnable {
	public int lower;
	public int upper;
	
	MyRunnable(int lower, int upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	@Override
	public void run() {
		for (int i = lower; i <= upper; ++i) {
			if (!Collatz.CollatzCheck(i, new HashSet<BigInteger>(1))) {
				System.out.println("Collatz conjecture fails!!!");
			}
		}
		System.out.println("(" + lower + "," + upper + ")");
	}
}
