package practice.array;

import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		while(T-- > 0){
		    int N = sc.nextInt();
		    int[] arr = new int[N];
		    
		    for(int i=0; i<arr.length; i++){
		        arr[i] = sc.nextInt();
		    }
		    
		    findMinBinary(arr, 0, N);
		}
		sc.close();
	}
	
	public static void findMinBinary(int[] arr, int left, int right){
	    int mid = (left + right) / 2;
	    if(arr.length == 1){
		System.out.println(arr[0]);
		return;
	    }
	    /*if(arr.length==2){
	        //element found. handling two size array or subarray
	        int min = arr[left] > arr[right-1] ? arr[right-1] : arr[left];
	        System.out.println(min);
	        return;
	    }*/
	    if(arr[mid] <= arr[previousElement(mid, arr.length)] && arr[mid] <= arr[nextElement(mid, arr.length)]){
	        System.out.println(arr[mid]);
	        return;
	    }
	    if(arr[mid] >= arr[arr.length-1] && mid < arr.length-1){
	        //it is present in right side
	        findMinBinary(arr, mid+1, right);
	    }else{
	        findMinBinary(arr, left, mid-1);
	    }
	}
	public static int nextElement(int i, int arrayLength){
	    return i == arrayLength-1 ? 0 : i+1;
	}	
	public static int previousElement(int i, int arrayLength){
	    return i==0 ? arrayLength-1 : i-1;
	}
}