import java.util.*;
import java.lang.*;
import java.io.*;

class GFG {
	public static void main (String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		while(T-- > 0){
		    int N = sc.nextInt();
		    StringBuffer buf = new StringBuffer();
		    StreamCountHolder holder = new StreamCountHolder(N);
		    for(int i=0; i<N; i++){
		        char c = sc.next().charAt(0);
		        holder.addChar(c);
		        buf.append(holder.getFirstNonRepeated()).append(" ");
		    }
		    System.out.println(buf.toString());
		}
		sc.close();
	}
}
class StreamCountHolder{
    private HashMap<Character, Node> countHolder;
    private Node head = null;
    private Node last = null;
    private Node nonRepeatingNode = null;
    public StreamCountHolder(int size){
        countHolder = new HashMap<Character, Node>(size*4/3);
    }
    public void addChar(char c){
        Node n = countHolder.get(c);
        if(null == n){
            n = new Node(c);
            if(null == head){
        	head = n;
        	last = n;
            }else{
        	Node prev = last;
        	last = n;
        	prev.next = last;
        	last.prev = prev;
            }
            n.count++;
            countHolder.put(c, n);
            if(nonRepeatingNode == null){
                nonRepeatingNode = n;
            }
        }else{
            n.count++;
            if(n == nonRepeatingNode){
                //need to find new Node
        	Node temp = nonRepeatingNode.next;
        	nonRepeatingNode = null;
        	while(temp != null){
        	    if(temp.count == 1){
        		nonRepeatingNode = temp;
        		break;
        	    }
        	    temp = temp.next;
        	}
            }
        }
    }
    
    public String getFirstNonRepeated(){
	if(nonRepeatingNode == null){
	    return "-1";
	}
	return nonRepeatingNode.c+"";
    }
    class Node{
        char c;
        int count;
        Node next;
        Node prev;
        
        Node(char c){
            this.c = c;
        }
    }
}