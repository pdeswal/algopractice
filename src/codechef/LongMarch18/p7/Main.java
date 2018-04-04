package codechef.LongMarch18.p7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
	MyScanner sc = new MyScanner();
	int N = sc.nextInt();
	int Q = sc.nextInt();
	int[] A = new int[N];

	for(int i=0; i<N; i++)
	    A[i] = sc.nextInt();

	SegmentTree tree = new SegmentTree(A);
	tree.createTree(0, N-1, 0);

	for(int i=0; i< Q; i++){
	    int action = sc.nextInt();
	    if(action == 1){
		int pos = sc.nextInt();
		int val = sc.nextInt();
		tree.findAndupdateIndexAndCallParentUpdate(pos-1, val, 0, N-1, 0);
	    }else if(action == 2){
		int l = sc.nextInt();
		int r = sc.nextInt();
		int[] mergedArr = tree.query(l-1, r-1, 0, N-1, 0);
		System.out.println(findLargestTriangles(mergedArr));
	    }
	}

	sc.close();
    }

    private static long findLargestTriangles(int[] A){
	if(A.length < 3){
	    return 0;
	}

	int i=0;
	while(i<A.length-2 && A[i] >= A[i+1]+A[i+2]){
	    i++;
	}

	if(i >= A.length-2){
	    return 0;
	}

	long parameter = 0;
	parameter = parameter+A[i]+A[i+1]+A[i+2];
	return parameter;


    }

    static class SegmentTree{
	int[] A;
	int[][] segTree;
	int segTreeSize;
	private final int K = 50;
	SegmentTree(int[] A){
	    this.A = A;
	    int digit = 0;
	    int arrSize=A.length;
	    while(arrSize > (1 << digit))
		digit++;
	    segTreeSize = (1 << (digit+1));
	    segTree = new int[segTreeSize][];
	}
	void createTree(int low, int high, int pos){
	    if(low == high){
		segTree[pos] = new int[1];
		segTree[pos][0] = A[low];
		return;
	    }

	    int mid = (low + high)/2;
	    createTree(low, mid, (pos << 1) + 1);
	    createTree(mid+1, high, (pos << 1) + 2);

	    segTree[pos] = mergeSortedArraysWithMaximumKElements(segTree[(pos << 1) + 1], segTree[(pos << 1) + 2]);

	}

	void findAndupdateIndexAndCallParentUpdate(int index, int newValue, int low, int high, int pos){
	    
	    if(index < low || index > high){
		return;
	    }
	    if(low == high){
		segTree[pos][0] = newValue;
		//now we know the position, now we need to update all its parents
		updateUpward(pos);
		return;
	    }
	    
	    int mid = (low + high)/2;
	    
	    if(index <= mid){
		findAndupdateIndexAndCallParentUpdate(index, newValue, low, mid, (pos << 1)+1);
	    }else{
		findAndupdateIndexAndCallParentUpdate(index, newValue, mid+1, high, (pos << 1)+2);
	    }
	    
	}
	private void updateUpward(int pos){
	    pos = pos-1 >> 1;
	    mergeSortedArraysWithMaximumKElements(segTree[(pos << 1) + 1], segTree[(pos << 1) + 2], segTree[pos]);
	    if(pos==0){
		return;
	    }
	    updateUpward(pos);
	}
	int[] query(int qlow, int qhigh, int low, int high, int pos){
	    if(qlow > high || qhigh < low){
		return new int[0];
	    }
	    if(qlow<=low && qhigh >= high){
		return segTree[pos];
	    }

	    int mid = (low + high)/2;

	    return mergeSortedArraysWithMaximumKElements(
		    query(qlow, qhigh, low, mid, (pos << 1)+1),
		    query(qlow, qhigh, mid+1, high, (pos << 1)+2)
		    );
	}

	private void mergeSortedArraysWithMaximumKElements(int[] A, int[] B, int[] resultedArray){
	    int resultLength = resultedArray.length;
	    int i=0; int j=0;int k=0;

	    while(k < resultLength){
		if(i< A.length && (j >= B.length || A[i] > B[j])){
		    resultedArray[k] = A[i];
		    i++;
		}else{
		    resultedArray[k] = B[j];
		    j++;
		}
		k++;
	    }

	}
	private int[] mergeSortedArraysWithMaximumKElements(int[] A, int[] B){
	    int resultLength = A.length + B.length >= K ? K : A.length + B.length;
	    int[] resultedArray = new int[resultLength];
	    mergeSortedArraysWithMaximumKElements(A, B, resultedArray);
	    return resultedArray;

	}
    }
    static class MyScanner {
	BufferedReader br;
	StringTokenizer st;

	public MyScanner() {
	    br = new BufferedReader(new InputStreamReader(System.in));
	}

	private String next() {
	    while (st == null || !st.hasMoreElements()) {
		try {
		    st = new StringTokenizer(br.readLine());
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	    return st.nextToken();
	}

	int nextInt() {
	    return Integer.parseInt(next());
	}

	long nextLong() {
	    return Long.parseLong(next());
	}

	double nextDouble() {
	    return Double.parseDouble(next());
	}

	char nextChar(){
	    return next().charAt(0);
	}

	String nextLine(){
	    String str = "";
	    try {
		str = br.readLine();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return str;
	}

	void close(){
	    if(br != null){
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}

    }
}



