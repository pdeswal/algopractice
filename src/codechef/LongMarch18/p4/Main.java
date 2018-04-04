package codechef.LongMarch18.p4;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	while(T-- > 0){
	    int N = sc.nextInt();
	    int H = sc.nextInt();
	    
	    int[] A = new int[N];
	    
	    for(int i=0; i< N; i++){
		A[i] = sc.nextInt();
	    }
	    
	    int answer = findMimimumEatingSpeed(A, N, H);
	    System.out.println(answer);
	}
	sc.close();
    }
    
    private static int findMimimumEatingSpeed(int[] A, int N, int H){
	Arrays.sort(A);
	int low = 1; int high = A[N-1];
	
	int lastSuccessValue = high;
	
	while(low <= high){
	    boolean isOk = false;
	    
	    int newtryValueHours = 0;
	    int newTryValue = (low + high) /2;
	    newtryValueHours = 0;

	    isOk = true;
	    for(int j=0; j<N; j++){
		if(A[j] <= newTryValue){
		    newtryValueHours++;
		}else if(A[j] % newTryValue == 0){
		    newtryValueHours += (A[j]/newTryValue);
		}else{
		    newtryValueHours += (A[j]/newTryValue) + 1;
		}

		if(newtryValueHours > H){
		    isOk = false;
		    break;
		}
	    }
	    
	    if(isOk){
		lastSuccessValue = newTryValue;
		high = newTryValue-1;
	    }else{
		low = newTryValue+1;
	    }
	    
	}
	return lastSuccessValue;
	
    }
}
