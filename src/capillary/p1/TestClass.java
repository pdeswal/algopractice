package capillary.p1;

import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	
	
	
	
	while(T-- > 0){
	    int N = sc.nextInt();
	    int K = sc.nextInt();
	    int Q = sc.nextInt();
	    int[] arr = new int[N];
	    for(int i=0; i< N; i++){
		arr[i] = sc.nextInt();
	    }
	    	    
	    int number = 0;
	    if(K==1){
		number = findMaxInArr(arr);
	    }else if(K==2){
		number = Math.min(arr[0], arr[N-1]);
	    }else if(K >= 3){
		number = findMinInArr(arr);
	    }
	    
	    if(number < Q){
		System.out.println(number);
	    }else{
		System.out.println("NO");
	    }
	}
	
	
	
	sc.close();
    }
    
    public static int findMaxInArr(int[] arr){
	int maxNumber = arr[0];
	
	for(int i=1; i< arr.length; i++){
	    if(arr[i] > maxNumber){
		maxNumber = arr[i];
	    }
	}
	
	return maxNumber;
    }
    
    public static int findMinInArr(int[] arr){
	int minNumber = arr[0];
	
	for(int i=1; i< arr.length; i++){
	    if(arr[i] < minNumber){
		minNumber = arr[i];
	    }
	}
	return minNumber;
    }
    
}
