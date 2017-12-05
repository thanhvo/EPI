import java.util.*;

public class RWTest {
	public static void main(String[] args) throws InterruptedException{
		int readNum = 10;
		int writeNum = 1;
		RW.data = new Date().toString();
		RW.LW = new Object();
		RW.LR = new Object();
		for (int i = 0; i <= readNum; i++) {
			new Reader().start();
		}
		for (int i = 0; i <= writeNum; i++) {
			new Writer().start();
			Thread.sleep(3000);
		}
		
	}
}
