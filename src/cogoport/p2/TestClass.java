package cogoport.p2;

import java.util.Scanner;

public class TestClass {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	
	while(T-- > 0){
	    int N = sc.nextInt();
	    int K = sc.nextInt();
	    
	    char[] smaller = new char[K];
	    //Assume its is 1000000.....and so on
	    smaller[0] = '1';
	    for(int i=1; i< K; i++){
		smaller[i] = '0';
	    }
	    
	    //make Bigger array from it.
	    char[] bigger = new char[N];
	    
	    int j=0;
	    for(int i=0; i<N; i++){
		bigger[i] = smaller[j];
		j++;
		j = j % K;
	    }
	    
	    convertLastCharIntoOneIfRequired(bigger, smaller, N, K);
	    int index = isPalindrome(bigger);
	    int countOfZeros = countNumberOfZeros(bigger);
	    
	    while(countOfZeros > 0 && index != -1){
		countOfZeros = tryString(bigger, smaller, N, K, index, countOfZeros);
	    }
	    
	    if(countOfZeros == 0){
		System.out.println("impossible");
	    }else{
		System.out.println(new String(bigger));
	    }
	}
	sc.close();
    }
    
    private static int tryString(char[] bigger, char[] smaller, int N, int K, int index, int countOfZero){
	//this method will return the new count of zeros
	int mod = index % K;
	//change the smaller array
	smaller[mod] = '1';
	//change the bigger
	int i = mod;
	while(i < N){
	    bigger[i] = '1';
	    i = i + K;
	    countOfZero --;  
	}
	
	return countOfZero;
    }
    private static void convertLastCharIntoOneIfRequired(char[] bigger, char[] smaller, int N, int K){
	if(bigger[N-1] == '1'){
	    //no changes are required
	}else{
	    //convert the last digit into one and change bigger and smaller array acc to that
	    int mod = (N-1) % K;
	    //change the smaller array
	    smaller[mod] = '1';
	    //change the bigger
	    int i = N-1;
	    while(i > 0){
		bigger[i] = '1';
		i = i - K;
	    }
	}
    }
    
    private static int countNumberOfZeros(char[] bigger){
	int count = 0;
	for(int i=0; i< bigger.length; i++){
	    if(bigger[i] == '0')
		count++;
	}
	
	return count;
    }
    private static int isPalindrome(char[] bigger){
	int index = -1; //if it is -1 then it is palindrome. otherwise it will return the index where it is not a palindrome and value is 0.
	int length = bigger.length;
	int begin = 0;
	int end = length-1;
	int middle = begin + end /2;
	
	for(int i=0; i<=middle; i++){
	    if(bigger[begin] == bigger[end]){
		begin++;
		end--;
	    }else{
		if(bigger[begin] == '0'){
		    return begin;
		}else{
		    return end;
		}
	    }
	}
	
	return -1;
    }
}
