package codechef.LongMarch18.p5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int N = sc.nextInt();
	int Q = sc.nextInt();
	
	int[] A = new int[N];
	int[][] cache = new int[31][N+1];
	
	for(int i=0; i< N; i++){
	    A[i] = sc.nextInt();
	}
	
	
	int number = 1;
	int bitCounts = 0;
	
	for(int i=0; i<31; i++){
	    bitCounts = 0;
	    for(int j=0; j< N; j++){
		int temp = A[j];
		int andResult = temp & number;
		if(andResult == number){
		    //then that bit contains 1 at that place
		    bitCounts++;
		}
		cache[i][j+1] = bitCounts;
	    }
	    
	    number = number << 1;
	}
	
	for(int i=0; i< Q; i++){
	    int L = sc.nextInt();
	    int R = sc.nextInt();
	    
	    int answer = Integer.MAX_VALUE;
	    
	    for(int k=0; k<31; k++){
		int countOf1BetweenLR = cache[k][R]-cache[k][L-1];
		int countOf0BetweenLR = R-L+1 - countOf1BetweenLR;
		
		if(countOf0BetweenLR <= countOf1BetweenLR){
		    //we need to mark the bit to zero to keep the answer minimum
		    answer = turnOffK(answer,k);
		}
	    }
	    
	    System.out.println(answer);
	}
	
	sc.close();
    }
    
    static int turnOffK(int n, int k)
    {
        // k must be greater than 0
        if (k < 0) return n;
     
        // Do & of n with a number with all set bits except
        // the k'th bit
        return (n & ~(1 << (k)));
    }
}
