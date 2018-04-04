package cogoport.p1;

import java.util.Scanner;

public class TestClass {

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	String N = sc.next();
	char arr[] = N.toCharArray();
	int[] onesArray = new int [arr.length];
	int[] zeroArray = new int [arr.length];
	int count = 0;
	for(int i = 0; i < arr.length; i++ ){
	    if(arr[i] == '1'){
		onesArray[i] = count;
		count++;
	    }

	}
	count = 0;
	for(int i =arr.length-1; i >= 0; i-- ){
	    if(arr[i] == '0' )
		count++;	
	    else{
		zeroArray[i] = count;
		count=0;
	    }

	}

	long answer = 0;
	for(int i = 0; i < arr.length; i++ ){
	    if(arr[i] == '1' )
		answer += (onesArray[i] + 1) * (zeroArray[i] + 1);

	}

	System.out.println(answer);
	sc.close();
    }
}
