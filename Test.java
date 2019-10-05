import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class Test {
    static AtomicInteger counter = new AtomicInteger(0);
	
	public static void main(String[] args)  throws InterruptedException {
		System.out.println("Main thread has started");
		
		WorkHard wh = new WorkHard();
		PlayHard ph = new PlayHard();
		Thread pt = new Thread(ph);
		pt.start();
		
		ExecutorService service = null;
		try {
			service = Executors.newSingleThreadExecutor(); // new thread using ExecutorService
			service.execute(() -> {for (int i = 0; i < 3; i++) // this thread has the highest priority, others will continue only when it is terminated
				System.out.println("What's going on?");}
			);
		} finally {
			if (service != null) service.shutdown();
		}
		
		while (wh.isAlive() || pt.isAlive() || !service.isTerminated()) Thread.sleep(500);
	} 
}

class WorkHard extends Thread { // new thread extending Thread Class
	WorkHard() {
		start();
	}
	
	@Override
	public void run() {
		while(Test.counter.getAndIncrement() < 10) {
			System.out.println("Work Hard...");

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}

class PlayHard implements Runnable { // new thread using Runnable object
	@Override
	public void run() {
		while(Test.counter.getAndIncrement() < 10) {
			System.out.println("Play Hard...");
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}
