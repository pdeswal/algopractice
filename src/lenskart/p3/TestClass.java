package lenskart.p3;

import java.util.Scanner;

public class TestClass
{
    static int st[]; 
 
    private static int min(int x, int y) {
        return (x < y) ? x : y;
    }
 
    private static int mid(int s, int e) {
        return s + (e - s) / 2;
    }
 
    private static int recRmq(int segStart, int segEnd, int queryStart, int queryEnd, int index)
    {
        if (queryStart <= segStart && queryEnd >= segEnd)
            return st[index];
 
        if (segEnd < queryStart || segStart > queryEnd)
            return Integer.MAX_VALUE;
 
        int mid = mid(segStart, segEnd);
        return min(recRmq(segStart, mid, queryStart, queryEnd, 2 * index + 1),
                recRmq(mid + 1, segEnd, queryStart, queryEnd, 2 * index + 2));
    }
 
    private static int RMQ(int n, int queryStart, int queryEnd)
    {
        if (queryStart < 0 || queryEnd > n - 1 || queryStart > queryEnd) {
            System.out.println("Invalid Input");
            return -1;
        }
 
        return recRmq(0, n - 1, queryStart, queryEnd, 0);
    }
 
    private static int recSt(int arr[], int segStart, int segEnd, int si)
    {
        if (segStart == segEnd) {
            st[si] = arr[segStart];
            return arr[segStart];
        }
 
        int mid = mid(segStart, segEnd);
        st[si] = min(recSt(arr, segStart, mid, si * 2 + 1),
                recSt(arr, mid + 1, segEnd, si * 2 + 2));
        return st[si];
    }
 
    private static void createSeqTree(int arr[], int n)
    {
 
        int x = (int) (Math.ceil(Math.log(n) / Math.log(2)));
 
        int max_size = 2 * (int) Math.pow(2, x) - 1;
        st = new int[max_size]; // allocate memory
 
        recSt(arr, 0, n - 1, 0);
    }
 
    public static void main(String args[]) 
    {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	while(T-- > 0){
	    try{
		int N = sc.nextInt();
		    int[] arr = new int[N];
		    for(int i=0; i < N ; i++){
			arr[i] = sc.nextInt();
		    }
		    
		    createSeqTree(arr, N);
		    
		    long max = Long.MIN_VALUE;
		    
		    for(int i=0; i< N; i++){
			for(int j=i; j<N; j++){
			    long temp = (j-i+1) * RMQ(N, i, j);
			    max = temp > max ? temp : max;
			}
		    }
		    
		    System.out.println(max);
	    }catch(Exception  e){
		System.out.println(0);
	    }
	    
	}
	sc.close();
    }
}
