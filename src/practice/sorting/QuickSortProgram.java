package practice.sorting;

public class QuickSortProgram {
    public static void main(String[] args) {
	int[] arr = {8, 6, 4, 29, 3, 7, 8, 13, 15, 21, 1, 3, 2, 25};
	quickSort(arr, 0, arr.length-1);
	
	for(int i=0; i< arr.length; i++){
	    System.out.print(arr[i] + " ");
	}
	System.out.println();
    }
    
    public static void quickSort(int[] arr, int low, int high){
	
	if(low < high){
	    int pivotIndex = partition(arr, low, high);
	    quickSort(arr, low, pivotIndex-1);
	    quickSort(arr, pivotIndex+1, high);
	}
	
    }
    public static int partition(int[] arr, int low, int high){
	int pivot = arr[high];
	
	int i = low-1;
	for(int j = low; j < high; j++){
	    if(arr[j] <= pivot){
		i++;
		int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
	    }
	}
	
	int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
	
	return i+1;
    }
}
