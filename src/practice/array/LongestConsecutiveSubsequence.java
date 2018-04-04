package practice.array;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class LongestConsecutiveSubsequence {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int N = sc.nextInt();
	int[] arr = new int[N];
	for(int i=0; i< N; i++){
	    arr[i] = sc.nextInt();
	}
	System.out.println(findLongestConsecutiveSubsequence(arr));
	sc.close();
    }
    
    public static int findLongestConsecutiveSubsequence(int[] arr){
	
	int maxLength = 0;
	Map<Integer, Integer> starting = new HashMap<>();
	Map<Integer, Integer> ending = new HashMap<>();
	
	for(int i=0; i< arr.length; i++){
	    int temp = arr[i];
	    
	    if(starting.get(temp+1) != null){
		int len = starting.get(temp+1);
		starting.remove(temp+1);
		starting.put(temp, len+1);
		ending.put(temp+len, len+1);
		maxLength = maxLength < len+1 ? len+1 : maxLength;
	    }else{
		starting.put(temp, 1);
		ending.put(temp, 1);
		maxLength = maxLength < 1 ? 1 : maxLength;
	    }
	    
	    if(ending.get(temp-1) != null){
		int len = ending.get(temp-1);
		int newLenght = starting.get(temp)+1;
		starting.put(temp-1-len+1, newLenght);
		ending.put(temp, newLenght);
		ending.remove(temp-1);
		maxLength = maxLength < newLenght ? newLenght : maxLength;
	    }
	}
	
	return maxLength;
    }
}
