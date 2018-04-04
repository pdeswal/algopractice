import java.util.Scanner;


public class SpiralTravsersalArray {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int rows = sc.nextInt();
	int cols = sc.nextInt();
	
	int[][] arr = new int[rows][cols];
	
	for(int i=0; i< rows; i++){
	    for(int j=0; j<cols; j++){
		arr[i][j] = sc.nextInt();
	    }
	}
	spiralTravsersal(arr, rows, cols);
	
	sc.close();
    }
    
    private static void spiralTravsersal(int[][] arr, int rows, int cols){
	
	int i=0, j=0, m = rows-1, n = cols-1;
	
	while(i <= m && j <= n){
	    for(int k=j; k<=n; k++){
		System.out.print(arr[i][k]+",");
	    }
	    i++;
	    
	    for(int k=i; k <=m; k++){
		System.out.print(arr[k][n]+",");
	    }
	    n--;
	    
	    if(i<=m){
		for(int k=n; k >=j; k--){
		    System.out.print(arr[m][k]+",");
		}
		m--;	
	    }
	    
	    if(j<=n){
		for(int k=m; k >=i; k--){
		    System.out.print(arr[k][j]+",");
		}
		j++;
	    }
	}
	
    }
}
