package servicenow;

import java.util.Scanner;

public class TestClass1 {

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	
	for(int i= 0; i < T; i++){
	 int x = sc.nextInt();
	 int y = sc.nextInt();
	 int l = sc.nextInt(); 
	 int m = sc.nextInt(); 
	 int a = sc.nextInt(); 
	 int b = sc.nextInt(); 
	 
	 
	 if(bottomRightFit(x, y, l, m, a, b)){
	     System.out.println("bottom-right");
	 }else if(bottomLeftFit(x, y, l, m, a, b)){
	     System.out.println("bottom-left");
	 }else if(topRightFit(x, y, l, m, a, b)){
	     System.out.println("top-right");
	 } else if(topLeftFit(x, y, l, m, a, b)){
	     System.out.println("top-left");
	 }
	 
	}

    }

    public static boolean bottomRightFit(int x,int y, int l, int m,int a, int b){
	int x1 = a;
	int y1 = b - m;
	
	int x2 = a + l;
	int y2 = b;
	
	if(x1 >= 0 && y1 >=0 && x2 <= x && y2 <= y)
	    return true;
	return false;
    }
    
    public static boolean bottomLeftFit(int x,int y, int l, int m,int a, int b){
	int x1 = a - l;
	int y1 = b - m;
	
	int x2 = a;
	int y2 = b;
	
	if(x1 >= 0 && y1 >=0 && x2 <= x && y2 <= y)
	    return true;
	return false;
    }
    
    public static boolean topRightFit(int x,int y, int l, int m,int a, int b){
	int x1 = a;
	int y1 = b;
	
	int x2 = a + l;
	int y2 = b + m;
	
	if(x1 >= 0 && y1 >=0 && x2 <= x && y2 <= y)
	    return true;
	return false;
    }
    
    public static boolean topLeftFit(int x,int y, int l, int m,int a, int b){
	int x1 = a - l;
	int y1 = b;
	
	int x2 = a;
	int y2 = b + m;
	
	if(x1 >= 0 && y1 >=0 && x2 <= x && y2 <= y)
	    return true;
	return false;
    }
    
}
