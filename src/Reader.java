
public class Reader extends Thread{
	public void run() {
		while (true) {
			synchronized(RW.LR) {
				RW.readCount++;
				RW.LR.notify();
			}
			System.out.println(RW.data);
			synchronized(RW.LR) {
				RW.readCount--;
				RW.LR.notify();
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
