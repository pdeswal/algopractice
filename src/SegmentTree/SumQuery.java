package SegmentTree;

import java.util.Scanner;

public class SumQuery {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int N = sc.nextInt();
	int[] arr = new int[N];
	for(int i=0; i<N; i++){
	    arr[i] = sc.nextInt();
	}
	
	long[] segTree = buildSegTree(arr, N);
	
	int Q = sc.nextInt();
	
	for(int i=0; i< Q; i++){
	    int L = sc.nextInt();
	    int R = sc.nextInt();
	    long ans = rangeQuery(segTree, L, R, 0, N-1, 0);
	    System.out.println(ans);
	    
	}
	
	sc.close();
    }
    
    public static long[] buildSegTree(int[] arr, int N){
	int digit = 0;
	while(N > (1 << digit)){
	    digit++;
	}
	int segTreeSize = 1 << (digit + 1);
	
	long[] segTree = new long[segTreeSize];
	buildTree(arr, segTree, 0, N-1, 0);
	return segTree;
    }
    
    public static void buildTree(int[] arr, long[] segTree, int low, int high, int pos){
	if(low == high){
	    segTree[pos] = arr[low];
	    return;
	}
	
	int mid = (low + high)/2;
	buildTree(arr, segTree, low, mid, (pos << 1)+1);
	buildTree(arr, segTree, mid+1, high, (pos << 1)+2);
	segTree[pos] = segTree[(pos << 1)+1] + segTree[(pos << 1)+2];
    }
    
    public static long rangeQuery(long[] segTree, int qLow, int qHigh, int low, int high, int pos){
	if(qLow > high || qHigh < low ){
	    return 0;
	}
	
	if(qLow <= low && qHigh >= high){
	    return segTree[pos];
	}
	
	int mid = (low + high) /2;
	return rangeQuery(segTree, qLow, qHigh, low, mid, (pos<<1)+1) + rangeQuery(segTree, qLow, qHigh, mid+1, high, (pos<<1)+2);
	
    }
}
