
public class Reader extends Thread{
	public void run() {
		while (true) {
			synchronized(RW.LR) {
				if (!RW.lastWrite) {
					RW.LR.notify();
					continue;
				} else {
					RW.readCount++;				
					RW.LR.notify();
				}
			}
			System.out.println(RW.data);
			synchronized(RW.LR) {
				RW.readCount--;
				RW.lastWrite = false;
				RW.LR.notify();
			}			
		}
	}
}
