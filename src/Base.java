
public class Base {
    public void method1(){
	System.out.println("base->method1");
	Inner inner = new Inner();
	inner.callIt();
    }
    private void method2(){
	System.out.println("base->method2");
	method3();
    }
    public void method3(){
	System.out.println("base->method3");
    }
    
    class Inner{
	public void callIt(){
	    method2();
	}
    }
}
