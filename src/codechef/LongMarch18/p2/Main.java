package codechef.LongMarch18.p2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int T = sc.nextInt();
	while(T-- > 0){
	    int N = sc.nextInt();
	    int[] price = new int[N];
	    int[] qty = new int[N];
	    int[] dis = new int[N];
	    
	    for(int i=0; i< N; i++){
		price[i] = sc.nextInt();
		qty[i] = sc.nextInt();
		dis[i] = sc.nextInt();
	    }
	    
	    double originalPrice = 0;
	    double actualPrice = 0;
	    for(int i=0; i< N; i++){
		originalPrice+=(price[i] * qty[i]);
		
		double newPrice = price[i] * qty[i] * ((100 + dis[i])*1.0/100);
		newPrice = newPrice * ((100 - dis[i])*1.0/100);
		actualPrice += newPrice;
		
	    }
	    
	    System.out.println(originalPrice-actualPrice);
	}
	sc.close();
    }
}
