package codechef.LongMarch18.p3;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	while(T-- > 0){
	    int N = sc.nextInt();
	    int[] arr = new int[N];
	    
	    for(int i=0; i< N ; i++){
		arr[i] = sc.nextInt();
	    }
	    
	    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	    for(int i=0; i< N ; i++){
		if(map.containsKey(arr[i])){
		    map.put(arr[i], map.get(arr[i])+1);
		}else{
		    map.put(arr[i], 1);
		}
	    }
	    
	    int answer = 0;
	    for(Integer i : map.values()){
		answer += i -1;
	    }
	    
	    System.out.println(answer);
	}
	sc.close();
    }
}
