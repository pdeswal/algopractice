
public class MyMain2 {
    public static void main(String[] args) throws InterruptedException {
	Thread t1 = new Thread(new Counter());
	Thread t2 = new Thread(new Counter());
	
	t1.start();
	t2.start();
	
	t1.join();
	t2.join();
	
	Counter.printCount();
	
    }
    public static class Counter implements Runnable{
	private static volatile int count = 0;
	@Override
	public void run() {
	    for(int i=0; i<10000; i++){
		increament();
	    }
	}
	private static synchronized void increament(){
	    count++;
	}
	public static void printCount(){
	    System.out.println("Count: " + count);
	}
    }
}
