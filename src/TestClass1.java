import java.util.Scanner;


public class TestClass1 {
    public static void main(String[] args) {
	int inputStringLength , numberOfQueries;
	Scanner s = new Scanner(System.in);
	inputStringLength = s.nextInt();
	numberOfQueries = s.nextInt();
	String inputString = s.nextLine();
	inputString = s.nextLine();
	
	char [] arr = inputString.toCharArray();
	int [] longValues = new int[inputStringLength];
	int longestSubArr = 0;
	longestSubArr = longestSubarray(arr, longValues, inputStringLength);
	
	for(int i=0; i<numberOfQueries; i++){
	    int query = s.nextInt();
	    if(query == 1){
		//print the maxlength
		System.out.println(longestSubArr);
	    }else{
		int updateAt = s.nextInt();
		longestSubArr = rippleEffect(arr, longValues, inputStringLength, updateAt-1, longestSubArr);
	    }
	}
	
    }
    public static int rippleEffect(char[] inputArray, int [] valueArray, int n, int updateIndex, int subArrayLength){
	int newLongest = 0;
	if(inputArray[updateIndex] == '1'){
	    return subArrayLength;
	}else{
	    inputArray[updateIndex] = '1';
	    int left = updateIndex -1;
	    int right = updateIndex + 1;
	    
	    if(left >= 0 && right < n && inputArray[left] == '1' && inputArray[right] == '1'){
		int leftLong = valueArray[left];
		int rightLong = valueArray[right];
		
		valueArray[updateIndex+1] = 0;
		valueArray[updateIndex-1] = 0;
		
		valueArray[updateIndex-leftLong] = leftLong+rightLong+1;
		valueArray[updateIndex+rightLong] = leftLong+rightLong+1;
		
		
		newLongest = leftLong+rightLong+1;
	    }
	    else if(left >= 0 && inputArray[left] == '1'){
		int leftLong = valueArray[left];
		
		valueArray[updateIndex-1] = 0;
		valueArray[updateIndex-leftLong] = leftLong+1;
		valueArray[updateIndex] = leftLong+1;
		newLongest = leftLong+1;
		
	    }
	    else if(right < n && inputArray[right] == '1'){		
		int rightLong = valueArray[right];

		valueArray[updateIndex+1] = 0;
		valueArray[updateIndex+rightLong] = rightLong+1;
		valueArray[updateIndex] = rightLong+1;
		newLongest = rightLong+1;
		
	    }
	    else{
		valueArray[updateIndex] = 1;
		newLongest =1;
	    }
	    
	}
	return Math.max(newLongest, subArrayLength);
    }
    public static int longestSubarray(char[] inputArray, int [] valueArray, int inputLength){
	int longest = 0;
	int currLong = 0;
	int start = 0;
	int end = 0;
	for(int i=0; i<inputLength; i++){
	    if(inputArray[i] == '0'){
		longest = Math.max(longest, currLong);
		
		//endIndex
		end = i-1;
		if(start >= 0){
		    if(end >= 0){
			    valueArray[start] = valueArray[end] = currLong;
			}else{
			    valueArray[start] = currLong;
			}   
		}
		
		start = -1;
		currLong = 0;
		
	    }else{
		currLong ++;
		if(currLong == 1){
		    //startIndex
		    start = i;
		}
		if(i == inputLength-1){
		    end = i;
		    valueArray[start] = valueArray[end] = currLong;
		}
	    }
	}
	longest = Math.max(longest, currLong);
	return longest;
    }
}
