package practice.string;

/*
 * Counting number of permutations from interleaving of two string is also an interesting problem
 * Given are 2 string
 * ABCDE, 123	  	|   1 	|   2   |   3   |
 * Empty String		1	0 	0	0
 * 	A-		1	1	1	1
 * 	B-		1	2	3	4
 * 	C-		1	3	6	10
 * 	D-		1	4	10	20
 * 	E-		1	5	15	35
 * 
 * 	There are two things two any number at any point is sum of number in previous line till that index
 * 	A[i][j] = A[i-1][0] + A[i-1][1] + ......... + A[i-1][j]
 * 	But this could be further broken down to
 * 	Any number at any point is sum of 2 number
 * 	A[i][j] = A[i-1][j] + A[i][j-1]
 * 	
 * */
public class Interleaving {
    static int count = 0;
    public static void main(String[] args) {
	String A = "ABCD";
	String B = "123";
	
	String result="";
	System.out.println(countPermutations(A,B));
	printAllPermutations(A.toCharArray(), B.toCharArray(), 0, 0, result);
	
    }
    
    private static void printAllPermutations(char[] A, char[] B, int aIndex, int bIndex, String result){
	if(aIndex == A.length){
	    for(int i=bIndex; i<B.length; i++){
		result += B[i];
	    }
	    count++;
	    System.out.println(count+":\t"+result);
	    return;
	}
	
	if(bIndex == B.length){
	    for(int i=aIndex; i<A.length; i++){
		result += A[i];
	    }
	    count++;
	    System.out.println(count+":\t"+result);
	    return;
	}
	
	printAllPermutations(A,B,aIndex+1,bIndex,result+A[aIndex]);
	printAllPermutations(A,B,aIndex,bIndex+1,result+B[bIndex]);
	
    }
    
    private static int countPermutations(String A, String B){
	int ans=0;
	
	int[][] couting = new int[A.length()+1][B.length()+1];
	
	couting[0][0] = 1;
	for(int i=1; i<=A.length(); i++){
	    for(int j=0; j<=B.length(); j++){
		if(j==0){
		    couting[i][j] = couting[i-1][j];
		}else{
		    couting[i][j] = couting[i-1][j] + couting[i][j-1];
		}
		
	    }
	}
	
	for(int i=0; i<=B.length(); i++){
	    ans += couting[A.length()][i];
	}
	
	return ans;
    }
}
