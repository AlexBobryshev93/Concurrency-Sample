import java.util.concurrent.*;

public class Test {
    public static void main(String[] args)  throws InterruptedException {
		System.out.println("Main thread has started");
		
		WorkHard wh = new WorkHard();
		PlayHard ph = new PlayHard();
		Thread pt = new Thread(ph);
		pt.start();
		
		ExecutorService service = null;
		try {
			service = Executors.newSingleThreadExecutor();
			service.execute(() -> {for (int i = 0; i < 3; i++) 
				System.out.println("What's going on?");}
			);
		} finally {
			if (service != null) service.shutdown();
		}
		
		while (wh.isAlive() || pt.isAlive() || !service.isTerminated()) Thread.sleep(500);
	} 
}

class WorkHard extends Thread {
	int counter;

	WorkHard() {
		start();
	}
	
	@Override
	public void run() {
		while(counter <= 10) {
			System.out.println("Work Hard...");
			counter++;

			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}

class PlayHard implements Runnable {
	int counter;

	@Override
	public void run() {
		while(counter <= 10) {
			System.out.println("Play Hard...");
			counter++;
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
	}
}
