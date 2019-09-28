public class Test {
    public static void main(String[] args) {
		System.out.println("Main thread has started");
		WorkHard wh = new WorkHard();
		PlayHard ph = new PlayHard();
		Thread pt = new Thread(ph);
		pt.start();
		
		while (wh.stop && ph.stop);
	} 
}

class WorkHard extends Thread {
	int counter;
	boolean stop;
	
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
		
		stop = true;
	}
}

class PlayHard implements Runnable {
	int counter;
	boolean stop;
	
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
		
		stop = true;
	}
}