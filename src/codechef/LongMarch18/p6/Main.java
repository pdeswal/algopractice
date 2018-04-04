package codechef.LongMarch18.p6;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	while(T-- > 0){
	    int N = sc.nextInt();
	    int[] S = new int[N];
	    
	    for(int i=0; i < N; i++){
		S[i] = sc.nextInt();
	    }
	    
	    int[] votes = countVotes(S, N);
	    
	    for(int i=0; i< N; i++){
		System.out.print(votes[i]+" ");
	    }
	    System.out.println();
	}
	sc.close();
    }
    
    public static int[] countVotes(int[] S, int N){
	int[] votes = new int[N];
	
	for(int i=0; i< N; i++){
	    int sum = 0;
	    for(int j=i-1; j>=0; j--){
		if(sum <= S[i]){
		    votes[j]++;
		}
		sum+=S[j];
		if(sum>S[i]){
		    break;
		}
		
	    }
	    sum=0;
	    for(int j=i+1; j<N; j++){
		if(sum <= S[i]){
		    votes[j]++;
		}
		sum+=S[j];
		if(sum>S[i]){
		    break;
		}
		
	    }
	}
	return votes;
    }
}
