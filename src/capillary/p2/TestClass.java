package capillary.p2;

import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	Point stick1 = new Point();
	stick1.x = sc.nextInt();
	stick1.y = sc.nextInt();
	
	Point stick2 = new Point();
	stick2.x = sc.nextInt();
	stick2.y = sc.nextInt();
	
	int N = sc.nextInt();
	
	Point[] points = new Point[N];
	
	for(int i=0; i< N; i++){
	    points[i] = new Point();
	    points[i].x = sc.nextInt();
	}
	
	for(int i=0; i< N; i++){
	    points[i].y = sc.nextInt();
	}
	
	int Q = sc.nextInt();
	
	for(int i=0; i< Q; i++){
	    int time = sc.nextInt(); //time is radius of circle
	    
	    int count = 0;
	    for(int j=0; j< N; j++){
		if(isPointInsideBothCircles(stick1, stick2, points[j], time)){
		    count++;
		}
	    }
	    System.out.print(count + " ");
	}
	
	sc.close();
    }
    private static boolean isPointInsideCircle(Point c, Point p, int r){
	boolean isInside = false;
	
	int radiusSquare = r * r;
	int distanceSquare = ((c.x - p.x) * (c.x - p.x)) + ((c.y - p.y) * (c.y - p.y));
	
	isInside = radiusSquare >= distanceSquare;
	return isInside;
    }
    
    private static boolean isPointInsideBothCircles(Point c1, Point c2, Point p, int r){
	boolean insideCircle1 = isPointInsideCircle(c1, p, r);
	
	if(insideCircle1){
	    return isPointInsideCircle(c2, p, r);
	}
	return false;
	
    }
    
    
    static class Point{
	int x;
	int y;
    }
}
