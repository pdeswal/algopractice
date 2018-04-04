import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;


public class CandidateCode {
    private static Node[] nodes = new Node[10];
    public static int combinationCounts(int level)
    {
	    //Write code here
	    
	    
	    nodes[0] = new Node('A', "ABD".toCharArray());
	    nodes[1] = new Node('B', "BAEC".toCharArray());
	    nodes[2] = new Node('C', "CBF".toCharArray());
	    nodes[3] = new Node('D', "DAEG".toCharArray());
	    nodes[4] = new Node('E', "EBDFH".toCharArray());
	    nodes[5] = new Node('F', "FCEI".toCharArray());
	    nodes[6] = new Node('G', "GDH".toCharArray());
	    nodes[7] = new Node('H', "HEGJI".toCharArray());
	    nodes[8] = new Node('I', "IFH".toCharArray());
	    nodes[9] = new Node('J', "JH".toCharArray());
	    
	    int moves = 0; 
	    for(Node node : nodes){
		int temp = count2(level, node);;
		moves += temp; 
	    }
	    
	    return moves;
    }
    public static int count1(int level, Node node){
	int nbs = node.nbs.length;
	return (int)Math.pow(nbs, level-1);
    }

    public static int count(int level, Node node){
        Queue<Node> queue = new LinkedList<>();
        int currLevel = 1;
        
        queue.add(node);
        
        while(currLevel < level){
            node = queue.poll();
            for(char c : node.nbs){
        	queue.add(new Node(c, nodes[c-'A'].nbs, node.level + 1));
            }
            node = queue.peek();
            currLevel = node.level;
        }
        
        return queue.size();
    }
    
    public static int count2(int level, Node node){
	int[][] arr = new int[10][level+1];
	arr[node.name-'A'][1] = 1;
        for(int i=2; i<=level; i++){
            for(int j=0; j<10; j++){
        	arr[j][i] = arr[j][i-1];
            }
            
            for(int j=0; j< 10; j++){
        	if(arr[j][i-1] > 0){
        	    Node n = nodes[j];
        	    for(char c : n.nbs){
        		if(c != n.name){
        		    arr[c-'A'][i] += arr[j][i-1];
        		}
        	    }
        	    
        	}
            }
        }
        
        int sum = 0;
        for(int i=0; i< 10; i++){
            sum += arr[i][level];
        }
        
        return sum;
    }
    
    public static class Node{
	char name;
	char[] nbs;
	int level = 1;
	Node(char name, char[] nbs){
	    this.name = name;
	    this.nbs = nbs;
	}
	Node(char name, char[] nbs, int level){
	    this.name = name;
	    this.nbs = nbs;
	    this.level = level;
	}
	public String toString(){
	    return name+"";
	}
    }
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int output = 0;
	int ip1=0;
	for(int i =0; i< 10; i++){
	 ip1 = sc.nextInt();
	
	output = combinationCounts(ip1);
	System.out.println(ip1+" "+output);
	}
	
    }
}
