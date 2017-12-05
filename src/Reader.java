
public class Reader extends Thread{
	public void run() {
		while (true) {
			synchronized(RW.LW) {
				RW.LW.notify();
			}
			synchronized(RW.LR) {
				RW.readCount++;
				RW.LR.notify();
			}
			System.out.println(RW.data);
			synchronized(RW.LR) {
				RW.readCount--;
				RW.LR.notify();
			}			
		}
	}
}
