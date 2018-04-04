package codechef.LongMarch18.p1;

import java.util.Scanner;

//https://www.codechef.com/MARCH18B/problems/CHEGLOVE
public class Main {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	while(T-- > 0){
	    int N = sc.nextInt();
	    int L[] = new int[N];
	    int G[] = new int[N];
	    
	    for(int i=0; i< N; i++){
		L[i] = sc.nextInt();
	    }
	    
	    for(int i=0; i< N; i++){
		G[i] = sc.nextInt();
	    }
	    
	    boolean front = true;
	    
	    for(int i=0; i< N; i++){
		if(L[i] <= G[i]){
		    continue;
		}else{
		    front = false;
		    break;
		}
	    }
	    
	    boolean back = true;
	    
	    for(int i=0; i< N; i++){
		if(L[i] <= G[N-i-1]){
		    continue;
		}else{
		    back = false;
		    break;
		}
	    }
	    
	    if(front&&back){
		System.out.println("both");
	    }else if(front){
		System.out.println("front");
	    }else if(back){
		System.out.println("back");
	    }else{
		System.out.println("none");
	    }
	}
	sc.close();
    }
}
