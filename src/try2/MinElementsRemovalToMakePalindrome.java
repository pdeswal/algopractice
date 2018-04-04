package try2;

public class MinElementsRemovalToMakePalindrome {
    public static void main(String[] args) {
	String str= "geeksforgeeks";
	char[] arr = str.toCharArray();
	int count = elementCountToMakePalindrome(arr, 0, arr.length-1,0);
	System.out.println(count);
    }
    
    public static int elementCountToMakePalindrome(char[] arr, int low, int high, int count){
	if(low >= high){
	    return 0;
	}
	if(arr[low] == arr[high]){
	    count = elementCountToMakePalindrome(arr, low+1, high-1, count+1);
	}else{
	    int count1 = elementCountToMakePalindrome(arr, low+1, high, count+1);
	    int count2 = elementCountToMakePalindrome(arr, low, high-1, count+1);
	    
	    count = count1 < count2 ? count1 : count2;
	    count++;
	}
	return count;
    }
}
