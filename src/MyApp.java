import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MyApp {
    private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
    private Random random = new Random();
    public void produce(){
	while(true){
	    int value = random.nextInt(100);
	    try {
		System.out.println("Adding: " + value + " Queue Size: " + queue.size());
		queue.put(value);
		System.out.println("Added: " + value + " Queue Size: " + queue.size());
		
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}

    }
    public void consume(){
	int value = -1;
	try {
	    value = queue.take();
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	    System.out.println("Consumed: " + value + " Queue Size: " + queue.size());
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

    }
    public static void main(String[] args) {
	final MyApp app = new MyApp();
	Thread t1 = new Thread(new Runnable(){

	    @Override
	    public void run() {
		app.produce();

	    }

	});

	Thread t2 = new Thread(new Runnable(){

	    @Override
	    public void run() {
		while(true){
		    app.consume();
		}

	    }

	});

	t1.start();
	//t2.start();

	try {
	    t1.join();
	    //t2.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}


    }
}
