package codechef.LongMarch18.p7;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main_brute {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int N = sc.nextInt();
	int Q = sc.nextInt();
	int[] A = new int[N];
	
	PriorityQueue<Integer> queue = new PriorityQueue<>(N, new Comparator<Integer>() {

	    @Override
	    public int compare(Integer o1, Integer o2) {
		return o2.compareTo(o1);
	    }
	});
	
	for(int i=0; i<N; i++)
	    A[i] = sc.nextInt();
	
	for(int i=0; i< Q; i++){
	    int action = sc.nextInt();
	    if(action == 1){
		int pos = sc.nextInt();
		int val = sc.nextInt();
		A[pos-1] = val;
	    }else if(action == 2){
		int l = sc.nextInt();
		int r = sc.nextInt();
		System.out.println(findLargestTriangles(A, l, r, queue));
	    }
	}
	sc.close();
    }
    
    private static long findLargestTriangles(int[] A, int l, int r, PriorityQueue<Integer> queue){
	long ans = 0;
	if(r-l+1 < 3){
	    return ans;
	}
	queue.clear();
	
	for(int i=l-1; i<r; i++){
	    queue.add(A[i]);
	}
	
	long[] arr = new long[3];
	arr[0] = queue.remove();
	arr[1] = queue.remove();
	arr[2] = queue.remove();
	
	while(!queue.isEmpty() && arr[2] + arr[1] <= arr[0]){
	    arr[0] = arr[1];
	    arr[1] = arr[2];
	    arr[2] = queue.remove();
	}
	
	if(arr[2] + arr[1] > arr[0]){
	    ans = arr[2] + arr[1] + arr[0];
	    return ans;
	}else{
	    return ans;
	}
	
    }
}
