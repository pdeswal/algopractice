
public class Child extends Base {
    public void method2(){
	System.out.println("child->method2");
	method3();
    }
    public void method3(){
	System.out.println("Child->method3");
    }
    class Inner{
	public void callIt(){
	    method2();
	}
    }
}
