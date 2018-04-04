import java.util.Arrays;
import java.util.Scanner;


public class TestClass {
    public static void main(String[] args) {
	int n;
	
	Scanner s = new Scanner(System.in);
	n = s.nextInt();
	int workersCount = 2*n;
	int [] workers = new int[workersCount];
	
	for(int i=0; i< workersCount; i++){
	    workers[i] = s.nextInt();
	}
	
	Arrays.sort(workers);
	
	int answer = 0;
	
	for(int i=0; i< workersCount; i+=2){
	    answer = answer + Math.min(workers[i], workers[i+1]);
	}
	
	System.out.println(answer);
	
	s.close();
	
    }
}
