package practice.sorting;

public class MergeSort {
    public static void main(String[] args) {
	int[] arr = {8, 6, 4, 29, 3, 7, 8, 13, 15, 21, 1, 3, 2, 25};
	sort(arr, 0, arr.length-1);
	
	for(int i=0; i< arr.length; i++){
	    System.out.print(arr[i] + " ");
	}
	System.out.println();
    }
    
    public static void sort(int[] arr, int low, int high){
	
    }
}
