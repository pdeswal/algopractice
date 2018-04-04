package lenskart.p1;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Problem1 {
      
      public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int T = sc.nextInt();
            while(T-- > 0){
                  String str = sc.next();
                  printWinner(str);
            }
            sc.close();
            
      }

      private static void printWinner(String str) {
	    Set<Character> set = new HashSet<Character>(str.length());
            char[] cArr = str.toCharArray();
            int len = cArr.length;
                         
            
            for(int i = 0; i < len; i++){
                  if(!set.contains(cArr[i])){
                        set.add(cArr[i]);
                  }
            }
            
            if(set.size()%2==0){
                  System.out.println("Player2");
            }else{
                  System.out.println("Player1");
            }
      }
}
