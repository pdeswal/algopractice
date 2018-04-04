import java.util.Comparator;


public class MyMain1 {
    public static void main(String[] args) throws InterruptedException {
	
	Thread t1 = new Thread(new MyThread());
	Thread t2 = new Thread(new MyThread());
	Thread t3 = new Thread(new MyThread());
	Thread t4 = new Thread(new MyThread());
	
	t1.start();
	t2.start();
	t3.start();
	t4.start();
	
	t1.join();
	t2.join();
	t3.join();
	t4.join();
	
	System.out.println("Main stopped..");
	
    }
    
    public void startTesting(){
	   /*Runnable r = new Runnable() {
	    
	    @Override
	    public void run() {
		System.out.println("Doing its job");
	    }
	};*/
	Runnable r = () -> { System.out.println("Task #2 is running"); };
	try {
	    Thread.sleep(3600000);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	System.out.println(r);
	System.out.println(r.getClass().getName());
    }
    
    public static class MyThread implements Runnable{

	@Override
	public void run() {
	    MyMain1 m1 = new MyMain1();
	    m1.startTesting();
	    
	}
	
    }
}
