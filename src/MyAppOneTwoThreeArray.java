import java.util.concurrent.locks.ReentrantLock;


public class MyAppOneTwoThreeArray {
    private int index = 0;
    private int iteration = 0;
    int[] arr = null;
    private Object lock = new Object();
    
    boolean keepRunning = true;
    public MyAppOneTwoThreeArray(int[] arr){
	this.arr = arr;
    }
    public void consumeArray(){
	synchronized (lock) {
	    while(keepRunning){
		iteration++;
		    int startIndex = index;
		    index = index + iteration;

		    for(int i=startIndex; i<index && !(i >= arr.length); i++){
			System.out.println("index: "+i+" value: " + arr[i] + " Thread name: " + Thread.currentThread().getName());
			try {
			    Thread.sleep(100);
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		    }

		    if(index > arr.length){
			keepRunning = false;//to finish all threads
			lock.notify();
		    }else{
			lock.notify();
			try {
			    lock.wait();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			}
		    }
	    }
	    
	}
    }
    public static void main(String[] args) {
	final int[] arr = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
	final MyAppOneTwoThreeArray app = new MyAppOneTwoThreeArray(arr);
	Thread t1 = new Thread(new Runnable() {
	    
	    @Override
	    public void run() {
		app.consumeArray();
	    }
	});
	Thread t2 = new Thread(new Runnable() {
	    
	    @Override
	    public void run() {
		app.consumeArray();
	    }
	});
	
	t1.start();
	t2.start();
	
	try {
	    t1.join();
	    t2.join();
	    System.out.println("Completed..");
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	
    }
}
