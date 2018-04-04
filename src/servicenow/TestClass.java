package servicenow;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class TestClass {
    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	int N = sc.nextInt();
	int M = sc.nextInt();
	int T = sc.nextInt();
	int C = sc.nextInt();
	
	Graph G = new Graph(N);
	
	for(int i=0; i < M; i++){
	    int U = sc.nextInt();
	    int V = sc.nextInt();
	    
	    Node uNode = G.getNode(U-1);
	    Node vNode = G.getNode(V-1);
	    
	    uNode.addNeibh(vNode);
	    vNode.addNeibh(uNode);
	    
	}
	
	int src = sc.nextInt();
	int dest = sc.nextInt();
	
	String path = bfs(G, src, dest);
	int count = path.split(" ").length;
	System.out.println(count);
	System.out.println(path);
	
	sc.close();
    }
    
    public static String bfs(Graph G, int src, int dest){
	Node srcNode = G.getNode(src-1);
	
	Queue<Node> queue = new LinkedList<>();
	
	queue.add(srcNode);
	while(!queue.isEmpty()){
	    Node temp = queue.poll();
	    if(temp.value == dest){
		//Node has been found
		return temp.builder.toString() + temp.value;
	    }
	   
	    temp.visited = true;
	    for(Node node : temp.neigbhs){
		if(!node.visited){
		    node.append(temp.builder.toString() + temp.value + " ");
		    node.visited = true;   
		    queue.add(node);
		}
	    }
	    
	    
	}
	
	return null;
	
    }
    
    public static class Node implements Comparable<Node>{
	int value;
	Set<Node> neigbhs = new TreeSet<Node>();
	boolean visited = false;
	StringBuilder builder = new StringBuilder();
	
	public void append(String val){
	    builder.append(val);
	}
	
	public Node(int value){
	    this.value = value;
	}
	public void addNeibh(Node node){
	    neigbhs.add(node);
	}
	@Override
	public int hashCode() {
	    return value;
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
		return true;
	    if (obj == null)
		return false;
	    if (getClass() != obj.getClass())
		return false;
	    Node other = (Node) obj;
	    if (value != other.value)
		return false;
	    return true;
	}
	@Override
	public int compareTo(Node o) {
	    return this.value - o.value;
	}
	
    }
    
    public static class Graph{
	int N;
	Node[] nodes;
	
	public Node getNode(int i){
	    return nodes[i];
	}
	public Graph(int N){
	    this.N = N;
	    nodes = new Node[N];
	    
	    for(int i=0; i< N; i++){
		nodes[i] = new Node(i+1);
	    }
	}
    }
}
