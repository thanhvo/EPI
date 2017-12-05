import java.util.Date;

public class Writer extends Thread{
	public void run() {
		while(true) {
			synchronized(RW.LW) {
				boolean done = false;
				while (!done) {
					synchronized(RW.LR) {
						if (RW.readCount == 0) {
							RW.data = new Date().toString();
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
