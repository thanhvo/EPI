import java.util.*;

public class RWTest {
	public static void main(String[] args) throws InterruptedException{
		int READS = 10;
		int WRITES = 3;
		RW.data = new Date().toString();
		RW.LW = new Object();
		RW.LR = new Object();
		for (int i = 0; i <= WRITES; i++) {
			new Writer().start();			
		}
		
		for (int i = 0; i <= READS; i++) {
			new Reader().start();
		}		
	}
}
