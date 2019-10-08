import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class Test {
    static AtomicInteger counter = new AtomicInteger(0);
	
	public static void main(String[] args)  throws InterruptedException {
		System.out.println("Main thread has started");
		
		WorkHard wh = new WorkHard();
		PlayHard ph = new PlayHard();
		Thread pt = new Thread(ph);
		
		wh.setPriority(Thread.MAX_PRIORITY);
		pt.setPriority(Thread.MIN_PRIORITY);
		pt.start();
		
		ExecutorService service = null;
		try {
			final int n = 3; // how many threads do we want here?
			service = Executors.newFixedThreadPool(n); // new ThreadPool using ExecutorService
			CyclicBarrier cb = new CyclicBarrier(n);
			
			for (int i = 0; i < n; i++) service.submit(() -> { // these threads have the highest priority, others will continue only when these are terminated
				System.out.println("Here comes Thread#" + Thread.currentThread().getId());
				
				try {
					cb.await();
				} catch (InterruptedException | BrokenBarrierException e) {
					System.out.println(e);
				}
			});
		} finally {
			if (service != null) service.shutdown();
		}
		
		while (wh.isAlive() || pt.isAlive() || !service.isTerminated()) Thread.sleep(500);
		
		System.out.println("Main thread is terminated");
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
