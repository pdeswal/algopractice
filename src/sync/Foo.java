package sync;

import java.util.concurrent.atomic.AtomicInteger;

public class Foo {
    
    AtomicInteger count = new AtomicInteger(1);
    public Foo(){
	
    }
    
    public void methodA(){
	synchronized (this) {
	    while(count.get()!=1){
		try {
		    wait();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    System.out.println("Method A called");
	    count.getAndIncrement();
	    notifyAll();
	}
	
    }
    
    public void methodB(){
	synchronized (this) {
	    while(count.get()!=2){
		try {
		    wait();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    System.out.println("Method B called");
	    count.getAndIncrement();
	    notifyAll();
	}
    }
    
    public void methodC(){
	synchronized (this) {
	    while(count.get()!=3){
		try {
		    wait();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    System.out.println("Method C called");
	    count.getAndIncrement();
	    notifyAll();
	}
    }
    
    public static void main(String[] args) throws InterruptedException {
	Foo foo = new Foo();
	Thread t1 = new Thread(new TaskA(foo));
	Thread t2 = new Thread(new TaskB(foo));
	Thread t3 = new Thread(new TaskC(foo));
	
	
	t2.start();
	t3.start();
	t1.start();
	
	t1.join();
	t2.join();
	t3.join();
    }
    
    static class TaskA implements Runnable{
	Foo foo;
	TaskA(Foo foo){
	    this.foo = foo;
	}
	@Override
	public void run() {
	    foo.methodA();
	}
	
    }
    static class TaskB implements Runnable{
	Foo foo;
	TaskB(Foo foo){
	    this.foo = foo;
	}
	@Override
	public void run() {
	    foo.methodB();
	}
	
    }
    static class TaskC implements Runnable{
	Foo foo;
	TaskC(Foo foo){
	    this.foo = foo;
	}
	@Override
	public void run() {
	    foo.methodC();
	}
	
    }
}
