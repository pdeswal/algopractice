package codechef.LongFeb18.p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
	MyScanner sc = new MyScanner();
	int T = sc.nextInt();
	while(T-- > 0){
	    
	}
    }
    
    public int countLovely(String str){
	int count = 0;
	char[] arr = str.toCharArray();
	Set<Character> set = new HashSet<>(10);
	for(int i=0; i<=arr.length-4; i++){
	    set.clear();
	    for(;;){
		
	    }
	}
	return count;
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
