package try2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyMain {
    public static void main(String[] args) throws ParseException{
	String dateStr = "06/20/17 23:21";
	SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/yy hh:mm");
	Date date = sdf.parse(dateStr);
	System.out.println(date);
    }
}
class A{
    private String a1;
    private int sum;
    
    
    @Override
    public String toString() {
	return "A [a1=" + a1 + ", sum=" + sum + "]";
    }
    public A(String a1, int sum) {
	super();
	this.a1 = a1;
	this.sum = sum;
    }
    public String getA1() {
        return a1;
    }
    public void setA1(String a1) {
        this.a1 = a1;
    }
    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }
    
}
class B{
    private String a2;
    private int cost;
    
    
    @Override
    public String toString() {
	return "B [a2=" + a2 + ", cost=" + cost + "]";
    }
    public B(String a2, int cost) {
	super();
	this.a2 = a2;
	this.cost = cost;
    }
    public String getA2() {
        return a2;
    }
    public void setA2(String a2) {
        this.a2 = a2;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    
}
