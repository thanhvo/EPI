import java.util.Date;

public class Writer extends Thread{
	public void run() {
		while(true) {
			synchronized(RW.LW) {
				boolean done = false;
				while (!done) {
					synchronized(RW.LR) {
						if (RW.lastWrite) {
							RW.LR.notify();							
						}
						else {
							if (RW.readCount == 0) {
								RW.data = new Date().toString();
								RW.lastWrite = true;
								done = true;
							} else {
								// use wait/notify to avoid busy waiting
								try {
									RW.LR.wait();								
								} catch(InterruptedException e) {
									System.out.println("InterrutpedException in Writer wait");
								}
							}
							RW.LR.notify();
						}
					}
				}
			}
		}
	}
}
