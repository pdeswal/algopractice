package lenskart.p2;

import java.util.Scanner;

public class Problem2 {
    public static void main(String[] args) {
	Scanner	sc = new Scanner(System.in);
	int T = sc.nextInt();
	while(T-- > 0){
	    int N = sc.nextInt();
	    int[] arr = new int[N];
	    
	    for(int i=0; i< N ; i++){
		arr[i] = sc.nextInt();
	    }
	    
	    
	    int maxIndex = getMaxIndex(arr, 0, N-1);
	    int minIndex = getMinIndex(arr, 0, N-1);
	    
	    if(minIndex == N-1 && maxIndex == 0 && arr.length > 2){
		int lSubMin = getMinIndex(arr, 0, N-2);
		int lSubMax = getMaxIndex(arr, 0, N-2);
		int leftMax = arr[lSubMax] - arr[lSubMin];
		
		int rSubMin = getMinIndex(arr, 1, N-1);
		int rSubArrMax = getMaxIndex(arr, 1, N-1);
		int rightMax = arr[rSubArrMax] - arr[rSubMin];
		
		int max = rightMax > leftMax ? rightMax : leftMax;
		
		System.out.println(max);
		
	    }else{
		System.out.println(arr[maxIndex] - arr[minIndex]);
	    }
	}
	
	
	sc.close();
    }
    
    private static int getMaxIndex(int[] arr, int left, int right){
	int index = left;
	int max = arr[left];
	
	for(int i=left+1; i<= right; i++){
	    if(max < arr[i]){
		index = i;
		max = arr[i];
	    }
	}
	
	return index;
    }
    
    private static int getMinIndex(int[] arr, int left, int right){
	int index = left;
	int min = arr[left];
	
	for(int i=left+1; i<= right; i++){
	    if(min > arr[i]){
		index = i;
		min = arr[i];
	    }
	}
	
	return index;
    }
    
    
    
}
