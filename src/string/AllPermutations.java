package string;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class AllPermutations {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	
	int T = sc.nextInt();
	sc.nextLine();
	while(T-- > 0){
	    String str = sc.nextLine();
	    Set<String> set = new TreeSet<>();
	    printAllPermutaions("", str, set);
	    for(String s : set){
		System.out.print(s+" ");
	    }
	    System.out.println();
	}
	
	sc.close();
	
    }
    
    private static void printAllPermutaions(String permuted, String rem, Set<String> set){
	if(rem.length() == 0){
	    //System.out.print(permuted + " ");
	    set.add(permuted);
	    return;
	}
	
	for(int i=0; i< rem.length(); i++){
	    printAllPermutaions(permuted + rem.charAt(i), rem.subSequence(0, i) + rem.substring(i+1), set);
	}
    }
}
