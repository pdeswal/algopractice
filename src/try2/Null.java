package try2;

public class Null {
    public static void greet(){
	System.out.println("Hello");
    }
    public static void main(String[] args) {
	((Null)null).greet();
	Object obj =null;
	new Null().func1(obj);
	new Null().func1(null);
    }
    public void func1(String d){
   	System.out.println("Double Array");
   	final class B{
   	    int a = 10;
   	    public int addInto(int b){
   		return a + b;
   	    }
   	}
   	B b = new B();
   	
   	System.out.println(b.addInto(10));
    }
   
    public void func1(Object o){
	System.out.println("Object");
    }
    
   
    
}

